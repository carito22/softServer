package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleAnticipos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PagosDetalleAnticiposDaoImpl extends GenericDaoImpl<CarPagosDetalleAnticipos, Integer> implements PagosDetalleAnticiposDao {
    
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<CarPagosDetalleAnticipos> listarDetallesAnticipoPorPago(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM cartera.car_pagos_detalle_anticipos "
                + "WHERE car_pagos_detalle_anticipos.pag_empresa = '" + empresa + "' AND "
                + "car_pagos_detalle_anticipos.pag_periodo = '" + periodo + "' AND " + "car_pagos_detalle_anticipos.pag_tipo = '"
                + tipo + "' AND " + "car_pagos_detalle_anticipos.pag_numero = '" + numero + "';";
        return genericSQLDao.obtenerPorSql(sql, CarPagosDetalleAnticipos.class);
    }

}
