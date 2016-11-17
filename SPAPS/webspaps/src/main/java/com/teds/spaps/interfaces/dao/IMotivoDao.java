/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IMotivoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param motivo
	 * @return
	 */
	Motivo create(Motivo motivo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Motivo update(Motivo update);

	/**
	 * registrar object
	 * 
	 * @param Motivo
	 * @return Motivo
	 */
	Motivo registrar(Motivo motivo);

	/**
	 * modificar object
	 * 
	 * @param Motivo
	 * @return Motivo
	 */
	Motivo modificar(Motivo motivo);

	/**
	 * eliminar object
	 * 
	 * @param motivo
	 * @return
	 */
	boolean eliminar(Motivo motivo);

	/**
	 * 
	 * @return
	 */
	List<Motivo> obtenerTodosActivosEInactivosPorOrdenAsc();

	Motivo obtenerMotivo(Integer id);

	List<Motivo> obtenerMotivoOrdenAscPorId();

	List<Motivo> obtenerMotivoOrdenDescPorId();

	List<Motivo> obtenerPorCompania(Compania compania);

	List<Motivo> obtenerPorSucursal(Sucursal sucursal);

	List<Motivo> obtenerPorHistoriaClinica(HistoriaClinica historiaClinica);

	List<Motivo> obtenerPorHC(HistoriaClinica historiaClinica, Compania compania);

	List<Motivo> onComplete(String query);

}
