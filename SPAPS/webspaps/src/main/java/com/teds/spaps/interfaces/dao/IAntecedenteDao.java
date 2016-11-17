/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.DetalleTipoAntecedente;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IAntecedenteDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param antecedente
	 * @return
	 */
	Antecedente create(Antecedente antecedente);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Antecedente update(Antecedente update);

	/**
	 * registrar object
	 * 
	 * @param Antecedente
	 * @return Antecedente
	 */
	Antecedente registrar(Antecedente antecedente,
			List<DetalleAntecedenteEspecialidad> detalle,
			List<DetalleTipoAntecedente> listaTipoAntecedente);

	/**
	 * modificar object
	 * 
	 * @param Antecedente
	 * @return Antecedente
	 */
	Antecedente modificar(Antecedente antecedente,
			List<DetalleAntecedenteEspecialidad> sacar,
			List<DetalleAntecedenteEspecialidad> meter,
			List<DetalleTipoAntecedente> antecedentesSacar,
			List<DetalleTipoAntecedente> antecedentesMeter);

	/**
	 * eliminar object
	 * 
	 * @param antecedente
	 * @return
	 */
	boolean eliminar(Antecedente antecedente,
			List<DetalleTipoAntecedente> detalleTipoAntecedentes,
			List<DetalleAntecedenteEspecialidad> detalle);

	/**
	 * 
	 * @return
	 */
	List<Antecedente> obtenerTodosActivosEInactivosPorOrdenAsc();

	Antecedente obtenerAntecedente(Integer id);

	Antecedente obtenerAntecedente(String descripcion);

	List<Antecedente> obtenerAntecedenteOrdenAscPorId();

	List<Antecedente> obtenerAntecedenteOrdenDescPorId();

	List<Antecedente> obtenerPorCompania(Compania compania);

	List<Antecedente> obtenerPorSucursal(Sucursal sucursal);

	boolean verificarRepetido(String descripcion, Sucursal sucursal);

	List<Antecedente> onComplete(String query);

	List<Antecedente> obtenerPorCompania(String descripcion, Compania compania);

	/**
	 * @param antecedente
	 * @return
	 */
	Antecedente registrar(Antecedente antecedente);

}
