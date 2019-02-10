package com.exilant.qutap.agent.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.exilant.qutap.agent.Agent;
import com.exilant.qutap.agent.error.AgentInitException;

public class VideoAndScreenShot {
	
	/*public String uploadVideo(File file) throws AgentInitException {

		Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
		String path = PROPS.getProperty("qutapDashPathToUploadVideo");
		String sucess = "success";

		try {
			
			URL dl = null;
			dl = new URL(path+file);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost uploadFile = new HttpPost(path);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			File f = new File(file.getAbsolutePath());
			builder.addBinaryBody(
			    "file",
			    new FileInputStream(f),
			    ContentType.APPLICATION_OCTET_STREAM,
			    f.getName()
			);

			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			CloseableHttpResponse response = httpClient.execute(uploadFile);
			HttpEntity responseEntity = response.getEntity();
			
			
		} catch (Exception e) {
			sucess = "fail";
			e.printStackTrace();
		}
		return sucess;
	}*/

}
