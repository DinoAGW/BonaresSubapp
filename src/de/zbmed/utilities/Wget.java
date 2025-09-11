package de.zbmed.utilities;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Wget {
	public static void main(String[] args) throws Exception {
		download("https://nbg1.your-objectstorage.com/zalf-lza/2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0.zip",
				null);
		System.out.println("Wget Ende");
	}

	public static void download(String fileUrl, String targetPath) throws Exception {
		if (targetPath == null) {
			targetPath = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
		}
		try (InputStream in = new URL(fileUrl).openStream()) {
			Files.copy(in, Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
		}
	}
}
