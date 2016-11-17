/**
 * @author mauriciobejaranorivera
 */

/**
 * Permite ocultar o mostrar un fragmento de codigo
 * @param action Ej: 'show' para mostrar el fragmento, 'hide' para ocultar el fragmento
 * @param id Identificador del gragmento
 */
function viewHideFragment(action,id){
	if(action.value.equals('show')){
		document.getElementById(id.value).style.display = 'block';
	}else if(action.value.equals('hide')){
		document.getElementById(id.value).style.display = 'none';
	}
}