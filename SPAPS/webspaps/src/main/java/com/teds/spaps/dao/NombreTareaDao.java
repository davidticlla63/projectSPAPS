package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.INombreTareaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.NombreTarea;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class NombreTareaDao extends DataAccessObjectJpa<NombreTarea> implements
		INombreTareaDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public NombreTareaDao() {
		super(NombreTarea.class);
	}

	@Override
	public NombreTarea create(NombreTarea nombreTarea) {
		return super.create(nombreTarea);
	}

	@Override
	public NombreTarea update(NombreTarea nombreTarea) {
		return super.update(nombreTarea);
	}

	@Override
	public NombreTarea registrar(NombreTarea NombreTarea) {
		try {
			beginTransaction();
			NombreTarea = create(NombreTarea);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "NombreTarea "
					+ NombreTarea.getNombre());
			return NombreTarea;
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
	public NombreTarea modificar(NombreTarea NombreTarea) {
		try {
			beginTransaction();
			NombreTarea = update(NombreTarea);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "NombreTarea "
						+ NombreTarea.getNombre());
			return NombreTarea;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				if (!isDelete())
					FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(NombreTarea NombreTarea) {
		try {
			setDelete(true);
			NombreTarea.setEstado("RM");
			beginTransaction();
			NombreTarea bar = update(NombreTarea);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ NombreTarea.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(NombreTarea nombreTarea, Compania compania) {
		List<NombreTarea> nombreTareas = obtenerPorCompania(compania);
		for (NombreTarea nombreTarea2 : nombreTareas) {
			if (nombreTarea2.getNombre().equalsIgnoreCase(
					nombreTarea.getNombre())
					|| nombreTarea2.equals(nombreTarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(NombreTarea nombreTarea,
			Compania compania) {
		List<NombreTarea> tareas = obtenerPorCompania(compania);
		for (NombreTarea cobertura2 : tareas) {
			if (cobertura2.getNombre()
					.equalsIgnoreCase(nombreTarea.getNombre())
					&& !cobertura2.equals(nombreTarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetido(NombreTarea nombreTarea, Sucursal sucursal) {
		List<NombreTarea> nombreTareas = obtenerPorSucursal(sucursal);
		for (NombreTarea nombreTarea2 : nombreTareas) {
			if (nombreTarea2.getNombre().equalsIgnoreCase(
					nombreTarea.getNombre())
					|| nombreTarea2.equals(nombreTarea))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(NombreTarea nombreTarea,
			Sucursal sucursal) {
		List<NombreTarea> tareas = obtenerPorSucursal(sucursal);
		for (NombreTarea cobertura2 : tareas) {
			if (cobertura2.getNombre()
					.equalsIgnoreCase(nombreTarea.getNombre())
					&& !cobertura2.equals(nombreTarea))
				return true;
		}
		return false;
	}

	@Override
	public NombreTarea obtenerNombreTarea(Integer id) {
		return findById(id);
	}

	@Override
	public NombreTarea obtenerNombreTarea(String descripcion) {
		return findByParameter("nombre", descripcion);
	}

	@Override
	public List<NombreTarea> obtenerNombreTareaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<NombreTarea> obtenerNombreTareaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<NombreTarea> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<NombreTarea> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<NombreTarea> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.INombreTareaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<NombreTarea> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<NombreTarea> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				descripcion, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<NombreTarea> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				descripcion, "estado", "AC", "sucursal", sucursal.getId());
	}

}
