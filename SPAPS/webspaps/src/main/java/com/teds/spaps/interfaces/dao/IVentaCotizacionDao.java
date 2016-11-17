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
public interface IVentaCotizacionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaCotizacion create(VentaCotizacion especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaCotizacion update(VentaCotizacion update);

	/**
	 * registrar object
	 * 
	 * @param VentaCotizacion
	 * @return VentaCotizacion
	 */
	VentaCotizacion registrar(VentaCotizacion examen,List<VentaCotizacionDetalle> listCotizacionDetalle);
    VentaCotizacion registrar(VentaCotizacion examen);	
    /**
	 * modificar object
	 * 
	 * @param VentaCotizacion
	 * @return VentaCotizacion
	 */
	VentaCotizacion modificar(VentaCotizacion examen,List<VentaCotizacionDetalle> listCotizacionDetalle);
	VentaCotizacion modificar(VentaCotizacion examen);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaCotizacion especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaCotizacion> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaCotizacion obtenerVentaCotizacion(Integer id);

	VentaCotizacion obtenerVentaCotizacion(String nombre);

	List<VentaCotizacion> obtenerVentaCotizacionOrdenAscPorId();

	List<VentaCotizacion> obtenerVentaCotizacionOrdenDescPorId();

	List<VentaCotizacion> obtenerPorCompania(Compania compania);

	List<VentaCotizacion> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania);

	List<VentaCotizacion> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);


	List<VentaCotizacion> obtenerPorCompania(String nombre, Compania compania);

	List<VentaCotizacion> obtenerPorSucursal(Sucursal sucursal);

	List<VentaCotizacion> obtenerAllActivos();

}
