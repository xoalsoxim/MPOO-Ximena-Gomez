package ejercicio1;

import java.time.LocalDate;
import java.util.Optional;


/* =========================================================
   ENUMS PROPORCIONADOS POR HACKERRANK
   ========================================================= */

enum CategoriaProducto {
    BEBIDA,
    SNACK
}


enum ResultadoRegistro {
    PRODUCTO_REGISTRADO,
    CODIGO_DUPLICADO,
    PRODUCTO_INVALIDO,
    PRECIO_INVALIDO
}


enum ResultadoReabastecimiento {
    REABASTECIMIENTO_REALIZADO,
    PRODUCTO_NO_ENCONTRADO,
    CANTIDAD_INVALIDA
}


enum ResultadoEntrega {
    PRODUCTO_PREPARADO,
    PRODUCTO_NO_ENCONTRADO,
    SIN_EXISTENCIAS
}


enum EstadoCobro {
    COBRO_REALIZADO,
    MONTO_INVALIDO,
    SIN_FONDOS_SUFFICIENTES
}


enum EstadoCompra {
    VENTA_REALIZADA,
    PRODUCTO_NO_ENCONTRADO,
    SIN_EXISTENCIAS,
    PAGO_INSUFICIENTE,
    SIN_FONDOS_SUFFICIENTES
}


/* =========================================================
   CLASES DE RESULTADO Y TRANSFERENCIA
   ========================================================= */

class ProductoInventario {

    private final Producto producto;
    private final double precio;
    private final int existencias;


    public ProductoInventario(
            Producto producto,
            double precio,
            int existencias
    ) {
        this.producto = producto;
        this.precio = precio;
        this.existencias = existencias;
    }


    public Producto getProducto() {
        return producto;
    }


    public double getPrecio() {
        return precio;
    }


    public int getExistencias() {
        return existencias;
    }
}


class ResultadoCobro {

    private final EstadoCobro estado;
    private final double cambio;


    public ResultadoCobro(
            EstadoCobro estado,
            double cambio
    ) {
        this.estado = estado;
        this.cambio = cambio;
    }


    public EstadoCobro getEstado() {
        return estado;
    }


    public double getCambio() {
        return cambio;
    }
}


class ResultadoCompra {

    private final EstadoCompra estado;
    private final Producto producto;
    private final double cambio;


    public ResultadoCompra(
            EstadoCompra estado,
            Producto producto,
            double cambio
    ) {
        this.estado = estado;
        this.producto = producto;
        this.cambio = cambio;
    }


    public EstadoCompra getEstado() {
        return estado;
    }


    public Producto getProducto() {
        return producto;
    }


    public double getCambio() {
        return cambio;
    }
}


/* =========================================================
   DTOs DE ENTRADA
   ========================================================= */

class RegistroProductoDTO {

    private final String codigo;
    private final String nombre;
    private final LocalDate fechaCaducidad;
    private final CategoriaProducto categoria;
    private final double precio;


    public RegistroProductoDTO(
            String codigo,
            String nombre,
            LocalDate fechaCaducidad,
            CategoriaProducto categoria,
            double precio
    ) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
        this.categoria = categoria;
        this.precio = precio;
    }


    public String getCodigo() {
        return codigo;
    }


    public String getNombre() {
        return nombre;
    }


    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }


    public CategoriaProducto getCategoria() {
        return categoria;
    }


    public double getPrecio() {
        return precio;
    }
}


class ReabastecimientoDTO {

    private final String codigo;
    private final int cantidad;


    public ReabastecimientoDTO(
            String codigo,
            int cantidad
    ) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }


    public String getCodigo() {
        return codigo;
    }


    public int getCantidad() {
        return cantidad;
    }
}


class CompraDTO {

    private final String codigoProducto;
    private final double cantidadRecibida;


    public CompraDTO(
            String codigoProducto,
            double cantidadRecibida
    ) {
        this.codigoProducto = codigoProducto;
        this.cantidadRecibida = cantidadRecibida;
    }


