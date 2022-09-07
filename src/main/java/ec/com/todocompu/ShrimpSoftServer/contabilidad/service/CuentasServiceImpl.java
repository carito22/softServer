package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.Map;

@Service
public class CuentasServiceImpl implements CuentasService {

    @Autowired
    private CuentasDao cuentasDao;

    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ProductoCategoriaDao productoCategoriaDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private String mensaje;

    @Override
    public boolean validarCuentaGrupoDetalle(ConCuentasTO conCuentasTO, SisInfoTO usuario) throws Exception {
        boolean respuesta = false;
        usuario.setEmpresa(conCuentasTO.getEmpCodigo());
        List<ConCuentasTO> listaConCuentasTO = cuentasDao.getListaBuscarConCuentasTO(conCuentasTO.getEmpCodigo(), "", null);
        int cantidadcuenta = conCuentasTO.getCuentaCodigo().length();
        int contador = 0;
        int indice;
        List<ConEstructuraTO> lista = estructuraService.getListaConEstructuraTO(conCuentasTO.getEmpCodigo());
        int cantidadGrupo1 = 0;
        int cantidadGrupo2 = 0;
        int cantidadGrupo3 = 0;
        int cantidadGrupo4 = 0;
        int cantidadGrupo5 = 0;
        int cantidadGrupo6 = 0;
        for (ConEstructuraTO conEstructuraTO : lista) {
            cantidadGrupo1 += conEstructuraTO.getEstGrupo1();
            cantidadGrupo2 += cantidadGrupo1 + conEstructuraTO.getEstGrupo2();
            cantidadGrupo3 += cantidadGrupo2 + conEstructuraTO.getEstGrupo3();
            cantidadGrupo4 += cantidadGrupo3 + conEstructuraTO.getEstGrupo4();
            cantidadGrupo5 += cantidadGrupo4 + conEstructuraTO.getEstGrupo5();
            cantidadGrupo6 += cantidadGrupo5 + conEstructuraTO.getEstGrupo6();
        }
        if (cantidadcuenta == cantidadGrupo1) {
            respuesta = true;
        } else if (cantidadcuenta == cantidadGrupo2) {
            for (ConCuentasTO itemConCuentasTO : listaConCuentasTO) {
                if (itemConCuentasTO.getCuentaCodigo().equalsIgnoreCase(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo1))) {
                    for (int i = contador; i < listaConCuentasTO.size(); i++) {
                        indice = listaConCuentasTO.get(i).getCuentaCodigo().lastIndexOf(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo1));
                        if (indice >= 0) {
                            respuesta = true;
                            break;
                        }
                    }
                }
                contador++;
            }
        } else if (cantidadcuenta == cantidadGrupo3) {
            for (ConCuentasTO itemConCuentasTO : listaConCuentasTO) {
                if (itemConCuentasTO.getCuentaCodigo().equalsIgnoreCase(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo2))) {
                    for (int i = contador; i < listaConCuentasTO.size(); i++) {
                        indice = listaConCuentasTO.get(i).getCuentaCodigo().lastIndexOf(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo2));
                        if (indice >= 0) {
                            respuesta = true;
                            break;
                        }
                    }
                }
                contador++;
            }
        } else if (cantidadcuenta == cantidadGrupo4) {
            for (ConCuentasTO itemConCuentasTO : listaConCuentasTO) {
                if (itemConCuentasTO.getCuentaCodigo().equalsIgnoreCase(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo3))) {
                    for (int i = contador; i < listaConCuentasTO.size(); i++) {
                        indice = listaConCuentasTO.get(i).getCuentaCodigo().lastIndexOf(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo3));
                        if (indice >= 0) {
                            respuesta = true;
                            break;
                        }
                    }
                }
                contador++;
            }
        } else if (cantidadcuenta == cantidadGrupo5) {
            for (ConCuentasTO itemConCuentasTO : listaConCuentasTO) {
                if (itemConCuentasTO.getCuentaCodigo().equalsIgnoreCase(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo4))) {
                    for (int i = contador; i < listaConCuentasTO.size(); i++) {
                        indice = listaConCuentasTO.get(i).getCuentaCodigo().lastIndexOf(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo4));
                        if (indice >= 0) {
                            respuesta = true;
                            break;
                        }
                    }
                }
                contador++;
            }
        } else if (cantidadcuenta == cantidadGrupo6) {
            for (ConCuentasTO itemConCuentasTO : listaConCuentasTO) {
                if (itemConCuentasTO.getCuentaCodigo().equalsIgnoreCase(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo5))) {
                    for (int i = contador; i < listaConCuentasTO.size(); i++) {
                        indice = listaConCuentasTO.get(i).getCuentaCodigo().lastIndexOf(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo5));
                        if (indice >= 0) {
                            respuesta = true;
                            break;
                        }
                    }
                }
                contador++;
            }
        }
        return respuesta;
    }

    @Override
    public ConCuentas obtenerConCuenta(String empresa, String cuenta) throws Exception {
        return cuentasDao.buscarCuentas(empresa, cuenta);
    }

    @Override
    public ConCuentasTO obtenerConCuentaTO(String empresa, String cuenta) throws Exception {
        return cuentasDao.buscarCuentasTO(empresa, cuenta);
    }

    @Override
    public String buscarDetalleContablePadre(String empresa, String codigo) throws Exception {
        String encontro = "";
        List<ConCuentasTO> listaConCuentasTO = getListaBuscarConCuentasTO(empresa, codigo, null);
        for (ConCuentasTO conCuentasTO : listaConCuentasTO) {
            if (conCuentasTO.getCuentaCodigo().equals(codigo)) {
                encontro = conCuentasTO.getCuentaDetalle().trim();
                break;
            } else {
                encontro = "";
            }
        }
        return encontro;
    }

    @Override
    public Map<String, Object> buscarNombresDesdeHastaPadre(String empresa, String codigoDesde, String codigoHasta, SisInfoTO usuario) throws Exception {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        List<ConEstructuraTO> lista = estructuraService.getListaConEstructuraTO(empresa);
        short estGrupo1 = lista.get(0).getEstGrupo1();
        short estGrupo2 = lista.get(0).getEstGrupo2();
        short estGrupo3 = lista.get(0).getEstGrupo3();
        short estGrupo4 = lista.get(0).getEstGrupo4();
        short estGrupo5 = lista.get(0).getEstGrupo5();
        short estGrupo6 = lista.get(0).getEstGrupo6();

        String ctaDesde, nombDesde, ctaHasta, nombHasta;
        /// cuenta Desde
        ctaDesde = codigoDesde.trim().substring(0,
                ((estGrupo2 == 0 ? 0 : estGrupo1) + (estGrupo3 == 0 ? 0 : estGrupo2)
                + (estGrupo4 == 0 ? 0 : estGrupo3) + (estGrupo5 == 0 ? 0 : estGrupo4)
                + (estGrupo6 == 0 ? 0 : estGrupo5)));
        nombDesde = buscarDetalleContablePadre(empresa, ctaDesde);
        /// cuenta hasta
        if (codigoHasta != null) {
            ctaHasta = codigoHasta.trim().substring(0,
                    ((estGrupo2 == 0 ? 0 : estGrupo1) + (estGrupo3 == 0 ? 0 : estGrupo2)
                    + (estGrupo4 == 0 ? 0 : estGrupo3) + (estGrupo5 == 0 ? 0 : estGrupo4)
                    + (estGrupo6 == 0 ? 0 : estGrupo5)));
            nombHasta = buscarDetalleContablePadre(empresa, ctaHasta);
            respuesta.put("nombrePadreHasta", ctaHasta + " | " + nombHasta);
        }
        respuesta.put("nombrePadresDesde", ctaDesde + " | " + nombDesde);

        return respuesta;
    }

    @Override
    public String insertarConCuenta(ConCuentasTO conCuentasTO, SisInfoTO sisInfoTO) throws Exception {
        String resultadoTransaccion = null;
        ConCuentas conCuentas = cuentasDao.obtenerPorId(ConCuentas.class,
                new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
        if (conCuentas == null) {
            susClave = conCuentasTO.getCuentaCodigo();
            susDetalle = "Se insertó cuenta " + conCuentasTO.getCuentaCodigo() + " --> " + conCuentasTO.getCuentaDetalle();
            susSuceso = "INSERT";
            susTabla = "con_cuentas";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            conCuentasTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaSistema());
            conCuentas = ConversionesContabilidad.convertirConCuentasTO_ConCuentas(conCuentasTO);
            if (cuentasDao.insertarConCuenta(conCuentas, sisSuceso)) {
                resultadoTransaccion = "T";
            };
        }
        return resultadoTransaccion;
    }

    @Override
    public Boolean insertarListaConCuentaTO(List<ConCuentasTO> listaConCuentasTO, String empresa, SisInfoTO sisInfoTO) throws Exception {
        Boolean resultadoTransaccion = false;
        String cuentaInvalido = null;
        List<ConEstructuraTO> listaConEstructura = estructuraService.getListaConEstructuraTO(empresa);
        for (ConCuentasTO conCuentasTO : listaConCuentasTO) {
            if (false == this.validarCuentaGrupo(conCuentasTO, listaConCuentasTO, listaConEstructura)) {
                cuentaInvalido = conCuentasTO.getCuentaCodigo();
                break;
            }
        }
        if (cuentaInvalido != null) {
            throw new GeneralException("La cuenta " + cuentaInvalido + " no tiene grupo", "Cuenta detalle sin grupo");
        }
        List<ConCuentas> listadoConCuentas = new java.util.ArrayList();
        List<SisSuceso> listadoSisSuceso = new java.util.ArrayList();
        SisSuceso sisSuceso;
        ConCuentas conCuentas;
        //Se crean las listas de las entidades que seran insertadas
        for (ConCuentasTO conCuentasTO : listaConCuentasTO) {
            susClave = conCuentasTO.getCuentaCodigo();
            susDetalle = "Se insertó cuenta " + conCuentasTO.getCuentaCodigo() + " --> " + conCuentasTO.getCuentaDetalle();
            susSuceso = "INSERT";
            susTabla = "con_cuentas";
            sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaSistema());
            conCuentas = ConversionesContabilidad.convertirConCuentasTO_ConCuentas(conCuentasTO);
            listadoConCuentas.add(conCuentas);
            listadoSisSuceso.add(sisSuceso);
        }
        if (cuentasDao.insertarListadoConCuenta(listadoConCuentas, listadoSisSuceso)) {
            resultadoTransaccion = true;
        }
        return resultadoTransaccion;
    }

    private boolean validarCuentaGrupo(ConCuentasTO conCuentasTO, List<ConCuentasTO> listaConCuentasTO, List<ConEstructuraTO> lista) throws Exception {
        boolean respuesta = false;
        int cantidadcuenta = conCuentasTO.getCuentaCodigo().length();
        int cantidadGrupo1 = 0;
        int cantidadGrupo2 = 0;
        int cantidadGrupo3 = 0;
        int cantidadGrupo4 = 0;
        int cantidadGrupo5 = 0;
        int cantidadGrupo6 = 0;
        for (ConEstructuraTO conEstructuraTO : lista) {
            cantidadGrupo1 += conEstructuraTO.getEstGrupo1();
            cantidadGrupo2 += cantidadGrupo1 + conEstructuraTO.getEstGrupo2();
            cantidadGrupo3 += cantidadGrupo2 + conEstructuraTO.getEstGrupo3();
            cantidadGrupo4 += cantidadGrupo3 + conEstructuraTO.getEstGrupo4();
            cantidadGrupo5 += cantidadGrupo4 + conEstructuraTO.getEstGrupo5();
            cantidadGrupo6 += cantidadGrupo5 + conEstructuraTO.getEstGrupo6();
        }
        if (cantidadcuenta == cantidadGrupo1) {
            respuesta = true;
        } else {
            for (ConCuentasTO itemConCuentasTO : listaConCuentasTO) {
                int cantidadGrupo = cantidadGrupo1;
                if (cantidadcuenta == cantidadGrupo3) {
                    cantidadGrupo = cantidadGrupo2;
                } else if (cantidadcuenta == cantidadGrupo4) {
                    cantidadGrupo = cantidadGrupo3;
                } else if (cantidadcuenta == cantidadGrupo5) {
                    cantidadGrupo = cantidadGrupo4;
                } else if (cantidadcuenta == cantidadGrupo6) {
                    cantidadGrupo = cantidadGrupo5;
                }
                //Si encuentra el grupo
                if (itemConCuentasTO.getCuentaCodigo().equalsIgnoreCase(conCuentasTO.getCuentaCodigo().substring(0, cantidadGrupo))) {
                    respuesta = true;
                    break;
                }
            }
        }
        return respuesta;
    }

    @Override
    public String modificarConCuenta(ConCuentasTO conCuentasTO, String codigoCambiarLlave, SisInfoTO sisInfoTO)
            throws Exception {
        String resultadoTransaccion = null;
        ConCuentas conCuentasAux = null;
        if (codigoCambiarLlave.trim().isEmpty()) {
            conCuentasAux = cuentasDao.obtenerPorId(ConCuentas.class,
                    new ConCuentasPK(conCuentasTO.getEmpCodigo().trim(), conCuentasTO.getCuentaCodigo().trim()));
        } else {
            conCuentasAux = cuentasDao.obtenerPorId(ConCuentas.class,
                    new ConCuentasPK(conCuentasTO.getEmpCodigo().trim(), codigoCambiarLlave));
        }
        if (conCuentasAux != null && codigoCambiarLlave.trim().isEmpty()) {
            boolean prueba = moficiarEstadoContablesHijos(conCuentasTO, sisInfoTO);
            if (!prueba) {
                susClave = conCuentasTO.getCuentaCodigo();
                susDetalle = "Se modificó a la cuenta contable con codigo " + conCuentasTO.getCuentaCodigo();
                susSuceso = "UPDATE";
                susTabla = "con_cuentas";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                conCuentasTO.setUsrInsertaCuenta(conCuentasAux.getUsrCodigo());
                conCuentasTO.setUsrFechaInsertaCuenta(
                        UtilsValidacion.fecha(conCuentasAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                ConCuentas conCuentas = ConversionesContabilidad.convertirConCuentasTO_ConCuentas(conCuentasTO);
                if (cuentasDao.modificarConCuenta(conCuentas, sisSuceso)) {
                    resultadoTransaccion = "T";
                };
            } else {
                resultadoTransaccion = "T";
            }

        } else if (conCuentasAux != null && !codigoCambiarLlave.trim().isEmpty()) {
            boolean prueba = moficiarEstadoContablesHijos(conCuentasTO, sisInfoTO);
            if (!prueba) {
                susClave = conCuentasTO.getCuentaCodigo();
                susDetalle = "Se modificó a la cuenta contable con codigo " + codigoCambiarLlave
                        + " por el código " + conCuentasTO.getCuentaCodigo();
                susSuceso = "UPDATE";
                susTabla = "con_cuentas";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                        sisInfoTO);
                conCuentasTO.setUsrInsertaCuenta(conCuentasAux.getUsrCodigo());
                conCuentasTO.setUsrFechaInsertaCuenta(
                        UtilsValidacion.fecha(conCuentasAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                ConCuentas conCuentas = ConversionesContabilidad.convertirConCuentasTO_ConCuentas(conCuentasTO);
                if (conCuentasAux.getConCuentasPK().getCtaCodigo().equals(conCuentas.getConCuentasPK().getCtaCodigo())) {
                    resultadoTransaccion = cuentasDao.modificarConCuenta(conCuentas, sisSuceso) ? "T" : "F";
                } else {
                    resultadoTransaccion = cuentasDao.modificarConCuentaLlavePrincipal(conCuentasAux, conCuentas, sisSuceso) ? "T" : "F";
                }
            } else {
                resultadoTransaccion = "T";
            }
        } else if (conCuentasAux == null && codigoCambiarLlave.trim().isEmpty()) {
            comprobar = false;
        } else if (conCuentasAux == null && !codigoCambiarLlave.trim().isEmpty()) {
            boolean prueba = moficiarEstadoContablesHijos(conCuentasTO, sisInfoTO);
            if (!prueba) {
                susClave = conCuentasTO.getCuentaCodigo();
                susDetalle = "Se modificó a la cuenta contable con codigo " + codigoCambiarLlave
                        + " por el código " + conCuentasTO.getCuentaCodigo();
                susSuceso = "UPDATE";
                susTabla = "con_cuentas";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                        sisInfoTO);
                conCuentasTO.setUsrInsertaCuenta(conCuentasAux.getUsrCodigo());
                conCuentasTO.setUsrFechaInsertaCuenta(
                        UtilsValidacion.fecha(conCuentasAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                ConCuentas conCuentas = ConversionesContabilidad
                        .convertirConCuentasTO_ConCuentas(conCuentasTO);
                resultadoTransaccion = cuentasDao.modificarConCuenta(conCuentas, sisSuceso) ? "T" : "F";;
            } else {
                resultadoTransaccion = "T";
            }
        }
        return resultadoTransaccion;
    }

    @Override
    public MensajeTO modificarEstadoConCuenta(ConCuentasTO conCuentasTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        ConCuentas conCuentasLocal = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
        if (conCuentasLocal != null) {
            susClave = conCuentasLocal.getConCuentasPK().getCtaCodigo();
            susDetalle = "Se cambió el estado de la cuenta contable: " + conCuentasLocal.getCtaDetalle() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "contabilidad.con_cuentas";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasLocal.setCtaActivo(estado);
            comprobar = cuentasDao.modificarConCuenta(conCuentasLocal, sisSuceso);
            if (comprobar) {
                mensajeTO.setMensaje("T");
            }
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarEstadoBloqueoConCuenta(ConCuentasTO conCuentasTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        comprobar = false;
        ConCuentas conCuentasLocal = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
        if (conCuentasLocal != null) {
            susClave = conCuentasLocal.getConCuentasPK().getCtaCodigo();
            susDetalle = "Se" + (estado ? " bloqueó " : " desbloqueó ") + "el estado de la cuenta contable: " + conCuentasLocal.getCtaDetalle() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "contabilidad.con_cuentas";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasLocal.setCtaBloqueada(estado);
            comprobar = cuentasDao.modificarConCuenta(conCuentasLocal, sisSuceso);
            if (comprobar) {
                mensajeTO.setMensaje("T");
            };
        }
        return mensajeTO;
    }

    @Override
    public String eliminarConCuenta(ConCuentasTO conCuentasTO, SisInfoTO sisInfoTO) throws Exception {
        ConCuentas conCuentas = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
        String resultadoTransaccion = null;
        if (conCuentas != null) {
            if (detalleDao.buscarConteoDetalleEliminarCuenta(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()) == 0) {
                susClave = conCuentasTO.getCuentaCodigo();
                susDetalle = "Se eliminó a la cuenta contable " + conCuentasTO.getCuentaDetalle();
                susSuceso = "DELETE";
                susTabla = "con_cuentas";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                if (cuentasDao.eliminarConCuenta(conCuentas, sisSuceso)) {
                    resultadoTransaccion = "TSe eliminó correctamente la cuenta contable..";
                } else {
                    resultadoTransaccion = "FLa cuenta contable tiene problemas..";
                }
            } else {
                resultadoTransaccion = "FLa cuenta contable tiene movimientos..";
            }
        } else {
            resultadoTransaccion = "FLa cuenta contable ya no está disponible.";
        }
        return resultadoTransaccion;
    }

    private boolean moficiarEstadoContablesHijos(ConCuentasTO conCuentasTO, SisInfoTO sisInfoTO) throws Exception {
        List<ConCuentasTO> listaCuentasTO = cuentasDao.getListaConCuentasTO(conCuentasTO.getEmpCodigo());
        ConCuentas conCuentas = null;
        List<ConCuentas> listaCuentas = new ArrayList<ConCuentas>();
        listaCuentas.clear();
        int contador = 0;
        int indice = 0;
        for (ConCuentasTO conCuentasTO1 : listaCuentasTO) {
            if (conCuentasTO1.getCuentaCodigo().equals(conCuentasTO.getCuentaCodigo())) {
                if (((conCuentasTO1.getCuentaActivo() == true) && (conCuentasTO.getCuentaActivo() == false))
                        || ((conCuentasTO1.getCuentaActivo() == false) && (conCuentasTO.getCuentaActivo() == true))) {

                    ConCuentas conCuentasAux1 = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                            conCuentasTO.getEmpCodigo().trim(), conCuentasTO.getCuentaCodigo().trim()));

                    conCuentas = new ConCuentas();
                    conCuentas.setConCuentasPK(
                            new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
                    conCuentas.setCtaDetalle(conCuentasTO.getCuentaDetalle().trim());
                    conCuentas.setCtaActivo(conCuentasTO.getCuentaActivo());
                    conCuentas.setCtaBloqueada(conCuentasTO.getCuentaBloqueada());
                    conCuentas.setCtaRelacionada(conCuentasTO.getCtaRelacionada() != null ? conCuentasTO.getCtaRelacionada() : false);
                    conCuentas.setCtaGrupoComparativo(conCuentasTO.getCtaGrupoComparativo());
                    conCuentas.setCtaDetalleComparativo(conCuentasTO.getCtaDetalleComparativo());
                    conCuentas.setUsrCodigo(conCuentasAux1.getUsrCodigo());
                    conCuentas.setUsrEmpresa(conCuentasAux1.getUsrEmpresa());
                    conCuentas.setUsrFechaInserta(conCuentasAux1.getUsrFechaInserta());
                    listaCuentas.add(conCuentas);
                    conCuentas = null;
                    for (int i = contador + 1; i < listaCuentasTO.size(); i++) {
                        indice = listaCuentasTO.get(i).getCuentaCodigo().lastIndexOf(conCuentasTO.getCuentaCodigo());
                        if (indice >= 0) {
                            conCuentas = new ConCuentas();
                            ConCuentas conCuentasAux2 = cuentasDao.obtenerPorId(ConCuentas.class,
                                    new ConCuentasPK(listaCuentasTO.get(i).getEmpCodigo().trim(),
                                            listaCuentasTO.get(i).getCuentaCodigo().trim()));

                            conCuentas.setConCuentasPK(new ConCuentasPK(listaCuentasTO.get(i).getEmpCodigo(),
                                    listaCuentasTO.get(i).getCuentaCodigo()));
                            conCuentas.setCtaDetalle(listaCuentasTO.get(i).getCuentaDetalle().trim());
                            conCuentas.setCtaActivo(conCuentasTO.getCuentaActivo());
                            conCuentas.setCtaBloqueada(conCuentasTO.getCuentaBloqueada());
                            conCuentas.setCtaRelacionada(conCuentasTO.getCtaRelacionada() != null ? conCuentasTO.getCtaRelacionada() : false);
                            conCuentas.setCtaGrupoComparativo(conCuentasTO.getCtaGrupoComparativo());
                            conCuentas.setCtaDetalleComparativo(conCuentasTO.getCtaDetalleComparativo());
                            conCuentas.setUsrCodigo(conCuentasAux2.getUsrCodigo());
                            conCuentas.setUsrEmpresa(conCuentasAux2.getUsrEmpresa());
                            conCuentas.setUsrFechaInserta(conCuentasAux2.getUsrFechaInserta());
                            listaCuentas.add(conCuentas);
                            conCuentas = null;
                        } else {
                            break;
                        }
                    }
                }
            }
            contador++;
        }
        boolean modifico = false;
        if (listaCuentas.size() > 0) {
            modifico = cuentasDao.modificarListaConCuenta(listaCuentas);
        } else {
            modifico = false;
        }
        return modifico;
    }

    @Override
    public List<ConCuentasTO> getListaConCuentasTO(String empresa) throws Exception {
        return cuentasDao.getListaConCuentasTO(empresa);
    }

    @Override
    public List<ConCuentasTO> obtenerConCuentasParaSinFormatear(String empresa) throws Exception {
        return cuentasDao.obtenerConCuentasParaSinFormatear(empresa);
    }

    @Override
    public List<ConCuentasTO> getRangoCuentasTO(String empresa, String codigoCuentaDesde, String codigoCuentaHasta,
            int largoCuenta, String usuario, SisInfoTO sisInfoTO) throws Exception {
        List<ConCuentasTO> conCuentasTOs = null;
        conCuentasTOs = cuentasDao.getRangoCuentasTO(empresa, codigoCuentaDesde, codigoCuentaHasta, largoCuenta);
        return conCuentasTOs;
    }

    @Override
    public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(String empresa) throws Exception {
        return cuentasDao.getListaConCuentasFlujoTO(empresa);
    }

    @Override
    public List<ConCuentasTO> getListaBuscarConCuentasTO(String empresa, String buscar, String ctaGrupo)
            throws Exception {
        return cuentasDao.getListaBuscarConCuentasTO(empresa, buscar, ctaGrupo);
    }

    @Override
    public int getConteoCuentasTO(String empresa) throws Exception {
        return cuentasDao.getConteoCuentasTO(empresa);
    }

    @Override
    public String eliminarTodoConCuentas(String empresa) throws Exception {
        return cuentasDao.eliminarTodoConCuentas(empresa);
    }

    @Override
    public List<ConCuentasTO> getListaBuscarConCuentasTO(String empresa, String buscar, String ctaGrupo, boolean cuentaActivo) throws Exception {
        ctaGrupo = ctaGrupo == null ? ctaGrupo : "'" + ctaGrupo + "'";
        String consulta = "";
        if (cuentaActivo) {
            consulta = " AND cta_activo = " + cuentaActivo;
        }
        String sql = "SELECT cta_empresa, cta_codigo, "
                + "REPEAT(' ', CHAR_LENGTH(TRIM(BOTH ' ' FROM cta_codigo))) || cta_detalle , cta_detalle , "
                + "cta_activo, cta_bloqueada, usr_codigo, usr_fecha_inserta, "
                + "cta_grupo_comparativo, cta_detalle_comparativo, cta_relacionada FROM "
                + "contabilidad.con_cuentas WHERE cta_empresa = ('"
                + empresa + "') AND " + "CASE WHEN ('" + buscar + "') = '' THEN TRUE "
                + "ELSE (cta_codigo || UPPER(cta_detalle) LIKE '%' || TRANSLATE('" + buscar
                + "', ' ', '%') || '%') END AND " + "CASE WHEN " + ctaGrupo + " IS NULL THEN TRUE "
                + "ELSE SUBSTRING(cta_codigo,1,LENGTH(" + ctaGrupo + "))=" + ctaGrupo + " END "
                + consulta
                + " ORDER BY cta_codigo;";

        return genericSQLDao.obtenerPorSql(sql, ConCuentasTO.class);
    }

    @Override
    public Map<String, Object> obtenerDatosParaPlanContable(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<ConCuentasTO> conCuentas = getListaConCuentasTO(empresa);
        List<ConEstructuraTO> conEstructura = estructuraService.getListaConEstructuraTO(empresa);
        if (conCuentas != null && !conCuentas.isEmpty()) {
            campos.put("tamanioPlan", conCuentas.size());
        } else {
            campos.put("tamanioPlan", 0);
        }
        campos.put("tamanioEstructura", conEstructura);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerConCuentaProductoCategoria(String empresa, String codigoCategoria) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        InvProductoCategoria cat = productoCategoriaDao.buscarInvProductoCategoria(empresa, codigoCategoria);
        ConCuentas cuentaCompra = null;
        ConCuentas cuentaCostoAutomatico = null;
        ConCuentas cuentaCostoVenta = null;
        ConCuentas cuentaVenta = null;

        if (cat == null) {
            return null;
        } else {
            if (cat.getCatCuentaCompra() != null && !cat.getCatCuentaCompra().equals("")) {
                cuentaCompra = obtenerConCuenta(cat.getInvProductoCategoriaPK().getCatEmpresa(), cat.getCatCuentaCompra());
            }
            if (cat.getCatCuentaCostoVenta() != null && !cat.getCatCuentaCostoVenta().equals("")) {
                cuentaCostoVenta = obtenerConCuenta(cat.getInvProductoCategoriaPK().getCatEmpresa(), cat.getCatCuentaCostoVenta());
            }
            if (cat.getCatCuentaVenta() != null && !cat.getCatCuentaVenta().equals("")) {
                cuentaVenta = obtenerConCuenta(cat.getInvProductoCategoriaPK().getCatEmpresa(), cat.getCatCuentaVenta());
            }
        }
        campos.put("cuentaCompra", cuentaCompra);
        campos.put("cuentaCostoAutomatico", cuentaCostoAutomatico);
        campos.put("cuentaCostoVenta", cuentaCostoVenta);
        campos.put("cuentaVenta", cuentaVenta);
        return campos;
    }

}
