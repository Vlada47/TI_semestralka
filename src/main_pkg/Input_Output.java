package main_pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Input_Output {
	
	private static final String WHITE_SPACE = " ";
	
	/**
	 * Metoda pro nacteni informaci o automatu ze souboru ve specifickem formatu.
	 * Tyto informace jsou pak ulozeny do prislusneho objektu typu Automaton.
	 * @param filePath
	 * @return
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
		String[] inputStatusArray = new String[inputStatusCnt];
		for(int i = 0; i < inputStatusArray.length; i++) {
			inputStatusArray[i] = lineArray[i+1];
		}
		a.setInputStatus(inputStatusCnt, inputStatusArray);
		
		fileLine = br.readLine();
		lineArray = fileLine.split(WHITE_SPACE);
		int outputStatusCnt = Integer.parseInt(lineArray[0]);
		String[] outputStatusArray = new String[outputStatusCnt];
		for(int i = 0; i < outputStatusArray.length; i++) {
			outputStatusArray[i] = lineArray[i+1];
		}
		a.setOutputStatus(outputStatusCnt, outputStatusArray);
		
		br.close();
		
		return a;
	}
	
	/**
	 * Metoda pro zapsani parametru automatu ve specifickem formatu do souboru.
	 * @param a
	 * @param filepath
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
		String[] inputStatus = a.getInputStatusArray();
		for(int i = 0; i < a.getInputStatusCnt(); i++) {
			line += inputStatus[i]+" ";
		}
		bw.write(line); bw.newLine();
		
		line = a.getOutputStatusCnt()+" ";
		String[] outputStatus = a.getOutputStatusArray();
		for(int i = 0; i < a.getOutputStatusCnt(); i++) {
			line += outputStatus[i]+" ";
		}
		bw.write(line);
		
		bw.close();
	}

}
