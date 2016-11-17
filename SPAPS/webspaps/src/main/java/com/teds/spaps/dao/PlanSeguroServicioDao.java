/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IPlanSeguroServicioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.PlanSeguroServicio;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class PlanSeguroServicioDao extends DataAccessObjectJpa<PlanSeguroServicio>
		implements IPlanSeguroServicioDao {

	/**
	 * @param typeT
	 */
	public PlanSeguroServicioDao() {
		super(PlanSeguroServicio.class);
	}

	@Override
	public PlanSeguroServicio create(PlanSeguroServicio planSeguroServicio) {
		return super.create(planSeguroServicio);
	}

	@Override
	public PlanSeguroServicio update(PlanSeguroServicio planSeguroServicio) {
		return super.update(planSeguroServicio);
	}

	@Override
	public void delete(PlanSeguroServicio planSeguroServicio) {
		super.deleteReal(planSeguroServicio);
	}

	@Override
	public PlanSeguroServicio registrar(PlanSeguroServicio planSeguroServicio) {
		try {
			beginTransaction();
			planSeguroServicio = create(planSeguroServicio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleCobertura "
					+ planSeguroServicio.toString());
			return planSeguroServicio;
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
	public boolean registrar(List<PlanSeguroServicio> coberturas) {
		try {
			beginTransaction();
			for (PlanSeguroServicio planSeguroServicio : coberturas) {
				create(planSeguroServicio);
			}
			commitTransaction();
			return true;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public PlanSeguroServicio modificar(PlanSeguroServicio planSeguroServicio) {
		try {
			beginTransaction();
			planSeguroServicio = update(planSeguroServicio);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DetalleCobertura "
					+ planSeguroServicio.toString());
			return planSeguroServicio;
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
	public boolean eliminar(PlanSeguroServicio planSeguroServicio) {
		try {
			planSeguroServicio.setEstado("RM");
			beginTransaction();
			PlanSeguroServicio bar = update(planSeguroServicio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleCobertura "
					+ planSeguroServicio.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<PlanSeguroServicio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#
	 * obtenerDetalleCobertura(java.lang.Integer)
	 */
	@Override
	public PlanSeguroServicio obtenerDetalleCobertura(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#
	 * obtenerDetalleCoberturaOrdenAscPorId()
	 */
	@Override
	public List<PlanSeguroServicio> obtenerDetalleCoberturaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#
	 * obtenerDetalleCoberturaOrdenDescPorId()
	 */
	@Override
	public List<PlanSeguroServicio> obtenerDetalleCoberturaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<PlanSeguroServicio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<PlanSeguroServicio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<PlanSeguroServicio> obtenerPorPlanSeguro(PlanSeguro planSeguro) {
		return findAllActivosByParameter("planSeguro", planSeguro.getId());
	}

	@Override
	public List<PlanSeguroServicio> obtenerPorCobertura(Servicio servicio) {
		return findAllActivosByParameter("cobertura", servicio.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleCoberturaDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<PlanSeguroServicio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
