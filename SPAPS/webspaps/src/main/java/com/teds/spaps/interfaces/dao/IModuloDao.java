package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Modulo;

/**
 * Interface used to interact with the Modulo.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IModuloDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param modulo
	 * @return
	 */
	Modulo create(Modulo modulo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param modulo
	 * @return
	 */
	Modulo update(Modulo modulo);

	/**
	 * registrar object
	 * 
	 * @param Modulo
	 * @return Modulo
	 */
	Modulo registrar(Modulo modulo);

	/**
	 * modificar object
	 * 
	 * @param Modulo
	 * @return Modulo
	 */
	Modulo modificar(Modulo modulo);

	/**
	 * eliminar object
	 * 
	 * @param modulo
	 * @return boolean
	 */
	boolean eliminar(Modulo modulo);

	/**
	 * 
	 * @return List<Modulo>
	 */
	List<Modulo> obtenerTodosActivosEInactivosPorOrdenAsc();

	//
	// /**
	// * obtenerModuloPorNombre
	// * @param nombre
	// * @return Modulo
	// */
	// Modulo obtenerModuloPorNombre(String nombre);
	//
	//
	// /**
	// * obtenerModuloOrdenAscPorId
	// * @return List<Modulo>
	// */
	// List<Modulo> obtenerModuloOrdenAscPorId() ;
	//
	// /**
	// * obtenerModuloOrdenDescPorId
	// * @return List<Modulo>
	// */
	// List<Modulo> obtenerModuloOrdenDescPorId();

}
