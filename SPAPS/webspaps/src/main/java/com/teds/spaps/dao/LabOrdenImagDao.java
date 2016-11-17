package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.interfaces.dao.ILabOrdenDetalleImagDao;
import com.teds.spaps.interfaces.dao.ILabOrdenImagDao;
import com.teds.spaps.interfaces.dao.ILabOrdenSubDetalleImagDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.LabExamenDetalleImag;
import com.teds.spaps.model.LabOrdenDetalleImag;
import com.teds.spaps.model.LabOrdenImag;
import com.teds.spaps.model.LabOrdenSubDetalleImag;
import com.teds.spaps.model.LabValoresRef;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.Time;

@Stateless
public class LabOrdenImagDao extends DataAccessObjectJpa<LabOrdenImag>
		implements ILabOrdenImagDao {

	private @Inject ILabOrdenDetalleImagDao ordenDetalleDao;
	private @Inject ILabOrdenSubDetalleImagDao ordenSubDetalleDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	public LabOrdenImagDao() {
		super(LabOrdenImag.class);
	}

	@Override
	public LabOrdenImag create(LabOrdenImag labOrden) {
		return super.create(labOrden);
	}

	@Override
	public LabOrdenImag update(LabOrdenImag labOrden) {
		return super.update(labOrden);
	}

	@Override
	public LabOrdenImag registrar(LabOrdenImag orden) {
		try {
			ParamSistemaIndices sistemaIndices = sistemaIndicesDao
					.obtenerPorCompania("OI", orden.getCompania());
			beginTransaction();
			orden.setCodigo(sistemaIndices.getPrefijo()
					+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
							sistemaIndices.getCorrelativo()));

			orden = create(orden);
			sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
			for (LabOrdenDetalleImag labExamenDetalle : orden
					.getListOrdenDetalle()) {
				labExamenDetalle.setOrden(orden);
				labExamenDetalle.setFechaRegistro(new Date());
				ordenDetalleDao.create(labExamenDetalle);
				for (LabExamenDetalleImag detalle : labExamenDetalle
						.getLabExamen().getListExamenDetalle()) {
					LabOrdenSubDetalleImag subDetalle = new LabOrdenSubDetalleImag();
					subDetalle.setCompania(orden.getCompania());
					subDetalle.setSucursal(orden.getSucursal());
					subDetalle.setParametros(detalle.getParametros());
					subDetalle.setDescripcion(detalle.getDescripcion());
					subDetalle.setLabel(detalle.isLabel());
					subDetalle.setOrden(orden);
					subDetalle.setOrdenDetalle(labExamenDetalle);
					subDetalle.setUnidaMedida(detalle.getUnidaMedida());

					subDetalle = ordenSubDetalleDao.create(subDetalle);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"LabOrdenImag " + orden.toString());
			return orden;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			System.err.println("Error en " + e.getStackTrace());
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public LabOrdenImag registrar(LabOrdenImag orden,
			List<LabOrdenDetalleImag> listExamenDetalles) {
		try {
			beginTransaction();
			ParamSistemaIndices sistemaIndices = sistemaIndicesDao
					.obtenerPorCompania("OI", orden.getCompania());
			orden.setCodigo(sistemaIndices.getPrefijo()
					+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
							sistemaIndices.getCorrelativo()));

			orden = create(orden);
			sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
			for (LabOrdenDetalleImag labExamenDetalle : listExamenDetalles) {
				labExamenDetalle.setOrden(orden);
				labExamenDetalle.setFechaRegistro(new Date());
				ordenDetalleDao.create(labExamenDetalle);
				for (LabExamenDetalleImag detalle : labExamenDetalle
						.getLabExamen().getListExamenDetalle()) {
					LabOrdenSubDetalleImag subDetalle = new LabOrdenSubDetalleImag();
					subDetalle.setCompania(orden.getCompania());
					subDetalle.setSucursal(orden.getSucursal());
					subDetalle.setParametros(detalle.getParametros());
					subDetalle.setDescripcion(detalle.getDescripcion());
					subDetalle.setLabel(detalle.isLabel());
					subDetalle.setOrden(orden);
					subDetalle.setOrdenDetalle(labExamenDetalle);
					subDetalle.setUnidaMedida(detalle.getUnidaMedida());

					subDetalle = ordenSubDetalleDao.create(subDetalle);
				}
			}
			commitTransaction();
			return orden;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println(cause);
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			System.err.println("Error en " + e.getStackTrace());
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public LabOrdenImag modificar(LabOrdenImag orden) {
		try {
			orden.setListOrdenDetalle(new ArrayList<>());
			beginTransaction();
			orden = update(orden);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"LabOrdenImag " + orden.toString());
			return orden;
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
	public LabOrdenImag registrarResultados(LabOrdenImag orden,
			List<LabOrdenDetalleImag> listOrdenDetalles) {
		try {
			orden.setListOrdenDetalle(new ArrayList<>());
			beginTransaction();
			orden = update(orden);
			for (LabOrdenDetalleImag ordenDetalle : listOrdenDetalles) {
				// System.out.println("Examen: "+ordenDetalle.getDescripcion());
				ordenDetalle = ordenDetalleDao.update(ordenDetalle);
				for (LabOrdenSubDetalleImag subDetalle : ordenDetalle
						.getListOrdenSubDetalle()) {
					// System.out.println("detalle : "+subDetalle.getDescripcion()+" Resultado :"+subDetalle.getResultado());
					subDetalle = ordenSubDetalleDao.update(subDetalle);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Actualizacion Correcto", "LabOrdenImag "
					+ orden.toString());
			return orden;
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
	public LabOrdenImag registrarResultadosDetalle(
			LabOrdenDetalleImag ordenDetalle) {
		try {
			beginTransaction();
			ordenDetalle = ordenDetalleDao.update(ordenDetalle);
			for (LabOrdenSubDetalleImag subDetalle : ordenDetalle
					.getListOrdenSubDetalle()) {
				// System.out.println("detalle : "+subDetalle.getDescripcion()+" Resultado :"+subDetalle.getResultado());
				subDetalle = ordenSubDetalleDao.update(subDetalle);
			}
			commitTransaction();
			FacesUtil.infoMessage("Actualizacion Correcto", "LabOrdenImag "
					+ ordenDetalle.getOrden().toString());
			return ordenDetalle.getOrden();
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
	public LabOrdenImag modificar(LabOrdenImag orden,
			List<LabOrdenDetalleImag> listExamenDetalles) {
		try {
			orden.setListOrdenDetalle(new ArrayList<>());
			beginTransaction();
			orden = update(orden);
			ordenSubDetalleDao.deleteDetail(orden);
			ordenDetalleDao.deleteDetail(orden);
			for (LabOrdenDetalleImag labOrdenDetalle : listExamenDetalles) {
				LabOrdenDetalleImag detalle = new LabOrdenDetalleImag();
				detalle.setOrden(orden);
				detalle.setFechaRegistro(labOrdenDetalle.getFechaRegistro());
				detalle.setCompania(labOrdenDetalle.getCompania());
				detalle.setDescripcion(labOrdenDetalle.getDescripcion());
				detalle.setEstado(labOrdenDetalle.getEstado());
				detalle.setLabExamen(labOrdenDetalle.getLabExamen());
				detalle.setParametros(labOrdenDetalle.getParametros());
				detalle.setSucursal(labOrdenDetalle.getSucursal());
				detalle.setUsuarioRegistro(labOrdenDetalle.getUsuarioRegistro());
				detalle.setFechaModificacion(new Date());
				ordenDetalleDao.create(detalle);
				for (LabExamenDetalleImag subDetalle1 : labOrdenDetalle
						.getLabExamen().getListExamenDetalle()) {
					LabOrdenSubDetalleImag subDetalle = new LabOrdenSubDetalleImag();
					subDetalle.setCompania(orden.getCompania());
					subDetalle.setSucursal(orden.getSucursal());
					subDetalle.setParametros(subDetalle1.getParametros());
					subDetalle.setDescripcion(subDetalle1.getDescripcion());
					subDetalle.setOrdenDetalle(detalle);
					subDetalle.setLabel(subDetalle1.isLabel());
					subDetalle.setOrden(orden);
					subDetalle.setUnidaMedida(subDetalle1.getUnidaMedida());

					subDetalle = ordenSubDetalleDao.create(subDetalle);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "LabOrdenImag "
					+ orden.toString());
			return orden;
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
	public boolean eliminar(LabOrdenImag labOrden) {
		try {
			beginTransaction();
			LabOrdenImag bar = update(labOrden);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabOrdenImag "
					+ labOrden.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean eliminarOrden(LabOrdenImag orden) {
		try {
			beginTransaction();
			orden.setEstado("RM");
			LabOrdenImag bar = update(orden);
			for (LabOrdenDetalleImag ordenDetalle : orden.getListOrdenDetalle()) {
				ordenDetalle.setEstado("RM");
				ordenDetalleDao.update(ordenDetalle);
				for (LabOrdenSubDetalleImag ordenSubDetalle : ordenDetalle
						.getListOrdenSubDetalle()) {
					ordenSubDetalle.setEstado("RM");
					ordenSubDetalleDao.update(ordenSubDetalle);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"LabOrden " + orden.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			System.out.println("error eliminar orden = " + e.getMessage());
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	private String obtenerValorReferencia(LabOrdenImag orden,
			LabExamenDetalle detalle) {

		for (LabValoresRef valoresRef : detalle.getListValoresReferencia()) {
			orden.setSucursal(sucursalDao.obtenerSucursal(orden.getSucursal()
					.getId()));
			System.out.println(valoresRef.getEdad().equals(
					Time.obtenerTipoEdad(orden.getPaciente()
							.getFechaNacimiento()))
					+ " "
					+ valoresRef.getSexo()
					+ " "
					+ valoresRef.getCiudad().getNombre()
					+ " "
					+ orden.getPaciente().getFechaNacimiento());
			if (valoresRef.getCiudad().getId().intValue() == orden
					.getSucursal().getCiudad().getId().intValue()
					&& valoresRef.getEdad().equals("N")
					&& valoresRef.getSexo().equals("N")) {
				return valoresRef.getParametros();
			}
			if (valoresRef.getCiudad().getId().intValue() == orden
					.getSucursal().getCiudad().getId().intValue()
					&& valoresRef.getEdad().equals(
							Time.obtenerTipoEdad(orden.getPaciente()
									.getFechaNacimiento()))
					&& valoresRef.getSexo().equals("N")) {
				return valoresRef.getParametros();
			}
			if (valoresRef.getCiudad().equals(orden.getSucursal().getCiudad())
					&& valoresRef.getEdad().equals("N")
					&& valoresRef.getSexo().equals(
							orden.getPaciente().getSexo())) {
				return valoresRef.getParametros();
			}
			if (valoresRef.getCiudad().getId().intValue() == orden
					.getSucursal().getCiudad().getId().intValue()
					&& valoresRef.getEdad().equals(
							Time.obtenerTipoEdad(orden.getPaciente()
									.getFechaNacimiento()))
					&& valoresRef.getSexo().equals(
							orden.getPaciente().getSexo())) {
				return valoresRef.getParametros();
			}

		}
		return "";
	}

	@Override
	public LabOrdenImag obtenerLabOrdenImag(Integer id) {
		return findById(id);
	}

	@Override
	public LabOrdenImag obtenerLabOrdenImag(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabOrdenImag> obtenerLabOrdenImagOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenImag> obtenerLabOrdenImagOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrdenImag> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabOrdenImag> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabOrdenImag> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabOrdenImag> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania, Personal personal) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"medico", personal.getId(), "fechaRegistro", "desc");
	}

	@Override
	public List<LabOrdenImag> obtenerParaHC(Paciente paciente,
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectFour("paciente", "historiaClinica",
				"compania", "estado", paciente.getId(),
				historiaClinica.getId(), compania.getId(), "AC");
	}

	@Override
	public List<LabOrdenImag> obtenerPorCompania(String nombre,
			Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<LabOrdenImag> obtenerDePaciente(Paciente paciente,
			Compania compania, Sucursal sucursal) {
		return findAllByParameterObjectFour("paciente", "compania", "estado",
				"sucursal", paciente.getId(), compania.getId(), "AP",
				sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenImagDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabOrdenImag> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.teds.spaps.interfaces.ILabOrdenImagDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabOrdenImag> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenImagDao#obtenerAllActivos()
	 */
	@Override
	public List<LabOrdenImag> obtenerAllActivos() {
		// TODO Auto-generated method stub
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public Integer findActiveCodigoMax(Compania compania) {
		Object obj = findActiveMax("correlativo", "compania", compania.getId());
		System.out.println("codigo : " + obj);
		if (obj == null) {
			return 1;
		} else {
			return (int) obj + 1;
		}
	}

	@Override
	public List<LabOrdenImag> obtenerPorSucursal(String estado, Date fecha,
			Personal medico, Sucursal sucursal) {
		return findAllActiveThwoParameter("estado", estado, "fecha", fecha,
				"medico", medico.getId(), "sucursal", sucursal.getId(),
				"fecha_modificacion", "DESC");
	}

	@Override
	public List<LabOrdenImag> obtenerPorSucursal(String estado,
			Personal medico, Sucursal sucursal) {
		return findAllActiveThwoParameter("estado", estado, "medico",
				medico.getId(), "sucursal", sucursal.getId(), "fecha", "DESC");
	}

	@Override
	public List<LabOrdenImag> obtenerProFechas(Sucursal sucursal,
			Date fechaini, Date fechafin) {
		return findAllActiveForThwoDatesAndThwoObject("sucursal",
				sucursal.getId(), "fechaRegistro", fechaini, "fechaRegistro",
				fechafin);
	}

	@Override
	public List<LabOrdenImag> obtenerProFechasYEstado(Sucursal sucursal,
			Date fechaini, Date fechafin, String estado) {
		return findAllActiveForThwoDatesAndThwoObject("sucursal",
				sucursal.getId(), "fechaRegistro", fechaini, "fechaRegistro",
				fechafin, "id", "DESC", "estado", estado);
	}
}
