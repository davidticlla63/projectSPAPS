package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoAtencionDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAtencion;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class TipoAtencionDao extends DataAccessObjectJpa<TipoAtencion>
		implements ITipoAtencionDao {

	public TipoAtencionDao() {
		super(TipoAtencion.class);
	}

	@Override
	public TipoAtencion create(TipoAtencion tipoAtencion) {
		return super.create(tipoAtencion);
	}

	@Override
	public TipoAtencion update(TipoAtencion tipoAtencion) {
		return super.update(tipoAtencion);
	}

	@Override
	public TipoAtencion registrar(TipoAtencion tipoAtencion) {
		try {
			beginTransaction();
			tipoAtencion = create(tipoAtencion);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "TipoAtencion "
					+ tipoAtencion.toString());
			return tipoAtencion;
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
	public TipoAtencion modificar(TipoAtencion tipoAtencion) {
		try {
			beginTransaction();
			tipoAtencion = update(tipoAtencion);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "TipoAtencion "
					+ tipoAtencion.toString());
			return tipoAtencion;
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
	public boolean eliminar(TipoAtencion tipoAtencion) {
		try {
			beginTransaction();
			TipoAtencion bar = update(tipoAtencion);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "TipoAtencion "
					+ tipoAtencion.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public TipoAtencion obtenerTipoAtencion(Integer id) {
		return findById(id);
	}

	@Override
	public List<TipoAtencion> obtenerTipoAtencionOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<TipoAtencion> obtenerTipoAtencionOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<TipoAtencion> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<TipoAtencion> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<TipoAtencion> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoAtencionDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TipoAtencion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITipoAtencionDao#obtenerTipoAtencion(java
	 * .lang.String)
	 */
	@Override
	public TipoAtencion obtenerTipoAtencion(String descripcion) {
		// TODO Auto-generated method stub
		return null;
	}

}
