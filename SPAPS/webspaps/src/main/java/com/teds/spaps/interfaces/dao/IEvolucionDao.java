/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Evolucion;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IEvolucionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param evolucion
	 * @return
	 */
	Evolucion create(Evolucion evolucion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Evolucion update(Evolucion update);

	/**
	 * registrar object
	 * 
	 * @param Evolucion
	 * @return Evolucion
	 */
	Evolucion registrar(Evolucion evolucion);

	/**
	 * modificar object
	 * 
	 * @param Evolucion
	 * @return Evolucion
	 */
	Evolucion modificar(Evolucion evolucion);

	/**
	 * eliminar object
	 * 
	 * @param evolucion
	 * @return
	 */
	boolean eliminar(Evolucion evolucion);

	/**
	 * 
	 * @return
	 */
	List<Evolucion> obtenerTodosActivosEInactivosPorOrdenAsc();

	Evolucion obtenerEvolucion(Integer id);

	List<Evolucion> obtenerEvolucionOrdenAscPorId();

	List<Evolucion> obtenerEvolucionOrdenDescPorId();

	List<Evolucion> obtenerPorCompania(Compania compania);

	List<Evolucion> obtenerPorSucursal(Sucursal sucursal);

	List<Evolucion> obtenerPorHM(HistoriaClinica historiaClinica,
			Compania compania);

	List<Evolucion> onComplete(String query);

	/**
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	boolean verificarPorHM(HistoriaClinica historiaClinica, Compania compania);

}
