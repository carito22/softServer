package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadecredito.NotaCredito;
/**
*
* @author Ing. Carlos Ajila
*/
public class NotaCreditoReporte {
	private NotaCredito notaCredito;
	private List<DetallesAdicionalesReporte> detallesAdiciones;
	private List<InformacionAdicional> infoAdicional;

	public NotaCreditoReporte(NotaCredito notaCredito) {
		this.notaCredito = notaCredito;
	}

	public NotaCredito getNotaCredito() {
		return this.notaCredito;
	}

	public void setNotaCredito(NotaCredito notaCredito) {
		this.notaCredito = notaCredito;
	}

	public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
		this.detallesAdiciones = new ArrayList();

		for (NotaCredito.Detalles.Detalle det : getNotaCredito().getDetalles().getDetalle()) {
			DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
			detAd.setCodigoPrincipal(det.getCodigoInterno());
			detAd.setCodigoAuxiliar(det.getCodigoAdicional());
			detAd.setDescripcion(det.getDescripcion());
			detAd.setCantidad(det.getCantidad().toPlainString());
			detAd.setPrecioTotalSinImpuesto(det.getPrecioTotalSinImpuesto().toString());
			detAd.setPrecioUnitario(det.getPrecioUnitario().toString());
			detAd.setDescuento(det.getDescuento().toString());
			int i = 0;
			if ((det.getDetallesAdicionales() != null) && (det.getDetallesAdicionales().getDetAdicional() != null)) {
				for (NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional : det
						.getDetallesAdicionales().getDetAdicional()) {
					if (i == 0) {
						detAd.setDetalle1(detAdicional.getNombre());
					}
					if (i == 1) {
						detAd.setDetalle2(detAdicional.getNombre());
					}
					if (i == 2) {
						detAd.setDetalle3(detAdicional.getNombre());
					}
					i++;
				}

			}

			detAd.setInfoAdicional(getInfoAdicional());
			this.detallesAdiciones.add(detAd);
		}
		return this.detallesAdiciones;
	}

	public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
		this.detallesAdiciones = detallesAdiciones;
	}

	public List<InformacionAdicional> getInfoAdicional() {
		if (this.notaCredito.getInfoAdicional() != null) {
			this.infoAdicional = new ArrayList();
			if ((this.notaCredito.getInfoAdicional().getCampoAdicional() != null)
					&& (!this.notaCredito.getInfoAdicional().getCampoAdicional().isEmpty())) {
				for (NotaCredito.InfoAdicional.CampoAdicional ca : this.notaCredito.getInfoAdicional()
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
