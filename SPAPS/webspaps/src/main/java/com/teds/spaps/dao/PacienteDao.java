package com.teds.spaps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.teds.spaps.interfaces.dao.IGrupoFamiliarDao;
import com.teds.spaps.interfaces.dao.IIdentificacionIndividuoDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.IPacienteEmpresaDao;
import com.teds.spaps.interfaces.dao.IParamSistemaIndicesDao;
import com.teds.spaps.interfaces.dao.IPlanesSeguroDao;
import com.teds.spaps.model.Compania;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.GrupoFamiliar;
import com.teds.spaps.model.IdentificacionPaciente;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PacienteEmpresa;
import com.teds.spaps.model.ParamSistemaIndices;
import com.teds.spaps.model.Parentesco;
import com.teds.spaps.model.PlanesSeguro;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.Time;

@Stateless
public class PacienteDao extends DataAccessObjectJpa<Paciente> implements
		IPacienteDao {

	private @Inject IPlanesSeguroDao planesSeguroDao;
	private @Inject IIdentificacionIndividuoDao identificacionIndividuoDao;
	private @Inject IGrupoFamiliarDao grupoFamiliarDao;
	private @Inject IPacienteEmpresaDao pacienteEmpresaDao;
	private @Inject IParamSistemaIndicesDao sistemaIndicesDao;

	public PacienteDao() {
		super(Paciente.class);
	}

	@Override
	public Paciente create(Paciente paciente) {
		return super.create(paciente);
	}

	@Override
	public Paciente update(Paciente paciente) {
		return super.update(paciente);
	}

	@Override
	public void delete(Paciente paciente) {
		super.deleteReal(paciente);
	}

	@Override
	public Paciente registrar(Paciente paciente) {
		try {
			beginTransaction();
			paciente = create(paciente);
			commitTransaction();
			return paciente;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Paciente registrar(Paciente paciente,
			List<PlanesSeguro> listaPlanSeguro, GrupoFamiliar grupoFamiliar,
			Parentesco parentesco,
			List<IdentificacionPaciente> listaDocumentosIdentificacion,
			List<PacienteEmpresa> empresas) {
		try {
			paciente.setEstado("AC");
			if (paciente.getTipoAfiliacion().equals("S")
					|| !listaPlanSeguro.isEmpty()) {
				beginTransaction();
				if (!paciente.getTitular().equals("T")) {
					paciente.setGrupoFamiliar(grupoFamiliar);
					paciente.setParentesco(parentesco);
				} else {
					System.out
							.println("entro a grupo familiar auto con el individuo = "
									+ paciente.getNombre());
					GrupoFamiliar auxiliar = new GrupoFamiliar();
					auxiliar.setNombre(paciente.getApellidoPaterno() + " "
							+ paciente.getApellidoMaterno());
					auxiliar.setCodigo("PEN");
					auxiliar.setSexo(paciente.getSexo());
					auxiliar.setEstado("AC");
					auxiliar.setFechaRegistro(new Date());
					auxiliar.setFechaModificacion(auxiliar.getFechaRegistro());
					auxiliar.setUsuarioRegistro(paciente.getUsuarioRegistro());
					auxiliar.setSucursal(paciente.getSucursal());
					auxiliar.setCompania(auxiliar.getSucursal().getCompania());
					auxiliar = grupoFamiliarDao.create(auxiliar);
					paciente.setGrupoFamiliar(auxiliar);
					// individuo = update(individuo);
				}

				paciente = create(paciente);
				ParamSistemaIndices sistemaIndices = sistemaIndicesDao
						.obtenerPorCompania("AD", paciente.getSucursal()
								.getCompania());
				paciente.setCodigo(sistemaIndices.getPrefijo()
						+ Time.rellendarCeros(
								sistemaIndices.getNumeroDigitos(),
								sistemaIndices.getCorrelativo()));
				paciente = update(paciente);
				sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
				FacesUtil.infoMessage("Codigo generado:", "Su código es: "
						+ paciente.getCodigo());
				List<PlanesSeguro> listaPlanesSeguros = new ArrayList<PlanesSeguro>();
				for (PlanesSeguro plan : listaPlanSeguro) {
					plan.setIndividuo(paciente);
					System.out.println("individuoregistrado= "
							+ paciente.getNombre());
					plan.setEstado(paciente.getEstado());
					plan.setFechaRegistro(new Date());
					plan.setFechaModificacion(plan.getFechaRegistro());
					plan.setSucursal(paciente.getSucursal());
					plan.setCompania(paciente.getSucursal().getCompania());
					plan.setUsuarioRegistro(paciente.getUsuarioRegistro());
					plan.setId(null);
					listaPlanesSeguros.add(plan);
				}
				for (PlanesSeguro planesSeguro : listaPlanesSeguros) {
					System.out.println("planesSeguroi="
							+ planesSeguro.getIndividuo().toString() + " "
							+ planesSeguro.getPlanSeguro().toString());
					planesSeguroDao.create(planesSeguro);
				}
			} else {
				beginTransaction();
				paciente = create(paciente);
				ParamSistemaIndices sistemaIndices = sistemaIndicesDao
						.obtenerPorCompania("AD", paciente.getSucursal()
								.getCompania());
				paciente.setCodigo(sistemaIndices.getPrefijo()
						+ Time.rellendarCeros(
								sistemaIndices.getNumeroDigitos(),
								sistemaIndices.getCorrelativo()));
				paciente = update(paciente);
				sistemaIndices = sistemaIndicesDao.increment(sistemaIndices);
				FacesUtil.infoMessage("Codigo generado:", "Su código es: "
						+ paciente.getCodigo());
			}
			for (IdentificacionPaciente plan : listaDocumentosIdentificacion) {
				plan.setIndividuo(paciente);
				plan.setEstado(paciente.getEstado());
				plan.setFechaRegistro(new Date());
				plan.setFechaModificacion(plan.getFechaRegistro());
				plan.setSucursal(paciente.getSucursal());
				plan.setCompania(paciente.getSucursal().getCompania());
				plan.setUsuarioRegistro(paciente.getUsuarioRegistro());
			}
			for (IdentificacionPaciente planesSeguro : listaDocumentosIdentificacion) {
				identificacionIndividuoDao.create(planesSeguro);
			}
			for (PacienteEmpresa pacienteEmpresa : empresas) {
				pacienteEmpresa.setPaciente(paciente);
				pacienteEmpresa.setCompania(paciente.getCompania());
				pacienteEmpresa.setEstado(paciente.getEstado());
				pacienteEmpresa.setFechaModificacion(paciente
						.getFechaModificacion());
				pacienteEmpresa.setFechaRegistro(paciente.getFechaRegistro());
				pacienteEmpresa.setSucursal(paciente.getSucursal());
				pacienteEmpresa.setUsuarioRegistro(paciente
						.getUsuarioRegistro());
				pacienteEmpresaDao.create(pacienteEmpresa);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Paciente " + paciente.toString());
			return paciente;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			System.out.println(cause);
			rollbackTransaction();
			return null;
		}
	}

	/*
	 * public Individuo registrar(Individuo individuo) { try {
	 * beginTransaction(); individuo = create(individuo); commitTransaction();
	 * FacesUtil.infoMessage("Registro Correcto", "Individuo " +
	 * individuo.toString()); return individuo; } catch (Exception e) { String
	 * cause = e.getMessage(); if (cause .contains(
	 * "org.hibernate.exception.ConstraintViolationException: could not execute statement"
	 * )) { FacesUtil.errorMessage("Ya existe un registro igual."); } else {
	 * FacesUtil.errorMessage("Error al registrar"); } rollbackTransaction();
	 * return null; } }
	 */

	@Override
	public Paciente modificar(Paciente paciente,
			List<PlanesSeguro> listaEliminar, List<PlanesSeguro> listaMeter,
			GrupoFamiliar grupoFamiliar, Parentesco parentesco,
			List<IdentificacionPaciente> listaDISacar,
			List<IdentificacionPaciente> listaDIMeter,
			List<PacienteEmpresa> empresasSacar,
			List<PacienteEmpresa> empresasMeter) {
		try {
			beginTransaction();
			paciente = update(paciente);
			if (paciente.getTipoAfiliacion().equals("S")) {
				if (paciente.getTitular().equals("N")) {
					paciente.setGrupoFamiliar(grupoFamiliar);
					paciente.setParentesco(parentesco);
				} else {
					// no hace nada cuando es titular
				}
				for (PlanesSeguro planesSeguro : listaEliminar) {
					planesSeguroDao.delete(planesSeguro);
				}
				for (PlanesSeguro plan : listaMeter) {
					plan.setIndividuo(paciente);
					System.out.println("individuoregistrado= "
							+ paciente.getNombre());
					System.out.println("plan de seguro seleccionado= "
							+ plan.getPlanSeguro().getDescripcion());
					plan.setEstado(paciente.getEstado());
					plan.setFechaRegistro(new Date());
					plan.setFechaModificacion(paciente.getFechaModificacion());
					plan.setSucursal(paciente.getSucursal());
					plan.setCompania(paciente.getSucursal().getCompania());
					plan.setUsuarioRegistro(paciente.getUsuarioRegistro());
					plan.setId(null);
					planesSeguroDao.create(plan);
				}
			}
			for (IdentificacionPaciente identificacionPaciente : listaDISacar) {
				identificacionIndividuoDao.delete(identificacionPaciente);
			}
			for (IdentificacionPaciente identificacionIndividuo2 : listaDIMeter) {
				identificacionIndividuo2.setIndividuo(paciente);
				identificacionIndividuo2.setEstado(paciente.getEstado());
				identificacionIndividuo2.setFechaRegistro(new Date());
				identificacionIndividuo2.setFechaModificacion(paciente
						.getFechaModificacion());
				identificacionIndividuo2.setSucursal(paciente.getSucursal());
				identificacionIndividuo2.setCompania(paciente.getSucursal()
						.getCompania());
				identificacionIndividuo2.setUsuarioRegistro(paciente
						.getUsuarioRegistro());
				identificacionIndividuo2.setId(null);
				identificacionIndividuoDao.create(identificacionIndividuo2);
			}
			for (PacienteEmpresa pacienteEmpresa : empresasSacar) {
				pacienteEmpresaDao.delete(pacienteEmpresa);
			}
			for (PacienteEmpresa pacienteEmpresa : empresasMeter) {
				pacienteEmpresa.setId(null);
				pacienteEmpresa.setPaciente(paciente);
				pacienteEmpresa.setCompania(paciente.getCompania());
				pacienteEmpresa.setEstado(paciente.getEstado());
				pacienteEmpresa.setFechaModificacion(paciente
						.getFechaModificacion());
				pacienteEmpresa.setFechaRegistro(paciente.getFechaRegistro());
				pacienteEmpresa.setSucursal(paciente.getSucursal());
				pacienteEmpresa.setUsuarioRegistro(paciente
						.getUsuarioRegistro());
				pacienteEmpresaDao.create(pacienteEmpresa);
			}
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Paciente "
					+ paciente.toString());
			return paciente;
		} catch (Exception e) {
			String cause = e.getMessage();
			System.out.println("error en update paciente = " + cause);
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				System.out.println(cause);
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	@Override
	public boolean eliminar(Paciente paciente,
			List<PlanesSeguro> listaEliminar,
			List<IdentificacionPaciente> identificacionPacientes,
			List<PacienteEmpresa> empresas) {
		try {
			paciente.setEstado("RM");
			beginTransaction();
			paciente = update(paciente);
			if (paciente.getTipoAfiliacion().equals("S")) {
				if (paciente.getTitular().equals("N")) {
					// nada
				} else {
					// no hace nada cuando es titular
				}
				for (PlanesSeguro planesSeguro : listaEliminar) {
					planesSeguro.setEstado("RM");
					planesSeguroDao.update(planesSeguro);
				}
			}
			for (IdentificacionPaciente identificacionPaciente : identificacionPacientes) {
				identificacionPaciente.setEstado(paciente.getEstado());
				identificacionIndividuoDao.update(identificacionPaciente);
			}
			for (PacienteEmpresa pacienteEmpresa : empresas) {
				pacienteEmpresa.setEstado(paciente.getEstado());
				pacienteEmpresaDao.update(pacienteEmpresa);
			}
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Individuo "
					+ paciente.toString());
			return paciente != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	@Override
	public Paciente obtenerIndividuo(Integer id) {
		return findById(id);
	}

	@Override
	public Paciente obtenerPaciente(Integer id) {
		return findByParameter("id", id);
	}

	@Override
	public Paciente obtenerPaciente(String codigo, Compania compania) {
		return findByParameterObjectTwo("codigo", "compania", codigo,
				compania.getId());
	}

	@Override
	public Paciente obtenerPacienteHC(String codigoHc, Compania compania) {
		return findByParameterObjectTwo("codigoHc", codigoHc, "compania",
				compania.getId());
	}

	@Override
	public Paciente obtenerIndividuoPorFamilia(Integer id) {
		return findByParameter("grupoFamiliar", id);
	}

	@Override
	public Paciente obtenerTitularPorFamilia(Integer id) {
		return findByParameterObjectTwo("grupoFamiliar", "titular", id, "T");
	}

	@Override
	public Paciente obtenerIndividuoPorFamilia(String codigo) {
		return findByParameter("grupoFamiliar", codigo);
	}

	@Override
	public List<Paciente> obtenerIndividuosPorNombreCompania(String nombre,
			String apellidoPaterno, String apellidoMaterno, Compania compania) {
		/*
		 * String query = "select em from Paciente em where em.nombre like '%" +
		 * nombre + "%' or em.apellidoPaterno like '%" + apellidoPaterno +
		 * "%' or em.apellidoMaterno like '%" + apellidoMaterno +
		 * "%' and estado='AC' and em.compania=" + compania.getId();
		 */
		// return
		// findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
		// nombre, "apellidoPaterno", apellidoPaterno, "apellidoMaterno",
		// apellidoMaterno, "estado", "AC", "compania", compania.getId());
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "apellidoPaterno", apellidoPaterno,
				"apellidoMaterno", apellidoMaterno, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Paciente> obtenerIndividuosPorNombreSucursal(String nombre,
			String apellidoPaterno, String apellidoMaterno, Sucursal sucursal) {
		/*
		 * String query = "select em from Paciente em where em.nombre like '%" +
		 * nombre + "%' or em.apellidoPaterno like '%" + apellidoPaterno +
		 * "%' or em.apellidoMaterno like '%" + apellidoMaterno +
		 * "%' and estado='AC' and em.compania=" + compania.getId();
		 */
		// return
		// findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
		// nombre, "apellidoPaterno", apellidoPaterno, "apellidoMaterno",
		// apellidoMaterno, "estado", "AC", "compania", compania.getId());
		return findAllActiveOtherTableAndParameterForNamesAutoComplete(
				"nombre", nombre, "apellidoPaterno", apellidoPaterno,
				"apellidoMaterno", apellidoMaterno, "estado", "AC", "sucursal",
				sucursal.getId());
	}

	@Override
	public List<Paciente> obtenerPorSucursal(String nombre, Sucursal sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "sucursal", sucursal.getId());
	}

	@Override
	public List<Paciente> obtenerPorCompania(String nombre, Compania sucursal) {
		return findAllActiveOtherTableAndParameterForNameAutoComplete("nombre",
				nombre, "estado", "AC", "compania", sucursal.getId());
	}

	@Override
	public List<Paciente> obtenerIndividuosPorCodigo(String codigo,
			Compania compania) {
		/*
		 * String query = "select em from Paciente em where em.codigo like '%" +
		 * codigo + "%' and estado='AC' and em.id_compania =" +
		 * compania.getId();
		 */
		return findAllActiveOtherTableAndParameterForNameAutoComplete("codigo",
				codigo, "estado", "AC", "compania", compania.getId());
	}

	@Override
	public List<Paciente> obtenerIndividuoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public List<Paciente> obtenerIndividuoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	@Override
	public List<Paciente> obtenerFamiliaPorCodigo(String codigo) {
		return findAllActivosByParameter("grupoFamiliar", codigo);
	}

	@Override
	public List<Paciente> obtenerPorCodigoHC(String codigo, Compania compania) {
		/*
		 * String query = "select em from Paciente em where em.codigo like '%" +
		 * codigo + "%' and estado='AC' and em.id_compania =" +
		 * compania.getId();
		 */
		return findAllActiveOtherTableAndParameterForNameAutoComplete(
				"codigoHc", codigo, "estado", "AC", "compania",
				compania.getId());
	}

	@Override
	public List<Paciente> obtenerFamiliaPorId(Integer id) {
		return findAllActivosByParameter("grupoFamiliar", id);
	}

	@Override
	public List<Paciente> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	@Override
	public List<Paciente> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa);
	}

	@Override
	public List<Paciente> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.teds.spaps.interfaces.dao.IIndividuoDao#
	 * obtenerTodosActivosEInactivosPorOrdenAsc()
	 */
	@Override
	public List<Paciente> obtenerTodosActivosEInactivosPorOrdenAsc() {
		return findAscAllOrderedByParameter("id");
	}

	@Override
	public Paciente obtenerUltimo() {
		String query = "select max(em.id) from paciente em";
		return executeQuerySingleResult(query);
	}

	@Override
	public String generarCodigo(String patron, Integer id) {
		String codigo = "";
		patron = patron.toUpperCase();
		if (patron.isEmpty()) {
			codigo = "PRT";
		} else {
			codigo = patron.substring(0, 3);
		}
		Integer cantidadDigitos = 10;
		String ids = Integer.toString(id);
		Integer cantidadDigitosId = ids.length();
		for (int i = 0; i < cantidadDigitos - cantidadDigitosId; i++) {
			codigo += "0";
		}
		System.out.println("codigo generado = " + codigo);
		return codigo + ids;
	}

	@Override
	public boolean verificarRegistro(Paciente paciente, Sucursal sucursal) {
		return findByParameterObjectFour("nombre", "apellidoPaterno",
				"apellidoMaterno", "sucursal", paciente.getNombre(),
				paciente.getApellidoPaterno(), paciente.getApellidoMaterno(),
				sucursal.getId()) != null;
	}

	@Override
	public Paciente DevolverPacientePorId(Integer id) {
		String query = "SELECT NEW com.teds.spaps.model.Paciente(p.id, p.nombre)FROM Paciente AS p where p.id="
				+ id;
		Query q = getEntityManager().createQuery(query);
		System.out.println(q.getSingleResult());
		return (Paciente) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Paciente> devolverPacienteOnCompleteCompania(Compania compania,
			String nombre) {
		String query = "select NEW com.teds.spaps.model.Paciente(p.id, p.nombre)from Paciente p where upper(translate(p.nombre, 'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜñ', 'aeiouAEIOUaeiouAEIOUÑ')) like '%"
				+ nombre + "%'";
		Query q = getEntityManager().createQuery(query);
		return (List<Paciente>) q.getResultList();
	}

}
