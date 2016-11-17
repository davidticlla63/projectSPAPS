/**
 * @author ANITA
 */
package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Permiso;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;

/**
 * @author ANITA
 *
 */
public interface ICompaniaDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param compania
	 * @return
	 */
	Compania create(Compania compania);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Compania update(Compania update);

	/**
	 * registrar object
	 * 
	 * @param Compania
	 *            ,Sucursal,Usuario,List<Permiso>
	 * @return boolean
	 */
	boolean registrar(Compania compania, Sucursal sucursal, Usuario usuario,
			Rol rol, List<Permiso> listPermiso);

	/**
	 * registrar object
	 * 
	 * @param Compania
	 * @return Compania
	 */
	Compania registrar(Compania compania);

	/**
	 * modificar object
	 * 
	 * @param Compania
	 * @return Compania
	 */
	Compania modificar(Compania compania);

	/**
	 * modificar object
	 * 
	 * @param Compania
	 *            ,Sucursal,Usuario
	 * @return boolean
	 */
	boolean modificar(Compania compania, Sucursal sucursal, Usuario usuario);

	/**
	 * eliminar object
	 * 
	 * @param compania
	 * @return
	 */
	boolean eliminar(Compania compania);

	/**
	 * eliminar object
	 * 
	 * @param Compania
	 *            ,Sucursal,Usuario
	 * @return boolean
	 */
	boolean eliminar(Compania compania, Sucursal sucursal, Usuario usuario);

	/**
	 * 
	 * @return
	 */
	List<Compania> obtenerTodosActivosEInactivosPorOrdenAsc();

	Compania obtenerCompania(Integer id);

	List<Compania> obtenerCompaniaOrdenAscPorId();

	List<Compania> obtenerCompaniaOrdenDescPorId();

	List<Compania> obtenerPorCompania(Compania compania);

	Compania obtenerCompania(Integer id, Compania compania);

	Compania obtenerCompania(String descripcion);

	List<Compania> obtenerAllActivos();

	List<Compania> obtenerCompaniasActivos(String descripcion);

	/**
	 * @param descripcion
	 * @param compania
	 * @return
	 */
	List<Compania> obtenerPorCompaniaAutoComplete(String descripcion);

}
