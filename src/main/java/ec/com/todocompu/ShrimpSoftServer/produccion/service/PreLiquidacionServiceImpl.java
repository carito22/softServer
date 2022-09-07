package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PreLiquidacionDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunPreLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Service
public class PreLiquidacionServiceImpl implements PreLiquidacionService {

    @Autowired
    private PreLiquidacionDao preLiquidacionDao;
    @Autowired
    private GenericoDao<PrdPreLiquidacion, PrdPreLiquidacionPK> preLiquidacionCriteriaDao;
    private PrdProductoService prdProductoService;
    @Autowired
    private TallaService tallaService;
    @Autowired
    private PreLiquidacionMotivoService preliquidacionMotivoService;
    @Autowired
    private CorridaService corridaService;
    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public MensajeTO insertarModificarPrdPreLiquidacion(PrdPreLiquidacion prdPreLiquidacion, List<PrdPreLiquidacionDetalle> listaPrdPreLiquidacionDetalle, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if (UtilsValidacion.isFechaSuperior(prdPreLiquidacion.getPlFecha())) {
            retorno = "FLa fecha que ingreso es superior a la fecha actual del servidor. Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy");
        } else {
            sisInfoTO.setEmpresa(prdPreLiquidacion.getPrdPreLiquidacionPK().getPlEmpresa());
            prdPreLiquidacion.setPlAnulado(false);
            prdPreLiquidacion.setPlPendiente(false);
            if (prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero() == null || prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero().compareToIgnoreCase("") == 0) {
                prdPreLiquidacion.setUsrEmpresa(sisInfoTO.getEmpresa());
                prdPreLiquidacion.setUsrCodigo(sisInfoTO.getUsuario());
                prdPreLiquidacion.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
            }
            susSuceso = (prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero() == null || prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "inventario.prd_preliquidacion";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero() == null || prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero().compareToIgnoreCase("") == 0)
                    ? "La preliquidación de pesca: Lote " + prdPreLiquidacion.getPlLote() + ", se ha guardado correctamente."
                    : "La preliquidación de pesca: Lote " + prdPreLiquidacion.getPlLote() + ", se ha modificado correctamente.";
            comprobar = preLiquidacionDao.insertarModificarPrdPreLiquidacion(prdPreLiquidacion, listaPrdPreLiquidacionDetalle, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar la Liquidacion...\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>" + mensaje + " <br> con la siguiente información:<br><br>"
                        + "Motivo: <font size = 5>" + prdPreLiquidacion.getPrdPreLiquidacionPK().getPlMotivo()
                        + "</font>.<br> Numero: <font size = 5>"
                        + prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero() + "</font>.<br></html>";
                mensajeTO.setFechaCreacion(prdPreLiquidacion.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("liquidacion", prdPreLiquidacion);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public PrdPreLiquidacion getPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK) {
        return preLiquidacionDao.getPrdPreLiquidacion(liquidacionPK);
    }

    @Override
    public List<PrdPreLiquidacion> getListaPrdPreLiquidacion(String empresa) {
        return preLiquidacionDao.getListaPrdPreLiquidacion(empresa);
    }

    @Override
    public String desmayorizarPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK) {
        preLiquidacionDao.desmayorizarPrdPreLiquidacion(liquidacionPK);
        return "TLa preliquidación de pesca " + liquidacionPK.getPlNumero() + ", se ha desmayorizado correctamente.";
    }

    @Override
    public String anularRestaurarPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK, boolean anularRestaurar) {
        preLiquidacionDao.anularRestaurarPrdPreLiquidacion(liquidacionPK, anularRestaurar);
        return "TLa preliquidación de pesca se ha " + (anularRestaurar ? "anulado" : "restaurado") + " correctamente. "
                + liquidacionPK.getPlNumero();
    }

    @Override
    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacion(String empresa, String sector, String piscina, String busqueda) throws Exception {
        return preLiquidacionDao.getListaPrdConsultaPreLiquidacion(empresa, sector, piscina, busqueda);
    }

    @Override
    public PrdPreLiquidacionTO getBuscaObjPreLiquidacionPorLote(String plLote) throws Exception {
        return preLiquidacionDao.getBuscaObjPreLiquidacionPorLote(plLote);
    }

    @Override
    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception {
        return preLiquidacionDao.getListaPrdConsultaPreLiquidacionTO(empresa, sector, piscina, busqueda, nRegistros);
    }

