/**
 * 
 */
package dtm;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * To stop printing trace runs to a file (input >= 30) or to the terminal (input < 30), change PRINT_TRACE to false.
 * 
 * Class for constructing a Turing Machine
 * @author Cristina Padro-Juarbe
 *
 */
public class TuringMachine {

	private static final boolean DEBUG = false;
	private static final boolean PRINT_TRACE = true;
	private static final int INCREASE_TAPE = 1;
	private static final char BLANK = ' ';
	private Set<String> stateSet;
	private Set<Transition> transitionSet;
	private char[] tape;
	private String startState;
	private String acceptState;
	private String rejectState;
	private String currentState;
	private int symbolIndex;
	
	/**
	 * Constructor of the Turing Machine
	 */
	public TuringMachine() {
		this.stateSet = new HashSet<String>();
		this.transitionSet = new HashSet<Transition>();
		this.tape = null;
		this.startState = null;
		this.acceptState = null;
		this.rejectState = null;
		this.currentState = null;
		this.symbolIndex = 0;
	}
	
	/**
     * Class for constructing a Transition for the Turing Machine
 	 * @author Cristina Padro-Juarbe
	 *
	 */
	private class Transition {
		public char readSymbol;
		public char writeSymbol;
		public String readState;
		public String writeState;
		public int moveDirection;
		
		public Transition() {}

		/**
		 * 
		 * @return the string value of the current symbol to read
		 */
		public char getReadSymbol() {
			return readSymbol;
		}

		/**
		 * 
		 * @param readSymbol string value of the current symbol to read
		 */
		public void setReadSymbol(char readSymbol) {
			this.readSymbol = readSymbol;
		}

		/**
		 * 
		 * @return the string value of the current symbol to write 
		 */
		public char getWriteSymbol() {
			return writeSymbol;
		}

		/**
		 * 
		 * @param writeSymbol string value of the current symbol to write 
		 */
		public void setWriteSymbol(char writeSymbol) {
			this.writeSymbol = writeSymbol;
		}

		/**
		 * 
		 * @return the string value of the current state
		 */
		public String getReadState() {
			return readState;
		}

		/**
		 * 
		 * @param readState string value of the current state
		 */
		public void setReadState(String readState) {
			this.readState = readState;
		}

		/**
		 * 
		 * @return the string value of the next state
		 */
		public String getWriteState() {
			return writeState;
		}

		/**
		 * 
		 * @param writeState string value of the next state
		 */
		public void setWriteState(String writeState) {
			this.writeState = writeState;
		}

		/**
		 * 
		 * @return the direction of the tape head
		 */
		public int getMoveDirection() {
			return moveDirection;
		}

		/**
		 * 
		 * @param moveDirection direction of the tape head
		 */
		public void setMoveDirection(int moveDirection) {
			this.moveDirection = moveDirection;
		}
		
		/**
		 * 
		 * @return the string value of a transition
		 */
		public String transitionToString() {			
			return "(" + this.getReadState() + ", " + this.getReadSymbol() + ", " + this.getWriteState() + ", " + this.getWriteSymbol() + ", " + this.getMoveDirection() + ")";
		}
	}

