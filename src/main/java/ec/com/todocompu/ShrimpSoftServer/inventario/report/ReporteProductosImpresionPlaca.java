package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteProductosImpresionPlaca implements Serializable {
	private static final long serialVersionUID = 1L;

	private String lpspCodigoPrincipal;
	private String lpspCodigoBarra;
	private String lpspNombre;
	private String proEmpaque;
	private BigDecimal lpspPrecio1;
	private BigDecimal lpspPrecio2;
	private BigDecimal lpspPrecio3;

	public ReporteProductosImpresionPlaca() {
	}

	public ReporteProductosImpresionPlaca(String lpspCodigoPrincipal, String lpspCodigoBarra, String lpspNombre,
			String proEmpaque, BigDecimal lpspPrecio1, BigDecimal lpspPrecio2, BigDecimal lpspPrecio3) {
		this.lpspCodigoPrincipal = lpspCodigoPrincipal;
		this.lpspCodigoBarra = lpspCodigoBarra;
		this.lpspNombre = lpspNombre;
		this.proEmpaque = proEmpaque;
		this.lpspPrecio1 = lpspPrecio1;
		this.lpspPrecio2 = lpspPrecio2;
		this.lpspPrecio3 = lpspPrecio3;
	}

	public String getLpspCodigoBarra() {
		return lpspCodigoBarra;
	}

	public void setLpspCodigoBarra(String lpspCodigoBarra) {
		this.lpspCodigoBarra = lpspCodigoBarra;
	}

	public String getLpspCodigoPrincipal() {
		return lpspCodigoPrincipal;
	}

	public void setLpspCodigoPrincipal(String lpspCodigoPrincipal) {
		this.lpspCodigoPrincipal = lpspCodigoPrincipal;
	}

	public String getLpspNombre() {
		return lpspNombre;
	}

	public void setLpspNombre(String lpspNombre) {
		this.lpspNombre = lpspNombre;
	}

	public BigDecimal getLpspPrecio1() {
		return lpspPrecio1;
	}

	public void setLpspPrecio1(BigDecimal lpspPrecio1) {
		this.lpspPrecio1 = lpspPrecio1;
	}

	public BigDecimal getLpspPrecio2() {
		return lpspPrecio2;
	}

	public void setLpspPrecio2(BigDecimal lpspPrecio2) {
		this.lpspPrecio2 = lpspPrecio2;
	}

	public BigDecimal getLpspPrecio3() {
		return lpspPrecio3;
	}

	public void setLpspPrecio3(BigDecimal lpspPrecio3) {
		this.lpspPrecio3 = lpspPrecio3;
	}

	public String getProEmpaque() {
		return proEmpaque;
	}

	public void setProEmpaque(String proEmpaque) {
		this.proEmpaque = proEmpaque;
	}
}
