/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoTarea;

/**
 * @author ANITA
 *
 */
public interface ITipoTareaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param tipoTarea
	 * @return
	 */
	TipoTarea create(TipoTarea tipoTarea);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TipoTarea update(TipoTarea update);

	/**
	 * registrar object
	 * 
	 * @param TipoTarea
	 * @return TipoTarea
	 */
	TipoTarea registrar(TipoTarea tipoTarea);

	/**
	 * modificar object
	 * 
	 * @param TipoTarea
	 * @return TipoTarea
	 */
	TipoTarea modificar(TipoTarea tipoTarea);

	/**
	 * eliminar object
	 * 
	 * @param tipoTarea
	 * @return
	 */
	boolean eliminar(TipoTarea tipoTarea);

	/**
	 * 
	 * @return
	 */
	List<TipoTarea> obtenerTodosActivosEInactivosPorOrdenAsc();

	TipoTarea obtenerTipoTarea(Integer id);

	TipoTarea obtenerTipoTarea(String nombre);

	List<TipoTarea> obtenerTipoTareaOrdenAscPorId();

	List<TipoTarea> obtenerTipoTareaOrdenDescPorId();

	List<TipoTarea> obtenerPorCompania(Compania compania);

	List<TipoTarea> obtenerPorSucursal(Sucursal sucursal);

	List<TipoTarea> onComplete(String query);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<TipoTarea> obtenerPorSucursal(String nombre, Sucursal sucursal);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<TipoTarea> obtenerPorCompania(String nombre, Compania compania);

	/**
	 * @param tarea
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetidoUpdate(TipoTarea tarea, Sucursal sucursal);

	/**
	 * @param tarea
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetido(TipoTarea tarea, Sucursal sucursal);

	/**
	 * @param tarea
	 * @param compania
	 * @return
	 */
	boolean verificarRepetidoUpdate(TipoTarea tarea, Compania compania);

	/**
	 * @param tarea
	 * @param compania
	 * @return
	 */
	boolean verificarRepetido(TipoTarea tarea, Compania compania);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<TipoTarea> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<TipoTarea> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal);

}
