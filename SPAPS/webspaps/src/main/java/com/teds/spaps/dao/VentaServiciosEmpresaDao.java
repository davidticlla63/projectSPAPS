package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IVentaServiciosEmpresaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosEmpresa;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaServiciosEmpresaDao extends DataAccessObjectJpa<VentaServiciosEmpresa>
		implements IVentaServiciosEmpresaDao {

	public VentaServiciosEmpresaDao() {
		super(VentaServiciosEmpresa.class);
	}

	@Override
	public boolean deleteDetail(VentaServicios labExamen) {
		return super.removeDetail("servicios", labExamen.getId());
	}
	
	@Override
	public void delete(VentaServiciosEmpresa labExamen) {
		super.deleteReal(labExamen);
		//return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public VentaServiciosEmpresa create(VentaServiciosEmpresa especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaServiciosEmpresa update(VentaServiciosEmpresa especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaServiciosEmpresa registrar(VentaServiciosEmpresa especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaServiciosEmpresa "
					+ especialidad.toString());
			return especialidad;
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
	public VentaServiciosEmpresa modificar(VentaServiciosEmpresa especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaServiciosEmpresa "
					+ especialidad.toString());
			return especialidad;
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
	public boolean eliminar(VentaServiciosEmpresa especialidad) {
		try {
			beginTransaction();
			VentaServiciosEmpresa bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaServiciosEmpresa "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaServiciosEmpresa obtenerVentaServiciosEmpresa(Integer id) {
		return findById(id);
	}

	@Override
	public VentaServiciosEmpresa obtenerVentaServiciosEmpresa(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaServiciosEmpresa> obtenerVentaServiciosEmpresaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServiciosEmpresa> obtenerVentaServiciosEmpresaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServiciosEmpresa> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaServiciosEmpresa> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<VentaServiciosEmpresa> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosEmpresaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaServiciosEmpresa> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaServiciosEmpresaDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaServiciosEmpresa> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosEmpresaDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaServiciosEmpresa> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
