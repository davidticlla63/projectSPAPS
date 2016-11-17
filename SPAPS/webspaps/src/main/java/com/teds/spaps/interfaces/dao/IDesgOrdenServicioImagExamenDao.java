/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgOrdenServicioImagExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgOrdenServicioImagExamen
	 * @return
	 */
	DesgOrdenServicioImagExamen create(
			DesgOrdenServicioImagExamen desgOrdenServicioImagExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgOrdenServicioImagExamen update(
			DesgOrdenServicioImagExamen desgOrdenServicioImagExamen);

	void delete(DesgOrdenServicioImagExamen desgOrdenServicioImagExamen);

	/**
	 * registrar object
	 * 
	 * @param DesgOrdenServicioImagExamen
	 * @return DesgOrdenServicioImagExamen
	 */
	DesgOrdenServicioImagExamen registrar(
			DesgOrdenServicioImagExamen desgOrdenServicioImagExamen);

	boolean registrar(List<DesgOrdenServicioImagExamen> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DesgOrdenServicioImagExamen
	 * @return DesgOrdenServicioImagExamen
	 */
	DesgOrdenServicioImagExamen modificar(
			DesgOrdenServicioImagExamen desgOrdenServicioImagExamen);

	/**
	 * eliminar object
	 * 
	 * @param desgOrdenServicioImagExamen
	 * @return
	 */
	boolean eliminar(DesgOrdenServicioImagExamen desgOrdenServicioImagExamen);

	/**
	 * 
	 * @return
	 */
	List<DesgOrdenServicioImagExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgOrdenServicioImagExamen obtenerDesgOrdenServicioImagExamen(Integer id);

	List<DesgOrdenServicioImagExamen> obtenerDesgOrdenServicioImagExamenOrdenAscPorId();

	List<DesgOrdenServicioImagExamen> obtenerDesgOrdenServicioImagExamenOrdenDescPorId();

	List<DesgOrdenServicioImagExamen> obtenerPorCompania(Compania compania);

	List<DesgOrdenServicioImagExamen> obtenerPorSucursal(Sucursal sucursal);

	List<DesgOrdenServicioImagExamen> obtenerPorOrden(DesgOrdenServicio orden);

	List<DesgOrdenServicioImagExamen> obtenerPorExamen(LabExamenImag examen);

	List<DesgOrdenServicioImagExamen> onComplete(String query);

	/**
	 * @param examen
	 * @param sucursal
	 * @return
	 */
	boolean verificarExamenRegistrado(LabExamenImag examen, Sucursal sucursal);

	/**
	 * @param orden
	 * @return
	 */
	List<DesgOrdenServicioImagExamen> obtenerPorOrdenHC(DesgOrdenServicio orden);

}
