/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 MauricioBejaranoRivera mbr.bejarano@gmail.com. All rights reserved.
 *
 * The contents of this file are subject to the terms intellectual property.
 */
package bo.com.mbr.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import org.primefaces.component.commandbutton.CommandButtonRenderer;

import bo.com.mbr.components.ButtonActionComponent;

/**
 * <strong>Class ButtonActionRenderer</strong>
 * <p>
 * Rrepresentacion de Componente , permite evaluar si el button tiene permisos para ppoder mostrarlo.
 * </p>
 * @author mauriciobejaranorivera
 *
 */
@FacesRenderer(componentFamily=ButtonActionComponent.COMPONENT_FAMILY, rendererType=ButtonActionRenderer.RENDERER_TYPE)
public class ButtonActionRenderer extends CommandButtonRenderer{

	public static final String RENDERER_TYPE = "bo.com.mbr.renderers.ButtonActionRenderer";

	public static final String ID_BUTTON_REGISTRAR= "idButtonRegistrar";
	public static final String ID_BUTTON_MODIFICAR = "idButtonModificar";
	public static final String ID_BUTTON_ELIMINAR = "idButtonEliminar";
	public static final String ID_BUTTON_PROCESAR = "idButtonProcesar";
	public static final String ID_BUTTON_REVERTIR = "idButtonRevertir";


	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		final ButtonActionComponent customComponent = (ButtonActionComponent) component;
		String idCommandButton = customComponent.getId();
		String pageCommandButton = customComponent.getPage();
		System.out.println("BUTTON PAGE: "+pageCommandButton+" ID: "+idCommandButton);
		switch (idCommandButton) {
		case ID_BUTTON_REGISTRAR:
			customComponent.setDisabled(true);
			break;
		case ID_BUTTON_MODIFICAR:
			customComponent.setDisabled(true);
			break;
		case ID_BUTTON_ELIMINAR:
			customComponent.setDisabled(true);
			break;
		case ID_BUTTON_PROCESAR:
			customComponent.setDisabled(true);
			break;
		case ID_BUTTON_REVERTIR:
			customComponent.setDisabled(true);
			break;
		default:
			break;
		}
		super.encodeBegin(context, component);
	}

}