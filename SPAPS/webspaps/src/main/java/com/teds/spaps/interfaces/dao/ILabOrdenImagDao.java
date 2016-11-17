/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.Date;
import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface ILabOrdenImagDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param labOrden
	 * @return
	 */
	LabOrdenImag create(LabOrdenImag labOrden);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	LabOrdenImag update(LabOrdenImag update);

	/**
	 * registrar object
	 * 
	 * @param LabOrdenImag
	 * @return LabOrdenImag
	 */
	LabOrdenImag registrar(LabOrdenImag labOrden,
			List<LabOrdenDetalleImag> listExamenDetalles);

	LabOrdenImag modificar(LabOrdenImag orden);

	/**
	 * modificar object
	 * 
	 * @param LabOrdenImag
	 * @return LabOrdenImag
	 */
	LabOrdenImag modificar(LabOrdenImag labOrden,
			List<LabOrdenDetalleImag> listExamenDetalles);

	LabOrdenImag registrarResultados(LabOrdenImag orden,
			List<LabOrdenDetalleImag> listOrdenDetalles);

	LabOrdenImag registrarResultadosDetalle(LabOrdenDetalleImag ordenDetalle);

	/**
	 * eliminar object
	 * 
	 * @param labOrden
	 * @return
	 */
	boolean eliminar(LabOrdenImag labOrden);

	/**
	 * 
	 * @return
	 */
	List<LabOrdenImag> obtenerTodosActivosEInactivosPorOrdenAsc();

	LabOrdenImag obtenerLabOrdenImag(Integer id);

	LabOrdenImag obtenerLabOrdenImag(String nombre);

	List<LabOrdenImag> obtenerLabOrdenImagOrdenAscPorId();

	List<LabOrdenImag> obtenerLabOrdenImagOrdenDescPorId();

	List<LabOrdenImag> obtenerPorCompania(Compania compania);

	List<LabOrdenImag> obtenerPorCompaniaOrdenadoFechaRegistro(Compania compania);

	List<LabOrdenImag> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania);

	List<LabOrdenImag> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania, Personal personal);

	List<LabOrdenImag> obtenerPorCompania(String nombre, Compania compania);

	List<LabOrdenImag> obtenerPorSucursal(Sucursal sucursal);

	List<LabOrdenImag> obtenerAllActivos();

	Integer findActiveCodigoMax(Compania compania);

	List<LabOrdenImag> obtenerDePaciente(Paciente paciente, Compania compania,
			Sucursal sucursal);

	/**
	 * @param paciente
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	List<LabOrdenImag> obtenerParaHC(Paciente paciente,
			HistoriaClinica historiaClinica, Compania compania);

	/**
	 * @param orden
	 * @return
	 */
	LabOrdenImag registrar(LabOrdenImag orden);

	/**
	 * @param labOrden
	 * @return
	 */
	boolean eliminarOrden(LabOrdenImag labOrden);

	public List<LabOrdenImag> obtenerPorSucursal(String estado, Date fecha,
			Personal medico, Sucursal sucursal);

	public List<LabOrdenImag> obtenerPorSucursal(String estado,
			Personal medico, Sucursal sucursal);
	
	List<LabOrdenImag> obtenerProFechas(Sucursal sucursal,Date fechaini,Date fechafin);
	
	List<LabOrdenImag> obtenerProFechasYEstado(Sucursal sucursal,Date fechaini,Date fechafin,String estado);

}
