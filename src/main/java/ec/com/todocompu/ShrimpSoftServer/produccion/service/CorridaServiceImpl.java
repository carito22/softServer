package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosMotivoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.CorridaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidaResumenCorridaSubcategoria;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidadConsumo;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidadCosto;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidadGrameajePromedioPorPiscina;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidadPiscinaCorrida;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidadResumenTrazabilidadAlimentacion;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidasConsumosDiarios;
import ec.com.todocompu.ShrimpSoftServer.produccion.helper.NumeroColumnaDesconocidasCostosDiarios;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.CostoDetalleMultipleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDiariosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostosDiariosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunAnalisisVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaConsultaGrameajePromedioPorPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaSubcategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenTrazabilidadAlimentacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdTrazabilidadCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CorridaServiceImpl implements CorridaService {

    @Autowired
    private CorridaDao corridaDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private CorridaDetalleService corridaDetalleService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private PiscinaDao piscinaDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private ConsumosMotivoService consumosMotivoService;
    @Autowired
    private EquipoControlService equipoControlService;
    @Autowired
    private PiscinaService piscinaService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<PrdListaCorridaTO> obtenerCorrida(String empresa, String sector, String piscina, Date fecha)
            throws Exception {
        return corridaDao.obtenerCorrida(empresa, sector, piscina, fecha);
    }

    @Override
    public List<PrdListaCorridaTO> getListaCorridaTO(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String tipo) throws Exception {
        return corridaDao.getListaCorrida(empresa, sector, piscina, fechaDesde, fechaHasta, tipo);
    }

    @Override
    public List<PrdCorridaListadoTO> listarCorridasPorFecha(String empresa, String fechaDesde, String fechaHasta, String tipo) throws Exception {
        return corridaDao.listarCorridasPorFecha(empresa, fechaDesde, fechaHasta, tipo);
    }

    @Override
    public List<PrdListaCorridaTO> getListaCorridaTO(String empresa, String sector, String piscina, String corrida)
            throws Exception {
        return corridaDao.getListaCorrida(empresa, sector, piscina, corrida);
    }

    @Override
    public PrdFunAnalisisVentasTO getPrdFunAnalisisVentasTO(String empresa, String sector, String piscina,
            String fechaDesde, String fechaHasta) throws Exception {
        return corridaDao.getPrdFunAnalisisVentasTO(empresa, sector, piscina, fechaDesde, fechaHasta);
    }

    @Override
    public List<PrdListaCostosDetalleCorridaTO> getPrdListaCostosDetalleCorridaTO(String empresa, String sector,
            String piscina, String corrida, String desde, String hasta, String agrupadoPor) throws Exception {
        return corridaDao.getPrdListaCostosDetalleCorridaTO(empresa, sector, piscina, corrida, desde, hasta, agrupadoPor);
    }

    @Override
    public List<PrdComboCorridaTO> getComboPrdCorridaTO(String empresa, String sector, String piscina)
            throws Exception {
        return corridaDao.getComboPrdCorrida(empresa, sector, piscina);
    }

    @Override
    public List<PrdComboCorridaTO> getComboPrdCorridaFiltradoTO(String empresa, String sector, String piscina, Date fecha)
            throws Exception {
        return corridaDao.getComboPrdCorridaFiltrado(empresa, sector, piscina, fecha);
    }

    @Override
    public List<PrdResumenCorridaTO> getListaResumenCorridaTO(String empresa, String sector, String desde, String hasta,
            String tipoResumen) throws Exception {
        return corridaDao.getListaResumenCorridaTO(empresa, sector, desde, hasta, tipoResumen);
    }

    @Override
    public List<PrdContabilizarCorridaTO> getListaContabilizarCorridaTO(String empresa, String desde, String hasta)
            throws Exception {
        return corridaDao.getListaContabilizarCorridaTO(empresa, desde, hasta);
    }

    @Override
    public String consultarFechaMaxMin(String empresa, String tipoResumen) throws Exception {
        return corridaDao.consultarFechaMaxMin(empresa, tipoResumen);
    }

    @Override
    public List<PrdCostoDetalleFechaTO> getPrdListadoCostoDetalleFechaTO(String empresa, String secCodigo, String desde,
            String hasta, String agrupadoPor) throws Exception {
        return corridaDao.getPrdListadoCostoDetalleFechaTO(empresa, secCodigo, desde, hasta, agrupadoPor);
    }

    @Override
    public RetornoTO modificarCorridaActivo(String empresa, String sector, String hasta, String proceso,
            String agrupadoPor) throws Exception {
        String sql = "UPDATE produccion.prd_corrida " + "SET cor_activa = CASE WHEN '" + sector
                + "' IS NULL THEN TRUE ELSE cor_sector = '" + sector + "' END AND " + "(('" + hasta
                + "' >= prd_corrida.cor_fecha_desde AND '" + hasta + "' <= prd_corrida.cor_fecha_hasta) OR " + "('"
                + hasta + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                + "WHERE (cor_empresa = '" + empresa + "');";
        corridaDao.actualizarSQL(sql);
        return numeroColumnaDesconocidadPiscinaCorridaConsumo(empresa, sector, hasta, proceso, agrupadoPor);
    }

    @Override
    public RetornoTO modificarCorridaActivoSeleccionMultiple(String empresa,
            List<MultiplePiscinaCorrida> multiplePiscinaCorrida, String proceso, String agrupadoPor) throws Exception {
        corridaDao.actualizarSQL(
                "UPDATE produccion.prd_corrida SET cor_activa = false WHERE (cor_empresa = '" + empresa + "');");
        for (MultiplePiscinaCorrida mpc : multiplePiscinaCorrida) {
            PrdCorrida prdCorrida = corridaDao
                    .obtenerCorrida(new PrdCorridaPK(empresa, mpc.getSector(), mpc.getPiscina(), mpc.getCorrida()));
            prdCorrida.setCorActiva(true);
            corridaDao.actualizar(prdCorrida);
        }
        return numeroColumnaDesconocidadPiscinaCorridaConsumo(empresa, null, null, proceso, agrupadoPor);
    }

    private RetornoTO numeroColumnaDesconocidadPiscinaCorridaConsumo(String empresa, String sector, String hasta,
            String proceso, String agrupadoPor) throws Exception {
        RetornoTO retornoTO = new RetornoTO();
        switch (proceso) {
            case "COSTO": {
                List<CostoDetalleMultipleCorridaTO> costoDetalleMultipleCorridaTOs = corridaDao
                        .getCostoDetalleMultipleCorridaTO(empresa, hasta, agrupadoPor);
                NumeroColumnaDesconocidadPiscinaCorrida obj = new NumeroColumnaDesconocidadPiscinaCorrida();
                obj.agruparCabeceraColumnas(costoDetalleMultipleCorridaTOs);
                obj.agruparProductos(costoDetalleMultipleCorridaTOs);
                obj.llenarObjetoParaTabla(costoDetalleMultipleCorridaTOs);
                retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
                retornoTO.setColumnas(obj.getColumnas());
                retornoTO.setDatos(obj.getDatos());
                break;
            }
            case "PRODUCTO": {
                List<PrdConsumosTO> prdConsumoProductoEnProcesoTOs = corridaDao.getConsumoProductosProceso(empresa, sector,
                        hasta);
                NumeroColumnaDesconocidadConsumo obj = new NumeroColumnaDesconocidadConsumo(prdConsumoProductoEnProcesoTOs);
                retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
                retornoTO.setColumnas(obj.getColumnas());
                retornoTO.setDatos(obj.getDatos());
                break;
            }
            default: {
                List<PrdConsumosTO> prdConsumoMultiplePiscinaTOs = corridaDao.getConsumoMultiplePiscina(empresa);
                NumeroColumnaDesconocidadConsumo obj = new NumeroColumnaDesconocidadConsumo(prdConsumoMultiplePiscinaTOs);
                retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
                retornoTO.setColumnas(obj.getColumnas());
                retornoTO.setDatos(obj.getDatos());
                break;
            }
        }
        retornoTO.setMensaje("TModificado...");
        return retornoTO;
    }

    @Override
    public PrdCorridaTO getPrdCorridaTO(String empresa, String sector, String piscina, String fecha) throws Exception {
        return corridaDao.getPrdCorridaTO(empresa, sector, piscina, fecha);
    }

    @Override
    public List<MultiplePiscinaCorrida> getCostoDetallePersonalizado(String empresa, String sector, String fecha) {
        return corridaDao.getCostoDetallePersonalizado(empresa, sector, fecha);
    }

    @Override
    public RetornoTO getCostoPorFechaProrrateado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String usuario, String agrupadoPor) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        try {
            List<PrdCostoTO> prdCostoPorFechaProrrateadoTOs = corridaDao.getCostoPorFechaProrrateado(empresa,
                    codigoSector, fechaInicio, fechaFin, agrupadoPor);
            mensaje = "T";
            NumeroColumnaDesconocidadCosto obj = new NumeroColumnaDesconocidadCosto(prdCostoPorFechaProrrateadoTOs);
            retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
            retornoTO.setColumnas(obj.getColumnas());
            retornoTO.setDatos(obj.getDatos());
        } catch (Exception e) {
            mensaje = "FOcurrió un error al obtener los datos de la Base de Datos. \nContacte con el administrador...";
        }
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public List<PrdFunCostosPorFechaSimpleTO> getPrdFunCostosPorFechaSimpleTO(String codigoSector, String fechaInicio,
            String fechaFin, String infEmpresea) throws Exception {
        List<PrdFunCostosPorFechaSimpleTO> prdFunCostosPorFechaSimpleTOs = null;
        try {
            prdFunCostosPorFechaSimpleTOs = corridaDao.getPrdFunCostosPorFechaSimpleTO(codigoSector, fechaInicio,
                    fechaFin, infEmpresea);
        } catch (Exception e) {
            // Excepciones.guardarExcepcionesEJB(e, getClass().getName(),
            // "getPrdFunCostosPorFechaSimpleTO", sisInfoTO,
            // sistemaDao);
        }
        return prdFunCostosPorFechaSimpleTOs;
    }

    @Override
    public RetornoTO getCostoPorPiscinaSemanal(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String usuario, String agrupadoPor, String periodo) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        try {
            List<PrdCostoTO> prdCostoPorPiscinaSemanalTOs = corridaDao.getCostoPorPorPiscinaSemanal(empresa,
                    codigoSector, numeroPiscina, fechaInicio, fechaFin, agrupadoPor, periodo);

            mensaje = "T";
            NumeroColumnaDesconocidadCosto obj = new NumeroColumnaDesconocidadCosto(prdCostoPorPiscinaSemanalTOs);
            retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
            retornoTO.setColumnas(obj.getColumnas());
            retornoTO.setDatos(obj.getDatos());
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public RetornoTO getCostosMensuales(String empresa, String codigoSector, String codigoBodega,
            String fechaInicio, String fechaFin, String usuario, String agrupadoPor) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        try {
            List<PrdCostoTO> prdCostoPorPiscinaSemanalTOs = corridaDao.getCostosMensuales(empresa,
                    codigoSector, codigoBodega, fechaInicio, fechaFin, agrupadoPor);

            mensaje = "T";
            NumeroColumnaDesconocidadCosto obj = new NumeroColumnaDesconocidadCosto(prdCostoPorPiscinaSemanalTOs);
            retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
            retornoTO.setColumnas(obj.getColumnas());
            retornoTO.setDatos(obj.getDatos());
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public RetornoTO getConsumoPorFechaDesglosado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String usuario) throws Exception {
        String mensaje = "";
        RetornoTO retornoTO = new RetornoTO();
        try {
            List<PrdConsumosTO> prdConsumoPorFechaDesglosadoTOs = corridaDao.getConsumoPorFechaDesglosado(empresa,
                    codigoSector, fechaInicio, fechaFin);
            mensaje = "T";
            NumeroColumnaDesconocidadConsumo obj = new NumeroColumnaDesconocidadConsumo(
                    prdConsumoPorFechaDesglosadoTOs);
            retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
            retornoTO.setColumnas(obj.getColumnas());
            retornoTO.setDatos(obj.getDatos());
        } catch (Exception e) {
            mensaje = e.getMessage();
        }
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public RetornoTO getConsumoProductosProceso(String empresa, String fecha, String usuario, SisInfoTO sisInfoTO)
            throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        List<PrdConsumosTO> prdConsumoProductoEnProcesoTOs = corridaDao.getConsumoProductosProceso(empresa, "",
                fecha);
        mensaje = "T";
        NumeroColumnaDesconocidadConsumo obj = new NumeroColumnaDesconocidadConsumo(prdConsumoProductoEnProcesoTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public List<PrdConsumosTO> getPrdConsumosFechaTO(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception {
        return corridaDao.getPrdConsumosFechaTO(empresa, sector, fechaDesde, fechaHasta);
    }

    @Override
    public List<PrdConsumosTO> getPrdConsumosPiscinaTO(String empresa, String sector, String piscina, String fechaDesde,
            String fechaHasta) throws Exception {
        return corridaDao.getPrdConsumosPiscinaTO(empresa, sector, piscina, fechaDesde, fechaHasta);
    }

    @Override
    public RetornoTO getConsumoPorPiscinaPeriodo(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String periodo, String usuario) throws Exception {
        String mensaje = "";
        RetornoTO retornoTO = new RetornoTO();
        List<PrdConsumosTO> prdConsumosPorPiscinaPeriodoTOs = corridaDao.getConsumoPorPiscinaPeriodo(empresa,
                codigoSector, numeroPiscina, fechaInicio, fechaFin, periodo);
        mensaje = "T";
        NumeroColumnaDesconocidadConsumo obj = new NumeroColumnaDesconocidadConsumo(
                prdConsumosPorPiscinaPeriodoTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public List<PrdUtilidadDiariaCorridaTO> getUtilidadDiariaCorrida(String empresa, String codigoSector, String numeroPiscina, String numeroCorrida) throws Exception {
        return corridaDao.getUtilidadDiariaCorrida(empresa, codigoSector, numeroPiscina, numeroCorrida);
    }

    @Override
    public RetornoTO getConsumosMensuales(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        List<PrdConsumosTO> prdConsumosMensualesTOs = corridaDao.getConsumosMensuales(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin);
        mensaje = "T";
        NumeroColumnaDesconocidadConsumo obj = new NumeroColumnaDesconocidadConsumo(
                prdConsumosMensualesTOs);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public Map<String, Object> getConsumosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario, String piscinaNumero, String codigoProducto) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<PrdConsumosDiariosTO> prdConsumosMensualesTOs = corridaDao.getConsumosDiarios(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, piscinaNumero, codigoProducto);
        NumeroColumnaDesconocidasConsumosDiarios obj = new NumeroColumnaDesconocidasConsumosDiarios(prdConsumosMensualesTOs);
        List<String> piscinas = obj.getPiscinasSinDuplicados();
        List<List<List<String>>> listaDeDatos = new ArrayList<>();
        piscinas.stream().filter((piscina) -> (piscina != null && !piscina.equals(""))).map((piscina) -> {
            List<List<String>> datosPorPicina = completarTotales(obj, piscina);
            return datosPorPicina;
        }).forEachOrdered((datosPorPicina) -> {
            listaDeDatos.add(datosPorPicina);
        });
        campos.put("columnas", obj.getColumnas());
        campos.put("datos", listaDeDatos);
        return campos;
    }

    public List<List<String>> completarTotales(NumeroColumnaDesconocidasConsumosDiarios obj, String piscina) {
        List<List<String>> datosPorPicina = new ArrayList<>();
        obj.getDatos().stream().map(detalle -> detalle.get(0).equals(piscina) ? datosPorPicina.add(detalle) : null).collect(Collectors.toList());
        List<String> porPiscina = Arrays.asList(new String[obj.getColumnas().size()]);
        for (int i = 0; i < datosPorPicina.size(); i++) {
            porPiscina.set(3, "TOTAL CONSUMO: ");
            for (int j = 0; j < obj.getColumnasFaltantes().size(); j++) {
                BigDecimal suma = porPiscina.get(j + 4) != null ? new BigDecimal(porPiscina.get(j + 4)) : new BigDecimal(BigInteger.ZERO);
                BigDecimal suma2 = datosPorPicina.get(i).get(j + 4) != null ? new BigDecimal(datosPorPicina.get(i).get(j + 4)) : new BigDecimal(BigInteger.ZERO);
                porPiscina.set(j + 4, suma.add(suma2) + "");
            }
        }
        datosPorPicina.add(porPiscina);
        return datosPorPicina;
    }

    @Override
    public Map<String, Object> getCostosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario, String piscinaNumero, String codigoProducto) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<PrdCostosDiariosTO> prdCostosMensualesTOs = corridaDao.getCostosDiarios(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, piscinaNumero, codigoProducto);
        NumeroColumnaDesconocidasCostosDiarios obj = new NumeroColumnaDesconocidasCostosDiarios(prdCostosMensualesTOs);
        List<String> piscinas = obj.getPiscinasSinDuplicados();
        List<List<List<String>>> listaDeDatos = new ArrayList<>();
        piscinas.stream().filter((piscina) -> (piscina != null && !piscina.equals(""))).map((piscina) -> {
            List<List<String>> datosPorPicina = completarTotalesCostos(obj, piscina);
            return datosPorPicina;
        }).forEachOrdered((datosPorPicina) -> {
            listaDeDatos.add(datosPorPicina);
        });
        campos.put("columnas", obj.getColumnas());
        campos.put("datos", listaDeDatos);
        return campos;
    }

    public List<List<String>> completarTotalesCostos(NumeroColumnaDesconocidasCostosDiarios obj, String piscina) {
        List<List<String>> datosPorPicina = new ArrayList<>();
        obj.getDatos().stream().map(detalle -> detalle.get(0).equals(piscina) ? datosPorPicina.add(detalle) : null).collect(Collectors.toList());
        List<String> porPiscina = Arrays.asList(new String[obj.getColumnas().size()]);
        for (int i = 0; i < datosPorPicina.size(); i++) {
            porPiscina.set(3, "TOTAL COSTO: ");
            for (int j = 0; j < obj.getColumnasFaltantes().size(); j++) {
                BigDecimal suma = porPiscina.get(j + 4) != null ? new BigDecimal(porPiscina.get(j + 4)) : new BigDecimal(BigInteger.ZERO);
                BigDecimal suma2 = datosPorPicina.get(i).get(j + 4) != null ? new BigDecimal(datosPorPicina.get(i).get(j + 4)) : new BigDecimal(BigInteger.ZERO);
                porPiscina.set(j + 4, suma.add(suma2) + "");
            }
        }
        datosPorPicina.add(porPiscina);
        return datosPorPicina;
    }

    @Override
    public List<PrdResumenFinancieroTO> getPrdResumenFinancieroTO(String empresa, String secCodigo, String desde,
            String hasta) throws Exception {
        return corridaDao.getPrdResumenFinancieroTO(empresa, secCodigo, desde, hasta);
    }

    @Override
    public List<PrdResumenPescaSiembraTO> getResumenPesca(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            String usuario, boolean incluirTransferencia) throws Exception {
        return corridaDao.getResumenPesca(empresa, codigoSector, fechaInicio, fechaFin, incluirTransferencia);
    }

    @Override
    public List<PrdResumenPescaSiembraTO> getResumenSiembra(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            String usuario) throws Exception {
        return corridaDao.getResumenSiembra(empresa, codigoSector, fechaInicio, fechaFin);
    }

    @Override
    public List<PrdListaFunAnalisisPesosTO> getFunAnalisisPesos(String empresa, String sector, String fecha)
            throws Exception {

        List<PrdListaFunAnalisisPesosTO> lista = corridaDao.getFunAnalisisPesos(empresa, sector, fecha);
        List<PrdListaFunAnalisisPesosComplementoTO> listaComplemento = corridaDao
                .getFunAnalisisPesosComplemento(empresa, sector, fecha);

        String fechaAuxiliar = "";
        Integer ncolumnas = 0;

        for (PrdListaFunAnalisisPesosComplementoTO prdPesoComplemento : listaComplemento) {
            if (!fechaAuxiliar.equals(prdPesoComplemento.getApFecha())) {
                if (fechaAuxiliar.equals("Inc. Promedio")) {
                    ncolumnas = 5;
                } else {
                    ncolumnas++;
                }
            }

            for (PrdListaFunAnalisisPesosTO prdPeso : lista) {
                if (prdPeso.getApSector().equals(prdPesoComplemento.getApSector())
                        && prdPeso.getApPiscina().equals(prdPesoComplemento.getApPiscina())
                        && prdPeso.getApCorrida().equals(prdPesoComplemento.getApCorrida())) {
                    switch (ncolumnas) {
                        case 1:
                            prdPeso.setApPesoIncrementoSemana4(prdPesoComplemento.getApIncremento());
                            break;
                        case 2:
                            prdPeso.setApPesoIncrementoSemana3(prdPesoComplemento.getApIncremento());
                            break;
                        case 3:
                            prdPeso.setApPesoIncrementoSemana2(prdPesoComplemento.getApIncremento());
                            break;
                        case 4:
                            prdPeso.setApPesoIncrementoSemana1(prdPesoComplemento.getApIncremento());
                            break;
                        case 5:
                            prdPeso.setApPesoIncrementoPromedio(prdPesoComplemento.getApIncremento());
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
            fechaAuxiliar = prdPesoComplemento.getApFecha();
        }
        return lista;
    }

    @Override
    public List<String> getFunFechaSemanas(String empresa, String sector, String fecha) throws Exception {
        return corridaDao.getFunFechaSemanas(empresa, sector, fecha);
    }

    @Override
    public List<PrdListaConsultaGrameajePromedioPorPiscinaTO> getListaConsultaGrameajePromedioPorPiscinaTOs(
            String empresa, String sector, String nomSector) throws Exception {
        return corridaDao.getListaConsultaGrameajePromedioPorPiscinaTOs(empresa, sector, nomSector);
    }

    @Override
    public RetornoTO getConsultaGrameajePromedioPorPiscina(String empresa, String sector, String nomSector, boolean sobrevivencia, SisInfoTO sisInfoTO)
            throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        List<PrdListaConsultaGrameajePromedioPorPiscinaTO> prdListaConsultaGrameajePromedioPorPiscinaTOs = corridaDao
                .getListaConsultaGrameajePromedioPorPiscinaTOs(empresa, sector, nomSector);
        mensaje = "T";
        NumeroColumnaDesconocidadGrameajePromedioPorPiscina obj = new NumeroColumnaDesconocidadGrameajePromedioPorPiscina();

        obj.agruparCabeceraColumnas(prdListaConsultaGrameajePromedioPorPiscinaTOs);
        obj.agruparPisina(prdListaConsultaGrameajePromedioPorPiscinaTOs);
        obj.llenarObjetoParaTabla(prdListaConsultaGrameajePromedioPorPiscinaTOs, sobrevivencia);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public String insertarCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception {
        List<PrdListaCorridaTO> listaCorrida = getListaCorridaTO(prdCorrida.getPrdCorridaPK().getCorEmpresa(), prdCorrida.getPrdCorridaPK().getCorSector(), prdCorrida.getPrdCorridaPK().getCorPiscina(), null, null, null);
        SisPeriodo estadoPeriodoInicio = periodoService.getPeriodoPorFecha(prdCorrida.getCorFechaDesde(), prdCorrida.getPrdCorridaPK().getCorEmpresa());
        if (estadoPeriodoInicio == null) {
            throw new GeneralException("No existe periodo para la fecha que inicializa la corrida");
        } else {
            if (estadoPeriodoInicio.getPerCerrado()) {
                throw new GeneralException("El periodo que inicializa la corrida esta cerrado ");
            }
        }
        for (PrdListaCorridaTO itemProduccionBBCorrida : listaCorrida) {
            if (itemProduccionBBCorrida.getCorNumero().equalsIgnoreCase(prdCorrida.getPrdCorridaPK().getCorNumero())) {
                throw new GeneralException("Numero de corrida " + itemProduccionBBCorrida.getCorNumero() + " ya existe porfavor intente con otro numero");
            }
            if (itemProduccionBBCorrida.getCorFechaHasta() == null) {
                throw new GeneralException("No se puede crear una corrida, si la corrida número " + itemProduccionBBCorrida.getCorNumero() + " se encuentra abierta");
            }
            if ((prdCorrida.getCorFechaDesde().getTime() >= UtilsValidacion.fecha(itemProduccionBBCorrida.getCorFechaDesde(), "yyyy-MM-dd").getTime())
                    && (prdCorrida.getCorFechaDesde().getTime() <= UtilsValidacion.fecha(itemProduccionBBCorrida.getCorFechaHasta(), "yyyy-MM-dd").getTime())) {
                throw new GeneralException("La corrida " + itemProduccionBBCorrida.getCorNumero() + " tiene el mismo rango de fecha");
            }
        }
        String mensaje = insertarPrdCorrida(prdCorrida, sisInfoTO);
        return mensaje;
    }

    @Override
    public String insertarPrdCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (prdCorrida.getCorFechaDesde().after(new Date())) {
            return "FLa fecha desde no puede ser superior a la fecha: ." + UtilsDate.fechaFormatoString(new Date());
        }
        prdCorrida.getPrdCorridaPK().setCorNumero(prdCorrida.getPrdCorridaPK().getCorNumero().toUpperCase());
        String validarFechasComprasyConsumosHuerfanos = corridaDao.validarFechasComprasyConsumosHuerfanos(
                prdCorrida.getPrdCorridaPK().getCorEmpresa(), prdCorrida.getPrdCorridaPK().getCorSector(),
                prdCorrida.getPrdCorridaPK().getCorPiscina(), prdCorrida.getPrdCorridaPK().getCorNumero(),
                prdCorrida.getCorFechaDesde(), prdCorrida.getCorFechaHasta(), "INSERT");
        if (corridaDao.obtenerPorId(PrdCorrida.class, prdCorrida.getPrdCorridaPK()) != null) {
            retorno = "FLa corrida que va a ingresar ya existe. Intente con otro.";
        } else if (validarFechasComprasyConsumosHuerfanos != null && validarFechasComprasyConsumosHuerfanos.compareToIgnoreCase("") != 0) {
            retorno = "F" + validarFechasComprasyConsumosHuerfanos;
        } else {
            sisInfoTO.setEmpresa(prdCorrida.getPrdCorridaPK().getCorEmpresa());
            susDetalle = "Se insertó la corrida del Sector " + prdCorrida.getPrdCorridaPK().getCorSector() + " de la piscina " + prdCorrida.getPrdCorridaPK().getCorPiscina() + " con numero " + prdCorrida.getPrdCorridaPK().getCorNumero();
            susClave = prdCorrida.getPrdCorridaPK().getCorSector() + " | " + prdCorrida.getPrdCorridaPK().getCorPiscina() + " | " + prdCorrida.getPrdCorridaPK().getCorNumero();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_corrida";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (prdCorrida.getCorFechaHasta() == null) {
                prdCorrida.setPrdCorridaDetalleList(new ArrayList<PrdCorridaDetalle>());
            }
            prdCorrida.setCorLaboratorio(prdCorrida.getCorLaboratorio() != null ? prdCorrida.getCorLaboratorio().toUpperCase() : "");
            prdCorrida.setCorNauplio(prdCorrida.getCorNauplio() != null ? prdCorrida.getCorNauplio().toUpperCase() : "");
            prdCorrida.setCorObservaciones(prdCorrida.getCorObservaciones() != null ? prdCorrida.getCorObservaciones().toUpperCase() : "");
            prdCorrida.setCorTipoSiembra(prdCorrida.getCorTipoSiembra() == null
                    || prdCorrida.getCorTipoSiembra().compareToIgnoreCase("") == 0 ? "DIRECTO" : prdCorrida.getCorTipoSiembra());
            prdCorrida.setCorActiva(true);
            prdCorrida.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdCorrida.setUsrCodigo(sisInfoTO.getUsuario());
            prdCorrida.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (corridaDao.insertarPrdCorrida(prdCorrida, sisSuceso)) {
                retorno = "TLa corrida N. " + prdCorrida.getPrdCorridaPK().getCorNumero() + " se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar la corrida. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarCorrida(PrdCorrida prdCorrida, boolean cambioFechaDesde, boolean cambioFechaHasta, SisInfoTO sisInfoTO) throws Exception {
        List<PrdListaCorridaTO> listaCorrida = getListaCorridaTO(prdCorrida.getPrdCorridaPK().getCorEmpresa(), prdCorrida.getPrdCorridaPK().getCorSector(), prdCorrida.getPrdCorridaPK().getCorPiscina(), null, null, null);
        if (cambioFechaDesde && prdCorrida.getCorFechaDesde() != null) {
            SisPeriodo estadoPeriodoInicio = periodoService.getPeriodoPorFecha(prdCorrida.getCorFechaDesde(), prdCorrida.getPrdCorridaPK().getCorEmpresa());
            if (estadoPeriodoInicio == null) {
                throw new GeneralException("No existe periodo para la fecha que inicializa la corrida");
            } else {
                if (estadoPeriodoInicio.getPerCerrado()) {
                    throw new GeneralException("El periodo que inicializa la corrida esta cerrado ");
                }
            }
        }
        if (cambioFechaHasta && prdCorrida.getCorFechaHasta() != null) {
            SisPeriodo estadoPeriodoInicio = periodoService.getPeriodoPorFecha(prdCorrida.getCorFechaHasta(), prdCorrida.getPrdCorridaPK().getCorEmpresa());
            if (estadoPeriodoInicio == null) {
                throw new GeneralException("No existe periodo para la fecha que finaliza la corrida");
            } else {
                if (estadoPeriodoInicio.getPerCerrado()) {
                    throw new GeneralException("El periodo que finaliza la corrida esta cerrado ");
                }
            }
        }
        for (PrdListaCorridaTO itemProduccionBBCorrida : listaCorrida) {
            if (itemProduccionBBCorrida.getCorFechaHasta() != null) {
                if ((prdCorrida.getCorFechaDesde().getTime() >= UtilsValidacion.fecha(itemProduccionBBCorrida.getCorFechaDesde(), "yyyy-MM-dd").getTime()) && (prdCorrida.getCorFechaDesde().getTime() <= UtilsValidacion.fecha(itemProduccionBBCorrida.getCorFechaHasta(), "yyyy-MM-dd").getTime())) {
                    if (!prdCorrida.getPrdCorridaPK().getCorNumero().equalsIgnoreCase(itemProduccionBBCorrida.getCorNumero())) {
                        throw new GeneralException("La corrida " + itemProduccionBBCorrida.getCorNumero() + " tiene el mismo rango de fecha");
                    }
                }
            } else {
                if (prdCorrida.getCorFechaHasta() == null && !prdCorrida.getPrdCorridaPK().getCorNumero().equalsIgnoreCase(itemProduccionBBCorrida.getCorNumero())) {
                    throw new GeneralException("No se puede modificar la corrida, si la corrida número " + itemProduccionBBCorrida.getCorNumero() + " se encuentra abierta");
                }
            }
        }
        prdCorrida.setCorActiva(true);
        String mensaje = modificarPrdCorrida(prdCorrida, sisInfoTO);
        return mensaje;
    }

    @Override
    public String modificarPrdCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (prdCorrida.getCorFechaDesde().after(new Date())) {
            return "FLa fecha desde no puede ser superior a la fecha: ." + UtilsDate.fechaFormatoString(new Date());
        }
        String validarFechasComprasyConsumosHuerfanos = corridaDao.validarFechasComprasyConsumosHuerfanos(
                prdCorrida.getPrdCorridaPK().getCorEmpresa(), prdCorrida.getPrdCorridaPK().getCorSector(),
                prdCorrida.getPrdCorridaPK().getCorPiscina(), prdCorrida.getPrdCorridaPK().getCorNumero(),
                prdCorrida.getCorFechaDesde(), prdCorrida.getCorFechaHasta(), "UPDATE");
        if (validarFechasComprasyConsumosHuerfanos != null && validarFechasComprasyConsumosHuerfanos.compareToIgnoreCase("") != 0) {
            retorno = "F" + validarFechasComprasyConsumosHuerfanos;
        } else {
            sisInfoTO.setEmpresa(prdCorrida.getPrdCorridaPK().getCorEmpresa());
            susDetalle = "Se modificó la corrida del Sector " + prdCorrida.getPrdCorridaPK().getCorSector() + " de la piscina " + prdCorrida.getPrdCorridaPK().getCorPiscina() + " con numero " + prdCorrida.getPrdCorridaPK().getCorNumero();
            susClave = prdCorrida.getPrdCorridaPK().getCorSector() + " | " + prdCorrida.getPrdCorridaPK().getCorPiscina() + " | " + prdCorrida.getPrdCorridaPK().getCorNumero();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_corrida";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            BigDecimal sum = new BigDecimal("100.00");
            if (prdCorrida.getCorFechaHasta() == null) {
                prdCorrida.setPrdCorridaDetalleList(new ArrayList<PrdCorridaDetalle>());
            } else {
                //evaluar que porcentaje no exceda a 100
                if (prdCorrida.getPrdCorridaDetalleList() != null && prdCorrida.getPrdCorridaDetalleList().size() > 0) {
                    sum = BigDecimal.ZERO;
                    for (PrdCorridaDetalle item : prdCorrida.getPrdCorridaDetalleList()) {
                        sum = sum.add((item.getDetPorcentaje() != null ? item.getDetPorcentaje() : BigDecimal.ZERO));
                    }
                }
            }

            if (sum.compareTo(new BigDecimal("100")) != 0) {
                retorno = "FLa suma de porcentajes debe ser igual al 100%";
            } else {
                prdCorrida.getPrdCorridaPK().setCorNumero(prdCorrida.getPrdCorridaPK().getCorNumero().toUpperCase());
                prdCorrida.setCorLaboratorio(prdCorrida.getCorLaboratorio() != null ? prdCorrida.getCorLaboratorio().toUpperCase() : "");
                prdCorrida.setCorNauplio(prdCorrida.getCorNauplio() != null ? prdCorrida.getCorNauplio().toUpperCase() : "");
                prdCorrida.setCorObservaciones(prdCorrida.getCorObservaciones() != null ? prdCorrida.getCorObservaciones().toUpperCase() : "");

                if ((prdCorrida.getUsrEmpresa() == null || prdCorrida.getUsrEmpresa().compareToIgnoreCase("") == 0)
                        || (prdCorrida.getUsrCodigo() == null || prdCorrida.getUsrCodigo().compareToIgnoreCase("") == 0)
                        || prdCorrida.getUsrFechaInserta() == null || prdCorrida.getConContable() == null) {
                    PrdCorrida pc = corridaDao.obtenerCorrida(prdCorrida.getPrdCorridaPK());
                    prdCorrida.setConContable(pc.getConContable());
                    prdCorrida.setUsrEmpresa(pc.getUsrEmpresa());
                    prdCorrida.setUsrCodigo(pc.getUsrCodigo());
                    prdCorrida.setUsrFechaInserta(pc.getUsrFechaInserta());
                }
                if (corridaDao.modificarPrdCorrida(prdCorrida, sisSuceso)) {
                    retorno = "TLa corrida N. " + prdCorrida.getPrdCorridaPK().getCorNumero() + " se ha modificado correctamente.";
                } else {
                    retorno = "FHubo un error al guardar la corrida. Intente de nuevo o contacte con el administrador";
                }
            }
        }
        return retorno;
    }

    @Override
    public String eliminarPrdCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdCorrida.getPrdCorridaPK().getCorEmpresa());
        prdCorrida.getPrdCorridaPK().setCorNumero(prdCorrida.getPrdCorridaPK().getCorNumero().toUpperCase());

        if (!corridaDao.eliminarPrdCorrida(prdCorrida.getPrdCorridaPK().getCorEmpresa(),
                prdCorrida.getPrdCorridaPK().getCorSector(), prdCorrida.getPrdCorridaPK().getCorPiscina(),
                prdCorrida.getPrdCorridaPK().getCorNumero())) {
            retorno = "FNo se puede eliminar la corrida porque presenta movimientos";
        } else {
            susDetalle = "Se eliminó a la corrida del Sector " + prdCorrida.getPrdCorridaPK().getCorSector() + " de la piscina " + prdCorrida.getPrdCorridaPK().getCorPiscina() + " con numero " + prdCorrida.getPrdCorridaPK().getCorNumero();
            susClave = prdCorrida.getPrdCorridaPK().getCorSector() + " | " + prdCorrida.getPrdCorridaPK().getCorPiscina() + " | " + prdCorrida.getPrdCorridaPK().getCorNumero();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_corrida";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            if (corridaDao.eliminarPrdCorrida(prdCorrida, sisSuceso)) {
                retorno = "TLa corrida se eliminó correctamente.";
            } else {
                retorno = "FHubo un error al guardar la corrida. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public Map<String, Object> obtenerDetalleCorrida(String empresa, String sector, String piscina, String numero, Date fechaHasta) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<PrdCorridaDetalle> listaCorridaOrigenDetalle = corridaDetalleService.getCorridaDetalleDestinoPorCorrida(empresa, sector, piscina, numero);
        List<PrdCorridaDetalle> listaCorridaDestinoDetalle = corridaDetalleService.getCorridaDetalleOrigenPorCorrida(empresa, sector, piscina, numero);
        Boolean estadoPeriodoHasta = true;
        if (fechaHasta != null) {
            SisPeriodo estadoPeriodoInicio = periodoService.getPeriodoPorFecha(fechaHasta, empresa);
            if (estadoPeriodoInicio == null) {
                estadoPeriodoHasta = true;
            } else {
                if (estadoPeriodoInicio.getPerCerrado()) {
                    estadoPeriodoHasta = false;
                }
            }
        }
        if (listaCorridaDestinoDetalle != null && !listaCorridaDestinoDetalle.isEmpty()) {
            for (PrdCorridaDetalle corridaDetalle : listaCorridaDestinoDetalle) {
                PrdPiscinaPK pk = new PrdPiscinaPK(corridaDetalle.getPrdCorridaDestino().getPrdCorridaPK().getCorEmpresa(), corridaDetalle.getPrdCorridaDestino().getPrdCorridaPK().getCorSector(), corridaDetalle.getPrdCorridaDestino().getPrdCorridaPK().getCorPiscina());
                PrdPiscina piscinaDestino = piscinaDao.obtenerPorId(PrdPiscina.class, pk);
                corridaDetalle.getPrdCorridaDestino().setPrdPiscina(piscinaDestino);
            }
        }
        if (listaCorridaOrigenDetalle != null && !listaCorridaOrigenDetalle.isEmpty()) {
            for (PrdCorridaDetalle corridaDetalle : listaCorridaOrigenDetalle) {
                PrdPiscinaPK pk = new PrdPiscinaPK(corridaDetalle.getPrdCorridaOrigen().getPrdCorridaPK().getCorEmpresa(), corridaDetalle.getPrdCorridaOrigen().getPrdCorridaPK().getCorSector(), corridaDetalle.getPrdCorridaOrigen().getPrdCorridaPK().getCorPiscina());
                PrdPiscina piscinaOrigen = piscinaDao.obtenerPorId(PrdPiscina.class, pk);
                corridaDetalle.getPrdCorridaOrigen().setPrdPiscina(piscinaOrigen);
            }
        }
        campos.put("listaCorridaDetalleOrigen", listaCorridaOrigenDetalle);
        campos.put("listaCorridaDetalleDestino", listaCorridaDestinoDetalle);
        campos.put("estadoPeriodoHasta", estadoPeriodoHasta);
        return campos;
    }

    @Override
    public String cambiarCodigoPrdCorrida(PrdCorridaPK prdCorridaPK, String nuevoCodigoPrdCorrida) throws Exception {
        List<PrdCorrida> listaCorrida = corridaDao.getListaCorridasPorEmpresaSectorPiscina(prdCorridaPK.getCorEmpresa(), prdCorridaPK.getCorSector(), prdCorridaPK.getCorPiscina());
        String mensaje = "";
        for (PrdCorrida prdCorrida : listaCorrida) {
            if (prdCorrida.getPrdCorridaPK().getCorNumero().equalsIgnoreCase(nuevoCodigoPrdCorrida)) {
                mensaje = "FYa existe una corrida con el numero " + nuevoCodigoPrdCorrida;
            }
        }
        if ((mensaje).equals("")) {
            mensaje = corridaDao.cambiarCodigoPrdCorrida(prdCorridaPK, nuevoCodigoPrdCorrida);
        }
        return mensaje;
    }

    @Override
    public List<PrdCorrida> getListaCorridasPorEmpresaSectorPiscina(String empresa, String sector, String piscina)
            throws Exception {
        return corridaDao.getListaCorridasPorEmpresaSectorPiscina(empresa, sector, piscina);
    }

    @Override
    public List<PrdCorrida> getListaCorridasPorEmpresa(String empresa) throws Exception {
        return corridaDao.getListaCorridasPorEmpresa(empresa);
    }

    @Override
    public List<PrdCorrida> getCorridaPorNumero(String empresa, String sector, String piscina, String numero)
            throws Exception {
        return corridaDao.getCorridaPorNumero(empresa, sector, piscina, numero);
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudCorrida(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        Date fechaDesde = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaDesde"));//FECHA DESDE DE CORRIDA 
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));

        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo periodo = null;
        List<PrdListaSectorTO> sectores = sectorService.getListaSectorTO(empresa, true);
        List<PrdListaSectorTO> todoLosSectores = sectorService.obtenerTodosLosSectores(empresa);

        List<String> laboratorios = corridaDao.obtenerLaboratiroDeCorrida(empresa);
        List<String> nauplios = corridaDao.obtenerNauplioDeCorrida(empresa);

        if (empresa != null && sector != null && piscina != null && numero != null) {
            List<PrdCorridaDetalle> listaCorridaOrigenDetalle = corridaDetalleService.getCorridaDetalleDestinoPorCorrida(empresa, sector, piscina, numero);
            List<PrdCorridaDetalle> listaCorridaDestinoDetalle = corridaDetalleService.getCorridaDetalleOrigenPorCorrida(empresa, sector, piscina, numero);
            Boolean estadoPeriodoHasta = true;
            if (fechaHasta != null) {
                SisPeriodo estadoPeriodoInicio = periodoService.getPeriodoPorFecha(fechaHasta, empresa);
                if (estadoPeriodoInicio == null) {
                    estadoPeriodoHasta = true;
                } else {
                    if (estadoPeriodoInicio.getPerCerrado()) {
                        estadoPeriodoHasta = false;
                    }
                }
            }
            if (listaCorridaDestinoDetalle != null && !listaCorridaDestinoDetalle.isEmpty()) {
                for (PrdCorridaDetalle corridaDetalle : listaCorridaDestinoDetalle) {
                    PrdCorrida c = corridaDao.obtenerCorrida(corridaDetalle.getPrdCorridaDestino().getPrdCorridaPK());
                    corridaDetalle.setPrdCorridaDestino(c);
                    corridaDetalle.getPrdCorridaDestino().setPrdCorridaDetalleList(new ArrayList<>());
                }
            }
            if (listaCorridaOrigenDetalle != null && !listaCorridaOrigenDetalle.isEmpty()) {
                for (PrdCorridaDetalle corridaDetalle : listaCorridaOrigenDetalle) {
                    PrdCorrida c = corridaDao.obtenerCorrida(corridaDetalle.getPrdCorridaOrigen().getPrdCorridaPK());
                    corridaDetalle.setPrdCorridaOrigen(c);
                    corridaDetalle.getPrdCorridaOrigen().setPrdCorridaDetalleList(new ArrayList<>());
                }
            }
            periodo = periodoService.getPeriodoPorFecha(fechaDesde, empresa);
            campos.put("listaCorridaDetalleOrigen", listaCorridaOrigenDetalle);
            campos.put("listaCorridaDetalleDestino", listaCorridaDestinoDetalle);
            campos.put("estadoPeriodoHasta", estadoPeriodoHasta);
        } else {
            periodo = periodoService.getPeriodoPorFecha(UtilsDate.date(fechaActual), empresa);
        }

        campos.put("fechaActual", fechaActual);
        campos.put("periodo", periodo);
        campos.put("sectores", sectores);
        campos.put("laboratorios", laboratorios);
        campos.put("nauplios", nauplios);
        campos.put("todoLosSectores", todoLosSectores);

        return campos;
    }

    @Override
    public List<PrdListaCostosDetalleCorridaTO> completarProductos(List<PrdListaCostosDetalleCorridaTO> consumos, String empresa) throws Exception {
        for (PrdListaCostosDetalleCorridaTO consumo : consumos) {
            consumo.setInvProducto(productoDao.buscarInvProducto(empresa, consumo.getCostoCodigo()));
        }
        return consumos;
    }

    @Override
    public List<PrdCorrida> getComboPrdCorridaFiltradoFecha(String empresa, String sector, String piscina, Date fecha) throws Exception {
        return corridaDao.getComboPrdCorridaFiltradoFecha(empresa, sector, piscina, fecha);
    }

    @Override
    public RetornoTO getPrdResumenTrazabilidadAlimentacionTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin, SisInfoTO sisInfoTO) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        List<PrdResumenTrazabilidadAlimentacionTO> listaResumen = corridaDao.getPrdResumenTrazabilidadAlimentacionTO(empresa, sector, tipo, fechaInicio, fechaFin);
        mensaje = "T";

        NumeroColumnaDesconocidadResumenTrazabilidadAlimentacion obj = new NumeroColumnaDesconocidadResumenTrazabilidadAlimentacion(listaResumen);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public RetornoTO getPrdResumenCorridaSubcategoriaTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin, SisInfoTO sisInfoTO) throws Exception {
        String mensaje;
        RetornoTO retornoTO = new RetornoTO();
        List<PrdResumenCorridaSubcategoriaTO> listaResumen = corridaDao.getPrdResumenCorridaSubcategoriaTO(empresa, sector, tipo, fechaInicio, fechaFin);
        mensaje = "T";

        NumeroColumnaDesconocidaResumenCorridaSubcategoria obj = new NumeroColumnaDesconocidaResumenCorridaSubcategoria(listaResumen);
        retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
        retornoTO.setColumnas(obj.getColumnas());
        retornoTO.setDatos(obj.getDatos());
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public List<PrdTrazabilidadCorridaTO> getPrdTrazabilidadCorridaTO(String empresa, String sector, String piscina, String corrida, BigDecimal costo, String tipo) throws Exception {
        return corridaDao.getPrdTrazabilidadCorridaTO(empresa, sector, piscina, corrida, costo, tipo);
    }

    @Override
    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductos(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception {
        return corridaDao.getPrdConsumosDetalladoProductos(empresa, motivo, sector, piscina, busqueda, fechaDesde, fechaHasta, equipo);
    }

    @Override
    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoCC(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception {
        return corridaDao.getPrdConsumosDetalladoProductosAgrupadoCC(empresa, motivo, sector, piscina, busqueda, fechaDesde, fechaHasta, equipo);
    }

    @Override
    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoEquipo(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception {
        return corridaDao.getPrdConsumosDetalladoProductosAgrupadoEquipo(empresa, motivo, sector, piscina, busqueda, fechaDesde, fechaHasta, equipo);
    }

    @Override
    public List<PrdContabilizarCorridaCostoVentaTO> getResumenCorridaCostoVenta(String empresa, String fechaInicio, String fechaFin) throws Exception {
        return corridaDao.getResumenCorridaCostoVenta(empresa, fechaInicio, fechaFin);
    }

    @Override
    public Map<String, Object> obtenerDatosConsumoDetalladoProductos(Map<String, Object> map) throws Exception {

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        Map<String, Object> campos = new HashMap<>();
        List<InvConsumosMotivoTO> listaMotivos = consumosMotivoService.getInvListaConsumoMotivoTO(empresa, false, null);
        List<PrdEquipoControl> listaEquipos = equipoControlService.listarEquiposControl(empresa);
        List<PrdListaSectorTO> listaSector = sectorService.getListaSectorTO(empresa, true);
        List<PrdListaPiscinaTO> listaPiscina = piscinaService.getListaPiscinaTO(empresa, null, true);

        campos.put("listaMotivos", listaMotivos);
        campos.put("listaEquipos", listaEquipos);
        campos.put("listaSector", listaSector);
        campos.put("listaPiscina", listaPiscina);
        return campos;
    }

}
