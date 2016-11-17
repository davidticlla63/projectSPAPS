package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Presentacion;

public interface IPresentacionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param presentacion
	 * @return
	 */
	Presentacion create(Presentacion presentacion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Presentacion update(Presentacion update);

	/**
	 * registrar object
	 * 
	 * @param Presentacion
	 * @return Presentacion
	 */
	Presentacion registrar(Presentacion presentacion);

	/**
	 * modificar object
	 * 
	 * @param Presentacion
	 * @return Presentacion
	 */
	Presentacion modificar(Presentacion presentacion);

	/**
	 * eliminar object
	 * 
	 * @param presentacion
	 * @return
	 */
	boolean eliminar(Presentacion presentacion);

	/**
	 * 
	 * @return
	 */
	List<Presentacion> obtenerTodosActivosEInactivosPorOrdenAsc();

	Presentacion obtenerPresentacion(Integer id);

	List<Presentacion> obtenerPorCompania(String descripcion, Compania compania);

	Presentacion obtenerPresentacion(String descripcion, Compania compania);

	List<Presentacion> obtenerPresentacionOrdenAscPorId();

	List<Presentacion> obtenerPresentacionOrdenDescPorId();

	boolean verificarRepetido(Presentacion presentacion, Compania compania);

	boolean verificarRepetidoUpdate(Presentacion presentacion, Compania compania);

	List<Presentacion> obtenerPorCompania(Compania compania);

	List<Presentacion> onComplete(String query);

}
