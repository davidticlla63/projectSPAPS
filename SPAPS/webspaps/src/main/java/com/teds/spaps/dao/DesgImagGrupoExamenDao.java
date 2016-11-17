/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgImagGrupoExamenDao extends
		DataAccessObjectJpa<DesgImagGrupoExamen> implements
		IDesgImagGrupoExamenDao {

	/**
	 * @param typeT
	 */
	public DesgImagGrupoExamenDao() {
		super(DesgImagGrupoExamen.class);
	}

	@Override
	public DesgImagGrupoExamen create(DesgImagGrupoExamen detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DesgImagGrupoExamen update(DesgImagGrupoExamen detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DesgImagGrupoExamen detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DesgImagGrupoExamen registrar(DesgImagGrupoExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgImagGrupoExamen "
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
	public boolean registrar(List<DesgImagGrupoExamen> antecedentes) {
		try {
			beginTransaction();
			for (DesgImagGrupoExamen detalleAntecedente : antecedentes) {
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
	public DesgImagGrupoExamen modificar(DesgImagGrupoExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DesgImagGrupoExamen " + detalleAntecedente.toString());
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
	public boolean eliminar(DesgImagGrupoExamen detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DesgImagGrupoExamen bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"DesgImagGrupoExamen " + detalleAntecedente.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarExamenRegistrado(LabExamenImag examen,
			Sucursal sucursal) {
		List<DesgImagGrupoExamen> examenes = obtenerPorSucursal(sucursal);
		for (DesgImagGrupoExamen desgLabGrupoExamen : examenes) {
			if (desgLabGrupoExamen.getExamen().equals(examen))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgImagGrupoExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#
	 * obtenerDesgImagGrupoExamen(java.lang.Integer)
	 */
	@Override
	public DesgImagGrupoExamen obtenerDesgImagGrupoExamen(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#
	 * obtenerDesgImagGrupoExamenOrdenAscPorId()
	 */
	@Override
	public List<DesgImagGrupoExamen> obtenerDesgImagGrupoExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#
	 * obtenerDesgImagGrupoExamenOrdenDescPorId()
	 */
	@Override
	public List<DesgImagGrupoExamen> obtenerDesgImagGrupoExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgImagGrupoExamen> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgImagGrupoExamen> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DesgImagGrupoExamen> obtenerPorExamen(LabExamenImag examen) {
		return findAllActivosByParameter("examen", examen.getId());
	}

	@Override
	public List<DesgImagGrupoExamen> obtenerPorGrupo(DesgImagGrupo grupo) {
		return findAllActivosByParameter("grupo", grupo.getId());
	}

	@Override
	public List<LabExamenImag> obtenerExamenesPorGrupo(DesgImagGrupo grupo) {
		List<LabExamenImag> resultado = new ArrayList<LabExamenImag>();
		List<DesgImagGrupoExamen> list = obtenerPorGrupo(grupo);
		for (DesgImagGrupoExamen desgLabGrupoExamen : list) {
			resultado.add(desgLabGrupoExamen.getExamen());
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DesgImagGrupoExamen> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
