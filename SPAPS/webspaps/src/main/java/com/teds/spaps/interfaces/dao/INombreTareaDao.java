/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.NombreTarea;
import com.teds.spaps.model.Sucursal;

public interface INombreTareaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param nombreTarea
	 * @return
	 */
	NombreTarea create(NombreTarea nombreTarea);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	NombreTarea update(NombreTarea update);

	/**
	 * registrar object
	 * 
	 * @param NombreTarea
	 * @return NombreTarea
	 */
	NombreTarea registrar(NombreTarea nombreTarea);

	/**
	 * modificar object
	 * 
	 * @param NombreTarea
	 * @return NombreTarea
	 */
	NombreTarea modificar(NombreTarea nombreTarea);

	/**
	 * eliminar object
	 * 
	 * @param nombreTarea
	 * @return
	 */
	boolean eliminar(NombreTarea nombreTarea);

	/**
	 * 
	 * @return
	 */
	List<NombreTarea> obtenerTodosActivosEInactivosPorOrdenAsc();

	NombreTarea obtenerNombreTarea(Integer id);

	NombreTarea obtenerNombreTarea(String descripcion);

	List<NombreTarea> obtenerNombreTareaOrdenAscPorId();

	List<NombreTarea> obtenerNombreTareaOrdenDescPorId();

	List<NombreTarea> obtenerPorCompania(Compania compania);

	List<NombreTarea> obtenerPorSucursal(Sucursal sucursal);

	List<NombreTarea> obtenerAllActivos();

	boolean verificarRepetido(NombreTarea nombreTarea, Compania compania);

	boolean verificarRepetidoUpdate(NombreTarea nombreTarea, Compania compania);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<NombreTarea> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<NombreTarea> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

	/**
	 * @param nombreTarea
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetido(NombreTarea nombreTarea, Sucursal sucursal);

	/**
	 * @param nombreTarea
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetidoUpdate(NombreTarea nombreTarea, Sucursal sucursal);

}