	/**
	 * This method is the control unit of the Transition Machine and where all the work gets done.
	 * 
	 * @param input the bytes provided by the user {0, 1}*
	 * @param pw is the Print Writer object to write output and traces to a file
	 * @return run if the input was accepted. Returns false if the input was rejected.
	 */
	public boolean run(String input, PrintWriter pw) {
		// start at the start state
		this.currentState = this.startState;	
		// initialize the tape to an array of characters
		this.tape = input.toCharArray();
		
		// for printing traces
		if(PRINT_TRACE) {
			if (pw == null) {
				System.out.println("Initial value in tape: [" + String.valueOf(this.tape) + "]\n");		
				System.out.println("Outputs after the current transition is executed:\n");
			} else {
				pw.println("Initial values in tape: [" + String.valueOf(this.tape) + "]");	
				pw.println();
				pw.println("Outputs after the current transition is executed:\n");
				pw.println();
			}
		}
		
		// at the start state q0, skip all blank read symbols and start the TM at a non-blank symbol 
		while(this.currentState.equals(this.startState) && this.tape[this.symbolIndex] == BLANK && this.symbolIndex < this.tape.length) {
			this.symbolIndex++;
		}
		
		// loop while the current state is not a halting state
		while(!this.currentState.equals(this.acceptState) && !this.currentState.equals(this.rejectState)) { 
			
			// get the current transition from the transition table
			Transition currentTransition = getTransition(this.currentState, this.tape[this.symbolIndex]);	
			
			// used for trace runs only
			Transition tempTransition = currentTransition;
			int tempSymbolIndex = this.symbolIndex;
			
			// this transition is not in the transition set
			if(currentTransition == null) {
				this.currentState = this.rejectState;
				return false;
			} 

			// go to next state
			this.currentState = currentTransition.getWriteState();			
			// write the symbol indicated by the current transition
			this.tape[this.symbolIndex] = currentTransition.getWriteSymbol();

			// move to left, move to write, or don't move 
			if(currentTransition.getMoveDirection() == -1) {
				this.symbolIndex--;
			} else if(currentTransition.getMoveDirection() == 1) {
				this.symbolIndex++;
			}

			// add more space in the 'infinite' tape and calculate the new symbolIndex in the new array			
			if(this.symbolIndex < 0) {
				//grow to the left
				expandTapeLeft();
			} else if(this.symbolIndex > this.tape.length - 1) {
				//grow to the right
				expandTapeRight();
			}
			
			if(PRINT_TRACE) {	
				// peek the next transition in the transition table
				Transition nextTransition = getNextTransition(tempTransition.getWriteState(), tempSymbolIndex, tempTransition.getMoveDirection());
				
				// print to the terminal
				if(pw == null) {
					System.out.print("Tape: [" + String.valueOf(this.tape) + "]\tindex: " + tempSymbolIndex + "\tCurrent Transition: " + tempTransition.transitionToString() + "\t");				
					
					// There are no more states. The current state is the one of the halting states.
					if(nextTransition == null) {
						System.out.println("\tHalting state: " + tempTransition.getWriteState() + "\n");
						System.out.println("Tape output: " + String.valueOf(this.tape));
						
						// case where Mode is 2 (Subtraction) and the results is a negative number
						if(tempTransition.getWriteState().equals(this.rejectState) && (tempTransition.getReadState().equals("q10") && tempTransition.getReadSymbol() == BLANK ||  
								tempTransition.getReadState().equals("q6") && tempTransition.getReadSymbol() == BLANK)) {
							System.out.println("\nThe results is a negative number. This TM doesn't handle signed binaries.");
						}						
					} else {
						// prints the next transition
						System.out.println("\tNext Transition: " + nextTransition.transitionToString());
					}
				} else {
					// print to a file
					pw.print("Tape: [" + String.valueOf(this.tape) + "]\tindex: " + tempSymbolIndex + "\tCurrent Transition: " + tempTransition.transitionToString() + "	");				
					
					// There are no more states. The current state is the one of the halting states.
					if(nextTransition == null) {
						pw.println("	Halting state: " + tempTransition.getWriteState());
						pw.println();
						pw.println("Tape output: " + String.valueOf(this.tape));
						
						// case where Mode is 2 (Subtraction) and the results is a negative number
						if(tempTransition.getWriteState().equals(this.rejectState) && (tempTransition.getReadState().equals("q10") && tempTransition.getReadSymbol() == BLANK ||  
								tempTransition.getReadState().equals("q6") && tempTransition.getReadSymbol() == BLANK)) {
							pw.println();
							pw.println("The results is a negative number. This TM doesn't handle signed binaries.");
						}
					} else {
						// prints the next transition
						pw.println("	Next Transition: " + nextTransition.transitionToString());
					}
				}				
			}
		}
		
		return this.currentState.equals(this.acceptState);
	}
	
