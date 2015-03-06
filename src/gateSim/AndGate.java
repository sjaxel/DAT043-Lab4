package gateSim;
import java.util.List;

public class AndGate extends BasicGate {
	@Override
	public boolean calculateValue() {
			if (getInputGate().size() > 1) {
				for (Gate gate : getInputGate()) {
					if (!gate.getOutputValue()) {
						return false;
					}
				}
				return true;
			} else {
				String message = "Gate: " + getName() + 
						" - Incorrect number of input signals. Number is: " + getInputGate().size();
				throw new GateException(message);
			}
	}

}
