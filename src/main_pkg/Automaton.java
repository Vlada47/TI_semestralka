package main_pkg;

import java.util.ArrayList;

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
	 * Pole se vstupnimi stavy.
	 */
	private ArrayList<String> inputStatuses;
	
	/**
	 * Pole s vystupnimi stavy.
	 */
	private ArrayList<String> outputStatuses;
	
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
	
	public void setInputStatuses(ArrayList<String> statusArray) {
		this.inputStatuses = statusArray;
	}
	
	public void setOutputStatuses(ArrayList<String> statusArray) {
		this.outputStatuses = statusArray;
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
	 * @return the inputStatusArray
	 */
	public ArrayList<String> getInputStatusArray() {
		return inputStatuses;
	}

	/**
	 * @return the outputStatusArray
	 */
	public ArrayList<String> getOutputStatusArray() {
		return outputStatuses;
	}
}
