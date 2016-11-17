package com.teds.spaps.interfaces.dao;

import java.util.List;

import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;

/**
 * Interface used to interact with the UsuarioSucursal.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IUsuarioSucursalDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param usuario
	 * @return
	 */
	UsuarioSucursal create(UsuarioSucursal usuarioSucursal);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param update
	 * @return
	 */
	UsuarioSucursal update(UsuarioSucursal update);

	/**
	 * registrar object
	 * 
	 * @param UsuarioSucursal
	 * @return UsuarioSucursal
	 */
	UsuarioSucursal registrar(UsuarioSucursal usuarioSucursal);

	/**
	 * modificar object
	 * 
	 * @param UsuarioSucursal
	 * @return UsuarioSucursal
	 */
	UsuarioSucursal modificar(UsuarioSucursal usuarioSucursal);

	/**
	 * eliminar object
	 * 
	 * @param usuario
	 * @return
	 */
	boolean eliminar(UsuarioSucursal usuarioSucursal);


	/**
	 * 
	 * @return List<UsuarioSucursal>
	 */
	List<UsuarioSucursal> obtenerTodosActivosEInactivosPorOrdenAsc();
	
	/**
	 * 
	 * @return List<UsuarioSucursal>
	 */
	List<UsuarioSucursal> obtenerTodosPorCompania(Compania compania);
	
	/**
	 * 
	 * @return List<UsuarioSucursal>
	 */
	List<UsuarioSucursal> obtenerTodosPorUsuario(Usuario usuario);

}
