package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IUnidadVecinalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.UnidadVecinal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class UnidadVecinalDao extends DataAccessObjectJpa<UnidadVecinal>
		implements IUnidadVecinalDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public UnidadVecinalDao() {
		super(UnidadVecinal.class);
	}

	@Override
	public UnidadVecinal create(UnidadVecinal unidadVecinal) {
		return super.create(unidadVecinal);
	}

	@Override
	public UnidadVecinal update(UnidadVecinal unidadVecinal) {
		return super.update(unidadVecinal);
	}

	@Override
	public UnidadVecinal registrar(UnidadVecinal UnidadVecinal) {
		try {
			beginTransaction();
			UnidadVecinal = create(UnidadVecinal);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "UnidadVecinal "
					+ UnidadVecinal.getNumero());
			return UnidadVecinal;
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
	public UnidadVecinal modificar(UnidadVecinal UnidadVecinal) {
		try {
			beginTransaction();
			UnidadVecinal = update(UnidadVecinal);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "UnidadVecinal "
						+ UnidadVecinal.getNumero());
			return UnidadVecinal;
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
	public boolean eliminar(UnidadVecinal UnidadVecinal) {
		try {
			setDelete(true);
			UnidadVecinal.setEstado("RM");
			beginTransaction();
			UnidadVecinal bar = update(UnidadVecinal);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "UnidadVecinal "
					+ UnidadVecinal.getNumero());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public UnidadVecinal obtenerUnidadVecinal(Integer id) {
		return findById(id);
	}

	@Override
	public UnidadVecinal obtenerUnidadVecinal(String numero) {
		return findByParameter("numero", numero);
	}

	@Override
	public List<UnidadVecinal> obtenerUnidadVecinalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<UnidadVecinal> obtenerUnidadVecinalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<UnidadVecinal> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<UnidadVecinal> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IUnidadVecinalDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<UnidadVecinal> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<UnidadVecinal> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("numero", query);
	}

	@Override
	public List<UnidadVecinal> obtenerPorCompaniaAutoComplete(String numero,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("numero",
				numero, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<UnidadVecinal> obtenerPorSucursalAutoComplete(String numero,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("numero",
				numero, "estado", "AC", "sucursal", sucursal.getId());
	}

}
