package com.exilant.qutap.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
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

import com.exilant.qutap.agent.Uploading;
import com.exilant.qutap.plugin.LoadConfiguration;

@SuppressWarnings("deprecation")
public class UploadPlugin implements Uploading{

	@Override
	public String videoUpload(String path, String fileName) {
		System.out.println("Calling in video plugin::::::::;videoUpload");
		
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
			
			System.out.println("inside if ::::::::;videoUpload");
			
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
		System.out.println("response of video path in plugin::::::::::"+strRes);
		return strRes;
		
	}
	
	

	@Override
	public String screenShotUpload(String path, String fileName) {
		System.out.println("Calling in video plugin::::::::;screenShotUpload");
		
		String strRes = "";

		//Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
		String directoryPath = path;
		
		//String backUpFolderPath = path+"/videoFileBackUpFolderPath";
		LoadConfiguration loadConfig = new LoadConfiguration();
		Properties PROPS = loadConfig.getProperties();
		String serverPathToUploadVideo= PROPS.getProperty("serverPathToUploadScreenshot");
		File file = getFileName(directoryPath, fileName);
		//System.out.println("666666666666666666666666666666"+file.getAbsolutePath()+"12111111111111111111111111111"+file.getName());
		if (!(file == null)) {
			System.out.println("inside if ::::::::;screenShotUpload");
			
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
			System.out.println("inside if after execute::::::::;screenShotUpload");
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
