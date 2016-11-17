package com.teds.spaps.structore;

import java.util.ArrayList;
import java.util.List;

import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamenImag;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDOrdenImagenologia {

	private int correlativo;
	private LabGrupoExamenImag grupoExamen;
	private boolean checkAll = false;
	private List<LabExamenImag> listaExamen = new ArrayList<LabExamenImag>();

	/**
	 * @param correlativo
	 * @param grupoExamen
	 * @param listaExamen
	 */
	public EDOrdenImagenologia(int correlativo, LabGrupoExamenImag grupoExamen,
			List<LabExamenImag> listaExamen) {
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
	public LabGrupoExamenImag getGrupoExamen() {
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
	public List<LabExamenImag> getListaExamen() {
		return listaExamen;
	}

}
