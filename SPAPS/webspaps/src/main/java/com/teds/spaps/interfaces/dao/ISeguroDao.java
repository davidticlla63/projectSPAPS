/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ISeguroDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param seguro
	 * @return
	 */
	Seguro create(Seguro seguro);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Seguro update(Seguro update);

	/**
	 * registrar object
	 * 
	 * @param Seguro
	 * @return Seguro
	 */
	Seguro registrar(Seguro seguro);

	/**
	 * modificar object
	 * 
	 * @param Seguro
	 * @return Seguro
	 */
	Seguro modificar(Seguro seguro);

	/**
	 * eliminar object
	 * 
	 * @param seguro
	 * @return
	 */
	boolean eliminar(Seguro seguro);

	/**
	 * 
	 * @return
	 */
	List<Seguro> obtenerTodosActivosEInactivosPorOrdenAsc();

	Seguro obtenerSeguro(Integer id);

	Seguro obtenerSeguro(Integer id, Compania compania);

	Seguro obtenerSeguro(String codigo);

	Seguro obtenerSeguro(String codigo, Compania compania);

	Seguro obtenerSeguroPorNombre(String nombre);

	Seguro obtenerSeguroPorDescripcion(String descripcion, Compania compania);

	List<Seguro> obtenerSeguroOrdenAscPorId();

	List<Seguro> obtenerSeguroOrdenDescPorId();

	List<Seguro> obtenerPorSucursal(Sucursal sucursal);

	List<Seguro> obtenerAllActivos();

	List<Seguro> obtenerPorCompania(Compania compania);

	List<Seguro> obtenerPorCompania(String nombre, Compania compania);
	Seguro DevolverSeguroPorId(Integer id);
	List<Seguro> devolverSeguroOnCompleteCompania(Compania compania, String nombre);
}
