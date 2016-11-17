/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Cargo;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ICargoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param cargo
	 * @return
	 */
	Cargo create(Cargo cargo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Cargo update(Cargo update);

	/**
	 * registrar object
	 * 
	 * @param Cargo
	 * @return Cargo
	 */
	Cargo registrar(Cargo cargo);

	/**
	 * modificar object
	 * 
	 * @param Cargo
	 * @return Cargo
	 */
	Cargo modificar(Cargo cargo);

	/**
	 * eliminar object
	 * 
	 * @param cargo
	 * @return
	 */
	boolean eliminar(Cargo cargo);

	/**
	 * 
	 * @return
	 */
	List<Cargo> obtenerTodosActivosEInactivosPorOrdenAsc();

	Cargo obtenerCargo(Integer id);

	List<Cargo> obtenerCargoOrdenAscPorId();

	List<Cargo> obtenerCargoOrdenDescPorId();

	List<Cargo> obtenerPorCompania(Compania compania);

	Cargo obtenerCargo(String descripcion);

	List<Cargo> obtenerPorEmpresa(Empresa empresa);

	List<Cargo> obtenerPorEmpresa(String nombre, Empresa empresa);
	
	public List<Cargo> obtenerPorCompania(String nombre, Compania compania);

	List<Cargo> obtenerPorSucursal(Sucursal sucursal);

	List<Cargo> onComplete(String query);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<Cargo> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<Cargo> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
