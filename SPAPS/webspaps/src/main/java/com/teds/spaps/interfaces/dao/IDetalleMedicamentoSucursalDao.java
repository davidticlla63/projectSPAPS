/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamentoSucursal;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleMedicamentoSucursalDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleMedicamentoSucursal
	 * @return
	 */
	DetalleMedicamentoSucursal create(
			DetalleMedicamentoSucursal detalleMedicamentoSucursal);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleMedicamentoSucursal update(DetalleMedicamentoSucursal update);

	void delete(DetalleMedicamentoSucursal detalleMedicamentoSucursal);

	/**
	 * registrar object
	 * 
	 * @param DetalleMedicamentoSucursal
	 * @return DetalleMedicamentoSucursal
	 */
	DetalleMedicamentoSucursal registrar(
			DetalleMedicamentoSucursal detalleMedicamentoSucursal);

	boolean registrar(List<DetalleMedicamentoSucursal> antecedentes);

	/**
	 * modificar object
	 * 
	 * @param DetalleMedicamentoSucursal
	 * @return DetalleMedicamentoSucursal
	 */
	DetalleMedicamentoSucursal modificar(
			DetalleMedicamentoSucursal detalleMedicamentoSucursal);

	/**
	 * eliminar object
	 * 
	 * @param detalleMedicamentoSucursal
	 * @return
	 */
	boolean eliminar(DetalleMedicamentoSucursal detalleMedicamentoSucursal);

	/**
	 * 
	 * @return
	 */
	List<DetalleMedicamentoSucursal> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleMedicamentoSucursal obtenerDetalleMedicamentoSucursal(Integer id);

	List<DetalleMedicamentoSucursal> obtenerDetalleMedicamentoSucursalOrdenAscPorId();

	List<DetalleMedicamentoSucursal> obtenerDetalleMedicamentoSucursalOrdenDescPorId();

	List<DetalleMedicamentoSucursal> obtenerPorCompania(Compania compania);

	List<DetalleMedicamentoSucursal> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleMedicamentoSucursal> obtenerPorSucursalRegistro(
			Sucursal sucursalRegistro);

	List<DetalleMedicamentoSucursal> obtenerPorMedicamento(
			Medicamento medicamento);

	List<DetalleMedicamentoSucursal> obtenerPorAntecedente(
			Antecedente antecedente);

	List<DetalleMedicamentoSucursal> onComplete(String query);

	boolean verificarRepetido(Sucursal sucursal,
			DetalleMedicamentoSucursal detalleMedicamentoSucursal);

	boolean verificarRepetidoUpdate(Sucursal sucursal,
			DetalleMedicamentoSucursal detalleMedicamentoSucursal);

}
