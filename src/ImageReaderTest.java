package src;
import static org.junit.Assert.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ImageReaderTest {
    private  ImplementImageIO  imageIO = new ImplementImageIO();
    private ImplementImageProcesser processer = new ImplementImageProcesser();
    private Image testImage;
    private Image goalImage;
    private String goalFilePath;
    private String testFilePath;
    public ImageReaderTest(String goalFilePath, String testFilePath) {
	this.goalFilePath = goalFilePath;
	this.testFilePath = testFilePath;
    }
    @Parameters
	public static Collection prepareData() {
	//test data
	Object[][] objects = {
	    {"image/goal/1_red_goal.bmp", "image/1.bmp"},
	    {"image/goal/1_green_goal.bmp", "image/1.bmp"},
	    {"image/goal/1_blue_goal.bmp", "image/1.bmp"},
	    {"image/goal/1_gray_goal.bmp", "image/1.bmp"},
	    {"image/goal/2_red_goal.bmp", "image/2.bmp"},
	    {"image/goal/2_green_goal.bmp", "image/2.bmp"},
	    {"image/goal/2_blue_goal.bmp", "image/2.bmp"},
	    {"image/goal/2_gray_goal.bmp", "image/2.bmp"}
	};
	return Arrays.asList(objects);
    }
    @Test
	public void testImageProcessor() throws IOException {
	goalImage = imageIO.myRead(goalFilePath);
	testImage = imageIO.myRead(testFilePath);
		if (goalFilePath.contains("red")) {
	    testImage = processer.showChanelR(testImage);
	}
	if (goalFilePath.contains("green")) {
	    testImage = processer.showChanelG(testImage);
	}
	if (goalFilePath.contains("blue")) {
	    testImage = processer.showChanelB(testImage);
	}
	if (goalFilePath.contains("gray")) {
	    testImage = processer.showGray(testImage);
	}
	assertEquals(testImage.getHeight(null), goalImage.getHeight(null));
	assertEquals(testImage.getWidth(null), goalImage.getWidth(null));
	int width = testImage.getWidth(null);
	int height = testImage.getHeight(null);
	BufferedImage goalBufferedImage = 
	    new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	goalBufferedImage.getGraphics().drawImage(goalImage, 0, 0, null);
	BufferedImage testBufferedImage = 
	    new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	testBufferedImage.getGraphics().drawImage(testImage, 0, 0, null);
	for (int i = 0 ; i < width ; ++i)
	    for (int j = 0 ; j < height ; ++j) {
		Object goalData = goalBufferedImage.getRaster().getDataElements(i, j, null);
		Object testData = testBufferedImage.getRaster().getDataElements(i, j, null);
		//test red
		int goalRed = goalBufferedImage.getColorModel().getRed(goalData);
		int testRed = testBufferedImage.getColorModel().getRed(testData);
		assertEquals(goalRed, testRed);
		//test blue
		int goalBlue = goalBufferedImage.getColorModel().getBlue(goalData);
		int testBlue = testBufferedImage.getColorModel().getBlue(testData);
		assertEquals(goalBlue, testBlue);
		//testGreen
		int goalGreen = goalBufferedImage.getColorModel().getGreen(goalData);
		int testGreen = testBufferedImage.getColorModel().getGreen(testData);
		assertEquals(goalGreen, testGreen);
		}
    }
}















