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

import com.teds.spaps.interfaces.dao.IPlanSeguroDao;
import com.teds.spaps.interfaces.dao.IPlanSeguroServicioDao;
import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.interfaces.dao.IServicioDao;
import com.teds.spaps.model.PlanSeguro;
import com.teds.spaps.model.PlanSeguroServicio;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.Servicio;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "planSeguroController")
@ViewScoped
public class PlanSeguroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 844192813700641693L;
	/******* DAO **********/
	private @Inject IPlanSeguroDao planSeguroDao;
	private @Inject SessionMain sessionMain;
	private @Inject ISeguroDao seguroDao;
	private @Inject IServicioDao servicioDao;
	private @Inject IPlanSeguroServicioDao planSeguroServicioDao;

	/******* OBJECT **********/
	private PlanSeguro planSeguro;
	private PlanSeguro planSeguroSelected;
	private Sucursal sucursal;
	private Seguro selectedSeguro;
	private Servicio servicio;

	/******* LIST **********/
	private List<PlanSeguro> listaPlanSeguro;
	private List<Seguro> listaSeguro = new ArrayList<Seguro>();
	private List<PlanSeguroServicio> listaDetalleCobertura = new ArrayList<PlanSeguroServicio>();
	private List<PlanSeguroServicio> listaDetalleCoberturaBackup = new ArrayList<PlanSeguroServicio>();
	private List<Servicio> listaCobertura = new ArrayList<Servicio>();

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

	/**
	 * 
	 */
	public PlanSeguroController() {
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
		servicio = new Servicio();
		seleccionado = false;
		planSeguro = new PlanSeguro();
		planSeguroSelected = new PlanSeguro();
		listaPlanSeguro = planSeguroDao.obtenerPorCompania(sucursal
				.getCompania());
		listaCobertura = new ArrayList<Servicio>();
		listaDetalleCobertura = new ArrayList<PlanSeguroServicio>();
		listaDetalleCoberturaBackup = new ArrayList<PlanSeguroServicio>();
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (planSeguro.getDescripcion().trim().isEmpty()
					|| planSeguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			planSeguro.setCompania(sucursal.getCompania());
			planSeguro.setSucursal(sucursal);
			planSeguro.setFechaRegistro(new Date());
			planSeguro.setFechaModificacion(planSeguro.getFechaRegistro());
			planSeguro.setUsuarioRegistro(sessionMain.getUsuarioLogin()
					.getLogin());
			planSeguro.setSeguro(selectedSeguro);
			PlanSeguro r = planSeguroDao.registrar(planSeguro,
					listaDetalleCobertura);
			if (r != null) {
				FacesUtil.infoMessage("PlanSeguro registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de planSeguro: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (planSeguro.getDescripcion().trim().isEmpty()
					|| planSeguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				planSeguro.setCompania(getSucursal().getCompania());
				planSeguro.setSucursal(getSucursal());
				planSeguro.setFechaModificacion(new Date());
				planSeguro.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				planSeguro.setSeguro(selectedSeguro);
				PlanSeguro r = planSeguroDao.modificar(planSeguro,
						listaDetalleCoberturaBackup, listaDetalleCobertura);
				if (r != null) {
					FacesUtil.infoMessage("PlanSeguro actualizado",
							r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de planSeguro: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (planSeguroDao.eliminar(planSeguro, listaDetalleCobertura)) {
				FacesUtil.infoMessage("PlanSeguro Eliminado",
						planSeguro.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de planSeguro: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		planSeguro = planSeguroSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
		selectedSeguro = planSeguro.getSeguro();
		listaDetalleCobertura = planSeguroServicioDao
				.obtenerPorPlanSeguro(planSeguro);
		listaDetalleCoberturaBackup = planSeguroServicioDao
				.obtenerPorPlanSeguro(planSeguro);
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		planSeguro = new PlanSeguro();
		planSeguro.setFechaRegistro(new Date());
		selectedSeguro = new Seguro();
	}

	public void actionCancelar() {
		System.out.println("Ingreso a actionCancelar..");
		crear = true;
		seleccionado = false;
		registrar = false;
		planSeguro = new PlanSeguro();
		selectedSeguro = new Seguro();
	}

	// ONCOMPLETETEXT PAIS
	public List<Seguro> completeSeguro(String query) {
		String upperQuery = query.toUpperCase();
		listaSeguro = seguroDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listaSeguro;
	}

	public boolean verificarCobertura(String descripcion) {
		for (PlanSeguroServicio cobertura : listaDetalleCobertura) {
			if (cobertura.getCobertura().getDescripcion().equals(descripcion))
				return true;
		}
		return false;
	}

	public List<Servicio> completeCobertura(String query) {
		String upperQuery = query.toUpperCase();
		listaCobertura = servicioDao.obtenerPorCompania(upperQuery,
				sucursal.getCompania());
		return listaCobertura;
	}

	public void onRowDeleteCobertura(PlanSeguroServicio planSeguroServicio) {
		// deleteDetalle(detalleTipoDiagnostico);
		listaDetalleCobertura.remove(planSeguroServicio);
	}

	public void onRowSelectCoberturaClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		if (verificarCobertura(nombre)) {
			FacesUtil.errorMessage("Ya esta asociada tal cobertura al plan.");
		} else {
			for (Servicio i : listaCobertura) {
				if (i.getDescripcion().equals(nombre)) {
					PlanSeguroServicio auxiliar = new PlanSeguroServicio();
					auxiliar.setCobertura(i);
					listaDetalleCobertura.add(auxiliar);
					return;
				}
			}
		}
	}

	public void onRowSelectSeguroClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		for (Seguro i : listaSeguro) {
			if (i.getNombre().equals(nombre)) {
				selectedSeguro = i;
				return;
			}
		}
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<Seguro> getListaSeguro() {
		return listaSeguro;
	}

	public void setListaSeguro(List<Seguro> listaSeguro) {
		this.listaSeguro = listaSeguro;
	}

	public Seguro getSelectedSeguro() {
		return selectedSeguro;
	}

	public void setSelectedSeguro(Seguro selectedSeguro) {
		this.selectedSeguro = selectedSeguro;
	}

	public PlanSeguro getPlanSeguro() {
		return planSeguro;
	}

	public PlanSeguro getPlanSeguroSelected() {
		return planSeguroSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<PlanSeguro> getListaPlanSeguro() {
		return listaPlanSeguro;
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

	public void setPlanSeguro(PlanSeguro planSeguro) {
		this.planSeguro = planSeguro;
	}

	public void setPlanSeguroSelected(PlanSeguro planSeguroSelected) {
		this.planSeguroSelected = planSeguroSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPlanSeguro(List<PlanSeguro> listaPlanSeguro) {
		this.listaPlanSeguro = listaPlanSeguro;
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

	public Servicio getCobertura() {
		return servicio;
	}

	public void setCobertura(Servicio servicio) {
		this.servicio = servicio;
	}

	public List<PlanSeguroServicio> getListaDetalleCobertura() {
		return listaDetalleCobertura;
	}

	public void setListaDetalleCobertura(
			List<PlanSeguroServicio> listaDetalleCobertura) {
		this.listaDetalleCobertura = listaDetalleCobertura;
	}

	public List<PlanSeguroServicio> getListaDetalleCoberturaBackup() {
		return listaDetalleCoberturaBackup;
	}

	public void setListaDetalleCoberturaBackup(
			List<PlanSeguroServicio> listaDetalleCoberturaBackup) {
		this.listaDetalleCoberturaBackup = listaDetalleCoberturaBackup;
	}

	public List<Servicio> getListaCobertura() {
		return listaCobertura;
	}

	public void setListaCobertura(List<Servicio> listaCobertura) {
		this.listaCobertura = listaCobertura;
	}

}
