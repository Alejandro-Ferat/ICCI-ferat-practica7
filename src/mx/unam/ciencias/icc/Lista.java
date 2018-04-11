package mx.unam.ciencias.icc;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Iterable<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
           this. elemento=elemento;
         }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            siguiente=cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(siguiente==null){
		throw new NoSuchElementException();
	    }
	    anterior=siguiente;
	    siguiente=siguiente.siguiente;
	    return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(anterior==null){
		throw new NoSuchElementException();
	    }
	    siguiente=anterior;
	    anterior=anterior.anterior;
	    return siguiente.elemento;
	}

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
	    siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
	    siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        return cabeza==null;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if(elemento==null){
	    throw new IllegalArgumentException();
	}
	Nodo nodo= new Nodo(elemento);
	if(this.esVacia()){
	    cabeza=nodo;
	    rabo=cabeza;
	}else{
	    rabo.siguiente=nodo;
	    nodo.anterior=rabo;
	    rabo=nodo;	    
      	}	
	longitud++;
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento==null){
	    throw new IllegalArgumentException();
	}
        Nodo nodo= new Nodo(elemento);
	if(this.esVacia()){
	    cabeza=nodo;
	    rabo=cabeza;
	}else{
	    cabeza.anterior=nodo;
	    nodo.siguiente=cabeza;
	    cabeza=nodo;	    
      	}	
	longitud++;
    }

    /**
      * Este método recibe un índice y el nodo cabeza y nos regresa el nodo en dicho
      * índice o null si el indice es menor que 0 o mayor que la longitud de la
      * cadena
      *@param Nodo cabeza (servira como contador)
      *@param el indice del nodo que queremos
      *@return null o el nodo en el indice buscado
      */
    private Nodo indiceNodo1(int indice, Nodo contador){
	if(this.esVacia()){
	    return null;
	}else if(indice < 0 || longitud <= indice){
	    return null;
	}else{
	    if(indice == 0){
		return contador;
	    }else{
		return this.indiceNodo1(indice-1,contador.siguiente);
	    }
	}
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if(elemento==null){
	    throw new IllegalArgumentException();
	}else if(i<=0){
	    this.agregaInicio(elemento);
	}else if (longitud <=i){
	    this.agregaFinal(elemento);
	     
	}else{
	    Nodo iesimo = this.indiceNodo1(i,cabeza);
	    Nodo nuevo = new Nodo(elemento);
	    nuevo.anterior=iesimo.anterior;
	    nuevo.siguiente=iesimo;
	    iesimo.anterior.siguiente=nuevo;
	    iesimo.anterior=nuevo;
	    longitud++;
	}
    }
    
    /**
       *Devuelve el primer nodo de la lista cuyo elemento sea el parámetro elemento.
       *Este método es privado ya que servira como auxiliar de otros métodos de esta clase.
       *En caso de que la lista no contenga ningún nodo con el elemento buscado el 
       *método regresara null.
       *@param El Nodo cabeza (servira como contador)
       *@param El elemento buscado
       *@return El primer nodo que tenga el elemento buscado
       */
    private Nodo buscaNodo(T elemento, Nodo cabezaContador){
	if(this.esVacia() || cabezaContador ==null){
	    return null;
	}else{
	    if(cabezaContador.elemento.equals(elemento)){
		return cabezaContador;
	    }else{
		cabezaContador=cabezaContador.siguiente;
		return this.buscaNodo(elemento, cabezaContador);
	    }
	}
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
        if(this.esVacia()){
	    return;
	}else{
	    Nodo resultado =this.buscaNodo(elemento,cabeza);
	    if(resultado==null){
		return;
	    }else if(resultado.equals(cabeza)){
		this.eliminaPrimero();
		return;
	    }else if(resultado.equals(rabo)){
		this.eliminaUltimo();
		return;
	    }else{
		resultado.anterior.siguiente=resultado.siguiente;
		resultado.siguiente.anterior=resultado.anterior;
		resultado=null;
		longitud--;
	    }	
	}
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
         if(this.esVacia()){
	     throw new NoSuchElementException();
	}else if(longitud ==1){
	    T primero = cabeza.elemento;
	    cabeza=null;
	    rabo = null;
	    longitud--;
	    return(primero);
	}else{
	    T primero = cabeza.elemento;
	    cabeza.siguiente.anterior=null;
	    cabeza=cabeza.siguiente;
	    longitud--;
	    return(primero);
	 }
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(this.esVacia()){
	    throw new NoSuchElementException();
	}else if(longitud == 1){
	    T ultimo = rabo.elemento;
	    rabo = null;
	    cabeza = null;
	    longitud--;
	    return(ultimo);
	}else{
	    T ultimo =rabo.elemento;
	    rabo.anterior.siguiente=null;
	    rabo=rabo.anterior;
	    longitud--;
	    return(ultimo);
	}
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(T elemento) {
        return this.esVacia() ? false : this.buscaNodo(elemento,cabeza) != null ;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reversa = new Lista<T>();
	if(this.esVacia()){
	    return(reversa);
	}else{
	    Nodo contador =new Nodo(null);
	    contador=this.rabo;
	    while(contador != null){
		reversa.agregaFinal(contador.elemento);
		contador = contador.anterior;		
	    }
	    return(reversa);
	}
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copia = new Lista<T>();
	if(this.esVacia()){
	    return(copia);
	}else{
	    Nodo contador =new Nodo(null);
	    contador=this.cabeza;
	    while(contador != null){
		copia.agregaFinal(contador.elemento);
		contador = contador.siguiente;		
	    }
	    return(copia);
	}
    }

    /**
     * Limpia la lista de elementos. El llamar este método es equivalente a
     * eliminar todos los elementos de la lista.
     */
    public void limpia() {
         while(longitud != 0){
	    this.eliminaPrimero();
	}
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
	if(this.esVacia() ){
	    throw new NoSuchElementException();
	}else{
	    return(cabeza.elemento);
	}
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(this.esVacia() ){
	     throw new NoSuchElementException();
	}else{
	    return(rabo.elemento);
	}
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        Nodo indice = this.indiceNodo1(i,cabeza);
	if(indice==null){
	    throw new ExcepcionIndiceInvalido();
	}else{
	    return indice.elemento;
	}
    }

    /** Metodo que recibe un nodo y el nodo cabeza y devuelve el indice del 
     *  primer nodo que recibio, el método devuelve -1 si el nodo buscado
     *  no se encuetra en la lista o si el nodo buscado es null
     *@param Nodo buscado
     *@param Nodo cabeza (es el contador)
     *@return Indice del nodo recibido
     */
    private int indiceNodo2(Nodo buscado, Nodo contador){
	if(this.esVacia()){
	    return -1;
	}else if(buscado == null){
	    return -1;
	}else{
	    if(buscado.equals(contador)){
		return 0;
	    }else{
	       	return 1+this.indiceNodo2(buscado,contador.siguiente);
	    }
	}
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        Nodo buscado =this.buscaNodo(elemento,cabeza);
	int indice =this.indiceNodo2(buscado,cabeza);
	return indice;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
	String r="";
	if(this.esVacia()){
	    String s="[]";
	    return(s);
	}else if(longitud==1){
	    String s= "["+ String.format("%s]", r.valueOf(this.get(0)) );
	    return(s);
	}else{
	    String s = "[";
	    for (int i = 0; i < longitud-1; i++)
		s += String.format("%s, ", r.valueOf(this.get(i)));
	    s += String.format("%s]", r.valueOf(this.get(longitud-1)));
	    return(s);
	}
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
         if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        if( !(this.longitud==lista.longitud) ){
	    return false;
	}else{ 
	    Nodo contador1;
	    Nodo contador2;
	    contador1=this.cabeza;
	    contador2=lista.cabeza;
	    boolean r= true;
	    for(int i =0; i< longitud;i++){
		if(contador1.elemento.equals(contador2.elemento)){
		    r=true;
		    contador1=contador1.siguiente;
		    contador2=contador2.siguiente;
		}else{
		    r=false;
		    break;
		}
	    }
	    return r;	
	}
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
