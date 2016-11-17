/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
public class IdentificacionIndividuoDao extends
		DataAccessObjectJpa<IdentificacionPaciente> implements
		IIdentificacionIndividuoDao {

	public IdentificacionIndividuoDao() {
		super(IdentificacionPaciente.class);
	}

	@Override
	public IdentificacionPaciente create(
			IdentificacionPaciente identificacionPaciente) {
		return super.create(identificacionPaciente);
	}

	@Override
	public IdentificacionPaciente update(
			IdentificacionPaciente identificacionPaciente) {
		return super.update(identificacionPaciente);
	}

	@Override
	public void delete(IdentificacionPaciente identificacionPaciente) {
		super.deleteReal(identificacionPaciente);
	}

	@Override
	public IdentificacionPaciente registrar(
			IdentificacionPaciente identificacionPaciente) {
		try {
			beginTransaction();
			identificacionPaciente = create(identificacionPaciente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Registro Correcto",
					"Identificacion del paciente "
							+ identificacionPaciente.toString());
			return identificacionPaciente;
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
	public IdentificacionPaciente modificar(
			IdentificacionPaciente identificacionPaciente) {
		try {
			beginTransaction();
			identificacionPaciente = update(identificacionPaciente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"Identificacion del paciente "
							+ identificacionPaciente.toString());
			return identificacionPaciente;
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
	public boolean eliminar(IdentificacionPaciente identificacionPaciente) {
		try {
			identificacionPaciente.setEstado("RM");
			beginTransaction();
			IdentificacionPaciente bar = update(identificacionPaciente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"Identificacion del paciente "
							+ identificacionPaciente.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean deletePS(IdentificacionPaciente identificacionPaciente) {
		try {
			identificacionPaciente.setEstado("RM");
			beginTransaction();
			deleteReal(identificacionPaciente);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"Identificacion del paciente "
							+ identificacionPaciente.toString());
			return true;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public IdentificacionPaciente obtenerIdentificacionIndividuo(Integer id) {
		return findById(id);
	}

	@Override
	public IdentificacionPaciente obtenrIdentificacionIndividuo(Integer id,
			Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	@Override
	public IdentificacionPaciente obtenerIdentificacionIndividuo(
			String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public IdentificacionPaciente obtenerIdentificacionIndividuo(
			String descripcion, Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	@Override
	public IdentificacionPaciente obtenerIdentificacionIndividuoPorDescripcion(
			String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public IdentificacionPaciente obtenerIdentificacionIndividuoPorDescripcion(
			String descripcion, Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	@Override
	public List<IdentificacionPaciente> obtenerIdentificacionIndividuoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<IdentificacionPaciente> obtenerIdentificacionIndividuoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<IdentificacionPaciente> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<IdentificacionPaciente> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<IdentificacionPaciente> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<IdentificacionPaciente> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<IdentificacionPaciente> obtenerPorIndividuo(Paciente paciente) {
		return findAllByParameter("paciente", paciente.getId());
	}

	@Override
	public List<IdentificacionPaciente> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public boolean verificarDocumento(Sucursal sucursal,
			IdentificacionPaciente identificacionPaciente) {
		List<IdentificacionPaciente> listado = obtenerPorSucursal(sucursal);
		for (int i = 0; i < listado.size(); i++) {
			if (listado.get(i).getDescripcion()
					.equals(identificacionPaciente.getDescripcion())
					&& listado
							.get(i)
							.getDocumentoIdentidad()
							.equals(identificacionPaciente
									.getDocumentoIdentidad()))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarDocumento(Compania compania,
			IdentificacionPaciente identificacionPaciente) {
		List<IdentificacionPaciente> listado = obtenerPorCompania(compania);
		for (int i = 0; i < listado.size(); i++) {
			if (listado.get(i).getDescripcion()
					.equals(identificacionPaciente.getDescripcion())
					&& listado
							.get(i)
							.getDocumentoIdentidad()
							.equals(identificacionPaciente
									.getDocumentoIdentidad()))
				return true;
		}
		return false;
	}

}
