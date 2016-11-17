/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPaisDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param pais
	 * @return
	 */
	Pais create(Pais pais);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Pais update(Pais update);

	/**
	 * registrar object
	 * 
	 * @param Pais
	 * @return Pais
	 */
	Pais registrar(Pais pais);

	/**
	 * modificar object
	 * 
	 * @param Pais
	 * @return Pais
	 */
	Pais modificar(Pais pais);

	/**
	 * eliminar object
	 * 
	 * @param pais
	 * @return
	 */
	boolean eliminar(Pais pais);

	/**
	 * 
	 * @return
	 */
	List<Pais> obtenerTodosActivosEInactivosPorOrdenAsc();

	Pais obtenerPais(Integer id);

	List<Pais> obtenerPaisOrdenAscPorId();

	List<Pais> obtenerPaisOrdenDescPorId();

	List<Pais> obtenerPorCompania(Compania compania);

	List<Pais> obtenerPorCompaniaAutoComplete(String nombre, Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<Pais> obtenerPorSucursalAutoComplete(String nombre, Sucursal sucursal);

}
