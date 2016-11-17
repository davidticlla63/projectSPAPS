package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IVentaTipoServiciosDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaTipoServicios;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaTipoServiciosDao extends DataAccessObjectJpa<VentaTipoServicios>
		implements IVentaTipoServiciosDao {

	public VentaTipoServiciosDao() {
		super(VentaTipoServicios.class);
	}

	@Override
	public VentaTipoServicios create(VentaTipoServicios especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaTipoServicios update(VentaTipoServicios especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaTipoServicios registrar(VentaTipoServicios especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaTipoServicios "
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
	public VentaTipoServicios modificar(VentaTipoServicios especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaTipoServicios "
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
	public boolean eliminar(VentaTipoServicios especialidad) {
		try {
			beginTransaction();
			VentaTipoServicios bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaTipoServicios "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaTipoServicios obtenerVentaTipoServicios(Integer id) {
		return findById(id);
	}

	@Override
	public VentaTipoServicios obtenerVentaTipoServicios(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaTipoServicios> obtenerVentaTipoServiciosOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaTipoServicios> obtenerVentaTipoServiciosOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaTipoServicios> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaTipoServicios> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("descripcion",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaTipoServiciosDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaTipoServicios> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaTipoServiciosDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaTipoServicios> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaTipoServiciosDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaTipoServicios> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
}
