package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoCuentaDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBanco;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuenta;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuentaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import java.util.HashMap;
import java.util.Map;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private BancoCuentaDao cuentaDao;

    @Autowired
    private BancoDao bancoDao;

    @Autowired
    private EstructuraDao estructuraDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<ListaBanCuentaTO> getListaBanCuentaTO(String empresa) throws Exception {
        return cuentaDao.getListaBanCuentaTO(empresa);
    }

    @Override
    public BanCuentaTO getBancoCuentaTO(String empresa, String cuentaContable) throws Exception {
        BanCuenta banCuenta = cuentaDao.obtenerPorId(BanCuenta.class, new BanCuentaPK(empresa, cuentaContable));
        BanCuentaTO banBancoTO = ConversionesBanco.convertirBanCuenta_BanCuentaTO(banCuenta);
        return banBancoTO;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudCuentas(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<ListaBanBancoTO> listadoBancos = bancoDao.getListaBanBancoTO(empresa);
        List<ConEstructuraTO> listadoEstructura = estructuraDao.getListaConEstructuraTO(empresa);

        /*Si existe una lista de estructura, sumar sus grupos y obtener el tamanio*/
        if (listadoEstructura != null && !listadoEstructura.isEmpty()) {
            Integer tamanioEstructura = listadoEstructura.get(0).getEstGrupo1() + listadoEstructura.get(0).getEstGrupo2() + listadoEstructura.get(0).getEstGrupo3() + listadoEstructura.get(0).getEstGrupo4() + listadoEstructura.get(0).getEstGrupo5() + listadoEstructura.get(0).getEstGrupo6();
            campos.put("tamanioEstructura", tamanioEstructura);
        }

        campos.put("listadoBancos", listadoBancos);

        return campos;
    }

    @Override
    public boolean insertarBanCuentaTO(BanCuentaTO banCuentaTO, String codigoBanco, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = "Se inserto la cuenta bancaria " + banCuentaTO.getCtaNumero();
        susSuceso = "INSERT";
        susTabla = "banco.ban_cuenta";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banCuentaTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaSistema());
        banCuentaTO.setUsrCodigo(sisInfoTO.getUsuario());
        BanCuenta banCuenta = ConversionesBanco.convertirBanCuentaTO_BanCuenta(banCuentaTO);
        BanBanco banBanco = bancoDao.obtenerPorId(BanBanco.class, new BanBancoPK(banCuentaTO.getCtaEmpresa(), codigoBanco));
        if (banBanco != null) {
            banCuenta.setBanBanco(banBanco);
            comprobar = cuentaDao.insertarBanCuenta(banCuenta, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarBanCuentaTO(BanCuentaTO banCuentaTO, String codigoBanco, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = banCuentaTO.getCtaContable().toString();
        susDetalle = "Se modificó la Cuenta de la cuenta contable " + banCuentaTO.getCtaContable();
        susSuceso = "UPDATE";
        susTabla = "banco.ban_cuenta";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        banCuentaTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaSistema());
        BanCuenta banCuentaModificar = cuentaDao.obtenerPorId(BanCuenta.class,
                new BanCuentaPK(banCuentaTO.getCtaEmpresa(), banCuentaTO.getCtaContable()));
        if (banCuentaModificar != null) {
            banCuentaTO.setUsrCodigo(banCuentaModificar.getUsrCodigo());
            banCuentaTO.setUsrInsertaCuenta(banCuentaModificar.getUsrCodigo());
            banCuentaTO.setUsrFechaInsertaCuenta(
                    UtilsValidacion.fecha(banCuentaModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            BanCuenta banCuenta = ConversionesBanco.convertirBanCuentaTO_BanCuenta(banCuentaTO);
            BanBanco banBanco = bancoDao.obtenerPorId(BanBanco.class,
                    new BanBancoPK(banCuentaTO.getCtaEmpresa(), codigoBanco));
            if (banBanco != null) {
                banCuenta.setBanBanco(banBanco);
                comprobar = cuentaDao.modificarBanCuenta(banCuenta, sisSuceso);
            }
        }
        return comprobar;
    }

    @Override
    public boolean eliminarBanCuentaTO(BanCuentaTO banCuentaTO, String codigoBanco, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        if (!cuentaDao.canDeleteCuenta(banCuentaTO.getCtaEmpresa(), banCuentaTO.getCtaContable())) {
            susClave = banCuentaTO.getCtaContable().toString();
            susDetalle = "Se eliminó la Cuenta de la cuenta contable " + banCuentaTO.getCtaContable();
            susSuceso = "UPDATE";
            susTabla = "banco.ban_cuenta";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            banCuentaTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaSistema());
            BanCuenta banCuentaModificar = cuentaDao.obtenerPorId(BanCuenta.class,
                    new BanCuentaPK(banCuentaTO.getCtaEmpresa(), banCuentaTO.getCtaContable().toString()));
            if (banCuentaModificar != null) {
                banCuentaTO.setUsrInsertaCuenta(banCuentaModificar.getUsrCodigo());
                banCuentaTO.setUsrFechaInsertaCuenta(
                        UtilsValidacion.fecha(banCuentaModificar.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                BanCuenta banCuenta = ConversionesBanco.convertirBanCuentaTO_BanCuenta(banCuentaTO);
                BanBanco banBanco = bancoDao.obtenerPorId(BanBanco.class,
                        new BanBancoPK(banCuentaTO.getCtaEmpresa(), codigoBanco));
                if (banBanco != null) {
                    banCuenta.setBanBanco(banBanco);
                    comprobar = cuentaDao.eliminarBanCuenta(banCuenta, sisSuceso);
                }
            }
        }
        return comprobar;
    }

    @Override
    public List<BanComboBancoTO> getBanComboBancoTO(String empresa) throws Exception {
        return cuentaDao.getBanComboBancoTO(empresa);
    }

    @Override
    public BigDecimal getBanValorSaldoSistema(String empresa, String cuenta, String hasta) throws Exception {
        return cuentaDao.getBanValorSaldoSistema(empresa, cuenta, hasta);
    }

    @Override
    public List<BanComboCuentaTO> getBanComboCuentaTO(String empresa) throws Exception {
        return cuentaDao.getBanComboCuentaTO(empresa);
    }

    @Override
    public List<String> getBanCuentasContablesBancos(String empresa) throws Exception {
        return cuentaDao.getBanCuentasContablesBancos(empresa);
    }

    @Override
    public List<String> getValidarCuentaContableConBancoExiste(String banEmpresa, String banCodigo) throws Exception {
        return cuentaDao.getValidarCuentaContableConBancoExiste(banEmpresa, banCodigo);
    }
}
