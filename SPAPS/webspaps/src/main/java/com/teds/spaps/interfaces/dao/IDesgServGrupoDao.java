/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgServGrupo;
import com.teds.spaps.model.DesgServGrupoServicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.structore.EDDesgOrdenServicio;

/**
 * @author ANITA
 *
 */
public interface IDesgServGrupoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	DesgServGrupo create(DesgServGrupo desgServGrupo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgServGrupo update(DesgServGrupo desgServGrupo);

	/**
	 * registrar object
	 * 
	 * @param DesgServGrupo
	 * @return DesgServGrupo
	 */
	DesgServGrupo registrar(DesgServGrupo desgServGrupo);

	/**
	 * modificar object
	 * 
	 * @param DesgServGrupo
	 * @return DesgServGrupo
	 */
	DesgServGrupo modificar(DesgServGrupo desgServGrupo);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(DesgServGrupo desgServGrupo);

	/**
	 * 
	 * @return
	 */
	List<DesgServGrupo> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgServGrupo obtenerDesgServGrupo(Integer id);

	DesgServGrupo obtenerDesgServGrupo(String descripcion);

	List<DesgServGrupo> obtenerDesgServGrupoOrdenAscPorId();

	List<DesgServGrupo> obtenerDesgServGrupoOrdenDescPorId();

	List<DesgServGrupo> obtenerPorCompania(Compania compania);

	List<DesgServGrupo> obtenerPorCompania(String descripcion, Compania compania);

	List<DesgServGrupo> obtenerPorSucursal(Sucursal sucursal);

	List<DesgServGrupo> obtenerAllActivos();

	List<DesgServGrupo> obtenerAllGrupoExamen(Compania compania);

	/**
	 * @param desgServGrupo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetido(DesgServGrupo desgServGrupo, Compania compania);

	/**
	 * @param desgServGrupo
	 * @param compania
	 * @return
	 */
	boolean verificarRepetidoUpdate(DesgServGrupo desgServGrupo,
			Compania compania);

	/**
	 * @param detalleAntecedente
	 */
	void delete(DesgServGrupo grupo);

	/**
	 * @param desgServGrupo
	 * @param examenes
	 * @return
	 */
	boolean eliminar(DesgServGrupo desgServGrupo,
			List<DesgServGrupoServicio> examenes);

	/**
	 * @param desgServGrupo
	 * @param examenesSacar
	 * @param examenesMeter
	 * @return
	 */
	DesgServGrupo modificar(DesgServGrupo desgServGrupo,
			List<DesgServGrupoServicio> examenesSacar,
			List<DesgServGrupoServicio> examenesMeter);

	/**
	 * @param desgServGrupo
	 * @param examenes
	 * @return
	 */
	DesgServGrupo registrar(DesgServGrupo desgServGrupo,
			List<DesgServGrupoServicio> examenes);

	/**
	 * @param sucursal
	 * @return
	 */
	List<EDDesgOrdenServicio> obtenerEDGrupos(Sucursal sucursal);

}
