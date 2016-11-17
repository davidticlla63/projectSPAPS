/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class PersonalEspecialidadDao extends
		DataAccessObjectJpa<PersonalEspecialidad> implements
		IPersonalEspecialidadDao {
	
	@Override
	public boolean deleteDetail(Personal labExamen) {
		return super.removeDetail("personal", labExamen.getId());
	}

	/**
	 * @param typeT
	 */
	public PersonalEspecialidadDao() {
		super(PersonalEspecialidad.class);
	}

	@Override
	public PersonalEspecialidad create(
			PersonalEspecialidad PersonalEspecialidad) {
		return super.create(PersonalEspecialidad);
	}

	@Override
	public PersonalEspecialidad update(
			PersonalEspecialidad PersonalEspecialidad) {
		return super.update(PersonalEspecialidad);
	}

	@Override
	public void delete(PersonalEspecialidad PersonalEspecialidad) {
		super.deleteReal(PersonalEspecialidad);
	}

	@Override
	public PersonalEspecialidad registrar(
			PersonalEspecialidad PersonalEspecialidad) {
		try {
			beginTransaction();
			PersonalEspecialidad = create(PersonalEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"PersonalEspecialidad "
							+ PersonalEspecialidad.toString());
			return PersonalEspecialidad;
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
	public PersonalEspecialidad modificar(
			PersonalEspecialidad PersonalEspecialidad) {
		try {
			beginTransaction();
			PersonalEspecialidad = update(PersonalEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"PersonalEspecialidad "
							+ PersonalEspecialidad.toString());
			return PersonalEspecialidad;
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
	public boolean eliminar(
			PersonalEspecialidad PersonalEspecialidad) {
		try {
			PersonalEspecialidad.setEstado(false);
			beginTransaction();
			PersonalEspecialidad bar = update(PersonalEspecialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"PersonalEspecialidad "
							+ PersonalEspecialidad.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<PersonalEspecialidad> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#
	 * obtenerPersonalEspecialidad(java.lang.Integer)
	 */
	@Override
	public PersonalEspecialidad obtenerPersonalEspecialidad(
			Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#
	 * obtenerPersonalEspecialidadOrdenAscPorId()
	 */
	@Override
	public List<PersonalEspecialidad> obtenerPersonalEspecialidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#
	 * obtenerPersonalEspecialidadOrdenDescPorId()
	 */
	@Override
	public List<PersonalEspecialidad> obtenerPersonalEspecialidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#
	 * obtenerPorCompania (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<PersonalEspecialidad> obtenerPorCompania(
			Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#
	 * obtenerPorSucursal (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<PersonalEspecialidad> obtenerPorSucursal(
			Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<PersonalEspecialidad> obtenerPorHM(
			HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaMedica",
				historiaClinica.getId());
	}

	@Override
	public List<PersonalEspecialidad> obtenerPorPersonal(
			Personal personal) {
		return findAllActivosByParameter("personal", personal.getId());
	}

	@Override
	public List<PersonalEspecialidad> obtenerPorEspecialidad(
			Especialidad especialidad) {
		return findAllActivosByParameter("especialidad", especialidad.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IPersonalEspecialidadDao#onComplete
	 * (java. lang.String)
	 */
	@Override
	public List<PersonalEspecialidad> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
