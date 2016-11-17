/**
 * 
 */
package com.teds.spaps.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Sucursal;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;

/**
 * @author ANITA
 *
 */
@ManagedBean(name = "empresaController")
@ViewScoped
public class EmpresaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125210622622704977L;
	/******* DAO **********/
	private @Inject IEmpresaDao empresaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Empresa empresa;
	private Empresa empresaSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Empresa> listaEmpresa;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionado = false;

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
		empresa = new Empresa();
		empresaSelected = new Empresa();
		listaEmpresa = empresaDao.obtenerPorCompania(sucursal.getCompania());
	}

	public void registrar() {
		try {
			System.out.println("Ingreso a registrar");

			if (empresa.getDescripcion().trim().isEmpty()
					|| empresa.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			}
			empresa.setCompania(sucursal.getCompania());
			empresa.setSucursal(sucursal);
			empresa.setFechaRegistro(new Date());
			empresa.setFechaModificacion(empresa.getFechaRegistro());
			empresa.setUsuarioRegistro(sessionMain.getUsuarioLogin().getLogin());

			Empresa r = empresaDao.registrar(empresa);
			if (r != null) {
				FacesUtil.infoMessage("Empresa registrado", r.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al registrar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de empresa: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			System.out.println("Ingreso a actualizar");
			if (empresa.getDescripcion().trim().isEmpty()
					|| empresa.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				empresa.setCompania(getSucursal().getCompania());
				empresa.setSucursal(getSucursal());
				empresa.setFechaModificacion(new Date());
				empresa.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getLogin());
				Empresa r = empresaDao.modificar(empresa);
				if (r != null) {
					FacesUtil.infoMessage("Empresa actualizado", r.toString());
					defaultValues();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					defaultValues();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de empresa: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (empresaDao.eliminar(empresa)) {
				FacesUtil.infoMessage("Empresa Eliminado", empresa.toString());
				defaultValues();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				defaultValues();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de empresa: "
					+ e.getMessage());
		}
	}

	public void onRowSelect(SelectEvent event) {
		empresa = empresaSelected;
		crear = false;
		seleccionado = true;
		registrar = false;
	}

	public void actionNuevo() {
		System.out.println("Ingreso a actionNuevo...");
		crear = false;
		seleccionado = false;
		registrar = true;
		empresa = new Empresa();
		empresa.setFechaRegistro(new Date());
	}

	public void actionCancelar() {
		crear = true;
		seleccionado = false;
		registrar = false;
		empresa = new Empresa();
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	/**
	 * 
	 */
	public EmpresaController() {
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public Empresa getEmpresaSelected() {
		return empresaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
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

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setEmpresaSelected(Empresa empresaSelected) {
		this.empresaSelected = empresaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
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

}
