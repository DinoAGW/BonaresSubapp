package de.zbmed.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class Zip {
	public static final String fs = System.getProperty("file.separator");

	public static void main(String[] args) throws Exception {
		unzip(Drive.workspace + fs + "2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0.zip", null);
		System.out.println("Zip Ende");
	}

	public static void unzip(String zipFileString, String outputFolderOpt) throws Exception {
		if (!zipFileString.endsWith(".zip")) {
			throw new Exception("Muss eine zip Datei sein");
		}
		String outputFolder;
		if (outputFolderOpt == null) {
			outputFolder = zipFileString.substring(0, zipFileString.length() - 4) + fs;
		} else {
			if (!outputFolderOpt.endsWith(fs)) {
				throw new Exception("OutputFolder muss mit " + fs + " enden");
			}
			outputFolder = outputFolderOpt;
		}
		ZipFile zipFile = new ZipFile(zipFileString);

		zipFile.stream().forEach(entry -> {
			try (InputStream is = zipFile.getInputStream(entry)) {
				File outFile = new File(outputFolder + entry.getName());
				outFile.getParentFile().mkdirs();
				try (FileOutputStream fos = new FileOutputStream(outFile)) {
					byte[] buffer = new byte[1024];
					int len;
					while ((len = is.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		zipFile.close();
	}
}
