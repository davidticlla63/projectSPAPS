package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class SucursalDao extends DataAccessObjectJpa<Sucursal> implements
		ISucursalDao {

	public SucursalDao() {
		super(Sucursal.class);
	}

	@Override
	public Sucursal create(Sucursal sucursal) {
		return super.create(sucursal);
	}

	@Override
	public Sucursal update(Sucursal sucursal) {
		return super.update(sucursal);
	}

	@Override
	public Sucursal registrar(Sucursal sucursal) {
		try {
			beginTransaction();
			sucursal = create(sucursal);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Sucursal " + sucursal.getDescripcion());
			return sucursal;
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
	public Sucursal modificar(Sucursal sucursal) {
		try {
			beginTransaction();
			sucursal = update(sucursal);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Sucursal "
					+ sucursal.getDescripcion());
			return sucursal;
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
	public boolean eliminar(Sucursal sucursal) {
		try {
			sucursal.setEstado("RM");
			beginTransaction();
			Sucursal bar = update(sucursal);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Sucursal "
					+ sucursal.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Sucursal obtenerSucursal(Integer id) {
		return findById(id);
	}

	@Override
	public Sucursal obtenerSucursal(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	@Override
	public Sucursal obtenerSucursal(String descripcion) {
		return findByParameter("nombre", descripcion);
	}

	@Override
	public Sucursal obtenerSucursal(String descripcion, Compania compania) {
		return findByParameterObjectTwo("nombre", "compania", descripcion,
				compania.getId());
	}

	@Override
	public List<Sucursal> obtenerSucursalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Sucursal> obtenerSucursalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Sucursal> obtenerPorCompania(Compania compania) {
		return findActiveParameter("compania", compania.getId());
	}

	@Override
	public List<Sucursal> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	@Override
	public List<Sucursal> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ISucursalDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Sucursal> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Sucursal> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<Sucursal> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
