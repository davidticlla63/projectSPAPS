/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgLabGrupoExamenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgLabGrupoExamen
	 * @return
	 */
	DesgLabGrupoExamen create(DesgLabGrupoExamen desgLabGrupoExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgLabGrupoExamen update(DesgLabGrupoExamen desgLabGrupoExamen);

	void delete(DesgLabGrupoExamen desgLabGrupoExamen);

	/**
	 * registrar object
	 * 
	 * @param DesgLabGrupoExamen
	 * @return DesgLabGrupoExamen
	 */
	DesgLabGrupoExamen registrar(DesgLabGrupoExamen desgLabGrupoExamen);

	boolean registrar(List<DesgLabGrupoExamen> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DesgLabGrupoExamen
	 * @return DesgLabGrupoExamen
	 */
	DesgLabGrupoExamen modificar(DesgLabGrupoExamen desgLabGrupoExamen);

	/**
	 * eliminar object
	 * 
	 * @param desgLabGrupoExamen
	 * @return
	 */
	boolean eliminar(DesgLabGrupoExamen desgLabGrupoExamen);

	/**
	 * 
	 * @return
	 */
	List<DesgLabGrupoExamen> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgLabGrupoExamen obtenerDesgLabGrupoExamen(Integer id);

	List<DesgLabGrupoExamen> obtenerDesgLabGrupoExamenOrdenAscPorId();

	List<DesgLabGrupoExamen> obtenerDesgLabGrupoExamenOrdenDescPorId();

	List<DesgLabGrupoExamen> obtenerPorCompania(Compania compania);

	List<DesgLabGrupoExamen> obtenerPorSucursal(Sucursal sucursal);

	List<DesgLabGrupoExamen> obtenerPorExamen(LabExamen examen);

	List<DesgLabGrupoExamen> obtenerPorGrupo(DesgLabGrupo grupo);

	List<DesgLabGrupoExamen> onComplete(String query);

	/**
	 * @param examen
	 * @param sucursal
	 * @return
	 */
	boolean verificarExamenRegistrado(LabExamen examen, Sucursal sucursal);

	/**
	 * @param grupo
	 * @return
	 */
	List<LabExamen> obtenerExamenesPorGrupo(DesgLabGrupo grupo);

}
