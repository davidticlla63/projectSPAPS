package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IVentaCotizacionDetalleDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaCotizacion;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaCotizacionDetalleDao extends DataAccessObjectJpa<VentaCotizacionDetalle>
		implements IVentaCotizacionDetalleDao {

	public VentaCotizacionDetalleDao() {
		super(VentaCotizacionDetalle.class);
	}

	@Override
	public boolean deleteDetail(VentaCotizacion labExamen) {
		return super.removeDetail("cotizacion", labExamen.getId());
	}
	
	@Override
	public void delete(VentaCotizacionDetalle labExamen) {
		super.deleteReal(labExamen);
		//return super.removeDetail("labExamen", labExamen.getId());
	}

	@Override
	public VentaCotizacionDetalle create(VentaCotizacionDetalle especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaCotizacionDetalle update(VentaCotizacionDetalle especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaCotizacionDetalle registrar(VentaCotizacionDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaCotizacionDetalle "
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
	public VentaCotizacionDetalle modificar(VentaCotizacionDetalle especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaCotizacionDetalle "
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
	public boolean eliminar(VentaCotizacionDetalle especialidad) {
		try {
			beginTransaction();
			VentaCotizacionDetalle bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaCotizacionDetalle "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaCotizacionDetalle obtenerVentaCotizacionDetalle(Integer id) {
		return findById(id);
	}

	@Override
	public VentaCotizacionDetalle obtenerVentaCotizacionDetalle(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaCotizacionDetalle> obtenerVentaCotizacionDetalleOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaCotizacionDetalle> obtenerVentaCotizacionDetalleOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaCotizacionDetalle> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaCotizacionDetalle> obtenerPorCotizacion(VentaCotizacion examen) {
		return findAllActiveParameter("cotizacion", examen.getId());
	}

	@Override
	public List<VentaCotizacionDetalle> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"concepto", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaCotizacionDetalleDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaCotizacionDetalle> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaCotizacionDetalleDao#obtenerPorSucursal(com
	 * .teds .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaCotizacionDetalle> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaCotizacionDetalleDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaCotizacionDetalle> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
