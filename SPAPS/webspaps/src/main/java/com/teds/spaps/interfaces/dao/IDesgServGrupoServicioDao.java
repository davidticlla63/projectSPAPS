/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgServGrupo;
import com.teds.spaps.model.DesgServGrupoServicio;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgServGrupoServicioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgServGrupoServicio
	 * @return
	 */
	DesgServGrupoServicio create(DesgServGrupoServicio desgServGrupoServicio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgServGrupoServicio update(DesgServGrupoServicio desgServGrupoServicio);

	void delete(DesgServGrupoServicio desgServGrupoServicio);

	/**
	 * registrar object
	 * 
	 * @param DesgServGrupoServicio
	 * @return DesgServGrupoServicio
	 */
	DesgServGrupoServicio registrar(DesgServGrupoServicio desgServGrupoServicio);

	boolean registrar(List<DesgServGrupoServicio> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DesgServGrupoServicio
	 * @return DesgServGrupoServicio
	 */
	DesgServGrupoServicio modificar(DesgServGrupoServicio desgServGrupoServicio);

	/**
	 * eliminar object
	 * 
	 * @param desgServGrupoServicio
	 * @return
	 */
	boolean eliminar(DesgServGrupoServicio desgServGrupoServicio);

	/**
	 * 
	 * @return
	 */
	List<DesgServGrupoServicio> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgServGrupoServicio obtenerDesgServGrupoServicio(Integer id);

	List<DesgServGrupoServicio> obtenerDesgServGrupoServicioOrdenAscPorId();

	List<DesgServGrupoServicio> obtenerDesgServGrupoServicioOrdenDescPorId();

	List<DesgServGrupoServicio> obtenerPorCompania(Compania compania);

	List<DesgServGrupoServicio> obtenerPorSucursal(Sucursal sucursal);

	List<DesgServGrupoServicio> obtenerPorServicio(Servicio servicio);

	List<DesgServGrupoServicio> obtenerPorGrupo(DesgServGrupo grupo);

	List<DesgServGrupoServicio> onComplete(String query);

	/**
	 * @param examen
	 * @param sucursal
	 * @return
	 */
	boolean verificarServicioRegistrado(Servicio servicio, Sucursal sucursal);

	/**
	 * @param grupo
	 * @return
	 */
	List<Servicio> obtenerServiciosPorGrupo(DesgServGrupo grupo);

}
