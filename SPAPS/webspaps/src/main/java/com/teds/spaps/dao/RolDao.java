package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IRolDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Rol;
import com.teds.spaps.util.FacesUtil;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class RolDao extends DataAccessObjectJpa<Rol> implements IRolDao {

	public RolDao() {
		super(Rol.class);
	}

	@Override
	public Rol create(Rol rol) {
		return super.create(rol);
	}

	@Override
	public Rol update(Rol rol) {
		return super.update(rol);
	}

	@Override
	public Rol registrar(Rol rol) {
		try {
			beginTransaction();
			rol = create(rol);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Rol " + rol.toString());
			return rol;
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
	public boolean modificar(Rol rol) {
		try {
			beginTransaction();
			update(rol);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Rol " + rol.toString());
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
			return true;
		}
	}

	@Override
	public boolean eliminar(Rol rol) {
		try {
			beginTransaction();
			rol.setEstado("RM");
			rol.setNombre(new Date() + "|" + rol.getNombre());
			rol = update(rol);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Rol " + rol.toString());
			return true;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public List<Rol> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByParameterObjectTwoQueryOr("estado", "estado", "AC",
				"IN");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> obtenerTodosActivosEInactivosRpoCompaniaPorOrdenAsc(
			Compania compania) {
		return ((List<Rol>) super.getEntityManager()
				.createNamedQuery("Rol.findAllActiveAndInactiveByCompania")
				.setParameter("idCompania", compania.getId()).getResultList());
	}

	// public Rol obtenerRol(Integer id) {
	// return findById(id);
	// }
	//
	// public Rol obtenerRolPorNombre(String nombre) {
	// return findByParameter("nombre", nombre);
	// }
	//
	// public List<Rol> obtenerRolChilds(Integer id) {
	// return findAllActivosByParameter("rol", id);
	// }
	//
	// public List<Rol> obtenerRolChilds(String nombre) {
	// return findAllActivosByParameter("nombre", nombre);
	// }
	//
	// public List<Rol> obtenerRolOrdenAscPorId() {
	// return findAscAllOrderedByParameter("id");
	// }
	//
	// public List<Rol> obtenerRolOrdenDescPorId() {
	// return findDescAllOrderedByParameter("id");
	// }

}
