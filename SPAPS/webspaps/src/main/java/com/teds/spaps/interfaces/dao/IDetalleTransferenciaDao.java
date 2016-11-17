/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTransferencia;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Transferencia;

/**
 * @author ANITA
 *
 */
public interface IDetalleTransferenciaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleTransferencia
	 * @return
	 */
	DetalleTransferencia create(DetalleTransferencia detalleTransferencia);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleTransferencia update(DetalleTransferencia update);

	void delete(DetalleTransferencia detalleTransferencia);

	/**
	 * registrar object
	 * 
	 * @param DetalleTransferencia
	 * @return DetalleTransferencia
	 */
	DetalleTransferencia registrar(DetalleTransferencia detalleTransferencia);

	/**
	 * modificar object
	 * 
	 * @param DetalleTransferencia
	 * @return DetalleTransferencia
	 */
	DetalleTransferencia modificar(DetalleTransferencia detalleTransferencia);

	/**
	 * eliminar object
	 * 
	 * @param detalleTransferencia
	 * @return
	 */
	boolean eliminar(DetalleTransferencia detalleTransferencia);

	/**
	 * 
	 * @return
	 */
	List<DetalleTransferencia> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleTransferencia obtenerDetalleTransferencia(Integer id);

	List<DetalleTransferencia> obtenerDetalleTransferenciaOrdenAscPorId();

	List<DetalleTransferencia> obtenerDetalleTransferenciaOrdenDescPorId();

	List<DetalleTransferencia> obtenerPorCompania(Compania compania);

	List<DetalleTransferencia> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleTransferencia> obtenerPorHM(HistoriaClinica historiaClinica);

	List<DetalleTransferencia> obtenerPorTransferencia(
			Transferencia transferencia);

	List<DetalleTransferencia> onComplete(String query);

}
