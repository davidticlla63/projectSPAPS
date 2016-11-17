/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabGrupoExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabGrupoExamen create(LabGrupoExamen especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabGrupoExamen update(LabGrupoExamen update);

	/**
	 * registrar object
	 * 
	 * @param LabGrupoExamen
	 * @return LabGrupoExamen
	 */
	LabGrupoExamen registrar(LabGrupoExamen especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabGrupoExamen
	 * @return LabGrupoExamen
	 */
	LabGrupoExamen modificar(LabGrupoExamen especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabGrupoExamen especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabGrupoExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabGrupoExamen obtenerLabGrupoExamen(Integer id);

	LabGrupoExamen obtenerLabGrupoExamen(String nombre);

	List<LabGrupoExamen> obtenerLabGrupoExamenOrdenAscPorId();

	List<LabGrupoExamen> obtenerLabGrupoExamenOrdenDescPorId();

	List<LabGrupoExamen> obtenerPorCompania(Compania compania);

	List<LabGrupoExamen> obtenerPorCompania(String nombre, Compania compania);

	List<LabGrupoExamen> obtenerPorSucursal(Sucursal sucursal);

	List<LabGrupoExamen> obtenerAllActivos();
	
	public List<LabGrupoExamen> obtenerAllGrupoExamen(Compania compania) ;

}
