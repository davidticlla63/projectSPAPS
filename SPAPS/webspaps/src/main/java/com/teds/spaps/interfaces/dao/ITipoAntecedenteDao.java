/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;

/**
 * @author ANITA
 *
 */
public interface ITipoAntecedenteDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param tipoAntecedente
	 * @return
	 */
	TipoAntecedente create(TipoAntecedente tipoAntecedente);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	TipoAntecedente update(TipoAntecedente update);

	/**
	 * registrar object
	 * 
	 * @param TipoAntecedente
	 * @return TipoAntecedente
	 */
	TipoAntecedente registrar(TipoAntecedente tipoAntecedente);

	/**
	 * modificar object
	 * 
	 * @param TipoAntecedente
	 * @return TipoAntecedente
	 */
	TipoAntecedente modificar(TipoAntecedente tipoAntecedente);

	/**
	 * eliminar object
	 * 
	 * @param tipoAntecedente
	 * @return
	 */
	boolean eliminar(TipoAntecedente tipoAntecedente);

	/**
	 * 
	 * @return
	 */
	List<TipoAntecedente> obtenerTodosActivosEInactivosPorOrdenAsc();

	TipoAntecedente obtenerTipoAntecedente(Integer id);

	TipoAntecedente obtenerTipoAntecedente(String nombre);

	List<TipoAntecedente> obtenerTipoAntecedenteOrdenAscPorId();

	List<TipoAntecedente> obtenerTipoAntecedenteOrdenDescPorId();

	List<TipoAntecedente> obtenerPorCompania(Compania compania);

	List<TipoAntecedente> obtenerPorSucursal(Sucursal sucursal);

	List<TipoAntecedente> onComplete(String query);

}
