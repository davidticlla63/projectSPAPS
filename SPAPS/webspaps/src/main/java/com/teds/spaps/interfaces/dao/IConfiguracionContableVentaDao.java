package com.teds.spaps.interfaces.dao;

import com.teds.spaps.model.ConfiguracionContableVenta;
import com.teds.spaps.model.Empresa;


/**
 * Interface used to interact with the ConfiguracionContable.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IConfiguracionContableVentaDao {
	
	/**
	 * Use only intermediate transactions
	 * @param configuracionContable
	 * @return
	 */
	ConfiguracionContableVenta create(ConfiguracionContableVenta configuracionContableVenta);
	
	/**
	 * Use only intermediate transactions
	 * @param configuracionContable
	 * @return
	 */
	ConfiguracionContableVenta update(ConfiguracionContableVenta configuracionContableVenta);
	
	/**
	 * modificar object
	 * @param ConfiguracionContable
	 * @return ConfiguracionContable
	 */
	boolean modificar(ConfiguracionContableVenta configuracionContableVenta) ;
	
	/**
	 * 
	 * @return
	 */
	ConfiguracionContableVenta obtenerPorEmpresa(Empresa empresa);

}
