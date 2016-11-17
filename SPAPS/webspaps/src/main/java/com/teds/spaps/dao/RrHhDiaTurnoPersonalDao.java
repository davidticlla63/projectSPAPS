package com.teds.spaps.dao;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IRrHhDiaTurnoDao;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.RrHhDiaTurno;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class RrHhDiaTurnoPersonalDao extends DataAccessObjectJpa<RrHhDiaTurno>
		implements IRrHhDiaTurnoDao {

	public RrHhDiaTurnoPersonalDao() {
		super(RrHhDiaTurno.class);
	}

	@Override
	public boolean deleteDetail(Personal labExamen) {
		return super.removeDetail("personal", labExamen.getId());
	}
	
	@Override
	public boolean deleteDetail(PersonalEspecialidad personalEspecialidad) {
		return super.removeDetail("personalEspecialidad", personalEspecialidad.getId());
	}
	
	@Override
	public void delete(RrHhDiaTurno labExamen) {
		super.deleteReal(labExamen);
		//return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public RrHhDiaTurno create(RrHhDiaTurno especialidad) {
		return super.create(especialidad);
	}

	@Override
	public RrHhDiaTurno update(RrHhDiaTurno especialidad) {
		return super.update(especialidad);
	}

	@Override
	public RrHhDiaTurno registrar(RrHhDiaTurno especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "RrHhDiaTurno "
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
	public RrHhDiaTurno modificar(RrHhDiaTurno especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "RrHhDiaTurno "
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
	public boolean eliminar(RrHhDiaTurno especialidad) {
		try {
			beginTransaction();
			RrHhDiaTurno bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "RrHhDiaTurno "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public RrHhDiaTurno obtenerRrHhDiaTurno(Integer id) {
		return findById(id);
	}

}
