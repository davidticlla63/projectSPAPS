/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.PlanSeguroServicio;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPlanSeguroDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param planSeguro
	 * @return
	 */
	PlanSeguro create(PlanSeguro planSeguro);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	PlanSeguro update(PlanSeguro update);

	/**
	 * registrar object
	 * 
	 * @param PlanSeguro
	 * @return PlanSeguro
	 */
	PlanSeguro registrar(PlanSeguro planSeguro,
			List<PlanSeguroServicio> coberturas);

	/**
	 * modificar object
	 * 
	 * @param PlanSeguro
	 * @return PlanSeguro
	 */
	PlanSeguro modificar(PlanSeguro planSeguro,
			List<PlanSeguroServicio> coberturasEliminar,
			List<PlanSeguroServicio> coberturasMeter);

	/**
	 * eliminar object
	 * 
	 * @param planSeguro
	 * @return
	 */
	boolean eliminar(PlanSeguro planSeguro, List<PlanSeguroServicio> coberturas);

	/**
	 * 
	 * @return
	 */
	List<PlanSeguro> obtenerTodosActivosEInactivosPorOrdenAsc();

	PlanSeguro obtenerPlanSeguro(Integer id);

	PlanSeguro obtenrPlanSeguro(Integer id, Compania compania);

	PlanSeguro obtenerPlanSeguro(String codigo);

	PlanSeguro obtenerPlanSeguro(String codigo, Compania compania);

	PlanSeguro obtenerPlanSeguroPorDescripcion(String descripcion);

	PlanSeguro obtenerPlanSeguroPorDescripcion(String descripcion,
			Compania compania);

	List<PlanSeguro> obtenerPlanSeguroOrdenAscPorId();

	List<PlanSeguro> obtenerPlanSeguroOrdenDescPorId();

	List<PlanSeguro> obtenerPorCompania(Compania compania);

	List<PlanSeguro> obtenerPorSucursal(Sucursal sucursal);

	List<PlanSeguro> obtenerAllActivos();

	List<PlanSeguro> onComplete(String query);

	PlanSeguro obtenerPlanSeguro(String descripcion, Seguro seguro);

	/**
	 * @param nombre
	 * @param compania
	 * @return
	 */
	List<PlanSeguro> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania);

	/**
	 * @param nombre
	 * @param sucursal
	 * @return
	 */
	List<PlanSeguro> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal);
}
