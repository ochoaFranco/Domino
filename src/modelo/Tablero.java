package modelo;

public class Tablero {
    private static int extremoIzq;
    private static int extremoDerec;

    public static int getExtremoDerec() {
        return extremoDerec;
    }

    public static int getExtremoIzq() {
        return extremoIzq;
    }

    public static void setExtremoDerec(int extremoDerec) {
        Tablero.extremoDerec = extremoDerec;
    }

    public static void setExtremoIzq(int extremoIzq) {
        Tablero.extremoIzq = extremoIzq;
    }
}
