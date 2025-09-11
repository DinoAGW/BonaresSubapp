package de.zbmed.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

//import com.opencsv.CSVReader;

/*
 * diese Klasse vereint möglichst alles, was mit Dateizugriff auf der Festplatte zu tun hat (um eine Übersicht zu haben)
 * und bietet auch ein paar Dateioperationen, die immer wieder genutzt werden
 */
public class Drive {
	private static final String fs = System.getProperty("file.separator");
	public static final String osName = System.getProperty("os.name");
	public static final String home = System.getProperty("user.home");
	public static final String workspace = home + fs + "workspace" + fs + "BonaresSubapp";

	public static void main(String[] args) throws Exception {
		loescheRekursiv("/home/wutschka/workspace/BonaresSubapp/2e6185dc-1fcd-4253-bcb7-499abf005db0/");
		System.out.println("Drive Ende");
	}

	public static void saveStringToFile(String str, String datei) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(datei)) {
			out.println(str);
		}
	}

	public static String loadFileToString(File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("Datei " + file.getAbsolutePath() + " existiert nicht.");
		}
		Charset encoding = Charset.defaultCharset();
		byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
		return new String(encoded, encoding);
	}

	public static void geheSicherDassOrdnerExistiert(String ziel) {
		File zielFile = new File(ziel);
		if (!zielFile.exists()) {
			zielFile.mkdirs();
		}
	}

	public static void loescheFallsExistiert(String ziel) {
		File zielFile = new File(ziel);
		if (zielFile.exists()) {
			zielFile.delete();
		}
	}

	public static void loescheRekursiv(String ziel) throws Exception {
		File zielFile = new File(ziel);
		if (!zielFile.exists()) {
			throw new Exception(ziel + " existiert nicht");
		}
		if (zielFile.isFile()) {
			zielFile.delete();
		} else if (zielFile.isDirectory()) {
			for (File insider : zielFile.listFiles()) {
				loescheRekursiv(insider.getAbsolutePath());
			}
			zielFile.delete();
		} else {
			throw new Exception("Unerwartete Alternative " + ziel);
		}
	}

//	public static String[][] readCsvFileMehrspaltig(File file) throws Exception {
//		List<String[]> data = new Stack<>();
//		try (CSVReader reader = new CSVReader(new FileReader(file))) {
//			String[] nextLine;
//			while ((nextLine = reader.readNext()) != null) {
//				data.add(nextLine);
//			}
//		}
//		String[][] ret = new String[data.size()][];
//		for (int i = 0; i < data.size(); ++i) {
//			ret[i] = data.get(i);
//		}
//		return ret;
//	}
//
//	public static String[] readCsvFileEinspaltig(File file) throws Exception {
//		String[][] data = readCsvFileMehrspaltig(file);
//		String[] ret = new String[data.length];
//		for (int i = 0; i < data.length; ++i) {
//			ret[i] = data[i][0];
//		}
//		return ret;
//	}
//
//	public static void printZeile(String[] zeile) {
//		StringBuilder sb = new StringBuilder();
//		if (zeile.length > 0) {
//			sb.append("'" + zeile[0] + "'");
//		}
//		for (int i = 1; i < zeile.length; ++i) {
//			sb.append(",");
//			sb.append("'" + zeile[i] + "'");
//		}
//		System.out.println(sb);
//	}
}
