/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.EstadoCivil;
import com.teds.spaps.model.Sucursal;

public interface IEstadoCivilDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param estadoCivil
	 * @return
	 */
	EstadoCivil create(EstadoCivil estadoCivil);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	EstadoCivil update(EstadoCivil update);

	/**
	 * registrar object
	 * 
	 * @param EstadoCivil
	 * @return EstadoCivil
	 */
	EstadoCivil registrar(EstadoCivil estadoCivil);

	/**
	 * modificar object
	 * 
	 * @param EstadoCivil
	 * @return EstadoCivil
	 */
	EstadoCivil modificar(EstadoCivil estadoCivil);

	/**
	 * eliminar object
	 * 
	 * @param estadoCivil
	 * @return
	 */
	boolean eliminar(EstadoCivil estadoCivil);

	/**
	 * 
	 * @return
	 */
	List<EstadoCivil> obtenerTodosActivosEInactivosPorOrdenAsc();

	EstadoCivil obtenerEstadoCivil(Integer id);

	EstadoCivil obtenerEstadoCivil(String descripcion);

	List<EstadoCivil> obtenerEstadoCivilOrdenAscPorId();

	List<EstadoCivil> obtenerEstadoCivilOrdenDescPorId();

	List<EstadoCivil> obtenerPorCompania(Compania compania);

	List<EstadoCivil> obtenerPorSucursal(Sucursal sucursal);

	List<EstadoCivil> obtenerAllActivos();

	boolean verificarRepetido(EstadoCivil estadoCivil, Compania compania);

	boolean verificarRepetidoUpdate(EstadoCivil estadoCivil, Compania compania);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<EstadoCivil> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<EstadoCivil> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
