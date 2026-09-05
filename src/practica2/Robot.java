package practica2;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Robot {

    private UUID id;
    private String nombre;
    private String categoria;
    private double peso;
    private double velocidadMaxima;
    private double velocidadActual;
    private double distanciaRecorrida;

    private InfoPropietario propietario;
    private Bateria bateria;

    private static int totalRobots = 0;
    private static final Random random = new Random();

    public Robot(String nombre, String categoria, double peso,
                 double velocidadMaxima, InfoPropietario propietario,
                 Bateria bateria) {

        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.categoria = categoria;
        this.peso = peso;
        this.velocidadMaxima = velocidadMaxima;
        this.velocidadActual = 0;
        this.distanciaRecorrida = 0;
        this.propietario = propietario;
        this.bateria = bateria;

        totalRobots++;
    }

    public void avanzar(double segundos) {
        double velocidad = random.nextDouble() * velocidadMaxima;

        velocidadActual = velocidad;
        distanciaRecorrida += velocidad * segundos;

        bateria.consumir(random.nextDouble() * 10);
    }

    public void cambiarVelocidad(double velocidad) {
        if (velocidad < 0) {
            velocidad = 0;
        }

        if (velocidad > velocidadMaxima) {
            velocidad = velocidadMaxima;
        }

        velocidadActual = velocidad;
    }

    public void detener() {
        velocidadActual = 0;
    }

    public void recargar() {
        bateria.recargar();
    }

    public double calcularRendimiento() {
        return distanciaRecorrida / peso;
    }

    public static int getTotalRobots() {
        return totalRobots;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", peso=" + peso +
                ", velocidadActual=" + velocidadActual +
                ", distanciaRecorrida=" + distanciaRecorrida +
                ", bateria=" + bateria +
                ", propietario=" + propietario +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Robot)) {
            return false;
        }

        Robot otro = (Robot) obj;

        return Objects.equals(id, otro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}