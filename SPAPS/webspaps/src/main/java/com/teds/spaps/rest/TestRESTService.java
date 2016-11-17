package com.teds.spaps.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@Path("/1")
@RequestScoped
public class TestRESTService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> listAllMembers() {
		return new ArrayList<>();
	}

}
