package ec.com.todocompu.ShrimpSoftServer.banco.service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDebitoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.listaBanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebito;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebitoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author KevinQuispe
 */
@Service
public class BancoDebitoServiceImpl implements BancoDebitoService {

    @Autowired
    private BancoDebitoDao bancoDebitoDao;
    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<listaBanBancoDebitoTO> getListaBanBancoDebitoTO(String empresa) throws Exception {

        return bancoDebitoDao.getListaBanBancoDebitoTO(empresa);
    }

    @Override
    public boolean insertarBancoDebitoTO(BanBancoDebitoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanCodigo() + " " + banBancoTO.getBanNombre();
        susDetalle = "Se ingresó el banco " + banBancoTO.getBanNombre() + " con código " + banBancoTO.getBanCodigo();
        susSuceso = "INSERT";
        susTabla = "banco.ban_banco_debito";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaSistema());
        if (bancoDebitoDao.obtenerPorId(BanBancoDebito.class,
                new BanBancoDebitoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo())) == null) {
            BanBancoDebito banBanco = ConversionesBanco.convertirBanBancoTO_BanBancoDebito(banBancoTO);
            comprobar = bancoDebitoDao.insertarBancoDebito(banBanco, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarBancoDebitoTO(BanBancoDebitoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanCodigo() + " " + banBancoTO.getBanNombre();
        susDetalle = "Se modificó el banco " + banBancoTO.getBanNombre() + " con código " + banBancoTO.getBanCodigo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_banco_debito";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaSistema());
        BanBancoDebito banBancoModificar = bancoDebitoDao.obtenerPorId(BanBancoDebito.class,
                new BanBancoDebitoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo()));
        if (banBancoModificar != null) {
            banBancoTO.setUsrInsertaBanco(banBancoModificar.getUsrCodigo());
            banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fecha(banBancoModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanBancoDebito banBanco = ConversionesBanco.convertirBanBancoTO_BanBancoDebito(banBancoTO);
            comprobar = bancoDebitoDao.modificarBancoDebito(banBanco, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarBancoDebitoTO(BanBancoDebitoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanCodigo() + " " + banBancoTO.getBanNombre();
        susDetalle = "Se eliminó el banco " + banBancoTO.getBanNombre() + " con código " + banBancoTO.getBanCodigo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_banco_debito";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaSistema());
        BanBancoDebito banBancoModificar = bancoDebitoDao.obtenerPorId(BanBancoDebito.class,
                new BanBancoDebitoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo()));
        if (banBancoModificar != null) {
            banBancoTO.setUsrInsertaBanco(banBancoModificar.getUsrCodigo());
            banBancoTO.setUsrFechaInsertaBanco(
                    UtilsValidacion.fecha(banBancoModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanBancoDebito banBanco = ConversionesBanco.convertirBanBancoTO_BanBancoDebito(banBancoTO);
            comprobar = bancoDebitoDao.eliminarBancoDebito(banBanco, sisSuceso);
        }
        return comprobar;
    }
}
