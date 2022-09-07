package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.TallaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.LiquidacionLotesValorizadaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTallaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class TallaServiceImpl implements TallaService {

    @Autowired
    private TallaDao tallaDao;
    @Autowired
    ProductoService productoService;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public boolean insertarPrdLiquidacionLotesValorizada(LiquidacionLotesValorizadaTO liquidacionLotesValorizadaTO,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = liquidacionLotesValorizadaTO.getLiqCliente() + " " + liquidacionLotesValorizadaTO.getLiqNumero();
        susDetalle = "Se insertó la liquidación de lotes valorizada con código "
                + liquidacionLotesValorizadaTO.getLiqCliente() + " | " + liquidacionLotesValorizadaTO.getLiqNumero()
                + " de la piscina " + liquidacionLotesValorizadaTO.getPisNumero() + " del sector "
                + liquidacionLotesValorizadaTO.getSecCodigo();
        susSuceso = "INSERT";
        susTabla = "produccion.prd_liquidacion";
        liquidacionLotesValorizadaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        return comprobar;
    }

    @Override
    public PrdLiquidacionTallaTO getPrdLiquidacionTallaTO(String empresa,
            PrdLiquidacionTallaTablaTO prdLiquidacionTallaTablaTO) throws Exception {
        return tallaDao.getPrdLiquidacionTallaTO(empresa, prdLiquidacionTallaTablaTO);
    }

    @Override
    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa) throws Exception {
        return tallaDao.getListaPrdLiquidacionTallaTablaTO(empresa);
    }

    @Override
    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa, String codigo)
            throws Exception {
        return tallaDao.getListaPrdLiquidacionTallaTablaTO(empresa, codigo);
    }

    @Override
    public String insertarPrdLiquidacionTallaTO(PrdLiquidacionTallaTO prdLiquidacionTallaTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        if (tallaDao.obtenerPorId(PrdTalla.class, new PrdTallaPK(prdLiquidacionTallaTO.getTallaEmpresa(),
                prdLiquidacionTallaTO.getTallaCodigo())) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susDetalle = "Se insertó el Talla de la liquidacion: " + prdLiquidacionTallaTO.getTallaDetalle();
            susClave = prdLiquidacionTallaTO.getTallaCodigo();
            susTabla = "produccion.prd_liquidacion_Talla";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionTallaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            PrdTalla prdLiquidacionTalla = ConversionesProduccion
                    .convertirPrdLiquidacionTallaTO_PrdLiquidacionTalla(prdLiquidacionTallaTO);
            if (tallaDao.insertarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
                retorno = "TEl Talla de la liquidacion se guardo correctamente...";
            } else {
                retorno = "FHubo un error al guardar el Talla de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl Talla de la liquidacion que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionTallaTO(PrdLiquidacionTallaTO prdLiquidacionTallaTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        PrdTalla prdLiquidacionTallaAux = tallaDao.obtenerPorId(PrdTalla.class,
                new PrdTallaPK(prdLiquidacionTallaTO.getTallaEmpresa(), prdLiquidacionTallaTO.getTallaCodigo()));
        if (prdLiquidacionTallaAux != null) {
            /// CREAR EL SUSCESO
            susDetalle = "Se modificó el Talla de la liquidacion: " + prdLiquidacionTallaTO.getTallaDetalle();
            susClave = prdLiquidacionTallaTO.getTallaCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_liquidacion_Talla";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionTallaTO.setUsrCodigo(prdLiquidacionTallaAux.getUsrCodigo());
            prdLiquidacionTallaTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionTallaAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdTalla prdLiquidacionTalla = ConversionesProduccion
                    .convertirPrdLiquidacionTallaTO_PrdLiquidacionTalla(prdLiquidacionTallaTO);
            if (tallaDao.modificarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
                retorno = "TEl Talla de la liquidacion se modificó correctamente...";
            } else {
                retorno = "Hubo un error al modificar el Talla de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl Talla de la liquidacion que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdLiquidacionTallaTO(PrdLiquidacionTallaTO prdLiquidacionTallaTO, SisInfoTO sisInfoTO)
            throws Exception {
        boolean seguir = tallaDao.banderaEliminarLiquidacionTalla(prdLiquidacionTallaTO.getTallaEmpresa(),
                prdLiquidacionTallaTO.getTallaCodigo());
        PrdTalla prdLiquidacionTalla = null;
        String retorno = "";
        if (seguir) {
            prdLiquidacionTallaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            prdLiquidacionTalla = tallaDao.getPrdLiquidacionTalla(prdLiquidacionTallaTO.getTallaEmpresa(),
                    prdLiquidacionTallaTO.getTallaCodigo());
            // SUCESO
            susClave = prdLiquidacionTallaTO.getTallaEmpresa();
            susTabla = "produccion.prd_liquidacion_motivo";
            susDetalle = "Se eliminó el motivo de liquidacion: " + prdLiquidacionTallaTO.getTallaDetalle()
                    + " con código " + prdLiquidacionTallaTO.getTallaCodigo();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionTallaTO.setUsrCodigo(prdLiquidacionTalla.getUsrCodigo());
            prdLiquidacionTallaTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionTalla.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            prdLiquidacionTalla = ConversionesProduccion
                    .convertirPrdLiquidacionTallaTO_PrdLiquidacionTalla(prdLiquidacionTallaTO);

            if (tallaDao.eliminarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
                retorno = "TEl motivo de liquidacion de pesca se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el motivo de liquidación de pesca...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar un motivo con movimientos";
        }
        return retorno;
    }

    @Override
    public String insertarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        prdLiquidacionTalla.getPrdLiquidacionTallaPK()
                .setTallaCodigo(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo().toUpperCase());

        if (tallaDao.obtenerPorId(PrdTalla.class, prdLiquidacionTalla.getPrdLiquidacionTallaPK()) != null) {
            retorno = "FLa Talla de la liquidación que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaEmpresa());

            List<PrdTalla> listadoDeTallas = tallaDao.getListaPrdLiquidacionTalla(sisInfoTO.getEmpresa(), prdLiquidacionTalla.getTallaGramosDesde(), prdLiquidacionTalla.getTallaGramosHasta(), prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo());
            if (listadoDeTallas != null && !listadoDeTallas.isEmpty()) {
                return "FExisten tallas que se encuentran en el mismo rango, por favor revisar gramos hasta y desde.";
            }

            susDetalle = "Se insertó la Talla de la liquidacion: " + prdLiquidacionTalla.getTallaDetalle();
            susClave = prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo();
            susTabla = "produccion.prd_liquidacion_Talla";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdLiquidacionTalla.setTallaDetalle(prdLiquidacionTalla.getTallaDetalle().toUpperCase());
            prdLiquidacionTalla.setTallaGramosDesde(prdLiquidacionTalla.getTallaGramosDesde());
            prdLiquidacionTalla.setTallaGramosHasta(prdLiquidacionTalla.getTallaGramosHasta());
            prdLiquidacionTalla.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdLiquidacionTalla.setUsrCodigo(sisInfoTO.getUsuario());
            prdLiquidacionTalla.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (tallaDao.insertarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
                retorno = "TLa Talla de liquidación: Código " + prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo() + ", se ha ingresado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaEmpresa());
        List<PrdTalla> listadoDeTallas = tallaDao.getListaPrdLiquidacionTalla(sisInfoTO.getEmpresa(), prdLiquidacionTalla.getTallaGramosDesde(), prdLiquidacionTalla.getTallaGramosHasta(), prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo());
        if (listadoDeTallas != null && !listadoDeTallas.isEmpty()) {
            return "FExisten tallas que se encuentran en el mismo rango, por favor revisar gramos hasta y desde.";
        }
        susDetalle = "Se modificó la Talla de la liquidacion: " + prdLiquidacionTalla.getTallaDetalle();
        susClave = prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_Talla";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdLiquidacionTalla.setTallaDetalle(prdLiquidacionTalla.getTallaDetalle().toUpperCase());

        if (tallaDao.modificarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
            retorno = "TLa talla de liquidación: código " + prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo() + ", se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar la Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaEmpresa());

        susDetalle = "Se modificó la Talla de la liquidacion: " + prdLiquidacionTalla.getTallaDetalle();
        susClave = prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_Talla";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdLiquidacionTalla.setTallaDetalle(prdLiquidacionTalla.getTallaDetalle().toUpperCase());
        prdLiquidacionTalla.setTallaInactivo(estado);
        String sms = estado ? "inactivado" : "activado";
        if (tallaDao.modificarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
            retorno = "TLa Talla de liquidación: Código " + prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo() + ", se ha " + sms + " correctamente.";
        } else {
            retorno = "Hubo un error al modificar la Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!tallaDao.banderaEliminarLiquidacionTalla(
                prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaEmpresa(),
                prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo())) {
            retorno = "FNo se puede eliminar la Talla de la liquidacion porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaEmpresa());

            susDetalle = "Se eliminó la Talla de la liquidacion: " + prdLiquidacionTalla.getTallaDetalle();
            susClave = prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_liquidacion_Talla";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (tallaDao.eliminarPrdLiquidacionTalla(prdLiquidacionTalla, sisSuceso)) {
                retorno = "TLa Talla de liquidación: Código " + prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar la Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca) throws Exception {
        return tallaDao.getListaPrdLiquidacionTalla(empresa, presupuestoPesca);
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca, boolean inactivos) throws Exception {
        return tallaDao.getListaPrdLiquidacionTalla(empresa, presupuestoPesca, inactivos);
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTallaDetalle(String empresa) throws Exception {
        return tallaDao.getListaPrdLiquidacionTallaDetalle(empresa);
    }

    @Override
    public Map<String, Object> verificarTallaExcel(List<PrdTalla> prdLiquidacionTalla, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<PrdTalla> listaTallas = new ArrayList<>();
        if (prdLiquidacionTalla != null && !prdLiquidacionTalla.isEmpty()) {
            for (int i = 0; i < prdLiquidacionTalla.size(); i++) {
                PrdTalla talla = null;
                InvProducto producto = null;
                boolean unidadValidad = false;
                prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK().setTallaCodigo(prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK().getTallaCodigo().toUpperCase());
                String codigo = prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK().getTallaCodigo().toUpperCase();
                if (tallaDao.obtenerPorId(PrdTalla.class, prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK()) != null) {
                    listaMensajesEnviar.add("FLa Talla con el codigo <strong class='pl-2'>" + prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK().getTallaCodigo() + "</strong> que va a ingresar ya existe. Intente con otro.");
                } else {
                    talla = new PrdTalla();
                    talla.setPrdLiquidacionTallaPK(new PrdTallaPK(sisInfoTO.getEmpresa(), codigo));
                    talla.setTallaDetalle(prdLiquidacionTalla.get(i).getTallaDetalle().toUpperCase());
                    talla.setTallaGramosDesde(prdLiquidacionTalla.get(i).getTallaGramosDesde());
                    talla.setTallaGramosHasta(prdLiquidacionTalla.get(i).getTallaGramosHasta());
                    talla.setTallaPrecio(prdLiquidacionTalla.get(i).getTallaPrecio());
                    talla.setUsrEmpresa(sisInfoTO.getEmpresa());
                    talla.setUsrCodigo(sisInfoTO.getUsuario());
                    talla.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
                    if (prdLiquidacionTalla.get(i).getInvProducto() != null) {
                        producto = productoService.obtenerPorId(prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK().getTallaEmpresa(), prdLiquidacionTalla.get(i).getPrdLiquidacionTallaPK().getTallaCodigo());
                        if (producto != null) {
                            talla.setInvProducto(producto);
                        } else {
                            listaMensajesEnviar.add("FEl producto con el codigo <strong class='pl-2'>" + prdLiquidacionTalla.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + "</strong> no existe. Intente con otro.");
                        }
                    }

                    if (prdLiquidacionTalla.get(i).getTallaUnidadMedida() != null) {
                        if (prdLiquidacionTalla.get(i).getTallaUnidadMedida().equals("LIBRAS")
                                || prdLiquidacionTalla.get(i).getTallaUnidadMedida().equals("KILOS")) {
                            talla.setTallaUnidadMedida(prdLiquidacionTalla.get(i).getTallaUnidadMedida());
                            unidadValidad = true;
                        } else {
                            listaMensajesEnviar.add("FEl valor de talla <strong class='pl-2'>" + prdLiquidacionTalla.get(i).getTallaUnidadMedida() + "</strong> no existe. Intente con otro.");
                        }
                    }

                    if (unidadValidad) {
                        listaTallas.add(talla);
                    } else {
                        listaMensajesEnviar.add("FVerificar los valores ingresados");
                    }

                }
            }
            mapResultadoEnviar = new HashMap<String, Object>();
            mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
            mapResultadoEnviar.put("listaTallas", listaTallas);
        }
        return mapResultadoEnviar;
    }

    @Override
    public Map<String, Object> insertarTallasImportadas(PrdTalla talla, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        Map<String, Object> respuesta = new HashMap<>();
        if (tallaDao.obtenerPorId(PrdTalla.class, talla.getPrdLiquidacionTallaPK()) != null) {
            retorno = "FLa Talla de la liquidacion que va a ingresar ya existe. Intente con otro.";
        } else {
            List<PrdTalla> listadoDeTallas = tallaDao.getListaPrdLiquidacionTalla(sisInfoTO.getEmpresa(), talla.getTallaGramosDesde(), talla.getTallaGramosHasta(), talla.getPrdLiquidacionTallaPK().getTallaCodigo());
            if (listadoDeTallas != null && !listadoDeTallas.isEmpty()) {
                retorno = "FExisten tallas que se encuentran en el mismo rango, por favor revisar gramos hasta y desde.";
            }
            susDetalle = "Se insertó la Talla de la liquidacion: " + talla.getTallaDetalle();
            susClave = talla.getPrdLiquidacionTallaPK().getTallaCodigo();
            susTabla = "produccion.prd_liquidacion_Talla";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (tallaDao.insertarPrdLiquidacionTalla(talla, sisSuceso)) {
                retorno = "T<html>La Talla de liquidación: <br><br>Código<font size = 5> " + talla.getPrdLiquidacionTallaPK().getTallaCodigo() + "</font>, se ha ingresado correctamente.</html>";
            } else {
                retorno = "FHubo un error al guardar el Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        respuesta.put("mensaje", retorno);
        respuesta.put("prdTalla", talla);

        return respuesta;
    }

}
