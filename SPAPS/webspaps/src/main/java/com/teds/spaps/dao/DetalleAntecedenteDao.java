/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleAntecedente;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleAntecedenteDao extends
		DataAccessObjectJpa<DetalleAntecedente> implements
		IDetalleAntecedenteDao {

	/**
	 * @param typeT
	 */
	public DetalleAntecedenteDao() {
		super(DetalleAntecedente.class);
	}

	@Override
	public DetalleAntecedente create(DetalleAntecedente detalleAntecedente) {
		return super.create(detalleAntecedente);
	}

	@Override
	public DetalleAntecedente update(DetalleAntecedente detalleAntecedente) {
		return super.update(detalleAntecedente);
	}

	@Override
	public void delete(DetalleAntecedente detalleAntecedente) {
		super.deleteReal(detalleAntecedente);
	}

	@Override
	public DetalleAntecedente registrar(DetalleAntecedente detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = create(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleAntecedente "
					+ detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean registrar(List<DetalleAntecedente> antecedentes) {
		try {
			beginTransaction();
			for (DetalleAntecedente detalleAntecedente : antecedentes) {
				create(detalleAntecedente);
			}
			commitTransaction();
			return true;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public DetalleAntecedente modificar(DetalleAntecedente detalleAntecedente) {
		try {
			beginTransaction();
			detalleAntecedente = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleAntecedente " + detalleAntecedente.toString());
			return detalleAntecedente;
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
	public boolean eliminar(DetalleAntecedente detalleAntecedente) {
		try {
			detalleAntecedente.setEstado("RM");
			beginTransaction();
			DetalleAntecedente bar = update(detalleAntecedente);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleAntecedente "
					+ detalleAntecedente.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleAntecedente> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#
	 * obtenerDetalleAntecedente(java.lang.Integer)
	 */
	@Override
	public DetalleAntecedente obtenerDetalleAntecedente(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#
	 * obtenerDetalleAntecedenteOrdenAscPorId()
	 */
	@Override
	public List<DetalleAntecedente> obtenerDetalleAntecedenteOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#
	 * obtenerDetalleAntecedenteOrdenDescPorId()
	 */
	@Override
	public List<DetalleAntecedente> obtenerDetalleAntecedenteOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleAntecedente> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleAntecedente> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<DetalleAntecedente> obtenerPorHM(HistoriaClinica historiaClinica) {
		return findAllActivosByParameter("historiaClinica",
				historiaClinica.getId());
	}

	@Override
	public List<DetalleAntecedente> obtenerPorHC(
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	@Override
	public boolean verificarRepetido(HistoriaClinica historiaClinica,
			DetalleAntecedente detalleAntecedente) {
		List<DetalleAntecedente> antecedentes = obtenerPorHM(historiaClinica);
		for (DetalleAntecedente detalleAntecedente2 : antecedentes) {
			if (detalleAntecedente2.getAntecedente().equals(
					detalleAntecedente.getAntecedente()))
				return true;
		}
		return false;
	}

	@Override
	public List<DetalleAntecedente> obtenerPorAntecedente(
			Antecedente antecedente) {
		return findAllActivosByParameter("antecedente", antecedente.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleAntecedenteDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleAntecedente> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
