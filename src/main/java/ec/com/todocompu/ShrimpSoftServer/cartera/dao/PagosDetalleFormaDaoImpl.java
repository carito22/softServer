package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleForma;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PagosDetalleFormaDaoImpl extends GenericDaoImpl<CarPagosDetalleForma, Integer> implements PagosDetalleFormaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<CarPagosDetalleForma> listarDetallesFormaPorPago(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM cartera.car_pagos_detalle_forma "
                + "WHERE car_pagos_detalle_forma.pag_empresa = '" + empresa + "' AND "
                + "car_pagos_detalle_forma.pag_periodo = '" + periodo + "' AND " + "car_pagos_detalle_forma.pag_tipo = '"
                + tipo + "' AND " + "car_pagos_detalle_forma.pag_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarPagosDetalleForma.class);
    }
    
}
