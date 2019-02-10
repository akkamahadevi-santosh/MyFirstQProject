//package com.exilant.qutap.plugin;
//
//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Properties;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.client.ClientProtocolException;
//
//import org.junit.AfterClass;
//
//import org.junit.BeforeClass;
//
//import org.junit.Test;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//
//import org.mockito.runners.MockitoJUnitRunner;
//import org.mockserver.client.server.MockServerClient;
//import org.mockserver.integration.ClientAndServer;
//import org.mockserver.model.Header;
//import org.mockserver.model.HttpStatusCode;
//
//import com.google.appengine.repackaged.com.google.api.client.http.HttpHeaders;
//
//import static org.mockserver.model.HttpResponse.response;
//import static org.mockserver.integration.ClientAndServer.startClientAndServer;
//import static org.mockserver.model.HttpRequest.request;
//import static org.mockserver.model.HttpResponse.response;
//
//
//@SuppressWarnings("deprecation")
//@RunWith(MockitoJUnitRunner.class)
//public class UploadPluginTest {
//	
//
//	static LoadConfiguration loadConfig = new LoadConfiguration();
//	static Properties PROPS = loadConfig.getProperties();
//	
//	static String memberIp=PROPS.getProperty("memberIp");
//	static String port=PROPS.getProperty("port");
//	static int portNo=Integer.parseInt(port);
//	
//	static MockServerClient mockServer = new MockServerClient(memberIp, portNo);
//	 
//	 @InjectMocks
//	 private static UploadPlugin uploadPlugin;
//	
//	
//	
//	@BeforeClass
//    public static void startServer() {
//        mockServer = startClientAndServer(portNo);
//    }
//	
//	
//	@Test
//	public void testVideoUpload() throws ClientProtocolException, IOException, InterruptedException, ExecutionException {
//		
//		String videoFileFolderPath=PROPS.getProperty("videoFileFolderPath");
//		String expectedResFrVideoUpload=PROPS.getProperty("expectedResFrVideoUpload");
//		
//		//--------------creating a test file-----------------
//		
//		File dir = new File(videoFileFolderPath);
//		File testVideoFile = new File(dir+"/"+"testVideoFile.txt");
//		
//		//File file = new File("c://temp//testFile1.txt");
//		  
//		//Create the file
//		if (testVideoFile.createNewFile())
//		{
//		    System.out.println("File is created!");
//		} else {
//		    System.out.println("File already exists.");
//		}
//		 
//		//Write Content
//		FileWriter writer = new FileWriter(testVideoFile);
//		writer.write("Test data");
//		writer.close();
//	
////--------------------mock httpClient.execute(post) method------------------
//	
//		String expected=expectedResFrVideoUpload;
//		
//		mockServer.when(
//			      request()
//			        .withMethod("POST")
//			        .withPath("/qutapServer/upload")
//			        //.withHeader("\"enctype\", \"multipart/form-data\"")
//			       //.withHeaders(new Header("Accept", "text/xml, multipart/related"))
//                    //.withBody("testVideoFile.txt")
//			       
//			    ).respond(
//			      response()
//			        .withStatusCode(HttpStatusCode.OK_200.code())
//			        .withHeader(Header.header("Content-Type", "text/plain"))
//			        .withBody(expectedResFrVideoUpload));
//			  
//		
//		//Mockito.when(mHttpClient.execute(httppost)).thenReturn(response);
//		
//		String resp=uploadPlugin.videoUpload(dir.getAbsolutePath(), testVideoFile.getName());
//		//String resp=uploadPlugin.videoUpload(dir.getAbsolutePath(), "TestCaseId5c4efaa5ae0975284b9eb185-2019-01-28 19.01.57.mp4");
//		
//		System.out.println("expected:......"+expected);
//		System.out.println("resp:......"+resp);
//		
//		assertEquals(expected, resp);
//		
//	}
//	
//	
//	@Test
//	public void testScreenshotUpload() throws ClientProtocolException, IOException, InterruptedException, ExecutionException {
//		
//		LoadConfiguration loadConfig = new LoadConfiguration();
//		Properties PROPS = loadConfig.getProperties();
//		
//		String screenshotFileFolderPath=PROPS.getProperty("screenshotFileFolderPath");
//		String expectedResForScreenshotUpload=PROPS.getProperty("expectedResForScreenshotUpload");
//		
//		//--------------creating a test file-----------------
//		
//		File dir = new File(screenshotFileFolderPath);
//		File testScreenshotFile = new File(dir+"/"+"testScreenshotFile.txt");
//		
//		//File file = new File("c://temp//testFile1.txt");
//		  
//		//Create the file
//		if (testScreenshotFile.createNewFile())
//		{
//		    System.out.println("File is created!");
//		} else {
//		    System.out.println("File already exists.");
//		}
//		 
//		//Write Content
//		FileWriter writer = new FileWriter(testScreenshotFile);
//		writer.write("Scrrenshot File Test data");
//		writer.close();
//	
////--------------------mock httpClient.execute(post) method------------------
//	
//		String expected=expectedResForScreenshotUpload;
//		
//		mockServer.when(
//			      request()
//			        .withMethod("POST")
//			        .withPath("/qutapServer/uploadScreenShot")
//			    ).respond(
//			      response()
//			        .withStatusCode(HttpStatusCode.OK_200.code())
//			        .withHeader(Header.header("Content-Type", "text/plain"))
//			        .withBody(expectedResForScreenshotUpload));
//			  
//		
//		//Mockito.when(mHttpClient.execute(httppost)).thenReturn(response);
//		
//		String resp=uploadPlugin.screenShotUpload(dir.getAbsolutePath(), testScreenshotFile.getName());
//		
//		System.out.println("expected:......"+expected);
//		System.out.println("resp:......"+resp);
//		
//		//String expected=EntityUtils.toString(response.getEntity());
//		
//		assertEquals(expected, resp);
//		
//		
//	}
//	
//	
//	@Test
//	public void testVideoUploadForInvalidReq() throws ClientProtocolException, IOException, InterruptedException, ExecutionException {
//		
//		 mockServer.when(
//			      request()
//			        .withMethod("POST")
//			        .withPath("/qutap/upload")
//			    )
//	                .respond(
//	                    response()
//	                        .withStatusCode(401)
//	                        .withHeaders(
//	                            new Header("Content-Type", "application/json; charset=utf-8"),
//	                            new Header("Cache-Control", "public, max-age=86400")
//	                    )
//	                        .withBody("{ message: 'incorrect url' }")
//	                        .withDelay(TimeUnit.SECONDS,1)
//	                );
//	
//	}
//	
//	
//	
//	@Test
//	public void testScreenshotUploadForInvalidReq() throws ClientProtocolException, IOException, InterruptedException, ExecutionException {
//		
//		 mockServer.when(
//			      request()
//			        .withMethod("POST")
//			        .withPath("/qutap/uploadScreenShot")
//			    )
//	                .respond(
//	                    response()
//	                        .withStatusCode(401)
//	                        .withHeaders(
//	                            new Header("Content-Type", "application/json; charset=utf-8"),
//	                            new Header("Cache-Control", "public, max-age=86400")
//	                    )
//	                        .withBody("{ message: 'incorrect url' }")
//	                        .withDelay(TimeUnit.SECONDS,1)
//	                );
//	
//	}
//	
//	
//	
//	@AfterClass
//    public static void stopServer() {
//        mockServer.stop();
//    }
//	
//	
//	
//}
