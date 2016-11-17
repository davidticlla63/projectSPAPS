/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgImagGrupoExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgImagGrupoExamen
	 * @return
	 */
	DesgImagGrupoExamen create(DesgImagGrupoExamen desgImagGrupoExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgImagGrupoExamen update(DesgImagGrupoExamen desgImagGrupoExamen);

	void delete(DesgImagGrupoExamen desgImagGrupoExamen);

	/**
	 * registrar object
	 * 
	 * @param DesgImagGrupoExamen
	 * @return DesgImagGrupoExamen
	 */
	DesgImagGrupoExamen registrar(DesgImagGrupoExamen desgImagGrupoExamen);

	boolean registrar(List<DesgImagGrupoExamen> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DesgImagGrupoExamen
	 * @return DesgImagGrupoExamen
	 */
	DesgImagGrupoExamen modificar(DesgImagGrupoExamen desgImagGrupoExamen);

	/**
	 * eliminar object
	 * 
	 * @param desgImagGrupoExamen
	 * @return
	 */
	boolean eliminar(DesgImagGrupoExamen desgImagGrupoExamen);

	/**
	 * 
	 * @return
	 */
	List<DesgImagGrupoExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgImagGrupoExamen obtenerDesgImagGrupoExamen(Integer id);

	List<DesgImagGrupoExamen> obtenerDesgImagGrupoExamenOrdenAscPorId();

	List<DesgImagGrupoExamen> obtenerDesgImagGrupoExamenOrdenDescPorId();

	List<DesgImagGrupoExamen> obtenerPorCompania(Compania compania);

	List<DesgImagGrupoExamen> obtenerPorSucursal(Sucursal sucursal);

	List<DesgImagGrupoExamen> obtenerPorExamen(LabExamenImag examen);

	List<DesgImagGrupoExamen> obtenerPorGrupo(DesgImagGrupo grupo);

	List<DesgImagGrupoExamen> onComplete(String query);

	/**
	 * @param examen
	 * @param sucursal
	 * @return
	 */
	boolean verificarExamenRegistrado(LabExamenImag examen, Sucursal sucursal);

	/**
	 * @param grupo
	 * @return
	 */
	List<LabExamenImag> obtenerExamenesPorGrupo(DesgImagGrupo grupo);

}