	/**
	 * This method simulates an 'infinite' tape. It increments the tape by one when we need to move to the left and  
	 * their is no more space in the array.
	 * 
	 */
	private void expandTapeLeft() {
		char[] newTape = new char[this.tape.length + INCREASE_TAPE];
		for(int index = 0; index < newTape.length; index++) {
			if(index < INCREASE_TAPE) {
				newTape[index] = BLANK;
			} else {
				newTape[index] = this.tape[index - INCREASE_TAPE];
			}
		}
		
		this.tape = newTape;
		this.symbolIndex += INCREASE_TAPE; 
		newTape = null;		
	}
	
	/**
	 * This method simulates an 'infinite' tape. It increments the tape by one when we need to move to the right and 
	 * their is no more space in the array.
	 * 
	 */
	private void expandTapeRight() {
		char[] newTape = new char[this.tape.length + INCREASE_TAPE];
		for(int index = 0; index < newTape.length; index++) {
			if(index < this.tape.length) {
				newTape[index] = this.tape[index];
			} else {
				newTape[index] = BLANK;
			}
		}
		
		this.tape = newTape; 
		newTape = null;
	}

	/**
	 * This method adds a state to the state set if it's not contained in the state set.
	 * 
	 * @param state the string value of the start state
	 * @return true if the state was added to the state set. Returns false if the state is already in the state set.
	 */
	public boolean addState(String state) {
		// add the state to the state set if it doesn't exist
		if(!this.stateSet.contains(state)) {
			this.stateSet.add(state);	
			if(DEBUG) {
				System.out.println("State " + state + " added.\n");		
			}
			return true;
		}	
		
		if(DEBUG) {
			System.out.println("State " + state + " skipped because is already in the set.\n");	
		}		
		return false;
	}
	
	/**
	 * Initializes the start state. It adds the start state to the state set if it's not contained in the state set.
	 * 
	 * @param q0 the string value of the start state q0
	 */
	public void setStartState(String q0) {
		// add the start state to the state set if it doesn't exist
		if(!this.stateSet.contains(q0)) {
			addState(q0);
		} 
		
		// initializes the start state
		this.startState = q0;	
		
		if(DEBUG) {
			System.out.println("Start state " + this.startState + " set.\n");
		}
	}

	/**
	 * Initializes the accept state. It adds the accept state to the state set if it's not contained in the state set.
	 * 
	 * @param qy the string value of the accept state (halting state qY)
	 */
	public void setAcceptState(String qy) {
		// add the accept state to the state set if it doesn't exist
		if(!this.stateSet.contains(qy)) {
			addState(qy);
		} 
		
		// initializes the accept state
		this.acceptState = qy;	
		if(DEBUG) {
			System.out.println("Accept state " + this.acceptState + " set.\n");
		}
	}
	
	/**
	 * Initializes the reject state. It adds the reject state to the state set if it's not contained in the state set.
	 * 
	 * @param qn the string value of the reject state (halting state qN)
	 */
	public void setRejectState(String qn) {
		// add the reject state to the state set if it doesn't exist
		if(!this.stateSet.contains(qn)) {
			addState(qn);	
		} 		
		
		// initializes the reject state
		this.rejectState = qn;	
		if(DEBUG) {
			System.out.println("Reject state " + this.rejectState + " set.\n");
		}
	}
	
