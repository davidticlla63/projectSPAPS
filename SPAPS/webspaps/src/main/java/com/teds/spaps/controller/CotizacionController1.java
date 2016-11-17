/**
 * @author WILSON
 */
package com.teds.spaps.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.interfaces.dao.IVentaCotizacionDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.VentaCotizacion;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;


/**
 * @author WILSON
 *
 */
@ManagedBean(name="cotizacionController1")
@ViewScoped
public class CotizacionController1 {
	private static final long serialVersionUID = 1L;

	//DAO
	private @Inject IPacienteDao pacienteDao;
	private @Inject IVentaCotizacionDao cotizacionDao;
	private @Inject IVentaServiciosDao ventaServicioDao;
	private @Inject IEmpresaDao empresaDao;
	private @Inject ISeguroDao seguroDao;
	private @Inject SessionMain sessionDao;
	private @Inject ILabOrdenDao ordenLabDao;

	//OBJECT 
	private VentaCotizacion cotizacion;
	private Seguro seguroSeleccionado;
	private Empresa empresaSeleccionada;
	private VentaCotizacionDetalle detalleCotizacion;
	private VentaServicios ventaServicio;
	private String numeroOrden;
	private Double total;


	//VAR
	private String currentPage = "/pages/ventas/cotizaciontab/list.xhtml";

	//LIST
	private List<VentaCotizacion> listaCotizacion;


	//STATE
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;
	private boolean modificacion;
	private boolean anulacion;
	private boolean procesar;
	private Double TipoCambio;
	private String idTablaProductos;
	private String tipoCliente;
	private boolean seguro;
	private boolean empresa;
	private boolean particular;

	@PostConstruct
	public void init() {
		loadDefault();
	}

	public void loadDefault(){
		total=0.0;
		tipoCliente="par";
		seguro=false;
		empresa=false;
		particular=true;
		nuevo = true;
		seleccionado = false;
		registrar = false;
		cotizacion=new VentaCotizacion();
		//cotizacion.setCodigo("Generado Por el Sistema");
		seguroSeleccionado=new Seguro();
		detalleCotizacion=new VentaCotizacionDetalle();
		listaCotizacion=cotizacionDao.obtenerPorCompania(sessionDao.getCompania());
	}


	//ACTION
	public void onRowSelect(SelectEvent event) {

	}
	public void cargarOpciones(){
		if(tipoCliente.equals("emp")){
			empresa=true;
			seguro=false;
			particular=false;
		}
		if(tipoCliente.equals("seg")){
			seguro=true;
			empresa=false;
			particular=false;
		}
		if(tipoCliente.equals("par")){
			seguro=false;
			empresa=false;
			particular=true;
		}

	}

	public void onRowSelect(){
		nuevo = false;
		seleccionado = true;
		registrar = false;
		currentPage = "/pages/ventas/cotizaciontab/edit.xhtml";
		int i=0;
		for (VentaCotizacionDetalle ventaCotizacionDetalle : cotizacion.getListCotizacionDetalles()) {
			ventaCotizacionDetalle.setIdcorrelativo(i);
			System.out.println("El DETALLE ES :"+ventaCotizacionDetalle);
			i++;

		}
	}


	public void actionNuevo(){
		cotizacion=new VentaCotizacion();
		detalleCotizacion=new VentaCotizacionDetalle();
		nuevo = false;
		seleccionado = false;
		registrar = true;
		modificacion=false;
		idTablaProductos="form001:singleDT2";
		currentPage = "/pages/ventas/cotizaciontab/edit.xhtml";
	}


	public void actualizarStock(){
		

	}
	public void actionCancelar(){
		nuevo = true;
		seleccionado = false;
		registrar = false;
		loadDefault();
		currentPage = "/pages/ventas/cotizaciontab/list.xhtml";
	}

	public void mostrarPrecios(){
		detalleCotizacion.setPrecioUnitario(detalleCotizacion.getServicio().getPrecioVenta());
		detalleCotizacion.setCantidad(1);
		detalleCotizacion.setPrecioSubTotal(detalleCotizacion.getPrecioUnitario()*detalleCotizacion.getCantidad());
	}

