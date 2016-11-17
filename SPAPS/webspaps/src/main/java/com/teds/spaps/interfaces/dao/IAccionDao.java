package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Accion;

/**
 * Interface used to interact with the Accion.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IAccionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param accion
	 * @return
	 */
	Accion create(Accion accion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param accion
	 * @return
	 */
	Accion update(Accion accion);

	/**
	 * registrar object
	 * 
	 * @param Accion
	 * @return Accion
	 */
	Accion registrar(Accion accion);

	/**
	 * modificar object
	 * 
	 * @param Accion
	 * @return Accion
	 */
	Accion modificar(Accion accion);

	/**
	 * eliminar object
	 * 
	 * @param Accion
	 * @return
	 */
	boolean eliminar(Accion accion);

	/**
	 * 
	 * @return List<MenuAccion>
	 */
	List<Accion> obtenerTodosActivosEInactivosPorOrdenAsc();

	// /**
	// * obtenerMenuAccion
	// * @param id
	// * @return MenuAccion
	// */
	// MenuAccion obtenerMenuAccion(Integer id);
	//
	// /**
	// * obtenerMenuAccionPorMenu
	// * @param id
	// * @return List<MenuAccion>
	// */
	// List<MenuAccion> obtenerMenuAccionPorMenu(Integer id);
	//
	// /**
	// * obtenerOrdenAscPorId
	// * @return List<MenuAccion>
	// */
	// List<MenuAccion> obtenerOrdenAscPorId();
	//
	// /**
	// * obtenerOrdenDescPorId
	// * @return List<MenuAccion>
	// */
	// List<MenuAccion> obtenerOrdenDescPorId() ;

}
