/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAtencion;

/**
 * @author ANITA
 *
 */
public interface ITipoAtencionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param TipoAtencion
	 * @return
	 */
	TipoAtencion create(TipoAtencion TipoAtencion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TipoAtencion update(TipoAtencion update);

	/**
	 * registrar object
	 * 
	 * @param TipoAtencion
	 * @return TipoAtencion
	 */
	TipoAtencion registrar(TipoAtencion cita);

	/**
	 * modificar object
	 * 
	 * @param TipoAtencion
	 * @return TipoAtencion
	 */
	TipoAtencion modificar(TipoAtencion cita);

	/**
	 * eliminar object
	 * 
	 * @param TipoAtencion
	 * @return
	 */
	boolean eliminar(TipoAtencion cita);

	/**
	 * 
	 * @return
	 */
	List<TipoAtencion> obtenerTodosActivosEInactivosPorOrdenAsc();

	TipoAtencion obtenerTipoAtencion(Integer id);

	List<TipoAtencion> obtenerTipoAtencionOrdenAscPorId();

	List<TipoAtencion> obtenerTipoAtencionOrdenDescPorId();

	List<TipoAtencion> obtenerPorCompania(String nombre, Compania compania);

	TipoAtencion obtenerTipoAtencion(String descripcion);

	List<TipoAtencion> obtenerPorCompania(Compania compania);

	List<TipoAtencion> obtenerPorSucursal(Sucursal sucursal);

}
