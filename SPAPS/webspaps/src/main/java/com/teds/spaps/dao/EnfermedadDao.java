/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IEnfermedadDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Enfermedad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class EnfermedadDao extends DataAccessObjectJpa<Enfermedad> implements
		IEnfermedadDao {

	/**
	 * @param typeT
	 */
	public EnfermedadDao() {
		super(Enfermedad.class);
	}

	@Override
	public Enfermedad create(Enfermedad enfermedad) {
		return super.create(enfermedad);
	}

	@Override
	public Enfermedad update(Enfermedad enfermedad) {
		return super.update(enfermedad);
	}

	@Override
	public Enfermedad registrar(Enfermedad enfermedad) {
		try {
			beginTransaction();
			enfermedad = create(enfermedad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Enfermedad "
					+ enfermedad.toString());
			return enfermedad;
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
	public Enfermedad modificar(Enfermedad enfermedad) {
		try {
			beginTransaction();
			enfermedad = update(enfermedad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Enfermedad "
					+ enfermedad.toString());
			return enfermedad;
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
	public boolean eliminar(Enfermedad enfermedad) {
		try {
			enfermedad.setEstado("RM");
			beginTransaction();
			Enfermedad bar = update(enfermedad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Enfermedad "
					+ enfermedad.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IEnfermedadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Enfermedad> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEnfermedadDao#obtenerEnfermedad(java.lang
	 * .Integer)
	 */
	@Override
	public Enfermedad obtenerEnfermedad(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEnfermedadDao#obtenerEnfermedadOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Enfermedad> obtenerEnfermedadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEnfermedadDao#obtenerEnfermedadOrdenDescPorId
	 * ()
	 */
	@Override
	public List<Enfermedad> obtenerEnfermedadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEnfermedadDao#obtenerPorCompania(com.teds.
	 * spaps.model.Compania)
	 */
	@Override
	public List<Enfermedad> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEnfermedadDao#obtenerPorSucursal(com.teds.
	 * spaps.model.Sucursal)
	 */
	@Override
	public List<Enfermedad> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Enfermedad> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaClinica",
				historiaClinica.getId());
	}

	@Override
	public List<Enfermedad> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEnfermedadDao#onComplete(java.lang.String)
	 */
	@Override
	public List<Enfermedad> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
