/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDetalleTipoEstudioDao;
import com.teds.spaps.interfaces.dao.IEstudioDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleTipoEstudio;
import com.teds.spaps.model.Estudio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class EstudioDao extends DataAccessObjectJpa<Estudio> implements
		IEstudioDao {

	private @Inject IDetalleTipoEstudioDao detalleTipoEstudioDao;

	/**
	 * @param typeT
	 */
	public EstudioDao() {
		super(Estudio.class);
	}

	@Override
	public Estudio create(Estudio estudio) {
		return super.create(estudio);
	}

	@Override
	public Estudio update(Estudio estudio) {
		return super.update(estudio);
	}

	@Override
	public Estudio registrar(Estudio estudio,
			List<DetalleTipoEstudio> listaDetalleTipoEstudio) {
		try {
			beginTransaction();
			estudio = create(estudio);
			for (DetalleTipoEstudio detalleTipoEstudio : listaDetalleTipoEstudio) {
				detalleTipoEstudio.setEstudio(estudio);
				detalleTipoEstudio.setFechaRegistro(estudio.getFechaRegistro());
				detalleTipoEstudio.setFechaModificacion(estudio
						.getFechaModificacion());
				detalleTipoEstudio.setEstado(estudio.getEstado());
				detalleTipoEstudio.setCompania(estudio.getCompania());
				detalleTipoEstudio.setSucursal(estudio.getSucursal());
				detalleTipoEstudio.setUsuarioRegistro(estudio
						.getUsuarioRegistro());
				detalleTipoEstudioDao.create(detalleTipoEstudio);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Estudio " + estudio.toString());
			return estudio;
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
	public Estudio modificar(Estudio estudio, List<DetalleTipoEstudio> sacar,
			List<DetalleTipoEstudio> meter) {
		try {
			beginTransaction();
			estudio = update(estudio);
			for (DetalleTipoEstudio detalleTipoEstudio : sacar) {
				detalleTipoEstudioDao.delete(detalleTipoEstudio);
			}
			sacar = new ArrayList<DetalleTipoEstudio>();
			sacar = detalleTipoEstudioDao.obtenerPorEstudio(estudio);
			System.out.println("verificacion si elimino = " + sacar.size());
			for (DetalleTipoEstudio detalleTipoEstudio1 : meter) {
				detalleTipoEstudio1.setEstudio(estudio);
				detalleTipoEstudio1.setFechaRegistro(new Date());
				detalleTipoEstudio1.setFechaModificacion(estudio
						.getFechaModificacion());
				detalleTipoEstudio1.setId(null);
				detalleTipoEstudio1.setSucursal(estudio.getSucursal());
				detalleTipoEstudio1.setCompania(estudio.getCompania());
				detalleTipoEstudio1.setUsuarioRegistro(estudio
						.getUsuarioRegistro());
				detalleTipoEstudio1.setEstado(estudio.getEstado());
				detalleTipoEstudioDao.create(detalleTipoEstudio1);
			}
			sacar = new ArrayList<DetalleTipoEstudio>();
			sacar = detalleTipoEstudioDao.obtenerPorEstudio(estudio);
			System.out.println("verificacion si actualizo = " + sacar.size());
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Estudio " + estudio.toString());
			return estudio;
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
	public boolean eliminar(Estudio estudio,
			List<DetalleTipoEstudio> detalleTipoEstudios) {
		try {
			estudio.setEstado("RM");
			beginTransaction();
			Estudio bar = update(estudio);
			for (DetalleTipoEstudio detalleTipoEstudio : detalleTipoEstudios) {
				detalleTipoEstudio.setEstado("RM");
				detalleTipoEstudioDao.update(detalleTipoEstudio);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Estudio " + estudio.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IEstudioDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Estudio> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEstudioDao#obtenerEstudio(java.lang.Integer
	 * )
	 */
	@Override
	public Estudio obtenerEstudio(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEstudioDao#obtenerEstudioOrdenAscPorId()
	 */
	@Override
	public List<Estudio> obtenerEstudioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEstudioDao#obtenerEstudioOrdenDescPorId()
	 */
	@Override
	public List<Estudio> obtenerEstudioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEstudioDao#obtenerPorCompania(com.teds.
	 * spaps.model.Compania)
	 */
	@Override
	public List<Estudio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEstudioDao#obtenerPorSucursal(com.teds.
	 * spaps.model.Sucursal)
	 */
	@Override
	public List<Estudio> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IEstudioDao#onComplete(java.lang.String)
	 */
	@Override
	public List<Estudio> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("nombre", query);
	}

}
