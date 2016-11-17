/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamenDetalleImag;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabExamenDetalleImagDao {

	boolean deleteDetail(LabExamenImag labExamen);

	void delete(LabExamenDetalleImag labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabExamenDetalleImag create(LabExamenDetalleImag especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabExamenDetalleImag update(LabExamenDetalleImag update);

	/**
	 * registrar object
	 * 
	 * @param LabExamenDetalleImag
	 * @return LabExamenDetalleImag
	 */
	LabExamenDetalleImag registrar(LabExamenDetalleImag especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabExamenDetalleImag
	 * @return LabExamenDetalleImag
	 */
	LabExamenDetalleImag modificar(LabExamenDetalleImag especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabExamenDetalleImag especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabExamenDetalleImag> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabExamenDetalleImag obtenerLabExamenDetalleImag(Integer id);

	LabExamenDetalleImag obtenerLabExamenDetalleImag(String nombre);

	List<LabExamenDetalleImag> obtenerLabExamenDetalleImagOrdenAscPorId();

	List<LabExamenDetalleImag> obtenerLabExamenDetalleImagOrdenDescPorId();

	List<LabExamenDetalleImag> obtenerPorCompania(Compania compania);

	List<LabExamenDetalleImag> obtenerPorExamen(LabExamenImag examen);

	List<LabExamenDetalleImag> obtenerPorCompania(String nombre,
			Compania compania);

	List<LabExamenDetalleImag> obtenerPorSucursal(Sucursal sucursal);

	List<LabExamenDetalleImag> obtenerAllActivos();

}
