/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPrecioTallaLiquidacionPescaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface PrdPrecioTallaLiquidacionPescaDao extends GenericDao<PrdPrecioTallaLiquidacionPesca, PrdPrecioTallaLiquidacionPescaPK> {

    public Boolean cambiarPrecioTallaLiquidacionPesca(List<PrdPrecioTallaLiquidacionPesca> listado, List<SisSuceso> sisSucesos) throws Exception;

    public PrdPrecioTallaLiquidacionPesca obtenerPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String tallaCodigo);

    public List<PrdPrecioTallaLiquidacionPesca> getListPrdPrecioTallaLiquidacionPesca(String empresa, String fecha, String busqueda);

    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTONuevos(String empresa, String fecha, String busqueda);

    public List<PrdPrecioTallaLiquidacionPescaTO> getListPrdPrecioTallaLiquidacionPescaTO(String empresa, String fecha, String busqueda);

}
