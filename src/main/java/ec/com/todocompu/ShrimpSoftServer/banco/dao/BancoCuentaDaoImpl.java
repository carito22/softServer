package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoCuentaDao;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuenta;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuentaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCuenta;
import java.math.BigInteger;

@Repository
public class BancoCuentaDaoImpl extends GenericDaoImpl<BanCuenta, BanCuentaPK> implements BancoCuentaDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoCuentaDao sucesoCuentaDao;

    @Override
    public BigDecimal getBanValorSaldoSistema(String empresa, String cuenta, String hasta) throws Exception {
        try {
            String sql = "" + "SELECT * FROM contabilidad.fun_saldo_cuenta(" + "'" + empresa + "', " + "'" + cuenta//el booleano aun no se sabe para que sirve(aun no hay funcion)
                    + "', " + "'" + hasta + "', false );";
            return (BigDecimal) genericSQLDao.obtenerObjetoPorSql(sql);
        } catch (Exception e) {
            return new BigDecimal("0.00");
        }
    }

    @Override
    public boolean canDeleteCuenta(String empresa, String codigoContable) throws Exception {
        boolean encontrado = false;
        String sql = "SELECT count(*) as rpta " + "FROM contabilidad.con_detalle " + "INNER JOIN banco.ban_cheque "
                + "ON con_detalle.det_secuencia = ban_cheque.det_secuencia " + "WHERE cta_empresa = ('" + empresa
                + "') " + "AND cta_codigo = ('" + codigoContable + "');";
        BigInteger rpta = (BigInteger) genericSQLDao.obtenerObjetoPorSql(sql);
        int valor = rpta.compareTo(new BigInteger("0"));
        if (valor >= 1) {
            encontrado = true;
        }
        return encontrado;
    }

    @Override
    public List<ListaBanCuentaTO> getListaBanCuentaTO(String empresa) throws Exception {
        String sql = "SELECT ban_codigo, cta_numero, cta_titular, cta_oficial, cta_formato_cheque, cta_cuenta_contable, cta_codigo_bancario, cta_prefijo_bancario,"
                + " row_number() over (partition by '' order by cta_cuenta_contable) id, cta_numeracion "
                + "FROM banco.ban_cuenta WHERE (cta_empresa = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, ListaBanCuentaTO.class);
    }

    @Override
    public List<BanComboBancoTO> getBanComboBancoTO(String empresa) throws Exception {
        String sql = "SELECT ban_nombre, cta_numero, cta_titular, cta_formato_cheque, cta_cuenta_contable, cta_codigo_bancario, cta_prefijo_bancario "
                + "FROM banco.ban_cuenta INNER JOIN banco.ban_banco "
                + "ON ban_cuenta.ban_empresa = ban_banco.ban_empresa AND "
                + "ban_cuenta.ban_codigo = ban_banco.ban_codigo " + "WHERE (ban_cuenta.ban_empresa = '" + empresa
                + "');";
        return genericSQLDao.obtenerPorSql(sql, BanComboBancoTO.class);
    }

    @Override
    public List<BanComboCuentaTO> getBanComboCuentaTO(String empresa) throws Exception {
        String sql = "SELECT cta_cuenta_contable, cta_numero || ' | ' || ban_nombre cta_cuenta_bancaria "
                + "FROM banco.ban_cuenta INNER JOIN banco.ban_banco ON "
                + "ban_cuenta.ban_empresa = ban_banco.ban_empresa AND "
                + "ban_cuenta.ban_codigo = ban_banco.ban_codigo " + "WHERE ban_cuenta.ban_empresa='" + empresa + "';";
        return genericSQLDao.obtenerPorSql(sql, BanComboCuentaTO.class);
    }

    @Override
    public List<String> getBanCuentasContablesBancos(String empresa) throws Exception {
        String sql = "SELECT cta_cuenta_contable FROM banco.ban_cuenta WHERE cta_empresa = '" + empresa + "'";
        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public String getNombreBanco(String empresa, String cuentaContable) throws Exception {
        String sql = "SELECT ban_nombre " + "FROM banco.ban_cuenta INNER JOIN banco.ban_banco "
                + "ON ban_cuenta.ban_empresa = ban_banco.ban_empresa AND "
                + "ban_cuenta.ban_codigo = ban_banco.ban_codigo " + "WHERE cta_empresa='" + empresa + "' AND "
                + "cta_cuenta_contable='" + cuentaContable + "';";
        try {
            Object[] array = UtilsConversiones.convertirListToArray(genericSQLDao.obtenerPorSql(sql), 0);
            return array[0].toString().trim();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return "";
        }

    }

    @Override
    public boolean insertarBanCuenta(BanCuenta banCuenta, SisSuceso sisSuceso) throws Exception {
        insertar(banCuenta);
        sisSuceso.setSusDetalle("Se ingreso la Cuenta con número " + banCuenta.getCtaNumero());
        sucesoDao.insertar(sisSuceso);
        ////////insertar suceso cuenta///
        SisSucesoCuenta sucesoCuenta = new SisSucesoCuenta();
        String json = UtilsJSON.objetoToJson(banCuenta);
        sucesoCuenta.setSusJson(json);
        sucesoCuenta.setSisSuceso(sisSuceso);
        sucesoCuenta.setBanCuenta(banCuenta);
        sucesoCuentaDao.insertar(sucesoCuenta);
        return true;
    }

    @Override
    public boolean modificarBanCuenta(BanCuenta banCuenta, SisSuceso sisSuceso) throws Exception {
        actualizar(banCuenta);
        sucesoDao.insertar(sisSuceso);
        ////////insertar suceso cuenta///
        SisSucesoCuenta sucesoCuenta = new SisSucesoCuenta();
        String json = UtilsJSON.objetoToJson(banCuenta);
        sucesoCuenta.setSusJson(json);
        sucesoCuenta.setSisSuceso(sisSuceso);
        sucesoCuenta.setBanCuenta(banCuenta);
        sucesoCuentaDao.insertar(sucesoCuenta);
        return true;
    }

    @Override
    public boolean eliminarBanCuenta(BanCuenta banCuenta, SisSuceso sisSuceso) throws Exception {
        eliminar(banCuenta);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<String> getValidarCuentaContableConBancoExiste(String banEmpresa, String banCodigo) throws Exception {
        String sql = "SELECT '('|| ban_cuenta.cta_cuenta_contable || ' - '|| ban_banco.ban_codigo || ' '|| ban_banco.ban_nombre|| ')'"
                + "FROM banco.ban_banco INNER JOIN banco.ban_cuenta ON "
                + "ban_banco.ban_empresa = ban_cuenta.ban_empresa AND "
                + "ban_banco.ban_codigo = ban_cuenta.ban_codigo "
                + "WHERE (ban_banco.ban_empresa, ban_banco.ban_codigo)= ('" + banEmpresa + "', '" + banCodigo + "')";

        return genericSQLDao.obtenerPorSql(sql);
    }

}
