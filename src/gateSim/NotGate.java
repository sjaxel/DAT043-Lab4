package gateSim;
import java.util.List;


public class NotGate extends BasicGate {
	
	/**
	 * Sets the input gates of the gate.
	 * @param gateList A list containing (1) Gate.
	 */
	@Override
	public void setInputGate(Gate gate) {
		if (getInputGates().size() == 0) {
			getInputGates().add(gate);
			gate.setOutputGate(this);
		} else {
			String message = "Gate: " + getName() + 
					" - Incorrect number of input signals. Number is: " + getInputGates().size();
			throw new GateException(message);
		}
	}

	@Override
	public boolean calculateValue() {
		return !(getInputGates().get(0).getOutputValue());
	}

}
