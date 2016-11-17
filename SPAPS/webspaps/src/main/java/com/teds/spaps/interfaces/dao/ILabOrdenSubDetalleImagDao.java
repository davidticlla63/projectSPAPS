/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.LabOrdenSubDetalleImag;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabOrdenSubDetalleImagDao {
	
	boolean deleteDetail(LabOrdenImag labExamen);
	
	void delete(LabOrdenSubDetalleImag labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabOrdenSubDetalleImag create(LabOrdenSubDetalleImag especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabOrdenSubDetalleImag update(LabOrdenSubDetalleImag update);

	/**
	 * registrar object
	 * 
	 * @param LabOrdenSubDetalleImag
	 * @return LabOrdenSubDetalleImag
	 */
	LabOrdenSubDetalleImag registrar(LabOrdenSubDetalleImag especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabOrdenSubDetalleImag
	 * @return LabOrdenSubDetalleImag
	 */
	LabOrdenSubDetalleImag modificar(LabOrdenSubDetalleImag especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabOrdenSubDetalleImag especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabOrdenSubDetalleImag> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabOrdenSubDetalleImag obtenerLabOrdenSubDetalleImag(Integer id);

	LabOrdenSubDetalleImag obtenerLabOrdenSubDetalleImag(String nombre);

	List<LabOrdenSubDetalleImag> obtenerLabOrdenSubDetalleImagOrdenAscPorId();

	List<LabOrdenSubDetalleImag> obtenerLabOrdenSubDetalleImagOrdenDescPorId();

	List<LabOrdenSubDetalleImag> obtenerPorCompania(Compania compania);
	
	List<LabOrdenSubDetalleImag> obtenerPorExamen(LabExamen examen);

	List<LabOrdenSubDetalleImag> obtenerPorCompania(String nombre, Compania compania);

	List<LabOrdenSubDetalleImag> obtenerPorSucursal(Sucursal sucursal);

	List<LabOrdenSubDetalleImag> obtenerAllActivos();

}
