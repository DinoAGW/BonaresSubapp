package de.zbmed;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;

import de.zbmed.api.Rosetta;
import de.zbmed.api.Zalf;
import de.zbmed.utilities.Drive;
import de.zbmed.utilities.Transferserver;
import de.zbmed.utilities.Wget;
import de.zbmed.utilities.Zip;

public class SubApp {
	public static final String fs = System.getProperty("file.separator");

	public static void main(String[] args) throws Exception {
//		bearbeite("82407524", "14903169", "dev");
		bearbeite("337532297", "337533490", "test");
		System.out.println("SubApp Ende");
	}

	static void bearbeite(String materialFlowId, String producerId, String rosettaInstance) throws Exception {
		JsonNode tas = Zalf.getToAcknowledge();
		for (JsonNode ta : tas) {
			String uuid = ta.get("uuid").asText();
			String mdId = ta.get("metadata_identifier").asText();
			int version = ta.get("version").asInt();
			if (version != 1)
				continue;
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
			Wget.download(url, targetPath);
//			System.out.println("Entpacke von " + targetPath + " nach " + Drive.workspace + fs + mdId + fs);
			Zip.unzip(targetPath, Drive.workspace + fs + mdId + fs);
			targetPathFile.delete();
			Transferserver ts = new Transferserver();
			try {
				ts.uploadFolder(Drive.workspace + fs + mdId + fs,
						"/exchange/lza/lza-zbmed/" + rosettaInstance + "/SubApp/" + mdId + "/");
			} finally {
				ts.disconnect();
			}
			String depositXml = Rosetta.submitDepositActivity(mdId, materialFlowId, producerId, rosettaInstance);
			System.out.println("Rückgabe:\n" + depositXml);
			String sipId = Rosetta.extractSipId(depositXml);
			Rosetta.waitTillProcessed(sipId, rosettaInstance);
			Zalf.acknowledge(uuid);
			Drive.loescheRekursiv(Drive.workspace + fs + mdId + fs);
			// TODO: Pakete von Transferserver auf L verschieben?
//			break;
		}
	}
}
