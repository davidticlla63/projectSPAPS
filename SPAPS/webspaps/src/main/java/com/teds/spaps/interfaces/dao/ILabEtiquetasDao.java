/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabEtiquetas;

/**
 * @author ANITA
 *
 */
public interface ILabEtiquetasDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabEtiquetas create(LabEtiquetas especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabEtiquetas update(LabEtiquetas update);

	/**
	 * registrar object
	 * 
	 * @param LabEtiquetas
	 * @return LabEtiquetas
	 */
	LabEtiquetas registrar(LabEtiquetas especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabEtiquetas
	 * @return LabEtiquetas
	 */
	LabEtiquetas modificar(LabEtiquetas especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabEtiquetas especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabEtiquetas> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabEtiquetas obtenerLabEtiquetas(Integer id);
	
	List<LabEtiquetas> obtenerLabEtiquetasOrdenAscPorId();

	List<LabEtiquetas> obtenerLabEtiquetasOrdenDescPorId();

	List<LabEtiquetas> obtenerPorCompania(Compania compania);

	List<LabEtiquetas> obtenerPorCompania(String nombre, Compania compania);

}
