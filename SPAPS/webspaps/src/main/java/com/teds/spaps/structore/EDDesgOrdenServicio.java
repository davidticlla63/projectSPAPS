package com.teds.spaps.structore;

import java.util.ArrayList;
import java.util.List;

import com.teds.spaps.model.DesgServGrupo;
import com.teds.spaps.model.DesgServGrupoServicio;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDDesgOrdenServicio {

	private DesgServGrupo grupoServicio;
	private List<DesgServGrupoServicio> servicios = new ArrayList<>();

	/**
	 * @param correlativo
	 * @param grupoExamen
	 * @param servicios
	 */
	public EDDesgOrdenServicio(DesgServGrupo grupoExamen,
			List<DesgServGrupoServicio> servicios) {
		super();
		this.grupoServicio = grupoExamen;
		this.servicios = servicios;
	}

	/**
	 * 
	 */
	public EDDesgOrdenServicio() {
		super();
		this.grupoServicio = new DesgServGrupo();
		this.servicios = new ArrayList<DesgServGrupoServicio>();
	}

	public DesgServGrupo getGrupoServicio() {
		return grupoServicio;
	}

	public List<DesgServGrupoServicio> getServicios() {
		return servicios;
	}

	public void setGrupoServicio(DesgServGrupo grupoServicio) {
		this.grupoServicio = grupoServicio;
	}

	public void setServicios(List<DesgServGrupoServicio> servicios) {
		this.servicios = servicios;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.grupoServicio
				.equals(((EDDesgOrdenServicio) obj).grupoServicio);
	}

}
