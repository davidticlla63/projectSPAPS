/**
 * @author ANITA
 */
package com.teds.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao;
import com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao;
import com.teds.spaps.interfaces.dao.IMedicamentoDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.DetalleMedicamentoPresentacion;
import com.teds.spaps.model.DetalleMedicamentoSucursal;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;

/**
 * @author ANITA
 *
 */
@Stateless
public class MedicamentoDao extends DataAccessObjectJpa<Medicamento> implements
		IMedicamentoDao {

	private @Inject IDetalleMedicamentoSucursalDao detalleMedicamentoSucursalDao;
	private @Inject IDetalleMedicamentoPresentacionDao detalleMedicamentoPresentacionDao;

	/**
	 * @param typeT
	 */
	public MedicamentoDao() {
		super(Medicamento.class);
	}

	@Override
	public Medicamento create(Medicamento medicamento) {
		return super.create(medicamento);
	}

	@Override
	public Medicamento update(Medicamento medicamento) {
		return super.update(medicamento);
	}

	@Override
	public Medicamento registrar(Medicamento medicamento) {
		try {
			beginTransaction();
			medicamento = create(medicamento);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Medicamento "
					+ medicamento.toString());
			return medicamento;
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
	public Medicamento registrar(Medicamento medicamento,
			List<DetalleMedicamentoSucursal> detalle,
			List<DetalleMedicamentoPresentacion> presentaciones) {
		try {
			beginTransaction();
			medicamento = create(medicamento);
			for (DetalleMedicamentoSucursal detalleMedicamentoSucursal : detalle) {
				detalleMedicamentoSucursal.setMedicamento(medicamento);
				detalleMedicamentoSucursal.setFechaRegistro(medicamento
						.getFechaRegistro());
				detalleMedicamentoSucursal.setFechaModificacion(medicamento
						.getFechaModificacion());
				detalleMedicamentoSucursal.setEstado(medicamento.getEstado());
				detalleMedicamentoSucursal.setCompania(medicamento
						.getCompania());
				detalleMedicamentoSucursal.setSucursalRegistro(medicamento
						.getSucursal());
				detalleMedicamentoSucursal.setUsuarioRegistro(medicamento
						.getUsuarioRegistro());
				detalleMedicamentoSucursalDao
						.create(detalleMedicamentoSucursal);
			}
			for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion : presentaciones) {
				detalleMedicamentoPresentacion.setMedicamento(medicamento);
				detalleMedicamentoPresentacion.setFechaRegistro(medicamento
						.getFechaRegistro());
				detalleMedicamentoPresentacion.setFechaModificacion(medicamento
						.getFechaModificacion());
				detalleMedicamentoPresentacion.setEstado(medicamento
						.getEstado());
				detalleMedicamentoPresentacion.setCompania(medicamento
						.getCompania());
				detalleMedicamentoPresentacion.setSucursalRegistro(medicamento
						.getSucursal());
				detalleMedicamentoPresentacion.setUsuarioRegistro(medicamento
						.getUsuarioRegistro());
				detalleMedicamentoPresentacionDao
						.create(detalleMedicamentoPresentacion);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Medicamento "
					+ medicamento.toString());
			return medicamento;
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
	public Medicamento modificar(Medicamento medicamento,
			List<DetalleMedicamentoSucursal> medicamentosSacar,
			List<DetalleMedicamentoSucursal> medicamentosMeter,
			List<DetalleMedicamentoPresentacion> presentacionesSacar,
			List<DetalleMedicamentoPresentacion> presentacionesMeter) {
		try {
			beginTransaction();
			medicamento = update(medicamento);
			for (DetalleMedicamentoSucursal detalleMedicamentoSucursal : medicamentosSacar) {
				detalleMedicamentoSucursalDao
						.delete(detalleMedicamentoSucursal);
			}
			for (DetalleMedicamentoSucursal detalleMedicamentoSucursal1 : medicamentosMeter) {
				detalleMedicamentoSucursal1.setMedicamento(medicamento);
				detalleMedicamentoSucursal1.setFechaRegistro(new Date());
				detalleMedicamentoSucursal1.setFechaModificacion(medicamento
						.getFechaModificacion());
				detalleMedicamentoSucursal1.setId(null);
				detalleMedicamentoSucursal1.setSucursalRegistro(medicamento
						.getSucursal());
				detalleMedicamentoSucursal1.setCompania(medicamento
						.getCompania());
				detalleMedicamentoSucursal1.setUsuarioRegistro(medicamento
						.getUsuarioRegistro());
				detalleMedicamentoSucursal1.setEstado(medicamento.getEstado());
				detalleMedicamentoSucursalDao
						.create(detalleMedicamentoSucursal1);
			}
			for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion : presentacionesSacar) {
				detalleMedicamentoPresentacionDao
						.delete(detalleMedicamentoPresentacion);
			}
			for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion1 : presentacionesMeter) {
				detalleMedicamentoPresentacion1.setMedicamento(medicamento);
				detalleMedicamentoPresentacion1.setFechaRegistro(new Date());
				detalleMedicamentoPresentacion1
						.setFechaModificacion(medicamento
								.getFechaModificacion());
				detalleMedicamentoPresentacion1.setId(null);
				detalleMedicamentoPresentacion1.setSucursalRegistro(medicamento
						.getSucursal());
				detalleMedicamentoPresentacion1.setCompania(medicamento
						.getCompania());
				detalleMedicamentoPresentacion1.setUsuarioRegistro(medicamento
						.getUsuarioRegistro());
				detalleMedicamentoPresentacion1.setEstado(medicamento
						.getEstado());
				detalleMedicamentoPresentacionDao
						.create(detalleMedicamentoPresentacion1);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Medicamento "
					+ medicamento.toString());
			return medicamento;
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
	public boolean eliminar(Medicamento medicamento,
			List<DetalleMedicamentoSucursal> detalle,
			List<DetalleMedicamentoPresentacion> presentaciones) {
		try {
			medicamento.setEstado("RM");
			beginTransaction();
			medicamento = update(medicamento);
			for (DetalleMedicamentoSucursal detalleMedicamentoSucursal : detalle) {
				detalleMedicamentoSucursal.setMedicamento(medicamento);
				detalleMedicamentoSucursal.setEstado("RM");
				detalleMedicamentoSucursalDao
						.update(detalleMedicamentoSucursal);
			}
			for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion : presentaciones) {
				detalleMedicamentoPresentacion.setMedicamento(medicamento);
				detalleMedicamentoPresentacion.setEstado("RM");
				detalleMedicamentoPresentacionDao
						.update(detalleMedicamentoPresentacion);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Medicamento "
					+ medicamento.toString());
			return medicamento != null ? true : false;
		} catch (Exception e) {
			rollbackTransaction();
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IMedicamentoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Medicamento> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMedicamentoDao#obtenerMedicamento(java
	 * .lang.Integer)
	 */
	@Override
	public Medicamento obtenerMedicamento(Integer id) {
		return findById(id);
	}

	@Override
	public Medicamento obtenerMedicamentoNG(String nombreGenerico,
			String presentacion, Compania compania) {
		return findByParameterObjectFour("estado", "nombreGenerico",
				"presentacion", "compania", "AC", nombreGenerico, presentacion,
				compania.getId());
	}

	@Override
	public Medicamento obtenerMedicamentoNC(String nombreComercial,
			String presentacion, Compania compania) {
		return findByParameterObjectFour("estado", "nombreComercial",
				"presentacion", "compania", "AC", nombreComercial,
				presentacion, compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMedicamentoDao#obtenerMedicamentoOrdenAscPorId
	 * ()
	 */
	@Override
	public List<Medicamento> obtenerMedicamentoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IMedicamentoDao#
	 * obtenerMedicamentoOrdenDescPorId()
	 */
	@Override
	public List<Medicamento> obtenerMedicamentoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMedicamentoDao#obtenerPorCompania(com.
	 * teds. spaps.model.Compania)
	 */
	@Override
	public List<Medicamento> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	@Override
	public List<Medicamento> obtenerAutoCompleteNG(String nombreGenerico,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"nombreGenerico", nombreGenerico, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Medicamento> obtenerAutoCompleteNC(String nombreComercial,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"nombreComercial", nombreComercial, "estado", "AC", "compania",
				compania.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMedicamentoDao#obtenerPorSucursal(com.
	 * teds. spaps.model.Sucursal)
	 */
	@Override
	public List<Medicamento> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.dao.IMedicamentoDao#onComplete(java.lang.String
	 * )
	 */
	@Override
	public List<Medicamento> onComplete(String query) {
		return findAllAndParameterForNameAutoComplete("descripcion", query);
	}

}
