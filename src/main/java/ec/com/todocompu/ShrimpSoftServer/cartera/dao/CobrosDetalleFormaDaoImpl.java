package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class CobrosDetalleFormaDaoImpl extends GenericDaoImpl<CarCobrosDetalleForma, Integer> implements CobrosDetalleFormaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<CarCobrosDetalleForma> listarDetallesFormaPorCobro(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM cartera.car_cobros_detalle_forma "
                + "WHERE car_cobros_detalle_forma.cob_empresa = '" + empresa + "' AND "
                + "car_cobros_detalle_forma.cob_periodo = '" + periodo + "' AND " + "car_cobros_detalle_forma.cob_tipo = '"
                + tipo + "' AND " + "car_cobros_detalle_forma.cob_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarCobrosDetalleForma.class);
    }

}
