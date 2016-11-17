/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Parentesco;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IParentescoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param parentesco
	 * @return
	 */
	Parentesco create(Parentesco parentesco);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Parentesco update(Parentesco update);

	/**
	 * registrar object
	 * 
	 * @param Parentesco
	 * @return Parentesco
	 */
	Parentesco registrar(Parentesco parentesco);

	/**
	 * modificar object
	 * 
	 * @param Parentesco
	 * @return Parentesco
	 */
	Parentesco modificar(Parentesco parentesco);

	/**
	 * eliminar object
	 * 
	 * @param parentesco
	 * @return
	 */
	boolean eliminar(Parentesco parentesco);

	/**
	 * 
	 * @return
	 */
	List<Parentesco> obtenerTodosActivosEInactivosPorOrdenAsc();

	Parentesco obtenerParentesco(Integer id);

	List<Parentesco> obtenerParentescoOrdenAscPorId();

	List<Parentesco> obtenerParentescoOrdenDescPorId();

	List<Parentesco> obtenerPorCompania(Compania compania);

	List<Parentesco> obtenerPorEmpresa(Empresa empresa);

	List<Parentesco> obtenerPorSucursal(Sucursal sucursal);

	List<Parentesco> onComplete(String query);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	Parentesco obtenerParentesco(String nombre, Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<Parentesco> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<Parentesco> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

}
