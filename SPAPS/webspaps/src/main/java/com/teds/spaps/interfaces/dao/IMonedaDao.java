package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Moneda;


/**
 * Interface used to interact with the Moneda.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IMonedaDao {
	
	/**
	 * Use only intermediate transactions
	 * @param moneda
	 * @return
	 */
	Moneda create(Moneda moneda);
	
	/**
	 * Use only intermediate transactions
	 * @param moneda
	 * @return
	 */
	Moneda update(Moneda moneda);
	
	/**
	 * 
	 * @return
	 */
	List<Moneda> obtenerTodosActivosEInactivosPorOrdenAsc();

}
