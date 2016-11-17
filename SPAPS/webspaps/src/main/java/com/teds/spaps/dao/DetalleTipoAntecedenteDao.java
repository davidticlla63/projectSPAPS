/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoAntecedente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleTipoAntecedenteDao extends
		DataAccessObjectJpa<DetalleTipoAntecedente> implements
		IDetalleTipoAntecedenteDao {

	/**
	 * @param typeT
	 */
	public DetalleTipoAntecedenteDao() {
		super(DetalleTipoAntecedente.class);
	}

	@Override
	public DetalleTipoAntecedente create(
			DetalleTipoAntecedente detalleTipoAntecedente) {
		return super.create(detalleTipoAntecedente);
	}

	@Override
	public DetalleTipoAntecedente update(
			DetalleTipoAntecedente detalleTipoAntecedente) {
		return super.update(detalleTipoAntecedente);
	}

	@Override
	public void delete(DetalleTipoAntecedente detalleTipoAntecedente) {
		super.deleteReal(detalleTipoAntecedente);
	}

	@Override
	public DetalleTipoAntecedente registrar(
			DetalleTipoAntecedente detalleTipoAntecedente) {
		try {
			beginTransaction();
			detalleTipoAntecedente = create(detalleTipoAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Registro Correcto",
					"DetalleTipoAntecedente "
							+ detalleTipoAntecedente.toString());
			return detalleTipoAntecedente;
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
	public DetalleTipoAntecedente modificar(
			DetalleTipoAntecedente detalleTipoAntecedente) {
		try {
			beginTransaction();
			detalleTipoAntecedente = update(detalleTipoAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"DetalleTipoAntecedente "
							+ detalleTipoAntecedente.toString());
			return detalleTipoAntecedente;
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
	public boolean eliminar(DetalleTipoAntecedente detalleTipoAntecedente) {
		try {
			detalleTipoAntecedente.setEstado("RM");
			beginTransaction();
			DetalleTipoAntecedente bar = update(detalleTipoAntecedente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"DetalleTipoAntecedente "
							+ detalleTipoAntecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleTipoAntecedente> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#
	 * obtenerDetalleTipoAntecedente(java.lang.Integer)
	 */
	@Override
	public DetalleTipoAntecedente obtenerDetalleTipoAntecedente(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#
	 * obtenerDetalleTipoAntecedenteOrdenAscPorId()
	 */
	@Override
	public List<DetalleTipoAntecedente> obtenerDetalleTipoAntecedenteOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#
	 * obtenerDetalleTipoAntecedenteOrdenDescPorId()
	 */
	@Override
	public List<DetalleTipoAntecedente> obtenerDetalleTipoAntecedenteOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleTipoAntecedente> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleTipoAntecedente> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleTipoAntecedente> obtenerPorAntecedente(
			Antecedente antecedente) {
		return findAllActivosByParameter("antecedente", antecedente.getId());
	}

	@Override
	public List<DetalleTipoAntecedente> obtenerPorTipoAntecedente(
			TipoAntecedente tipoAntecedente) {
		return findAllActivosByParameter("tipoAntecedente",
				tipoAntecedente.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleTipoAntecedente> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
