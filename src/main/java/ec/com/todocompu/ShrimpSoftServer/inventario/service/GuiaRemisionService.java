/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
public interface GuiaRemisionService {

    @Transactional
    public List<InvConsultaGuiaRemisionTO> getListaInvGuiaRemisionTO(String empresa, String tipoDocumento, String fechaInicio, String fechaFinal, String nRegistros) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosBasicosGuiaRemision(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosBasicosGuiaRemisionConsulta(Map<String, Object> map) throws Exception;

    @Transactional
    public MensajeTO insertarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetallesTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetallesTO, List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetallesTOEliminar, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String validarSecuenciaPermitida(String empresa, String numero, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<InvGuiaRemisionDetalle> obtenerGuiaRemisionDetalle(String empresa, String periodo, String numero) throws Exception;

    @Transactional
    public List<InvGuiaRemisionDetalleTO> obtenerGuiaRemisionDetalleTO(String empresa, String periodo, String numero) throws Exception;

    @Transactional
    public String anularGuiaRemision(String empresa, String periodo, String numero) throws Exception;

    @Transactional
    public String desmayorizarGuiaRemision(String empresa, String periodo, String numero) throws Exception;

    @Transactional
    public InvGuiaRemision buscarInvGuiaRemision(String empresa, String periodo, String compNumero) throws Exception;

    public InvGuiaRemisionTO buscarInvGuiaRemisionTO(String empresa, String numeroDocumento) throws Exception;

    public Boolean actualizarClaveExterna(InvGuiaRemisionPK pk, String clave, SisInfoTO sisInfoTO) throws Exception;

    //PENDIENTE
    @Transactional
    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientes(String empresa) throws Exception;

    public String eliminarGuiaRemision(String empresa, String periodo, String numero, SisInfoTO sisInfoTO) throws Exception;

}
