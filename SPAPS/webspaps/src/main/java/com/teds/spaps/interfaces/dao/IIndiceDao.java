/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Indice;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IIndiceDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param indice
	 * @return
	 */
	Indice create(Indice indice);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Indice update(Indice update);

	/**
	 * registrar object
	 * 
	 * @param Indice
	 * @return Indice
	 */
	Indice registrar(Indice indice);

	/**
	 * modificar object
	 * 
	 * @param Indice
	 * @return Indice
	 */
	Indice modificar(Indice indice);

	/**
	 * eliminar object
	 * 
	 * @param indice
	 * @return
	 */
	boolean eliminar(Indice indice);

	/**
	 * 
	 * @return
	 */
	List<Indice> obtenerTodosActivosEInactivosPorOrdenAsc();

	Indice obtenerIndice(Integer id);

	Indice obtenerIndice(String descripcion);

	List<Indice> obtenerIndiceOrdenAscPorId();

	List<Indice> obtenerIndiceOrdenDescPorId();

	List<Indice> obtenerPorCompania(Compania compania);

	List<Indice> obtenerPorSucursal(Sucursal sucursal);

	List<Indice> onComplete(String query);

	List<Indice> obtenerPorCompania(String descripcion, Compania compania);

	/**
	 * @param compania
	 * @return
	 */
	boolean verificarIndiceDeCompania(Compania compania);

	/**
	 * @param compania
	 * @return
	 */
	Indice obtenerIndice(Compania compania);

}
