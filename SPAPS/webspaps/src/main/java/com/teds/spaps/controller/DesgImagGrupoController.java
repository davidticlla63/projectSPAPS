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

import com.teds.spaps.interfaces.dao.IDesgImagGrupoDao;
import com.teds.spaps.interfaces.dao.IDesgImagGrupoExamenDao;
import com.teds.spaps.interfaces.dao.ILabExamenImagDao;
import com.teds.spaps.model.DesgImagGrupo;
import com.teds.spaps.model.DesgImagGrupoExamen;
import com.teds.spaps.model.LabExamenImag;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "desgImagGrupoController")
@ViewScoped
public class DesgImagGrupoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1873014253539178757L;
	/******* DAO **********/
	private @Inject IDesgImagGrupoDao desgImagGrupoDao;
	private @Inject SessionMain sessionMain;
	private @Inject ILabExamenImagDao labExamenDao;
	private @Inject IDesgImagGrupoExamenDao labGrupoExamenDao;

	/******* OBJECT **********/
	private DesgImagGrupo desgImagGrupo;
	private DesgImagGrupo desgImagGrupoSelected;
	private Sucursal sucursal;
	private LabExamenImag examen;
	private Usuario usuario;

	/******* LIST **********/
	private List<DesgImagGrupo> listaDesgImagGrupo;
	public static List<LabExamenImag> examenes;
	private List<DesgImagGrupoExamen> grupoExamenes;
	private List<DesgImagGrupoExamen> grupoExamenesBackup;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	// URL PAGE
	private String currentPage = "/pages/desgravamen/grupo_imageneologia/list.xhtml";

	/**
	 * 
	 */
	public DesgImagGrupoController() {
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
		desgImagGrupo = new DesgImagGrupo();
		desgImagGrupoSelected = new DesgImagGrupo();
		examen = new LabExamenImag();
		examenes = new ArrayList<LabExamenImag>();
		grupoExamenes = new ArrayList<DesgImagGrupoExamen>();
		listaDesgImagGrupo = desgImagGrupoDao.obtenerPorCompania(sucursal
				.getCompania());
		currentPage = "/pages/desgravamen/grupo_imageneologia/list.xhtml";
	}

	public List<LabExamenImag> onCompleteExamen(String query) {
		String upperQuery = query.toUpperCase().trim();
		examenes = labExamenDao.obtenerPorSucursalAutoComplete(upperQuery,
				sucursal);
		return examenes;
	}

	public boolean verificarExamen(LabExamenImag examen) {
		for (DesgImagGrupoExamen labExamen : grupoExamenes) {
			if (labExamen.getExamen().getDescripcion()
					.equalsIgnoreCase(examen.getDescripcion()))
				return true;
		}
		return false;
	}

	public void onSelectExamenClick(SelectEvent event) {
		examen = (LabExamenImag) event.getObject();
		if (verificarExamen(examen)) {
			FacesUtil.infoMessage("VALIDACION", "Ya existe el examen.");
		} else {
			if (labGrupoExamenDao.verificarExamenRegistrado(examen, sucursal)) {
				FacesUtil.infoMessage("VALIDACION",
						"Ya existe el examen en otro grupo.");
			} else {
				DesgImagGrupoExamen auxiliar = new DesgImagGrupoExamen();
				auxiliar.setExamen(examen);
				grupoExamenes.add(auxiliar);
			}
		}
	}

	public void onRowDeleteExamen(DesgImagGrupoExamen examen) {
		grupoExamenes.remove(examen);
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");
			if (desgImagGrupoDao.verificarRepetido(desgImagGrupo,
					sucursal.getCompania())) {
				FacesUtil.infoMessage("VALIDACION", "Ya existe el grupo.");
			} else {
				if (desgImagGrupo.getDescripcion().trim().isEmpty()
						|| desgImagGrupo.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				}
				desgImagGrupo.setCompania(sucursal.getCompania());
				desgImagGrupo.setSucursal(sucursal);
				desgImagGrupo.setFechaRegistro(new Date());
				desgImagGrupo.setFechaModificacion(desgImagGrupo
						.getFechaRegistro());
				desgImagGrupo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());

				DesgImagGrupo r = desgImagGrupoDao.registrar(desgImagGrupo,
						grupoExamenes);
				if (r != null) {
					defaultValues();
				} else {
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de desgImagGrupo: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (desgImagGrupoDao.verificarRepetidoUpdate(desgImagGrupo,
					sucursal.getCompania())) {
				FacesUtil.infoMessage("VALIDACION", "Ya existe el grupo.");
			} else {
				if (desgImagGrupo.getDescripcion().trim().isEmpty()
						|| desgImagGrupo.getEstado().trim().isEmpty()
						|| getSucursal() == null
						|| getSucursal().getCompania() == null) {
					FacesUtil.infoMessage("VALIDACION",
							"No puede haber campos vacíos");
					return;
				} else {
					desgImagGrupo.setCompania(getSucursal().getCompania());
					desgImagGrupo.setSucursal(getSucursal());
					desgImagGrupo.setFechaModificacion(new Date());
					desgImagGrupo.setUsuarioRegistro(sessionMain
							.getUsuarioLogin().getLogin());
					DesgImagGrupo r = desgImagGrupoDao.modificar(desgImagGrupo,
							grupoExamenesBackup, grupoExamenes);
					if (r != null) {
						defaultValues();
					} else {
						defaultValues();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de desgImagGrupo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (desgImagGrupoDao.eliminar(desgImagGrupo, grupoExamenesBackup)) {
				FacesUtil.infoMessage("DesgImagGrupo Eliminado",
						desgImagGrupo.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de desgImagGrupo: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		desgImagGrupo = desgImagGrupoSelected;
		grupoExamenes = labGrupoExamenDao.obtenerPorGrupo(desgImagGrupo);
		grupoExamenesBackup = labGrupoExamenDao.obtenerPorGrupo(desgImagGrupo);
		crear = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/desgravamen/grupo_imageneologia/edit.xhtml";
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		desgImagGrupo = new DesgImagGrupo();
		desgImagGrupo.setFechaRegistro(new Date());
		currentPage = "/pages/desgravamen/grupo_imageneologia/edit.xhtml";
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		desgImagGrupo = new DesgImagGrupo();
		currentPage = "/pages/desgravamen/grupo_imageneologia/list.xhtml";
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public DesgImagGrupo getDesgImagGrupo() {
		return desgImagGrupo;
	}

	public DesgImagGrupo getDesgImagGrupoSelected() {
		return desgImagGrupoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<DesgImagGrupo> getListaDesgImagGrupo() {
		return listaDesgImagGrupo;
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

	public void setDesgImagGrupo(DesgImagGrupo labGrupo) {
		this.desgImagGrupo = labGrupo;
	}

	public void setDesgImagGrupoSelected(DesgImagGrupo desgImagGrupoSelected) {
		this.desgImagGrupoSelected = desgImagGrupoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDesgImagGrupo(List<DesgImagGrupo> listaDesgImagGrupo) {
		this.listaDesgImagGrupo = listaDesgImagGrupo;
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

	public static List<LabExamenImag> getExamenes() {
		return examenes;
	}

	public static void setExamenes(List<LabExamenImag> examenes) {
		DesgImagGrupoController.examenes = examenes;
	}

	public LabExamenImag getExamen() {
		return examen;
	}

	public void setExamen(LabExamenImag examen) {
		this.examen = examen;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DesgImagGrupoExamen> getGrupoExamenes() {
		return grupoExamenes;
	}

	public void setGrupoExamenes(List<DesgImagGrupoExamen> grupoExamenes) {
		this.grupoExamenes = grupoExamenes;
	}

	public List<DesgImagGrupoExamen> getGrupoExamenesBackup() {
		return grupoExamenesBackup;
	}

	public void setGrupoExamenesBackup(
			List<DesgImagGrupoExamen> grupoExamenesBackup) {
		this.grupoExamenesBackup = grupoExamenesBackup;
	}

}
