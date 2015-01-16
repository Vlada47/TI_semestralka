package main_pkg;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Trida pro prevod NKA na DKA
 * 
 * @author jklaus
 *
 */
public class Convert {
	/**
	 * NKA a DKA automaty
	 */
	private static Automaton nka, dka;

	/**
	 * Prechodova tabulka NKA
	 */
	private static String[][] nkaTable;

	/**
	 * Prechodova tabulka DKA. Ma nejvice 2^N stavu, kde N je pocet stavu NKA
	 */
	private static String[][] dkaTable;

	/**
	 * Seznam stavu, ktere budou tvorit stavy DKA
	 */
	private static LinkedList<String> statuses = new LinkedList<String>();

	/****************************************************************************************************************************************************/

	/**
	 * Metoda, ktera prevede NKA na DKA
	 * 
	 * @param nka
	 *            NKA, ktery chceme prevest na DKA
	 * @return DKA
	 */
	public static Automaton nkaToDka(Automaton nka) {
		nkaTable = nka.getAutomatonTable();
		dkaTable = new String[(int) Math.pow(2, nkaTable.length)][nka
				.getInputCnt()];
		for (int i = 0; i < dkaTable.length; i++) {
			for (int j = 0; j < dkaTable[i].length; j++) {
				dkaTable[i][j] = "";
			}
		}

		// Prvni radka bude stejna jako v NKA
		statuses.add("A");
		for (int i = 0; i < nkaTable[0].length; i++) {
			if (notUsed(sortString(nkaTable[0][i]))) {
				statuses.add(sortString(nkaTable[0][i]));
			}
		}

		//Pro kazdou radku se pro kazdy sloupec zjisti novy stav a pripadne prida do seznamu
		int count = 0;
		//Dokud nejsem na konci seznamu, tzn. uz nemam nove stavy
		while (count < statuses.size()) {
			String s = statuses.get(count);
			//Pro kazdy sloupec v radku
			for (int i = 0; i < dkaTable[count].length; i++) {
				//Pro kazdy puvodni stav z noveho stavu
				for (int j = 0; j < s.length(); j++) {
					String status = nkaTable[s.charAt(j) - 'A'][i];
					//Kontroluji, jestli jiz stav neni v tomto sloupci
					for(int k = 0; k < status.length(); k++) {
						if(notUsed(dkaTable[count][i], status.charAt(k))) {
							dkaTable[count][i] += status.charAt(k);
						}
					}
				}
				//Nove vyplneny sloupec seradim podle abecedy
				dkaTable[count][i] = sortString(dkaTable[count][i]);
				//Pokud stav ze sloupce jeste neni v seznamu, pridam ho
				if(notUsed(dkaTable[count][i]) && !dkaTable[count][i].equals("")) {
					statuses.add(dkaTable[count][i]);
				}
			}
			count++;
		}

		//TODO upravit tabulku, aby nemela prazdne radky (2^pocetStavu) a vytvorit DKA s atributy...
		return dka;
	}

	/**
	 * Projde seznam stavu a zjistí, zda je nový stav v seznamu
	 * 
	 * @param status
	 *            Novy stav
	 * @return true pokud není v seznamu, false pokud v nem je
	 */
	private static boolean notUsed(String status) {
		for (int i = 0; i < statuses.size(); i++) {
			if (status.equals(statuses.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Zjisti, zda string obsahuje znak
	 * @param s String, ve kterem hledam
	 * @param c Znak, ktery hledam
	 * @return True pokud neobsahuje, false pokud ano
	 */
	private static boolean notUsed(String s, char c) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Seradi stavy podle abecedy
	 * 
	 * @param s
	 *            String stavu
	 * @return Serazeny string stavu
	 */
	private static String sortString(String s) {
		char[] array = s.toCharArray();
		Arrays.sort(array);
		s = new String(array);
		return s;
	}
}
