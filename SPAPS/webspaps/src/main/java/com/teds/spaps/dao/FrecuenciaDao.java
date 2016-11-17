package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IFrecuenciaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Frecuencia;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class FrecuenciaDao extends DataAccessObjectJpa<Frecuencia> implements
		IFrecuenciaDao {

	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public FrecuenciaDao() {
		super(Frecuencia.class);
	}

	@Override
	public Frecuencia create(Frecuencia frecuencia) {
		return super.create(frecuencia);
	}

	@Override
	public Frecuencia update(Frecuencia frecuencia) {
		return super.update(frecuencia);
	}

	@Override
	public Frecuencia registrar(Frecuencia frecuencia) {
		try {
			beginTransaction();
			frecuencia = create(frecuencia);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Frecuencia "
					+ frecuencia.toString());
			return frecuencia;
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
	public Frecuencia modificar(Frecuencia frecuencia) {
		try {
			beginTransaction();
			frecuencia = update(frecuencia);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "Frecuencia "
						+ frecuencia.toString());
			return frecuencia;
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
	public boolean eliminar(Frecuencia frecuencia) {
		try {
			setDelete(true);
			frecuencia.setEstado("RM");
			beginTransaction();
			Frecuencia bar = update(frecuencia);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "Frecuencia "
					+ frecuencia.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public Frecuencia obtenerFrecuencia(Integer id) {
		return findById(id);
	}

	@Override
	public Frecuencia obtenerFrecuencia(String descripcion, Compania compania) {
		return findByParameterObjectThree("estado", "descripcionn", "compania",
				"AC", descripcion, compania.getId());
	}

	@Override
	public List<Frecuencia> obtenerFrecuenciaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Frecuencia> obtenerFrecuenciaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Frecuencia> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	@Override
	public boolean verificarRepetido(Frecuencia frecuencia, Compania compania) {
		List<Frecuencia> frecuencias = obtenerPorCompania(compania);
		for (Frecuencia frecuencia2 : frecuencias) {
			if (frecuencia2.getDescripcion().equalsIgnoreCase(
					frecuencia.getDescripcion())
					|| frecuencia2.equals(frecuencia))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Frecuencia frecuencia,
			Compania compania) {
		List<Frecuencia> frecuencias = obtenerPorCompania(compania);
		for (Frecuencia frecuencia2 : frecuencias) {
			if (frecuencia2.equals(frecuencia))
				return false;
			if (frecuencia2.getDescripcion().equalsIgnoreCase(
					frecuencia.getDescripcion()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IFrecuenciaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Frecuencia> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<Frecuencia> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
