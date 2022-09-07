package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipocomprobante;

public interface TipoComprobanteDao extends GenericDao<AnxTipocomprobante, String> {

    public List<AnxTipoComprobanteTO> getAnexoTipoComprobanteTO() throws Exception;

    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTO(String codigoTipoTransaccion)
            throws Exception;

    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTOCompleto() throws Exception;

    public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(String codigo) throws Exception;

    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboParaVentaRecurrente() throws Exception;

}
