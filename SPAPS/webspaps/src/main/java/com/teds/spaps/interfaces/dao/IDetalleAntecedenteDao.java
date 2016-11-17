/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleAntecedente;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleAntecedenteDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleAntecedente
	 * @return
	 */
	DetalleAntecedente create(DetalleAntecedente detalleAntecedente);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleAntecedente update(DetalleAntecedente update);

	void delete(DetalleAntecedente detalleAntecedente);

	/**
	 * registrar object
	 * 
	 * @param DetalleAntecedente
	 * @return DetalleAntecedente
	 */
	DetalleAntecedente registrar(DetalleAntecedente detalleAntecedente);

	boolean registrar(List<DetalleAntecedente> antecedentes);

	/**
	 * modificar object
	 * 
	 * @param DetalleAntecedente
	 * @return DetalleAntecedente
	 */
	DetalleAntecedente modificar(DetalleAntecedente detalleAntecedente);

	/**
	 * eliminar object
	 * 
	 * @param detalleAntecedente
	 * @return
	 */
	boolean eliminar(DetalleAntecedente detalleAntecedente);

	/**
	 * 
	 * @return
	 */
	List<DetalleAntecedente> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleAntecedente obtenerDetalleAntecedente(Integer id);

	List<DetalleAntecedente> obtenerDetalleAntecedenteOrdenAscPorId();

	List<DetalleAntecedente> obtenerDetalleAntecedenteOrdenDescPorId();

	List<DetalleAntecedente> obtenerPorCompania(Compania compania);

	List<DetalleAntecedente> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleAntecedente> obtenerPorHM(HistoriaClinica historiaClinica);

	List<DetalleAntecedente> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<DetalleAntecedente> obtenerPorAntecedente(Antecedente antecedente);

	List<DetalleAntecedente> onComplete(String query);

	boolean verificarRepetido(HistoriaClinica historiaClinica,
			DetalleAntecedente detalleAntecedente);

}
