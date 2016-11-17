package com.teds.spaps.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Entity
@Table(name = "nivel", schema = "public")
public class Nivel implements Serializable {

	private static final long serialVersionUID = -4925169429377477966L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer nivel;
	
	private Integer nroDigito;
	
	@OneToMany(mappedBy="nivel" ,fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PlanCuenta> planCuenta;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_empresa", nullable=true)
	private Empresa empresa;

	public Nivel() {
		super();
		this.id = 0;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
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
			if(!(obj instanceof Nivel)){
				return false;
			}else{
				if(((Nivel)obj).id==this.id){
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

	public Integer getNroDigito() {
		return nroDigito;
	}

	public void setNroDigito(Integer nroDigito) {
		this.nroDigito = nroDigito;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Set<PlanCuenta> getPlanCuenta() {
		return planCuenta;
	}

	public void setPlanCuenta(Set<PlanCuenta> planCuenta) {
		this.planCuenta = planCuenta;
	}
}


