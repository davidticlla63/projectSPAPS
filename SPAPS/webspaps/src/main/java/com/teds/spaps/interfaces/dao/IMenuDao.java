package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Menu;

/**
 * Interface used to interact with the Menu.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IMenuDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param menu
	 * @return
	 */
	Menu create(Menu menu);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param menu
	 * @return
	 */
	Menu update(Menu menu);

	/**
	 * registrar object
	 * 
	 * @param Menu
	 * @return Menu
	 */
	Menu registrar(Menu menu);

	/**
	 * modificar object
	 * 
	 * @param Menu
	 * @return Menu
	 */
	Menu modificar(Menu menu);

	/**
	 * eliminar object
	 * 
	 * @param Menu
	 * @return
	 */
	boolean eliminar(Menu menu);

	/**
	 * 
	 * @return List<MenuAccion>
	 */
	List<Menu> obtenerTodosActivosEInactivosPorOrdenAsc();

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
