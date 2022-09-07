package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;

public class Suceso {
	public static SisSuceso crearSisSuceso(String susTabla, String susClave, String susSuceso, String susDetalle,
			SisInfoTO sisInfoTO) {
		SisSuceso sisSuceso = new SisSuceso();
		sisSuceso.setSusSecuencia(0);
		sisSuceso.setSusTabla(susTabla);
		sisSuceso.setSusClave(susClave);
		sisSuceso.setSusSuceso(susSuceso);
		sisSuceso.setSusDetalle(susDetalle);
		sisSuceso.setSusMac(sisInfoTO.getMac());
		sisSuceso.setDetEmpresa(sisInfoTO.getEmpresa());
		sisSuceso.setSisUsuario(new SisUsuario(sisInfoTO.getUsuario()));
		sisSuceso.setSusFecha(UtilsDate.timestamp());
		return sisSuceso;
	}

}
