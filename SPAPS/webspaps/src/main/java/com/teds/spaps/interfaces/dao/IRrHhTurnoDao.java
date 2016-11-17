/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.RrHhTurno;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IRrHhTurnoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param cargo
	 * @return
	 */
	RrHhTurno create(RrHhTurno cargo);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	RrHhTurno update(RrHhTurno update);

	/**
	 * registrar object
	 * 
	 * @param RrHhTurno
	 * @return RrHhTurnoSemana
	 */
	RrHhTurno registrar(RrHhTurno cargo);

	/**
	 * modificar object
	 * 
	 * @param RrHhTurno
	 * @return RrHhTurnoSemana
	 */
	RrHhTurno modificar(RrHhTurno cargo);

	/**
	 * eliminar object
	 * 
	 * @param cargo
	 * @return
	 */
	boolean eliminar(RrHhTurno cargo);

	/**
	 * 
	 * @return
	 */
	List<RrHhTurno> obtenerTodosActivosEInactivosPorOrdenAsc();

	RrHhTurno obtenerRrHhTurnoSemana(Integer id);

	List<RrHhTurno> obtenerRrHhTurnoSemanaOrdenAscPorId();

	List<RrHhTurno> obtenerRrHhTurnoSemanaOrdenDescPorId();

	List<RrHhTurno> obtenerPorCompania(Compania compania);

	RrHhTurno obtenerRrHhTurnoSemana(String descripcion);

	List<RrHhTurno> obtenerPorEmpresa(Empresa empresa);

	List<RrHhTurno> obtenerPorEmpresa(String nombre, Empresa empresa);
	
	List<RrHhTurno> obtenerPorSucursal(Sucursal sucursal);

	List<RrHhTurno> onComplete(String query);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<RrHhTurno> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<RrHhTurno> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