	/**
	 * This method adds a transition to the transition set if it's not contained in the transition set.
	 * 
	 * @param readState the string value of the current state 
	 * @param readSymbol the string value of the current symbol to read
	 * @param writeState the string value of the next state 
	 * @param writeSymbol the string value of the current symbol to write
	 * @param moveDirection the direction of the current transition
	 * @return true if the transition was added to the transition set. Returns false if the transition is already in the transition set.
	 */
	public boolean addTransition(String readState, char readSymbol, String writeState, char writeSymbol, int moveDirection) {
		if(DEBUG) {
			if(!this.stateSet.contains(readState)) {
				System.out.println("Invalid write state " + readState + "\n");
			} else if(!this.stateSet.contains(writeState)) {
				System.out.println("Invalid write state " + writeState + "\n");
			} else if(moveDirection < -1 || moveDirection > 1) {
				System.out.println("Invalid direction " + moveDirection + "\n");			
			}
		}
		
		// check that the states exists and that the mode direction is valid
		if(!this.stateSet.contains(readState) || !this.stateSet.contains(writeState) || (moveDirection < -1 || moveDirection > 1)) {
			if(DEBUG) {
				System.out.println("Transition (" + readState + ", " + readSymbol + ", " + writeState + ", " + writeSymbol + ", " + moveDirection + ") skipped because of invalid state or move direction.\n");			
			}			
			return false;
		}
			
		// create a transition
		Transition transition = new Transition();
		transition.setReadState(readState);
		transition.setReadSymbol(readSymbol);
		transition.setWriteState(writeState);
		transition.setWriteSymbol(writeSymbol);
		transition.setMoveDirection(moveDirection);
				
		// check if the transition is in the transition set
		// add the transition if it's not in the transition set
		if(getTransition(transition.getReadState(), transition.getReadSymbol()) == null) {
			// add the transition to the transition set 
			this.transitionSet.add(transition);
			if(DEBUG) {
				System.out.println("Transition (" + readState + ", " + readSymbol + ", " + writeState + ", " + writeSymbol + ", " + moveDirection + ") added.\n");
			}
		} else {
			if(DEBUG) {
				System.out.println("Transition (" + readState + ", " + readSymbol + ", " + writeState + ", " + writeSymbol + ", " + moveDirection + ") skipped because is already in the set.\n");
			}
			// the transition is already in the transition set 
			return false;
		} 		
		
		// the transition was added to the transition set
		return true;
	}
	
	/**
	 * This method returns the current transition in the transition table
	 * 
	 * @param readState the string value of the current state 
	 * @param readSymbol the string value of the current symbol to read
	 * @return the current transition
	 */
	public Transition getTransition(String readState, char readSymbol) {
		// iterate the transition set until the current transition is found
		Iterator<Transition> iterator = this.transitionSet.iterator();			
		while(iterator.hasNext()) {
			Transition nextTransition = iterator.next();

			// look in the transition set for transition with the specified readState and readSymbol
			if(nextTransition.getReadState().equals(readState) && nextTransition.getReadSymbol() == readSymbol) {
				return nextTransition;
			}
		}

		// when no transition with the specified readState and readSymbol is found
		// this occurs when we arrive at a halting state
		return null;
	}
	
	/**
	 * This method returns the next transition in the transition table
	 * 
	 * @param writeState the string value of the next state 
	 * @param nextIndex the index of the next transition
	 * @param direction of the current transition
	 * @return the next transition 
	 */
	public Transition getNextTransition(String writeState, int nextIndex, int direction) {
		// check that we don't get an out of bounds exception
		if(nextIndex < this.tape.length - 1 && direction > 0) {
			nextIndex++;
		} else if(nextIndex > 0 && direction < 0) {
			nextIndex--;
		}

		// iterate the transition set until the next transition is found
		Iterator<Transition> iterator = this.transitionSet.iterator();			
		while(iterator.hasNext()) {
			Transition nextTransition = iterator.next();

			// look in the transition set for transition with the specified writeState and nextIndex
			if(nextTransition.getReadState().equals(writeState) && nextTransition.getReadSymbol() == this.tape[nextIndex]) {
				// return the next transition
				return nextTransition;
			}
		}

		// when no next transition is found
		// this occurs when we arrive at a halting state
		return null;
	}
	
	/**
	 * 
	 * @return the state set of the Turing Machine
	 */
	public Set<String> getStateSet() {
		return this.stateSet;
	}

	/**
	 * 
	 * @return the transition set of the Turing Machine
	 */
	public Set<Transition> getTransitionSet() {
		return this.transitionSet;
	}
	
	/**
	 * 
	 * @return a string value of the transition set of the Turing Machine
	 */
	public String transitionsToString() {
		String result = "[";
		int index = 0;
		
		for(Transition t: this.getTransitionSet()) {
			result += "(" + t.getReadState() + ", " + t.getReadSymbol() + ", " + t.getWriteState() + ", " + t.getWriteSymbol() + ", " + t.getMoveDirection() + ")";
			index++;
			
			if(index % 8 == 0) {
				result += "\n";
			}
		}
		
		return result + "]";
	}

}
