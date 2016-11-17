package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

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
public class UsuarioSucursalDao extends DataAccessObjectJpa<UsuarioSucursal>
		implements IUsuarioSucursalDao {

	public UsuarioSucursalDao() {
		super(UsuarioSucursal.class);
	}

	@Override
	public UsuarioSucursal registrar(UsuarioSucursal usuario) {
		try {
			beginTransaction();
			usuario = create(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "UsuarioSucursal ");
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
	public UsuarioSucursal modificar(UsuarioSucursal usuario) {
		try {
			beginTransaction();
			usuario = update(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "UsuarioSucursal "
					+ usuario.toString());
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
	public boolean eliminar(UsuarioSucursal usuario) {
		try {
			beginTransaction();
			// usuario.setState("RM");
			// usuario.setLogin(new Date() + "|" + usuario.getLogin());
			UsuarioSucursal bar = update(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "UsuarioSucursal "
					+ usuario.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<UsuarioSucursal> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioSucursal> obtenerTodosPorCompania(Compania compania) {
		return ((List<UsuarioSucursal>) super.getEntityManager()
				.createNamedQuery("UsuarioSucursal.findAllByCompania")
				.setParameter("idCompania", compania.getId()).getResultList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioSucursal> obtenerTodosPorUsuario(Usuario usuario) {
		return ((List<UsuarioSucursal>) super.getEntityManager()
				.createNamedQuery("UsuarioSucursal.findAllByUsuario")
				.setParameter("idUsuario", usuario.getId()).getResultList());
	}

}
