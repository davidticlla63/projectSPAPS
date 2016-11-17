package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.teds.spaps.interfaces.dao.IVentaServiciosDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosEmpresaDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosSegurosDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.ServicioExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosEmpresa;
import com.teds.spaps.model.VentaServiciosSeguros;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaServiciosDao extends DataAccessObjectJpa<VentaServicios>
		implements IVentaServiciosDao {

	private @Inject IVentaServiciosEmpresaDao serviciosEmpresaDao;
	private @Inject IVentaServiciosSegurosDao serviciosSegurosDao;
	
	public VentaServiciosDao() {
		super(VentaServicios.class);
	}

	@Override
	public VentaServicios create(VentaServicios especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaServicios update(VentaServicios especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaServicios registrar(VentaServicios examen,List<VentaServiciosEmpresa> listServiciosEmpresas,List<VentaServiciosSeguros> listServiciosSeguros) {
		try {
			beginTransaction();
			examen = create(examen);
			for (VentaServiciosSeguros ventaServiciosSeguros : listServiciosSeguros) {
				ventaServiciosSeguros.setServicios(examen);
				ventaServiciosSeguros= serviciosSegurosDao.create(ventaServiciosSeguros);
			}
			for (VentaServiciosEmpresa ventaServiciosEmpresa : listServiciosEmpresas) {
				ventaServiciosEmpresa.setServicios(examen);
				ventaServiciosEmpresa= serviciosEmpresaDao.create(ventaServiciosEmpresa);
			}

			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaServicios "
					+ examen.toString());
			return examen;
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
	public VentaServicios modificar(VentaServicios examen,List<VentaServiciosEmpresa> listServiciosEmpresas,List<VentaServiciosSeguros> listServiciosSeguros) {
		try {
			examen.setListServiciosEmpresas(new ArrayList<VentaServiciosEmpresa>());
			examen.setListServiciosSeguros(new ArrayList<VentaServiciosSeguros>());
			beginTransaction();
			examen = update(examen);
			serviciosSegurosDao.deleteDetail(examen);
			for (VentaServiciosSeguros ventaServiciosSeguros : listServiciosSeguros) {
				ventaServiciosSeguros.setId(null);
				ventaServiciosSeguros.setServicios(examen);
				ventaServiciosSeguros= serviciosSegurosDao.create(ventaServiciosSeguros);
			}
			
			serviciosEmpresaDao.deleteDetail(examen);
			for (VentaServiciosEmpresa ventaServiciosEmpresa : listServiciosEmpresas) {
				ventaServiciosEmpresa.setId(null);
				ventaServiciosEmpresa.setServicios(examen);
				ventaServiciosEmpresa= serviciosEmpresaDao.create(ventaServiciosEmpresa);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaServicios "
					+ examen.toString());
			return examen;
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
	public boolean eliminar(VentaServicios especialidad) {
		try {
			beginTransaction();
			VentaServicios bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaServicios "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaServicios obtenerVentaServicios(Integer id) {
		return findById(id);
	}

	@Override
	public VentaServicios obtenerVentaServicios(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaServicios> obtenerVentaServiciosOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServicios> obtenerVentaServiciosOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaServicios> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaServicios> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<VentaServicios> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<VentaServicios> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaServicios> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaServiciosDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaServicios> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaServiciosDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaServicios> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}
	@Override
	public VentaServicios DevolverServicioPorId(Integer id){
		//String query = "SELECT NEW com.teds.spaps.model.VentaServicios(p.id, p.descripcion, p.precioVenta)FROM VentaServicios AS p where p.id="+id;
		String query = "SELECT p FROM VentaServicios p where p.id="+id;
		Query q = getEntityManager().createQuery(query);
		System.out.println(q.getSingleResult());
		return (VentaServicios)q.getSingleResult();
	}
	@Override
	public List<VentaServicios> devolverServicioOnCompleteCompania(Compania compania, String nombre){
		//String query =  "select NEW com.teds.spaps.model.VentaServicios(p.id, p.descripcion, p.precioVenta,p.grupoServicios) from VentaServicios p where upper(translate(p.descripcion, 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
		String query =  "select p from VentaServicios p where upper(translate(p.descripcion, 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ nombre + "%'";
		Query q = getEntityManager().createQuery(query);
		return (List<VentaServicios>)q.getResultList();
	}
	
	@Override
	public List<VentaServicios> devolverServiciosPorOrdenDeLab(LabOrden ordendelab){
		List<VentaServicios> serviciosADevolver=new ArrayList<>();
		for (LabOrdenDetalle detalle : ordendelab.getListOrdenDetalle()) {
			String query =  "select se from ServicioExamen se where se.examen.id="+detalle.getLabExamen().getId();
			Query q = getEntityManager().createQuery(query);
			ServicioExamen se=(ServicioExamen) q.getSingleResult();
			serviciosADevolver.add(DevolverServicioPorId(se.getServicio().getId()));
		}
		return serviciosADevolver;
	}
	
}
