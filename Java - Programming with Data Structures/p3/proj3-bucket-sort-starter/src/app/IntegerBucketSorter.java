package app;

/**
 * This class implements the bucket sort algorithm for an array of integers. 
 * It uses a two dimensional array to carry out the algorithm.
 * The maximum integer length, MAX_DIGIT_LIMIT, is 5, so only integers with 5 digits or less 
 * can be processed. Also at this time, only integers >=0 can be processed.
 * The SENTINEL value -1 is used to indicate an empty cell in the buckets array.
 */
public class IntegerBucketSorter implements Sorter {
   // 2-D int array- the "buckets".
   int[][] buckets;
   // indicates an "empty" cell- no data.
   private static final int SENTINEL = -1;
   // maximum number of digits an integer can have to be processed.
   private static final int MAX_DIGIT_LIMIT = 5;
   
  /**
   * Use the bucket sort algorithm to return an array of the integers in the 
   * input array in sorted order. The input array and the returned array may be the same array.
   * @param dataArray an array of integers to be sorted.
   * @return an array of integers sorted in ascending order.
   * @throws an Exception if any integer in the array is > MAX_DIGIT_LIMIT.
   */
   public int[] sort(int[] dataArray) throws Exception {
      
      buckets = new int[10][dataArray.length];
      for(int i = 0; i < dataArray.length; i++){
         if(findIntLength(dataArray[i]) > MAX_DIGIT_LIMIT) throw new StringIndexOutOfBoundsException();
      }
      
      int size = findMaxIntLength(dataArray);
      int valueOfPlace = 1;
      for(int j = 0; j < size; j++){
         resetBucketValues();
         distribute(dataArray, valueOfPlace);
         collect(dataArray);
         valueOfPlace++;
      }
      
      return dataArray;
   }

   /**
    * Distribution phase:
    * Iterate through the input array and distribute the data into the buckets array 
    * by sorting on the value of each integer at the current place. The integer's value 
    * at the current place is the index of the row into which it is distributed.
    * The next available cell in the row has to be found so data that already exists in that row
    * is not overwritten.
    * @param dataArray the integers to be sorted.
    * @param curPlace the current "place" used to determine the bucket where an integer is written to. 
    */ 
   public void distribute(int[] dataArray, int curPlace){
      
      int numberOfPlace = 0;
      int counter = 0;

      for(int i = 0; i < dataArray.length; i++){
         int temp;

         temp = getPlaceValue(curPlace, dataArray[i]);
            while(buckets[temp][counter] != SENTINEL){
               counter++;
            }

         buckets[temp][counter] = dataArray[i];

      }
   }

   /**
   *  Collection phase:
   *  Iterate through the buckets array starting at row 0. 
   *  Collect all integers stored in that row into the data array,
   *  in the order they appear in that row.
   *  Do that for each row until done.
   * @param dataArray the integers to be sorted.
   */
   public void collect(int[] dataArray) {
   
      int pos = 0;
      for(int i = 0; i < 10; i++){
         for(int j = 0; j < buckets[i].length; j++){
            if(buckets[i][j] != SENTINEL){
               dataArray[pos] = buckets[i][j];
               pos++;
            }
         }
      }
   }
   
   /**
   * Finds the integer with the maximum number of digits, or "places"
   * in the array. This is used to determine how many iterations of the 
   * bucket sort algorithm are required.
   * Assume the length of the array >=0.
   * This method calls findIntLength.
   * @param array the input array of integers to be sorted.
   * @throws Exception if findIntLength throws an Exception.
   * @return the largest number of digits found in the integers in the array, return 0 if array is empty. 
   */   
   public int findMaxIntLength(int[] array) throws Exception{
      int max = -1;
   

   
      
      if(array.length == 0) {
         max = 0;
         }
      else{
         for(int i = 0; i < array.length; i++){
            if(max < array[i]){
               max = array[i];
            }
            
         }

         max = findIntLength(max);
      }

      return max;
   }
   
   /**
   * Returns the number of digits or "places" in the argument, num.
   * If num was 0, the return would be 1.
   * If num was 5, the return would be 1.
   * If num was 15, the return would be 2.
   * If num was 500, the return would be 3.
   * etc. This method should handle an integer with up to MAX_DIGIT_LIMIT places.
   * Assume num >=0.
   * @param num the integer whose number of digits we want to determine.
   * @return the number of digits of num.
   * @throws Exception if the number of digits of num is > MAX_DIGIT_LIMIT.
   */
   public int findIntLength(int num) throws Exception {
      int len = -1;
   
      
      if(num < 10){
         len = 1;
         }
      else{
         len += 1;
         
         while(num != 0){
            len++;
            num /= 10;
         }
      }

      if(len > MAX_DIGIT_LIMIT) throw new StringIndexOutOfBoundsException();

      return len;
   }
   
   /**
   * Returns the digit at the specific "place" in the argument, num.
   * If the argument does not have a digit at the specified place, 0 is returned.
   * If place was 1 and num was 5, the return would be 5.
   * If place was 2 and num was 5, the return would be 0.
   * If place was 1 and num was 39, the return would be 9.
   * If place was 2 and num was 167, the return would be 6.
   * Assume num >=0.
   * @param place the specific digit required.
   * @param num the integer we want to extract the digit from.
   * @return the digit at the specified place.
   */
   public int getPlaceValue(int place, int num){
      int digit = -1;
   
      int numPlace = 0;
      int value = 0;
      int numCopy = num;

         while(numPlace != place){
            value = numCopy%10;
            numCopy = numCopy/10;
            numPlace++;
         }
         

      digit = value;

      return digit;
   }
   
   /**
    * This method overwrites all cells in the "buckets" array
    * with the SENTINEL value. This resets the array to be ready 
    * for another round of the bucket sort algorithm. It should 
    * also be called as soon as the buckets array is created so that 
    * it is properly initialized.
    */
   public void resetBucketValues(){
   
   for(int i = 0; i < 10; i++){
      for(int j = 0; j < buckets[i].length; j++){
      buckets[i][j] = SENTINEL;
      } 
   }


   }
}