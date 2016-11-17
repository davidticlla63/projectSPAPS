/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.LabValoresRef;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabValoresRefDao {
	
	boolean deleteDetail(LabExamen labExamen);

	void delete(LabValoresRef labExamen) ;
	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	LabValoresRef create(LabValoresRef especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabValoresRef update(LabValoresRef update);

	/**
	 * registrar object
	 * 
	 * @param LabValoresRef
	 * @return LabValoresRef
	 */
	LabValoresRef registrar(LabValoresRef especialidad);

	/**
	 * modificar object
	 * 
	 * @param LabValoresRef
	 * @return LabValoresRef
	 */
	LabValoresRef modificar(LabValoresRef especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(LabValoresRef especialidad);

	/**
	 * 
	 * @return
	 */
	List<LabValoresRef> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabValoresRef obtenerLabValoresRef(Integer id);

	LabValoresRef obtenerLabValoresRef(String nombre);

	List<LabValoresRef> obtenerLabValoresRefOrdenAscPorId();

	List<LabValoresRef> obtenerLabValoresRefOrdenDescPorId();

	List<LabValoresRef> obtenerPorCompania(Compania compania);
	
	List<LabValoresRef> obtenerPorExamenDetalle(LabExamenDetalle examen);

	List<LabValoresRef> obtenerPorCompania(String nombre, Compania compania);

	List<LabValoresRef> obtenerPorSucursal(Sucursal sucursal);

	List<LabValoresRef> obtenerAllActivos();

}
