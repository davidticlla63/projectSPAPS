package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ICargoDao;
import com.teds.spaps.model.Cargo;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class CargoDao extends DataAccessObjectJpa<Cargo> implements ICargoDao {

	public CargoDao() {
		super(Cargo.class);
	}

	@Override
	public Cargo registrar(Cargo cargo) {
		try {
			beginTransaction();
			cargo = create(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Cargo " + cargo.getDescripcion());
			return cargo;
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
	public Cargo modificar(Cargo cargo) {
		try {
			beginTransaction();
			cargo = update(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Cargo " + cargo.getDescripcion());
			return cargo;
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
	public boolean eliminar(Cargo cargo) {
		try {
			beginTransaction();
			Cargo bar = update(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Cargo " + cargo.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Cargo obtenerCargo(Integer id) {
		return findById(id);
	}

	@Override
	public Cargo obtenerCargo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<Cargo> obtenerCargoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Cargo> obtenerCargoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Cargo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Cargo> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa.getId());
	}

	@Override
	public List<Cargo> obtenerPorEmpresa(String nombre, Empresa empresa) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "empresa",
				empresa.getId());
	}
	
	@Override
	public List<Cargo> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Cargo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ICargoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Cargo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<Cargo> onComplete(String query) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"descripcion", query, "estado", "AC", "empresa");
	}

	@Override
	public List<Cargo> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Cargo> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				descripcion, "estado", "AC", "sucursal", sucursal.getId());
	}

}
