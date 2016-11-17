/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.TipoDiagnostico;

/**
 * @author ANITA
 *
 */
public interface IDetalleTipoDiagnosticoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param detalleTipoDiagnostico
	 * @return
	 */
	DetalleTipoDiagnostico create(DetalleTipoDiagnostico detalleTipoDiagnostico);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DetalleTipoDiagnostico update(DetalleTipoDiagnostico update);

	void delete(DetalleTipoDiagnostico detalleTipoDiagnostico);

	/**
	 * registrar object
	 * 
	 * @param DetalleTipoDiagnostico
	 * @return DetalleTipoDiagnostico
	 */
	DetalleTipoDiagnostico registrar(DetalleTipoDiagnostico detalleTipoDiagnostico);

	/**
	 * modificar object
	 * 
	 * @param DetalleTipoDiagnostico
	 * @return DetalleTipoDiagnostico
	 */
	DetalleTipoDiagnostico modificar(DetalleTipoDiagnostico detalleTipoDiagnostico);

	/**
	 * eliminar object
	 * 
	 * @param detalleTipoDiagnostico
	 * @return
	 */
	boolean eliminar(DetalleTipoDiagnostico detalleTipoDiagnostico);

	/**
	 * 
	 * @return
	 */
	List<DetalleTipoDiagnostico> obtenerTodosActivosEInactivosPorOrdenAsc();

	DetalleTipoDiagnostico obtenerDetalleTipoDiagnostico(Integer id);

	List<DetalleTipoDiagnostico> obtenerDetalleTipoDiagnosticoOrdenAscPorId();

	List<DetalleTipoDiagnostico> obtenerDetalleTipoDiagnosticoOrdenDescPorId();

	List<DetalleTipoDiagnostico> obtenerPorCompania(Compania compania);

	List<DetalleTipoDiagnostico> obtenerPorSucursal(Sucursal sucursal);

	List<DetalleTipoDiagnostico> obtenerPorDiagnostico(Diagnostico diagnostico);

	List<DetalleTipoDiagnostico> obtenerPorTipoDiagnostico(TipoDiagnostico tipoDiagnostico);

	List<DetalleTipoDiagnostico> onComplete(String query);

}
