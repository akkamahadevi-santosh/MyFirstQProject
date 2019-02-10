package com.exilant.qutap.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.AttachmentPart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.exilant.qutap.engine.exception.ITAPEngineException;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;
import com.hazelcast.instance.HazelcastInstanceFactory;

@Controller
public class ZipFileUploadController {

	private static final String FILE_TYPE = ".zip";

	private static final int BUFFER_SIZE = 4096;

	LoadConfiguration loadConfig = new LoadConfiguration();
	Properties PROPS = loadConfig.getProperties();

	@RequestMapping(value = "/plugin/{pluginName}", method = RequestMethod.GET)
	public void getPlugin(@PathVariable("pluginName") String fileLocation, HttpServletResponse response) {
		String FILE_LOCATION = PROPS.getProperty("pluginPath");
		try {
			File pluginPath=new File(FILE_LOCATION);
			File[] pluginsPath=pluginPath.listFiles();
			for(File value:pluginsPath) {

				if(value.getName().trim().toLowerCase().startsWith(fileLocation.substring(0, fileLocation.length()-8).trim().toLowerCase())) {


					fileLocation = FILE_LOCATION+"/"+value.getName()+"/target/"+fileLocation+FILE_TYPE;
					File file = new File(fileLocation);

					InputStream inputStream = new FileInputStream(file);

					response.setContentType("application/octet-stream");
					response.setHeader("Content-disposition", "attachment; filename=" + file.getName());

					org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());

					response.flushBuffer();
				}
			}
		}
		catch (IOException ex) {
			throw new ITAPEngineException("IOException " + response + ":" + ex);
		}

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "text/plain")
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file) {
		String response = null;
		//String FILE_LOCATION = PROPS.getProperty("recordPath");
		String FILE_LOCATION=System.getProperty("catalina.home");

		File dir=new File(FILE_LOCATION+"/videoFolder");
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filePath =dir+ "/"+file.getOriginalFilename();




		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(bytes);
				stream.close();
				response =  filePath ;
			} catch (Exception e) {
				throw new ITAPEngineException("IOException " + "You failed to upload " + ":" + e);
			}
		} else {
			response = "You failed to upload " + filePath + " because the file was empty";
		}
		return response;
	}


	@RequestMapping(value = "/uploadScreenShot", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "text/plain")
	public @ResponseBody String handleFileUploadScreenShot(@RequestParam("file") MultipartFile file) {
		String response = null;
		//String FILE_LOCATION = PROPS.getProperty("recordPath");
		String FILE_LOCATION=System.getProperty("catalina.home");

		File dir=new File(FILE_LOCATION+"/ScreenShotFolder");
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filePath =dir+ "/"+file.getOriginalFilename();


		if (!file.isEmpty()) {
			try {

				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(bytes);
				stream.close();
				response =  filePath ;
			} catch (Exception e) {
				throw new ITAPEngineException("IOException " + "You failed to upload " + ":" + e);
			}
		} else {
			response = "You failed to upload " + filePath + " because the file was empty";
		}
		return response;
	}

	@RequestMapping(path = "/user", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody String appendEvent(@RequestBody String agentInfo) throws JSONException {
		boolean success;

		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject agentInfo1 = new JSONObject(agentInfo);
		map = toMap(agentInfo1);

		Queue<Map> queue = new LinkedList<Map>();
		queue.add(map);


		return "sucess";
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}

		return map;
	}

	@RequestMapping(value = "/uploadMultipleFile/{pluginName}", method = RequestMethod.GET)
	public String uploadMultipleFileHandler(@PathVariable("pluginName") String agentInstallerFile) {
		String message = null;
		File file;
		String totalFilePath = getClass().getClassLoader().getResource(PROPS.getProperty("agentInstallerZipPath")).toString();
		String accurateFilePath = totalFilePath.substring(totalFilePath.indexOf(':')+1);

		String agentZipPath = accurateFilePath  + agentInstallerFile + FILE_TYPE;
		String shellScriptPath = accurateFilePath  + PROPS.getProperty("shellScriptName")
		+ ".sh";

		FileInputStream input;
		MultipartFile multipartFileAgent = null;

		// FileInputStream input1;
		MultipartFile multipartFileShell = null;

		try {
			file = new File(agentZipPath);
			input = new FileInputStream(file);
			multipartFileAgent = new MockMultipartFile("file", file.getName(), "application/octet-stream",
					IOUtils.toByteArray(input));

			file = new File(shellScriptPath);
			input = new FileInputStream(file);
			multipartFileShell = new MockMultipartFile("file", file.getName(), "application/octet-stream",
					IOUtils.toByteArray(input));

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		MultipartFile[] files = { multipartFileAgent, multipartFileShell };
		String[] fileNames = { agentInstallerFile + ".zip", PROPS.getProperty("shellScriptName") + ".sh" };

		File dir = null;
		for (int i = 0; i < files.length; i++) {
			MultipartFile getEachFile = files[i];
			String fileName = fileNames[i];
			try {

				byte[] bytes = getEachFile.getBytes();
				String home = System.getProperty("user.home");
				dir = new File(home + "/Downloads/zip");
				if (!dir.exists())
					dir.mkdirs();

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				message = message + "You successfully uploaded file=" + fileName + "<br />";
			} catch (Exception e) {
				return "You failed to upload " + fileName + " => " + e.getMessage();
			}
		}

		String DownloadedShellScriptpath = dir.toString() + "/" + PROPS.getProperty("shellScriptName") + ".sh";
		// String command = "chmod 777"+" " +path;
		try {
			Thread.sleep(10000);
			Runtime.getRuntime().exec("chmod 777" + " " + DownloadedShellScriptpath);
			Process process = Runtime.getRuntime().exec("/usr/bin/open -a Terminal "+DownloadedShellScriptpath);
			Thread.sleep(30000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println("Script output: " + s);
			}

		} catch (IOException | InterruptedException e) {

			e.printStackTrace();
		}

		return message;
	}


}

