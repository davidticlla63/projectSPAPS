/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.ITransferenciaDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Transferencia;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class TransferenciaDao extends DataAccessObjectJpa<Transferencia>
		implements ITransferenciaDao {

	/**
	 * @param typeT
	 */
	public TransferenciaDao() {
		super(Transferencia.class);
	}

	@Override
	public Transferencia create(Transferencia transferencia) {
		return super.create(transferencia);
	}

	@Override
	public Transferencia update(Transferencia transferencia) {
		return super.update(transferencia);
	}

	@Override
	public Transferencia registrar(Transferencia transferencia) {
		try {
			System.out.println("ingreso a registrar transferencia dao con "
					+ transferencia.getMotivo());
			beginTransaction();
			transferencia = create(transferencia);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Transferencia "
					+ transferencia.toString());
			return transferencia;
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
	public Transferencia modificar(Transferencia transferencia) {
		try {
			beginTransaction();
			transferencia = update(transferencia);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Transferencia "
					+ transferencia.toString());
			return transferencia;
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
	public boolean eliminar(Transferencia transferencia) {
		try {
			transferencia.setEstado("RM");
			beginTransaction();
			Transferencia bar = update(transferencia);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Transferencia "
					+ transferencia.toString());
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
	 * @see com.teds.spaps.interfaces.dao.ITransferenciaDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Transferencia> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITransferenciaDao#obtenerTransferencia(
	 * java.lang.Integer)
	 */
	@Override
	public Transferencia obtenerTransferencia(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITransferenciaDao#
	 * obtenerTransferenciaOrdenAscPorId()
	 */
	@Override
	public List<Transferencia> obtenerTransferenciaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.ITransferenciaDao#
	 * obtenerTransferenciaOrdenDescPorId()
	 */
	@Override
	public List<Transferencia> obtenerTransferenciaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITransferenciaDao#obtenerPorCompania(com
	 * .teds. spaps.model.Compania)
	 */
	@Override
	public List<Transferencia> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITransferenciaDao#obtenerPorSucursal(com
	 * .teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Transferencia> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	@Override
	public List<Transferencia> obtenerPorHC(HistoriaClinica historiaClinica,
			Compania compania) {
		return findAllByParameterObjectFourQueryOr("estado", "estado",
				"historiaClinica", "compania", "AC", "II",
				historiaClinica.getId(), compania.getId());
	}

	@Override
	public boolean verificarTransferencia(HistoriaClinica historiaClinica,
			Compania compania) {
		return (long) findCount("estado", "historiaClinica", "compania",
				historiaClinica.getId(), compania.getId()) > 0;
	}

	@Override
	public Transferencia obtenerTransferenciaParaContra(
			HistoriaClinica historiaClinica, Compania compania) {
		return findMaxTransferencia("id", "historiaClinica", "compania",
				historiaClinica.getId(), compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.ITransferenciaDao#onComplete(java.lang.
	 * String)
	 */
	@Override
	public List<Transferencia> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
