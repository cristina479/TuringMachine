/**
 * 
 */
package dtm;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Main class for the Turing Machine
 * @author Cristina Padro-Juarbe
 *
 */
public class MainTM {
	
	final static int MAX_LENGTH_BINARIES = 30;
	final static int DEFAULT_MODE = 0;
	final static int ADDITION_MODE = 1;
	final static int SUBTRACTION_MODE = 2;
	final static int MULTIPLICATION_MODE = 3;
	final static String FILEPATH = "./dtm/";
	final static String FILENAME_MODULE3 = "default.txt";
	final static String FILENAME_ADDITION = "addition.txt";
	final static String FILENAME_SUBTRACTION = "subtraction.txt";
	final static String FILENAME_MULTIPLICATION = "multiplication.txt";
	static PrintWriter printWriter = null;	
	
	public static void main(String[] args) {	
		// initialize user input variables
		Scanner in = new Scanner(System.in);
		String mode = "";
		int modeInt = -1;
		
		do {	
			mode = "";
			modeInt = -1;
			
			// instructions to the user
			System.out.println("Operations in the Turing Machine: (0 - DTM of Module 3)	(1 - Addition) (2 - Subtraction) (3 - Multiplication)");
			System.out.println("Enter an integer number between 0 and 3 to select a mode of operation. To quit enter 'q'.");
			//in = new Scanner(System.in);
			mode = in.next();

			// check if the mode is integer
			if(isInteger(mode)) {
				modeInt = Integer.parseInt(mode);
				in.nextLine();
			}
			
			// when mode is an integer between 0 and 3
			if(modeInt >= 0 && modeInt <= 3) {
				System.out.println("\nFor mode 1, 2, and 3 enter binary numbers separated by ONE space character (i.e. 1100101 101).");
				System.out.println("Enter input: ");
				String input = in.nextLine();               //10101 - mode 0	//110110 101011 - mode 1   //100 1110 or 110000 111010100 - mode 2
				
				// call a helper method to run the Turing Machine
				selectMachine(input, modeInt);
			} else {
				// when the user enters an invalid mode or operation 
				if(!mode.equals("q")) {
					System.out.println("Invalid mode of operation.");				
				} else {
					// when the user quits the program
					System.out.println("End of program.");
				}
			}			
		} while(!mode.equals("q"));
		
		in.close();	
	}
	
	/**
	 * Helper method to initialize the Turing Machine depending on the mode of operation.
	 * 
	 * @param input the bytes provided by the user {0, 1}*
	 * @param mode is the mode of operation of the Turing Machine. Mode 0 - DTM of Module 3, Mode 1 - Addition, Mode 2 - Subtraction, Mode 3 - Multiplication 
	 */
	private static void selectMachine(String input, int mode) {
		TuringMachine tm = null;
		boolean trace_run_console = input.length() <= MAX_LENGTH_BINARIES ? true : false;
		
		switch(mode) {
		case 0:
			// DTM of Module 3
			System.out.println("\nYou selected the DTM of Module 3.\n");
			tm = Operations.addDefaultCollection();	
			startTM(trace_run_console, tm, input, FILENAME_MODULE3);	
			break;
		case 1:
			// Addition of two binary numbers
			System.out.println("\nYou selected the ADDITION operation.\n");
			tm = Operations.addAdditionCollection();
			startTM(trace_run_console, tm, input, FILENAME_ADDITION);	
			break;
		case 2:
			// Subtraction of two binary numbers
			System.out.println("\nYou selected the SUBTRACTION operation.\n");
			tm = Operations.addSubtractionCollection();
			startTM(trace_run_console, tm, input, FILENAME_SUBTRACTION);	
			break;  
		case 3:
			// Multiplication of two binary numbers
			System.out.println("\nYou selected the MULTIPLICATION operation.\n");
			tm = Operations.addMultiplicationCollection();
			startTM(trace_run_console, tm, input, FILENAME_MULTIPLICATION);	
			break;
		default:
			System.out.println("\nInvalid mode.");
		}
	}
	
	/**
	 * Helper method that runs the Turing Machine.
	 * 
	 * @param trace_run_console a constant to determine if the trace runs should be printed to a file or to the terminal
	 * @param myMachine the Turing Machine selected given a mode of operation
	 * @param input the bytes provided by the user {0, 1}*
	 * @param fileName the file name where the trace runs and outputs will be written when input > 30
	 */
	private static void startTM(boolean trace_run_console, TuringMachine myMachine, String input, String fileName) {	
		boolean accepted = false;
		
		// for when input > 30
		if (!trace_run_console) {
			try {					
				printWriter = new PrintWriter(new FileWriter(FILEPATH + fileName));	
				
				// where all the work is being done
				accepted = myMachine.run(input, printWriter);	
				
				// prints the halting state
				if(accepted) {
					printWriter.println("The input was accepted.");
					printWriter.println();					
				} else {
					printWriter.println("The input was rejected.\n");
					printWriter.println();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (printWriter != null)
					printWriter.close();
			}					
		} else {
			// for when input <= 30
			// where all the work is being done
			accepted = myMachine.run(input, null);
			
			// prints the halting state 
			if(accepted) {
				System.out.println("The input was accepted.\n");
			} else {
				System.out.println("The input was rejected.\n");
			}
		}
	}

	/**
	 * This method is used to determine if we should prompt the user for a mode of operation or to quit the program
	 * 
	 * @param mode is the mode of operation of the Turing Machine. Mode 0 - DTM of Module 3, Mode 1 - Addition, Mode 2 - Subtraction, Mode 3 - Multiplication
	 * @return true if mode is an integer, false otherwise
	 */
	public static boolean isInteger(String mode) {
	    try {
	        Integer.parseInt(mode);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
	
}
