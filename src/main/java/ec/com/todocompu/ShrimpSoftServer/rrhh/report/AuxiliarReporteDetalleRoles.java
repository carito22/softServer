package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReportesRol;

public class AuxiliarReporteDetalleRoles implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<ReportesRol> lista = new ArrayList<ReportesRol>();

	public AuxiliarReporteDetalleRoles() {
	}

	public AuxiliarReporteDetalleRoles(List<ReportesRol> lista) {
		this.lista = lista;
	}

	public List<ReportesRol> getLista() {
		return lista;
	}

	public void setLista(List<ReportesRol> lista) {
		this.lista = lista;
	}

}
