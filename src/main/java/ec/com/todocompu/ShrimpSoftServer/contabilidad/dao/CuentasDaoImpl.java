package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CuentasDaoImpl extends GenericDaoImpl<ConCuentas, ConCuentasPK> implements CuentasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarConCuenta(ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception {
        insertar(conCuentas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarListadoConCuenta(List<ConCuentas> listadoConCuentas, List<SisSuceso> listadoSisSuceso) throws Exception {
        insertar(listadoConCuentas);
        sucesoDao.insertar(listadoSisSuceso);
        return true;
    }

    @Override
    public boolean modificarConCuenta(ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception {
        actualizar(conCuentas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarConCuentaLlavePrincipal(ConCuentas conCuentasEliminar, ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception {
        eliminar(conCuentasEliminar);
        actualizar(conCuentas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarListaConCuenta(List<ConCuentas> listaConCuentas) throws Exception {
        for (int i = 0; i < listaConCuentas.size(); i++) {
            actualizar(listaConCuentas.get(i));
        }
        return true;
    }

    @Override
    public boolean eliminarConCuenta(ConCuentas conCuentas, SisSuceso sisSuceso) throws Exception {
        eliminar(conCuentas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<ConCuentasTO> getListaConCuentasTO(String empresa) throws Exception {
        String sql = "SELECT cta_empresa,  cta_codigo, REPEAT(' ', "
                + "CHAR_LENGTH(TRIM(BOTH ' ' FROM cta_codigo))) || cta_detalle as cta_detalle, "
                + "cta_activo, cta_bloqueada, usr_codigo, null as usr_fecha_inserta, "
                + "cta_grupo_comparativo, cta_detalle_comparativo, cta_relacionada FROM "
                + "contabilidad.con_cuentas WHERE cta_empresa = ('" + empresa + "')" + " ORDER BY cta_codigo";
        return genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);
    }

    @Override
    public List<ConCuentasTO> obtenerConCuentasParaSinFormatear(String empresa) throws Exception {
        String sql = "SELECT cta_empresa,  cta_codigo, cta_detalle, "
                + "cta_activo, cta_bloqueada, usr_codigo, null as usr_fecha_inserta, "
                + "cta_grupo_comparativo, cta_detalle_comparativo, cta_relacionada FROM "
                + "contabilidad.con_cuentas WHERE cta_empresa = ('" + empresa + "')" + " ORDER BY cta_codigo";
        return genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);
    }

    @Override
    public List<ConCuentasTO> getRangoCuentasTO(String empresa, String codigoCuentaDesde, String codigoCuentaHasta,
            int largoCuenta) throws Exception {
        codigoCuentaDesde = codigoCuentaDesde == null ? codigoCuentaDesde : "'" + codigoCuentaDesde + "'";
        codigoCuentaHasta = codigoCuentaHasta == null ? codigoCuentaHasta : "'" + codigoCuentaHasta + "'";
        String sql = "SELECT * FROM contabilidad.con_cuentas WHERE cta_empresa = ('" + empresa
                + "') and cta_codigo >= (" + codigoCuentaDesde + ") and cta_codigo <= (" + codigoCuentaHasta
                + ") and LENGTH(cta_codigo) = ('" + largoCuenta + "') ORDER BY cta_codigo";

        return genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);
    }

    @Override
    public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(String empresa) throws Exception {
        String sql = "SELECT flu_empresa,  flu_codigo, REPEAT(' ', "
                + "CHAR_LENGTH(TRIM(BOTH ' ' FROM flu_codigo))) || flu_detalle as flu_detalle, flu_activo, usr_codigo, usr_fecha_inserta FROM "
                + "contabilidad.con_cuentas_flujo WHERE flu_empresa = ('" + empresa + "') ORDER BY flu_codigo";

        return genericSQLDao.obtenerPorSql(sql, ConCuentasFlujoTO.class);
    }

    @Override
    public List<ConCuentasTO> getListaBuscarConCuentasTO(String empresa, String buscar, String ctaGrupo)
            throws Exception {
        ctaGrupo = ctaGrupo == null ? ctaGrupo : "'" + ctaGrupo + "'";
        String sql = "SELECT cta_empresa, cta_codigo, "
                + "REPEAT(' ', CHAR_LENGTH(TRIM(BOTH ' ' FROM cta_codigo))) || cta_detalle cta_detalle, "
                + "cta_activo, cta_bloqueada, usr_codigo, usr_fecha_inserta, "
                + "cta_grupo_comparativo, cta_detalle_comparativo, cta_relacionada FROM "
                + "contabilidad.con_cuentas WHERE cta_empresa = ('"
                + empresa + "') AND " + "CASE WHEN ('" + buscar + "') = '' THEN TRUE "
                + "ELSE (cta_codigo || UPPER(cta_detalle) LIKE '%' || TRANSLATE('" + buscar
                + "', ' ', '%') || '%') END AND " + "CASE WHEN " + ctaGrupo + " IS NULL THEN TRUE "
                + "ELSE SUBSTRING(cta_codigo,1,LENGTH(" + ctaGrupo + "))=" + ctaGrupo + " END "
                + "ORDER BY cta_codigo;";

        return genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);
    }

    @Override
    public ConCuentas buscarCuentas(String empresa, String cuenta) throws Exception {
        String sql = "SELECT cta_empresa,cta_codigo,cta_detalle,cta_activo, cta_bloqueada, usr_codigo,usr_fecha_inserta, "
                + "cta_grupo_comparativo, cta_detalle_comparativo, cta_relacionada FROM "
                + "contabilidad.con_cuentas where cta_empresa = ('" + empresa + "') AND cta_codigo " + "= ('"
                + cuenta + "') ";

        List<ConCuentasTO> conCuentasFormaPago = genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);

        ConCuentasPK conCuentasPK = null;
        conCuentasPK = new ConCuentasPK();
        ConCuentas conCuentas = null;
        conCuentas = new ConCuentas();

        if (conCuentasFormaPago.size() > 0) {
            conCuentasPK.setCtaCodigo(conCuentasFormaPago.get(0).getCuentaCodigo());
            conCuentasPK.setCtaEmpresa(conCuentasFormaPago.get(0).getEmpCodigo());
            conCuentas.setConCuentasPK(conCuentasPK);
            conCuentas.setCtaDetalle(conCuentasFormaPago.get(0).getCuentaDetalle());
            conCuentas.setCtaActivo(conCuentasFormaPago.get(0).getCuentaActivo());
            conCuentas.setCtaBloqueada(conCuentasFormaPago.get(0).getCuentaBloqueada());
            conCuentas.setCtaRelacionada(conCuentasFormaPago.get(0).getCtaRelacionada());
            conCuentas.setCtaGrupoComparativo(conCuentasFormaPago.get(0).getCtaGrupoComparativo());
            conCuentas.setCtaDetalleComparativo(conCuentasFormaPago.get(0).getCtaDetalleComparativo());
            conCuentas.setUsrEmpresa(conCuentasFormaPago.get(0).getEmpCodigo());
            conCuentas.setUsrCodigo(conCuentasFormaPago.get(0).getUsrInsertaCuenta());
            conCuentas.setUsrFechaInserta(
                    UtilsValidacion.fechaString_Date(conCuentasFormaPago.get(0).getUsrFechaInsertaCuenta()));

        }
        return conCuentas;
    }

    @Override
    public ConCuentasTO buscarCuentasTO(String empresa, String cuenta) throws Exception {
        String sql = "SELECT cta_empresa,cta_codigo, REPEAT(' ', CHAR_LENGTH(TRIM(BOTH ' ' FROM cta_codigo))) || cta_detalle as cta_detalle,"
                + " cta_activo, cta_bloqueada, usr_codigo, usr_fecha_inserta, "
                + "cta_grupo_comparativo, cta_detalle_comparativo, cta_relacionada FROM "
                + "contabilidad.con_cuentas where cta_empresa = ('" + empresa + "') AND cta_codigo " + "= ('"
                + cuenta + "') ";
        List<ConCuentasTO> conCuentasTO = genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);
        ConCuentasTO cuentaTO = new ConCuentasTO();
        if (conCuentasTO.size() > 0) {
            cuentaTO = conCuentasTO.get(0);
        }

        return cuentaTO;
    }

    @Override
    public int getConteoCuentasTO(String empresa) throws Exception {
        String sql = "SELECT COUNT(*) FROM contabilidad.con_cuentas " + "WHERE cta_empresa = '" + empresa + "'";
        return (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String eliminarTodoConCuentas(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_eliminar_cuentas('" + empresa + "');";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }
}
