/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPersonalEspecialidadDao {

	
	boolean deleteDetail(Personal labExamen);
	/**
	 * Use only intermediate transactions
	 * 
	 * @param PersonalEspecialidad
	 * @return
	 */
	PersonalEspecialidad create(
			PersonalEspecialidad PersonalEspecialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	PersonalEspecialidad update(PersonalEspecialidad update);

	void delete(PersonalEspecialidad PersonalEspecialidad);

	/**
	 * registrar object
	 * 
	 * @param PersonalEspecialidad
	 * @return PersonalEspecialidad
	 */
	PersonalEspecialidad registrar(
			PersonalEspecialidad PersonalEspecialidad);

	/**
	 * modificar object
	 * 
	 * @param PersonalEspecialidad
	 * @return PersonalEspecialidad
	 */
	PersonalEspecialidad modificar(
			PersonalEspecialidad PersonalEspecialidad);

	/**
	 * eliminar object
	 * 
	 * @param PersonalEspecialidad
	 * @return
	 */
	boolean eliminar(PersonalEspecialidad PersonalEspecialidad);

	/**
	 * 
	 * @return
	 */
	List<PersonalEspecialidad> obtenerTodosActivosEInactivosPorOrdenAsc();

	PersonalEspecialidad obtenerPersonalEspecialidad(Integer id);

	List<PersonalEspecialidad> obtenerPersonalEspecialidadOrdenAscPorId();

	List<PersonalEspecialidad> obtenerPersonalEspecialidadOrdenDescPorId();

	List<PersonalEspecialidad> obtenerPorCompania(Compania compania);

	List<PersonalEspecialidad> obtenerPorSucursal(Sucursal sucursal);

	List<PersonalEspecialidad> obtenerPorHM(
			HistoriaClinica historiaClinica);

	List<PersonalEspecialidad> obtenerPorPersonal(Personal personal);

	List<PersonalEspecialidad> obtenerPorEspecialidad(
			Especialidad especialidad);

	List<PersonalEspecialidad> onComplete(String query);

}
