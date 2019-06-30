/**
 * 
 */
package dtm;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author crist
 *
 */
public class MainTM {
	
	final static int MAX_LENGTH_ARRAY = 30;
	final static int DEFAULT_MODE = 0;
	final static int ADDITION_MODE = 1;
	final static int SUBTRACTION_MODE = 2;
	final static int MULTIPLICATION_MODE = 3;
	final static String FILEPATH = "src/dtm/";
	final static String FILENAME_MODULE3 = "default.txt";
	final static String FILENAME_ADDITION = "addition.txt";
	final static String FILENAME_SUBTRACTION = "subtraction.txt";
	final static String FILENAME_MULTIPLICATION = "multiplication.txt";
	static PrintWriter printWriter = null;	
	
	public static void main(String[] args) {					
		Scanner in = new Scanner(System.in);
		String mode = "";
		int modeInt = -1;
		
		do {	
			mode = "";
			modeInt = -1;
			System.out.println("Operations in the Turing Machine: (0 - DTM of Module 3)	(1 - Addition) (2 - Subtraction) (3 - Multiplication)");
			System.out.println("Enter an integer number between 0 and 3 to select a mode of operation. To quit enter 'q'.");
			//in = new Scanner(System.in);
			mode = in.next();

			if(isInteger(mode)) {
				modeInt = Integer.parseInt(mode);
				in.nextLine();
			}
			
			if(modeInt >= 0 && modeInt <= 3) {
				System.out.println("Enter input: ");
				String input = in.nextLine();               //10101 - mode 0	//110110 101011 - mode 1   //100 1110 or 110000 111010100 - mode 2
				selectMachine(input, modeInt);
			} else {
				if(!mode.equals("q")) {
					System.out.println("Invalid mode of operation.");				
				} else {
					System.out.println("End of program.");
				}
			}			
		} while(!mode.equals("q"));
		
		in.close();	
	}
	
	private static void selectMachine(String input, int mode) {
		TuringMachine tm = null;
		boolean trace_run_console = input.length() <= MAX_LENGTH_ARRAY ? true : false;
		
		switch(mode) {
		case 0:
			System.out.println("\nYou selected the DTM of Module 3.\n");
			tm = Operations.addDefaultCollection();	
			startTM(trace_run_console, tm, input, FILENAME_MODULE3);	
			break;
		case 1:
			System.out.println("\nYou selected the ADDITION operation.\n");
			tm = Operations.addAdditionCollection();
			startTM(trace_run_console, tm, input, FILENAME_ADDITION);	
			break;
		case 2:
			System.out.println("\nYou selected the SUBTRACTION operation.\n");
			tm = Operations.addSubtractionCollection();
			startTM(trace_run_console, tm, input, FILENAME_SUBTRACTION);	
			break;  
		case 3:
			System.out.println("\nYou selected the MULTIPLICATION operation.\n");
			tm = Operations.addMultiplicationCollection();
			startTM(trace_run_console, tm, input, FILENAME_MULTIPLICATION);	
			break;
		default:
			System.out.println("\nInvalid mode.");
		}
	}
	
	private static void startTM(boolean trace_run_console, TuringMachine myMachine, String input, String fileName) {	
		boolean accepted = false;
		if (!trace_run_console) {
			try {					
				printWriter = new PrintWriter(new FileWriter(FILEPATH + fileName));			
				accepted = myMachine.run(input, printWriter);	
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
			accepted = myMachine.run(input, null);
			
			if(accepted) {
				System.out.println("The input was accepted.\n");
			} else {
				System.out.println("The input was rejected.\n");
			}
		}
	}

	public static boolean isInteger(String mode) {
	    try {
	        Integer.parseInt(mode);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
	
}
