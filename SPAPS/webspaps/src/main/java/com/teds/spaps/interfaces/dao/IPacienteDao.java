/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.GrupoFamiliar;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.Parentesco;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Sucursal;

/**
 * @author ANITA
 *
 */
public interface IPacienteDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param paciente
	 * @return
	 */
	Paciente create(Paciente paciente);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Paciente update(Paciente update);

	void delete(Paciente paciente);

	/**
	 * 
	 * @return
	 */
	List<Paciente> obtenerTodosActivosEInactivosPorOrdenAsc();

	Paciente obtenerIndividuo(Integer id);

	Paciente obtenerIndividuoPorFamilia(Integer id);

	Paciente obtenerIndividuoPorFamilia(String codigo);

	List<Paciente> obtenerIndividuosPorNombreCompania(String nombre,
			String apellidoPaterno, String apellidoMaterno, Compania compania);

	List<Paciente> obtenerPorSucursal(String nombre, Sucursal sucursal);

	List<Paciente> obtenerIndividuosPorCodigo(String codigo, Compania compania);

	List<Paciente> obtenerIndividuoOrdenAscPorId();

	List<Paciente> obtenerIndividuoOrdenDescPorId();

	List<Paciente> obtenerFamiliaPorCodigo(String codigo);

	List<Paciente> obtenerFamiliaPorId(Integer id);

	List<Paciente> obtenerPorCompania(Compania compania);

	List<Paciente> obtenerPorEmpresa(Empresa empresa);

	List<Paciente> obtenerPorSucursal(Sucursal sucursal);

	List<Paciente> obtenerPorCompania(String nombre, Compania sucursal);

	Paciente obtenerUltimo();

	String generarCodigo(String patron, Integer id);

	boolean verificarRegistro(Paciente paciente, Sucursal sucursal);

	/**
	 * @param codigo
	 * @param compania
	 * @return
	 */
	List<Paciente> obtenerPorCodigoHC(String codigo, Compania compania);

	/**
	 * @param codigo
	 * @param compania
	 * @return
	 */
	Paciente obtenerPaciente(String codigo, Compania compania);

	/**
	 * @param codigoHc
	 * @param compania
	 * @return
	 */
	Paciente obtenerPacienteHC(String codigoHc, Compania compania);

	/**
	 * @param paciente
	 * @return
	 */
	Paciente registrar(Paciente paciente);

	/**
	 * @param nombre
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param sucursal
	 * @return
	 */
	List<Paciente> obtenerIndividuosPorNombreSucursal(String nombre,
			String apellidoPaterno, String apellidoMaterno, Sucursal sucursal);

	/**
	 * @param id
	 * @return
	 */
	Paciente obtenerPaciente(Integer id);

	/**
	 * @param id
	 * @return
	 */
	Paciente obtenerTitularPorFamilia(Integer id);

	/**
	 * @param paciente
	 * @param listaPlanSeguro
	 * @param grupoFamiliar
	 * @param parentesco
	 * @param listaDocumentosIdentificacion
	 * @param empresas
	 * @return
	 */
	Paciente registrar(Paciente paciente, List<PlanesSeguro> listaPlanSeguro,
			GrupoFamiliar grupoFamiliar, Parentesco parentesco,
			List<IdentificacionPaciente> listaDocumentosIdentificacion,
			List<PacienteEmpresa> empresas);

	/**
	 * @param paciente
	 * @param listaEliminar
	 * @param listaMeter
	 * @param grupoFamiliar
	 * @param parentesco
	 * @param listaDISacar
	 * @param listaDIMeter
	 * @param empresasSacar
	 * @param empresasMeter
	 * @return
	 */
	Paciente modificar(Paciente paciente, List<PlanesSeguro> listaEliminar,
			List<PlanesSeguro> listaMeter, GrupoFamiliar grupoFamiliar,
			Parentesco parentesco, List<IdentificacionPaciente> listaDISacar,
			List<IdentificacionPaciente> listaDIMeter,
			List<PacienteEmpresa> empresasSacar,
			List<PacienteEmpresa> empresasMeter);

	/**
	 * @param paciente
	 * @param listaEliminar
	 * @param identificacionPacientes
	 * @param empresas
	 * @return
	 */
	boolean eliminar(Paciente paciente, List<PlanesSeguro> listaEliminar,
			List<IdentificacionPaciente> identificacionPacientes,
			List<PacienteEmpresa> empresas);

	Paciente DevolverPacientePorId(Integer id);
	List<Paciente> devolverPacienteOnCompleteCompania(Compania compania, String nombre);

}
