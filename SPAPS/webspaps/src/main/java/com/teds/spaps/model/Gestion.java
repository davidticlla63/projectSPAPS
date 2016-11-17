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
@Table(name = "gestion", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"gestion","id_empresa"}))
public class Gestion implements Serializable {

	private static final long serialVersionUID = 1017112418637684740L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String gestion;
	
	@Column(name="periodo")
	private String periodo;
	
	//AC= ACTIVA | CE= CERRADA
	@Column(name="estado_cierre" )
	private String estadoCierre;
	
	@Column(name="iniciada",nullable=true )
	private boolean iniciada;//al crear una toma de incentario inicial se pone en true
	
	@Column(name="periodo_actual")
	private String periodoActual;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="id_empresa", nullable=false)
	private Empresa empresa;
	
	@Size(max = 2) //AC , IN
	@Column(name="estado", nullable=true )
	private String estado;
	
	@Column(name="fecha_registro",nullable=false )
	private Date fechaRegistro;
	
	@Column(name="fecha_modificacion",nullable=true )
	private Date fechaModificacion;
	
	@Column(name="usuario_registro",nullable=false )
	private String usuarioRegistro;

	public Gestion() {
		super();
		this.id = 0;
		this.estadoCierre = "AC";
		this.estado = "AC";
	}
	
	@Override
	public String toString() {
		return "Gestion [id=" + id + ", gestion=" + gestion + ", periodo="
				+ periodo + ", estadoCierre=" + estadoCierre + ", iniciada="
				+ iniciada + ", periodoActual=" + periodoActual + ", empresa="
				+ empresa + ", estado=" + estado + ", fechaRegistro="
				+ fechaRegistro + ", fechaModificacion=" + fechaModificacion
				+ ", usuarioRegistro=" + usuarioRegistro + "]";
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
			if(!(obj instanceof Gestion)){
				return false;
			}else{
				if(((Gestion)obj).id==this.id){
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
	
	public String getGestion() {
		return gestion;
	}

	public void setGestion(String gestion) {
		this.gestion = gestion;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoCierre() {
		return estadoCierre;
	}

	public void setEstadoCierre(String estadoCierre) {
		this.estadoCierre = estadoCierre;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public boolean isIniciada() {
		return iniciada;
	}

	public void setIniciada(boolean iniciada) {
		this.iniciada = iniciada;
	}

	public String getPeriodoActual() {
		return periodoActual;
	}

	public void setPeriodoActual(String periodoActual) {
		this.periodoActual = periodoActual;
	}	

}


