/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgOrdenServicioLabExamenDao extends
		DataAccessObjectJpa<DesgOrdenServicioLabExamen> implements
		IDesgOrdenServicioLabExamenDao {

	/**
	 * @param typeT
	 */
	public DesgOrdenServicioLabExamenDao() {
		super(DesgOrdenServicioLabExamen.class);
	}

	@Override
	public DesgOrdenServicioLabExamen create(
			DesgOrdenServicioLabExamen detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DesgOrdenServicioLabExamen update(
			DesgOrdenServicioLabExamen detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DesgOrdenServicioLabExamen detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DesgOrdenServicioLabExamen registrar(
			DesgOrdenServicioLabExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Registro Correcto",
					"DesgOrdenServicioLabExamen "
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
	public boolean registrar(List<DesgOrdenServicioLabExamen> antecedentes) {
		try {
			beginTransaction();
			for (DesgOrdenServicioLabExamen detalleAntecedente : antecedentes) {
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
	public DesgOrdenServicioLabExamen modificar(
			DesgOrdenServicioLabExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"DesgOrdenServicioLabExamen "
							+ detalleAntecedente.toString());
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
	public boolean eliminar(DesgOrdenServicioLabExamen detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DesgOrdenServicioLabExamen bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"DesgOrdenServicioLabExamen "
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
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerDesgOrdenServicioLabExamen(java.lang.Integer)
	 */
	@Override
	public DesgOrdenServicioLabExamen obtenerDesgOrdenServicioLabExamen(
			Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerDesgOrdenServicioLabExamenOrdenAscPorId()
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerDesgOrdenServicioLabExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerDesgOrdenServicioLabExamenOrdenDescPorId()
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerDesgOrdenServicioLabExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerPorCompania (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerPorSucursal (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#onComplete
	 * (java. lang.String)
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * obtenerPorServicio (com.teds.spaps.model.Servicio)
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerPorExamen(LabExamen examen) {
		return findAllActivosByParameter("examen", examen.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#obtenerPorOrden
	 * ( com.teds.spaps.model.DesgOrdenServicio)
	 */
	@Override
	public List<DesgOrdenServicioLabExamen> obtenerPorOrden(
			DesgOrdenServicio orden) {
		return findAllByParameter("ordenServicio", orden.getId());
	}

	@Override
	public List<DesgOrdenServicioLabExamen> obtenerPorOrdenHC(
			DesgOrdenServicio orden) {
		return findAllByParameterObjectTwo("ordenServicio", "estado",
				orden.getId(), "PE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao#
	 * verificarServicioRegistrado(com.teds.spaps.model.Servicio,
	 * com.teds.spaps.model.Sucursal)
	 */
	@Override
	public boolean verificarExamenRegistrado(LabExamen examen, Sucursal sucursal) {
		List<DesgOrdenServicioLabExamen> examenes = obtenerPorSucursal(sucursal);
		for (DesgOrdenServicioLabExamen desgLabGrupoExamen : examenes) {
			if (desgLabGrupoExamen.getExamen().equals(examen))
				return true;
		}
		return false;
	}

}
