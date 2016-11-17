package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "rr_hh_turno", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id" }))
public class RrHhTurno implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "hora_entrada_i", nullable = false)
	private String horaEntradaI;//ENTRADA ANTICIPADO
	
	@Column(name = "hora_entrada", nullable = false)
	private String horaEntrada;//ENTRADA NORMAL
	
	@Column(name = "hora_entrada_p", nullable = false)
	private String horaEntradaP;//ENTRADA TARDE
	
	@Column(name = "hora_salida_i", nullable = false)
	private String horaSalidaI;//SALIDA ANTICIPADO
	
	@Column(name = "hora_salida", nullable = false)
	private String horaSalida;//SALIDA NORMAL
	
	@Column(name = "hora_salida_p", nullable = false)
	private String horaSalidaP;//SALIDA TARDE
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public RrHhTurno() {
		super();
		this.id = 0;
		this.estado="AC";
		this.usuarioRegistro = "";
	}
	
	



	/**
	 * @param nombre
	 * @param horaEntradaI
	 * @param horaEntrada
	 * @param horaEntradaP
	 * @param horaSalidaI
	 * @param horaSalida
	 * @param horaSalidaP
	 * @param fechaModificacion
	 * @param fechaRegistro
	 * @param usuarioRegistro
	 */
	public RrHhTurno(String nombre, String horaEntradaI, String horaEntrada,
			String horaEntradaP, String horaSalidaI, String horaSalida,
			String horaSalidaP, Date fechaModificacion, Date fechaRegistro,
			String usuarioRegistro) {
		super();
		this.nombre = nombre;
		this.horaEntradaI = horaEntradaI;
		this.horaEntrada = horaEntrada;
		this.horaEntradaP = horaEntradaP;
		this.horaSalidaI = horaSalidaI;
		this.horaSalida = horaSalida;
		this.horaSalidaP = horaSalidaP;
		this.fechaModificacion = fechaModificacion;
		this.fechaRegistro = fechaRegistro;
		this.usuarioRegistro = usuarioRegistro;
	}





	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String descripcion) {
		this.nombre = descripcion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fecha_modificacion) {
		this.fechaModificacion = fecha_modificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fecha_registro) {
		this.fechaRegistro = fecha_registro;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuario_registro) {
		this.usuarioRegistro = usuario_registro;
	}

	@Override
	public String toString() {
		return getNombre();
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
			if (!(obj instanceof RrHhTurno)) {
				return false;
			} else {
				if (((RrHhTurno) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}



	/**
	 * @return the horaEntradaI
	 */
	public String getHoraEntradaI() {
		return horaEntradaI;
	}



	/**
	 * @param horaEntradaI the horaEntradaI to set
	 */
	public void setHoraEntradaI(String horaEntradaI) {
		this.horaEntradaI = horaEntradaI;
	}



	/**
	 * @return the horaEntrada
	 */
	public String getHoraEntrada() {
		return horaEntrada;
	}



	/**
	 * @param horaEntrada the horaEntrada to set
	 */
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}



	/**
	 * @return the horaEntradaP
	 */
	public String getHoraEntradaP() {
		return horaEntradaP;
	}



	/**
	 * @param horaEntradaP the horaEntradaP to set
	 */
	public void setHoraEntradaP(String horaEntradaP) {
		this.horaEntradaP = horaEntradaP;
	}



	/**
	 * @return the horaSalidaI
	 */
	public String getHoraSalidaI() {
		return horaSalidaI;
	}



	/**
	 * @param horaSalidaI the horaSalidaI to set
	 */
	public void setHoraSalidaI(String horaSalidaI) {
		this.horaSalidaI = horaSalidaI;
	}



	/**
	 * @return the horaSalida
	 */
	public String getHoraSalida() {
		return horaSalida;
	}



	/**
	 * @param horaSalida the horaSalida to set
	 */
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}



	/**
	 * @return the horaSalidaP
	 */
	public String getHoraSalidaP() {
		return horaSalidaP;
	}



	/**
	 * @param horaSalidaP the horaSalidaP to set
	 */
	public void setHoraSalidaP(String horaSalidaP) {
		this.horaSalidaP = horaSalidaP;
	}





	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}





	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}





	/**
	 * @return the compania
	 */
	public Compania getCompania() {
		return compania;
	}





	/**
	 * @param compania the compania to set
	 */
	public void setCompania(Compania compania) {
		this.compania = compania;
	}

}
