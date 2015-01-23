package main_pkg;

import java.io.IOException;

/**
 * Hlavni trida programu. Resi spravny pocet vstupu a vyjimky vracene metodami
 * pro praci se soubory.
 * 
 * @author Vlada47
 *
 */
public class Main {
	
	/**
	 * Hlavni metoda programu, ktera postupne spusti metody pro vytvoreni nedeterministickeho automatu,
	 * jeho prevod na deterministicky a nasledny zapis vysledku do souboru.
	 * @param args - vstupni argumenty programu, zde jsou pouzity jako cesty k souborum pro nacteni a vypis automatu
	 */
	public static void main(String[] args) {

		if (args.length < 2) {
			System.err
					.println("Musite zadat dva parametry. Prvni parametr je cesta k souboru s NKA a druhy parametr cesta k souboru, kam se zapise DKA.");
		} else {
			String inputFilePath = args[0];
			String outputFilePath = args[1];

			try {
				Automaton a = Input_Output
						.createAutomatonFromFile(inputFilePath);
				System.out
						.println("Nedeterministicky konecny automat uspesne nacten ze souboru.");

				a = Convert.nkaToDka(a);
				if (a != null) {
					System.out
							.println("Prekonvertovani automatu na deterministicky probehlo uspesne.");

					Input_Output.writeAutomatonToFile(a, outputFilePath);
					System.out
							.println("Prekonvertovany automat uspesne ulozen do souboru.");
				}
			} catch (IOException e) {
				System.err
						.println("Chyba pri cteni souboru s NKA nebo zapisu do souboru s DKA: "
								+ e.getMessage());
			} catch (NumberFormatException f) {
				System.err
						.println("Chyba pri prevadeni cisel ze String na Integer: "
								+ f.getMessage());
			}
		}
	}
}
