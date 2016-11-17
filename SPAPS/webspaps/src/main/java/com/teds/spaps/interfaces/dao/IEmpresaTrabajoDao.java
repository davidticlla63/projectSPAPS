/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.EmpresaTrabajo;
import com.teds.spaps.model.Sucursal;

public interface IEmpresaTrabajoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param empresaTrabajo
	 * @return
	 */
	EmpresaTrabajo create(EmpresaTrabajo empresaTrabajo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	EmpresaTrabajo update(EmpresaTrabajo update);

	/**
	 * registrar object
	 * 
	 * @param EmpresaTrabajo
	 * @return EmpresaTrabajo
	 */
	EmpresaTrabajo registrar(EmpresaTrabajo empresaTrabajo);

	/**
	 * modificar object
	 * 
	 * @param EmpresaTrabajo
	 * @return EmpresaTrabajo
	 */
	EmpresaTrabajo modificar(EmpresaTrabajo empresaTrabajo);

	/**
	 * eliminar object
	 * 
	 * @param empresaTrabajo
	 * @return
	 */
	boolean eliminar(EmpresaTrabajo empresaTrabajo);

	/**
	 * 
	 * @return
	 */
	List<EmpresaTrabajo> obtenerTodosActivosEInactivosPorOrdenAsc();

	EmpresaTrabajo obtenerEmpresaTrabajo(Integer id);

	EmpresaTrabajo obtenerEmpresaTrabajo(String descripcion);

	List<EmpresaTrabajo> obtenerEmpresaTrabajoOrdenAscPorId();

	List<EmpresaTrabajo> obtenerEmpresaTrabajoOrdenDescPorId();

	List<EmpresaTrabajo> obtenerPorCompania(Compania compania);

	List<EmpresaTrabajo> obtenerPorSucursal(Sucursal sucursal);

	List<EmpresaTrabajo> obtenerAllActivos();

	boolean verificarRepetido(EmpresaTrabajo empresaTrabajo, Compania compania);

	boolean verificarRepetidoUpdate(EmpresaTrabajo empresaTrabajo,
			Compania compania);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<EmpresaTrabajo> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<EmpresaTrabajo> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

	/**
	 * @param empresaTrabajo
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetido(EmpresaTrabajo empresaTrabajo, Sucursal sucursal);

	/**
	 * @param empresaTrabajo
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetidoUpdate(EmpresaTrabajo empresaTrabajo,
			Sucursal sucursal);

}
