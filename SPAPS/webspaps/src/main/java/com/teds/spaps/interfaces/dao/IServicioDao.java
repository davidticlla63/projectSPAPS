/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IServicioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param servicio
	 * @return
	 */
	Servicio create(Servicio servicio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Servicio update(Servicio update);

	/**
	 * registrar object
	 * 
	 * @param Servicio
	 * @return Cobertura
	 */
	Servicio registrar(Servicio servicio);

	/**
	 * modificar object
	 * 
	 * @param Servicio
	 * @return Cobertura
	 */
	Servicio modificar(Servicio servicio);

	/**
	 * eliminar object
	 * 
	 * @param servicio
	 * @return
	 */
	boolean eliminar(Servicio servicio);

	/**
	 * 
	 * @return
	 */
	List<Servicio> obtenerTodosActivosEInactivosPorOrdenAsc();

	Servicio obtenerCobertura(Integer id);

	List<Servicio> obtenerCoberturaOrdenAscPorId();

	List<Servicio> obtenerCoberturaOrdenDescPorId();

	List<Servicio> obtenerPorCompania(Compania compania);

	List<Servicio> obtenerPorSucursal(Sucursal sucursal);

	List<Servicio> obtenerPorPlanSeguro(PlanSeguro planSeguro);

	List<Servicio> obtenerPorCompania(String nombre, Compania compania);

	boolean verificarRepetido(Servicio servicio, Compania compania);

	boolean verificarRepetidoUpdate(Servicio servicio, Compania compania);

	List<Servicio> onComplete(String query);

}
