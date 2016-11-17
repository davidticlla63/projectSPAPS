/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoAntecedenteDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class TipoAntecedenteDao extends DataAccessObjectJpa<TipoAntecedente>
		implements ITipoAntecedenteDao {

	/**
	 * @param typeT
	 */
	public TipoAntecedenteDao() {
		super(TipoAntecedente.class);
	}

	@Override
	public TipoAntecedente create(TipoAntecedente tipoAntecedente) {
		return super.create(tipoAntecedente);
	}

	@Override
	public TipoAntecedente update(TipoAntecedente tipoAntecedente) {
		return super.update(tipoAntecedente);
	}

	@Override
	public TipoAntecedente registrar(TipoAntecedente tipoAntecedente) {
		try {
			beginTransaction();
			tipoAntecedente = create(tipoAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "TipoAntecedente "
					+ tipoAntecedente.toString());
			return tipoAntecedente;
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
	public TipoAntecedente modificar(TipoAntecedente tipoAntecedente) {
		try {
			beginTransaction();
			tipoAntecedente = update(tipoAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "TipoAntecedente "
					+ tipoAntecedente.toString());
			return tipoAntecedente;
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
	public boolean eliminar(TipoAntecedente tipoAntecedente) {
		try {
			tipoAntecedente.setEstado("RM");
			beginTransaction();
			TipoAntecedente bar = update(tipoAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "TipoAntecedente "
					+ tipoAntecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TipoAntecedente> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#obtenerTipoAntecedente
	 * (java .lang.Integer)
	 */
	@Override
	public TipoAntecedente obtenerTipoAntecedente(Integer id) {
		return findById(id);
	}

	@Override
	public TipoAntecedente obtenerTipoAntecedente(String nombre) {
		return findByParameter("nombre", nombre);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#
	 * obtenerTipoAntecedenteOrdenAscPorId ()
	 */
	@Override
	public List<TipoAntecedente> obtenerTipoAntecedenteOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#
	 * obtenerTipoAntecedenteOrdenDescPorId()
	 */
	@Override
	public List<TipoAntecedente> obtenerTipoAntecedenteOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<TipoAntecedente> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<TipoAntecedente> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoAntecedenteDao#onComplete(java.lang
	 * .String )
	 */
	@Override
	public List<TipoAntecedente> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
