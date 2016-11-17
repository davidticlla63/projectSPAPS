/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamenDetalleImag;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.LabGrupoExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDOrdenImagenologia;

/**
 * @author ANITA
 *
 */
public interface ILabExamenImagDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabExamenImag create(LabExamenImag especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabExamenImag update(LabExamenImag update);

	/**
	 * registrar object
	 * 
	 * @param LabExamenImag
	 * @return LabExamenImag
	 */
	LabExamenImag registrar(LabExamenImag especialidad,
			List<LabExamenDetalleImag> listExamenDetalles);

	/**
	 * modificar object
	 * 
	 * @param LabExamenImag
	 * @return LabExamenImag
	 */
	LabExamenImag modificar(LabExamenImag especialidad,
			List<LabExamenDetalleImag> listExamenDetalles);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabExamenImag especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabExamenImag> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabExamenImag obtenerLabExamenImag(Integer id);

	LabExamenImag obtenerLabExamenImag(String nombre);

	List<LabExamenImag> obtenerLabExamenImagOrdenAscPorId();

	List<LabExamenImag> obtenerLabExamenImagOrdenDescPorId();

	List<LabExamenImag> obtenerPorCompania(Compania compania);

	List<LabExamenImag> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania);

	List<LabExamenImag> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);

	List<LabExamenImag> obtenerPorGrupoExamen(LabGrupoExamenImag grupoExamen);

	public List<LabExamenImag> obtenerExamenesPorGrupoExamen(
			LabGrupoExamenImag grupoExamen);

	List<LabExamenImag> obtenerPorCompania(String nombre, Compania compania);

	List<LabExamenImag> obtenerPorSucursal(Sucursal sucursal);

	List<LabExamenImag> obtenerAllActivos();

	List<EDOrdenImagenologia> obtenerAllStructureOrden(Compania compania);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<LabExamenImag> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<LabExamenImag> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
