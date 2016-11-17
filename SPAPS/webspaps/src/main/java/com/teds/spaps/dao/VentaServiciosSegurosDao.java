package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IVentaServiciosSegurosDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosSeguros;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaServiciosSegurosDao extends DataAccessObjectJpa<VentaServiciosSeguros>
		implements IVentaServiciosSegurosDao {

	public VentaServiciosSegurosDao() {
		super(VentaServiciosSeguros.class);
	}

	@Override
	public boolean deleteDetail(VentaServicios labExamen) {
		return super.removeDetail("servicios", labExamen.getId());
	}
	
	@Override
	public void delete(VentaServiciosSeguros labExamen) {
		super.deleteReal(labExamen);
		//return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public VentaServiciosSeguros create(VentaServiciosSeguros especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaServiciosSeguros update(VentaServiciosSeguros especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaServiciosSeguros registrar(VentaServiciosSeguros especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaServiciosSeguros "
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
	public VentaServiciosSeguros modificar(VentaServiciosSeguros especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaServiciosSeguros "
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
	public boolean eliminar(VentaServiciosSeguros especialidad) {
		try {
			beginTransaction();
			VentaServiciosSeguros bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaServiciosSeguros "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaServiciosSeguros obtenerVentaServiciosSeguros(Integer id) {
		return findById(id);
	}

	@Override
	public VentaServiciosSeguros obtenerVentaServiciosSeguros(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaServiciosSeguros> obtenerVentaServiciosSegurosOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServiciosSeguros> obtenerVentaServiciosSegurosOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServiciosSeguros> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaServiciosSeguros> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("labExamen", examen.getId());
	}

	@Override
	public List<VentaServiciosSeguros> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosSegurosDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaServiciosSeguros> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaServiciosSegurosDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaServiciosSeguros> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosSegurosDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaServiciosSeguros> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
