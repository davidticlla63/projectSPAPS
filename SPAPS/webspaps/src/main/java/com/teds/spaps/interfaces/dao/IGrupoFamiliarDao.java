/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.GrupoFamiliar;
import com.teds.spaps.model.Sucursal;

public interface IGrupoFamiliarDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param grupoFamiliar
	 * @return
	 */
	GrupoFamiliar create(GrupoFamiliar grupoFamiliar);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	GrupoFamiliar update(GrupoFamiliar update);

	/**
	 * registrar object
	 * 
	 * @param GrupoFamiliar
	 * @return GrupoFamiliar
	 */
	GrupoFamiliar registrar(GrupoFamiliar grupoFamiliar);

	/**
	 * modificar object
	 * 
	 * @param GrupoFamiliar
	 * @return GrupoFamiliar
	 */
	GrupoFamiliar modificar(GrupoFamiliar grupoFamiliar);

	/**
	 * eliminar object
	 * 
	 * @param grupoFamiliar
	 * @return
	 */
	boolean eliminar(GrupoFamiliar grupoFamiliar);

	/**
	 * 
	 * @return
	 */
	List<GrupoFamiliar> obtenerTodosActivosEInactivosPorOrdenAsc();

	GrupoFamiliar obtenerGrupoFamiliar1(Integer id);

	GrupoFamiliar obtenerGrupoFamiliar1(String descripcion);

	List<GrupoFamiliar> obtenerGrupoFamiliar1OrdenAscPorId();

	List<GrupoFamiliar> obtenerGrupoFamiliar1OrdenDescPorId();

	List<GrupoFamiliar> obtenerPorCompania(Compania compania);

	List<GrupoFamiliar> obtenerPorSucursal(Sucursal sucursal);

	List<GrupoFamiliar> obtenerAllActivos();

	boolean verificarRepetido(GrupoFamiliar grupoFamiliar, Compania compania);

	boolean verificarRepetidoUpdate(GrupoFamiliar grupoFamiliar,
			Compania compania);

	List<GrupoFamiliar> onComplete(String query);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<GrupoFamiliar> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<GrupoFamiliar> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal);
}
