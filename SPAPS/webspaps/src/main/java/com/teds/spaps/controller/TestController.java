package com.teds.spaps.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IParameterDao;
import com.teds.spaps.model.Parameter;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@ManagedBean(name = "testController")
@ViewScoped
public class TestController implements Serializable {

	private static final long serialVersionUID = 1L;

	// DAO
	private @Inject IParameterDao parameterDao;

	// LIST
	private List<Parameter> listParameter;

	// OBJECT
	private Parameter parameter;

	// COMPONET
	private TabView tabView;

	// VAR
	private double valorN;
	private String valorT;
	private boolean tipoValor;
	private boolean modificar;

	// STATE
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;

	@PostConstruct
	public void init() {
		System.out.println("init - testController");
		loadDefault();
	}

	public void loadDefault() {
		this.parameter = new Parameter();
		this.listParameter = parameterDao
				.obtenerTodosActivosEInactivosPorOrdenAsc();

		this.tipoValor = true;
		this.modificar = false;
		this.setValorN(0);
		this.setValorT("");

		nuevo = true;
		seleccionado = false;
		registrar = false;
	}

	// EVENT

	public void onRowSelect(SelectEvent event) {
		nuevo = false;
		seleccionado = true;
		registrar = false;

		modificar = true;
		try {
			valorN = Double.valueOf(parameter.getValor());
			tipoValor = true;
		} catch (Exception e) {
			tipoValor = false;
			valorT = parameter.getValor();
		}
	}

	public void actionNuevo() {
		nuevo = false;
		seleccionado = false;
		registrar = true;
	}

	public void actionCancelar() {
		nuevo = true;
		seleccionado = false;
		registrar = false;

		valorT = "";
		parameter = new Parameter();

	}

	// PROCESO

	public void registrarParameter() {
		if (tipoValor) {
			parameter.setValor(String.valueOf(valorN));
		} else {
			parameter.setValor(valorT);
		}
		Parameter p = parameterDao.registrar(parameter);
		if (p != null) {
			loadDefault();
		}
	}

	public void modificarParameter() {
		if (tipoValor) {
			parameter.setValor(String.valueOf(valorN));
		} else {
			parameter.setValor(valorT);
		}
		Parameter p = parameterDao.modificar(parameter);
		if (p != null) {
			loadDefault();
		}
	}

	public void eliminarParameter() {
		boolean p = parameterDao.eliminar(parameter);
		if (p) {
			loadDefault();
		}
	}

	// get and seter

	public List<Parameter> getListParameter() {
		return listParameter;
	}

	public void setListParameter(List<Parameter> listParameter) {
		this.listParameter = listParameter;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public TabView getTabView() {
		FacesContext fc = FacesContext.getCurrentInstance();
		tabView = (TabView) fc.getApplication().createComponent(
				"org.primefaces.component.TabView");
		// this.listParameter =
		// parameterDao.obtenerTodosActivosEInactivosPorOrdenAsc();

		// Se crean dinamicamente las tabs y en su contenido, unas cajas de
		// texto
		for (Parameter sub : listParameter) {
			Tab tab = new Tab();
			tab.setTitle(sub.getDescripcion());
			OutputLabel label = new OutputLabel();
			InputText inputtext = new InputText();
			inputtext.setLabel("Label");
			try {
				Double.valueOf(sub.getValor());
				label.setValue("Valor Numerico: ");
				NumberFormat formatter = new DecimalFormat("#,##0.000");
				inputtext.setValue(formatter.format(Double.valueOf(sub
						.getValor())));
			} catch (Exception e) {
				label.setValue("Valor Texto: ");
				inputtext.setValue(sub.getValor());
			}
			tab.getChildren().add(label);
			inputtext.setOnfocus("");
			tab.getChildren().add(inputtext);
			tabView.getChildren().add(tab);
		}
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public double getValorN() {
		return valorN;
	}

	public void setValorN(double valorN) {
		this.valorN = valorN;
	}

	public String getValorT() {
		return valorT;
	}

	public void setValorT(String valorT) {
		this.valorT = valorT;
	}

	public boolean isTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(boolean tipoValor) {
		this.tipoValor = tipoValor;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
