/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class AnxVentaExportacionDaoImpl extends GenericDaoImpl<AnxVentaExportacion, Integer> implements AnxVentaExportacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public AnxVentaExportacion obtenerAnxVentaExportacion(String vtaEmpres, String vtaPeriodo, String vtaMotivo, String vtaNumero) {
        String sql = "SELECT * FROM anexo.anx_venta_exportacion WHERE vta_empresa='" + vtaEmpres + "' AND vta_periodo='" + vtaPeriodo + "' AND vta_motivo='" + vtaMotivo + "' AND vta_numero='" + vtaNumero + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxVentaExportacion.class);
    }

    @Override
    public List<AnxVentaExportacion> getListaAnxVentaExportacion(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.anx_venta_exportacion";
        return genericSQLDao.obtenerPorSql(sql, AnxVentaExportacion.class);
    }

    @Override
    public List<AnxListaVentaExportacionTO> getAnxListaVentaExportacionTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM " + "anexo.fun_exportaciones('" + empresa + "','" + fechaDesde + "','" + fechaHasta + "');";
        return genericSQLDao.obtenerPorSql(sql, AnxListaVentaExportacionTO.class);
    }

}
