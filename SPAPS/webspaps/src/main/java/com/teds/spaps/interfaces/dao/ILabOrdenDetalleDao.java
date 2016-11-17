/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabOrdenDetalleDao {
	
	boolean deleteDetail(LabOrden labExamen);
	
	void delete(LabOrdenDetalle labOrdenDetalle);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabOrdenDetalle create(LabOrdenDetalle especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabOrdenDetalle update(LabOrdenDetalle update);

	/**
	 * registrar object
	 * 
	 * @param LabOrdenDetalle
	 * @return LabOrdenDetalle
	 */
	LabOrdenDetalle registrar(LabOrdenDetalle especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabOrdenDetalle
	 * @return LabOrdenDetalle
	 */
	LabOrdenDetalle modificar(LabOrdenDetalle especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabOrdenDetalle especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabOrdenDetalle> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabOrdenDetalle obtenerLabOrdenDetalle(Integer id);

	LabOrdenDetalle obtenerLabOrdenDetalle(String nombre);

	List<LabOrdenDetalle> obtenerLabOrdenDetalleOrdenAscPorId();

	List<LabOrdenDetalle> obtenerLabOrdenDetalleOrdenDescPorId();

	List<LabOrdenDetalle> obtenerPorCompania(Compania compania);
	
	List<LabOrdenDetalle> obtenerPorExamen(LabExamen examen);

	List<LabOrdenDetalle> obtenerPorCompania(String nombre, Compania compania);

	List<LabOrdenDetalle> obtenerPorSucursal(Sucursal sucursal);

	List<LabOrdenDetalle> obtenerAllActivos();

}
