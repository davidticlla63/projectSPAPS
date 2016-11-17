/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDDesgOrdenImagExamen;

/**
 * @author ANITA
 *
 */
public interface IDesgImagGrupoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	DesgImagGrupo create(DesgImagGrupo desgImagGrupo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgImagGrupo update(DesgImagGrupo desgImagGrupo);

	/**
	 * registrar object
	 * 
	 * @param DesgImagGrupo
	 * @return DesgImagGrupo
	 */
	DesgImagGrupo registrar(DesgImagGrupo desgImagGrupo);

	/**
	 * modificar object
	 * 
	 * @param DesgImagGrupo
	 * @return DesgImagGrupo
	 */
	DesgImagGrupo modificar(DesgImagGrupo desgImagGrupo);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(DesgImagGrupo desgImagGrupo);

	/**
	 * 
	 * @return
	 */
	List<DesgImagGrupo> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgImagGrupo obtenerDesgImagGrupo(Integer id);

	DesgImagGrupo obtenerDesgImagGrupo(String descripcion);

	List<DesgImagGrupo> obtenerDesgImagGrupoOrdenAscPorId();

	List<DesgImagGrupo> obtenerDesgImagGrupoOrdenDescPorId();

	List<DesgImagGrupo> obtenerPorCompania(Compania compania);

	List<DesgImagGrupo> obtenerPorCompania(String descripcion, Compania compania);

	List<DesgImagGrupo> obtenerPorSucursal(Sucursal sucursal);

	List<DesgImagGrupo> obtenerAllActivos();

	List<DesgImagGrupo> obtenerAllGrupoExamen(Compania compania);

	/**
	 * @param desgImagGrupo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetido(DesgImagGrupo desgImagGrupo, Compania compania);

	/**
	 * @param desgImagGrupo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetidoUpdate(DesgImagGrupo desgImagGrupo,
			Compania compania);

	/**
	 * @param desgImagGrupo
	 * @param examenes
	 * @return
	 */
	boolean eliminar(DesgImagGrupo desgImagGrupo,
			List<DesgImagGrupoExamen> examenes);

	/**
	 * @param desgImagGrupo
	 * @param examenesSacar
	 * @param examenesMeter
	 * @return
	 */
	DesgImagGrupo modificar(DesgImagGrupo desgImagGrupo,
			List<DesgImagGrupoExamen> examenesSacar,
			List<DesgImagGrupoExamen> examenesMeter);

	/**
	 * @param desgImagGrupo
	 * @param examenes
	 * @return
	 */
	DesgImagGrupo registrar(DesgImagGrupo desgImagGrupo,
			List<DesgImagGrupoExamen> examenes);

	/**
	 * @param sucursal
	 * @return
	 */
	List<EDDesgOrdenImagExamen> obtenerEDGrupos(Sucursal sucursal);

}
