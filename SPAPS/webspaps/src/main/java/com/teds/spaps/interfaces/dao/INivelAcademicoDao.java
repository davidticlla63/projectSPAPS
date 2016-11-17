/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.NivelAcademico;

/**
 * @author ANITA
 *
 */
public interface INivelAcademicoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param nivelAcademico
	 * @return
	 */
	NivelAcademico create(NivelAcademico nivelAcademico);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	NivelAcademico update(NivelAcademico update);

	/**
	 * registrar object
	 * 
	 * @param NivelAcademico
	 * @return NivelAcademico
	 */
	NivelAcademico registrar(NivelAcademico nivelAcademico);

	/**
	 * modificar object
	 * 
	 * @param NivelAcademico
	 * @return NivelAcademico
	 */
	NivelAcademico modificar(NivelAcademico nivelAcademico);

	/**
	 * eliminar object
	 * 
	 * @param nivelAcademico
	 * @return
	 */
	boolean eliminar(NivelAcademico nivelAcademico);

	/**
	 * 
	 * @return
	 */
	List<NivelAcademico> obtenerTodosActivosEInactivosPorOrdenAsc();

	NivelAcademico obtenerNivelAcademico(Integer id);

	List<NivelAcademico> obtenerNivelAcademicoOrdenAscPorId();

	List<NivelAcademico> obtenerNivelAcademicoOrdenDescPorId();

	List<NivelAcademico> obtenerPorCompania(Compania compania);

}
