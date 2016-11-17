/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IHistoriaClinicaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param historiaClinica
	 * @return
	 */
	HistoriaClinica create(HistoriaClinica historiaClinica);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	HistoriaClinica update(HistoriaClinica update);

	/**
	 * registrar object
	 * 
	 * @param HistoriaClinica
	 * @return HistoriaMedica
	 */
	HistoriaClinica registrar(HistoriaClinica historiaClinica);

	/**
	 * modificar object
	 * 
	 * @param HistoriaClinica
	 * @return HistoriaMedica
	 */
	HistoriaClinica modificar(HistoriaClinica historiaClinica);

	/**
	 * eliminar object
	 * 
	 * @param historiaClinica
	 * @return
	 */
	boolean eliminar(HistoriaClinica historiaClinica);

	/**
	 * 
	 * @return
	 */
	List<HistoriaClinica> obtenerTodosActivosEInactivosPorOrdenAsc();

	HistoriaClinica obtenerHistoriaMedica(Integer id);

	List<HistoriaClinica> obtenerHistoriaMedicaOrdenAscPorId();

	List<HistoriaClinica> obtenerHistoriaMedicaOrdenDescPorId();

	HistoriaClinica obtenerPorPaciente(Paciente paciente);

	List<HistoriaClinica> obtenerPorCompania(Compania compania);

	List<HistoriaClinica> obtenerPorSucursal(Sucursal sucursal);

	List<HistoriaClinica> onComplete(String query);

	List<HistoriaClinica> obtenerAutoComplete(String query, Compania compania);

	Integer findActiveCodigoMax(Paciente paciente, Compania compania);

	HistoriaClinica obtenerPorCodigo(String codigo, Compania compania);

	/**
	 * @param paciente
	 * @return
	 */
	boolean verificarHC(Paciente paciente);
}
