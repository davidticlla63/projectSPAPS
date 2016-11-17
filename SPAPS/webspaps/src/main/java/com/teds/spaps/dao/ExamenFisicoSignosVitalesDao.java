/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IExamenFisicoSignosVitalesDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ExamenFisicoSignosVitales;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class ExamenFisicoSignosVitalesDao extends
		DataAccessObjectJpa<ExamenFisicoSignosVitales> implements
		IExamenFisicoSignosVitalesDao {

	/**
	 * @param typeT
	 */
	public ExamenFisicoSignosVitalesDao() {
		super(ExamenFisicoSignosVitales.class);
	}

	@Override
	public ExamenFisicoSignosVitales create(
			ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		return super.create(examenFisicoSignosVitales);
	}

	@Override
	public ExamenFisicoSignosVitales update(
			ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		return super.update(examenFisicoSignosVitales);
	}

	@Override
	public ExamenFisicoSignosVitales registrar(
			ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		try {
			beginTransaction();
			examenFisicoSignosVitales = create(examenFisicoSignosVitales);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "ExamenFisico "
					+ examenFisicoSignosVitales.toString());
			return examenFisicoSignosVitales;
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
	public ExamenFisicoSignosVitales modificar(
			ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		try {
			beginTransaction();
			examenFisicoSignosVitales = update(examenFisicoSignosVitales);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "ExamenFisico "
					+ examenFisicoSignosVitales.toString());
			return examenFisicoSignosVitales;
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
	public boolean eliminar(ExamenFisicoSignosVitales examenFisicoSignosVitales) {
		try {
			examenFisicoSignosVitales.setEstado("RM");
			beginTransaction();
			ExamenFisicoSignosVitales bar = update(examenFisicoSignosVitales);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "ExamenFisico "
					+ examenFisicoSignosVitales.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IExamenFisicoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<ExamenFisicoSignosVitales> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IExamenFisicoDao#obtenerExamenFisico(java
	 * .lang.Integer)
	 */
	@Override
	public ExamenFisicoSignosVitales obtenerExamenFisico(Integer id) {
		return findById(id);
	}

	@Override
	public boolean verificarExamenEnfermera(HistoriaClinica historiaClinica,
			Compania compania) {
		return (long) findCount("estado", "historiaClinica", "compania",
				historiaClinica.getId(), compania.getId()) > 0;
	}

	@Override
	public ExamenFisicoSignosVitales obtenerExamenEnfermera(
			HistoriaClinica historiaClinica, Compania compania) {
		return findMaxTransferencia("id", "historiaClinica", "compania",
				historiaClinica.getId(), compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IExamenFisicoDao#
	 * obtenerExamenFisicoOrdenAscPorId()
	 */
	@Override
	public List<ExamenFisicoSignosVitales> obtenerExamenFisicoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IExamenFisicoDao#
	 * obtenerExamenFisicoOrdenDescPorId()
	 */
	@Override
	public List<ExamenFisicoSignosVitales> obtenerExamenFisicoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IExamenFisicoDao#obtenerPorCompania(com
	 * .teds. spaps.model.Compania)
	 */
	@Override
	public List<ExamenFisicoSignosVitales> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IExamenFisicoDao#obtenerPorSucursal(com
	 * .teds. spaps.model.Sucursal)
	 */
	@Override
	public List<ExamenFisicoSignosVitales> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<ExamenFisicoSignosVitales> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaClinica",
				historiaClinica.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IExamenFisicoDao#onComplete(java.lang.String
	 * )
	 */
	@Override
	public List<ExamenFisicoSignosVitales> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion_signos",
				query);
	}

	@Override
	public List<ExamenFisicoSignosVitales> obtenerListaCompania(
			Compania compania) {
		return findAllByParameterObjectThreeQueryOr("estado", "estado",
				"compania", "AC", "II", compania.getId());
	}

}
