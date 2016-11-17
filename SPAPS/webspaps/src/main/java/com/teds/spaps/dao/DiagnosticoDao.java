/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDetalleDiagnosticoEspecialidadDao;
import com.teds.spaps.interfaces.dao.IDetalleTipoDiagnosticoDao;
import com.teds.spaps.interfaces.dao.IDiagnosticoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleDiagnosticoEspecialidad;
import com.teds.spaps.model.DetalleTipoDiagnostico;
import com.teds.spaps.model.Diagnostico;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DiagnosticoDao extends DataAccessObjectJpa<Diagnostico> implements
		IDiagnosticoDao {

	private @Inject IDetalleTipoDiagnosticoDao detalleTipoDiagnosticoDao;
	private @Inject IDetalleDiagnosticoEspecialidadDao detalleDiagnosticoEspecialidadDao;

	/**
	 * @param typeT
	 */
	public DiagnosticoDao() {
		super(Diagnostico.class);
	}

	@Override
	public Diagnostico create(Diagnostico diagnostico) {
		return super.create(diagnostico);
	}

	@Override
	public Diagnostico update(Diagnostico diagnostico) {
		return super.update(diagnostico);
	}

	@Override
	public Diagnostico registrar(Diagnostico diagnostico) {
		try {
			beginTransaction();
			diagnostico = create(diagnostico);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Diagnostico "
					+ diagnostico.toString());
			return diagnostico;
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
	public Diagnostico registrar(
			Diagnostico diagnostico,
			List<DetalleTipoDiagnostico> listaDetalleTipoDiagnostico,
			List<DetalleDiagnosticoEspecialidad> listaDetalleDiagnosticoEspecialidad) {
		try {
			beginTransaction();
			diagnostico = create(diagnostico);
			for (DetalleTipoDiagnostico detalleTipoDiagnostico : listaDetalleTipoDiagnostico) {
				detalleTipoDiagnostico.setDiagnostico(diagnostico);
				detalleTipoDiagnostico.setFechaRegistro(diagnostico
						.getFechaRegistro());
				detalleTipoDiagnostico.setFechaModificacion(diagnostico
						.getFechaModificacion());
				detalleTipoDiagnostico.setEstado(diagnostico.getEstado());
				detalleTipoDiagnostico.setCompania(diagnostico.getCompania());
				detalleTipoDiagnostico.setSucursal(diagnostico.getSucursal());
				detalleTipoDiagnostico.setUsuarioRegistro(diagnostico
						.getUsuarioRegistro());
				detalleTipoDiagnosticoDao.create(detalleTipoDiagnostico);
			}
			for (DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad : listaDetalleDiagnosticoEspecialidad) {
				detalleDiagnosticoEspecialidad.setDiagnostico(diagnostico);
				detalleDiagnosticoEspecialidad.setEstado(diagnostico
						.getEstado());
				detalleDiagnosticoEspecialidad.setFechaRegistro(diagnostico
						.getFechaRegistro());
				detalleDiagnosticoEspecialidad.setFechaModificacion(diagnostico
						.getFechaModificacion());
				detalleDiagnosticoEspecialidad.setSucursal(diagnostico
						.getSucursal());
				detalleDiagnosticoEspecialidad.setCompania(diagnostico
						.getCompania());
				detalleDiagnosticoEspecialidad.setUsuarioRegistro(diagnostico
						.getUsuarioRegistro());
				detalleDiagnosticoEspecialidadDao
						.create(detalleDiagnosticoEspecialidad);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Diagnostico "
					+ diagnostico.toString());
			return diagnostico;
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
	public Diagnostico modificar(Diagnostico diagnostico,
			List<DetalleTipoDiagnostico> sacar,
			List<DetalleTipoDiagnostico> meter,
			List<DetalleDiagnosticoEspecialidad> diagnosticoSacar,
			List<DetalleDiagnosticoEspecialidad> diagnosticoMeter) {
		try {
			beginTransaction();
			diagnostico = update(diagnostico);
			for (DetalleTipoDiagnostico detalleTipoDiagnostico : sacar) {
				detalleTipoDiagnosticoDao.delete(detalleTipoDiagnostico);
			}
			for (DetalleTipoDiagnostico detalleTipoDiagnostico1 : meter) {
				detalleTipoDiagnostico1.setDiagnostico(diagnostico);
				detalleTipoDiagnostico1.setFechaRegistro(new Date());
				detalleTipoDiagnostico1.setFechaModificacion(diagnostico
						.getFechaModificacion());
				detalleTipoDiagnostico1.setId(null);
				detalleTipoDiagnostico1.setSucursal(diagnostico.getSucursal());
				detalleTipoDiagnostico1.setCompania(diagnostico.getCompania());
				detalleTipoDiagnostico1.setUsuarioRegistro(diagnostico
						.getUsuarioRegistro());
				detalleTipoDiagnostico1.setEstado(diagnostico.getEstado());
				detalleTipoDiagnosticoDao.create(detalleTipoDiagnostico1);
			}
			for (DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad : diagnosticoSacar) {
				detalleDiagnosticoEspecialidadDao
						.delete(detalleDiagnosticoEspecialidad);
			}
			for (DetalleDiagnosticoEspecialidad detalleAntecedenteEspecialidad1 : diagnosticoMeter) {
				detalleAntecedenteEspecialidad1.setDiagnostico(diagnostico);
				detalleAntecedenteEspecialidad1.setFechaRegistro(new Date());
				detalleAntecedenteEspecialidad1
						.setFechaModificacion(diagnostico
								.getFechaModificacion());
				detalleAntecedenteEspecialidad1.setId(null);
				detalleAntecedenteEspecialidad1.setSucursal(diagnostico
						.getSucursal());
				detalleAntecedenteEspecialidad1.setCompania(diagnostico
						.getCompania());
				detalleAntecedenteEspecialidad1.setUsuarioRegistro(diagnostico
						.getUsuarioRegistro());
				detalleAntecedenteEspecialidad1.setEstado(diagnostico
						.getEstado());
				detalleDiagnosticoEspecialidadDao
						.create(detalleAntecedenteEspecialidad1);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Diagnostico "
					+ diagnostico.toString());
			return diagnostico;
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
	public boolean eliminar(Diagnostico diagnostico,
			List<DetalleTipoDiagnostico> detalleTipoDiagnosticos,
			List<DetalleDiagnosticoEspecialidad> detalleDiagnosticoEspecialidads) {
		try {
			diagnostico.setEstado("RM");
			beginTransaction();
			Diagnostico bar = update(diagnostico);
			for (DetalleTipoDiagnostico detalleTipoDiagnostico : detalleTipoDiagnosticos) {
				detalleTipoDiagnostico.setEstado("RM");
				detalleTipoDiagnosticoDao.update(detalleTipoDiagnostico);
			}
			for (DetalleDiagnosticoEspecialidad detalleDiagnosticoEspecialidad : detalleDiagnosticoEspecialidads) {
				detalleDiagnosticoEspecialidad.setEstado("RM");
				detalleDiagnosticoEspecialidad.setDiagnostico(bar);
				detalleDiagnosticoEspecialidadDao
						.update(detalleDiagnosticoEspecialidad);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Diagnostico "
					+ diagnostico.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	@Override
	public boolean verificarRepetido(Diagnostico diagnostico, Compania compania) {
		List<Diagnostico> diagnosticos = obtenerPorCompania(compania);
		for (Diagnostico diagnostico2 : diagnosticos) {
			if (diagnostico2.getDescripcion().equalsIgnoreCase(
					diagnostico.getDescripcion())
					|| diagnostico2.equals(diagnostico))
				return true;
		}
		return false;
	}

	@Override
	public boolean verificarRepetidoUpdate(Diagnostico diagnostico,
			Compania compania) {
		List<Diagnostico> diagnosticos = obtenerPorCompania(compania);
		for (Diagnostico diagnostico2 : diagnosticos) {
			if (diagnostico2.getDescripcion().equalsIgnoreCase(
					diagnostico.getDescripcion())
					&& !diagnostico2.equals(diagnostico))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDiagnosticoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Diagnostico> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoDao#obtenerDiagnostico(java
	 * .lang.Integer )
	 */
	@Override
	public Diagnostico obtenerDiagnostico(Integer id) {
		return findById(id);
	}

	@Override
	public Diagnostico obtenerDiagnostico(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoDao#obtenerDiagnosticoOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Diagnostico> obtenerDiagnosticoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDiagnosticoDao#
	 * obtenerDiagnosticoOrdenDescPorId()
	 */
	@Override
	public List<Diagnostico> obtenerDiagnosticoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<Diagnostico> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Diagnostico> obtenerPorCompaniaAutoComplete(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Diagnostico> obtenerPorSucursalAutoComplete(String descripcion,
			Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Diagnostico> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDiagnosticoDao#onComplete(java.lang.String
	 * )
	 */
	@Override
	public List<Diagnostico> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
