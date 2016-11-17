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
@Table(name = "config_contable_compra", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"id_empresa"}))
public class ConfiguracionContableCompra implements Serializable {

	private static final long serialVersionUID = -8812447129953055293L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="tipo_comprabante", nullable=false )
	private String tipoComprabante;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="id_empresa", nullable=false)
	private Empresa empresa;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cuenta_banco_nacional", nullable=true)
	private PlanCuenta cuentaBancoNacional;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cuenta_banco_extranjero", nullable=true)
	private PlanCuenta cuentaBancoExtranjero;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cuenta_gasto", nullable=true)
	private PlanCuenta cuentaGasto;
	
	@Size(max = 2) //AC , IN
	private String estado;
	
	@Column(name="fecha_modificacion",nullable=true )
	private Date fechaModificacion;
	
	@Column(name="fecha_registro",nullable=false )
	private Date fechaRegistro;
	
	@Column(name="usuario_registro",nullable=false )
	private String UsuarioRegistro;

	public ConfiguracionContableCompra() {
		super();
		this.id = 0;
		this.estado = "AC";
		
	}
	
	@Override
	public String toString() {
		return ""+id ;
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
			if(!(obj instanceof ConfiguracionContableCompra)){
				return false;
			}else{
				if(((ConfiguracionContableCompra)obj).id==this.id){
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

	public String getUsuarioRegistro() {
		return UsuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		UsuarioRegistro = usuarioRegistro;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public PlanCuenta getCuentaGasto() {
		return cuentaGasto;
	}

	public void setCuentaGasto(PlanCuenta cuentaGasto) {
		this.cuentaGasto = cuentaGasto;
	}

	public PlanCuenta getCuentaBancoExtranjero() {
		return cuentaBancoExtranjero;
	}

	public void setCuentaBancoExtranjero(PlanCuenta cuentaBancoExtranjero) {
		this.cuentaBancoExtranjero = cuentaBancoExtranjero;
	}

	public PlanCuenta getCuentaBancoNacional() {
		return cuentaBancoNacional;
	}

	public void setCuentaBancoNacional(PlanCuenta cuentaBancoNacional) {
		this.cuentaBancoNacional = cuentaBancoNacional;
	}

	public String getTipoComprabante() {
		return tipoComprabante;
	}

	public void setTipoComprabante(String tipoComprabante) {
		this.tipoComprabante = tipoComprabante;
	}
	

}


