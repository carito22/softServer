/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class AnxRetencionesVentaDaoImpl extends GenericDaoImpl<AnxRetencionesVenta, Integer> implements AnxRetencionesVentaDao {

    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean insertarListadoAnxRetencionesVenta(List<AnxRetencionesVenta> listado, List<SisSuceso> listadoSisSuceso) throws Exception {
        insertar(listado);
        sisSucesoDao.insertar(listadoSisSuceso);
        return true;
    }

    @Override
    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportar(String empresa, String cliIdentificacion, String busqueda) throws Exception {
        String sqlCliente = cliIdentificacion != null ? " AND cli_identificacion='" + cliIdentificacion + "'" : "";

        String complementoSql = busqueda != null && !busqueda.equals("")
                ? (" AND  (cli_razon_social || cli_identificacion || ret_numero_documento || ret_num_doc_sustento || ret_clave_acceso "
                + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = '' THEN '~' ELSE ('" + busqueda + "') END || ' ', ' ', '%'))") : "";

        String sql = "SELECT * FROM anexo.anx_retenciones_venta WHERE ret_empresa = '" + empresa
                + "'" + sqlCliente + complementoSql + ";";
        List<AnxRetencionesVenta> listado = genericSQLDao.obtenerPorSql(sql, AnxRetencionesVenta.class);
        return listado;
    }

    @Override
    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(String empresa) throws Exception {
        String sql = "SELECT * from anexo.anx_retenciones_venta ret_pend "
                + "LEFT JOIN anexo.anx_venta ret "
                + "ON ret_pend.ret_empresa=ret.ven_empresa "
                + "AND ret_pend.ret_clave_acceso= ret.ven_retencionautorizacion "
                + "WHERE ret.ven_retencionautorizacion is null "
                + "AND ret.ven_empresa is null  AND ret_pend.ret_num_doc_sustento is not null "
                + "AND ret_pend.ret_empresa = '" + empresa + "';";
        List<AnxRetencionesVenta> listado = genericSQLDao.obtenerPorSql(sql, AnxRetencionesVenta.class);
        return listado;
    }

    @Override
    public AnxRetencionesVenta obtenerAnxRetencionesVenta(String empresa, String numeroDocumento, String identificacionCliente) {
        String sql = "SELECT * FROM anexo.anx_retenciones_venta WHERE ret_empresa='" + empresa
                + "' AND cli_identificacion='" + identificacionCliente
                + "' AND ret_numero_documento='" + numeroDocumento + "'";

        AnxRetencionesVenta homol = genericSQLDao.obtenerObjetoPorSql(sql, AnxRetencionesVenta.class);
        return homol;
    }

    @Override
    public boolean insertarAnxRetencionesVenta(AnxRetencionesVenta anxRetencionesVenta, SisSuceso sisSuceso) throws Exception {
        insertar(anxRetencionesVenta);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean anularRetencionesComprobantes(String empresa, String claveAcceso, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE anexo.anx_retenciones_venta SET ret_anulado = true WHERE ret_empresa = '" + empresa + "' and ret_clave_acceso = '" + claveAcceso + "'";
        genericSQLDao.ejecutarSQL(sql);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
