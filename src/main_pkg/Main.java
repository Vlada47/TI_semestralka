package main_pkg;

import java.io.IOException;

public class Main {
	
	public static final String FILE_PATH = "data\\automat.txt";

	public static void main(String[] args) {
		
		try {
			Automaton a = Input_Output.createAutomatonFromFile(FILE_PATH);
			
			System.out.println(a.getAutomatonType());
			
			a = Convert.nkaToDka(a);
		}
		catch(IOException e) {
			System.err.println("Chyba pri cteni souboru: "+e.getMessage());
		}
		catch(NumberFormatException f) {
			System.err.println("Chyba pri prevadeni cisel ze Stringu na Integer: "+f.getMessage());
		}
	}
}
