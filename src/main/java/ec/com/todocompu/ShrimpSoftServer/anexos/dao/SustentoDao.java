package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxSustento;

public interface SustentoDao extends GenericDao<AnxSustento, String> {

    public List<AnxSustentoTO> getAnexoSustentoTO() throws Exception;

    public List<AnxSustentoComboTO> getListaAnxSustentoComboTO(String tipoComprobante) throws Exception;

    public List<AnxSustentoComboTO> getListaAnxSustentoComboTOByTipoCredito(String tipoComprobante, String tipoCredito) throws Exception;

    public AnxSustentoComboTO obtenerAnxSustentoComboTO(String codigo) throws Exception;

}
