package gateSim;
import java.util.List;

public class NandGate extends BasicGate {
	@Override
	public boolean calculateValue() {
		if (getInputGates().size() > 1) {
			for (Gate gate : getInputGates()) {
				if (!gate.getOutputValue()) {
					return true;
				}
			}
			return false;
		} else {
			String message = "Gate: " + getName() + 
					" - Incorrect number of input signals. Number is: " + getInputGates().size();
			throw new GateException(message);
		}
}

}