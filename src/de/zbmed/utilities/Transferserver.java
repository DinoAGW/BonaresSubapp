package de.zbmed.utilities;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Transferserver {
	private static final String fs = System.getProperty("file.separator");
	private String host;
	private int port;
	private String user;
	private String privateKeyPath;
	private String keyPasswort;
	private JSch jsch;
	private Session session;
	private Channel channel;
	private ChannelSftp sftpChannel;

	public Transferserver() throws Exception {
		host = Custom.getSftpAdresse();
		port = 22;
		user = Custom.getSftpUsername();
		privateKeyPath = Drive.home + Custom.getSftpKeyFile();
		keyPasswort = Custom.getSftpKeyPwd();
		jsch = new JSch();
		jsch.addIdentity(privateKeyPath, keyPasswort);
		jsch.setKnownHosts("~/.ssh/known_hosts");
		session = jsch.getSession(user, host, port);
		session.connect();
		channel = session.openChannel("sftp");
		channel.connect();
		sftpChannel = (ChannelSftp) channel;
	}

	public void disconnect() {
		if (sftpChannel != null && sftpChannel.isConnected()) {
			sftpChannel.disconnect();
		}
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
	}

	public void uploadFile(String localFilePath, String remoteFilePath) throws Exception {
		sftpChannel.put(localFilePath, remoteFilePath);
	}

	public void uploadFolder(String localFolderPath, String remoteFolderPath) throws Exception {
		if (!localFolderPath.endsWith(fs)) {
			throw new Exception("Es wird erwartet, dass localFolderPath mit " + fs + " endet: " + localFolderPath);
		}
		if (!remoteFolderPath.endsWith("/")) {
			throw new Exception("Es wird erwartet, dass localFolderPath mit / endet: " + remoteFolderPath);
		}
		File localFolder = new File(localFolderPath);
		if (!localFolder.exists() || !localFolder.isDirectory()) {
			throw new Exception("Es muss sich um einen existierenden Ordner handeln: " + localFolderPath);
		}
//		System.out.println("Erzeuge Ordner: " + remoteFolderPath);
		mkdir(remoteFolderPath);
		for (File insider : localFolder.listFiles()) {
			if (insider.getName().startsWith(".")) {
				System.out.println("Überspringe: " + insider.getAbsolutePath());
				continue;
			}
			if (insider.isFile()) {
//				System.out.println("Lade hoch: " + insider.getAbsolutePath());
				String name = insider.getName();
				uploadFile(localFolderPath + name, remoteFolderPath + name);
			} else if (insider.isDirectory()) {
				String name = insider.getName();
				uploadFolder(localFolderPath + name + fs, remoteFolderPath + name + "/");
			} else {
				System.err.println("Ausnahmefall nicht erwartet: " + insider.getAbsolutePath());
			}
		}
	}

	public void mkdir(String remoteFolderPath) throws Exception {
		try {
			sftpChannel.mkdir(remoteFolderPath);
		} catch (Exception e) {
			System.err.println("Fehler beim erstellen des Ordners " + remoteFolderPath);
			throw e;
		}
	}

	public void ls(String remoteFilePath) throws Exception {
		Vector<LsEntry> lses = sftpChannel.ls(remoteFilePath);
		for (LsEntry lse : lses) {
			if (lse.getAttrs().isDir()) {
				System.out.println(lse.getFilename() + "/");
			} else {
				System.out.println(lse.getFilename());
			}
		}
	}

	public void getFile(String remoteFilePath, String localFilePath) throws Exception {
		InputStream is = sftpChannel.get(remoteFilePath);
		Files.copy(is, Paths.get(localFilePath));
	}

	public void getFolder(String remoteFolderPath, String localFolderPath) throws Exception {
		if (!localFolderPath.endsWith(fs)) {
			throw new Exception("Es wird erwartet, dass localFolderPath mit " + fs + " endet: " + localFolderPath);
		}
		if (!remoteFolderPath.endsWith("/")) {
			throw new Exception("Es wird erwartet, dass localFolderPath mit / endet: " + remoteFolderPath);
		}
		File localFolder = new File(localFolderPath);
		if (localFolder.exists()) {
			throw new Exception("Datei oder Ordner existiert bereits: " + localFolderPath);
		}
		if (!localFolder.mkdirs()) {
			throw new Exception("OrdnerErstellung hat nicht geklappt: " + localFolderPath);
		}
		Vector<LsEntry> lses = sftpChannel.ls(remoteFolderPath);
		for (LsEntry lse : lses) {
			if (lse.getAttrs().isDir()) {
				String folderName = lse.getFilename();
				getFolder(remoteFolderPath + folderName + "/", localFolderPath + folderName + fs);
			} else {
				String fileName = lse.getFilename();
				getFile(remoteFolderPath + fileName, localFolderPath + fileName);
			}
		}
	}

	public void removeFile(String remoteFilePath) throws Exception {
		sftpChannel.rm(remoteFilePath);
	}

	public static void main(String[] args) throws Exception {
		Transferserver ts = new Transferserver();
		try {
//			ts.uploadFolder("2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0" + fs,
//					"/exchange/lza/lza-zbmed/dev/SubApp/2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0/");
//			ts.ls("/exchange/lza/lza-zbmed/dev/SubApp/");
			ts.getFolder("/exchange/lza/lza-zbmed/dev/SubApp/2025_09_04_2e6185dc-1fcd-4253-bcb7-499abf005db0/",
					"/home/wutschka/L/04_Versuchs_SIPs/dev/SubApp/Versuch1/");
		} finally {
			ts.disconnect();
		}
	}
}
