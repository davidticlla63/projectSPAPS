package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Via;

public interface IViaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param via
	 * @return
	 */
	Via create(Via via);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Via update(Via update);

	/**
	 * registrar object
	 * 
	 * @param Via
	 * @return Via
	 */
	Via registrar(Via via);

	/**
	 * modificar object
	 * 
	 * @param Via
	 * @return Via
	 */
	Via modificar(Via via);

	/**
	 * eliminar object
	 * 
	 * @param via
	 * @return
	 */
	boolean eliminar(Via via);

	/**
	 * 
	 * @return
	 */
	List<Via> obtenerTodosActivosEInactivosPorOrdenAsc();

	Via obtenerVia(Integer id);

	Via obtenerVia(String descripcion, Compania compania);

	List<Via> obtenerViaOrdenAscPorId();

	List<Via> obtenerViaOrdenDescPorId();

	boolean verificarRepetido(Via via, Compania compania);

	boolean verificarRepetidoUpdate(Via via, Compania compania);

	List<Via> obtenerPorCompania(Compania compania);

	List<Via> onComplete(String query);

}
