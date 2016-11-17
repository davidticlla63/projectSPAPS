package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Frecuencia;

public interface IFrecuenciaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param frecuencia
	 * @return
	 */
	Frecuencia create(Frecuencia frecuencia);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Frecuencia update(Frecuencia update);

	/**
	 * registrar object
	 * 
	 * @param Frecuencia
	 * @return Frecuencia
	 */
	Frecuencia registrar(Frecuencia frecuencia);

	/**
	 * modificar object
	 * 
	 * @param Frecuencia
	 * @return Frecuencia
	 */
	Frecuencia modificar(Frecuencia frecuencia);

	/**
	 * eliminar object
	 * 
	 * @param frecuencia
	 * @return
	 */
	boolean eliminar(Frecuencia frecuencia);

	/**
	 * 
	 * @return
	 */
	List<Frecuencia> obtenerTodosActivosEInactivosPorOrdenAsc();

	Frecuencia obtenerFrecuencia(Integer id);

	Frecuencia obtenerFrecuencia(String descripcion, Compania compania);

	List<Frecuencia> obtenerFrecuenciaOrdenAscPorId();

	List<Frecuencia> obtenerFrecuenciaOrdenDescPorId();

	boolean verificarRepetido(Frecuencia frecuencia, Compania compania);

	boolean verificarRepetidoUpdate(Frecuencia frecuencia, Compania compania);

	List<Frecuencia> obtenerPorCompania(Compania compania);

	List<Frecuencia> onComplete(String query);

}
