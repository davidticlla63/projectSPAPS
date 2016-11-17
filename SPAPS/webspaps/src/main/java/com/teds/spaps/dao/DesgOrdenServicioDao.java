/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioImagExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServicioLabExamenDao;
import com.teds.spaps.interfaces.dao.IDesgOrdenServiciosDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DesgOrdenServicio;
import com.teds.spaps.model.DesgOrdenServicioImagExamen;
import com.teds.spaps.model.DesgOrdenServicioLabExamen;
import com.teds.spaps.model.DesgOrdenServicios;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DesgOrdenServicioDao extends
		DataAccessObjectJpa<DesgOrdenServicio> implements IDesgOrdenServicioDao {

	private @Inject IDesgOrdenServicioLabExamenDao desgOrdenServicioLabExamenDao;
	private @Inject IDesgOrdenServicioImagExamenDao desgOrdenServicioImagExamenDao;
	private @Inject IDesgOrdenServiciosDao desgOrdenServiciosDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	/**
	 * @param typeT
	 */
	public DesgOrdenServicioDao() {
		super(DesgOrdenServicio.class);
	}

	@Override
	public DesgOrdenServicio create(DesgOrdenServicio desgOrdenServicio) {
		return super.create(desgOrdenServicio);
	}

	@Override
	public DesgOrdenServicio update(DesgOrdenServicio desgOrdenServicio) {
		return super.update(desgOrdenServicio);
	}

	@Override
	public DesgOrdenServicio registrar(DesgOrdenServicio desgOrdenServicio) {
		try {
			beginTransaction();
			desgOrdenServicio = create(desgOrdenServicio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgOrdenServicio "
					+ desgOrdenServicio.toString());
			return desgOrdenServicio;
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
	public DesgOrdenServicio registrar(DesgOrdenServicio desgOrdenServicio,
			List<DesgOrdenServicioLabExamen> labExamenes,
			List<DesgOrdenServicioImagExamen> imagExamenes,
			List<DesgOrdenServicios> servicios) {
		try {
			beginTransaction();
			desgOrdenServicio = create(desgOrdenServicio);
			if (desgOrdenServicio.getId() > 0) {
				ParamSistemaIndices sistemaIndices = sistemaIndicesDao
						.obtenerPorCompania("OS",
								desgOrdenServicio.getCompania());
				sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
			}
			for (DesgOrdenServicios desgOrdenServicios : servicios) {
				desgOrdenServicios.setCompania(desgOrdenServicio.getCompania());
				desgOrdenServicios.setEstado(desgOrdenServicio.getEstado());
				desgOrdenServicios.setFechaModificacion(desgOrdenServicio
						.getFechaModificacion());
				desgOrdenServicios.setFechaRegistro(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicios.setOrdenServicio(desgOrdenServicio);
				desgOrdenServicios.setSucursal(desgOrdenServicio.getSucursal());
				desgOrdenServicios.setUsuarioRegistro(desgOrdenServicio
						.getUsuarioRegistro());
				desgOrdenServiciosDao.create(desgOrdenServicios);
			}
			for (DesgOrdenServicioLabExamen desgOrdenServicios : labExamenes) {
				desgOrdenServicios.setCompania(desgOrdenServicio.getCompania());
				desgOrdenServicios.setEstado(desgOrdenServicio.getEstado());
				desgOrdenServicios.setFechaModificacion(desgOrdenServicio
						.getFechaModificacion());
				desgOrdenServicios.setFechaRegistro(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicios.setOrdenServicio(desgOrdenServicio);
				desgOrdenServicios.setSucursal(desgOrdenServicio.getSucursal());
				desgOrdenServicios.setUsuarioRegistro(desgOrdenServicio
						.getUsuarioRegistro());
				desgOrdenServicioLabExamenDao.create(desgOrdenServicios);
			}
			for (DesgOrdenServicioImagExamen desgOrdenServicios : imagExamenes) {
				desgOrdenServicios.setCompania(desgOrdenServicio.getCompania());
				desgOrdenServicios.setEstado(desgOrdenServicio.getEstado());
				desgOrdenServicios.setFechaModificacion(desgOrdenServicio
						.getFechaModificacion());
				desgOrdenServicios.setFechaRegistro(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicios.setOrdenServicio(desgOrdenServicio);
				desgOrdenServicios.setSucursal(desgOrdenServicio.getSucursal());
				desgOrdenServicios.setUsuarioRegistro(desgOrdenServicio
						.getUsuarioRegistro());
				desgOrdenServicioImagExamenDao.create(desgOrdenServicios);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DesgOrdenServicio "
					+ desgOrdenServicio.toString());
			return desgOrdenServicio;
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
	public DesgOrdenServicio modificar(DesgOrdenServicio desgOrdenServicio,
			List<DesgOrdenServicioLabExamen> labExamenesSacar,
			List<DesgOrdenServicioLabExamen> labExamenesMeter,
			List<DesgOrdenServicioImagExamen> imagExamenesSacar,
			List<DesgOrdenServicioImagExamen> imagExamenesMeter,
			List<DesgOrdenServicios> serviciosSacar,
			List<DesgOrdenServicios> serviciosMeter) {
		try {
			beginTransaction();
			desgOrdenServicio = update(desgOrdenServicio);
			for (DesgOrdenServicioLabExamen desgOrdenServicios : labExamenesSacar) {
				desgOrdenServicioLabExamenDao.delete(desgOrdenServicios);
			}
			for (DesgOrdenServicioImagExamen desgOrdenServicios : imagExamenesSacar) {
				desgOrdenServicioImagExamenDao.delete(desgOrdenServicios);
			}
			for (DesgOrdenServicios desgOrdenServicios : serviciosSacar) {
				desgOrdenServiciosDao.delete(desgOrdenServicios);
			}
			for (DesgOrdenServicios desgOrdenServicios : serviciosMeter) {
				desgOrdenServicios.setId(null);
				desgOrdenServicios.setCompania(desgOrdenServicio.getCompania());
				desgOrdenServicios.setEstado(desgOrdenServicio.getEstado());
				desgOrdenServicios.setFechaModificacion(desgOrdenServicio
						.getFechaModificacion());
				desgOrdenServicios.setFechaRegistro(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicios.setOrdenServicio(desgOrdenServicio);
				desgOrdenServicios.setSucursal(desgOrdenServicio.getSucursal());
				desgOrdenServicios.setUsuarioRegistro(desgOrdenServicio
						.getUsuarioRegistro());
				desgOrdenServiciosDao.create(desgOrdenServicios);
			}
			for (DesgOrdenServicioLabExamen desgOrdenServicios : labExamenesMeter) {
				desgOrdenServicios.setId(null);
				desgOrdenServicios.setCompania(desgOrdenServicio.getCompania());
				desgOrdenServicios.setEstado(desgOrdenServicio.getEstado());
				desgOrdenServicios.setFechaModificacion(desgOrdenServicio
						.getFechaModificacion());
				desgOrdenServicios.setFechaRegistro(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicios.setOrdenServicio(desgOrdenServicio);
				desgOrdenServicios.setSucursal(desgOrdenServicio.getSucursal());
				desgOrdenServicios.setUsuarioRegistro(desgOrdenServicio
						.getUsuarioRegistro());
				desgOrdenServicioLabExamenDao.create(desgOrdenServicios);
			}
			for (DesgOrdenServicioImagExamen desgOrdenServicios : imagExamenesMeter) {
				desgOrdenServicios.setId(null);
				desgOrdenServicios.setCompania(desgOrdenServicio.getCompania());
				desgOrdenServicios.setEstado(desgOrdenServicio.getEstado());
				desgOrdenServicios.setFechaModificacion(desgOrdenServicio
						.getFechaModificacion());
				desgOrdenServicios.setFechaRegistro(desgOrdenServicio
						.getFechaRegistro());
				desgOrdenServicios.setOrdenServicio(desgOrdenServicio);
				desgOrdenServicios.setSucursal(desgOrdenServicio.getSucursal());
				desgOrdenServicios.setUsuarioRegistro(desgOrdenServicio
						.getUsuarioRegistro());
				desgOrdenServicioImagExamenDao.create(desgOrdenServicios);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "DesgOrdenServicio "
					+ desgOrdenServicio.toString());
			return desgOrdenServicio;
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
	public DesgOrdenServicio modificar(DesgOrdenServicio desgOrdenServicio) {
		try {
			beginTransaction();
			desgOrdenServicio = update(desgOrdenServicio);
			commitTransaction();
			return desgOrdenServicio;
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
	public boolean eliminar(DesgOrdenServicio desgOrdenServicio) {
		try {
			desgOrdenServicio.setEstado("RM");
			beginTransaction();
			DesgOrdenServicio bar = update(desgOrdenServicio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgOrdenServicio "
					+ desgOrdenServicio.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean eliminar(DesgOrdenServicio desgOrdenServicio,
			List<DesgOrdenServicioLabExamen> labExamenes,
			List<DesgOrdenServicioImagExamen> imagExamenes,
			List<DesgOrdenServicios> servicios) {
		try {
			desgOrdenServicio.setEstado("RM");
			beginTransaction();
			DesgOrdenServicio bar = update(desgOrdenServicio);
			for (DesgOrdenServicioLabExamen desgOrdenServicios : labExamenes) {
				desgOrdenServicios.setEstado(bar.getEstado());
				desgOrdenServicioLabExamenDao.update(desgOrdenServicios);
			}
			for (DesgOrdenServicioImagExamen desgOrdenServicios : imagExamenes) {
				desgOrdenServicios.setEstado(bar.getEstado());
				desgOrdenServicioImagExamenDao.update(desgOrdenServicios);
			}
			for (DesgOrdenServicios desgOrdenServicios : servicios) {
				desgOrdenServicios.setEstado(bar.getEstado());
				desgOrdenServiciosDao.update(desgOrdenServicios);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DesgOrdenServicio "
					+ desgOrdenServicio.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DesgOrdenServicio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#obtenerDesgOrdenServicio
	 * (java.lang.Integer)
	 */
	@Override
	public DesgOrdenServicio obtenerDesgOrdenServicio(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#
	 * obtenerDesgOrdenServicioOrdenAscPorId()
	 */
	@Override
	public List<DesgOrdenServicio> obtenerDesgOrdenServicioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#
	 * obtenerDesgOrdenServicioOrdenDescPorId()
	 */
	@Override
	public List<DesgOrdenServicio> obtenerDesgOrdenServicioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DesgOrdenServicio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DesgOrdenServicio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DesgOrdenServicio> obtenerPorCliente(Paciente cliente,
			Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"cliente", "compania", "AC", "AE", cliente.getId(),
				compania.getId());
	}

	@Override
	public List<DesgOrdenServicio> obtenerAll() {
		return findAllByParameterObjectFourOr("estado", "estado", "estado",
				"estado", "PE", "AE", "RE", "NR");
	}

	@Override
	public List<DesgOrdenServicio> obtenerPorCliente(Paciente cliente,
			Sucursal sucursal) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"cliente", "sucursal", "AC", "II", cliente.getId(),
				sucursal.getId());
	}

	@Override
	public List<DesgOrdenServicio> obtenerPorClientePendientes(
			Paciente cliente, Sucursal sucursal) {
		return findAllByParameterObjectThree("cliente", "sucursal", "estado",
				cliente.getId(), sucursal.getId(), "PE");
	}

	@Override
	public List<DesgOrdenServicio> obtenerPorClienteAprobadas(Paciente cliente,
			Sucursal sucursal) {
		return findAllByParameterObjectThree("cliente", "sucursal", "estado",
				cliente.getId(), sucursal.getId(), "AE");
	}

	@Override
	public List<DesgOrdenServicio> obtenerPorHC(
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectTwo("historiaClinica", "compania",
				historiaClinica.getId(), compania.getId());
	}

	@Override
	public List<DesgOrdenServicio> obtenerPorHC(
			HistoriaClinica historiaClinica, Sucursal sucursal) {
		return findAllByParameterObjectTwo("historiaClinica", "sucursal",
				historiaClinica.getId(), sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDesgOrdenServicioDao#onComplete(java.lang
	 * .String)
	 */
	@Override
	public List<DesgOrdenServicio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
