/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HCLabOrdenDocumentos;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IHCLabOrdenDocumentosDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param hcLabOrdenDocumentos
	 * @return
	 */
	HCLabOrdenDocumentos create(HCLabOrdenDocumentos hcLabOrdenDocumentos);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	HCLabOrdenDocumentos update(HCLabOrdenDocumentos update);

	/**
	 * registrar object
	 * 
	 * @param HCLabOrdenDocumentos
	 * @return HCLabOrdenDocumentos
	 */
	HCLabOrdenDocumentos registrar(HCLabOrdenDocumentos hcLabOrdenDocumentos);

	/**
	 * modificar object
	 * 
	 * @param HCLabOrdenDocumentos
	 * @return HCLabOrdenDocumentos
	 */
	HCLabOrdenDocumentos modificar(HCLabOrdenDocumentos hcLabOrdenDocumentos);

	/**
	 * eliminar object
	 * 
	 * @param hcLabOrdenDocumentos
	 * @return
	 */
	boolean eliminar(HCLabOrdenDocumentos hcLabOrdenDocumentos);

	/**
	 * 
	 * @return
	 */
	List<HCLabOrdenDocumentos> obtenerTodosActivosEInactivosPorOrdenAsc();

	HCLabOrdenDocumentos obtenerHCLabOrdenDocumentos(Integer id);

	List<HCLabOrdenDocumentos> obtenerHCLabOrdenDocumentosOrdenAscPorId();

	List<HCLabOrdenDocumentos> obtenerHCLabOrdenDocumentosOrdenDescPorId();

	List<HCLabOrdenDocumentos> obtenerPorCompania(Compania compania);

	HCLabOrdenDocumentos obtenerHCLabOrdenDocumentos(String path);

	List<HCLabOrdenDocumentos> obtenerPorOrden(LabOrden orden);

	List<HCLabOrdenDocumentos> obtenerPorSucursal(Sucursal sucursal);

	List<HCLabOrdenDocumentos> onComplete(String query);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<HCLabOrdenDocumentos> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<HCLabOrdenDocumentos> obtenerPorSucursalAutoComplete(
			String descripcion, Sucursal sucursal);

	/**
	 * @param hcLabOrdenDocumentos
	 * @param compania
	 * @return
	 */
	boolean verificarRepetido(HCLabOrdenDocumentos hcLabOrdenDocumentos,
			Compania compania);

	/**
	 * @param hcLabOrdenDocumentos
	 * @param compania
	 * @return
	 */
	boolean verificarRepetidoUpdate(HCLabOrdenDocumentos hcLabOrdenDocumentos,
			Compania compania);

	/**
	 * @param hcLabOrdenDocumentos
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetido(HCLabOrdenDocumentos hcLabOrdenDocumentos,
			Sucursal sucursal);

	/**
	 * @param hcLabOrdenDocumentos
	 * @param sucursal
	 * @return
	 */
	boolean verificarRepetidoUpdate(HCLabOrdenDocumentos hcLabOrdenDocumentos,
			Sucursal sucursal);

}
