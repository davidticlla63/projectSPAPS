/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleEstudioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleEstudio
	 * @return
	 */
	DetalleEstudio create(DetalleEstudio detalleEstudio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleEstudio update(DetalleEstudio update);

	void delete(DetalleEstudio detalleEstudio);

	/**
	 * registrar object
	 * 
	 * @param DetalleEstudio
	 * @return DetalleEstudio
	 */
	DetalleEstudio registrar(DetalleEstudio detalleEstudio);

	/**
	 * modificar object
	 * 
	 * @param DetalleEstudio
	 * @return DetalleEstudio
	 */
	DetalleEstudio modificar(DetalleEstudio detalleEstudio);

	/**
	 * eliminar object
	 * 
	 * @param detalleEstudio
	 * @return
	 */
	boolean eliminar(DetalleEstudio detalleEstudio);

	/**
	 * 
	 * @return
	 */
	List<DetalleEstudio> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleEstudio obtenerDetalleEstudio(Integer id);

	List<DetalleEstudio> obtenerDetalleEstudioOrdenAscPorId();

	List<DetalleEstudio> obtenerDetalleEstudioOrdenDescPorId();

	List<DetalleEstudio> obtenerPorCompania(Compania compania);

	List<DetalleEstudio> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleEstudio> obtenerPorHM(HistoriaClinica historiaClinica);

	List<DetalleEstudio> obtenerPorEstudio(Estudio estudio);

	List<DetalleEstudio> onComplete(String query);

}
