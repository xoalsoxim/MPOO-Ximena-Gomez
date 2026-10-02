package ejercicio1;
	
import java.io.*;
import java.text.*;
import java.util.*;

	class Result {

	    public static String calcularEstancia(String tipoVehiculo, String fechaEntrada, String horaEntrada, String fechaSalida, String horaSalida) {
	        try {
	            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            formato.setLenient(false);

            Calendar entrada = Calendar.getInstance();
            Calendar salida = Calendar.getInstance();

            entrada.setTime(formato.parse(fechaEntrada + " " + horaEntrada));
            salida.setTime(formato.parse(fechaSalida + " " + horaSalida));

            if (!salida.after(entrada)) {
                return "INVALID";
            }

            long diferencia = salida.getTimeInMillis() - entrada.getTimeInMillis();
            long horasCobradas = (diferencia + 3600000 - 1) / 3600000;

            double tarifaHora;
            double maximo24;

            switch (tipoVehiculo) {
                case "MOTOCICLETA":
                    tarifaHora = 15.00;
                    maximo24 = 100.00;
                    break;

                case "AUTOMOVIL":
                    tarifaHora = 25.00;
                    maximo24 = 180.00;
                    break;

                case "CAMIONETA":
                    tarifaHora = 35.00;
                    maximo24 = 250.00;
                    break;

                case "ELECTRICO":
                    tarifaHora = 20.00;
                    maximo24 = 150.00;
                    break;

                default:
                    return "INVALID";
            }

            double costo = 0;
            long horasRestantes = horasCobradas;

            while (horasRestantes > 0) {
                long horasBloque = Math.min(horasRestantes, 24);
                double costoBloque = horasBloque * tarifaHora;

                if (costoBloque > maximo24) {
                    costoBloque = maximo24;
                }

                costo += costoBloque;
                horasRestantes -= horasBloque;
            }

            int diaEntrada = entrada.get(Calendar.DAY_OF_WEEK);
            int diaSalida = salida.get(Calendar.DAY_OF_WEEK);

            boolean finDeSemana =
                    diaEntrada == Calendar.SATURDAY ||
                    diaEntrada == Calendar.SUNDAY ||
                    diaSalida == Calendar.SATURDAY ||
                    diaSalida == Calendar.SUNDAY;

            if (finDeSemana) {
                costo *= 1.20;
            }

            int horaEntradaNum = entrada.get(Calendar.HOUR_OF_DAY);
            int horaSalidaNum = salida.get(Calendar.HOUR_OF_DAY);

            boolean nocturna =
                    horaEntradaNum >= 20 ||
                    horaSalidaNum < 6 ||
                    entrada.get(Calendar.YEAR) != salida.get(Calendar.YEAR) ||
                    entrada.get(Calendar.DAY_OF_YEAR) != salida.get(Calendar.DAY_OF_YEAR);

            if (nocturna) {
                costo *= 1.15;
            }

            if (tipoVehiculo.equals("ELECTRICO")) {
                costo *= 0.90;
            }

            String tipoEstancia;

            if (finDeSemana && nocturna) {
                tipoEstancia = "MIXTA";
            } else if (finDeSemana) {
                tipoEstancia = "FIN_SEMANA";
            } else if (nocturna) {
                tipoEstancia = "NOCTURNA";
            } else {
                tipoEstancia = "NORMAL";
            }

            return horasCobradas + " " +
                   String.format(Locale.US, "%.2f", costo) +
                   " " + tipoEstancia;

        } catch (Exception e) {
            return "INVALID";
        }
    }
}
	public class TarifaDinamicaDeEstacionamiento {
	    public static void main(String[] args) throws IOException {
	        BufferedReader bufferedReader = new BufferedReader(
	                new InputStreamReader(System.in));
	
	        String tipoVehiculo = bufferedReader.readLine();
	        String fechaEntrada = bufferedReader.readLine();
	        String horaEntrada = bufferedReader.readLine();
	        String fechaSalida = bufferedReader.readLine();
	        String horaSalida = bufferedReader.readLine();
	
	        String result = Result.calcularEstancia(
	                tipoVehiculo,
	                fechaEntrada,
	                horaEntrada,
	                fechaSalida,
	                horaSalida
	        );
	
	        System.out.println(result);
	
	        bufferedReader.close();
    }
}

