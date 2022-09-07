package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipotransaccion;

@Repository
public class TipoTransaccionDaoImpl extends GenericDaoImpl<AnxTipotransaccion, String> implements TipoTransaccionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxTipoListaTransaccionTO> getAnexoTipoListaTransaccionTO() throws Exception {
        List<AnxTipoListaTransaccionTO> listaAnxTipoListaTransaccionTO = new ArrayList<AnxTipoListaTransaccionTO>(1);
        List<AnxTipotransaccion> listaAnxTipoTransaccion = obtenerTodos(AnxTipotransaccion.class);
        if (!listaAnxTipoTransaccion.isEmpty()) {
            for (int i = 0; i < listaAnxTipoTransaccion.size(); i++) {
                listaAnxTipoListaTransaccionTO.add(ConversionesAnexos
                        .convertirAnxTipoListaTransaccion_AnxTipoListaTransaccionTO(listaAnxTipoTransaccion.get(i)));
            }
        }
        return listaAnxTipoListaTransaccionTO;
    }

    @Override
    public String getCodigoAnxTipoTransaccionTO(String codigoTipoIdentificacion, String tipoTransaccion)
            throws Exception {
        String sql = "";
        if (tipoTransaccion.equals("COMPRA")) {
            sql = "SELECT tt_codigo " + "FROM anexo.anx_tipotransaccion WHERE tt_codigo <= '03' AND tt_id = ('"
                    + codigoTipoIdentificacion + "')";
        } else {
            sql = "SELECT tt_codigo " + "FROM anexo.anx_tipotransaccion WHERE tt_codigo > '03' AND tt_id = ('"
                    + codigoTipoIdentificacion + "')";
        }
        Object tipoTrans = genericSQLDao.obtenerObjetoPorSql(sql);
        return tipoTrans != null ? tipoTrans.toString() : "";
    }
}
