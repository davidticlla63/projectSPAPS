package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IEspecialidadDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class EspecialidadDao extends DataAccessObjectJpa<Especialidad>
		implements IEspecialidadDao {

	public EspecialidadDao() {
		super(Especialidad.class);
	}

	@Override
	public Especialidad create(Especialidad especialidad) {
		return super.create(especialidad);
	}

	@Override
	public Especialidad update(Especialidad especialidad) {
		return super.update(especialidad);
	}

	@Override
	public Especialidad registrar(Especialidad especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Especialidad "
					+ especialidad.toString());
			return especialidad;
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
	public Especialidad modificar(Especialidad especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Especialidad "
					+ especialidad.toString());
			return especialidad;
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
	public boolean eliminar(Especialidad especialidad) {
		try {
			beginTransaction();
			Especialidad bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Especialidad "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Especialidad obtenerEspecialidad(Integer id) {
		return findById(id);
	}

	@Override
	public Especialidad obtenerEspecialidad(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<Especialidad> obtenerEspecialidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Especialidad> obtenerEspecialidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Especialidad> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<Especialidad> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IEspecialidadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Especialidad> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IEspecialidadDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<Especialidad> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IEspecialidadDao#obtenerAllActivos()
	 */
	@Override
	public List<Especialidad> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
