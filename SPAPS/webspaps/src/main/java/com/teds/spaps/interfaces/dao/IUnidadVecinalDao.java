/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.UnidadVecinal;

/**
 * @author ANITA
 *
 */
public interface IUnidadVecinalDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param unidadVecinal
	 * @return
	 */
	UnidadVecinal create(UnidadVecinal unidadVecinal);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	UnidadVecinal update(UnidadVecinal update);

	/**
	 * registrar object
	 * 
	 * @param UnidadVecinal
	 * @return UnidadVecinal
	 */
	UnidadVecinal registrar(UnidadVecinal unidadVecinal);

	/**
	 * modificar object
	 * 
	 * @param UnidadVecinal
	 * @return UnidadVecinal
	 */
	UnidadVecinal modificar(UnidadVecinal unidadVecinal);

	/**
	 * eliminar object
	 * 
	 * @param unidadVecinal
	 * @return
	 */
	boolean eliminar(UnidadVecinal unidadVecinal);

	/**
	 * 
	 * @return
	 */
	List<UnidadVecinal> obtenerTodosActivosEInactivosPorOrdenAsc();

	UnidadVecinal obtenerUnidadVecinal(Integer id);

	UnidadVecinal obtenerUnidadVecinal(String numero);

	List<UnidadVecinal> obtenerUnidadVecinalOrdenAscPorId();

	List<UnidadVecinal> obtenerUnidadVecinalOrdenDescPorId();

	List<UnidadVecinal> obtenerPorCompania(Compania compania);

	List<UnidadVecinal> obtenerPorSucursal(Sucursal sucursal);

	List<UnidadVecinal> onComplete(String query);

	/**
	 * @param numero
	 * @param compania
	 * @return
	 */
	List<UnidadVecinal> obtenerPorCompaniaAutoComplete(String numero,
			Compania compania);

	/**
	 * @param numero
	 * @param sucursal
	 * @return
	 */
	List<UnidadVecinal> obtenerPorSucursalAutoComplete(String numero,
			Sucursal sucursal);
}
