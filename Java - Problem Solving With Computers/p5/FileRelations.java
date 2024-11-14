/**
* FileRelations is intended to be a simple search engine to find the most related text files possible
* @author [Yerramsetty Likhit Raj]
* @version 1.0
*/

import java.io.*;

public class FileRelations{

   /**
   * a String holding the file name for the highest scoring file
   */
   private String topFile;
   /**
   * an int holding the score for the highest scoring file
   */
   private double topScore;
   /**
   * wordsToFind: a String array holding all the words to search for
   */
   private String[] wordsToFind;
   
   /**
   * This is the primary and only constructor for the FileRelations class.
   * @param toFind holds all the key words to search for
   */
   public FileRelations(String[] toFind){
      topFile = "";
      topScore = -1.0;
      wordsToFind = toFind;
   }
   
   /**
   * Analyses the file based on the keywords and updates the top scoring file and score once done.
   * @param fileContent an array of Strings each representing a line in the original file
   * @param fileName the name of the file being analysed
   */ 
   public void analyseFile(String[] fileContent, String fileName){
      
      int score = 0;
      double totalScore = 0.0;
      for(String line : fileContent){
         score += scoreLine(line);
      }
      totalScore = (double) score / fileContent.length;
      if(totalScore > topScore){
         topScore = totalScore;
         topFile = fileName;
      }
      
   }
   
   /**
   * Analyses the line passed in and scores it based on frequency of the key words. A helper method for analyseFile.
   * @param line a String representing a single line in a file
   * @returns the score of the line passed in
   */ 
   private int scoreLine(String line){
      
      String[] lineWords = line.split(" ");
      
      
      int lineScore = 0;
      for( String word: lineWords){
         lineScore += countWord(word);
      }
      
      return lineScore;
   }
   
   /**
   * Analyses the word passed in and scores it based on if it is one of the key words. A helper method for scoreLine.
   * @param word a String representing a single word in a file
   * @returns the score of the word passed in
   */ 
   private int countWord(String word){
      
      int wordCount = 0;
      for(String currentWord : wordsToFind){
         if(word.equalsIgnoreCase(currentWord)){
            wordCount++;
         }
      }
      return wordCount;
   }
   
   /**
   * A getter method for the key words used in this object's search
   * @returns a String array containing the key words to search for.
   */ 
   public String[] getWords(){
      
      return wordsToFind;
   }
   
   /**
   * A getter method for the top scoring file in the object currently
   * @returns a String representing the file name of the top scoring file.
   */ 
   public String getTopFile(){
      
      return topFile;
   }
   
   /**
   * A getter method for the score of the top scoring file in the object currently
   * @returns an int representing the score of the top scoring file.
   */ 
   public double getTopScore(){
      
      return topScore;
   }
}