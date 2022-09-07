package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoConciliacionBancariaDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoConciliacionBancaria;
import java.util.stream.Collectors;

@Repository
public class ConciliacionDaoImpl extends GenericDaoImpl<BanConciliacion, BanConciliacionPK> implements ConciliacionDao {

    @Autowired
    private ChequeDao chequeDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoConciliacionBancariaDao sucesoConciliacionBancariaDao;

    @Override
    public Boolean conciliacionHasta(String empresa, String hasta, String cuenta) throws Exception {
        String sql = "SELECT COALESCE ('" + hasta + "' > MAX(conc_hasta), TRUE) " + "FROM banco.ban_conciliacion "
                + "WHERE (conc_empresa = '" + empresa + "') " + "AND (conc_cuenta_contable = '" + cuenta + "');";
        try {
            return (Boolean) genericSQLDao.obtenerObjetoPorSql(sql);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean conciliacionPendiente(String empresa, String cuentaContable) throws Exception {
        String sql = "SELECT COUNT(*) FROM banco.ban_conciliacion "
                + "WHERE (conc_empresa = '" + empresa
                + "') AND (conc_cuenta_contable='" + cuentaContable
                + "') AND (conc_pendiente)";
        int conteo = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        if (conteo == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<BanListaConciliacionTO> getBanListaConciliacionTO(String empresa, String buscar) throws Exception {
        String sql = "SELECT conc_cuenta_contable, cta_detalle, "
                + "conc_codigo, conc_hasta, conc_saldo_estado_cuenta, " + "conc_cheques_girados_y_no_cobrados, "
                + "conc_depositos_en_transito, conc_pendiente, row_number() over (partition by '' order by conc_cuenta_contable, conc_hasta desc) id "
                + "FROM banco.ban_conciliacion " + "INNER JOIN contabilidad.con_cuentas "
                + "ON ban_conciliacion.cta_empresa = con_cuentas.cta_empresa "
                + "AND ban_conciliacion.cta_cuenta_contable = con_cuentas.cta_codigo " + "WHERE (conc_empresa = '"
                + empresa + "') AND " + "(conc_cuenta_contable || conc_codigo || conc_hasta || "
                + "conc_hasta || conc_codigo || conc_cuenta_contable " + "LIKE TRANSLATE(' ' || '" + buscar
                + "' || ' ', ' ', '%')) " + "ORDER BY 1, 4 DESC;";
        return genericSQLDao.obtenerPorSql(sql, BanListaConciliacionTO.class);
    }

    @Override
    public String getBanConciliacionFechaHasta(String empresa, String cuenta) throws Exception {
        try {
            String sql = "SELECT MAX(conc_hasta) " + "FROM banco.ban_conciliacion " + "WHERE (conc_empresa = '"
                    + empresa + "') AND " + "(conc_cuenta_contable = '" + cuenta + "');";
            return genericSQLDao.obtenerObjetoPorSql(sql).toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaDebitoTO(String empresa, String cuenta,
            String codigo, String hasta) throws Exception {
        String sql = "SELECT cb_contable, cb_secuencial, cb_fecha,cb_fecha_vencimiento, cb_documento, "
                + "cb_valor, cb_conciliado, cb_conciliacion, cb_concepto, cb_observaciones, cb_dc, cb_categoria, cb_reversado, id "
                + "FROM banco.fun_conciliacion_bancaria(" + "'" + empresa + "', " + "'" + cuenta + "', " + "'" + codigo
                + "', " + "'" + hasta + "') " + "WHERE (cb_dc = 'C');";
        return genericSQLDao.obtenerPorSql(sql, BanListaConciliacionBancariaTO.class);
    }

    @Override
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaCreditoTO(String empresa, String cuenta,
            String codigo, String hasta) throws Exception {
        String sql = "SELECT cb_contable, cb_secuencial, cb_fecha,cb_fecha_vencimiento, cb_documento, "
                + "cb_valor, cb_conciliado, cb_conciliacion, cb_concepto, cb_observaciones, cb_dc, cb_categoria, cb_reversado, id "
                + "FROM banco.fun_conciliacion_bancaria(" + "'" + empresa + "', " + "'" + cuenta + "', " + "'" + codigo
                + "', " + "'" + hasta + "') " + "WHERE (cb_dc = 'D');";
        return genericSQLDao.obtenerPorSql(sql, BanListaConciliacionBancariaTO.class);
    }

    @Override
    public Boolean accionBanConciliacion(BanConciliacion banConciliacion, List<BanCheque> banCheques, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(banConciliacion);
        }
        if (accion == 'M') {
            actualizar(banConciliacion);
        }
        if (accion == 'E') {
            eliminar(banConciliacion);
        }

        List<BanCheque> listaChequesNoRepetidos = banCheques.stream()
                .map(item -> item)
                .distinct()
                .collect(Collectors.toList());

        for (BanCheque banCheque : listaChequesNoRepetidos) {
            if (accion == 'E') {
                banCheque.setConcCodigo(null);
                banCheque.setConcCuentaContable(null);
                banCheque.setConcEmpresa(null);
            }
            chequeDao.saveOrUpdate(banCheque);
        }
        sucesoDao.insertar(sisSuceso);
        /////////////////////////crear suceso /////////////////////////
        if (accion == 'I' || accion == 'M') {
            SisSucesoConciliacionBancaria sucesoConc = new SisSucesoConciliacionBancaria();
            BanConciliacion copia = ConversionesBanco.convertirBanConciliacion_BanConciliacion(banConciliacion);
            if (copia.getBanCuenta() != null) {
                copia.getBanCuenta().setBanConciliacionList(null);
            }
            if (copia.getBanCuenta().getBanBanco() != null) {
                copia.getBanCuenta().getBanBanco().setBanCuentaList(null);
            }

            String json = UtilsJSON.objetoToJson(copia);
            if (banCheques != null && banCheques.size() > 0) {
                String jsonListado = UtilsJSON.objetoToJson(banCheques);
                if (json != null && !json.equals("") && jsonListado != null && !jsonListado.equals("")) {
                    json = json.substring(0, json.length() - 1) + "," + "\"banCheques\"" + ":" + jsonListado + "}";
                }
            }
            sucesoConc.setSusJson(json);
            sucesoConc.setSisSuceso(sisSuceso);
            sucesoConc.setBanConciliacion(copia);
            sucesoConciliacionBancariaDao.insertar(sucesoConc);
        }
        return true;
    }

    @Override
    public List<BanConciliacionDatosAdjuntos> listarImagenesDeBanConciliacion(BanConciliacionPK pk) throws Exception {
        String sql = "select * from banco.ban_conciliacion_datos_adjuntos where "
                + "conc_empresa = '" + pk.getConcEmpresa()
                + "' and conc_cuenta_contable = '" + pk.getConcCuentaContable()
                + "' and conc_codigo = '" + pk.getConcCodigo() + "' ";
        return genericSQLDao.obtenerPorSql(sql, BanConciliacionDatosAdjuntos.class);
    }

}
