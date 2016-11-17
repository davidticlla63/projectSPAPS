package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IEstadoCivilDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.EstadoCivil;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class EstadoCivilDao extends DataAccessObjectJpa<EstadoCivil> implements
		IEstadoCivilDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public EstadoCivilDao() {
		super(EstadoCivil.class);
	}

	@Override
	public EstadoCivil create(EstadoCivil estadoCivil) {
		return super.create(estadoCivil);
	}

	@Override
	public EstadoCivil update(EstadoCivil estadoCivil) {
		return super.update(estadoCivil);
	}

	@Override
	public EstadoCivil registrar(EstadoCivil EstadoCivil) {
		try {
			beginTransaction();
			EstadoCivil = create(EstadoCivil);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "EstadoCivil "
					+ EstadoCivil.getDescripcion());
			return EstadoCivil;
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
	public EstadoCivil modificar(EstadoCivil EstadoCivil) {
		try {
			beginTransaction();
			EstadoCivil = update(EstadoCivil);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "EstadoCivil "
						+ EstadoCivil.getDescripcion());
			return EstadoCivil;
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
	public boolean eliminar(EstadoCivil EstadoCivil) {
		try {
			setDelete(true);
			EstadoCivil.setEstado("RM");
			beginTransaction();
			EstadoCivil bar = update(EstadoCivil);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ EstadoCivil.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(EstadoCivil estadoCivil, Compania compania) {
		List<EstadoCivil> estadoCivils = obtenerPorCompania(compania);
		for (EstadoCivil estadoCivil2 : estadoCivils) {
			if (estadoCivil2.getDescripcion().equalsIgnoreCase(
					estadoCivil.getDescripcion())
					|| estadoCivil2.equals(estadoCivil))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(EstadoCivil estadoCivil,
			Compania compania) {
		List<EstadoCivil> estadoCivils = obtenerPorCompania(compania);
		for (EstadoCivil estadoCivil2 : estadoCivils) {
			if (estadoCivil2.equals(estadoCivil))
				return false;
			if (estadoCivil2.getDescripcion().equalsIgnoreCase(
					estadoCivil.getDescripcion())
					&& !estadoCivil2.equals(estadoCivil))
				return true;
		}
		return false;
	}

	@Override
	public EstadoCivil obtenerEstadoCivil(Integer id) {
		return findById(id);
	}

	@Override
	public EstadoCivil obtenerEstadoCivil(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<EstadoCivil> obtenerEstadoCivilOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<EstadoCivil> obtenerEstadoCivilOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<EstadoCivil> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<EstadoCivil> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<EstadoCivil> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IEstadoCivilDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<EstadoCivil> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<EstadoCivil> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<EstadoCivil> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

}
