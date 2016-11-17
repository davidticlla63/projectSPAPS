/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IEspecialidadDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	Especialidad create(Especialidad especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Especialidad update(Especialidad update);

	/**
	 * registrar object
	 * 
	 * @param Especialidad
	 * @return Especialidad
	 */
	Especialidad registrar(Especialidad especialidad);

	/**
	 * modificar object
	 * 
	 * @param Especialidad
	 * @return Especialidad
	 */
	Especialidad modificar(Especialidad especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(Especialidad especialidad);

	/**
	 * 
	 * @return
	 */
	List<Especialidad> obtenerTodosActivosEInactivosPorOrdenAsc();

	Especialidad obtenerEspecialidad(Integer id);

	Especialidad obtenerEspecialidad(String nombre);

	List<Especialidad> obtenerEspecialidadOrdenAscPorId();

	List<Especialidad> obtenerEspecialidadOrdenDescPorId();

	List<Especialidad> obtenerPorCompania(Compania compania);

	List<Especialidad> obtenerPorCompania(String nombre, Compania compania);

	List<Especialidad> obtenerPorSucursal(Sucursal sucursal);

	List<Especialidad> obtenerAllActivos();

}
