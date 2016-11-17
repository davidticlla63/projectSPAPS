/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleAntecedenteEspecialidadDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleAntecedenteEspecialidad
	 * @return
	 */
	DetalleAntecedenteEspecialidad create(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleAntecedenteEspecialidad update(DetalleAntecedenteEspecialidad update);

	void delete(DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad);

	/**
	 * registrar object
	 * 
	 * @param DetalleAntecedenteEspecialidad
	 * @return DetalleAntecedenteEspecialidad
	 */
	DetalleAntecedenteEspecialidad registrar(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad);

	/**
	 * modificar object
	 * 
	 * @param DetalleAntecedenteEspecialidad
	 * @return DetalleAntecedenteEspecialidad
	 */
	DetalleAntecedenteEspecialidad modificar(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad);

	/**
	 * eliminar object
	 * 
	 * @param detalleAntecedenteEspecialidad
	 * @return
	 */
	boolean eliminar(
			DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad);

	/**
	 * 
	 * @return
	 */
	List<DetalleAntecedenteEspecialidad> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleAntecedenteEspecialidad obtenerDetalleAntecedenteEspecialidad(
			Integer id);

	List<DetalleAntecedenteEspecialidad> obtenerDetalleAntecedenteEspecialidadOrdenAscPorId();

	List<DetalleAntecedenteEspecialidad> obtenerDetalleAntecedenteEspecialidadOrdenDescPorId();

	List<DetalleAntecedenteEspecialidad> obtenerPorCompania(Compania compania);

	List<DetalleAntecedenteEspecialidad> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleAntecedenteEspecialidad> obtenerPorHM(
			HistoriaClinica historiaClinica);

	List<DetalleAntecedenteEspecialidad> obtenerPorAntecedente(
			Antecedente antecedente);

	List<DetalleAntecedenteEspecialidad> obtenerPorEspecialidad(
			Especialidad especialidad);

	List<DetalleAntecedenteEspecialidad> onComplete(String query);

}
