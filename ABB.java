/**
 * Implementa un árbol binario de búsqueda.
 * @author Araceli Mercado
 */
public class ABB extends ArbolBinario {

    public ABB getNodo(int inf) {
        if (inf == info) {
            return this;
        }
        if (inf < info) {
            if (izq != null) {
                return ((ABB)izq).getNodo(inf);
            }
        } else {
            return null;
        }
        if (izq != null) {
            return ((ABB)izq).getNodo(inf);
        }
        return null;
    }

    public void rotaDerecha(ABB abuelo, ABB padre, ABB hijo) {
        if (abuelo != null) { // padre no es la raíz
            abuelo.der = hijo;
            padre.izq = hijo.der;
            hijo.der = padre;
        }
    }

    public void rotaIzquierda(ABB abuelo, ABB padre, ABB hijo) {
        if (abuelo != null) { // padre no es la raíz
            abuelo.izq = hijo;
            padre.der = hijo.izq;
            hijo.izq = padre;
        }
    }

    /**
     * Balancea el árbol mediante un arreglo.
     * @param datos El arreglo de datos ya ordenado.
     * @param primero El índice del primer elemento del arreglo.
     * @param ultimo El índice del último elemento del arreglo.
     * @throws Exception Cuando el arreglo es nullo o está vacío o los índices están fuera de rango.
     */
    public void balancea(int[] datos, int primero, int ultimo) throws Exception {
        if (datos == null) {
            throw new Exception("El arreglo de datos es null.");
        }
        if (datos.length == 0) {
            throw new Exception("El arreglo de datos está vacío.");
        }
        if (primero < 0 || ultimo >= datos.length) {
            throw new Exception("Los índices están fuera de rango.");
        }
        if (primero <= ultimo) {
            int mitad = (primero + ultimo) / 2;
            inserta(datos[mitad]);
            balancea(datos, primero, mitad - 1);
            balancea(datos, mitad + 1, ultimo);
        }
    }

    /**
     * Borra un nodo del árbol.
     * @param inf La información del nodo a borrar.
     */
    public void elimina(int inf) {
        ABB padre = getPadre(inf);
        if (padre == null) { // inf no está en el árbol o queremos borrar la raíz
            if (inf == getInfo()) { // es la raíz
                // TODO arreglar esta parte
                ABB aux = (ABB)izq;
                ABB masDer = ((ABB)izq).nodoMasDer();
                masDer.der = der; // colgamos el derecho en el árbol izq
                der = izq.der;
                izq = izq.izq;
                info = aux.getInfo();
                return;
            } else { // no está en el árbol
                return;
            }
        }
        if (inf < padre.getInfo()) { // es hijo izq
            if (((ABB)padre.izq).isHoja()) {
                padre.izq = null;
            } else { // el nodo a borrar tiene hijo/s
                int unHijoSolo = ((ABB)padre.izq).unHijo();
                if (unHijoSolo == -1) { // el hijo tiene hijo izq
                    padre.izq = padre.izq.izq;
                } else if (unHijoSolo == 1) { // el hijo tiene hijo der
                    padre.izq = padre.izq.der;
                } else { // tiene dos hijos
                    ABB masIzq = ((ABB)padre.izq.der).nodoMasIzq();
                    masIzq.izq = padre.izq.izq;
                    padre.izq = padre.izq.der;
                }
            }
        } else { // es hijo der
            if (((ABB)padre.der).isHoja()) {
                padre.der = null;
            } else { // el nodo a borrar tiene hijo/s
                int unHijoSolo = ((ABB)padre.der).unHijo();
                if (unHijoSolo == -1) { // el hijo tiene hijo izq
                    padre.der = padre.der.izq;
                } else if (unHijoSolo == 1) { // el hijo tiene hijo der
                    padre.der = padre.der.der;
                } else { // tiene dos hijos
                    ABB masDer = ((ABB)padre.der.izq).nodoMasDer();
                    masDer.der = padre.der.der;
                    padre.der = padre.der.izq;
                }
            }
        }

    }

    /*
     * Obtiene el primer nodo del árbol que no tenga hijo izquierdo.
     */
    public ABB nodoMasIzq() {
        if (izq == null) {
            return this;
        }
        return ((ABB)izq).nodoMasIzq();
    }

    /*
     * Obtiene el primer nodo del árbol que no tenga hijo derecho.
     */
    public ABB nodoMasDer() {
        if (der == null) {
            return this;
        }
        return ((ABB)der).nodoMasDer();
    }

    /*
     * Dice si tiene un hijo y cuál.
     * @return -1 si sólo tiene hijo izq, 0 si tiene dos hijos o es hoja y 1 si sólo tiene hijo der
     */
    public int unHijo() {
        if (izq != null && der == null) {
            return -1;
        } else if (izq == null && der != null) {
            return 1;
        } else {
            return 0;
        }
    }

    /*
     * Obtiene el padre del nodo con inf.
     */
    public ABB getPadre(int inf) {
        if (isVacio() || inf == info) {
            return null;
        }
        if (inf < info) {
            if (izq == null) { // inf no está en el árbol
                return null;
            } else {
                if (izq.info == inf) {
                    return this;
                } else {
                    return ((ABB)izq).getPadre(inf);
                }
            }
        } else {
            if (der == null) { // inf no está en el árbol
                return null;
            } else {
                if (der.info == inf) {
                    return this;
                } else {
                    return ((ABB)der).getPadre(inf);
                }
            }
        }
    }

    /*
     * Decide si un nodo es hoja.   
     */
    private boolean isHoja() {
        return izq == null && der == null;
    }

    /**
     * Agrega un nodo al árbol.
     * @param inf La información del nodo que se va a agregar.
     */
    public void inserta(int inf) {
        if (isVacio()) {
            info = inf;
            return;
        }
        if (inf == info) {
            return;
        }
        if (inf < info) { // lo cuelgo del lado izq
            if (izq == null) {
                izq = new ABB(inf);
            } else {
                ((ABB)izq).inserta(inf);
            }
        } else { // lo cuelgo del lado derecho
            if (der == null) {
                der = new ABB(inf);
            } else {
                ((ABB)der).inserta(inf);
            }
        }
    }

    /* **************CONSTRUCTORES**************** */
    public ABB() {
        super();
    }
    public ABB(int inf) {
        super(inf);
    }
    /* **************CONSTRUCTORES**************** */
}
