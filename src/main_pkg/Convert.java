package main_pkg;

import java.util.ArrayList;
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
	 * DKA automat
	 */
	private static Automaton dka;

	/**
	 * Prechodova tabulka NKA
	 */
	private static String[][] nkaTable;

	/**
	 * Tabulka e-nasledniku
	 */
	private static String[] eTable;

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

		eTable = new String[nkaTable.length];
		for (int i = 0; i < eTable.length; i++) {
			eTable[i] = (char) ('A' + i) + "";
		}
		createETable(nkaTable, nka.getInputCnt(), eTable);

		dkaTable = new String[(int) Math.pow(2, nkaTable.length)][nka
				.getInputCnt()];
		for (int i = 0; i < dkaTable.length; i++) {
			for (int j = 0; j < dkaTable[i].length; j++) {
				dkaTable[i][j] = "";
			}
		}

		// Prvni stav bude sjednoceni vstupnich stavu
		String s = "";
		for (int i = 0; i < nka.getInputStatusArray().size(); i++) {
			s += nka.getInputStatusArray().get(i);
		}
		s = sortString(s);
		// Pridam prvnimu stavu e-nasledniky
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < eTable[s.charAt(i) - 'A'].length(); j++) {
				if (notUsed(s, eTable[s.charAt(i) - 'A'].charAt(j))) {
					s += eTable[s.charAt(i) - 'A'].charAt(j);
				}
			}
		}
		statuses.add(sortString(s));

		// Pro kazdou radku se pro kazdy sloupec zjisti novy stav a pripadne
		// prida do seznamu
		int count = 0;
		// Dokud nejsem na konci seznamu, tzn. uz nemam nove stavy
		while (count < statuses.size() && statuses.size() <= 26) {
			s = statuses.get(count);
			// Pro kazdy sloupec v radku
			for (int i = 0; i < dkaTable[count].length; i++) {
				// Pro kazdy puvodni stav z noveho stavu
				for (int j = 0; j < s.length(); j++) {
					String status = nkaTable[s.charAt(j) - 'A'][i];
					// Kontroluji, jestli jiz stav neni v tomto sloupci
					if (status.charAt(0) == '-') {
						continue;
					}
					for (int k = 0; k < status.length(); k++) {
						if (notUsed(dkaTable[count][i], status.charAt(k))) {
							dkaTable[count][i] += status.charAt(k);
						}
					}
				}
				// Pridam do sloupce e-nasledniky
				for (int j = 0; j < dkaTable[count][i].length(); j++) {
					for (int k = 0; k < eTable[dkaTable[count][i].charAt(j) - 'A']
							.length(); k++) {
						if (notUsed(dkaTable[count][i],
								eTable[dkaTable[count][i].charAt(j) - 'A']
										.charAt(k))) {
							dkaTable[count][i] += eTable[dkaTable[count][i]
									.charAt(j) - 'A'].charAt(k);
						}
					}
				}
				// Nove vyplneny sloupec seradim podle abecedy
				dkaTable[count][i] = sortString(dkaTable[count][i]);
				// Pokud stav ze sloupce jeste neni v seznamu, pridam ho
				if (notUsed(dkaTable[count][i])
						&& !dkaTable[count][i].equals("")) {
					statuses.add(dkaTable[count][i]);
				}
			}
			count++;
		}

		if (statuses.size() > 26) {
			System.out
					.println("Takto velky vstupni NKA neni podporovany. Vznikly DKA ma vice nez 26 (A-Z) stavu");
			return null;
		}
		dka = new Automaton("DKAR", statuses.size(), nka.getInputCnt());
		setOuputStatusesToDka(nka, dka, statuses);
		renameStatuses(dkaTable, statuses);
		dka.setAutomatonTable(dkaTable);
		ArrayList<String> inputStatus = new ArrayList<String>();
		inputStatus.add("A");
		dka.setInputStatuses(inputStatus);

		return dka;
	}

	/**
	 * Prohleda stavy deterministickeho automatu a urci na zaklade informaci z
	 * nedeterministickeho, ktere budou vystupni. Zaroven je pred ulozenim do
	 * automatu prepise podle pozadavku na vystup (A...Z).
	 * 
	 * @param nka
	 *            - nedeterministicky automat
	 * @param dka
	 *            - deterministicky automat
	 * @param statuses
	 *            - stavy deterministickeho automatu
	 */
	private static void setOuputStatusesToDka(Automaton nka, Automaton dka,
			LinkedList<String> statuses) {
		ArrayList<String> outputStatusArrayNKA = nka.getOutputStatusArray();
		ArrayList<String> outputStatusesDKA = new ArrayList<String>();

		for (int i = 0; i < statuses.size(); i++) {
			String status = statuses.get(i);
			for (int j = 0; j < outputStatusArrayNKA.size(); j++) {
				if (status.contains(outputStatusArrayNKA.get(j))) {
					char s = (char) ('A' + i);
					outputStatusesDKA.add(s + "");
					break;
				}
			}
		}

		dka.setOutputStatuses(outputStatusesDKA);
	}

	/**
	 * Prepise nazvy (Stringy) stavu v prechodove tabulce deterministickeho
	 * automatu podle oznaceni vyzadovaneho vystupem (A...Z).
	 * 
	 * @param dkaTable
	 *            - prechodova tabulka deterministickeho automatu
	 * @param statuses
	 *            - stavy deterministickeho automatu
	 */
	private static void renameStatuses(String[][] dkaTable,
			LinkedList<String> statuses) {
		for (int i = 0; i < statuses.size(); i++) {
			for (int j = 0; j < dkaTable[i].length; j++) {
				String tableStatus = dkaTable[i][j];

				for (int k = 0; k < statuses.size(); k++) {
					String listStatus = statuses.get(k);

					if (tableStatus.equals(listStatus)) {
						char s = (char) ('A' + k);
						dkaTable[i][j] = s + "";
					}
				}
			}
		}
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
	 * 
	 * @param s
	 *            String, ve kterem hledam
	 * @param c
	 *            Znak, ktery hledam
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

	/**
	 * Vytvori tabulku e-nasledniku
	 * 
	 * @param nkaTable
	 *            Prechodova tabulka NKA
	 * @param inputCount
	 *            Pocet prvku mnoziny vstupu
	 * @param eTable
	 *            Tabulka e-nasledniku
	 */
	private static void createETable(String[][] nkaTable, int inputCount,
			String[] eTable) {
		for (int i = 0; i < nkaTable.length; i++) {
			String e = "";
			int j = i;
			int k = 0;
			while (nkaTable[j].length > inputCount) {
				String s = nkaTable[j][inputCount];
				for (int l = 0; l < s.length(); l++) {
					if (notUsed(e, s.charAt(l))) {
						e += s.charAt(l);
					}
				}
				if (k < e.length()) {
					j = e.charAt(k) - 'A';
					k++;
				} else
					break;
			}
			eTable[i] += sortString(e);
		}
	}
}
