/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdPrecioTallaLiquidacionPescaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.TallaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPrecioTallaLiquidacionPescaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class PrdPrecioTallaLiquidacionPescaServiceImpl implements PrdPrecioTallaLiquidacionPescaService {

    @Autowired
    private TallaDao tallaDao;
    private BigDecimal cero = new BigDecimal("0.00");

    @Autowired
    private PrdPrecioTallaLiquidacionPescaDao prdPrecioTallaLiquidacionPescaDao;

    @Override
    public List<PrdPrecioTallaLiquidacionPesca> getListPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String busqueda) {
        return prdPrecioTallaLiquidacionPescaDao.getListPrdPrecioTallaLiquidacionPesca(empresa, fecha, busqueda);
    }

    @Override
    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTONuevos(String empresa, String fecha, String busqueda) {
        return prdPrecioTallaLiquidacionPescaDao.getListPrdPrecioTallaLiquidacionPescaTONuevos(empresa, fecha, busqueda);
    }

    @Override
    public PrdPrecioTallaLiquidacionPesca obtenerPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String codigoTalla) {
        return prdPrecioTallaLiquidacionPescaDao.obtenerPrdPrecioTallaLiquidacionPesca(empresa, fecha, codigoTalla);
    }

    @Override
    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTO(String empresa, String fecha, String busqueda) {
        return prdPrecioTallaLiquidacionPescaDao.getListPrdPrecioTallaLiquidacionPescaTO(empresa, fecha, busqueda);
    }

    @Override
    public MensajeTO cambiarPrecioTallaLiquidacionPesca(List<PrdPrecioTallaLiquidacionPescaTO> listado, SisInfoTO sisInfoTO) throws Exception {
        List<SisSuceso> sisSucesos = new ArrayList<>();
        List<String> mensajeClase = new ArrayList<>();
        MensajeTO mensajeTO = new MensajeTO();

        List<PrdPrecioTallaLiquidacionPesca> listaGuardar = new ArrayList<>();

        for (PrdPrecioTallaLiquidacionPescaTO item : listado) {
            PrdTalla talla = tallaDao.getPrdLiquidacionTalla(item.getTallaEmpresa(), item.getTallaCodigo());

            if (talla != null) {
                item.setPrdPrecio01(item.getPrdPrecio01() == null ? cero : item.getPrdPrecio01());
                item.setPrdPrecio02(item.getPrdPrecio02() == null ? cero : item.getPrdPrecio02());
                item.setPrdPrecio03(item.getPrdPrecio03() == null ? cero : item.getPrdPrecio03());
                item.setPrdPrecio04(item.getPrdPrecio04() == null ? cero : item.getPrdPrecio04());
                item.setPrdPrecio05(item.getPrdPrecio05() == null ? cero : item.getPrdPrecio05());
                item.setPrdPrecio06(item.getPrdPrecio06() == null ? cero : item.getPrdPrecio06());
                String susClave = item.getTallaCodigo();
                String susDetalle = "";
                String susSuceso = "";
                //modifica
                if (item.getUsrCodigo() != null && item.getUsrEmpresa() != null) {
                    susDetalle = "Se actualizó el precio de la talla: " + talla.getTallaDescripcion();
                    susSuceso = "UPDATE";
                    //nuevo
                } else {
                    item.setUsrCodigo(sisInfoTO.getUsuario());
                    item.setUsrEmpresa(sisInfoTO.getEmpresa());
                    item.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
                    susDetalle = "Se insertó el precio de la talla: " + talla.getTallaDescripcion();
                    susSuceso = "INSERT";
                }

                String susTabla = "produccion.prd_precio_talla_liquidacion_pesca";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                sisSucesos.add(sisSuceso);
                //CONVERTIR A OBJETO
                PrdPrecioTallaLiquidacionPesca tallaPescaPrecio = ConversionesProduccion.convertirPrdPrecioTallaLiquidacionPescaTO_PrdPrecioTallaLiquidacionPesca(item);
                listaGuardar.add(tallaPescaPrecio);
            } else {
                mensajeClase.add("La talla " + talla.getTallaDescripcion() + " no se encuentra disponible.");
            }
        }

        if (mensajeClase.isEmpty()) {
            if (prdPrecioTallaLiquidacionPescaDao.cambiarPrecioTallaLiquidacionPesca(listaGuardar, sisSucesos)) {
                mensajeTO.setMensaje("T<html>Los precios de los tallas fueron actualizados correctamente...</html>");
            } else {
                mensajeTO.setMensaje("F<html>Hubo un error al actualizar los precios de las tallas...<br>Contacte con el administrador o intentelo de nuevo</html>");
            }
        } else {
            mensajeTO.setMensaje("F<html>Hubo un error al actualizar los precios de las tallas...</html>");
            mensajeTO.setListaErrores1(mensajeClase);
        }
        return mensajeTO;
    }

}
