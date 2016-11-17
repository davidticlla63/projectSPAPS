/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ISucursalDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param sucursal
	 * @return
	 */
	Sucursal create(Sucursal sucursal);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Sucursal update(Sucursal update);

	/**
	 * registrar object
	 * 
	 * @param Sucursal
	 * @return Sucursal
	 */
	Sucursal registrar(Sucursal sucursal);

	/**
	 * modificar object
	 * 
	 * @param Sucursal
	 * @return Sucursal
	 */
	Sucursal modificar(Sucursal sucursal);

	/**
	 * eliminar object
	 * 
	 * @param sucursal
	 * @return
	 */
	boolean eliminar(Sucursal sucursal);

	/**
	 * 
	 * @return
	 */
	List<Sucursal> obtenerTodosActivosEInactivosPorOrdenAsc();

	Sucursal obtenerSucursal(Integer id);

	Sucursal obtenerSucursal(Integer id, Compania compania);

	Sucursal obtenerSucursal(String descripcion);

	Sucursal obtenerSucursal(String descripcion, Compania compania);

	List<Sucursal> obtenerSucursalOrdenAscPorId();

	List<Sucursal> obtenerSucursalOrdenDescPorId();

	List<Sucursal> obtenerPorCompania(Compania compania);

	List<Sucursal> obtenerAllActivos();

	List<Sucursal> obtenerPorCompania(String nombre, Compania compania);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<Sucursal> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<Sucursal> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal);
}
