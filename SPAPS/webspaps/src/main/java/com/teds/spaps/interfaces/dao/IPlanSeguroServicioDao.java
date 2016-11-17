/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.PlanSeguroServicio;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPlanSeguroServicioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param planSeguroServicio
	 * @return
	 */
	PlanSeguroServicio create(PlanSeguroServicio planSeguroServicio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	PlanSeguroServicio update(PlanSeguroServicio update);

	void delete(PlanSeguroServicio planSeguroServicio);

	/**
	 * registrar object
	 * 
	 * @param PlanSeguroServicio
	 * @return DetalleCobertura
	 */
	PlanSeguroServicio registrar(PlanSeguroServicio planSeguroServicio);

	boolean registrar(List<PlanSeguroServicio> coberturas);

	/**
	 * modificar object
	 * 
	 * @param PlanSeguroServicio
	 * @return DetalleCobertura
	 */
	PlanSeguroServicio modificar(PlanSeguroServicio planSeguroServicio);

	/**
	 * eliminar object
	 * 
	 * @param planSeguroServicio
	 * @return
	 */
	boolean eliminar(PlanSeguroServicio planSeguroServicio);

	/**
	 * 
	 * @return
	 */
	List<PlanSeguroServicio> obtenerTodosActivosEInactivosPorOrdenAsc();

	PlanSeguroServicio obtenerDetalleCobertura(Integer id);

	List<PlanSeguroServicio> obtenerDetalleCoberturaOrdenAscPorId();

	List<PlanSeguroServicio> obtenerDetalleCoberturaOrdenDescPorId();

	List<PlanSeguroServicio> obtenerPorCompania(Compania compania);

	List<PlanSeguroServicio> obtenerPorSucursal(Sucursal sucursal);

	List<PlanSeguroServicio> obtenerPorPlanSeguro(PlanSeguro planSeguro);

	List<PlanSeguroServicio> obtenerPorCobertura(Servicio servicio);

	List<PlanSeguroServicio> onComplete(String query);

}
