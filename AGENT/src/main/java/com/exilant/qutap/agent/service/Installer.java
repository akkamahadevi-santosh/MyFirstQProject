package com.exilant.qutap.agent.service;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.exilant.qutap.agent.Agent;
import com.exilant.qutap.agent.error.AgentInitException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Installer {

	private static final long serialVersionUID = 1L;

	private static String PATH = "/agent/Installer";
	private static final int BUFFER_SIZE = 4096;
	public static String PLUGINPATH = "../agent-installer-0.1/plugins";

	public String installer(String runnerName) throws AgentInitException {

		Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
		String path = PROPS.getProperty("pluginInstaller");
		String sucess = "success";

		try {
			
			URL dl = null;
			dl = new URL(path+runnerName);
		
			System.out.println("EVENNNNNNNNNNNNNNNNNNNNNNNNNN"+path+runnerName);
	//		String s[]=dl.getPath().split("/");
			System.out.println("MWWWWWWWWWWWWWWWWWWWWWWWW"+dl.getFile().substring(19));
			System.out.println("FKDSJFAJKFDJSFDJSAFJ"+PLUGINPATH+dl.getFile().substring(19));
			File f = new File(PLUGINPATH+dl.getFile().substring(19));
			FileUtils.copyURLToFile(dl, f);
			System.out.println("ccccccccccccccccccccccccccccccccccccccccc");
		} catch (Exception e) {
			sucess = "fail";
			e.printStackTrace();
		}
		return sucess;
	}

	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		File f = new File(zipFilePath);
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				System.out.println("fullPath:123::::::"+filePath);
				extractFile(zipIn, filePath);
			}

			else {
				// if the entry is a directory, make the directory
				System.out.println("fullPath:::::::"+filePath);
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
		f.delete();

	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

}
