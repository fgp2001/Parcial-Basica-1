package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.dominio.Bazar;
import ar.edu.unlam.pb1.dominio.Producto;
import ar.edu.unlam.pb1.dominio.enums.TipoDeIva;
import ar.edu.unlam.pb1.interfaz.enums.MenuPrincipal;

public class GestionDeBazar {

	private static final Scanner TECLADO = new Scanner(System.in);
	private static final String MENU_TIPO_DE_IVA = "\n\nIngrese el tipo de planta:\n1 - General\n2 - Reducido I\n3 - Reducido II\n4 - Super Reducido";

	public static void main(String[] args) {
		// TODO: Escriba el codigo necesario para garantizar el correcto funcionamiento
		// del software. Para armar el menu, se debera utilizar el enum MenuPrincipal,
		// buscando llevar el codigo a ejecutarse (en cada caso del menu) a un metodo
		// apropiado (Ver los métodos incluídos a continuación).

		int numeroIngresado = 0;
		MenuPrincipal opcionMenu = null;

		String nombre = ingresarString("\nIngrese el nombre del bazar:");
		Bazar bazar = new Bazar(nombre);

		do {
			mostrarMenuPrincipal();

			opcionMenu = obtenerOpcionDeEnumParaMenuPrincipal();

			switch (opcionMenu) {
			case AGREGAR_PRODUCTO:
				agregarProducto(bazar);
				break;
			case AGREGAR_STOCK_A_PRODUCTO:
				agregarStockAProducto(bazar);
				break;
			case INCREMENTAR_COSTO_DE_PRODUCTO_EN_PORCENTAJE:
				incrementarCostoDeProductoEnPorcentaje(bazar);
				break;
			case MOSTRAR_PRODUCTOS_POR_SECCION:
				mostrarProductosPorSeccion(bazar);
				break;
			case MOSTRAR_PROMEDIO_DE_PRECIO_VENTA_DE_PRODUCTOS_POR_SECCION_CON_O_SIN_IVA:
				mostrarPromedioDePrecioVentaDeProductosPorSeccionConOSinIva(bazar);
				break;
			case SALIR:
				mostrarPorPantalla("\n\nHasta luego!");

				break;
			default:
				break;
			}

		} while (!opcionMenu.equals(MenuPrincipal.SALIR));

	}

	private static void agregarProducto(Bazar bazar) {
		// TODO: El usuario debera ingresar los datos de un producto. Para la "seccion"
		// ver el metodo
		// ingresarSeccionValidada().

		// El porcentaje de ganancia y costo del producto, no pueden ser menores a cero.
		// En caso de que se ingrese un valor menor a cero, se debera continuar
		// solicitando el dato hasta que se ingrese uno valido.
		// Si el mensaje de respuesta al momento de agregar no esta vacio, mostrar el
		// mensaje "\nNo se pudo agregar el producto:" seguido del contenido del mensaje
		// de respuesta.
		// Si el mensaje esta vacio, mostrar un mensaje de exito.

		String codigoDeBarra = ingresarString("\nIngrese el codigo de barra del producto: ");
		mostrarPorPantalla("Ingresar la sección");
		char seccion = ingresarSeccionValidada();
		String nombre = ingresarString("\nIngrese el nombre del producto");
		TipoDeIva tipoIva = ingresarTipoDeIva(MENU_TIPO_DE_IVA);
		int stock = ingresarNumeroEntero("\nIngresar el stock del producto");

		double porcentajeGanancia = 0;
		double costo = 0;
		boolean datosValidos = false;
		do {
			costo = ingresarDouble("\nIngresar el costo del producto:");
			porcentajeGanancia = ingresarDouble("\nIngresar el porcentaje de ganancia del producto:");
			
		} while (costo < 0 || porcentajeGanancia < 0);

		String mensaje = bazar.agregarProducto(codigoDeBarra, seccion, nombre, tipoIva, stock, porcentajeGanancia,
				costo);

		if (!mensaje.isEmpty()) {
			mostrarPorPantalla("\nNo se pudo agregar el producto" + mensaje);
		}else {
			mostrarPorPantalla("\nProducto agregado exitosamente.");
		}
		

	}

	private static void agregarStockAProducto(Bazar bazar) {
		// TODO: Se deben mostrar los productos. El usuario debe ingresar el codigo de
		// barras del producto al que
		// se le agregara stock. Si el valor para agregar al stock es menor a cero, se
		// debera seguir solicitando el dato.
		// Mostrar un mensaje de exito en caso de concretar la operacion o "\nProducto
		// inexistente." si no fue posible agregarle stock al producto.

		mostrarProductos(bazar.getProductos());
		String codigoBarra = ingresarString("\nIngresar el codigo de barra del producto a modificar stock: ");
		Producto productoPrueba = bazar.obtenerProductoPorCodigoDeBarras(codigoBarra);
		boolean stockAgregado = false;
		int stock = 0;

		if (productoPrueba != null) {

			do {
				stock = ingresarNumeroEntero("Ingresar el nuevo stock del producto");
			} while (stock < 0);

			stockAgregado = bazar.agregarStockAProducto(codigoBarra, stock);
		}

		if (stockAgregado) {
			mostrarPorPantalla("\nStock agregado exitosamente!");
		} else {
			mostrarPorPantalla("\nProducto inexistente...");
		}

	}

