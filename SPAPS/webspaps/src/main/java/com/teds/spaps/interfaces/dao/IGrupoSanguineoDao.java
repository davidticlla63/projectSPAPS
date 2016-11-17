package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.GrupoSanguineo;
import com.teds.spaps.model.Sucursal;

/*
 * @author ANITA
 */
public interface IGrupoSanguineoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param grupoSanguineo
	 * @return
	 */
	GrupoSanguineo create(GrupoSanguineo grupoSanguineo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	GrupoSanguineo update(GrupoSanguineo update);

	/**
	 * registrar object
	 * 
	 * @param GrupoSanguineo
	 * @return GrupoSanguineo
	 */
	GrupoSanguineo registrar(GrupoSanguineo grupoSanguineo);

	/**
	 * modificar object
	 * 
	 * @param GrupoSanguineo
	 * @return GrupoSanguineo
	 */
	GrupoSanguineo modificar(GrupoSanguineo grupoSanguineo);

	/**
	 * eliminar object
	 * 
	 * @param grupoSanguineo
	 * @return
	 */
	boolean eliminar(GrupoSanguineo grupoSanguineo);

	/**
	 * 
	 * @return
	 */
	List<GrupoSanguineo> obtenerTodosActivosEInactivosPorOrdenAsc();

	GrupoSanguineo obtenerGrupoSanguineo(Integer id);

	GrupoSanguineo obtenerPorDescripcion(String descripcion);

	List<GrupoSanguineo> obtenerGrupoSanguineoOrdenAscPorId();

	List<GrupoSanguineo> obtenerGrupoSanguineoOrdenDescPorId();

	List<GrupoSanguineo> obtenerPorCompania(Compania compania);

	List<GrupoSanguineo> obtenerAllActivos();

	List<GrupoSanguineo> obtenerPorSucursal(Sucursal sucursal);

	List<GrupoSanguineo> onComplete(String query);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<GrupoSanguineo> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<GrupoSanguineo> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
