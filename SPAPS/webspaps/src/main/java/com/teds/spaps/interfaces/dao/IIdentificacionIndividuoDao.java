/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IIdentificacionIndividuoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param identificacionPaciente
	 * @return
	 */
	IdentificacionPaciente create(
			IdentificacionPaciente identificacionPaciente);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	IdentificacionPaciente update(IdentificacionPaciente update);

	void delete(IdentificacionPaciente identificacionPaciente);

	/**
	 * registrar object
	 * 
	 * @param IdentificacionPaciente
	 * @return IdentificacionIndividuo
	 */
	IdentificacionPaciente registrar(
			IdentificacionPaciente identificacionPaciente);

	/**
	 * modificar object
	 * 
	 * @param IdentificacionPaciente
	 * @return IdentificacionIndividuo
	 */
	IdentificacionPaciente modificar(
			IdentificacionPaciente identificacionPaciente);

	/**
	 * eliminar object
	 * 
	 * @param identificacionPaciente
	 * @return
	 */
	boolean eliminar(IdentificacionPaciente identificacionPaciente);

	/**
	 * eliminar object real
	 * 
	 * @param identificacionPaciente
	 * @return
	 */
	boolean deletePS(IdentificacionPaciente identificacionPaciente);

	/**
	 * 
	 * @return
	 */
	List<IdentificacionPaciente> obtenerTodosActivosEInactivosPorOrdenAsc();

	IdentificacionPaciente obtenerIdentificacionIndividuo(Integer id);

	IdentificacionPaciente obtenrIdentificacionIndividuo(Integer id,
			Compania compania);

	IdentificacionPaciente obtenerIdentificacionIndividuo(String codigo);

	IdentificacionPaciente obtenerIdentificacionIndividuo(String codigo,
			Compania compania);

	IdentificacionPaciente obtenerIdentificacionIndividuoPorDescripcion(
			String descripcion);

	IdentificacionPaciente obtenerIdentificacionIndividuoPorDescripcion(
			String descripcion, Compania compania);

	List<IdentificacionPaciente> obtenerIdentificacionIndividuoOrdenAscPorId();

	List<IdentificacionPaciente> obtenerIdentificacionIndividuoOrdenDescPorId();

	List<IdentificacionPaciente> obtenerPorCompania(Compania compania);

	List<IdentificacionPaciente> obtenerPorSucursal(Sucursal sucursal);

	List<IdentificacionPaciente> obtenerAllActivos();

	List<IdentificacionPaciente> obtenerPorIndividuo(Paciente paciente);

	boolean verificarDocumento(Sucursal sucursal,
			IdentificacionPaciente identificacionPaciente);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<IdentificacionPaciente> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania);

	/**
	 * @param compania
	 * @param identificacionPaciente
	 * @return
	 */
	boolean verificarDocumento(Compania compania,
			IdentificacionPaciente identificacionPaciente);

}
