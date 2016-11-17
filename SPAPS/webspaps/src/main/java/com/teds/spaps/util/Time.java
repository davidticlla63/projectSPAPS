package com.teds.spaps.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class Time implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1808620452373692675L;

	@SuppressWarnings("resource")
	static public String rellendarCeros(Integer numCeros, Integer correlativo) {
		Formatter fmt = new Formatter();
		fmt.format("%0" + numCeros + "d", correlativo);
		String numero = fmt.toString();
		return numero;
	}

	static public int calcularEdad(Date fechaNac) {

		LocalDate ahora = LocalDate.now();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fechaNac);
		// String fecha = dt.format(fechaNac);
		System.out.println(fecha);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate nacimiento = LocalDate.parse(fecha, fmt);
		Period periodo = Period.between(nacimiento, ahora);
		System.out.printf("Tu edad es: %s años, %s meses y %s días",
				periodo.getYears(), periodo.getMonths(), periodo.getDays());

		return periodo.getYears();
	}

	static public Period calcularEdadMesesYAnio(Date fechaNac) {
		String fecha = convertSimpleDateToString(fechaNac);
		System.out.println(fecha);
		// 01/01/2000
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaNa = LocalDate.parse(fecha, fmt);

		LocalDate ahora = LocalDate.now();

		Period periodo = Period.between(fechaNa, ahora);
		System.out.printf("Tu edad es: %s años, %s meses y %s días",
				periodo.getYears(), periodo.getMonths(), periodo.getDays());
		System.out.println(periodo.toString());
		return periodo;
	}

	/**
	 * 
	 * @param fechaNacimiento
	 * @return NIÑO, ADULTO
	 */
	static public String obtenerTipoEdad(Date fechaNacimiento) {
		int anios = calcularEdad(fechaNacimiento);
		System.out.println(anios);
		if (anios >= 0 && anios <= 15) {
			System.out.println("NIÑO");
			return "NIÑO";
		}
		if (anios > 15) {
			System.out.println("ADULTO");
			return "ADULTO";
		}
		return "ERROR";
	}

	@SuppressWarnings("deprecation")
	static public String convertTimeToString(Date time) {
		return time.getHours() + ":" + time.getMinutes() + ":"
				+ time.getSeconds();

	}

	static public String convertDateToString(Date time) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fecha = dt.format(time);
		System.out.println("Fecha: " + fecha);
		return fecha;

	}

	static public String convertSimpleDateToString(Date time) {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = dt.format(time);
		System.out.println("Fecha: " + fecha);
		return fecha;

	}

	public static Date setCeroMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		/*
		 * calendar.set(Calendar.SECOND, 0); calendar.set(Calendar.MINUTE, 0);
		 * calendar.set(Calendar.HOUR, 0);
		 */

		return calendar.getTime();
	}

	public static Date sumMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
		return calendar.getTime();
	}

	public static Date diferenceMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - minutes);
		return calendar.getTime();
	}

	public static Date sumHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + hour);
		return calendar.getTime();
	}

	public static Date diferenceHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - hour);
		return calendar.getTime();
	}

	@SuppressWarnings("unused")
	static public boolean may(Date time1, Date time2) {

		long lantes = time1.getTime();
		long lahora = time2.getTime();

		long hora = (lahora - lantes) / 3600000;
		if (lantes > lahora) {
			return true;
		} else {
			return false;
		}

	}

	static public Date sumarRestarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de días a añadir, o
													// restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos
									// días añadidos

	}

	static public Date sumarRestarHorasFecha(Date fecha, int horas) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.HOUR_OF_DAY, horas); // numero de días a añadir, o
													// restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos
									// días añadidos

	}

	static public String obtenerFormatoYYYYMMDD(Date date) {
		try {
			String DATE_FORMAT = "yyyyMMdd";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			String fecha = sdf.format(date);
			System.out.println(fecha);
			return fecha;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en obtenerFormatoYYYYMMDD: "
					+ e.getMessage());
			return null;
		}
	}

	static public String obtenerFormatoYYYYMMDDHH(Date date) {
		try {
			String DATE_FORMAT = "yyyyMMddkk";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			String fecha = sdf.format(date);
			System.out.println(fecha);
			return fecha;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en obtenerFormatoYYYYMMDDHH: "
					+ e.getMessage());
			return null;
		}
	}

	static public String obtenerFormatoYYYYMMDDHHMISS(Date date) {
		try {
			String DATE_FORMAT = "yyyyMMddkkmmss";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			String fecha = sdf.format(date);
			System.out.println(fecha);
			return fecha;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en obtenerFormatoYYYYMMDDHHMISS: "
					+ e.getMessage());
			return null;
		}
	}

	static public String mes(int mes) {
		switch (mes) {
		case 1:
			return "Enero";
		case 2:
			return "Febrero";
		case 3:
			return "Marzo";
		case 4:
			return "Abril";
		case 5:
			return "Mayo";
		case 6:
			return "Junio";
		case 7:
			return "Julio";
		case 8:
			return "Agosto";
		case 9:
			return "Septiembre";
		case 10:
			return "Octubre";
		case 11:
			return "Noviembre";
		case 12:
			return "Diciembre";

		}
		return null;
	}

	static public String obtenerFormatoYYYYMM(Date date) {
		try {
			String DATE_FORMAT = "yyyyMM";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(date);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en obtenerFormatoYYYYMM: "
					+ e.getMessage());
			return null;
		}
	}

	static public String obtenerFormatoYYYY(Date date) {
		try {
			String DATE_FORMAT = "yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(date);

		} catch (Exception e) {
			// TODO: handle exception
			System.out
					.println("Error en obtenerFormatoYYYY: " + e.getMessage());
			return null;
		}
	}

	static public String obtenerFormatoMM(Date date) {
		try {
			String DATE_FORMAT = "MM";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(date);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en obtenerFormatoMM: " + e.getMessage());
			return null;
		}
	}

	public static Date getPrimerDiaDelMes(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.getActualMinimum(Calendar.DAY_OF_MONTH),
				cal.getMinimum(Calendar.HOUR_OF_DAY),
				cal.getMinimum(Calendar.MINUTE),
				cal.getMinimum(Calendar.SECOND));
		System.out.println(cal.getTime());
		return cal.getTime();
	}

	public static Date getUltimoDiaDelMes(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.getActualMaximum(Calendar.DAY_OF_MONTH),
				cal.getMaximum(Calendar.HOUR_OF_DAY),
				cal.getMaximum(Calendar.MINUTE),
				cal.getMaximum(Calendar.SECOND));
		System.out.println(cal.getTime());
		return cal.getTime();
	}

	/**
	 * 
	 * @param value1 año inicial
	 * @param value2 ano Final
	 * @param vaue año del Paciente
	 * @return
	 */
	static public boolean yaerBetween(int value1, int value2, int vaue) {
		return vaue >= value1 && vaue <= value2;
	}
/**
 * 
 * @param data valor= 10A, 10M, 10D, DIAS , MESES, AÑOS
 * @return
 */
	static public int valueInt(String data) {
		return Integer.parseInt((data.substring(0, data.length() - 1)));
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String string = "10/08/1985";
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		String dia = "5D";
		System.out.println("Edad : "
				+ dia.substring(dia.length() - 1, dia.length()));
		try {
			date = format.parse(string);
			System.out.println("Edad : " + Time.obtenerTipoEdad(date));
			System.out.println(Time.calcularEdadMesesYAnio(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
