package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoNumeracionRegistroDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoNumeracionRegistro;

@Repository
public class AnexoNumeracionDaoImpl extends GenericDaoImpl<AnxNumeracion, Integer> implements AnexoNumeracionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private SucesoNumeracionRegistroDao sucesoNumeracionRegistroDao;

    @Override
    public AnxNumeracionTO getAnexoNumeracionTO(Integer secuencia) throws Exception {
        return ConversionesAnexos.convertirAnxNumeracion_AnxAnxNumeracionTO(obtenerPorId(AnxNumeracion.class, secuencia));
    }

    @Override
    public boolean insertarAnexoNumeracion(AnxNumeracion anxNumeracion, SisSuceso sisSuceso) throws Exception {
        sisSucesoDao.insertar(sisSuceso);
        insertar(anxNumeracion);
        //////////insertar suceso//////////////
        SisSucesoNumeracionRegistro sucesoNum = new SisSucesoNumeracionRegistro();
        String json = UtilsJSON.objetoToJson(anxNumeracion);
        sucesoNum.setSusJson(json);
        sucesoNum.setSisSuceso(sisSuceso);
        sucesoNum.setAnxNumeracion(anxNumeracion);
        sucesoNumeracionRegistroDao.insertar(sucesoNum);
        return true;
    }

    @Override
    public boolean modificarAnexoNumeracion(AnxNumeracion anxNumeracion, SisSuceso sisSuceso) throws Exception {
        sisSucesoDao.insertar(sisSuceso);
        actualizar(anxNumeracion);
        //////////insertar suceso////////////// 
        SisSucesoNumeracionRegistro sucesoNum = new SisSucesoNumeracionRegistro();
        String json = UtilsJSON.objetoToJson(anxNumeracion);
        sucesoNum.setSusJson(json);
        sucesoNum.setSisSuceso(sisSuceso);
        sucesoNum.setAnxNumeracion(anxNumeracion);
        sucesoNumeracionRegistroDao.insertar(sucesoNum);
        return true;
    }

    @Override
    public boolean eliminarAnexoNumeracion(AnxNumeracion anxNumeracion, SisSuceso sisSuceso) throws Exception {
        eliminar(anxNumeracion);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa) throws Exception {
        String sql = "SELECT num_secuencial, num_comprobante, num_desde, num_hasta, notificaciones.id_principal, num_autorizacion, num_observacion, num_caduca FROM anexo.anx_numeracion "
                + " LEFT JOIN sistemaweb.sis_empresa_notificaciones notificaciones"
                + " ON notificaciones.id = anexo.anx_numeracion.id_notificaciones"
                + " WHERE num_empresa = ('" + empresa + "') ORDER BY 2, 3";

        return genericSQLDao.obtenerPorSql(sql, AnxNumeracionTablaTO.class);

    }

    @Override
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento) throws Exception {
        String sql = "SELECT num_secuencial, num_comprobante, num_desde, num_hasta, notificaciones.id_principal, num_autorizacion, num_observacion, num_caduca FROM anexo.anx_numeracion "
                + " LEFT JOIN sistemaweb.sis_empresa_notificaciones notificaciones"
                + " ON notificaciones.id = anexo.anx_numeracion.id_notificaciones"
                + " WHERE (num_empresa, num_comprobante) = ('" + empresa + "', '" + tipoDocumento + "') ORDER BY 2, 3";
        return genericSQLDao.obtenerPorSql(sql, AnxNumeracionTablaTO.class);
    }

    @Override
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento, String fechaCaduda) throws Exception {
        String sql = "SELECT num_secuencial, num_comprobante, num_desde, num_hasta, notificaciones.id_principal, num_autorizacion, num_observacion, num_caduca FROM anexo.anx_numeracion "
                + " LEFT JOIN sistemaweb.sis_empresa_notificaciones notificaciones"
                + " ON notificaciones.id = anexo.anx_numeracion.id_notificaciones "
                + "WHERE num_empresa = '" + empresa + "'"
                + " and num_comprobante='" + tipoDocumento + "'"
                + " and num_caduca>='" + fechaCaduda + "' ORDER BY 2, 3";
        return genericSQLDao.obtenerPorSql(sql, AnxNumeracionTablaTO.class);
    }

}
