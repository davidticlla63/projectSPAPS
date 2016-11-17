/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.RrHhDiaTurno;

/**
 * @author david
 *
 */
public interface IRrHhDiaTurnoDao {
	
	boolean deleteDetail(Personal labExamen);
	
	boolean deleteDetail(PersonalEspecialidad personalEspecialidad) ;
	
	void delete(RrHhDiaTurno labExamen);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param especialidad
	 * @return
	 */
	RrHhDiaTurno create(RrHhDiaTurno especialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	RrHhDiaTurno update(RrHhDiaTurno update);

	/**
	 * registrar object
	 * 
	 * @param RrHhDiaTurno
	 * @return RrHhDiaTurno
	 */
	RrHhDiaTurno registrar(RrHhDiaTurno especialidad);

	/**
	 * modificar object
	 * 
	 * @param RrHhDiaTurno
	 * @return RrHhDiaTurno
	 */
	RrHhDiaTurno modificar(RrHhDiaTurno especialidad);

	/**
	 * eliminar object
	 * 
	 * @param especialidad
	 * @return
	 */
	boolean eliminar(RrHhDiaTurno especialidad);


	RrHhDiaTurno obtenerRrHhDiaTurno(Integer id);

}
