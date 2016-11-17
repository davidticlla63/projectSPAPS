/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabIndicaciones;

/**
 * @author ANITA
 *
 */
public interface ILabIndicacionesDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabIndicaciones create(LabIndicaciones especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabIndicaciones update(LabIndicaciones update);

	/**
	 * registrar object
	 * 
	 * @param LabIndicaciones
	 * @return LabIndicaciones
	 */
	LabIndicaciones registrar(LabIndicaciones especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabIndicaciones
	 * @return LabIndicaciones
	 */
	LabIndicaciones modificar(LabIndicaciones especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabIndicaciones especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabIndicaciones> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabIndicaciones obtenerLabIndicaciones(Integer id);
	
	List<LabIndicaciones> obtenerLabIndicacionesOrdenAscPorId();

	List<LabIndicaciones> obtenerLabIndicacionesOrdenDescPorId();

	List<LabIndicaciones> obtenerPorCompania(Compania compania);

	List<LabIndicaciones> obtenerPorCompania(String nombre, Compania compania);

}
