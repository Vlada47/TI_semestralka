package main_pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trida obsahujici metody pro nacteni NKA ze souboru a pro ulozeni parametru DKA do souboru ve specifickem formatu.
 * @author Vlada47
 *
 */
public class Input_Output {
	
	private static final String WHITE_SPACE = " ";
	
	/**
	 * Metoda pro nacteni informaci o automatu ze souboru ve specifickem formatu.
	 * Tyto informace jsou pak ulozeny do prislusneho objektu typu Automaton.
	 * @param filePath - cesta k souboru, odkud se maji informace ziskat
	 * @return vraci instanci tridy Automat predstavujici vytvoreny nedeterministicky automat
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static Automaton createAutomatonFromFile(String filePath) throws IOException, NumberFormatException {
		Automaton a;
		String fileLine;
		String[] lineArray;
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		
		fileLine = br.readLine();
		String automatonType = fileLine;
		
		fileLine = br.readLine();
		int statusCnt = Integer.parseInt(fileLine);
		
		fileLine = br.readLine();
		int inputCnt = Integer.parseInt(fileLine);
		
		a = new Automaton(automatonType, statusCnt, inputCnt);
		
		String[][] automatonTable = new String[statusCnt][inputCnt+1];
		for(int i = 0; i < statusCnt; i++) {
			fileLine = br.readLine();
			automatonTable[i] = fileLine.split(WHITE_SPACE);
		}
		a.setAutomatonTable(automatonTable);
		
		fileLine = br.readLine();
		lineArray = fileLine.split(WHITE_SPACE);
		int inputStatusCnt = Integer.parseInt(lineArray[0]);
		ArrayList<String> inputStatusArray = new ArrayList<String>();
		for(int i = 0; i < inputStatusCnt; i++) {
			inputStatusArray.add(lineArray[i+1]);
		}
		a.setInputStatuses(inputStatusArray);
		
		fileLine = br.readLine();
		lineArray = fileLine.split(WHITE_SPACE);
		int outputStatusCnt = Integer.parseInt(lineArray[0]);
		ArrayList<String> outputStatusArray = new ArrayList<String>();
		for(int i = 0; i < outputStatusCnt; i++) {
			outputStatusArray.add(lineArray[i+1]);
		}
		a.setOutputStatuses(outputStatusArray);
		
		br.close();
		
		return a;
	}
	
	/**
	 * Metoda pro zapsani parametru automatu ve specifickem formatu do souboru.
	 * @param a - instance tridy Automat predstavujici deterministicky automat, co se ma zapsat do souboru
	 * @param filepath - cesta k souboru, do ktereho se automat zapise 
	 * @throws IOException
	 */
	public static void writeAutomatonToFile(Automaton a, String filepath) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
		
		String[][] automatonTable = a.getAutomatonTable();
		
		bw.write(a.getAutomatonType()); bw.newLine();
		bw.write(a.getStatusCnt()+""); bw.newLine();
		bw.write(a.getInputCnt()+""); bw.newLine();
		
		String line = "";
		for(int i = 0; i < a.getStatusCnt(); i++) {
			line = "";
			for(int j = 0; j < automatonTable[i].length; j++) {
				line += automatonTable[i][j]+" ";
			}
			bw.write(line); bw.newLine();
		}
		
		line = "";
		ArrayList<String> inputStatus = a.getInputStatusArray();
		for(int i = 0; i < inputStatus.size(); i++) {
			line += inputStatus.get(i)+" ";
		}
		bw.write(line); bw.newLine();
		
		
		ArrayList<String> outputStatus = a.getOutputStatusArray();
		line = outputStatus.size()+" ";
		for(int i = 0; i < outputStatus.size(); i++) {
			line += outputStatus.get(i)+" ";
		}
		bw.write(line);
		
		bw.close();
	}

}
