/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleDiagnosticoEspecialidad;
import com.teds.spaps.model.DetalleTipoDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDiagnosticoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param diagnostico
	 * @return
	 */
	Diagnostico create(Diagnostico diagnostico);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Diagnostico update(Diagnostico update);

	/**
	 * registrar object
	 * 
	 * @param listaDetalleTipoDiagnostico
	 * 
	 * @param Diagnostico
	 * @return Diagnostico
	 */
	Diagnostico registrar(
			Diagnostico diagnostico,
			List<DetalleTipoDiagnostico> listaDetalleTipoDiagnostico,
			List<DetalleDiagnosticoEspecialidad> listaDetalleDiagnosticoEspecialidad);

	/**
	 * modificar object
	 * 
	 * @param Diagnostico
	 * @return Diagnostico
	 */
	Diagnostico modificar(Diagnostico diagnostico,
			List<DetalleTipoDiagnostico> sacar,
			List<DetalleTipoDiagnostico> meter,
			List<DetalleDiagnosticoEspecialidad> diagnosticoSacar,
			List<DetalleDiagnosticoEspecialidad> diagnosticoMeter);

	/**
	 * eliminar object
	 * 
	 * @param diagnostico
	 * @return
	 */
	boolean eliminar(Diagnostico diagnostico,
			List<DetalleTipoDiagnostico> detalleTipoDiagnosticos,
			List<DetalleDiagnosticoEspecialidad> detalleDiagnosticoEspecialidads);

	boolean verificarRepetido(Diagnostico diagnostico, Compania compania);

	boolean verificarRepetidoUpdate(Diagnostico diagnostico, Compania compania);

	/**
	 * 
	 * @return
	 */
	List<Diagnostico> obtenerTodosActivosEInactivosPorOrdenAsc();

	Diagnostico obtenerDiagnostico(Integer id);

	Diagnostico obtenerDiagnostico(String descripcion);

	List<Diagnostico> obtenerDiagnosticoOrdenAscPorId();

	List<Diagnostico> obtenerDiagnosticoOrdenDescPorId();

	List<Diagnostico> obtenerPorCompania(Compania compania);

	List<Diagnostico> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	List<Diagnostico> obtenerPorSucursal(Sucursal sucursal);

	List<Diagnostico> onComplete(String query);

	/**
	 * @param diagnostico
	 * @return
	 */
	Diagnostico registrar(Diagnostico diagnostico);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<Diagnostico> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

}
