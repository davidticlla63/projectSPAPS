package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Gestion;
import com.teds.spaps.model.TipoCuenta;


/**
 * Interface used to interact with the TipoCuenta.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ITipoCuentaDao {
	
	/**
	 * Use only intermediate transactions
	 * @param tipoCuenta
	 * @return
	 */
	TipoCuenta create(TipoCuenta tipoCuenta);
	
	/**
	 * Use only intermediate transactions
	 * @param tipoCuenta
	 * @return
	 */
	TipoCuenta update(TipoCuenta tipoCuenta);
	
	List<TipoCuenta> obtenerOrdenAscPorId();
	
	List<TipoCuenta> obtenerOrdenDescPorId();
	
	List<TipoCuenta> obtenerActivosOrdenDescPorId();
	
	List<TipoCuenta> obtenerActivosOrdenAscPorId();
	
	List<TipoCuenta> obtenerPorEmpresaYGesstion(Empresa empresa,Gestion gestion);
	
	

}
