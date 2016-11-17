/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDDesgOrdenLabExamen;

/**
 * @author ANITA
 *
 */
public interface IDesgLabGrupoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	DesgLabGrupo create(DesgLabGrupo desgLabGrupo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgLabGrupo update(DesgLabGrupo desgLabGrupo);

	/**
	 * registrar object
	 * 
	 * @param DesgLabGrupo
	 * @return DesgLabGrupo
	 */
	DesgLabGrupo registrar(DesgLabGrupo desgLabGrupo);

	/**
	 * modificar object
	 * 
	 * @param DesgLabGrupo
	 * @return DesgLabGrupo
	 */
	DesgLabGrupo modificar(DesgLabGrupo desgLabGrupo);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(DesgLabGrupo desgLabGrupo);

	/**
	 * 
	 * @return
	 */
	List<DesgLabGrupo> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgLabGrupo obtenerDesgLabGrupo(Integer id);

	DesgLabGrupo obtenerDesgLabGrupo(String descripcion);

	List<DesgLabGrupo> obtenerDesgLabGrupoOrdenAscPorId();

	List<DesgLabGrupo> obtenerDesgLabGrupoOrdenDescPorId();

	List<DesgLabGrupo> obtenerPorCompania(Compania compania);

	List<DesgLabGrupo> obtenerPorCompania(String descripcion, Compania compania);

	List<DesgLabGrupo> obtenerPorSucursal(Sucursal sucursal);

	List<DesgLabGrupo> obtenerAllActivos();

	List<DesgLabGrupo> obtenerAllGrupoExamen(Compania compania);

	/**
	 * @param desgLabGrupo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetido(DesgLabGrupo desgLabGrupo, Compania compania);

	/**
	 * @param desgLabGrupo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetidoUpdate(DesgLabGrupo desgLabGrupo, Compania compania);

	/**
	 * @param detalleAntecedente
	 */
	void delete(DesgLabGrupo grupo);

	/**
	 * @param desgLabGrupo
	 * @param examenes
	 * @return
	 */
	boolean eliminar(DesgLabGrupo desgLabGrupo,
			List<DesgLabGrupoExamen> examenes);

	/**
	 * @param desgLabGrupo
	 * @param examenesSacar
	 * @param examenesMeter
	 * @return
	 */
	DesgLabGrupo modificar(DesgLabGrupo desgLabGrupo,
			List<DesgLabGrupoExamen> examenesSacar,
			List<DesgLabGrupoExamen> examenesMeter);

	/**
	 * @param desgLabGrupo
	 * @param examenes
	 * @return
	 */
	DesgLabGrupo registrar(DesgLabGrupo desgLabGrupo,
			List<DesgLabGrupoExamen> examenes);

	/**
	 * @param sucursal
	 * @return
	 */
	List<EDDesgOrdenLabExamen> obtenerEDGrupos(Sucursal sucursal);

}
