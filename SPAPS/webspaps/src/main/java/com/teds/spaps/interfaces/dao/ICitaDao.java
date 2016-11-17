/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Cita;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ICitaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param Cita
	 * @return
	 */
	Cita create(Cita Cita);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Cita update(Cita update);

	/**
	 * registrar object
	 * 
	 * @param Cita
	 * @return Cita
	 */
	Cita registrar(Cita cita);

	/**
	 * modificar object
	 * 
	 * @param Cita
	 * @return Cita
	 */
	Cita modificar(Cita cita);

	/**
	 * eliminar object
	 * 
	 * @param Cita
	 * @return
	 */
	boolean eliminar(Cita cita);

	/**
	 * 
	 * @return
	 */
	List<Cita> obtenerTodosActivosEInactivosPorOrdenAsc();

	Cita obtenerCita(Integer id);

	List<Cita> obtenerCitaOrdenAscPorId();

	List<Cita> obtenerCitaOrdenDescPorId();

	Cita obtenerCita(String descripcion);

	List<Cita> obtenerPorEmpresa(Empresa empresa);

	List<Cita> obtenerPorSucursal(Sucursal sucursal);

	List<Cita> obtenerPorCitasEntreDosFechas(String usuario, Sucursal sucursal,
			Date fechaini, Date fechafin);

	List<Cita> obtenerPorCitasEntreDosFechasPorMedicos(Personal personal,
			Sucursal sucursal, Date fechaini, Date fechafin);

}
