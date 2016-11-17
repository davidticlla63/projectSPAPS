/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Transferencia;

/**
 * @author ANITA
 *
 */
public interface ITransferenciaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param transferencia
	 * @return
	 */
	Transferencia create(Transferencia transferencia);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Transferencia update(Transferencia update);

	/**
	 * registrar object
	 * 
	 * @param Transferencia
	 * @return Transferencia
	 */
	Transferencia registrar(Transferencia transferencia);

	/**
	 * modificar object
	 * 
	 * @param Transferencia
	 * @return Transferencia
	 */
	Transferencia modificar(Transferencia transferencia);

	/**
	 * eliminar object
	 * 
	 * @param transferencia
	 * @return
	 */
	boolean eliminar(Transferencia transferencia);

	/**
	 * 
	 * @return
	 */
	List<Transferencia> obtenerTodosActivosEInactivosPorOrdenAsc();

	Transferencia obtenerTransferencia(Integer id);

	List<Transferencia> obtenerTransferenciaOrdenAscPorId();

	List<Transferencia> obtenerTransferenciaOrdenDescPorId();

	List<Transferencia> obtenerPorCompania(Compania compania);

	List<Transferencia> obtenerPorSucursal(Sucursal sucursal);

	List<Transferencia> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<Transferencia> onComplete(String query);

	/**
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	Transferencia obtenerTransferenciaParaContra(
			HistoriaClinica historiaClinica, Compania compania);

	/**
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	boolean verificarTransferencia(HistoriaClinica historiaClinica,
			Compania compania);

}
