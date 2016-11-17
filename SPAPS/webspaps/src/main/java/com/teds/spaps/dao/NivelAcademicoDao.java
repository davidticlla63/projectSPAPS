package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.INivelAcademicoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.NivelAcademico;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class NivelAcademicoDao extends DataAccessObjectJpa<NivelAcademico>
		implements INivelAcademicoDao {

	public NivelAcademicoDao() {
		super(NivelAcademico.class);
	}

	@Override
	public NivelAcademico create(NivelAcademico nivelAcademico) {
		return super.create(nivelAcademico);
	}

	@Override
	public NivelAcademico update(NivelAcademico nivelAcademico) {
		return super.update(nivelAcademico);
	}

	@Override
	public NivelAcademico registrar(NivelAcademico nivelAcademico) {
		try {
			beginTransaction();
			nivelAcademico = create(nivelAcademico);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "NivelAcademico "
					+ nivelAcademico.getDescripcion());
			return nivelAcademico;
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
	public NivelAcademico modificar(NivelAcademico nivelAcademico) {
		try {
			beginTransaction();
			nivelAcademico = update(nivelAcademico);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "NivelAcademico "
					+ nivelAcademico.getDescripcion());
			return nivelAcademico;
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
	public boolean eliminar(NivelAcademico nivelAcademico) {
		try {
			nivelAcademico.setEstado("RM");
			beginTransaction();
			NivelAcademico bar = update(nivelAcademico);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "NivelAcademico "
					+ nivelAcademico.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public NivelAcademico obtenerNivelAcademico(Integer id) {
		return findById(id);
	}

	@Override
	public List<NivelAcademico> obtenerNivelAcademicoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<NivelAcademico> obtenerNivelAcademicoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<NivelAcademico> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.INivelAcademicoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<NivelAcademico> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

}
