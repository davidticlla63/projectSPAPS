/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamentoPresentacion;
import com.teds.spaps.model.DetalleMedicamentoSucursal;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IMedicamentoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param medicamento
	 * @return
	 */
	Medicamento create(Medicamento medicamento);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Medicamento update(Medicamento update);

	/**
	 * registrar object
	 * 
	 * @param Medicamento
	 * @return Medicamento
	 */
	Medicamento registrar(Medicamento medicamento,
			List<DetalleMedicamentoSucursal> detalle,
			List<DetalleMedicamentoPresentacion> presentaciones);

	/**
	 * modificar object
	 * 
	 * @param Medicamento
	 * @return Medicamento
	 */
	Medicamento modificar(Medicamento medicamento,
			List<DetalleMedicamentoSucursal> medicamentosSacar,
			List<DetalleMedicamentoSucursal> medicamentosMeter,
			List<DetalleMedicamentoPresentacion> presentacionesSacar,
			List<DetalleMedicamentoPresentacion> presentacionesMeter);

	/**
	 * eliminar object
	 * 
	 * @param medicamento
	 * @return
	 */
	boolean eliminar(Medicamento medicamento,
			List<DetalleMedicamentoSucursal> detalle,
			List<DetalleMedicamentoPresentacion> presentaciones);

	/**
	 * 
	 * @return
	 */
	List<Medicamento> obtenerTodosActivosEInactivosPorOrdenAsc();

	Medicamento obtenerMedicamento(Integer id);

	Medicamento obtenerMedicamentoNG(String nombreGenerico,
			String presentacion, Compania compania);

	Medicamento obtenerMedicamentoNC(String nombreComercial,
			String presentacion, Compania compania);

	List<Medicamento> obtenerMedicamentoOrdenAscPorId();

	List<Medicamento> obtenerMedicamentoOrdenDescPorId();

	List<Medicamento> obtenerPorCompania(Compania compania);

	List<Medicamento> obtenerPorSucursal(Sucursal sucursal);

	List<Medicamento> onComplete(String query);

	List<Medicamento> obtenerAutoCompleteNG(String nombreGenerico,
			Compania compania);

	List<Medicamento> obtenerAutoCompleteNC(String nombreComercial,
			Compania compania);

	/**
	 * @param medicamento
	 * @return
	 */
	Medicamento registrar(Medicamento medicamento);

}
