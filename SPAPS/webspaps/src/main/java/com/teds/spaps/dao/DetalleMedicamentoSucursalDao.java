/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao;
import com.teds.spaps.model.Antecedente;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamentoSucursal;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class DetalleMedicamentoSucursalDao extends
		DataAccessObjectJpa<DetalleMedicamentoSucursal> implements
		IDetalleMedicamentoSucursalDao {

	/**
	 * @param typeT
	 */
	public DetalleMedicamentoSucursalDao() {
		super(DetalleMedicamentoSucursal.class);
	}

	@Override
	public DetalleMedicamentoSucursal create(DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		return super.create(detalleMedicamentoSucursal);
	}

	@Override
	public DetalleMedicamentoSucursal update(DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		return super.update(detalleMedicamentoSucursal);
	}

	@Override
	public void delete(DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		super.deleteReal(detalleMedicamentoSucursal);
	}

	@Override
	public DetalleMedicamentoSucursal registrar(DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		try {
			beginTransaction();
			detalleMedicamentoSucursal = create(detalleMedicamentoSucursal);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DetalleMedicamentoSucursal "
					+ detalleMedicamentoSucursal.toString());
			return detalleMedicamentoSucursal;
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
	public boolean registrar(List<DetalleMedicamentoSucursal> antecedentes) {
		try {
			beginTransaction();
			for (DetalleMedicamentoSucursal detalleMedicamentoSucursal : antecedentes) {
				create(detalleMedicamentoSucursal);
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
	public DetalleMedicamentoSucursal modificar(DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		try {
			beginTransaction();
			detalleMedicamentoSucursal = update(detalleMedicamentoSucursal);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"DetalleMedicamentoSucursal " + detalleMedicamentoSucursal.toString());
			return detalleMedicamentoSucursal;
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
	public boolean eliminar(DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		try {
			detalleMedicamentoSucursal.setEstado("RM");
			beginTransaction();
			DetalleMedicamentoSucursal bar = update(detalleMedicamentoSucursal);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "DetalleMedicamentoSucursal "
					+ detalleMedicamentoSucursal.toString());
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
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<DetalleMedicamentoSucursal> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#
	 * obtenerDetalleMedicamentoSucursal(java.lang.Integer)
	 */
	@Override
	public DetalleMedicamentoSucursal obtenerDetalleMedicamentoSucursal(Integer id) {
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#
	 * obtenerDetalleMedicamentoSucursalOrdenAscPorId()
	 */
	@Override
	public List<DetalleMedicamentoSucursal> obtenerDetalleMedicamentoSucursalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#
	 * obtenerDetalleMedicamentoSucursalOrdenDescPorId()
	 */
	@Override
	public List<DetalleMedicamentoSucursal> obtenerDetalleMedicamentoSucursalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#obtenerPorCompania
	 * (com.teds. spaps.model.Compania)
	 */
	@Override
	public List<DetalleMedicamentoSucursal> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#obtenerPorSucursal
	 * (com.teds. spaps.model.Sucursal)
	 */
	@Override
	public List<DetalleMedicamentoSucursal> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}
	
	@Override
	public List<DetalleMedicamentoSucursal> obtenerPorSucursalRegistro(Sucursal sucursal) {
		return findAllActivosByParameter("sucursalRegistro", sucursal.getId());
	}

	@Override
	public List<DetalleMedicamentoSucursal> obtenerPorMedicamento(Medicamento medicamento) {
		return findAllActivosByParameter("medicamento",
				medicamento.getId());
	}

	@Override
	public boolean verificarRepetido(Sucursal sucursal,
			DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		List<DetalleMedicamentoSucursal> antecedentes = obtenerPorSucursal(sucursal);
		for (DetalleMedicamentoSucursal detalleMedicamentoSucursal2 : antecedentes) {
			if (detalleMedicamentoSucursal2.getSucursal().equals(detalleMedicamentoSucursal.getSucursal()))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean verificarRepetidoUpdate(Sucursal sucursal,
			DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		List<DetalleMedicamentoSucursal> antecedentes = obtenerPorSucursal(sucursal);
		for (DetalleMedicamentoSucursal detalleMedicamentoSucursal2 : antecedentes) {
			if(detalleMedicamentoSucursal2.equals(detalleMedicamentoSucursal))
				return false;
			if (detalleMedicamentoSucursal2.getSucursal().equals(detalleMedicamentoSucursal.getSucursal()))
				return true;
		}
		return false;
	}

	@Override
	public List<DetalleMedicamentoSucursal> obtenerPorAntecedente(
			Antecedente antecedente) {
		return findAllActivosByParameter("antecedente", antecedente.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao#onComplete(java.
	 * lang.String)
	 */
	@Override
	public List<DetalleMedicamentoSucursal> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
