package gateSim;
import java.util.*;

public abstract class Gate {
	private String name;
	private boolean outputValue;
	private static int delay;
	private List<Gate> outputGates;
	private List<Gate> inputGates;
	/**
	 * Calculates output value 
	 */
	public void init(){
		outputChanged(calculateValue());
	}
	/**
	 * Get the name of the gate
	 * @return A string containing the gate name.
	 */
	
	public String getName(){
		return name;
	}
	
	/**
	 * Set the delay of the Gate.
	 * @param delay The delay in milliseconds.
	 */
	public static void setDelay(int d){
		delay = d;
	}
	
	/**
	 * Get the delay of the Gate.
	 * @return The delay in milliseconds.
	 */
	public static int getDelay(){
		return delay;
	}
	/**
	 * Sets the name of the gate.
	 * @param name A string with the name you want to set.
	 */
	public void setName(String name){
		this.name = name;			
	}
	/**
	 * Gets the output value of the gate.
	 * @return The output value of the gate.
	 */
	public boolean getOutputValue(){
		return outputValue;
	}
	/**
	 * Sets the input gates of the gate.
	 * @param gateList A list of Gate.
	 */
	public void setInputGate(Gate gate) {
		if (inputGates == null) {
			inputGates = new ArrayList<Gate>();
			inputGates.add(gate);
		} else {
			inputGates.add(gate);
		}
	}
	
	/**
	 * Gets the connected inputgates
	 * @return A list of input gates.
	 */
	public List<Gate> getInputGate(){
		if (inputGates == null) {
			inputGates = new ArrayList<Gate>();
			return inputGates;
		} else {
			return inputGates;
		}
	}
	
	/**
	 * Calculates the output value of the Gate.
	 * @return The output value of the Gate.
	 */
	public abstract boolean calculateValue();
	
	/**
	 * Tells the Gate that the input has changed.
	 */
	public abstract void inputChanged();
	
	/**
	 * Tells the gate that the output has changed and sets the new
	 * output value.
	 * @param output The new output value of the Gate.
	 */
	protected void outputChanged(boolean output){
		outputValue = output;
	}
}
