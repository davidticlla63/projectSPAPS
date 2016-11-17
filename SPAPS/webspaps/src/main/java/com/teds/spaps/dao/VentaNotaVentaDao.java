package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IVentaNotaVentaDao;
//import com.teds.spaps.interfaces.dao.IVentaNotaVentaDetalleDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.model.VentaDetalleNotaVenta;
import com.teds.spaps.model.VentaNotaVenta;
//import com.teds.spaps.model.VentaNotaVentaDetalle;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class VentaNotaVentaDao extends DataAccessObjectJpa<VentaNotaVenta>
		implements IVentaNotaVentaDao {

	//private @Inject IVentaNotaVentaDetalleDao cotizacionDetalleDao;

	public VentaNotaVentaDao() {
		super(VentaNotaVenta.class);
	}

	 public String generateDeliveryInId(int id) {

	        if (id < 10) {
	            return "VTA-000" + id;

	        } else if (id >= 10 && id < 100) {
	            return "VTA-00" + id;

	        } else if (id >= 100 && id < 1000) {
	            return "VTA-0" + id;

	        } else {
	            return "VTA-" + id;

	        }
	    }
	
	
	public VentaNotaVenta create(VentaNotaVenta especialidad) {
		return super.create(especialidad);
	}

	@Override
	public VentaNotaVenta update(VentaNotaVenta especialidad) {
		return super.update(especialidad);
	}

	
	@Override
	public VentaNotaVenta registrar(VentaNotaVenta examen) {
		try {
			beginTransaction();
			examen = super.create(examen);
			examen.setCodigo(generateDeliveryInId(examen.getId()));
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Nota de Venta "
					+ examen.getCodigo());
			return examen;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println("Error cause "+cause);
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
	
	public VentaNotaVenta modificar(VentaNotaVenta examen){
try {
			
			beginTransaction();
			examen = update(examen);
			commitTransaction();
			FacesUtil.infoMessage("Modificacion Correcta", "Nota de Venta "
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
	/*
	@Override
	public VentaNotaVenta modificar(VentaNotaVenta examen,
			List<VentaNotaVentaDetalle> listCotizacionDetalle) {
		try {
			examen.setListCotizacionDetalles(new ArrayList<VentaNotaVentaDetalle>());
			beginTransaction();
			examen = update(examen);
			cotizacionDetalleDao.deleteDetail(examen);
			for (VentaNotaVentaDetalle cotizacionDetalle : listCotizacionDetalle) {
				cotizacionDetalle.setCotizacion(examen);
				cotizacionDetalle = cotizacionDetalleDao
						.create(cotizacionDetalle);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "VentaNotaVenta "
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
	}*/

	@Override
	public boolean eliminar(VentaNotaVenta especialidad) {
		try {
			beginTransaction();
			VentaNotaVenta bar = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "VentaNotaVenta "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public VentaNotaVenta obtenerVentaNotaVenta(Integer id) {
		return findById(id);
	}

	@Override
	public VentaNotaVenta obtenerVentaNotaVenta(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<VentaNotaVenta> obtenerVentaNotaVentaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<VentaNotaVenta> obtenerVentaNotaVentaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<VentaNotaVenta> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<VentaNotaVenta> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<VentaNotaVenta> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<VentaNotaVenta> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaNotaVentaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<VentaNotaVenta> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.IVentaNotaVentaDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<VentaNotaVenta> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.IVentaNotaVentaDao#obtenerAllActivos()
	 */
	@Override
	public List<VentaNotaVenta> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

}
