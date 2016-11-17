package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.ConfiguracionContable;
import com.teds.spaps.model.Empresa;


/**
 * Interface used to interact with the ConfiguracionContable.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IConfiguracionContableDao {
	
	/**
	 * Use only intermediate transactions
	 * @param configuracionContable
	 * @return
	 */
	ConfiguracionContable create(ConfiguracionContable configuracionContable);
	
	/**
	 * Use only intermediate transactions
	 * @param configuracionContable
	 * @return
	 */
	ConfiguracionContable update(ConfiguracionContable configuracionContable);
	
	/**
	 * modificar object
	 * @param ConfiguracionContable
	 * @return ConfiguracionContable
	 */
	boolean modificar(ConfiguracionContable configuracionContable) ;
	
	/**
	 * 
	 * @return
	 */
	ConfiguracionContable obtenerPorEmpresac(Empresa empresa);

}
