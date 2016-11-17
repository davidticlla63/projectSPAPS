package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Barrio;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;

public interface IBarrioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param barrio
	 * @return
	 */
	Barrio create(Barrio barrio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Barrio update(Barrio update);

	/**
	 * registrar object
	 * 
	 * @param Barrio
	 * @return Barrio
	 */
	Barrio registrar(Barrio barrio);

	/**
	 * modificar object
	 * 
	 * @param Barrio
	 * @return Barrio
	 */
	Barrio modificar(Barrio barrio);

	/**
	 * eliminar object
	 * 
	 * @param barrio
	 * @return
	 */
	boolean eliminar(Barrio barrio);

	/**
	 * 
	 * @return
	 */
	List<Barrio> obtenerTodosActivosEInactivosPorOrdenAsc();

	Barrio obtenerBarrio(Integer id);

	List<Barrio> obtenerBarrioOrdenAscPorId();

	List<Barrio> obtenerBarrioOrdenDescPorId();

	boolean verificarRepetido(Barrio barrio, Compania compania);

	boolean verificarRepetidoUpdate(Barrio barrio, Compania compania);

	List<Barrio> obtenerPorCompania(Compania compania);

	List<Barrio> onComplete(String query);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<Barrio> obtenerPorCompaniaAutoComplete(String nombre, Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<Barrio> obtenerPorSucursalAutoComplete(String nombre, Sucursal sucursal);

}
