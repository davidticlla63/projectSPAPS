package com.teds.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.teds.spaps.interfaces.dao.INivelDao;
import com.teds.spaps.model.Empresa;
import com.teds.spaps.model.Nivel;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Stateless
public class NivelDao extends DataAccessObject<Nivel> implements INivelDao {

	public NivelDao(){
		super(Nivel.class);
	}
	
    public Nivel findByNivelEmpresa(int nivel,Empresa empresa) {
    	String query = "select em from Nivel em  where em.nivel="+nivel+" and em.empresa.id="+empresa.getId();
    	return  executeQuerySingleResult(query);
    }

	public List<Nivel> obtenerOrdenAscPorId(){
		return findAscAllOrderedByParameter("id");
	}

	public List<Nivel> obtenerOrdenDescPorId(){
		return findDescAllOrderedByParameter("id");
	}

	public Nivel obtenerPrimer(){
		try{
			return findAllActivosByMaxResultOrderByAsc(1).get(0);
		}catch(Exception e){
			return new Nivel();
		}
	}

//	public List<Nivel> obtenerPorConsulta(String query){
//		return findAllActivosByQueryAndTwoParameter("estado","AC","nombre", query);
//	}
	
}