    public String getCodigoProducto() {
        return codigoProducto;
    }


    public double getCantidadRecibida() {
        return cantidadRecibida;
    }
}


/* =========================================================
   INTERFAZ INVENTARIO
   ========================================================= */

interface Inventario {

    ResultadoRegistro registrarProducto(
            Producto producto,
            double precio
    );

    Optional<ProductoInventario> buscarProducto(
            String codigo
    );

    ResultadoReabastecimiento reabastecer(
            String codigo,
            int cantidad
    );

    ResultadoEntrega despachar(
            String codigo
    );
}


/* =========================================================
   INTERFAZ MONEDERO
   ========================================================= */

interface Monedero {

    ResultadoCobro cobrar(
            double precio,
            double cantidadRecibida
    );

    double consultarFondos();
}


/* =========================================================
   CONTRATO DE LA MAQUINA
   ========================================================= */

interface MaquinaDespachadora {

    ResultadoRegistro registrarProducto(
            RegistroProductoDTO producto
    );

    Optional<ProductoInventario> buscarProducto(
            String codigo
    );

    ResultadoReabastecimiento reabastecer(
            ReabastecimientoDTO solicitud
    );

    ResultadoCompra comprar(
            CompraDTO solicitud
    );

    double consultarFondos();
}

/*
 * =========================================================
 * PRACTICA 9 - SMARTVEND
 * =========================================================
 *
 * Desarrolla aqui tu solucion utilizando Programacion
 * Orientada a Objetos.
 *
 * HackerRank proporciona:
 *
 * - enums
 * - DTOs
 * - clases de resultado
 * - interface Inventario
 * - interface Monedero
 * - interface MaquinaDespachadora
 *
 * No modifiques los contratos proporcionados.
 *
 *
 * Deberas desarrollar, entre otras, las clases:
 *
 * - Producto
 * - clases derivadas de Producto
 * - una implementacion de Inventario
 * - una implementacion de Monedero
 * - MaquinaExpendedora
 * - MaquinaExpendedora.Builder
 *
 *
 * MaquinaExpendedora debera implementar:
 *
 * MaquinaDespachadora
 *
 *
 * IMPORTANTE:
 *
 * HackerRank utiliza un unico archivo Java.
 * No declares tus clases como public.
 */


/* =========================================================
   DESARROLLA AQUI TU SOLUCION
   ========================================================= */


abstract class Producto {

    private final String codigo;
    private final String nombre;
    private final LocalDate fechaCaducidad;
    private final CategoriaProducto categoria;

    public Producto(
            String codigo,
            String nombre,
            LocalDate fechaCaducidad,
            CategoriaProducto categoria
    ) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }
}


class Bebida extends Producto {

    public Bebida(
            String codigo,
            String nombre,
            LocalDate fechaCaducidad
    ) {
        super(
                codigo,
                nombre,
                fechaCaducidad,
                CategoriaProducto.BEBIDA
        );
    }
}


class Snack extends Producto {

    public Snack(
            String codigo,
            String nombre,
            LocalDate fechaCaducidad
    ) {
        super(
                codigo,
                nombre,
                fechaCaducidad,
                CategoriaProducto.SNACK
        );
    }
}


class InventarioImpl implements Inventario {

    private static class Registro {

        private final Producto producto;
        private final double precio;
        private int existencias;

        public Registro(Producto producto, double precio) {
            this.producto = producto;
            this.precio = precio;
            this.existencias = 0;
        }

        public ProductoInventario obtener() {
            return new ProductoInventario(
                    producto,
                    precio,
                    existencias
            );
        }
    }

    private final java.util.Map<String, Registro> productos =
            new java.util.HashMap<>();

