/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMotivo;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Motivo;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleMotivoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleMotivo
	 * @return
	 */
	DetalleMotivo create(DetalleMotivo detalleMotivo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleMotivo update(DetalleMotivo update);

	void delete(DetalleMotivo detalleMotivo);

	/**
	 * registrar object
	 * 
	 * @param DetalleMotivo
	 * @return DetalleMotivo
	 */
	DetalleMotivo registrar(DetalleMotivo detalleMotivo);

	/**
	 * modificar object
	 * 
	 * @param DetalleMotivo
	 * @return DetalleMotivo
	 */
	DetalleMotivo modificar(DetalleMotivo detalleMotivo);

	/**
	 * eliminar object
	 * 
	 * @param detalleMotivo
	 * @return
	 */
	boolean eliminar(DetalleMotivo detalleMotivo);

	/**
	 * 
	 * @return
	 */
	List<DetalleMotivo> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleMotivo obtenerDetalleMotivo(Integer id);

	List<DetalleMotivo> obtenerDetalleMotivoOrdenAscPorId();

	List<DetalleMotivo> obtenerDetalleMotivoOrdenDescPorId();

	List<DetalleMotivo> obtenerPorCompania(Compania compania);

	List<DetalleMotivo> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleMotivo> obtenerPorHM(HistoriaClinica historiaClinica);

	List<DetalleMotivo> obtenerPorMotivo(Motivo motivo);

	List<DetalleMotivo> onComplete(String query);

}
