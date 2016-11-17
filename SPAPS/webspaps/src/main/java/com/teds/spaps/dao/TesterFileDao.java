package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITesterFileDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.TesterFile;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class TesterFileDao extends DataAccessObjectJpa<TesterFile> implements
		ITesterFileDao {

	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public TesterFileDao() {
		super(TesterFile.class);
	}

	@Override
	public TesterFile create(TesterFile testerFile) {
		return super.create(testerFile);
	}

	@Override
	public TesterFile update(TesterFile testerFile) {
		return super.update(testerFile);
	}

	@Override
	public TesterFile registrar(TesterFile testerFile) {
		try {
			beginTransaction();
			testerFile = create(testerFile);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "TesterFile "
					+ testerFile.getNombre());
			return testerFile;
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
	public TesterFile modificar(TesterFile testerFile) {
		try {
			beginTransaction();
			testerFile = update(testerFile);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "TesterFile "
						+ testerFile.getNombre());
			return testerFile;
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
	public boolean eliminar(TesterFile testerFile) {
		try {
			setDelete(true);
			// testerFile.setEstado("RM");
			beginTransaction();
			TesterFile bar = update(testerFile);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "TesterFile "
					+ testerFile.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public TesterFile obtenerTesterFile(Integer id) {
		return findById(id);
	}

	@Override
	public List<TesterFile> obtenerTesterFileOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<TesterFile> obtenerTesterFileOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<TesterFile> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITesterFileDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<TesterFile> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<TesterFile> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("nombre", query);
	}

}
