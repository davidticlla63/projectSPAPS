/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleDiagnosticoEspecialidad;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleDiagnosticoEspecialidadDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleDiagnosticoEspecialidad
	 * @return
	 */
	DetalleDiagnosticoEspecialidad create(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleDiagnosticoEspecialidad update(DetalleDiagnosticoEspecialidad update);

	void delete(DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad);

	/**
	 * registrar object
	 * 
	 * @param DetalleDiagnosticoEspecialidad
	 * @return DetalleDiagnosticoEspecialidad
	 */
	DetalleDiagnosticoEspecialidad registrar(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad);

	/**
	 * modificar object
	 * 
	 * @param DetalleDiagnosticoEspecialidad
	 * @return DetalleDiagnosticoEspecialidad
	 */
	DetalleDiagnosticoEspecialidad modificar(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad);

	/**
	 * eliminar object
	 * 
	 * @param detalleDiagnosticoEspecialidad
	 * @return
	 */
	boolean eliminar(
			DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad);

	/**
	 * 
	 * @return
	 */
	List<DetalleDiagnosticoEspecialidad> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleDiagnosticoEspecialidad obtenerDetalleDiagnosticoEspecialidad(
			Integer id);

	List<DetalleDiagnosticoEspecialidad> obtenerDetalleDiagnosticoEspecialidadOrdenAscPorId();

	List<DetalleDiagnosticoEspecialidad> obtenerDetalleDiagnosticoEspecialidadOrdenDescPorId();

	List<DetalleDiagnosticoEspecialidad> obtenerPorCompania(Compania compania);

	List<DetalleDiagnosticoEspecialidad> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleDiagnosticoEspecialidad> obtenerPorHM(
			HistoriaClinica historiaClinica);

	List<DetalleDiagnosticoEspecialidad> obtenerPorDiagnostico(
			Diagnostico diagnostico);

	List<DetalleDiagnosticoEspecialidad> obtenerPorEspecialidad(
			Especialidad especialidad);

	List<DetalleDiagnosticoEspecialidad> onComplete(String query);

}
