/**
 * 
 */
package dtm;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author crist
 *
 */
public class TuringMachine {

	private static final boolean DEBUG = false;
	private static final boolean PRINT_TRACE = false;
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
	
	private class Transition {
		public char readSymbol;
		public char writeSymbol;
		public String readState;
		public String writeState;
		public int moveDirection;
		
		public Transition() {}

		public char getReadSymbol() {
			return readSymbol;
		}

		public void setReadSymbol(char readSymbol) {
			this.readSymbol = readSymbol;
		}

		public char getWriteSymbol() {
			return writeSymbol;
		}

		public void setWriteSymbol(char writeSymbol) {
			this.writeSymbol = writeSymbol;
		}

		public String getReadState() {
			return readState;
		}

		public void setReadState(String readState) {
			this.readState = readState;
		}

		public String getWriteState() {
			return writeState;
		}

		public void setWriteState(String writeState) {
			this.writeState = writeState;
		}

		public int getMoveDirection() {
			return moveDirection;
		}

		public void setMoveDirection(int moveDirection) {
			this.moveDirection = moveDirection;
		}
		
		public String transitionToString() {			
			return "(" + this.getReadState() + ", " + this.getReadSymbol() + ", " + this.getWriteState() + ", " + this.getWriteSymbol() + ", " + this.getMoveDirection() + ")";
		}
	}

	public boolean run(String input, PrintWriter pw) {
		this.currentState = this.startState;	
		this.tape = input.toCharArray();
		
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
		
		//at the start state q0, skip all blank symbols to start the TM at a non-blank symbol 
		while(this.currentState.equals(this.startState) && this.tape[this.symbolIndex] == BLANK && this.symbolIndex < this.tape.length) {
			this.symbolIndex++;
		}
		
		while(!this.currentState.equals(this.acceptState) && !this.currentState.equals(this.rejectState)) { 			
			Transition currentTransition = getTransition(this.currentState, this.tape[this.symbolIndex]);	
			//used for trace runs only
			Transition tempTransition = currentTransition;
			int tempSymbolIndex = this.symbolIndex;
			
			//This transition is not in the transition set.
			if(currentTransition == null) {
				this.currentState = this.rejectState;
				return false;
			} 

			//change to next state
			this.currentState = currentTransition.getWriteState();			
			//write symbol 
			this.tape[this.symbolIndex] = currentTransition.getWriteSymbol();

			//move to left, move to write, or don't move 
			if(currentTransition.getMoveDirection() == -1) {
				this.symbolIndex--;
			} else if(currentTransition.getMoveDirection() == 1) {
				this.symbolIndex++;
			}

			//add more space in the 'infinite' tape and calculate the new symbolIndex in the new array			
			if(this.symbolIndex < 0) {
				//grow to the left
				expandTapeLeft();
			} else if(this.symbolIndex > this.tape.length - 1) {
				//grow to the right
				expandTapeRight();
			}
			
			if(PRINT_TRACE) {			
				Transition nextTransition = getNextTransition(tempTransition.getWriteState(), tempSymbolIndex, tempTransition.getMoveDirection());
				
				if(pw == null) {
					System.out.print("Tape: [" + String.valueOf(this.tape) + "]\tindex: " + tempSymbolIndex + "\tCurrent Transition: " + tempTransition.transitionToString() + "\t");				
					
					//there are no more states. The current state is the one of the halting states.
					if(nextTransition == null) {
						System.out.println("\tHalting state: " + tempTransition.getWriteState() + "\n");
						System.out.println("Tape output: " + String.valueOf(this.tape));
						if(tempTransition.getWriteState().equals(this.rejectState) && (tempTransition.getReadState().equals("q10") && tempTransition.getReadSymbol() == BLANK ||  
								tempTransition.getReadState().equals("q6") && tempTransition.getReadSymbol() == BLANK)) {
							System.out.println("\nThe results is a negative number. This TM doesn't handle signed binaries.");
						}						
					} else {
						System.out.println("\tNext Transition: " + nextTransition.transitionToString());
					}
				} else {
					pw.print("Tape: [" + String.valueOf(this.tape) + "]\tindex: " + tempSymbolIndex + "\tCurrent Transition: " + tempTransition.transitionToString() + "	");				
					
					//there are no more states. The current state is the one of the halting states.
					if(nextTransition == null) {
						pw.println("	Halting state: " + tempTransition.getWriteState());
						pw.println();
						pw.println("Tape output: " + String.valueOf(this.tape));
						if(tempTransition.getWriteState().equals(this.rejectState) && (tempTransition.getReadState().equals("q10") && tempTransition.getReadSymbol() == BLANK ||  
								tempTransition.getReadState().equals("q6") && tempTransition.getReadSymbol() == BLANK)) {
							pw.println();
							pw.println("The results is a negative number. This TM doesn't handle signed binaries.");
						}
					} else {
						pw.println("	Next Transition: " + nextTransition.transitionToString());
					}
				}				
			}
		}
		
		return this.currentState.equals(this.acceptState);
	}
	
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

