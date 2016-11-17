package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IViaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Via;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class ViaDao extends DataAccessObjectJpa<Via> implements IViaDao {

	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public ViaDao() {
		super(Via.class);
	}

	@Override
	public Via create(Via via) {
		return super.create(via);
	}

	@Override
	public Via update(Via via) {
		return super.update(via);
	}

	@Override
	public Via registrar(Via via) {
		try {
			beginTransaction();
			via = create(via);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Via " + via.toString());
			return via;
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
	public Via modificar(Via via) {
		try {
			beginTransaction();
			via = update(via);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"Via " + via.toString());
			return via;
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
	public boolean eliminar(Via via) {
		try {
			setDelete(true);
			via.setEstado("RM");
			beginTransaction();
			Via bar = update(via);
			commitTransaction();
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta",
					"Via " + via.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public Via obtenerVia(Integer id) {
		return findById(id);
	}

	@Override
	public Via obtenerVia(String descripcion, Compania compania) {
		return findByParameterObjectThree("estado", "descripcionn", "compania",
				"AC", descripcion, compania.getId());
	}

	@Override
	public List<Via> obtenerViaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Via> obtenerViaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Via> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	@Override
	public boolean verificarRepetido(Via via, Compania compania) {
		List<Via> vias = obtenerPorCompania(compania);
		for (Via via2 : vias) {
			if (via2.getDescripcion().equalsIgnoreCase(via.getDescripcion())
					|| via2.equals(via))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Via via, Compania compania) {
		List<Via> vias = obtenerPorCompania(compania);
		for (Via via2 : vias) {
			if (via2.equals(via))
				return false;
			if (via2.getDescripcion().equalsIgnoreCase(via.getDescripcion()))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IViaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Via> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<Via> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