    @Override
    public String desmayorizarPrdPreLiquidacionLote(List<ListaPreLiquidacionTO> listado, String empresa) {
        String respuesta = "";
        int contador = 0;
        StringBuilder mensaje = new StringBuilder();
        for (ListaPreLiquidacionTO liquidacion : listado) {
            if (liquidacion.getPlAnulado().equalsIgnoreCase("ANULADO") || liquidacion.getPlPendiente().equalsIgnoreCase("PENDIENTE")) {
                mensaje.append("\n").append("No se puede desmayorizar la pre liquidación de pesca ").append(liquidacion.getPlNumero()).append(" ya ha sido anulada o está pendiente.");
                contador++;
            } else {
                String cadena = desmayorizarPrdPreLiquidacion(new PrdPreLiquidacionPK(empresa, liquidacion.getPlMotivo(), liquidacion.getPlNumero())) + "/";
                mensaje.append("\n").append(cadena.substring(1, cadena.length())).append(".");
                if (cadena.charAt(0) != 'T') {
                    contador++;
                }
                liquidacion.setPlPendiente("PENDIENTE");
            }
        }
        if (contador == 0) {
            respuesta = 'T' + mensaje.toString();
        } else {
            respuesta = mensaje.toString();
        }

        return respuesta;
    }

    @Override
    public PrdPreLiquidacion obtenerPrdPreLiquidacion(PrdPreLiquidacionPK liquidacionPK) {
        return preLiquidacionDao.obtenerPorId(PrdPreLiquidacion.class, liquidacionPK);
    }

    @Override
    public PrdCorrida obtenerCorrida(PrdPreLiquidacionPK pk, SisInfoTO sisInfoTO) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(PrdPreLiquidacion.class);
        filtro.add(Restrictions.eq("prdPreLiquidacionPK.plEmpresa", pk.getPlEmpresa()));
        filtro.add(Restrictions.eq("prdPreLiquidacionPK.plMotivo", pk.getPlMotivo()));
        filtro.add(Restrictions.eq("prdPreLiquidacionPK.plNumero", pk.getPlNumero()));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("prdCorrida.prdCorridaPK"), "prdCorridaPK")
        );
        List<PrdCorrida> c = preLiquidacionCriteriaDao.proyeccionPorCriteria(filtro, PrdCorrida.class);
        PrdCorrida corrida = c.size() > 0 ? c.get(0) : null;

        if (corrida != null) {
            return corrida.getPrdCorridaPK() != null ? corrida : null;
        }
        return null;
    }

    @Override
    public Map<String, Object> obtenerDatosParaPreLiquidacionPesca(String empresa, String piscina, String sector) throws Exception {

        Map<String, Object> campos = new HashMap<>();
        List<PrdTalla> listaTallas = new ArrayList<PrdTalla>();
        List<PrdCorrida> listaCorridas = new ArrayList<PrdCorrida>();
        List<PrdPreLiquidacionMotivo> listaMotivos = new ArrayList<PrdPreLiquidacionMotivo>();

        listaCorridas = corridaService.getListaCorridasPorEmpresaSectorPiscina(empresa, sector, piscina);
        listaTallas = tallaService.getListaPrdLiquidacionTalla(empresa, false);
        listaMotivos = preliquidacionMotivoService.getListaPrdPreLiquidacionMotivoTO(empresa, false);
        campos.put("listaMotivos", listaMotivos);
        campos.put("listadoTallas", listaTallas);
        campos.put("listadoCorridas", listaCorridas);
        return campos;
    }

    //liquidacion detalleProducto
    @Override
    public List<PrdPreLiquidacionDetalleProductoTO> getListadoPreLiquidacionDetalleProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception {
        return preLiquidacionDao.getListadoPreLiquidacionDetalleProductoTO(empresa, sector, piscina, desde, hasta, cliente, talla, tipo);
    }

    //Listado de PreLiquidacion Consolidando Tallas
    @Override
    public List<PrdFunPreLiquidacionConsolidandoTallasTO> getPrdFunPreLiquidacionConsolidandoTallasTO(String empresa, String desde, String hasta, String sector, String cliente, String piscina) throws Exception {
        return preLiquidacionDao.getPrdFunPreLiquidacionConsolidandoTallasTO(empresa, desde, hasta, sector, cliente, piscina);
    }

    //listar consolidando clientes
    @Override
    public List<PrdPreLiquidacionConsolidandoClientesTO> getListadoPreLiquidacionConsolidandoClientesTO(String empresa, String sector, String desde, String hasta, String cliente) throws Exception {
        return preLiquidacionDao.getPrdFunPreLiquidacionConsolidandoClientesTO(empresa, sector, desde, hasta, cliente);
    }

    //liquidacion listado consulta
    @Override
    public List<PrdPreLiquidacionConsultaTO> getListadoPreLiquidacionConsultasTO(String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception {
        return preLiquidacionDao.getPrdFunPreLiquidacionConsultaTO(empresa, sector, piscina, desde, hasta, incluirAnuladas);
    }
    
    //Listado de preliquidacion detalle
    @Override
    public List<PrdPreLiquidacionesDetalleTO> listarPreLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception {
        return preLiquidacionDao.listarPreLiquidacionesDetalle(empresa, desde, hasta);
    }
}
