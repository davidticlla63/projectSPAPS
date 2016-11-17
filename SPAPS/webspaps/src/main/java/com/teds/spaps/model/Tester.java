/**
 * @author ANITA
 */
package com.teds.spaps.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ANITA
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<PlanesSeguro> lista = new ArrayList<>();
		PlanesSeguro planesSeguro = new PlanesSeguro();
		PlanSeguro planSeguro = new PlanSeguro();
		planSeguro.setId(1);
		planSeguro.setDescripcion("plan1");
		planesSeguro.setPlanSeguro(planSeguro);
		lista.add(planesSeguro);

		planesSeguro = new PlanesSeguro();
		planSeguro = new PlanSeguro();
		planSeguro.setId(2);
		planSeguro.setDescripcion("plan2");
		planesSeguro.setPlanSeguro(planSeguro);
		lista.add(planesSeguro);

		planesSeguro = new PlanesSeguro();
		planSeguro = new PlanSeguro();
		planSeguro.setId(3);
		planSeguro.setDescripcion("plan3");
		planesSeguro.setPlanSeguro(planSeguro);
		lista.add(planesSeguro);

		planesSeguro = lista.get(0);
		for (int i = 0; i < lista.size(); i++) {
			PlanesSeguro planesSeguro1 = lista.get(i);
			if (planesSeguro.getPlanSeguro().equals(
					planesSeguro1.getPlanSeguro())) {
				lista.remove(i);
			}
		}
		// lista.remove(planesSeguro);

		for (PlanesSeguro planesSeguro2 : lista) {
			System.out.println("plan seguro = {id ="
					+ planesSeguro2.getPlanSeguro().getId()
					+ ", plan seguro = "
					+ planesSeguro2.getPlanSeguro().toString() + " }");
		}
	}

}
