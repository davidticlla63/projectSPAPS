/**
 * @author WILSON
 */
package com.teds.spaps.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

















import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;

import com.teds.spaps.dao.LabOrdenDao;
import com.teds.spaps.interfaces.dao.IComprobanteDao;
import com.teds.spaps.interfaces.dao.IEmpresaDao;
import com.teds.spaps.interfaces.dao.ILabOrdenDao;
import com.teds.spaps.interfaces.dao.IPacienteDao;
import com.teds.spaps.interfaces.dao.ISeguroDao;
import com.teds.spaps.interfaces.dao.IVentaCotizacionDao;
import com.teds.spaps.interfaces.dao.IVentaNotaVentaDao;
import com.teds.spaps.interfaces.dao.IVentaServiciosDao;
import com.teds.spaps.model.Comprobante;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.FacturaVenta;
import com.teds.spaps.model.LineaContable;
import com.teds.spaps.model.NotaVentaComprobante;
import com.teds.spaps.model.LabOrden;
import com.teds.spaps.model.Paciente;
import com.teds.spaps.model.PlanCuenta;
import com.teds.spaps.model.Seguro;
import com.teds.spaps.model.TipoComprobante;
import com.teds.spaps.model.VentaCotizacion;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.model.VentaCotizacionDetalle;
import com.teds.spaps.model.VentaDetalleNotaVenta;
import com.teds.spaps.model.VentaNotaVenta;
import com.teds.spaps.model.VentaServicios;
import com.teds.spaps.util.FacesUtil;
import com.teds.spaps.util.FacturacionUtil;
import com.teds.spaps.util.SessionMain;


/**
 * @author WILSON
 *
 */
@ManagedBean(name="ventaController1")
@ViewScoped
public class VentaController1 {
	private static final long serialVersionUID = 1L;

	//DAO
	private @Inject IPacienteDao pacienteDao;
	private @Inject IVentaNotaVentaDao notaVentaDao;
	private @Inject IVentaServiciosDao ventaServicioDao;
	private @Inject IEmpresaDao empresaDao;
	private @Inject ISeguroDao seguroDao;
	private @Inject SessionMain sessionDao;
	private @Inject ILabOrdenDao ordenLabDao;
	private @Inject IComprobanteDao comprobanteDao;

	//OBJECT 
	private VentaNotaVenta notaVenta;
	private Seguro seguroSeleccionado;
	private Empresa empresaSeleccionada;
	private VentaDetalleNotaVenta detalleNotaVenta;
	private VentaServicios ventaServicio;
	private String numeroOrden;
	private Double total;
	private LabOrden ordenDeLaboratorio;


	//VAR
	private String currentPage = "/pages/ventas/notaventa/list.xhtml";

