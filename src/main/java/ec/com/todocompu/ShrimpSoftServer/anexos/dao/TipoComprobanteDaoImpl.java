package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipocomprobante;

@Repository
public class TipoComprobanteDaoImpl extends GenericDaoImpl<AnxTipocomprobante, String> implements TipoComprobanteDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxTipoComprobanteTO> getAnexoTipoComprobanteTO() throws Exception {
        List<AnxTipoComprobanteTO> listaAnxTipoComprobanteTO = new ArrayList<AnxTipoComprobanteTO>(1);
        List<AnxTipocomprobante> listaAnxTipoComprobante = obtenerTodosOrder(AnxTipocomprobante.class, "tcCodigo");
        if (!listaAnxTipoComprobante.isEmpty()) {
            for (int i = 0; i < listaAnxTipoComprobante.size(); i++) {
                listaAnxTipoComprobanteTO.add(ConversionesAnexos.convertirAnxTipoComprobante_AnxTipoComprobanteTO(listaAnxTipoComprobante.get(i)));
            }
        }
        return listaAnxTipoComprobanteTO;
    }

    @Override
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTO(String codigoTipoTransaccion)
            throws Exception {
        String sql = "";
        if (codigoTipoTransaccion != null) {
            sql = "SELECT tc_codigo, tc_descripcion, tc_reporte FROM anexo." + "anx_tipocomprobante WHERE POSITION(('"
                    + codigoTipoTransaccion + "~') in tc_transaccion)!=0 ORDER BY tc_codigo";
        } else {
            sql = "SELECT tc_codigo, tc_descripcion, tc_reporte FROM anexo." + "anx_tipocomprobante ORDER BY tc_codigo";

        }
        return genericSQLDao.obtenerPorSql(sql, AnxTipoComprobanteComboTO.class);
    }

    @Override
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTOCompleto() throws Exception {
        String sql = "SELECT tc_codigo, tc_descripcion, tc_reporte FROM anexo."
                + "anx_tipocomprobante ORDER BY tc_codigo";
        return genericSQLDao.obtenerPorSql(sql, AnxTipoComprobanteComboTO.class);
    }

    @Override
    public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(String codigo) throws Exception {
        codigo = codigo == null ? "" : codigo;
        String sql = "SELECT tc_codigo, tc_descripcion, tc_abreviatura FROM anexo.anx_tipocomprobante "
                + "WHERE tc_codigo LIKE '%" + codigo + "%' OR tc_descripcion LIKE '%" + codigo
                + "%' OR tc_abreviatura LIKE '%" + codigo + "%' ORDER BY tc_codigo";

        return genericSQLDao.obtenerPorSql(sql, AnxTipoComprobanteTablaTO.class);
    }

    @Override
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboParaVentaRecurrente() throws Exception {
        String sql = "SELECT tc_codigo, tc_descripcion, tc_reporte FROM anexo." + "anx_tipocomprobante "
                + "where tc_codigo = '00' or tc_codigo = '02' or tc_codigo = '18' ORDER BY tc_codigo";
        return genericSQLDao.obtenerPorSql(sql, AnxTipoComprobanteComboTO.class);
    }

}
