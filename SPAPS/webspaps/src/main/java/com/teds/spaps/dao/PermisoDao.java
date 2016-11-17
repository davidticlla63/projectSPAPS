/**
 * 
 */
package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.primefaces.model.TreeNode;

import com.teds.spaps.interfaces.dao.IPermisoDao;
import com.teds.spaps.model.MenuAccion;
import com.teds.spaps.model.Permiso;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.EDPermiso;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class PermisoDao extends DataAccessObjectJpa<Permiso> implements
		IPermisoDao {

	public PermisoDao() {
		super(Permiso.class);
	}

	@Override
	public Permiso create(Permiso permiso) {
		return super.create(permiso);
	}

	@Override
	public Permiso update(Permiso permiso) {
		return super.update(permiso);
	}

	@Override
	public boolean registrar(List<Permiso> listPermisosAnteriores,
			TreeNode[] selectedNodes, Rol rol, Usuario usuario) {
		try {
			Date fechaActual = new Date();
			List<Permiso> listPermisosNuevo = new ArrayList<>();
			for (TreeNode tn : selectedNodes) {
				EDPermiso e = (EDPermiso) tn.getData();
				MenuAccion menuAccion = e.getMenuAccion();
				if (menuAccion != null) {
					Permiso permiso = new Permiso();
					permiso.setId(listPermisosNuevo.size() + 1);
					permiso.setDescripcion("ninguna");
					permiso.setEstado("AC");
					permiso.setFechaRegistro(fechaActual);
					permiso.setMenuAccion(menuAccion);
					permiso.setRol(rol);
					permiso.setUsuarioRegistro("admin");
					listPermisosNuevo.add(permiso);
				}
			}
			beginTransaction();
			// Verifica si los permiso anteriores esta contenidos en los nuevos
			// permiso,
			// para decidir si eliminarlos o crearlos
			for (Permiso permiso : listPermisosAnteriores) {
				MenuAccion menuAcction = permiso.getMenuAccion();
				if (containsMenuActtion(menuAcction, listPermisosNuevo)) {
					// si esta contenido en la list de nuevos
					// entones borrarlo de la lista de nuevos
					listPermisosNuevo = eliminarPermisoPorMenuAcction(
							menuAcction, listPermisosNuevo);
				} else {
					// si no esta entonces eliminarlo de la base de datos
					permiso.setEstado("RM");
					update(permiso);
				}
			}
			// registrar los nuevos permisos
			for (Permiso permiso : listPermisosNuevo) {
				permiso.setId(0);
				create(permiso);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Permisos agregados al Grupo Usuario " + rol.getNombre());
			return true;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return false;
		}
	}

	private boolean containsMenuActtion(MenuAccion menuAcction,
			List<Permiso> listPermiso) {
		for (Permiso permiso : listPermiso) {
			if (permiso.getMenuAccion().equals(menuAcction)) {
				return true;
			}
		}
		return false;
	}

	private List<Permiso> eliminarPermisoPorMenuAcction(MenuAccion menuAcction,
			List<Permiso> listPermiso) {
		Permiso p = new Permiso();
		for (Permiso permiso : listPermiso) {
			if (permiso.getMenuAccion().equals(menuAcction)) {
				p = permiso;
			}
		}
		listPermiso.remove(p);
		return listPermiso;
	}

	@Override
	public Permiso modificar(Permiso permiso) {
		try {
			beginTransaction();
			permiso = update(permiso);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Permiso " + permiso.toString());
			return permiso;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Permiso permiso) {
		try {
			beginTransaction();
			Permiso bar = update(permiso);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Permiso " + permiso.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<Permiso> obtenerPorRol(Rol rol) {
		return findAllActiveByParameter("rol", rol.getId());
	}

	// @Override
	// public Permiso obtenerPermiso(Integer id) {
	// return findById(id);
	// }
	//
	// @Override
	// public Permiso obtenerPorNombre(String descripcion) {
	// return findByParameter("descripcion", descripcion);
	// }
	//
	// @Override
	// public List<Permiso> obtenerPermisosPorRol(Rol rol) {
	// return findAllActivosByParameter("rol", rol.getId());
	// }
	//
	// @Override
	// public List<Permiso> obtenerDeRol(String nombre) {
	// return findAllActivosByParameter("rol", nombre);
	// }
	//
	// @Override
	// public List<Permiso> obtenerDeMenuAccion(Integer id) {
	// return findAllActivosByParameter("menuAccion", id);
	// }
	//
	// @Override
	// public List<Permiso> obtenerOrdenAscPorId() {
	// return findAscAllOrderedByParameter("id");
	// }
	//
	// @Override
	// public List<Permiso> obtenerOrdenDescPorId() {
	// return findDescAllOrderedByParameter("id");
	// }

}
