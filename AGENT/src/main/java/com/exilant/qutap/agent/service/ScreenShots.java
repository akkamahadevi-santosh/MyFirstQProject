package com.exilant.qutap.agent.service;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.exilant.qutap.agent.Agent;
import com.google.appengine.repackaged.org.joda.time.Period;



public class ScreenShots {

	// public static void main(String[] args) throws IOException,
	// InterruptedException {

	static void StartScreenShots(String path, String StartDateTime, String EndDateTime, String testStepId)
			throws IOException, InterruptedException, ParseException {

//		Date startDate = new Date(StartDateTime);
//		Date EndDate = new Date(EndDateTime);
//		System.out.println(startDate);
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		System.out.println("hhhhhhhhhhhhHHHHHHHHHHHHHH"+StartDateTime);
		System.out.println("hhhhhhhhhhhhHHHHHHHHHHHHHH"+EndDateTime);
		Date startDate = df.parse(StartDateTime.substring(11));
		Date EndDate = df.parse(EndDateTime.substring(11));
		
		long miliseconds=EndDate.getTime()-startDate.getTime();
		
		System.out.println("*******************");
		System.out.println(miliseconds);
		
		System.out.println("*******************");
		

		Desktop.getDesktop().open(new File(path));

		Thread.sleep(miliseconds);
		captureScreen(testStepId,StartDateTime);
		
	
	}

	// }
	// int delay,String fileName

	public static void captureScreen(String testStepId,String StartDateTime) throws IOException {

		try {
			Properties PROPS = Agent.loadConfigurations(Agent.AGENT_CONFIG);
			String directoryPath = PROPS.getProperty("screenShotFileFolder");
			File destDir = new File(directoryPath);
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

			BufferedImage capture = new Robot().createScreenCapture(screenRect);

			ImageIO.write(capture, "png",
					new File(directoryPath + testStepId+"-"+StartDateTime + ".jpeg"));

		} catch (Exception ex) {

			System.out.println(ex);

		}

	}

}


