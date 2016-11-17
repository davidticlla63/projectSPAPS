/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IMotivoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class MotivoDao extends DataAccessObjectJpa<Motivo> implements
		IMotivoDao {

	/**
	 * @param typeT
	 */
	public MotivoDao() {
		super(Motivo.class);
	}

	@Override
	public Motivo create(Motivo motivo) {
		return super.create(motivo);
	}

	@Override
	public Motivo update(Motivo motivo) {
		return super.update(motivo);
	}

	@Override
	public Motivo registrar(Motivo motivo) {
		try {
			beginTransaction();
			motivo = create(motivo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Motivo " + motivo.toString());
			return motivo;
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
	public Motivo modificar(Motivo motivo) {
		try {
			beginTransaction();
			motivo = update(motivo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Motivo " + motivo.toString());
			return motivo;
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
	public boolean eliminar(Motivo motivo) {
		try {
			motivo.setEstado("RM");
			beginTransaction();
			Motivo bar = update(motivo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Motivo " + motivo.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IMotivoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Motivo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMotivoDao#obtenerMotivo(java.lang.Integer)
	 */
	@Override
	public Motivo obtenerMotivo(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMotivoDao#obtenerMotivoOrdenAscPorId()
	 */
	@Override
	public List<Motivo> obtenerMotivoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMotivoDao#obtenerMotivoOrdenDescPorId()
	 */
	@Override
	public List<Motivo> obtenerMotivoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMotivoDao#obtenerPorCompania(com.teds.
	 * spaps.model.Compania)
	 */
	@Override
	public List<Motivo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMotivoDao#obtenerPorSucursal(com.teds.
	 * spaps.model.Sucursal)
	 */
	@Override
	public List<Motivo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Motivo> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica) {
		return findAllActiveByParameter("historiaClinica",
				historiaClinica.getId());
	}

	@Override
	public List<Motivo> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMotivoDao#onComplete(java.lang.String)
	 */
	@Override
	public List<Motivo> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
