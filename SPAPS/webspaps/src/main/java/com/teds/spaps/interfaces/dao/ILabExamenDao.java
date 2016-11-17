/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.LabGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDOrdenLaboratorio;

/**
 * @author ANITA
 *
 */
public interface ILabExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabExamen create(LabExamen especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabExamen update(LabExamen update);

	/**
	 * registrar object
	 * 
	 * @param LabExamen
	 * @return LabExamen
	 */
	LabExamen registrar(LabExamen especialidad,
			List<LabExamenDetalle> listExamenDetalles);

	/**
	 * modificar object
	 * 
	 * @param LabExamen
	 * @return LabExamen
	 */
	LabExamen modificar(LabExamen especialidad,
			List<LabExamenDetalle> listExamenDetalles);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabExamen especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabExamen obtenerLabExamen(Integer id);

	LabExamen obtenerLabExamen(String nombre);

	List<LabExamen> obtenerLabExamenOrdenAscPorId();

	List<LabExamen> obtenerLabExamenOrdenDescPorId();

	List<LabExamen> obtenerPorCompania(Compania compania);

	List<LabExamen> obtenerPorCompaniaOrdenadoFechaRegistro(Compania compania);

	List<LabExamen> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);

	List<LabExamen> obtenerPorGrupoExamen(LabGrupoExamen grupoExamen);

	List<LabExamen> obtenerPorCompania(String nombre, Compania compania);

	List<LabExamen> obtenerPorSucursal(Sucursal sucursal);

	List<LabExamen> obtenerAllActivos();

	List<EDOrdenLaboratorio> obtenerAllStructureOrden(Compania compania);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<LabExamen> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<LabExamen> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
