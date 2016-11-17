/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ExamenFisicoSegmentario;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IExamenFisicoSegmentarioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param examenFisicoSegmentario
	 * @return
	 */
	ExamenFisicoSegmentario create(
			ExamenFisicoSegmentario examenFisicoSegmentario);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	ExamenFisicoSegmentario update(ExamenFisicoSegmentario update);

	/**
	 * registrar object
	 * 
	 * @param ExamenFisicoSegmentario
	 * @return ExamenFisico
	 */
	ExamenFisicoSegmentario registrar(
			ExamenFisicoSegmentario examenFisicoSegmentario);

	/**
	 * modificar object
	 * 
	 * @param ExamenFisicoSegmentario
	 * @return ExamenFisico
	 */
	ExamenFisicoSegmentario modificar(
			ExamenFisicoSegmentario examenFisicoSegmentario);

	/**
	 * eliminar object
	 * 
	 * @param examenFisicoSegmentario
	 * @return
	 */
	boolean eliminar(ExamenFisicoSegmentario examenFisicoSegmentario);

	/**
	 * 
	 * @return
	 */
	List<ExamenFisicoSegmentario> obtenerTodosActivosEInactivosPorOrdenAsc();

	ExamenFisicoSegmentario obtenerExamenFisico(Integer id);

	List<ExamenFisicoSegmentario> obtenerExamenFisicoOrdenAscPorId();

	List<ExamenFisicoSegmentario> obtenerExamenFisicoOrdenDescPorId();

	List<ExamenFisicoSegmentario> obtenerPorCompania(Compania compania);

	List<ExamenFisicoSegmentario> obtenerPorSucursal(Sucursal sucursal);

	List<ExamenFisicoSegmentario> obtenerPorHistoriaClinica(
			HistoriaClinica historiaClinica);

	List<ExamenFisicoSegmentario> onComplete(String query);

}
