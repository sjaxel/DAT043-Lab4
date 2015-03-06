package gateSim;
import java.util.*;

public abstract class Gate {
	private String name;
	private boolean outputValue;
	private static int delay;
	private List<Gate> outputGates;
	private List<Gate> inputGates;
	/**
	 * Calculates outputValue.
	 * <p>
	 * Evaluates the output of the gate. Should only be called after
	 * enough inputs have been connected.
	 */
	public void init(){
		outputChanged(calculateValue());
	}
	/**
	 * Get the name of the gate.
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
	 * <p>
	 * Method for getting the updated output value of the
	 * gate in it's current state.
	 * @return The output value of the gate.
	 */
	public boolean getOutputValue(){
		return outputValue;
	}
	/**
	 * Sets the input gates of the gate.
	 * <p>
	 * This adds the supplied gate to the input variables of
	 * the gate. It also adds this gate to the output list
	 * of the provided gate.
	 * @param gateList A list of Gate.
	 */
	public void setInputGate(Gate gate) {
		if (inputGates == null) {
			inputGates = new ArrayList<Gate>();
			inputGates.add(gate);
			gate.setOutputGate(this);
		} else {
			inputGates.add(gate);
			gate.setOutputGate(this);
		}
	}
	
	/**
	 * Gets the connected input Gates.
	 * <p>
	 * This provides a list of all the gates that provide
	 * input values for this gate.
	 * @return A list of input gates.
	 */
	public List<Gate> getInputGates(){
		if (inputGates == null) {
			inputGates = new ArrayList<Gate>();
			return inputGates;
		} else {
			return inputGates;
		}
	}
	
	/**
	 * Adds a gate to the outputGates list.
	 * <p>
	 * This method adds a gate to the list of gates that
	 * will be notified when this gate changes it's output.
	 * @param gate The gate to be added to outputGates.
	 */
	public void setOutputGate(Gate gate){
		if (outputGates == null) {
			outputGates = new ArrayList<Gate>();
			outputGates.add(gate);
		} else {
			outputGates.add(gate);
		}
	}
	
	/**
	 * Calculated the output value of the gate from the input it receives.
	 * <p>
	 * This function contains the logic of the Gate. It returns a boolean
	 * depending on the input values of the Gates in the inputGates list.
	 * calculateValue doesn't update the value of the gate, as this is
	 * handled elsewhere.
	 * @throws GateException
	 * @return boolean The the output state of the gate.
	 */
	public abstract boolean calculateValue();
	
	/**
	 * Tells the Gate that the input has changed.
	 */
	public abstract void inputChanged();
	
	/**
	 * Tells the gate that the output has changed and sets the new
	 * output value. Notifies all connected gates that it's output
	 * has changed.
	 * <p>
	 * This method is responsible for notifying the dependent Gates
	 * that the current Gate has changed it's outputValue and they
	 * need to update theirs.
	 * @param output The new output value of the Gate.
	 */
	protected void outputChanged(boolean output){
		outputValue = output;
		if (outputGates != null) {
			for (Gate gate : outputGates) {
				gate.inputChanged();
			}
		}
	}
}
