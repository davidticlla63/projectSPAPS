package bo.com.mbr.components;

import javax.faces.component.FacesComponent;

import org.primefaces.component.commandbutton.CommandButton;

import bo.com.mbr.renderers.ButtonActionRenderer;


/**
 * <strong>Class ButtonActionComponent</strong>
 * <p>
 * Evaluar el Id del Button
 * </p>
 * @author mauriciobejaranorivera
 *
 */
@FacesComponent(ButtonActionComponent.COMPONENT_TYPE)
public class ButtonActionComponent extends CommandButton{

	public static final String COMPONENT_FAMILY = "bo.com.mbr.components";
	public static final String COMPONENT_TYPE = "bo.com.mbr.components.ButtonActionComponent";

	private enum PropertyKeys {
		page
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public String getRendererType() {
		return ButtonActionRenderer.RENDERER_TYPE;
	}

	public String getPage() {
		return (String) getStateHelper().eval(PropertyKeys.page, "page");
	}

	public void setPage(String page) {
		getStateHelper().put(PropertyKeys.page, page);
	}

	//	@Override
	//	public String getValue() {
	//		final ValueExpression valueExpression = getValueExpression("value");
	//	    if (valueExpression != null) {
	//	    	return (String) valueExpression.getValue(getFacesContext().getELContext());
	//	    }
	//	    return value;
	//	}

}
