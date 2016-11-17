/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabOrdenDetalleImagDao {
	
	boolean deleteDetail(LabOrdenImag labExamen);
	
	void delete(LabOrdenDetalleImag labOrdenDetalle);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabOrdenDetalleImag create(LabOrdenDetalleImag especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabOrdenDetalleImag update(LabOrdenDetalleImag update);

	/**
	 * registrar object
	 * 
	 * @param LabOrdenDetalleImag
	 * @return LabOrdenDetalleImag
	 */
	LabOrdenDetalleImag registrar(LabOrdenDetalleImag especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabOrdenDetalleImag
	 * @return LabOrdenDetalleImag
	 */
	LabOrdenDetalleImag modificar(LabOrdenDetalleImag especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabOrdenDetalleImag especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabOrdenDetalleImag> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabOrdenDetalleImag obtenerLabOrdenDetalleImag(Integer id);

	LabOrdenDetalleImag obtenerLabOrdenDetalleImag(String nombre);

	List<LabOrdenDetalleImag> obtenerLabOrdenDetalleImagOrdenAscPorId();

	List<LabOrdenDetalleImag> obtenerLabOrdenDetalleImagOrdenDescPorId();

	List<LabOrdenDetalleImag> obtenerPorCompania(Compania compania);
	
	List<LabOrdenDetalleImag> obtenerPorExamen(LabExamen examen);

	List<LabOrdenDetalleImag> obtenerPorCompania(String nombre, Compania compania);

	List<LabOrdenDetalleImag> obtenerPorSucursal(Sucursal sucursal);

	List<LabOrdenDetalleImag> obtenerAllActivos();

}