    @Override
    public synchronized ResultadoRegistro registrarProducto(
            Producto producto,
            double precio
    ) {
        if (producto == null
                || producto.getCodigo() == null
                || producto.getCodigo().trim().isEmpty()
                || producto.getNombre() == null
                || producto.getNombre().trim().isEmpty()
                || producto.getFechaCaducidad() == null
                || producto.getCategoria() == null) {
            return ResultadoRegistro.PRODUCTO_INVALIDO;
        }

        if (!Double.isFinite(precio) || precio <= 0) {
            return ResultadoRegistro.PRECIO_INVALIDO;
        }

        String codigo = producto.getCodigo();

        if (productos.containsKey(codigo)) {
            return ResultadoRegistro.CODIGO_DUPLICADO;
        }

        productos.put(codigo, new Registro(producto, precio));

        return ResultadoRegistro.PRODUCTO_REGISTRADO;
    }

    @Override
    public synchronized Optional<ProductoInventario> buscarProducto(
            String codigo
    ) {
        if (codigo == null) {
            return Optional.empty();
        }

        Registro registro = productos.get(codigo);

        if (registro == null) {
            return Optional.empty();
        }

        return Optional.of(registro.obtener());
    }

    @Override
    public synchronized ResultadoReabastecimiento reabastecer(
            String codigo,
            int cantidad
    ) {
        if (cantidad <= 0) {
            return ResultadoReabastecimiento.CANTIDAD_INVALIDA;
        }

        Registro registro = productos.get(codigo);

        if (registro == null) {
            return ResultadoReabastecimiento.PRODUCTO_NO_ENCONTRADO;
        }

        if (registro.existencias > Integer.MAX_VALUE - cantidad) {
            return ResultadoReabastecimiento.CANTIDAD_INVALIDA;
        }

        registro.existencias += cantidad;

        return ResultadoReabastecimiento.REABASTECIMIENTO_REALIZADO;
    }

    @Override
    public synchronized ResultadoEntrega despachar(String codigo) {
        Registro registro = productos.get(codigo);

        if (registro == null) {
            return ResultadoEntrega.PRODUCTO_NO_ENCONTRADO;
        }

        if (registro.existencias <= 0) {
            return ResultadoEntrega.SIN_EXISTENCIAS;
        }

        registro.existencias--;

        return ResultadoEntrega.PRODUCTO_PREPARADO;
    }
}


class MonederoImpl implements Monedero {

    private double fondos;

    public MonederoImpl(double fondosIniciales) {
        if (!Double.isFinite(fondosIniciales)
                || fondosIniciales < 0) {
            throw new IllegalArgumentException(
                    "Fondos iniciales invalidos"
            );
        }

        this.fondos = fondosIniciales;
    }

    public synchronized boolean puedeCobrar(
            double precio,
            double cantidadRecibida
    ) {
        if (!Double.isFinite(precio)
                || !Double.isFinite(cantidadRecibida)
                || precio <= 0
                || cantidadRecibida < precio) {
            return false;
        }

        double cambio = cantidadRecibida - precio;

        return Double.isFinite(fondos + precio)
                && cambio <= fondos;
    }

    @Override
    public synchronized ResultadoCobro cobrar(
            double precio,
            double cantidadRecibida
    ) {
        if (!Double.isFinite(precio)
                || !Double.isFinite(cantidadRecibida)
                || precio <= 0
                || cantidadRecibida < precio) {
            return new ResultadoCobro(
                    EstadoCobro.MONTO_INVALIDO,
                    0
            );
        }

        double cambio = cantidadRecibida - precio;

        if (cambio > fondos) {
            return new ResultadoCobro(
                    EstadoCobro.SIN_FONDOS_SUFFICIENTES,
                    0
            );
        }

        double nuevosFondos = fondos + precio;

        if (!Double.isFinite(nuevosFondos)) {
            return new ResultadoCobro(
                    EstadoCobro.MONTO_INVALIDO,
                    0
            );
        }

        fondos = nuevosFondos;

        return new ResultadoCobro(
                EstadoCobro.COBRO_REALIZADO,
                cambio
        );
    }

