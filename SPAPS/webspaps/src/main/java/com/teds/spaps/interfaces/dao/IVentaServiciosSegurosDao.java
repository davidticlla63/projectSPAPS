/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosSeguros;

/**
 * @author ANITA
 *
 */
public interface IVentaServiciosSegurosDao {
	
	boolean deleteDetail(VentaServicios labExamen);
	
	void delete(VentaServiciosSeguros labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaServiciosSeguros create(VentaServiciosSeguros especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaServiciosSeguros update(VentaServiciosSeguros update);

	/**
	 * registrar object
	 * 
	 * @param VentaServiciosSeguros
	 * @return VentaServiciosSeguros
	 */
	VentaServiciosSeguros registrar(VentaServiciosSeguros especialidad);

	/**
	 * modificar object
	 * 
	 * @param VentaServiciosSeguros
	 * @return VentaServiciosSeguros
	 */
	VentaServiciosSeguros modificar(VentaServiciosSeguros especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaServiciosSeguros especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaServiciosSeguros> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaServiciosSeguros obtenerVentaServiciosSeguros(Integer id);

	VentaServiciosSeguros obtenerVentaServiciosSeguros(String nombre);

	List<VentaServiciosSeguros> obtenerVentaServiciosSegurosOrdenAscPorId();

	List<VentaServiciosSeguros> obtenerVentaServiciosSegurosOrdenDescPorId();

	List<VentaServiciosSeguros> obtenerPorCompania(Compania compania);
	
	List<VentaServiciosSeguros> obtenerPorExamen(LabExamen examen);

	List<VentaServiciosSeguros> obtenerPorCompania(String nombre, Compania compania);

	List<VentaServiciosSeguros> obtenerPorSucursal(Sucursal sucursal);

	List<VentaServiciosSeguros> obtenerAllActivos();

}
