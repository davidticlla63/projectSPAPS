package com.teds.spaps.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mauriciobejaranorivera
 *
 */
public class FacturacionUtil {

	/**
	 * Convertir monstos en literal
	 * @param totalFactura
	 * @return
	 */
	public static String obtenerMontoLiteral(double totalFactura) {
		String totalLiteral;
		try {
			totalLiteral = NumerosToLetras.convertNumberToLetter(totalFactura);
			return totalLiteral;
		} catch (Exception e) {
			System.out.println("Error en obtenerMontoLiteral: "+ e.getMessage());
			return "Error Literal";
		}
	}
	
	public static String armarCadenaQR(String nit,int numeroFactura,String numeroAutorizacion,Date fechaFactura,double totalFacturado,String codigoControl,String nitCi) {
		String cadenaQR = "";
		try {
			cadenaQR = new String();

			// NIT emisor
			cadenaQR = cadenaQR.concat(nit);
			cadenaQR = cadenaQR.concat("|");

			// Numero de Factura
			cadenaQR = cadenaQR.concat(String.valueOf(numeroFactura));
			cadenaQR = cadenaQR.concat("|");

			// Numero de Autorizacion
			cadenaQR = cadenaQR.concat(numeroAutorizacion);
			cadenaQR = cadenaQR.concat("|");

			// Fecha de Emision
			cadenaQR = cadenaQR.concat(obtenerFechaEmision(fechaFactura));
			cadenaQR = cadenaQR.concat("|");

			// Total Bs
			cadenaQR = cadenaQR.concat(String.valueOf(totalFacturado));
			cadenaQR = cadenaQR.concat("|");

			// Importe Base para el Credito Fiscal
			cadenaQR = cadenaQR.concat(String.valueOf(totalFacturado));
			cadenaQR = cadenaQR.concat("|");

			// Codigo de Control
			cadenaQR = cadenaQR.concat(codigoControl);
			cadenaQR = cadenaQR.concat("|");

			// NIT / CI del Comprador
			cadenaQR = cadenaQR.concat(nitCi);
			cadenaQR = cadenaQR.concat("|");

			// Importe ICE/IEHD/TASAS [cuando corresponda]
			cadenaQR = cadenaQR.concat("0");
			cadenaQR = cadenaQR.concat("|");

			// Importe por ventas no Gravadas o Gravadas a Tasa Cero [cuando
			// corresponda]
			cadenaQR = cadenaQR.concat("0");
			cadenaQR = cadenaQR.concat("|");

			// Importe no Sujeto a Credito Fiscal [cuando corresponda]
			cadenaQR = cadenaQR.concat("0");
			cadenaQR = cadenaQR.concat("|");

			// Descuentos Bonificaciones y Rebajas Obtenidas [cuando
			// corresponda]
			cadenaQR = cadenaQR.concat("0");

			return cadenaQR;
		} catch (Exception e) {
			e.printStackTrace();
			return cadenaQR;
		}
	}
	
	public static String obtenerFechaEmision(Date fechaEmision) {
		try {
			String DATE_FORMAT = "dd/MM/yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(fechaEmision);

		} catch (Exception e) {
			return "Error Fecha Emision";
		}
	}
	
	public static String obtenerCodigoControl(Date fechaFactura,int numeroFactura,String numeroAutorizacion,String llaveControl, double totalBolivianos, String nitCi) {
		try {
			CodigoControl7 cc = new CodigoControl7();
			int montoFactura = (int) totalBolivianos;
			cc.setNumeroAutorizacion(numeroAutorizacion);
			cc.setNumeroFactura(numeroFactura);
			cc.setNitci(nitCi);
			cc.setFechaTransaccion(fechaFactura);
			cc.setMonto(montoFactura);
			cc.setLlaveDosificacion(llaveControl);
			// Obtener Codigo Control V7
			String codigoControlV7 = cc.obtener();
			return codigoControlV7;
		} catch (Exception e) {
			return "Error CC";
		}
	}
	
}
