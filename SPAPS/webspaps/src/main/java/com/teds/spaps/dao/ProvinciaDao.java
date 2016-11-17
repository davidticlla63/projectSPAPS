package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IProvinciaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.Provincia;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class ProvinciaDao extends DataAccessObjectJpa<Provincia> implements
		IProvinciaDao {

	public ProvinciaDao() {
		super(Provincia.class);
	}

	@Override
	public Provincia create(Provincia provincia) {
		return super.create(provincia);
	}

	@Override
	public Provincia update(Provincia provincia) {
		return super.update(provincia);
	}

	@Override
	public Provincia registrar(Provincia provincia) {
		try {
			beginTransaction();
			provincia = create(provincia);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Provincia " + provincia.getNombre());
			return provincia;
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
	public Provincia modificar(Provincia provincia) {
		try {
			beginTransaction();
			provincia = update(provincia);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Provincia "
					+ provincia.getNombre());
			return provincia;
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
	public boolean eliminar(Provincia provincia) {
		try {
			provincia.setEstado("RM");
			beginTransaction();
			Provincia bar = modificar(provincia);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Provincia "
					+ provincia.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Provincia obtenerProvincia(Integer id) {
		return findById(id);
	}

	@Override
	public List<Provincia> obtenerProvinciaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Provincia> obtenerProvinciaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Provincia> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Provincia> obtenerPorDepartamento(String nombre,
			Departamento departamento) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", departamento.getId());
	}

	@Override
	public List<Provincia> obtenerPorDepartamentoCompaniaAutoComplete(
			String nombre, Departamento departamento, Compania compania) {
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "departamento", departamento.getId(),
				"compania", compania.getId(), "estado", "AC");
	}

	@Override
	public List<Provincia> obtenerPorDepartamentoSucursalAutoComplete(
			String nombre, Departamento departamento, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "departamento", departamento.getId(),
				"sucursal", sucursal.getId(), "estado", "AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IProvinciaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Provincia> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IProvinciaDao#
	 * obtenerPorDepartamentoCompaniaAutoComplete(java.lang.String,
	 * com.teds.spaps.model.Compania)
	 */
	@Override
	public List<Provincia> obtenerPorDepartamentoCompaniaAutoComplete(
			String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IProvinciaDao#
	 * obtenerPorDepartamentoSucursalAutoComplete(java.lang.String,
	 * com.teds.spaps.model.Sucursal)
	 */
	@Override
	public List<Provincia> obtenerPorDepartamentoSucursalAutoComplete(
			String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
