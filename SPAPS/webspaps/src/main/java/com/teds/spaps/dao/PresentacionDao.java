package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IPresentacionDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Presentacion;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class PresentacionDao extends DataAccessObjectJpa<Presentacion>
		implements IPresentacionDao {

	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public PresentacionDao() {
		super(Presentacion.class);
	}

	@Override
	public Presentacion create(Presentacion presentacion) {
		return super.create(presentacion);
	}

	@Override
	public Presentacion update(Presentacion presentacion) {
		return super.update(presentacion);
	}

	@Override
	public Presentacion registrar(Presentacion presentacion) {
		try {
			beginTransaction();
			presentacion = create(presentacion);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Presentacion "
					+ presentacion.toString());
			return presentacion;
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
	public Presentacion modificar(Presentacion presentacion) {
		try {
			beginTransaction();
			presentacion = update(presentacion);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "Presentacion "
						+ presentacion.toString());
			return presentacion;
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
	public boolean eliminar(Presentacion presentacion) {
		try {
			setDelete(true);
			presentacion.setEstado("RM");
			beginTransaction();
			Presentacion bar = update(presentacion);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "Presentacion "
					+ presentacion.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public Presentacion obtenerPresentacion(Integer id) {
		return findById(id);
	}

	@Override
	public List<Presentacion> obtenerPorCompania(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public Presentacion obtenerPresentacion(String descripcion,
			Compania compania) {
		return findByParameterObjectThree("estado", "descripcion", "compania",
				"AC", descripcion, compania.getId());
	}

	@Override
	public List<Presentacion> obtenerPresentacionOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Presentacion> obtenerPresentacionOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Presentacion> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	@Override
	public boolean verificarRepetido(Presentacion presentacion,
			Compania compania) {
		List<Presentacion> presentacions = obtenerPorCompania(compania);
		for (Presentacion presentacion2 : presentacions) {
			if (presentacion2.getDescripcion().equalsIgnoreCase(
					presentacion.getDescripcion())
					|| presentacion2.equals(presentacion))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Presentacion presentacion,
			Compania compania) {
		List<Presentacion> presentacions = obtenerPorCompania(compania);
		for (Presentacion presentacion2 : presentacions) {
			if (presentacion2.equals(presentacion))
				return false;
			if (presentacion2.getDescripcion().equalsIgnoreCase(
					presentacion.getDescripcion()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IPresentacionDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Presentacion> obtenerTodosActivosEInactivosPorOrdenAsc() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<Presentacion> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
