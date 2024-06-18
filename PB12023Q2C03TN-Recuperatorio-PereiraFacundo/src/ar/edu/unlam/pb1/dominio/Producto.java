package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.TipoDeIva;

public class Producto {
	private final double IVA_GENERAL = 1.27;
	private final double REDUCIDO_I = 1.21;
	private final double REDUCIDO_II = 10.05;
	private final double SUPER_REDUCIDO = 0.25;

	private String codigoBarras;
	private String nombre;
	private char seccion;
	private TipoDeIva tipoDeIva;
	private int stock;
	private double porcentajeGanancia;
	private double costo;

	// TODO: Completar getters, setters, constructor y metodos necesarios para
	// garantizar el correcto funcionamiento. No olvidar incluir el precio de venta
	// al mostrar un producto (mostrando el precio de venta con IVA).

	public Producto(String codigoBarras, char seccion, String nombre, TipoDeIva tipoDeIva, int stock,
			double porcentajeGanancia, double costo) {
		this.codigoBarras = codigoBarras;
		this.seccion = seccion;
		this.nombre = nombre;
		this.tipoDeIva = tipoDeIva;
		this.stock = stock;
		this.porcentajeGanancia = porcentajeGanancia;
		this.costo = costo;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public double getCosto() {
		return costo;
	}

	public void incrementarCosto(double porcentaje) {
		this.costo += (this.costo * (porcentaje / 100));
	}

	public int getStock() {
		return stock;
	}

	public void agregarStock(int stock) {
		this.stock += stock;
	}

	public char getSeccion() {
		return seccion;
	}

	public void setSeccion(char seccion) {
		this.seccion = seccion;
	}

	public double obtenerPrecioDeVenta(boolean incluyeIva) {
		// TODO: Calcular y devolver el precio de venta de un producto considerando la
		// siguiente ecuación:
		// Precio venta = (costo / (Base porcentual - porcentaje de ganancia)) * 100.
		// Para la base porcentual, utilizaremos 100%.
		// Ejemplo: ($100 / (100% - 20%)) * 100 (este valor es constante).
		// En caso de que el precio de venta deba incluir el porcentaje de IVA, se
		// deberá
		// agregar al precio de venta segun su tipo. Siempre devolver el precio de venta
		// redondeado (el valor decimal sea mayor o igual a 5).

		double precioDeVenta = 0;
		double precioFinal = 0;
		precioDeVenta = (this.costo / (100 - this.porcentajeGanancia)) * 100;

		if (incluyeIva) {
			switch (this.tipoDeIva) {
			case GENERAL:
				precioFinal = precioDeVenta * IVA_GENERAL;
				precioDeVenta = precioFinal;
				break;
			case REDUCIDO_I:
				precioFinal = precioDeVenta * REDUCIDO_I;
				precioDeVenta = precioFinal;
				break;
			case REDUCIDO_II:
				precioFinal = precioDeVenta * REDUCIDO_II;
				precioDeVenta = precioFinal;
				break;
			case SUPER_REDUCIDO:
				precioFinal = precioDeVenta * SUPER_REDUCIDO;
				precioDeVenta = precioFinal;
				break;
			default:
				break;
			}

		}
	

		return Math.round(precioDeVenta);
	}

	@Override
	public String toString() {
		return "Producto [codigoBarras=" + codigoBarras + ", nombre=" + nombre + ", seccion=" + seccion + ", tipoDeIva="
				+ tipoDeIva + ", stock=" + stock + ", porcentajeGanancia=" + porcentajeGanancia + ", costo=" + costo
				+ "]";
	}

}
