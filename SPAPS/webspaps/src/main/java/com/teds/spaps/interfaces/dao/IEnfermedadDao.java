/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Enfermedad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IEnfermedadDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param enfermedad
	 * @return
	 */
	Enfermedad create(Enfermedad enfermedad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Enfermedad update(Enfermedad update);

	/**
	 * registrar object
	 * 
	 * @param Enfermedad
	 * @return Enfermedad
	 */
	Enfermedad registrar(Enfermedad enfermedad);

	/**
	 * modificar object
	 * 
	 * @param Enfermedad
	 * @return Enfermedad
	 */
	Enfermedad modificar(Enfermedad enfermedad);

	/**
	 * eliminar object
	 * 
	 * @param enfermedad
	 * @return
	 */
	boolean eliminar(Enfermedad enfermedad);

	/**
	 * 
	 * @return
	 */
	List<Enfermedad> obtenerTodosActivosEInactivosPorOrdenAsc();

	Enfermedad obtenerEnfermedad(Integer id);

	List<Enfermedad> obtenerEnfermedadOrdenAscPorId();

	List<Enfermedad> obtenerEnfermedadOrdenDescPorId();

	List<Enfermedad> obtenerPorCompania(Compania compania);

	List<Enfermedad> obtenerPorSucursal(Sucursal sucursal);

	List<Enfermedad> obtenerPorHistoriaClinica(HistoriaClinica historiaClinica);

	List<Enfermedad> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<Enfermedad> onComplete(String query);

}
