package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IVentaServiciosExamenDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosExamen;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaServiciosExamenDao extends DataAccessObjectJpa<VentaServiciosExamen>
		implements IVentaServiciosExamenDao {

	public VentaServiciosExamenDao() {
		super(VentaServiciosExamen.class);
	}

	@Override
	public boolean deleteDetail(VentaServicios labExamen) {
		return super.removeDetail("servicios", labExamen.getId());
	}
	
	@Override
	public void delete(VentaServiciosExamen labExamen) {
		super.deleteReal(labExamen);
		//return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public VentaServiciosExamen create(VentaServiciosExamen especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaServiciosExamen update(VentaServiciosExamen especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaServiciosExamen registrar(VentaServiciosExamen especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaServiciosExamen "
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
	public VentaServiciosExamen modificar(VentaServiciosExamen especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaServiciosExamen "
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
	public boolean eliminar(VentaServiciosExamen especialidad) {
		try {
			beginTransaction();
			VentaServiciosExamen bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaServiciosExamen "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaServiciosExamen obtenerVentaServiciosExamen(Integer id) {
		return findById(id);
	}

	@Override
	public VentaServiciosExamen obtenerVentaServiciosExamen(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaServiciosExamen> obtenerVentaServiciosExamenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServiciosExamen> obtenerVentaServiciosExamenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServiciosExamen> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaServiciosExamen> obtenerPorExamen(LabExamen examen) {
		return findAllActiveParameter("examen", examen.getId());
	}

	@Override
	public List<VentaServiciosExamen> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosExamenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaServiciosExamen> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaServiciosExamenDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaServiciosExamen> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosExamenDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaServiciosExamen> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
