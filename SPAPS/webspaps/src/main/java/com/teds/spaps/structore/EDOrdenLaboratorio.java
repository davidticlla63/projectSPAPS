package com.teds.spaps.structore;

import java.util.ArrayList;
import java.util.List;

import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabGrupoExamen;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDOrdenLaboratorio {

	private int correlativo;
	private LabGrupoExamen grupoExamen;
	private boolean checkAll = false;
	private List<LabExamen> listaExamen = new ArrayList<LabExamen>();

	/**
	 * @param correlativo
	 * @param grupoExamen
	 * @param listaExamen
	 */
	public EDOrdenLaboratorio(int correlativo, LabGrupoExamen grupoExamen,
			List<LabExamen> listaExamen) {
		super();
		this.correlativo = correlativo;
		this.grupoExamen = grupoExamen;
		this.listaExamen = listaExamen;
	}

	

	/**
	 * @return the correlativo
	 */
	public int getCorrelativo() {
		return correlativo;
	}

	/**
	 * @return the grupoExamen
	 */
	public LabGrupoExamen getGrupoExamen() {
		return grupoExamen;
	}


	/**
	 * @return the checkAll
	 */
	public boolean isCheckAll() {
		return checkAll;
	}

	/**
	 * @return the listaExamen
	 */
	public List<LabExamen> getListaExamen() {
		return listaExamen;
	}

}
