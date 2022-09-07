package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface TransferenciasService {

    public InvTransferenciasTO getInvTransferenciasCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroTransferencia) throws Exception;

    public List<InvListaDetalleTransferenciaTO> getInvTransferenciasDetalleTO(String empresa, String codigoPeriodo, String motivo,
            String numeroTransferencia) throws Exception;

    public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(String empresa, String fechaDesde,
            String fechaHasta, String status) throws Exception;

    public List<InvListaConsultaTransferenciaTO> getListaInvConsultaTransferencias(String empresa, String periodo,
            String motivo, String busqueda, String nRegistros) throws Exception;

    public MensajeTO insertarInvTransferenciaTO(InvTransferenciasTO invTransferenciasTO,
            List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarInvTransferenciasTO(InvTransferenciasTO invTransferenciasTO,
            List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO,
            List<InvTransferenciasDetalleTO> listaInvTransferenciasEliminarDetalleTO, boolean desmayorizar,
            InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion, SisInfoTO sisInfoTO) throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception;

    public void quitarPendiente(InvTransferenciasTO invTransferenciasTO) throws Exception;

    public String anularTransferencia(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String desmayorizarTransferencia(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String restaurarTransferencia(String empresa, String periodo, String motivo, String numero) throws Exception;

    public Map<String, Object> consultarTransferencia(Map<String, Object> map) throws Exception;

    public Map<String, Object> consultarTransferenciaActivo(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosBasicosNuevaTransferencia(Map<String, Object> map) throws Exception;

    public List<String> desmayorizarTransferenciaLote(String empresa, List<InvListaConsultaTransferenciaTO> lista, SisInfoTO sisInfoTO) throws Exception;
}
