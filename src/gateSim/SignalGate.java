package gateSim;

public class SignalGate extends Gate {

	/**
	 * Sets the output value
	 * @param value Sets the output value you want to set.
	 */
	public void setValue(boolean value){
		outputChanged(value);
		
	}

	@Override
	public boolean calculateValue() {
		return getOutputValue();
	}
	
	/**
	 * SignalGate doesn't take any inputs.
	 * Throws new GateException when called.
	 */
	@Override
	public void setInputGate(Gate gate){
		String message = "Gate: " + getName() + 
				" - SignalGate, does not take input.";
		throw new GateException(message);
	}
	/**
	 * inputChanged doesn't take any inputs.
	 * Throws new GateException when called.
	 */
	@Override
	public void inputChanged() {
		String message = "Gate: " + getName() + 
				" - SignalGate, does not take input.";
		throw new GateException(message);

	}

}
