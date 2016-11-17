package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IVentaCotizacionDao;
import com.teds.spaps.interfaces.dao.IVentaCotizacionDetalleDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaCotizacion;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.model.VentaDetalleNotaVenta;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaCotizacionDao extends DataAccessObjectJpa<VentaCotizacion>
		implements IVentaCotizacionDao {

	private @Inject IVentaCotizacionDetalleDao cotizacionDetalleDao;

	 public String generateDeliveryInId(int id) {

	        if (id < 10) {
	            return "COT-000" + id;

	        } else if (id >= 10 && id < 100) {
	            return "COT-00" + id;

	        } else if (id >= 100 && id < 1000) {
	            return "COT-0" + id;

	        } else {
	            return "COT-" + id;

	        }
	    }
	
	public VentaCotizacionDao() {
		super(VentaCotizacion.class);
	}

	@Override
	public VentaCotizacion create(VentaCotizacion especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaCotizacion update(VentaCotizacion especialidad) {
		return super.update(especialidad);
	}

	@Override
	public VentaCotizacion registrar(VentaCotizacion examen,
			List<VentaCotizacionDetalle> listCotizacionDetalle) {
		try {
			beginTransaction();
			examen = create(examen);
			for (VentaCotizacionDetalle cotizacionDetalle : listCotizacionDetalle) {
				cotizacionDetalle.setCotizacion(examen);
				cotizacionDetalle = cotizacionDetalleDao
						.create(cotizacionDetalle);
			}

			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "VentaCotizacion "
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
	public VentaCotizacion registrar(VentaCotizacion examen){
		try {
			beginTransaction();
			examen = create(examen);
			examen.setCodigo(generateDeliveryInId(examen.getId()));
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Cotizacion "
					+ examen.getCodigo());
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
	public VentaCotizacion modificar(VentaCotizacion examen){
		try {
			
			beginTransaction();
			examen = update(examen);
			commitTransaction();
			FacesUtil.infoMessage("Modificacion Correcta", "Cotizacion "
					+ examen.getCodigo());
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
	public VentaCotizacion modificar(VentaCotizacion examen,
			List<VentaCotizacionDetalle> listCotizacionDetalle) {
		try {
			examen.setListCotizacionDetalles(new ArrayList<VentaCotizacionDetalle>());
			beginTransaction();
			examen = update(examen);
			cotizacionDetalleDao.deleteDetail(examen);
			for (VentaCotizacionDetalle cotizacionDetalle : listCotizacionDetalle) {
				cotizacionDetalle.setCotizacion(examen);
				cotizacionDetalle = cotizacionDetalleDao
						.create(cotizacionDetalle);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaCotizacion "
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
	public boolean eliminar(VentaCotizacion especialidad) {
		try {
			beginTransaction();
			VentaCotizacion bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaCotizacion "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaCotizacion obtenerVentaCotizacion(Integer id) {
		return findById(id);
	}

	@Override
	public VentaCotizacion obtenerVentaCotizacion(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaCotizacion> obtenerVentaCotizacionOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaCotizacion> obtenerVentaCotizacionOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaCotizacion> obtenerPorCompania(Compania compania) {
		 List<VentaCotizacion> lista=findAllActiveParameter("compania", compania.getId());
		 for (VentaCotizacion ventaCotizacion : lista) {
			for (VentaCotizacionDetalle ventaCotizacionDetalle : ventaCotizacion.getListCotizacionDetalles()) {
				if (ventaCotizacionDetalle.getEstado().equals("RM")) {
					ventaCotizacion.getListCotizacionDetalles().remove(ventaCotizacionDetalle);
				}
			}
		}
		 return lista;
	}

	@Override
	public List<VentaCotizacion> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<VentaCotizacion> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<VentaCotizacion> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaCotizacionDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaCotizacion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaCotizacionDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaCotizacion> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaCotizacionDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaCotizacion> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

}
