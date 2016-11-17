package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "plan_cuenta", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"codigo_auxiliar","id_empresa"}))
public class PlanCuenta implements Serializable {
	
	private static final long serialVersionUID = 4553321307847606337L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	/**
	 * Ej:
	 * - 1
	 * - 11
	 * - 111
	 * - 2
	 * - 21
	 */
	@Column(name="codigo",nullable=false )
	private String codigo;
	
	/**
	 * Nombre de la Cuenta
	 */
	@Column(name="descripcion",nullable=false )
	private String descripcion;
	
	/**
	 * - CONTROL
	 * - AUXILIAR
	 */
	@Column(name="clase",nullable=false )
	private String clase;
	
	/**
	 * Ej:Rellenado con ceros
	 * - 100000000
	 * - 110000000
	 * - 111000000
	 * - 200000000
	 * - 210000000
	 */
	@Column(name="codigo_auxiliar",nullable=true )
	private String codigoAuxiliar;
	
	/**
	 * - SI
	 * - NO
	 */
	@Column(name="ufv",nullable=true )
	private boolean ufv;
	
	/**
	 * - SI
	 * - NO
	 */
	@Column(name="centro_costo",nullable=true )
	private String centroCosto;
	
	/**
	 * - Ajuste Por Inflacion (API)
	 * - Ajuste Por Diferencia de Cambio (APDC)
	 * - Ajuste Por Correccion Monetaria (APCM)
	 * - Ajuste al capital (AAC)
	 * - Ajueste de reservas Patrimoniales (ARP)
	 * - Sin Ajuste (SA)
	 */
	@Column(name="ajuste",nullable=true )
	private String ajuste;
	
	/**
	 * - Ambos (A)
	 * - Movimiento al debe (MD)
	 * - Movimiento al haber (MH)
	 * - Ninguno (N)
	 */
	@Column(name="movimiento",nullable=true )
	private String movimiento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_cuenta",nullable=true )
	private TipoCuenta tipoCuenta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_plan_cuenta_padre",nullable=true )
	private PlanCuenta planCuentaPadre;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_moneda_empresa",nullable=true )
	private MonedaEmpresa monedaEmpresa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_empresa",nullable=true )
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_nivel",nullable=false )
	private Nivel nivel;

	private Date fecha;
	
	@Size(max = 2) //AC , IN
	private String estado;
	
	@Column(name="usuario_registro",nullable=false )
	private String usuarioRegistro;

//	@OneToMany( fetch=FetchType.LAZY, mappedBy="plancuenta", cascade = CascadeType.ALL)
//    private  Set<PlanCuenta> planCuenta;
	
	@OneToMany(mappedBy="planCuenta", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private  List<LineaContable> lineaContable;

	public PlanCuenta() {
		super();
		this.id = 0;
		this.codigo = "";
		this.descripcion = "";
		this.clase = "CONTROL";
		this.ufv = false;
		this.centroCosto = "NO";
		this.ajuste = "SA";
		this.movimiento = "A";
	}
	
	@Override
	public String toString() {
		return "PlanCuenta [id=" + id + ", codigo=" + codigo + ", descripcion="
				+ descripcion + ", clase=" + clase + ", codigoAuxiliar="
				+ codigoAuxiliar + ", ufv=" + ufv + ", tipoCuenta="
				+ tipoCuenta + ", planCuentaPadre=" + planCuentaPadre
				+ ", monedaEmpresa=" + monedaEmpresa + ", empresa=" + empresa
				+ ", nivel=" + nivel + ", fecha=" + fecha + ", estado="
				+ estado + ", usuarioRegistro=" + usuarioRegistro
				+ ", lineaContable=" + lineaContable + "]";
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}else{
			if(!(obj instanceof PlanCuenta)){
				return false;
			}else{
				if(((PlanCuenta)obj).id==this.id){
					return true;
				}else{
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PlanCuenta getPlanCuentaPadre() {
		return planCuentaPadre;
	}

	public void setPlanCuentaPadre(PlanCuenta planCuentaPadre) {
		this.planCuentaPadre = planCuentaPadre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta TipoCuenta) {
		this.tipoCuenta = TipoCuenta;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public boolean isUfv() {
		return ufv;
	}

	public void setUfv(boolean ufv) {
		this.ufv = ufv;
	}
	
	public MonedaEmpresa getMonedaEmpresa() {
		return monedaEmpresa;
	}

	public void setMonedaEmpresa(MonedaEmpresa monedaEmpresa) {
		this.monedaEmpresa = monedaEmpresa;
	}

	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}

	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}

//	public Set<PlanCuenta> getPlanCuenta() {
//		return planCuenta;
//	}
//
//	public void setPlanCuenta(Set<PlanCuenta> planCuenta) {
//		this.planCuenta = planCuenta;
//	}

	public List<LineaContable> getLineaContable() {
		return lineaContable;
	}

	public void setLineaContable(List<LineaContable> lineaContable) {
		this.lineaContable = lineaContable;
	}

	public String getAjuste() {
		return ajuste;
	}

	public void setAjuste(String ajuste) {
		this.ajuste = ajuste;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}
}