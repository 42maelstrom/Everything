/**
 *  An iterative solution to the Towers of Hanoi problem.
 *
 *  @author	Kevin Andrew Seiter
 */

import java.io.*;
import java.lang.*;

public class HanoiIterative{
    
    // -------------------------------------------------------------------------
    // All integers needed for program calculations.

    public static int n;         
    
    public static int numMoves;       
    
    public static int second = 0;     
   
    public static int third;          
    
    public static int pos2;           
    
    public static int pos3;
    
    public static int j;

    public static int i;
    // -------------------------------------------------------------------------
    /**
     * The main method of the program. It functions as follows: An int value
     * is passed via the commandline arguments which is then used to determine
     * the minimum number of steps the process will take. From there, an 
     * array is filled with all disks starting locations. This array will be
     * used throughout the program to keep track of disks last position. This is
     * necessary iteratavely because recursively, stacked methods keep track of 
     * this inherently. From there, the program enters a loop that is bounded by
     * the minimum number of processes. Inside the loops, the program begins 
     * by determining if the number of disks on tower A are odd or even. For an
     * even number of starting disks, the program will function by moving all 
     * disks to tower c. For an odd number of disks, they will all be moved to 
     * tower b. This is for calculation sake, and does not effect the functionality
     * of the program. During the process, all conclusions are printed via
     * system.out.println and additional println()'s are for spacing purposes only.
     * When the program has reached the minimum number of processes, it assumes
     * all disks are in their proper locations and terminates. A try/catch/finally
     * block protects the programs integrity  from ridiculously ignorant users.
     * NOTE: Comments reading, "Iterative vs Recursive" highlight the downside
     * of solving this problem iteratively.
     */
     public static void main(String args[]) {
	 //--------------------------------------------------------------------
	 try{
	     if( args.length == 1 ){
		 System.out.println();
		 n = Integer.parseInt(args[0]);         //Sets n to commandline int
		 int[] locations = new int[ n + 2 ];    //Sets location size 
		 
		 for ( j=0; j < n; j++ ){               //For loop - Initially all
		     locations[j] = 0;                  //discs are on tower 1
		 }
		 
		 locations[ n + 1 ] = 2;                //Final disk destination
		 numMoves = 1;                          
		 for ( i = 1; i <= n; i++){             //Calculates minimum steps
		     numMoves *= 2;                     //based on disc size then
		 }                                      //subtracts one. ( standard
		 numMoves -= 1;                         //algorithm 2^n - 1 )
		 
		 //Begins iterative solution. Bound by min number of steps.
		 for ( i = 1; i <= numMoves; i++ ){     
		     if ( i%2 == 1 ){                   //Determines odd or even.
			 second = locations[1];
			 locations[1] = ( locations[1] + 1 ) % 3;
			 System.out.print("Move disc 1 to ");
			 System.out.println((char)('A'+locations[1]));
		     }
		     
		     else {                             //If number is even.
			 third = 3 - second - locations[1];
			 pos2 = n + 1;
			 for ( j = n + 1; j >=2; j-- )   //Iterative vs Recursive.
			     if ( locations[j] == second )
				 pos2 = j;
			 pos3 = n + 1;
			 for ( j = n + 1; j >= 2; j-- )  //Iterative vs Recursive.
			     if ( locations[j] == third )
				 pos3 = j;
			 System.out.print("Move disc "); //Assumes something is moving.

			 //Iterative set. Much slower here than Recursive.
			 if ( pos2 < pos3 ){
			     System.out.print( pos2 );
			     System.out.print(" to ");
			     System.out.println((char)('A' + third));
			     locations[pos2] = third;
			 }
			 //Iterative set. Much slower here than Recursive.
			 else {
			     System.out.print( pos3 );
			     System.out.print(" to ");
			     System.out.println((char)('A' + second));
			     locations[ pos3 ] = second;
			 }
		     }
		 }
	     }
	 }               //Protects Program Integrity.
	 catch( Exception e ){
	     System.err.println("YOU SUCK. ENTER A VALID INT VALUE FOR #");
	     System.err.println("FORMAT : java HanoiIterative #");
	 }               //Protects Program Integrity.
	 finally{
	     System.out.println();
	     System.out.println("CREATED BY: KEVIN SEITER");
	     System.out.println();
	 }
     }
}//HanoiIterative
//--------------------------------------------------------------------------------
