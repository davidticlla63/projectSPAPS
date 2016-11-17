package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Rol;

/**
 * Interface used to interact with the Rol.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IRolDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param rol
	 * @return
	 */
	Rol create(Rol rol);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param rol
	 * @return
	 */
	Rol update(Rol rol);

	/**
	 * registrar object
	 * 
	 * @param Rol
	 * @return Rol
	 */
	Rol registrar(Rol rol);

	/**
	 * modificar object
	 * 
	 * @param Rol
	 * @return Rol
	 */
	boolean modificar(Rol rol);

	/**
	 * eliminar object
	 * 
	 * @param menuAccion
	 * @return
	 */
	boolean eliminar(Rol rol);

	/**
	 * 
	 * @return
	 */
	List<Rol> obtenerTodosActivosEInactivosPorOrdenAsc();
	
	/**
	 * 
	 * @return
	 */
	List<Rol> obtenerTodosActivosEInactivosRpoCompaniaPorOrdenAsc(Compania compania);

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
