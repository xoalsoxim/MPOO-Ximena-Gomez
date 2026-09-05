package practica2;

public class Bateria {

    private String tipo;
    private int anioFabricacion;
    private String fabricante;
    private double carga;

    public Bateria(String tipo, int anioFabricacion, String fabricante, double carga) {
        this.tipo = tipo;
        this.anioFabricacion = anioFabricacion;
        this.fabricante = fabricante;
        this.carga = carga;
    }

    public double getCarga() {
        return carga;
    }

    public void consumir(double porcentaje) {
        carga -= porcentaje;

        if (carga < 0) {
            carga = 0;
        }
    }

    public void recargar() {
        carga = 100;
    }

    @Override
    public String toString() {
        return tipo + ", " + fabricante + ", " + anioFabricacion
                + ", carga=" + String.format("%.2f", carga) + "%";
    }
}
