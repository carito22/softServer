package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.Factura;
/**
*
* @author Ing. Carlos Ajila
*/
public class FacturaReporte {

	public Factura factura;
	private String detalle1;
	private String detalle2;
	private String detalle3;
	private List<DetallesAdicionalesReporte> detallesAdiciones;
	private List<InformacionAdicional> infoAdicional;
	private List<InformacionPago> formasPago;

	public FacturaReporte(Factura factura) {
		this.factura = factura;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String getDetalle1() {
		return this.detalle1;
	}

	public void setDetalle1(String detalle1) {
		this.detalle1 = detalle1;
	}

	public String getDetalle2() {
		return this.detalle2;
	}

	public void setDetalle2(String detalle2) {
		this.detalle2 = detalle2;
	}

	public String getDetalle3() {
		return this.detalle3;
	}

	public void setDetalle3(String detalle3) {
		this.detalle3 = detalle3;
	}

	public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
		this.detallesAdiciones = new ArrayList();

		for (Factura.Detalles.Detalle det : getFactura().getDetalles().getDetalle()) {
			DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
			detAd.setCodigoPrincipal(det.getCodigoPrincipal());
			detAd.setCodigoAuxiliar(det.getCodigoAuxiliar());
			detAd.setDescripcion(det.getDescripcion());
			detAd.setCantidad(det.getCantidad().toPlainString());
			detAd.setPrecioTotalSinImpuesto(det.getPrecioTotalSinImpuesto().toString());
			detAd.setPrecioUnitario(det.getPrecioUnitario().toString());
			if (det.getDescuento() != null) {
				detAd.setDescuento(det.getDescuento().toString());
			}
			int i = 0;
			if ((det.getDetallesAdicionales() != null) && (det.getDetallesAdicionales().getDetAdicional() != null)
					&& (!det.getDetallesAdicionales().getDetAdicional().isEmpty())) {
				for (Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional : det
						.getDetallesAdicionales().getDetAdicional()) {
					if (i == 0) {
						detAd.setDetalle1(detAdicional.getValor());
					}
					if (i == 1) {
						detAd.setDetalle2(detAdicional.getValor());
					}
					if (i == 2) {
						detAd.setDetalle3(detAdicional.getValor());
					}
					i++;
				}
			}
			detAd.setInfoAdicional(getInfoAdicional());
			detAd.setFormasPago(getFormasPago());
			this.detallesAdiciones.add(detAd);
		}

		return this.detallesAdiciones;
	}

	public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
		this.detallesAdiciones = detallesAdiciones;
	}

	public List<InformacionAdicional> getInfoAdicional() {
		if (getFactura().getInfoAdicional() != null) {
			this.infoAdicional = new ArrayList();
			if ((getFactura().getInfoAdicional().getCampoAdicional() != null)
					&& (!this.factura.getInfoAdicional().getCampoAdicional().isEmpty())) {
				for (Factura.InfoAdicional.CampoAdicional ca : getFactura().getInfoAdicional().getCampoAdicional()) {
					this.infoAdicional.add(new InformacionAdicional(ca.getValue(), ca.getNombre()));
				}
			}
		}
		return this.infoAdicional;
	}

	public List<InformacionPago> getFormasPago() {
		if (getFactura().getInfoFactura().getPagos() != null) {
			this.formasPago = new ArrayList();
			if ((getFactura().getInfoFactura().getPagos().getPago() != null)
					&& (!this.factura.getInfoFactura().getPagos().getPago().isEmpty())) {
				for (Factura.InfoFactura.Pagos.Pago pago : getFactura().getInfoFactura().getPagos().getPago()) {

					String formaPago = "";
					if (pago.getFormaPago().compareTo("01") == 0)
						formaPago = "01 - SIN UTILIZACION DEL SISTEMA FINANCIERO";
					else if (pago.getFormaPago().compareTo("17") == 0)
						formaPago = "17 - DINERO ELECTRÓNICO";
					else if (pago.getFormaPago().compareTo("19") == 0)
						formaPago = "19 - TARJETA DE CRÉDITO";
					else if (pago.getFormaPago().compareTo("20") == 0)
						formaPago = "20 - OTROS CON UTILIZACION DEL SISTEMA FINANCIERO";

					this.formasPago.add(new InformacionPago(pago.getTotal() + "", formaPago));
				}
			}
		}
		return this.formasPago;
	}

	public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
}
