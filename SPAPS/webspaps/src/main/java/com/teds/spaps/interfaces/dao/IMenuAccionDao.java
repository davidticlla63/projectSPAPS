package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.MenuAccion;

/**
 * Interface used to interact with the MenuAccion.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IMenuAccionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param menuAccion
	 * @return
	 */
	MenuAccion create(MenuAccion menuAccion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param menuAccion
	 * @return
	 */
	MenuAccion update(MenuAccion menuAccion);

	/**
	 * registrar object
	 * 
	 * @param MenuAccion
	 * @return MenuAccion
	 */
	MenuAccion registrar(MenuAccion menuAccion);

	/**
	 * modificar object
	 * 
	 * @param MenuAccion
	 * @return MenuAccion
	 */
	MenuAccion modificar(MenuAccion menuAccion);

	/**
	 * eliminar object
	 * 
	 * @param menuAccion
	 * @return
	 */
	boolean eliminar(MenuAccion menuAccion);

	/**
	 * 
	 * @return List<MenuAccion>
	 */
	List<MenuAccion> obtenerTodosActivosEInactivosPorOrdenAsc();

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
