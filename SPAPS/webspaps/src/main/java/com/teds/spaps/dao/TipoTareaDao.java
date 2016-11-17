/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITipoTareaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoTarea;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class TipoTareaDao extends DataAccessObjectJpa<TipoTarea> implements
		ITipoTareaDao {

	/**
	 * @param typeT
	 */
	public TipoTareaDao() {
		super(TipoTarea.class);
	}

	@Override
	public TipoTarea create(TipoTarea tipoTarea) {
		return super.create(tipoTarea);
	}

	@Override
	public TipoTarea update(TipoTarea tipoTarea) {
		return super.update(tipoTarea);
	}

	@Override
	public TipoTarea registrar(TipoTarea tipoTarea) {
		try {
			beginTransaction();
			tipoTarea = create(tipoTarea);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"TipoTarea " + tipoTarea.toString());
			return tipoTarea;
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
	public TipoTarea modificar(TipoTarea tipoTarea) {
		try {
			beginTransaction();
			tipoTarea = update(tipoTarea);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "TipoTarea "
					+ tipoTarea.toString());
			return tipoTarea;
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
	public boolean eliminar(TipoTarea tipoTarea) {
		try {
			tipoTarea.setEstado("RM");
			beginTransaction();
			TipoTarea bar = update(tipoTarea);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "TipoTarea "
					+ tipoTarea.toString());
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
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TipoTarea> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#obtenerTipoTarea (java
	 * .lang.Integer)
	 */
	@Override
	public TipoTarea obtenerTipoTarea(Integer id) {
		return findById(id);
	}

	@Override
	public TipoTarea obtenerTipoTarea(String nombre) {
		return findByParameter("nombre", nombre);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#
	 * obtenerTipoTareaOrdenAscPorId ()
	 */
	@Override
	public List<TipoTarea> obtenerTipoTareaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#
	 * obtenerTipoTareaOrdenDescPorId()
	 */
	@Override
	public List<TipoTarea> obtenerTipoTareaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<TipoTarea> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<TipoTarea> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<TipoTarea> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<TipoTarea> obtenerPorSucursal(String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

	@Override
	public boolean verificarRepetido(TipoTarea tarea, Compania compania) {
		List<TipoTarea> tareas = obtenerPorCompania(compania);
		for (TipoTarea cobertura2 : tareas) {
			if (cobertura2.getNombre().equalsIgnoreCase(tarea.getNombre())
					|| cobertura2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(TipoTarea tarea, Compania compania) {
		List<TipoTarea> tareas = obtenerPorCompania(compania);
		for (TipoTarea cobertura2 : tareas) {
			if (cobertura2.getNombre().equalsIgnoreCase(tarea.getNombre())
					&& !cobertura2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetido(TipoTarea tarea, Sucursal sucursal) {
		List<TipoTarea> tareas = obtenerPorSucursal(sucursal);
		for (TipoTarea cobertura2 : tareas) {
			if (cobertura2.getNombre().equalsIgnoreCase(tarea.getNombre())
					|| cobertura2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(TipoTarea tarea, Sucursal sucursal) {
		List<TipoTarea> tareas = obtenerPorSucursal(sucursal);
		for (TipoTarea cobertura2 : tareas) {
			if (cobertura2.getNombre().equalsIgnoreCase(tarea.getNombre())
					&& !cobertura2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public List<TipoTarea> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<TipoTarea> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITipoTareaDao#onComplete(java.lang
	 * .String )
	 */
	@Override
	public List<TipoTarea> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
