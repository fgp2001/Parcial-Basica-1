package ar.edu.unlam.pb1.dominio;

import java.util.Iterator;

import ar.edu.unlam.pb1.dominio.enums.TipoDeIva;

public class Bazar {

	private static final int MAXIMO_PRODUCTOS = 1000;
	// TODO: Completar getters, setters, constructor y metodos necesarios para
	// garantizar el correcto funcionamiento.

	private String nombre;
	private Producto[] productos;

	public Bazar(String nombre) {
		this.nombre = nombre;
		this.productos = new Producto[MAXIMO_PRODUCTOS];
	}

	public Producto[] getProductos() {
		return productos;
	}

	public void setProductos(Producto[] productos) {
		this.productos = productos;
	}

	public Producto obtenerProductoPorCodigoDeBarras(String codigoDeBarras) {
		// TODO: Buscar y devolver el producto que cumpla con el codigo de barras
		// suministrado. En caso de no existir algun producto, devolver null.

		Producto producto = null;
		int posicion = 0;
		boolean encontrado = false;

		while (!encontrado && posicion < this.productos.length) {
			if (this.productos[posicion] != null
					&& this.productos[posicion].getCodigoBarras().equalsIgnoreCase(codigoDeBarras)) {
				producto = this.productos[posicion];
				encontrado = true;
			}
			posicion++;
		}

		return producto;
	}

	public String agregarProducto(String codigoDeBarras, char seccion, String nombre, TipoDeIva tipoDeIva, int stock,
			double porcentajeGanancia, double costo) {

		// TODO: Se debera agregar un producto al bazar. Antes de realizar esta accion,
		// se debera validar el producto (ver metodo productoValido()).
		// Si el mensaje obtenido esta vacio, se debe proceder a agregar el producto al
		// bazar.
		// Devolver el mensaje resultante de validar el producto (puede estar vacio o
		// tener indicaciones de validacion).
		String valido = productoValido(codigoDeBarras, stock);
		Producto producto = null;
		boolean agregado = false;
		if (valido.isEmpty()) {
			producto = new Producto(codigoDeBarras, seccion, nombre, tipoDeIva, stock, porcentajeGanancia, costo);

			// SE AGREGA AL ARRAY
			if (producto != null) {
				int posicion = 0;
				while (posicion < this.productos.length && !agregado) {
					if (this.productos[posicion] == null) {
						this.productos[posicion] = producto;
						agregado = true;
					}
					posicion++;
				}
			}

		}

		return valido;

	}

	private String productoValido(String codigoDeBarras, int stock) {
		// TODO: Verificar si los datos para crear un producto son validos. En caso de
		// ser valido, se debera devolver un texto vacio ("").
		// Las siguientes validaciones deben considerarse:
		// - El codigo de barras debe ser valido (ver metodo codigoDeBarrasValido()). En
		// caso de no ser valido agregar al mensaje de respuesta: "\nCodigo de barras
		// invalido."
		// - No debe existir otro producto con el mismo codigo de barras. Si existe,
		// agregar al mensaje de respuesta: "\nCodigo de barras existente."
		// - El stock ingresado debe ser mayor que cero. Si no lo fuera, agregar al
		// mensaje de respuesta: "\nEl stock no puede ser negativo."
		// Ejemplo de texto de respuesta con las 3 validaciones:
		// "\nCodigo de barras invalido.\nCodigo de barras existente.\nEl stock no puede
		// ser negativo."
		int posicion = 0;

		String productoValido = " ";
		boolean codigoDeBarrasValido = this.codigoDeBarrasValido(codigoDeBarras);
		Producto productoBuscado = this.obtenerProductoPorCodigoDeBarras(codigoDeBarras);
		boolean productoExistente = false;

		while (!productoExistente && posicion < this.productos.length) {
			if (this.productos[posicion] != null && this.productos[posicion].getCodigoBarras().equals(codigoDeBarras)) {
				productoExistente = true;
			}
			posicion++;
		}

		if (stock > 0) {
			productoValido += "\nEl stock no puede ser negativo.";

		}
		if (productoExistente) {
			productoValido += "\nCodigo de barras existente.";
		}
		if (!codigoDeBarrasValido) {
			productoValido += "\nCodigo de barras Invalido.";
		}
		return productoValido;
	}

