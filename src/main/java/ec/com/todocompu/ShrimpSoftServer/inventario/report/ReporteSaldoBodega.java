/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteSaldoBodega {
	private String bodega;
	private String hasta;

	////// DETALLE
	private String sbBodega;
	private String sbProducto;
	private String sbNombre;
	private String sbMedida;
	private String sbSerie;
	private BigDecimal sbStock;
	private BigDecimal sbCosto;
	private BigDecimal sbTotal;
        private BigDecimal sbCajetas;

	public ReporteSaldoBodega() {
	}

	public ReporteSaldoBodega(String bodega, String hasta, String sbBodega, String sbProducto, String sbNombre,
			String sbMedida, BigDecimal sbStock, BigDecimal sbCosto, BigDecimal sbTotal, BigDecimal sbCajetas) {
		this.bodega = bodega;
		this.hasta = hasta;
		this.sbBodega = sbBodega;
		this.sbProducto = sbProducto;
		this.sbNombre = sbNombre;
		this.sbMedida = sbMedida;
		this.sbStock = sbStock;
		this.sbCosto = sbCosto;
		this.sbTotal = sbTotal;
                this.sbCajetas = sbCajetas;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public String getSbBodega() {
		return sbBodega;
	}

	public void setSbBodega(String sbBodega) {
		this.sbBodega = sbBodega;
	}

	public BigDecimal getSbCosto() {
		return sbCosto;
	}

	public void setSbCosto(BigDecimal sbCosto) {
		this.sbCosto = sbCosto;
	}

	public String getSbMedida() {
		return sbMedida;
	}

	public void setSbMedida(String sbMedida) {
		this.sbMedida = sbMedida;
	}

	public String getSbNombre() {
		return sbNombre;
	}

	public void setSbNombre(String sbNombre) {
		this.sbNombre = sbNombre;
	}

	public String getSbProducto() {
		return sbProducto;
	}

	public void setSbProducto(String sbProducto) {
		this.sbProducto = sbProducto;
	}

	public BigDecimal getSbStock() {
		return sbStock;
	}

	public void setSbStock(BigDecimal sbStock) {
		this.sbStock = sbStock;
	}

	public BigDecimal getSbTotal() {
		return sbTotal;
	}

	public void setSbTotal(BigDecimal sbTotal) {
		this.sbTotal = sbTotal;
	}

        public String getSbSerie() {
            return sbSerie;
        }

        public void setSbSerie(String sbSerie) {
            this.sbSerie = sbSerie;
        }

        public BigDecimal getSbCajetas() {
            return sbCajetas;
        }

        public void setSbCajetas(BigDecimal sbCajetas) {
            this.sbCajetas = sbCajetas;
        }
            
}
