package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.exolab.castor.types.Date;

import com.teds.spaps.interfaces.dao.ICompaniaDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPermisoDao;
import com.teds.spaps.interfaces.dao.IPersonalDao;
import com.teds.spaps.interfaces.dao.IRolDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.interfaces.dao.IUsuarioDao;
import com.teds.spaps.interfaces.dao.IUsuarioSucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Permiso;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Rol;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.model.UsuarioSucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.Time;

@Stateless
public class CompaniaDao extends DataAccessObjectJpa<Compania> implements
		ICompaniaDao {

	private @Inject IUsuarioDao usuarioDao;
	private @Inject IPersonalDao personalDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IPermisoDao permisoDao;
	private @Inject IRolDao rolDao;
	private @Inject IUsuarioSucursalDao usuarioSucursalDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	public CompaniaDao() {
		super(Compania.class);
	}

	@Override
	public Compania create(Compania compania) {
		return super.create(compania);
	}

	@Override
	public Compania update(Compania compania) {
		return super.update(compania);
	}

	@Override
	public boolean registrar(Compania compania, Sucursal sucursal,
			Usuario usuario, Rol rol, List<Permiso> listPermiso) {
		try {
			beginTransaction();
			// compania
			compania = create(compania);
			// rol
			rol.setFechaRegistro(compania.getFechaRegistro());
			rol.setCompania(compania);
			rol = rolDao.create(rol);
			// sucursal
			sucursal.setFechaRegistro(compania.getFechaRegistro());
			sucursal.setCompania(compania);
			sucursal = sucursalDao.create(sucursal);
			// usuario
			usuario.setFechaRegistro(compania.getFechaRegistro());
			usuario.setRol(rol);
			usuario.setUsuarioRegistro(compania.getUsuarioRegistro());
			//usuario.getListUsuarioSucursals().add(new UsuarioSucursal(usuario, sucursal, "AC", sucursal.getFechaRegistro(), sucursal.getUsuarioRegistro()));
			
			//usuario = usuarioDao.create(usuario);
			
			Personal  personal= new Personal();
			personal.setNombre(usuario.getNombre());
			personal.setEmail(usuario.getEmail());
			personal.setApellidoMaterno("");
			personal.setApellidoPaterno("");
			personal.setEstado("AC");
			personal.setDireccion("");
			personal.setTelefono("");
			personal.setFechaRegistro(usuario.getFechaRegistro());
			personal.setUsuarioRegistro(compania.getUsuarioRegistro());
			personal.setCodigo("AD0000"+compania.getId());
			personal.setUsuario(usuario);
			personal.setSucursal(sucursal);
			personal.setCargo(null);
			personal.setLogin(true);
			personal =personalDao .create(personal);
			
			// Usuario Sucursal
			UsuarioSucursal us = new UsuarioSucursal();
			us.setEstado("AC");
			us.setSucursal(sucursal);
			us.setUsuario(usuario);
			us.setFechaRegistro(compania.getFechaRegistro());
			us.setUsuarioRegistro(compania.getUsuarioRegistro());
			us = usuarioSucursalDao.create(us);
			for (Permiso permiso : listPermiso) {
				permiso.setFechaRegistro(compania.getFechaRegistro());
				permiso.setRol(rol);
				permisoDao.create(permiso);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Compania " + compania.getDescripcion());
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

	@Override
	public Compania registrar(Compania compania) {
		try {
			beginTransaction();
			compania = create(compania);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Compania " + compania.getDescripcion());
			return compania;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean modificar(Compania compania, Sucursal sucursal,
			Usuario usuario) {
		try {
			beginTransaction();
			update(compania);
			sucursalDao.update(sucursal);
			usuarioDao.update(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Compania "
					+ compania.getDescripcion());
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

	@Override
	public Compania modificar(Compania compania) {
		try {
			beginTransaction();
			compania = update(compania);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Compania "
					+ compania.getDescripcion());
			return compania;
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
	public boolean eliminar(Compania compania, Sucursal sucursal,
			Usuario usuario) {
		try {
			beginTransaction();
			compania.setEstado("RM");
			compania.setNombre(new Date() + "|" + compania.getNombre());
			update(compania);
			sucursal.setEstado("RM");
			sucursal.setNombre(new Date() + "|" + sucursal.getNombre());
			sucursalDao.update(sucursal);
			usuario.setEstado("RM");
			usuario.setLogin(new Date() + "|" + usuario.getLogin());
			usuarioDao.update(usuario);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Compania "
					+ compania.getDescripcion());
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

	@Override
	public boolean eliminar(Compania compania) {
		try {
			beginTransaction();
			Compania bar = update(compania);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Compania "
					+ compania.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Compania obtenerCompania(Integer id) {
		return findById(id);
	}

	@Override
	public Compania obtenerCompania(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	@Override
	public Compania obtenerCompania(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<Compania> obtenerCompaniaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Compania> obtenerCompaniaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Compania> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	@Override
	public List<Compania> obtenerCompaniasActivos(String descripcion) {
		return findAllActivosByQuery("descripcion", descripcion);
	}

	@Override
	public List<Compania> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	@Override
	public List<Compania> obtenerPorCompania(Compania compania) {
		return null;
	}

	@Override
	public List<Compania> obtenerPorCompaniaAutoComplete(String descripcion) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC");
	}

}