	private static void incrementarCostoDeProductoEnPorcentaje(Bazar bazar) {
		// TODO: Se deben mostrar los productos. El usuario debera ingresar el codigo de
		// barras del producto al que se
		// le quiere incrementar el costo y, el porcentaje que se debera incrementar.
		// Ejemplo de datos ingresados por el usuario:
		// codigoDeBarras = 111111111111, porcentaje = 10%
		// Luego de incrementar el costo, mostrar el producto actualizado.

		mostrarProductos(bazar.getProductos());
		String codigoBarra = ingresarString("\nIngresar el codigo de barra del producto a incrementar el costo: ");
		Producto producto = bazar.obtenerProductoPorCodigoDeBarras(codigoBarra);
		double porcentajeGanancia = ingresarDouble("\nIngresar el porcentaje de ganancia del producto:");

		bazar.incrementarCostoDeProductoConPorcentaje(codigoBarra, porcentajeGanancia);

		mostrarPorPantalla(producto.toString());

	}

	private static void mostrarProductosPorSeccion(Bazar bazar) {
		char seccion = ingresarSeccionValidada();
		mostrarProductos(bazar.obtenerProductosPorSeccion(seccion));
	}

	private static void mostrarPromedioDePrecioVentaDeProductosPorSeccionConOSinIva(Bazar bazar) {
		// TODO: Se debe solicitar el ingreso de la seccion y si los precios llevan IVA
		// para obtener el promedio solicitado.
		// Si los precios deben incluir el IVA, el usuario debe ingresar una S, caso
		// contrario una letra N.
		// Mostrar por pantalla el promedio de precios de venta de productos en la
		// seccion indicada contemplando el IVA.
		char seccion = ingresarSeccionValidada();
		char opcionIva = ingresarChar(
				"\nIngrese S en caso de que el producto INCLUYA IVA.\nIngrese N en caso de que no.");
		boolean incluyeIva = false;

		if (opcionIva == 'S' || opcionIva == 'S') {
			incluyeIva = true;
		}
		if (opcionIva == 'N' || opcionIva == 'n') {
			incluyeIva = false;
		}

		double promedioPrecioVenta = bazar.obtenerPromedioDePrecioDeVentaDeProductosPorSeccionConOSinIva(seccion,
				incluyeIva);

		mostrarPorPantalla(
				"El promedio de precio venta para la seccion de " + seccion + "es de $" + promedioPrecioVenta);

		// PROBAR

	}

	private static char ingresarSeccionValidada() {
		// TODO: Solicitar al usuario el ingreso de un caracter dentro de los siguientes
		// posibles: A - B - C - D.
		// En caso de ingresar una opcion invalida, continuar solicitando el dato hasta
		// que se ingrese una opcion valida.
		char seccion;
		do {
			seccion = ingresarChar("\nIngresar la seccion:");
		} while (seccion != 'a' || seccion == 'b' || seccion == 'c' || seccion == 'd'); // REVISAR / CONSULTAR
		return seccion;
	}

	private static void mostrarMenuPrincipal() {

		String menu = "";

		for (int i = 0; i < MenuPrincipal.values().length; i++) {
			menu += "\n" + (i + 1) + ") " + MenuPrincipal.values()[i];
		}

		mostrarPorPantalla(menu);
	}

	private static MenuPrincipal obtenerOpcionDeEnumParaMenuPrincipal() {
		int opcion = 0;
		mostrarMenuPrincipal();
		opcion = ingresarNumeroEntero("\nIngrese opcion: ");
		MenuPrincipal menuPrincipal = MenuPrincipal.values()[opcion - 1];
		return menuPrincipal;
	}

	private static TipoDeIva ingresarTipoDeIva(String mensaje) {
		int numeroIngresado = ingresarNumeroEntero(mensaje);
		TipoDeIva opcionSeleccionada = TipoDeIva.values()[numeroIngresado - 1];
		return opcionSeleccionada;

		// VALIDACION
	}

	private static int ingresarNumeroEntero(String mensaje) {
		mostrarPorPantalla(mensaje);
		return TECLADO.nextInt();
	}

	private static String ingresarString(String mensaje) {
		mostrarPorPantalla(mensaje);
		return TECLADO.next();
	}

	private static double ingresarDouble(String mensaje) {
		mostrarPorPantalla(mensaje);
		return TECLADO.nextDouble();
	}

	private static char ingresarChar(String mensaje) {
		mostrarPorPantalla(mensaje);
		return TECLADO.next().charAt(0);
	}

	private static void mostrarPorPantalla(String mensaje) {
		System.out.println(mensaje);
	}

	private static void mostrarProductos(Producto[] productos) {
		for (int i = 0; i < productos.length; i++) {
			if (productos[i] != null) {
				mostrarPorPantalla(productos[i].toString());
			}
		}
	}
}
