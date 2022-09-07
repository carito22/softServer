/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciaciones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface DepreciacionActivoFijoDao extends GenericDao<AfDepreciaciones, Integer> {

    public AfDepreciaciones getAfDepreciaciones(Integer codigo) throws Exception;

    public boolean insertarAfDepreciaciones(AfDepreciaciones afDepreciaciones, SisSuceso sisSuceso) throws Exception;

    public List<AfDepreciacionesTO> listarDepreciaciones(String empresa, String fechaHasta) throws Exception;

    public List<AfDepreciacionesTO> listarDepreciacionesConsulta(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AfDepreciaciones> listarDepreciacionesSegunContable(String empresa, String periodo, String tipo, String numero) throws Exception;

    public boolean productoSeEncuentraDepreciacion(String empresa, String codigoProducto) throws Exception;

    public boolean insertarModificarAfDepreciaciones(ConContable conContable, List<AfDepreciaciones> listado, SisSuceso sisSuceso) throws Exception;

    public List<ConContableTO> obtenerContablesSegunDepreciacion(String empresa, String periodo, String codigoProducto) throws Exception;
}
