package de.zbmed;

import java.io.File;
import java.util.List;
import java.util.Stack;

import com.fasterxml.jackson.databind.JsonNode;

import de.zbmed.api.Rosetta;
import de.zbmed.api.Zalf;
import de.zbmed.utilities.Drive;
import de.zbmed.utilities.Transferserver;
import de.zbmed.utilities.Wget;
import de.zbmed.utilities.Zip;

public class SubApp {
	public static String fs = System.getProperty("file.separator");
	private static String lOrdner = Drive.home + "/L/02_SIPs_PROD/03_BonaRes/";
	private static Boolean breakAfterIngest = false;

	public static void main(String[] args) throws Exception {
//		bearbeite("82407524", "14903169", "dev");
//		bearbeite("337532297", "337533490", "test");
		bearbeite("2716999051", "2716919169", "prod");

		System.out.println("SubApp Ende");
	}

	static void bearbeite(String materialFlowId, String producerId, String rosettaInstance) throws Exception {
		JsonNode tas = Zalf.getToAcknowledge();
		Transferserver ts = new Transferserver();
		List<String> fehlermeldungen = new Stack<>();
		for (JsonNode ta : tas) {
			String uuid = ta.get("uuid").asText();
			String mdId = ta.get("metadata_identifier").asText();
			int version = ta.get("version").asInt();
			if (version != 1)
				continue;
			if (new File(lOrdner + mdId).exists())
				throw new Exception("mdId '" + mdId + "' wurde anscheinend schon ingestet.");
			String url = ta.get("url").asText();
			if (!url.startsWith("https://nbg1.your-objectstorage.com/zalf-dis-lza/") || !url.endsWith(mdId + ".zip")) {
				// TODO: url sollte später ganz bestimmte Form haben
				throw new Exception(
						"Url muss z.B. aussehen wie: https://nbg1.your-objectstorage.com/zalf-dis-lza/2e6185dc-1fcd-4253-bcb7-499abf005db0.zip\n, ist aber: '"
								+ url + "'");
			}
//			System.out.println(ta.toPrettyString());
			System.out.println("Verarbeite " + uuid + " (" + mdId + ") = " + url);
			String targetPath = Drive.workspace + fs + mdId + ".zip";
			File targetPathFile = new File(targetPath);
			if (targetPathFile.exists()) {
				throw new Exception("Datei " + targetPath + " existiert schon");
			}
//			System.out.println("Lade herunter von " + url + " nach " + targetPath);
			try {
				Wget.download(url, targetPath);
			} catch (Exception e) {
				System.err.println("Fehler beim Download:\n" + e.getMessage());
				fehlermeldungen
						.add("Fehler beim Download von " + uuid + " (" + mdId + ") = " + url + ":\n" + e.getMessage());
				continue;
			}
//			System.out.println("Entpacke von " + targetPath + " nach " + Drive.workspace + fs + mdId + fs);
			Zip.unzip(targetPath, Drive.workspace + fs + mdId + fs);
			targetPathFile.delete();
			ts.uploadFolder(Drive.workspace + fs + mdId + fs,
					"/exchange/lza/lza-zbmed/" + rosettaInstance + "/SubApp/" + mdId + "/");
			String depositXml = Rosetta.submitDepositActivity(mdId, materialFlowId, producerId, rosettaInstance);
			System.out.println("Rückgabe:\n" + depositXml);
			String sipId = Rosetta.extractSipId(depositXml);
			Rosetta.waitTillProcessed(sipId, rosettaInstance);
			Zalf.acknowledge(uuid);
			Drive.loescheRekursiv(Drive.workspace + fs + mdId + fs);
			// Pakete auf L verschieben
			ts.getFolder("/exchange/lza/lza-zbmed/" + rosettaInstance + "/SubApp/" + mdId + "/", lOrdner + mdId + "/");
			ts.removeFolder("/exchange/lza/lza-zbmed/" + rosettaInstance + "/SubApp/" + mdId + "/");
			if (breakAfterIngest) {
				break;
			}
		}
		ts.disconnect();
		System.out.println("Fehlermeldungen:");
		for (String fehlermeldung : fehlermeldungen) {
			System.out.println(fehlermeldung);
		}
	}
}
