/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface VentaGuiaRemisionService {
    //VENTA GUIA REMISION

    public Map<String, Object> obtenerDatosBasicosVentaGuiaRemision(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosBasicosImportarComprasTransferencias(Map<String, Object> map) throws Exception;

    public MensajeTO insertarTransaccionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO actualizarTransaccionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO insertarActualizarTransaccionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisInfoTO sisInfoTO) throws Exception;

    public InvVentaGuiaRemision obtenerInvVentaGuiaRemision(String empresa, String periodo, String motivo, String numero);
}
