package com.teds.spaps.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.teds.spaps.enums.Sexo;

/**
 * Entity implementation class for Entity: Individuo
 *
 */
@Entity
@Table(name = "paciente", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Paciente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8925177463571716333L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 20)
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Size(max = 100)
	@Column(name = "apellido_paterno", nullable = false)
	private String apellidoPaterno;
	@Size(max = 100)
	@Column(name = "apellido_materno", nullable = true, insertable = true, updatable = true)
	private String apellidoMaterno;
	@Size(max = 255)
	@Column(name = "correo", nullable = true, insertable = true, updatable = true)
	private String correo;
	@Size(max = 255)
	@Column(name = "direccion", nullable = true, insertable = true, updatable = true)
	private String direccion;
	@Column(name = "edad", nullable = true, insertable = true, updatable = true)
	private Integer edad;
	@Column(name = "fecha_ingreso", nullable = false)
	private Date fechaIngreso;
	@Column(name = "fecha_nacimiento", nullable = true, insertable = true, updatable = true)
	private Date fechaNacimiento;
	@Column(name = "imagen", nullable = true, insertable = true, updatable = true)
	private byte[] imagen;

	@Transient
	private StreamedContent streamedContent;

	// @Size(max = 1)
	// @Column(name = "sexo", nullable = false)
	// private String sexo;// MASCULINO = M, FEMENINO = F

	@Enumerated(EnumType.STRING)
	private Sexo sexo;// HOMBRE, MUJER, A= AMBOS, F= FEMENINO, M=
						// MASCULINO

	@Size(max = 255)
	@Column(name = "telefono", nullable = true, insertable = true, updatable = true)
	private String telefono;
	@Size(max = 30)
	@Column(name = "nit", nullable = true, insertable = true, updatable = true)
	private String nit;
	@Size(max = 1)
	@Column(name = "tipo_afiliacion", nullable = false)
	private String tipoAfiliacion;// particular = P, asegurado = S, empresa = E
	@Size(max = 1)
	@Column(name = "titular", nullable = true, insertable = true, updatable = true)
	private String titular;// titular = T, conyugue = C, dependiente = D

	@Size(max = 20)
	@Column(name = "codigo_hc", nullable = true, insertable = true, updatable = true)
	private String codigoHc;

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_parentesco", nullable = true, insertable = true, updatable = true)
	private Parentesco parentesco;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_grupo_sanguineo", nullable = true, insertable = true, updatable = true)
	private GrupoSanguineo grupoSanguineo;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_barrio", nullable = true, insertable = true, updatable = true)
	private Barrio barrio;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_cargo_trabajo", nullable = true, insertable = true, updatable = true)
	private CargoTrabajo cargo;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_grupo_familiar", nullable = true, insertable = true, updatable = true)
	private GrupoFamiliar grupoFamiliar;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_raza", nullable = true, insertable = true, updatable = true)
	private Raza raza;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_unidad_vecinal", nullable = true, insertable = true, updatable = true)
	private UnidadVecinal unidadVecinal;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_provincia", nullable = true, insertable = true, updatable = true)
	private Provincia provincia;
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_nivel_academico", nullable = true, insertable = true, updatable = true)
	private NivelAcademico nivelAcademico;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_estado_civil", nullable = false)
	private EstadoCivil estadoCivil;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;

	public Paciente() {
		super();
		this.id = 0;
		this.codigo = "";
		this.nombre = "";
		this.apellidoPaterno = "";
		this.apellidoMaterno = "";
		this.direccion = "";
		this.edad = 0;
		this.sexo = Sexo.MASCULINO;
		this.telefono = "";
		this.nit = "";
		this.estado = "AC";
		this.parentesco = null;
		this.grupoSanguineo = null;
		this.barrio = null;
		this.cargo = null;
		this.grupoFamiliar = null;
		this.raza = null;
		this.unidadVecinal = null;
		this.provincia = null;
		this.nivelAcademico = null;
		this.estadoCivil = null;
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
	}

	public Paciente(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public GrupoSanguineo getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public CargoTrabajo getCargo() {
		return cargo;
	}

	public void setCargo(CargoTrabajo cargo) {
		this.cargo = cargo;
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	public Raza getRaza() {
		return raza;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public UnidadVecinal getUnidadVecinal() {
		return unidadVecinal;
	}

	public void setUnidadVecinal(UnidadVecinal unidadVecinal) {
		this.unidadVecinal = unidadVecinal;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
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

	public String getTipoAfiliacion() {
		return tipoAfiliacion;
	}

	public void setTipoAfiliacion(String tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	private byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

	public StreamedContent getStreamedContent() {
		String mimeType = "image/jpg";
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(this.imagen);
			return new DefaultStreamedContent(new ByteArrayInputStream(
					toByteArrayUsingJava(is)), mimeType);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return getNombre() + " " + getApellidoPaterno() + " "
				+ getApellidoMaterno();
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
			if (!(obj instanceof Paciente)) {
				return false;
			} else {
				if (((Paciente) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the codigoHc
	 */
	public String getCodigoHc() {
		return codigoHc;
	}

	/**
	 * @param codigoHc
	 *            the codigoHc to set
	 */
	public void setCodigoHc(String codigoHc) {
		this.codigoHc = codigoHc;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public SelectItem[] getSexos() {
		SelectItem[] items = new SelectItem[2];
		int i = 0;
		for (Sexo t : Sexo.values()) {
			if (t == Sexo.MASCULINO || t == Sexo.FEMENINO) {
				items[i++] = new SelectItem(t, t.getLabel());

			}
		}
		return items;
	}

}
