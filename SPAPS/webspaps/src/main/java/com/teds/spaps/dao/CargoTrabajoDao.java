package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ICargoTrabajoDao;
import com.teds.spaps.model.CargoTrabajo;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.EmpresaTrabajo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class CargoTrabajoDao extends DataAccessObjectJpa<CargoTrabajo>
		implements ICargoTrabajoDao {

	public CargoTrabajoDao() {
		super(CargoTrabajo.class);
	}

	@Override
	public CargoTrabajo registrar(CargoTrabajo cargoTrabajo) {
		try {
			beginTransaction();
			cargoTrabajo = create(cargoTrabajo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "CargoTrabajo "
					+ cargoTrabajo.getDescripcion());
			return cargoTrabajo;
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
	public CargoTrabajo modificar(CargoTrabajo cargoTrabajo) {
		try {
			beginTransaction();
			cargoTrabajo = update(cargoTrabajo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "CargoTrabajo "
					+ cargoTrabajo.getDescripcion());
			return cargoTrabajo;
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
	public boolean eliminar(CargoTrabajo cargoTrabajo) {
		try {
			beginTransaction();
			CargoTrabajo bar = update(cargoTrabajo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "CargoTrabajo "
					+ cargoTrabajo.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public CargoTrabajo obtenerCargoTrabajo(Integer id) {
		return findById(id);
	}

	@Override
	public CargoTrabajo obtenerCargoTrabajo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<CargoTrabajo> obtenerCargoTrabajoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<CargoTrabajo> obtenerCargoTrabajoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<CargoTrabajo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<CargoTrabajo> obtenerPorEmpresa(EmpresaTrabajo empresa) {
		return findAllActivosByParameter("empresaTrabajo", empresa.getId());
	}

	@Override
	public List<CargoTrabajo> obtenerPorEmpresa(String nombre,
			EmpresaTrabajo empresa) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "empresaTrabajo",
				empresa.getId());
	}

	@Override
	public List<CargoTrabajo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ICargoTrabajoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<CargoTrabajo> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<CargoTrabajo> onComplete(String query) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"descripcion", query, "estado", "AC", "empresaTrabajo");
	}

	@Override
	public List<CargoTrabajo> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<CargoTrabajo> obtenerPorSucursalAutoComplete(
			String descripcion, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				descripcion, "estado", "AC", "sucursal", sucursal.getId());
	}

	@Override
	public boolean verificarRepetido(CargoTrabajo cargoTrabajo,
			Compania compania) {
		List<CargoTrabajo> desgImagGrupos = obtenerPorCompania(compania);
		for (CargoTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					cargoTrabajo.getDescripcion())
					|| desgImagGrupo2.getEmpresaTrabajo().equals(
							cargoTrabajo.getEmpresaTrabajo())
					|| desgImagGrupo2.equals(cargoTrabajo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(CargoTrabajo cargoTrabajo,
			Compania compania) {
		List<CargoTrabajo> desgImagGrupos = obtenerPorCompania(compania);
		for (CargoTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					cargoTrabajo.getDescripcion())
					&& !desgImagGrupo2.equals(cargoTrabajo)
					|| desgImagGrupo2.getEmpresaTrabajo().equals(
							cargoTrabajo.getEmpresaTrabajo()))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetido(CargoTrabajo cargoTrabajo,
			Sucursal sucursal) {
		List<CargoTrabajo> desgImagGrupos = obtenerPorSucursal(sucursal);
		for (CargoTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					cargoTrabajo.getDescripcion())
					|| desgImagGrupo2.getEmpresaTrabajo().equals(
							cargoTrabajo.getEmpresaTrabajo())
					|| desgImagGrupo2.equals(cargoTrabajo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(CargoTrabajo cargoTrabajo,
			Sucursal sucursal) {
		List<CargoTrabajo> desgImagGrupos = obtenerPorSucursal(sucursal);
		for (CargoTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					cargoTrabajo.getDescripcion())
					&& !desgImagGrupo2.equals(cargoTrabajo)
					|| desgImagGrupo2.getEmpresaTrabajo().equals(
							cargoTrabajo.getEmpresaTrabajo()))
				return true;
		}
		return false;
	}

}
