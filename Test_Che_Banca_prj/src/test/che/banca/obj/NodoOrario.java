package test.che.banca.obj;

public class NodoOrario {

	private Orario info;
	private NodoOrario nextNodo = null;

	public NodoOrario(Orario orario) {
		this.info = orario;
	}

	public Orario getInfo() {
		return info;
	}

	public void setInfo(Orario info) {
		this.info = info;
	}

	public NodoOrario getNextNodo() {
		return nextNodo;
	}

	public void setNextNodo(NodoOrario nextNodo) {
		this.nextNodo = nextNodo;
	}
}
