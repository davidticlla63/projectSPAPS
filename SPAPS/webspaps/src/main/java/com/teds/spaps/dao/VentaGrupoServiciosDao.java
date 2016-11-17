package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IVentaGrupoServiciosDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaGrupoServicios;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaGrupoServiciosDao extends DataAccessObjectJpa<VentaGrupoServicios>
		implements IVentaGrupoServiciosDao {

	public VentaGrupoServiciosDao() {
		super(VentaGrupoServicios.class);
	}

	@Override
	public VentaGrupoServicios create(VentaGrupoServicios especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaGrupoServicios update(VentaGrupoServicios especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaGrupoServicios registrar(VentaGrupoServicios especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaGrupoServicios "
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
	public VentaGrupoServicios modificar(VentaGrupoServicios especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaGrupoServicios "
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
	public boolean eliminar(VentaGrupoServicios especialidad) {
		try {
			beginTransaction();
			VentaGrupoServicios bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaGrupoServicios "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaGrupoServicios obtenerVentaGrupoServicios(Integer id) {
		return findById(id);
	}

	@Override
	public VentaGrupoServicios obtenerVentaGrupoServicios(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaGrupoServicios> obtenerVentaGrupoServiciosOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaGrupoServicios> obtenerVentaGrupoServiciosOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaGrupoServicios> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaGrupoServicios> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaGrupoServiciosDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaGrupoServicios> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaGrupoServiciosDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaGrupoServicios> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaGrupoServiciosDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaGrupoServicios> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaGrupoServicios> obtenerAllGrupoServicios(Compania compania) {
		// TODO Auto-generated method stub
		String query = "select em.grupoExamen from LabExamen em  where em.estado='AC' and em.compania.id="
				+ compania.getId()
				+ "  and em.visualizar=true and em.tipoExamen.descripcion like '%LABORATORIO%' order by em.id  asc";
		System.out.println(query);
		return (List<VentaGrupoServicios>) executeQueryResulList(query);
	}
}
