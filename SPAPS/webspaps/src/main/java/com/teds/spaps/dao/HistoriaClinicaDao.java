/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IHistoriaClinicaDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class HistoriaClinicaDao extends DataAccessObjectJpa<HistoriaClinica>
		implements IHistoriaClinicaDao {

	private @Inject IPacienteDao pacienteDao;

	/**
	 * @param typeT
	 */
	public HistoriaClinicaDao() {
		super(HistoriaClinica.class);
	}

	@Override
	public HistoriaClinica create(HistoriaClinica historiaClinica) {
		return super.create(historiaClinica);
	}

	@Override
	public HistoriaClinica update(HistoriaClinica historiaClinica) {
		return super.update(historiaClinica);
	}

	@Override
	public HistoriaClinica registrar(HistoriaClinica historiaClinica) {
		try {
			beginTransaction();
			historiaClinica = create(historiaClinica);
			Paciente paciente = historiaClinica.getPaciente();
			paciente.setCodigoHc(historiaClinica.getCodigo());
			paciente = pacienteDao.update(paciente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "HistoriaMedica "
					+ historiaClinica.toString());
			return historiaClinica;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				System.err.println("Error en modificar : " + e.getStackTrace());
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public HistoriaClinica modificar(HistoriaClinica historiaClinica) {
		try {
			beginTransaction();
			historiaClinica = update(historiaClinica);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "HistoriaMedica "
					+ historiaClinica.toString());
			return historiaClinica;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				System.err.println("Error en modificar : " + e.getMessage());
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(HistoriaClinica historiaClinica) {
		try {
			historiaClinica.setEstado("RM");
			beginTransaction();
			HistoriaClinica bar = update(historiaClinica);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "HistoriaMedica "
					+ historiaClinica.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<HistoriaClinica> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#obtenerHistoriaMedica
	 * (java.lang.Integer)
	 */
	@Override
	public HistoriaClinica obtenerHistoriaMedica(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#
	 * obtenerHistoriaMedicaOrdenAscPorId()
	 */
	@Override
	public List<HistoriaClinica> obtenerHistoriaMedicaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#
	 * obtenerHistoriaMedicaOrdenDescPorId()
	 */
	@Override
	public List<HistoriaClinica> obtenerHistoriaMedicaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#obtenerPorCompania(com
	 * .teds. spaps.model.Compania)
	 */
	@Override
	public List<HistoriaClinica> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#obtenerPorCompania(com
	 * .teds. spaps.model.Compania)
	 */
	@Override
	public HistoriaClinica obtenerPorPaciente(Paciente paciente) {
		return findByParameter("paciente", paciente.getId());
	}

	@Override
	public HistoriaClinica obtenerPorCodigo(String codigo, Compania compania) {
		return findByParameterObjectThree("codigo", "compania", "estado",
				codigo, compania.getId(), "AC");
	}

	@Override
	public boolean verificarHC(Paciente paciente) {
		return (Long) (findCount("id", "paciente", paciente.getId())) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#obtenerPorSucursal(com
	 * .teds. spaps.model.Sucursal)
	 */
	@Override
	public List<HistoriaClinica> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IHistoriaMedicaDao#onComplete(java.lang
	 * .String)
	 */
	@Override
	public List<HistoriaClinica> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	@Override
	public List<HistoriaClinica> obtenerAutoComplete(String query,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("codigo",
				query, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public Integer findActiveCodigoMax(Paciente paciente, Compania compania) {
		Object obj = findActiveMax("correlativo", "paciente", paciente.getId(),
				"compania", compania.getId());
		System.out.println("codigo : " + obj);
		if (obj == null) {
			return 1;
		} else {
			return (int) obj + 1;
		}
	}

}
