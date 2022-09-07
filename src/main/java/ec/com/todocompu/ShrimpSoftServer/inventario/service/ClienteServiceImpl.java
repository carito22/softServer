package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.PorcentajeIvaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.DpaEcuadorService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PaisService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoIdentificacionService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoDebitoService;
import ec.com.todocompu.ShrimpSoftServer.caja.service.CajaService;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesVentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.NumeracionVariosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VendedorDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.isp.service.SisEmpresaParametrosMikrotikService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.CedulaRuc;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.listaBanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarial;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarialPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedor;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteCategoriaDao clienteCategoriaDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private VendedorDao vendedorDao;
    @Autowired
    private GenericoDao<InvCliente, InvClientePK> clienteDaoCriteria;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private TipoIdentificacionService tipoIdentificacionService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private DpaEcuadorService dpaEcuadorService;
    @Autowired
    private ClienteCategoriaService clienteCategoriaService;
    @Autowired
    private InvClienteGrupoEmpresarialService invClienteGrupoEmpresarialService;
    @Autowired
    private ClientesVentasDetalleService clientesVentasDetalleService;
    @Autowired
    private ClientesVentasDetalleDao clientesVentasDetalleDao;
    @Autowired
    private ClientesDireccionesService clientesDireccionesService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PorcentajeIvaDao porcentajeIvaDao;
    @Autowired
    private BancoDebitoService bancoDebitoService;
    @Autowired
    private VentasMotivoDao ventasMotivoDao;
    @Autowired
    private CajaService cajaService;
    @Autowired
    private ClienteContratoService clienteContratoService;
    @Autowired
    private BodegaDao bodegaDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private SisEmpresaParametrosMikrotikService sisEmpresaParametrosMikrotikService;
    @Autowired
    private GuiaInpService guiaInpService;
    @Autowired
    private PaisService paisService;

    @Override
    public List<InvListaClienteTO> getListaClienteTO(String empresa, String busqueda, boolean activo_Cliente)
            throws Exception {
        return clienteDao.getListaClienteTO(empresa, busqueda, activo_Cliente);
    }

    @Override
    public boolean getClienteRepetidoCedulaRUC(String empresa, String identificacion, char tipo) throws Exception {
        boolean clienteRepetidoAdic = true;
        boolean clienteRepetido = true;
        if (tipo == 'C' || tipo == 'R') {
            String cedula = tipo == 'C' ? identificacion : identificacion.substring(0, 10);
            String ruc = tipo == 'C' ? identificacion + "001" : identificacion;
            clienteRepetido = getClienteRepetido("'" + empresa + "'", null, "'" + cedula + "'", null, null);
            clienteRepetidoAdic = getClienteRepetido("'" + empresa + "'", null, "'" + ruc + "'", null, null);
        } else {
            clienteRepetido = getClienteRepetido("'" + empresa + "'", null, "'" + identificacion + "'", null, null);
        }
        clienteRepetido = clienteRepetido || clienteRepetidoAdic;

        return clienteRepetido;
    }

    @Override
    public boolean getClienteRepetido(String empresa, String codigo, String id, String nombre, String razonSocial)
            throws Exception {
        return clienteDao.getClienteRepetido(empresa, codigo, id, nombre, razonSocial);
    }

    @Override
    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String identificacion, char tipo) {
        return clienteDao.obtenerInvClientePorCedulaRuc(empresa, identificacion, tipo);
    }

    @Override
    public List<InvClienteTO> getClienteTO(String empresa, String codigo) throws Exception {
        return clienteDao.getClienteTO(empresa, codigo);
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, String categoria) throws Exception {
        return clienteDao.getInvFunListadoClientesTO(empresa, categoria);
    }

    @Override
    public Boolean buscarConteoCliente(String empCodigo, String codigoCliente) throws Exception {
        return clienteDao.buscarConteoCliente(empCodigo, codigoCliente);
    }

    @Override
    public String insertarInvClienteTO(InvClienteTO invClienteTO, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        String codigoGenerado = "";
        if (invClienteTO.getCliCodigo().trim().isEmpty()) {
            invClienteTO.setCliCodigo(
                    clienteDao.getInvProximaNumeracionCliente(invClienteTO.getEmpCodigo(), invClienteTO));
            codigoGenerado = invClienteTO.getCliCodigo();
        }
        if (clienteDao.buscarInvCliente(invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invClienteTO.getCliCodigo();
            susDetalle = "Se ingresó el cliente " + invClienteTO.getCliRazonSocial() + " con código "
                    + invClienteTO.getCliCodigo();
            susSuceso = "INSERT";
            susTabla = "inventario.inv_cliente";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invClienteTO.setUsrFechaInsertaCliente(UtilsValidacion.fechaSistema());
            InvCliente invCliente = ConversionesInventario.convertirInvClienteTO_InvCliente(invClienteTO);
            invCliente.setUsrCodigo(sisInfoTO.getUsuario());
            ///// BUSCAR POR ID
            boolean var = clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'", "",
                    (invClienteTO.getCliId().trim().isEmpty() ? "" : "'" + invClienteTO.getCliId() + "'"), "", "");
            if (var && !("F".equals(invClienteTO.getCliTipoId().toString())
                    && "9999999999999".equals(invClienteTO.getCliId()))) {
                retorno = "FEl ID del Cliente ya existe en los registros.\nIntente ingresando otro ID...";
            } else ///// BUSCAR POR NOMBRE
            if (clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'", "", "",
                    (invClienteTO.getCliNombreComercial() == null || invClienteTO.getCliNombreComercial().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliNombreComercial() + "'"),
                    "")) {
                retorno = "FEl Nombre del Cliente ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZON SOCIAL
            if (clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'", "", "", "",
                    (invClienteTO.getCliRazonSocial().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliRazonSocial() + "'"))) {
                retorno = "FLa Razón Social ya existe en los registros.\nIntente ingresando otro nombre...";
            } else {
                retorno = "T";
            }

            if (retorno.charAt(0) == 'T') {
                InvClienteCategoria invClienteCategoria = clienteCategoriaDao
                        .buscarInvClienteCategoria(invClienteTO.getEmpCodigo(), invClienteTO.getCliCategoria());
                InvVendedor invVendedor = vendedorDao.buscarInvVendedor(invClienteTO.getEmpCodigo(),
                        invClienteTO.getVendCodigo());

                if (invClienteCategoria != null && invVendedor != null) {
                    invCliente.setInvClienteCategoria(invClienteCategoria);
                    invCliente.setInvVendedor(invVendedor);
                    InvNumeracionVarios invNumeracionVarios = numeracionVariosDao
                            .obtenerPorId(InvNumeracionVarios.class, invClienteTO.getEmpCodigo());

                    if (invNumeracionVarios == null && codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios = null;
                    } else if (invNumeracionVarios == null && !codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios = new InvNumeracionVarios(invClienteTO.getEmpCodigo(), "00000",
                                "00000", codigoGenerado, invClienteTO.getEmpCodigo());
                    } else if (invNumeracionVarios != null && codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios = null;
                    } else if (invNumeracionVarios != null && !codigoGenerado.trim().isEmpty()) {
                        invNumeracionVarios.setNumClientes(codigoGenerado);
                    }
                    if (clienteDao.insertarInvCliente(invCliente, sisSuceso, invNumeracionVarios)) {
                        if (listadoINP != null) {
                            for (int i = 0; i < listadoINP.size(); i++) {
                                listadoINP.get(i).getInvGuiaRemisionInpPK().setInpCliCodigo(invClienteTO.getCliCodigo());
                                guiaInpService.insertarInvGuiaRemisionInp(listadoINP.get(i), sisInfoTO);
                            }
                        }
                        retorno = "T<html>Se ha guardado el siguiente cliente:<br><br>Código: <font size = 5>"
                                + invCliente.getInvClientePK().getCliCodigo().trim()
                                + "</font>.<br>Nombre: <font size = 5>" + invCliente.getCliRazonSocial().trim()
                                + "</font>.</html>";
                    } else {
                        retorno = "FHubo un error al ingresar al cliente...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            }
        } else {
            retorno = "FEl código del cliente que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public Map<String, Object> insertarListadoExcelClientes(List<InvClienteTO> listaInvClienteTO, List<AnxProvinciaCantonTO> listaProvincias, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<InvClienteTO> listaClientes = new ArrayList<>();
        List<AnxProvinciaCantonTO> listadoCiudad = new ArrayList<>();
        for (InvClienteTO clienteTO : listaInvClienteTO) {
            String codigoProvincia = null;
            boolean ciudadCorrecta = false;
            boolean insertar = true;
            //verificar si inserta o modifica cliente
            if (clienteTO.getCliId() != null && !clienteTO.getCliId().equals("")) {
                InvClienteTO client = clienteDao.obtenerClienteTOPorCedulaRuc(sisInfoTO.getEmpresa(), clienteTO.getCliId());
                if (client != null) {
                    clienteTO.setCliCodigo(client.getCliCodigo());
                    insertar = false;
                }
            }
            // obtengo lista ciudades luego de identificar provincia del cliente
            for (int i = 0; i < listaProvincias.size(); i++) {
                if (clienteTO.getCliProvincia().toUpperCase().equals(listaProvincias.get(i).getNombre().toUpperCase())) {
                    codigoProvincia = listaProvincias.get(i).getCodigo();
                    listadoCiudad = dpaEcuadorService.getComboAnxDpaCantonTO(codigoProvincia);
                    break;
                }
            }
            // valido/seteo ciudad cliente
            for (int i = 0; i < listadoCiudad.size(); i++) {
                if (clienteTO.getCliCiudad().toUpperCase().equals(listadoCiudad.get(i).getNombre().toUpperCase())) {
                    clienteTO.setCliCiudad(listadoCiudad.get(i).getNombre());
                    ciudadCorrecta = true;
                    break;
                }
            }
            // continuo si cliente tiene ciudad correcta
            if (!ciudadCorrecta) {
                listaMensajesEnviar.add("F<html>La ciudad ingresada : <br><br>Código:<font size = 5> " + clienteTO.getCliCiudad() + "</font>, no consta en los registros del sistema.</html>");

            } else {
                // creo invCliente para insertarlo en base de datos
                InvCliente invCliente = ConversionesInventario.convertirInvClienteTO_InvCliente(clienteTO);
                /// PREPARANDO OBJETO SISSUCESO [BEGIN]
                susClave = clienteTO.getCliCodigo();
                susDetalle = "Se " + (insertar ? "ingresó" : "modificó") + " el cliente " + clienteTO.getCliRazonSocial() + " con código " + clienteTO.getCliCodigo();
                susSuceso = (insertar ? "INSERT" : "UPDATE");
                susTabla = "inventario.inv_proveedor";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PREPARANDO OBJETO SISSUCESO [END]
                InvClienteCategoria invClienteCategoria = clienteCategoriaDao.buscarInvClienteCategoria(clienteTO.getEmpCodigo(), clienteTO.getCliCategoria());
                invCliente.setInvClienteCategoria(invClienteCategoria);
                InvVendedor invVendedor = vendedorDao.buscarInvVendedor(clienteTO.getEmpCodigo(), clienteTO.getVendCodigo());
                invCliente.setInvVendedor(invVendedor);
                // se crea objeto InvNumeracionVarios 
                InvNumeracionVarios invNumeracionVariosAux = numeracionVariosDao.obtenerPorId(InvNumeracionVarios.class, clienteTO.getEmpCodigo());
                InvNumeracionVarios invNumeracionVarios = ConversionesInventario.convertirInvNumeracionVarios_InvNumeracionVarios(invNumeracionVariosAux);
                if (invNumeracionVarios == null && clienteTO.getCliCodigo().trim().isEmpty()) {
                    invNumeracionVarios = null;
                } else if (invNumeracionVarios == null && !clienteTO.getCliCodigo().trim().isEmpty()) {
                    invNumeracionVarios = new InvNumeracionVarios(clienteTO.getEmpCodigo(), "00000", "00000", clienteTO.getCliCodigo(), clienteTO.getEmpCodigo());
                } else if (invNumeracionVarios != null && clienteTO.getCliCodigo().trim().isEmpty()) {
                    invNumeracionVarios = null;
                } else if (invNumeracionVarios != null && !clienteTO.getCliCodigo().trim().isEmpty()) {
                    invNumeracionVarios.setNumClientes(clienteTO.getCliCodigo());
                }

                if (insertar) {
                    if (clienteDao.insertarInvCliente(invCliente, sisSuceso, invNumeracionVarios)) {
                        listaClientes.add(clienteTO);
                        listaMensajesEnviar.add("TEl cliente con ID: " + clienteTO.getCliId() + ", se ha guardado correctamente.");
                    } else {
                        listaMensajesEnviar.add("FEl cliente con ID: " + clienteTO.getCliId() + ", NO se ha guardado correctamente.");
                    }
                } else {
                    if (clienteDao.modificarInvCliente(invCliente, sisSuceso)) {
                        listaClientes.add(clienteTO);
                        listaMensajesEnviar.add("TEl cliente con ID: " + clienteTO.getCliId() + ", se ha modificado correctamente.");
                    } else {
                        listaMensajesEnviar.add("FEl cliente con ID: " + clienteTO.getCliId() + ", NO se ha modificado correctamente.");
                    }
                }

            }
        }
        // añado listas a map y retorno
        mapResultadoEnviar = new HashMap<String, Object>();
        mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
        mapResultadoEnviar.put("listaImportarClientes", listaClientes);

        return mapResultadoEnviar;
    }

    @Override
    public String insertarInvClienteTOVentaAutomatica(InvClienteTO invClienteTO, List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO, List<InvClientesDirecciones> listaDirecciones, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO) throws Exception {
        String respues = insertarInvClienteTO(invClienteTO, listadoINP, sisInfoTO);
        if (respues.charAt(0) == 'T' && invClientesVentasDetalleTO != null) {
            clientesVentasDetalleService.insertarClientesVentasDetalles(invClientesVentasDetalleTO, invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo(), sisInfoTO);
            clientesDireccionesService.insertarClientesDirecciones(listaDirecciones, invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo(), sisInfoTO);
        }
        return respues;
    }

    @Override
    public String modificarInvClienteTOVentaAutomatica(InvClienteTO invClienteTO, String codigoAnterior, List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO, List<InvClientesDirecciones> listaDirecciones, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO) throws Exception {
        String respues = modificarInvClienteTO(invClienteTO, codigoAnterior, listadoINP, sisInfoTO);
        if (respues.charAt(0) == 'T' && invClientesVentasDetalleTO != null) {
            clientesVentasDetalleService.insertarClientesVentasDetalles(invClientesVentasDetalleTO, invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo(), sisInfoTO);
            clientesDireccionesService.insertarClientesDirecciones(listaDirecciones, invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo(), sisInfoTO);
        }
        return respues;
    }

    @Override
    public String modificarInvClienteTO(InvClienteTO invClienteTO, String codigoAnterior, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        InvCliente invClienteAux = null;
        if (codigoAnterior.trim().isEmpty()) {
            invClienteAux = clienteDao.buscarInvCliente(invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo());
        } else {
            invClienteAux = clienteDao.buscarInvCliente(invClienteTO.getEmpCodigo(), codigoAnterior);
        }
        if (codigoAnterior.trim().isEmpty()) {
            ///// BUSCAR POR ID
            boolean var = clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'",
                    (invClienteTO.getCliCodigo().trim().isEmpty() ? "" : "'" + invClienteTO.getCliCodigo() + "'"),
                    (invClienteTO.getCliId().trim().isEmpty() ? "" : "'" + invClienteTO.getCliId() + "'"), "", "");
            if (var && !("F".equals(invClienteTO.getCliTipoId().toString())
                    && "9999999999999".equals(invClienteTO.getCliId()))) {
                retorno = "FEl ID del Cliente ya existe en los registros.\nIntente ingresando otro ID...";
            } else ///// BUSCAR POR NOMBRE
            if (clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'",
                    (invClienteTO.getCliCodigo().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliCodigo() + "'"),
                    "", (invClienteTO.getCliRazonSocial().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliRazonSocial() + "'"),
                    "")) {
                retorno = "FEl Nombre del Cliente ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZON SOCIAL
            if (clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'",
                    (invClienteTO.getCliCodigo().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliCodigo() + "'"),
                    "", "", (invClienteTO.getCliRazonSocial().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliRazonSocial() + "'"))) {
                retorno = "FLa Razón Social ya existe en los registros.\nIntente ingresando otro nombre...";
            } else {
                retorno = "T";
            }
        } else {
            ///// BUSCAR POR ID
            boolean var = clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'",
                    (codigoAnterior.trim().isEmpty() ? "" : "'" + codigoAnterior + "'"),
                    (invClienteTO.getCliId().trim().isEmpty() ? "" : "'" + invClienteTO.getCliId() + "'"), "", "");
            if (var && !("F".equals(invClienteTO.getCliTipoId().toString())
                    && "9999999999999".equals(invClienteTO.getCliId()))) {
                retorno = "FEl ID del Cliente ya existe en los registros.\nIntente ingresando otro ID...";
            } else ///// BUSCAR POR NOMBRE
            if (clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'",
                    (codigoAnterior.trim().isEmpty() ? "" : "'" + codigoAnterior + "'"), "",
                    (invClienteTO.getCliRazonSocial().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliRazonSocial() + "'"),
                    "")) {
                retorno = "FEl Nombre del Cliente ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZON SOCIAL
            if (clienteDao.getClienteRepetido("'" + invClienteTO.getEmpCodigo() + "'",
                    (codigoAnterior.trim().isEmpty() ? "" : "'" + codigoAnterior + "'"), "", "",
                    (invClienteTO.getCliRazonSocial().trim().isEmpty() ? ""
                    : "'" + invClienteTO.getCliRazonSocial() + "'"))) {
                retorno = "FLa Razón Social ya existe en los registros.\nIntente ingresando otro nombre...";
            } else {
                retorno = "T";
            }
        }

        if (retorno.charAt(0) == 'T') {
            if (invClienteAux != null && codigoAnterior.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invClienteTO.getCliCodigo();
                susDetalle = "Se modificó el cliente " + invClienteTO.getCliRazonSocial() + " con código "
                        + invClienteTO.getCliCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_cliente";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invClienteTO.setUsrInsertaCliente(invClienteAux.getUsrCodigo());
                invClienteTO.setUsrFechaInsertaCliente(
                        UtilsValidacion.fecha(invClienteAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvCliente invCliente = ConversionesInventario.convertirInvClienteTO_InvCliente(invClienteTO);
                //
                InvClienteCategoria invClienteCategoria = clienteCategoriaDao
                        .buscarInvClienteCategoria(invClienteTO.getEmpCodigo(), invClienteTO.getCliCategoria());
                if (invClienteCategoria != null) {
                    invCliente.setInvClienteCategoria(invClienteCategoria);

                    InvVendedor invVendedor = vendedorDao.buscarInvVendedor(invClienteTO.getEmpCodigo(),
                            invClienteTO.getVendCodigo());

                    invCliente.setInvVendedor(invVendedor);
                    if (invCliente.getCliIdTipo() == 'F') {
                        invCliente.setCliIdNumero(null);
                    }
                    if (clienteDao.modificarInvCliente(invCliente, sisSuceso)) {
                        insertarInpModificar(listadoINP, sisInfoTO, invClienteTO);
                        retorno = "TEl cliente se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar el cliente...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else if (invClienteAux != null && !codigoAnterior.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invClienteTO.getCliCodigo();
                susDetalle = "Se modificó el cliente " + invClienteTO.getCliRazonSocial() + " con código " + invClienteTO.getCliCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_cliente";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                        sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A
                /// ENTITY
                invClienteTO.setUsrInsertaCliente(invClienteAux.getUsrCodigo());
                invClienteTO.setUsrFechaInsertaCliente(
                        UtilsValidacion.fecha(invClienteAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvCliente invCliente = ConversionesInventario.convertirInvClienteTO_InvCliente(invClienteTO);

                InvClienteCategoria invClienteCategoria = clienteCategoriaDao
                        .buscarInvClienteCategoria(invClienteTO.getEmpCodigo(), invClienteTO.getCliCategoria());
                if (invClienteCategoria != null) {
                    invCliente.setInvClienteCategoria(invClienteCategoria);
                    InvVendedor invVendedor = vendedorDao.buscarInvVendedor(invClienteTO.getEmpCodigo(),
                            invClienteTO.getVendCodigo());
                    invCliente.setInvVendedor(invVendedor);

                    if (invClienteAux.getInvClientePK().getCliCodigo()
                            .equals(invCliente.getInvClientePK().getCliCodigo())) {
                        if (invCliente.getCliIdTipo() == 'F') {
                            invCliente.setCliIdNumero(null);
                        }
                        if (clienteDao.modificarInvCliente(invCliente, sisSuceso)) {
                            insertarInpModificar(listadoINP, sisInfoTO, invClienteTO);
                            retorno = "TEl cliente se modificó correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar el cliente...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else if (clienteDao.modificarInvClienteLlavePrincipal(invClienteAux, invCliente,
                            sisSuceso)) {
                        retorno = "TEl cliente se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar el cliente...\nIntente de nuevo o contacte con el administrador";
                    }

                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else if (invClienteAux == null && codigoAnterior.trim().isEmpty()) {
                retorno = "FEl código del cliente que va a modificar ya no está disponible...\nIntente con otro...";
            } else {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invClienteTO.getCliCodigo();
                susDetalle = "Se modificó el cliente " + invClienteTO.getCliRazonSocial() + " con código " + invClienteTO.getCliCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_cliente";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                        sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO
                /// A ENTITY
                invClienteTO.setUsrInsertaCliente(invClienteAux.getUsrCodigo());
                invClienteTO.setUsrFechaInsertaCliente(
                        UtilsValidacion.fecha(invClienteAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvCliente invCliente = ConversionesInventario
                        .convertirInvClienteTO_InvCliente(invClienteTO);

                InvClienteCategoria invClienteCategoria = clienteCategoriaDao.buscarInvClienteCategoria(
                        invClienteTO.getEmpCodigo(), invClienteTO.getCliCategoria());
                if (invClienteCategoria != null) {
                    invCliente.setInvClienteCategoria(invClienteCategoria);
                    InvVendedor invVendedor = vendedorDao.buscarInvVendedor(invClienteTO.getEmpCodigo(),
                            invClienteTO.getVendCodigo());
                    invCliente.setInvVendedor(invVendedor);

                    if (invClienteAux.getInvClientePK().getCliCodigo().equals(invCliente.getInvClientePK().getCliCodigo())) {
                        if (clienteDao.modificarInvCliente(invCliente, sisSuceso)) {
                            insertarInpModificar(listadoINP, sisInfoTO, invClienteTO);
                            retorno = "TEl cliente se modificó correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar el cliente...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else if (clienteDao.modificarInvClienteLlavePrincipal(invClienteAux, invCliente, sisSuceso)) {
                        insertarInpModificar(listadoINP, sisInfoTO, invClienteTO);
                        retorno = "TEl cliente se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar el cliente...\nIntente de nuevo o contacte con el administrador";
                    }

                } else {
                    retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            }
        }
        return retorno;
    }

    public void insertarInpModificar(List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO, InvClienteTO invClienteTO) throws Exception {
        List<InvGuiaRemisionInp> inps = guiaInpService.getInvGuiaRemisionInp(sisInfoTO.getEmpresa(), invClienteTO.getCliCodigo(), true);
        if (listadoINP != null && !listadoINP.isEmpty()) {
            if (inps != null && !inps.isEmpty()) {
                listadoINP.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    inps.removeIf(n -> (n.getInvGuiaRemisionInpPK().getInpCliCodigo().equals(item.getInvGuiaRemisionInpPK().getInpCliCodigo())));
                });
                for (int i = 0; i < inps.size(); i++) {
                    guiaInpService.eliminarInvGuiaRemisionInp(inps.get(i), sisInfoTO);
                }
            }
            for (int i = 0; i < listadoINP.size(); i++) {
                listadoINP.get(i).getInvGuiaRemisionInpPK().setInpCliCodigo(invClienteTO.getCliCodigo());
                guiaInpService.insertarInvGuiaRemisionInp(listadoINP.get(i), sisInfoTO);
            }
        } else {
            if (inps != null && !inps.isEmpty()) {
                for (int i = 0; i < inps.size(); i++) {
                    guiaInpService.eliminarInvGuiaRemisionInp(inps.get(i), sisInfoTO);
                }
            }
        }
    }

    @Override
    public boolean modificarClienteLugarEntrega(InvClientePK invClientePK, String cliLugaresEntrega, SisInfoTO sisInfoTO) throws Exception {
        InvCliente invCliente = clienteDaoCriteria.obtener(InvCliente.class, invClientePK);
        if (invCliente != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invCliente.getUsrCodigo();
            susDetalle = "Se modificó el estado de el cliente " + invCliente.getCliRazonSocial() + " con código "
                    + invCliente.getInvClientePK().getCliCodigo();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_cliente";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ACTUALIZAR ESTADO
            invCliente.setCliLugaresEntrega(cliLugaresEntrega);
            clienteDao.actualizar(invCliente);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El cliente ya no esta disponible.");
        }
    }

    @Override
    public String eliminarInvClienteTO(InvClienteTO invClienteTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvCliente invClienteAux = clienteDao.buscarInvCliente(invClienteTO.getEmpCodigo(),
                invClienteTO.getCliCodigo());
        if (invClienteAux != null) {
            if (clienteDao.buscarConteoCliente(invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo())) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invClienteTO.getCliCodigo();
                susDetalle = "Se eliminó el cliente " + invClienteTO.getCliRazonSocial() + " con código " + invClienteTO.getCliCodigo();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_cliente";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                invClienteTO.setUsrInsertaCliente(invClienteAux.getUsrCodigo());
                invClienteTO.setUsrFechaInsertaCliente(UtilsValidacion.fecha(invClienteAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvCliente invCliente = ConversionesInventario.convertirInvClienteTO_InvCliente(invClienteTO);
                invCliente.setInvClienteCategoria(invClienteAux.getInvClienteCategoria());
                if (clienteDao.eliminarInvCliente(invCliente, sisSuceso)) {
                    retorno = "TEl cliente se eliminó correctamente...";
                } else {
                    retorno = "FHubo un error al eliminar al cliente...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FNo se puede eliminar este Cliente debido a que tiene movimientos en Inventarios.";
            }
        } else {
            retorno = "FEl Cliente que va a eliminar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String cedulaRuc) {
        return clienteDao.obtenerInvClientePorCedulaRuc(empresa, cedulaRuc);
    }

    @Override
    public InvClienteTO obtenerClienteTO(String empresa, String codigo) throws Exception {
        return clienteDao.obtenerClienteTO(empresa, codigo);
    }

    @Override
    public InvCliente obtenerCliente(String empresa, String codigo) throws Exception {
        return clienteDao.obtenerCliente(empresa, codigo);
    }

    @Override
    public boolean eliminarInvCliente(InvClientePK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        InvCliente invCliente = clienteDaoCriteria.obtener(InvCliente.class, pk);
        if (invCliente != null) {
            if (clienteDao.buscarConteoCliente(pk.getCliEmpresa(), pk.getCliCodigo())) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invCliente.getInvClientePK().getCliCodigo();
                susDetalle = "Se eliminó el cliente " + invCliente.getCliRazonSocial() + " con código " + invCliente.getInvClientePK().getCliCodigo();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_cliente";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                //ELIMINAR
                clienteDao.eliminar(invCliente);
                sucesoDao.insertar(sisSuceso);
                return true;
            } else {
                throw new GeneralException("No se puede eliminar este Cliente debido a que tiene movimientos en Inventarios.");
            }
        } else {
            throw new GeneralException("El cliente ya no esta disponible.");
        }
    }

    @Override
    public boolean modificarEstadoInvCliente(InvClientePK invClientePK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException {
        InvCliente invCliente = clienteDaoCriteria.obtener(InvCliente.class, invClientePK);
        if (invCliente != null) {
            /// PREPARANDO OBJETO SISSUCESO
            String detalle = estado ? "inactivo" : "activo";
            susClave = invCliente.getUsrCodigo();
            susDetalle = "Se " + detalle + " el estado del cliente " + invCliente.getCliRazonSocial() + " con código "
                    + invCliente.getInvClientePK().getCliCodigo();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_cliente";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ACTUALIZAR ESTADO
            invCliente.setCliInactivo(estado);
            clienteDao.actualizar(invCliente);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El cliente ya no esta disponible.");
        }
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria) throws Exception {
        return clienteDao.getInvFunListadoClientesTO(empresa, mostrarInactivo, categoria);
    }

    //modificado add parametro grupo empresarial
    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception {
        return clienteDao.getInvFunListadoClientesTO(empresa, mostrarInactivo, categoria, busqueda, grupoEmpresarial);
    }

    @Override
    public List<InvClienteContratosTO> listarReporteComprativoContratos(String empresa, boolean mostrarInactivo, String categoria, String busqueda, boolean diferencia) throws Exception {
        return clienteDao.listarReporteComprativoContratos(empresa, mostrarInactivo, categoria, busqueda, diferencia);
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTOSinVentaRecurrente(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception {
        return clienteDao.getInvFunListadoClientesTOSinVentaRecurrente(empresa, mostrarInactivo, categoria, busqueda, grupoEmpresarial);
    }

    @Override
    public List<InvClienteRecurrenteTO> listarClientesParaVentarecurrente(String empresa, String fecha) throws Exception {
        return clienteDao.listarClientesParaVentarecurrente(empresa, fecha);
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosCliente(Map<String, Object> parametros) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        InvVentasTO venta = new InvVentasTO();
        List<InvClientesVentasDetalleTO> listaInvVentasDetalleTO = new ArrayList<>();
        List<InvClientesDirecciones> listaDirecciones = new ArrayList<>();
        List<InvClienteContratoTO> contratos = new ArrayList<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuarioCodigo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        InvClienteTO invClienteTO = new InvClienteTO();
        List<AnxPaisTO> listadoPaises = paisService.getComboAnxPaisTO();
        List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
        cuentasBancarias.add(new ComboGenericoTO("CTE", "CTE"));
        cuentasBancarias.add(new ComboGenericoTO("AHO", "AHO"));
        cuentasBancarias.add(new ComboGenericoTO("TAR", "TAR"));

        List<ComboGenericoTO> generos = new ArrayList<>();
        generos.add(new ComboGenericoTO("F", "FEMENINO"));
        generos.add(new ComboGenericoTO("M", "MASCULINO"));
        generos.add(new ComboGenericoTO("PJ", "PERSONA JURIDICA"));

        List<ComboGenericoTO> estadosCivil = new ArrayList<>();
        estadosCivil.add(new ComboGenericoTO("S", "SOLTERO"));
        estadosCivil.add(new ComboGenericoTO("C", "CASADO"));
        estadosCivil.add(new ComboGenericoTO("D", "DIVORCIADO"));
        estadosCivil.add(new ComboGenericoTO("V", "VIUDO"));
        estadosCivil.add(new ComboGenericoTO("U", "UNION LIBRE"));

        List<AnxTipoIdentificacionTO> listaIdentificacion = tipoIdentificacionService.getListaAnxTipoIdentificacionTO();
        List<AnxProvinciaCantonTO> listaProvincias = dpaEcuadorService.getComboAnxDpaProvinciaTO();
        List<InvVendedorComboListadoTO> listaVendedores = vendedorService.getComboinvListaVendedorTOs(empresa, null);
        List<InvClienteCategoriaTO> listaCategorias = clienteCategoriaService.getInvClienteCategoriaTO(empresa);
        List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, null);
        List<listaBanBancoDebitoTO> listaBanBancoDebito = bancoDebitoService.getListaBanBancoDebitoTO(empresa);
        List<InvVentaMotivoTO> listaMotivosVenta = ventasMotivoDao.getListaInvVentasMotivoTO(empresa, true, null);
        CajCajaTO caja = cajaService.getCajCajaTO(empresa, usuarioCodigo);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        BigDecimal iva = porcentajeIvaDao.getValorAnxPorcentajeIvaTO(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"));
        venta.setVtaIvaVigente(iva);

        if (cliente != null && !cliente.equals("")) {
            invClienteTO = obtenerClienteTO(empresa, cliente);
            List<InvGuiaRemisionInp> listadoINP = guiaInpService.getInvGuiaRemisionInp(empresa, cliente, true);
            campos.put("listadoINP", listadoINP);
            listaInvVentasDetalleTO = clientesVentasDetalleDao.listarInvClientesVentasDetalleTO(empresa, cliente, 0);
            listaDirecciones = clientesDireccionesService.listarInvClientesDirecciones(empresa, cliente);
            contratos = clienteContratoService.getListarInvClienteContratoTO(empresa, cliente, null, null);
        }
        List<InvListaBodegasTO> listadoBodegas = bodegaDao.buscarBodegasTO(empresa, false, null);

        campos.put("listadoAnxPaisTO", listadoPaises);
        campos.put("listaIdentificacion", listaIdentificacion);
        campos.put("listaProvincias", listaProvincias);
        campos.put("listaVendedores", listaVendedores);
        campos.put("listaCategorias", listaCategorias);
        campos.put("listaGrupoEmpresarial", listaGrupoEmpresarial);
        campos.put("listaBanBancoDebito", listaBanBancoDebito);
        campos.put("cuentasBancarias", cuentasBancarias);
        campos.put("listaGenero", generos);
        campos.put("listaEstadoCivil", estadosCivil);
        campos.put("listaMotivosVenta", listaMotivosVenta);
        campos.put("contratos", contratos);
        campos.put("caja", caja);
        campos.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
        campos.put("listaDirecciones", listaDirecciones);
        campos.put("listadoBodegas", listadoBodegas);
        campos.put("venta", venta);
        campos.put("invClienteTO", invClienteTO);

        return campos;
    }

    @Override
    public List<InvClientesDirecciones> getListInvClientesDirecciones(String empresa, String cliCodigo) throws Exception {
        List<InvClientesDirecciones> listaDirecciones = new ArrayList<>();
        InvCliente cliente = clienteDao.buscarInvCliente(empresa, cliCodigo);

        if (cliente != null) {
            listaDirecciones = clientesDireccionesService.listarInvClientesDirecciones(empresa, cliCodigo);
            if (cliente.getCliCodigoEstablecimiento() != null && !cliente.getCliCodigoEstablecimiento().contentEquals("")) {
                InvClientesDirecciones direccionDefecto = new InvClientesDirecciones();
                direccionDefecto.setDirDetalle(cliente.getCliDireccion());
                direccionDefecto.setDirCodigo(cliente.getCliCodigoEstablecimiento());
                listaDirecciones.add(direccionDefecto);
            }
        }

        return listaDirecciones;
    }

    @Override
    public InvEntidadTransaccionTO obtenerInvEntidadTransaccionTOCuentasPorCobrar(String empresa, String codigo, String documento) throws Exception {
        InvCliente cliente = clienteDao.buscarInvCliente(empresa, codigo);
        if (cliente != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(documento != null ? documento : "GRUPO DE DOCUMENTOS");
            entidadTransaccion.setTipo("Cuentas por cobrar");
            entidadTransaccion.setRazonSocial(cliente.getCliRazonSocial());
            entidadTransaccion.setIdentificacion(cliente.getCliIdNumero());
            return entidadTransaccion;
        }
        return null;
    }

    @Override
    public InvEntidadTransaccionTO obtenerInvEntidadTransaccionTORolesPago(String empresa, String codigo, String documento) throws Exception {
        RhEmpleado cliente = empleadoDao.buscarEmpleado(empresa, codigo);
        if (cliente != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(documento != null ? documento : "GRUPO DE DOCUMENTOS");
            entidadTransaccion.setTipo("Roles de pago");
            entidadTransaccion.setRazonSocial(cliente.getEmpNombres() + " " + cliente.getEmpApellidos());
            entidadTransaccion.setIdentificacion(cliente.getEmpNumero());
            return entidadTransaccion;
        }
        return null;
    }

    @Override
    public boolean modificarClienteGrupoEmpresarial(InvClientePK invClientePK, String codigoGrupoEmpresarial, SisInfoTO sisInfoTO) throws Exception {
        InvCliente invCliente = clienteDaoCriteria.obtener(InvCliente.class, invClientePK);

        InvClienteGrupoEmpresarial ge = new InvClienteGrupoEmpresarial();
        ge.setInvClienteGrupoEmpresarialPK(new InvClienteGrupoEmpresarialPK(sisInfoTO.getEmpresa(), codigoGrupoEmpresarial));

        if (invCliente != null) {
            susClave = invCliente.getUsrCodigo();
            susDetalle = "Se modificó el estado de el cliente " + invCliente.getCliRazonSocial() + " con código " + invCliente.getInvClientePK().getCliCodigo();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_cliente";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ACTUALIZAR ESTADO
            invCliente.setInvClienteGrupoEmpresarial(ge);
            clienteDao.actualizar(invCliente);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El cliente ya no esta disponible.");
        }
    }

    @Override
    public Map<String, Object> validarIdentificacionCliente(Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        if (empresa != null && !empresa.equals("")) {
            empresa = "'" + empresa + "'";
        }
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        if (codigo != null && !codigo.equals("")) {
            codigo = "'" + codigo + "'";
        }
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        String identificacion = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        if (id != null && !id.equals("")) {
            id = "'" + id + "'";
        }
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        if (nombre != null && !nombre.equals("")) {
            nombre = "'" + nombre + "'";
        }
        String razonSocial = UtilsJSON.jsonToObjeto(String.class, map.get("razonSocial"));
        if (razonSocial != null && !razonSocial.equals("")) {
            razonSocial = "'" + razonSocial + "'";
        }
        Map<String, Object> campos = new HashMap<>();
        boolean clienteRepetido = getClienteRepetido(empresa, codigo, id, nombre, razonSocial);
        if (clienteRepetido) {
            campos.put("rptaRepetido", "Cliente ya existe.");
        } else {
            String respues = CedulaRuc.comprobacion(UtilsJSON.jsonToObjeto(String.class, map.get("id")));
            if (respues != null && !respues.equals("T")) {
                campos.put("rptaCedula", "F".equals(respues.substring(0, 1)) ? respues.substring(1) : respues);
            } else {
                if (tipo != null && (tipo.equals("R") || tipo.equals("C")) && identificacion != null && !identificacion.equals("")
                        && !clienteRepetido && respues != null && respues.equals("T")) {
                    campos.put("consultaDatos", true);
                }
            }
        }
        return campos;
    }

    @Override
    public boolean validarClienteParaVentarecurrente(String empresa, String fecha, String cliCodigo, int grupo) throws Exception {
        return clienteDao.validarClienteParaVentarecurrente(empresa, fecha, cliCodigo, grupo);
    }

    @Override
    public List<InvClienteSinMovimientoTO> obtenerClientesSinMovimientos(String empresa) throws Exception {
        return clienteDao.obtenerClientesSinMovimientos(empresa);
    }

    @Override
    public Map<String, Object> obtenerDatosClienteSegunEmpresa(Map<String, Object> map) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvClienteCategoriaTO> listaCategorias = clienteCategoriaService.getInvClienteCategoriaTO(empresa);
        SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
        Map<String, Object> campos = new HashMap<>();
        campos.put("listaCategorias", listaCategorias);
        campos.put("sisEmpresaParametrosMikrotik", sisEmpresaParametrosMikrotik);
        return campos;
    }

    @Override
    public InvClienteTO getInvClienteTOByRazonSocial(String empresa, String razonSocial) throws Exception {
        return clienteDao.getInvClienteTOByRazonSocial(empresa, razonSocial);
    }

    @Override
    public InvCliente getBuscaCedulaCliente(String empresa, String identificacion, char tipo) throws Exception {
        InvCliente cliente = null;
        if (tipo == 'C' || tipo == 'R') {
            String cedula = tipo == 'C' ? identificacion : identificacion.substring(0, 10);
            String ruc = tipo == 'C' ? identificacion + "001" : identificacion;
            cliente = obtenerInvClientePorCedulaRuc(empresa, cedula);
            InvCliente clienteAdic = obtenerInvClientePorCedulaRuc(empresa, ruc);

            if (clienteAdic != null) {
                cliente = clienteAdic;
            }
        } else {
            cliente = obtenerInvClientePorCedulaRuc(empresa, identificacion);
        }

        return cliente;
    }

    @Override
    public Map<String, Object> verificarExistenciaEnClientes(String empresa, List<InvClienteTO> clientesExcel, List<InvClienteTO> clientesIncompletosExcel) throws Exception {
        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<InvClienteTO> listaClientes = new ArrayList<>();
        String codigoClienteIncrementado = "", vendCodigo = "";
        if (clientesIncompletosExcel.size() > 0) {
            for (InvClienteTO clienteIncompleto : clientesIncompletosExcel) {
                String reconoceCliente = "";
                if (clienteIncompleto.getCliId() != null) {
                    reconoceCliente = clienteIncompleto.getCliId();
                } else if (clienteIncompleto.getCliRazonSocial() != null) {
                    reconoceCliente = clienteIncompleto.getCliRazonSocial();
                } else if (clienteIncompleto.getCliNombreComercial() != null) {
                    reconoceCliente = clienteIncompleto.getCliNombreComercial();
                }

                if (clienteIncompleto.getCliTipoId() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'TIPO_IDENTIFICACION' vacio.");
                }
                if (clienteIncompleto.getCliId() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'NUMERO_IDENTIFICACION' vacio.");
                }
                if (clienteIncompleto.getCliCategoria() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'TIPO_CONTRIBUYENTE' vacio.");
                }
                if (clienteIncompleto.getCliRazonSocial() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'RAZON_SOCIAL' vacio.");
                }
                if (clienteIncompleto.getCliProvincia() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'PROVINCIA' vacio.");
                }
                if (clienteIncompleto.getCliCiudad() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'CIUDAD' vacio.");
                }
                if (clienteIncompleto.getCliDireccion() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'DIRECCION' vacio.");
                }
                if (clienteIncompleto.getCliCodigoEstablecimiento() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'CODIGO_ESTABLECIMIENTO_SRI' vacio.");
                }
                if (clienteIncompleto.getVendCodigo() == null) {
                    listaMensajesEnviar.add("FEl cliente: <strong class='pl-2'>" + reconoceCliente + " </strong>, tiene campo obligatorio 'VENDEDOR' vacio.");
                }
            }
        }
        if (clientesExcel != null && !clientesExcel.isEmpty()) {
            for (int i = 0; i < clientesExcel.size(); i++) {
                boolean insertar = true;
                boolean verificarProvincia = false, verificarTipoContribuyente_Categoria = false, verificarTipoId = false, verificarRazonSocialUnica = false, ProvNombreComercialUnico = false, verificarClienteVendedor = false, validarCodigoEstablecimiento = false, verificarGrupoEmpresarial = false;
                String identificacion = clientesExcel.get(i).getCliId();
                InvClienteTO invClienteTO = null;
                // identifico existencia previa de cliente

                if (clientesExcel.get(i).getCliId() != null && !clientesExcel.get(i).getCliId().equals("")) {
                    InvClienteTO clienteTO = clienteDao.obtenerClienteTOPorCedulaRuc(empresa, clientesExcel.get(i).getCliId());
                    if (clienteTO != null) {
                        clientesExcel.get(i).setCliCodigo(clienteTO.getCliCodigo());
                        insertar = false;
                    }
                }

                invClienteTO = new InvClienteTO();
                invClienteTO.setCliObservaciones(clientesExcel.get(i).getCliObservaciones() != null ? clientesExcel.get(i).getCliObservaciones() : null);
                invClienteTO.setCliId(clientesExcel.get(i).getCliId());
                // seteo ciudad sin validarla, se validara la ciudad en metodo insertarListadoExcelProveedores
                invClienteTO.setCliCiudad(clientesExcel.get(i).getCliCiudad().toUpperCase());
                invClienteTO.setCliParroquia(clientesExcel.get(i).getCliParroquia() != null ? clientesExcel.get(i).getCliParroquia().toUpperCase() : null);
                invClienteTO.setCliDireccion(clientesExcel.get(i).getCliDireccion() != null ? clientesExcel.get(i).getCliDireccion().toUpperCase() : null);
                invClienteTO.setCliCelular(clientesExcel.get(i).getCliCelular() != null ? clientesExcel.get(i).getCliCelular() : null);
                invClienteTO.setCliTelefono(clientesExcel.get(i).getCliTelefono() != null ? clientesExcel.get(i).getCliTelefono() : null);
                invClienteTO.setCliEmail(clientesExcel.get(i).getCliEmail() != null ? clientesExcel.get(i).getCliEmail() : null);
                invClienteTO.setUsrFechaInsertaCliente(UtilsValidacion.fechaSistema());
                invClienteTO.setEmpCodigo(clientesExcel.get(i).getEmpCodigo());
                invClienteTO.setUsrInsertaCliente(clientesExcel.get(i).getUsrInsertaCliente());
                invClienteTO.setCliRelacionado(false);
                invClienteTO.setCliInactivo(false);
                // validacion/seteo de proviincia
                for (AnxProvinciaCantonTO provincia : dpaEcuadorService.getComboAnxDpaProvinciaTO()) {
                    if (provincia.getNombre().toUpperCase().equals(clientesExcel.get(i).getCliProvincia().toUpperCase())) {
                        invClienteTO.setCliProvincia(clientesExcel.get(i).getCliProvincia().toUpperCase());
                        verificarProvincia = true;
                        break;//salgo de provincia
                    }
                }
                // valido/añado mensaje error provincia
                if (!verificarProvincia) {
                    listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, con provincia: <strong class='pl-2'>"
                            + clientesExcel.get(i).getCliProvincia()
                            + " </strong> no existente.<br>");
                }
                //verifico existencia de invClienteCategoria, pero no lo seteo. Se seteara en el metodo insertarListadoExcelProveedores
                InvClienteCategoria invClienteCategoria = clienteCategoriaDao.buscarInvClienteCategoria(clientesExcel.get(i).getEmpCodigo(), clientesExcel.get(i).getCliCategoria());
                if (invClienteCategoria != null) {
                    invClienteTO.setCliCategoria(clientesExcel.get(i).getCliCategoria());
                    verificarTipoContribuyente_Categoria = true;
                } else {
                    listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, tiene una categoria de tipo de contribuyente: <strong class='pl-2'>'"
                            + clientesExcel.get(i).getCliCategoria()
                            + "'</strong> no existente.<br>");
                }
                //verificar grupo empresarial
                if (clientesExcel.get(i).getGeCodigo() != null && !clientesExcel.get(i).getGeCodigo().equals("")) {
                    if (invClienteGrupoEmpresarialService.obtenerInvClienteGrupoEmpresarial(clientesExcel.get(i).getEmpCodigo(), clientesExcel.get(i).getGeCodigo()) == null) {
                        listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, tiene un grupo empresarial: <strong class='pl-2'>'"
                                + clientesExcel.get(i).getGeCodigo()
                                + "'</strong> no existente.<br>");
                    } else {
                        invClienteTO.setGeEmpresa(clientesExcel.get(i).getEmpCodigo());
                        invClienteTO.setGeCodigo(clientesExcel.get(i).getGeCodigo());
                        verificarGrupoEmpresarial = true;
                    }
                } else {
                    verificarGrupoEmpresarial = true;
                }
                //verifico existencia de cliente vendedor, pero no seteo. Se setea en el metodo insertarListadoExcelProveedores
                vendCodigo = clientesExcel.get(i).getVendCodigo();
                InvVendedor invVendedor = vendedorDao.buscarInvVendedor(clientesExcel.get(i).getEmpCodigo(), vendCodigo);
                if (invVendedor != null) {
                    invClienteTO.setVendCodigo(vendCodigo);
                    verificarClienteVendedor = true;
                } else {
                    listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, con vendedor: <strong class='pl-2'>'"
                            + vendCodigo + "'</strong> no existente.<br>");
                }
                String tipoIdClienteExcel = String.valueOf(clientesExcel.get(i).getCliTipoId()).toUpperCase();
                if (tipoIdClienteExcel.equals("R")
                        || tipoIdClienteExcel.equals("P")
                        || tipoIdClienteExcel.equals("F")
                        || tipoIdClienteExcel.equals("C")) {
                    invClienteTO.setCliTipoId(tipoIdClienteExcel.charAt(0));
                    verificarTipoId = true;
                } else {
                    listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, con Tipo ID: <strong class='pl-2'>'"
                            + tipoIdClienteExcel + "'</strong> no existente.<br>");
                }
                invClienteTO.setCliPrecio((short) 0);/// 
                invClienteTO.setCliDiasCredito((short) 0);///
                invClienteTO.setCliCupoCredito(new BigDecimal(0));/// 
                String codigo = clientesExcel.get(i).getCliCodigoEstablecimiento();
                if (codigo != null || !codigo.equals("")) {
                    invClienteTO.setCliCodigoEstablecimiento(clientesExcel.get(i).getCliCodigoEstablecimiento());
                    validarCodigoEstablecimiento = true;
                }
                // Buscar por Nombre del cliente
                if (clienteDao.getClienteRepetido("'" + clientesExcel.get(i).getEmpCodigo() + "'", (insertar ? null : "'" + clientesExcel.get(i).getCliCodigo() + "'"), null, (clientesExcel.get(i).getCliNombreComercial() == null ? null : "'" + clientesExcel.get(i).getCliNombreComercial() + "'"), null)) {
                    listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, tiene un nombre comercial: <strong class='pl-2'>'" + clientesExcel.get(i).getCliNombreComercial() + "'</strong>, ya existente. Intente con otro Nombre comercial.");
                } else {
                    invClienteTO.setCliNombreComercial(clientesExcel.get(i).getCliNombreComercial() != null ? clientesExcel.get(i).getCliNombreComercial().toUpperCase() : null);
                    ProvNombreComercialUnico = true;
                } // BUSCAR POR RAZONSOCIAL
                if (clienteDao.getClienteRepetido("'" + clientesExcel.get(i).getEmpCodigo() + "'", (insertar ? null : "'" + clientesExcel.get(i).getCliCodigo() + "'"), "", null, (clientesExcel.get(i).getCliRazonSocial().trim().isEmpty() ? null : "'" + clientesExcel.get(i).getCliRazonSocial() + "'"))) {
                    listaMensajesEnviar.add("FCliente: <strong class='pl-2'>" + identificacion + "</strong>, tiene razon social: <strong class='pl-2'>'" + clientesExcel.get(i).getCliRazonSocial() + "'</strong>, ya existente.\nIntente ingresando otra Razón Social.");
                } else {
                    invClienteTO.setCliRazonSocial(clientesExcel.get(i).getCliRazonSocial() != null ? clientesExcel.get(i).getCliRazonSocial().toUpperCase() : null);
                    verificarRazonSocialUnica = true;
                }
                // valido valores unicos y correctos de cliente
                if (verificarGrupoEmpresarial && verificarTipoId && verificarTipoContribuyente_Categoria && verificarRazonSocialUnica && ProvNombreComercialUnico && verificarClienteVendedor && verificarProvincia && validarCodigoEstablecimiento) {
                    // obtengo valor de proveedorCodigo
                    codigoClienteIncrementado = getCodigoClienteIncrementado(codigoClienteIncrementado, invClienteTO);
                    // seteo proveedorCodigo
                    invClienteTO.setCliCodigo(codigoClienteIncrementado);
                    // AÑANDO PROVEEDOR a LISTA
                    listaClientes.add(invClienteTO);
                }

            }
        }
        mapResultadoEnviar = new HashMap<String, Object>();
        mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
        mapResultadoEnviar.put("listaClientes", listaClientes);
        return mapResultadoEnviar;
    }

    public String getCodigoClienteIncrementado(String codigoIncrementado, InvClienteTO cliente) throws Exception {
        // obtengo codigo cliente por 1ra y unica vez
        if (codigoIncrementado.equals("")) {
            codigoIncrementado = clienteDao.getInvProximaNumeracionCliente(cliente.getEmpCodigo(), cliente);
        } // incremento codigo cliente anteriormente obtenido
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
            } while (clienteDao.buscarInvCliente(cliente.getEmpCodigo(), rellenarConCeros + numeracion) != null); // "buscarInvProveedor" to "clienteDao.buscarConteoCliente"
            codigoIncrementado = rellenarConCeros + numeracion;
        }
        return codigoIncrementado;
    }

    @Override
    public boolean actualizarGrupoEnCliente(String empresa, String codigoCliente, String codigoGrupoEmp, SisInfoTO sisInfoTO) throws Exception {
        //suceso
        //PREPARANDO OBJETO SISSUCESO
        susClave = codigoCliente;
        susDetalle = "Se actualizó grupo empresarial con codigo: " + codigoGrupoEmp + " del cliente " + codigoCliente;
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_cliente";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return clienteDao.actualizarGrupoEnCliente(empresa, codigoCliente, codigoGrupoEmp, sisSuceso);
    }

}
