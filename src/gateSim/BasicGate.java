package gateSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public abstract class BasicGate extends Gate implements ActionListener {
	Timer timer = new Timer(getDelay(), this);
	boolean store;
	
	@Override
	public void init(){
		timer.setRepeats(false);
		outputChanged(calculateValue());
	}
	
	/**
	 * Updates the input value of the Gate.
	 */
	@Override
	public void inputChanged() {
		if (!timer.isRunning()) {
			if (calculateValue() != getOutputValue()) {
				store = calculateValue();
				timer.start();
			}
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		outputChanged(store);
	}

}
