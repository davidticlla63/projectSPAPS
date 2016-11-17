/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabExamenDetalleDao {
	
	boolean deleteDetail(LabExamen labExamen);
	
	void delete(LabExamenDetalle labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabExamenDetalle create(LabExamenDetalle especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabExamenDetalle update(LabExamenDetalle update);

	/**
	 * registrar object
	 * 
	 * @param LabExamenDetalle
	 * @return LabExamenDetalle
	 */
	LabExamenDetalle registrar(LabExamenDetalle especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabExamenDetalle
	 * @return LabExamenDetalle
	 */
	LabExamenDetalle modificar(LabExamenDetalle especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabExamenDetalle especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabExamenDetalle> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabExamenDetalle obtenerLabExamenDetalle(Integer id);

	LabExamenDetalle obtenerLabExamenDetalle(String nombre);

	List<LabExamenDetalle> obtenerLabExamenDetalleOrdenAscPorId();

	List<LabExamenDetalle> obtenerLabExamenDetalleOrdenDescPorId();

	List<LabExamenDetalle> obtenerPorCompania(Compania compania);
	
	List<LabExamenDetalle> obtenerPorExamen(LabExamen examen);

	List<LabExamenDetalle> obtenerPorCompania(String nombre, Compania compania);

	List<LabExamenDetalle> obtenerPorSucursal(Sucursal sucursal);

	List<LabExamenDetalle> obtenerAllActivos();

}
