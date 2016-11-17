/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenSubDetalle;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabOrdenSubDetalleDao {
	
	boolean deleteDetail(LabOrden labExamen);
	
	void delete(LabOrdenSubDetalle labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabOrdenSubDetalle create(LabOrdenSubDetalle especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabOrdenSubDetalle update(LabOrdenSubDetalle update);

	/**
	 * registrar object
	 * 
	 * @param LabOrdenSubDetalle
	 * @return LabOrdenSubDetalle
	 */
	LabOrdenSubDetalle registrar(LabOrdenSubDetalle especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabOrdenSubDetalle
	 * @return LabOrdenSubDetalle
	 */
	LabOrdenSubDetalle modificar(LabOrdenSubDetalle especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabOrdenSubDetalle especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabOrdenSubDetalle> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabOrdenSubDetalle obtenerLabOrdenSubDetalle(Integer id);

	LabOrdenSubDetalle obtenerLabOrdenSubDetalle(String nombre);

	List<LabOrdenSubDetalle> obtenerLabOrdenSubDetalleOrdenAscPorId();

	List<LabOrdenSubDetalle> obtenerLabOrdenSubDetalleOrdenDescPorId();

	List<LabOrdenSubDetalle> obtenerPorCompania(Compania compania);
	
	List<LabOrdenSubDetalle> obtenerPorExamen(LabExamen examen);

	List<LabOrdenSubDetalle> obtenerPorCompania(String nombre, Compania compania);

	List<LabOrdenSubDetalle> obtenerPorSucursal(Sucursal sucursal);

	List<LabOrdenSubDetalle> obtenerAllActivos();

}
