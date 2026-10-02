package ejercicio2;

import java.io.*;
import java.text.*;
import java.util.*;

class Result {

    public static String evaluarLicencia(String fechaActual, String fechaVencimiento, String tipoLicencia, int renovacionesPrevias) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);

        Calendar actual = Calendar.getInstance();
        Calendar vencimiento = Calendar.getInstance();

        try {
            actual.setTime(formato.parse(fechaActual));
            vencimiento.setTime(formato.parse(fechaVencimiento));
        } catch (Exception e) {
            return "INVALID";
        }

        long diferencia = vencimiento.getTimeInMillis() - actual.getTimeInMillis();
        long diferenciaDias = diferencia / (1000 * 60 * 60 * 24);

        String estado;

        if (diferenciaDias > 30) {
            estado = "VIGENTE";
        } else if (diferenciaDias >= 0) {
            estado = "PROXIMA_A_VENCER";
        } else if (diferenciaDias >= -90) {
            estado = "VENCIDA";
        } else {
            estado = "BLOQUEADA";
        }

        if (estado.equals("BLOQUEADA")) {
            return "BLOQUEADA " + diferenciaDias + " 0.00 NO_DISPONIBLE";
        }

        double costo;

        switch (tipoLicencia) {
            case "BASICA":
                costo = 1000.00;
                break;

            case "PROFESIONAL":
                costo = 1500.00;
                break;

            case "EMPRESARIAL":
                costo = 2500.00;
                break;

            case "TEMPORAL":
                costo = 600.00;
                break;

            default:
                return "INVALID";
        }

        if (estado.equals("VIGENTE")) {
            costo *= 0.90;
        } else if (estado.equals("VENCIDA")) {
            costo *= 1.20;
        }

        if (renovacionesPrevias > 3) {
            costo *= 0.95;
        }

        Calendar nuevaFecha;

        if (estado.equals("VENCIDA")) {
            nuevaFecha = (Calendar) actual.clone();
        } else {
            nuevaFecha = (Calendar) vencimiento.clone();
        }

        switch (tipoLicencia) {
            case "BASICA":
                nuevaFecha.add(Calendar.YEAR, 1);
                break;

            case "PROFESIONAL":
                nuevaFecha.add(Calendar.YEAR, 2);
                break;

            case "EMPRESARIAL":
                nuevaFecha.add(Calendar.YEAR, 3);
                break;

            case "TEMPORAL":
                nuevaFecha.add(Calendar.MONTH, 6);
                break;
        }

        String fechaNueva = formato.format(nuevaFecha.getTime());

        return estado + " " + diferenciaDias + " "
                + String.format(Locale.US, "%.2f", costo)
                + " " + fechaNueva;
    }
}

public class SistemaDeRenovacionDeLicencias {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        String fechaActual = bufferedReader.readLine();
        String fechaVencimiento = bufferedReader.readLine();
        String tipoLicencia = bufferedReader.readLine();
        int renovacionesPrevias =
                Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.evaluarLicencia(
                fechaActual,
                fechaVencimiento,
                tipoLicencia,
                renovacionesPrevias
        );

        System.out.println(result);

        bufferedReader.close();
    }
}
