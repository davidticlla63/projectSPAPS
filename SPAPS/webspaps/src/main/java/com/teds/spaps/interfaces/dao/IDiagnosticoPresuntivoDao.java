/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Consulta;
import com.teds.spaps.model.DiagnosticoPresuntivo;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDiagnosticoPresuntivoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param diagnosticoPresuntivo
	 * @return
	 */
	DiagnosticoPresuntivo create(DiagnosticoPresuntivo diagnosticoPresuntivo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DiagnosticoPresuntivo update(DiagnosticoPresuntivo update);

	/**
	 * registrar object
	 * 
	 * @param DiagnosticoPresuntivo
	 * @return DiagnosticoPresuntivo
	 */
	DiagnosticoPresuntivo registrar(DiagnosticoPresuntivo diagnosticoPresuntivo);

	/**
	 * modificar object
	 * 
	 * @param DiagnosticoPresuntivo
	 * @return DiagnosticoPresuntivo
	 */
	DiagnosticoPresuntivo modificar(DiagnosticoPresuntivo diagnosticoPresuntivo);

	/**
	 * eliminar object
	 * 
	 * @param diagnosticoPresuntivo
	 * @return
	 */
	boolean eliminar(DiagnosticoPresuntivo diagnosticoPresuntivo);

	/**
	 * 
	 * @return
	 */
	List<DiagnosticoPresuntivo> obtenerTodosActivosEInactivosPorOrdenAsc();

	DiagnosticoPresuntivo obtenerDiagnosticoPresuntivo(Integer id);

	List<DiagnosticoPresuntivo> obtenerDiagnosticoPresuntivoOrdenAscPorId();

	List<DiagnosticoPresuntivo> obtenerDiagnosticoPresuntivoOrdenDescPorId();

	List<DiagnosticoPresuntivo> obtenerPorCompania(Compania compania);

	List<DiagnosticoPresuntivo> obtenerPorSucursal(Sucursal sucursal);

	List<DiagnosticoPresuntivo> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica);

	List<DiagnosticoPresuntivo> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<DiagnosticoPresuntivo> onComplete(String query);

	/**
	 * @param historiaClinica
	 * @param compania
	 * @param consulta
	 * @return
	 */
	DiagnosticoPresuntivo obtenerParaEvolucion(HistoriaClinica historiaClinica,
			Compania compania, Consulta consulta);

	/**
	 * @param historiaClinica
	 * @param compania
	 * @param consulta
	 * @return
	 */
	boolean verificarParaEvolucion(HistoriaClinica historiaClinica,
			Compania compania, Consulta consulta);

}
