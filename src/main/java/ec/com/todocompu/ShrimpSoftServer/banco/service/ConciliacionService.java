package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ConciliacionService {

    @Transactional
    public String getBanConciliacionFechaHasta(String empresa, String cuenta) throws Exception;

    @Transactional
    public Boolean conciliacionHasta(String empresa, String hasta, String cuenta) throws Exception;

    @Transactional
    public boolean conciliacionPendiente(String empresa, String cuentaContable) throws Exception;

    @Transactional
    public List<BanListaConciliacionTO> getBanListaConciliacionTO(String empresa, String buscar) throws Exception;

    @Transactional
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaDebitoTO(String empresa, String cuenta, String codigo, String hasta) throws Exception;

    @Transactional
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaCreditoTO(String empresa, String cuenta, String codigo, String hasta) throws Exception;

    @Transactional
    public String accionBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanChequeTO> banChequeTOs, List<BanConciliacionDatosAdjuntos> listadoImagenes, char accion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String guardarBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, List<BanConciliacionDatosAdjuntos> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, List<BanConciliacionDatosAdjuntos> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosConciliacion(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaConciliacion(Map<String, Object> map) throws Exception;

    public List<BanConciliacionDatosAdjuntos> listarImagenesDeBanConciliacion(BanConciliacionPK pk) throws Exception;
}
