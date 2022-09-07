package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleCompras;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PagosDetalleComprasDaoImpl extends GenericDaoImpl<CarPagosDetalleCompras, Integer> implements PagosDetalleComprasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<CarPagosDetalleCompras> listarDetallesComprasPorPago(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM cartera.car_pagos_detalle_compras "
                + "WHERE car_pagos_detalle_compras.pag_empresa = '" + empresa + "' AND "
                + "car_pagos_detalle_compras.pag_periodo = '" + periodo + "' AND " + "car_pagos_detalle_compras.pag_tipo = '"
                + tipo + "' AND " + "car_pagos_detalle_compras.pag_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarPagosDetalleCompras.class);
    }

}
