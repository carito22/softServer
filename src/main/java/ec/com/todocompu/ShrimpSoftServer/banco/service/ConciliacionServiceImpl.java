package ec.com.todocompu.ShrimpSoftServer.banco.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoCuentaDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.ChequeDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.ConciliacionDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.NumberToLetterConverter;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuenta;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuentaPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConciliacionServiceImpl implements ConciliacionService {

    @Autowired
    private ConciliacionDao conciliacionDao;
    @Autowired
    private BancoCuentaDao cuentaDao;
    @Autowired
    private ChequeDao chequeDao;
    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GenericoDao<BanConciliacionDatosAdjuntos, Integer> banConciliacionDatosAdjuntosDao;
    private boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String getBanConciliacionFechaHasta(String empresa, String cuenta) throws Exception {
        return conciliacionDao.getBanConciliacionFechaHasta(empresa, cuenta);
    }

    @Override
    public Boolean conciliacionHasta(String empresa, String hasta, String cuenta) throws Exception {
        return conciliacionDao.conciliacionHasta(empresa, hasta, cuenta);
    }

    @Override
    public boolean conciliacionPendiente(String empresa, String cuentaContable) throws Exception {
        return conciliacionDao.conciliacionPendiente(empresa, cuentaContable);
    }

    @Override
    public List<BanListaConciliacionTO> getBanListaConciliacionTO(String empresa, String buscar) throws Exception {
        return conciliacionDao.getBanListaConciliacionTO(empresa, buscar);
    }

    @Override
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaDebitoTO(String empresa, String cuenta, String codigo, String hasta) throws Exception {
        return conciliacionDao.getBanListaConciliacionBancariaDebitoTO(empresa, cuenta, codigo, hasta);
    }

    @Override
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaCreditoTO(String empresa, String cuenta, String codigo, String hasta) throws Exception {
        return conciliacionDao.getBanListaConciliacionBancariaCreditoTO(empresa, cuenta, codigo, hasta);
    }

    @Override
    public String accionBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanChequeTO> banChequeTOs, List<BanConciliacionDatosAdjuntos> listadoImagenes, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        mensaje = "";
        BanCuenta banCuenta = cuentaDao.obtenerPorId(BanCuenta.class, new BanCuentaPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable()));//BUSCANDO banCuenta
        String estadoPeriodo = periodoService.estadoPeriodo(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcHasta());
        if (estadoPeriodo.isEmpty()) {
            if (banCuenta != null) {
                SisSuceso sisSuceso = obtenerSuceso(banConciliacionTO, accion, sisInfoTO);
                BanConciliacion banConciliacion = ConversionesBanco.convertirBanConciliacionTO_BanConciliacion(banConciliacionTO);//CREANDO BanConciliacion
                banConciliacion.setBanCuenta(cuentaDao.obtenerPorId(BanCuenta.class, new BanCuentaPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable())));
                List<BanCheque> listaDeCheques = listaCheques(banChequeTOs);
                switch (accion) {
                    case 'E':
                        if (conciliacionDao.obtenerPorId(BanConciliacion.class, new BanConciliacionPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo())) != null) {
                            String fechaHasta = conciliacionDao.getBanConciliacionFechaHasta(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable());//BUSCANDO fecha hasta
                            if (banConciliacionTO.getConcHasta().equals(fechaHasta)) {
                                banConciliacion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                                comprobar = conciliacionDao.accionBanConciliacion(banConciliacion, listaDeCheques, sisSuceso, accion);
                            } else {
                                mensaje = "FNo se puede eliminar porque no es la última conciliación...";
                            }
                        } else {
                            mensaje = "FNo se encuentra la conciliación...";
                        }
                        break;
                    case 'M':
                        if (conciliacionDao.obtenerPorId(BanConciliacion.class, new BanConciliacionPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo())) != null) {
                            banConciliacion.setUsrFechaInserta(conciliacionDao.obtenerPorId(BanConciliacion.class, new BanConciliacionPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo())).getUsrFechaInserta());
                            comprobar = conciliacionDao.accionBanConciliacion(banConciliacion, listaDeCheques, sisSuceso, accion);
                        } else {
                            mensaje = "FNo se encuentra la conciliación...";
                        }
                        break;
                    case 'I':
                        //VERIFICAR Conciliacion
                        if (conciliacionDao.conciliacionHasta(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcHasta(), banConciliacionTO.getConcCuentaContable())) {
                            if (conciliacionDao.
                                    obtenerPorId(BanConciliacion.class, new BanConciliacionPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo())) == null) {
                                //BUSCANDO existencia BanCuenta
                                if (cuentaDao.obtenerPorId(BanCuenta.class, new BanCuentaPK(banConciliacionTO.getConcEmpresa(), banConciliacionTO.getConcCuentaContable())) != null) {
                                    banConciliacion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                                    comprobar = conciliacionDao.accionBanConciliacion(banConciliacion, listaDeCheques, sisSuceso, accion);
                                } else {
                                    mensaje = "FLa cuenta contable para la Conciliación no existe...";
                                }
                            } else {
                                mensaje = "FYa existe la Conciliación...";
                            }
                        } else {
                            mensaje = "FLa fecha de conciliación tiene que ser mayor a la última registrada...";
                        }
                        break;
                }
                if (comprobar) {
                    if (accion == 'E') {
                        mensaje = "TLa conciliación se ha eliminado correctamente";
                    }
                    if (accion == 'M') {
                        mensaje = "TLa conciliación se ha modificado correctamente.";
                        actualizarImagenesBanConciliacion(listadoImagenes, banConciliacion.getBanConciliacionPK());
                        if (listaDeCheques != null && listaDeCheques.size() != banChequeTOs.size()) {
                            mensaje = "TLa conciliación se ha modificado, sin embargo, existen inconsistencias; por favor revisar la conciliación. ";
                        }
                    }
                    if (accion == 'I') {
                        mensaje = "TLa conciliación se ha guardado correctamente";
                        actualizarImagenesBanConciliacion(listadoImagenes, banConciliacion.getBanConciliacionPK());
                        if (listaDeCheques != null && listaDeCheques.size() != banChequeTOs.size()) {
                            mensaje = "TLa conciliación se ha modificado, sin embargo, existen inconsistencias; por favor revisar la conciliación. ";
                        }
                    }
                }
            } else {
                mensaje = "FNo se encuentra cuenta contable...";
            }
        } else {
            mensaje = "F" + estadoPeriodo;
        }
        return mensaje;
    }

    public boolean actualizarImagenesBanConciliacion(List<BanConciliacionDatosAdjuntos> listado, BanConciliacionPK pk) throws Exception {
        List<BanConciliacionDatosAdjuntos> listAdjuntosEnLaBase = conciliacionDao.listarImagenesDeBanConciliacion(pk);
        if (listado != null && !listado.isEmpty()) {
            if (listAdjuntosEnLaBase.size() > 0) {
                listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                });
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        banConciliacionDatosAdjuntosDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });
                }
            }
            insertarImagenesBanConciliacion(listado, pk);
        }
        return true;
    }

    public boolean insertarImagenesBanConciliacion(List<BanConciliacionDatosAdjuntos> listado, BanConciliacionPK pk) throws Exception {
        String bucket = sistemaWebServicio.obtenerRutaImagen(pk.getConcEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (BanConciliacionDatosAdjuntos datoAdjunto : listado) {
                if (datoAdjunto.getAdjSecuencial() == null) {
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(datoAdjunto.getAdjClaveArchivo(), datoAdjunto.getImagenString());
                    BanConciliacionDatosAdjuntos invAdjunto = new BanConciliacionDatosAdjuntos();
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "ban_conciliacion/" + pk.getConcCuentaContable() + "/" + pk.getConcCodigo() + "/";
                    invAdjunto.setBanConciliacion(new BanConciliacion(pk));
                    invAdjunto.setAdjTipo(datoAdjunto.getAdjTipo());
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    banConciliacionDatosAdjuntosDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, datoAdjunto.getImagenString(), combo.getValor());
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return true;
    }

    public SisSuceso obtenerSuceso(BanConciliacionTO banConciliacionTO, char accion, SisInfoTO sisInfoTO) {
        susClave = banConciliacionTO.getConcCodigo() + " " + banConciliacionTO.getConcCuentaContable();
        if (accion == 'I') {
            susDetalle = "Se ingresó la conciliación con código " + banConciliacionTO.getConcCodigo() + " y cuenta contable " + banConciliacionTO.getConcCuentaContable();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó la conciliación con código " + banConciliacionTO.getConcCodigo() + " y cuenta contable " + banConciliacionTO.getConcCuentaContable();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó la conciliación con código " + banConciliacionTO.getConcCodigo() + " y cuenta contable " + banConciliacionTO.getConcCuentaContable();
            susSuceso = "DELETE";
        }
        susTabla = "banco.ban_conciliacion";
        return Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
    }

    private List<BanCheque> listaCheques(List<BanChequeTO> banChequeTOs) throws Exception {
        BanCheque banCheque;
        ConDetalle conDetalle;
        List<BanCheque> banCheques = new ArrayList<>(0);
        for (BanChequeTO banChequeTO : banChequeTOs) {
            conDetalle = detalleDao.obtenerPorId(ConDetalle.class, banChequeTO.getChqSecuencia());
            if (conDetalle != null) {
                banCheque = chequeDao.obtenerPorId(BanCheque.class, banChequeTO.getChqSecuencia());
                if (banCheque != null) {
                    if (banCheque.getConcCodigo() == null || banChequeTO.getConcCodigo() == null || banCheque.getConcCodigo().equals(banChequeTO.getConcCodigo())) {
                        banCheque.setConcCodigo(banChequeTO.getConcCodigo());
                        banCheque.setConcCuentaContable(banChequeTO.getConcCuentaContable());
                        banCheque.setConcEmpresa(banChequeTO.getConcEmpresa());
                    } else {
                        throw new GeneralException("Uno o varios movimientos estan marcados en otra conciliación. Secuencial: " + banCheque.getDetSecuencia());
                    }
                } else {
                    banCheque = ConversionesBanco.convertirBanChequeTO_BanCheque(banChequeTO);
                }
                banCheques.add(banCheque);
            }
        }
        return banCheques;
    }

    @Override
    public Map<String, Object> obtenerDatosConciliacion(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigoConciliacion = UtilsJSON.jsonToObjeto(String.class, map.get("codigoConciliacion"));
        String cuentaBanco = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito = UtilsJSON.jsonToList(BanListaConciliacionBancariaTO.class, map.get("listaConciliacionBancariaDebito"));
        String hasta = "";
        List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito = new ArrayList<>();
        BigDecimal chequesEnTransito = new BigDecimal("0.00");
        BigDecimal valorChequesNoConciliadas = new BigDecimal("0.00");
        BigDecimal valorChequesConciliadas = new BigDecimal("0.00");
        Integer cantidadChequesNoConciliadas = 0;
        Integer cantidadChequesConciliadas = 0;
        BigDecimal depositosEnTransito = new BigDecimal("0.00");
        BigDecimal valorDepositosNoConciliadas = new BigDecimal("0.00");
        BigDecimal valorDepositosConciliadas = new BigDecimal("0.00");
        Integer cantidadDepositosNoConciliadas = 0;
        Integer cantidadDepositosConciliadas = 0;

        hasta = getBanConciliacionFechaHasta(empresa, cuentaBanco);
        listaConciliacionBancariaCredito = conciliacionDao.getBanListaConciliacionBancariaCreditoTO(empresa, cuentaBanco, codigoConciliacion, fechaHasta);

        BigDecimal saldoSistema = cuentaDao.getBanValorSaldoSistema(empresa, cuentaBanco, fechaHasta);

        /*CALCULOS DEBITOS*/
        for (int i = 0; i < listaConciliacionBancariaDebito.size(); i++) {
            if (!listaConciliacionBancariaDebito.get(i).getCbConciliado()) {
                valorChequesNoConciliadas = valorChequesNoConciliadas.add(listaConciliacionBancariaDebito.get(i).getCbValor());
                cantidadChequesNoConciliadas++;
            } else {
                valorChequesConciliadas = valorChequesConciliadas.add(listaConciliacionBancariaDebito.get(i).getCbValor());
                cantidadChequesConciliadas++;
            }
        }
        chequesEnTransito = valorChequesNoConciliadas;


        /*CALCULOS CREDITOS*/
        for (int i = 0; i < listaConciliacionBancariaCredito.size(); i++) {
            if (!listaConciliacionBancariaCredito.get(i).getCbConciliado()) {
                valorDepositosNoConciliadas = valorDepositosNoConciliadas.add(listaConciliacionBancariaCredito.get(i).getCbValor());
                cantidadDepositosNoConciliadas++;
            } else {
                valorDepositosConciliadas = valorDepositosConciliadas.add(listaConciliacionBancariaCredito.get(i).getCbValor());
                cantidadDepositosConciliadas++;
            }
        }
        depositosEnTransito = valorDepositosNoConciliadas;

        campos.put("hasta", hasta);
        campos.put("saldoEstadoCuenta", hasta);
        campos.put("chequesEnTransito", chequesEnTransito);
        campos.put("chequesCantidadNoConciliadas", cantidadChequesNoConciliadas);
        campos.put("chequesValorNoConciliadas", valorChequesNoConciliadas);
        campos.put("chequesCantidadConciliadas", cantidadChequesConciliadas);
        campos.put("chequesValorConciliadas", valorChequesConciliadas);

        campos.put("depositosEnTransito", depositosEnTransito);
        campos.put("depositosCantidadNoConciliadas", cantidadDepositosNoConciliadas);
        campos.put("depositosValorNoConciliadas", valorDepositosNoConciliadas);
        campos.put("depositosCantidadConciliadas", cantidadDepositosConciliadas);
        campos.put("depositosValorConciliadas", valorDepositosConciliadas);

        campos.put("saldoSistema", saldoSistema);

        campos.put("listaConciliacionBancariaDebito", listaConciliacionBancariaDebito);
        campos.put("listaConciliacionBancariaCredito", listaConciliacionBancariaCredito);

        return campos;
    }

    @Override
    public String guardarBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, List<BanConciliacionDatosAdjuntos> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        List<BanChequeTO> listaBanChequeTOs = obetnerListadoBancoChequeTO(banConciliacionTO, listaConciliacionBancariaDebito, listaConciliacionBancariaCredito, sisInfoTO);
        return accionBanConciliacionTO(banConciliacionTO, listaBanChequeTOs, listadoImagenes, 'I', sisInfoTO);
    }

    @Override
    public String modificarBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, List<BanConciliacionDatosAdjuntos> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        List<BanChequeTO> listaBanChequeTOs = obetnerListadoBancoChequeTO(banConciliacionTO, listaConciliacionBancariaDebito, listaConciliacionBancariaCredito, sisInfoTO);
        return accionBanConciliacionTO(banConciliacionTO, listaBanChequeTOs, listadoImagenes, 'M', sisInfoTO);
    }

    @Override
    public String eliminarBanConciliacionTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, SisInfoTO sisInfoTO) throws Exception {
        List<BanChequeTO> listaBanChequeTOs = obetnerListadoBancoChequeTO(banConciliacionTO, listaConciliacionBancariaDebito, listaConciliacionBancariaCredito, sisInfoTO);    
        return accionBanConciliacionTO(banConciliacionTO, listaBanChequeTOs, null, 'E', sisInfoTO);
    }

    public List<BanChequeTO> obetnerListadoBancoChequeTO(BanConciliacionTO banConciliacionTO, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaDebito, List<BanListaConciliacionBancariaTO> listaConciliacionBancariaCredito, SisInfoTO sisInfoTO) throws Exception {
        List<BanChequeTO> listaBanChequeTOs = new ArrayList<>();
        /*DEBITOS*/
        for (int i = 0; i < listaConciliacionBancariaDebito.size(); i++) {
            BanListaConciliacionBancariaTO banListaConciliacionBancariaTO = listaConciliacionBancariaDebito.get(i);
            BanChequeTO banChequeTO;
            BigDecimal valor = new BigDecimal(banListaConciliacionBancariaTO.getCbValor().toPlainString());
            if (banListaConciliacionBancariaTO.getCbConciliado().equals(true)) {
                banChequeTO = new BanChequeTO(
                        banListaConciliacionBancariaTO.getCbSecuencial().longValue(),
                        banListaConciliacionBancariaTO.getCbConcepto(),
                        NumberToLetterConverter.convertNumberToLetter(valor.toPlainString()),
                        "Machala",
                        banConciliacionTO.getConcHasta(), false, false, null, false, null, null, false, null,
                        null, false, null, null, false, null, null, false, sisInfoTO.getEmpresa(),
                        banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo(), null, banListaConciliacionBancariaTO.getCbSecuencial().longValue());
            } else {
                banChequeTO = new BanChequeTO(banListaConciliacionBancariaTO.getCbSecuencial().longValue(),
                        banListaConciliacionBancariaTO.getCbConcepto(),
                        NumberToLetterConverter.convertNumberToLetter(valor.toPlainString()),
                        "Machala",
                        banConciliacionTO.getConcHasta(), false, false, null, false, null, null, false, null,
                        null, false, null, null, false, null, null, false, null, null,
                        null, null, banListaConciliacionBancariaTO.getCbSecuencial().longValue());
            }
            listaBanChequeTOs.add(banChequeTO);
        }
        /*CREDITOS*/
        for (int i = 0; i < listaConciliacionBancariaCredito.size(); i++) {
            BanListaConciliacionBancariaTO banListaConciliacionBancariaTO = listaConciliacionBancariaCredito.get(i);
            BanChequeTO banChequeTO;
            if (banListaConciliacionBancariaTO.getCbConciliado().equals(true)) {
                banChequeTO = new BanChequeTO(banListaConciliacionBancariaTO.getCbSecuencial().longValue(),
                        banListaConciliacionBancariaTO.getCbConcepto(),
                        NumberToLetterConverter.convertNumberToLetter(banListaConciliacionBancariaTO.getCbValor().toPlainString()),
                        "Machala",
                        banConciliacionTO.getConcHasta(), false, false, null, false, null, null, false, null,
                        null, false, null, null, false, null, null, false, sisInfoTO.getEmpresa(),
                        banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo(), null, banListaConciliacionBancariaTO.getCbSecuencial().longValue());
            } else {
                banChequeTO = new BanChequeTO(banListaConciliacionBancariaTO.getCbSecuencial().longValue(),
                        banListaConciliacionBancariaTO.getCbConcepto(),
                        NumberToLetterConverter.convertNumberToLetter(banListaConciliacionBancariaTO.getCbValor().toPlainString()),
                        "Machala",
                        banConciliacionTO.getConcHasta(), false, false, null, false, null, null, false, null,
                        null, false, null, null, false, null, null, false, null, null,
                        null, null, banListaConciliacionBancariaTO.getCbSecuencial().longValue());
            }
            listaBanChequeTOs.add(banChequeTO);
        }
        return listaBanChequeTOs;
    }

    @Override
    public Map<String, Object> obtenerDatosParaConciliacion(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<BanComboBancoTO> bancos = cuentaDao.getBanComboBancoTO(empresa);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        campos.put("bancos", bancos);
        campos.put("fechaActual", fechaActual);
        return campos;
    }

    @Override
    public List<BanConciliacionDatosAdjuntos> listarImagenesDeBanConciliacion(BanConciliacionPK pk) throws Exception {
        return conciliacionDao.listarImagenesDeBanConciliacion(pk);
    }

}
