/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Especialidad;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.PersonalEspecialidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;

/**
 * @author ANITA
 *
 */
public interface IPersonalDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param personal
	 * @return
	 */
	Personal create(Personal personal);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Personal update(Personal update);
	
	
	Personal registrar(Personal personal);

	/**
	 * registrar object
	 * 
	 * @param Personal
	 * @return Personal
	 */
	Personal registrar(Personal personal,
			List<PersonalEspecialidad> especialidades, TreeNode[] selectedNodes);

	/**
	 * modificar object
	 * 
	 * @param Personal
	 * @return Personal
	 */
	Personal modificar(Personal personal,
			List<PersonalEspecialidad> especialidadesMeter,
			TreeNode[] selectedNodes);

	/**
	 * eliminar object
	 * 
	 * @param personal
	 * @return
	 */
	boolean eliminar(Personal personal);

	/**
	 * 
	 * @return
	 */
	List<Personal> obtenerTodosActivosEInactivosPorOrdenAsc();

	Personal obtenerPersonal(Integer id);

	List<Personal> obtenerPersonalOrdenAscPorId();

	List<Personal> obtenerPersonalOrdenDescPorId();

	List<Personal> obtenerPorSucursal(Sucursal sucursal);

	List<Personal> obtenerPorCompania(String nombre, Sucursal sucursal);

	List<Personal> obtenerPorUsuario(Usuario usuario);

	List<Personal> obtenerPorSucursal(String nombre, Sucursal sucursal);

	List<Personal> obtenerPorSucursalAndEpecialidad(String descripcion,
			Especialidad especialidad, Sucursal sucursal);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<Personal> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania);

	/**
	 * @param descripcion
	 * @param sucursal
	 * @return
	 */
	List<Personal> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal);

	/**
	 * @param nombre
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param compania
	 * @return
	 */
	List<Personal> obtenerPorNombreCompania(String nombre,
			String apellidoPaterno, String apellidoMaterno, Compania compania);

	/**
	 * @param nombre
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param sucursal
	 * @return
	 */
	List<Personal> obtenerPorNombreSucursal(String nombre,
			String apellidoPaterno, String apellidoMaterno, Sucursal sucursal);

	/**
	 * @param usuario
	 * @param sucursal
	 * @return
	 */
	Personal obtenerPorUsuario(Usuario usuario, Sucursal sucursal);

	/**
	 * @param usuario
	 * @param compania
	 * @return
	 */
	Personal obtenerPorUsuario(Usuario usuario, Compania compania);
}
