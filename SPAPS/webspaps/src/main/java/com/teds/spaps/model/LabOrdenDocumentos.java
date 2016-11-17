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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.primefaces.model.UploadedFile;

/**
 * Entity implementation class for Entity: Cargo
 *
 */
@Entity
@Table(name = "lab_orden_documentos", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class LabOrdenDocumentos implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "path_imagen", nullable = true)
	private String pathImagen;

	@Transient
	private UploadedFile file;

	private String comentario;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	/*
	 * pendiente = PE aprobado = AP vigente = VI atrasado = AT procesado = PR
	 */
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden_detalle", nullable = false)
	private LabOrdenDetalleImag ordenDetalleImag;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private LabOrdenImag orden;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public LabOrdenDocumentos() {
		super();
		this.id = 0;
		this.estado = "AC";
		this.usuarioRegistro = "";
	}

	/**
	 * @param pathImagen
	 * @param enviado
	 * @param ordenDetalleImag
	 * @param orden
	 * @param fechaRegistro
	 * @param usuarioRegistro
	 */
	public LabOrdenDocumentos(String pathImagen,
			LabOrdenDetalleImag ordenDetalleImag, LabOrdenImag orden,
			Date fechaRegistro, String usuarioRegistro) {
		super();
		this.pathImagen = pathImagen;
		this.ordenDetalleImag = ordenDetalleImag;
		this.orden = orden;
		this.fechaRegistro = fechaRegistro;
		this.usuarioRegistro = usuarioRegistro;
	}

	public Integer getId() {
		return id;
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
		return getPathImagen();
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
			if (!(obj instanceof LabOrdenDocumentos)) {
				return false;
			} else {
				if (((LabOrdenDocumentos) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the orden
	 */
	public LabOrdenImag getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(LabOrdenImag orden) {
		this.orden = orden;
	}

	/**
	 * @return the pathImagen
	 */
	public String getPathImagen() {
		return pathImagen;
	}

	/**
	 * @param pathImagen
	 *            the pathImagen to set
	 */
	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
