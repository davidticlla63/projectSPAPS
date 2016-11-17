package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Gestion;


/**
 * Interface used to interact with the Gestion.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IGestionDao {
	
	/**
	 * Use only intermediate transactions
	 * @param gestion
	 * @return
	 */
	Gestion create(Gestion gestion);
	
	/**
	 * Use only intermediate transactions
	 * @param gestion
	 * @return
	 */
	Gestion update(Gestion gestion);
	
	/**
	 * modificar object
	 * @param Gestion
	 * @return Gestion
	 */
	boolean modificar(Gestion gestion) ;
	
	/**
	 * 
	 * @return
	 */
	Gestion obtenerGestion();
	
	/**
	 * 
	 * @return
	 */
	List<Gestion> obtenerTodosActivosEInactivosPorOrdenAsc();

}
