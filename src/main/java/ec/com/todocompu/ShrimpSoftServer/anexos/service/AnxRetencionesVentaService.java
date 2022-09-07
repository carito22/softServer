/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxRetencionesVentaService {

    public AnxRetencionesVenta obtenerAnxRetencionesVenta(String empresa, String numeroDocumento, String identificacionCliente);

    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportar(String empresa, String cliIdentificacion, String busqueda) throws Exception;

    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(String empresa) throws Exception;

    @Transactional
    public String insertarAnxRetencionesVenta(AnxRetencionesVenta anxRetencionesVenta, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean insertarListadoAnxRetencionesVenta(List<AnxRetencionesVenta> listado, List<SisSuceso> listadoSisSuceso) throws Exception;

    public String anularRetencionesComprobantes(String empresa, List<AnxRetencionesVenta> listado, SisInfoTO sisInfoTO) throws Exception;
}
