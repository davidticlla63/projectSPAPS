/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamentoPresentacion;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Presentacion;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleMedicamentoPresentacionDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleMedicamentoPresentacion
	 * @return
	 */
	DetalleMedicamentoPresentacion create(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleMedicamentoPresentacion update(DetalleMedicamentoPresentacion update);

	void delete(DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

	/**
	 * registrar object
	 * 
	 * @param DetalleMedicamentoPresentacion
	 * @return DetalleMedicamentoPresentacion
	 */
	DetalleMedicamentoPresentacion registrar(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

	boolean registrar(List<DetalleMedicamentoPresentacion> antecedentes);

	/**
	 * modificar object
	 * 
	 * @param DetalleMedicamentoPresentacion
	 * @return DetalleMedicamentoPresentacion
	 */
	DetalleMedicamentoPresentacion modificar(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

	/**
	 * eliminar object
	 * 
	 * @param detalleMedicamentoPresentacion
	 * @return
	 */
	boolean eliminar(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

	/**
	 * 
	 * @return
	 */
	List<DetalleMedicamentoPresentacion> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleMedicamentoPresentacion obtenerDetalleMedicamentoPresentacion(
			Integer id);

	List<DetalleMedicamentoPresentacion> obtenerDetalleMedicamentoPresentacionOrdenAscPorId();

	List<DetalleMedicamentoPresentacion> obtenerDetalleMedicamentoPresentacionOrdenDescPorId();

	List<DetalleMedicamentoPresentacion> obtenerPorCompania(Compania compania);

	List<DetalleMedicamentoPresentacion> obtenerPorPresentacion(
			Presentacion presentacion, Compania compania);

	List<DetalleMedicamentoPresentacion> obtenerPorSucursalRegistro(
			Sucursal sucursalRegistro);

	List<DetalleMedicamentoPresentacion> obtenerPorMedicamento(
			Medicamento medicamento, Compania compania);

	List<DetalleMedicamentoPresentacion> onComplete(String query);

	boolean verificarRepetido(Compania compania,
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

	boolean verificarRepetidoUpdate(Compania compania,
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion);

}
