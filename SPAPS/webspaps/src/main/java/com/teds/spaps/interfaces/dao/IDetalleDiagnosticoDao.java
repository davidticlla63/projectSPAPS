/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDetalleDiagnosticoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleDiagnostico
	 * @return
	 */
	DetalleDiagnostico create(DetalleDiagnostico detalleDiagnostico);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleDiagnostico update(DetalleDiagnostico update);

	void delete(DetalleDiagnostico detalleDiagnostico);

	/**
	 * registrar object
	 * 
	 * @param DetalleDiagnostico
	 * @return DetalleDiagnostico
	 */
	DetalleDiagnostico registrar(DetalleDiagnostico detalleDiagnostico);

	boolean registrar(List<DetalleDiagnostico> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param DetalleDiagnostico
	 * @return DetalleDiagnostico
	 */
	DetalleDiagnostico modificar(DetalleDiagnostico detalleDiagnostico);

	/**
	 * eliminar object
	 * 
	 * @param detalleDiagnostico
	 * @return
	 */
	boolean eliminar(DetalleDiagnostico detalleDiagnostico);

	boolean verificarRepetido(HistoriaClinica historiaClinica,
			DetalleDiagnostico detalleDiagnostico);

	/**
	 * 
	 * @return
	 */
	List<DetalleDiagnostico> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleDiagnostico obtenerDetalleDiagnostico(Integer id);

	List<DetalleDiagnostico> obtenerDetalleDiagnosticoOrdenAscPorId();

	List<DetalleDiagnostico> obtenerDetalleDiagnosticoOrdenDescPorId();

	List<DetalleDiagnostico> obtenerPorCompania(Compania compania);

	List<DetalleDiagnostico> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleDiagnostico> obtenerPorHM(HistoriaClinica historiaClinica);

	List<DetalleDiagnostico> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

	List<DetalleDiagnostico> obtenerPorDiagnostico(Diagnostico diagnostico);

	List<DetalleDiagnostico> onComplete(String query);

}
