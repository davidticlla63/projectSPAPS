/**
 * 
 */
package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.LabProgramacionOrden;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabProgramacionOrdenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param departamento
	 * @return
	 */
	LabProgramacionOrden create(LabProgramacionOrden departamento);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabProgramacionOrden update(LabProgramacionOrden update);

	/**
	 * registrar object
	 * 
	 * @param LabProgramacionOrden
	 * @return LabProgramacionOrden
	 */
	LabProgramacionOrden registrar(LabProgramacionOrden departamento);

	LabProgramacionOrden completarRegistro(LabProgramacionOrden departamento);

	/**
	 * modificar object
	 * 
	 * @param LabProgramacionOrden
	 * @return LabProgramacionOrden
	 */
	LabProgramacionOrden modificar(LabProgramacionOrden departamento);

	/**
	 * eliminar object
	 * 
	 * @param departamento
	 * @return
	 */
	boolean eliminar(LabProgramacionOrden departamento);

	LabProgramacionOrden obtenerLabProgramacionOrden(Integer id);

	List<LabProgramacionOrden> obtenerPorSucursal(Sucursal sucursal);

	List<LabProgramacionOrden> obtenerPorSucursal(String estado, Date fecha,
			Personal medico, Sucursal sucursal);

	List<LabProgramacionOrden> obtenerPorSucursal(String estado,
			Personal medico, Sucursal sucursal);

	List<LabProgramacionOrden> obtenerPorSucursalYFechas(Date fechaIni,
			Date fechaFin, Personal medico, Sucursal sucursal);

}
