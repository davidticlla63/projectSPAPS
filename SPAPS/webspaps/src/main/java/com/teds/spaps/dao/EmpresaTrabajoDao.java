package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IEmpresaTrabajoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.EmpresaTrabajo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class EmpresaTrabajoDao extends DataAccessObjectJpa<EmpresaTrabajo>
		implements IEmpresaTrabajoDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public EmpresaTrabajoDao() {
		super(EmpresaTrabajo.class);
	}

	@Override
	public EmpresaTrabajo create(EmpresaTrabajo empresaTrabajo) {
		return super.create(empresaTrabajo);
	}

	@Override
	public EmpresaTrabajo update(EmpresaTrabajo empresaTrabajo) {
		return super.update(empresaTrabajo);
	}

	@Override
	public EmpresaTrabajo registrar(EmpresaTrabajo EmpresaTrabajo) {
		try {
			beginTransaction();
			EmpresaTrabajo = create(EmpresaTrabajo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "EmpresaTrabajo "
					+ EmpresaTrabajo.getDescripcion());
			return EmpresaTrabajo;
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
	public EmpresaTrabajo modificar(EmpresaTrabajo EmpresaTrabajo) {
		try {
			beginTransaction();
			EmpresaTrabajo = update(EmpresaTrabajo);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"EmpresaTrabajo " + EmpresaTrabajo.getDescripcion());
			return EmpresaTrabajo;
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
	public boolean eliminar(EmpresaTrabajo EmpresaTrabajo) {
		try {
			setDelete(true);
			EmpresaTrabajo.setEstado("RM");
			beginTransaction();
			EmpresaTrabajo bar = update(EmpresaTrabajo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ EmpresaTrabajo.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public EmpresaTrabajo obtenerEmpresaTrabajo(Integer id) {
		return findById(id);
	}

	@Override
	public EmpresaTrabajo obtenerEmpresaTrabajo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<EmpresaTrabajo> obtenerEmpresaTrabajoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<EmpresaTrabajo> obtenerEmpresaTrabajoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<EmpresaTrabajo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<EmpresaTrabajo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<EmpresaTrabajo> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IEmpresaTrabajoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<EmpresaTrabajo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<EmpresaTrabajo> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<EmpresaTrabajo> obtenerPorSucursalAutoComplete(
			String descripcion, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	@Override
	public boolean verificarRepetido(EmpresaTrabajo empresaTrabajo,
			Compania compania) {
		List<EmpresaTrabajo> desgImagGrupos = obtenerPorCompania(compania);
		for (EmpresaTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					empresaTrabajo.getDescripcion())
					|| desgImagGrupo2.equals(empresaTrabajo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(EmpresaTrabajo empresaTrabajo,
			Compania compania) {
		List<EmpresaTrabajo> desgImagGrupos = obtenerPorCompania(compania);
		for (EmpresaTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					empresaTrabajo.getDescripcion())
					&& !desgImagGrupo2.equals(empresaTrabajo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetido(EmpresaTrabajo empresaTrabajo,
			Sucursal sucursal) {
		List<EmpresaTrabajo> desgImagGrupos = obtenerPorSucursal(sucursal);
		for (EmpresaTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					empresaTrabajo.getDescripcion())
					|| desgImagGrupo2.equals(empresaTrabajo))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(EmpresaTrabajo empresaTrabajo,
			Sucursal sucursal) {
		List<EmpresaTrabajo> desgImagGrupos = obtenerPorSucursal(sucursal);
		for (EmpresaTrabajo desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getDescripcion().equalsIgnoreCase(
					empresaTrabajo.getDescripcion())
					&& !desgImagGrupo2.equals(empresaTrabajo))
				return true;
		}
		return false;
	}

}
