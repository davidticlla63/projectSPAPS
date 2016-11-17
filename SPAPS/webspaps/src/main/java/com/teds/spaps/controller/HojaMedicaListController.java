/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IHistoriaClinicaDao;
import com.teds.spaps.model.HistoriaClinica;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "hojaMedicaListController")
@ViewScoped
public class HojaMedicaListController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1682168731519850189L;
	/******* DAO **********/
	private @Inject IHistoriaClinicaDao historiaClinicaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private HistoriaClinica historiaClinica;
	private HistoriaClinica historiaClinicaSelected;
	private Sucursal sucursal;
	private Usuario usuario;

	/******* LIST **********/
	private List<HistoriaClinica> listaHistoriaClinica;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	@Inject
	private FacesContext facesContext;
	private HttpServletRequest request;
	
	//url page
	
	private String currentPage;

	@PostConstruct
	public void initNew() {
		historiaClinica = new HistoriaClinica();
		historiaClinicaSelected = new HistoriaClinica();
		usuario = sessionMain.getUsuarioLogin();
		sucursal = sessionMain.PruebaSucursal();
		listaHistoriaClinica = historiaClinicaDao.obtenerPorCompania(sessionMain
				.getCompania());
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
		setCurrentPage("/pages/historias_clinicas/registro_historias_clinicas/list_hc.xhtml");
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		modificar = false;
		registrar = true;
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		
		FacesContext.getCurrentInstance().renderResponse();
//		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}
	

	public void redireccionar(HistoriaClinica newHistoriaClinica) {
		try {
			String navigateString;
			if (newHistoriaClinica.getPaciente().getCodigoHc() == null
					|| newHistoriaClinica.getPaciente().getCodigoHc().trim().length() == 0) {
				// CREACION DE HOJA MEDICA
				navigateString = "/pages/historias_clinicas/registro_historias_clinicas/index.xhtml?idPaciente="
						+ newHistoriaClinica.getId();
			} else {
				// CREACION DE HISTORIA CLINICA
				navigateString = "/pages/historias_clinicas/registro_detalle_historias_clinicas/historia_medica.xhtml?idPaciente="
						+ newHistoriaClinica.getPaciente().getId();
			}
			FacesContext.getCurrentInstance().getExternalContext()
			.getSessionMap()
			.put("historiaClinicaDetalleController", null);
			request = (HttpServletRequest) facesContext.getExternalContext()
					.getRequest();
			System.out.println(request.getContextPath() + navigateString);
			facesContext.getExternalContext().redirect(
					request.getContextPath() + navigateString);

		} catch (Exception e) {
			System.out.println("Error en registro de historiaClinica: "
					+ e.getMessage());
		}

	}

	public void initConversation() {
		if (!FacesContext.getCurrentInstance().isPostback()
				&& conversation.isTransient()) {
			conversation.begin();
			System.out.println(">>>>>>>>>> CONVERSACION INICIADA...");
		}
	}

	public String endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
		return "kardex_producto.xhtml?faces-redirect=true";
	}


	/**
	 * 
	 */
	public HojaMedicaListController() {
	}

	public HistoriaClinica getHistoriaClinica() {
		return historiaClinica;
	}

	public HistoriaClinica getHistoriaClinicaSelected() {
		return historiaClinicaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<HistoriaClinica> getListaHistoriaClinica() {
		return listaHistoriaClinica;
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

	public void setHistoriaClinica(HistoriaClinica historiaClinica) {
		this.historiaClinica = historiaClinica;
	}

	public void setHistoriaClinicaSelected(HistoriaClinica historiaClinicaSelected) {
		this.historiaClinicaSelected = historiaClinicaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaHistoriaClinica(List<HistoriaClinica> listaHistoriaClinica) {
		this.listaHistoriaClinica = listaHistoriaClinica;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the currentPage
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
