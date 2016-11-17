package com.teds.spaps.interfaces.dao;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.ParametroEmpresa;

/**
 * Interface used to interact with the ParametroEmpresa.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IParametroEmpresaDao {
	
	/**
	 * Use only intermediate transactions
	 * @param gestion
	 * @return
	 */
	ParametroEmpresa create(ParametroEmpresa gestion);
	
	/**
	 * Use only intermediate transactions
	 * @param gestion
	 * @return
	 */
	ParametroEmpresa update(ParametroEmpresa gestion);
	

	ParametroEmpresa registrar(ParametroEmpresa usuario);

	boolean modificar(ParametroEmpresa usuario);

	ParametroEmpresa obtenerPorEmpresa(Empresa empresa);

}
