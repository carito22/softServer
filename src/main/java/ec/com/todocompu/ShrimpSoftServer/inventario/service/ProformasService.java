package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ProformasService {

    public InvProformaCabeceraTO getInvProformaCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroProforma) throws Exception;

    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(String empresa, String periodo, String motivo,
            String busqueda) throws Exception;

    public List<InvListaConsultaProformaTO> getListaInvConsultaProformas(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros) throws Exception;

    public MensajeTO insertarInvProformasTO(InvProformasTO invProformasTO,
            List<InvProformasDetalleTO> listaInvProformasDetalleTO, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarInvProformasTO(InvProformasTO invProformasTO,
            List<InvProformasDetalleTO> listaInvProformasDetalleTO,
            List<InvProformasDetalleTO> listaInvProformasEliminarDetalleTO, boolean quitarAnulado, SisInfoTO sisInfoTO)
            throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String desmayorizarProforma(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String desmayorizarLoteProforma(List<InvListaConsultaProformaTO> listado, SisInfoTO sisInfoTO) throws Exception;

    public String anularProforma(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String restaurarProforma(String empresa, String periodo, String motivo, String numero) throws Exception;

    public Map<String, Object> consultarProforma(Map<String, Object> map) throws Exception;

    public Map<String, Object> consultarProformaSoloActivos(Map<String, Object> map) throws Exception;

    public Map<String, Object> nuevaProforma(Map<String, Object> map) throws Exception;

    public List<InvProformaClienteTO> listarInvProformaClienteTO(String empresa, String cliCodigo) throws Exception;
}
