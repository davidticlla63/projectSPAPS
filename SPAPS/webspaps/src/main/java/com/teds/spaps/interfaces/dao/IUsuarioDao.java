package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;

/**
 * Interface used to interact with the Usuario.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IUsuarioDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param usuario
	 * @return
	 */
	Usuario create(Usuario usuario);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	Usuario update(Usuario update);

	/**
	 * registrar object
	 * 
	 * @param Usuario
	 * @return Usuario
	 */
	Usuario registrar(Usuario usuario);
	
	/**
	 * registrar object
	 * 
	 * @param Usuario,List<UsuarioSucursal>
	 * @return boolean
	 */
	boolean registrar(Usuario usuario,List<UsuarioSucursal> listUsuarioSucursal);

	/**
	 * modificar object
	 * 
	 * @param Usuario
	 * @return Usuario
	 */
	boolean modificar(Usuario usuario);
	
	/**
	 * modificar object
	 * 
	 * @param Usuario,List<UsuarioSucursal>,List<UsuarioSucursal>
	 * @return boolean
	 */
	boolean modificar(Usuario usuario,List<UsuarioSucursal> listUsuarioSucursal,List<UsuarioSucursal> listUsuarioSucursalEliminadas);

	/**
	 * eliminar object
	 * 
	 * @param usuario
	 * @return
	 */
	boolean eliminar(Usuario usuario);

	/**
	 * 
	 * @param login
	 * @param password
	 * @return Usuario
	 */
	Usuario obtenerPorLoginYPassword(String login, String password);
	
	
	Usuario obtenerPorRol(String estado,Compania compania);

	/**
	 * 
	 * @return
	 */
	List<Usuario> obtenerTodosActivosEInactivosPorOrdenAsc();
	
	/**
	 * 
	 * @return
	 */
	List<Usuario> obtenerTodosActivosEInactivosPorCompaniaPorOrdenAsc(Compania compania);

	Usuario findByLogin(String login, String password);

}
