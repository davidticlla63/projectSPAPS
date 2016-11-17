/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITareaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class TareaDao extends DataAccessObjectJpa<Tarea> implements ITareaDao {

	/**
	 * @param typeT
	 */
	public TareaDao() {
		super(Tarea.class);
	}

	@Override
	public Tarea create(Tarea tarea) {
		return super.create(tarea);
	}

	@Override
	public Tarea update(Tarea tarea) {
		return super.update(tarea);
	}

	@Override
	public Tarea registrar(Tarea tarea) {
		try {
			beginTransaction();
			tarea = create(tarea);
			commitTransaction();
			return tarea;
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
	public Tarea modificar(Tarea tarea) {
		try {
			beginTransaction();
			tarea = update(tarea);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Tarea " + tarea.toString());
			return tarea;
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
	public boolean eliminar(Tarea tarea) {
		try {
			tarea.setEstado("RM");
			beginTransaction();
			Tarea bar = update(tarea);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Tarea " + tarea.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(Tarea tarea, Compania compania) {
		List<Tarea> tareas = obtenerPorCompania(compania);
		for (Tarea tarea2 : tareas) {
			if (tarea2.getNombreTarea().equals(tarea.getNombreTarea())
					|| tarea2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Tarea tarea, Compania compania) {
		List<Tarea> tareas = obtenerPorCompania(compania);
		for (Tarea tarea2 : tareas) {
			if (tarea2.getNombreTarea().equals(tarea.getNombreTarea())
					&& !tarea2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetido(Tarea tarea, Sucursal sucursal) {
		List<Tarea> tareas = obtenerPorSucursal(sucursal);
		for (Tarea tarea2 : tareas) {
			if (tarea2.getNombreTarea().equals(tarea.getNombreTarea())
					|| tarea2.equals(tarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Tarea tarea, Sucursal sucursal) {
		List<Tarea> tareas = obtenerPorSucursal(sucursal);
		for (Tarea tarea2 : tareas) {
			if (tarea2.getNombreTarea().equals(tarea.getNombreTarea())
					&& !tarea2.equals(tarea))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Tarea> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#obtenerTarea(java.lang
	 * .Integer)
	 */
	@Override
	public Tarea obtenerTarea(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#obtenerTareaOrdenAscPorId ()
	 */
	@Override
	public List<Tarea> obtenerTareaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#obtenerTareaOrdenDescPorId
	 * ()
	 */
	@Override
	public List<Tarea> obtenerTareaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#obtenerPorCompania(com.teds.
	 * spaps.model.Compania)
	 */
	@Override
	public List<Tarea> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Tarea> obtenerPorPersonal(Personal personal, Sucursal sucursal) {
		return findAllActivosByParameterTwo("personal", "sucursal",
				personal.getId(), sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#obtenerPorSucursal(com.teds.
	 * spaps.model.Sucursal)
	 */
	@Override
	public List<Tarea> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Tarea> obtenerPorPlanSeguro(PlanSeguro planSeguro) {
		return findAllActiveByParameter("planSeguro", planSeguro.getId());
	}

	@Override
	public List<Tarea> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Tarea> obtenerPorSucursal(String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITareaDao#onComplete(java.lang.String)
	 */
	@Override
	public List<Tarea> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
