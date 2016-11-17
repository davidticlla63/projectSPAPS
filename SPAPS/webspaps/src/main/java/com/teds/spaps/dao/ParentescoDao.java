/**
 * 
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IParentescoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Parentesco;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class ParentescoDao extends DataAccessObjectJpa<Parentesco> implements
		IParentescoDao {

	public ParentescoDao() {
		super(Parentesco.class);
	}

	@Override
	public Parentesco create(Parentesco parentesco) {
		return super.create(parentesco);
	}

	@Override
	public Parentesco update(Parentesco parentesco) {
		return super.update(parentesco);
	}

	@Override
	public Parentesco registrar(Parentesco parentesco) {
		try {
			beginTransaction();
			parentesco = create(parentesco);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Parentesco "
					+ parentesco.toString());
			return parentesco;
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
	public Parentesco modificar(Parentesco parentesco) {
		try {
			beginTransaction();
			parentesco = update(parentesco);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Parentesco "
					+ parentesco.toString());
			return parentesco;
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
	public boolean eliminar(Parentesco parentesco) {
		try {
			parentesco.setEstado("RM");
			beginTransaction();
			Parentesco bar = update(parentesco);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Parentesco "
					+ parentesco.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Parentesco obtenerParentesco(Integer id) {
		return findById(id);
	}

	@Override
	public Parentesco obtenerParentesco(String nombre, Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", nombre,
				compania.getId());
	}

	@Override
	public List<Parentesco> obtenerParentescoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Parentesco> obtenerParentescoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Parentesco> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	@Override
	public List<Parentesco> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa);
	}

	@Override
	public List<Parentesco> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IParentescoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Parentesco> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<Parentesco> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	@Override
	public List<Parentesco> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Parentesco> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}
}
