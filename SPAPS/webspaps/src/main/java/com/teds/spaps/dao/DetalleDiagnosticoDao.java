/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleDiagnosticoDao extends
		DataAccessObjectJpa<DetalleDiagnostico> implements
		IDetalleDiagnosticoDao {

	/**
	 * @param typeT
	 */
	public DetalleDiagnosticoDao() {
		super(DetalleDiagnostico.class);
	}

	@Override
	public DetalleDiagnostico create(DetalleDiagnostico detalleDiagnostico) {
		return super.create(detalleDiagnostico);
	}

	@Override
	public DetalleDiagnostico update(DetalleDiagnostico detalleDiagnostico) {
		return super.update(detalleDiagnostico);
	}

	@Override
	public void delete(DetalleDiagnostico detalleDiagnostico) {
		super.deleteReal(detalleDiagnostico);
	}

	@Override
	public DetalleDiagnostico registrar(DetalleDiagnostico detalleDiagnostico) {
		try {
			beginTransaction();
			detalleDiagnostico = create(detalleDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleDiagnostico "
					+ detalleDiagnostico.toString());
			return detalleDiagnostico;
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
	public boolean registrar(List<DetalleDiagnostico> diagnosticos) {
		try {
			beginTransaction();
			for (DetalleDiagnostico detalleDiagnostico : diagnosticos) {
				create(detalleDiagnostico);
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
	public DetalleDiagnostico modificar(DetalleDiagnostico detalleDiagnostico) {
		try {
			beginTransaction();
			detalleDiagnostico = update(detalleDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleDiagnostico " + detalleDiagnostico.toString());
			return detalleDiagnostico;
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
	public boolean eliminar(DetalleDiagnostico detalleDiagnostico) {
		try {
			detalleDiagnostico.setEstado("RM");
			beginTransaction();
			DetalleDiagnostico bar = update(detalleDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleDiagnostico "
					+ detalleDiagnostico.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(HistoriaClinica historiaClinica,
			DetalleDiagnostico detalleDiagnostico) {
		List<DetalleDiagnostico> diagnosticos = obtenerPorHM(historiaClinica);
		for (DetalleDiagnostico detalleDiagnostico2 : diagnosticos) {
			if (detalleDiagnostico2.getDiagnostico().equals(
					detalleDiagnostico.getDiagnostico()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleDiagnostico> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#
	 * obtenerDetalleDiagnostico(java.lang.Integer)
	 */
	@Override
	public DetalleDiagnostico obtenerDetalleDiagnostico(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#
	 * obtenerDetalleDiagnosticoOrdenAscPorId()
	 */
	@Override
	public List<DetalleDiagnostico> obtenerDetalleDiagnosticoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#
	 * obtenerDetalleDiagnosticoOrdenDescPorId()
	 */
	@Override
	public List<DetalleDiagnostico> obtenerDetalleDiagnosticoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleDiagnostico> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleDiagnostico> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleDiagnostico> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllByParameterObjectThreeQueryOr("estado", "estado",
				"historiaClinica", "AC", "II", historiaClinica.getId());
	}

	@Override
	public List<DetalleDiagnostico> obtenerPorHC(
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	@Override
	public List<DetalleDiagnostico> obtenerPorDiagnostico(
			Diagnostico diagnostico) {
		return findAllActivosByParameter("diagnostico", diagnostico.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleDiagnosticoDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleDiagnostico> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
