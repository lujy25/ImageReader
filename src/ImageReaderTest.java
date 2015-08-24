package test;
import static org.junit.Assert.*;
import org.junit.*;
import java.awt.Image;
public class ImageReaderTest {
    private static ImplementImageIO  imageIO;
    private static ImplementProcesser processor;
    @BeforeClass
	public void beforeTest() {
	imageIO = new ImplementImageIO();
	processor = new ImplementImageIO();
    }
public boolean isEqual
    @Test
	public void testMyRead1() {
	String filePath1 = "bmptest/1.bmp";
	Image image1 = imageIO.myRead(filePath1);
	File file1 = new File(filePath1);
	BufferdImage bi = ImageIO.read(file1);
	Image image1 = new ImageIcon(image1).getImage();


	String filePath2 = "bmptest/2.bmp";
   
	Image image2 = imageIO.myRead(filePath2);
	Image 
	    @Test
	    public void testMove1() {
	    //origin locatioin
	    jumper.moveTo(new Location(0,0));
	    //test move east
	    jumper.setDirection(Location.EAST);
	    jumper.move();
	    assertEquals(new Location(0,2),jumper.getLocation());
	    //test move south-east
	    jumper.setDirection(Location.SOUTHEAST);
	    jumper.move();
	    assertEquals(new Location(2,4),jumper.getLocation());
	    //test move north-east
	    jumper.setDirection(Location.NORTHEAST);
	    jumper.move();
	    assertEquals(new Location(0,6),jumper.getLocation());
	    //test move south
	    jumper.setDirection(Location.SOUTH);
	    jumper.move();
	    assertEquals(new Location(2,6),jumper.getLocation());
	    //test move south-west
	    jumper.setDirection(Location.SOUTHWEST);
	    jumper.move();
	    assertEquals(new Location(4,4),jumper.getLocation());
	    //test move west
	    jumper.setDirection(Location.WEST);
	    jumper.move();
	    assertEquals(new Location(4,2),jumper.getLocation());
	    //test move north
	    jumper.setDirection(Location.NORTH);
	    jumper.move();
	    assertEquals(new Location(2,2),jumper.getLocation());
	    //test move north-west
	    jumper.setDirection(Location.NORTHWEST);
	    jumper.move();
	    assertEquals(new Location(0,0),jumper.getLocation());
	    //test move outsize
	    jumper.setDirection(Location.NORTH);
	    jumper.move();
	    assertEquals(null,jumper.getLocation());
	}
	@Test
	    public void testMove2() {
	    //test move north
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.NORTH);
	    jumper.move();
	    assertEquals(new Location(0,2),jumper.getLocation());
	    //test move east
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.EAST);
	    jumper.move();
	    assertEquals(new Location(2,4),jumper.getLocation());
	    //test move south
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.SOUTH);
	    jumper.move();
	    assertEquals(new Location(4,2),jumper.getLocation());
	    //test move west
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.WEST);
	    jumper.move();
	    assertEquals(new Location(2,0),jumper.getLocation());
	    //test move north-east
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.NORTHEAST);
	    jumper.move();
	    assertEquals(new Location(0,4),jumper.getLocation());
	    //test move south-east
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.SOUTHEAST);
	    jumper.move();
	    assertEquals(new Location(4,4),jumper.getLocation());
	    //test move south-west
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.SOUTHWEST);
	    jumper.move();
	    assertEquals(new Location(4,0),jumper.getLocation());
	    //test move north-west
	    jumper.moveTo(new Location(2,2));
	    jumper.setDirection(Location.NORTHWEST);
	    jumper.move();
	    assertEquals(new Location(0,0),jumper.getLocation());
	    //test move outsize
	    jumper.moveTo(new Location(8,8));
	    jumper.setDirection(Location.SOUTHEAST);
	    jumper.move();
	    assertEquals(null,jumper.getLocation());
	}
	@Test
	    public void testCanMove1() {
	    //origin location
	    jumper.moveTo(new Location(0,0));
	    //test encounter grid border with one step
	    jumper.setDirection(Location.NORTH);
	    assertEquals(false,jumper.canMove());
	    //test encounter grid border with two step
	    jumper.moveTo(new Location(1,0));
	    jumper.setDirection(Location.NORTH);
	    assertEquals(false,jumper.canMove());
	    //test encounter other actor
	    jumper.moveTo(new Location(0,0));
	    actor.moveTo(new Location(0,2));
	    jumper.setDirection(Location.EAST);
	    assertEquals(false,jumper.canMove());
	    //test can move
	    jumper.moveTo(new Location(0,0));
	    actor.moveTo(new Location(0,1));
	    jumper.setDirection(Location.EAST);
	    assertEquals(true,jumper.canMove());
	}
	@Test
	    public void testCanMove2() {
	    //origin location
	    jumper.moveTo(new Location(9,9));
	    //test encounter grid border with one step
	    jumper.setDirection(Location.SOUTHEAST);
	    assertEquals(false,jumper.canMove());
	    //test encounter grid border with two step
	    jumper.moveTo(new Location(8,8));
	    jumper.setDirection(Location.SOUTHEAST);
	    assertEquals(false,jumper.canMove());
	    //test encounter other actor
	    jumper.moveTo(new Location(7,7));
	    actor.moveTo(new Location(9,9));
	    jumper.setDirection(Location.SOUTHEAST);
	    assertEquals(false,jumper.canMove());
	    //test can move
	    jumper.moveTo(new Location(0,0));
	    actor.moveTo(new Location(1,1));
	    jumper.setDirection(Location.SOUTHEAST);
	    assertEquals(true,jumper.canMove());
	}
    }
