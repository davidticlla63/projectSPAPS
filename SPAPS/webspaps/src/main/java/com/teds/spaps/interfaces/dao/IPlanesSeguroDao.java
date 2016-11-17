/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPlanesSeguroDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param planesSeguro
	 * @return
	 */
	PlanesSeguro create(PlanesSeguro planesSeguro);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	PlanesSeguro update(PlanesSeguro update);

	void delete(PlanesSeguro planesSeguro);

	/**
	 * registrar object
	 * 
	 * @param PlanesSeguro
	 * @return PlanesSeguro
	 */
	PlanesSeguro registrar(PlanesSeguro planesSeguro);

	/**
	 * modificar object
	 * 
	 * @param PlanesSeguro
	 * @return PlanesSeguro
	 */
	PlanesSeguro modificar(PlanesSeguro planesSeguro);

	/**
	 * eliminar object
	 * 
	 * @param planesSeguro
	 * @return
	 */
	boolean eliminar(PlanesSeguro planesSeguro);

	/**
	 * eliminar object real
	 * 
	 * @param planesSeguro
	 * @return
	 */
	boolean deletePS(PlanesSeguro planesSeguro);

	/**
	 * 
	 * @return
	 */
	List<PlanesSeguro> obtenerTodosActivosEInactivosPorOrdenAsc();

	PlanesSeguro obtenerPlanesSeguro(Integer id);

	PlanesSeguro obtenrPlanesSeguro(Integer id, Compania compania);

	PlanesSeguro obtenerPlanesSeguro(String codigo);

	PlanesSeguro obtenerPlanesSeguro(String codigo, Compania compania);

	PlanesSeguro obtenerPlanesSeguroPorDescripcion(String descripcion);

	PlanesSeguro obtenerPlanesSeguroPorDescripcion(String descripcion,
			Compania compania);

	List<PlanesSeguro> obtenerPlanesSeguroOrdenAscPorId();

	List<PlanesSeguro> obtenerPlanesSeguroOrdenDescPorId();

	List<PlanesSeguro> obtenerPorCompania(Compania compania);

	List<PlanesSeguro> obtenerPorSucursal(Sucursal sucursal);

	List<PlanesSeguro> obtenerAllActivos();

	List<PlanesSeguro> obtenerPorIndividuo(Paciente paciente);
}
