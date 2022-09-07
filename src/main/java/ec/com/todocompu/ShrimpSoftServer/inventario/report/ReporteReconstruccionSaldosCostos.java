package ec.com.todocompu.ShrimpSoftServer.inventario.report;

public class ReporteReconstruccionSaldosCostos {

	private String errBodega;
	private String errProductoCodigo;
	private String errProductoNombre;
	private Integer errCantidad;
	private String errDesde;
	private String errHasta;

	public ReporteReconstruccionSaldosCostos() {

	}

	public ReporteReconstruccionSaldosCostos(String errBodega, String errProductoCodigo, String errProductoNombre,
			Integer errCantidad, String errDesde, String errHasta) {
		this.errBodega = errBodega;
		this.errProductoCodigo = errProductoCodigo;
		this.errProductoNombre = errProductoNombre;
		this.errCantidad = errCantidad;
		this.errDesde = errDesde;
		this.errHasta = errHasta;
	}

	public String getErrBodega() {
		return errBodega;
	}

	public void setErrBodega(String errBodega) {
		this.errBodega = errBodega;
	}

	public String getErrProductoCodigo() {
		return errProductoCodigo;
	}

	public void setErrProductoCodigo(String errProductoCodigo) {
		this.errProductoCodigo = errProductoCodigo;
	}

	public String getErrProductoNombre() {
		return errProductoNombre;
	}

	public void setErrProductoNombre(String errProductoNombre) {
		this.errProductoNombre = errProductoNombre;
	}

	public Integer getErrCantidad() {
		return errCantidad;
	}

	public void setErrCantidad(Integer errCantidad) {
		this.errCantidad = errCantidad;
	}

	public String getErrDesde() {
		return errDesde;
	}

	public void setErrDesde(String desde) {
		this.errDesde = errDesde;
	}

	public String getErrHasta() {
		return errHasta;
	}

	public void setErrHasta(String errHasta) {
		this.errHasta = errHasta;
	}

}
