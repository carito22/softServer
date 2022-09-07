package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class CobrosDetalleVentasDaoImpl extends GenericDaoImpl<CarCobrosDetalleVentas, Integer> implements CobrosDetalleVentasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<CarCobrosDetalleVentas> listarDetallesVentasPorCobro(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM cartera.car_cobros_detalle_ventas "
                + "WHERE car_cobros_detalle_ventas.cob_empresa = '" + empresa + "' AND "
                + "car_cobros_detalle_ventas.cob_periodo = '" + periodo + "' AND " + "car_cobros_detalle_ventas.cob_tipo = '"
                + tipo + "' AND " + "car_cobros_detalle_ventas.cob_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarCobrosDetalleVentas.class);
    }

}
