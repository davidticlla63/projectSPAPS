/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.model.VentaServiciosEmpresa;

/**
 * @author ANITA
 *
 */
public interface IVentaServiciosEmpresaDao {
	
	boolean deleteDetail(VentaServicios labExamen);
	
	void delete(VentaServiciosEmpresa labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	VentaServiciosEmpresa create(VentaServiciosEmpresa especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	VentaServiciosEmpresa update(VentaServiciosEmpresa update);

	/**
	 * registrar object
	 * 
	 * @param VentaServiciosEmpresa
	 * @return VentaServiciosEmpresa
	 */
	VentaServiciosEmpresa registrar(VentaServiciosEmpresa especialidad);

	/**
	 * modificar object
	 * 
	 * @param VentaServiciosEmpresa
	 * @return VentaServiciosEmpresa
	 */
	VentaServiciosEmpresa modificar(VentaServiciosEmpresa especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(VentaServiciosEmpresa especialidad);

	/**
	 * 
	 * @return
	 */
	List<VentaServiciosEmpresa> obtenerTodosActivosEInactivosPorOrdenAsc();

	VentaServiciosEmpresa obtenerVentaServiciosEmpresa(Integer id);

	VentaServiciosEmpresa obtenerVentaServiciosEmpresa(String nombre);

	List<VentaServiciosEmpresa> obtenerVentaServiciosEmpresaOrdenAscPorId();

	List<VentaServiciosEmpresa> obtenerVentaServiciosEmpresaOrdenDescPorId();

	List<VentaServiciosEmpresa> obtenerPorCompania(Compania compania);
	
	List<VentaServiciosEmpresa> obtenerPorExamen(LabExamen examen);

	List<VentaServiciosEmpresa> obtenerPorCompania(String nombre, Compania compania);

	List<VentaServiciosEmpresa> obtenerPorSucursal(Sucursal sucursal);

	List<VentaServiciosEmpresa> obtenerAllActivos();

}
