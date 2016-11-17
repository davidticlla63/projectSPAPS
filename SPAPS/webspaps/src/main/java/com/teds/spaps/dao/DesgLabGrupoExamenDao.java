/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgLabGrupoExamenDao extends
		DataAccessObjectJpa<DesgLabGrupoExamen> implements
		IDesgLabGrupoExamenDao {

	/**
	 * @param typeT
	 */
	public DesgLabGrupoExamenDao() {
		super(DesgLabGrupoExamen.class);
	}

	@Override
	public DesgLabGrupoExamen create(DesgLabGrupoExamen detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DesgLabGrupoExamen update(DesgLabGrupoExamen detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DesgLabGrupoExamen detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DesgLabGrupoExamen registrar(DesgLabGrupoExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgLabGrupoExamen "
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
	public boolean registrar(List<DesgLabGrupoExamen> antecedentes) {
		try {
			beginTransaction();
			for (DesgLabGrupoExamen detalleAntecedente : antecedentes) {
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
	public DesgLabGrupoExamen modificar(DesgLabGrupoExamen detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DesgLabGrupoExamen " + detalleAntecedente.toString());
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
	public boolean eliminar(DesgLabGrupoExamen detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DesgLabGrupoExamen bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgLabGrupoExamen "
					+ detalleAntecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgLabGrupoExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#
	 * obtenerDesgLabGrupoExamen(java.lang.Integer)
	 */
	@Override
	public DesgLabGrupoExamen obtenerDesgLabGrupoExamen(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#
	 * obtenerDesgLabGrupoExamenOrdenAscPorId()
	 */
	@Override
	public List<DesgLabGrupoExamen> obtenerDesgLabGrupoExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#
	 * obtenerDesgLabGrupoExamenOrdenDescPorId()
	 */
	@Override
	public List<DesgLabGrupoExamen> obtenerDesgLabGrupoExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgLabGrupoExamen> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgLabGrupoExamen> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public boolean verificarExamenRegistrado(LabExamen examen, Sucursal sucursal) {
		List<DesgLabGrupoExamen> examenes = obtenerPorSucursal(sucursal);
		for (DesgLabGrupoExamen desgLabGrupoExamen : examenes) {
			if (desgLabGrupoExamen.getExamen().equals(examen))
				return true;
		}
		return false;
	}

	@Override
	public List<DesgLabGrupoExamen> obtenerPorExamen(LabExamen examen) {
		return findAllActivosByParameter("examen", examen.getId());
	}

	@Override
	public List<DesgLabGrupoExamen> obtenerPorGrupo(DesgLabGrupo grupo) {
		return findAllActivosByParameter("grupo", grupo.getId());
	}

	@Override
	public List<LabExamen> obtenerExamenesPorGrupo(DesgLabGrupo grupo) {
		List<LabExamen> resultado = new ArrayList<LabExamen>();
		List<DesgLabGrupoExamen> list = obtenerPorGrupo(grupo);
		for (DesgLabGrupoExamen desgLabGrupoExamen : list) {
			resultado.add(desgLabGrupoExamen.getExamen());
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DesgLabGrupoExamen> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
