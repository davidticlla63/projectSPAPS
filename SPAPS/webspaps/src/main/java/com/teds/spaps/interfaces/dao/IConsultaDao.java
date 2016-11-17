/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IConsultaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param consulta
	 * @return
	 */
	Consulta create(Consulta consulta);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Consulta update(Consulta update);

	void delete(Consulta consulta);

	/**
	 * registrar object
	 * 
	 * @param Consulta
	 * @return Consulta
	 */
	Consulta registrar(Consulta consulta);

	boolean registrar(List<Consulta> antecedentes);

	/**
	 * modificar object
	 * 
	 * @param Consulta
	 * @return Consulta
	 */
	Consulta modificar(Consulta consulta);

	/**
	 * eliminar object
	 * 
	 * @param consulta
	 * @return
	 */
	boolean eliminar(Consulta consulta);

	/**
	 * 
	 * @return
	 */
	List<Consulta> obtenerTodosActivosEInactivosPorOrdenAsc();

	Consulta obtenerConsulta(Integer id);

	List<Consulta> obtenerConsultaOrdenAscPorId();

	List<Consulta> obtenerConsultaOrdenDescPorId();

	List<Consulta> obtenerPorCompania(Compania compania);

	List<Consulta> obtenerPorSucursal(Sucursal sucursal);

	List<Consulta> obtenerPorHM(HistoriaClinica historiaClinica);

	List<Consulta> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<Consulta> obtenerPorPaciente(Paciente paciente);

	List<Consulta> onComplete(String query);

	/**
	 * @param paciente
	 * @param compania
	 * @return
	 */
	Consulta obtenerReConsulta(Paciente paciente, Compania compania);

	/**
	 * @param paciente
	 * @param compania
	 * @return
	 */
	Consulta obtenerConsultaActualDePaciente(Paciente paciente,
			Compania compania);

	/**
	 * @param paciente
	 * @param compania
	 * @return
	 */
	boolean tieneConsultaPaciente(Paciente paciente, Compania compania);

}
