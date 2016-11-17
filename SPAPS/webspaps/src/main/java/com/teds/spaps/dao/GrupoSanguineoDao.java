package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IGrupoSanguineoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.GrupoSanguineo;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class GrupoSanguineoDao extends DataAccessObjectJpa<GrupoSanguineo>
		implements IGrupoSanguineoDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public GrupoSanguineoDao() {
		super(GrupoSanguineo.class);
	}

	@Override
	public GrupoSanguineo create(GrupoSanguineo grupoSanguineo) {
		return super.create(grupoSanguineo);
	}

	@Override
	public GrupoSanguineo update(GrupoSanguineo grupoSanguineo) {
		return super.update(grupoSanguineo);
	}

	@Override
	public GrupoSanguineo registrar(GrupoSanguineo GrupoSanguineo) {
		try {
			beginTransaction();
			GrupoSanguineo = create(GrupoSanguineo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Grupo Sanguineo "
					+ GrupoSanguineo.getDescripcion());
			return GrupoSanguineo;
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
	public GrupoSanguineo modificar(GrupoSanguineo GrupoSanguineo) {
		try {
			beginTransaction();
			GrupoSanguineo = update(GrupoSanguineo);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"Grupo Sanguineo " + GrupoSanguineo.getDescripcion());
			return GrupoSanguineo;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				if (!isDelete())
					FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(GrupoSanguineo GrupoSanguineo) {
		try {
			setDelete(true);
			GrupoSanguineo.setEstado("RM");
			beginTransaction();
			GrupoSanguineo bar = update(GrupoSanguineo);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ GrupoSanguineo.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public GrupoSanguineo obtenerGrupoSanguineo(Integer id) {
		return findById(id);
	}

	@Override
	public GrupoSanguineo obtenerPorDescripcion(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<GrupoSanguineo> obtenerGrupoSanguineoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<GrupoSanguineo> obtenerGrupoSanguineoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<GrupoSanguineo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<GrupoSanguineo> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	@Override
	public List<GrupoSanguineo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IGrupoSanguineoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<GrupoSanguineo> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<GrupoSanguineo> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<GrupoSanguineo> obtenerPorSucursalAutoComplete(
			String descripcion, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<GrupoSanguineo> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
