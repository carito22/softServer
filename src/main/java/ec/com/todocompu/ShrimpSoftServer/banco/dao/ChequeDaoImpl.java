package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequePreavisadoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanDetalleChequesPosfechadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ChequeDaoImpl extends GenericDaoImpl<BanCheque, Long> implements ChequeDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private DetalleDao conDetalleDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean isExisteCheque(String empresa, String cuentaContable, String numeroCheque) throws Exception {
        numeroCheque = numeroCheque.compareToIgnoreCase("") == 0 ? null : numeroCheque;
        String sql = "SELECT COUNT(det_secuencia) "
                + "FROM contabilidad.con_contable INNER JOIN contabilidad.con_detalle "
                + "ON con_contable.con_empresa = con_detalle.con_empresa AND "
                + "con_contable.con_periodo = con_detalle.con_periodo AND "
                + "con_contable.con_tipo    = con_detalle.con_tipo    AND "
                + "con_contable.con_numero  = con_detalle.con_numero " + "WHERE (cta_empresa='" + empresa + "' AND "
                + "cta_codigo='" + cuentaContable + "') AND " + "det_documento='" + numeroCheque + "' AND "
                + "det_debito_credito='C' AND " + "NOT con_contable.con_anulado";
        int i = Integer
                .parseInt(UtilsConversiones.convertirListToArray(genericSQLDao.obtenerPorSql(sql), 0)[0].toString());
        return (i != 0);
    }

    @Override
    public boolean reutilizarCheque(BanCheque banChequeAux, ConDetalle conDetalle, SisSuceso sisSucesoContable, SisSuceso sisSuceso) throws Exception {
        if (conDetalle != null) {
            conDetalleDao.actualizar(conDetalle);
        }
        if (sisSucesoContable != null) {
            sucesoDao.insertar(sisSucesoContable);
        }
        saveOrUpdate(banChequeAux);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<ListaBanChequesNumeracionTO> getListaChequesNumeracionTO(String empresa) throws Exception {
        String sql = "SELECT * FROM banco.ban_cheques_numeracionORDER BY chq_secuencial";
        return genericSQLDao.obtenerPorSql(sql, ListaBanChequesNumeracionTO.class);
    }

    @Override
    public boolean isExisteChequeAimprimir(String empresa, String cuentaContable, String numeroCheque,
            Long detSecuencia) throws Exception {
        String sql = "SELECT * FROM banco.fun_sw_cheque_repetido('" + empresa + "', '" + cuentaContable + "', '"
                + numeroCheque + "' , " + detSecuencia + ")";
        boolean resp = (boolean) genericSQLDao.obtenerObjetoPorSql(sql);

        return resp;
    }

    @Override
    public BanChequeTO buscarBanChequeTO(String empresa, String cuenta, String numero) throws Exception {
        String sql = "SELECT * FROM banco.fun_cheque(" + "'" + empresa + "'," + "'" + cuenta + "'," + "'" + numero
                + "')";
        return genericSQLDao.obtenerObjetoPorSql(sql, BanChequeTO.class);
    }

    @Override
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosTO(String empresa, String cuentaBancaria)
            throws Exception {
        cuentaBancaria = cuentaBancaria == null ? null : "'" + cuentaBancaria + "'";
        String sql = "SELECT * FROM banco.fun_cheques_no_impresos('" + empresa + "', " + cuentaBancaria + "," + null + ")";
        return genericSQLDao.obtenerPorSql(sql, BanListaChequesNoImpresosTO.class);
    }

    @Override
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosWebTO(String empresa, String cuentaBancaria, String modulo)
            throws Exception {
        cuentaBancaria = cuentaBancaria == null ? null : "'" + cuentaBancaria + "'";
        modulo = modulo == null ? null : "'" + modulo + "'";
        String sql = "SELECT * FROM banco.fun_cheques_no_impresos('" + empresa + "', " + cuentaBancaria + "," + modulo + ")";
        return genericSQLDao.obtenerPorSql(sql, BanListaChequesNoImpresosTO.class);
    }

    @Override
    public List<BanListaChequeTO> getBanListaChequeTO(String empresa, String cuenta, String desde, String hasta,
            String tipo) throws Exception {
        String sql = "SELECT * FROM banco.fun_cheques(" + "'" + empresa + "', " + "'" + cuenta + "', " + "'" + desde
                + "', " + "'" + hasta + "', " + "'" + tipo + "');";
        return genericSQLDao.obtenerPorSql(sql, BanListaChequeTO.class);
    }

    @Override
    public List<BanListaChequesImpresosTO> getBanListaChequesImpresosTO(String empresa, String cuenta, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM banco.fun_cheques_impresos(" + "'" + empresa + "', " + "'" + cuenta + "', " + "'" + desde
                + "', " + "'" + hasta + "')";
        return genericSQLDao.obtenerPorSql(sql, BanListaChequesImpresosTO.class);
    }

    @Override
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechado(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception {
        sector = sector == null ? sector : "'" + sector + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        grupo = grupo == null ? grupo : "'" + grupo + "'";
        String sql = "SELECT * FROM banco.fun_detalle_cheques_en_custodia(" + "'" + empresa + "', " + sector + ", "
                + cliente + ", " + desde + ", " + hasta + "," + grupo + "," + ichfa + ");";
        return genericSQLDao.obtenerPorSql(sql, BanDetalleChequesPosfechadosTO.class);
    }
    
    @Override
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechadoAnticipos(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception {
        sector = sector == null ? sector : "'" + sector + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        desde = desde == null ? null : "'" + desde + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        grupo = grupo == null ? grupo : "'" + grupo + "'";
        String sql = "SELECT * FROM banco.fun_detalle_cheques_en_custodia_anticipos(" + "'" + empresa + "', " + sector + ", "
                + cliente + ", " + desde + ", " + hasta + "," + grupo + "," + ichfa + ");";
        return genericSQLDao.obtenerPorSql(sql, BanDetalleChequesPosfechadosTO.class);
    }

    @Override
    public List<BanFunChequesNoEntregadosTO> getBanFunChequesNoEntregadosTO(String empresa, String cuenta)
            throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        cuenta = cuenta == null ? cuenta : "'" + cuenta + "'";
        String sql = "SELECT chq_cuenta_codigo, chq_cuenta_detalle, chq_beneficiario, chq_numero, chq_valor, chq_fecha_emision, chq_fecha_vencimiento, "
                + "chq_observacion, chq_impreso, chq_revisado, chq_contable_periodo, chq_contable_tipo, chq_contable_numero, chq_secuencia, chq_orden, "
                + "false as estado, id FROM banco.fun_cheques_no_entregados(" + empresa + ", " + cuenta + ");";
        return genericSQLDao.obtenerPorSql(sql, BanFunChequesNoEntregadosTO.class);
    }

    @Override
    public List<BanFunChequesNoRevisadosTO> getBanFunChequesNoRevisadosTO(String empresa, String cuenta)
            throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        cuenta = cuenta == null ? cuenta : "'" + cuenta + "'";
        String sql = "SELECT chq_cuenta_codigo, chq_cuenta_detalle, chq_beneficiario, chq_numero, chq_valor, chq_fecha_emision, chq_fecha_vencimiento, "
                + "chq_observacion, chq_impreso, chq_entregado, chq_contable_periodo, chq_contable_tipo, chq_contable_numero, chq_secuencia, chq_orden, "
                + "false as estado, id FROM banco.fun_cheques_no_revisados(" + empresa + ", " + cuenta + ");";
        return genericSQLDao.obtenerPorSql(sql, BanFunChequesNoRevisadosTO.class);
    }

    @Override
    public List<BanChequePreavisadoTO> getBanFunChequesPreavisados(String empresa, String cuenta, String nombrebanco)
            throws Exception {
        String sql = "";

        if (nombrebanco.lastIndexOf("BOLIVARIANO") >= 0) {
            sql = "SELECT * FROM banco.fun_cheques_preavisados('" + empresa + "', '" + cuenta + "')";
        } else if (nombrebanco.lastIndexOf("INTERNACIONAL") >= 0) {
            sql = "SELECT ''::text,''::text,''::text,''::text,''::text,chq_beneficiario_tipo_id,''::text,''::text,''::text,"
                    + "''::text,chq_cuenta_numero,chq_cheque_numero,''::text,chq_cheque_valor,''::text,''::text,''::text,''::text,chq_fecha_revision"
                    + " FROM banco.fun_cheques_preavisados_internacional('" + empresa + "', '" + cuenta + "')";
        } else {
            sql = "SELECT * FROM banco.fun_cheques_preavisados('" + empresa + "', '" + cuenta + "')";
        }

        return genericSQLDao.obtenerPorSql(sql, BanChequePreavisadoTO.class);
    }

    @Override
    public int getConteoChequesPreavisados(String empresa, String cuenta) throws Exception {
        String sql = "SELECT COUNT(*) FROM banco.ban_preavisos " + "WHERE pre_empresa = '" + empresa
                + "' AND pre_cuenta_contable='" + cuenta + "'";

        // return Integer
        // .parseInt(UtilsConversiones.convertirListToArray(genericSQLDao.obtenerPorSql(sql),
        // 0)[0].toString());
        return (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        // return
        // Integer.parseInt(genericSQLDao.obtenerObjetoPorSql(sql).toString());
    }

    @Override
    public int getConteoChequesPreavisados(String empresa, String cuentaContable, String nombreArchivo)
            throws Exception {
        String sql = "SELECT COUNT(*) FROM banco.ban_preavisos " + "WHERE pre_empresa = '" + empresa
                + "' AND pre_cuenta_contable = '" + cuentaContable + "' AND " + "pre_nombre_archivo_generado = '"
                + nombreArchivo + "'";
        return Integer.parseInt(genericSQLDao.obtenerObjetoPorSql(sql).toString());
    }

    @Override
    public Object getBanChequeSecuencial(String empresa, String cuenta) throws Exception {
        try {
            String sql = "SELECT * FROM banco.fun_cheque_obtener_secuencial('" + empresa + "', '" + cuenta + "');";
            Object[] array = UtilsConversiones.convertirListToArray(genericSQLDao.obtenerPorSql(sql), 0);

            if (array != null && array.length > 0) {
                if (array[0] == null) {
                    return 0;
                }
                return Integer.parseInt(array[0].toString().trim());
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean insertarBanCheque(BanCheque banCheque, SisSuceso sisSuceso, ConContable conContable,
            ConDetalle conDetalle) throws Exception {
        //contableService.actualizar(conContable);
        conDetalleDao.actualizar(conDetalle);
        saveOrUpdate(banCheque);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarBanCheque(BanCheque banCheque, SisSuceso sisSuceso) throws Exception {
        actualizar(banCheque);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarBanCheque(BanCheque banCheque, SisSuceso sisSuceso) throws Exception {
        insertar(banCheque);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarBanCheques(BanCheque banCheque, SisSuceso sisSuceso) throws Exception {
        eliminar(banCheque);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public Boolean accionBanCheques(List<BanCheque> banCheques, List<ConContableConDetalle> conContableConDetalles,
            List<SisSuceso> sisSucesos, char accion) throws Exception {
        for (BanCheque banCheque : banCheques) {
            if (accion == 'E') {
                banCheque.setConcCodigo(null);
                banCheque.setConcCuentaContable(null);
                banCheque.setConcEmpresa(null);
            }
            saveOrUpdate(banCheque);
        }
        for (ConContableConDetalle conContableConDetalle : conContableConDetalles) {
            //contableService.actualizar(conContableConDetalle.getConContable());
            conDetalleDao.actualizar(conContableConDetalle.getConDetalle());
        }
        for (SisSuceso sisSuceso : sisSucesos) {
            sucesoDao.insertar(sisSuceso);
        }
        return true;
    }

    @Override
    public boolean actualizarCambioCheque(String empresa, String cuenta, String referencia, String referenciaNueva, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM banco.actualizar_cambio_cheque("
                + "'" + empresa + "',"
                + "'" + cuenta + "',"
                + "'" + referencia + "',"
                + "'" + referenciaNueva + "',"
                + "'" + periodo + "',"
                + "'" + tipo + "',"
                + "'" + numero + "')";
        return (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<ConDetalle> listarChequesReversados(String empresa, String desde, String hasta) throws Exception {
        String sql = "";

        sql = "SELECT con_contable_detalle.* "
                + "FROM contabilidad.con_detalle con_contable_detalle "
                + "INNER JOIN contabilidad.con_contable  "
                + "ON con_contable_detalle.con_empresa = con_contable.con_empresa AND "
                + "   con_contable_detalle.con_periodo = con_contable.con_periodo AND "
                + "   con_contable_detalle.con_tipo = con_contable.con_tipo AND "
                + "   con_contable_detalle.con_numero = con_contable.con_numero "
                + "INNER JOIN contabilidad.con_cuentas "
                + "ON con_contable_detalle.cta_empresa = con_cuentas.cta_empresa AND "
                + "   con_contable_detalle.cta_codigo = con_cuentas.cta_codigo "
                //cheques
                + "LEFT JOIN banco.ban_cheque cheque "
                + "ON con_contable_detalle.det_secuencia = cheque.det_secuencia "
                //fin cheques
                + "WHERE con_contable.con_fecha >= '" + desde + "' AND con_contable.con_fecha <= '" + hasta + "' "
                + "AND con_contable.con_empresa = '" + empresa + "' "
                + "AND con_contable_detalle.det_documento IS NOT NULL "
                + "AND con_contable_detalle.cta_codigo IN (SELECT cta_cuenta_contable FROM banco.ban_cuenta WHERE cta_empresa = '" + empresa + "') "
                + "AND con_contable_detalle.det_debito_credito = 'C' "
                + "AND (con_contable.con_reversado OR con_contable.con_referencia = 'banco.ban_cheque_reversados' OR "
                + "(con_contable.con_anulado AND cheque.chq_impreso) OR cheque.chq_reversado)";

        return genericSQLDao.obtenerPorSql(sql, ConDetalle.class);
    }

}
