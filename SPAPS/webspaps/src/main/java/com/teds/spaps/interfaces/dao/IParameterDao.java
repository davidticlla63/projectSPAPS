package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Parameter;

/**
 * Interface used to interact with the Parameter.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IParameterDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param accion
	 * @return
	 */
	Parameter create(Parameter accion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param parameter
	 * @return
	 */
	Parameter update(Parameter parameter);

	/**
	 * registrar object
	 * 
	 * @param Parameter
	 * @return Parameter
	 */
	Parameter registrar(Parameter parameter);

	/**
	 * modificar object
	 * 
	 * @param Parameter
	 * @return Parameter
	 */
	Parameter modificar(Parameter parameter);

	/**
	 * eliminar object
	 * 
	 * @param Parameter
	 * @return
	 */
	boolean eliminar(Parameter parameter);

	/**
	 * 
	 * @return List<Parameter>
	 */
	List<Parameter> obtenerTodosActivosEInactivosPorOrdenAsc();

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
