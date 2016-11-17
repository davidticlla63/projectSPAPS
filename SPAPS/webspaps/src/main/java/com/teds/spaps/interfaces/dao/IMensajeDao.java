/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Mensaje;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;

public interface IMensajeDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param mensaje
	 * @return
	 */
	Mensaje create(Mensaje mensaje);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Mensaje update(Mensaje update);

	/**
	 * registrar object
	 * 
	 * @param Mensaje
	 * @return Mensaje
	 */
	Mensaje registrar(Mensaje mensaje);

	/**
	 * modificar object
	 * 
	 * @param Mensaje
	 * @return Mensaje
	 */
	Mensaje modificar(Mensaje mensaje);

	/**
	 * eliminar object
	 * 
	 * @param mensaje
	 * @return
	 */
	boolean eliminar(Mensaje mensaje);

	/**
	 * 
	 * @return
	 */
	List<Mensaje> obtenerTodosActivosEInactivosPorOrdenAsc();

	Mensaje obtenerMensaje(Integer id);

	Mensaje obtenerMensaje(String mensaje);

	List<Mensaje> obtenerMensajeOrdenAscPorId();

	List<Mensaje> obtenerMensajeOrdenDescPorId();

	List<Mensaje> obtenerPorCompania(Compania compania);

	List<Mensaje> obtenerPorSucursal(Sucursal sucursal);

	List<Mensaje> obtenerPorTarea(Tarea tarea, Compania compania);

	List<Mensaje> obtenerPorTarea(Tarea tarea, Sucursal sucursal);

	List<Mensaje> obtenerAllActivos();

	boolean verificarRepetido(Mensaje mensaje, Compania compania);

	boolean verificarRepetidoUpdate(Mensaje mensaje, Compania compania);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<Mensaje> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<Mensaje> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
