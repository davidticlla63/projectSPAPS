/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoDiagnostico;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleTipoDiagnosticoDao extends
		DataAccessObjectJpa<DetalleTipoDiagnostico> implements
		IDetalleTipoDiagnosticoDao {

	/**
	 * @param typeT
	 */
	public DetalleTipoDiagnosticoDao() {
		super(DetalleTipoDiagnostico.class);
	}

	@Override
	public DetalleTipoDiagnostico create(
			DetalleTipoDiagnostico detalleTipoDiagnostico) {
		return super.create(detalleTipoDiagnostico);
	}

	@Override
	public DetalleTipoDiagnostico update(
			DetalleTipoDiagnostico detalleTipoDiagnostico) {
		return super.update(detalleTipoDiagnostico);
	}

	@Override
	public void delete(DetalleTipoDiagnostico detalleTipoDiagnostico) {
		super.deleteReal(detalleTipoDiagnostico);
	}

	@Override
	public DetalleTipoDiagnostico registrar(
			DetalleTipoDiagnostico detalleTipoDiagnostico) {
		try {
			beginTransaction();
			detalleTipoDiagnostico = create(detalleTipoDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage(
					"Registro Correcto",
					"DetalleTipoDiagnostico "
							+ detalleTipoDiagnostico.toString());
			return detalleTipoDiagnostico;
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
	public DetalleTipoDiagnostico modificar(
			DetalleTipoDiagnostico detalleTipoDiagnostico) {
		try {
			beginTransaction();
			detalleTipoDiagnostico = update(detalleTipoDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"DetalleTipoDiagnostico "
							+ detalleTipoDiagnostico.toString());
			return detalleTipoDiagnostico;
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
	public boolean eliminar(DetalleTipoDiagnostico detalleTipoDiagnostico) {
		try {
			detalleTipoDiagnostico.setEstado("RM");
			beginTransaction();
			DetalleTipoDiagnostico bar = update(detalleTipoDiagnostico);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"DetalleTipoDiagnostico "
							+ detalleTipoDiagnostico.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleTipoDiagnostico> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#
	 * obtenerDetalleTipoDiagnostico(java.lang.Integer)
	 */
	@Override
	public DetalleTipoDiagnostico obtenerDetalleTipoDiagnostico(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#
	 * obtenerDetalleTipoDiagnosticoOrdenAscPorId()
	 */
	@Override
	public List<DetalleTipoDiagnostico> obtenerDetalleTipoDiagnosticoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#
	 * obtenerDetalleTipoDiagnosticoOrdenDescPorId()
	 */
	@Override
	public List<DetalleTipoDiagnostico> obtenerDetalleTipoDiagnosticoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleTipoDiagnostico> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleTipoDiagnostico> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleTipoDiagnostico> obtenerPorDiagnostico(
			Diagnostico diagnostico) {
		return findAllActivosByParameter("diagnostico", diagnostico.getId());
	}

	@Override
	public List<DetalleTipoDiagnostico> obtenerPorTipoDiagnostico(
			TipoDiagnostico tipoDiagnostico) {
		return findAllActivosByParameter("tipoDiagnostico",
				tipoDiagnostico.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleTipoDiagnostico> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
