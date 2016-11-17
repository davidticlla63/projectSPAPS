/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicios;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgOrdenServiciosDao extends
		DataAccessObjectJpa<DesgOrdenServicios> implements
		IDesgOrdenServiciosDao {

	/**
	 * @param typeT
	 */
	public DesgOrdenServiciosDao() {
		super(DesgOrdenServicios.class);
	}

	@Override
	public DesgOrdenServicios create(DesgOrdenServicios detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DesgOrdenServicios update(DesgOrdenServicios detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DesgOrdenServicios detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DesgOrdenServicios registrar(DesgOrdenServicios detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgOrdenServicios "
					+ detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean registrar(List<DesgOrdenServicios> antecedentes) {
		try {
			beginTransaction();
			for (DesgOrdenServicios detalleAntecedente : antecedentes) {
				create(detalleAntecedente);
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
	public DesgOrdenServicios modificar(DesgOrdenServicios detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DesgOrdenServicios " + detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean eliminar(DesgOrdenServicios detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DesgOrdenServicios bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgOrdenServicios "
					+ detalleAntecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgOrdenServicios> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#
	 * obtenerDesgOrdenServicios(java.lang.Integer)
	 */
	@Override
	public DesgOrdenServicios obtenerDesgOrdenServicios(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#
	 * obtenerDesgOrdenServiciosOrdenAscPorId()
	 */
	@Override
	public List<DesgOrdenServicios> obtenerDesgOrdenServiciosOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#
	 * obtenerDesgOrdenServiciosOrdenDescPorId()
	 */
	@Override
	public List<DesgOrdenServicios> obtenerDesgOrdenServiciosOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgOrdenServicios> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgOrdenServicios> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DesgOrdenServicios> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#obtenerPorServicio
	 * (com.teds.spaps.model.Servicio)
	 */
	@Override
	public List<DesgOrdenServicios> obtenerPorServicio(Servicio servicio) {
		return findAllActivosByParameter("servicio", servicio.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#obtenerPorOrden(
	 * com.teds.spaps.model.DesgOrdenServicio)
	 */
	@Override
	public List<DesgOrdenServicios> obtenerPorOrden(DesgOrdenServicio orden) {
		return findAllByParameter("ordenServicio", orden.getId());
	}

	@Override
	public List<DesgOrdenServicios> obtenerPorOrdenHC(DesgOrdenServicio orden) {
		return findAllByParameterObjectTwo("ordenServicio", "estado",
				orden.getId(), "PE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao#
	 * verificarServicioRegistrado(com.teds.spaps.model.Servicio,
	 * com.teds.spaps.model.Sucursal)
	 */
	@Override
	public boolean verificarServicioRegistrado(Servicio servicio,
			Sucursal sucursal) {
		List<DesgOrdenServicios> examenes = obtenerPorSucursal(sucursal);
		for (DesgOrdenServicios desgLabGrupoExamen : examenes) {
			if (desgLabGrupoExamen.getServicio().equals(servicio))
				return true;
		}
		return false;
	}

}
