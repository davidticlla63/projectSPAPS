/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IServicioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class ServicioDao extends DataAccessObjectJpa<Servicio> implements
		IServicioDao {

	/**
	 * @param typeT
	 */
	public ServicioDao() {
		super(Servicio.class);
	}

	@Override
	public Servicio create(Servicio servicio) {
		return super.create(servicio);
	}

	@Override
	public Servicio update(Servicio servicio) {
		return super.update(servicio);
	}

	@Override
	public Servicio registrar(Servicio servicio) {
		try {
			beginTransaction();
			servicio = create(servicio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Cobertura " + servicio.toString());
			return servicio;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public Servicio modificar(Servicio servicio) {
		try {
			beginTransaction();
			servicio = update(servicio);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Cobertura "
					+ servicio.toString());
			return servicio;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Servicio servicio) {
		try {
			servicio.setEstado("RM");
			beginTransaction();
			Servicio bar = update(servicio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Cobertura "
					+ servicio.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(Servicio servicio, Compania compania) {
		List<Servicio> servicios = obtenerPorCompania(compania);
		for (Servicio cobertura2 : servicios) {
			if (cobertura2.getDescripcion().equalsIgnoreCase(
					servicio.getDescripcion())
					|| cobertura2.equals(servicio))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Servicio servicio, Compania compania) {
		List<Servicio> servicios = obtenerPorCompania(compania);
		for (Servicio cobertura2 : servicios) {
			if (cobertura2.equals(servicio))
				return false;
			if (cobertura2.getDescripcion().equalsIgnoreCase(
					servicio.getDescripcion()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ICoberturaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Servicio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ICoberturaDao#obtenerCobertura(java.lang
	 * .Integer)
	 */
	@Override
	public Servicio obtenerCobertura(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ICoberturaDao#obtenerCoberturaOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Servicio> obtenerCoberturaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ICoberturaDao#obtenerCoberturaOrdenDescPorId
	 * ()
	 */
	@Override
	public List<Servicio> obtenerCoberturaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ICoberturaDao#obtenerPorCompania(com.teds.
	 * spaps.model.Compania)
	 */
	@Override
	public List<Servicio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ICoberturaDao#obtenerPorSucursal(com.teds.
	 * spaps.model.Sucursal)
	 */
	@Override
	public List<Servicio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Servicio> obtenerPorPlanSeguro(PlanSeguro planSeguro) {
		return findAllActiveByParameter("planSeguro", planSeguro.getId());
	}

	@Override
	public List<Servicio> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ICoberturaDao#onComplete(java.lang.String)
	 */
	@Override
	public List<Servicio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
