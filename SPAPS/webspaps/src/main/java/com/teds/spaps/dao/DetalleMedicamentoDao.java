/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamento;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleMedicamentoDao extends
		DataAccessObjectJpa<DetalleMedicamento> implements
		IDetalleMedicamentoDao {

	/**
	 * @param typeT
	 */
	public DetalleMedicamentoDao() {
		super(DetalleMedicamento.class);
	}

	@Override
	public DetalleMedicamento create(DetalleMedicamento detalleMedicamento) {
		return super.create(detalleMedicamento);
	}

	@Override
	public DetalleMedicamento update(DetalleMedicamento detalleMedicamento) {
		return super.update(detalleMedicamento);
	}

	@Override
	public void delete(DetalleMedicamento detalleMedicamento) {
		super.deleteReal(detalleMedicamento);
	}

	@Override
	public DetalleMedicamento registrar(DetalleMedicamento detalleMedicamento) {
		try {
			beginTransaction();
			detalleMedicamento = create(detalleMedicamento);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleMedicamento "
					+ detalleMedicamento.toString());
			return detalleMedicamento;
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
	public boolean registrar(List<DetalleMedicamento> medicamentos) {
		try {
			beginTransaction();
			for (DetalleMedicamento detalleMedicamento : medicamentos) {
				create(detalleMedicamento);
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
	public DetalleMedicamento modificar(DetalleMedicamento detalleMedicamento) {
		try {
			beginTransaction();
			detalleMedicamento = update(detalleMedicamento);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleMedicamento " + detalleMedicamento.toString());
			return detalleMedicamento;
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
	public boolean eliminar(DetalleMedicamento detalleMedicamento) {
		try {
			detalleMedicamento.setEstado("RM");
			beginTransaction();
			DetalleMedicamento bar = update(detalleMedicamento);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleMedicamento "
					+ detalleMedicamento.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(HistoriaClinica historiaClinica,
			DetalleMedicamento detalleMedicamento) {
		List<DetalleMedicamento> medicamentos = obtenerPorHM(historiaClinica);
		for (DetalleMedicamento detalleMedicamento2 : medicamentos) {
			if (detalleMedicamento2.getMedicamento().equals(
					detalleMedicamento.getMedicamento()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleMedicamento> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#
	 * obtenerDetalleMedicamento(java.lang.Integer)
	 */
	@Override
	public DetalleMedicamento obtenerDetalleMedicamento(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#
	 * obtenerDetalleMedicamentoOrdenAscPorId()
	 */
	@Override
	public List<DetalleMedicamento> obtenerDetalleMedicamentoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#
	 * obtenerDetalleMedicamentoOrdenDescPorId()
	 */
	@Override
	public List<DetalleMedicamento> obtenerDetalleMedicamentoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleMedicamento> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleMedicamento> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleMedicamento> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllByParameterObjectThreeQueryOr("estado", "estado",
				"historiaClinica", "AC", "II", historiaClinica.getId());
	}

	@Override
	public List<DetalleMedicamento> obtenerPorHC(
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	@Override
	public List<DetalleMedicamento> obtenerPorMedicamento(
			Medicamento medicamento, Compania compania) {
		return findAllByParameterObjectThreeQueryOr("estado", "compania",
				"medicamento", "AC", compania.getId(), medicamento.getId());
	}

	@Override
	public List<DetalleMedicamento> obtenerUltimaReceta(Compania compania) {
		Integer maxId = obtenerMaxID(compania);
		if (maxId > 0) {
			DetalleMedicamento auxiliar = obtenerDetalleMedicamento(maxId);
			return findAllByParameterObjectThreeQueryOr("estado", "compania",
					"receta", "AC", compania.getId(), auxiliar.getReceta());
		} else {
			return new ArrayList<DetalleMedicamento>();
		}
	}

	@Override
	public List<DetalleMedicamento> obtenerPorReceta(Integer receta,
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectThree("receta", "historiaClinica",
				"compania", receta, historiaClinica.getId(), compania.getId());
	}

	@Override
	public List<DetalleMedicamento> obtenerPorMedicamento(
			Medicamento medicamento) {
		return findAllActivosByParameter("medicamento", medicamento.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleMedicamento> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	@Override
	public Integer obtenerMaxID(Compania compania) {
		if (findActiveMax("id", "compania", compania.getId()) == null)
			return 0;
		return (Integer) findActiveMax("id", "compania", compania.getId());
	}

	@Override
	public Integer obtenerMax(Compania compania) {
		if (findActiveMax("receta", "compania", compania.getId()) == null)
			return 1;
		return (Integer) findActiveMax("receta", "compania", compania.getId()) + 1;
	}

}
