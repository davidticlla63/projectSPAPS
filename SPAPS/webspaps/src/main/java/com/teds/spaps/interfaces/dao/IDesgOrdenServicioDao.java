/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.DesgOrdenServicios;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IDesgOrdenServicioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param desgOrdenServicio
	 * @return
	 */
	DesgOrdenServicio create(DesgOrdenServicio desgOrdenServicio);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	DesgOrdenServicio update(DesgOrdenServicio update);

	/**
	 * registrar object
	 * 
	 * @param DesgOrdenServicio
	 * @return DesgOrdenServicio
	 */
	DesgOrdenServicio registrar(DesgOrdenServicio desgOrdenServicio);

	/**
	 * modificar object
	 * 
	 * @param DesgOrdenServicio
	 * @return DesgOrdenServicio
	 */
	DesgOrdenServicio modificar(DesgOrdenServicio desgOrdenServicio);

	/**
	 * eliminar object
	 * 
	 * @param desgOrdenServicio
	 * @return
	 */
	boolean eliminar(DesgOrdenServicio desgOrdenServicio);

	/**
	 * 
	 * @return
	 */
	List<DesgOrdenServicio> obtenerTodosActivosEInactivosPorOrdenAsc();

	DesgOrdenServicio obtenerDesgOrdenServicio(Integer id);

	List<DesgOrdenServicio> obtenerDesgOrdenServicioOrdenAscPorId();

	List<DesgOrdenServicio> obtenerDesgOrdenServicioOrdenDescPorId();

	List<DesgOrdenServicio> obtenerPorCompania(Compania compania);

	List<DesgOrdenServicio> obtenerPorSucursal(Sucursal sucursal);

	List<DesgOrdenServicio> obtenerPorCliente(Paciente cliente,
			Compania compania);

	List<DesgOrdenServicio> obtenerPorCliente(Paciente cliente,
			Sucursal sucursal);

	List<DesgOrdenServicio> onComplete(String query);

	/**
	 * @param desgOrdenServicio
	 * @param labExamenes
	 * @param imagExamenes
	 * @param servicios
	 * @return
	 */
	DesgOrdenServicio registrar(DesgOrdenServicio desgOrdenServicio,
			List<DesgOrdenServicioLabExamen> labExamenes,
			List<DesgOrdenServicioImagExamen> imagExamenes,
			List<DesgOrdenServicios> servicios);

	/**
	 * @param desgOrdenServicio
	 * @param labExamenesSacar
	 * @param labExamenesMeter
	 * @param imagExamenesSacar
	 * @param imagExamenesMeter
	 * @param serviciosSacar
	 * @param serviciosMeter
	 * @return
	 */
	DesgOrdenServicio modificar(DesgOrdenServicio desgOrdenServicio,
			List<DesgOrdenServicioLabExamen> labExamenesSacar,
			List<DesgOrdenServicioLabExamen> labExamenesMeter,
			List<DesgOrdenServicioImagExamen> imagExamenesSacar,
			List<DesgOrdenServicioImagExamen> imagExamenesMeter,
			List<DesgOrdenServicios> serviciosSacar,
			List<DesgOrdenServicios> serviciosMeter);

	/**
	 * @param desgOrdenServicio
	 * @param labExamenes
	 * @param imagExamenes
	 * @param servicios
	 * @return
	 */
	boolean eliminar(DesgOrdenServicio desgOrdenServicio,
			List<DesgOrdenServicioLabExamen> labExamenes,
			List<DesgOrdenServicioImagExamen> imagExamenes,
			List<DesgOrdenServicios> servicios);

	/**
	 * @return
	 */
	List<DesgOrdenServicio> obtenerAll();

	/**
	 * @param cliente
	 * @param sucursal
	 * @return
	 */
	List<DesgOrdenServicio> obtenerPorClientePendientes(Paciente cliente,
			Sucursal sucursal);

	/**
	 * @param cliente
	 * @param sucursal
	 * @return
	 */
	List<DesgOrdenServicio> obtenerPorClienteAprobadas(Paciente cliente,
			Sucursal sucursal);

	/**
	 * @param historiaClinica
	 * @param sucursal
	 * @return
	 */
	List<DesgOrdenServicio> obtenerPorHC(HistoriaClinica historiaClinica,
			Sucursal sucursal);

	/**
	 * @param historiaClinica
	 * @param compania
	 * @return
	 */
	List<DesgOrdenServicio> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania);

}
