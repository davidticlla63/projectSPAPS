package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoExamen;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class TipoExamenDao extends DataAccessObjectJpa<TipoExamen> implements ITipoExamenDao {

	public TipoExamenDao() {
		super(TipoExamen.class);
	}

	@Override
	public TipoExamen create(TipoExamen tipoExamen) {
		return super.create(tipoExamen);
	}

	@Override
	public TipoExamen update(TipoExamen tipoExamen) {
		return super.update(tipoExamen);
	}

	@Override
	public TipoExamen registrar(TipoExamen tipoExamen) {
		try {
			beginTransaction();
			tipoExamen = create(tipoExamen);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"TipoExamen " + tipoExamen.getNombre());
			return tipoExamen;
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
	public TipoExamen modificar(TipoExamen tipoExamen) {
		try {
			beginTransaction();
			tipoExamen = update(tipoExamen);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"TipoExamen " + tipoExamen.getNombre());
			return tipoExamen;
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
	public boolean eliminar(TipoExamen tipoExamen) {
		try {
			beginTransaction();
			tipoExamen.setEstado("RM");
			tipoExamen.setFechaModificacion(new Date());
			TipoExamen bar = update(tipoExamen);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"TipoExamen " + tipoExamen.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public TipoExamen obtenerTipoExamen(Integer id) {
		return findById(id);
	}

	@Override
	public List<TipoExamen> obtenerTipoExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<TipoExamen> obtenerTipoExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<TipoExamen> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActiveParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<TipoExamen> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TipoExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

}
