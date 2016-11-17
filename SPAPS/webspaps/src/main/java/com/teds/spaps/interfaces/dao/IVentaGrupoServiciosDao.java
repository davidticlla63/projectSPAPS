/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaGrupoServicios;

/**
 * @author ANITA
 *
 */
public interface IVentaGrupoServiciosDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaGrupoServicios create(VentaGrupoServicios especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaGrupoServicios update(VentaGrupoServicios update);

	/**
	 * registrar object
	 * 
	 * @param VentaGrupoServicios
	 * @return VentaGrupoServicios
	 */
	VentaGrupoServicios registrar(VentaGrupoServicios especialidad);

	/**
	 * modificar object
	 * 
	 * @param VentaGrupoServicios
	 * @return VentaGrupoServicios
	 */
	VentaGrupoServicios modificar(VentaGrupoServicios especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaGrupoServicios especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaGrupoServicios> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaGrupoServicios obtenerVentaGrupoServicios(Integer id);

	VentaGrupoServicios obtenerVentaGrupoServicios(String nombre);

	List<VentaGrupoServicios> obtenerVentaGrupoServiciosOrdenAscPorId();

	List<VentaGrupoServicios> obtenerVentaGrupoServiciosOrdenDescPorId();

	List<VentaGrupoServicios> obtenerPorCompania(Compania compania);

	List<VentaGrupoServicios> obtenerPorCompania(String nombre, Compania compania);

	List<VentaGrupoServicios> obtenerPorSucursal(Sucursal sucursal);

	List<VentaGrupoServicios> obtenerAllActivos();
	
	List<VentaGrupoServicios> obtenerAllGrupoServicios(Compania compania) ;

}
