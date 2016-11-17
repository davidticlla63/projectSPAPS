/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabGrupoExamenImagDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabGrupoExamenImag create(LabGrupoExamenImag especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabGrupoExamenImag update(LabGrupoExamenImag update);

	/**
	 * registrar object
	 * 
	 * @param LabGrupoExamenImag
	 * @return LabGrupoExamenImag
	 */
	LabGrupoExamenImag registrar(LabGrupoExamenImag especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabGrupoExamenImag
	 * @return LabGrupoExamenImag
	 */
	LabGrupoExamenImag modificar(LabGrupoExamenImag especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabGrupoExamenImag especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabGrupoExamenImag> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabGrupoExamenImag obtenerLabGrupoExamenImag(Integer id);

	LabGrupoExamenImag obtenerLabGrupoExamenImag(String nombre);

	List<LabGrupoExamenImag> obtenerLabGrupoExamenImagOrdenAscPorId();

	List<LabGrupoExamenImag> obtenerLabGrupoExamenImagOrdenDescPorId();

	List<LabGrupoExamenImag> obtenerPorCompania(Compania compania);

	List<LabGrupoExamenImag> obtenerPorCompania(String nombre, Compania compania);

	List<LabGrupoExamenImag> obtenerPorSucursal(Sucursal sucursal);

	List<LabGrupoExamenImag> obtenerAllActivos();
	
	public List<LabGrupoExamenImag> obtenerAllGrupoExamen(Compania compania);

}
