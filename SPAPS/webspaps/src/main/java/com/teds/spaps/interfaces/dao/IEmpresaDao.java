package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IEmpresaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param empresa
	 * @return
	 */
	Empresa create(Empresa empresa);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Empresa update(Empresa update);

	/**
	 * registrar object
	 * 
	 * @param Empresa
	 * @return Empresa
	 */
	Empresa registrar(Empresa empresa);

	/**
	 * modificar object
	 * 
	 * @param Empresa
	 * @return Empresa
	 */
	Empresa modificar(Empresa empresa);

	/**
	 * eliminar object
	 * 
	 * @param empresa
	 * @return
	 */
	boolean eliminar(Empresa empresa);

	/**
	 * 
	 * @return
	 */
	List<Empresa> obtenerTodosActivosEInactivosPorOrdenAsc();

	Empresa obtenerEmpresa(Integer id);

	Empresa obtenerEmpresa(String descripcion);

	List<Empresa> obtenerEmpresaOrdenAscPorId();

	List<Empresa> obtenerEmpresaOrdenDescPorId();

	List<Empresa> obtenerPorCompania(Compania compania);

	List<Empresa> obtenerPorCompania(String nombre, Compania compania);

	List<Empresa> obtenerPorSucursal(Sucursal sucursal);

	List<Empresa> obtenerAllActivos();

	List<Empresa> onComplete(String query);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<Empresa> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<Empresa> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);
	Empresa DevolverEmpresaPorId(Integer id);
	List<Empresa> devolverEmpresaOnCompleteCompania(Compania compania, String nombre);
}
