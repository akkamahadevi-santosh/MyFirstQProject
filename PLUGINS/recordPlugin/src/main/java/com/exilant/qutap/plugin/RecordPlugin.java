package com.exilant.qutap.plugin;

import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.util.Properties;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import com.exilant.qutap.agent.Recording;
import com.exilant.qutap.plugin.LoadConfiguration;
import com.exilant.qutap.plugin.SpecializedScreenRecorder;
//Interface Task Extended from Agent
public class RecordPlugin implements Recording {
	
	private ScreenRecorder screenRecorder;
	@Override
	public void startRecording(String testCaseId) 
    {    
		try
		{
			System.out.println("yessssssssssssssssssssssssssssss in recording");              
			LoadConfiguration loadConfig = new LoadConfiguration();
			Properties PROPS = loadConfig.getProperties();
			String FILE_LOCATION = PROPS.getProperty("vedioPath_In_Agent");
           File file = new File(FILE_LOCATION);
           System.out.println("yessssssssssssssssssssssssssssss in recording");              
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           int width = screenSize.width;
           int height = screenSize.height;
                          
           Rectangle captureSize = new Rectangle(0,0, width, height);
                          
         GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();
		
		
         
         
         
//       this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
//		   new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,MIME_QUICKTIME),
//     new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_JPEG,
//          CompressorNameKey, ENCODING_QUICKTIME_JPEG,
//          DepthKey, 24, FrameRateKey, Rational.valueOf(15),
//          QualityKey, 1.0f,
//          KeyFrameIntervalKey, 15 * 60),
//     new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
//          FrameRateKey, Rational.valueOf(30)),
//     null, file, "MyVideo");
//       
      
         
         
        this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
     		   new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,MIME_AVI),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                 CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                 DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                 QualityKey, 1.0f,
                 KeyFrameIntervalKey, 15 * 60),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                 FrameRateKey, Rational.valueOf(30)),
            null, file, "TestCaseId"+testCaseId);
 
       this.screenRecorder.start();
		}
		catch(Exception e)
		{
			System.out.println(""+e);
		}
    
    }
	@Override
    public void stopRecording() 
    {
		try
		{
          this.screenRecorder.stop();
		}
		catch(Exception e)
		{
			System.out.println(""+e);
		}
    }

//	private ScreenRecorder screenRecorder;
//	@Override
//	public void startRecording() {
//		File file = new File("/Users/manjunath.shet/Desktop/Videos");
//        
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = screenSize.width;
//        int height = screenSize.height;
//                       
//        Rectangle captureSize = new Rectangle(0,0, width, height);
//                       
//      GraphicsConfiguration gc = GraphicsEnvironment
//         .getLocalGraphicsEnvironment()
//         .getDefaultScreenDevice()
//         .getDefaultConfiguration();
//      this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
//     		   new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,MIME_AVI),
//            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                 CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                 DepthKey, 24, FrameRateKey, Rational.valueOf(15),
//                 QualityKey, 1.0f,
//                 KeyFrameIntervalKey, 15 * 60),
//            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
//                 FrameRateKey, Rational.valueOf(30)),
//            null, file, "MyVideo");
//       this.screenRecorder.start();
//		
//	}
//
//	@Override
//	public void stopRecording() {
//		this.screenRecorder.stop();
//		
//		
//	}

 
	 
	  

    

}
