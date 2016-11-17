/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicios;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgOrdenServiciosDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgOrdenServicios
	 * @return
	 */
	DesgOrdenServicios create(DesgOrdenServicios desgOrdenServicios);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgOrdenServicios update(DesgOrdenServicios desgOrdenServicios);

	void delete(DesgOrdenServicios desgOrdenServicios);

	/**
	 * registrar object
	 * 
	 * @param DesgOrdenServicios
	 * @return DesgOrdenServicios
	 */
	DesgOrdenServicios registrar(DesgOrdenServicios desgOrdenServicios);

	boolean registrar(List<DesgOrdenServicios> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DesgOrdenServicios
	 * @return DesgOrdenServicios
	 */
	DesgOrdenServicios modificar(DesgOrdenServicios desgOrdenServicios);

	/**
	 * eliminar object
	 * 
	 * @param desgOrdenServicios
	 * @return
	 */
	boolean eliminar(DesgOrdenServicios desgOrdenServicios);

	/**
	 * 
	 * @return
	 */
	List<DesgOrdenServicios> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgOrdenServicios obtenerDesgOrdenServicios(Integer id);

	List<DesgOrdenServicios> obtenerDesgOrdenServiciosOrdenAscPorId();

	List<DesgOrdenServicios> obtenerDesgOrdenServiciosOrdenDescPorId();

	List<DesgOrdenServicios> obtenerPorCompania(Compania compania);

	List<DesgOrdenServicios> obtenerPorSucursal(Sucursal sucursal);

	List<DesgOrdenServicios> obtenerPorServicio(Servicio servicio);

	List<DesgOrdenServicios> obtenerPorOrden(DesgOrdenServicio desgOrdenServicio);

	List<DesgOrdenServicios> onComplete(String query);

	/**
	 * @param examen
	 * @param sucursal
	 * @return
	 */
	boolean verificarServicioRegistrado(Servicio servicio, Sucursal sucursal);

	/**
	 * @param orden
	 * @return
	 */
	List<DesgOrdenServicios> obtenerPorOrdenHC(DesgOrdenServicio orden);

}
