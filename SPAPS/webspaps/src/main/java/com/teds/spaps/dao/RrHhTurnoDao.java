package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IRrHhTurnoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.RrHhTurno;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class RrHhTurnoDao extends DataAccessObjectJpa<RrHhTurno> implements IRrHhTurnoDao {

	public RrHhTurnoDao() {
		super(RrHhTurno.class);
	}

	@Override
	public RrHhTurno registrar(RrHhTurno cargo) {
		try {
			beginTransaction();
			cargo = create(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"RrHhTurnoSemana " + cargo.getNombre());
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
	public RrHhTurno modificar(RrHhTurno cargo) {
		try {
			beginTransaction();
			cargo = update(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"RrHhTurnoSemana " + cargo.getNombre());
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
	public boolean eliminar(RrHhTurno cargo) {
		try {
			beginTransaction();
			RrHhTurno bar = update(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"RrHhTurnoSemana " + cargo.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public RrHhTurno obtenerRrHhTurnoSemana(Integer id) {
		return findById(id);
	}

	@Override
	public RrHhTurno obtenerRrHhTurnoSemana(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<RrHhTurno> obtenerRrHhTurnoSemanaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<RrHhTurno> obtenerRrHhTurnoSemanaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<RrHhTurno> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<RrHhTurno> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa.getId());
	}

	@Override
	public List<RrHhTurno> obtenerPorEmpresa(String nombre, Empresa empresa) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"nombre", nombre, "estado", "AC", "empresa",
				empresa.getId());
	}

	@Override
	public List<RrHhTurno> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IRrHhTurnoSemanaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<RrHhTurno> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<RrHhTurno> onComplete(String query) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"nombre", query, "estado", "AC", "empresa");
	}

	@Override
	public List<RrHhTurno> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"nombre", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<RrHhTurno> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
