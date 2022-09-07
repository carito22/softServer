package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequePreavisadoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanDetalleChequesPosfechadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ChequeNoImpresoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface ChequeService {

    @Transactional
    public List<ListaBanChequesNumeracionTO> getListaChequesNumeracionTO(String empresa) throws Exception;

    @Transactional
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosTO(String empresa, String cuentaBancaria) throws Exception;

    @Transactional
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosWebTO(String empresa, String cuentaBancaria, String modulo)
            throws Exception;

    @Transactional
    public String reutilizarCheque(Long detSecuencia, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean isExisteChequeAimprimir(String empresa, String cuentaContable, String numeroCheque,
            Long detSecuencia) throws Exception;

    @Transactional
    public boolean isExisteCheque(String empresa, String cuentaContable, String numeroCheque) throws Exception;

    @Transactional
    public String insertarBanChequeTO(BanChequeTO banChequeTO, String usrInserta, String numeroCheque, String empresa,
            SisInfoTO sisInfoTO) throws Exception;

    public String modificarFechaBanChequeTO(String empresa, String cuenta, String numero, String fecha, String usuario,
            SisInfoTO sisInfoTO) throws Exception;

    public String modificarNumeroBanChequeTO(String empresa, String cuenta, String numero, String numeroNuevo,
            String usuario, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<BanListaChequeTO> getBanListaChequeTO(String empresa, String cuenta, String desde, String hasta,
            String tipo) throws Exception;

    @Transactional
    public List<BanListaChequesImpresosTO> getBanListaChequesImpresosTO(String empresa, String cuenta, String desde, String hasta) throws Exception;

    @Transactional
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechado(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;
    
    @Transactional
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechadoAnticipos(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;

    @Transactional
    public List<BanFunChequesNoEntregadosTO> getBanFunChequesNoEntregadosTO(String empresa, String cuenta)
            throws Exception;

    @Transactional
    public List<String> insertarBanFunChequesNoEntregados(String empresa, String cuenta,
            List<BanFunChequesNoEntregadosTO> banFunChequesNoEntregadosTOs, String usuario, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public List<BanFunChequesNoRevisadosTO> getBanFunChequesNoRevisadosTO(String empresa, String cuenta)
            throws Exception;

    @Transactional
    public List<String> insertarBanFunChequesNoRevisados(String empresa, String cuenta,
            List<BanFunChequesNoRevisadosTO> banFunChequesNoRevisadosTOs, String usuario, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public List<BanChequePreavisadoTO> getBanFunChequesPreavisados(String empresa, String cuenta) throws Exception;

    @Transactional
    public int getConteoChequesPreavisados(String empresa, String cuenta) throws Exception;

    @Transactional
    public MensajeTO cambioDeCheque(String cuenta, String cuentaActual, String chequeAnterior, String chequeNuevo,
            String empresa, String usuario, String observaciones, String fecha, String con_tipo_cod,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO cambioDeChequeWeb(String cuenta, String cuentaActual, String chequeAnterior, String chequeNuevo,
            String empresa, String usuario, String observaciones, String fecha, String con_tipo_cod,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO reversarChequeWeb(String empresa, String motivo, String cp, String cuenta, String cheque, String fecha, String observaciones, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public BanCheque buscarBanCheque(Long secuencial) throws Exception;

    @Transactional
    public Object getBanChequeSecuencial(String empresa, String cuenta) throws Exception;

    public ChequeNoImpresoTO visualizarChequeNoImpreso(String empresa, BanListaChequesNoImpresosTO banListaChequesNoImpresosTO) throws Exception;

    public List<ConDetalle> listarChequesReversados(String empresa, String desde, String hasta) throws Exception;
}
