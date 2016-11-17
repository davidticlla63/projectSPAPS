/**
 * 
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IGrupoFamiliarDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.GrupoFamiliar;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class GrupoFamiliar1Dao extends DataAccessObjectJpa<GrupoFamiliar>
		implements IGrupoFamiliarDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public GrupoFamiliar1Dao() {
		super(GrupoFamiliar.class);
	}

	@Override
	public GrupoFamiliar create(GrupoFamiliar grupoFamiliar) {
		return super.create(grupoFamiliar);
	}

	/*
	 * @Override public GrupoFamiliar update(GrupoFamiliar grupoFamiliar) {
	 * return super.update(grupoFamiliar); }
	 */

	@Override
	public GrupoFamiliar registrar(GrupoFamiliar grupoFamiliar) {
		try {
			beginTransaction();
			grupoFamiliar = create(grupoFamiliar);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "GrupoFamiliar "
					+ grupoFamiliar.toString());
			return grupoFamiliar;
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
	public GrupoFamiliar modificar(GrupoFamiliar grupoFamiliar) {
		try {
			beginTransaction();
			grupoFamiliar = update(grupoFamiliar);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Grupo Familiar "
					+ grupoFamiliar.toString());
			return grupoFamiliar;
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
	public boolean eliminar(GrupoFamiliar grupoFamiliar) {
		try {
			setDelete(true);
			grupoFamiliar.setEstado("RM");
			beginTransaction();
			GrupoFamiliar bar = update(grupoFamiliar);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Familiar "
					+ grupoFamiliar.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(GrupoFamiliar grupoFamiliar,
			Compania compania) {
		List<GrupoFamiliar> grupoFamiliar1s = obtenerPorCompania(compania);
		for (GrupoFamiliar grupoFamiliar12 : grupoFamiliar1s) {
			if (grupoFamiliar12.getCodigo().equalsIgnoreCase(
					grupoFamiliar.getCodigo())
					|| grupoFamiliar12.equals(grupoFamiliar))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(GrupoFamiliar grupoFamiliar,
			Compania compania) {
		List<GrupoFamiliar> grupoFamiliar1s = obtenerPorCompania(compania);
		for (GrupoFamiliar grupoFamiliar12 : grupoFamiliar1s) {
			if (grupoFamiliar12.getCodigo().equalsIgnoreCase(
					grupoFamiliar.getCodigo())
					&& !grupoFamiliar12.equals(grupoFamiliar))
				return true;
		}
		return false;
	}

	@Override
	public GrupoFamiliar obtenerGrupoFamiliar1(Integer id) {
		return findById(id);
	}

	@Override
	public GrupoFamiliar obtenerGrupoFamiliar1(String descripcion) {
		return findByParameter("nombre", descripcion);
	}

	@Override
	public List<GrupoFamiliar> obtenerGrupoFamiliar1OrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<GrupoFamiliar> obtenerGrupoFamiliar1OrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<GrupoFamiliar> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<GrupoFamiliar> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<GrupoFamiliar> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IGrupoFamiliarDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<GrupoFamiliar> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<GrupoFamiliar> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("nombre", query);
	}

	@Override
	public List<GrupoFamiliar> obtenerPorCompaniaAutoComplete(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<GrupoFamiliar> obtenerPorSucursalAutoComplete(String nombre,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
