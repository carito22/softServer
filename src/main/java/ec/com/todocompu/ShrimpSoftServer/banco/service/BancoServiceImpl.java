package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ConsultaDatosBancoCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBanco;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class BancoServiceImpl implements BancoService {

    @Autowired
    private BancoDao bancoDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso; 
    private String susDetalle;

    @Override
    public List<ListaBanBancoTO> getListaBanBancoTO(String empresa) throws Exception {
        return bancoDao.getListaBanBancoTO(empresa);
    }

    @Override
    public List<ListaBanBancoTO> getListaBanBancoTODefecto(String empresa) throws Exception {
        return bancoDao.getListaBanBancoTODefecto(empresa);
    }

    @Override
    public BanBancoTO getBancoTO(String empresa, String codigo) throws Exception {
        BanBanco banBanco = bancoDao.obtenerPorId(BanBanco.class, new BanBancoPK(empresa, codigo));
        BanBancoTO banBancoTO = ConversionesBanco.convertirBanBanco_BanBancoTO(banBanco);
        return banBancoTO;
    }

    @Override
    public ConsultaDatosBancoCuentaTO getConsultaDatosBancoCuentaTO(String empresa, String cuenta) throws Exception {
        return bancoDao.getConsultaDatosBancoCuentaTO(empresa, cuenta);
    }

    @Override
    public boolean insertarBanBancoTO(BanBancoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanCodigo() + " " + banBancoTO.getBanNombre();
        susDetalle = "Se ingreso el Banco " + banBancoTO.getBanNombre() + " con código "
                + banBancoTO.getBanCodigo();
        susSuceso = "INSERT";
        susTabla = "banco.ban_banco";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaSistema());
        if (bancoDao.obtenerPorId(BanBanco.class,
                new BanBancoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo())) == null) {
            BanBanco banBanco = ConversionesBanco.convertirBanBancoTO_BanBanco(banBancoTO);
            comprobar = bancoDao.insertarBanBanco(banBanco, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarBanBancoTO(BanBancoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanCodigo() + " " + banBancoTO.getBanNombre();
        susDetalle = "Se modificó el Banco " + banBancoTO.getBanNombre() + " con código " + banBancoTO.getBanCodigo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_banco";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaSistema());
        BanBanco banBancoModificar = bancoDao.obtenerPorId(BanBanco.class,
                new BanBancoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo()));
        if (banBancoModificar != null) {
            banBancoTO.setUsrInsertaBanco(banBancoModificar.getUsrCodigo());
            banBancoTO.setUsrFechaInsertaBanco(
                    UtilsValidacion.fecha(banBancoModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanBanco banBanco = ConversionesBanco.convertirBanBancoTO_BanBanco(banBancoTO);
            comprobar = bancoDao.modificarBanBanco(banBanco, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarBanBancoTO(BanBancoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banBancoTO.getBanCodigo() + " " + banBancoTO.getBanNombre();
        susDetalle = "Se eliminó el Banco " + banBancoTO.getBanNombre() + " con código " + banBancoTO.getBanCodigo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_banco";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaSistema());
        BanBanco banBancoModificar = bancoDao.obtenerPorId(BanBanco.class,
                new BanBancoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo()));
        if (banBancoModificar != null) {
            banBancoTO.setUsrInsertaBanco(banBancoModificar.getUsrCodigo());
            banBancoTO.setUsrFechaInsertaBanco(
                    UtilsValidacion.fecha(banBancoModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanBanco banBanco = ConversionesBanco.convertirBanBancoTO_BanBanco(banBancoTO);
            comprobar = bancoDao.eliminarBanBanco(banBanco, sisSuceso);
        }
        return comprobar;
    }
    
}
