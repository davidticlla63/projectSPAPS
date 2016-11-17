/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamentoPresentacion;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Presentacion;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleMedicamentoPresentacionDao extends
		DataAccessObjectJpa<DetalleMedicamentoPresentacion> implements
		IDetalleMedicamentoPresentacionDao {

	/**
	 * @param typeT
	 */
	public DetalleMedicamentoPresentacionDao() {
		super(DetalleMedicamentoPresentacion.class);
	}

	@Override
	public DetalleMedicamentoPresentacion create(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		return super.create(detalleMedicamentoPresentacion);
	}

	@Override
	public DetalleMedicamentoPresentacion update(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		return super.update(detalleMedicamentoPresentacion);
	}

	@Override
	public void delete(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		super.deleteReal(detalleMedicamentoPresentacion);
	}

	@Override
	public DetalleMedicamentoPresentacion registrar(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		try {
			beginTransaction();
			detalleMedicamentoPresentacion = create(detalleMedicamentoPresentacion);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"DetalleMedicamentoPresentacion "
							+ detalleMedicamentoPresentacion.toString());
			return detalleMedicamentoPresentacion;
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
	public boolean registrar(List<DetalleMedicamentoPresentacion> presentaciones) {
		try {
			beginTransaction();
			for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion : presentaciones) {
				create(detalleMedicamentoPresentacion);
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
	public DetalleMedicamentoPresentacion modificar(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		try {
			beginTransaction();
			detalleMedicamentoPresentacion = update(detalleMedicamentoPresentacion);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleMedicamentoPresentacion "
							+ detalleMedicamentoPresentacion.toString());
			return detalleMedicamentoPresentacion;
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
	public boolean eliminar(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		try {
			detalleMedicamentoPresentacion.setEstado("RM");
			beginTransaction();
			DetalleMedicamentoPresentacion bar = update(detalleMedicamentoPresentacion);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"DetalleMedicamentoPresentacion "
							+ detalleMedicamentoPresentacion.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleMedicamentoPresentacion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#
	 * obtenerDetalleMedicamentoPresentacion(java.lang.Integer)
	 */
	@Override
	public DetalleMedicamentoPresentacion obtenerDetalleMedicamentoPresentacion(
			Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#
	 * obtenerDetalleMedicamentoPresentacionOrdenAscPorId()
	 */
	@Override
	public List<DetalleMedicamentoPresentacion> obtenerDetalleMedicamentoPresentacionOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#
	 * obtenerDetalleMedicamentoPresentacionOrdenDescPorId()
	 */
	@Override
	public List<DetalleMedicamentoPresentacion> obtenerDetalleMedicamentoPresentacionOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#
	 * obtenerPorCompania (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleMedicamentoPresentacion> obtenerPorCompania(
			Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#
	 * obtenerPorPresentacion (com.teds. spaps.model.Presentacion)
	 */
	@Override
	public List<DetalleMedicamentoPresentacion> obtenerPorPresentacion(
			Presentacion presentacion, Compania compania) {
		return findAllActivosByParameterTwo("presentacion", "compania",
				presentacion.getId(), compania.getId());
	}

	@Override
	public List<DetalleMedicamentoPresentacion> obtenerPorSucursalRegistro(
			Sucursal sucursal) {
		return findAllActivosByParameter("sucursalRegistro", sucursal.getId());
	}

	@Override
	public List<DetalleMedicamentoPresentacion> obtenerPorMedicamento(
			Medicamento medicamento, Compania compania) {
		return findAllActivosByParameterTwo("medicamento", "compania",
				medicamento.getId(), compania.getId());
	}

	@Override
	public boolean verificarRepetido(Compania compania,
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		List<DetalleMedicamentoPresentacion> presentaciones = obtenerPorCompania(compania);
		for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion2 : presentaciones) {
			if (detalleMedicamentoPresentacion2.getPresentacion().equals(
					detalleMedicamentoPresentacion.getPresentacion()))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Compania compania,
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		List<DetalleMedicamentoPresentacion> presentaciones = obtenerPorCompania(compania);
		for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion2 : presentaciones) {
			if (detalleMedicamentoPresentacion2
					.equals(detalleMedicamentoPresentacion))
				return false;
			if (detalleMedicamentoPresentacion2.getPresentacion().equals(
					detalleMedicamentoPresentacion.getPresentacion()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao#onComplete
	 * (java. lang.String)
	 */
	@Override
	public List<DetalleMedicamentoPresentacion> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
