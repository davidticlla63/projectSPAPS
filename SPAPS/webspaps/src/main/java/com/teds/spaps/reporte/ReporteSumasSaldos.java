package com.teds.spaps.reporte;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.teds.spaps.util.Conexion;

/**
 * 
 * @author mauriciobejaranorivera
 *
 */
@WebServlet("/ReportSumasSaldos")
public class ReporteSumasSaldos  extends HttpServlet{

	private static final long serialVersionUID = 4293037836240051425L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		JasperReport jasperReport;
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup(Conexion.datasourse);
			conn = ds.getConnection();

			Integer pIdEmpresa = Integer.parseInt(request.getParameter("pIdEmpresa"));
			String  pUsuario = request.getParameter("pUsuario");
			Integer pFechaFin = Integer.parseInt(request.getParameter("pFechaFin"));
			String pTitulo = request.getParameter("pTitulo");
			String pTipoMoneda = request.getParameter("pTipoMoneda");
			String pSimboloMoneda = request.getParameter("pSimboloMoneda");
			
			String urlPath = request.getRequestURL().toString();
			urlPath = urlPath.substring(0, urlPath.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

			String rutaReporte = urlPath+"resources/report/contabilidad/sumas_saldos.jasper";

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("pIdEmpresa",pIdEmpresa);
			parameters.put("pUsuario",pUsuario);
			parameters.put("pFechaFin",pFechaFin);
			parameters.put("pTitulo", pTitulo);
			parameters.put("pTipoMoneda",pTipoMoneda);
			parameters.put("pSimboloMoneda", pSimboloMoneda);
			System.out.println(" Maps : "+parameters);

			jasperReport = (JasperReport)JRLoader.loadObject (new URL(rutaReporte));
			JasperPrint jasperPrint2 = JasperFillManager.fillReport(jasperReport, parameters, conn);
			response.setContentType("application/pdf");// text/html  application/pdf   application/html
			JasperExportManager.exportReportToPdfStream(jasperPrint2,servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());			
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
