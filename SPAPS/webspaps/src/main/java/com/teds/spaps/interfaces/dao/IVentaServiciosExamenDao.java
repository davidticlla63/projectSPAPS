/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosExamen;

/**
 * @author ANITA
 *
 */
public interface IVentaServiciosExamenDao {
	
	boolean deleteDetail(VentaServicios labExamen);
	
	void delete(VentaServiciosExamen labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaServiciosExamen create(VentaServiciosExamen especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaServiciosExamen update(VentaServiciosExamen update);

	/**
	 * registrar object
	 * 
	 * @param VentaServiciosExamen
	 * @return VentaServiciosExamen
	 */
	VentaServiciosExamen registrar(VentaServiciosExamen especialidad);

	/**
	 * modificar object
	 * 
	 * @param VentaServiciosExamen
	 * @return VentaServiciosExamen
	 */
	VentaServiciosExamen modificar(VentaServiciosExamen especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaServiciosExamen especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaServiciosExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaServiciosExamen obtenerVentaServiciosExamen(Integer id);

	VentaServiciosExamen obtenerVentaServiciosExamen(String nombre);

	List<VentaServiciosExamen> obtenerVentaServiciosExamenOrdenAscPorId();

	List<VentaServiciosExamen> obtenerVentaServiciosExamenOrdenDescPorId();

	List<VentaServiciosExamen> obtenerPorCompania(Compania compania);
	
	List<VentaServiciosExamen> obtenerPorExamen(LabExamen examen);

	List<VentaServiciosExamen> obtenerPorCompania(String nombre, Compania compania);

	List<VentaServiciosExamen> obtenerPorSucursal(Sucursal sucursal);

	List<VentaServiciosExamen> obtenerAllActivos();

}