	private boolean codigoDeBarrasValido(String codigoDeBarras) {
		// TODO: Se debera devolver verdadero en caso de que el codigo de barras este
		// conformado por 12 caracteres y todos ellos sean numeros.

		// ASCII O ISDIGIT

		boolean codigoValido = false;
		boolean contieneNumeros = true;

		for (int i = 0; i < codigoDeBarras.length(); i++) {
			if (!Character.isDigit(codigoDeBarras.charAt(i))) {
				contieneNumeros = false;
				break;
			}
		}

		if (codigoDeBarras.length() == 12 && contieneNumeros) {
			codigoValido = true;
		}

		return codigoValido;
	}

	public boolean agregarStockAProducto(String codigoDeBarras, int stockParaAgregar) {
		// TODO: Agregar el stock indicado al producto que cumpla con el codigo de
		// barras, solo si el producto existe.
		boolean agregado = false;
		Producto producto = obtenerProductoPorCodigoDeBarras(codigoDeBarras);
		if (producto != null) {
			producto.agregarStock(stockParaAgregar);
			agregado = true;
		} else {
			agregado = false;
		}

		return agregado;
	}

	public void incrementarCostoDeProductoConPorcentaje(String codigoDeBarras, double porcentajeIncremento) {
		// TODO: Incrementar el costo de un producto el cual se debera buscar por su
		// codigo de barras, en el porcentaje indicado.
		// Ejemplo: costo = 100, porcentaje 10% -> nuevo costo = 110
		boolean codigoValido = codigoDeBarrasValido(codigoDeBarras);
		Producto producto = null;
		if (codigoValido) {
			producto = obtenerProductoPorCodigoDeBarras(codigoDeBarras);
		}

		if (producto != null) {
			producto.incrementarCosto(porcentajeIncremento);
		}

	}

	public Producto[] obtenerProductosPorSeccion(char seccion) {
		// TODO: Se debera devolver un array de productos que contengan solo los
		// productos que se encuentren en la seccion indicada.

		Producto[] arrayPorSeccion = new Producto[this.productos.length];
		int posicion = 0;

		for (int i = 0; i < this.productos.length; i++) {
			if (this.productos[i] != null && this.productos[i].getSeccion() == seccion) {
				arrayPorSeccion[posicion++] = this.productos[i];
			}
		}

		return arrayPorSeccion;
	}

	public double obtenerPromedioDePrecioDeVentaDeProductosPorSeccionConOSinIva(char seccion, boolean incluyeIva) {
		// TODO: Se debera calcular y devolver el promedio de precio de venta de
		// productos que se encuentren en la seccion indicada, considerando si se debe
		// incluir el IVA o no.
		// Si no hay productos en dicha seccion, se debera devolver cero. Si hay, el
		// promedio se debera mostrar redondeado segun si sus decimales son mayor o
		// igual a cinco.

		// VER

		Producto[] arrayProductos = obtenerProductosPorSeccion(seccion);
		double promedio = 0;
		double cantProductos = 0;
		double monto = 0;
		boolean encontrado = false;

		if (arrayProductos != null) {
			for (int i = 0; i < arrayProductos.length; i++) {
				monto += arrayProductos[i].obtenerPrecioDeVenta(incluyeIva);
				cantProductos++;

			}
			encontrado = true;
		}

		else {
			promedio = 0;
		}

		if (encontrado) {
			promedio = monto / cantProductos++;
//		
		}

		return Math.round(cantProductos);
	}

}
