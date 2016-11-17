/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Tarea;

/**
 * @author ANITA
 *
 */
public interface ITareaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param tarea
	 * @return
	 */
	Tarea create(Tarea tarea);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Tarea update(Tarea tarea);

	/**
	 * registrar object
	 * 
	 * @param Tarea
	 * @return Tarea
	 */
	Tarea registrar(Tarea tarea);

	/**
	 * modificar object
	 * 
	 * @param Tarea
	 * @return Tarea
	 */
	Tarea modificar(Tarea tarea);

	/**
	 * eliminar object
	 * 
	 * @param tarea
	 * @return
	 */
	boolean eliminar(Tarea tarea);

	/**
	 * 
	 * @return
	 */
	List<Tarea> obtenerTodosActivosEInactivosPorOrdenAsc();

	Tarea obtenerTarea(Integer id);

	List<Tarea> obtenerTareaOrdenAscPorId();

	List<Tarea> obtenerTareaOrdenDescPorId();

	List<Tarea> obtenerPorCompania(Compania compania);

	List<Tarea> obtenerPorSucursal(Sucursal sucursal);

	List<Tarea> obtenerPorPlanSeguro(PlanSeguro planSeguro);

	List<Tarea> obtenerPorCompania(String nombre, Compania compania);

	boolean verificarRepetido(Tarea tarea, Compania compania);

	boolean verificarRepetidoUpdate(Tarea tarea, Compania compania);

	List<Tarea> onComplete(String query);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<Tarea> obtenerPorSucursal(String nombre, Sucursal sucursal);

	/**
	 * @param tarea
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetidoUpdate(Tarea tarea, Sucursal sucursal);

	/**
	 * @param tarea
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetido(Tarea tarea, Sucursal sucursal);

	/**
	 * @param personal
	 * @param sucursal
	 * @return
	 */
	List<Tarea> obtenerPorPersonal(Personal personal, Sucursal sucursal);

}
