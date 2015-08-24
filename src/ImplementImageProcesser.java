import java.awt.image.MemoryImageSource;
import imagereader.IImageProcessor;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import java.awt.*;
//the interface was use to handle different rbg chanel
interface HandleRGB {
    public int handle(int rgb);
}
class ImplementImageProcesser implements IImageProcessor {
    private final int RED_CHANEL = 0xffff0000;
    private final int GREEN_CHANEL = 0xff00ff00;
    private final int BLUE_CHANEL = 0xff0000ff;
    private final int HIGH_BIT_ZEOR = 0x00ffffff;
    private final int HEX = 0xff;
    public Image showChanelR(Image sourceImage) {
	return show(sourceImage, new HandleRGB() {
		public int handle(int rgb) {
		    return rgb & RED_CHANEL;
		}
	    });
    }
    public Image showChanelG(Image sourceImage) {
	return show(sourceImage, new HandleRGB() {
		public int handle(int rgb) {
		    return rgb & GREEN_CHANEL;
		}
	    });
    }
    public Image showChanelB(Image sourceImage) {
	return show(sourceImage, new HandleRGB() {
		public int handle(int rgb) {
		    return rgb & BLUE_CHANEL;
		}
	    });
    }
    public Image showGray(Image sourceImage) {
	return show(sourceImage, new HandleRGB() {
		public int handle(int rgb) {
		    int R = (rgb & RED_CHANEL & HIGH_BIT_ZEOR) >>16;
		    int G = (rgb & GREEN_CHANEL & HIGH_BIT_ZEOR) >> 8;
		    int B = (rgb & BLUE_CHANEL & HIGH_BIT_ZEOR);
		    int grey =  (int)(0.299 * R + 0.587 * G + 0.144 * B);
		    return grey + (grey << 8) + (grey << 16) + (HEX << 24);
		}
	    });

    }
    //change the image to different style according to the HandleRGB
    private Image show(Image sourceImage,HandleRGB handleRGB) {
	BufferedImage bufferedImage = ImplementImageProcesser.imageToBufferImage(sourceImage);
	int width = bufferedImage.getWidth();
	int height = bufferedImage.getHeight();
	int[] changeRgbData = new int[width * height];
	for (int i = 0 ; i < width ; ++i)
	    for (int j = 0 ; j < height ; ++j) {
		int rgb = bufferedImage.getRGB(i,j);
		rgb = handleRGB.handle(rgb);
		changeRgbData[width * j + i] = rgb;
	    }
	return Toolkit.getDefaultToolkit().
	    createImage(new MemoryImageSource(width, height, changeRgbData, 0, width));

	

    }
    //change to image to BufferedImage
    public static BufferedImage imageToBufferImage(Image image) {
	//ensures all the pixels in the image are loaded
	image = new ImageIcon(image).getImage();
	BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
	// Copy image to buffered image
	Graphics graphics = bufferedImage.createGraphics();
	// Paint the image onto the buffered image
	graphics.drawImage(image, 0, 0, null);
	graphics.dispose();
	return bufferedImage;
    }
}