/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaTipoServicios;

/**
 * @author ANITA
 *
 */
public interface IVentaTipoServiciosDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaTipoServicios create(VentaTipoServicios especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaTipoServicios update(VentaTipoServicios update);

	/**
	 * registrar object
	 * 
	 * @param VentaTipoServicios
	 * @return VentaTipoServicios
	 */
	VentaTipoServicios registrar(VentaTipoServicios especialidad);

	/**
	 * modificar object
	 * 
	 * @param VentaTipoServicios
	 * @return VentaTipoServicios
	 */
	VentaTipoServicios modificar(VentaTipoServicios especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaTipoServicios especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaTipoServicios> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaTipoServicios obtenerVentaTipoServicios(Integer id);

	VentaTipoServicios obtenerVentaTipoServicios(String nombre);

	List<VentaTipoServicios> obtenerVentaTipoServiciosOrdenAscPorId();

	List<VentaTipoServicios> obtenerVentaTipoServiciosOrdenDescPorId();

	List<VentaTipoServicios> obtenerPorCompania(Compania compania);

	List<VentaTipoServicios> obtenerPorCompania(String nombre, Compania compania);

	List<VentaTipoServicios> obtenerPorSucursal(Sucursal sucursal);

	List<VentaTipoServicios> obtenerAllActivos();

}
