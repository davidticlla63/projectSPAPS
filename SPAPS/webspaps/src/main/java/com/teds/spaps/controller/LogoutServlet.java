package com.teds.spaps.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean sessionActive = request.getUserPrincipal() == null?false:true;
		if(sessionActive){
			try{
				System.out.println("LogoutServlet");
		
				response.setHeader("Cache-Control", "no-cache, no-store");
				response.setHeader("Pragma", "no-cache");
				
				request.getSession().invalidate();//remove session.
				request.logout();//JAAS log out! do NOT work? (servlet specification)
				response.sendRedirect(request.getContextPath());
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("Session Inactive");
			response.sendRedirect(request.getContextPath());
		}
	}
}