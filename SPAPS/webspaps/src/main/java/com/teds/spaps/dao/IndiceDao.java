/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IIndiceDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Indice;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class IndiceDao extends DataAccessObjectJpa<Indice> implements
		IIndiceDao {

	/**
	 * @param typeT
	 */
	public IndiceDao() {
		super(Indice.class);
	}

	@Override
	public Indice create(Indice indice) {
		return super.create(indice);
	}

	@Override
	public Indice update(Indice indice) {
		return super.update(indice);
	}

	@Override
	public Indice registrar(Indice indice) {
		try {
			beginTransaction();
			indice = create(indice);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Indice " + indice.toString());
			return indice;
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
	public Indice modificar(Indice indice) {
		try {
			beginTransaction();
			indice = update(indice);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Indice " + indice.toString());
			return indice;
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
	public boolean eliminar(Indice indice) {
		try {
			indice.setEstado("RM");
			beginTransaction();
			Indice bar = update(indice);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Indice " + indice.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Indice> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#obtenerIndice(java
	 * .lang.Integer)
	 */
	@Override
	public Indice obtenerIndice(Integer id) {
		return findById(id);
	}

	@Override
	public Indice obtenerIndice(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	@Override
	public Indice obtenerIndice(Compania compania) {
		return findByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#obtenerIndiceOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Indice> obtenerIndiceOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#
	 * obtenerIndiceOrdenDescPorId()
	 */
	@Override
	public List<Indice> obtenerIndiceOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<Indice> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public boolean verificarIndiceDeCompania(Compania compania) {
		return (long) findCount("id", "compania", compania.getId()) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Indice> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndiceDao#onComplete(java.lang.String
	 * )
	 */
	@Override
	public List<Indice> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

	@Override
	public List<Indice> obtenerPorCompania(String descripcion, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", descripcion, "estado", "AC", "compania",
				compania.getId());
	}

}
