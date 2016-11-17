/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.DiagnosticoPresuntivo;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DiagnosticoPresuntivoDao extends
		DataAccessObjectJpa<DiagnosticoPresuntivo> implements
		IDiagnosticoPresuntivoDao {

	/**
	 * @param typeT
	 */
	public DiagnosticoPresuntivoDao() {
		super(DiagnosticoPresuntivo.class);
	}

	@Override
	public DiagnosticoPresuntivo create(
			DiagnosticoPresuntivo diagnosticoPresuntivo) {
		return super.create(diagnosticoPresuntivo);
	}

	@Override
	public DiagnosticoPresuntivo update(
			DiagnosticoPresuntivo diagnosticoPresuntivo) {
		return super.update(diagnosticoPresuntivo);
	}

	@Override
	public DiagnosticoPresuntivo registrar(
			DiagnosticoPresuntivo diagnosticoPresuntivo) {
		try {
			beginTransaction();
			diagnosticoPresuntivo = create(diagnosticoPresuntivo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DiagnosticoPresuntivo "
					+ diagnosticoPresuntivo.toString());
			return diagnosticoPresuntivo;
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
	public DiagnosticoPresuntivo modificar(
			DiagnosticoPresuntivo diagnosticoPresuntivo) {
		try {
			beginTransaction();
			diagnosticoPresuntivo = update(diagnosticoPresuntivo);
			commitTransaction();
			FacesUtil
					.infoMessage(
							"Modificación Correcta",
							"DiagnosticoPresuntivo "
									+ diagnosticoPresuntivo.toString());
			return diagnosticoPresuntivo;
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
	public boolean eliminar(DiagnosticoPresuntivo diagnosticoPresuntivo) {
		try {
			diagnosticoPresuntivo.setEstado("RM");
			beginTransaction();
			DiagnosticoPresuntivo bar = update(diagnosticoPresuntivo);
			commitTransaction();
			FacesUtil
					.infoMessage(
							"Eliminación Correcta",
							"DiagnosticoPresuntivo "
									+ diagnosticoPresuntivo.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DiagnosticoPresuntivo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#
	 * obtenerDiagnosticoPresuntivo(java.lang.Integer)
	 */
	@Override
	public DiagnosticoPresuntivo obtenerDiagnosticoPresuntivo(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#
	 * obtenerDiagnosticoPresuntivoOrdenAscPorId()
	 */
	@Override
	public List<DiagnosticoPresuntivo> obtenerDiagnosticoPresuntivoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#
	 * obtenerDiagnosticoPresuntivoOrdenDescPorId()
	 */
	@Override
	public List<DiagnosticoPresuntivo> obtenerDiagnosticoPresuntivoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DiagnosticoPresuntivo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DiagnosticoPresuntivo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DiagnosticoPresuntivo> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica) {
		return findAllActiveByParameter("historiaClinica",
				historiaClinica.getId());
	}

	@Override
	public boolean verificarParaEvolucion(HistoriaClinica historiaClinica,
			Compania compania, Consulta consulta) {
		return (Long) (findCountParaEvolucion("id", "historiaClinica",
				"compania", "consulta", historiaClinica.getId(),
				compania.getId(), consulta.getId())) > 0;
	}

	@Override
	public DiagnosticoPresuntivo obtenerParaEvolucion(
			HistoriaClinica historiaClinica, Compania compania,
			Consulta consulta) {
		return findByParameterObjectFour("historiaClinica", "compania",
				"consulta", "estado", historiaClinica.getId(),
				compania.getId(), consulta.getId(), "AC");
	}

	@Override
	public List<DiagnosticoPresuntivo> obtenerPorHC(
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoPresuntivoDao#onComplete(java
	 * .lang.String)
	 */
	@Override
	public List<DiagnosticoPresuntivo> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
