package main_pkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Input_Output {
	
	private static final String WHITE_SPACE = " ";
	
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
		a.setInputStatus(outputStatusCnt, outputStatusArray);
		
		br.close();
		
		return a;
	}

}
