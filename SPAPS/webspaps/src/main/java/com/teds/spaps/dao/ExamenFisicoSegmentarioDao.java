/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IExamenFisicoSegmentarioDao;
import com.teds.spaps.interfaces.dao.IExamenFisicoSignosVitalesDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ExamenFisicoSegmentario;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class ExamenFisicoSegmentarioDao extends
		DataAccessObjectJpa<ExamenFisicoSegmentario> implements
		IExamenFisicoSegmentarioDao {

	private @Inject IExamenFisicoSignosVitalesDao examenFisicoSignosVitalesDao;

	/**
	 * @param typeT
	 */
	public ExamenFisicoSegmentarioDao() {
		super(ExamenFisicoSegmentario.class);
	}

	@Override
	public ExamenFisicoSegmentario create(
			ExamenFisicoSegmentario examenFisicoSegmentario) {
		return super.create(examenFisicoSegmentario);
	}

	@Override
	public ExamenFisicoSegmentario update(
			ExamenFisicoSegmentario examenFisicoSegmentario) {
		return super.update(examenFisicoSegmentario);
	}

	@Override
	public ExamenFisicoSegmentario registrar(
			ExamenFisicoSegmentario examenFisicoSegmentario) {
		try {
			beginTransaction();
			examenFisicoSegmentario = create(examenFisicoSegmentario);
			if (examenFisicoSegmentario.getExamenEnfermera() != null
					&& examenFisicoSegmentario.getExamenEnfermera().getId() > 0) {
				examenFisicoSegmentario.getExamenEnfermera().setEstado("AC");
				examenFisicoSignosVitalesDao.update(examenFisicoSegmentario
						.getExamenEnfermera());
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Examen Fisico "
					+ examenFisicoSegmentario.toString());
			return examenFisicoSegmentario;
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
	public ExamenFisicoSegmentario modificar(
			ExamenFisicoSegmentario examenFisicoSegmentario) {
		try {
			beginTransaction();
			examenFisicoSegmentario = update(examenFisicoSegmentario);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "ExamenFisico "
					+ examenFisicoSegmentario.toString());
			return examenFisicoSegmentario;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
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
	public boolean eliminar(ExamenFisicoSegmentario examenFisicoSegmentario) {
		try {
			examenFisicoSegmentario.setEstado("RM");
			beginTransaction();
			ExamenFisicoSegmentario bar = update(examenFisicoSegmentario);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "ExamenFisico "
					+ examenFisicoSegmentario.toString());
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
	public List<ExamenFisicoSegmentario> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public ExamenFisicoSegmentario obtenerExamenFisico(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IExamenFisicoDao#
	 * obtenerExamenFisicoOrdenAscPorId()
	 */
	@Override
	public List<ExamenFisicoSegmentario> obtenerExamenFisicoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IExamenFisicoDao#
	 * obtenerExamenFisicoOrdenDescPorId()
	 */
	@Override
	public List<ExamenFisicoSegmentario> obtenerExamenFisicoOrdenDescPorId() {
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
	public List<ExamenFisicoSegmentario> obtenerPorCompania(Compania compania) {
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
	public List<ExamenFisicoSegmentario> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<ExamenFisicoSegmentario> obtenerPorHistoriaClinica(
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
	public List<ExamenFisicoSegmentario> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion_signos",
				query);
	}

}