	//LIST
	private List<VentaNotaVenta> listaNotaDeVenta;


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
		notaVenta=new VentaNotaVenta();
		seguroSeleccionado=new Seguro();
		detalleNotaVenta=new VentaDetalleNotaVenta();
		listaNotaDeVenta=notaVentaDao.obtenerPorCompania(sessionDao.getCompania());
	}


	//ACTION

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
		currentPage = "/pages/ventas/notaventa/edit.xhtml";
		int i=0;
		for (VentaDetalleNotaVenta ventaCotizacionDetalle : notaVenta.getListaDetalles()) {
			ventaCotizacionDetalle.setIdcorrelativo(i);
			System.out.println("El DETALLE ES :"+ventaCotizacionDetalle);
			i++;

		}
	}


	public void actionNuevo(){
		notaVenta=new VentaNotaVenta();
		detalleNotaVenta=new VentaDetalleNotaVenta();
		nuevo = false;
		seleccionado = false;
		registrar = true;
		modificacion=false;
		idTablaProductos="form001:singleDT2";
		//listaTraspasoProducto=new ArrayList<>();
		currentPage = "/pages/ventas/notaventa/edit.xhtml";
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
		currentPage = "/pages/ventas/notaventa/list.xhtml";
	}

	public void mostrarPrecios(){
		detalleNotaVenta.setPrecioUnitario(detalleNotaVenta.getServicio().getPrecioVenta());
		detalleNotaVenta.setCantidad(1);
		detalleNotaVenta.setPrecioSubTotal(detalleNotaVenta.getPrecioUnitario()*detalleNotaVenta.getCantidad());
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
		if (notaVenta.getListaDetalles().size()>0) {
			notaVenta.setFechaRegistro(new Date());
			notaVenta.setCompania(sessionDao.getCompania());
			notaVenta.setEstado("AC");
			notaVenta.setUsuarioRegistro("admin");
			notaVenta.setPrecioTotalBs(total);
			//FACTURACION
			cargarFacturaVenta();
			//CONTABILIDAD
			cargarContabilidad();
			VentaNotaVenta oT =notaVentaDao.registrar(notaVenta);
			if(oT!=null){
				loadDefault();				
				currentPage = "/pages/ventas/notaventa/list.xhtml";      
				if(ordenDeLaboratorio!=null){
				ordenDeLaboratorio.setEstado("AP");
				ordenLabDao.modificar(ordenDeLaboratorio);
				}	
				}
		} else {
			FacesUtil.infoMessage("Informacion", "El detalle no debe estar vacio");
		}
	}
	public void modificarCotizacion(){

		if (notaVenta.getListaDetalles().size()>0) {
			notaVenta.setPrecioTotalBs(total);
			VentaNotaVenta oT =notaVentaDao.modificar(notaVenta);
			if(oT!=null){
				loadDefault();
				currentPage = "/pages/ventas/notaventa/list.xhtml";
			}
		} else {
			FacesUtil.infoMessage("Informacion", "El detalle no debe estar vacio");
		}

	}
	/*DETALLE NOTA DE VENTA*/
	public void cancelarOrdenLab(){

		numeroOrden="";
	}
	public void calcularTotal(){

		for (VentaDetalleNotaVenta ventaCotizacionDetalle : notaVenta.getListaDetalles()) {
			total=total+ventaCotizacionDetalle.getPrecioSubTotal();	
		}
	}

	private FacturaVenta cargarFacturaVenta(){
		int numeroFactura = 1;
		String numeroAutorizacion = "1234567890";
		String llaveControl = "74c28n348723yujdhs7834y273ym4k238h2k38uhs278ehni72387hjs8e";
		Date fechaFactura = notaVenta.getFechaRegistro();
		String nitCi = "1234567890";
		double totalFacturado = 1;
		double totalBolivianos = 1;
		FacturaVenta fv = new FacturaVenta();
		fv.setNumeroFactura(String.valueOf(numeroFactura));
		fv.setCambio(0);
		fv.setCodigoControl(FacturacionUtil.obtenerCodigoControl(fechaFactura, numeroFactura, numeroAutorizacion, llaveControl, totalBolivianos, nitCi));
		fv.setCodigoRespuestaRapida(FacturacionUtil.armarCadenaQR(nitCi, numeroFactura, numeroAutorizacion, fechaFactura, totalFacturado, fv.getCodigoControl(), nitCi));
		fv.setConcepto("Venta");
		fv.setCreditoFiscal("0");
		fv.setDebitoFiscal(0d);
		fv.setEstado("AC");
		fv.setFechaRegistro(fechaFactura);
		fv.setFechaFactura(fechaFactura);
		fv.setFechaLimiteEmision(fechaFactura);
		fv.setImporteBaseDebitoFiscal(0d);
		fv.setImporteDescuentosBonificaciones(0d);
		fv.setImporteExportaciones(0d);
		fv.setImporteICE(0d);
		fv.setImporteSubTotal(0d);
		fv.setImporteVentasGrabadasTasaCero(0d);
		fv.setNumeroAutorizacion(numeroAutorizacion);
		fv.setTipoCambio(0d);
		fv.setTipoPago("EFECTIVO");
		fv.setTotalEfectivo(notaVenta.getPrecioTotalBs());
		fv.setTotalFacturado(notaVenta.getPrecioTotalBs());
		fv.setTotalLiteral(FacturacionUtil.obtenerMontoLiteral(totalFacturado));
		fv.setTotalPagar(0d);
		fv.setUsuarioRegistro(sessionDao.getUsuarioLogin().getLogin());
		fv.setNotaVenta(notaVenta);
		notaVenta.setFacturaVenta(fv);
		return fv;
	}

	private void cargarContabilidad(){
		TipoComprobante tc = new TipoComprobante();
		tc.setId(1);//INGRESO
		tc.setSigla("I");
		Comprobante co = new Comprobante();
		co.setFecha(notaVenta.getFechaRegistro());
		co.setFechaRegistro(notaVenta.getFechaRegistro());
		co.setNumero(comprobanteDao.obtenerNumeroComprobante(co.getFecha(),sessionDao.getEmpresaLogin(),tc));
		co.setCorrelativo(getPatternCorrelativo(co.getNumero(),tc.getSigla()));
		co.setCorrelativoTransaccional("");
		co.setEmpresa(sessionDao.getEmpresaLogin());
		co.setEstado("PR");
		co.setGestion(sessionDao.getGestionLogin());
		co.setGlosa("Ingreso por Venta de Servicios");
		co.setImporteLiteralExtranjero("");
		co.setImporteLiteralNacional("");
		co.setImporteTotalDebeExtranjero(0d);
		co.setImporteTotalDebeNacional(0d);
		co.setImporteTotalHaberExtranjero(0d);
		co.setImporteTotalHaberNacional(0d);
		co.setModuloTransaccion("VENTA");
		co.setNombre("");
		co.setSimboloMoneda("Bs");
		co.setSucursal(sessionDao.getSucursalLogin());
		co.setTipoCambio(6.93);
		co.setTipoComprobante(tc);
		co.setTipoMoneda("NACIONAL");
		co.setUsuarioRegistro(sessionDao.getUsuarioLogin().getLogin());
		List<LineaContable> listLineaContable = new ArrayList<>();
		//HABER
		double totalDebeNacional = 0;
		double totalHaberNacional = 0;
		double totalDebeExtranjero = 0;
		double totalHaberExtranjero = 0;
		for (VentaDetalleNotaVenta ventaCotizacionDetalle : notaVenta.getListaDetalles()) {
			LineaContable lc1 = new LineaContable();
			lc1.setCentroCosto(null);
			lc1.setComprobante(co);
			lc1.setDebeExtranjero(0d);
			lc1.setDebeNacional(0d);
			lc1.setEstado("AC");
			lc1.setFecha(notaVenta.getFechaRegistro());
			lc1.setFechaRegistro(notaVenta.getFechaRegistro());
			lc1.setGlosa(co.getGlosa());
			lc1.setHaberExtranjero(ventaCotizacionDetalle.getPrecioSubTotal()/6.93);
			lc1.setHaberNacional(ventaCotizacionDetalle.getPrecioSubTotal());
			//System.out.println("Servicio: "+ventaCotizacionDetalle.getServicio().toString());
			PlanCuenta planCuentaServicio = ventaCotizacionDetalle.getServicio().getGrupoServicios().getCuentaIngreso();
			lc1.setPlanCuenta(planCuentaServicio);
			lc1.setUsuarioRegistro(sessionDao.getUsuarioLogin().getLogin());
			listLineaContable.add(lc1);
			totalDebeNacional = totalDebeNacional + ventaCotizacionDetalle.getPrecioSubTotal();
			totalDebeExtranjero = totalDebeExtranjero + ( ventaCotizacionDetalle.getPrecioSubTotal() / 6.93);
		}
		// DEBE ( CAJA )
		LineaContable lc2 = new LineaContable();
		lc2.setCentroCosto(null);
		lc2.setComprobante(co);  // <-------aqui descomentar
		lc2.setDebeExtranjero(totalDebeExtranjero);
		lc2.setDebeNacional(totalDebeNacional);
		lc2.setEstado("AC");
		lc2.setFecha(notaVenta.getFechaRegistro());
		lc2.setFechaRegistro(notaVenta.getFechaRegistro());
		lc2.setGlosa(co.getGlosa());
		lc2.setHaberExtranjero(0d);
		lc2.setHaberNacional(0d);
		PlanCuenta planCuentaCaja = new PlanCuenta();
		planCuentaCaja.setId(5);
		lc2.setPlanCuenta(planCuentaCaja);
		lc2.setUsuarioRegistro(sessionDao.getUsuarioLogin().getLogin());
		listLineaContable.add(lc2);  // <-------aqui descomentar
		//create comprobante
		co.setListLineaContable(listLineaContable);
		co.setImporteTotalDebeNacional(totalDebeNacional);
		co.setImporteTotalDebeExtranjero(totalDebeExtranjero);
		co.setImporteTotalHaberExtranjero(totalHaberExtranjero);
		co.setImporteTotalHaberNacional(totalHaberNacional);
		co = comprobanteDao.create(co);
		// CARGAR RELACION INTERMEDIA ENTRE COMPROBANTE Y NOTAVENTA
		NotaVentaComprobante notaVentaComprobante = new NotaVentaComprobante();
		notaVentaComprobante.setFechaRegistro(notaVenta.getFechaRegistro());
		notaVentaComprobante.setEstado("AC");
		notaVentaComprobante.setUsuarioRegistro(sessionDao.getUsuarioLogin().getLogin());
		notaVentaComprobante.setComprobante(co);
		notaVentaComprobante.setVentaNotaVenta(notaVenta);
		notaVenta.setNotaVentaComprobante(notaVentaComprobante);
	}
	
	private String getPatternCorrelativo(int comprobante,String sigla){
		// pather = "1508-000001";
		StringBuilder build = new StringBuilder();
		Date fecha = new Date(); 
		String year = new SimpleDateFormat("yy").format(fecha);
		String mes = new SimpleDateFormat("MM").format(fecha);
		return build.append(sigla).append(year).append(mes).append("-").append(String.format("%06d", comprobante)).toString();
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
		if (detalleNotaVenta.getServicio()!=null) {
			detalleNotaVenta.setIdcorrelativo(notaVenta.getListaDetalles().size());
			detalleNotaVenta.setEstado("AC");
			notaVenta.addDetalle(detalleNotaVenta);
			detalleNotaVenta=new VentaDetalleNotaVenta();
		} else {
			FacesUtil.infoMessage("Informacion", "Seleccione un examen");	
		}
	}
	public void eliminarDetalle(VentaDetalleNotaVenta detalleNotaVenta){

		if (detalleNotaVenta.getId()!=0) {
			detalleNotaVenta.setEstado("RM");
		}
		System.out.println("Entro en ELIMINAR");
		notaVenta.getListaDetalles().remove(detalleNotaVenta);
		for ( int i=detalleNotaVenta.getIdcorrelativo();i<notaVenta.getListaDetalles().size();i++) {			
			VentaDetalleNotaVenta dNV=notaVenta.getListaDetalles().get(i);
			dNV.setIdcorrelativo(i);		
		}
		this.detalleNotaVenta=new VentaDetalleNotaVenta();
	}
	public void modificarDetalle(VentaDetalleNotaVenta detalleNotaVenta){
		System.out.println("Entro en modificar el detalle global es;"+this.detalleNotaVenta);
		notaVenta.getListaDetalles().set(detalleNotaVenta.getIdcorrelativo(), detalleNotaVenta);
		System.out.println("Entro en modificar el detalle por parametro es;"+detalleNotaVenta);
		System.out.println("Entro en modificar el detalle global es;"+this.detalleNotaVenta);
		this.detalleNotaVenta=new VentaDetalleNotaVenta();
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
				VentaDetalleNotaVenta vcd=new VentaDetalleNotaVenta();
				vcd.setServicio(ventaServicios);
				vcd.setCantidad(1.0);
				vcd.setEstado("ac");
				vcd.setFechaRegistro(new Date());
				vcd.setUsuarioRegistro("admin");
				vcd.setPrecioUnitario(ventaServicios.getPrecioVenta());
				vcd.setPrecioSubTotal(ventaServicios.getPrecioVenta()*vcd.getCantidad());
				vcd.setIdcorrelativo(notaVenta.getListaDetalles().size());
				notaVenta.addDetalle(vcd);

			}

			FacesUtil.hideDialog("dlgProducto");
			ordenDeLaboratorio=ordenLabDao.obtenerPorCodigo(numeroOrden);
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
		for (VentaDetalleNotaVenta ventaCotizacionDetalle : notaVenta.getListaDetalles()) {
			total=total+ventaCotizacionDetalle.getPrecioSubTotal();	
		}
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public VentaNotaVenta getNotaVenta() {
		return notaVenta;
	}

	public void setNotaVenta(VentaNotaVenta notaVenta) {
		this.notaVenta = notaVenta;
	}

	public VentaDetalleNotaVenta getDetalleNotaVenta() {
		return detalleNotaVenta;
	}

	public void setDetalleNotaVenta(VentaDetalleNotaVenta detalleNotaVenta) {
		this.detalleNotaVenta = detalleNotaVenta;
	}

	public List<VentaNotaVenta> getListaNotaDeVenta() {
		return listaNotaDeVenta;
	}

	public void setListaNotaDeVenta(List<VentaNotaVenta> listaNotaDeVenta) {
		this.listaNotaDeVenta = listaNotaDeVenta;
	}


}
