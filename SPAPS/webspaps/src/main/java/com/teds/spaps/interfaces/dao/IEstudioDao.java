/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IEstudioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param estudio
	 * @return
	 */
	Estudio create(Estudio estudio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Estudio update(Estudio update);

	/**
	 * registrar object
	 * 
	 * @param listaDetalleTipoEstudio
	 * 
	 * @param Estudio
	 * @return Estudio
	 */
	Estudio registrar(Estudio estudio,
			List<DetalleTipoEstudio> listaDetalleTipoEstudio);

	/**
	 * modificar object
	 * 
	 * @param Estudio
	 * @return Estudio
	 */
	Estudio modificar(Estudio estudio, List<DetalleTipoEstudio> sacar,
			List<DetalleTipoEstudio> meter);

	/**
	 * eliminar object
	 * 
	 * @param estudio
	 * @return
	 */
	boolean eliminar(Estudio estudio,
			List<DetalleTipoEstudio> detalleTipoEstudios);

	/**
	 * 
	 * @return
	 */
	List<Estudio> obtenerTodosActivosEInactivosPorOrdenAsc();

	Estudio obtenerEstudio(Integer id);

	List<Estudio> obtenerEstudioOrdenAscPorId();

	List<Estudio> obtenerEstudioOrdenDescPorId();

	List<Estudio> obtenerPorCompania(Compania compania);

	List<Estudio> obtenerPorSucursal(Sucursal sucursal);

	List<Estudio> onComplete(String query);

}
