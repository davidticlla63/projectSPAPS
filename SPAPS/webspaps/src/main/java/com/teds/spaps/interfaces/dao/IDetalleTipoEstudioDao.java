/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoEstudio;

/**
 * @author ANITA
 *
 */
public interface IDetalleTipoEstudioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleTipoEstudio
	 * @return
	 */
	DetalleTipoEstudio create(DetalleTipoEstudio detalleTipoEstudio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleTipoEstudio update(DetalleTipoEstudio update);

	void delete(DetalleTipoEstudio detalleTipoEstudio);

	/**
	 * registrar object
	 * 
	 * @param DetalleTipoEstudio
	 * @return DetalleTipoEstudio
	 */
	DetalleTipoEstudio registrar(DetalleTipoEstudio detalleTipoEstudio);

	/**
	 * modificar object
	 * 
	 * @param DetalleTipoEstudio
	 * @return DetalleTipoEstudio
	 */
	DetalleTipoEstudio modificar(DetalleTipoEstudio detalleTipoEstudio);

	/**
	 * eliminar object
	 * 
	 * @param detalleTipoEstudio
	 * @return
	 */
	boolean eliminar(DetalleTipoEstudio detalleTipoEstudio);

	/**
	 * 
	 * @return
	 */
	List<DetalleTipoEstudio> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleTipoEstudio obtenerDetalleTipoEstudio(Integer id);

	List<DetalleTipoEstudio> obtenerDetalleTipoEstudioOrdenAscPorId();

	List<DetalleTipoEstudio> obtenerDetalleTipoEstudioOrdenDescPorId();

	List<DetalleTipoEstudio> obtenerPorCompania(Compania compania);

	List<DetalleTipoEstudio> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleTipoEstudio> obtenerPorEstudio(Estudio estudio);

	List<DetalleTipoEstudio> obtenerPorTipoEstudio(TipoEstudio tipoEstudio);

	List<DetalleTipoEstudio> onComplete(String query);

}
