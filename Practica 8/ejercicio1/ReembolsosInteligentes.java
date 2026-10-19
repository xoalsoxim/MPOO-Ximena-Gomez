package ejercicio1;



	import java.io.*;
	import java.time.*;
	import java.time.temporal.ChronoUnit;


	/*
	 * =====================================================
	 * TIPOS PROPORCIONADOS POR HACKERRANK
	 * =====================================================
	 */

	enum TipoReembolso {
	    ALIMENTOS,
	    TRANSPORTE,
	    HOSPEDAJE
	}


	enum ResultadoValidacion {
	    APROBADO,
	    SIN_COMPROBANTE,
	    FUERA_DE_PERIODO,
	    EXCEDE_LIMITE,
	    REQUIERE_AUTORIZACION
	}


	/*
	 * =====================================================
	 * DTO DE ENTRADA
	 * =====================================================
	 *
	 * Contiene toda la informacion necesaria para construir
	 * y evaluar una solicitud de reembolso.
	 */

	final class ReembolsoInputDTO {

	    private final TipoReembolso tipo;
	    private final String folio;
	    private final String empleado;
	    private final double monto;
	    private final LocalDate fechaGasto;
	    private final boolean tieneComprobante;
	    private final boolean autorizadoGerente;
	    private final LocalDate fechaEvaluacion;


	    public ReembolsoInputDTO(
	            TipoReembolso tipo,
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto,
	            boolean tieneComprobante,
	            boolean autorizadoGerente,
	            LocalDate fechaEvaluacion) {

	        this.tipo = tipo;
	        this.folio = folio;
	        this.empleado = empleado;
	        this.monto = monto;
	        this.fechaGasto = fechaGasto;
	        this.tieneComprobante = tieneComprobante;
	        this.autorizadoGerente = autorizadoGerente;
	        this.fechaEvaluacion = fechaEvaluacion;
	    }


	    public TipoReembolso getTipo() {
	        return tipo;
	    }

	    public String getFolio() {
	        return folio;
	    }

	    public String getEmpleado() {
	        return empleado;
	    }

	    public double getMonto() {
	        return monto;
	    }

	    public LocalDate getFechaGasto() {
	        return fechaGasto;
	    }

	    public boolean tieneComprobante() {
	        return tieneComprobante;
	    }

	    public boolean estaAutorizadoGerente() {
	        return autorizadoGerente;
	    }

	    public LocalDate getFechaEvaluacion() {
	        return fechaEvaluacion;
	    }
	}


	/*
	 * =====================================================
	 * DTO DE SALIDA
	 * =====================================================
	 */

	final class ReembolsoOutputDTO {

	    private final String folio;
	    private final TipoReembolso tipo;
	    private final ResultadoValidacion resultado;


	    public ReembolsoOutputDTO(
	            String folio,
	            TipoReembolso tipo,
	            ResultadoValidacion resultado) {

	        this.folio = folio;
	        this.tipo = tipo;
	        this.resultado = resultado;
	    }


	    public String getFolio() {
	        return folio;
	    }

	    public TipoReembolso getTipo() {
	        return tipo;
	    }

	    public ResultadoValidacion getResultado() {
	        return resultado;
	    }
	}

	/*
	 * =====================================================
	 * EXPENSEFLOW
	 * =====================================================
	 *
	 * Complete la solucion utilizando:
	 *
	 *      - Herencia
	 *      - Clases abstractas
	 *      - Constructores sobrecargados
	 *      - super(...)
	 *      - Sobrescritura
	 *      - Polimorfismo
	 *      - Factory Method
	 *      - Chain of Responsibility
	 *
	 *
	 * =====================================================
	 * 1. JERARQUIA DE REEMBOLSOS
	 * =====================================================
	 *
	 * Cree una clase abstracta:
	 *
	 *      Reembolso
	 *
	 * Debe conservar como minimo:
	 *
	 *      folio
	 *      empleado
	 *      monto
	 *      fechaGasto
	 *      tieneComprobante
	 *      autorizadoGerente
	 *
	 * Debe declarar:
	 *
	 *      public abstract double getLimitePermitido();
	 *
	 *
	 * Cree las siguientes subclases:
	 *
	 *      ReembolsoAlimentos
	 *      ReembolsoTransporte
	 *      ReembolsoHospedaje
	 *
	 * Limites:
	 *
	 *      ALIMENTOS   -> 1000
	 *      TRANSPORTE  -> 1500
	 *      HOSPEDAJE   -> 4000
	 *
	 * Cada subclase debe sobrescribir getLimitePermitido().
	 *
	 * NO utilice switch, if/else o instanceof para determinar
	 * el limite.
	 *
	 *
	 * =====================================================
	 * 2. CONSTRUCTORES
	 * =====================================================
	 *
	 * Reembolso debe contener al menos dos constructores
	 * sobrecargados.
	 *
	 * Las clases derivadas deben utilizar super(...)
	 * para inicializar los datos heredados.
	 *
	 *
	 * =====================================================
	 * 3. FACTORY METHOD
	 * =====================================================
	 *
	 * Cree:
	 *
	 *      abstract class RegistroReembolso
	 *
	 * con un metodo Factory Method encargado de crear
	 * objetos Reembolso.
	 *
	 * Cree las especializaciones:
	 *
	 *      RegistroAlimentos
	 *      RegistroTransporte
	 *      RegistroHospedaje
	 *
	 * Cada Concrete Creator debe construir el Product
	 * correspondiente.
	 *
	 *
	 * =====================================================
	 * 4. CHAIN OF RESPONSIBILITY
	 * =====================================================
	 *
	 * Cree:
	 *
	 *      abstract class ValidadorReembolso
	 *
	 * Debe mantener una referencia al siguiente validador.
	 *
	 * Cree:
	 *
	 *      ValidadorComprobante
	 *      ValidadorPeriodo
	 *      ValidadorLimite
	 *      ValidadorGerencia
	 *
	 *
	 * La cadena debe ejecutarse en este orden:
	 *
	 *      COMPROBANTE
	 *          |
	 *      PERIODO
	 *          |
	 *      LIMITE
	 *          |
	 *      GERENCIA
	 *
	 *
	 * =====================================================
	 * 5. REGLAS
	 * =====================================================
	 *
	 * COMPROBANTE
	 *
	 * Si no existe comprobante:
	 *
	 *      SIN_COMPROBANTE
	 *
	 *
	 * PERIODO
	 *
	 * Calcule los dias transcurridos utilizando:
	 *
	 *      fechaGasto
	 *      fechaEvaluacion
	 *
	 * Si han transcurrido mas de 30 dias:
	 *
	 *      FUERA_DE_PERIODO
	 *
	 *
	 * LIMITE
	 *
	 * Compare el monto contra:
	 *
	 *      reembolso.getLimitePermitido()
	 *
	 * Si lo supera:
	 *
	 *      EXCEDE_LIMITE
	 *
	 *
	 * GERENCIA
	 *
	 * Si:
	 *
	 *      monto > 2000
	 *
	 * y no existe autorizacion:
	 *
	 *      REQUIERE_AUTORIZACION
	 *
	 *
	 * Si todas las reglas se cumplen:
	 *
	 *      APROBADO
	 *
	 *
	 * IMPORTANTE:
	 *
	 * Cuando una regla falle, la cadena debe detenerse.
	 *
	 *
	 * =====================================================
	 * 6. METODO PRINCIPAL DEL RETO
	 * =====================================================
	 *
	 * Implemente el metodo:
	 *
	 *      procesarReembolso(ReembolsoInputDTO input)
	 *
	 * El metodo debe:
	 *
	 *  1. Crear el tipo correcto de Reembolso utilizando
	 *     Factory Method.
	 *
	 *  2. Construir la cadena de validadores.
	 *
	 *  3. Enviar el Reembolso solamente al primer Handler.
	 *
	 *  4. Obtener el ResultadoValidacion.
	 *
	 *  5. Regresar un ReembolsoOutputDTO.
	 *
	 *
	 * NO imprima informacion desde este metodo.
	 *
	 * HackerRank se encargara de generar la salida.
	 */


	/* =====================================================
	   1. JERARQUIA DE REEMBOLSOS
	   ===================================================== */

	abstract class Reembolso {

	    protected final String folio;
	    protected final String empleado;
	    protected final double monto;
	    protected final LocalDate fechaGasto;
	    protected final boolean tieneComprobante;
	    protected final boolean autorizadoGerente;

	    public Reembolso(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto,
	            boolean tieneComprobante,
	            boolean autorizadoGerente) {

	        this.folio = folio;
	        this.empleado = empleado;
	        this.monto = monto;
	        this.fechaGasto = fechaGasto;
	        this.tieneComprobante = tieneComprobante;
	        this.autorizadoGerente = autorizadoGerente;
	    }

	    public Reembolso(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto) {

	        this(folio, empleado, monto, fechaGasto, false, false);
	    }

	    public abstract double getLimitePermitido();

	    public String getFolio() {
	        return folio;
	    }

	    public String getEmpleado() {
	        return empleado;
	    }

	    public double getMonto() {
	        return monto;
	    }

	    public LocalDate getFechaGasto() {
	        return fechaGasto;
	    }

	    public boolean tieneComprobante() {
	        return tieneComprobante;
	    }

	    public boolean estaAutorizadoGerente() {
	        return autorizadoGerente;
	    }
	}


	class ReembolsoAlimentos extends Reembolso {

	    public ReembolsoAlimentos(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto,
	            boolean tieneComprobante,
	            boolean autorizadoGerente) {

	        super(folio, empleado, monto, fechaGasto,
	                tieneComprobante, autorizadoGerente);
	    }

	    public ReembolsoAlimentos(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto) {

	        super(folio, empleado, monto, fechaGasto);
	    }

	    @Override
	    public double getLimitePermitido() {
	        return 1000.0;
	    }
	}


	class ReembolsoTransporte extends Reembolso {

	    public ReembolsoTransporte(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto,
	            boolean tieneComprobante,
	            boolean autorizadoGerente) {

	        super(folio, empleado, monto, fechaGasto,
	                tieneComprobante, autorizadoGerente);
	    }

	    public ReembolsoTransporte(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto) {

	        super(folio, empleado, monto, fechaGasto);
	    }

	    @Override
	    public double getLimitePermitido() {
	        return 1500.0;
	    }
	}


	class ReembolsoHospedaje extends Reembolso {

	    public ReembolsoHospedaje(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto,
	            boolean tieneComprobante,
	            boolean autorizadoGerente) {

	        super(folio, empleado, monto, fechaGasto,
	                tieneComprobante, autorizadoGerente);
	    }

	    public ReembolsoHospedaje(
	            String folio,
	            String empleado,
	            double monto,
	            LocalDate fechaGasto) {

	        super(folio, empleado, monto, fechaGasto);
	    }

	    @Override
	    public double getLimitePermitido() {
	        return 4000.0;
	    }
	}


	/* =====================================================
	   2. FACTORY METHOD
	   ===================================================== */

	abstract class RegistroReembolso {

	    public abstract Reembolso crearReembolso(
	            ReembolsoInputDTO input);
	}


	class RegistroAlimentos extends RegistroReembolso {

	    @Override
	    public Reembolso crearReembolso(ReembolsoInputDTO input) {
	        return new ReembolsoAlimentos(
	                input.getFolio(),
	                input.getEmpleado(),
	                input.getMonto(),
	                input.getFechaGasto(),
	                input.tieneComprobante(),
	                input.estaAutorizadoGerente()
	        );
	    }
	}


	class RegistroTransporte extends RegistroReembolso {

	    @Override
	    public Reembolso crearReembolso(ReembolsoInputDTO input) {
	        return new ReembolsoTransporte(
	                input.getFolio(),
	                input.getEmpleado(),
	                input.getMonto(),
	                input.getFechaGasto(),
	                input.tieneComprobante(),
	                input.estaAutorizadoGerente()
	        );
	    }
	}


	class RegistroHospedaje extends RegistroReembolso {

	    @Override
	    public Reembolso crearReembolso(ReembolsoInputDTO input) {
	        return new ReembolsoHospedaje(
	                input.getFolio(),
	                input.getEmpleado(),
	                input.getMonto(),
	                input.getFechaGasto(),
	                input.tieneComprobante(),
	                input.estaAutorizadoGerente()
	        );
	    }
	}


	/* =====================================================
	   3. CHAIN OF RESPONSIBILITY
	   ===================================================== */

	abstract class ValidadorReembolso {

	    private ValidadorReembolso siguiente;

	    public ValidadorReembolso establecerSiguiente(
	            ValidadorReembolso siguiente) {

	        this.siguiente = siguiente;
	        return siguiente;
	    }

	    public ResultadoValidacion validarCadena(
	            Reembolso reembolso,
	            LocalDate fechaEvaluacion) {

	        ResultadoValidacion resultado =
	                validar(reembolso, fechaEvaluacion);

	        if (resultado != null) {
	            return resultado;
	        }

	        if (siguiente != null) {
	            return siguiente.validarCadena(
	                    reembolso, fechaEvaluacion);
	        }

	        return ResultadoValidacion.APROBADO;
	    }

	    protected abstract ResultadoValidacion validar(
	            Reembolso reembolso,
	            LocalDate fechaEvaluacion);
	}


	class ValidadorComprobante extends ValidadorReembolso {

	    @Override
	    protected ResultadoValidacion validar(
	            Reembolso reembolso,
	            LocalDate fechaEvaluacion) {

	        if (!reembolso.tieneComprobante()) {
	            return ResultadoValidacion.SIN_COMPROBANTE;
	        }

	        return null;
	    }
	}


	class ValidadorPeriodo extends ValidadorReembolso {

	    @Override
	    protected ResultadoValidacion validar(
	            Reembolso reembolso,
	            LocalDate fechaEvaluacion) {

	        long dias = ChronoUnit.DAYS.between(
	                reembolso.getFechaGasto(),
	                fechaEvaluacion
	        );

	        if (dias > 30) {
	            return ResultadoValidacion.FUERA_DE_PERIODO;
	        }

	        return null;
	    }
	}


	class ValidadorLimite extends ValidadorReembolso {

	    @Override
	    protected ResultadoValidacion validar(
	            Reembolso reembolso,
	            LocalDate fechaEvaluacion) {

	        if (reembolso.getMonto()
	                > reembolso.getLimitePermitido()) {

	            return ResultadoValidacion.EXCEDE_LIMITE;
	        }

	        return null;
	    }
	}


	class ValidadorGerencia extends ValidadorReembolso {

	    @Override
	    protected ResultadoValidacion validar(
	            Reembolso reembolso,
	            LocalDate fechaEvaluacion) {

	        if (reembolso.getMonto() > 2000
	                && !reembolso.estaAutorizadoGerente()) {

	            return ResultadoValidacion.REQUIERE_AUTORIZACION;
	        }

	        return null;
	    }
	}


	/* =====================================================
	   4. METODO PRINCIPAL DEL RETO
	   ===================================================== */

	class Result {

	    public static ReembolsoOutputDTO procesarReembolso(
	            ReembolsoInputDTO input) {

	        RegistroReembolso registro;

	        switch (input.getTipo()) {
	            case ALIMENTOS:
	                registro = new RegistroAlimentos();
	                break;

	            case TRANSPORTE:
	                registro = new RegistroTransporte();
	                break;

	            case HOSPEDAJE:
	                registro = new RegistroHospedaje();
	                break;

	            default:
	                throw new IllegalArgumentException(
	                        "Tipo de reembolso no valido");
	        }

	        Reembolso reembolso =
	                registro.crearReembolso(input);

	        ValidadorReembolso comprobante =
	                new ValidadorComprobante();

	        ValidadorReembolso periodo =
	                new ValidadorPeriodo();

	        ValidadorReembolso limite =
	                new ValidadorLimite();

	        ValidadorReembolso gerencia =
	                new ValidadorGerencia();

	        comprobante.establecerSiguiente(periodo)
	                   .establecerSiguiente(limite)
	                   .establecerSiguiente(gerencia);

	        ResultadoValidacion resultado =
	                comprobante.validarCadena(
	                        reembolso,
	                        input.getFechaEvaluacion()
	                );

	        return new ReembolsoOutputDTO(
	                input.getFolio(),
	                input.getTipo(),
	                resultado
	        );
	    }
	}


	public class ReembolsosInteligentes {

	    public static void main(String[] args)
	            throws Exception {

	        BufferedReader br =
	                new BufferedReader(
	                        new InputStreamReader(System.in));


	        /*
	         * ==============================================
	         * FECHA DE EVALUACION
	         * ==============================================
	         */

	        String lineaFecha =
	                br.readLine();

	        LocalDate fechaEvaluacion =
	                LocalDate.parse(
	                        lineaFecha.trim()
	                );


	        /*
	         * ==============================================
	         * DATOS DEL REEMBOLSO
	         * ==============================================
	         */

	        String lineaSolicitud =
	                br.readLine();

	        String[] datos =
	                lineaSolicitud.split("\\|");


	        TipoReembolso tipo =
	                TipoReembolso.valueOf(
	                        datos[0].trim()
	                );

	        String folio =
	                datos[1].trim();

	        String empleado =
	                datos[2].trim();

	        double monto =
	                Double.parseDouble(
	                        datos[3].trim()
	                );

	        LocalDate fechaGasto =
	                LocalDate.parse(
	                        datos[4].trim()
	                );

	        boolean tieneComprobante =
	                Boolean.parseBoolean(
	                        datos[5].trim()
	                );

	        boolean autorizadoGerente =
	                Boolean.parseBoolean(
	                        datos[6].trim()
	                );


	        /*
	         * ==============================================
	         * CREACION DEL DTO DE ENTRADA
	         * ==============================================
	         */

	        ReembolsoInputDTO input =
	                new ReembolsoInputDTO(
	                        tipo,
	                        folio,
	                        empleado,
	                        monto,
	                        fechaGasto,
	                        tieneComprobante,
	                        autorizadoGerente,
	                        fechaEvaluacion
	                );


	        /*
	         * ==============================================
	         * EJECUCION DEL CODIGO DEL ALUMNO
	         * ==============================================
	         */

	        ReembolsoOutputDTO output =
	                Result.procesarReembolso(input);


	        /*
	         * ==============================================
	         * SALIDA
	         * ==============================================
	         */

	        System.out.println(
	                "REEMBOLSO "
	                + output.getFolio()
	        );

	        System.out.println(
	                "TIPO "
	                + output.getTipo()
	        );

	        System.out.println(
	                "RESULTADO "
	                + output.getResultado()
	        );
	    }
	}
