/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamento;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleMedicamentoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleMedicamento
	 * @return
	 */
	DetalleMedicamento create(DetalleMedicamento detalleMedicamento);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleMedicamento update(DetalleMedicamento update);

	void delete(DetalleMedicamento detalleMedicamento);

	/**
	 * registrar object
	 * 
	 * @param DetalleMedicamento
	 * @return DetalleMedicamento
	 */
	DetalleMedicamento registrar(DetalleMedicamento detalleMedicamento);

	boolean registrar(List<DetalleMedicamento> medicamentos);

	/**
	 * modificar object
	 * 
	 * @param DetalleMedicamento
	 * @return DetalleMedicamento
	 */
	DetalleMedicamento modificar(DetalleMedicamento detalleMedicamento);

	/**
	 * eliminar object
	 * 
	 * @param detalleMedicamento
	 * @return
	 */
	boolean eliminar(DetalleMedicamento detalleMedicamento);

	boolean verificarRepetido(HistoriaClinica historiaClinica,
			DetalleMedicamento detalleMedicamento);

	/**
	 * 
	 * @return
	 */
	List<DetalleMedicamento> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleMedicamento obtenerDetalleMedicamento(Integer id);

	List<DetalleMedicamento> obtenerDetalleMedicamentoOrdenAscPorId();

	List<DetalleMedicamento> obtenerDetalleMedicamentoOrdenDescPorId();

	List<DetalleMedicamento> obtenerPorCompania(Compania compania);

	List<DetalleMedicamento> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleMedicamento> obtenerPorHM(HistoriaClinica historiaClinica);

	List<DetalleMedicamento> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<DetalleMedicamento> obtenerPorMedicamento(Medicamento medicamento);

	List<DetalleMedicamento> onComplete(String query);

	List<DetalleMedicamento> obtenerPorMedicamento(Medicamento medicamento,
			Compania compania);

	Integer obtenerMax(Compania compania);

	Integer obtenerMaxID(Compania compania);

	List<DetalleMedicamento> obtenerUltimaReceta(Compania compania);

	/**
	 * @param receta
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	List<DetalleMedicamento> obtenerPorReceta(Integer receta,
			HistoriaClinica historiaClinica, Compania compania);

}
