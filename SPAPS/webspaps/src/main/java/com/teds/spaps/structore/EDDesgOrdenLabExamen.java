package com.teds.spaps.structore;

import java.util.ArrayList;
import java.util.List;

import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDDesgOrdenLabExamen {

	private DesgLabGrupo grupoExamen;
	private List<DesgLabGrupoExamen> listaExamen = new ArrayList<>();

	/**
	 * @param correlativo
	 * @param grupoExamen
	 * @param listaExamen
	 */
	public EDDesgOrdenLabExamen(DesgLabGrupo grupoExamen,
			List<DesgLabGrupoExamen> listaExamen) {
		super();
		this.grupoExamen = grupoExamen;
		this.listaExamen = listaExamen;
	}

	/**
	 * 
	 */
	public EDDesgOrdenLabExamen() {
		super();
		this.grupoExamen = new DesgLabGrupo();
		this.listaExamen = new ArrayList<DesgLabGrupoExamen>();
	}

	public DesgLabGrupo getGrupoExamen() {
		return grupoExamen;
	}

	public List<DesgLabGrupoExamen> getListaExamen() {
		return listaExamen;
	}

	public void setGrupoExamen(DesgLabGrupo grupoExamen) {
		this.grupoExamen = grupoExamen;
	}

	public void setListaExamen(List<DesgLabGrupoExamen> listaExamen) {
		this.listaExamen = listaExamen;
	}

}
