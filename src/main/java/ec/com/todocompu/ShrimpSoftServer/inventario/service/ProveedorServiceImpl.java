package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.DpaEcuadorDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.DpaEcuadorService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PaisService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoIdentificacionService;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.NumeracionVariosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProveedorCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProveedorDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.CedulaRuc;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private GenericReporteService genericReporteService;
    private String modulo = "inventario";
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private ProveedorCategoriaDao proveedorCategoriaDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private DpaEcuadorService dpaEcuadorService;
    @Autowired
    private ProveedorCategoriaService proveedorCategoriaService;
    @Autowired
    private TipoIdentificacionService tipoIdentificacionService;
    @Autowired
    private GenericoDao<InvProveedor, InvProveedorPK> proveedorDaoCriteria;
    @Autowired
    private InvProveedorTransportistaService invProveedorTransportistaService;
    @Autowired
    private BancoDao bancoDao;
    @Autowired
    private DpaEcuadorDao dpaEcuadorDao;
    @Autowired
    private PaisService paisService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public InvProveedor buscarInvProveedor(String empresa, String provCodigo) throws Exception {
        return proveedorDao.buscarInvProveedor(empresa, provCodigo);
    }

    @Override
    public List<InvListaProveedoresTO> getListaProveedoresTO(String empresa, String busqueda, boolean activoProveedor) throws Exception {
        return proveedorDao.getListaProveedoresTO(empresa, busqueda, activoProveedor);
    }

    @Override
    public List<InvProveedorTO> getProveedorTO(String empresa, String codigo) throws Exception {
        return proveedorDao.getProveedorTO(empresa, codigo);
    }

    @Override
    public InvProveedorTO getBuscaCedulaProveedorTO(String empresa, String cedRuc) throws Exception {
        return proveedorDao.getBuscaCedulaProveedorTO(empresa, cedRuc);
    }

    @Override
    public InvProveedorTO getBuscaCedulaProveedorTO(String empresa, String identificacion, char tipo) throws Exception {
        InvProveedorTO proveedor = null;
        if (tipo == 'C' || tipo == 'R') {
            String cedula = tipo == 'C' ? identificacion : identificacion.substring(0, 10);
            String ruc = tipo == 'C' ? identificacion + "001" : identificacion;
            proveedor = getBuscaCedulaProveedorTO(empresa, cedula);
            InvProveedorTO proveedorRepetidoAdic = getBuscaCedulaProveedorTO(empresa, ruc);

            if (proveedorRepetidoAdic != null) {
                proveedor = proveedorRepetidoAdic;
            }
        } else {
            proveedor = getBuscaCedulaProveedorTO(empresa, identificacion);
        }

        return proveedor;
    }

    @Override
    public boolean getProveedorRepetidoCedulaRUC(String empresa, String identificacion, char tipo) throws Exception {
        boolean proveedorRepetidoAdic = true;
        boolean proveedorRepetido = true;
        if (tipo == 'C' || tipo == 'R') {
            String cedula = tipo == 'C' ? identificacion : identificacion.substring(0, 10);
            String ruc = tipo == 'C' ? identificacion + "001" : identificacion;
            proveedorRepetido = getProveedorRepetido("'" + empresa + "'", null, "'" + cedula + "'", null, null);
            proveedorRepetidoAdic = getProveedorRepetido("'" + empresa + "'", null, "'" + ruc + "'", null, null);
        } else {
            proveedorRepetido = getProveedorRepetido("'" + empresa + "'", null, "'" + identificacion + "'", null, null);
        }
        proveedorRepetido = proveedorRepetido || proveedorRepetidoAdic;

        return proveedorRepetido;
    }

    @Override
    public boolean comprobarInvAplicaRetencionIva(String empresa, String codigo) throws Exception {
        return proveedorDao.comprobarInvAplicaRetencionIva(empresa, codigo);
    }

    @Override
    public List<InvFunListadoProveedoresTO> getInvFunListadoProveedoresTO(String empresa, String categoria) throws Exception {
        return proveedorDao.getInvFunListadoProveedoresTO(empresa, categoria);
    }

    @Override
    public Boolean buscarConteoProveedor(String empCodigo, String codigoProveedor) throws Exception {
        return proveedorDao.buscarConteoProveedor(empCodigo, codigoProveedor);
    }

    @Override
    public String insertarInvProveedorTO(InvProveedorTO invProveedorTO, SisInfoTO sisInfoTO, List<InvProveedorTransportista> transportistas) throws Exception {
        String retorno = "";
        String codigoGenerado = "";
        if (invProveedorTO.getProvCodigo() == null) {
            invProveedorTO.setProvCodigo("");
        }
        if (invProveedorTO.getProvCodigo().trim().isEmpty()) {
            invProveedorTO.setProvCodigo(proveedorDao.getInvProximaNumeracionProveedor(invProveedorTO.getEmpCodigo(), invProveedorTO));
            codigoGenerado = invProveedorTO.getProvCodigo();
        }
        InvProveedor invProveedorAux = proveedorDao.buscarInvProveedor(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCodigo());
        if (invProveedorAux == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invProveedorTO.getProvCodigo();
            susDetalle = "Se ingresó el proveedor " + invProveedorTO.getProvRazonSocial() + " con código " + invProveedorTO.getProvCodigo();
            susSuceso = "INSERT";
            susTabla = "inventario.inv_proveedor";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invProveedorTO.setUsrFechaInsertaProveedor(UtilsValidacion.fechaSistema());
            InvProveedor invProveedor = ConversionesInventario.convertirInvProveedorTO_InvProveedor(invProveedorTO);
            ///// BUSCAR POR ID
            if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", null, (invProveedorTO.getProvId().trim().isEmpty() ? null : "'" + invProveedorTO.getProvId() + "'"), null, null)) {
                retorno = "FEl ID prueba del proveedor que ingresó ya existe en los registros.\nIntente ingresando otro ID...";
            } else //// BUSCAR POR NOMBRE , ahora es OPCIONAL
            if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", null, null, (invProveedorTO.getProvNombreComercial() == null ? null : "'" + invProveedorTO.getProvNombreComercial() + "'"), null)) {
                retorno = "FEl Nombre del proveedor ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZONSOCIAL
            if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", null, null, null, (invProveedorTO.getProvRazonSocial().trim().isEmpty() ? null : "'" + invProveedorTO.getProvRazonSocial() + "'"))) {
                retorno = "FLa Razón Social del proveedor ya existe en los registros.\nIntente ingresando otro Razón Social...";
            } else {
                retorno = "T";
            }
            if (retorno.charAt(0) == 'T') {
                InvProveedorCategoria invProveedorCategoria = proveedorCategoriaDao.buscarInvProveedorCategoria(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCategoria());
                if (invProveedorCategoria != null) {
                    invProveedor.setInvProveedorCategoria(invProveedorCategoria);
                    InvNumeracionVarios invNumeracionVariosAux = numeracionVariosDao.obtenerPorId(InvNumeracionVarios.class, invProveedorTO.getEmpCodigo());
                    InvNumeracionVarios invNumeracionVarios = ConversionesInventario.convertirInvNumeracionVarios_InvNumeracionVarios(invNumeracionVariosAux);

                    if (invNumeracionVarios == null && codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios = null;
                    } else if (invNumeracionVarios == null && !codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios = new InvNumeracionVarios(invProveedorTO.getEmpCodigo(), "00000", "00000", codigoGenerado, invProveedorTO.getEmpCodigo());
                    } else if (invNumeracionVarios != null && codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios = null;
                    } else if (invNumeracionVarios != null && !codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios.setNumProveedores(codigoGenerado);
                    }

                    if (proveedorDao.insertarInvProveedor(invProveedor, sisSuceso, invNumeracionVarios)) {
                        if (transportistas != null && !transportistas.isEmpty()) {
                            for (int i = 0; i < transportistas.size(); i++) {
                                transportistas.get(i).setInvProveedor(invProveedor);
                                invProveedorTransportistaService.insertarProveedorTransportista(transportistas.get(i), sisInfoTO);
                            }
                        }
                        retorno = "T<html>Se ha guardado el siguiente proveedor:<br><br>Código: <font size = 5>"
                                + invProveedor.getInvProveedorPK().getProvCodigo().trim()
                                + "</font>.<br>Nombre: <font size = 5>" + invProveedor.getProvRazonSocial().trim()
                                + "</font></html>";
                    } else {
                        retorno = "FHubo un error al ingresar al Proveedor...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            }
        } else {
            retorno = "FEl código del proveedor que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public Map<String, Object> insertarListadoExcelProveedores(List<InvProveedorTO> listaInvProveedorTO, List<AnxProvinciaCantonTO> listaProvincias, SisInfoTO sisInfoTO) throws Exception {

        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<InvProveedorTO> listaProveedores = new ArrayList<>();
        List<String> listadoCiudad = dpaEcuadorDao.getListadoCantonesAllTO();
        for (InvProveedorTO proveedorTO : listaInvProveedorTO) {
            boolean ciudadCorrecta = false;
            boolean insertar = true;
            //verificar si inserta o modifica proveedor
            if (proveedorTO.getProvId() != null && !proveedorTO.getProvId().equals("")) {
                InvProveedorTO invProveedorTO = getBuscaCedulaProveedorTO(sisInfoTO.getEmpresa(), proveedorTO.getProvId());
                if (invProveedorTO != null) {
                    proveedorTO.setProvCodigo(invProveedorTO.getProvCodigo());
                    insertar = false;
                }
            }
            // valido/seteo ciudad proveedor
            for (int i = 0; i < listadoCiudad.size(); i++) {
                if (proveedorTO.getProvCiudad().toUpperCase().equals(listadoCiudad.get(i).toUpperCase())) {
                    proveedorTO.setProvCiudad(listadoCiudad.get(i));
                    ciudadCorrecta = true;
                    break;
                }
            }
            // continuo si proveedor tiene ciudad correcta
            if (!ciudadCorrecta) {
                listaMensajesEnviar.add("F<html>La ciudad ingresada : <br><br>Código:<font size = 5> " + proveedorTO.getProvCiudad() + "</font>, pueda que tenga espacios en el registro o no consta en el sistema.</html>");
            } else {
                // creo invProveedor para insertarlo en base de datos
                InvProveedor invProveedor = ConversionesInventario.convertirInvProveedorTO_InvProveedor(proveedorTO);
                /// PREPARANDO OBJETO SISSUCESO [BEGIN]
                susClave = proveedorTO.getProvCodigo();
                susDetalle = "Se " + (insertar ? "ingresó" : "modificó") + " el proveedor " + proveedorTO.getProvRazonSocial() + " con código " + proveedorTO.getProvCodigo();
                susSuceso = (insertar ? "INSERT" : "UPDATE");
                susTabla = "inventario.inv_proveedor";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PREPARANDO OBJETO SISSUCESO [END]
                InvProveedorCategoria invProveedorCategoria = proveedorCategoriaDao.buscarInvProveedorCategoria(proveedorTO.getEmpCodigo(), proveedorTO.getProvCategoria());
                invProveedor.setInvProveedorCategoria(invProveedorCategoria);
                // se crea objeto InvNumeracionVarios 
                InvNumeracionVarios invNumeracionVariosAux = numeracionVariosDao.obtenerPorId(InvNumeracionVarios.class, proveedorTO.getEmpCodigo());
                InvNumeracionVarios invNumeracionVarios = ConversionesInventario.convertirInvNumeracionVarios_InvNumeracionVarios(invNumeracionVariosAux);
                if (invNumeracionVarios == null && proveedorTO.getProvCodigo().trim().isEmpty()) {
                    invNumeracionVarios = null;
                } else if (invNumeracionVarios == null && !proveedorTO.getProvCodigo().trim().isEmpty()) {
                    invNumeracionVarios = new InvNumeracionVarios(proveedorTO.getEmpCodigo(), "00000", "00000", proveedorTO.getProvCodigo(), proveedorTO.getEmpCodigo());
                } else if (invNumeracionVarios != null && proveedorTO.getProvCodigo().trim().isEmpty()) {
                    invNumeracionVarios = null;
                } else if (invNumeracionVarios != null && !proveedorTO.getProvCodigo().trim().isEmpty()) {
                    invNumeracionVarios.setNumProveedores(proveedorTO.getProvCodigo());
                }
                // inserto provedor en base de datos
                if (insertar) {
                    if (proveedorDao.insertarInvProveedor(invProveedor, sisSuceso, invNumeracionVarios)) {
                        listaProveedores.add(proveedorTO);
                        listaMensajesEnviar.add("TEl proveedor con ID: " + proveedorTO.getProvId() + ", se ha guardado correctamente.");
                    } else {
                        listaMensajesEnviar.add("FEl proveedor con ID: " + proveedorTO.getProvId() + ", NO se ha guardado correctamente.");
                    }
                } else {
                    if (proveedorDao.modificarInvProveedor(invProveedor, sisSuceso)) {
                        listaProveedores.add(proveedorTO);
                        listaMensajesEnviar.add("TEl proveedor con ID: " + proveedorTO.getProvId() + ", se ha modificado correctamente.");
                    } else {
                        listaMensajesEnviar.add("FEl proveedor con ID: " + proveedorTO.getProvId() + ", NO se ha modificado correctamente.");
                    }
                }

            }
        }
        // añado listas a map y retorno
        mapResultadoEnviar = new HashMap<String, Object>();
        mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
        mapResultadoEnviar.put("ProveedoresInsertados", listaProveedores);
        return mapResultadoEnviar;
    }
    
    @Override
    public String modificarInvProveedorTO(InvProveedorTO invProveedorTO, String codigoAnterior, SisInfoTO sisInfoTO, List<InvProveedorTransportista> transportistas) throws Exception {
        String retorno = "";
        InvProveedor invProveedorAux = null;
        if (codigoAnterior.trim().isEmpty()) {
            invProveedorAux = proveedorDao.buscarInvProveedor(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCodigo());
        } else {
            invProveedorAux = proveedorDao.buscarInvProveedor(invProveedorTO.getEmpCodigo(), codigoAnterior);
        }
        if (codigoAnterior.trim().isEmpty()) {
            ///// BUSCAR POR ID
            if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", (invProveedorTO.getProvCodigo().trim().isEmpty() ? null : "'" + invProveedorTO.getProvCodigo() + "'"), (invProveedorTO.getProvId().trim().isEmpty() ? null : "'" + invProveedorTO.getProvId() + "'"), null, null)) {
                retorno = "FEl ID del proveedor que ingresó ya existe en los registros.\nIntente ingresando otro ID...";
            } else //// BUSCAR POR NOMBRE
            if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", (invProveedorTO.getProvCodigo().trim() == null ? null : "'" + invProveedorTO.getProvCodigo() + "'"), null, (invProveedorTO.getProvRazonSocial().trim().isEmpty() ? null : "'" + invProveedorTO.getProvRazonSocial() + "'"), null)) {
                retorno = "FEl Nombre del proveedor ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZONSOCIAL
            if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", (invProveedorTO.getProvCodigo().trim().isEmpty() ? null : "'" + invProveedorTO.getProvCodigo() + "'"), null, null, (invProveedorTO.getProvRazonSocial().trim().isEmpty() ? null : "'" + invProveedorTO.getProvRazonSocial() + "'"))) {
                retorno = "FLa Razón Social del proveedor ya existe en los registros.\nIntente ingresando otro Razón Social...";
            } else {
                retorno = "T";
            }
        } else ///// BUSCAR POR ID
        if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", (codigoAnterior.trim().isEmpty() ? null : "'" + codigoAnterior + "'"), (invProveedorTO.getProvId().trim().isEmpty() ? null : "'" + invProveedorTO.getProvId() + "'"), null, null)) {
            retorno = "FEl ID del proveedorrrrrrr que ingresó ya existe en los registros.\nIntente ingresando otro ID...";
        } else //// BUSCAR POR NOMBRE
        if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", (codigoAnterior.trim().isEmpty() ? null : "'" + codigoAnterior + "'"), null, (invProveedorTO.getProvRazonSocial().trim().isEmpty() ? null : "'" + invProveedorTO.getProvRazonSocial() + "'"), null)) {
            retorno = "FEl Nombre del proveedor ya existe en los registros.\nIntente ingresando otro Nombre...";
        } else //// BUSCAR POR RAZONSOCIAL
        if (proveedorDao.getProveedorRepetido("'" + invProveedorTO.getEmpCodigo() + "'", (codigoAnterior.trim().isEmpty() ? null : "'" + codigoAnterior + "'"), null, null, (invProveedorTO.getProvRazonSocial().trim().isEmpty() ? null : "'" + invProveedorTO.getProvRazonSocial() + "'"))) {
            retorno = "FLa Razón Social del proveedor ya existe en los registros.\nIntente ingresando otro Razón Social...";
        } else {
            retorno = "T";
        }

        if (retorno.charAt(0) == 'T') {
            if (invProveedorAux != null && codigoAnterior.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProveedorTO.getProvCodigo();
                susDetalle = "Se modificó el proveedor " + invProveedorTO.getProvRazonSocial() + " con código " + invProveedorTO.getProvCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_proveedor";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invProveedorTO.setUsrInsertaProveedor(invProveedorAux.getUsrCodigo());
                invProveedorTO.setUsrFechaInsertaProveedor(UtilsValidacion.fecha(invProveedorAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProveedor invProveedor = ConversionesInventario.convertirInvProveedorTO_InvProveedor(invProveedorTO);
                InvProveedorCategoria invProveedorCategoria = proveedorCategoriaDao.buscarInvProveedorCategoria(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCategoria());
                if (invProveedorCategoria != null) {
                    invProveedor.setInvProveedorCategoria(invProveedorCategoria);
                    if (proveedorDao.modificarInvProveedor(invProveedor, sisSuceso)) {
                        modificarProveedorTransportistas(transportistas, invProveedor, sisInfoTO);
                        retorno = "TEl proveedor se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar al proveedor...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else if (invProveedorAux != null && !codigoAnterior.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProveedorTO.getProvCodigo();
                susDetalle = "Se modificó el proveedor " + invProveedorTO.getProvRazonSocial() + " con código " + invProveedorTO.getProvCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_proveedor";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A
                /// ENTITY
                invProveedorTO.setUsrInsertaProveedor(invProveedorAux.getUsrCodigo());
                invProveedorTO.setUsrFechaInsertaProveedor(UtilsValidacion.fecha(invProveedorAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProveedor invProveedor = ConversionesInventario.convertirInvProveedorTO_InvProveedor(invProveedorTO);
                InvProveedorCategoria invProveedorCategoria = proveedorCategoriaDao.buscarInvProveedorCategoria(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCategoria());
                if (invProveedorCategoria != null) {
                    invProveedor.setInvProveedorCategoria(invProveedorCategoria);
                    if (invProveedorAux.getInvProveedorPK().getProvCodigo().equals(invProveedor.getInvProveedorPK().getProvCodigo())) {
                        if (proveedorDao.modificarInvProveedor(invProveedor, sisSuceso)) {
                            modificarProveedorTransportistas(transportistas, invProveedor, sisInfoTO);
                            retorno = "TEl proveedor se modificó correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar al proveedor...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else if (proveedorDao.modificarInvProveedorLlavePrincipal(invProveedorAux, invProveedor, sisSuceso)) {
                        modificarProveedorTransportistas(transportistas, invProveedor, sisInfoTO);
                        retorno = "TEl proveedor se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar al proveedor...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else if (invProveedorAux == null && codigoAnterior.trim().isEmpty()) {
                retorno = "FEl código del proveedor que va a modificar ya no está disponible...\nIntente con otro...";
            } else {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProveedorTO.getProvCodigo();
                susDetalle = "Se modificó el código " + codigoAnterior + " del proveedor " + invProveedorTO.getProvRazonSocial() + " por el código " + invProveedorTO.getProvCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_proveedor";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO
                /// A ENTITY
                invProveedorTO.setUsrInsertaProveedor(invProveedorAux.getUsrCodigo());
                invProveedorTO.setUsrFechaInsertaProveedor(UtilsValidacion.fecha(invProveedorAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProveedor invProveedor = ConversionesInventario.convertirInvProveedorTO_InvProveedor(invProveedorTO);
                InvProveedorCategoria invProveedorCategoria = proveedorCategoriaDao.buscarInvProveedorCategoria(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCategoria());
                if (invProveedorCategoria != null) {
                    invProveedor.setInvProveedorCategoria(invProveedorCategoria);
                    if (invProveedorAux.getInvProveedorPK().getProvCodigo().equals(invProveedor.getInvProveedorPK().getProvCodigo())) {
                        if (proveedorDao.modificarInvProveedor(invProveedor, sisSuceso)) {
                            modificarProveedorTransportistas(transportistas, invProveedor, sisInfoTO);
                            retorno = "TEl proveedor se modificó correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar al proveedor...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else if (proveedorDao.modificarInvProveedorLlavePrincipal(invProveedorAux, invProveedor, sisSuceso)) {
                        modificarProveedorTransportistas(transportistas, invProveedor, sisInfoTO);
                        retorno = "TEl proveedor se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar al proveedor...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            }
        }
        return retorno;
    }

    public void modificarProveedorTransportistas(List<InvProveedorTransportista> transportistas, InvProveedor invProveedor, SisInfoTO sisInfoTO) throws Exception {
        if (transportistas != null) {
            List<InvProveedorTransportista> enBDD = invProveedorTransportistaService.listarTransportistas(invProveedor.getInvProveedorPK().getProvEmpresa(), invProveedor.getInvProveedorPK().getProvCodigo());
            if (transportistas.isEmpty()) {
                if (enBDD != null && !enBDD.isEmpty()) {
                    for (InvProveedorTransportista transportista : enBDD) {
                        invProveedorTransportistaService.eliminar(transportista.getPtSecuencial(), transportista.getPtTransportistaRuc(), invProveedor.getProvIdNumero(), sisInfoTO);
                    }
                }
            } else {
                for (InvProveedorTransportista transportista : transportistas) {
                    if (transportista.getPtSecuencial() > 0) {
                        invProveedorTransportistaService.modificarProveedorTransportista(transportista, sisInfoTO);
                    } else {
                        transportista.setInvProveedor(invProveedor);
                        invProveedorTransportistaService.insertarProveedorTransportista(transportista, sisInfoTO);
                    }
                }
                if (enBDD != null && !enBDD.isEmpty()) {
                    enBDD.removeAll(transportistas);
                    for (InvProveedorTransportista transportista : enBDD) {
                        invProveedorTransportistaService.eliminar(transportista.getPtSecuencial(), transportista.getPtTransportistaRuc(), invProveedor.getProvIdNumero(), sisInfoTO);
                    }
                }
            }
        }
    }

    @Override
    public String eliminarInvProveedorTO(InvProveedorTO invProveedorTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvProveedor invProveedorAux = proveedorDao.buscarInvProveedor(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCodigo());
        if (invProveedorAux != null) {
            if (proveedorDao.buscarConteoProveedor(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCodigo())) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProveedorTO.getProvCodigo();
                susDetalle = "Se eliminó el proveedor " + invProveedorTO.getProvRazonSocial() + " con código " + invProveedorTO.getProvCodigo();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_proveedor";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                invProveedorTO.setUsrInsertaProveedor(invProveedorAux.getUsrCodigo());
                invProveedorTO.setUsrFechaInsertaProveedor(UtilsValidacion.fecha(invProveedorAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProveedor invProveedor = ConversionesInventario.convertirInvProveedorTO_InvProveedor(invProveedorTO);
                invProveedor.setInvProveedorCategoria(invProveedorAux.getInvProveedorCategoria());
                if (proveedorDao.eliminarInvProveedor(invProveedor, sisSuceso)) {
                    retorno = "TEl proveedor se eliminó correctamente...";
                } else {
                    retorno = "FHubo un error al eliminar al Proveedor...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FNo se puede eliminar este Proveedor debido a que tiene movimientos en Inventarios.";
            }
        } else {
            retorno = "FEl proveedor que va a eliminar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public InvProveedor obtenerInvProveedorPorCedulaRuc(String empresa, String ruc) {
        return proveedorDao.obtenerInvProveedorPorCedulaRuc(empresa, ruc);
    }

    /*getListProveedorTO*/
    @Override
    public List<InvProveedorTO> getListProveedorTO(String empresa, String codigoCategoria, boolean inactivos, String busqueda) throws Exception {
        return proveedorDao.getListProveedorTO(empresa, codigoCategoria, inactivos, busqueda);
    }

    @Override
    public boolean getProveedorRepetido(String empresa, String codigo, String id, String nombre, String razonSocial) throws Exception {
        return proveedorDao.getProveedorRepetido(empresa, codigo, id, nombre, razonSocial);
    }

    @Override
    public boolean modificarEstadoInvProveedor(InvProveedorPK invProveedorPK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException {
        InvProveedor invProveedor = proveedorDaoCriteria.obtener(InvProveedor.class, invProveedorPK);
        if (invProveedor != null) {
            susClave = invProveedor.getUsrCodigo();
            susDetalle = "Se modificó el estado de el proveedor " + invProveedor.getProvRazonSocial() + " con código " + invProveedor.getInvProveedorPK().getProvCodigo();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_proveedor";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invProveedor.setProvInactivo(estado);
            proveedorDao.actualizar(invProveedor);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El proveedor ya no esta disponible.");
        }
    }

    /*IMPRIMIR Y EXPORTAR*/
    @Override
    public byte[] generarReporteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportProveedor.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorTO> listado, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            String fila = "";
            String filaCabecera = "";
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de proveedores");
            listaCabecera.add("S");
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                filaCabecera += cabecera.isCheck() ? "S" + cabecera.getHeaderName() + "¬" : "";
            }
            listaCuerpo.add(filaCabecera);
            for (InvProveedorTO invProveedorTO : listado) {//Filas

                fila = "";
                for (int i = 0; i < listadoFiltrado.size(); i++) {//Columnas
                    if (listadoFiltrado.get(i).isCheck()) {
                        switch (listadoFiltrado.get(i).getField()) {
                            case "provCodigo":
                                fila += (invProveedorTO.getProvId() == null ? "B" : "S" + invProveedorTO.getProvId()) + "¬";
                                break;
                            case "provTipoId":
                                fila += (invProveedorTO.getProvTipoId() == null ? "B" : "S" + invProveedorTO.getProvTipoId()) + "¬";
                                break;
                            case "provNombreComercial":
                                fila += (invProveedorTO.getProvNombreComercial() == null ? "B" : "S" + invProveedorTO.getProvNombreComercial()) + "¬";
                                break;
                            case "provRazonSocial":
                                fila += (invProveedorTO.getProvRazonSocial() == null ? "B" : "S" + invProveedorTO.getProvRazonSocial()) + "¬";
                                break;
                            case "provCategoria":
                                String categoriaDesc = invProveedorTO.getProvCategoria();
                                if (invProveedorTO.getProvCategoria() != null && !invProveedorTO.getProvCategoria().equals("")) {
                                    InvProveedorCategoria categoria = proveedorCategoriaService.buscarInvProveedorCategoria(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCategoria());
                                    if (categoria != null) {
                                        categoriaDesc = categoria.getPcDetalle();
                                    }
                                }
                                fila += (invProveedorTO.getProvCategoria() == null ? "B" : "S" + categoriaDesc) + "¬";
                                break;
                            case "provProvincia":
                                fila += (invProveedorTO.getProvProvincia() == null ? "B" : "S" + invProveedorTO.getProvProvincia()) + "¬";
                                break;
                            case "provCiudad":
                                fila += (invProveedorTO.getProvCiudad() == null ? "B" : "S" + invProveedorTO.getProvCiudad()) + "¬";
                                break;
                            case "provZona":
                                fila += (invProveedorTO.getProvZona() == null ? "B" : "S" + invProveedorTO.getProvZona()) + "¬";
                                break;
                            case "provDireccion":
                                fila += (invProveedorTO.getProvDireccion() == null ? "B" : "S" + invProveedorTO.getProvDireccion()) + "¬";
                                break;
                            case "provTelefono":
                                fila += (invProveedorTO.getProvTelefono() == null ? "B" : "S" + invProveedorTO.getProvTelefono()) + "¬";
                                break;
                            case "provCelular":
                                fila += (invProveedorTO.getProvCelular() == null ? "B" : "S" + invProveedorTO.getProvCelular()) + "¬";
                                break;
                            case "provEmail":
                                fila += (invProveedorTO.getProvEmail() == null ? "B" : "S" + invProveedorTO.getProvEmail()) + "¬";
                                break;
                            case "provEmailOrdenCompra":
                                fila += (invProveedorTO.getProvEmailOrdenCompra() == null ? "B" : "S" + invProveedorTO.getProvEmailOrdenCompra()) + "¬";
                                break;
                            case "provObservaciones":
                                fila += (invProveedorTO.getProvObservaciones() == null ? "B" : "S" + invProveedorTO.getProvObservaciones()) + "¬";
                                break;
                            case "provDiasCredito":
                                fila += (invProveedorTO.getProvDiasCredito() == null ? "B" : "I" + invProveedorTO.getProvDiasCredito()) + "¬";
                                break;
                            case "provCupoCredito":
                                fila += (invProveedorTO.getProvCupoCredito() == null ? "B" : "I" + invProveedorTO.getProvCupoCredito()) + "¬";
                                break;
                            case "provInactivo":
                                fila += (invProveedorTO.getProvInactivo() == null ? "SINACTIVO" : "SACTIVO") + "¬";
                                break;
                            default:
                                fila += "";
                                break;
                        }
                    }

                }
                listaCuerpo.add(fila);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProveedorTodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorTO> listado) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de proveedores");
            listaCabecera.add("S");
            listaCuerpo.add("SID" + "¬" + "STipo ID" + "¬" + "SNombre comercial" + "¬" + "SRazón social" + "¬" + "STipo contribuyente" + "¬" + "SProvincia" + "¬" + "SCiudad" + "¬" + "SZona" + "¬" + "SDirección" + "¬" + "STelefono" + "¬" + "SCelular" + "¬" + "SEmail" + "¬" + "SObservaciones" + "¬" + "SEstado");
            for (InvProveedorTO invProveedorTO : listado) {
                String categoriaDesc = invProveedorTO.getProvCategoria();
                if (invProveedorTO.getProvCategoria() != null && !invProveedorTO.getProvCategoria().equals("")) {
                    InvProveedorCategoria categoria = proveedorCategoriaService.buscarInvProveedorCategoria(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCategoria());
                    if (categoria != null) {
                        categoriaDesc = categoria.getPcDetalle();
                    }
                }

                listaCuerpo.add(
                        (invProveedorTO.getProvId() == null ? "B" : "S" + invProveedorTO.getProvId()) + "¬"
                        + (invProveedorTO.getProvTipoId() == null ? "B" : "S" + invProveedorTO.getProvTipoId()) + "¬"
                        + (invProveedorTO.getProvNombreComercial() == null ? "B" : "S" + invProveedorTO.getProvNombreComercial()) + "¬"
                        + (invProveedorTO.getProvRazonSocial() == null ? "B" : "S" + invProveedorTO.getProvRazonSocial()) + "¬"
                        + (invProveedorTO.getProvCategoria() == null ? "B" : "S" + categoriaDesc) + "¬"
                        + (invProveedorTO.getProvProvincia() == null ? "B" : "S" + invProveedorTO.getProvProvincia()) + "¬"
                        + (invProveedorTO.getProvCiudad() == null ? "B" : "S" + invProveedorTO.getProvCiudad()) + "¬"
                        + (invProveedorTO.getProvZona() == null ? "B" : "S" + invProveedorTO.getProvZona()) + "¬"
                        + (invProveedorTO.getProvDireccion() == null ? "B" : "S" + invProveedorTO.getProvDireccion()) + "¬"
                        + (invProveedorTO.getProvTelefono() == null ? "B" : "S" + invProveedorTO.getProvTelefono()) + "¬"
                        + (invProveedorTO.getProvCelular() == null ? "B" : "S" + invProveedorTO.getProvCelular()) + "¬"
                        + (invProveedorTO.getProvEmail() == null ? "B" : "S" + invProveedorTO.getProvEmail()) + "¬"
                        + (invProveedorTO.getProvObservaciones() == null ? "B" : "S" + invProveedorTO.getProvObservaciones()) + "¬"
                        + (invProveedorTO.getProvInactivo() == null ? "SINACTIVO" : "SACTIVO"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosProveedor(Map<String, Object> parametros) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        List<AnxPaisTO> listadoPaises = paisService.getComboAnxPaisTO();

        List<AnxTipoIdentificacionTO> listaIdentificacion = tipoIdentificacionService.getListaAnxTipoIdentificacionTO();
        List<AnxProvinciaCantonTO> listaProvincias = dpaEcuadorService.getComboAnxDpaProvinciaTO();
        List<InvProveedorCategoriaTO> listaCategorias = proveedorCategoriaService.getInvProveedorCategoriaTO(empresa);
        List<ListaBanBancoTO> bancos = bancoDao.getListaBanBancoTO(empresa);
        if (proveedor != null && !proveedor.isEmpty()) {
            List<InvProveedorTransportista> transportistas = invProveedorTransportistaService.listarTransportistas(empresa, proveedor);
            campos.put("transportistas", transportistas);
        }
        List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
        cuentasBancarias.add(new ComboGenericoTO("03", "CTA CORRIENTE"));
        cuentasBancarias.add(new ComboGenericoTO("04", "CTA AHORRO"));
        cuentasBancarias.add(new ComboGenericoTO("05", "CTA VIRTUAL"));
        
        campos.put("listadoAnxPaisTO", listadoPaises);
        campos.put("listaIdentificacion", listaIdentificacion);
        campos.put("listaProvincias", listaProvincias);
        campos.put("listaCategorias", listaCategorias);
        campos.put("bancos", bancos);
        campos.put("cuentasBancarias", cuentasBancarias);

        return campos;
    }

    @Override
    public List<InvProveedorSinMovimientoTO> obtenerProveedoresSinMovimientos(String empresa) throws Exception {
        return proveedorDao.obtenerProveedoresSinMovimientos(empresa);
    }

    @Override
    public Map<String, Object> validarIdentificacionProveedor(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String identificacion = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String razonSocial = UtilsJSON.jsonToObjeto(String.class, map.get("razonSocial"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));

        if (empresa != null && !empresa.equals("")) {
            empresa = "'" + empresa + "'";
        }
        if (id != null && !id.equals("")) {
            id = "'" + id + "'";
        }
        if (codigo != null && !codigo.equals("")) {
            codigo = "'" + codigo + "'";
        }
        if (nombre != null && !nombre.equals("")) {
            nombre = "'" + nombre + "'";
        }
        if (razonSocial != null && !razonSocial.equals("")) {
            razonSocial = "'" + razonSocial + "'";
        }
        boolean proveedorRepetido = getProveedorRepetido(empresa, codigo, id, nombre, null);
        if (proveedorRepetido) {
            campos.put("rptaRepetido", "Proveedor ya existe.");
        }
        String respues = CedulaRuc.comprobacion(identificacion);
        if (respues != null && !respues.equals("T")) {
            campos.put("rptaCedula", "F".equals(respues.substring(0, 1)) ? respues.substring(1) : respues);
        }
        if (tipo != null && (tipo.equals("R") || tipo.equals("C")) && identificacion != null && !identificacion.equals("") && !proveedorRepetido
                && respues != null && respues.equals("T")) {
            campos.put("consultaDatos", true);
        }
        return campos;

    }

    @Override
    public Map<String, Object> validarIdentificacionTransportista(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String identificacion = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        String respues = CedulaRuc.comprobacion(identificacion);
        if (respues != null && !respues.equals("T")) {
            campos.put("rptaCedula", "F".equals(respues.substring(0, 1)) ? respues.substring(1) : respues);
        } else if (identificacion != null && !identificacion.equals("")) {
            campos.put("consultaSRI", true);
        }
        return campos;

    }

    @Override
    public String verificarDatosProveedorTO(String empresa, InvProveedorTO proveedor) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String respuesta = "T";

        proveedor.setEmpCodigo(empresa);
        if (proveedor.getProvId() != null && proveedor.getProvId().equals("")) {
            InvProveedorTO invProveedorTO = getBuscaCedulaProveedorTO(empresa, proveedor.getProvId());

            if (invProveedorTO != null) {
                respuesta = "FProveedor ya existe.";
            } else {
                //validar identificacion si es valida
                if (proveedor.getProvTipoId() != null) {
                    AnxTipoIdentificacionTO tipoIdenTO = tipoIdentificacionService.getAnxTipoIdentificacion(proveedor.getProvTipoId());
                    if (tipoIdenTO != null) {
                        if (proveedor.getProvCategoria() != null && proveedor.getProvCategoria().equals("")) {
                            InvProveedorCategoria categoria = proveedorCategoriaService.buscarInvProveedorCategoria(empresa, proveedor.getProvCategoria());
                            if (categoria != null) {
                                String id = null;
                                String nombre = null;
                                String razonSocial = null;
                                if (proveedor.getProvId() != null && !proveedor.getProvId().equals("")) {
                                    id = "'" + proveedor.getProvId() + "'";
                                }
                                if (nombre != null && !nombre.equals("")) {
                                    nombre = "'" + nombre + "'";
                                }
                                if (razonSocial != null && !razonSocial.equals("")) {
                                    razonSocial = "'" + razonSocial + "'";
                                }
                                boolean proveedorRepetido = getProveedorRepetido("'" + empresa + "'", null, id, nombre, razonSocial);
                                if (!proveedorRepetido) {
                                    boolean identificacionComprobada = false;
                                    if (proveedor.getProvTipoId().equalsIgnoreCase("C") || proveedor.getProvTipoId().equalsIgnoreCase("R")) {
                                        String respues = CedulaRuc.comprobacion(proveedor.getProvId());
                                        if (respues != null && !respues.equals("T")) {
                                            respuesta = "F".equals(respues.substring(0, 1)) ? respues.substring(1) : respues;
                                        } else {
                                            identificacionComprobada = true;
                                        }
                                    } else {
                                        identificacionComprobada = true;
                                    }

                                    if (identificacionComprobada) {
                                        //validando provincia y ciudad...

                                    }
                                } else {
                                    respuesta = "FProveedor ya existe.";
                                }
                            } else {
                                respuesta = "FTipo contribuyente no ya existe.";
                            }
                        } else {
                            respuesta = "FDebe ingresar tipo de contribuyente";
                        }
                    } else {
                        respuesta = "FTipo de identificación no ya existe.";
                    }
                } else {
                    respuesta = "FDebe ingresar tipo de identificación";
                }
            }
        } else {
            respuesta = "FDebe ingresar número de identificación";
        }

        return respuesta;
    }

    @Override
    public Map<String, Object> verificarExistenciaEnProovedor(String empresa, List<InvProveedorTO> proveedores, List<InvProveedorTO> proveedoresIncompletosExcel) throws Exception {
        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<InvProveedorTO> listaProveedores = new ArrayList<>();
        String codigoProveedorIncrementado = "";
        List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
        cuentasBancarias.add(new ComboGenericoTO("03", "CTA CORRIENTE"));
        cuentasBancarias.add(new ComboGenericoTO("04", "CTA AHORRO"));
        cuentasBancarias.add(new ComboGenericoTO("05", "CTA VIRTUAL"));

        if (proveedoresIncompletosExcel.size() > 0) {
            for (InvProveedorTO proveedorIncompleto : proveedoresIncompletosExcel) {
                String indentificacionProveedor = "";
                if (proveedorIncompleto.getProvId() != null) {
                    indentificacionProveedor = proveedorIncompleto.getProvId();
                } else if (proveedorIncompleto.getProvRazonSocial() != null) {
                    indentificacionProveedor = proveedorIncompleto.getProvRazonSocial();
                } else if (proveedorIncompleto.getProvNombreComercial() != null) {
                    indentificacionProveedor = proveedorIncompleto.getProvNombreComercial();
                }

                if (proveedorIncompleto.getProvTipoId() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'TIPO_IDENTIFICACION' vacio.");
                }
                if (proveedorIncompleto.getProvId() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'NUMERO_IDENTIFICACION' vacio.");
                }
                if (proveedorIncompleto.getProvCategoria() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'TIPO_CONTRIBUYENTE' vacio.");
                }
                if (proveedorIncompleto.getProvRazonSocial() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'RAZON_SOCIAL' vacio.");
                }
                if (proveedorIncompleto.getProvProvincia() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'PROVINCIA' vacio.");
                }
                if (proveedorIncompleto.getProvCiudad() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'CIUDAD' vacio.");
                }
                if (proveedorIncompleto.getProvDireccion() == null) {
                    listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'DIRECCION' vacio.");
                }

                if (proveedorIncompleto.getProvBanco() != null && !proveedorIncompleto.getProvBanco().equals("")) {
                    if (proveedorIncompleto.getProvTipoCuenta() == null || proveedorIncompleto.getProvTipoCuenta().equals("")) {
                        String error = "FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'TIPO_CUENTA' vacio";
                        if (proveedorIncompleto.getProvNroCuenta() == null || proveedorIncompleto.getProvNroCuenta().equals("")) {
                            error += " y campo obligatorio 'NUMERO_CUENTA' vacio";
                        } else {
                            error += ".";
                        }
                        listaMensajesEnviar.add(error);
                    } else {
                        if (proveedorIncompleto.getProvNroCuenta() == null || proveedorIncompleto.getProvNroCuenta().equals("")) {
                            listaMensajesEnviar.add("FEl proveedor: <strong>" + indentificacionProveedor + " </strong>, tiene campo obligatorio 'NUMERO_CUENTA' vacio");
                        }
                    }
                }
            }
        }
        if (proveedores != null && !proveedores.isEmpty()) {
            for (int i = 0; i < proveedores.size(); i++) {
                boolean insertar = true;
                boolean verificarProvincia = false, verificarTipoContribuyente_Categoria = false, verificarTipoId = false, verificarRazonSocialUnica = false, ProvNombreComercialUnico = false, verificarBanco = false, verificarTipoCuenta = false;
                String identificacion = proveedores.get(i).getProvId();
                InvProveedorTO proveedor = null;
                if (proveedores.get(i).getProvId() != null && !proveedores.get(i).getProvId().equals("")) {
                    InvProveedorTO invProveedorTO = getBuscaCedulaProveedorTO(empresa, proveedores.get(i).getProvId());
                    if (invProveedorTO != null) {
                        proveedores.get(i).setProvCodigo(invProveedorTO.getProvCodigo());
                        insertar = false;
                    }
                }
                proveedor = new InvProveedorTO();
                proveedor.setProvObservaciones(proveedores.get(i).getProvObservaciones() != null ? proveedores.get(i).getProvObservaciones() : null);
                proveedor.setProvId(proveedores.get(i).getProvId());
                // seteo ciudad sin validarla, se validara la ciudad en metodo insertarListadoExcelProveedores
                proveedor.setProvCiudad(proveedores.get(i).getProvCiudad().toUpperCase());
                proveedor.setProvParroquia(proveedores.get(i).getProvParroquia() != null ? proveedores.get(i).getProvParroquia().toUpperCase() : null);
                proveedor.setProvDireccion(proveedores.get(i).getProvDireccion() != null ? proveedores.get(i).getProvDireccion().toUpperCase() : null);
                proveedor.setProvCelular(proveedores.get(i).getProvCelular() != null ? proveedores.get(i).getProvCelular() : null);
                proveedor.setProvEmail(proveedores.get(i).getProvEmail() != null ? proveedores.get(i).getProvEmail() : null);
                proveedor.setProvEmailOrdenCompra(proveedores.get(i).getProvEmailOrdenCompra() != null ? proveedores.get(i).getProvEmailOrdenCompra() : null);
                proveedor.setUsrFechaInsertaProveedor(UtilsValidacion.fechaSistema());
                proveedor.setEmpCodigo(proveedores.get(i).getEmpCodigo());
                proveedor.setUsrInsertaProveedor(proveedores.get(i).getUsrInsertaProveedor());
                proveedor.setProvRelacionado(false);
                proveedor.setProvInactivo(false);
                // validacion/seteo de proviincia
                for (AnxProvinciaCantonTO provincia : dpaEcuadorService.getComboAnxDpaProvinciaTO()) {
                    if (provincia.getNombre().toUpperCase().equals(proveedores.get(i).getProvProvincia().toUpperCase())) {
                        proveedor.setProvProvincia(proveedores.get(i).getProvProvincia().toUpperCase());
                        verificarProvincia = true;
                        break; //salgo de provincia
                    }
                }
                //banco
                if (proveedores.get(i).getProvBanco() != null && !proveedores.get(i).getProvBanco().equals("")) {
                    for (ListaBanBancoTO banco : bancoDao.getListaBanBancoTO(empresa)) {
                        if (banco.getBanCodigo().equals(proveedores.get(i).getProvBanco())) {
                            proveedor.setProvBanco(proveedores.get(i).getProvBanco());
                            verificarBanco = true;
                        }
                    }
                    //tipo de cuenta Y numero cuenta verificarTipoCuenta
                    if (proveedores.get(i).getProvTipoCuenta() != null && !proveedores.get(i).getProvTipoCuenta().equals("")) {
                        for (ComboGenericoTO tipo : cuentasBancarias) {
                            if (tipo.getClave().equals(proveedores.get(i).getProvTipoCuenta())) {
                                proveedor.setProvTipoCuenta(proveedores.get(i).getProvTipoCuenta());
                                verificarTipoCuenta = true;
                            }
                        }
                        if (verificarBanco && verificarTipoCuenta) {
                            proveedor.setProvNroCuenta(proveedores.get(i).getProvNroCuenta());
                        }
                    } else {
                        verificarTipoCuenta = true;//ya se muestra el error arriba como obligatorio
                    }
                } else {
                    verificarBanco = true;
                    verificarTipoCuenta = true;
                }

                // valido/añado mensaje error 
                if (!verificarBanco) {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, con BANCO: <strong>'"
                            + proveedores.get(i).getProvTipoCuenta()
                            + " </strong> no existente.<br>");
                }
                if (!verificarTipoCuenta) {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, con TIPO CUENTA: <strong>'"
                            + proveedores.get(i).getProvTipoCuenta()
                            + " </strong> no existente.<br>");
                }
                if (!verificarProvincia) {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, con PROVINCIA: <strong> "
                            + proveedores.get(i).getProvProvincia()
                            + " </strong> pueda que tenga espacios en el registro o no exista.<br>");
                }

                for (InvProveedorCategoriaTO categoriaTipoContribuyente : proveedorCategoriaService.getInvProveedorCategoriaTO(empresa)) {
                    if (categoriaTipoContribuyente.getPcCodigo().equals(proveedores.get(i).getProvCategoria())) {
                        proveedor.setProvCategoria(proveedores.get(i).getProvCategoria());
                        verificarTipoContribuyente_Categoria = true;
                    }
                }
                if (!verificarTipoContribuyente_Categoria) {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, tiene una categoria TIPO_CONTRIBUYENTE: <strong>'"
                            + proveedores.get(i).getProvCategoria()
                            + " </strong> que no existente.<br>");
                }
                if (proveedores.get(i).getProvTipoId().toUpperCase().equals("R")
                        || proveedores.get(i).getProvTipoId().toUpperCase().equals("P")
                        || proveedores.get(i).getProvTipoId().toUpperCase().equals("F")
                        || proveedores.get(i).getProvTipoId().toUpperCase().equals("C")) {
                    proveedor.setProvTipoId(proveedores.get(i).getProvTipoId().toUpperCase());
                    verificarTipoId = true;
                } else {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, tiene un TIPO_IDENTIFICACION: <strong>'"
                            + proveedores.get(i).getProvTipoId()
                            + " </strong> no disponible en el sistema.<br>");
                }

                // Buscar por Nombre del proveedor
                if (proveedorDao.getProveedorRepetido("'" + proveedores.get(i).getEmpCodigo() + "'", (insertar ? null : "'" + proveedores.get(i).getProvCodigo() + "'"), null, (proveedores.get(i).getProvNombreComercial() == null ? null : "'" + proveedores.get(i).getProvNombreComercial() + "'"), null)) {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, con NOMBRE_COMERCIAL: <strong>'" + proveedores.get(i).getProvNombreComercial() + "'</strong>, ya existe. Intente con otro Nombre Comercial.");
                } else {
                    // valido/seteo nombre comercial
                    if (proveedores.get(i).getProvNombreComercial() != null && !proveedores.get(i).getProvNombreComercial().equals("")) {
                        proveedor.setProvNombreComercial(proveedores.get(i).getProvNombreComercial().toUpperCase());
                    }

                    ProvNombreComercialUnico = true;
                } // BUSCAR POR RAZONSOCIAL
                if (proveedorDao.getProveedorRepetido("'" + proveedores.get(i).getEmpCodigo() + "'", (insertar ? null : "'" + proveedores.get(i).getProvCodigo() + "'"), null, null, (proveedores.get(i).getProvRazonSocial().trim().isEmpty() ? null : "'" + proveedores.get(i).getProvRazonSocial() + "'"))) {
                    listaMensajesEnviar.add("FProveedor: <strong>" + identificacion + "</strong>, con RAZON_SOCIAL: <strong>'" + proveedores.get(i).getProvRazonSocial() + "'</strong>, ya existe.\nIntente ingresando otra Razón Social.");
                } else {
                    //valido/seteo razon social
                    proveedor.setProvRazonSocial(proveedores.get(i).getProvRazonSocial() != null ? proveedores.get(i).getProvRazonSocial().toUpperCase() : null);
                    verificarRazonSocialUnica = true;
                }

                // valido valores unicos y correctos de proveedor
                if (verificarTipoId && verificarTipoContribuyente_Categoria && verificarRazonSocialUnica && ProvNombreComercialUnico && ProvNombreComercialUnico
                        && verificarTipoCuenta && verificarBanco) {
                    // obtengo valor de proveedorCodigo
                    codigoProveedorIncrementado = getCodigoProveedorIncrementado(codigoProveedorIncrementado, proveedor);
                    // seteo proveedorCodigo
                    proveedor.setProvCodigo(codigoProveedorIncrementado);
                    // AÑANDO PROVEEDOR a LISTA
                    listaProveedores.add(proveedor);
                }

            }
        }
        mapResultadoEnviar = new HashMap<String, Object>();
        mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
        mapResultadoEnviar.put("listaProveedores", listaProveedores);
        return mapResultadoEnviar;
    }

    public String getCodigoProveedorIncrementado(String codigoIncrementado, InvProveedorTO proveedor) throws Exception {
        // obtengo codigo proveedor por 1ra y unica vez
        if (codigoIncrementado.equals("")) {
            codigoIncrementado = proveedorDao.getInvProximaNumeracionProveedor(proveedor.getEmpCodigo(), proveedor);
        } // incremento codigo proveedor anteriormente obtenido
        else {
            int numeracion = Integer.parseInt(codigoIncrementado);
            String rellenarConCeros = "";
            do {
                numeracion++;
                int numeroCerosAponer = 5 - String.valueOf(numeracion).trim().length();
                rellenarConCeros = "";
                for (int i = 0; i < numeroCerosAponer; i++) {
                    rellenarConCeros = rellenarConCeros + "0";
                }
            } while (buscarInvProveedor(proveedor.getEmpCodigo(), rellenarConCeros + numeracion) != null);
            codigoIncrementado = rellenarConCeros + numeracion;
        }
        /////////////
        return codigoIncrementado;
    }
}
