package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoCajaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCaja;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class BancoCajaServiceImpl implements BancoCajaService {

    @Autowired
    private BancoCajaDao cajaDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<ListaBanCajaTO> getListaBanCajaTO(String empresa) throws Exception {
        return cajaDao.getListaBanCajaTO(empresa);
    }

    @Override
    public List<BanCajaTO> getListBanCajaTO(String empresa) throws Exception {
        return cajaDao.getListBanCajaTO(empresa);
    }

    @Override
    public boolean insertarBanCajaTO(BanCajaTO banCajaTO, SisInfoTO sisInfoTO) throws Exception {
        susClave = banCajaTO.getCajaCodigo() + " " + banCajaTO.getCajaNombre();
        susDetalle = "Se ingreso la Caja " + banCajaTO.getCajaNombre() + " con código " + banCajaTO.getCajaCodigo();
        susSuceso = "INSERT";
        susTabla = "banco.ban_caja";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banCajaTO.setUsrFechaInsertaCaja(UtilsValidacion.fechaSistema());
        BanCaja banCaja = ConversionesBanco.convertirBanCajaTO_BanCaja(banCajaTO);

        return cajaDao.insertarBanCaja(banCaja, sisSuceso);
    }

    @Override
    public boolean modificarBanCajaTO(BanCajaTO banCajaTO, SisInfoTO sisInfoTO) throws Exception {
        susClave = banCajaTO.getCajaCodigo() + " " + banCajaTO.getCajaNombre();
        susDetalle = "Se modificó la Caja " + banCajaTO.getCajaNombre() + " con código "
                + banCajaTO.getCajaCodigo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_caja";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banCajaTO.setUsrFechaInsertaCaja(UtilsValidacion.fechaSistema());
        BanCaja banCajaModificar = cajaDao.obtenerPorId(BanCaja.class,
                new BanCajaPK(banCajaTO.getEmpCodigo(), banCajaTO.getCajaCodigo()));
        if (banCajaModificar != null) {
            banCajaTO.setUsrInsertaCaja(banCajaModificar.getUsrCodigo());
            banCajaTO.setUsrFechaInsertaCaja(
                    UtilsValidacion.fecha(banCajaModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanCaja banCaja = ConversionesBanco.convertirBanCajaTO_BanCaja(banCajaTO);
            return cajaDao.modificarBanCaja(banCaja, sisSuceso);
        } else {
            return false;
        }
    }

    @Override
    public boolean eliminarBanCajaTO(BanCajaTO banCajaTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banCajaTO.getCajaCodigo() + " " + banCajaTO.getCajaNombre();
        susDetalle = "Se eliminó la Caja " + banCajaTO.getCajaNombre() + " con código " + banCajaTO.getCajaCodigo();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_caja";
        banCajaTO.setUsrFechaInsertaCaja(UtilsValidacion.fechaSistema());
        BanCaja banCajaModificar = cajaDao.obtenerPorId(BanCaja.class,
                new BanCajaPK(banCajaTO.getEmpCodigo(), banCajaTO.getCajaCodigo()));
        if (banCajaModificar != null) {
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            banCajaTO.setUsrInsertaCaja(banCajaModificar.getUsrCodigo());
            banCajaTO.setUsrFechaInsertaCaja(
                    UtilsValidacion.fecha(banCajaModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanCaja banCaja = ConversionesBanco.convertirBanCajaTO_BanCaja(banCajaTO);
            comprobar = cajaDao.eliminarBanCaja(banCaja, sisSuceso);
        }
        return comprobar;
    }

}
