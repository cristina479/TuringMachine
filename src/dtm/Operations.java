package dtm;

public final class Operations {
	private static final boolean PRINT_INITIALIZATION = false;

	public Operations() {}
	
	/**
	 * This method creates the states and the transition table from the DTM in Module 3
	 * 
	 * @return the Turing Machine with the states and transitions of the DTM in Module 3
	 */
	public static TuringMachine addDefaultCollection() {	
		// create an instance of a Turing Machine
		TuringMachine tm = new TuringMachine();
		
		// add the states
		tm.addState("q0");
		tm.addState("q1");
		tm.addState("q2");
		tm.addState("q3");
		tm.setStartState("q0");
		tm.setAcceptState("qY");
		tm.setRejectState("qN");
		
		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Default state collection: " + tm.getStateSet() + "\n");
		}
		
		// add the transitions  (q, x, q', x', s) where q is the readState, x is the readSymbol, q' is the writeState, x' is the writeSymbol, and {-1, 0, 1} is the moveDirection
		tm.addTransition("q0", '0', "q0", '0', 1);
		tm.addTransition("q0", '1', "q0", '1', 1);
		tm.addTransition("q0", ' ', "q1", ' ', -1);
		
		tm.addTransition("q1", '0', "q2", ' ', -1);
		tm.addTransition("q1", '1', "q3", ' ', -1);
		tm.addTransition("q1", ' ', "qN", ' ', -1);
		
		tm.addTransition("q2", '0', "qY", ' ', -1);
		tm.addTransition("q2", '1', "qN", ' ', -1);
		tm.addTransition("q2", ' ', "qN", ' ', -1);
	
