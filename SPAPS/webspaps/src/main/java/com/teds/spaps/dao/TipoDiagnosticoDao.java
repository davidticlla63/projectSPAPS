/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoDiagnostico;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class TipoDiagnosticoDao extends DataAccessObjectJpa<TipoDiagnostico>
		implements ITipoDiagnosticoDao {

	/**
	 * @param typeT
	 */
	public TipoDiagnosticoDao() {
		super(TipoDiagnostico.class);
	}

	@Override
	public TipoDiagnostico create(TipoDiagnostico tipoDiagnostico) {
		return super.create(tipoDiagnostico);
	}

	@Override
	public TipoDiagnostico update(TipoDiagnostico tipoDiagnostico) {
		return super.update(tipoDiagnostico);
	}

	@Override
	public TipoDiagnostico registrar(TipoDiagnostico tipoDiagnostico) {
		try {
			beginTransaction();
			tipoDiagnostico = create(tipoDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "TipoDiagnostico "
					+ tipoDiagnostico.toString());
			return tipoDiagnostico;
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
	public TipoDiagnostico modificar(TipoDiagnostico tipoDiagnostico) {
		try {
			beginTransaction();
			tipoDiagnostico = update(tipoDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "TipoDiagnostico "
					+ tipoDiagnostico.toString());
			return tipoDiagnostico;
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
	public boolean eliminar(TipoDiagnostico tipoDiagnostico) {
		try {
			tipoDiagnostico.setEstado("RM");
			beginTransaction();
			TipoDiagnostico bar = update(tipoDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "TipoDiagnostico "
					+ tipoDiagnostico.toString());
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
	 * @see com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TipoDiagnostico> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#obtenerTipoDiagnostico
	 * (java .lang.Integer)
	 */
	@Override
	public TipoDiagnostico obtenerTipoDiagnostico(Integer id) {
		return findById(id);
	}

	@Override
	public TipoDiagnostico obtenerTipoDiagnostico(String nombre) {
		return findByParameter("nombre", nombre);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#
	 * obtenerTipoDiagnosticoOrdenAscPorId ()
	 */
	@Override
	public List<TipoDiagnostico> obtenerTipoDiagnosticoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#
	 * obtenerTipoDiagnosticoOrdenDescPorId()
	 */
	@Override
	public List<TipoDiagnostico> obtenerTipoDiagnosticoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<TipoDiagnostico> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<TipoDiagnostico> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<TipoDiagnostico> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoDiagnosticoDao#onComplete(java.lang
	 * .String )
	 */
	@Override
	public List<TipoDiagnostico> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
