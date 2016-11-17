/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Departamento;
import com.teds.spaps.model.Provincia;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IProvinciaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param provincia
	 * @return
	 */
	Provincia create(Provincia provincia);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Provincia update(Provincia update);

	/**
	 * registrar object
	 * 
	 * @param Provincia
	 * @return Provincia
	 */
	Provincia registrar(Provincia provincia);

	/**
	 * modificar object
	 * 
	 * @param Provincia
	 * @return Provincia
	 */
	Provincia modificar(Provincia provincia);

	/**
	 * eliminar object
	 * 
	 * @param provincia
	 * @return
	 */
	boolean eliminar(Provincia provincia);

	/**
	 * 
	 * @return
	 */
	List<Provincia> obtenerTodosActivosEInactivosPorOrdenAsc();

	Provincia obtenerProvincia(Integer id);

	List<Provincia> obtenerProvinciaOrdenAscPorId();

	List<Provincia> obtenerProvinciaOrdenDescPorId();

	List<Provincia> obtenerPorCompania(Compania compania);

	List<Provincia> obtenerPorDepartamento(String nombre,
			Departamento departamento);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<Provincia> obtenerPorDepartamentoCompaniaAutoComplete(String nombre,
			Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<Provincia> obtenerPorDepartamentoSucursalAutoComplete(String nombre,
			Sucursal sucursal);

	/**
	 * @param nombre
	 * @param departamento
	 * @param compania
	 * @return
	 */
	List<Provincia> obtenerPorDepartamentoCompaniaAutoComplete(String nombre,
			Departamento departamento, Compania compania);

	/**
	 * @param nombre
	 * @param departamento
	 * @param sucursal
	 * @return
	 */
	List<Provincia> obtenerPorDepartamentoSucursalAutoComplete(String nombre,
			Departamento departamento, Sucursal sucursal);

}
