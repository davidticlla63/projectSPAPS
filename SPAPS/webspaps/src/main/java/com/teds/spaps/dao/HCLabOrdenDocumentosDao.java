package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IHCLabOrdenDocumentosDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HCLabOrdenDocumentos;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class HCLabOrdenDocumentosDao extends
		DataAccessObjectJpa<HCLabOrdenDocumentos> implements
		IHCLabOrdenDocumentosDao {

	public HCLabOrdenDocumentosDao() {
		super(HCLabOrdenDocumentos.class);
	}

	@Override
	public HCLabOrdenDocumentos registrar(
			HCLabOrdenDocumentos hcLabOrdenDocumentos) {
		try {
			beginTransaction();
			hcLabOrdenDocumentos = create(hcLabOrdenDocumentos);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "HCLabOrdenDocumentos "
					+ hcLabOrdenDocumentos.getNombreArchivo());
			return hcLabOrdenDocumentos;
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
	public HCLabOrdenDocumentos modificar(
			HCLabOrdenDocumentos hcLabOrdenDocumentos) {
		try {
			beginTransaction();
			hcLabOrdenDocumentos = update(hcLabOrdenDocumentos);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"HCLabOrdenDocumentos "
							+ hcLabOrdenDocumentos.getNombreArchivo());
			return hcLabOrdenDocumentos;
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
	public boolean eliminar(HCLabOrdenDocumentos hcLabOrdenDocumentos) {
		try {
			beginTransaction();
			HCLabOrdenDocumentos bar = update(hcLabOrdenDocumentos);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"HCLabOrdenDocumentos "
							+ hcLabOrdenDocumentos.getNombreArchivo());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public HCLabOrdenDocumentos obtenerHCLabOrdenDocumentos(Integer id) {
		return findById(id);
	}

	@Override
	public HCLabOrdenDocumentos obtenerHCLabOrdenDocumentos(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerHCLabOrdenDocumentosOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerHCLabOrdenDocumentosOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerPorOrden(LabOrden orden) {
		return findAllActivosByParameter("orden", orden.getId());
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IHCLabOrdenDocumentosDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<HCLabOrdenDocumentos> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAllByEstadoOrderByAsc("AC");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDocumentoIdentidadDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<HCLabOrdenDocumentos> onComplete(String query) {
		return findAllActiveOtherTableAndParameterForNameSeguroAutoComplete(
				"descripcion", query, "estado", "AC", "empresaTrabajo");
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerPorCompaniaAutoComplete(
			String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<HCLabOrdenDocumentos> obtenerPorSucursalAutoComplete(
			String descripcion, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				descripcion, "estado", "AC", "sucursal", sucursal.getId());
	}

	@Override
	public boolean verificarRepetido(HCLabOrdenDocumentos hcLabOrdenDocumentos,
			Compania compania) {
		List<HCLabOrdenDocumentos> desgImagGrupos = obtenerPorCompania(compania);
		for (HCLabOrdenDocumentos desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getPathImagen().equalsIgnoreCase(
					hcLabOrdenDocumentos.getPathImagen())
					|| desgImagGrupo2.equals(hcLabOrdenDocumentos))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(
			HCLabOrdenDocumentos hcLabOrdenDocumentos, Compania compania) {
		List<HCLabOrdenDocumentos> desgImagGrupos = obtenerPorCompania(compania);
		for (HCLabOrdenDocumentos desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getPathImagen().equalsIgnoreCase(
					hcLabOrdenDocumentos.getPathImagen())
					&& !desgImagGrupo2.equals(hcLabOrdenDocumentos))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetido(HCLabOrdenDocumentos hcLabOrdenDocumentos,
			Sucursal sucursal) {
		List<HCLabOrdenDocumentos> desgImagGrupos = obtenerPorSucursal(sucursal);
		for (HCLabOrdenDocumentos desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getPathImagen().equalsIgnoreCase(
					hcLabOrdenDocumentos.getPathImagen())
					|| desgImagGrupo2.equals(hcLabOrdenDocumentos))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(
			HCLabOrdenDocumentos hcLabOrdenDocumentos, Sucursal sucursal) {
		List<HCLabOrdenDocumentos> desgImagGrupos = obtenerPorSucursal(sucursal);
		for (HCLabOrdenDocumentos desgImagGrupo2 : desgImagGrupos) {
			if (desgImagGrupo2.getPathImagen().equalsIgnoreCase(
					hcLabOrdenDocumentos.getPathImagen())
					&& !desgImagGrupo2.equals(hcLabOrdenDocumentos))
				return true;
		}
		return false;
	}

}
