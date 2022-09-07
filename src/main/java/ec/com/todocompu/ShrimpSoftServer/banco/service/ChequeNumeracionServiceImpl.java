package ec.com.todocompu.ShrimpSoftServer.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.ChequeNumeracionDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ChequeNumeracionServiceImpl implements ChequeNumeracionService {

    @Autowired
    private ChequeNumeracionDao chequeNumeracionDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean insertarBanChequeNumeracionTO(BanChequesNumeracionTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanSecuencial().toString();
        susDetalle = "Se ingreso la chequera desde " + banBancoTO.getBanDesde() + " hasta " + banBancoTO.getBanHasta();
        susSuceso = "INSERT";
        susTabla = "banco.ban_cheques_numeracion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (chequeNumeracionDao.obtenerPorId(BanChequeNumeracion.class, banBancoTO.getBanSecuencial()) == null) {
            BanChequeNumeracion banBanco = ConversionesBanco.convertirChequesNumeracionTO_BanChequesNumeracion(banBancoTO);
            comprobar = chequeNumeracionDao.insertarBanChequesNumeracion(banBanco, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarBanChequeNumeracionTO(BanChequesNumeracionTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanSecuencial().toString();
        susDetalle = "Se modifico la numeracion desde " + banBancoTO.getBanDesde() + " ,hasta " + banBancoTO.getBanHasta();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_cheques_numeracion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        BanChequeNumeracion banBancoModificar = chequeNumeracionDao.obtenerPorId(BanChequeNumeracion.class, banBancoTO.getBanSecuencial());
        if (banBancoModificar != null) {
            BanChequeNumeracion banBanco = ConversionesBanco.convertirChequesNumeracionTO_BanChequesNumeracion(banBancoTO);
            comprobar = chequeNumeracionDao.modificarBanChequeNumeracion(banBanco, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarBanChequeNumeracionTO(BanChequesNumeracionTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanSecuencial().toString();
        susDetalle = "Se eliminó la numeracion de cheques desde " + banBancoTO.getBanDesde() + " hasta " + banBancoTO.getBanHasta();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_banco";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        BanChequeNumeracion banBancoModificar = chequeNumeracionDao.obtenerPorId(BanChequeNumeracion.class, banBancoTO.getBanSecuencial());
        if (banBancoModificar != null) {
            BanChequeNumeracion banBanco = ConversionesBanco.convertirChequesNumeracionTO_BanChequesNumeracion(banBancoTO);
            comprobar = chequeNumeracionDao.eliminarBanChequeNumeracion(banBanco, sisSuceso);
        }
        return comprobar;
    }
}
