import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.*;


public class SnapCracklePopTest {

   private ByteArrayOutputStream outStream, baos;
   private PrintWriter out;
   private PrintStream old;
   private String endStr;

   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
      outStream = new ByteArrayOutputStream();
      baos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(baos);
      // IMPORTANT: Save the old System.out!
      old = System.out;
      // Tell Java to use your special stream
      System.setOut(ps);
   }


   /** Testing basic numeric input. **/
   @Test public void basicNumTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("10\n11\n12\n5\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: 2"+"\n"+"round 3: 3"+"\n"+"round 4: 4"+"\n"+"round 5: 5"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with no Snaps, Crackles, or Pops", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing snap. **/
   @Test public void snapTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("3\n11\n12\n5\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: 2"+"\n"+"round 3: snap"+"\n"+"round 4: 4"+"\n"+"round 5: 5"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Snap but no Crackles or Pops", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing crackle. **/
   @Test public void crackleTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("3\n4\n12\n5\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: 2"+"\n"+"round 3: snap"+"\n"+"round 4: crackle"+"\n"+"round 5: 5"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Crackle but no Snaps or Pops", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing pop. **/
   @Test public void popTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("3\n4\n5\n5\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: 2"+"\n"+"round 3: snap"+"\n"+"round 4: crackle"+"\n"+"round 5: pop"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Pop but no Snaps or Crackles", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing snapcrackle. **/
   @Test public void snapCrackleTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("2\n3\n10\n7\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: snap"+"\n"+"round 3: crackle"+"\n"+"round 4: snap"+"\n"+"round 5: 5"+"\n"+"round 6: snapcrackle"+"\n"+"round 7: 7"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Snap and a Crackle but no Pops", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing cracklepop. **/
   @Test public void cracklePopTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("10\n3\n2\n7\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: pop"+"\n"+"round 3: crackle"+"\n"+"round 4: pop"+"\n"+"round 5: 5"+"\n"+"round 6: cracklepop"+"\n"+"round 7: 7"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Crackle and a Pop but no Snaps", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing snappop. **/
   @Test public void snapPopTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("2\n10\n3\n7\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: snap"+"\n"+"round 3: pop"+"\n"+"round 4: snap"+"\n"+"round 5: 5"+"\n"+"round 6: snappop"+"\n"+"round 7: 7"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Snap and a Pop but no Crackles", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing snapcracklepop. **/
   @Test public void snapCracklePopTest1() throws Exception{
      
      // Write to buffer before converting it to input
      outStream.write("2\n3\n6\n7\n".getBytes());
      // Use this to capture input
      ByteArrayInputStream in = new ByteArrayInputStream(outStream.toByteArray());
      System.setIn(in);
      SnapCracklePopMain.main(new String[0]);
      String printed = baos.toString();
      String expected = "round 1: 1"+"\n"+"round 2: snap"+"\n"+"round 3: crackle"+"\n"+"round 4: snap"+"\n"+"round 5: 5"+"\n"+"round 6: snapcracklepop"+"\n"+"round 7: 7"+"\n"+System.lineSeparator();
      Assert.assertEquals("Output is correct with a Snap, Crackle, and Pop", expected, printed.substring(printed.length()-expected.length()).toLowerCase());
   }
   
   /** Testing getSnap(). **/
   @Test public void getSnapTest1() throws Exception{
      SnapCracklePop scp = new SnapCracklePop(3, 4, 5);
      Assert.assertEquals("getSnap() returns the correct value", 3, scp.getSnap());
   }
   
   /** Testing getCrackle(). **/
   @Test public void getCrackleTest1() throws Exception{
      SnapCracklePop scp = new SnapCracklePop(3, 4, 5);
      Assert.assertEquals("getCrackle() returns the correct value", 4, scp.getCrackle());
   }
   
   /** Testing getPop(). **/
   @Test public void getPopTest1() throws Exception{
      SnapCracklePop scp = new SnapCracklePop(3, 4, 5);
      Assert.assertEquals("getPop() returns the correct value", 5, scp.getPop());
   }
}