package test.che.banca.obj;

import test.che.banca.exceptions.OrarioExceptions;

/**
 * 
 * @author Danilo
 *
 *         Esercizio 3
 * 
 */
public class Tabellone {

	private NodoOrario nodoOrario = null;

	/**
	 * punto 1: un costruttore che preso in ingresso un oggetto di tipo Orario lo
	 * inserisce come primo elemento della lista di orari memorizzati al suo interno
	 * 
	 */
	public Tabellone(Orario orario) {
		NodoOrario newNodo = new NodoOrario(orario);

		// verifico di non aver già istanziato altri nodi prima
		if (this.nodoOrario != null) {
			// se li ho istanziati, sposto tutti i nodi su quello appena creato
			newNodo.setNextNodo(this.nodoOrario);
		}

		this.nodoOrario = newNodo;
	}

	/**
	 * punto 2: un metodo che dato in ingresso una variabile di tipo Orario crea un
	 * nuovo nodo e lo inserisce in coda alla lista degli orari contenuti nel
	 * tabellone
	 * 
	 */
	public void aggiungiOrario(Orario orario) {
		NodoOrario scoda = this.nodoOrario;
		NodoOrario nodoAttuale = scoda;
		// ciclo su tutti i nodi fino a trovare l'ultimo (che non ha riferimenti a
		// nessun altro nodo interno)
		while (scoda != null) {
			nodoAttuale = scoda;
			scoda = scoda.getNextNodo();
		}
		scoda = new NodoOrario(orario);
		nodoAttuale.setNextNodo(scoda);
	}

	/**
	 * punto 3: un metodo che elimina il nodo relativo al primo orario memorizzato
	 * nella lista di orari
	 * 
	 */
	public void eliminaPrimoOrario() {
		this.nodoOrario = this.nodoOrario.getNextNodo();
	}

	/**
	 * punto 4: un metodo che stampa tutti gli orari contenuti nella lista
	 * memorizzata all'interno del tabellone tenendo conto dell'eventualita' che il
	 * tabellone possa essere vuoto
	 * 
	 */
	public void stampaTabellone() {
		if (this.nodoOrario != null) {
			NodoOrario scoda = this.nodoOrario;

			// ciclo su tutti i nodi fino a trovare l'ultimo (che non ha riferimenti a
			// nessun altro nodo interno)
			while (scoda != null) {
				System.out.println(scoda.getInfo().getDescrizioneOrario());
				scoda = scoda.getNextNodo();
			}
		} else {
			System.out.println("Non sono presenti orari sul tabellone");
		}
	}

	public void modificaOrarioTreno(String treno, Integer nuovoOrario) throws OrarioExceptions {
		if (this.nodoOrario != null) {
			NodoOrario scoda = this.nodoOrario;

			// ciclo su tutti i nodi fino a trovare l'ultimo (che non ha riferimenti a
			// nessun altro nodo interno)
			while (scoda != null && !treno.equals(scoda.getInfo().getTreno())) {
				scoda = scoda.getNextNodo();
			}

			if (scoda != null) {
				scoda.getInfo().setOrarioEffettivo(nuovoOrario);
			} else {
				System.out.println("Treno specificato non trovato");
			}
		} else {
			System.out.println("Non sono presenti orari sul tabellone");
		}
	}
}
