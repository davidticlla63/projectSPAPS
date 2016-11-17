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
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.interfaces.dao.IVentaNotaVentaDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.VentaDetalleNotaVenta;
import com.teds.spaps.model.VentaNotaVenta;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.SessionMain;


/**
 * @author WILSON
 *
 */
@ManagedBean(name="notaVentaController")
@ViewScoped
public class NotaVentaController {
	private static final long serialVersionUID = 1L;

	//DAO
	private @Inject IPacienteDao pacienteDao;
	private @Inject IVentaNotaVentaDao notaVentaDao;
	private @Inject IVentaServiciosDao ventaServicioDao;
	private @Inject IEmpresaDao empresaDao;
	private @Inject ISeguroDao seguroDao;
	private @Inject SessionMain sessionDao;

	//OBJECT 
	private VentaNotaVenta notaVenta;
	private Seguro seguroSeleccionado;
	private Empresa empresaSeleccionada;
	private VentaDetalleNotaVenta detalleNotaVenta;
	private VentaServicios ventaServicio;

	//VAR
	private String currentPage = "/pages/ventas/venta/list.xhtml";

	//LIST
	private List<VentaNotaVenta> listaNotaVenta;

	//STATE
	private boolean nuevo;
	private boolean registrar;
	private boolean seleccionado;
	private boolean modificacion;
	private boolean anulacion;
	private boolean procesar;
	private Double TipoCambio;
	private String idTablaProductos;
	private boolean modificarDetalleOI;
	private Double cantidadDisponible=0.0;
	private String tipoCliente;
	private boolean seguro;
	private boolean empresa;
	private boolean particular;

	@PostConstruct
	public void init() {
		loadDefault();
	}

	public void loadDefault(){
		tipoCliente="par";
		seguro=false;
		empresa=false;
		particular=true;
		nuevo = true;
		seleccionado = false;
		registrar = false;
		notaVenta=new VentaNotaVenta();
		seguroSeleccionado=new Seguro();
        listaNotaVenta=notaVentaDao.obtenerPorCompania(sessionDao.getCompania());
	}


	//ACTION
	public void onRowSelect(SelectEvent event) {
		modificacion = false;
		/*if(ordenTraspaso.getEstado().equals("AC")){
			modificacion=true;
		}*/
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


	}
	public void redireccionarAModificacion() {
		/*nuevo = false;
		seleccionado=true;
		registrar = false;
		modificacion=false;
		currentPage = "/pages/inventario/proceso/orden_traspaso/edit.xhtml";
		listaTraspasoProducto = traspasoProductoDao.obtenerTodosPorOrdenTraspaso(ordenTraspaso);
		int i=0;
		for(TraspasoProducto tP : listaTraspasoProducto){
			tP.setIdCorrelativo(i);
			tP.setListaDetalleOrdenTraspaso(CargarListaDetalleTraspasoDeProductosPEPS(tP));
			i++;	
		}
		listaTraspasoProductoEliminadas= new ArrayList<>();*/
	}

	public void actionNuevo(){
		notaVenta=new VentaNotaVenta();
		nuevo = false;
		seleccionado = false;
		registrar = true;
		modificacion=false;
		idTablaProductos="form001:singleDT2";
		//listaTraspasoProducto=new ArrayList<>();
		currentPage = "/pages/ventas/venta/edit.xhtml";
	}


	public void actualizarStock(){
		//System.out.println(traspasoProducto.getProducto());
		//	cantidadDisponible=almacenProductoDao.DevolverSumaCantidadPorProducto(traspasoProducto.getProducto());	
	}
	public void actionCancelar(){
		nuevo = true;
		seleccionado = false;
		registrar = false;
		loadDefault();
		currentPage = "/pages/ventas/venta/list.xhtml";
	}

	public void mostrarPrecios(){
		detalleNotaVenta.setPrecioUnitario(detalleNotaVenta.getServicio().getPrecioVenta());
	}
	
	public void cargarlistaPreciosSeguro(){

		System.out.println(seguroSeleccionado);

	}
	public void cargarlistaPreciosEmpresa(){

		System.out.println(empresaSeleccionada);

	}
	public void calcularSubTotal(AjaxBehaviorEvent event){
	    detalleNotaVenta.setPrecioSubTotal(detalleNotaVenta.getCantidad()*detalleNotaVenta.getPrecioUnitario());
		System.out.println("LA MULTIPLICACIONES ES"+ detalleNotaVenta.getPrecioSubTotal()) ;
	}
	/*CRUD NOTA DE VENTA*/
	
	public void registrarNotaDeVenta(){
		
		System.out.println("LISTA DETALLE= "+notaVenta.getListaDetalles().size());
		notaVenta.setFechaRegistro(new Date());
		notaVenta.setCodigo("123");
		notaVenta.setUsuarioRegistro("admin");
		//notaVenta.setSucursal(sessionDao.getSucursalLogin());
		notaVenta.setCompania(sessionDao.getCompania());
		System.out.println("NOTA DE VENTA ES "+notaVenta);
		System.out.println("LISTA DETALLE= "+notaVenta.getListaDetalles().size());
		VentaNotaVenta oT =notaVentaDao.registrar(notaVenta);
		if(oT!=null){
			loadDefault();
			currentPage = "/pages/ventas/venta/list.xhtml";
		}
		
	}
	
	/*DETALLE NOTA DE VENTA*/
	
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
        	detalleNotaVenta =new VentaDetalleNotaVenta();
 			FacesUtil.updateComponent("formDlgDetalleOrdenIngreso");
 			FacesUtil.showDialog("dlgProducto");
             break;
         default:
            break;
     }
		
		
	}
	public void registrarOModificarDetalleVenta(){
		detalleNotaVenta.setIdcorrelativo(notaVenta.getListaDetalles().size());
		notaVenta.addDetalle(detalleNotaVenta);
		FacesUtil.hideDialog("dlgProducto");
	}
	public void eliminarDetalleVenta(VentaDetalleNotaVenta detalleNotaVenta){
		notaVenta.getListaDetalles().remove(detalleNotaVenta);
		for ( int i=detalleNotaVenta.getIdcorrelativo();i<notaVenta.getListaDetalles().size();i++) {			
			VentaDetalleNotaVenta dNV=notaVenta.getListaDetalles().get(i);
			dNV.setIdcorrelativo(i);		
		}
	}
	public void modificarDetalleVenta(VentaDetalleNotaVenta detalleNotaVenta){
		notaVenta.getListaDetalles().remove(detalleNotaVenta);
		for ( int i=detalleNotaVenta.getIdcorrelativo();i<notaVenta.getListaDetalles().size();i++) {			
			VentaDetalleNotaVenta dNV=notaVenta.getListaDetalles().get(i);
			dNV.setIdcorrelativo(i);		
		}
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
	public VentaNotaVenta getNotaVenta() {
		return notaVenta;
	}

	public void setNotaVenta(VentaNotaVenta notaVenta) {
		this.notaVenta = notaVenta;
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

	public VentaDetalleNotaVenta getDetalleNotaVenta() {
		return detalleNotaVenta;
	}

	public void setDetalleNotaVenta(VentaDetalleNotaVenta detalleNotaVenta) {
		this.detalleNotaVenta = detalleNotaVenta;
	}

	public List<VentaNotaVenta> getListaNotaVenta() {
		return listaNotaVenta;
	}

	public void setListaNotaVenta(List<VentaNotaVenta> listaNotaVenta) {
		this.listaNotaVenta = listaNotaVenta;
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

}
