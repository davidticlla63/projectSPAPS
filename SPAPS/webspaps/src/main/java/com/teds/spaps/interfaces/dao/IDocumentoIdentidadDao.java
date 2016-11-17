/**
 * 
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DocumentoIdentidad;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDocumentoIdentidadDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param documentoIdentidad
	 * @return
	 */
	DocumentoIdentidad create(DocumentoIdentidad documentoIdentidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DocumentoIdentidad update(DocumentoIdentidad update);

	/**
	 * registrar object
	 * 
	 * @param DocumentoIdentidad
	 * @return DocumentoIdentidad
	 */
	DocumentoIdentidad registrar(DocumentoIdentidad documentoIdentidad);

	/**
	 * modificar object
	 * 
	 * @param DocumentoIdentidad
	 * @return DocumentoIdentidad
	 */
	DocumentoIdentidad modificar(DocumentoIdentidad documentoIdentidad);

	/**
	 * eliminar object
	 * 
	 * @param documentoIdentidad
	 * @return
	 */
	boolean eliminar(DocumentoIdentidad documentoIdentidad);

	/**
	 * 
	 * @return
	 */
	List<DocumentoIdentidad> obtenerTodosActivosEInactivosPorOrdenAsc();

	DocumentoIdentidad obtenerDocumentoIdentidad(Integer id);

	DocumentoIdentidad obtenerDocumentoIdentidad(String nombre);

	List<DocumentoIdentidad> obtenerDocumentoIdentidadOrdenAscPorId();

	List<DocumentoIdentidad> obtenerDocumentoIdentidadOrdenDescPorId();

	List<DocumentoIdentidad> obtenerPorCompania(Compania compania);

	List<DocumentoIdentidad> obtenerPorSucursal(Sucursal sucursal);

	List<DocumentoIdentidad> obtenerAllActivos();

	List<DocumentoIdentidad> onComplete(String query);

	boolean verificarRepetido(DocumentoIdentidad documentoIdentidad,
			Compania compania);

	boolean verificarRepetidoUpdate(DocumentoIdentidad documentoIdentidad,
			Compania compania);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<DocumentoIdentidad> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<DocumentoIdentidad> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal);

}
