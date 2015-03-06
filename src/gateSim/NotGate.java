package gateSim;
import java.util.List;


public class NotGate extends BasicGate {
	
	/**
	 * Sets the input gates of the gate.
	 * @param gateList A list containing (1) Gate.
	 */
	@Override
	public void setInputGate(Gate gate) {
		if (getInputGate().size() == 0) {
			getInputGate().add(gate);
		} else {
			String message = "Gate: " + getName() + 
					" - Incorrect number of input signals. Number is: " + getInputGate().size();
			throw new GateException(message);
		}
	}

	@Override
	public boolean calculateValue() {
		return !(getInputGate().get(0).getOutputValue());
	}

}