	public boolean addState(String state) {
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
	
	public void setStartState(String q0) {
		if(!this.stateSet.contains(q0)) {
			addState(q0);
		} 
		
		this.startState = q0;	
		
		if(DEBUG) {
			System.out.println("Start state " + this.startState + " set.\n");
		}
	}

	public void setAcceptState(String qy) {
		if(!this.stateSet.contains(qy)) {
			addState(qy);
		} 
		
		this.acceptState = qy;	
		if(DEBUG) {
			System.out.println("Accept state " + this.acceptState + " set.\n");
		}
	}
	
	public void setRejectState(String qn) {
		if(!this.stateSet.contains(qn)) {
			addState(qn);	
		} 		
		
		this.rejectState = qn;	
		if(DEBUG) {
			System.out.println("Reject state " + this.rejectState + " set.\n");
		}
	}
	
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
		
		if(!this.stateSet.contains(readState) || !this.stateSet.contains(writeState) || (moveDirection < -1 || moveDirection > 1)) {
			if(DEBUG) {
				System.out.println("Transition (" + readState + ", " + readSymbol + ", " + writeState + ", " + writeSymbol + ", " + moveDirection + ") skipped because of invalid state or move direction.\n");			
			}			
			return false;
		}
			
		Transition transition = new Transition();
		transition.setReadState(readState);
		transition.setReadSymbol(readSymbol);
		transition.setWriteState(writeState);
		transition.setWriteSymbol(writeSymbol);
		transition.setMoveDirection(moveDirection);
				
		//Check that the transition is in the transition set
		if(getTransition(transition.getReadState(), transition.getReadSymbol()) == null) {
			this.transitionSet.add(transition);
			if(DEBUG) {
				System.out.println("Transition (" + readState + ", " + readSymbol + ", " + writeState + ", " + writeSymbol + ", " + moveDirection + ") added.\n");
			}
		} else {
			if(DEBUG) {
				System.out.println("Transition (" + readState + ", " + readSymbol + ", " + writeState + ", " + writeSymbol + ", " + moveDirection + ") skipped because is already in the set.\n");
			}
			return false;
		} 		
		return true;
	}
	
	public Transition getTransition(String readState, char readSymbol) {
		//look in the transition set for transition with readState and readSymbol		
		Iterator<Transition> iterator = this.transitionSet.iterator();			
		while(iterator.hasNext()) {
			Transition nextTransition = iterator.next();

			if(nextTransition.getReadState().equals(readState) && nextTransition.getReadSymbol() == readSymbol) {
				return nextTransition;
			}
		}

		return null;
	}
	
	public Transition getNextTransition(String writeState, int nextIndex, int direction) {
		if(nextIndex < this.tape.length - 1 && direction > 0) {
			nextIndex++;
		} else if(nextIndex > 0 && direction < 0) {
			nextIndex--;
		}

		Iterator<Transition> iterator = this.transitionSet.iterator();			
		while(iterator.hasNext()) {
			Transition nextTransition = iterator.next();

			if(nextTransition.getReadState().equals(writeState) && nextTransition.getReadSymbol() == this.tape[nextIndex]) {
				return nextTransition;
			}
		}

		return null;
	}
		
	public Set<String> getStateSet() {
		return this.stateSet;
	}

	public Set<Transition> getTransitionSet() {
		return this.transitionSet;
	}
	
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