		tm.addTransition("q3", '0', "qN", ' ', -1);
		tm.addTransition("q3", '1', "qN", ' ', -1);
		tm.addTransition("q3", ' ', "qN", ' ', -1);
		
		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Deafult transition collection:\n" + tm.transitionsToString() + "\n");	
		}
		
		return tm;
	}
	
	/**
	 * This method creates the states and the transition table to perform a binary addition operation
	 * 
	 * @return the Turing Machine with the states and transitions needed to perform a binary addition operation
	 */
	public static TuringMachine addAdditionCollection() {
		// create a Turing Machine
		TuringMachine tm = new TuringMachine();

		// add the states
		tm.addState("q0");
		tm.addState("q1");
		tm.addState("q2");
		tm.addState("q3");
		tm.addState("q4");
		tm.addState("q5");
		tm.addState("q6");
		tm.addState("q7");
		tm.addState("q8");
		tm.addState("q9");
		tm.setStartState("q0");
		tm.setAcceptState("qY");
		tm.setRejectState("qN");

		// for debugging purposes 
		if(PRINT_INITIALIZATION) {
			System.out.println("Adittion state collection: " + tm.getStateSet() + "\n");
		}

		// add the transitions  (q, x, q', x', s) where q is the readState, x is the readSymbol, q' is the writeState, x' is the writeSymbol, and {-1, 0, 1} is the moveDirection
		tm.addTransition("q0", '0', "q0", '0', 1);
		tm.addTransition("q0", '1', "q0", '1', 1);
		tm.addTransition("q0", ' ', "q1", ' ', 1);
		tm.addTransition("q0", 'i', "q0", 'i', 1);
		tm.addTransition("q0", 'j', "q0", 'j', 1);

		tm.addTransition("q1", '0', "q1", '0', 1);
		tm.addTransition("q1", '1', "q1", '1', 1);
		tm.addTransition("q1", ' ', "q2", ' ', -1);
		tm.addTransition("q1", 'i', "q1", 'i', 1);
		tm.addTransition("q1", 'j', "q1", 'j', 1);

		tm.addTransition("q2", '0', "q3", ' ', -1);
		tm.addTransition("q2", '1', "q4", ' ', -1);
		tm.addTransition("q2", ' ', "q9", ' ', -1);
		tm.addTransition("q2", 'i', "qN", 'i', -1);
		tm.addTransition("q2", 'j', "qN", 'j', -1);
		
		tm.addTransition("q3", '0', "q3", '0', -1);
		tm.addTransition("q3", '1', "q3", '1', -1);
		tm.addTransition("q3", ' ', "q5", ' ', -1);
		tm.addTransition("q3", 'i', "q3", 'i', -1);
		tm.addTransition("q3", 'j', "q3", 'j', -1);
		
		tm.addTransition("q4", '0', "q4", '0', -1);
		tm.addTransition("q4", '1', "q4", '1', -1);
		tm.addTransition("q4", ' ', "q6", ' ', -1);
		tm.addTransition("q4", 'i', "q4", 'i', -1);
		tm.addTransition("q4", 'j', "q4", 'j', -1);
		
		tm.addTransition("q5", '0', "q0", 'i', 1);
		tm.addTransition("q5", '1', "q0", 'j', 1);
		tm.addTransition("q5", ' ', "q0", 'i', 1);
		tm.addTransition("q5", 'i', "q5", 'i', -1);
		tm.addTransition("q5", 'j', "q5", 'j', -1);
		
		tm.addTransition("q6", '0', "q7", '1', 0);
		tm.addTransition("q6", '1', "q6", '0', -1);
		tm.addTransition("q6", ' ', "q7", '1', 0);
		tm.addTransition("q6", 'i', "q6", 'i', -1);
		tm.addTransition("q6", 'j', "q6", 'j', -1);
		
		tm.addTransition("q7", '0', "q7", '0', 1);
		tm.addTransition("q7", '1', "q7", '1', 1);
		tm.addTransition("q7", ' ', "q8", ' ', -1);
		tm.addTransition("q7", 'i', "q8", 'i', -1);
		tm.addTransition("q7", 'j', "q8", 'j', -1);
		
		tm.addTransition("q8", '0', "q0", 'i', 1);
		tm.addTransition("q8", '1', "q0", 'j', 1);
		tm.addTransition("q8", ' ', "qN", ' ', 1);
		tm.addTransition("q8", 'i', "qN", 'i', 1);
		tm.addTransition("q8", 'j', "qN", 'j', 1);
		
		tm.addTransition("q9", '0', "q9", '0', -1);
		tm.addTransition("q9", '1', "q9", '1', -1);
		tm.addTransition("q9", ' ', "qY", ' ', 1);
		tm.addTransition("q9", 'i', "q9", '0', -1);
		tm.addTransition("q9", 'j', "q9", '1', -1);

		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Addition transition collection:\n" + tm.transitionsToString() + "\n");
		}
		
		return tm;
	}
	
	/**
	 * This method creates the states and the transition table to perform a binary subtraction operation
	 * 
	 * @return the Turing Machine with the states and transitions needed to perform a binary subtraction operation
	 */
	public static TuringMachine addSubtractionCollection() {
		// create an instance of a Turing Machine
		TuringMachine tm = new TuringMachine();

		// add the states
		tm.addState("q0");
		tm.addState("q1");
		tm.addState("q2");
		tm.addState("q3");
		tm.addState("q4");
		tm.addState("q5");
		tm.addState("q6");
		tm.addState("q7");
		tm.addState("q8");
		tm.addState("q9");
		tm.addState("q10");
		tm.addState("q11");
		tm.addState("q12");
		tm.setStartState("q0");
		tm.setAcceptState("qY");
		tm.setRejectState("qN");
		
		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Subtraction state collection: " + tm.getStateSet() + "\n");
		}
			
		// add the transitions  (q, x, q', x', s) where q is the readState, x is the readSymbol, q' is the writeState, x' is the writeSymbol, and {-1, 0, 1} is the moveDirection
		tm.addTransition("q0", '0', "q0", '0', 1);
		tm.addTransition("q0", '1', "q0", '1', 1);
		tm.addTransition("q0", ' ', "q1", ' ', 1);
		tm.addTransition("q0", 'i', "q0", 'i', 1);
		tm.addTransition("q0", 'j', "q0", 'j', 1);

		tm.addTransition("q1", '0', "q1", '0', 1);
		tm.addTransition("q1", '1', "q1", '1', 1);
		tm.addTransition("q1", ' ', "q2", ' ', -1);
		tm.addTransition("q1", 'i', "q1", 'i', 1);
		tm.addTransition("q1", 'j', "q1", 'j', 1);

		tm.addTransition("q2", '0', "q3", ' ', -1);
		tm.addTransition("q2", '1', "q4", ' ', -1);
		tm.addTransition("q2", ' ', "q9", ' ', -1);	
		tm.addTransition("q2", 'i', "qN", 'i', -1);
		tm.addTransition("q2", 'j', "qN", 'j', -1);		
		
		tm.addTransition("q3", '0', "q3", '0', -1);
		tm.addTransition("q3", '1', "q3", '1', -1);
		tm.addTransition("q3", ' ', "q5", ' ', -1);	
		tm.addTransition("q3", 'i', "qN", 'i', -1);
		tm.addTransition("q3", 'j', "qN", 'j', -1);
		
		tm.addTransition("q4", '0', "q4", '0', -1);
		tm.addTransition("q4", '1', "q4", '1', -1);
		tm.addTransition("q4", ' ', "q6", ' ', -1);	
		tm.addTransition("q4", 'i', "qN", 'i', -1);
		tm.addTransition("q4", 'j', "qN", 'j', -1);
	
		tm.addTransition("q5", '0', "q0", 'i', 1);
		tm.addTransition("q5", '1', "q0", 'j', 1);
		tm.addTransition("q5", ' ', "q5", ' ', -1);	
		tm.addTransition("q5", 'i', "q5", 'i', -1);
		tm.addTransition("q5", 'j', "q5", 'j', -1);

		tm.addTransition("q6", '0', "q10", '0', -1);   
		tm.addTransition("q6", '1', "q7", '0', 1);	   
		tm.addTransition("q6", ' ', "qN", ' ', 1);	
		tm.addTransition("q6", 'i', "q6", 'i', -1);
		tm.addTransition("q6", 'j', "q6", 'j', -1);
	
		tm.addTransition("q7", '0', "q7", '0', 1);
		tm.addTransition("q7", '1', "q7", '1', 1);
		tm.addTransition("q7", ' ', "q8", ' ', -1);	
		tm.addTransition("q7", 'i', "q8", 'i', -1);
		tm.addTransition("q7", 'j', "q8", 'j', -1);
		
		tm.addTransition("q8", '0', "q0", 'i', 1);
		tm.addTransition("q8", '1', "q0", 'j', 1);
		tm.addTransition("q8", ' ', "qN", ' ', 0);	
		tm.addTransition("q8", 'i', "qN", 'i', 0);
		tm.addTransition("q8", 'j', "qN", 'j', 0);

		tm.addTransition("q9", '0', "q9", '0', -1);
		tm.addTransition("q9", '1', "q9", '1', -1);
		tm.addTransition("q9", ' ', "q12", ' ', 1);	
		tm.addTransition("q9", 'i', "q9", '0', -1);
		tm.addTransition("q9", 'j', "q9", '1', -1);
	
		tm.addTransition("q10", '0', "q10", '0', -1);
		tm.addTransition("q10", '1', "q11", '0', 1);
		tm.addTransition("q10", ' ', "qN", ' ', 1);	
		tm.addTransition("q10", 'i', "qN", 'i', 0);
		tm.addTransition("q10", 'j', "qN", 'j', 0);
	
		tm.addTransition("q11", '0', "q11", '1', 1);
		tm.addTransition("q11", '1', "q8", '1', -1);
		tm.addTransition("q11", ' ', "q8", ' ', -1);	
		tm.addTransition("q11", 'i', "q8", 'i', -1);
		tm.addTransition("q11", 'j', "q8", 'j', -1);
	
		tm.addTransition("q12", '0', "q12", ' ', 1);
		tm.addTransition("q12", '1', "qY", '1', 0);
		tm.addTransition("q12", ' ', "qN", ' ', 1);	
		tm.addTransition("q12", 'i', "qN", 'i', 0);
		tm.addTransition("q12", 'j', "qN", 'j', 0);
				
		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Subtraction transition collection:\n" + tm.transitionsToString() + "\n");
		}
		
		return tm;
	}
	
	/**
	 * This method creates the states and the transition table to perform a binary multiplication operation
	 * 
	 * @return the Turing Machine with the states and transitions needed to perform a binary subtraction operation
	 */
	public static TuringMachine addMultiplicationCollection() {
		// create an instance of a Turing Machine
		TuringMachine tm = new TuringMachine();		

		// add the states
		tm.addState("q0");
		tm.addState("q1");
		tm.addState("q2");
		tm.addState("q3");
		tm.addState("q4");
		tm.addState("q5");		
		tm.addState("q6");
		tm.addState("q7");
		tm.addState("q8");
		tm.addState("q9");		
		tm.addState("q10");
		tm.addState("q11");
		tm.addState("q12");		
		tm.addState("q13");
		tm.addState("q14");
		tm.addState("q15");
		tm.addState("q16");
		tm.addState("q17");
		tm.addState("q18");
		tm.addState("q19");
		tm.addState("q20");
		tm.addState("q21");
		tm.addState("q22");
		tm.addState("q23");
		tm.addState("q24");
		tm.addState("q25");
		tm.addState("q26");
		tm.addState("q27");
		tm.addState("q28");
		tm.addState("q29");
		tm.addState("q30");
		tm.addState("q31");		
		tm.addState("q32");
		tm.addState("q33");
		tm.addState("q34");
		tm.addState("q35");
		tm.addState("q36");
		tm.addState("q37");
		tm.addState("q38");	
		tm.addState("q39");
		tm.addState("q40");
		tm.addState("q41");
		tm.setStartState("q0");
		tm.setAcceptState("qY");
		tm.setRejectState("qN");

		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Multiplication state collection: " + tm.getStateSet() + "\n");
		}

		// add the transitions  (q, x, q', x', s) where q is the readState, x is the readSymbol, q' is the writeState, x' is the writeSymbol, and {-1, 0, 1} is the moveDirection
		tm.addTransition("q0", '0', "q1", '0', -1);
		tm.addTransition("q0", '1', "q1", '1', -1);
		tm.addTransition("q0", ' ', "q1", ' ', -1);
		tm.addTransition("q0", 'a', "q1", 'a', -1);
		tm.addTransition("q0", 'i', "q1", 'i', -1);
		tm.addTransition("q0", 'j', "q1", 'j', -1);
		
		tm.addTransition("q1", '0', "qN", '0', -1);
		tm.addTransition("q1", '1', "qN", '1', -1);
		tm.addTransition("q1", ' ', "q2", ' ', -1);
		tm.addTransition("q1", 'a', "qN", 'a', -1);
		tm.addTransition("q1", 'i', "qN", 'i', -1);
		tm.addTransition("q1", 'j', "qN", 'j', -1);
		
		tm.addTransition("q2", '0', "qN", '0', 1);
		tm.addTransition("q2", '1', "qN", '1', 1);
		tm.addTransition("q2", ' ', "q3", '0', 1);
		tm.addTransition("q2", 'a', "qN", 'a', 1);
		tm.addTransition("q2", 'i', "qN", 'i', 1);
		tm.addTransition("q2", 'j', "qN", 'j', 1);

		tm.addTransition("q3", '0', "qN", '0', 1);
		tm.addTransition("q3", '1', "qN", '1', 1);
		tm.addTransition("q3", ' ', "q4", ' ', 1);
		tm.addTransition("q3", 'a', "qN", 'a', 1);
		tm.addTransition("q3", 'i', "qN", 'i', 1);
		tm.addTransition("q3", 'j', "qN", 'j', 1);
		
		tm.addTransition("q4", '0', "q4", '0', 1);
		tm.addTransition("q4", '1', "q4", '1', 1);
		tm.addTransition("q4", ' ', "q5", ' ', -1);
		tm.addTransition("q4", 'a', "q5", 'a', -1);
		tm.addTransition("q4", 'i', "qN", 'i', 1);
		tm.addTransition("q4", 'j', "qN", 'j', 1);
		
		tm.addTransition("q5", '0', "q6", 'a', 1);
		tm.addTransition("q5", '1', "q10", 'a', 1);
		tm.addTransition("q5", ' ', "qN", ' ', 1);
		tm.addTransition("q5", 'a', "qN", 'a', 1);
		tm.addTransition("q5", 'i', "qN", 'i', 1);
		tm.addTransition("q5", 'j', "qN", 'j', 1);
		
		tm.addTransition("q6", '0', "q7", '0', 1);
		tm.addTransition("q6", '1', "q7", '1', 1);
		tm.addTransition("q6", ' ', "q6", ' ', 1);
		tm.addTransition("q6", 'a', "q6", 'a', 1);
		tm.addTransition("q6", 'i', "q7", 'i', 1);
		tm.addTransition("q6", 'j', "q7", 'j', 1);

		tm.addTransition("q7", '0', "q7", '0', 1);
		tm.addTransition("q7", '1', "q7", '1', 1);
		tm.addTransition("q7", ' ', "q8", '0', -1);
		tm.addTransition("q7", 'a', "q7", 'a', 1);
		tm.addTransition("q7", 'i', "q7", 'i', 1);
		tm.addTransition("q7", 'j', "q7", 'j', 1);
		
		tm.addTransition("q8", '0', "q8", '0', -1);
		tm.addTransition("q8", '1', "q8", '1', -1);
		tm.addTransition("q8", ' ', "q9", ' ', -1);
		tm.addTransition("q8", 'a', "q8", 'a', -1);
		tm.addTransition("q8", 'i', "q8", 'i', -1);
		tm.addTransition("q8", 'j', "q8", 'j', -1);

		tm.addTransition("q9", '0', "q5", '0', 0);
		tm.addTransition("q9", '1', "q5", '1', 0);
		tm.addTransition("q9", ' ', "q39", ' ', 1);
		tm.addTransition("q9", 'a', "q9", 'a', -1);
		tm.addTransition("q9", 'i', "qN", 'i', 1);
		tm.addTransition("q9", 'j', "qN", 'j', 1);		
	
		tm.addTransition("q10", '0', "q11", '0', 1);
		tm.addTransition("q10", '1', "q11", '1', 1);
		tm.addTransition("q10", ' ', "q10", ' ', 1);
		tm.addTransition("q10", 'a', "q10", 'a', 1);
		tm.addTransition("q10", 'i', "q11", 'i', 1);
		tm.addTransition("q10", 'j', "q11", 'j', 1);

		tm.addTransition("q11", '0', "q11", '0', 1);
		tm.addTransition("q11", '1', "q11", '1', 1);
		tm.addTransition("q11", ' ', "q12", ' ', -1);
		tm.addTransition("q11", 'a', "q11", 'a', 1);
		tm.addTransition("q11", 'i', "q11", 'i', 1);
		tm.addTransition("q11", 'j', "q11", 'j', 1);

		tm.addTransition("q12", '0', "q13", 'j', -1);
		tm.addTransition("q12", '1', "q22", 'i', -1);
		tm.addTransition("q12", ' ', "q32", ' ', 1);
		tm.addTransition("q12", 'a', "qN", 'a', 1);
		tm.addTransition("q12", 'i', "q12", 'i', -1);
		tm.addTransition("q12", 'j', "q12", 'j', -1);	
		
		tm.addTransition("q13", '0', "q13", '0', -1);
		tm.addTransition("q13", '1', "q13", '1', -1);
		tm.addTransition("q13", ' ', "q14", ' ', -1);
		tm.addTransition("q13", 'a', "q13", 'a', -1);
		tm.addTransition("q13", 'i', "q13", 'i', -1);
		tm.addTransition("q13", 'j', "q13", 'j', -1);	
	
		tm.addTransition("q14", '0', "q15", '0', -1);
		tm.addTransition("q14", '1', "q15", '1', -1);
		tm.addTransition("q14", ' ', "q14", ' ', -1);
		tm.addTransition("q14", 'a', "q15", 'a', -1);
		tm.addTransition("q14", 'i', "q15", 'i', -1);
		tm.addTransition("q14", 'j', "q15", 'j', -1);	

		tm.addTransition("q15", '0', "q15", '0', -1);
		tm.addTransition("q15", '1', "q15", '1', -1);
		tm.addTransition("q15", ' ', "q16", ' ', -1);
		tm.addTransition("q15", 'a', "q15", 'a', -1);
		tm.addTransition("q15", 'i', "q15", 'i', -1);
		tm.addTransition("q15", 'j', "q15", 'j', -1);	

		tm.addTransition("q16", '0', "q17", 'j', 1);
		tm.addTransition("q16", '1', "q17", 'i', 1);
		tm.addTransition("q16", ' ', "q17", 'j', 1);
		tm.addTransition("q16", 'a', "qN", 'a', -1);
		tm.addTransition("q16", 'i', "q16", 'i', -1);
		tm.addTransition("q16", 'j', "q16", 'j', -1);	
	
		tm.addTransition("q17", '0', "q17", '0', 1);
		tm.addTransition("q17", '1', "q17", '1', 1);
		tm.addTransition("q17", ' ', "q18", ' ', 1);
		tm.addTransition("q17", 'a', "q17", 'a', 1);
		tm.addTransition("q17", 'i', "q17", 'i', 1);
		tm.addTransition("q17", 'j', "q17", 'j', 1);	
	
		tm.addTransition("q18", '0', "q19", '0', 1);
		tm.addTransition("q18", '1', "q19", '1', 1);
		tm.addTransition("q18", ' ', "q18", ' ', 1);
		tm.addTransition("q18", 'a', "q19", 'a', 1);
		tm.addTransition("q18", 'i', "q19", 'i', 1);
		tm.addTransition("q18", 'j', "q19", 'j', 1);	
	
		tm.addTransition("q19", '0', "q19", '0', 1);
		tm.addTransition("q19", '1', "q19", '1', 1);
		tm.addTransition("q19", ' ', "q20", ' ', 1);
		tm.addTransition("q19", 'a', "q19", 'a', 1);
		tm.addTransition("q19", 'i', "q19", 'i', 1);
		tm.addTransition("q19", 'j', "q19", 'j', 1);	
		
		tm.addTransition("q20", '0', "q21", '0', 1);
		tm.addTransition("q20", '1', "q21", '1', 1);
		tm.addTransition("q20", ' ', "q20", ' ', 1);
		tm.addTransition("q20", 'a', "q21", 'a', 1);
		tm.addTransition("q20", 'i', "q21", 'i', 1);
		tm.addTransition("q20", 'j', "q21", 'j', 1);	
		
		tm.addTransition("q21", '0', "q21", '0', 1);
		tm.addTransition("q21", '1', "q21", '1', 1);
		tm.addTransition("q21", ' ', "q12", ' ', -1);
		tm.addTransition("q21", 'a', "q21", 'a', 1);
		tm.addTransition("q21", 'i', "q21", 'i', 1);
		tm.addTransition("q21", 'j', "q21", 'j', 1);	
		
		tm.addTransition("q22", '0', "q22", '0', -1);
		tm.addTransition("q22", '1', "q22", '1', -1);
		tm.addTransition("q22", ' ', "q23", ' ', -1);
		tm.addTransition("q22", 'a', "q22", 'a', -1);
		tm.addTransition("q22", 'i', "q22", 'i', -1);
		tm.addTransition("q22", 'j', "q22", 'j', -1);	

		tm.addTransition("q23", '0', "q24", '0', -1);
		tm.addTransition("q23", '1', "q24", '1', -1);
		tm.addTransition("q23", ' ', "q23", ' ', -1);
		tm.addTransition("q23", 'a', "q24", 'a', -1);
		tm.addTransition("q23", 'i', "q24", 'i', -1);
		tm.addTransition("q23", 'j', "q24", 'j', -1);	

		tm.addTransition("q24", '0', "q24", '0', -1);
		tm.addTransition("q24", '1', "q24", '1', -1);
		tm.addTransition("q24", ' ', "q25", ' ', -1);
		tm.addTransition("q24", 'a', "q24", 'a', -1);
		tm.addTransition("q24", 'i', "q24", 'i', -1);
		tm.addTransition("q24", 'j', "q24", 'j', -1);	

		tm.addTransition("q25", '0', "q27", 'i', 1);
		tm.addTransition("q25", '1', "q26", 'j', -1);
		tm.addTransition("q25", ' ', "q27", 'i', 1);
		tm.addTransition("q25", 'a', "qN", 'a', -1);
		tm.addTransition("q25", 'i', "q25", 'i', -1);
		tm.addTransition("q25", 'j', "q25", 'j', -1);	

		tm.addTransition("q26", '0', "q27", '1', 1);
		tm.addTransition("q26", '1', "q26", '0', -1);
		tm.addTransition("q26", ' ', "q27", '1', 1);
		tm.addTransition("q26", 'a', "qN", 'a', 1);
		tm.addTransition("q26", 'i', "qN", 'i', 1);
		tm.addTransition("q26", 'j', "qN", 'j', 1);	

		tm.addTransition("q27", '0', "q27", '0', 1);
		tm.addTransition("q27", '1', "q27", '1', 1);
		tm.addTransition("q27", ' ', "q28", ' ', 1);
		tm.addTransition("q27", 'a', "q27", 'a', 1);
		tm.addTransition("q27", 'i', "q27", 'i', 1);
		tm.addTransition("q27", 'j', "q27", 'j', 1);	

		tm.addTransition("q28", '0', "q29", '0', 1);
		tm.addTransition("q28", '1', "q29", '1', 1);
		tm.addTransition("q28", ' ', "q28", ' ', 1);
		tm.addTransition("q28", 'a', "q29", 'a', 1);
		tm.addTransition("q28", 'i', "q29", 'i', 1);
		tm.addTransition("q28", 'j', "q29", 'j', 1);	

		tm.addTransition("q29", '0', "q29", '0', 1);
		tm.addTransition("q29", '1', "q29", '1', 1);
		tm.addTransition("q29", ' ', "q30", ' ', 1);
		tm.addTransition("q29", 'a', "q29", 'a', 1);
		tm.addTransition("q29", 'i', "q29", 'i', 1);
		tm.addTransition("q29", 'j', "q29", 'j', 1);	

		tm.addTransition("q30", '0', "q31", '0', 1);
		tm.addTransition("q30", '1', "q31", '1', 1);
		tm.addTransition("q30", ' ', "q30", ' ', 1);
		tm.addTransition("q30", 'a', "q31", 'a', 1);
		tm.addTransition("q30", 'i', "q31", 'i', 1);
		tm.addTransition("q30", 'j', "q31", 'j', 1);	

		tm.addTransition("q31", '0', "q31", '0', 1);
		tm.addTransition("q31", '1', "q31", '1', 1);
		tm.addTransition("q31", ' ', "q12", ' ', -1);
		tm.addTransition("q31", 'a', "q31", 'a', 1);
		tm.addTransition("q31", 'i', "q31", 'i', 1);
		tm.addTransition("q31", 'j', "q31", 'j', 1);	
	
		tm.addTransition("q32", '0', "qN", '0', -1);
		tm.addTransition("q32", '1', "qN", '1', -1);
		tm.addTransition("q32", ' ', "q33", ' ', -1);
		tm.addTransition("q32", 'a', "qN", 'a', 1);
		tm.addTransition("q32", 'i', "q32", '1', 1);
		tm.addTransition("q32", 'j', "q32", '0', 1);

		tm.addTransition("q33", '0', "q33", '0', -1);
		tm.addTransition("q33", '1', "q33", '1', -1);
		tm.addTransition("q33", ' ', "q34", ' ', -1);
		tm.addTransition("q33", 'a', "q33", 'a', -1);
		tm.addTransition("q33", 'i', "q33", 'i', -1);
		tm.addTransition("q33", 'j', "q33", 'j', -1);

		tm.addTransition("q34", '0', "q35", '0', -1);
		tm.addTransition("q34", '1', "q35", '1', -1);
		tm.addTransition("q34", ' ', "q34", ' ', -1);
		tm.addTransition("q34", 'a', "q35", 'a', -1);
		tm.addTransition("q34", 'i', "q35", 'i', -1);
		tm.addTransition("q34", 'j', "q35", 'j', -1);

		tm.addTransition("q35", '0', "q35", '0', -1);
		tm.addTransition("q35", '1', "q35", '1', -1);
		tm.addTransition("q35", ' ', "q36", ' ', -1);
		tm.addTransition("q35", 'a', "q35", 'a', -1);
		tm.addTransition("q35", 'i', "q35", 'i', -1);
		tm.addTransition("q35", 'j', "q35", 'j', -1);

		tm.addTransition("q36", '0', "q37", '0', 1);
		tm.addTransition("q36", '1', "q37", '1', 1);
		tm.addTransition("q36", ' ', "q37", ' ', 1);
		tm.addTransition("q36", 'a', "q37", 'a', 1);
		tm.addTransition("q36", 'i', "q36", '1', -1);
		tm.addTransition("q36", 'j', "q36", '0', -1);

		tm.addTransition("q37", '0', "q37", '0', 1);
		tm.addTransition("q37", '1', "q37", '1', 1);
		tm.addTransition("q37", ' ', "q38", ' ', 1);
		tm.addTransition("q37", 'a', "q37", 'a', 1);
		tm.addTransition("q37", 'i', "q37", 'i', 1);
		tm.addTransition("q37", 'j', "q37", 'j', 1);

		tm.addTransition("q38", '0', "q38", '0', 1);
		tm.addTransition("q38", '1', "q38", '1', 1);
		tm.addTransition("q38", ' ', "q6", ' ', 1);
		tm.addTransition("q38", 'a', "q38", 'a', 1);
		tm.addTransition("q38", 'i', "q38", 'i', 1);
		tm.addTransition("q38", 'j', "q38", 'j', 1);
		
		tm.addTransition("q39", '0', "qN", '0', 1);
		tm.addTransition("q39", '1', "qN", '1', 1);
		tm.addTransition("q39", ' ', "q40", ' ', 1);
		tm.addTransition("q39", 'a', "q39", ' ', 1);
		tm.addTransition("q39", 'i', "qN", 'i', 1);
		tm.addTransition("q39", 'j', "qN", 'j', 1);

		tm.addTransition("q40", '0', "q40", ' ', 1);
		tm.addTransition("q40", '1', "q40", ' ', 1);
		tm.addTransition("q40", ' ', "q41", ' ', -1);
		tm.addTransition("q40", 'a', "q40", ' ', 1);
		tm.addTransition("q40", 'i', "q40", ' ', 1);
		tm.addTransition("q40", 'j', "q40", ' ', 1);

		tm.addTransition("q41", '0', "qY", '0', 0);
		tm.addTransition("q41", '1', "qY", '1', 0);
		tm.addTransition("q41", ' ', "q41", ' ', -1);
		tm.addTransition("q41", 'a', "qN", 'a', 0);
		tm.addTransition("q41", 'i', "qN", 'i', 0);
		tm.addTransition("q41", 'j', "qN", 'j', 0);

		// for debugging purposes
		if(PRINT_INITIALIZATION) {
			System.out.println("Multiplication transition collection:\n" + tm.transitionsToString() + "\n");
		}
		
		return tm;
	}
}
 