/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IConsultaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class ConsultaDao extends DataAccessObjectJpa<Consulta> implements
		IConsultaDao {

	/**
	 * @param typeT
	 */
	public ConsultaDao() {
		super(Consulta.class);
	}

	@Override
	public Consulta create(Consulta consulta) {
		return super.create(consulta);
	}

	@Override
	public Consulta update(Consulta consulta) {
		return super.update(consulta);
	}

	@Override
	public void delete(Consulta consulta) {
		super.deleteReal(consulta);
	}

	@Override
	public Consulta registrar(Consulta consulta) {
		try {
			beginTransaction();
			consulta = create(consulta);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Consulta " + consulta.toString());
			return consulta;
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
	public boolean registrar(List<Consulta> antecedentes) {
		try {
			beginTransaction();
			for (Consulta consulta : antecedentes) {
				create(consulta);
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
	public Consulta modificar(Consulta consulta) {
		try {
			beginTransaction();
			consulta = update(consulta);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Consulta "
					+ consulta.toString());
			return consulta;
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
	public boolean eliminar(Consulta consulta) {
		try {
			consulta.setEstado("RM");
			beginTransaction();
			Consulta bar = update(consulta);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Consulta "
					+ consulta.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Consulta> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#
	 * obtenerConsulta(java.lang.Integer)
	 */
	@Override
	public Consulta obtenerConsulta(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#
	 * obtenerConsultaOrdenAscPorId()
	 */
	@Override
	public List<Consulta> obtenerConsultaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#
	 * obtenerConsultaOrdenDescPorId()
	 */
	@Override
	public List<Consulta> obtenerConsultaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<Consulta> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Consulta> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Consulta> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaClinica",
				historiaClinica.getId());
	}

	@Override
	public List<Consulta> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "AT",
				historiaClinica.getId(), compania.getId());
	}

	@Override
	public Consulta obtenerReConsulta(Paciente paciente, Compania compania) {
		Integer code = (int) findActiveMax("id", "paciente", paciente.getId(),
				"compania", compania.getId());
		return obtenerConsulta(code);
	}

	@Override
	public boolean tieneConsultaPaciente(Paciente paciente, Compania compania) {
		return (long) findCountConsulta("id", "paciente", "compania",
				paciente.getId(), compania.getId()) > 0;
	}

	@Override
	public Consulta obtenerConsultaActualDePaciente(Paciente paciente,
			Compania compania) {
		return findByParameterObjectThree("estado", "paciente", "compania",
				"AT", paciente.getId(), compania.getId());
	}

	@Override
	public List<Consulta> obtenerPorPaciente(Paciente paciente) {
		return findAllActivosByParameter("paciente", paciente.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IConsultaDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<Consulta> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
