/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Raza;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IRazaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param raza
	 * @return
	 */
	Raza create(Raza raza);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Raza update(Raza update);

	/**
	 * registrar object
	 * 
	 * @param Raza
	 * @return Raza
	 */
	Raza registrar(Raza raza);

	/**
	 * modificar object
	 * 
	 * @param Raza
	 * @return Raza
	 */
	Raza modificar(Raza raza);

	/**
	 * eliminar object
	 * 
	 * @param raza
	 * @return
	 */
	boolean eliminar(Raza raza);

	/**
	 * 
	 * @return
	 */
	List<Raza> obtenerTodosActivosEInactivosPorOrdenAsc();

	Raza obtenerRaza(Integer id);

	List<Raza> obtenerRazaOrdenAscPorId();

	List<Raza> obtenerRazaOrdenDescPorId();

	List<Raza> obtenerPorCompania(Compania compania);

	List<Raza> obtenerAllActivos();

	List<Raza> obtenerPorSucursal(Sucursal sucursal);

	List<Raza> onComplete(String query);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<Raza> obtenerPorCompaniaAutoComplete(String nombre, Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<Raza> obtenerPorSucursalAutoComplete(String nombre, Sucursal sucursal);
}
