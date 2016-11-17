package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IRazaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Raza;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class RazaDao extends DataAccessObjectJpa<Raza> implements IRazaDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public RazaDao() {
		super(Raza.class);
	}

	@Override
	public Raza create(Raza raza) {
		return super.create(raza);
	}

	@Override
	public Raza update(Raza raza) {
		return super.update(raza);
	}

	@Override
	public Raza registrar(Raza Raza) {
		try {
			beginTransaction();
			Raza = create(Raza);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Raza " + Raza.getNombre());
			return Raza;
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
	public Raza modificar(Raza Raza) {
		try {
			beginTransaction();
			Raza = update(Raza);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"Raza " + Raza.getNombre());
			return Raza;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				if (!isDelete())
					FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Raza Raza) {
		try {
			setDelete(true);
			Raza.setEstado("RM");
			beginTransaction();
			Raza bar = update(Raza);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ Raza.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Raza obtenerRaza(Integer id) {
		return findById(id);
	}

	@Override
	public List<Raza> obtenerRazaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Raza> obtenerRazaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Raza> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Raza> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	@Override
	public List<Raza> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IRazaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Raza> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<Raza> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("nombre", query);
	}

	@Override
	public List<Raza> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<Raza> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
