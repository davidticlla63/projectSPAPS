/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IDesgLabGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgLabGrupoExamenDao;
import com.teds.spaps.interfaces.dao.ILabExamenDao;
import com.teds.spaps.model.DesgLabGrupo;
import com.teds.spaps.model.DesgLabGrupoExamen;
import com.teds.spaps.model.LabExamen;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "desgLabGrupoController")
@ViewScoped
public class DesgLabGrupoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3986910208410458581L;
	/******* DAO **********/
	private @Inject IDesgLabGrupoDao desgLabGrupoDao;
	private @Inject SessionMain sessionMain;
	private @Inject ILabExamenDao labExamenDao;
	private @Inject IDesgLabGrupoExamenDao labGrupoExamenDao;

	/******* OBJECT **********/
	private DesgLabGrupo desgLabGrupo;
	private DesgLabGrupo desgLabGrupoSelected;
	private Sucursal sucursal;
	private LabExamen examen;
	private Usuario usuario;

	/******* LIST **********/
	private List<DesgLabGrupo> listaDesgLabGrupo;
	public static List<LabExamen> examenes;
	private List<DesgLabGrupoExamen> grupoExamenes;
	private List<DesgLabGrupoExamen> grupoExamenesBackup;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/desgravamen/grupo_laboratorio/list.xhtml";

	/**
	 * 
	 */
	public DesgLabGrupoController() {
	}

	@PostConstruct
	public void initNew() {
		sucursal = sessionMain.PruebaSucursal();
		System.out.println("Sucursal : " + sucursal.getDescripcion());
		System.out.println("Compañia : "
				+ sucursal.getCompania().getDescripcion());
		defaultValues();
	}

	private void defaultValues() {
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		seleccionado = false;
		desgLabGrupo = new DesgLabGrupo();
		desgLabGrupoSelected = new DesgLabGrupo();
		examen = new LabExamen();
		examenes = new ArrayList<LabExamen>();
		grupoExamenes = new ArrayList<DesgLabGrupoExamen>();
		listaDesgLabGrupo = desgLabGrupoDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/desgravamen/grupo_laboratorio/list.xhtml";
	}

	public List<LabExamen> onCompleteExamen(String query) {
		String upperQuery = query.toUpperCase().trim();
		examenes = labExamenDao.obtenerPorSucursalAutoComplete(upperQuery,
				sucursal);
		return examenes;
	}

	public boolean verificarExamen(LabExamen examen) {
		for (DesgLabGrupoExamen labExamen : grupoExamenes) {
			if (labExamen.getExamen().getDescripcion()
					.equalsIgnoreCase(examen.getDescripcion()))
				return true;
		}
		return false;
	}

	public void onSelectExamenClick(SelectEvent event) {
		examen = (LabExamen) event.getObject();
		if (verificarExamen(examen)) {
			FacesUtil.infoMessage("VALIDACION", "Ya existe el examen.");
		} else {
			if (labGrupoExamenDao.verificarExamenRegistrado(examen, sucursal)) {
				FacesUtil.infoMessage("VALIDACION",
						"El examen ya se encuentra en otro grupo.");
			} else {
				DesgLabGrupoExamen auxiliar = new DesgLabGrupoExamen();
				auxiliar.setExamen(examen);
				grupoExamenes.add(auxiliar);
			}
		}
	}

	public void onRowDeleteExamen(DesgLabGrupoExamen examen) {
		grupoExamenes.remove(examen);
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");
			if (desgLabGrupoDao.verificarRepetido(desgLabGrupo,
					sucursal.getCompania())) {
				FacesUtil.infoMessage("VALIDACION", "Ya existe el grupo.");
			} else {
				if (desgLabGrupo.getDescripcion().trim().isEmpty()
						|| desgLabGrupo.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				}
				desgLabGrupo.setCompania(sucursal.getCompania());
				desgLabGrupo.setSucursal(sucursal);
				desgLabGrupo.setFechaRegistro(new Date());
				desgLabGrupo.setFechaModificacion(desgLabGrupo
						.getFechaRegistro());
				desgLabGrupo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());

				DesgLabGrupo r = desgLabGrupoDao.registrar(desgLabGrupo,
						grupoExamenes);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de desgLabGrupo: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (desgLabGrupoDao.verificarRepetidoUpdate(desgLabGrupo,
					sucursal.getCompania())) {
				FacesUtil.infoMessage("VALIDACION", "Ya existe el grupo.");
			} else {
				if (desgLabGrupo.getDescripcion().trim().isEmpty()
						|| desgLabGrupo.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					desgLabGrupo.setCompania(getSucursal().getCompania());
					desgLabGrupo.setSucursal(getSucursal());
					desgLabGrupo.setFechaModificacion(new Date());
					desgLabGrupo.setUsuarioRegistro(sessionMain
							.getUsuarioLogin().getLogin());
					DesgLabGrupo r = desgLabGrupoDao.modificar(desgLabGrupo,
							grupoExamenesBackup, grupoExamenes);
					if (r != null) {
						defaultValues();
					} else {
						defaultValues();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de desgLabGrupo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (desgLabGrupoDao.eliminar(desgLabGrupo, grupoExamenesBackup)) {
				FacesUtil.infoMessage("DesgLabGrupo Eliminado",
						desgLabGrupo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de desgLabGrupo: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		desgLabGrupo = desgLabGrupoSelected;
		grupoExamenes = labGrupoExamenDao.obtenerPorGrupo(desgLabGrupo);
		grupoExamenesBackup = labGrupoExamenDao.obtenerPorGrupo(desgLabGrupo);
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/desgravamen/grupo_laboratorio/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		desgLabGrupo = new DesgLabGrupo();
		desgLabGrupo.setFechaRegistro(new Date());
		currentPage = "/pages/desgravamen/grupo_laboratorio/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		desgLabGrupo = new DesgLabGrupo();
		currentPage = "/pages/desgravamen/grupo_laboratorio/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public DesgLabGrupo getDesgLabGrupo() {
		return desgLabGrupo;
	}

	public DesgLabGrupo getDesgLabGrupoSelected() {
		return desgLabGrupoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<DesgLabGrupo> getListaDesgLabGrupo() {
		return listaDesgLabGrupo;
	}

	public boolean isModificar() {
		return modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setDesgLabGrupo(DesgLabGrupo labGrupo) {
		this.desgLabGrupo = labGrupo;
	}

	public void setDesgLabGrupoSelected(DesgLabGrupo desgLabGrupoSelected) {
		this.desgLabGrupoSelected = desgLabGrupoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDesgLabGrupo(List<DesgLabGrupo> listaDesgLabGrupo) {
		this.listaDesgLabGrupo = listaDesgLabGrupo;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public static List<LabExamen> getExamenes() {
		return examenes;
	}

	public static void setExamenes(List<LabExamen> examenes) {
		DesgLabGrupoController.examenes = examenes;
	}

	public LabExamen getExamen() {
		return examen;
	}

	public void setExamen(LabExamen examen) {
		this.examen = examen;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DesgLabGrupoExamen> getGrupoExamenes() {
		return grupoExamenes;
	}

	public void setGrupoExamenes(List<DesgLabGrupoExamen> grupoExamenes) {
		this.grupoExamenes = grupoExamenes;
	}

	public List<DesgLabGrupoExamen> getGrupoExamenesBackup() {
		return grupoExamenesBackup;
	}

	public void setGrupoExamenesBackup(
			List<DesgLabGrupoExamen> grupoExamenesBackup) {
		this.grupoExamenesBackup = grupoExamenesBackup;
	}

}
