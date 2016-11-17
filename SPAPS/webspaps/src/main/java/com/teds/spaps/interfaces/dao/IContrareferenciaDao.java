/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Contrareferencia;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IContrareferenciaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param contrareferencia
	 * @return
	 */
	Contrareferencia create(Contrareferencia contrareferencia);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Contrareferencia update(Contrareferencia update);

	/**
	 * registrar object
	 * 
	 * @param Contrareferencia
	 * @return Contrareferencia
	 */
	Contrareferencia registrar(Contrareferencia contrareferencia);

	/**
	 * modificar object
	 * 
	 * @param Contrareferencia
	 * @return Contrareferencia
	 */
	Contrareferencia modificar(Contrareferencia contrareferencia);

	/**
	 * eliminar object
	 * 
	 * @param contrareferencia
	 * @return
	 */
	boolean eliminar(Contrareferencia contrareferencia);

	/**
	 * 
	 * @return
	 */
	List<Contrareferencia> obtenerTodosActivosEInactivosPorOrdenAsc();

	Contrareferencia obtenerContrareferencia(Integer id);

	List<Contrareferencia> obtenerContrareferenciaOrdenAscPorId();

	List<Contrareferencia> obtenerContrareferenciaOrdenDescPorId();

	List<Contrareferencia> obtenerPorCompania(Compania compania);

	List<Contrareferencia> obtenerPorSucursal(Sucursal sucursal);

	List<Contrareferencia> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<Contrareferencia> onComplete(String query);

}
