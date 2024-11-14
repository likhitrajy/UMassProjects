import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.*;
import java.util.*;


public class FileRelationsTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** countWord public tests **/
   @Test public void countWordTest1() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method countWordMethod = FileRelations.class.getDeclaredMethod("countWord", String.class);
         countWordMethod.setAccessible(true);
         Object[] params = {"hello"};
         returnValue = (Integer)countWordMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("countWord returns correct value in positive case", 1, returnValue);
   }
   
   @Test public void countWordTest2() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method countWordMethod = FileRelations.class.getDeclaredMethod("countWord", String.class);
         countWordMethod.setAccessible(true);
         Object[] params = {"World"};
         returnValue = (Integer)countWordMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("countWord returns correct value in another positive case", 1, returnValue);
   }
   
   @Test public void countWordTest3() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method countWordMethod = FileRelations.class.getDeclaredMethod("countWord", String.class);
         countWordMethod.setAccessible(true);
         Object[] params = {"computer"};
         returnValue = (Integer)countWordMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("countWord returns correct value in negative case", 0, returnValue);
   }
   
   @Test public void countWordTest4() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method countWordMethod = FileRelations.class.getDeclaredMethod("countWord", String.class);
         countWordMethod.setAccessible(true);
         Object[] params = {"hellos"};
         returnValue = (Integer)countWordMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("countWord returns correct value in another negative case", 0, returnValue);
   }


   /** scoreLine public tests **/
   @Test public void scoreLineTest1() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method scoreLineMethod = FileRelations.class.getDeclaredMethod("scoreLine", String.class);
         scoreLineMethod.setAccessible(true);
         Object[] params = {"hello world"};
         returnValue = (Integer)scoreLineMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("scoreLine returns correct value in simple case", 2, returnValue);
   }

   @Test public void scoreLineTest2() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method scoreLineMethod = FileRelations.class.getDeclaredMethod("scoreLine", String.class);
         scoreLineMethod.setAccessible(true);
         Object[] params = {"goodbye world"};
         returnValue = (Integer)scoreLineMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("scoreLine returns correct value in another simple case", 1, returnValue);
   }

   @Test public void scoreLineTest3() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method scoreLineMethod = FileRelations.class.getDeclaredMethod("scoreLine", String.class);
         scoreLineMethod.setAccessible(true);
         Object[] params = {"hello world how are you doing on this fine day"};
         returnValue = (Integer)scoreLineMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("scoreLine returns correct value in a less-simple case", 2, returnValue);
   }

   @Test public void scoreLineTest4() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      try{
         Method scoreLineMethod = FileRelations.class.getDeclaredMethod("scoreLine", String.class);
         scoreLineMethod.setAccessible(true);
         Object[] params = {"when I wake and say hello to the world I wonder if the world says hello back"};
         returnValue = (Integer)scoreLineMethod.invoke(fr, params);
      } catch (Exception e){}
      Assert.assertEquals("scoreLine returns correct value in a another less-simple case", 4, returnValue);
   }
   
   
   /** analyseFile public tests **/
   @Test public void analyseFileTest1() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      fr.analyseFile(params, "test file");
      String topFile = "";
      try{
         Field topFileField = FileRelations.class.getDeclaredField("topFile");
         topFileField.setAccessible(true);
         topFile = (String)topFileField.get(fr);
      } catch (Exception e){}
      Assert.assertEquals("analyseFile correctly updates topFile", "test file", topFile);
   }
   
   @Test public void analyseFileTest2() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      fr.analyseFile(params, "test file");
      fr.analyseFile(params, "test file 2");
      String topFile = "";
      try{
         Field topFileField = FileRelations.class.getDeclaredField("topFile");
         topFileField.setAccessible(true);
         topFile = (String)topFileField.get(fr);
      } catch (Exception e){}
      Assert.assertEquals("analyseFile correctly doesn't update topFile on the same score", "test file", topFile);
   }
   
   @Test public void analyseFileTest3() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      // Note "worldworld" is not valid.
      String[] params2 = {"hello hello hello hello", "hello world world worldworld"};
      fr.analyseFile(params, "test file");
      fr.analyseFile(params2, "test file 2");
      String topFile = "";
      try{
         Field topFileField = FileRelations.class.getDeclaredField("topFile");
         topFileField.setAccessible(true);
         topFile = (String)topFileField.get(fr);
      } catch (Exception e){}
      Assert.assertEquals("analyseFile correctly updates topFile on new high score", "test file 2", topFile);
   }
   
   @Test public void analyseFileTest4() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      fr.analyseFile(params, "test file");
      double topScore = -1;
      try{
         Field topScoreField = FileRelations.class.getDeclaredField("topScore");
         topScoreField.setAccessible(true);
         topScore = (Double)topScoreField.get(fr);
      } catch (Exception e){}
      Assert.assertEquals("analyseFile correctly updates topScore", 2, topScore, 0.0001);
   }
   
   @Test public void analyseFileTest5() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      // Note "worldworld" is not valid.
      String[] params2 = {"hello hello hello hello", "hello world world worldworld"};
      fr.analyseFile(params, "test file");
      fr.analyseFile(params2, "test file 2");
      double topScore = -1;
      try{
         Field topScoreField = FileRelations.class.getDeclaredField("topScore");
         topScoreField.setAccessible(true);
         topScore = (Double)topScoreField.get(fr);
      } catch (Exception e){}
      Assert.assertEquals("analyseFile correctly updates topScore on new high score", 3.5, topScore, 0.0001);
   }
   
   @Test public void analyseFileTest6() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say goodbye and I say goodbye", "goodbye plannet", "the PlAnNeT is a strange plannet Thats why I love it"};
      fr.analyseFile(params, "test file");
      double topScore = -1;
      try{
         Field topScoreField = FileRelations.class.getDeclaredField("topScore");
         topScoreField.setAccessible(true);
         topScore = (Double)topScoreField.get(fr);
      } catch (Exception e){}
      Assert.assertEquals("analyseFile correctly updates topScore on first file", 0, topScore, 0.0001);
   }
   
   /** the getters tests **/
   @Test public void gettersTest1() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      fr.analyseFile(params, "test file");
      double topScore = fr.getTopScore();
      String topFile = fr.getTopFile();
      String[] words = fr.getWords();
      Assert.assertEquals("getTopScore returns the top score", 2, topScore, 0.0001);
      Assert.assertEquals("getTopFile returns the top scoring file", "test file", topFile);
      Assert.assertEquals("getWords returns the words we're looking to find", new String[]{"hello", "world"}, words);
   }
   
   @Test public void gettersTest2() {
      FileRelations fr = new FileRelations(new String[]{"hello", "world", "another"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      fr.analyseFile(params, "test file");
      double topScore = fr.getTopScore();
      String topFile = fr.getTopFile();
      String[] words = fr.getWords();
      Assert.assertEquals("getTopScore returns the top score", 2, topScore, 0.0001);
      Assert.assertEquals("getTopFile returns the top scoring file", "test file", topFile);
      Assert.assertEquals("getWords returns the words we're looking to find", new String[]{"hello", "world", "another"}, words);
   }
   
   /** Putting it all together tests **/
   @Test public void allTogetherTest1() {
      FileRelations fr = new FileRelations(new String[]{"oh", "wow"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      // Note "worldworld" is not valid.
      String[] params2 = {"oh hello", "such code", "wow"};
      fr.analyseFile(params, "test file");
      double topScore = fr.getTopScore();
      Assert.assertEquals("analyseFile correctly updates topScore on new high score", 0, topScore, 0.0001);
      
      fr.analyseFile(params2, "test file 2");
      topScore = fr.getTopScore();
      Assert.assertEquals("analyseFile correctly updates topScore on new high score", 0.666666666666, topScore, 0.0001);
   }
   
   @Test public void allTogetherTest2() {
      FileRelations fr = new FileRelations(new String[]{"oh", "wow"});
      int returnValue = -1;
      String[] params = {"you say hello and I say hello", "hello world", "the WoRlD is a strange world Thats why I love it"};
      String[] params2 = {"oh hello", "such code", "wow"};
      fr.analyseFile(params, "test file");
      String topFile = fr.getTopFile();
      Assert.assertEquals("analyseFile correctly updates topFile on new high score", "test file", topFile);
      
      fr.analyseFile(params2, "test file 2");
      topFile = fr.getTopFile();
      Assert.assertEquals("analyseFile correctly updates topFile on new high score", "test file 2", topFile);
   }
}