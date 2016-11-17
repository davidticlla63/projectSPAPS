package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IPlanSeguroDao;
import com.teds.spaps.interfaces.dao.IPlanSeguroServicioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.PlanSeguroServicio;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class PlanSeguroDao extends DataAccessObjectJpa<PlanSeguro> implements
		IPlanSeguroDao {

	private @Inject IPlanSeguroServicioDao planSeguroServicioDao;

	public PlanSeguroDao() {
		super(PlanSeguro.class);
	}

	@Override
	public PlanSeguro create(PlanSeguro planSeguro) {
		return super.create(planSeguro);
	}

	@Override
	public PlanSeguro update(PlanSeguro planSeguro) {
		return super.update(planSeguro);
	}

	@Override
	public PlanSeguro registrar(PlanSeguro planSeguro,
			List<PlanSeguroServicio> coberturas) {
		try {
			beginTransaction();
			planSeguro = create(planSeguro);
			for (PlanSeguroServicio planSeguroServicio : coberturas) {
				planSeguroServicio.setPlanSeguro(planSeguro);
				planSeguroServicio.setSucursal(planSeguro.getSucursal());
				planSeguroServicio.setCompania(planSeguro.getCompania());
				planSeguroServicio
						.setFechaRegistro(planSeguro.getFechaRegistro());
				planSeguroServicio.setFechaModificacion(planSeguro
						.getFechaModificacion());
				planSeguroServicio.setUsuarioRegistro(planSeguro
						.getUsuarioRegistro());
				planSeguroServicioDao.create(planSeguroServicio);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "PlanSeguro "
					+ planSeguro.getDescripcion());
			return planSeguro;
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
	public PlanSeguro modificar(PlanSeguro planSeguro,
			List<PlanSeguroServicio> coberturasEliminar,
			List<PlanSeguroServicio> coberturasMeter) {
		try {
			beginTransaction();
			planSeguro = update(planSeguro);
			for (PlanSeguroServicio planSeguroServicio : coberturasEliminar) {
				planSeguroServicioDao.delete(planSeguroServicio);
			}
			for (PlanSeguroServicio detalleCobertura1 : coberturasMeter) {
				detalleCobertura1.setPlanSeguro(planSeguro);
				detalleCobertura1.setSucursal(planSeguro.getSucursal());
				detalleCobertura1.setCompania(planSeguro.getCompania());
				detalleCobertura1.setFechaRegistro(planSeguro
						.getFechaModificacion());
				detalleCobertura1.setFechaModificacion(planSeguro
						.getFechaModificacion());
				detalleCobertura1.setEstado(planSeguro.getEstado());
				detalleCobertura1.setUsuarioRegistro(planSeguro
						.getUsuarioRegistro());
				detalleCobertura1.setId(null);
				planSeguroServicioDao.create(detalleCobertura1);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "PlanSeguro "
					+ planSeguro.getDescripcion());
			return planSeguro;
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
	public boolean eliminar(PlanSeguro planSeguro,
			List<PlanSeguroServicio> coberturas) {
		try {
			planSeguro.setEstado("RM");
			beginTransaction();
			PlanSeguro bar = update(planSeguro);
			for (PlanSeguroServicio planSeguroServicio : coberturas) {
				planSeguroServicio.setEstado(planSeguro.getEstado());
				planSeguroServicioDao.update(planSeguroServicio);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "PlanSeguro "
					+ planSeguro.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public PlanSeguro obtenerPlanSeguro(Integer id) {
		return findById(id);
	}

	@Override
	public PlanSeguro obtenrPlanSeguro(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	@Override
	public PlanSeguro obtenerPlanSeguro(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public PlanSeguro obtenerPlanSeguro(String descripcion, Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	@Override
	public PlanSeguro obtenerPlanSeguro(String descripcion, Seguro seguro) {
		return findByParameterObjectTwo("descripcion", "seguro", descripcion,
				seguro.getId());
	}

	@Override
	public PlanSeguro obtenerPlanSeguroPorDescripcion(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public PlanSeguro obtenerPlanSeguroPorDescripcion(String descripcion,
			Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	@Override
	public List<PlanSeguro> obtenerPlanSeguroOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<PlanSeguro> obtenerPlanSeguroOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<PlanSeguro> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<PlanSeguro> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<PlanSeguro> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPlanSeguroDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<PlanSeguro> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<PlanSeguro> onComplete(String query) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"nombre", query, "estado", "AC", "seguro");
	}

	@Override
	public List<PlanSeguro> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"nombre", nombre, "compania", compania.getId(), "estado", "AC",
				"seguro");
	}

	@Override
	public List<PlanSeguro> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"nombre", nombre, "sucursal", sucursal.getId(), "estado", "AC",
				"seguro");
	}

}
