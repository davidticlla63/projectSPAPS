package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DocumentoIdentidad;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class DocumentoIdentidadDao extends
		DataAccessObjectJpa<DocumentoIdentidad> implements
		IDocumentoIdentidadDao {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public DocumentoIdentidadDao() {
		super(DocumentoIdentidad.class);
	}

	@Override
	public DocumentoIdentidad create(DocumentoIdentidad documentoIdentidad) {
		return super.create(documentoIdentidad);
	}

	@Override
	public DocumentoIdentidad update(DocumentoIdentidad documentoIdentidad) {
		return super.update(documentoIdentidad);
	}

	@Override
	public DocumentoIdentidad registrar(DocumentoIdentidad DocumentoIdentidad) {
		try {
			beginTransaction();
			DocumentoIdentidad = create(DocumentoIdentidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DocumentoIdentidad "
					+ DocumentoIdentidad.getNombre());
			return DocumentoIdentidad;
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
	public DocumentoIdentidad modificar(DocumentoIdentidad DocumentoIdentidad) {
		try {
			beginTransaction();
			DocumentoIdentidad = update(DocumentoIdentidad);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"DocumentoIdentidad " + DocumentoIdentidad.getNombre());
			return DocumentoIdentidad;
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
	public boolean eliminar(DocumentoIdentidad DocumentoIdentidad) {
		try {
			setDelete(true);
			DocumentoIdentidad.setEstado("RM");
			beginTransaction();
			DocumentoIdentidad bar = update(DocumentoIdentidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ DocumentoIdentidad.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(DocumentoIdentidad documentoIdentidad,
			Compania compania) {
		List<DocumentoIdentidad> documentoIdentidads = obtenerPorCompania(compania);
		for (DocumentoIdentidad documentoIdentidad2 : documentoIdentidads) {
			if (documentoIdentidad2.getNombre().equalsIgnoreCase(
					documentoIdentidad.getNombre())
					|| documentoIdentidad2.equals(documentoIdentidad))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(
			DocumentoIdentidad documentoIdentidad, Compania compania) {
		List<DocumentoIdentidad> documentoIdentidads = obtenerPorCompania(compania);
		for (DocumentoIdentidad documentoIdentidad2 : documentoIdentidads) {
			if (documentoIdentidad2.equals(documentoIdentidad))
				return false;
			if (documentoIdentidad2.getNombre().equalsIgnoreCase(
					documentoIdentidad.getNombre())
					&& !documentoIdentidad2.equals(documentoIdentidad))
				return true;
		}
		return false;
	}

	@Override
	public DocumentoIdentidad obtenerDocumentoIdentidad(Integer id) {
		return findById(id);
	}

	@Override
	public DocumentoIdentidad obtenerDocumentoIdentidad(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<DocumentoIdentidad> obtenerDocumentoIdentidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<DocumentoIdentidad> obtenerDocumentoIdentidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<DocumentoIdentidad> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<DocumentoIdentidad> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DocumentoIdentidad> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DocumentoIdentidad> obtenerTodosActivosEInactivosPorOrdenAsc() {
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
	public List<DocumentoIdentidad> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("nombre", query);
	}

	@Override
	public List<DocumentoIdentidad> obtenerPorCompaniaAutoComplete(
			String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<DocumentoIdentidad> obtenerPorSucursalAutoComplete(
			String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

}
