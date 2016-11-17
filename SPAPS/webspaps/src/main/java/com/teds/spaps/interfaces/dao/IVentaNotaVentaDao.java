/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaNotaVenta;
//import com.teds.spaps.model.VentaNotaVentaDetalle;

/**
 * @author ANITA
 *
 */
public interface IVentaNotaVentaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaNotaVenta update(VentaNotaVenta update);

	/**
	 * registrar object
	 * 
	 * @param VentaNotaVenta
	 * @return VentaNotaVenta
	 */
	VentaNotaVenta registrar(VentaNotaVenta examen);

	/**
	 * modificar object
	 * 
	 * @param VentaNotaVenta
	 * @return VentaNotaVenta
	 */
	VentaNotaVenta modificar(VentaNotaVenta examen);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaNotaVenta especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaNotaVenta> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaNotaVenta obtenerVentaNotaVenta(Integer id);

	VentaNotaVenta obtenerVentaNotaVenta(String nombre);

	List<VentaNotaVenta> obtenerVentaNotaVentaOrdenAscPorId();

	List<VentaNotaVenta> obtenerVentaNotaVentaOrdenDescPorId();

	List<VentaNotaVenta> obtenerPorCompania(Compania compania);

	List<VentaNotaVenta> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania);

	List<VentaNotaVenta> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);


	List<VentaNotaVenta> obtenerPorCompania(String nombre, Compania compania);

	List<VentaNotaVenta> obtenerPorSucursal(Sucursal sucursal);

	List<VentaNotaVenta> obtenerAllActivos();

}
