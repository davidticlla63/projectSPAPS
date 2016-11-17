/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IAntecedenteDao;
import com.teds.spaps.interfaces.dao.IDetalleAntecedenteEspecialidadDao;
import com.teds.spaps.interfaces.dao.IDetalleTipoAntecedenteDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleAntecedenteEspecialidad;
import com.teds.spaps.model.DetalleTipoAntecedente;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class AntecedenteDao extends DataAccessObjectJpa<Antecedente> implements
		IAntecedenteDao {

	private @Inject IDetalleAntecedenteEspecialidadDao detalleAntecedenteEspecialidadDao;
	private @Inject IDetalleTipoAntecedenteDao detalleTipoAntecedenteDao;

	/**
	 * @param typeT
	 */
	public AntecedenteDao() {
		super(Antecedente.class);
	}

	@Override
	public Antecedente create(Antecedente antecedente) {
		return super.create(antecedente);
	}

	@Override
	public Antecedente update(Antecedente antecedente) {
		return super.update(antecedente);
	}

	@Override
	public Antecedente registrar(Antecedente antecedente) {
		try {
			beginTransaction();
			antecedente = create(antecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Antecedente "
					+ antecedente.toString());
			return antecedente;
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
	public Antecedente registrar(Antecedente antecedente,
			List<DetalleAntecedenteEspecialidad> detalle,
			List<DetalleTipoAntecedente> listaTipoAntecedente) {
		try {
			beginTransaction();
			antecedente = create(antecedente);
			for (DetalleTipoAntecedente detalleTipoAntecedente : listaTipoAntecedente) {
				detalleTipoAntecedente.setAntecedente(antecedente);
				detalleTipoAntecedente.setFechaRegistro(antecedente
						.getFechaRegistro());
				detalleTipoAntecedente.setFechaModificacion(antecedente
						.getFechaModificacion());
				detalleTipoAntecedente.setEstado(antecedente.getEstado());
				detalleTipoAntecedente.setCompania(antecedente.getCompania());
				detalleTipoAntecedente.setSucursal(antecedente.getSucursal());
				detalleTipoAntecedente.setUsuarioRegistro(antecedente
						.getUsuarioRegistro());
				detalleTipoAntecedenteDao.create(detalleTipoAntecedente);
			}
			for (DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad : detalle) {
				detalleAntecedenteEspecialidad.setAntecedente(antecedente);
				detalleAntecedenteEspecialidad.setEstado(antecedente
						.getEstado());
				detalleAntecedenteEspecialidad.setFechaRegistro(antecedente
						.getFechaRegistro());
				detalleAntecedenteEspecialidad.setFechaModificacion(antecedente
						.getFechaModificacion());
				detalleAntecedenteEspecialidad.setSucursal(antecedente
						.getSucursal());
				detalleAntecedenteEspecialidad.setCompania(antecedente
						.getCompania());
				detalleAntecedenteEspecialidad.setUsuarioRegistro(antecedente
						.getUsuarioRegistro());
				detalleAntecedenteEspecialidadDao
						.create(detalleAntecedenteEspecialidad);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Antecedente "
					+ antecedente.toString());
			return antecedente;
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
	public Antecedente modificar(Antecedente antecedente,
			List<DetalleAntecedenteEspecialidad> sacar,
			List<DetalleAntecedenteEspecialidad> meter,
			List<DetalleTipoAntecedente> antecedentesSacar,
			List<DetalleTipoAntecedente> antecedentesMeter) {
		try {
			beginTransaction();
			antecedente = update(antecedente);
			for (DetalleTipoAntecedente detalleTipoAntecedente : antecedentesSacar) {
				detalleTipoAntecedenteDao.delete(detalleTipoAntecedente);
			}
			for (DetalleTipoAntecedente detalleTipoAntecedente1 : antecedentesMeter) {
				detalleTipoAntecedente1.setAntecedente(antecedente);
				detalleTipoAntecedente1.setFechaRegistro(new Date());
				detalleTipoAntecedente1.setFechaModificacion(antecedente
						.getFechaModificacion());
				detalleTipoAntecedente1.setId(null);
				detalleTipoAntecedente1.setSucursal(antecedente.getSucursal());
				detalleTipoAntecedente1.setCompania(antecedente.getCompania());
				detalleTipoAntecedente1.setUsuarioRegistro(antecedente
						.getUsuarioRegistro());
				detalleTipoAntecedente1.setEstado(antecedente.getEstado());
				detalleTipoAntecedenteDao.create(detalleTipoAntecedente1);
			}
			for (DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad : sacar) {
				detalleAntecedenteEspecialidadDao
						.delete(detalleAntecedenteEspecialidad);
			}
			for (DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad1 : meter) {
				detalleAntecedenteEspecialidad1.setAntecedente(antecedente);
				detalleAntecedenteEspecialidad1.setFechaRegistro(new Date());
				detalleAntecedenteEspecialidad1
						.setFechaModificacion(antecedente
								.getFechaModificacion());
				detalleAntecedenteEspecialidad1.setId(null);
				detalleAntecedenteEspecialidad1.setSucursal(antecedente
						.getSucursal());
				detalleAntecedenteEspecialidad1.setCompania(antecedente
						.getCompania());
				detalleAntecedenteEspecialidad1.setUsuarioRegistro(antecedente
						.getUsuarioRegistro());
				detalleAntecedenteEspecialidad1.setEstado(antecedente
						.getEstado());
				detalleAntecedenteEspecialidadDao
						.create(detalleAntecedenteEspecialidad1);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Antecedente "
					+ antecedente.toString());
			return antecedente;
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
	public boolean eliminar(Antecedente antecedente,
			List<DetalleTipoAntecedente> detalleTipoAntecedentes,
			List<DetalleAntecedenteEspecialidad> detalle) {
		try {
			antecedente.setEstado("RM");
			beginTransaction();
			Antecedente bar = update(antecedente);
			for (DetalleTipoAntecedente detalleTipoAntecedente : detalleTipoAntecedentes) {
				detalleTipoAntecedente.setEstado("RM");
				detalleTipoAntecedenteDao.update(detalleTipoAntecedente);
			}
			for (DetalleAntecedenteEspecialidad detalleAntecedenteEspecialidad : detalle) {
				detalleAntecedenteEspecialidad.setEstado("RM");
				detalleAntecedenteEspecialidad.setAntecedente(bar);
				detalleAntecedenteEspecialidadDao
						.update(detalleAntecedenteEspecialidad);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Antecedente "
					+ antecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IAntecedenteDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Antecedente> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IAntecedenteDao#obtenerAntecedente(java
	 * .lang.Integer)
	 */
	@Override
	public Antecedente obtenerAntecedente(Integer id) {
		return findById(id);
	}

	@Override
	public Antecedente obtenerAntecedente(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IAntecedenteDao#obtenerAntecedenteOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Antecedente> obtenerAntecedenteOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IAntecedenteDao#
	 * obtenerAntecedenteOrdenDescPorId()
	 */
	@Override
	public List<Antecedente> obtenerAntecedenteOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IAntecedenteDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<Antecedente> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public boolean verificarRepetido(String descripcion, Sucursal sucursal) {
		List<Antecedente> antecedentes = obtenerPorSucursal(sucursal);
		for (Antecedente antecedente : antecedentes) {
			if (antecedente.getDescripcion().equalsIgnoreCase(descripcion))
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IAntecedenteDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Antecedente> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IAntecedenteDao#onComplete(java.lang.String
	 * )
	 */
	@Override
	public List<Antecedente> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	@Override
	public List<Antecedente> obtenerPorCompania(String descripcion,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

}
