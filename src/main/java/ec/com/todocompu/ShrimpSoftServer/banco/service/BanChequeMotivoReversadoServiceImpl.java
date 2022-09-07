package ec.com.todocompu.ShrimpSoftServer.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoChequeMotivoReversadoDao;

@Service
public class BanChequeMotivoReversadoServiceImpl implements BanChequeMotivoReversadoService {

    @Autowired
    private BancoChequeMotivoReversadoDao bancoChequeMotivoReversadoDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarMotivoBancoReversoCheque(BanChequeMotivoReversado banChequeMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (bancoChequeMotivoReversadoDao.obtenerPorId(BanChequeMotivoReversado.class, banChequeMotivo.getBanChequeMotivoReversadoPK()) != null) {
            retorno = "FEl motivo de reverso que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(banChequeMotivo.getBanChequeMotivoReversadoPK().getBanEmpresa());
            susDetalle = "Se ingresó el motivo de reversado cheque del contable " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
            susClave = banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
            susTabla = "banco.ban_cheque_motivo_reversado";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            banChequeMotivo.setBanDescripcion(banChequeMotivo.getBanDescripcion().toUpperCase());
            banChequeMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            banChequeMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            banChequeMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
            if (bancoChequeMotivoReversadoDao.insertarBanChequeMotivoReversado(banChequeMotivo, sisSuceso)) {
                retorno = "TEl motivo de reverso para el contable: " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo() + ", se guardo con existo";
            } else {
                retorno = "FHubo un error al guardar el registro. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<BanChequeMotivoReversado> getListaMotivoReversadoCheque(String empresa) throws Exception {
        return bancoChequeMotivoReversadoDao.getListaMotivoReversadoCheque(empresa);
    }

    @Override
    public String modificarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";

        susDetalle = "Se modificó el motivo de reversado del contable " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        susClave = banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_cheque_motivo_reversado";

        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (bancoChequeMotivoReversadoDao.modificarMotivoReversado(banChequeMotivo, sisSuceso)) {
            retorno = "TEl motivo de reversado para el contable: " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo() + " se a modificado correctamente.";
        } else {
            retorno = "FHubo un error al modificar el motivo de reverso, Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(banChequeMotivo.getBanChequeMotivoReversadoPK().getBanEmpresa());
        susDetalle = "Se eliminó el motivo de reverso: " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        susClave = banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        susSuceso = "DELETE";
        susTabla = "banco.ban_cheque_motivo_reversado";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (bancoChequeMotivoReversadoDao.eliminarMotivoReversado(banChequeMotivo, sisSuceso)) {
            retorno = "TEl motivo de reversado: Código: " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo() + ", se eliminó correctamente";
        } else {
            retorno = "FHubo un erro al eliminar el motivo de reverso. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoMotivoReversado(BanChequeMotivoReversado banChequeMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(banChequeMotivo.getBanChequeMotivoReversadoPK().getBanEmpresa());
        susDetalle = "Se cambió el estado del motivo de reverso: " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        susClave = banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_cheque_motivo_reversado";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banChequeMotivo.setBanEstado(estado);
        if (bancoChequeMotivoReversadoDao.modificarEstadoMotivoReversado(banChequeMotivo, sisSuceso)) {
            retorno = "TEl motivo de reversado: " + banChequeMotivo.getBanChequeMotivoReversadoPK().getBanCodigoMotivo();
        } else {
            retorno = "Hubo un error al modificar el motivo de reverso. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

}
