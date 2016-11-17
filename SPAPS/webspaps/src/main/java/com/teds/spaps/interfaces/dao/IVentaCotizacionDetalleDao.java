/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaCotizacion;
import com.teds.spaps.model.VentaCotizacionDetalle;

/**
 * @author ANITA
 *
 */
public interface IVentaCotizacionDetalleDao {
	
	boolean deleteDetail(VentaCotizacion labExamen);
	
	void delete(VentaCotizacionDetalle labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaCotizacionDetalle create(VentaCotizacionDetalle especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaCotizacionDetalle update(VentaCotizacionDetalle update);

	/**
	 * registrar object
	 * 
	 * @param VentaCotizacionDetalle
	 * @return VentaCotizacionDetalle
	 */
	VentaCotizacionDetalle registrar(VentaCotizacionDetalle especialidad);

	/**
	 * modificar object
	 * 
	 * @param VentaCotizacionDetalle
	 * @return VentaCotizacionDetalle
	 */
	VentaCotizacionDetalle modificar(VentaCotizacionDetalle especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaCotizacionDetalle especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaCotizacionDetalle> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaCotizacionDetalle obtenerVentaCotizacionDetalle(Integer id);

	VentaCotizacionDetalle obtenerVentaCotizacionDetalle(String nombre);

	List<VentaCotizacionDetalle> obtenerVentaCotizacionDetalleOrdenAscPorId();

	List<VentaCotizacionDetalle> obtenerVentaCotizacionDetalleOrdenDescPorId();

	List<VentaCotizacionDetalle> obtenerPorCompania(Compania compania);
	
	List<VentaCotizacionDetalle> obtenerPorCotizacion(VentaCotizacion examen) ;

	List<VentaCotizacionDetalle> obtenerPorCompania(String nombre, Compania compania);

	List<VentaCotizacionDetalle> obtenerPorSucursal(Sucursal sucursal);

	List<VentaCotizacionDetalle> obtenerAllActivos();

}
