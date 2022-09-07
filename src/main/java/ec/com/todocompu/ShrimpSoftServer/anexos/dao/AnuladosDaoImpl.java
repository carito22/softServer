package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoNumeracionAnulacionDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxAnulados;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoNumeracionAnulacion;

@Repository
public class AnuladosDaoImpl extends GenericDaoImpl<AnxAnulados, Integer> implements AnuladosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private SucesoNumeracionAnulacionDao sucesoNumeracionAnulacionDao;

    @Override
    public AnxAnuladosTO getAnxAnuladosTO(Integer secuencia) throws Exception {
        return ConversionesAnexos.convertirAnxAnulados_AnxAnuladosTO(obtenerPorId(AnxAnulados.class, secuencia));
    }

    @Override
    public boolean insertarAnexoAnulados(AnxAnulados anxAnulados, SisSuceso sisSuceso) throws Exception {
        sisSucesoDao.insertar(sisSuceso);
        insertar(anxAnulados);
        ////insertar suceso///////////
        SisSucesoNumeracionAnulacion sucesoNum = new SisSucesoNumeracionAnulacion();
        AnxAnulados copia = ConversionesAnexos.convertirAnxAnulados_AnxAnulados(anxAnulados);
        if (copia.getTcCodigo() != null) {
            copia.getTcCodigo().setAnxAnuladosList(null);
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoNum.setSusJson(json);
        sucesoNum.setSisSuceso(sisSuceso);
        sucesoNum.setAnxAnulados(copia);
        sucesoNumeracionAnulacionDao.insertar(sucesoNum);
        return true;
    }

    @Override
    public boolean modificarAnexoAnulados(AnxAnulados anxAnulados, SisSuceso sisSuceso) throws Exception {
        sisSucesoDao.insertar(sisSuceso);
        actualizar(anxAnulados);
        ////insertar suceso///////////
        SisSucesoNumeracionAnulacion sucesoNum = new SisSucesoNumeracionAnulacion();
        AnxAnulados copia = ConversionesAnexos.convertirAnxAnulados_AnxAnulados(anxAnulados);
        if (copia.getTcCodigo() != null) {
            copia.getTcCodigo().setAnxAnuladosList(null);
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoNum.setSusJson(json);
        sucesoNum.setSisSuceso(sisSuceso);
        sucesoNum.setAnxAnulados(copia);
        sucesoNumeracionAnulacionDao.insertar(sucesoNum);
        return true;
    }

    @Override
    public boolean eliminarAnexoAnulados(AnxAnulados anxAnulados, SisSuceso sisSuceso) throws Exception {
        eliminar(anxAnulados);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<AnxAnuladoTablaTO> getListaAnxAnuladoTablaTO(String empresa) throws Exception {
        String fechaDesde = "";
        String fechaHasta = "";
        fechaDesde = fechaDesde.equals("") ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta.equals("") ? null : "'" + fechaHasta + "'";
        String sql = "SELECT " + "anu_secuencial, " + "anu_comprobante_tipo, " + "anu_comprobante_establecimiento, "
                + "anu_comprobante_punto_emision, " + "anu_secuencial_inicio, " + "anu_secuencial_fin, "
                + "anu_comprobante_establecimiento || '-' || anu_comprobante_punto_emision || '-' || anu_secuencial_inicio anu_secuencial_inicial, "
                + "anu_comprobante_establecimiento || '-' || anu_comprobante_punto_emision || '-' || anu_secuencial_fin anu_secuencial_final, "
                + "anu_autorizacion, " + "anu_fecha, " + "anu_comprobante_nombre, id " + "FROM anexo.fun_anulados('"
                + empresa + "', " + fechaDesde + ", " + fechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, AnxAnuladoTablaTO.class);
    }

    @Override
    public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        String sql = "SELECT anu_comprobante_tipo, anu_comprobante_establecimiento, "
                + "anu_comprobante_punto_emision, anu_secuencial_inicio, anu_secuencial_fin, "
                + "anu_autorizacion, id FROM anexo.fun_anulados('" + empresa + "','" + fechaDesde + "','" + fechaHasta
                + "');";
        return genericSQLDao.obtenerPorSql(sql, AnxListaComprobanteAnuladoTO.class);
    }

    @Override
    public List<AnxAnuladosTO> obtenerAnexoAnulado(AnxAnuladosTO anxAnuladosTO, String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.anx_anulados WHERE( usr_empresa= '" + empresa
                + "' AND tc_codigo= '" + anxAnuladosTO.getTcCodigo()
                + "' AND anu_comprobante_establecimiento= '" + anxAnuladosTO.getAnuComprobanteEstablecimiento()
                + "' AND anu_comprobante_punto_emision= '" + anxAnuladosTO.getAnuComprobantePuntoEmision()
                + "' AND anu_secuencial_inicio= '" + anxAnuladosTO.getAnuSecuencialInicio()
                + "' AND anu_secuencial_fin ='" + anxAnuladosTO.getAnuSecuencialFin() + "');";
        return genericSQLDao.obtenerPorSql(sql, AnxAnuladosTO.class);
    }
}
