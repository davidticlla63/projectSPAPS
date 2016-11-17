/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoAntecedente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoAntecedente;

/**
 * @author ANITA
 *
 */
public interface IDetalleTipoAntecedenteDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleTipoAntecedente
	 * @return
	 */
	DetalleTipoAntecedente create(DetalleTipoAntecedente detalleTipoAntecedente);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleTipoAntecedente update(DetalleTipoAntecedente update);

	void delete(DetalleTipoAntecedente detalleTipoAntecedente);

	/**
	 * registrar object
	 * 
	 * @param DetalleTipoAntecedente
	 * @return DetalleTipoAntecedente
	 */
	DetalleTipoAntecedente registrar(
			DetalleTipoAntecedente detalleTipoAntecedente);

	/**
	 * modificar object
	 * 
	 * @param DetalleTipoAntecedente
	 * @return DetalleTipoAntecedente
	 */
	DetalleTipoAntecedente modificar(
			DetalleTipoAntecedente detalleTipoAntecedente);

	/**
	 * eliminar object
	 * 
	 * @param detalleTipoAntecedente
	 * @return
	 */
	boolean eliminar(DetalleTipoAntecedente detalleTipoAntecedente);

	/**
	 * 
	 * @return
	 */
	List<DetalleTipoAntecedente> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleTipoAntecedente obtenerDetalleTipoAntecedente(Integer id);

	List<DetalleTipoAntecedente> obtenerDetalleTipoAntecedenteOrdenAscPorId();

	List<DetalleTipoAntecedente> obtenerDetalleTipoAntecedenteOrdenDescPorId();

	List<DetalleTipoAntecedente> obtenerPorCompania(Compania compania);

	List<DetalleTipoAntecedente> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleTipoAntecedente> obtenerPorAntecedente(Antecedente antecedente);

	List<DetalleTipoAntecedente> obtenerPorTipoAntecedente(
			TipoAntecedente tipoAntecedente);

	List<DetalleTipoAntecedente> onComplete(String query);

}
