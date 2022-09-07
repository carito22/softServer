package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasFlujoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasFlujoDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class CuentasFlujoServiceImpl implements CuentasFlujoService {

    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private CuentasFlujoDetalleDao cuentasFlujoDetalleDao;

    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private CuentasFlujoDao cuentasFlujoDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private String mensaje;

    @Override
    public boolean insertarConCuentaFlujo(ConCuentasFlujoTO conCuentasFlujoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ConCuentasFlujo conCuentasFlujo = cuentasFlujoDao.obtenerPorId(ConCuentasFlujo.class, new ConCuentasFlujoPK(conCuentasFlujoTO.getFluCodigo(), conCuentasFlujoTO.getCuentaCodigo()));
        if (conCuentasFlujo == null) {
            susClave = conCuentasFlujoTO.getCuentaCodigo();
            susDetalle = "Se insertó cuenta " + conCuentasFlujoTO.getCuentaCodigo() + " --> " + conCuentasFlujoTO.getCuentaDetalle();
            susSuceso = "INSERT";
            susTabla = "con_cuentas_flujo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasFlujoTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaSistema());
            conCuentasFlujo = ConversionesContabilidad.convertirConCuentasFlujoTO_ConCuentasFlujo(conCuentasFlujoTO);
            comprobar = cuentasFlujoDao.insertarConCuentaFlujo(conCuentasFlujo, sisSuceso);
        }
        return comprobar;
    }

    private boolean modificarEstadoContablesFlujoHijos(ConCuentasFlujoTO conCuentasFlujoTO, SisInfoTO sisInfoTO)
            throws Exception {
        List<ConCuentasFlujoTO> listaCuentasFlujoTO = cuentasDao
                .getListaConCuentasFlujoTO(conCuentasFlujoTO.getFluCodigo());
        ConCuentasFlujo conCuentasFlujo = null;
        List<ConCuentasFlujo> listaCuentasFlujo = new ArrayList<ConCuentasFlujo>();

        listaCuentasFlujo.clear();
        int contador = 0;
        int indice = 0;
        for (ConCuentasFlujoTO conCuentasFlujoTO1 : listaCuentasFlujoTO) {
            if (conCuentasFlujoTO1.getCuentaCodigo().equals(conCuentasFlujoTO.getCuentaCodigo())) {
                if (((conCuentasFlujoTO1.getCuentaActivo() == true) && (conCuentasFlujoTO.getCuentaActivo() == false))
                        || ((conCuentasFlujoTO1.getCuentaActivo() == false)
                        && (conCuentasFlujoTO.getCuentaActivo() == true))) {
                    ConCuentasFlujo conCuentasFlujoAux1 = cuentasFlujoDao.obtenerPorId(ConCuentasFlujo.class,
                            new ConCuentasFlujoPK(conCuentasFlujoTO.getFluCodigo().trim(),
                                    conCuentasFlujoTO.getCuentaCodigo().trim()));

                    conCuentasFlujo = new ConCuentasFlujo();
                    conCuentasFlujo.setConCuentasFlujoPK(new ConCuentasFlujoPK(conCuentasFlujoTO.getFluCodigo(),
                            conCuentasFlujoTO.getCuentaCodigo()));
                    conCuentasFlujo.setFluDetalle(conCuentasFlujoTO.getCuentaDetalle().trim());
                    conCuentasFlujo.setFluActivo(conCuentasFlujoTO.getCuentaActivo());
                    conCuentasFlujo.setUsrCodigo(conCuentasFlujoAux1.getUsrCodigo());
                    conCuentasFlujo.setUsrEmpresa(conCuentasFlujoAux1.getUsrEmpresa());
                    conCuentasFlujo.setUsrFechaInserta(conCuentasFlujoAux1.getUsrFechaInserta());
                    listaCuentasFlujo.add(conCuentasFlujo);
                    conCuentasFlujo = null;
                    for (int i = contador + 1; i < listaCuentasFlujoTO.size(); i++) {
                        indice = listaCuentasFlujoTO.get(i).getCuentaCodigo()
                                .lastIndexOf(conCuentasFlujoTO.getCuentaCodigo());
                        if (indice >= 0) {
                            conCuentasFlujo = new ConCuentasFlujo();
                            ConCuentas conCuentasAux2 = cuentasDao.obtenerPorId(ConCuentas.class,
                                    new ConCuentasPK(listaCuentasFlujoTO.get(i).getFluCodigo().trim(),
                                            listaCuentasFlujoTO.get(i).getCuentaCodigo().trim()));

                            conCuentasFlujo.setConCuentasFlujoPK(
                                    new ConCuentasFlujoPK(listaCuentasFlujoTO.get(i).getFluCodigo(),
                                            listaCuentasFlujoTO.get(i).getCuentaCodigo()));
                            conCuentasFlujo.setFluDetalle(listaCuentasFlujoTO.get(i).getCuentaDetalle().trim());
                            conCuentasFlujo.setFluActivo(conCuentasFlujoTO.getCuentaActivo());
                            conCuentasFlujo.setUsrCodigo(conCuentasAux2 == null ? ""
                                    : (conCuentasAux2.getUsrCodigo() == null ? "" : conCuentasAux2.getUsrCodigo()));
                            conCuentasFlujo.setUsrEmpresa(conCuentasAux2 == null ? ""
                                    : (conCuentasAux2.getUsrEmpresa() == null ? "" : conCuentasAux2.getUsrEmpresa()));
                            conCuentasFlujo.setUsrFechaInserta(conCuentasAux2 == null ? new Date()
                                    : (conCuentasAux2.getUsrFechaInserta() == null ? new Date()
                                    : conCuentasAux2.getUsrFechaInserta()));
                            listaCuentasFlujo.add(conCuentasFlujo);
                            conCuentasFlujo = null;
                        } else {
                            break;
                        }
                    }
                }
            }
            contador++;
        }

        boolean modifico = false;
        if (listaCuentasFlujo.size() > 0) {
            SisSuceso sisSuceso = Suceso.crearSisSuceso("con_cuentas",
                    listaCuentasFlujo.get(0).getConCuentasFlujoPK().getFluCodigo(), "UPDATE",
                    "Se modificó y desactivo a la cuenta contable" + " con codigo "
                    + listaCuentasFlujo.get(0).getConCuentasFlujoPK().getFluCodigo()
                    + " y a sus respectivos descendientes",
                    sisInfoTO);
            modifico = cuentasFlujoDao.modificarListaConCuentaFlujo(listaCuentasFlujo, sisSuceso);
        } else {
            modifico = false;
        }
        return modifico;
    }

    @Override
    public boolean modificarConCuentaFlujo(ConCuentasFlujoTO conCuentasFlujoTO, String codigoCambiarLlave,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ConCuentasFlujo conCuentasFlujoAux = null;
        if (codigoCambiarLlave.trim().isEmpty()) {
            conCuentasFlujoAux = cuentasFlujoDao.obtenerPorId(ConCuentasFlujo.class, new ConCuentasFlujoPK(
                    conCuentasFlujoTO.getFluCodigo().trim(), conCuentasFlujoTO.getCuentaCodigo().trim()));
        } else {
            conCuentasFlujoAux = cuentasFlujoDao.obtenerPorId(ConCuentasFlujo.class,
                    new ConCuentasFlujoPK(conCuentasFlujoTO.getFluCodigo().trim(), codigoCambiarLlave));
        }

        if (conCuentasFlujoAux != null && codigoCambiarLlave.trim().isEmpty()) {
            boolean prueba = modificarEstadoContablesFlujoHijos(conCuentasFlujoTO, sisInfoTO);
            if (!prueba) {
                susClave = conCuentasFlujoTO.getCuentaCodigo();
                susDetalle = "Se modificó a la cuenta contable con codigo " + conCuentasFlujoTO.getCuentaCodigo();
                susSuceso = "UPDATE";
                susTabla = "con_cuentas";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                conCuentasFlujoTO.setUsrInsertaCuenta(conCuentasFlujoAux.getUsrCodigo());
                conCuentasFlujoTO.setUsrFechaInsertaCuenta(
                        UtilsValidacion.fecha(conCuentasFlujoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                ConCuentasFlujo conCuentasFlujo = ConversionesContabilidad
                        .convertirConCuentasFlujoTO_ConCuentasFlujo(conCuentasFlujoTO);
                comprobar = cuentasFlujoDao.modificarConCuentaFlujo(conCuentasFlujo, sisSuceso);
            } else {
                comprobar = true;
            }

        } else if (conCuentasFlujoAux != null && !codigoCambiarLlave.trim().isEmpty()) {
            boolean prueba = modificarEstadoContablesFlujoHijos(conCuentasFlujoTO, sisInfoTO);
            if (!prueba) {
                susClave = conCuentasFlujoTO.getCuentaCodigo();
                susDetalle = "Se modificó la cuenta contable con codigo " + codigoCambiarLlave;
                susSuceso = "UPDATE";
                susTabla = "con_cuentas";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                        sisInfoTO);
                conCuentasFlujoTO.setUsrInsertaCuenta(conCuentasFlujoAux.getUsrCodigo());
                conCuentasFlujoTO.setUsrFechaInsertaCuenta(
                        UtilsValidacion.fecha(conCuentasFlujoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                ConCuentasFlujo conCuentasFlujo = ConversionesContabilidad
                        .convertirConCuentasFlujoTO_ConCuentasFlujo(conCuentasFlujoTO);
                if (conCuentasFlujoAux.getConCuentasFlujoPK().getFluCodigo()
                        .equals(conCuentasFlujo.getConCuentasFlujoPK().getFluCodigo())) {
                    comprobar = cuentasFlujoDao.modificarConCuentaFlujo(conCuentasFlujo, sisSuceso);
                } else {
                    comprobar = cuentasFlujoDao.modificarConCuentaFlujoLlavePrincipal(conCuentasFlujoAux,
                            conCuentasFlujo, sisSuceso);
                }
            } else {
                comprobar = true;
            }
        } else if (conCuentasFlujoAux == null && codigoCambiarLlave.trim().isEmpty()) {
            comprobar = false;
        } else if (conCuentasFlujoAux == null && !codigoCambiarLlave.trim().isEmpty()) {
            boolean prueba = modificarEstadoContablesFlujoHijos(conCuentasFlujoTO, sisInfoTO);
            if (!prueba) {
                susClave = conCuentasFlujoTO.getCuentaCodigo();
                susDetalle = "Se modificó a la cuenta contable con codigo " + codigoCambiarLlave
                        + " por el código " + conCuentasFlujoTO.getCuentaCodigo();
                susSuceso = "UPDATE";
                susTabla = "con_cuentas_flujo";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                        sisInfoTO);
                conCuentasFlujoTO.setUsrInsertaCuenta(conCuentasFlujoAux.getUsrCodigo());
                conCuentasFlujoTO.setUsrFechaInsertaCuenta(UtilsValidacion
                        .fecha(conCuentasFlujoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                ConCuentasFlujo conCuentasFlujo = ConversionesContabilidad
                        .convertirConCuentasFlujoTO_ConCuentasFlujo(conCuentasFlujoTO);
                comprobar = cuentasFlujoDao.modificarConCuentaFlujo(conCuentasFlujo, sisSuceso);
            } else {
                comprobar = true;
            }
        }
        return comprobar;
    }

    @Override
    public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa, String buscar) throws Exception {
        return cuentasFlujoDao.getListaBuscarConCuentasFlujoTO(empresa, buscar);
    }

    @Override
    public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(String empresa) throws Exception {
        return cuentasFlujoDao.getListaBuscarConCuentasFlujoTO(empresa);
    }

    @Override
    public String eliminarConCuentaFlujo(ConCuentasFlujoTO conCuentasFlujoTO, SisInfoTO sisInfoTO) throws Exception {
        ConCuentasFlujo conCuentasFlujo = null;
        mensaje = "FOcurrió un error en el lado del Servidor..";
        comprobar = false;
        if (detalleDao.buscarConteoDetalleEliminarCuenta(conCuentasFlujoTO.getFluCodigo(),
                conCuentasFlujoTO.getCuentaCodigo()) == 0) {
            susClave = conCuentasFlujoTO.getCuentaCodigo();
            susDetalle = "Se eliminó a la cuenta contable flujo " + conCuentasFlujoTO.getCuentaDetalle();
            susSuceso = "DELETE";
            susTabla = "con_cuentas_flujo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasFlujo = ConversionesContabilidad
                    .convertirConCuentasFlujoTO_ConCuentasFlujo(conCuentasFlujoTO);
            comprobar = cuentasFlujoDao.eliminarConCuentaFlujo(conCuentasFlujo, sisSuceso);
            if (comprobar) {
                mensaje = "TSe eliminó correctamente la cuenta contable de flujo..";
            } else {
                mensaje = "FLa cuenta contable de flujo tiene problemas..";
            }
        } else {
            mensaje = "FLa cuenta contable de flujo tiene movimientos..";
        }
        return mensaje;
    }

    @Override
    public String eliminarConCuentaFlujoDetalle(ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO, SisInfoTO sisInfoTO)
            throws Exception {
        ConCuentasFlujoDetalle conCuentasFlujoDetalle = null;
        mensaje = "FOcurrió un error en el lado del Servidor..";
        comprobar = false;
        ConCuentasFlujo conCuentasFlujo = cuentasFlujoDao.obtenerPorId(ConCuentasFlujo.class, new ConCuentasFlujoPK(
                conCuentasFlujoDetalleTO.getFluEmpresa(), conCuentasFlujoDetalleTO.getFluCodigo()));
        if (conCuentasFlujo != null) {
            susClave = conCuentasFlujoDetalleTO.getCtaCodigo();
            susDetalle = "Se eliminó a la cuenta flujo " + conCuentasFlujoDetalleTO.getDetCuentaFlujo();
            susSuceso = "DELETE";
            susTabla = "con_cuentas_flujo_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasFlujoDetalle = ConversionesContabilidad
                    .convertirConCuentasFlujoDetalleTO_ConCuentasFlujoDetalle(conCuentasFlujoDetalleTO);
            conCuentasFlujoDetalle.setConCuentasFlujo(conCuentasFlujo);
            comprobar = cuentasFlujoDetalleDao.eliminarConCuentaFlujoDetalle(conCuentasFlujoDetalle, sisSuceso);
            if (comprobar) {
                mensaje = "TSe eliminó correctamente la cuenta contable de flujo detalle..";
            } else {
                mensaje = "FLa cuenta contable de flujo detalle tiene problemas..";
            }
        } else {
            mensaje = "FLa cuenta contable de flujo detalle tiene movimientos..";
        }
        return mensaje;
    }

}
