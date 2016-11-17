/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgOrdenServicioImagExamenDao extends
		DataAccessObjectJpa<DesgOrdenServicioImagExamen> implements
		IDesgOrdenServicioImagExamenDao {

	/**
	 * @param typeT
	 */
	public DesgOrdenServicioImagExamenDao() {
		super(DesgOrdenServicioImagExamen.class);
	}

	@Override
	public DesgOrdenServicioImagExamen create(
			DesgOrdenServicioImagExamen detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DesgOrdenServicioImagExamen update(
			DesgOrdenServicioImagExamen detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DesgOrdenServicioImagExamen detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DesgOrdenServicioImagExamen registrar(
			DesgOrdenServicioImagExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Registro Correcto",
					"DesgOrdenServicioImagExamen "
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
	public boolean registrar(List<DesgOrdenServicioImagExamen> antecedentes) {
		try {
			beginTransaction();
			for (DesgOrdenServicioImagExamen detalleAntecedente : antecedentes) {
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
	public DesgOrdenServicioImagExamen modificar(
			DesgOrdenServicioImagExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"DesgOrdenServicioImagExamen "
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
	public boolean eliminar(DesgOrdenServicioImagExamen detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DesgOrdenServicioImagExamen bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"DesgOrdenServicioImagExamen "
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
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerDesgOrdenServicioImagExamen(java.lang.Integer)
	 */
	@Override
	public DesgOrdenServicioImagExamen obtenerDesgOrdenServicioImagExamen(
			Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerDesgOrdenServicioImagExamenOrdenAscPorId()
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerDesgOrdenServicioImagExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerDesgOrdenServicioImagExamenOrdenDescPorId()
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerDesgOrdenServicioImagExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerPorCompania (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerPorCompania(
			Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerPorSucursal (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerPorSucursal(
			Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#onComplete
	 * (java. lang.String)
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * obtenerPorServicio (com.teds.spaps.model.Servicio)
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerPorExamen(
			LabExamenImag examen) {
		return findAllActivosByParameter("examen", examen.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#obtenerPorOrden
	 * ( com.teds.spaps.model.DesgOrdenServicio)
	 */
	@Override
	public List<DesgOrdenServicioImagExamen> obtenerPorOrden(
			DesgOrdenServicio orden) {
		return findAllByParameter("ordenServicio", orden.getId());
	}

	@Override
	public List<DesgOrdenServicioImagExamen> obtenerPorOrdenHC(
			DesgOrdenServicio orden) {
		return findAllByParameterObjectTwo("ordenServicio", "estado",
				orden.getId(), "PE");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao#
	 * verificarServicioRegistrado(com.teds.spaps.model.Servicio,
	 * com.teds.spaps.model.Sucursal)
	 */
	@Override
	public boolean verificarExamenRegistrado(LabExamenImag examen,
			Sucursal sucursal) {
		List<DesgOrdenServicioImagExamen> examenes = obtenerPorSucursal(sucursal);
		for (DesgOrdenServicioImagExamen desgLabGrupoExamen : examenes) {
			if (desgLabGrupoExamen.getExamen().equals(examen))
				return true;
		}
		return false;
	}

}
