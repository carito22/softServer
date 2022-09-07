package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxSustento;

@Repository
public class SustentoDaoImpl extends GenericDaoImpl<AnxSustento, String> implements SustentoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxSustentoTO> getAnexoSustentoTO() throws Exception {
        List<AnxSustentoTO> listaAnxSustentoTO = new ArrayList<AnxSustentoTO>(1);
        List<AnxSustento> listaAnxSustento = obtenerTodos(AnxSustento.class);
        if (!listaAnxSustento.isEmpty()) {
            for (int i = 0; i < listaAnxSustento.size(); i++) {
                listaAnxSustentoTO.add(ConversionesAnexos.convertirAnxSustento_AnxSustentoTO(listaAnxSustento.get(i)));
            }
        }
        return listaAnxSustentoTO;
    }

    @Override
    public List<AnxSustentoComboTO> getListaAnxSustentoComboTO(String tipoComprobante) throws Exception {
        String sql = "SELECT sus_codigo, sus_descripcion,sus_tipo_credito_tributario FROM anexo.anx_sustento " + (tipoComprobante == null ? ""
                : "WHERE POSITION(('" + tipoComprobante + "') IN sus_comprobante)!=0 ORDER BY sus_codigo");

        return genericSQLDao.obtenerPorSql(sql, AnxSustentoComboTO.class);
    }

    @Override
    public List<AnxSustentoComboTO> getListaAnxSustentoComboTOByTipoCredito(String tipoComprobante, String tipoCredito) throws Exception {
        String sqlCredito = "";
        String sqlTipoComp = "";
        String sqlGeneral = "";

        if (tipoCredito != null && !tipoCredito.equals("")) {
            sqlCredito = " sus_tipo_credito_tributario='" + tipoCredito + "' ";
        }

        if (tipoComprobante != null && !tipoComprobante.equals("")) {
            sqlTipoComp = " POSITION(('" + tipoComprobante + "') IN sus_comprobante)!=0 ";
        }

        if (!sqlCredito.equals("") || !sqlTipoComp.equals("")) {
            sqlGeneral = " WHERE " + sqlCredito + (!sqlCredito.equals("") ? " and " : "") + sqlTipoComp;
        }

        String sql = "SELECT sus_codigo, sus_descripcion,sus_tipo_credito_tributario FROM anexo.anx_sustento " + sqlGeneral + " ORDER BY sus_codigo";
        return genericSQLDao.obtenerPorSql(sql, AnxSustentoComboTO.class);
    }

    @Override
    public AnxSustentoComboTO obtenerAnxSustentoComboTO(String codigo) throws Exception {
        String sql = "SELECT sus_codigo, sus_descripcion,sus_tipo_credito_tributario FROM anexo.anx_sustento WHERE sus_codigo = '" + codigo + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxSustentoComboTO.class);
    }

}
