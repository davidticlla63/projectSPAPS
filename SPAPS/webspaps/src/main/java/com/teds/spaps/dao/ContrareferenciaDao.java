/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IContrareferenciaDao;
import com.teds.spaps.interfaces.dao.ITransferenciaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Contrareferencia;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class ContrareferenciaDao extends DataAccessObjectJpa<Contrareferencia>
		implements IContrareferenciaDao {

	private @Inject ITransferenciaDao transferenciaDao;

	/**
	 * @param typeT
	 */
	public ContrareferenciaDao() {
		super(Contrareferencia.class);
	}

	@Override
	public Contrareferencia create(Contrareferencia contrareferencia) {
		return super.create(contrareferencia);
	}

	@Override
	public Contrareferencia update(Contrareferencia contrareferencia) {
		return super.update(contrareferencia);
	}

	@Override
	public Contrareferencia registrar(Contrareferencia contrareferencia) {
		try {
			beginTransaction();
			contrareferencia = create(contrareferencia);
			contrareferencia.getTransferencia().setEstado("AC");
			transferenciaDao.update(contrareferencia.getTransferencia());
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Contrareferencia "
					+ contrareferencia.toString());
			return contrareferencia;
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
	public Contrareferencia modificar(Contrareferencia contrareferencia) {
		try {
			beginTransaction();
			contrareferencia = update(contrareferencia);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Contrareferencia "
					+ contrareferencia.toString());
			return contrareferencia;
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
	public boolean eliminar(Contrareferencia contrareferencia) {
		try {
			contrareferencia.setEstado("RM");
			beginTransaction();
			Contrareferencia bar = update(contrareferencia);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Contrareferencia "
					+ contrareferencia.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IContrareferenciaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Contrareferencia> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IContrareferenciaDao#obtenerContrareferencia
	 * ( java.lang.Integer)
	 */
	@Override
	public Contrareferencia obtenerContrareferencia(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IContrareferenciaDao#
	 * obtenerContrareferenciaOrdenAscPorId()
	 */
	@Override
	public List<Contrareferencia> obtenerContrareferenciaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IContrareferenciaDao#
	 * obtenerContrareferenciaOrdenDescPorId()
	 */
	@Override
	public List<Contrareferencia> obtenerContrareferenciaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IContrareferenciaDao#obtenerPorCompania(com
	 * .teds. spaps.model.Compania)
	 */
	@Override
	public List<Contrareferencia> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IContrareferenciaDao#obtenerPorSucursal(com
	 * .teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Contrareferencia> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Contrareferencia> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania) {
		return findAllActivosByParameterTwo("historiaClinica", "compania",
				historiaClinica.getId(), compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IContrareferenciaDao#onComplete(java.lang.
	 * String)
	 */
	@Override
	public List<Contrareferencia> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
