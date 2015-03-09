package gateSim;
import java.util.*;
import java.io.*;

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
	 * @param d The delay in milliseconds.
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
	 * @param gate The gate to add as input.
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
	
	/**
	 * createGates is a method that reads a custom file and "creates" the gates.
	 * @param file The file to parse from.
	 * @return LinkedHashMap with a gatenames as keys and their corresponding gates as values.
	 */
	public static Map<String, Gate> createGates(File file){
		Map<String, Gate> gateMap = new LinkedHashMap<String, Gate>();
		BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(new FileReader(file));
			lineReader(fileReader, gateMap, "gate");
			fileReader = new BufferedReader(new FileReader(file));
			lineReader(fileReader, gateMap, "logic");
		} catch (IOException e) {
			throw new GateException("createGates: Cannot read file");
		}
		return gateMap;
	}
	
	/**
	 * Reads each line in the file and sends them to the correct parsermethod.
	 * @param fileReader BufferedReader with the text to parse.
	 * @param gateMap The LinkedHashMap to populate.
	 * @param mode Is a string that decides which parser to use.
	 */	
	private static void lineReader(BufferedReader fileReader, 
				Map<String, Gate> gateMap, String mode){
		try {
			String line = fileReader.readLine();
			while (line != null) {
				if (mode == "gate") {
					gateParse(gateMap, line);
					line = fileReader.readLine();
				} else if (mode == "logic") {
					logicParse(gateMap, line);
					line = fileReader.readLine();
				}
			}
			fileReader.close();
		} catch (IOException e) {
			throw new GateException("lineReader: Can't read line");
		}
		}
	
	/**
	 * Parser method for creating gates.
	 * <p>
	 * Checks if the line is a comment or not. If not, it parses the input
	 * at white spaces and adds the name and created gate to the Map. 
	 * @param gateMap The LinkedHashMap to populate.
	 * @param line A string containing a single line to parse.
	 */
	private static void gateParse(Map<String, Gate> gateMap, String line) {
		String[] tokens = line.split("\\s+");
		if (!tokens[0].matches("\\\\.|\\*.")) {
			try {
				String gateName;
				String className;
				Gate gate;
				if (gateMap.containsKey(tokens[0])) {
					throw new GateException("Duplicate gate names");
				} else {
					gateName = tokens[0];
				}
				className = tokens[1].toLowerCase();
				className = Character.toUpperCase(className.charAt(0)) 
						+ className.substring(1) + "Gate";
				gate = (Gate)Class.forName("gateSim." + className).newInstance();
				gate.setName(gateName);
				gateMap.put(gateName, gate);
			} catch (InstantiationException
					| IllegalAccessException | ClassNotFoundException e) {
				throw new GateException("gateParse(): Class creation error");
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
	}
	
	/**
	 * Parses the logic of the gate system and adds the correct output
	 * gates to the gates in the gateMap
	 * @param gateMap The gateMap containing the gates to update.
	 * @param line A single line containing the text to parse.
	 */
	private static void logicParse(Map<String, Gate> gateMap, String line) {
		String[] tokens = line.split("\\s+");
		if (!tokens[0].matches("\\\\.|\\*.")) {
			Gate gate = gateMap.get(tokens[0]);
			for (int i = 2; tokens.length > i; i++) {			
				gate.setInputGate(gateMap.get(tokens[i]));	
			}
		}
	}


}
