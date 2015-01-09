package main_pkg;

/**
 * Trida s konstruktorem a metodami pro ulozeni parametru konecneho automatu.
 * @author Vlada47
 *
 */
public class Automaton {
	
	/**
	 * Konstanta symbolizujici prazdny retezec.
	 */
	public final String EMPTY_STRING = "$";
	
	/**
	 * Zkratka typu automatu.
	 */
	private String automatonType;
	
	/**
	 * Pocet stavu automatu.
	 */
	private int statusCnt;
	
	/**
	 * Pocet prvku mnoziny vstupu.
	 */
	private int inputCnt;
	
	/**
	 * Prechodova tabulka automatu.
	 */
	private String[][] automatonTable;
	
	/**
	 * Pocet vstupnich stavu automatu.
	 */
	private int inputStatusCnt;
	
	/**
	 * Pocet vystupnich stavu automatu.
	 */
	private int outputStatusCnt;
	
	/**
	 * Pole se vstupnimi stavy.
	 */
	private String[] inputStatusArray;
	
	/**
	 * Pole s vystupnimi stavy.
	 */
	private String[] outputStatusArray;
	
	/**
	 * Konstruktor objektu automatu.
	 * @param automatonType - zkratka popisujici typ automatu.
	 * @param statusCnt - pocet stavu automatu.
	 * @param inputCnt - pocet prvku mnoziny vstupu.
	 */
	public Automaton(String automatonType, int statusCnt, int inputCnt) {
		this.automatonType = automatonType;
		this.statusCnt = statusCnt;
		this.inputCnt = inputCnt;
	}
	
	public void setAutomatonTable(String[][] table) {
		this.automatonTable = table;
	}
	
	public void setInputStatus(int cnt, String[] statusArray) {
		this.inputStatusCnt = cnt;
		this.inputStatusArray = statusArray;
	}
	
	public void setOutputStatus(int cnt, String[] statusArray) {
		this.outputStatusCnt = cnt;
		this.outputStatusArray = statusArray;
	}

	/**
	 * @return the automatonType
	 */
	public String getAutomatonType() {
		return automatonType;
	}

	/**
	 * @return the statusCnt
	 */
	public int getStatusCnt() {
		return statusCnt;
	}

	/**
	 * @return the inputCnt
	 */
	public int getInputCnt() {
		return inputCnt;
	}

	/**
	 * @return the automatonTable
	 */
	public String[][] getAutomatonTable() {
		return automatonTable;
	}

	/**
	 * @return the inputStatusCnt
	 */
	public int getInputStatusCnt() {
		return inputStatusCnt;
	}

	/**
	 * @return the outputStatusCnt
	 */
	public int getOutputStatusCnt() {
		return outputStatusCnt;
	}

	/**
	 * @return the inputStatusArray
	 */
	public String[] getInputStatusArray() {
		return inputStatusArray;
	}

	/**
	 * @return the outputStatusArray
	 */
	public String[] getOutputStatusArray() {
		return outputStatusArray;
	}
}
