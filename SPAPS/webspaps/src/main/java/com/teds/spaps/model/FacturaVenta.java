package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "factura_venta", schema = "public")
public class FacturaVenta implements Serializable {

	private static final long serialVersionUID = -8004384251955592126L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "concepto", nullable = false)
	private String concepto;

	@Column(name = "credito_fiscal", nullable = false)
	private String creditoFiscal;

	@Column(name = "numero_autorizacion", nullable = false)
	private String numeroAutorizacion;

	@Column(name = "total_efectivo", nullable = false)
	private double totalEfectivo;

	@Column(name = "total_pagar", nullable = false)
	private double totalPagar;

	@Column(name = "cambio", nullable = false)
	private double cambio;

	@Column(name = "total_literal", nullable = false)
	private String totalLiteral;

	@Column(name = "fecha_limite_emision", nullable = false)
	private Date fechaLimiteEmision;

	@Column(name = "fecha_factura", nullable = false)
	private Date fechaFactura;

	@Column(name = "numero_factura", nullable = false)
	private String numeroFactura;

	@Column(name = "total_facturado", nullable = false)
	private double totalFacturado;

	@Column(name = "codigo_control", nullable = false)
	private String codigoControl;

	@Column(name = "codigo_respuesta_rapida", nullable = false)
	private String codigoRespuestaRapida;//

	@Column(name = "tipo_pago", nullable = false)
	private String tipoPago;

	@Column(name = "tipo_cambio", nullable = false)
	private double tipoCambio;

	@Column(name = "importe_ice", nullable = false)
	private double importeICE;// campos para libro ventas
	
	@Column(name = "importe_exportaciones", nullable = false)
	private double importeExportaciones;// Excepciones
	
	@Column(name = "importe_ventas_grabadas_tasa_cero", nullable = false)
	private double importeVentasGrabadasTasaCero;// neto
	
	@Column(name = "importe_subtotal", nullable = false)
	private double importeSubTotal;
	
	@Column(name = "importe_descuentos_bonificaciones", nullable = false)
	private double importeDescuentosBonificaciones;
	
	@Column(name = "importe_base_debito_fiscal", nullable = false)
	private double importeBaseDebitoFiscal;
	
	@Column(name = "debito_fiscal", nullable = false)
	private double debitoFiscal;// IVA
	
	@Size(max = 2)// AC , IN
	private String estado;

	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;

	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nota_venta", nullable = true)
	private VentaNotaVenta notaVenta;

	public FacturaVenta() {
		super();
		this.id = 0;
		importeICE = 0;
		importeExportaciones = 0;
		importeVentasGrabadasTasaCero = 0;
		importeSubTotal = 0;
		importeDescuentosBonificaciones = 0;
		importeBaseDebitoFiscal = 0;
		debitoFiscal = 0;
	}

	

	@Override
	public String toString() {
		return "FacturaVenta [id=" + id + ", concepto=" + concepto
				+ ", creditoFiscal=" + creditoFiscal + ", numeroAutorizacion="
				+ numeroAutorizacion + ", totalEfectivo=" + totalEfectivo
				+ ", totalPagar=" + totalPagar + ", cambio=" + cambio
				+ ", totalLiteral=" + totalLiteral + ", fechaLimiteEmision="
				+ fechaLimiteEmision + ", fechaFactura=" + fechaFactura
				+ ", numeroFactura=" + numeroFactura + ", totalFacturado="
				+ totalFacturado + ", codigoControl=" + codigoControl
				+ ", codigoRespuestaRapida=" + codigoRespuestaRapida
				+ ", tipoPago=" + tipoPago + ", tipoCambio=" + tipoCambio
				+ ", importeICE=" + importeICE + ", importeExportaciones="
				+ importeExportaciones + ", importeVentasGrabadasTasaCero="
				+ importeVentasGrabadasTasaCero + ", importeSubTotal="
				+ importeSubTotal + ", importeDescuentosBonificaciones="
				+ importeDescuentosBonificaciones
				+ ", importeBaseDebitoFiscal=" + importeBaseDebitoFiscal
				+ ", debitoFiscal=" + debitoFiscal + ", estado=" + estado
				+ ", fechaRegistro=" + fechaRegistro + ", fechaModificacion="
				+ fechaModificacion + ", usuarioRegistro=" + usuarioRegistro
				+ ", notaVenta=" + notaVenta+ "]";
	}



	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (!(obj instanceof FacturaVenta)) {
				return false;
			} else {
				if (((FacturaVenta) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getCodigoControl() {
		return codigoControl;
	}

	public void setCodigoControl(String codigoControl) {
		this.codigoControl = codigoControl;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public double getTotalEfectivo() {
		return totalEfectivo;
	}

	public void setTotalEfectivo(double totalEfectivo) {
		this.totalEfectivo = totalEfectivo;
	}

	public double getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(double totalPagar) {
		this.totalPagar = totalPagar;
	}

	public double getCambio() {
		return cambio;
	}

	public void setCambio(double cambio) {
		this.cambio = cambio;
	}

	public String getTotalLiteral() {
		return totalLiteral;
	}

	public void setTotalLiteral(String totalLiteral) {
		this.totalLiteral = totalLiteral;
	}

	public Date getFechaLimiteEmision() {
		return fechaLimiteEmision;
	}

	public void setFechaLimiteEmision(Date fechaLimiteEmision) {
		this.fechaLimiteEmision = fechaLimiteEmision;
	}

	public double getTotalFacturado() {
		return totalFacturado;
	}

	public void setTotalFacturado(double totalFacturado) {
		this.totalFacturado = totalFacturado;
	}

	public String getCodigoRespuestaRapida() {
		return codigoRespuestaRapida;
	}

	public void setCodigoRespuestaRapida(String codigoRespuestaRapida) {
		this.codigoRespuestaRapida = codigoRespuestaRapida;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public double getImporteICE() {
		return importeICE;
	}

	public void setImporteICE(double importeICE) {
		this.importeICE = importeICE;
	}

	public double getImporteExportaciones() {
		return importeExportaciones;
	}

	public void setImporteExportaciones(double importeExportaciones) {
		this.importeExportaciones = importeExportaciones;
	}

	public double getImporteVentasGrabadasTasaCero() {
		return importeVentasGrabadasTasaCero;
	}

	public void setImporteVentasGrabadasTasaCero(
			double importeVentasGrabadasTasaCero) {
		this.importeVentasGrabadasTasaCero = importeVentasGrabadasTasaCero;
	}

	public double getImporteSubTotal() {
		return importeSubTotal;
	}

	public void setImporteSubTotal(double importeSubTotal) {
		this.importeSubTotal = importeSubTotal;
	}

	public double getImporteBaseDebitoFiscal() {
		return importeBaseDebitoFiscal;
	}

	public void setImporteBaseDebitoFiscal(double importeBaseDebitoFiscal) {
		this.importeBaseDebitoFiscal = importeBaseDebitoFiscal;
	}

	public double getImporteDescuentosBonificaciones() {
		return importeDescuentosBonificaciones;
	}

	public void setImporteDescuentosBonificaciones(
			double importeDescuentosBonificaciones) {
		this.importeDescuentosBonificaciones = importeDescuentosBonificaciones;
	}

	public double getDebitoFiscal() {
		return debitoFiscal;
	}

	public void setDebitoFiscal(double debitoFiscal) {
		this.debitoFiscal = debitoFiscal;
	}

	public String getCreditoFiscal() {
		return creditoFiscal;
	}

	public void setCreditoFiscal(String creditoFiscal) {
		this.creditoFiscal = creditoFiscal;
	}

	public VentaNotaVenta getNotaVenta() {
		return notaVenta;
	}

	public void setNotaVenta(VentaNotaVenta notaVenta) {
		this.notaVenta = notaVenta;
	}

}
