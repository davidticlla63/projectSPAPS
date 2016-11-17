/**
 * @author ANITA
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IDetalleMedicamentoPresentacionDao;
import com.teds.spaps.interfaces.dao.IDetalleMedicamentoSucursalDao;
import com.teds.spaps.interfaces.dao.IMedicamentoDao;
import com.teds.spaps.interfaces.dao.IPresentacionDao;
import com.teds.spaps.interfaces.dao.ISucursalDao;
import com.teds.spaps.model.DetalleMedicamentoPresentacion;
import com.teds.spaps.model.DetalleMedicamentoSucursal;
import com.teds.spaps.model.Medicamento;
import com.teds.spaps.model.Presentacion;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.model.Usuario;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "medicamentoController")
@ViewScoped
public class MedicamentoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2105381667632709353L;
	/******* DAO **********/
	private @Inject IMedicamentoDao medicamentoDao;
	private @Inject SessionMain sessionMain;
	private @Inject ISucursalDao sucursalDao;
	private @Inject IDetalleMedicamentoSucursalDao detalleMedicamentoSucursalDao;
	private @Inject IPresentacionDao presentacionDao;
	private @Inject IDetalleMedicamentoPresentacionDao detalleMedicamentoPresentacionDao;

	/******* OBJECT **********/
	private Medicamento medicamento;
	private Medicamento medicamentoSelected;
	private Sucursal sucursalRegistro;
	private Sucursal sucursal;
	private Usuario usuario;
	private Presentacion presentacion;

	/******* LIST **********/
	private List<Medicamento> listaMedicamento;
	private List<Sucursal> listaSucursales;
	private List<DetalleMedicamentoSucursal> listaDetalleMedicamentoSucursal;
	private List<DetalleMedicamentoSucursal> listaDetalleMedicamentoSucursalBackup;
	private List<Presentacion> listaPresentaciones;
	private List<DetalleMedicamentoPresentacion> listaDetalleMedicamentoPresentacion;
	private List<DetalleMedicamentoPresentacion> listaDetalleMedicamentoPresentacionBackup;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public MedicamentoController() {
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public Medicamento getMedicamentoSelected() {
		return medicamentoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Medicamento> getListaMedicamento() {
		return listaMedicamento;
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

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public void setMedicamentoSelected(Medicamento medicamentoSelected) {
		this.medicamentoSelected = medicamentoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaMedicamento(List<Medicamento> listaMedicamento) {
		this.listaMedicamento = listaMedicamento;
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

	public Sucursal getSucursalRegistro() {
		return sucursalRegistro;
	}

	public void setSucursalRegistro(Sucursal sucursalRegistro) {
		this.sucursalRegistro = sucursalRegistro;
	}

	public List<Sucursal> getListaSucursales() {
		return listaSucursales;
	}

	public void setListaSucursales(List<Sucursal> listaSucursales) {
		this.listaSucursales = listaSucursales;
	}

	public List<DetalleMedicamentoSucursal> getListaDetalleMedicamentoSucursal() {
		return listaDetalleMedicamentoSucursal;
	}

	public void setListaDetalleMedicamentoSucursal(
			List<DetalleMedicamentoSucursal> listaDetalleMedicamentoSucursal) {
		this.listaDetalleMedicamentoSucursal = listaDetalleMedicamentoSucursal;
	}

	public List<DetalleMedicamentoSucursal> getListaDetalleMedicamentoSucursalBackup() {
		return listaDetalleMedicamentoSucursalBackup;
	}

	public void setListaDetalleMedicamentoSucursalBackup(
			List<DetalleMedicamentoSucursal> listaDetalleMedicamentoSucursalBackup) {
		this.listaDetalleMedicamentoSucursalBackup = listaDetalleMedicamentoSucursalBackup;
	}

	public Presentacion getPresentacion() {
		return presentacion;
	}

	public List<Presentacion> getListaPresentaciones() {
		return listaPresentaciones;
	}

	public List<DetalleMedicamentoPresentacion> getListaDetalleMedicamentoPresentacion() {
		return listaDetalleMedicamentoPresentacion;
	}

	public List<DetalleMedicamentoPresentacion> getListaDetalleMedicamentoPresentacionBackup() {
		return listaDetalleMedicamentoPresentacionBackup;
	}

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

	public void setListaPresentaciones(List<Presentacion> listaPresentaciones) {
		this.listaPresentaciones = listaPresentaciones;
	}

	public void setListaDetalleMedicamentoPresentacion(
			List<DetalleMedicamentoPresentacion> listaDetalleMedicamentoPresentacion) {
		this.listaDetalleMedicamentoPresentacion = listaDetalleMedicamentoPresentacion;
	}

	public void setListaDetalleMedicamentoPresentacionBackup(
			List<DetalleMedicamentoPresentacion> listaDetalleMedicamentoPresentacionBackup) {
		this.listaDetalleMedicamentoPresentacionBackup = listaDetalleMedicamentoPresentacionBackup;
	}

	@PostConstruct
	public void initNew() {
		medicamento = new Medicamento();
		medicamentoSelected = new Medicamento();
		usuario = sessionMain.getUsuarioLogin();
		sucursalRegistro = sessionMain.PruebaSucursal();
		System.out.println("sucursal = " + sucursalRegistro.getNombre());
		sucursal = new Sucursal();
		presentacion = new Presentacion();
		listaPresentaciones = new ArrayList<Presentacion>();
		listaMedicamento = medicamentoDao.obtenerMedicamentoOrdenAscPorId();
		listaSucursales = new ArrayList<Sucursal>();
		listaDetalleMedicamentoSucursal = new ArrayList<DetalleMedicamentoSucursal>();
		listaDetalleMedicamentoPresentacion = new ArrayList<DetalleMedicamentoPresentacion>();
		listaDetalleMedicamentoPresentacionBackup = new ArrayList<DetalleMedicamentoPresentacion>();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
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
		listaDetalleMedicamentoSucursal = detalleMedicamentoSucursalDao
				.obtenerPorMedicamento(medicamento);
		listaDetalleMedicamentoSucursalBackup = detalleMedicamentoSucursalDao
				.obtenerPorMedicamento(medicamento);
		listaDetalleMedicamentoPresentacion = detalleMedicamentoPresentacionDao
				.obtenerPorMedicamento(medicamento,
						sucursalRegistro.getCompania());
		listaDetalleMedicamentoPresentacionBackup = detalleMedicamentoPresentacionDao
				.obtenerPorMedicamento(medicamento,
						sucursalRegistro.getCompania());
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("form001:singleDT");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public List<Sucursal> onCompleteSucursal(String query) {
		String upperQuery = query.toUpperCase();
		listaSucursales = sucursalDao.obtenerPorCompania(upperQuery,
				sucursalRegistro.getCompania());
		return listaSucursales;
	}

	public List<Presentacion> onCompletePresentacion(String query) {
		String upperQuery = query.toUpperCase();
		listaPresentaciones = presentacionDao.obtenerPorCompania(upperQuery,
				sucursalRegistro.getCompania());
		return listaPresentaciones;
	}

	public void onRowDeleteSucursal(
			DetalleMedicamentoSucursal detalleMedicamentoSucursal) {
		listaDetalleMedicamentoSucursal.remove(detalleMedicamentoSucursal);
	}

	public void onRowDeletePresentacion(
			DetalleMedicamentoPresentacion detalleMedicamentoPresentacion) {
		listaDetalleMedicamentoPresentacion
				.remove(detalleMedicamentoPresentacion);
	}

	public boolean verificarSucursal(Sucursal sucursal) {
		for (DetalleMedicamentoSucursal detalleMedicamentoSucursal : listaDetalleMedicamentoSucursal) {
			if (detalleMedicamentoSucursal.getSucursal().equals(sucursal))
				return true;
		}
		return false;
	}

	public boolean verificarPresentacion(Presentacion presentacion) {
		for (DetalleMedicamentoPresentacion detalleMedicamentoPresentacion : listaDetalleMedicamentoPresentacion) {
			if (detalleMedicamentoPresentacion.getPresentacion().equals(
					presentacion))
				return true;
		}
		return false;
	}

	public void onRowSelectSucursalClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		Sucursal t = sucursalDao.obtenerSucursal(nombre,
				sucursalRegistro.getCompania());
		if (verificarSucursal(t)) {
			FacesUtil.errorMessage("Ya se encuentra la sucursal.");
		} else {
			DetalleMedicamentoSucursal auxiliar = new DetalleMedicamentoSucursal();
			auxiliar.setSucursal(t);
			listaDetalleMedicamentoSucursal.add(auxiliar);
		}
	}

	public void onRowSelectPresentacionClick(SelectEvent event) {
		String nombre = event.getObject().toString();
		Presentacion t = presentacionDao.obtenerPresentacion(nombre,
				sucursalRegistro.getCompania());
		if (verificarPresentacion(t)) {
			FacesUtil.errorMessage("Ya se encuentra la presentación.");
		} else {
			DetalleMedicamentoPresentacion auxiliar = new DetalleMedicamentoPresentacion();
			auxiliar.setPresentacion(t);
			listaDetalleMedicamentoPresentacion.add(auxiliar);
		}
	}

	public void registrar() {
		try {
			if (medicamento.getNombreGenerico().trim().isEmpty()
					|| medicamento.getNombreComercial().trim().isEmpty()
					|| getSucursalRegistro() == null
					|| getSucursalRegistro().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
			} else {
				medicamento.setEstado("AC");
				medicamento.setSucursal(getSucursalRegistro());
				medicamento.setCompania(getSucursalRegistro().getCompania());
				medicamento.setFechaRegistro(new Date());
				medicamento
						.setFechaModificacion(medicamento.getFechaRegistro());
				medicamento.setUsuarioRegistro(getUsuario().getLogin());
				if (listaDetalleMedicamentoSucursal.size() == 0) {
					DetalleMedicamentoSucursal detalleMedicamentoSucursal = new DetalleMedicamentoSucursal();
					detalleMedicamentoSucursal.setSucursal(sucursalRegistro);
					listaDetalleMedicamentoSucursal
							.add(detalleMedicamentoSucursal);
				}
				medicamentoDao.registrar(medicamento,
						listaDetalleMedicamentoSucursal,
						listaDetalleMedicamentoPresentacion);
			}
			initNew();
		} catch (Exception e) {
			System.out.println("Error en registro de medicamento: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (medicamento.getNombreGenerico().trim().isEmpty()
					|| medicamento.getNombreComercial().trim().isEmpty()
					|| medicamento.getEstado().trim().isEmpty()
					|| getSucursalRegistro() == null
					|| getSucursalRegistro().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
			} else {
				medicamento.setSucursal(getSucursalRegistro());
				medicamento.setCompania(getSucursalRegistro().getCompania());
				medicamento.setFechaModificacion(new Date());
				medicamento.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				medicamentoDao.modificar(medicamento,
						listaDetalleMedicamentoSucursalBackup,
						listaDetalleMedicamentoSucursal,
						listaDetalleMedicamentoPresentacionBackup,
						listaDetalleMedicamentoPresentacion);
			}
			initNew();
		} catch (Exception e) {
			System.out.println("Error en modificacion de medicamento: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			medicamentoDao.eliminar(getMedicamento(),
					listaDetalleMedicamentoSucursalBackup,
					listaDetalleMedicamentoPresentacionBackup);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de medicamento: "
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

}
