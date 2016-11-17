package com.teds.spaps.structore;

import java.util.ArrayList;
import java.util.List;

import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDDesgOrdenImagExamen {

	private DesgImagGrupo grupoExamen;
	private List<DesgImagGrupoExamen> listaExamen = new ArrayList<>();

	/**
	 * @param correlativo
	 * @param grupoExamen
	 * @param listaExamen
	 */
	public EDDesgOrdenImagExamen(DesgImagGrupo grupoExamen,
			List<DesgImagGrupoExamen> listaExamen) {
		super();
		this.grupoExamen = grupoExamen;
		this.listaExamen = listaExamen;
	}

	/**
	 * 
	 */
	public EDDesgOrdenImagExamen() {
		super();
		this.grupoExamen = new DesgImagGrupo();
		this.listaExamen = new ArrayList<DesgImagGrupoExamen>();
	}

	public DesgImagGrupo getGrupoExamen() {
		return grupoExamen;
	}

	public List<DesgImagGrupoExamen> getListaExamen() {
		return listaExamen;
	}

	public void setGrupoExamen(DesgImagGrupo grupoExamen) {
		this.grupoExamen = grupoExamen;
	}

	public void setListaExamen(List<DesgImagGrupoExamen> listaExamen) {
		this.listaExamen = listaExamen;
	}

}