    @Override
    public synchronized double consultarFondos() {
        return fondos;
    }
}


class MaquinaExpendedora implements MaquinaDespachadora {

    private final Inventario inventario;
    private final Monedero monedero;

    private MaquinaExpendedora(
            Inventario inventario,
            Monedero monedero
    ) {
        if (inventario == null || monedero == null) {
            throw new IllegalArgumentException(
                    "Inventario y monedero son obligatorios"
            );
        }

        this.inventario = inventario;
        this.monedero = monedero;
    }

    public static class Builder {

        private Inventario inventario;
        private Monedero monedero;

        public Builder inventario(Inventario inventario) {
            this.inventario = inventario;
            return this;
        }

        public Builder monedero(Monedero monedero) {
            this.monedero = monedero;
            return this;
        }

        public Builder setInventario(Inventario inventario) {
            return inventario(inventario);
        }

        public Builder setMonedero(Monedero monedero) {
            return monedero(monedero);
        }

        public MaquinaExpendedora build() {
            return new MaquinaExpendedora(
                    inventario,
                    monedero
            );
        }
    }

    @Override
    public ResultadoRegistro registrarProducto(
            RegistroProductoDTO solicitud
    ) {
        if (solicitud == null
                || solicitud.getCategoria() == null) {
            return ResultadoRegistro.PRODUCTO_INVALIDO;
        }

        Producto producto;

        switch (solicitud.getCategoria()) {
            case BEBIDA:
                producto = new Bebida(
                        solicitud.getCodigo(),
                        solicitud.getNombre(),
                        solicitud.getFechaCaducidad()
                );
                break;

            case SNACK:
                producto = new Snack(
                        solicitud.getCodigo(),
                        solicitud.getNombre(),
                        solicitud.getFechaCaducidad()
                );
                break;

            default:
                return ResultadoRegistro.PRODUCTO_INVALIDO;
        }

        return inventario.registrarProducto(
                producto,
                solicitud.getPrecio()
        );
    }

    @Override
    public Optional<ProductoInventario> buscarProducto(
            String codigo
    ) {
        return inventario.buscarProducto(codigo);
    }

    @Override
    public ResultadoReabastecimiento reabastecer(
            ReabastecimientoDTO solicitud
    ) {
        if (solicitud == null) {
            return ResultadoReabastecimiento.PRODUCTO_NO_ENCONTRADO;
        }

        return inventario.reabastecer(
                solicitud.getCodigo(),
                solicitud.getCantidad()
        );
    }

