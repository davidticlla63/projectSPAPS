package com.teds.spaps.structore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.DesgOrdenServicios;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
public class EDDesgOrdenServicioHC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1326561595568270126L;
	private DesgOrdenServicio ordenServicio;
	private List<DesgOrdenServicioLabExamen> labExamenes;
	private List<DesgOrdenServicioImagExamen> imgExamenes;
	private List<DesgOrdenServicios> servicios;

	/**
	 * @param ordenServicio
	 * @param labExamenes
	 * @param imgExamenes
	 * @param servicios
	 */
	public EDDesgOrdenServicioHC(DesgOrdenServicio ordenServicio,
			List<DesgOrdenServicioLabExamen> labExamenes,
			List<DesgOrdenServicioImagExamen> imgExamenes,
			List<DesgOrdenServicios> servicios) {
		super();
		this.ordenServicio = ordenServicio;
		this.labExamenes = labExamenes;
		this.imgExamenes = imgExamenes;
		this.servicios = servicios;
	}

	/**
	 * 
	 */
	public EDDesgOrdenServicioHC() {
		super();
		this.ordenServicio = new DesgOrdenServicio();
		this.labExamenes = new ArrayList<DesgOrdenServicioLabExamen>();
		this.imgExamenes = new ArrayList<DesgOrdenServicioImagExamen>();
		this.servicios = new ArrayList<DesgOrdenServicios>();
	}

	/**
	 * @param ordenServicio
	 */
	public EDDesgOrdenServicioHC(DesgOrdenServicio ordenServicio) {
		super();
		this.ordenServicio = ordenServicio;

	}

	public DesgOrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	public List<DesgOrdenServicioLabExamen> getLabExamenes() {
		return labExamenes;
	}

	public List<DesgOrdenServicioImagExamen> getImgExamenes() {
		return imgExamenes;
	}

	public List<DesgOrdenServicios> getServicios() {
		return servicios;
	}

	public void setOrdenServicio(DesgOrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public void setLabExamenes(List<DesgOrdenServicioLabExamen> labExamenes) {
		this.labExamenes = labExamenes;
	}

	public void setImgExamenes(List<DesgOrdenServicioImagExamen> imgExamenes) {
		this.imgExamenes = imgExamenes;
	}

	public void setServicios(List<DesgOrdenServicios> servicios) {
		this.servicios = servicios;
	}

}
