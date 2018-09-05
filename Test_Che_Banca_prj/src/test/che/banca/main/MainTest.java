package test.che.banca.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import test.che.banca.exceptions.OrarioExceptions;
import test.che.banca.obj.Orario;
import test.che.banca.obj.Tabellone;

public class MainTest {

	private static Logger logger = Logger.getLogger("MainTest");

	private static String MENU = "[1] Stampa tabellone;\n[2] Aggiungi treno;\n[3] Elimina primo orario;\n[4] Modifica orario treno;\n[Q] Esci\n\nScelta: ";

	private static String QUIT = "Q";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));

		Tabellone tab = null;
		String scelta = QUIT;
		do {
			System.out.println(MENU);
			try {
				scelta = buffRead.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			switch (scelta) {
			case "1":
				if (tab != null) {
					tab.stampaTabellone();
				} else {
					System.out.println("Nessun tabellone disponibile, aggiungere prima un nuovo orario");
				}
				break;
			case "2":
				try {
					System.out.println("Treno n: ");
					String sceltaTreno = buffRead.readLine();

					boolean orarioCorretto = false;
					Integer sceltaOrarioInt = null;
					while (!orarioCorretto) {
						System.out.println("Orario: ");
						String sceltaOrario = buffRead.readLine();
						try {
							sceltaOrarioInt = Integer.parseInt(sceltaOrario);
							orarioCorretto = true;
						} catch (NumberFormatException ex) {
							System.out.println("L'orario inserito non è corretto, riprovare");
						}
					}

					System.out.println("Destinazione: ");
					String sceltaDestinazione = buffRead.readLine();

					Orario nuovoOrario = new Orario(sceltaTreno, sceltaOrarioInt, sceltaDestinazione);
					if (tab == null) {
						tab = new Tabellone(nuovoOrario);
					} else {
						tab.aggiungiOrario(nuovoOrario);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "3":
				if (tab != null) {
					tab.eliminaPrimoOrario();
				} else {
					System.out.println("Nessun tabellone disponibile, aggiungere prima un nuovo orario\n\n");
				}
				break;
			case "4":
				try {
					System.out.println("Treno n: ");
					String sceltaTreno = buffRead.readLine();

					boolean orarioCorretto = false;
					Integer sceltaOrarioInt = null;
					while (!orarioCorretto) {
						System.out.println("Orario: ");
						String sceltaOrario = buffRead.readLine();
						try {
							sceltaOrarioInt = Integer.parseInt(sceltaOrario);
							orarioCorretto = true;
						} catch (NumberFormatException ex) {
							System.out.println("L'orario inserito non è corretto, riprovare");
						}
					}

					tab.modificaOrarioTreno(sceltaTreno, sceltaOrarioInt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OrarioExceptions e) {
					logger.severe(e.getMessage());
				}
				break;
			default:
				continue;
			}
		} while (!QUIT.equalsIgnoreCase(scelta));
	}
}
