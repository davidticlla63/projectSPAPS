package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IUsuarioDao;
import com.teds.spaps.interfaces.dao.IUsuarioSucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class UsuarioDao extends DataAccessObjectJpa<Usuario> implements
		IUsuarioDao {

	private @Inject IUsuarioSucursalDao usuarioSucursalDao;

	public UsuarioDao() {
		super(Usuario.class);
	}

	/**
	 * registrar object
	 * 
	 * @param Usuario
	 *            ,List<UsuarioSucursal>
	 * @return Usuario
	 */
	@Override
	public boolean registrar(Usuario usuario,
			List<UsuarioSucursal> listUsuarioSucursal) {
		try {
			beginTransaction();
			usuario = create(usuario);
			for (UsuarioSucursal us : listUsuarioSucursal) {
				us.setUsuario(usuario);
				usuarioSucursalDao.create(us);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Usuario " + usuario.getNombre());
			return true;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Usuario registrar(Usuario usuario) {
		try {
			beginTransaction();
			usuario = create(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Usuario " + usuario.getNombre());
			return usuario;
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
	public boolean modificar(Usuario usuario,
			List<UsuarioSucursal> listUsuarioSucursal,
			List<UsuarioSucursal> listUsuarioSucursalEliminadas) {
		try {
			beginTransaction();
			update(usuario);
			for (UsuarioSucursal us : listUsuarioSucursal) {
				if (us.getId().intValue() <= 0) {
					us.setId(0);
					us.setFechaRegistro(usuario.getFechaRegistro());
					us.setUsuario(usuario);
					us.setUsuarioRegistro("admin");
					usuarioSucursalDao.create(us);
				} else {
					us.setFechaModificacion(usuario.getFechaModificacion());
					usuarioSucursalDao.update(us);
				}
			}
			for (UsuarioSucursal us : listUsuarioSucursalEliminadas) {
				us.setEstado("RM");
				usuarioSucursalDao.update(us);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Usuario " + usuario.getNombre());
			return true;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean modificar(Usuario usuario) {
		try {
			beginTransaction();
			update(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Usuario " + usuario.getNombre());
			return true;
		} catch (Exception e) {
			Throwable t = e.getCause();
			String cause = t.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean eliminar(Usuario usuario) {
		try {
			beginTransaction();
			usuario.setState("RM");
			usuario.setLogin(new Date() + "|" + usuario.getLogin());
			Usuario bar = update(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Usuario " + usuario.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Usuario obtenerPorLoginYPassword(String login, String password) {
		return findByParameterObjectTwo("login", "password", login, password);

	}

	@Override
	public Usuario obtenerPorRol(String estado, Compania compania) {
		String query = "select em from Usuario em where em.estado='AC' and em.tipo='"
				+ estado
				+ "' and  em.id in (select r.usuario.id from UsuarioSucursal r where r.sucursal.compania.id="
				+ compania.getId() + ")";
		System.out.println(query);
		return executeQuerySingleResult(query);

	}

	@Override
	public List<Usuario> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	@Override
	public Usuario findByLogin(String login, String password) {
		try {
			return findByParameterObjectTwo("login", "password", login,
					password);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Usuario> obtenerTodosActivosEInactivosPorCompaniaPorOrdenAsc(
			Compania compania) {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

}
