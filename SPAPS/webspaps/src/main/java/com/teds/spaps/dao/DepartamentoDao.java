package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDepartamentoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class DepartamentoDao extends DataAccessObjectJpa<Departamento>
		implements IDepartamentoDao {

	public DepartamentoDao() {
		super(Departamento.class);
	}

	@Override
	public Departamento create(Departamento departamento) {
		return super.create(departamento);
	}

	@Override
	public Departamento update(Departamento departamento) {
		return super.update(departamento);
	}

	@Override
	public Departamento registrar(Departamento departamento) {
		try {
			beginTransaction();
			departamento = create(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Departamento "
					+ departamento.getNombre());
			return departamento;
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
	public Departamento modificar(Departamento departamento) {
		try {
			beginTransaction();
			departamento = update(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Departamento "
					+ departamento.getNombre());
			return departamento;
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
	public boolean eliminar(Departamento departamento) {
		try {
			beginTransaction();
			Departamento bar = update(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Departamento "
					+ departamento.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Departamento obtenerDepartamento(Integer id) {
		return findById(id);
	}

	@Override
	public List<Departamento> obtenerDepartamentoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Departamento> obtenerDepartamentoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Departamento> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<Departamento> obtenerPorPais(String nombre, Pais pais) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "pais", pais.getId());
	}

	@Override
	public List<Departamento> obtenerPorPaisCompaniaAutoComplete(String nombre,
			Pais pais, Compania compania) {
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "pais", pais.getId(), "compania",
				compania.getId(), "estado", "AC");
	}

	@Override
	public List<Departamento> obtenerPorPaisSucursalAutoComplete(String nombre,
			Pais pais, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "pais", pais.getId(), "sucursal",
				sucursal.getId(), "estado", "AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDepartamentoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Departamento> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

}
