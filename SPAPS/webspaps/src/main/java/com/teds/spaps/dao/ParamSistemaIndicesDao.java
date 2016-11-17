package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class ParamSistemaIndicesDao extends
		DataAccessObjectJpa<ParamSistemaIndices> implements
		IParamSistemaIndicesDao {

	public ParamSistemaIndicesDao() {
		super(ParamSistemaIndices.class);
	}

	@Override
	public ParamSistemaIndices create(ParamSistemaIndices paramSistemaIndices) {
		return super.create(paramSistemaIndices);
	}

	@Override
	public ParamSistemaIndices update(ParamSistemaIndices paramSistemaIndices) {
		return super.update(paramSistemaIndices);
	}

	@Override
	public ParamSistemaIndices increment(ParamSistemaIndices paramSistemaIndices) {
		paramSistemaIndices
				.setCorrelativo(paramSistemaIndices.getCorrelativo() + 1);
		paramSistemaIndices.setFechaModificacion(new Date());
		return super.update(paramSistemaIndices);
	}

	@Override
	public ParamSistemaIndices registrar(ParamSistemaIndices paramSistemaIndices) {
		try {
			beginTransaction();
			paramSistemaIndices = create(paramSistemaIndices);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "ParamSistemaIndices "
					+ paramSistemaIndices.getNombreOperacion());
			return paramSistemaIndices;
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
	public void registrar(String usuario, Compania compania) {
		try {
			beginTransaction();
			List<ParamSistemaIndices> lisParamSistemaIndices = new ArrayList<ParamSistemaIndices>();
			lisParamSistemaIndices.add(new ParamSistemaIndices("ADMISION",
					"AD", "P", 8, 1, "AC", compania, new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices(
					"ORDENES LABORATORIO", "OL", "O", 8, 1, "AC", compania,
					new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices(
					"ORDENES IMAGENOLOGIA", "OI", "I", 8, 1, "AC", compania,
					new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices(
					"HISTORIAS CLINICAS", "HC", "HC", 8, 1, "AC", compania,
					new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices(
					"ORDENES DE CONSULTA", "OC", "OC", 8, 1, "AC", compania,
					new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices("COTIZACIONES",
					"CO", "CO", 8, 1, "AC", compania, new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices("COTIZACIONES",
					"PE", "PE", 8, 1, "AC", compania, new Date(), usuario));
			lisParamSistemaIndices.add(new ParamSistemaIndices(
					"ORDENES DE SERVICIOS", "OS", "OS", 8, 1, "AC", compania,
					new Date(), usuario));
			for (ParamSistemaIndices paramSistemaIndices2 : lisParamSistemaIndices) {
				paramSistemaIndices2 = create(paramSistemaIndices2);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "ParamSistemaIndices "
					+ lisParamSistemaIndices.size());

		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
		}
	}

	@Override
	public ParamSistemaIndices modificar(ParamSistemaIndices paramSistemaIndices) {
		try {
			beginTransaction();
			paramSistemaIndices = update(paramSistemaIndices);
			commitTransaction();
			FacesUtil.infoMessage(
					"Modificación Correcta",
					"ParamSistemaIndices "
							+ paramSistemaIndices.getNombreOperacion());
			return paramSistemaIndices;
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
	public boolean eliminar(ParamSistemaIndices paramSistemaIndices) {
		try {
			beginTransaction();
			paramSistemaIndices.setEstado("RM");
			paramSistemaIndices.setFechaModificacion(new Date());
			ParamSistemaIndices bar = update(paramSistemaIndices);
			commitTransaction();
			FacesUtil.infoMessage(
					"Eliminación Correcta",
					"ParamSistemaIndices "
							+ paramSistemaIndices.getNombreOperacion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public ParamSistemaIndices obtenerParamSistemaIndices(Integer id) {
		return findById(id);
	}

	@Override
	public List<ParamSistemaIndices> obtenerParamSistemaIndicesOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<ParamSistemaIndices> obtenerParamSistemaIndicesOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<ParamSistemaIndices> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public ParamSistemaIndices obtenerPorCompania(String key, Compania compania) {
		return findByQuerys("key", key, "compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<ParamSistemaIndices> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

}
