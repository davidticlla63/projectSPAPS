package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class EmpresaDao extends DataAccessObjectJpa<Empresa> implements
		IEmpresaDao {

	public EmpresaDao() {
		super(Empresa.class);
	}

	@Override
	public Empresa create(Empresa empresa) {
		return super.create(empresa);
	}

	@Override
	public Empresa update(Empresa empresa) {
		return super.update(empresa);
	}

	@Override
	public Empresa registrar(Empresa empresa) {
		try {
			beginTransaction();
			empresa = create(empresa);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Empresa " + empresa.getDescripcion());
			return empresa;
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
	public Empresa modificar(Empresa empresa) {
		try {
			beginTransaction();
			empresa = update(empresa);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Empresa " + empresa.getDescripcion());
			return empresa;
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
	public boolean eliminar(Empresa empresa) {
		try {
			empresa.setEstado("RM");
			beginTransaction();
			Empresa bar = update(empresa);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Empresa " + empresa.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Empresa obtenerEmpresa(Integer id) {
		return findById(id);
	}

	@Override
	public Empresa obtenerEmpresa(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<Empresa> obtenerEmpresaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Empresa> obtenerEmpresaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Empresa> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Empresa> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Empresa> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Empresa> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IEmpresaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Empresa> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<Empresa> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	@Override
	public List<Empresa> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Empresa> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	@Override
	public Empresa DevolverEmpresaPorId(Integer id) {
		String query = "SELECT NEW com.teds.spaps.model.Empresa(p.id, p.descripcion)FROM Empresa AS p where p.id="
				+ id;
		Query q = getEntityManager().createQuery(query);
		System.out.println(q.getSingleResult());
		return (Empresa) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresa> devolverEmpresaOnCompleteCompania(Compania compania,
			String nombre) {
		String query = "select NEW com.teds.spaps.model.Empresa(p.id, p.descripcion)from Empresa p where upper(translate(p.descripcion, 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ nombre + "%'";
		Query q = getEntityManager().createQuery(query);
		return (List<Empresa>) q.getResultList();
	}
}
