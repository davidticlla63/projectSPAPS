/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Ciudad;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ICiudadDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	Ciudad create(Ciudad especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Ciudad update(Ciudad update);

	/**
	 * registrar object
	 * 
	 * @param Ciudad
	 * @return Ciudad
	 */
	Ciudad registrar(Ciudad especialidad);

	/**
	 * modificar object
	 * 
	 * @param Ciudad
	 * @return Ciudad
	 */
	Ciudad modificar(Ciudad especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(Ciudad especialidad);

	/**
	 * 
	 * @return
	 */
	List<Ciudad> obtenerTodosActivosEInactivosPorOrdenAsc();

	Ciudad obtenerCiudad(Integer id);

	Ciudad obtenerCiudad(String nombre);

	List<Ciudad> obtenerCiudadOrdenAscPorId();

	List<Ciudad> obtenerCiudadOrdenDescPorId();

	List<Ciudad> obtenerPorCompania(Compania compania);

	List<Ciudad> obtenerPorCompania(String nombre, Compania compania);

	List<Ciudad> obtenerPorSucursal(Sucursal sucursal);

	List<Ciudad> obtenerAllActivos();
	
	List<Ciudad> obtenerAllGrupoExamen(Compania compania) ;

}
