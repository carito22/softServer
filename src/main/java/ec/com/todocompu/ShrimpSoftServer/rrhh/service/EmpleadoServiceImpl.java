package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.PaisDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ProvinciasDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.AnticipoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.CategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.RelacionTrabajoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.helper.NumeroColumnaDesconocidadConsolidadoIngresosTabulado;
import ec.com.todocompu.ShrimpSoftServer.rrhh.helper.NumeroColumnaDesconocidadProvisionFechas;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.CedulaRuc;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnulacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAvisoEntradaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCancelarBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhConsolidadoIngresosTabuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaIessTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionFechasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhParametros;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.olap4j.impl.ArrayMap;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private AnticipoMotivoDao anticipoMotivoDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private CategoriaDao categoriaDao;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ProvinciasDao provinciasDao;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private BancoDao bancoDao;
    @Autowired
    private SucesoDao suscesoDao;
    @Autowired
    private PiscinaService piscinaService;
    @Autowired
    public ParametrosService parametrosService;
    @Autowired
    public RelacionTrabajoDao relacionTrabajoDao;
    @Autowired
    private EmpleadoDescuentoFijoService empleadoDescuentoFijoService;
    @Autowired
    private SectorService sectorService;
    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarRhEmpleado(RhEmpleadoTO rhEmpleadoTO,
            List<RhEmpleadoDescuentosFijosTO> ListarhEmpleadoDescuentosFijosTO, SisInfoTO sisInfoTO) throws Exception {
        mensaje = "";
        RhEmpleado rhEmpleado = empleadoDao.buscarEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId());
        if (rhEmpleado == null) {
            if (!empleadoDao.repetidoRhEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId(),
                    rhEmpleadoTO.getEmpApellidos(), rhEmpleadoTO.getEmpNombres())) {

                List<RhEmpleadoDescuentosFijos> listRhEmpleadoDescuentosFijos = new ArrayList<RhEmpleadoDescuentosFijos>();
                RhEmpleadoDescuentosFijos obj = null;
                boolean band = true;
                RhAnticipoMotivo rhAnticipoMotivo = null;
                for (RhEmpleadoDescuentosFijosTO rhEmpleadoDescuentosFijosTO : ListarhEmpleadoDescuentosFijosTO) {
                    // obj =
                    // rrhhDao.getRhEmpleadoDescuentosFijos(rhEmpleadoDescuentosFijosTO.getDescSecuencial());
                    rhAnticipoMotivo = anticipoMotivoDao.getRhAnticipoMotivo(
                            rhEmpleadoDescuentosFijosTO.getMot_empresa(),
                            rhEmpleadoDescuentosFijosTO.getMot_detalle());
                    if (rhAnticipoMotivo != null) {
                        obj = ConversionesRRHH.convertirRhEmpleadoDescuentosFijosTO_RhEmpleadoDescuentosFijos(
                                rhEmpleadoDescuentosFijosTO);
                        listRhEmpleadoDescuentosFijos.add(obj);
                    } else {
                        band = false;
                        break;
                    }
                }
                if (band) {
                    ///// CREANDO Suceso
                    susClave = rhEmpleadoTO.getEmpId();
                    susDetalle = "Se ingresa a " + rhEmpleadoTO.getEmpApellidos() + " "
                            + rhEmpleadoTO.getEmpNombres();
                    susSuceso = "INSERT";
                    susTabla = "recursoshumanos.rh_empleado";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);

                    rhEmpleadoTO.setUsrFechaInsertaEmpleado(UtilsValidacion.fechaSistema());
                    rhEmpleado = ConversionesRRHH.convertirRhEmpleadoTO_RhEmpleado(rhEmpleadoTO);
                    rhEmpleado.setEmpTurismo(rhEmpleado.getEmpTurismo() != null ? rhEmpleado.getEmpTurismo() : BigDecimal.ZERO);
                    rhEmpleado.setEmpPensionesAlimenticias(rhEmpleado.getEmpPensionesAlimenticias() != null ? rhEmpleado.getEmpPensionesAlimenticias() : BigDecimal.ZERO);
                    rhEmpleado.setEmpDescuentoPrestamos(rhEmpleado.getEmpDescuentoPrestamos() != null ? rhEmpleado.getEmpDescuentoPrestamos() : BigDecimal.ZERO);
                    comprobar = empleadoDao.insertarRhEmpleado(rhEmpleado, listRhEmpleadoDescuentosFijos,
                            sisSuceso);
                    mensaje = "TSe ingresó el empleado..";
                } else {
                    mensaje = "FUno de los MOTIVO DE DESCUENTO ya no se encuentra disponible, inserte de NUEVO o cantactese con su administrador..";
                }
            } else {
                mensaje = "FEl empleado " + rhEmpleadoTO.getEmpApellidos() + " " + rhEmpleadoTO.getEmpNombres()
                        + " ya existe..";
            }
        } else {
            mensaje = "FEl ID del empleado ya existe..";
        }
        return mensaje;
    }

    @Override
    public String modificarRhEmpleado(RhEmpleadoTO rhEmpleadoTO, List<RhEmpleadoDescuentosFijosTO> listaModificar,
            List<RhEmpleadoDescuentosFijosTO> listaEliminar, SisInfoTO sisInfoTO) throws Exception {
        mensaje = "";
        Timestamp usrFechaInserta;
        RhEmpleado rhEmpleado = empleadoDao.buscarEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId());
        if (rhEmpleado != null) {
            if (!empleadoDao.repetidoRhEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId(),
                    rhEmpleadoTO.getEmpApellidos(), rhEmpleadoTO.getEmpNombres())) {

                List<RhEmpleadoDescuentosFijos> listModificarEntity = new ArrayList<RhEmpleadoDescuentosFijos>();
                List<RhEmpleadoDescuentosFijos> listEliminarEntity = new ArrayList<RhEmpleadoDescuentosFijos>();
                RhEmpleadoDescuentosFijos obj = null;
                RhAnticipoMotivo rhAnticipoMotivo = null;
                boolean band = true;
                for (RhEmpleadoDescuentosFijosTO rhEmpleadoDescuentosFijosTO : listaModificar) {
                    rhAnticipoMotivo = anticipoMotivoDao.getRhAnticipoMotivo(
                            rhEmpleadoDescuentosFijosTO.getMot_empresa(),
                            rhEmpleadoDescuentosFijosTO.getMot_detalle());
                    if (rhAnticipoMotivo != null) {
                        obj = ConversionesRRHH.convertirRhEmpleadoDescuentosFijosTO_RhEmpleadoDescuentosFijos(
                                rhEmpleadoDescuentosFijosTO);
                        listModificarEntity.add(obj);
                    } else {
                        band = false;
                        break;
                    }
                }
                for (RhEmpleadoDescuentosFijosTO rhEmpleadoDescuentosFijosTO : listaEliminar) {
                    rhAnticipoMotivo = anticipoMotivoDao.getRhAnticipoMotivo(
                            rhEmpleadoDescuentosFijosTO.getMot_empresa(),
                            rhEmpleadoDescuentosFijosTO.getMot_detalle());
                    if (rhAnticipoMotivo != null) {
                        obj = ConversionesRRHH.convertirRhEmpleadoDescuentosFijosTO_RhEmpleadoDescuentosFijos(
                                rhEmpleadoDescuentosFijosTO);
                        listEliminarEntity.add(obj);
                    } else {
                        band = false;
                        break;
                    }
                }

                if (band) {
                    usrFechaInserta = rhEmpleado.getUsrFechaInserta();
                    ///// CREANDO Suceso
                    susClave = rhEmpleadoTO.getEmpId();
                    susDetalle = "Se modifica a " + rhEmpleadoTO.getEmpApellidos() + " "
                            + rhEmpleadoTO.getEmpNombres();
                    susSuceso = "UPDATE";
                    susTabla = "recursoshumanos.rh_empleado";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);

                    rhEmpleado = ConversionesRRHH.convertirRhEmpleadoTO_RhEmpleado(rhEmpleadoTO);
                    rhEmpleado.setUsrFechaInserta(usrFechaInserta);
                    rhEmpleado.setEmpTurismo(rhEmpleado.getEmpTurismo() != null ? rhEmpleado.getEmpTurismo() : BigDecimal.ZERO);
                    rhEmpleado.setEmpPensionesAlimenticias(rhEmpleado.getEmpPensionesAlimenticias() != null ? rhEmpleado.getEmpPensionesAlimenticias() : BigDecimal.ZERO);
                    rhEmpleado.setEmpDescuentoPrestamos(rhEmpleado.getEmpDescuentoPrestamos() != null ? rhEmpleado.getEmpDescuentoPrestamos() : BigDecimal.ZERO);
                    if (empleadoDao.getSwInactivaEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId())
                            || (!empleadoDao.getSwInactivaEmpleado(rhEmpleadoTO.getEmpCodigo(),
                                    rhEmpleadoTO.getEmpId()) && !rhEmpleadoTO.getEmpInactivo())) {
                        comprobar = empleadoDao.modificarRhEmpleado(rhEmpleado, listModificarEntity,
                                listEliminarEntity, sisSuceso);
                        mensaje = "TSe modificó el empleado..";
                    } else {
                        mensaje = "FNo se puede inactivar el empleado, tiene valores pendientes..";
                    }
                } else {
                    mensaje = "FUno de los MOTIVO DE DESCUENTO ya no se encuentra disponible, inserte de NUEVO o cantactese con su administrador..";
                }
            } else {
                mensaje = "FEl empleado " + rhEmpleadoTO.getEmpApellidos() + " " + rhEmpleadoTO.getEmpNombres()
                        + " ya existe..";
            }
        } else {
            mensaje = "FEl ID del empleado no 1 existe..";
        }
        return mensaje;
    }

    @Override
    public String eliminarRhEmpleado(RhEmpleadoTO rhEmpleadoTO, List<RhEmpleadoDescuentosFijosTO> listaEliminar,
            SisInfoTO sisInfoTO) throws Exception {
        mensaje = "";
        RhEmpleado rhEmpleado = empleadoDao.buscarEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId());
        if (empleadoDao.eliminarRhEmpleado(rhEmpleadoTO.getEmpCodigo(), rhEmpleadoTO.getEmpId()) == true) {
            List<RhEmpleadoDescuentosFijos> listEliminarEntity = new ArrayList<RhEmpleadoDescuentosFijos>();
            RhEmpleadoDescuentosFijos obj = null;
            for (RhEmpleadoDescuentosFijosTO rhEmpleadoDescuentosFijosTO : listaEliminar) {
                obj = ConversionesRRHH.convertirRhEmpleadoDescuentosFijosTO_RhEmpleadoDescuentosFijos(
                        rhEmpleadoDescuentosFijosTO);
                listEliminarEntity.add(obj);
            }
            ///// CREANDO Suceso
            susClave = rhEmpleadoTO.getEmpId();
            susDetalle = "Se elimina a " + rhEmpleadoTO.getEmpApellidos() + " " + rhEmpleadoTO.getEmpNombres();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_empleado";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhEmpleadoTO.setUsrFechaInsertaEmpleado(UtilsValidacion.fechaSistema());
            rhEmpleado = ConversionesRRHH.convertirRhEmpleadoTO_RhEmpleado(rhEmpleadoTO);
            comprobar = empleadoDao.eliminarRhEmpleado(rhEmpleado, listEliminarEntity, sisSuceso);
            mensaje = "TSe eliminó el empleado..";
        } else {
            mensaje = "FEl registro no se puede Eliminar puesto que tiene referencias1..";
        }
        return mensaje;
    }

    @Override
    public List<RhEmpleadoTO> getListaRhEmpleadoTO(String empresa, String buscar, boolean estado) throws Exception {
        return empleadoDao.getListaEmpleadoTO(empresa, buscar, estado);
    }

    @Override
    public List<RhListaEmpleadoTO> getListaConsultaEmpleadoTO(String empresa, String buscar, Boolean interno,
            boolean estado) throws Exception {
        return empleadoDao.getListaConsultaEmpleado(empresa, buscar, interno, estado);
    }

    @Override
    public List<RhFunListadoEmpleadosTO> getRhFunListadoEmpleadosTO(String empresa, String provincia, String canton,
            String sector, String categoria, boolean estado) throws Exception {
        return empleadoDao.getRhFunListadoEmpleadosTO(empresa, provincia, canton, sector, categoria, estado);
    }

    @Override
    public String accionRhAvisosEntrada(RhAvisoEntradaTO rhAvisoEntradaTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        RhEmpleado rhEmpleado = empleadoDao.buscarEmpleado(rhAvisoEntradaTO.getEmpCodigo(),
                rhAvisoEntradaTO.getEmpId());
        if (rhEmpleado != null) {
            if (!rhEmpleado.getEmpInactivo()) {
                ///// CREANDO Suceso
                susClave = rhAvisoEntradaTO.getEmpId();
                if (accion == 'I') {
                    susDetalle = "Se insertó Aviso Entrada para el empleado " + rhAvisoEntradaTO.getEmpNombres()
                            + ":" + rhAvisoEntradaTO.getEmpId();
                    susSuceso = "INSERT";
                }
                if (accion == 'M') {
                    susDetalle = "Se modificó Aviso Entrada para el empleado " + rhAvisoEntradaTO.getEmpNombres()
                            + ":" + rhAvisoEntradaTO.getEmpId();
                    susSuceso = "UPDATE";
                }
                if (accion == 'E') {
                    susDetalle = "Se eliminó Aviso Entrada para el empleado " + rhAvisoEntradaTO.getEmpNombres()
                            + ":" + rhAvisoEntradaTO.getEmpId();
                    susSuceso = "DELETE";
                }
                susTabla = "recursoshumanos.rh_empleado";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                ///// CREANDO RhEmpleado
                RhEmpleadoTO rhEmpleadoTO = ConversionesRRHH.convertirRhEmpleado_RhEmpleadoTO(rhEmpleado);
                rhEmpleadoTO.setEmpCodigoAfiliacionIess(rhAvisoEntradaTO.getEmpCodigoAfiliacionIess());
                rhEmpleadoTO.setEmpCodigoCargo(rhAvisoEntradaTO.getEmpCodigoCargoIess());
                rhEmpleadoTO.setEmpFechaAfiliacionIess(rhAvisoEntradaTO.getEmpFechaAfiliacionIess());
                rhEmpleadoTO.setEmpSueldoIess(rhAvisoEntradaTO.getEmpSueldoIess());
                RhEmpleado rhEmpleadoAux = ConversionesRRHH.convertirRhEmpleadoTO_RhEmpleado(rhEmpleadoTO);
                comprobar = empleadoDao.accionRhAvisoEntrada(rhEmpleadoAux, sisSuceso, accion);
                if (comprobar) {
                    if (accion == 'E') {
                        mensaje = "TSe eliminó correctamente Aviso Entrada:\n" + rhAvisoEntradaTO.getEmpNombres()
                                + ":" + rhAvisoEntradaTO.getEmpId();
                    }
                    if (accion == 'M') {
                        mensaje = "TSe modificó correctamente Aviso Entrada:\n" + rhAvisoEntradaTO.getEmpNombres()
                                + ":" + rhAvisoEntradaTO.getEmpId();
                    }
                    if (accion == 'I') {
                        mensaje = "TSe ingresó correctamente Aviso Entrada:\n" + rhAvisoEntradaTO.getEmpNombres()
                                + ":" + rhAvisoEntradaTO.getEmpId();
                    }
                }
            } else {
                mensaje = "FEmpleado " + rhAvisoEntradaTO.getEmpNombres() + ":" + rhAvisoEntradaTO.getEmpId()
                        + ", esta inactivo..";
            }
        } else {
            mensaje = "FNo existe el empleado " + rhAvisoEntradaTO.getEmpNombres() + ":"
                    + rhAvisoEntradaTO.getEmpId() + "...";
        }
        return mensaje;
    }

    @Override
    public boolean getRhEmpleadoRetencion(String empCodigo, String empId) throws Exception {
        return empleadoDao.getRhEmpleadoRetencion(empCodigo, empId);
    }

    @Override
    public RhCtaIessTO buscarCtaRhIess(String empCodigo, String empId) throws Exception {
        return empleadoDao.buscarCtaRhIess(empCodigo, empId);
    }

    @Override
    public RhCancelarBeneficioSocialTO getRhCancelarBeneficioSocialTO(String empCodigo, String empId) throws Exception {
        return empleadoDao.getRhCancelarBeneficioSocialTO(empCodigo, empId);
    }

    @Override
    public BigDecimal getRhValorImpuestoRenta(String empCodigo, String empId, String fechaHasta, Integer diasLaborados,
            BigDecimal rolIngresos, BigDecimal rolExtras, BigDecimal rolIngresosExento) throws Exception {
        return empleadoDao.getRhValorImpuestoRenta(empCodigo, empId, fechaHasta, diasLaborados, rolIngresos, rolExtras, rolIngresosExento);
    }

    @Override
    public List<RhListaProvisionesTO> getRhListaProvisionesTO(String empresa, String periodo, String sector,
            String status) throws Exception {
        return empleadoDao.getRhListaProvisionesTO(empresa, periodo, sector, status);
    }

    @Override
    public List<RhListaProvisionesTO> getRhListaProvisionesComprobanteContableTO(String empresa, String periodo,
            String tipo, String numero) throws Exception {
        return empleadoDao.getRhListaProvisionesComprobanteContableTO(empresa, periodo, tipo, numero);
    }

    @Override
    public BigDecimal getValorRecalculadoIR(BigDecimal valor, String desde, String hasta) throws Exception {
        return empleadoDao.getValorRecalculadoIR(valor, desde, hasta);
    }

    @Override
    public boolean getSwInactivaEmpleado(String empresa, String empleado) throws Exception {
        return empleadoDao.getSwInactivaEmpleado(empresa, empleado);
    }

    @Override
    public List<RhEmpleadoDescuentosFijosTO> getRhEmpleadoDescuentosFijosTO(String empresa, String empresaID) throws Exception {
        return empleadoDao.getRhEmpleadoDescuentosFijosTO(empresa, empresaID);
    }

    @Override
    public String guardarImagenEmpleado(byte[] imagen, String nombre) throws Exception {
        return UtilsArchivos.guardarImagen(UtilsArchivos.getRutaImagenEmpleado(), imagen, nombre, 150, 100);
    }

    @Override
    public String eliminarImagenEmpleado(String nombre) throws Exception {
        mensaje = "";
        if (UtilsArchivos.eliminarArchivo(UtilsArchivos.getRutaImagenEmpleado() + nombre + ".jpg")) {
            mensaje = "Imagen Eliminada con exito..";
        } else {
            mensaje = "No tiene una Imagen ah eliminar..";
        }
        return mensaje;
    }

    @Override
    public byte[] obtenerImagenEmpleado(String nombre) throws Exception {
        InputStream is = UtilsArchivos.leerImagen(UtilsArchivos.getRutaImagenEmpleado() + nombre + ".jpg");
        byte[] bytes = null;
        if (is != null) {
            bytes = new byte[is.available()];
            is.read(bytes);
        }
        return bytes;
    }

    @Override
    public String obtenerRutaImagenEmpleado(String nombre) throws Exception {
        return UtilsArchivos.getRutaImagenEmpleado() + nombre + ".jpg";
    }

    @Override
    public List<RhAnulacionesTO> getRhAnulacionesTO(String empresa, String periodo, String tipo, String numero) throws Exception {
        return empleadoDao.getRhAnulacionesTO(empresa, periodo, tipo, numero);
    }

    @Override
    public RetornoTO getConsolidadoIngresosTabulado(String empresa, String codigoSector, String fechaInicio, String fechaFin, String usuario) throws Exception {
        String mensaje = "";
        RetornoTO retornoTO = new RetornoTO();
        List<RhConsolidadoIngresosTabuladoTO> rhConsolidadoIngresosTabuladoTOs = empleadoDao.getConsolidadoIngresosTabulado(empresa, codigoSector, fechaInicio, fechaFin);
        mensaje = "T";
        NumeroColumnaDesconocidadConsolidadoIngresosTabulado obj = new NumeroColumnaDesconocidadConsolidadoIngresosTabulado();

        obj.agruparCabeceraColumnas(rhConsolidadoIngresosTabuladoTOs);
        obj.agruparCuentas(rhConsolidadoIngresosTabuladoTOs);
        obj.llenarObjetoParaTabla(rhConsolidadoIngresosTabuladoTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public RetornoTO getProvisionPorFechas(String empresa, String codigoSector, String fechaInicio, String fechaFin, String agrupacion) throws Exception {
        String mensaje = "";
        RetornoTO retornoTO = new RetornoTO();
        List<RhProvisionFechasTO> rhProvisionFechasTOs = empleadoDao.getProvisionPorFechas(empresa, codigoSector,
                fechaInicio, fechaFin, agrupacion);
        mensaje = "T";
        NumeroColumnaDesconocidadProvisionFechas obj = new NumeroColumnaDesconocidadProvisionFechas();

        obj.agruparCabeceraColumnas(rhProvisionFechasTOs);
        obj.agruparCuentas(rhProvisionFechasTOs);
        obj.llenarObjetoParaTabla(rhProvisionFechasTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public List<RhProvisionDetalladoTO> listarProvisionesPorEmpleado(String empresa, String codigoSector, String fechaInicio, String fechaFin, boolean mostrarSaldos, String trabajador) throws Exception {
        return empleadoDao.listarProvisionesPorEmpleado(empresa, codigoSector, fechaInicio, fechaFin, mostrarSaldos, trabajador);
    }

    @Override
    public List<RhEmpleado> getListaEmpleado(String empresa, String buscar, boolean estado) throws Exception {
        return empleadoDao.getListaEmpleado(empresa, buscar, estado);
    }

    @Override
    public RhEmpleado getEmpleado(String empCodigo, String empId) throws Exception {
        return empleadoDao.buscarEmpleado(empCodigo, empId);
    }

    @Override
    public MensajeTO insertarModificarRhEmpleado(RhEmpleado rhEmpleado, List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        boolean insertaModifica = rhEmpleado.getUsrCodigo() == null || rhEmpleado.getUsrCodigo().compareToIgnoreCase("") == 0 ? true : false;
        RhEmpleado rhEmpleadoAux = empleadoDao.buscarEmpleado(rhEmpleado.getRhEmpleadoPK().getEmpEmpresa(),
                rhEmpleado.getRhEmpleadoPK().getEmpId());
        if (empleadoDao.repetidoRhEmpleado(rhEmpleado.getRhEmpleadoPK().getEmpEmpresa(),
                rhEmpleado.getRhEmpleadoPK().getEmpId(), rhEmpleado.getEmpApellidos(),
                rhEmpleado.getEmpNombres())) {
            retorno = "FEl empleado " + rhEmpleado.getEmpApellidos() + " " + rhEmpleado.getEmpNombres()
                    + " ya existe.";
        } else {
            sisInfoTO.setEmpresa(rhEmpleado.getRhEmpleadoPK().getEmpEmpresa());

            susClave = rhEmpleado.getRhEmpleadoPK().getEmpId();
            susDetalle = "Se " + (insertaModifica ? "ingresa" : "modifica") + " a " + rhEmpleado.getEmpApellidos()
                    + " " + rhEmpleado.getEmpNombres();
            susSuceso = "INSERT";
            susTabla = "recursoshumanos.rh_empleado";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = insertaModifica ? "ingreso" : "modifico";
            if (insertaModifica) {
                if (rhEmpleadoAux != null) {
                    retorno = "FEl ID del empleado ya existe..";
                } else {
                    rhEmpleado.setUsrEmpresa(sisInfoTO.getEmpresa());
                    rhEmpleado.setUsrCodigo(sisInfoTO.getUsuario());
                    rhEmpleado.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

                    if (!empleadoDao.insertarModificarRhEmpleado(rhEmpleado, listEmpleadoDescuentosFijos, sisSuceso)) {
                        retorno = "FHubo un error al guardar el empleado.";
                    } else {
                        retorno = "TEl empleado " + rhEmpleado.getEmpApellidos() + " " + rhEmpleado.getEmpNombres() + " se " + mensaje + " correctamente.";
                        mensajeTO.setFechaCreacion(rhEmpleado.getUsrFechaInserta().toString());
                        mensajeTO.getMap().put("rhEmpleado", rhEmpleado);
                    }
                }
            } else if (rhEmpleadoAux == null) {
                retorno = "FEl ID del empleado no existe..";
            } else {
                rhEmpleado.setRhEmpleadoDescuentosFijosList(rhEmpleadoAux.getRhEmpleadoDescuentosFijosList());
                if (!empleadoDao.insertarModificarRhEmpleado(rhEmpleado, listEmpleadoDescuentosFijos,
                        sisSuceso)) {
                    retorno = "FHubo un error al guardar el empleado.";
                } else {
                    retorno = "TEl empleado " + rhEmpleado.getEmpApellidos() + " " + rhEmpleado.getEmpNombres() + " se " + mensaje + " correctamente.";
                    mensajeTO.setFechaCreacion(rhEmpleado.getUsrFechaInserta().toString());
                    mensajeTO.getMap().put("rhEmpleado", rhEmpleado);
                }
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO cambiarEstadoRhEmpleado(RhEmpleadoPK pk, boolean activar, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class, pk);
        if (empleado != null) {
            susClave = pk.getEmpId();
            susDetalle = "Se  modifica  a " + empleado.getEmpApellidos()
                    + " " + empleado.getEmpNombres();
            susSuceso = "UPDATE";
            susTabla = "recursoshumanos.rh_empleado";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            empleado.setEmpInactivo(!activar);
            empleadoDao.saveOrUpdate(empleado);
            suscesoDao.insertar(sisSuceso);
            retorno = "TEl empleado " + empleado.getEmpApellidos() + " " + empleado.getEmpNombres() + " se ha " + (activar ? "activado" : "inactivado") + " correctamente";
        } else {
            retorno = "FEl empleado no se ha " + (activar ? "activado" : "inactivado") + " correctamente";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String eliminarRhEmpleado(RhEmpleadoPK rhEmpleadoPK, SisInfoTO sisInfoTO) throws Exception {
        mensaje = "";
        sisInfoTO.setEmpresa(rhEmpleadoPK.getEmpEmpresa());

        if (!empleadoDao.eliminarRhEmpleado(rhEmpleadoPK.getEmpEmpresa(), rhEmpleadoPK.getEmpId()) == true) {
            mensaje = "FEl registro no se puede Eliminar puesto que tiene referencias..";
        } else {
            RhEmpleado rhEmpleado = empleadoDao.buscarEmpleado(rhEmpleadoPK.getEmpEmpresa(),
                    rhEmpleadoPK.getEmpId());

            susDetalle = "Se elimina a " + rhEmpleado.getEmpApellidos() + " " + rhEmpleado.getEmpNombres();
            susClave = rhEmpleado.getRhEmpleadoPK().getEmpId();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_empleado";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            if (empleadoDao.eliminarRhEmpleado(rhEmpleado, rhEmpleado.getRhEmpleadoDescuentosFijosList(),
                    sisSuceso)) {
                mensaje = "TSe eliminó el empleado.";
            } else {
                mensaje = "FHubo un error al guardar el sector. Intente de nuevo o contacte con el administrador";
            }
        }
        return mensaje;
    }

    @Override
    public List<RhListaEmpleadoLoteTO> getListaEmpleadoLote(String empresa, String categoria, String sector, String fechaHasta, String motivo, boolean rol) {
        return empleadoDao.getListaEmpleadoLote(empresa, categoria, sector, fechaHasta, motivo, rol);
    }

    @Override
    public Map<String, Object> obtenerDatosParaBusquedaEmpleados(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<PrdListaSectorTO> sectores = sectorDao.getListaSector(empresa, true);
        List<RhComboCategoriaTO> categorias = categoriaDao.getComboCategoria(empresa);
        List<AnxProvinciaCantonTO> provincias = provinciasDao.getComboAnxProvinciaTO();
        if (provincias != null && !provincias.isEmpty()) {
            List<AnxProvinciaCantonTO> cantones = provinciasDao.getComboAnxCantonTO(provincias.get(0).getCodigo());
            respuesta.put("cantones", cantones);
        }
        respuesta.put("sectores", sectores);
        respuesta.put("categorias", categorias);
        respuesta.put("provincias", provincias);
        return respuesta;
    }

    @Override
    public List<RhListaBonosLoteTO> getListaRhBonos(String empresa, String categoria, String sector, String fechaHasta, String motivo, boolean rol, List<PrdListaPiscinaTO> listadoPiscinas) throws Exception {
        List<RhListaEmpleadoLoteTO> listado = empleadoDao.getListaEmpleadoLote(empresa, categoria, sector, fechaHasta, motivo, rol);
        List<RhListaBonosLoteTO> listadoBonos = new ArrayList<RhListaBonosLoteTO>();
        for (int i = 0; i < listado.size(); i++) {
            RhListaBonosLoteTO bono = new RhListaBonosLoteTO();
            bono.setDeducible(true);
            bono.setConcepto(null);
            bono.setPiscina(null);
            bono.setIsValorValido(true);
            bono.rhListaEmpleadoLoteTO = listado.get(i);
            if (listadoPiscinas == null) {
                List<PrdListaPiscinaTO> listaPiscinas = piscinaService.getListaPiscinaTO(empresa.trim(), bono.getRhListaEmpleadoLoteTO().getPrSector(), false);
                bono.setListaPiscinas(listaPiscinas);
            } else {
                bono.setListaPiscinas(listadoPiscinas);
            }
            listadoBonos.add(bono);
        }
        return listadoBonos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudEmpleados(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String empId = UtilsJSON.jsonToObjeto(String.class, map.get("empId"));

        List<AnxProvinciaCantonTO> provincias = provinciasDao.getComboAnxProvinciaTO();
        List<AnxPaisTO> paises = paisDao.getComboAnxPaisTO();
        AnxPaisTO paisLocal = paisDao.getLocalAnxPaisTO();

        List<ComboGenericoTO> residencias = new ArrayList<>();
        residencias.add(new ComboGenericoTO("01", "LOCAL"));
        residencias.add(new ComboGenericoTO("02", "EXTERIOR"));

        List<ComboGenericoTO> tiposIdentidad = new ArrayList<>();
        ComboGenericoTO identidadLocal = new ComboGenericoTO("C", "CEDULA");
        tiposIdentidad.add(new ComboGenericoTO("P", "PASAPORTE"));
        tiposIdentidad.add(new ComboGenericoTO("I", "IDENTIFICACÓN TRIBUTARIA EXTERIOR"));
        tiposIdentidad.add(new ComboGenericoTO("N", "NO APLICA"));
        List<ComboGenericoTO> tiposIdentidaddiscapacidad = tiposIdentidad;
        tiposIdentidaddiscapacidad.add(identidadLocal);

        List<ComboGenericoTO> generos = new ArrayList<>();
        generos.add(new ComboGenericoTO("F", "FEMENINO"));
        generos.add(new ComboGenericoTO("M", "MASCULINO"));

        List<ComboGenericoTO> estadosCivil = new ArrayList<>();
        estadosCivil.add(new ComboGenericoTO("SOLTERO", "SOLTERO"));
        estadosCivil.add(new ComboGenericoTO("CASADO", "CASADO"));
        estadosCivil.add(new ComboGenericoTO("DIVORCIADO", "DIVORCIADO"));
        estadosCivil.add(new ComboGenericoTO("VIUDO", "VIUDO"));
        estadosCivil.add(new ComboGenericoTO("UNION LIBRE", "UNION LIBRE"));

        List<ComboGenericoTO> motivos = new ArrayList<>();
        motivos.add(new ComboGenericoTO("DESAHUCIO", "DESAHUCIO"));
        motivos.add(new ComboGenericoTO("DESPIDO", "DESPIDO"));
        motivos.add(new ComboGenericoTO("RENUNCIA", "RENUNCIA"));
        motivos.add(new ComboGenericoTO("VISTO BUENO", "VISTO BUENO"));
        motivos.add(new ComboGenericoTO("OTROS", "OTROS"));

        List<ComboGenericoTO> discapacidades = new ArrayList<>();
        discapacidades.add(new ComboGenericoTO("01", "SIN DISCAPACIDAD"));
        discapacidades.add(new ComboGenericoTO("02", "CON DISCAPACIDAD"));
        discapacidades.add(new ComboGenericoTO("03", "SUSTITUTO"));
        discapacidades.add(new ComboGenericoTO("04", "FAMILIAR (ESPOSA(O) HIJOS)"));

        List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
        cuentasBancarias.add(new ComboGenericoTO("03", "CTA CORRIENTE"));
        cuentasBancarias.add(new ComboGenericoTO("04", "CTA AHORRO"));
        cuentasBancarias.add(new ComboGenericoTO("05", "CTA VIRTUAL"));

        Date fechaActual = sistemaWebServicio.getFechaActual();
        RhParametros rhParametros = parametrosService.getRhParametros(UtilsDate.fechaFormatoString(fechaActual, "dd-MM-yyyy"));

        List<ComboGenericoTO> formasDePago = new ArrayList<>();
        formasDePago.add(new ComboGenericoTO("MENSUAL", "MENSUAL"));
        formasDePago.add(new ComboGenericoTO("DIARIO", "DIARIO"));

        List<PrdSector> sectores = sectorDao.getListaSectorPorEmpresa(empresa, false);
        List<RhCategoria> categorias = categoriaService.getListaRhCategoria(empresa);
        List<RhRelacionTrabajo> relacionTrabajo = relacionTrabajoDao.listaRelacionTrabajo();

        List<ListaBanBancoTO> bancos = bancoDao.getListaBanBancoTO(empresa);

        if (empId != null && !empId.equals("")) {
            RhEmpleado empleado = empleadoDao.obtenerPorIdEvict(RhEmpleado.class, new RhEmpleadoPK(empresa, empId));
            respuesta.put("empleado", empleado);
        }

        respuesta.put("provincias", provincias);
        respuesta.put("paises", paises);
        respuesta.put("paisLocal", paisLocal);
        respuesta.put("residencias", residencias);
        respuesta.put("identidadLocal", identidadLocal);
        respuesta.put("tiposIdentidad", tiposIdentidad);
        respuesta.put("tiposIdentidaddiscapacidad", tiposIdentidaddiscapacidad);
        respuesta.put("generos", generos);
        respuesta.put("estadosCivil", estadosCivil);
        respuesta.put("discapacidades", discapacidades);
        respuesta.put("cuentasBancarias", cuentasBancarias);
        respuesta.put("fechaActual", fechaActual);
        respuesta.put("formasDePago", formasDePago);
        respuesta.put("sectores", sectores);
        respuesta.put("categorias", categorias);
        respuesta.put("bancos", bancos);
        respuesta.put("motivos", motivos);
        respuesta.put("rhParametros", rhParametros);
        respuesta.put("relacionTrabajo", relacionTrabajo);

        return respuesta;
    }

    @Override
    public Map<String, Object> obtenerComplementosEmpleado(Map<String, Object> parametros) throws Exception {
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, parametros.get("empleado"));
        String apellidos = UtilsJSON.jsonToObjeto(String.class, parametros.get("apellidos"));
        String nombres = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombres"));
        Map<String, Object> respuesta = new ArrayMap<>();
        List<RhEmpleadoDescuentosFijos> descuentos = empleadoDescuentoFijoService.getEmpleadoDescuentosFijos(empresa, empleado);
        String nombreArchivo = empleado + "_" + apellidos + "_" + nombres;
        byte[] imagen = obtenerImagenEmpleado(nombreArchivo);
        if (imagen != null) {
            String foto = Base64.getEncoder().encodeToString(imagen);
            respuesta.put("foto", foto);
        }
        respuesta.put("descuentos", descuentos);
        return respuesta;
    }

    @Override
    public void guardarImagenEmpleado(byte[] imagen, RhEmpleado rhEmpleado) throws Exception {
        String nombreArchivo = rhEmpleado.getRhEmpleadoPK().getEmpId() + "_" + rhEmpleado.getEmpApellidos() + "_" + rhEmpleado.getEmpNombres();
        if (imagen != null) {
            UtilsArchivos.guardarImagen(UtilsArchivos.getRutaImagenEmpleado(), imagen, nombreArchivo, 250, 200);
        } else {
            UtilsArchivos.eliminarImagen(UtilsArchivos.getRutaImagenEmpleado(), nombreArchivo);
        }
    }

    @Override
    public String cambiarCedulaEmpleado(String empresa, String cedulaInconrrecta, String cedulaCorrecta) throws Exception {
        String mensaje = "";
        if (empleadoDao.cambiarCedulaEmpleado(empresa, cedulaInconrrecta, cedulaCorrecta)) {
            mensaje = "TLa cédula se cambió correctamente.";
        } else {
            mensaje = "FHubo un error al cambiar la cédula. Intente de nuevo o contacte con el administrador";
        }
        return mensaje;
    }

    //obtener estado saldos empleados
    @Override
    public boolean getReconstruccionSaldosEmpleados(String empresa) throws Exception {
        boolean estado = empleadoDao.getReconstruccionSaldosEmpleados(empresa);
        if (estado == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getInactivaEmpleado(String empresa, String empleado) throws Exception {
        return empleadoDao.getInactivaEmpleado(empresa, empleado);
    }

    public String mensajesErroresExistencias(RhEmpleado empleado) throws Exception {
        List<String> listaMensajesEnviar = new ArrayList<>();
        String mensaje = "";
        if (empleado != null) {
            //evaluar Identificacion
            if (empleado.getRhEmpleadoPK().getEmpId() != null) {
                String respues = "";
                if (empleado.getEmpTipoId() == 'C') {
                    respues = CedulaRuc.comprobacion(empleado.getRhEmpleadoPK().getEmpId());
                    if (respues != null && !respues.equals("T")) {
                        listaMensajesEnviar.add(respues.substring(1));
                    }
                } else {
                    if (empleado.getEmpTipoId() != 'P') {
                        listaMensajesEnviar.add("El tipo de identificación debe ser 'P': pasaporte o 'C': cedula");
                    }
                }
                //evaluar si es empleado repetido
                List<RhEmpleado> empleados = empleadoDao.getListaEmpleado(
                        empleado.getRhEmpleadoPK().getEmpEmpresa(),
                        empleado.getRhEmpleadoPK().getEmpId(),
                        false);
                if (empleados != null && empleados.size() > 0) {
                    listaMensajesEnviar.add("Empleado con identificación: <strong>" + empleado.getRhEmpleadoPK().getEmpId() + "</strong> ya existe");
                }
            }
        }
        //validar genero
        if (empleado.getEmpGenero() != null) {
            if (empleado.getEmpGenero() != 'F' && empleado.getEmpGenero() != 'M') {
                listaMensajesEnviar.add("El género debe ser 'F': femenino o 'M: masculino");
            }
        }
        //validar sector
        if (empleado.getPrdSector() != null && empleado.getPrdSector().getPrdSectorPK().getSecCodigo() != null) {
            PrdSector sector = sectorService.obtenerPorEmpresaSector(empleado.getRhEmpleadoPK().getEmpEmpresa(), empleado.getPrdSector().getPrdSectorPK().getSecCodigo());
            if (sector == null) {
                listaMensajesEnviar.add("El sector:<strong> " + empleado.getPrdSector().getPrdSectorPK().getSecCodigo() + " </strong> no existe.");
            }
        }
        //validar categoria
        if (empleado.getRhCategoria() != null && empleado.getRhCategoria().getRhCategoriaPK().getCatNombre() != null) {
            if (categoriaDao.buscarCategoria(empleado.getRhEmpleadoPK().getEmpEmpresa(), empleado.getRhCategoria().getRhCategoriaPK().getCatNombre()) == null) {
                listaMensajesEnviar.add("La categoría:<strong> " + empleado.getRhCategoria().getRhCategoriaPK().getCatNombre() + " </strong> no existe.");
            }
        }
        //sueldo
        if (empleado.getEmpSueldoIess().compareTo(BigDecimal.ZERO) <= 0) {
            listaMensajesEnviar.add("El sueldo debe ser mayor a 0.00");
        }
        //Relacion trabajo
        if (empleado.getEmpRelacionTrabajo() != null && empleado.getEmpRelacionTrabajo().getRtCodigo() != null) {
            List<RhRelacionTrabajo> relacionTrabajo = relacionTrabajoDao.listaRelacionTrabajo();
            List<RhRelacionTrabajo> filtrado = relacionTrabajo.stream()
                    .filter(item -> item.getRtCodigo().equals(empleado.getEmpRelacionTrabajo().getRtCodigo()))
                    .collect(Collectors.toList());
            if (filtrado == null || filtrado.size() == 0) {
                listaMensajesEnviar.add("El código de relación: " + empleado.getEmpRelacionTrabajo().getRtCodigo() + " de trabajo no existe");
            }
        } else {
            listaMensajesEnviar.add("El código de relación de trabajo no es válido");
        }
        //pais, provincia y canton
        if (empleado.getEmpResidenciaTipo() != null) {
            //Local
            if (empleado.getEmpResidenciaTipo().equals("01")) {
                List<AnxProvinciaCantonTO> provincias = provinciasDao.getComboAnxProvinciaTO();
                if (provincias != null && !provincias.isEmpty()) {
                    //evaluar existe provincia
                    if (empleado.getEmpProvincia() == null) {
                        listaMensajesEnviar.add("La provincia no es valida");
                    }
                    if (empleado.getEmpCanton() == null) {
                        listaMensajesEnviar.add("El cantón no es valida");
                    }
                } else {
                    listaMensajesEnviar.add("No se encontraron provincias registradas.");
                }
                //Exterior
            } else {

            }
        } else {
            listaMensajesEnviar.add("El país residencia es incorrecto.");
        }

        //validacion banco,cuenta y numero
        if (empleado.getEmpBanco() != null && empleado.getEmpCuentaTipo() != null && empleado.getEmpCuentaNumero() != null) {
            List<ListaBanBancoTO> bancos = bancoDao.getListaBanBancoTO(empleado.getRhEmpleadoPK().getEmpEmpresa());
            //Banco
            List<ListaBanBancoTO> filtradoBanco = bancos.stream()
                    .filter(item -> item.getBanCodigo().equals(empleado.getEmpBanco()) || item.getBanNombre().equals(empleado.getEmpBanco()))
                    .collect(Collectors.toList());
            if (filtradoBanco != null && filtradoBanco.size() > 0) {
                //Cuenta tipo
                List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
                cuentasBancarias.add(new ComboGenericoTO("03", "CTA CORRIENTE"));
                cuentasBancarias.add(new ComboGenericoTO("04", "CTA AHORRO"));
                cuentasBancarias.add(new ComboGenericoTO("05", "CTA VIRTUAL"));

                List<ComboGenericoTO> filtradoTipo = cuentasBancarias.stream()
                        .filter(item -> item.getClave().equals(empleado.getEmpCuentaTipo()) || item.getValor().equals(empleado.getEmpCuentaTipo()))
                        .collect(Collectors.toList());
                if (filtradoTipo == null || filtradoTipo.size() == 0) {
                    listaMensajesEnviar.add("El tipo de cuenta: " + empleado.getEmpCuentaTipo() + " no existe.");
                }
            } else {
                listaMensajesEnviar.add("El banco: " + empleado.getEmpBanco() + " no existe.");
            }
        }

        if (listaMensajesEnviar.size() > 0) {
            mensaje = "FEl empleado <strong>" + empleado.getRhEmpleadoPK().getEmpId() + " </strong>, tiene los siguientes errores: <br>";
            for (String item : listaMensajesEnviar) {
                mensaje += item + "<br>";
            }
        }
        return mensaje;
    }

    public String mensajesErroresObligatorios(RhEmpleado empleado) {
        List<String> listaMensajesEnviar = new ArrayList<>();
        String mensaje = "";
        if (empleado != null) {
            if (empleado.getRhEmpleadoPK().getEmpId() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'ID' vacio.");
            }
            if (empleado.getEmpTipoId() == 0) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'TIPO_ID' vacio.");
            }
            if (empleado.getEmpResidenciaPais() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'RESIDENCIA_PAIS' vacio.");
            }
            if (empleado.getEmpApellidos() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'APELLIDOS' vacio.");
            }
            if (empleado.getEmpNombres() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'NOMBRES' vacio.");
            }
            if (empleado.getEmpGenero() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'GENERO' vacio.");
            }
            if (empleado.getEmpFechaNacimiento() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'FECHA_NACIMIENTO' vacio.");
            }
            if (empleado.getEmpProvincia() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'PROVINCIA' vacio.");
            }
            if (empleado.getEmpCanton() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'CANTON' vacio.");
            }
            if (empleado.getEmpLugarNacimiento() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'CIUDAD' vacio.");
            }
            if (empleado.getEmpDomicilio() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'DIRECCION' vacio.");
            }
            if (empleado.getEmpTelefono() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'TELEFONO' vacio.");
            }
            if (empleado.getEmpFechaPrimerIngreso() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'FECHA_PRIMER_INGRESO' vacio.");
            }
            if (empleado.getEmpFechaAfiliacionIess() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'FECHA_AFILIACION_IESS' vacio.");
            }
            if (empleado.getEmpCargo() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'CARGO' vacio.");
            }
            if (empleado.getEmpSueldoIess() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'SUELDO_IESS' vacio.");
            }
            if (empleado.getPrdSector() == null || empleado.getPrdSector().getPrdSectorPK().getSecCodigo() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'SECTOR' vacio.");
            }
            if (empleado.getRhCategoria() == null || empleado.getRhCategoria().getRhCategoriaPK().getCatNombre() == null) {
                listaMensajesEnviar.add("Tiene campo obligatorio 'CATEGORIA' vacio.");
            }

            if (empleado.getEmpBanco() != null || empleado.getEmpCuentaTipo() != null || empleado.getEmpCuentaNumero() != null) {
                if (empleado.getEmpCuentaTipo() == null || empleado.getEmpCuentaNumero() == null || empleado.getEmpBanco() == null) {
                    listaMensajesEnviar.add("Tiene campo obligatorio 'CUENTA_TIPO' y 'CUENTA_NUMERO' vacio.");
                }
            }

            if (empleado.getEmpCodigoAfiliacionIess() != null || empleado.getEmpCodigoCargo() != null) {
                if (empleado.getEmpCodigoAfiliacionIess() == null) {
                    listaMensajesEnviar.add("Tiene campo obligatorio 'NUMERO_REGISTRO_NOVEDAD' vacio.");
                }

                if (empleado.getEmpCodigoCargo() == null) {
                    listaMensajesEnviar.add("Tiene campo obligatorio 'NUMERO_ACTIVIDAD_SECTORIAL' vacio.");
                }
            }

            if (listaMensajesEnviar.size() > 0) {
                mensaje = "FEl empleado <strong>" + empleado.getRhEmpleadoPK().getEmpId() + " </strong>, tiene los siguientes errores: <br>";
                for (String item : listaMensajesEnviar) {
                    mensaje += item + "<br>";
                }
            }
        }
        return mensaje;
    }

    @Override
    public Map<String, Object> verificarExistenciaEmpleados(Map<String, Object> map) throws Exception {
        List<RhEmpleado> empleados = UtilsJSON.jsonToList(RhEmpleado.class, map.get("empleados"));
        //
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<RhEmpleado> empleadosPorCrear = new ArrayList<>();

        for (RhEmpleado empleado : empleados) {
            //residencia tipo
            if (empleado.getEmpResidenciaPais() != null) {
                if (empleado.getEmpResidenciaPais().toUpperCase().contains("ECUA") || empleado.getEmpResidenciaPais().toUpperCase().contains("LOCAL")) {
                    empleado.setEmpResidenciaTipo("01");//Local
                    empleado.setEmpResidenciaPais("593");

                    List<AnxProvinciaCantonTO> provincias = provinciasDao.getComboAnxProvinciaTO();
                    if (provincias != null && !provincias.isEmpty()) {
                        //evaluar existe provincia
                        if (empleado.getEmpProvincia() != null) {
                            List<AnxProvinciaCantonTO> filtrado = provincias.stream()
                                    .filter(item -> item.getNombre().equals(empleado.getEmpProvincia()))
                                    .collect(Collectors.toList());
                            if (filtrado != null && filtrado.size() > 0) {
                                empleado.setEmpProvincia(filtrado.get(0).getCodigo());
                                List<AnxProvinciaCantonTO> cantones = provinciasDao.getComboAnxCantonTO(filtrado.get(0).getCodigo());
                                //evaluar existe canton
                                if (empleado.getEmpCanton() != null) {
                                    List<AnxProvinciaCantonTO> filtradoCant = cantones.stream()
                                            .filter(item -> item.getNombre().equals(empleado.getEmpCanton()))
                                            .collect(Collectors.toList());
                                    if (filtradoCant != null && filtradoCant.size() > 0) {
                                        empleado.setEmpCanton(filtradoCant.get(0).getCodigo());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    empleado.setEmpResidenciaTipo("02");//exterior
                }
            } else {
                empleado.setEmpResidenciaTipo(null);
            }
            String mensajeError = mensajesErroresObligatorios(empleado);
            //No tiene error
            if (mensajeError.isEmpty()) {
                //Evaluar existencia de campos
                String mensajeErrorExistencia = mensajesErroresExistencias(empleado);
                if (mensajeErrorExistencia.isEmpty()) {
                    //******************Crear empleado*************
                    empleadosPorCrear.add(empleado);
                    //*********************************************
                } else {
                    //tiene errores
                    listaMensajesEnviar.add(mensajeErrorExistencia);
                }
            } else {
                //tiene errores
                listaMensajesEnviar.add(mensajeError);
            }
        }

        respuesta.put("listaMensajesEnviar", listaMensajesEnviar);
        respuesta.put("listaEmpleados", empleadosPorCrear);
        return respuesta;
    }

    @Override
    public Map<String, Object> insertarModificarRhEmpleadoLote(Map<String, Object> map) throws Exception {
        List<RhEmpleado> empleados = UtilsJSON.jsonToList(RhEmpleado.class, map.get("empleados"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        //
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<RhEmpleado> empleadosPorCrear = new ArrayList<>();

        for (RhEmpleado empleado : empleados) {
            empleado.setUsrEmpresa(sisInfoTO.getEmpresa());
            //Evaluar existencia de campos
            String mensajeErrorExistencia = mensajesErroresExistencias(empleado);
            if (mensajeErrorExistencia.isEmpty()) {
                //Setear codigos
                //Banco
                if (empleado.getEmpBanco() != null) {
                    List<ListaBanBancoTO> bancos = bancoDao.getListaBanBancoTO(empleado.getRhEmpleadoPK().getEmpEmpresa());
                    List<ListaBanBancoTO> filtradoBanco = bancos.stream()
                            .filter(item -> item.getBanCodigo().equals(empleado.getEmpBanco()) || item.getBanNombre().equals(empleado.getEmpBanco()))
                            .collect(Collectors.toList());
                    if (filtradoBanco != null && filtradoBanco.size() > 0) {
                        empleado.setEmpBanco(filtradoBanco.get(0).getBanCodigo());
                    }
                }
                //cuenta tipo
                if (empleado.getEmpCuentaTipo() != null) {
                    List<ComboGenericoTO> cuentasBancarias = new ArrayList<>();
                    cuentasBancarias.add(new ComboGenericoTO("03", "CTA CORRIENTE"));
                    cuentasBancarias.add(new ComboGenericoTO("04", "CTA AHORRO"));
                    cuentasBancarias.add(new ComboGenericoTO("05", "CTA VIRTUAL"));

                    List<ComboGenericoTO> filtradoTipo = cuentasBancarias.stream()
                            .filter(item -> item.getClave().equals(empleado.getEmpCuentaTipo()) || item.getValor().equals(empleado.getEmpCuentaTipo()))
                            .collect(Collectors.toList());
                    if (filtradoTipo != null && filtradoTipo.size() > 0) {
                        empleado.setEmpCuentaTipo(filtradoTipo.get(0).getClave());
                    }
                }

                //******************Crear empleado*************
                MensajeTO mensajeTO = insertarModificarRhEmpleado(empleado, new ArrayList<RhEmpleadoDescuentosFijos>(), sisInfoTO);
                if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                    RhEmpleado rhEmpleado = (RhEmpleado) mensajeTO.getMap().get("rhEmpleado");
                    empleadosPorCrear.add(rhEmpleado);
                    listaMensajesEnviar.add(mensajeTO.getMensaje().substring(0));
                } else {
                    listaMensajesEnviar.add(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(0) : "FNo se ha guardado trabajador.");
                }
                //*********************************************
            } else {
                //tiene errores
                listaMensajesEnviar.add(mensajeErrorExistencia);
            }
        }
        respuesta.put("listaMensajesEnviar", listaMensajesEnviar);
        respuesta.put("listaEmpleados", empleadosPorCrear);
        return respuesta;
    }

}
