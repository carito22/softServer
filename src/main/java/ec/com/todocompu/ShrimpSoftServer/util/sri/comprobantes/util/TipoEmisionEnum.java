package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

public enum TipoEmisionEnum {

	NORMAL("NORMAL"), CONTINGENCIA("INDISPONIBILIDAD DE SISTEMA");
	private String code;

	private TipoEmisionEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
}
