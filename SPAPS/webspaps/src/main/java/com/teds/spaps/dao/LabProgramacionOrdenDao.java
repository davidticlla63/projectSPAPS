package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.ILabOrdenImagDao;
import com.teds.spaps.interfaces.dao.ILabProgramacionOrdenDao;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.LabProgramacionOrden;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

@Stateless
public class LabProgramacionOrdenDao extends
		DataAccessObjectJpa<LabProgramacionOrden> implements
		ILabProgramacionOrdenDao {

	private @Inject ILabOrdenDao ordenDao;
	private @Inject ILabOrdenImagDao ordenImagDao;

	public LabProgramacionOrdenDao() {
		super(LabProgramacionOrden.class);
	}

	@Override
	public LabProgramacionOrden create(LabProgramacionOrden departamento) {
		return super.create(departamento);
	}

	@Override
	public LabProgramacionOrden update(LabProgramacionOrden departamento) {
		return super.update(departamento);
	}

	@Override
	public LabProgramacionOrden registrar(LabProgramacionOrden departamento) {
		try {
			LabOrden orden = new LabOrden();
			LabOrdenImag ordenImag = new LabOrdenImag();
			if (departamento.getTipoOrden().equals("LABORATORIO")) {
				orden = departamento.getOrden();
				orden.setListOrdenDetalle(new ArrayList<LabOrdenDetalle>());
				orden.setEstado("VI");
				orden.setFechaModificacion(new Date());
			} else {
				ordenImag = departamento.getOrdenImag();
				ordenImag
						.setListOrdenDetalle(new ArrayList<LabOrdenDetalleImag>());
				ordenImag.setEstado("VI");
				ordenImag.setFechaModificacion(new Date());
			}
			beginTransaction();
			departamento = create(departamento);
			if (departamento.getTipoOrden().equals("LABORATORIO")) {
				orden = ordenDao.update(orden);

			} else {
				ordenImag = ordenImagDao.update(ordenImag);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabProgramacionOrden "
					+ departamento.getObservacion());
			return departamento;
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
	public LabProgramacionOrden completarRegistro(
			LabProgramacionOrden departamento) {
		try {
			LabOrden orden = new LabOrden();
			LabOrdenImag ordenImag = new LabOrdenImag();
			if (departamento.getTipoOrden().equals("LABORATORIO")) {
				orden = departamento.getOrden();
				orden.setListOrdenDetalle(new ArrayList<LabOrdenDetalle>());
				orden.setEstado("PR");
				orden.setFechaModificacion(new Date());
			} else {
				ordenImag = departamento.getOrdenImag();
				ordenImag
						.setListOrdenDetalle(new ArrayList<LabOrdenDetalleImag>());
				ordenImag.setEstado("PR");
				ordenImag.setFechaModificacion(new Date());
			}
			beginTransaction();
			departamento = update(departamento);
			if (departamento.getTipoOrden().equals("LABORATORIO")) {
				orden = ordenDao.update(orden);

			} else {
				ordenImag = ordenImagDao.update(ordenImag);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "LabProgramacionOrden "
					+ departamento.getObservacion());
			return departamento;
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
	public LabProgramacionOrden modificar(LabProgramacionOrden departamento) {
		try {
			beginTransaction();
			departamento = update(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"LabProgramacionOrden " + departamento.getObservacion());
			return departamento;
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
	public boolean eliminar(LabProgramacionOrden departamento) {
		try {
			beginTransaction();
			LabProgramacionOrden bar = update(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"LabProgramacionOrden " + departamento.getObservacion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public LabProgramacionOrden obtenerLabProgramacionOrden(Integer id) {
		return findById(id);
	}

	@Override
	public List<LabProgramacionOrden> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActiveParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<LabProgramacionOrden> obtenerPorSucursal(String estado,
			Date fecha, Personal medico, Sucursal sucursal) {
		return findAllActiveThwoParameter("estado", estado, "fecha", fecha,
				"medico", medico.getId(), "sucursal", sucursal.getId(),
				"fecha_modificacion", "DESC");
	}

	@Override
	public List<LabProgramacionOrden> obtenerPorSucursal(String estado,
			Personal medico, Sucursal sucursal) {
		return findAllActiveThwoParameter("estado", estado, "medico",
				medico.getId(), "sucursal", sucursal.getId(), "fecha", "DESC");
	}

	@Override
	public List<LabProgramacionOrden> obtenerPorSucursalYFechas(
			Date fechaIni, Date fechaFin, Personal medico, Sucursal sucursal) {
		return findAllActiveForThwoDatesAndThwoObject("medico", medico.getId(),
				"sucursal", sucursal.getId(), "fecha", fechaIni, "fecha",
				fechaFin, "fecha", "DESC");

	}

}
