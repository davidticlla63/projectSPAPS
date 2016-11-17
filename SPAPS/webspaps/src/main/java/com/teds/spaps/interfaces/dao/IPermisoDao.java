package com.teds.spaps.interfaces.dao;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.teds.spaps.model.Permiso;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Usuario;

/**
 * Interface used to interact with the Permiso.
 * 
 * @author mauriciobejaranorivera
 *
 */
public interface IPermisoDao {

	/**
	 * Use only intermediate transactions
	 * 
	 * @param permiso
	 * @return
	 */
	Permiso create(Permiso permiso);

	/**
	 * Use only intermediate transactions
	 * 
	 * @param permiso
	 * @return
	 */
	Permiso update(Permiso permiso);

	/**
	 * registrar object
	 * 
	 * @param List<Permiso>
	 * @param TreeNode[]
	 * @param Rol
	 * @param Usuario
	 * @return boolean
	 */
	boolean registrar(List<Permiso> listPermisoAnteriores,TreeNode[] selectedNodes,Rol rol,Usuario usuario);

	/**
	 * modificar object
	 * 
	 * @param Permiso
	 * @return Permiso
	 */
	Permiso modificar(Permiso permiso);

	/**
	 * eliminar object
	 * 
	 * @param permiso
	 * @return boolean
	 */
	boolean eliminar(Permiso permiso);

	// /**
	// * obtenerPermiso
	// * @param id
	// * @return Permiso
	// */
	// Permiso obtenerPermiso(Integer id);
	//
	// /**
	// * obtenerPorNombre
	// * @param id
	// * @return Permiso
	// */
	// Permiso obtenerPorNombre(String descripcion);
	//
	// /**
	// * obtenerDeRol
	// * @param nombre
	// * @return List<Permiso>
	// */
	// List<Permiso> obtenerDeRol(String nombre);
	//
	/**
	 * obtenerPermisosDeRol
	 * 
	 * @return List<Permiso>
	 */
	List<Permiso> obtenerPorRol(Rol rol);
	//
	// /**
	// * obtenerDeMenuAccion
	// * @return List<Permiso>
	// */
	// List<Permiso> obtenerDeMenuAccion(Integer id) ;
	//
	// /**
	// * obtenerOrdenAscPorId
	// * @return List<Permiso>
	// */
	// List<Permiso> obtenerOrdenAscPorId() ;
	//
	// /**
	// * obtenerOrdenDescPorId
	// * @return List<Permiso>
	// */
	// List<Permiso> obtenerOrdenDescPorId() ;

}
