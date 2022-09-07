/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPrecioTallaLiquidacionPescaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class PrdPrecioTallaLiquidacionPescaDaoImpl extends GenericDaoImpl<PrdPrecioTallaLiquidacionPesca, PrdPrecioTallaLiquidacionPescaPK>
        implements PrdPrecioTallaLiquidacionPescaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private TallaDao tallaDao;

    @Override
    public List<PrdPrecioTallaLiquidacionPesca> getListPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String busqueda) {
        String complementoSql = "";
        if (busqueda != null && !busqueda.equals("")) {
            complementoSql = " AND (talla_codigo LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ";
        }

        String sql = "SELECT * FROM produccion.prd_precio_talla_liquidacion_pesca WHERE talla_empresa = ('" + empresa + "') AND "
                + " prd_fecha <= ('" + fecha + "')" + complementoSql + " ORDER BY prd_fecha DESC";
        return genericSQLDao.obtenerPorSql(sql, PrdPrecioTallaLiquidacionPesca.class);
    }

    @Override
    public PrdPrecioTallaLiquidacionPesca obtenerPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String tallaCodigo) {
        String sql = "SELECT * FROM produccion.prd_precio_talla_liquidacion_pesca WHERE talla_empresa = ('" + empresa + "') AND "
                + " prd_fecha <= ('" + fecha + "') AND talla_codigo = ('" + tallaCodigo + "') ORDER BY prd_fecha DESC  LIMIT 1";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdPrecioTallaLiquidacionPesca.class);
    }

    @Override
    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTO(String empresa, String fecha, String busqueda) {
        String complementoSql = "";
        if (busqueda != null && !busqueda.equals("")) {
            complementoSql = " AND (tl.talla_codigo || UPPER(talla_detalle) LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ";
        }

        String sql = "SELECT ROW_NUMBER() OVER() AS id, tl.talla_empresa, tl.talla_codigo, tl.prd_fecha,tl.prd_precio01, tl.prd_precio02, tl.prd_precio03, "
                + "tl.prd_precio04, tl.prd_precio05, tl.prd_precio06,tl.usr_empresa, tl.usr_codigo, tl.usr_fecha_inserta, "
                + "t.talla_detalle,t.talla_descripcion "
                + " FROM produccion.prd_precio_talla_liquidacion_pesca tl "
                + "INNER JOIN produccion.prd_talla t "
                + "ON t.talla_empresa = tl.talla_empresa "
                + "AND t.talla_codigo = tl.talla_codigo "
                + "WHERE tl.talla_empresa = ('" + empresa + "') AND "
                + " tl.prd_fecha = ('" + fecha + "')" + complementoSql + " ORDER BY prd_fecha DESC";
        return genericSQLDao.obtenerPorSql(sql, PrdPrecioTallaLiquidacionPescaTO.class);
    }

    @Override
    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTONuevos(String empresa, String fechaString, String busqueda) {
        List<PrdPrecioTallaLiquidacionPescaTO> listado = new ArrayList<>();
        List<PrdTalla> tallas = new ArrayList<>();
        Date fecha = null;
        if (fechaString != null && !fechaString.equals("")) {
            fecha = UtilsDate.fechaFormatoDate(fechaString, "yyyy-MM-dd");
        } else {
            fecha = UtilsDate.timestamp();
        }

        try {
            if (busqueda != null && !busqueda.equals("")) {
                tallas = tallaDao.getListaPrdLiquidacionTallaDetalle(empresa, busqueda);
            } else {
                tallas = tallaDao.getListaPrdLiquidacionTallaDetalle(empresa);
            }
            int i = 0;
            for (PrdTalla talla : tallas) {
                PrdPrecioTallaLiquidacionPescaTO precioTalla = new PrdPrecioTallaLiquidacionPescaTO();
                precioTalla.setId(i);
                precioTalla.setTallaCodigo(talla.getPrdLiquidacionTallaPK().getTallaCodigo());
                precioTalla.setTallaEmpresa(empresa);
                precioTalla.setTallaDetalle(talla.getTallaDetalle());
                precioTalla.setTallaDescripcion(talla.getTallaDescripcion());
                precioTalla.setPrdFecha(fecha);
                precioTalla.setPrdPrecio01(BigDecimal.ZERO);
                precioTalla.setPrdPrecio02(BigDecimal.ZERO);
                precioTalla.setPrdPrecio03(BigDecimal.ZERO);
                precioTalla.setPrdPrecio04(BigDecimal.ZERO);
                precioTalla.setPrdPrecio05(BigDecimal.ZERO);
                precioTalla.setPrdPrecio06(BigDecimal.ZERO);
                listado.add(precioTalla);
                i++;
            }
        } catch (Exception ex) {

        }

        return listado;
    }

    @Override
    public Boolean cambiarPrecioTallaLiquidacionPesca(List<PrdPrecioTallaLiquidacionPesca> listado, List<SisSuceso> sisSucesos) throws Exception {
        for (PrdPrecioTallaLiquidacionPesca precioTalla : listado) {
            saveOrUpdate(precioTalla);
        }
        for (SisSuceso sisSuceso : sisSucesos) {
            sucesoDao.insertar(sisSuceso);
        }
        return true;
    }

}
