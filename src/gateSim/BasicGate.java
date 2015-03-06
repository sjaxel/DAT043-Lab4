package gateSim;
public abstract class BasicGate extends Gate {
	
	/**
	 * Updates the input value of the Gate.
	 */
	@Override
	public void inputChanged() {
		outputChanged(calculateValue());
	}

}
