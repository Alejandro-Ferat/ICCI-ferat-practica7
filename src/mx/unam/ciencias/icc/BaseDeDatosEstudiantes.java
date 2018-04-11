package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de estudiantes.
 */
public class BaseDeDatosEstudiantes
    extends BaseDeDatos<Estudiante, CampoEstudiante> {

    /**
     * Crea un estudiante en blanco.
     * @return un estudiante en blanco.
     */
    @Override public Estudiante creaRegistro() {
        Estudiante e = new Estudiante(null,0,0,0);
	return e;
    }

    /**
     * Busca estudiantes por un campo específico.
     * @param campo el campo del registro por el cuál buscar.
     * @param texto el texto a buscar.
     * @return una lista con los estudiantes tales que en el campo especificado
     *         contienen el texto recibido, o una lista vacía si el texto es
     *         <code>null</code> o la cadena vacía.
     */
    @Override public Lista<Estudiante> buscaRegistros(CampoEstudiante campo,
                                                      String texto) {
	Lista<Estudiante> nueva = new Lista<Estudiante>();
	if(this.registros.esVacia() || texto==null || texto=="" ){
	    return nueva;
	}
	IteradorLista<Estudiante> iterador= this.registros.iteradorLista();
	while(iterador.hasNext() ){
	    Estudiante e = iterador.next();
	    String s= CampoCadena(e,campo);
	    if(s.contains(texto)){
		nueva.agregaFinal(e);
	    }
	}
	return nueva;	
    }

private static String CampoCadena(Estudiante e,CampoEstudiante campo){
	String s;
	switch(campo){
	case NOMBRE:
	    s=e.getNombre();
	    return s;

	case CUENTA:
	    s=String.valueOf(e.getCuenta());
	    return s;

	case PROMEDIO:
	    s=String.format("%2.2f",e.getPromedio());
	    return s;
       
	default:
	    s=String.valueOf(e.getEdad());
	    return s;
	}
    }    
}
