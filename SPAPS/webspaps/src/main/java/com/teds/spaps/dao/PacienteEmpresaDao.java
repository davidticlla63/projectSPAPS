/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IPacienteEmpresaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class PacienteEmpresaDao extends DataAccessObjectJpa<PacienteEmpresa>
		implements IPacienteEmpresaDao {

	/**
	 * @param typeT
	 */
	public PacienteEmpresaDao() {
		super(PacienteEmpresa.class);
	}

	@Override
	public PacienteEmpresa create(PacienteEmpresa detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public PacienteEmpresa update(PacienteEmpresa detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(PacienteEmpresa detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public PacienteEmpresa registrar(PacienteEmpresa detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "PacienteEmpresa "
					+ detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean registrar(List<PacienteEmpresa> antecedentes) {
		try {
			beginTransaction();
			for (PacienteEmpresa detalleAntecedente : antecedentes) {
				create(detalleAntecedente);
			}
			commitTransaction();
			return true;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public PacienteEmpresa modificar(PacienteEmpresa detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "PacienteEmpresa "
					+ detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean eliminar(PacienteEmpresa detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			PacienteEmpresa bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "PacienteEmpresa "
					+ detalleAntecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<PacienteEmpresa> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#
	 * obtenerPacienteEmpresa(java.lang.Integer)
	 */
	@Override
	public PacienteEmpresa obtenerPacienteEmpresa(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#
	 * obtenerPacienteEmpresaOrdenAscPorId()
	 */
	@Override
	public List<PacienteEmpresa> obtenerPacienteEmpresaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#
	 * obtenerPacienteEmpresaOrdenDescPorId()
	 */
	@Override
	public List<PacienteEmpresa> obtenerPacienteEmpresaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<PacienteEmpresa> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<PacienteEmpresa> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<PacienteEmpresa> obtenerPorPaciente(Paciente paciente) {
		return findAllActivosByParameter("paciente", paciente.getId());
	}

	@Override
	public List<PacienteEmpresa> obtenerPorEmpresa(Compania empresa) {
		return findAllActivosByParameter("empresa", empresa.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPacienteEmpresaDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<PacienteEmpresa> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
