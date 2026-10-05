package ejercicio2;

	import java.io.*;
	import java.util.*;
	

	/* =====================================================
   ENUMERACIONES BASE
   ===================================================== */

enum AccionPedido {
    AGREGAR,
    CONFIRMAR
}

enum TipoDescuento {
    REGULAR,
    FRECUENTE,
    MAYOREO
}

enum EstadoAgregarProducto {
    AGREGADO,
    PRODUCTO_NO_EXISTE,
    CANTIDAD_INVALIDA
}


/* =====================================================
   DTO DE PRODUCTO
   ===================================================== */

final class ProductoDTO {

    private final String id;
    private final String nombre;
    private final String precio;

    public ProductoDTO(String id, String nombre, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }
}


/* =====================================================
   DTO DE DETALLE DEL PEDIDO
   ===================================================== */

final class DetallePedidoDTO {

    private final int idProducto;
    private final String nombre;
    private final int cantidad;

    public DetallePedidoDTO(
            int idProducto,
            String nombre,
            int cantidad) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}


/* =====================================================
   DTO DEL RESUMEN DEL PEDIDO
   ===================================================== */

final class ResumenPedidoDTO {

    private final List<DetallePedidoDTO> detalles;
    private final double subtotal;
    private final double descuento;
    private final double total;

    public ResumenPedidoDTO(
            List<DetallePedidoDTO> detalles,
            double subtotal,
            double descuento,
            double total) {

        this.detalles = detalles;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }
}


/* =====================================================
   STRATEGY
   ===================================================== */

interface DescuentoStrategy {

    double calcularDescuento(double subtotal);
}


/* =====================================================
   CONTRATO PARA OBJETOS CON DESCUENTO
   ===================================================== */

interface Discountable {

    void setDiscount(DescuentoStrategy descuentoStrategy);
}


/* =====================================================
   REPOSITORIO DE PRODUCTOS
   ===================================================== */

interface ProductoRepository {

    boolean guardar(ProductoDTO producto);

    Producto buscarPorId(int idProducto);

    boolean existe(int idProducto);
}


/* =====================================================
   SERVICIO DE PEDIDOS
   ===================================================== */

interface PedidoService {

    EstadoAgregarProducto agregarProducto(
            Pedido pedido,
            int idProducto,
            int cantidad);

    DescuentoStrategy SelectorDescuento(
            TipoDescuento tipoDescuento);

    ResumenPedidoDTO confirmarPedido(
            Pedido pedido,
            TipoDescuento tipoDescuento);
}


/* =====================================================
   PRODUCTO
   ===================================================== */

final class Producto {

    private final int id;
    private final String nombre;
    private final double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}


/* =====================================================
   ELEMENTO DEL PEDIDO
   ===================================================== */

final class ElementoPedido {

    private final Producto producto;
    private int cantidad;

    public ElementoPedido(
            Producto producto,
            int cantidad) {

        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void agregarCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}


/* =====================================================
   PEDIDO
   ===================================================== */

final class Pedido implements Discountable {

    private final Map<Integer, ElementoPedido> elementos;
    private DescuentoStrategy descuentoStrategy;

    private Pedido() {
        elementos = new LinkedHashMap<>();
        descuentoStrategy = new DescuentoRegular();
    }

    public static Pedido empty() {
        return new Pedido();
    }

    @Override
    public void setDiscount(
            DescuentoStrategy descuentoStrategy) {

        this.descuentoStrategy = descuentoStrategy;
    }

    public void agregarProducto(
            Producto producto,
            int cantidad) {

        int id = producto.getId();

        if (elementos.containsKey(id)) {
            elementos.get(id).agregarCantidad(cantidad);
        } else {
            elementos.put(
                    id,
                    new ElementoPedido(producto, cantidad)
            );
        }
    }

    public Collection<ElementoPedido> getElementos() {
        return Collections.unmodifiableCollection(
                elementos.values()
        );
    }

    public double calcularSubtotal() {

        double subtotal = 0.0;

        for (ElementoPedido elemento : elementos.values()) {
            subtotal += elemento.calcularSubtotal();
        }

        return subtotal;
    }

