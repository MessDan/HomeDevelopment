package test.che.banca.obj;

import java.util.logging.Logger;

import test.che.banca.exceptions.OrarioExceptions;
import test.che.banca.utils.CommonConstants;

/**
 * 
 * @author Danilo
 * 
 *         Esercizio 1
 *
 */
public class Orario {

	private static Logger logger = Logger.getLogger("Orario");

	private String treno;
	private Integer orarioPrevisto;
	private Integer orarioEffettivo;
	private String destinazione;

	public Orario(String treno, Integer orarioPrevisto, String destinazione) {
		this.treno = treno;
		this.orarioPrevisto = orarioPrevisto;
		this.orarioEffettivo = orarioPrevisto; // si assume che siano inizialmente uguali
		this.destinazione = destinazione;
	}

	public String getTreno() {
		return treno;
	}

	/**
	 * punto 1: un metodo che restituisce la descrizione dell'orario
	 * 
	 */
	public String getDescrizioneOrario() {
		String retVal = new String();

		if (this.treno != null) {
			retVal = "Treno " + this.treno + CommonConstants.SPACE_SEPARATOR;
		}

		if (this.destinazione != null) {
			retVal += "per " + this.destinazione.toUpperCase() + CommonConstants.LINE_SEPARATOR;
		}

		if (this.orarioPrevisto != null) {
			retVal += "partenza ore: " + this.orarioPrevisto + CommonConstants.LINE_SEPARATOR;
		}

		if (this.orarioEffettivo != null) {
			retVal += "orario effettivo ore: " + this.orarioEffettivo;
		}

		Integer ritardo = ritardoTreno();
		if (ritardo > 0) {
			retVal += CommonConstants.LINE_SEPARATOR + "ritardo: " + ritardo + (ritardo > 1 ? " ore" : " ora");
		}

		return retVal + CommonConstants.SEMICOLON_SEPARATOR;
	}

	/**
	 * punto 2: un metodo che restituice le ore di ritardo del treno
	 * 
	 */
	public Integer ritardoTreno() {
		return this.orarioEffettivo - this.orarioPrevisto;
	}

	/**
	 * punto 3: un metodo che verifica se il treno e' in ritardo o no e restituisce
	 * un valore booleano (true se il treno e' in ritardo, false altrimenti)
	 * 
	 */
	public boolean isTrenoInRitardo() {
		return ritardoTreno() > 0;
	}

	/**
	 * punto 4: un metodo che preso in ingresso un intero lo imposta come orario
	 * effettivo del treno: se l'orario passato come parametro e' minore dell'orario
	 * previsto il metodo deve lanciare un'eccezione
	 * 
	 */
	public void setOrarioEffettivo(Integer orario) throws OrarioExceptions {
		if (orario < this.orarioPrevisto) {
			throw new OrarioExceptions("Impossibile impostare un orario precedente a quello previsto");
		} else {
			this.orarioEffettivo = orario;

			logger.info("Ora impostata correttamente");
		}
	}
}
