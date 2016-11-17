/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosEmpresa;
import com.teds.spaps.model.VentaServiciosSeguros;

/**
 * @author ANITA
 *
 */
public interface IVentaServiciosDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaServicios create(VentaServicios especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaServicios update(VentaServicios update);

	/**
	 * registrar object
	 * 
	 * @param VentaServicios
	 * @return VentaServicios
	 */
	public VentaServicios registrar(VentaServicios examen,
			List<VentaServiciosEmpresa> listServiciosEmpresas,
			List<VentaServiciosSeguros> listServiciosSeguros);

	/**
	 * modificar object
	 * 
	 * @param VentaServicios
	 * @return VentaServicios
	 */
	VentaServicios modificar(VentaServicios examen,
			List<VentaServiciosEmpresa> listServiciosEmpresas,
			List<VentaServiciosSeguros> listServiciosSeguros);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaServicios especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaServicios> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaServicios obtenerVentaServicios(Integer id);

	VentaServicios obtenerVentaServicios(String nombre);

	List<VentaServicios> obtenerVentaServiciosOrdenAscPorId();

	List<VentaServicios> obtenerVentaServiciosOrdenDescPorId();

	List<VentaServicios> obtenerPorCompania(Compania compania);

	List<VentaServicios> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania);

	List<VentaServicios> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);

	List<VentaServicios> obtenerPorCompania(String nombre, Compania compania);

	List<VentaServicios> obtenerPorSucursal(Sucursal sucursal);

	List<VentaServicios> obtenerAllActivos();

	VentaServicios DevolverServicioPorId(Integer id);

	List<VentaServicios> devolverServicioOnCompleteCompania(Compania compania,
			String nombre);

	List<VentaServicios> devolverServiciosPorOrdenDeLab(LabOrden ordendelab);
}
