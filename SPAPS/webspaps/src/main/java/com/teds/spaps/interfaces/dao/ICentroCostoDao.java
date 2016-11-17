package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.CentroCosto;
import com.teds.spaps.model.Empresa;


/**
 * Interface used to interact with the CentroCosto.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface ICentroCostoDao {
	
	/**
	 * Use only intermediate transactions
	 * @param centroCosto
	 * @return
	 */
	CentroCosto create(CentroCosto centroCosto);
	
	/**
	 * Use only intermediate transactions
	 * @param centroCosto
	 * @return
	 */
	CentroCosto update(CentroCosto centroCosto);
	
	CentroCosto registrar(CentroCosto usuario);

	boolean modificar(CentroCosto usuario);

	boolean eliminar(CentroCosto usuario);

	List<CentroCosto> obtenerOrdenAscPorId();
	
	List<CentroCosto> obtenerOrdenDescPorId();
	
	List<CentroCosto> obtenerTodosPorEmpresa(Empresa empresa);
}
