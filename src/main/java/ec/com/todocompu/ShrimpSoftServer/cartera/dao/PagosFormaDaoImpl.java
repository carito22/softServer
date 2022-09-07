package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoFormaPagoCarteraDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosForma;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaPagoCartera;

@Repository
public class PagosFormaDaoImpl extends GenericDaoImpl<CarPagosForma, Integer> implements PagosFormaDao {
    
    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoFormaPagoCarteraDao sucesoFormaPagoCarteraDao;
    
    @Override
    public Boolean accionCarFormaPago(CarPagosForma carPagosForma, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(carPagosForma);
        }
        if (accion == 'M') {
            actualizar(carPagosForma);
        }
        if (accion == 'E') {
            eliminar(carPagosForma);
        }
        
        sisSucesoDao.insertar(sisSuceso);
        if (accion == 'I' || accion == 'M') {
            SisSucesoFormaPagoCartera sisSucesoFp = new SisSucesoFormaPagoCartera();
            String json = UtilsJSON.objetoToJson(carPagosForma);
            sisSucesoFp.setSusJson(json);
            sisSucesoFp.setSisSuceso(sisSuceso);
            sisSucesoFp.setCarPagosForma(carPagosForma);
            sucesoFormaPagoCarteraDao.insertar(sisSucesoFp);
        }
        return true;
    }
    
    @Override
    public String buscarCtaFormaPago(String empresa, Integer secuencial) throws Exception {
        try {
            String sql = "SELECT cta_codigo " + "FROM cartera.car_pagos_forma " + "WHERE (usr_empresa = '" + empresa
                    + "') AND " + "(fp_secuencial = " + secuencial + ") AND "
                    + "(NOT fp_inactivo OR fp_inactivo IS NULL);";
            return (String) genericSQLDao.obtenerObjetoPorSql(sql);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Boolean buscarCarPagosForma(String ctaContable, String empresa, String sector) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM cartera.car_pagos_forma " + "WHERE (cta_codigo = '" + ctaContable
                + "') AND (cta_empresa = '" + empresa + "') AND (sec_codigo = '"+ sector +"');";
        return Integer.parseInt(genericSQLDao.obtenerObjetoPorSql(sql).toString()) == 1 ? true : false;
    }
    
    @Override
    public List<CarComboPagosCobrosFormaTO> getCarComboPagosCobrosForma(String empresa, char accion) throws Exception {
        String sql = "";
        if (accion == 'P') {
            sql = "SELECT fp_secuencial, sec_codigo || ' | ' || fp_detalle fp_detalle, car_pagos_forma.cta_codigo, "
                    + "ban_cuenta.cta_empresa IS NOT NULL validar, fp_inactivo as post_fechados, ban_cuenta.cta_numeracion as numeracion "
                    + "FROM cartera.car_pagos_forma LEFT JOIN banco.ban_cuenta "
                    + "ON car_pagos_forma.cta_empresa = ban_cuenta.cta_empresa AND "
                    + "car_pagos_forma.cta_codigo = ban_cuenta.cta_cuenta_contable WHERE car_pagos_forma.cta_empresa = '"
                    + empresa + "' AND NOT fp_inactivo " + "ORDER BY sec_codigo, fp_detalle";
        } else {
            sql = "SELECT fp_secuencial, sec_codigo || ' | ' || fp_detalle fp_detalle, cta_codigo, fp_inactivo as validar, "
                    + "det_cuenta IS NOT NULL post_fechados, null as numeracion "
                    + "FROM cartera.car_cobros_forma LEFT JOIN "
                    + "banco.ban_cuenta_caja_cheques ON car_cobros_forma.sec_empresa = ban_cuenta_caja_cheques.det_empresa "
                    + "AND car_cobros_forma.sec_codigo = ban_cuenta_caja_cheques.det_sector AND car_cobros_forma.cta_codigo "
                    + "= ban_cuenta_caja_cheques.det_cuenta WHERE car_cobros_forma.sec_empresa = '" + empresa
                    + "' AND NOT " + "fp_inactivo ORDER BY sec_codigo, fp_detalle;";
            
        }
        return genericSQLDao.obtenerPorSql(sql, CarComboPagosCobrosFormaTO.class);
    }
    
    @Override
    public List<CarListaPagosCobrosFormaTO> getCarListaPagosCobrosForma(String empresa, char accion) throws Exception {
        String sql = "";
        if (accion == 'P') {
            sql = "SELECT fp_secuencial, cta_codigo, fp_detalle, sec_codigo, fp_inactivo "
                    + "FROM cartera.car_pagos_forma " + "WHERE (usr_empresa = '" + empresa + "');";
        } else {
            sql = "SELECT fp_secuencial, cta_codigo, fp_detalle, sec_codigo, fp_inactivo "
                    + "FROM cartera.car_cobros_forma " + "WHERE (usr_empresa = '" + empresa + "');";
        }
        return genericSQLDao.obtenerPorSql(sql, CarListaPagosCobrosFormaTO.class);
    }
    
    @Override
    public List<CarPagosCobrosFormaTO> getListaPagosCobrosFormaTO(String empresa, char accion, boolean inactivos) throws Exception {
        String sql = "";
        String sqlCondicion = " ";
        if (!inactivos) {
            sqlCondicion = " and fp_inactivo is  false";
        }
        
        if (accion == 'P') {
            sql = "SELECT fp_secuencial, cta_codigo, fp_detalle, sec_codigo, fp_inactivo, usr_empresa, usr_codigo, usr_fecha_inserta "
                    + "FROM cartera.car_pagos_forma " + "WHERE (usr_empresa = '" + empresa + "') " + sqlCondicion;
        } else {
            sql = "SELECT fp_secuencial, cta_codigo, fp_detalle, sec_codigo, fp_inactivo, usr_empresa, usr_codigo, usr_fecha_inserta "
                    + "FROM cartera.car_cobros_forma " + "WHERE (usr_empresa = '" + empresa + "') " + sqlCondicion;
        }
        return genericSQLDao.obtenerPorSql(sql, CarPagosCobrosFormaTO.class);
    }
    
    @Override
    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception {
        String sql = "SELECT * FROM cartera.car_pagos_forma WHERE "
                + "(usr_empresa = '" + empresa + "') AND "
                + "(cta_codigo = '" + ctaCodigo + "') AND "
                + "(sec_empresa = '" + empresa + "') AND "
                + "(sec_codigo = '" + sector + "')";
        return genericSQLDao.obtenerPorSql(sql, CarPagosCobrosFormaTO.class).get(0);
    }
    
}
