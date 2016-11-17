/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoEstudio;

/**
 * @author ANITA
 *
 */
public interface ITipoEstudioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param tipoEstudio
	 * @return
	 */
	TipoEstudio create(TipoEstudio tipoEstudio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TipoEstudio update(TipoEstudio update);

	/**
	 * registrar object
	 * 
	 * @param TipoEstudio
	 * @return TipoEstudio
	 */
	TipoEstudio registrar(TipoEstudio tipoEstudio);

	/**
	 * modificar object
	 * 
	 * @param TipoEstudio
	 * @return TipoEstudio
	 */
	TipoEstudio modificar(TipoEstudio tipoEstudio);

	/**
	 * eliminar object
	 * 
	 * @param tipoEstudio
	 * @return
	 */
	boolean eliminar(TipoEstudio tipoEstudio);

	/**
	 * 
	 * @return
	 */
	List<TipoEstudio> obtenerTodosActivosEInactivosPorOrdenAsc();

	TipoEstudio obtenerTipoEstudio(Integer id);

	TipoEstudio obtenerTipoEstudio(String nombre);

	List<TipoEstudio> obtenerTipoEstudioOrdenAscPorId();

	List<TipoEstudio> obtenerTipoEstudioOrdenDescPorId();

	List<TipoEstudio> obtenerPorCompania(Compania compania);

	List<TipoEstudio> obtenerPorSucursal(Sucursal sucursal);

	List<TipoEstudio> onComplete(String query);

}
