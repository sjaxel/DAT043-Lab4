package gateSim;

public class AndGate extends BasicGate {
	@Override
	public boolean calculateValue() {
		if (getInputGates().size() > 1) {
			for (Gate gate : getInputGates()) {
				if (!gate.getOutputValue()) {
					return false;
				}
			}
			return true;
		} else {
			String message = "Gate: " + getName() + 
					" - Incorrect number of input signals. Number is: " + getInputGates().size();
			throw new GateException(message);
		}
	}
}
