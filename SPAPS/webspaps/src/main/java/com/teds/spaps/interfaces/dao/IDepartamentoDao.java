/**
 * 
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.Pais;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDepartamentoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param departamento
	 * @return
	 */
	Departamento create(Departamento departamento);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Departamento update(Departamento update);

	/**
	 * registrar object
	 * 
	 * @param Departamento
	 * @return Departamento
	 */
	Departamento registrar(Departamento departamento);

	/**
	 * modificar object
	 * 
	 * @param Departamento
	 * @return Departamento
	 */
	Departamento modificar(Departamento departamento);

	/**
	 * eliminar object
	 * 
	 * @param departamento
	 * @return
	 */
	boolean eliminar(Departamento departamento);

	/**
	 * 
	 * @return
	 */
	List<Departamento> obtenerTodosActivosEInactivosPorOrdenAsc();

	Departamento obtenerDepartamento(Integer id);

	List<Departamento> obtenerDepartamentoOrdenAscPorId();

	List<Departamento> obtenerDepartamentoOrdenDescPorId();

	List<Departamento> obtenerPorCompania(Compania compania);

	List<Departamento> obtenerPorPais(String nombre, Pais pais);

	/**
	 * @param nombre
	 * @param pais
	 * @param compania
	 * @return
	 */
	List<Departamento> obtenerPorPaisCompaniaAutoComplete(String nombre,
			Pais pais, Compania compania);

	/**
	 * @param nombre
	 * @param pais
	 * @param sucursal
	 * @return
	 */
	List<Departamento> obtenerPorPaisSucursalAutoComplete(String nombre,
			Pais pais, Sucursal sucursal);

}
