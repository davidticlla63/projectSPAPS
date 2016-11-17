/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoDiagnostico;

/**
 * @author ANITA
 *
 */
public interface ITipoDiagnosticoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param tipoDiagnostico
	 * @return
	 */
	TipoDiagnostico create(TipoDiagnostico tipoDiagnostico);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TipoDiagnostico update(TipoDiagnostico update);

	/**
	 * registrar object
	 * 
	 * @param TipoDiagnostico
	 * @return TipoDiagnostico
	 */
	TipoDiagnostico registrar(TipoDiagnostico tipoDiagnostico);

	/**
	 * modificar object
	 * 
	 * @param TipoDiagnostico
	 * @return TipoDiagnostico
	 */
	TipoDiagnostico modificar(TipoDiagnostico tipoDiagnostico);

	/**
	 * eliminar object
	 * 
	 * @param tipoDiagnostico
	 * @return
	 */
	boolean eliminar(TipoDiagnostico tipoDiagnostico);

	/**
	 * 
	 * @return
	 */
	List<TipoDiagnostico> obtenerTodosActivosEInactivosPorOrdenAsc();

	TipoDiagnostico obtenerTipoDiagnostico(Integer id);

	TipoDiagnostico obtenerTipoDiagnostico(String nombre);

	List<TipoDiagnostico> obtenerTipoDiagnosticoOrdenAscPorId();

	List<TipoDiagnostico> obtenerTipoDiagnosticoOrdenDescPorId();

	List<TipoDiagnostico> obtenerPorCompania(Compania compania);

	List<TipoDiagnostico> obtenerPorSucursal(Sucursal sucursal);

	List<TipoDiagnostico> onComplete(String query);

	List<TipoDiagnostico> obtenerPorCompania(String nombre, Compania compania);

}
