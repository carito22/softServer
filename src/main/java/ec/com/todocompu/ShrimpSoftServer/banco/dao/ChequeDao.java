package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequePreavisadoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanDetalleChequesPosfechadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ChequeDao extends GenericDao<BanCheque, Long> {

    public boolean isExisteCheque(String empresa, String cuentaContable, String numeroCheque) throws Exception;

    public boolean reutilizarCheque(BanCheque banChequeAux, ConDetalle conDetalle, SisSuceso sisSucesoContable, SisSuceso sisSuceso) throws Exception;

    public List<ListaBanChequesNumeracionTO> getListaChequesNumeracionTO(String empresa) throws Exception;

    @Transactional

    public boolean isExisteChequeAimprimir(String empresa, String cuentaContable, String numeroCheque,
            Long detSecuencia) throws Exception;

    @Transactional
    public BanChequeTO buscarBanChequeTO(String empresa, String cuenta, String numero) throws Exception;

    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosTO(String empresa, String cuentaBancaria) throws Exception;

    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosWebTO(String empresa, String cuentaBancaria, String modulo)
            throws Exception;

    public List<BanListaChequeTO> getBanListaChequeTO(String empresa, String cuenta, String desde, String hasta,
            String tipo) throws Exception;

    public List<BanListaChequesImpresosTO> getBanListaChequesImpresosTO(String empresa, String cuenta, String desde, String hasta) throws Exception;

    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechado(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;
    
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechadoAnticipos(String empresa, String sector, String cliente, String desde, String hasta, String grupo, boolean ichfa) throws Exception;

    public List<BanFunChequesNoEntregadosTO> getBanFunChequesNoEntregadosTO(String empresa, String cuenta)
            throws Exception;

    public List<BanFunChequesNoRevisadosTO> getBanFunChequesNoRevisadosTO(String empresa, String cuenta)
            throws Exception;

    public List<BanChequePreavisadoTO> getBanFunChequesPreavisados(String empresa, String cuenta, String nombrebanco)
            throws Exception;

    public int getConteoChequesPreavisados(String empresa, String cuenta) throws Exception;

    public int getConteoChequesPreavisados(String empresa, String cuentaContable, String nombreArchivo)
            throws Exception;

    public Object getBanChequeSecuencial(String empresa, String cuenta) throws Exception;

    public boolean insertarBanCheque(BanCheque banCheque, SisSuceso sisSuceso, ConContable conContable,
            ConDetalle conDetalle) throws Exception;

    @Transactional
    public boolean modificarBanCheque(BanCheque banCheque, SisSuceso sisSuceso) throws Exception;

    public boolean insertarBanCheque(BanCheque banCheque, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarBanCheques(BanCheque banCheque, SisSuceso sisSuceso) throws Exception;

    public Boolean accionBanCheques(List<BanCheque> banCheques, List<ConContableConDetalle> conContableConDetalles, List<SisSuceso> sisSucesos, char accion) throws Exception;

    public boolean actualizarCambioCheque(String empresa, String cuenta, String referencia, String referenciaNueva, String periodo, String tipo, String numero) throws Exception;

    public List<ConDetalle> listarChequesReversados(String empresa, String desde, String hasta) throws Exception;
}
