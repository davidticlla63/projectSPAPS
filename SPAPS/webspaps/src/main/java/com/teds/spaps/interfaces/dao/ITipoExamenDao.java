/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoExamen;

/**
 * @author ANITA
 *
 */
public interface ITipoExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param tipoExamen
	 * @return
	 */
	TipoExamen create(TipoExamen tipoExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TipoExamen update(TipoExamen update);

	/**
	 * registrar object
	 * 
	 * @param TipoExamen
	 * @return TipoExamen
	 */
	TipoExamen registrar(TipoExamen tipoExamen);

	/**
	 * modificar object
	 * 
	 * @param TipoExamen
	 * @return TipoExamen
	 */
	TipoExamen modificar(TipoExamen tipoExamen);

	/**
	 * eliminar object
	 * 
	 * @param tipoExamen
	 * @return
	 */
	boolean eliminar(TipoExamen tipoExamen);

	/**
	 * 
	 * @return
	 */
	List<TipoExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	TipoExamen obtenerTipoExamen(Integer id);

	List<TipoExamen> obtenerTipoExamenOrdenAscPorId();

	List<TipoExamen> obtenerTipoExamenOrdenDescPorId();

	List<TipoExamen> obtenerPorSucursal(Sucursal sucursal);

	List<TipoExamen> obtenerPorCompania(String nombre, Compania compania);

}
