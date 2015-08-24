import imagereader.IImageIO;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.image.MemoryImageSource;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.io.*;
import javax.imageio.ImageIO;
class ImplementImageIO implements IImageIO {
    private final int HEX = 0xff;
    public Image myRead(String filePath) {
	File imageFile = new File(filePath);
	if (!imageFile.exists()) {
	    return null;
	}
	try {
	    FileInputStream fis = new FileInputStream(imageFile);
	    int header_size = 14, info_size = 40;
	    byte[] header = new byte[header_size];
	    //get the bitmap header, its size is 14 bytes
	    fis.read(header, 0, header_size);
	    byte[] info = new byte[info_size];
	    //get the bitmap information, its size is 40 bytes;
	    fis.read(info, 0, info_size);
	    int bitmapWidth = (info[4] & HEX) | ((info[5] & HEX) << 8)
		| ((info[6] & HEX) << 16)
		| ((info[7] & HEX) <<24);
	    int bitmapHeigh = (info[8] & HEX) | ((info[9] & HEX) << 8)
		| ((info[10] & HEX) << 16)
		| ((info[11] & HEX) <<24);
	    int bitmapBitCount = (info[14] & HEX) | ((info[15] & HEX) << 8);
	    int bitmapSize = (info[20] & HEX) | ((info[21] & HEX) << 8)
		| ((info[22] & HEX) << 16)
		| ((info[23] & HEX) <<24);
	    int colorNum = (info[32] & HEX) | ((info[33] & HEX) << 8)
		| ((info[34] & HEX) << 16)
		| ((info[35] & HEX) <<24);
	    int widthBytes = (bitmapWidth * bitmapBitCount + 31) / 32 * 4;
	    //compute the empty pad
	    int[] realRgbData = null;
	    switch (bitmapBitCount) {
	    case 8:
		realRgbData = read8Image(fis, bitmapWidth, bitmapHeigh,  colorNum, widthBytes);
		break;
	    case 24:
		realRgbData = read24Image(fis, bitmapWidth, bitmapHeigh,  widthBytes);
		break;
	    }
	    Image image = Toolkit.getDefaultToolkit().
		createImage(new MemoryImageSource(bitmapWidth, bitmapHeigh, realRgbData, 0, bitmapWidth));
	    fis.close();
	    return image;
								 
	} catch(IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
    private int[] read24Image(FileInputStream fis, int bitmapWidth, int bitmapHeigh, int widthBytes) {
	int allSize = widthBytes * bitmapHeigh;
	int emptyPad = widthBytes - bitmapWidth * 3;
	byte[] allRgbData = new byte[allSize];
	try {
	    fis.read(allRgbData, 0, allSize);
	} catch(IOException e) {
	    e.printStackTrace();
	}
	int[] realRgbData = new int[bitmapWidth * bitmapHeigh];
	int cursor = 0;
	for (int i = 0 ; i < bitmapHeigh ; ++i) {
	    for (int j = 0 ; j < bitmapWidth ; ++j) {
		realRgbData[bitmapWidth * (bitmapHeigh - i -1) + j] = 
		    (allRgbData[cursor] & HEX) 
		    | ((allRgbData[cursor + 1] & HEX) << 8)
		    | ((allRgbData[cursor + 2] & HEX) << 16)
		    | ((255 & HEX) << 24);
		cursor += 3;
	    }
	    //escape the empty pad
	    cursor += emptyPad;
	}
	return realRgbData;
    }
    private int[] read8Image(FileInputStream fis, int bitmapWidth, int bitmapHeigh, int colorNum, int widthBytes) {
	int allSize = widthBytes * bitmapHeigh;
	int emptyPad = widthBytes - bitmapWidth;
	byte[] colorData = new byte[colorNum * 4];
	byte[] allRgbData = new byte[allSize];
	try {
	    fis.read(colorData, 0, colorNum * 4);
	    fis.read(allRgbData, 0, allSize);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	int[] colors = new int[colorNum];
	int cursor = 0;
	for (int i = 0 ; i < colorNum ; ++i) {
	    colors[i] =  (colorData[cursor] & HEX) 
		| ((colorData[cursor + 1] & HEX) << 8)
		| ((colorData[cursor + 2] & HEX) << 16)
		| ((255 & HEX) << 24);
	    cursor += 4;
	}
	int[] realRgbData = new int[bitmapWidth * bitmapHeigh];
	cursor = 0;
	for (int i = 0 ; i < bitmapHeigh ; ++i) {
	    for (int j = 0 ; j < bitmapWidth ; ++j) {
		realRgbData[bitmapWidth * (bitmapHeigh - i -1) + j] = colors[allRgbData[cursor] & HEX];
		cursor ++;
	    }
	    //escape the empty pad
	    cursor += emptyPad;
	}
	return realRgbData;
	//	return null;
    }
    public Image myWrite(Image image, String filePath) {
	BufferedImage bufferedImage = ImplementImageProcesser.imageToBufferImage(image);
	try {
	    ImageIO.write(bufferedImage, "bmp", new File(filePath +".bmp"));
	} catch(IOException e) {
	    e.printStackTrace();
	}
	return image;
    }
}