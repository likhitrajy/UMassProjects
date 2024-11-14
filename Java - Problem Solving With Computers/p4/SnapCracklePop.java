//TODOa complete this javadoc comment
/**
* [SnapCracklePop class is a class which uses snap, crackle, and pop to play a game.]
* @author [Yerramsetty Likhit Raj]
* @version 1.0
*/

//TODO1: declare the SnapCracklePop class
class SnapCracklePop{
 
   //TODOb Complete Comments
   /**
   * [snap- private int to store the value at which user chose Snap
      crackle- private int to store the value at which user chose Crackle
      pop- private int to store the value at which user chose Pop]
   */
   
   //TODO2 Declare private instance variables 
   //to store Snap, Crackle, and Pop values
   private int snap;
   private int crackle;
   private int pop;
   
   //TODOc complete comments
   /**
   * [The constructor takes three parameters and assigns the value to snap, crackle and pop]
   * @param [snap1] [assigns value to snap]
   * @param [crackle1] [assigns value to crackle]
   * @param [pop1] [assigns value to pop]
   */
    
   /*  The constructor takes in three ints,
   *  which must be assigned to their instance variables and initialized.
   */
   
   //TODO3 Write the constructor
   public SnapCracklePop(int snap1, int crackle1, int pop1){
      snap = snap1;
      crackle = crackle1;
      pop = pop1;
   
   }
   
   //TODOe complete comments
   /**
   * [playRound takes a parameter for representing the number of round being played and returns the result of that round.]
   * @param [round] [current round number.]
   * @return [string output of the current round.]
   */ 
     
   /* playRound() is a helper method for playGame(). 
   *  It takes an int parameter representing the 
   *  current round of play, and returns the 
   *  String result for that specific round only.
   */
   
   //TODO4 implement the playRound method 
   public String playRound(int round){
      String oPut = "";
      if(round % snap == 0){
         oPut += "snap";
      }
      if(round % crackle == 0){
         oPut += "crackle";
      }
      if(round % pop == 0){
         oPut += "pop";
      }
      if(oPut ==""){
         oPut += round;
      }
      return oPut;
   }
   
   //TODOd complete comments
   /**
   * [ playGame() takes a single parameter representing the rounds and return a String representing the result of the entire game.]
   * @param [rounds] [What the parameter represents total amount of rounds]
   * @return [String value of the complete result of the game.]
   */ 
   
   /* playGame() takes a single parameter representing the rounds and returns
   *  a String representing the result of the entire game. The helper method
   *  playRound() may be useful here, so you may want to complete it first.
   */
   
   //TODO5 implement the playGame method
   public String playGame(int rounds){
      String result = "";
      for(int i = 1 ; i <= rounds ; i++){
         String currentRes = playRound(i);
      
         if(!currentRes.isEmpty()){
            result += "Round " + i + ": " + currentRes + "\n"; 
         }
         else{
            result += "Round " + i + ": " + i + "\n";
         }
      }
      return result;
   }
      
      //Loop through the rounds of the game
         //call playRound to handle the specific round
      //return the total aggregated game results
   
   //TODOf complete comments
   /**
   * [This method returns the value of snap variable]
   * @return [snap]
   */ 
   
   //TODO6 implement the getSnap method
   int getSnap(){
      return snap;
   }
   
   //TODOg complete comments
   /**
   * [This method returns the value of crackle variable]
   * @return [crackle]
   */ 
   
   //TODO7 implement the getCrackle method  
   int getCrackle(){
      return crackle;
   }
   
   //TODOh complete comments
   /**
   * [This method returns the value of pop variable]
   * @return [pop]
   */ 
     
   //TODO8 implement the getPop method
   int getPop(){
      return pop;
   }
   
}//end class