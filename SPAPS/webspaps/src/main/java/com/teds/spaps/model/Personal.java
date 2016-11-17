package com.teds.spaps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.teds.spaps.enums.Dias;
import com.teds.spaps.enums.Sexo;

/**
 * Entity implementation class for Entity: Personal
 *
 */
@Entity
@Table(name = "personal", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {
		"id", "id_sucursal" }))
public class Personal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "codigo", nullable = false)
	@Size(max = 50)
	private String codigo;

	@Column(name = "nombre", nullable = false)
	@Size(max = 255)
	private String nombre;

	@Size(max = 100)
	@Column(name = "apellido_paterno", nullable = false)
	private String apellidoPaterno;

	@Size(max = 100)
	@Column(name = "apellido_materno", nullable = false)
	private String apellidoMaterno;

	@Column(name = "direccion", nullable = true)
	@Size(max = 255)
	private String direccion;

	@Column(name = "lugar_atencion", nullable = true)
	@Size(max = 255)
	private String lugarAtencion;

	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;

	@Size(max = 255)
	@Column(name = "email", nullable = true)
	private String email;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	private boolean login = false;// se loguea

	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_cargo", nullable = true)
	private Cargo cargo;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;

	@Column(name = "foto_perfil", nullable = true)
	private byte[] fotoPerfil;

	@Column(name = "peso_foto", nullable = true)
	private int pesoFoto;

	@Column(name = "fecha_modificacion", nullable = true)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private String usuarioRegistro;
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "id_usuario", nullable = true)
	private Usuario usuario;

	@OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PersonalEspecialidad> listEspecialidades = new ArrayList<PersonalEspecialidad>();

	public Personal() {
		super();
		this.id = 0;
		this.nombre = "";
		this.apellidoPaterno = "";
		this.apellidoMaterno = "";
		this.direccion = "";
		this.telefono = "";
		this.estado = "AC";
		this.cargo = new Cargo();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = "";
		this.login = false;
		this.sexo= Sexo.MASCULINO;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
			if (!(obj instanceof Personal)) {
				return false;
			} else {
				if (((Personal) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * @return the fotoPerfil
	 */
	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}

	/**
	 * @param fotoPerfil
	 *            the fotoPerfil to set
	 */
	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	/**
	 * @return the pesoFoto
	 */
	public int getPesoFoto() {
		return pesoFoto;
	}

	/**
	 * @param pesoFoto
	 *            the pesoFoto to set
	 */
	public void setPesoFoto(int pesoFoto) {
		this.pesoFoto = pesoFoto;
	}

	public Especialidad obtenerEspecialidadActiva() {
		for (PersonalEspecialidad especialidad : listEspecialidades) {
			if (especialidad.isEstado()) {
				return especialidad.getEspecialidad();
			}
		}
		return new Especialidad();
	}
	
	public List<RrHhDiaTurno> obtenerEspecialidadHorarios() {
		for (PersonalEspecialidad especialidad : listEspecialidades) {
			if (especialidad.isEstado()) {
				return especialidad.getListDiaTurnos();
			}
		}
		return new ArrayList<RrHhDiaTurno>();
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the lugarAtencion
	 */
	public String getLugarAtencion() {
		return lugarAtencion;
	}

	/**
	 * @param lugarAtencion
	 *            the lugarAtencion to set
	 */
	public void setLugarAtencion(String lugarAtencion) {
		this.lugarAtencion = lugarAtencion;
	}

	/**
	 * @return the listEspecialidades
	 */
	public List<PersonalEspecialidad> getListEspecialidades() {
		return listEspecialidades;
	}

	/**
	 * @param listEspecialidades
	 *            the listEspecialidades to set
	 */
	public void setListEspecialidades(
			List<PersonalEspecialidad> listEspecialidades) {
		this.listEspecialidades = listEspecialidades;
	}

	/**
	 * @return the sexo
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
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

	public SelectItem[] getDias() {
		SelectItem[] items = new SelectItem[7];
		int i = 0;
		for (Dias t : Dias.values()) {
			items[i++] = new SelectItem(t, t.getLabel());
		}
		return items;
	}

	public Dias[] getArrayDias() {
		return Dias.values();
	}

	/**
	 * @return the login
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(boolean login) {
		this.login = login;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
