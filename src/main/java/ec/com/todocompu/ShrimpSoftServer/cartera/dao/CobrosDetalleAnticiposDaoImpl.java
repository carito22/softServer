package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleAnticipos;
import java.util.List;

@Repository
public class CobrosDetalleAnticiposDaoImpl extends GenericDaoImpl<CarCobrosDetalleAnticipos, Integer>
        implements CobrosDetalleAnticiposDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    // *************
    @Override
    public CarListaCobrosClienteTO getCobrosConsultaClienteAnticipo(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        String sql = "SELECT inv_cliente.cli_codigo, inv_cliente.cli_razon_social, "
                + "inv_cliente.cli_id_numero, inv_cliente.cli_direccion, "
                + "con_contable.con_observaciones, con_contable.con_fecha, con_contable.con_anulado, 0.00 as cob_saldo_anterior, 0.00 as cob_valor, 0.00 as cob_saldo_actual "
                + "FROM cartera.car_cobros_anticipos INNER JOIN inventario.inv_cliente "
                + "ON car_cobros_anticipos.cli_empresa = inv_cliente.cli_empresa AND "
                + "car_cobros_anticipos.cli_codigo = inv_cliente.cli_codigo " + "INNER JOIN contabilidad.con_contable "
                + "ON car_cobros_anticipos.ant_empresa = con_contable.con_empresa AND "
                + "car_cobros_anticipos.ant_periodo = con_contable.con_periodo AND "
                + "car_cobros_anticipos.ant_tipo = con_contable.con_tipo AND "
                + "car_cobros_anticipos.ant_numero = con_contable.con_numero "
                + "WHERE car_cobros_anticipos.ant_empresa = '" + empresa + "' AND "
                + "car_cobros_anticipos.ant_periodo = '" + periodo + "' AND " + "car_cobros_anticipos.ant_tipo = '"
                + tipo + "' AND " + "car_cobros_anticipos.ant_numero = '" + numero + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, CarListaCobrosClienteTO.class);
    }

    @Override
    public Boolean getCarReversarCobroAnticipos(String empresa, String periodo, String numero, String tipo)
            throws Exception {
        String sql = "SELECT ant_reversado " + "FROM cartera.car_cobros_anticipos "
                + "WHERE car_cobros_anticipos.ant_empresa = '" + empresa + "' "
                + "AND car_cobros_anticipos.ant_periodo = '" + periodo + "' " + "AND (car_cobros_anticipos.ant_tipo = '"
                + tipo + "') " + "AND car_cobros_anticipos.ant_numero = '" + numero + "';";
        return (Boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<CarCobrosDetalleAnticipos> listarDetallesAnticipoPorCobro(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM cartera.car_cobros_detalle_anticipos "
                + "WHERE car_cobros_detalle_anticipos.cob_empresa = '" + empresa + "' AND "
                + "car_cobros_detalle_anticipos.cob_periodo = '" + periodo + "' AND " + "car_cobros_detalle_anticipos.cob_tipo = '"
                + tipo + "' AND " + "car_cobros_detalle_anticipos.cob_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarCobrosDetalleAnticipos.class);
    }

}
