package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

public class DocumentoReporteRIDE {
	private String numeroAutorizacion;
	private String fechaAutorizacion;
	private String estado;
	private String documento;
	
	public DocumentoReporteRIDE() {
		
	}

	public DocumentoReporteRIDE(String numeroAutorizacion, String fechaAutorizacion, String estado, String documento) {
		this.numeroAutorizacion = numeroAutorizacion;
		this.fechaAutorizacion = fechaAutorizacion;
		this.estado = estado;
		this.documento = documento;
	}

	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}

	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}

	public String getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(String fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	


}
