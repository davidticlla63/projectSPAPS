package com.teds.spaps.dao;

import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.teds.spaps.enums.Sexo;
import com.teds.spaps.enums.TipoSeleccion;
import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.ILabOrdenDetalleDao;
import com.teds.spaps.interfaces.dao.ILabOrdenSubDetalleDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.LabExamenDetalle;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.LabOrdenDetalle;
import com.teds.spaps.model.LabOrdenSubDetalle;
import com.teds.spaps.model.LabValoresRef;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Personal;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.Time;

@Stateless
public class LabOrdenDao extends DataAccessObjectJpa<LabOrden> implements
		ILabOrdenDao {

	private @Inject ILabOrdenDetalleDao ordenDetalleDao;
	private @Inject ILabOrdenSubDetalleDao ordenSubDetalleDao;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	public LabOrdenDao() {
		super(LabOrden.class);
	}

	@Override
	public LabOrden create(LabOrden labOrden) {
		return super.create(labOrden);
	}

	@Override
	public LabOrden update(LabOrden labOrden) {
		return super.update(labOrden);
	}

	@Override
	public LabOrden registrar(LabOrden orden) {
		try {
			ParamSistemaIndices sistemaIndices = sistemaIndicesDao
					.obtenerPorCompania("OL", orden.getCompania());
			beginTransaction();
			orden.setCodigo(sistemaIndices.getPrefijo()
					+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
							sistemaIndices.getCorrelativo()));

			orden = create(orden);
			sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
			for (LabOrdenDetalle labExamenDetalle : orden.getListOrdenDetalle()) {
				labExamenDetalle.setOrden(orden);
				labExamenDetalle.setFechaRegistro(new Date());
				ordenDetalleDao.create(labExamenDetalle);
				for (LabExamenDetalle detalle : labExamenDetalle.getLabExamen()
						.getListExamenDetalle()) {
					LabOrdenSubDetalle subDetalle = new LabOrdenSubDetalle();
					subDetalle.setCompania(orden.getCompania());
					subDetalle.setSucursal(orden.getSucursal());
					subDetalle.setParametros(detalle.getParametros());
					subDetalle.setDescripcion(detalle.getDescripcion());
					subDetalle.setLabel(detalle.isLabel());
					subDetalle.setOrden(orden);
					subDetalle.setOrdenDetalle(labExamenDetalle);
					subDetalle.setUnidaMedida(detalle.getUnidaMedida());
					subDetalle.setTipoValor(detalle.getTipoValor());
					if (detalle.isLabel()) {
						subDetalle.setParametros(detalle.getDescripcion());
					} else {
						if (detalle.getListValoresReferencia().size() == 0) {
							subDetalle.setParametros(detalle.getParametros());
						} else {
							LabValoresRef labValoresRef = valorReferencia(
									orden, detalle);
							if (labValoresRef != null) {
								subDetalle.setValoresRef(labValoresRef);
								subDetalle.setParametros(labValoresRef
										.getParametros());
								subDetalle.setValorFinal(labValoresRef
										.getValorFinal());
								subDetalle.setValorInicial(labValoresRef
										.getValorInicial());
								subDetalle.setTextoMultiple(labValoresRef
										.getTextoMultiple());
								subDetalle.setValor(labValoresRef.getValor());
							}
						}

					}
					subDetalle = ordenSubDetalleDao.create(subDetalle);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"LabOrden " + orden.toString());
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
	public LabOrden registrar(LabOrden orden,
			List<LabOrdenDetalle> listExamenDetalles) {
		try {
			beginTransaction();
			ParamSistemaIndices sistemaIndices = sistemaIndicesDao
					.obtenerPorCompania("OL", orden.getCompania());
			orden.setCodigo(sistemaIndices.getPrefijo()
					+ Time.rellendarCeros(sistemaIndices.getNumeroDigitos(),
							sistemaIndices.getCorrelativo()));

			orden = create(orden);
			sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
			for (LabOrdenDetalle labExamenDetalle : listExamenDetalles) {
				labExamenDetalle.setOrden(orden);
				labExamenDetalle.setFechaRegistro(new Date());
				ordenDetalleDao.create(labExamenDetalle);
				for (LabExamenDetalle detalle : labExamenDetalle.getLabExamen()
						.getListExamenDetalle()) {
					LabOrdenSubDetalle subDetalle = new LabOrdenSubDetalle();
					subDetalle.setCompania(orden.getCompania());
					subDetalle.setSucursal(orden.getSucursal());
					subDetalle.setParametros(detalle.getParametros());
					subDetalle.setDescripcion(detalle.getDescripcion());
					subDetalle.setLabel(detalle.isLabel());
					subDetalle.setOrden(orden);
					subDetalle.setTipoValor(detalle.getTipoValor());
					subDetalle.setOrdenDetalle(labExamenDetalle);
					subDetalle.setUnidaMedida(detalle.getUnidaMedida());
					if (detalle.isLabel()) {
						subDetalle.setParametros(detalle.getDescripcion());
					} else {
						if (detalle.getListValoresReferencia().size() == 0) {
							subDetalle.setParametros(detalle.getParametros());
						} else {

							LabValoresRef labValoresRef = valorReferencia(
									orden, detalle);
							if (labValoresRef != null) {
								subDetalle.setValoresRef(labValoresRef);
								subDetalle.setParametros(labValoresRef
										.getParametros());
								subDetalle.setValorFinal(labValoresRef
										.getValorFinal());
								subDetalle.setValorInicial(labValoresRef
										.getValorInicial());
								subDetalle.setTextoMultiple(labValoresRef
										.getTextoMultiple());
								subDetalle.setValor(labValoresRef.getValor());
							}
							// String resultado = obtenerValorReferencia(orden,
							// detalle);
							// if (resultado.trim().length() > 0
							// || resultado.trim().equals("")) {
							// subDetalle.setParametros(resultado);
							// }

						}

					}

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
	public LabOrden modificar(LabOrden orden) {
		try {
			orden.setListOrdenDetalle(new ArrayList<>());
			beginTransaction();
			orden = update(orden);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"LabOrden " + orden.toString());
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
	public LabOrden registrarResultados(LabOrden orden,
			List<LabOrdenDetalle> listOrdenDetalles) {
		try {
			orden.setListOrdenDetalle(new ArrayList<>());
			beginTransaction();
			orden = update(orden);
			for (LabOrdenDetalle ordenDetalle : listOrdenDetalles) {

				// System.out.println("Examen: "+ordenDetalle.getDescripcion());
				ordenDetalle = ordenDetalleDao.update(ordenDetalle);
				for (LabOrdenSubDetalle subDetalle : ordenDetalle
						.getListOrdenSubDetalle()) {
					// System.out.println("detalle : "+subDetalle.getDescripcion()+" Resultado :"+subDetalle.getResultado());
					subDetalle = ordenSubDetalleDao.update(subDetalle);

				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Actualizacion Correcto",
					"LabOrden " + orden.toString());
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
	public LabOrden registrarResultadosDetalle(LabOrdenDetalle ordenDetalle) {
		try {
			beginTransaction();
			ordenDetalle = ordenDetalleDao.update(ordenDetalle);
			for (LabOrdenSubDetalle subDetalle : ordenDetalle
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
	public LabOrden modificar(LabOrden orden,
			List<LabOrdenDetalle> listExamenDetalles) {
		try {
			orden.setListOrdenDetalle(new ArrayList<LabOrdenDetalle>());
			beginTransaction();
			orden = update(orden);
			ordenSubDetalleDao.deleteDetail(orden);
			ordenDetalleDao.deleteDetail(orden);
			for (LabOrdenDetalle labOrdenDetalle : listExamenDetalles) {
				LabOrdenDetalle detalle = new LabOrdenDetalle();
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
				for (LabExamenDetalle subDetalle1 : labOrdenDetalle
						.getLabExamen().getListExamenDetalle()) {
					LabOrdenSubDetalle subDetalle = new LabOrdenSubDetalle();
					subDetalle.setCompania(orden.getCompania());
					subDetalle.setSucursal(orden.getSucursal());
					subDetalle.setParametros(subDetalle1.getParametros());
					subDetalle.setDescripcion(subDetalle1.getDescripcion());
					subDetalle.setOrdenDetalle(detalle);
					subDetalle.setLabel(subDetalle1.isLabel());
					subDetalle.setOrden(orden);
					subDetalle.setUnidaMedida(subDetalle1.getUnidaMedida());
					subDetalle.setTipoValor(subDetalle1.getTipoValor());
					if (subDetalle1.isLabel()) {
						subDetalle.setParametros(subDetalle1.getDescripcion());
					} else {
						if (subDetalle1.getListValoresReferencia().size() == 0) {
							subDetalle.setParametros(subDetalle1
									.getParametros());
						} else {
							LabValoresRef labValoresRef = valorReferencia(
									orden, subDetalle1);
							if (labOrdenDetalle != null) {
								subDetalle.setValoresRef(labValoresRef);
								subDetalle.setParametros(labValoresRef
										.getParametros());
								subDetalle.setValorFinal(labValoresRef
										.getValorFinal());
								subDetalle.setValorInicial(labValoresRef
										.getValorInicial());
								subDetalle.setTextoMultiple(labValoresRef
										.getTextoMultiple());
								subDetalle.setValor(labValoresRef.getValor());
							}
							// subDetalle.setParametros(obtenerValorReferencia(
							// orden, subDetalle1));
						}

					}

					subDetalle = ordenSubDetalleDao.create(subDetalle);
				}
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"LabOrden " + orden.toString());
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
	public boolean eliminar(LabOrden labOrden) {
		try {
			beginTransaction();
			LabOrden bar = update(labOrden);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "LabOrden "
					+ labOrden.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public boolean eliminarOrden(LabOrden orden) {
		try {
			beginTransaction();
			orden.setEstado("RM");
			LabOrden bar = update(orden);
			for (LabOrdenDetalle ordenDetalle : orden.getListOrdenDetalle()) {
				ordenDetalle.setEstado("RM");
				ordenDetalleDao.update(ordenDetalle);
				for (LabOrdenSubDetalle ordenSubDetalle : ordenDetalle
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

	private LabValoresRef valorReferencia(LabOrden orden,
			LabExamenDetalle detalleExamen) {
		Period edad = Time.calcularEdadMesesYAnio(orden.getPaciente()
				.getFechaNacimiento());
		if (detalleExamen.getTipoParametro() == TipoSeleccion.NINGUNO) {
			return obtenerPorNinguno(detalleExamen);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.SEXO) {
			return obtenerPorSexo(detalleExamen, orden);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.EDAD) {
			return obtenerPorEdad(edad, detalleExamen);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.SEXO_EDAD) {
			obtenerPorSexoYEdad(edad, detalleExamen, orden);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.CIUDAD) {
			return obtenerPorCiudad(detalleExamen, orden);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.CIUDAD_SEXO) {
			return obtenerPorCiudadYSexo(detalleExamen, orden);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.CIUDAD_EDAD) {
			return obtenerPorCiudadYEdad(edad, detalleExamen, orden);
		}
		if (detalleExamen.getTipoParametro() == TipoSeleccion.CIUDAD_SEXO_EDAD) {
			return obtenerPorCiudadYSexoYEdad(edad, detalleExamen, orden);
		}
		return new LabValoresRef();
	}

	private LabValoresRef obtenerPorNinguno(LabExamenDetalle detalleExamen) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			return valoresRef;

		}
		return null;
	}

	private LabValoresRef obtenerPorSexo(LabExamenDetalle detalleExamen,
			LabOrden orden) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			if (valoresRef.getSexo() == Sexo.AMBOS) {
				return valoresRef;
			}
			if (valoresRef.getSexo() == Sexo.NINGUNO) {
				return valoresRef;
			}
			if (valoresRef.getSexo() == orden.getPaciente().getSexo()) {// FEMENINO,MASCULINO
				return valoresRef;
			}
		}
		return null;
	}

	private LabValoresRef obtenerPorSexoYEdad(Period edad,
			LabExamenDetalle detalleExamen, LabOrden orden) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			String edadInicial = valoresRef.getEdadInicial();
			String edadFinal = valoresRef.getEdadFinal();
			if (edad.getDays() > 0 && edad.getMonths() == 0
					&& edad.getYears() == 0) { // MENOS
												// UN
												// MES
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getSexo() == Sexo.AMBOS) {
					return valoresRef;
				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getSexo() == orden.getPaciente()
								.getSexo()) {// MASCULINO, FEMENINO
					return valoresRef;
				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getSexo() == Sexo.NINGUNO) {
					return valoresRef;
				}

			}
			if (edad.getMonths() > 0 && edad.getYears() == 0) {// TIENE
																// MENOS DE
																// UN AÑO
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getSexo() == Sexo.NINGUNO) {
					return valoresRef;

				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getSexo() == orden.getPaciente()
								.getSexo()) {// MASCULINO, FEMENINO
					return valoresRef;

				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getSexo() == Sexo.AMBOS) {
					return valoresRef;

				}

			}
			if (edad.getYears() > 0) {// ES MAYOR A
										// UN AÑO
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getSexo() == Sexo.AMBOS) {
					return valoresRef;

				}
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getSexo() == orden.getPaciente()
								.getSexo()) {// MASCULINO, FEMENINO
					return valoresRef;

				}
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getSexo() == Sexo.NINGUNO) {
					return valoresRef;

				}

			}
		}
		return null;
	}

	private LabValoresRef obtenerPorEdad(Period edad,
			LabExamenDetalle detalleExamen) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			String edadInicial = valoresRef.getEdadInicial();
			String edadFinal = valoresRef.getEdadFinal();
			if (edad.getDays() > 0 && edad.getMonths() == 0
					&& edad.getYears() == 0) { // MENOS
												// UN
												// MES
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()) {
					return valoresRef;
				}

			}
			if (edad.getMonths() > 0 && edad.getYears() == 0) {// TIENE
																// MENOS DE
																// UN AÑO
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()) {
					return valoresRef;

				}

			}
			if (edad.getYears() > 0) {// ES MAYOR A
										// UN AÑO
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")) {
					return valoresRef;

				}

			}
		}
		return null;
	}

	private LabValoresRef obtenerPorCiudad(LabExamenDetalle detalleExamen,
			LabOrden orden) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			if (valoresRef.getCiudad().getId() == orden.getSucursal()
					.getCiudad().getId()) {
				return valoresRef;
			}
		}
		return null;
	}

	private LabValoresRef obtenerPorCiudadYSexo(LabExamenDetalle detalleExamen,
			LabOrden orden) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			if (valoresRef.getCiudad().getId() == orden.getSucursal()
					.getCiudad().getId()
					&& valoresRef.getSexo() == Sexo.AMBOS) {
				return valoresRef;
			}
			if (valoresRef.getCiudad().getId() == orden.getSucursal()
					.getCiudad().getId()
					&& valoresRef.getSexo() == orden.getPaciente().getSexo()) {// MASCULINO,
																				// FEMENINO
				return valoresRef;
			}
			if (valoresRef.getCiudad().getId() == orden.getSucursal()
					.getCiudad().getId()
					&& valoresRef.getSexo() == Sexo.NINGUNO) {
				return valoresRef;
			}
		}
		return null;
	}

	private LabValoresRef obtenerPorCiudadYEdad(Period edad,
			LabExamenDetalle detalleExamen, LabOrden orden) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			String edadInicial = valoresRef.getEdadInicial();
			String edadFinal = valoresRef.getEdadFinal();
			if (edad.getDays() > 0 && edad.getMonths() == 0
					&& edad.getYears() == 0) { // MENOS
												// UN
												// MES
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;
				}

			}
			if (edad.getMonths() > 0 && edad.getYears() == 0) {// TIENE
																// MENOS DE
																// UN AÑO
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;

				}

			}
			if (edad.getYears() > 0) {// ES MAYOR A
										// UN AÑO
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;

				}

			}
		}
		return null;
	}

	private LabValoresRef obtenerPorCiudadYSexoYEdad(Period edad,
			LabExamenDetalle detalleExamen, LabOrden orden) {
		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
			String edadInicial = valoresRef.getEdadInicial();
			String edadFinal = valoresRef.getEdadFinal();
			if (edad.getDays() > 0 && edad.getMonths() == 0
					&& edad.getYears() == 0) { // MENOS
												// UN
												// MES
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getSexo() == Sexo.AMBOS
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;
				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getSexo() == orden.getPaciente()
								.getSexo()
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {// MASCULINO,
																		// FEMENINO
					return valoresRef;
				}

				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("D")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getDays()
						&& valoresRef.getSexo() == Sexo.NINGUNO
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;
				}

			}
			if (edad.getMonths() > 0 && edad.getYears() == 0) {// TIENE
																// MENOS DE
																// UN AÑO
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getSexo() == Sexo.NINGUNO
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;

				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getSexo() == orden.getPaciente()
								.getSexo()
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {// MASCULINO,
																		// FEMENINO
					return valoresRef;

				}
				if (edadFinal.substring(edadFinal.length() - 1,
						edadFinal.length()).equals("M")
						&& Integer.parseInt((edadFinal.substring(0,
								edadFinal.length() - 1))) >= edad.getMonths()
						&& valoresRef.getSexo() == Sexo.AMBOS
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;

				}

			}
			if (edad.getYears() > 0) {// ES MAYOR A
										// UN AÑO
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getSexo() == Sexo.AMBOS
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;

				}
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getSexo() == orden.getPaciente()
								.getSexo()
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {// MASCULINO,
																		// FEMENINO
					return valoresRef;

				}
				if (edadInicial.substring(edadInicial.length() - 1,
						edadInicial.length()).equals("A")
						&& Time.yaerBetween(Time.valueInt(edadInicial),
								Time.valueInt(edadFinal), edad.getYears())
						&& edadFinal.substring(edadFinal.length() - 1,
								edadFinal.length()).equals("A")
						&& valoresRef.getSexo() == Sexo.NINGUNO
						&& valoresRef.getCiudad().getId() == orden
								.getSucursal().getCiudad().getId()) {
					return valoresRef;

				}

			}
		}
		return null;
	}

	private String obtenerValorReferencia(LabOrden orden,
			LabExamenDetalle detalleExamen) {

		for (LabValoresRef valoresRef : detalleExamen
				.getListValoresReferencia()) {
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
	public LabOrden obtenerLabOrden(Integer id) {
		return findById(id);
	}

	@Override
	public LabOrden obtenerLabOrden(String nombre) {
		return findByParameter("nombre", nombre);
	}

	@Override
	public List<LabOrden> obtenerLabOrdenOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrden> obtenerLabOrdenOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<LabOrden> obtenerPorCompania(Compania compania) {
		return findAllActiveParameter("compania", compania.getId());
	}

	@Override
	public List<LabOrden> obtenerPorCompaniaOrdenadoFechaRegistro(
			Compania compania) {
		return findAllActiveParameter("compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabOrden> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"fechaRegistro", "desc");
	}

	@Override
	public List<LabOrden> obtenerPorCompaniaOrdenadoFechaRegistroRow50(
			Compania compania, Personal personal) {
		return findAllActiveParameter(50, "compania", compania.getId(),
				"medico", personal.getId(), "fechaRegistro", "desc");
	}

	@Override
	public List<LabOrden> obtenerParaHC(Paciente paciente,
			HistoriaClinica historiaClinica, Compania compania) {
		return findAllByParameterObjectFour("paciente", "historiaClinica",
				"compania", "estado", paciente.getId(),
				historiaClinica.getId(), compania.getId(), "AC");
	}

	@Override
	public List<LabOrden> obtenerPorCompania(String nombre, Compania compania) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"descripcion", nombre, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<LabOrden> obtenerDePaciente(Paciente paciente,
			Compania compania, Sucursal sucursal) {
		return findAllByParameterObjectThree("paciente", "compania", "estado",
				paciente.getId(), compania.getId(), "AP");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<LabOrden> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDao#obtenerPorSucursal(com.teds
	 * .spaps.model.Sucursal)
	 */
	@Override
	public List<LabOrden> obtenerPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.ILabOrdenDao#obtenerAllActivos()
	 */
	@Override
	public List<LabOrden> obtenerAllActivos() {
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
	public LabOrden obtenerPorCodigo(String codigo) {
		try {
			LabOrden ordendelab = findByParameterObject("codigo", codigo);
			System.out.println("la orden de lab es" + ordendelab);
			for (LabOrdenDetalle detalle : ordendelab.getListOrdenDetalle()) {
				System.out.println("detalle: " + detalle);
			}
			return ordendelab;
		} catch (Exception e) {
			return null;
		}
	}

	public List<LabOrden> obtenerPorSucursal(String estado, Date fecha,
			Personal medico, Sucursal sucursal) {
		return findAllActiveThwoParameter("estado", estado, "fecha", fecha,
				"medico", medico.getId(), "sucursal", sucursal.getId(),
				"fecha_modificacion", "DESC");
	}

	@Override
	public List<LabOrden> obtenerPorSucursal(String estado, Personal medico,
			Sucursal sucursal) {
		return findAllActiveThwoParameter("estado", estado, "medico",
				medico.getId(), "sucursal", sucursal.getId(), "fecha", "DESC");

	}

	@Override
	public List<LabOrden> obtenerProFechas(Sucursal sucursal, Date fechaini,
			Date fechafin) {
		return findAllActiveForThwoDatesAndThwoObject("sucursal",
				sucursal.getId(), "fechaRegistro", fechaini, "fechaRegistro",
				fechafin);
	}

	@Override
	public List<LabOrden> obtenerProFechasYEstado(Sucursal sucursal,
			Date fechaini, Date fechafin, String estado) {
		return findAllActiveForThwoDatesAndThwoObject("sucursal",
				sucursal.getId(), "fechaRegistro", fechaini, "fechaRegistro",
				fechafin, "id", "DESC", "estado", estado);
	}
}