	public void cargarlistaPreciosSeguro(){

		System.out.println(seguroSeleccionado);

	}
	public void cargarlistaPreciosEmpresa(){

		System.out.println(empresaSeleccionada);

	}
	public void calcularSubTotal(AjaxBehaviorEvent event){
		detalleCotizacion.setPrecioSubTotal(detalleCotizacion.getCantidad()*detalleCotizacion.getPrecioUnitario());
		System.out.println("LA MULTIPLICACIONES ES"+ detalleCotizacion.getPrecioSubTotal()) ;
	}
	/*CRUD NOTA DE VENTA*/
	


	public void registrarNotaDeVenta(){
		if (cotizacion.getListCotizacionDetalles().size()>0) {
			System.out.println("LISTA DETALLE= "+cotizacion.getListCotizacionDetalles().size());
			cotizacion.setFechaRegistro(new Date());
			cotizacion.setUsuarioRegistro("admin");
			cotizacion.setPrecioTotalBs(total);
			cotizacion.setCompania(sessionDao.getCompania());
			System.out.println("NOTA DE VENTA ES "+cotizacion);
			VentaCotizacion oT =cotizacionDao.registrar(cotizacion);
			if(oT!=null){
				loadDefault();
				currentPage = "/pages/ventas/cotizaciontab/list.xhtml";
			}
		}else{
			FacesUtil.infoMessage("Informacion", "El detalle no debe estar vacio");

		}


	}
	public void modificarCotizacion(){
		if (cotizacion.getListCotizacionDetalles().size()>0) {
			cotizacion.setPrecioTotalBs(total);
			VentaCotizacion oT =cotizacionDao.modificar(cotizacion);
			if(oT!=null){
				loadDefault();
				currentPage = "/pages/ventas/cotizaciontab/list.xhtml";
			}
		}else{
			FacesUtil.infoMessage("Informacion", "El detalle no debe estar vacio");
		}
	}
	/*DETALLE NOTA DE VENTA*/
	public void cancelarOrdenLab(){
		
		numeroOrden="";
	}
	public void nuevoDetalleVenta(){
		switch (tipoCliente) {
		case "emp":

			break;
		case "seg":
			if(seguroSeleccionado !=null){

			}else{
				FacesUtil.showDialog("dlg2");
			}
			break;
		case "par":
			detalleCotizacion =new VentaCotizacionDetalle();
			FacesUtil.updateComponent("formDlgDetalleOrdenIngreso");
			FacesUtil.showDialog("dlgProducto");
			break;
		default:
			break;
		}


	}
	public void registrarOModificarDetalleVenta(){
		if(detalleCotizacion.getServicio()!=null){
		detalleCotizacion.setIdcorrelativo(cotizacion.getListCotizacionDetalles().size());
		detalleCotizacion.setEstado("AC");
		cotizacion.addDetalle(detalleCotizacion);
		detalleCotizacion=new VentaCotizacionDetalle();
		}
		else{
			FacesUtil.infoMessage("Informacion", "Seleccione un examen");	
		}
		}
	public void eliminarDetalle(VentaCotizacionDetalle detalleCotizacion){

		if (detalleCotizacion.getId()!=0) {
			detalleCotizacion.setEstado("RM");
		}
		System.out.println("Entro en ELIMINAR");
		cotizacion.getListCotizacionDetalles().remove(detalleCotizacion);
		for ( int i=detalleCotizacion.getIdcorrelativo();i<cotizacion.getListCotizacionDetalles().size();i++) {			
			VentaCotizacionDetalle dNV=cotizacion.getListCotizacionDetalles().get(i);
			dNV.setIdcorrelativo(i);		
		}
		this.detalleCotizacion=new VentaCotizacionDetalle();
	}
	public void modificarDetalle(VentaCotizacionDetalle detalleCotizacion){
		System.out.println("Entro en modificar el detalle global es;"+this.detalleCotizacion);
		cotizacion.getListCotizacionDetalles().set(detalleCotizacion.getIdcorrelativo(), detalleCotizacion);
		System.out.println("Entro en modificar el detalle por parametro es;"+detalleCotizacion);
		System.out.println("Entro en modificar el detalle global es;"+this.detalleCotizacion);
		this.detalleCotizacion=new VentaCotizacionDetalle();
	}

	public void buscarOrdenLaboratorio(){
		FacesUtil.updateComponent("formDlgDetalleOrdenIngreso");
		FacesUtil.showDialog("dlgProducto");
	}

