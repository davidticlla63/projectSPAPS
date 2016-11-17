package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.MonedaEmpresa;


/**
 * Interface used to interact with the ParametroEmpresa.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IMonedaEmpresaDao {
	
	/**
	 * Use only intermediate transactions
	 * @param gestion
	 * @return
	 */
	MonedaEmpresa create(MonedaEmpresa monedaEmpresa);
	
	/**
	 * Use only intermediate transactions
	 * @param gestion
	 * @return
	 */
	MonedaEmpresa update(MonedaEmpresa monedaEmpresa);
	

	MonedaEmpresa registrar(MonedaEmpresa monedaEmpresa);
	
	boolean registrar(List<MonedaEmpresa> listMonedaEmpresa);

	boolean modificar(MonedaEmpresa monedaEmpresa);

	boolean eliminar(MonedaEmpresa usuario);

	List<MonedaEmpresa> obtenerMonedaEmpresaOrdenAscPorId();
	
	List<MonedaEmpresa> obtenerMonedaEmpresaOrdenDescPorId();
	
	List<MonedaEmpresa> obtenerTodasPorEmpresa(Empresa empresa);

	MonedaEmpresa obtenerPorId(Integer id);
	
	MonedaEmpresa obtenerNacionalPorEmpresa(Empresa empresa);
	
	MonedaEmpresa obtenerExtranjeraPorEmpresa(Empresa empresa);
}
