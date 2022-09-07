/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPrecioTallaLiquidacionPescaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
@Transactional
public interface PrdPrecioTallaLiquidacionPescaService {

    public List<PrdPrecioTallaLiquidacionPesca> getListPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String busqueda);

    public PrdPrecioTallaLiquidacionPesca obtenerPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String codigoTalla);

    public MensajeTO cambiarPrecioTallaLiquidacionPesca(List<PrdPrecioTallaLiquidacionPescaTO> listado, SisInfoTO sisInfoTO) throws Exception;

    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTONuevos(String empresa, String fecha, String busqueda) throws Exception;

    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTO(String empresa, String fecha, String busqueda);

}
