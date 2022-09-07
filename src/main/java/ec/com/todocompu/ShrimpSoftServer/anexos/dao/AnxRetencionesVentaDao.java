/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxRetencionesVentaDao extends GenericDao<AnxRetencionesVenta, Integer> {

    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportar(String empresa, String cliIdentificacion, String busqueda) throws Exception;

    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(String empresa) throws Exception;

    public AnxRetencionesVenta obtenerAnxRetencionesVenta(String empresa, String numeroDocumento, String identificacionCliente);

    public boolean insertarListadoAnxRetencionesVenta(List<AnxRetencionesVenta> listado, List<SisSuceso> listadoSisSuceso) throws Exception;

    public boolean insertarAnxRetencionesVenta(AnxRetencionesVenta anxRetencionesVenta, SisSuceso sisSuceso) throws Exception;
    
    public boolean anularRetencionesComprobantes (String empresa, String claveAcceso, SisSuceso sisSuceso) throws Exception;
}