    public double calcularDescuento(double subtotal) {
        return descuentoStrategy.calcularDescuento(subtotal);
    }
}


/* =====================================================
   ESTRATEGIAS DE DESCUENTO
   ===================================================== */

class DescuentoRegular implements DescuentoStrategy {

    @Override
    public double calcularDescuento(double subtotal) {
        return 0.0;
    }
}


class DescuentoFrecuente implements DescuentoStrategy {

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10;
    }
}


class DescuentoMayoreo implements DescuentoStrategy {

    @Override
    public double calcularDescuento(double subtotal) {

        if (subtotal >= 5000.0) {
            return subtotal * 0.15;
        }

        return 0.0;
    }
}


/* =====================================================
   REPOSITORIO DE PRODUCTOS
   ===================================================== */

class ProductoRepositoryImpl
        implements ProductoRepository {

    private final Map<Integer, Producto> productos;

    public ProductoRepositoryImpl() {
        productos = new HashMap<>();
    }

    @Override
    public boolean guardar(ProductoDTO dto) {

        if (dto == null) {
            return false;
        }

        try {

            int id = Integer.parseInt(
                    dto.getId().trim()
            );

            String nombre = dto.getNombre().trim();

            double precio = Double.parseDouble(
                    dto.getPrecio().trim()
            );

            if (id < 1
                    || nombre.isEmpty()
                    || precio <= 0
                    || !Double.isFinite(precio)) {

                return false;
            }

            if (productos.containsKey(id)) {
                return false;
            }

            Producto producto =
                    new Producto(
                            id,
                            nombre,
                            precio
                    );

            productos.put(id, producto);

            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        return productos.get(idProducto);
    }

    @Override
    public boolean existe(int idProducto) {
        return productos.containsKey(idProducto);
    }
}


/* =====================================================
   SERVICIO DE PEDIDOS
   ===================================================== */

class PedidoServiceImpl implements PedidoService {

    private final ProductoRepository productoRepository;

    public PedidoServiceImpl(
            ProductoRepository productoRepository) {

        this.productoRepository = productoRepository;
    }

    @Override
    public EstadoAgregarProducto agregarProducto(
            Pedido pedido,
            int idProducto,
            int cantidad) {

        if (cantidad <= 0) {
            return EstadoAgregarProducto.CANTIDAD_INVALIDA;
        }

        if (pedido == null) {
            return EstadoAgregarProducto.PRODUCTO_NO_EXISTE;
        }

        Producto producto =
                productoRepository.buscarPorId(idProducto);

        if (producto == null) {
            return EstadoAgregarProducto.PRODUCTO_NO_EXISTE;
        }

        pedido.agregarProducto(
                producto,
                cantidad
        );

        return EstadoAgregarProducto.AGREGADO;
    }

    @Override
    public DescuentoStrategy SelectorDescuento(
            TipoDescuento tipoDescuento) {

        if (tipoDescuento == null) {
            return new DescuentoRegular();
        }

        switch (tipoDescuento) {

            case REGULAR:
                return new DescuentoRegular();

            case FRECUENTE:
                return new DescuentoFrecuente();

            case MAYOREO:
                return new DescuentoMayoreo();

            default:
                return new DescuentoRegular();
        }
    }

    @Override
    public ResumenPedidoDTO confirmarPedido(
            Pedido pedido,
            TipoDescuento tipoDescuento) {

        if (pedido == null) {

            return new ResumenPedidoDTO(
                    new ArrayList<DetallePedidoDTO>(),
                    0.0,
                    0.0,
                    0.0
            );
        }

        DescuentoStrategy estrategia =
                SelectorDescuento(tipoDescuento);

        pedido.setDiscount(estrategia);

        List<DetallePedidoDTO> detalles =
                new ArrayList<>();

        for (ElementoPedido elemento :
                pedido.getElementos()) {

            Producto producto =
                    elemento.getProducto();

            detalles.add(
                    new DetallePedidoDTO(
                            producto.getId(),
                            producto.getNombre(),
                            elemento.getCantidad()
                    )
            );
        }

        double subtotal =
                pedido.calcularSubtotal();

        double descuento =
                pedido.calcularDescuento(subtotal);

        double total =
                subtotal - descuento;

        return new ResumenPedidoDTO(
                detalles,
                subtotal,
                descuento,
                total
        );
    }
}


/* =====================================================
   CLASE PRINCIPAL
   ===================================================== */

public class SistemaDePedidosConDescuentos {

    public static void main(String[] args)
            throws Exception {

        Locale.setDefault(Locale.US);

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(System.in)
                );

        String productos = br.readLine();

        String operaciones = br.readLine();

        ProductoRepository productoRepository =
                new ProductoRepositoryImpl();

        PedidoService pedidoService =
                new PedidoServiceImpl(
                        productoRepository
                );

        Pedido pedido =
                Pedido.empty();


        /* =================================================
           REGISTRO DE PRODUCTOS
           ================================================= */

        if (productos != null
                && !productos.isBlank()
                && !productos.equals("-")) {

            String[] registros =
                    productos.split(";");

            for (String registro : registros) {

                String[] datos =
                        registro.split("\\|", 3);

                if (datos.length < 3) {
                    continue;
                }

                ProductoDTO productoDTO =
                        new ProductoDTO(
                                datos[0].trim(),
                                datos[1].trim(),
                                datos[2].trim()
                        );

                productoRepository.guardar(
                        productoDTO
                );
            }
        }


        /* =================================================
           PROCESAMIENTO DE OPERACIONES
           ================================================= */

        StringBuilder salida =
                new StringBuilder();

        if (operaciones != null
                && !operaciones.isBlank()
                && !operaciones.equals("-")) {

            String[] listaOperaciones =
                    operaciones.split(";");

            for (String operacion :
                    listaOperaciones) {

                String[] datos =
                        operacion.split("\\|");

                if (datos.length == 0) {
                    continue;
                }

                AccionPedido accion =
                        AccionPedido.valueOf(
                                datos[0].trim()
                        );


                /* =========================================
                   AGREGAR PRODUCTO
                   ========================================= */

                if (accion ==
                        AccionPedido.AGREGAR) {

                    int idProducto =
                            Integer.parseInt(
                                    datos[1].trim()
                            );

                    int cantidad =
                            Integer.parseInt(
                                    datos[2].trim()
                            );

                    EstadoAgregarProducto estado =
                            pedidoService.agregarProducto(
                                    pedido,
                                    idProducto,
                                    cantidad
                            );

                    salida.append("AGREGAR ")
                            .append(idProducto)
                            .append(" ")
                            .append(estado)
                            .append("\n");
                }


                /* =========================================
                   CONFIRMAR PEDIDO
                   ========================================= */

                else if (accion ==
                        AccionPedido.CONFIRMAR) {

                    TipoDescuento tipoDescuento =
                            TipoDescuento.valueOf(
                                    datos[1].trim()
                            );

                    ResumenPedidoDTO resumen =
                            pedidoService.confirmarPedido(
                                    pedido,
                                    tipoDescuento
                            );

                    if (resumen == null) {

                        throw new IllegalStateException(
                                "confirmarPedido no debe regresar null"
                        );
                    }

                    imprimirResumen(
                            salida,
                            resumen
                    );
                }
            }
        }


        /* =================================================
           SALIDA
           ================================================= */

        System.out.print(
                salida.toString()
        );
    }


    /* =====================================================
       IMPRIMIR RESUMEN
       ===================================================== */

    private static void imprimirResumen(
            StringBuilder salida,
            ResumenPedidoDTO resumen) {

        List<DetallePedidoDTO> detalles =
                resumen.getDetalles();

        if (detalles == null) {

            throw new IllegalStateException(
                    "La lista de detalles no debe ser null"
            );
        }


        /* -------------------------------------------------
           DETALLE DE PRODUCTOS
           ------------------------------------------------- */

        for (DetallePedidoDTO detalle :
                detalles) {

            salida.append("ITEM ")
                    .append(
                            detalle.getIdProducto()
                    )
                    .append(" ")
                    .append(
                            detalle.getNombre()
                    )
                    .append(" ")
                    .append(
                            detalle.getCantidad()
                    )
                    .append("\n");
        }


        /* -------------------------------------------------
           RESUMEN ECONOMICO
           ------------------------------------------------- */

        salida.append(
                String.format(
                        Locale.US,
                        "RESUMEN %.2f %.2f %.2f",
                        resumen.getSubtotal(),
                        resumen.getDescuento(),
                        resumen.getTotal()
                )
        );
    }
}

