/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgOrdenServicioLabExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgOrdenServicioLabExamen
	 * @return
	 */
	DesgOrdenServicioLabExamen create(
			DesgOrdenServicioLabExamen desgOrdenServicioLabExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgOrdenServicioLabExamen update(
			DesgOrdenServicioLabExamen desgOrdenServicioLabExamen);

	void delete(DesgOrdenServicioLabExamen desgOrdenServicioLabExamen);

	/**
	 * registrar object
	 * 
	 * @param DesgOrdenServicioLabExamen
	 * @return DesgOrdenServicioLabExamen
	 */
	DesgOrdenServicioLabExamen registrar(
			DesgOrdenServicioLabExamen desgOrdenServicioLabExamen);

	boolean registrar(List<DesgOrdenServicioLabExamen> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DesgOrdenServicioLabExamen
	 * @return DesgOrdenServicioLabExamen
	 */
	DesgOrdenServicioLabExamen modificar(
			DesgOrdenServicioLabExamen desgOrdenServicioLabExamen);

	/**
	 * eliminar object
	 * 
	 * @param desgOrdenServicioLabExamen
	 * @return
	 */
	boolean eliminar(DesgOrdenServicioLabExamen desgOrdenServicioLabExamen);

	/**
	 * 
	 * @return
	 */
	List<DesgOrdenServicioLabExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgOrdenServicioLabExamen obtenerDesgOrdenServicioLabExamen(Integer id);

	List<DesgOrdenServicioLabExamen> obtenerDesgOrdenServicioLabExamenOrdenAscPorId();

	List<DesgOrdenServicioLabExamen> obtenerDesgOrdenServicioLabExamenOrdenDescPorId();

	List<DesgOrdenServicioLabExamen> obtenerPorCompania(Compania compania);

	List<DesgOrdenServicioLabExamen> obtenerPorSucursal(Sucursal sucursal);

	List<DesgOrdenServicioLabExamen> obtenerPorOrden(DesgOrdenServicio orden);

	List<DesgOrdenServicioLabExamen> obtenerPorExamen(LabExamen examen);

	List<DesgOrdenServicioLabExamen> onComplete(String query);

	/**
	 * @param examen
	 * @param sucursal
	 * @return
	 */
	boolean verificarExamenRegistrado(LabExamen examen, Sucursal sucursal);

	/**
	 * @param orden
	 * @return
	 */
	List<DesgOrdenServicioLabExamen> obtenerPorOrdenHC(DesgOrdenServicio orden);

}
