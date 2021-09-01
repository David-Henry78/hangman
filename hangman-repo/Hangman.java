import java.io.*;
import java.util.*;

public class Hangman
{
	public static void main(String[] args)
   	{
         // Complete this method
         
          Scanner kbd = new Scanner(System.in);
          
        //  int WordGrabber = Grabber();
          int guessCount = 5;         
          String secretWord = Word(Grabber());
          String[] badGuess = new String[secretWord.length()];
          String[] showGuess = new String[secretWord.length()];        
          for(int j = 0; j < showGuess.length; j++)
          {
            showGuess[j] = " _ ";
          }
          for(int k = 0; k < badGuess.length; k++)
          {
            badGuess[k] = " _ ";
          }
         
         
          System.out.println("I am thinking of a word that is "+ secretWord.length() +" letters long.");          
         // System.out.println("-------------");


          do
          {
            System.out.println("You have "+ guessCount + " guess(es) left.");
            System.out.print("Please guess a letter: ");
            char guess = kbd.next().charAt(0);

            for(int i = 0; i < secretWord.length(); i++)
             {            
               if(errorCheck(showGuess, guess).equals(guess + "") || errorCheck(badGuess, guess).equals(guess + ""))
                {
                  System.out.print("Oops! You've already guessed that letter: ");
                  break;                
                }
               else if(stringToChar(secretWord)[i] == guess)
                {             
                   System.out.print("Good guess: ");                                
                   for(int j = 0; j < showGuess.length; j++)
                    {
                      if(stringToChar(secretWord)[j] == guess)
                       {
                         showGuess[j] = blankArray(secretWord, guess)[j];
                         continue;
                       }
                    }break;                                                                                                                
                 }  
                else if(errorCheck(stringArray((secretWord)), guess).equals("Error"))
                 {   
                   guessCount--;
                   System.out.print("Oops! That letter is not in my word: ");
                   for(int k = 0; k < badGuess.length; k++)
                   {
                     if(badGuess[k] != " _ ")
                     {
                        continue;
                     }
                     else
                     {
                        badGuess[k] = guess + "";
                        break;
                     }
                   }
                   break; 
                 }
             }  
            
            for(int j = 0; j < showGuess.length; j++)
             {                     
               System.out.print(showGuess[j]);
             }                                                                                                    
            
            System.out.println();
            System.out.println("-------------"); 
         
          }while(guessCount != 0 && winCheck(showGuess) != true); // End of do-while loop
                      
          // Display if player won or lost 
         
          if(winCheck(showGuess) == true)
           {
               System.out.print("Congratulations, you won!"); 
           }
          else if (guessCount == 0)
           {   
               System.out.print("Sorry, you ran out of guesses. The word was " + secretWord);
           }   
  
     	} // End of main method
       
   	// Create methods here
     
     public static boolean winCheck(String[] showGuessArr)
     {
       boolean winChecker = true;
       for(int k = 0; k < showGuessArr.length; k++)
       {
          if((showGuessArr[k].equals(" _ ")))
          {
              winChecker = false;                     
          }
       }  
       return winChecker;
     }
     
      public static String errorCheck(String[] showGuessArr, char guessedLetter)
      {
         String checker = "Error";      
         for(int i = 0; i < showGuessArr.length; i++)
         {  
            if(showGuessArr[i].equals(guessedLetter + ""))
            {
               checker = showGuessArr[i] + "";
               break;
            }
         }         
         return checker ;
      }
      
      public static String[] stringArray(String secretWord)
      {
         String[] secretWordArr = new String[secretWord.length()];         
         for(int i = 0; i < secretWordArr.length; i++)
         {
            secretWordArr[i] = stringToChar(secretWord)[i] + "";
         }            
         return secretWordArr;       
       }
      
      public static String[] blankArray(String secretWord, char guessedLetter)
      {
         String[] partialArr = new String[secretWord.length()];         
         for(int i = 0; i < partialArr.length; i++)
         {
            partialArr[i] = ( " _ ");
            
            if (stringToChar(secretWord)[i] == guessedLetter) 
            {
               partialArr[i] = guessedLetter + "";
            }  
         }            
         return partialArr;       
      }
      
      public static String Word(int Grabber)
      {
         String wordGrabbed = " ";         
         for(int i = Grabber; i <= Grabber; i++)                
         {
             wordGrabbed = loadWords()[i];                                                  
         }           
         return wordGrabbed;
      }
      
      public static int Grabber()
      {
         int rand = (int) (Math.random() * 55909 + 1);           
         return rand;
      }
    
          
   	/* Helper Code -----------------------------------------------
   	* You do not have to understand the provided helper methods
   	* But you will have to know how/when to call these methods
   	* Make sure to read the instructions
   	* DO NOT make any changes to the methods below UNLESS specified
   	* by the directions.
   	*/
      
   	public static String[] loadWords()
   	{
    	/*
   		* Returns a String array of valid words.
   		* Also prints out the total number of words (Strings) in the array.
   		*/
   
      	ArrayList<String> wordList = new ArrayList<String>();
      	File f = new File("words.txt");
      	String[] wordsArr = new String[wordList.size()];
      	try
      	{
         	Scanner in = new Scanner(f);
         	while(in.hasNext())
         	{
            	String word = in.next();
            	wordList.add(word);
         	}
         	in.close();
         	System.out.println("Loading words from the file......");
         	System.out.println(wordList.size()+" words loaded.");
            System.out.println("Welcome to the game, Hangman!");
            System.out.println("-------------");
         	wordsArr = (String[])wordList.toArray(wordsArr);
      	} catch (FileNotFoundException ex) {
         	System.out.println("File not found.");
      	}//String words =
      	return wordsArr;
   	}
   
   	public static char[] stringToChar(String secretWord)
   	{
   		/**
   		* takes a string which is a secretWord
   		* Returns a char array of secretWord
   		* You can use printArray method to test the output
   		*/
      	char[] secretArr = new char[secretWord.length()];    
      	for (int i = 0; i < secretArr.length; i++)
      	{
         	secretArr[i] = secretWord.charAt(i);
      	}
      	 return secretArr; 
   	}
   
   	// End of Helper Code----------------------------------
    
} //program ends