	public void obtenerOrdenLab(){
		LabOrden lb=ordenLabDao.obtenerPorCodigo(numeroOrden);
		if (lb!=null) {
			List<VentaServicios> lista=ventaServicioDao.devolverServiciosPorOrdenDeLab(lb);
			for (VentaServicios ventaServicios : lista) {
				VentaCotizacionDetalle vcd=new VentaCotizacionDetalle();
				vcd.setServicio(ventaServicios);
				vcd.setCantidad(1.0);
				vcd.setEstado("ac");
				vcd.setFechaRegistro(new Date());
				vcd.setUsuarioRegistro("admin");
				vcd.setPrecioUnitario(ventaServicios.getPrecioVenta());
				vcd.setPrecioSubTotal(ventaServicios.getPrecioVenta()*vcd.getCantidad());
				vcd.setIdcorrelativo(cotizacion.getListCotizacionDetalles().size());
				cotizacion.addDetalle(vcd);

			}
			FacesUtil.hideDialog("dlgProducto");	
		} else {
			FacesUtil.showDialog("dlg2");
		}
		
			
	}
	public void onRowCancel(){

	}
	/*METODOS ONCOMPLETE*/

	public List<Paciente> onCompletePaciente(String query){
		String upperQuery = query.toUpperCase();
		return pacienteDao.devolverPacienteOnCompleteCompania(sessionDao.getCompania(),upperQuery);
	}
	public List<Empresa> onCompleteEmpresa(String query){
		String upperQuery = query.toUpperCase();
		return empresaDao.devolverEmpresaOnCompleteCompania(sessionDao.getCompania(),upperQuery);
	}
	public List<Seguro> onCompleteSeguro(String query){
		String upperQuery = query.toUpperCase();
		return seguroDao.devolverSeguroOnCompleteCompania(sessionDao.getCompania(),upperQuery);

	}
	public List<VentaServicios> onCompleteServicio(String query){
		String upperQuery = query.toUpperCase();
		return ventaServicioDao.devolverServicioOnCompleteCompania(sessionDao.getCompania(),upperQuery);
	}

	/*SETTERS AND GETTERS*/


	public boolean isNuevo() {
		return nuevo;
	}

	public VentaCotizacion getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(VentaCotizacion cotizacion) {
		this.cotizacion = cotizacion;
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

	public boolean isAnulacion() {
		return anulacion;
	}

	public void setAnulacion(boolean anulacion) {
		this.anulacion = anulacion;
	}

	public boolean isProcesar() {
		return procesar;
	}

	public void setProcesar(boolean procesar) {
		this.procesar = procesar;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public boolean isSeguro() {
		return seguro;
	}

	public void setSeguro(boolean seguro) {
		this.seguro = seguro;
	}

	public boolean isEmpresa() {
		return empresa;
	}

	public void setEmpresa(boolean empresa) {
		this.empresa = empresa;
	}

	public boolean isParticular() {
		return particular;
	}

	public void setParticular(boolean particular) {
		this.particular = particular;
	}

	public Seguro getSeguroSeleccionado() {
		return seguroSeleccionado;
	}

	public void setSeguroSeleccionado(Seguro seguroSeleccionado) {
		this.seguroSeleccionado = seguroSeleccionado;
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}



	public VentaCotizacionDetalle getDetalleCotizacion() {
		return detalleCotizacion;
	}

	public void setDetalleCotizacion(VentaCotizacionDetalle detalleCotizacion) {
		this.detalleCotizacion = detalleCotizacion;
	}

	public List<VentaCotizacion> getListaCotizacion() {
		return listaCotizacion;
	}

	public void setListaCotizacion(List<VentaCotizacion> listaCotizacion) {
		this.listaCotizacion = listaCotizacion;
	}

	public VentaServicios getVentaServicio() {
		return ventaServicio;
	}

	public void setVentaServicio(VentaServicios ventaServicio) {
		this.ventaServicio = ventaServicio;
	}

	public String getIdTablaProductos() {
		return idTablaProductos;
	}

	public void setIdTablaProductos(String idTablaProductos) {
		this.idTablaProductos = idTablaProductos;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public Double getTotal() {
		total=0.0;
		for (VentaCotizacionDetalle ventaCotizacionDetalle : cotizacion.getListCotizacionDetalles()) {
			total=total+ventaCotizacionDetalle.getPrecioSubTotal();	
		}
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
