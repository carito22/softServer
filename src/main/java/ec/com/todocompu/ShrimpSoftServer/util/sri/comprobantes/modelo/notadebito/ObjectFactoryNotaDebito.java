package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadebito;

import javax.xml.bind.annotation.XmlRegistry;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.InfoTributaria;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.notadebito.NotaDebito.InfoAdicional;
/**
*
* @author Ing. Carlos Ajila
*/

@XmlRegistry
public class ObjectFactoryNotaDebito {
	public InfoAdicional createNotaDebitoInfoAdicional() {
		return new InfoAdicional();
	}

	public ImpuestoNotaDebito createImpuesto() {
		return new ImpuestoNotaDebito();
	}

	public NotaDebito.Motivos createNotaDebitoMotivos() {
		return new NotaDebito.Motivos();
	}

	public NotaDebito.InfoNotaDebito.Impuestos createNotaDebitoInfoNotaDebitoImpuestos() {
		return new NotaDebito.InfoNotaDebito.Impuestos();
	}

	public NotaDebito.InfoNotaDebito createNotaDebitoInfoNotaDebito() {
		return new NotaDebito.InfoNotaDebito();
	}

	public NotaDebito.InfoAdicional.CampoAdicional createNotaDebitoInfoAdicionalCampoAdicional() {
		return new NotaDebito.InfoAdicional.CampoAdicional();
	}

	public Detalle createDetalle() {
		return new Detalle();
	}

	public InfoTributaria createInfoTributaria() {
		return new InfoTributaria();
	}

	public NotaDebito createNotaDebito() {
		return new NotaDebito();
	}

	public NotaDebito.Motivos.Motivo createNotaDebitoMotivosMotivo() {
		return new NotaDebito.Motivos.Motivo();
	}
}
