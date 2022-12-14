package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.rentencion.ImpuestoRetencion;
/**
*
* @author Ing. Carlos Ajila
*/
public class ComprobanteRetencionReporte {

	private ComprobanteRetencion comprobanteRetencion;
	private List<DetallesAdicionalesReporte> detallesAdiciones;
	private List<InformacionAdicional> infoAdicional;
	private static final String IVA = "IVA";
	private static final String RENTA = "RENTA";
	private static final String ICE = "ICE";
	private static final String ISD = "IMPUESTO A LA SALIDA DE DIVISAS";
	private static final String FACTURA = "FACTURA";
	private static final String NOTA_CREDITO = "NOTA DE CRÉDITO";
	private static final String NOTA_DEBITO = "NOTA DE DÉBITO";
	private static final String GUIA_REMISION = "GUÍA DE REMISIÓN";
	private static final String COMP_RETENCION = "COMPROBANTE RETENCIÓN";

	public ComprobanteRetencionReporte(ComprobanteRetencion comprobanteRetencion) {
		this.comprobanteRetencion = comprobanteRetencion;
	}

	public ComprobanteRetencion getComprobanteRetencion() {
		return this.comprobanteRetencion;
	}

	public void setComprobanteRetencion(ComprobanteRetencion comprobanteRetencion) {
		this.comprobanteRetencion = comprobanteRetencion;
	}

	public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
		this.detallesAdiciones = new ArrayList();
		for (ImpuestoRetencion im : this.comprobanteRetencion.getImpuestos().getImpuesto()) {
			DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
			detAd.setBaseImponible(im.getBaseImponible().toString());
			detAd.setPorcentajeRetener(im.getPorcentajeRetener().toString());
			detAd.setValorRetenido(im.getValorRetenido().toString());
			detAd.setNombreImpuesto(obtenerImpuestoDecripcion(im.getCodigo()));
			detAd.setInfoAdicional(getInfoAdicional());
			detAd.setNumeroComprobante(im.getNumDocSustento());
			detAd.setNombreComprobante(obtenerDecripcionComprobante(im.getCodDocSustento()));
			detAd.setFechaEmisionCcompModificado(im.getFechaEmisionDocSustento());
			detAd.setCodigoRetencion(im.getCodigoRetencion());
			this.detallesAdiciones.add(detAd);
		}
		return this.detallesAdiciones;
	}

	private String obtenerDecripcionComprobante(String codDocSustento) {
		if ("01".equals(codDocSustento)) {
			return "FACTURA";
		}
		if ("02".equals(codDocSustento)) {
			return "NOTA DE VENTA";
		}
		if ("03".equals(codDocSustento)) {
			return "LIQUIDACION DE COMPRA";
		}
		if ("04".equals(codDocSustento)) {
			return "NOTA DE CRÉDITO";
		}
		if ("05".equals(codDocSustento)) {
			return "NOTA DE DÉBITO";
		}
		if ("06".equals(codDocSustento)) {
			return "GUÍA DE REMISIÓN";
		}
		if ("07".equals(codDocSustento)) {
			return "COMPROBANTE RETENCIÓN";
		}
		return null;
	}

	private String obtenerImpuestoDecripcion(String codigoImpuesto) {
		if (codigoImpuesto.equals("1")) {
			return "RENTA";
		}
		if (codigoImpuesto.equals("2")) {
			return "IVA";
		}
		if (codigoImpuesto.equals("3")) {
			return "ICE";
		}
		if (codigoImpuesto.equals("6")) {
			return "IMPUESTO A LA SALIDA DE DIVISAS";
		}
		return null;
	}

	public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
		this.detallesAdiciones = detallesAdiciones;
	}

	public List<InformacionAdicional> getInfoAdicional() {
		if (this.comprobanteRetencion.getInfoAdicional() != null) {
			this.infoAdicional = new ArrayList();
			if ((this.comprobanteRetencion.getInfoAdicional().getCampoAdicional() != null)
					&& (!this.comprobanteRetencion.getInfoAdicional().getCampoAdicional().isEmpty())) {
				for (ComprobanteRetencion.InfoAdicional.CampoAdicional ca : this.comprobanteRetencion.getInfoAdicional()
						.getCampoAdicional()) {
					this.infoAdicional.add(new InformacionAdicional(ca.getValue(), ca.getNombre()));
				}
			}
		}
		return this.infoAdicional;
	}

	public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
}
