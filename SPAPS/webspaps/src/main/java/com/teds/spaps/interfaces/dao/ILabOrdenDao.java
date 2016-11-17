/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabOrdenDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param labOrden
	 * @return
	 */
	LabOrden create(LabOrden labOrden);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabOrden update(LabOrden update);

	/**
	 * registrar object
	 * 
	 * @param LabOrden
	 * @return LabOrden
	 */
	LabOrden registrar(LabOrden labOrden,
			List<LabOrdenDetalle> listExamenDetalles);

	LabOrden modificar(LabOrden orden);

	/**
	 * modificar object
	 * 
	 * @param LabOrden
	 * @return LabOrden
	 */
	LabOrden modificar(LabOrden labOrden,
			List<LabOrdenDetalle> listExamenDetalles);

	LabOrden registrarResultados(LabOrden orden,
			List<LabOrdenDetalle> listOrdenDetalles);

	LabOrden registrarResultadosDetalle(LabOrdenDetalle ordenDetalle);

	/**
	 * eliminar object
	 * 
	 * @param labOrden
	 * @return
	 */
	boolean eliminar(LabOrden labOrden);

	/**
	 * 
	 * @return
	 */
	List<LabOrden> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabOrden obtenerLabOrden(Integer id);

	LabOrden obtenerLabOrden(String nombre);

	List<LabOrden> obtenerLabOrdenOrdenAscPorId();

	List<LabOrden> obtenerLabOrdenOrdenDescPorId();

	List<LabOrden> obtenerPorCompania(Compania compania);

	List<LabOrden> obtenerPorCompaniaOrdenadoFechaRegistro(Compania compania);

	List<LabOrden> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);

	List<LabOrden> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania, Personal personal);

	List<LabOrden> obtenerPorCompania(String nombre, Compania compania);

	List<LabOrden> obtenerPorSucursal(Sucursal sucursal);

	List<LabOrden> obtenerAllActivos();

	Integer findActiveCodigoMax(Compania compania);

	List<LabOrden> obtenerDePaciente(Paciente paciente, Compania compania,
			Sucursal sucursal);

	public List<LabOrden> obtenerPorSucursal(String estado, Date fecha,
			Personal medico, Sucursal sucursal);

	public List<LabOrden> obtenerPorSucursal(String estado, Personal medico,
			Sucursal sucursal);
	
	List<LabOrden> obtenerProFechas(Sucursal sucursal,Date fechaini,Date fechafin);
	
	List<LabOrden> obtenerProFechasYEstado(Sucursal sucursal,Date fechaini,Date fechafin,String estado);

	/**
	 * @param paciente
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	List<LabOrden> obtenerParaHC(Paciente paciente,
			HistoriaClinica historiaClinica, Compania compania);

	LabOrden obtenerPorCodigo(String codigo);
	/**
	 * @param orden
	 * @return
	 */
	LabOrden registrar(LabOrden orden);

	/**
	 * @param labOrden
	 * @return
	 */
	boolean eliminarOrden(LabOrden labOrden);

}