    @Override
    public synchronized ResultadoCompra comprar(
            CompraDTO solicitud
    ) {
        if (solicitud == null) {
            return new ResultadoCompra(
                    EstadoCompra.PRODUCTO_NO_ENCONTRADO,
                    null,
                    0
            );
        }

        Optional<ProductoInventario> encontrado =
                inventario.buscarProducto(
                        solicitud.getCodigoProducto()
                );

        if (encontrado.isEmpty()) {
            return new ResultadoCompra(
                    EstadoCompra.PRODUCTO_NO_ENCONTRADO,
                    null,
                    0
            );
        }

        ProductoInventario registro = encontrado.get();
        Producto producto = registro.getProducto();

        if (registro.getExistencias() <= 0) {
            return new ResultadoCompra(
                    EstadoCompra.SIN_EXISTENCIAS,
                    producto,
                    0
            );
        }

        double precio = registro.getPrecio();
        double recibido = solicitud.getCantidadRecibida();

        if (!Double.isFinite(recibido)
                || recibido < precio
                || recibido < 0) {
            return new ResultadoCompra(
                    EstadoCompra.PAGO_INSUFICIENTE,
                    producto,
                    0
            );
        }

        MonederoImpl monederoInterno = null;

        if (monedero instanceof MonederoImpl) {
            monederoInterno = (MonederoImpl) monedero;

            if (!monederoInterno.puedeCobrar(
                    precio,
                    recibido
            )) {
                return new ResultadoCompra(
                        EstadoCompra.SIN_FONDOS_SUFFICIENTES,
                        producto,
                        0
                );
            }
        }

        ResultadoEntrega entrega =
                inventario.despachar(
                        solicitud.getCodigoProducto()
                );

        if (entrega != ResultadoEntrega.PRODUCTO_PREPARADO) {
            return new ResultadoCompra(
                    EstadoCompra.SIN_EXISTENCIAS,
                    producto,
                    0
            );
        }

        ResultadoCobro cobro = monedero.cobrar(
                precio,
                recibido
        );

        if (cobro.getEstado()
                == EstadoCobro.SIN_FONDOS_SUFFICIENTES) {
            if (inventario instanceof InventarioImpl) {
                ((InventarioImpl) inventario).reabastecer(
                        solicitud.getCodigoProducto(),
                        1
                );
            }

            return new ResultadoCompra(
                    EstadoCompra.SIN_FONDOS_SUFFICIENTES,
                    producto,
                    0
            );
        }

        if (cobro.getEstado() != EstadoCobro.COBRO_REALIZADO) {
            if (inventario instanceof InventarioImpl) {
                ((InventarioImpl) inventario).reabastecer(
                        solicitud.getCodigoProducto(),
                        1
                );
            }

            return new ResultadoCompra(
                    EstadoCompra.PAGO_INSUFICIENTE,
                    producto,
                    0
            );
        }

        return new ResultadoCompra(
                EstadoCompra.VENTA_REALIZADA,
                producto,
                cobro.getCambio()
        );
    }

    @Override
    public double consultarFondos() {
        return monedero.consultarFondos();
    }
}


/* =========================================================
   CLASE RESULT
   ========================================================= */

class Result {

    /*
     * HackerRank utilizara este metodo para obtener
     * la maquina construida por el alumno.
     *
     * Construye aqui los componentes necesarios y
     * devuelve una maquina valida.
     */

    public static MaquinaDespachadora crearMaquina(
            double fondosIniciales
    ) {

        /*
         * Debes:
         *
         * 1. Crear una implementacion de Inventario.
         * 2. Crear una implementacion de Monedero.
         * 3. Construir MaquinaExpendedora mediante Builder.
         * 4. Regresar la maquina construida.
         */

        Inventario inventario = new InventarioImpl();

        Monedero monedero = new MonederoImpl(
                fondosIniciales
        );

        return new MaquinaExpendedora.Builder()
                .inventario(inventario)
                .monedero(monedero)
                .build();
    }
}


public class EncapsulamientoeInterfaces {

    public static void main(String[] args)
            throws Exception {

        java.util.Locale.setDefault(
                java.util.Locale.US
        );

        java.io.BufferedReader reader =
                new java.io.BufferedReader(
                        new java.io.InputStreamReader(
                                System.in
                        )
                );


        /* =================================================
           FONDOS INICIALES
           ================================================= */

        double fondosIniciales =
                Double.parseDouble(
                        reader.readLine().trim()
                );


        /* =================================================
           CREAR LA MAQUINA DEL ALUMNO
           ================================================= */

        MaquinaDespachadora maquina =
                Result.crearMaquina(
                        fondosIniciales
                );


        if (maquina == null) {

            throw new IllegalStateException(
                    "Result.crearMaquina() regreso null"
            );
        }


        /* =================================================
           NUMERO DE OPERACIONES
           ================================================= */

        int numeroOperaciones =
                Integer.parseInt(
                        reader.readLine().trim()
                );


        /* =================================================
           EJECUTAR OPERACIONES
           ================================================= */

        for (int i = 0; i < numeroOperaciones; i++) {

            String linea =
                    reader.readLine();

            if (linea == null) {
                break;
            }

            ejecutarOperacion(
                    maquina,
                    linea
            );
        }
    }


