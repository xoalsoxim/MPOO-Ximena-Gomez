package practica2;

public class InfoPropietario {

    private String nombre;
    private String curp;

    public InfoPropietario(String nombre, String curp) {
        this.nombre = nombre;
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public String toString() {
        return nombre + " (" + curp + ")";
    }
}
