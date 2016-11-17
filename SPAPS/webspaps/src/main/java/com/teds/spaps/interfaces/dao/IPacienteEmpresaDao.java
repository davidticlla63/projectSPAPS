/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPacienteEmpresaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param pacienteEmpresa
	 * @return
	 */
	PacienteEmpresa create(PacienteEmpresa pacienteEmpresa);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	PacienteEmpresa update(PacienteEmpresa pacienteEmpresa);

	void delete(PacienteEmpresa pacienteEmpresa);

	/**
	 * registrar object
	 * 
	 * @param PacienteEmpresa
	 * @return PacienteEmpresa
	 */
	PacienteEmpresa registrar(PacienteEmpresa pacienteEmpresa);

	boolean registrar(List<PacienteEmpresa> diagnosticos);

	/**
	 * modificar object
	 * 
	 * @param PacienteEmpresa
	 * @return PacienteEmpresa
	 */
	PacienteEmpresa modificar(PacienteEmpresa pacienteEmpresa);

	/**
	 * eliminar object
	 * 
	 * @param pacienteEmpresa
	 * @return
	 */
	boolean eliminar(PacienteEmpresa pacienteEmpresa);

	/**
	 * 
	 * @return
	 */
	List<PacienteEmpresa> obtenerTodosActivosEInactivosPorOrdenAsc();

	PacienteEmpresa obtenerPacienteEmpresa(Integer id);

	List<PacienteEmpresa> obtenerPacienteEmpresaOrdenAscPorId();

	List<PacienteEmpresa> obtenerPacienteEmpresaOrdenDescPorId();

	List<PacienteEmpresa> obtenerPorCompania(Compania compania);

	List<PacienteEmpresa> obtenerPorSucursal(Sucursal sucursal);

	List<PacienteEmpresa> obtenerPorPaciente(Paciente paciente);

	List<PacienteEmpresa> obtenerPorEmpresa(Compania empresa);

	List<PacienteEmpresa> onComplete(String query);

}
