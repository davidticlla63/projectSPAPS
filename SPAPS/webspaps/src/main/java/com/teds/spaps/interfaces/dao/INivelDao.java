package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Nivel;


/**
 * Interface used to interact with the Nivel.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface INivelDao {
	
	/**
	 * Use only intermediate transactions
	 * @param nivel
	 * @return
	 */
	Nivel create(Nivel nivel);
	
	/**
	 * Use only intermediate transactions
	 * @param nivel
	 * @return
	 */
	Nivel update(Nivel nivel);
	
	Nivel findByNivelEmpresa(int nivel,Empresa empresa);
	
	List<Nivel> obtenerOrdenAscPorId();
	
	List<Nivel> obtenerOrdenDescPorId();
	
	Nivel obtenerPrimer();

}
