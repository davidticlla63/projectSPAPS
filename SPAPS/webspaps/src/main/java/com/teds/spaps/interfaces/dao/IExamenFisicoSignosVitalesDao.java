/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ExamenFisicoSignosVitales;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IExamenFisicoSignosVitalesDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param examenFisicoSignosVitales
	 * @return
	 */
	ExamenFisicoSignosVitales create(
			ExamenFisicoSignosVitales examenFisicoSignosVitales);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	ExamenFisicoSignosVitales update(ExamenFisicoSignosVitales update);

	/**
	 * registrar object
	 * 
	 * @param ExamenFisicoSignosVitales
	 * @return ExamenFisico
	 */
	ExamenFisicoSignosVitales registrar(
			ExamenFisicoSignosVitales examenFisicoSignosVitales);

	/**
	 * modificar object
	 * 
	 * @param ExamenFisicoSignosVitales
	 * @return ExamenFisico
	 */
	ExamenFisicoSignosVitales modificar(
			ExamenFisicoSignosVitales examenFisicoSignosVitales);

	/**
	 * eliminar object
	 * 
	 * @param examenFisicoSignosVitales
	 * @return
	 */
	boolean eliminar(ExamenFisicoSignosVitales examenFisicoSignosVitales);

	boolean verificarExamenEnfermera(HistoriaClinica historiaClinica,
			Compania compania);

	ExamenFisicoSignosVitales obtenerExamenEnfermera(
			HistoriaClinica historiaClinica, Compania compania);

	/**
	 * 
	 * @return
	 */
	List<ExamenFisicoSignosVitales> obtenerTodosActivosEInactivosPorOrdenAsc();

	ExamenFisicoSignosVitales obtenerExamenFisico(Integer id);

	List<ExamenFisicoSignosVitales> obtenerExamenFisicoOrdenAscPorId();

	List<ExamenFisicoSignosVitales> obtenerExamenFisicoOrdenDescPorId();

	List<ExamenFisicoSignosVitales> obtenerPorCompania(Compania compania);

	List<ExamenFisicoSignosVitales> obtenerPorSucursal(Sucursal sucursal);

	List<ExamenFisicoSignosVitales> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica);

	List<ExamenFisicoSignosVitales> onComplete(String query);

	List<ExamenFisicoSignosVitales> obtenerListaCompania(Compania compania);

}
