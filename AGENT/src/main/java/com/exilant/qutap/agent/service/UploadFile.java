package com.exilant.qutap.agent.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.exilant.qutap.agent.Agent;
import com.exilant.qutap.agent.error.AgentInitException;

@SuppressWarnings("deprecation")
public class UploadFile {

	
	
	public static String fileUpload(String path,String fileName) throws AgentInitException {
		String strRes = "";
		LoadConfiguration loadConfig = new LoadConfiguration();
		Properties PROPS = loadConfig.getProperties();
		//Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
		String directoryPath = path;
		String videoFileBackUpFolderPath=PROPS.getProperty("videoFileBackUpFolderPath");
		String backUpFolderPath = videoFileBackUpFolderPath;
		String serverPathToUploadVideo= PROPS.getProperty("serverPathToUploadVideo");
		File file = getFileName(directoryPath, fileName);
		if (!(file == null)) {

			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			@SuppressWarnings("resource")
			DefaultHttpClient mHttpClient = new DefaultHttpClient(params);
			int resultCode;
			try {
				HttpPost httppost = new HttpPost(serverPathToUploadVideo);
				httppost.addHeader("enctype", "multipart/form-data");
				MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				File filebody = new File(directoryPath+"/"+file.getName());
				
				multipartEntity.addPart("file", new FileBody(filebody));


				httppost.setEntity(multipartEntity);

			HttpResponse response = mHttpClient.execute(httppost);
			
			String res=EntityUtils.toString(response.getEntity());
			
			//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+res);	
			//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+response.toString());
				strRes=res;
			} catch (Exception e) {
				e.printStackTrace();
				resultCode = 2;
			}
			moveAndDeleteFile(directoryPath, backUpFolderPath, file);
		} else {
			System.out.println("FileNot Present");
		}
		return strRes;
	}
	public static String fileUploadScreenShot(String path,String fileName) throws AgentInitException {
		String strRes = "";

		//Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
		String directoryPath = path;
		//String backUpFolderPath = path+"/videoFileBackUpFolderPath";
		LoadConfiguration loadConfig = new LoadConfiguration();
		Properties PROPS = loadConfig.getProperties();
		String serverPathToUploadVideo= PROPS.getProperty("serverPathToUploadScreenShot");
		File file = getFileName(directoryPath, fileName);
		//System.out.println("666666666666666666666666666666"+file.getAbsolutePath()+"12111111111111111111111111111"+file.getName());
		if (!(file == null)) {

			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			@SuppressWarnings("resource")
			DefaultHttpClient mHttpClient = new DefaultHttpClient(params);
			int resultCode;
			try {
				HttpPost httppost = new HttpPost(serverPathToUploadVideo);
				httppost.addHeader("enctype", "multipart/form-data");
				MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				File filebody = new File(directoryPath+"/"+file.getName());
				
				multipartEntity.addPart("file", new FileBody(filebody));


				httppost.setEntity(multipartEntity);

			HttpResponse response = mHttpClient.execute(httppost);
			
			String res=EntityUtils.toString(response.getEntity());
			
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+res);	
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+response.toString());
				strRes=res;
			} catch (Exception e) {
				e.printStackTrace();
				resultCode = 2;
			}
			
		} else {
			System.out.println("FileNot Present");
		}
		return strRes;
	}
	public static File getFileName(String directoryPath, String fileName) {
		//System.out.println("*********"+directoryPath);
		File dir = new File(directoryPath);
		
	
		if (!dir.exists()) {
			dir.mkdir();
		}
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(fileName);
			}
		});
		if (foundFiles.length > 0) {

			return foundFiles[0];
		} else {
			return null;
		}
	}

	public static void moveAndDeleteFile(String directoryPath, String backUpFolderPath, File file) {

		File source = new File(directoryPath+"/"+file.getName());
		File dest = new File(backUpFolderPath);
		if (!dest.exists()) {
			dest.mkdir();
		}
		try {
			FileUtils.copyFileToDirectory(source, dest);
		file.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}