    /* =====================================================
       INTERPRETAR OPERACION
       ===================================================== */

    private static void ejecutarOperacion(
            MaquinaDespachadora maquina,
            String linea
    ) {

        String[] partes =
                linea.split(
                        "\\|",
                        -1
                );

        String operacion =
                partes[0]
                        .trim()
                        .toUpperCase();


        switch (operacion) {

            case "REGISTRAR":
                registrar(
                        maquina,
                        partes
                );
                break;


            case "BUSCAR":
                buscar(
                        maquina,
                        partes
                );
                break;


            case "REABASTECER":
                reabastecer(
                        maquina,
                        partes
                );
                break;


            case "COMPRAR":
                comprar(
                        maquina,
                        partes
                );
                break;


            case "FONDOS":
                consultarFondos(
                        maquina
                );
                break;


            default:
                throw new IllegalArgumentException(
                        "Operacion desconocida: "
                                + operacion
                );
        }
    }


    /* =====================================================
       REGISTRAR
       ===================================================== */

    private static void registrar(
            MaquinaDespachadora maquina,
            String[] partes
    ) {

        RegistroProductoDTO producto =
                new RegistroProductoDTO(

                        partes[1].trim(),

                        partes[2].trim(),

                        LocalDate.parse(
                                partes[3].trim()
                        ),

                        CategoriaProducto.valueOf(
                                partes[4]
                                        .trim()
                                        .toUpperCase()
                        ),

                        Double.parseDouble(
                                partes[5].trim()
                        )
                );


        ResultadoRegistro resultado =
                maquina.registrarProducto(
                        producto
                );


        System.out.println(
                "REGISTRAR|"
                        + resultado
        );
    }


    /* =====================================================
       BUSCAR
       ===================================================== */

    private static void buscar(
            MaquinaDespachadora maquina,
            String[] partes
    ) {

        String codigo =
                partes[1].trim();


        Optional<ProductoInventario> resultado =
                maquina.buscarProducto(
                        codigo
                );


        if (resultado.isEmpty()) {

            System.out.println(
                    "BUSCAR|NO_ENCONTRADO"
            );

            return;
        }


        ProductoInventario producto =
                resultado.get();


        System.out.printf(
                java.util.Locale.US,
                "BUSCAR|ENCONTRADO|%.2f|%d%n",
                producto.getPrecio(),
                producto.getExistencias()
        );
    }


    /* =====================================================
       REABASTECER
       ===================================================== */

    private static void reabastecer(
            MaquinaDespachadora maquina,
            String[] partes
    ) {

        ReabastecimientoDTO solicitud =
                new ReabastecimientoDTO(

                        partes[1].trim(),

                        Integer.parseInt(
                                partes[2].trim()
                        )
                );


        ResultadoReabastecimiento resultado =
                maquina.reabastecer(
                        solicitud
                );


        System.out.println(
                "REABASTECER|"
                        + resultado
        );
    }


    /* =====================================================
       COMPRAR
       ===================================================== */

    private static void comprar(
            MaquinaDespachadora maquina,
            String[] partes
    ) {

        CompraDTO solicitud =
                new CompraDTO(

                        partes[1].trim(),

                        Double.parseDouble(
                                partes[2].trim()
                        )
                );


        ResultadoCompra resultado =
                maquina.comprar(
                        solicitud
                );


        System.out.printf(
                java.util.Locale.US,
                "COMPRAR|%s|%.2f%n",
                resultado.getEstado(),
                resultado.getCambio()
        );
    }


    /* =====================================================
       CONSULTAR FONDOS
       ===================================================== */

    private static void consultarFondos(
            MaquinaDespachadora maquina
    ) {

        System.out.printf(
                java.util.Locale.US,
                "FONDOS|%.2f%n",
                maquina.consultarFondos()
        );
    }
}