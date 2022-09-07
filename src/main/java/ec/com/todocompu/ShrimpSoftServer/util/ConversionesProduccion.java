/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util;

import java.math.BigDecimal;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodegaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPrecioTallaLiquidacionPescaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoEgresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoIngresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdVehiculosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplancton;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameajePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReproceso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoEgreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoIngreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSobrevivencia;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTallaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculosPK;

/**
 *
 * @author Andres Guachisaca
 */
public class ConversionesProduccion {

    // <editor-fold defaultstate="collapsed" desc="SECTOR">
    public static PrdSector convertirPrdSectorTO_PrdSector(PrdSectorTO prdSectorTO) throws Exception {
        PrdSector prdSector = new PrdSector();
        prdSector.setPrdSectorPK(new PrdSectorPK(prdSectorTO.getSecEmpresa(), prdSectorTO.getSecCodigo()));
        prdSector.setSecNombre(prdSectorTO.getSecNombre());
        prdSector.setSecLatitud(prdSectorTO.getSecLatitud());
        prdSector.setSecLongitud(prdSectorTO.getSecLongitud());
        prdSector.setSecActivo(prdSectorTO.getSecActivo());
        prdSector.setUsrEmpresa(prdSectorTO.getUsrEmpresa());
        prdSector.setUsrCodigo(prdSectorTO.getUsrCodigo());
        prdSector.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdSectorTO.getUsrFechaInsertaSector())));
        prdSector.setSecDireccion(prdSectorTO.getSecDireccion());
        prdSector.setSecTelefono(prdSectorTO.getSecTelefono());
        prdSector.setSecActividad(prdSectorTO.getSecActividad());
        return prdSector;
    }

    public static PrdPrecioTallaLiquidacionPesca convertirPrdPrecioTallaLiquidacionPescaTO_PrdPrecioTallaLiquidacionPesca(PrdPrecioTallaLiquidacionPescaTO precioTallaLiquidacionPescaTO) throws Exception {
        PrdPrecioTallaLiquidacionPesca precioTallaLiquidacionPesca = new PrdPrecioTallaLiquidacionPesca();
        PrdPrecioTallaLiquidacionPescaPK pk = new PrdPrecioTallaLiquidacionPescaPK();
        pk.setPrdFecha(precioTallaLiquidacionPescaTO.getPrdFecha());
        pk.setTallaCodigo(precioTallaLiquidacionPescaTO.getTallaCodigo());
        pk.setTallaEmpresa(precioTallaLiquidacionPescaTO.getTallaEmpresa());
        precioTallaLiquidacionPesca.setPrdPrecioTallaLiquidacionPescaPK(pk);

        precioTallaLiquidacionPesca.setPrdPrecio01(precioTallaLiquidacionPescaTO.getPrdPrecio01());
        precioTallaLiquidacionPesca.setPrdPrecio02(precioTallaLiquidacionPescaTO.getPrdPrecio02());
        precioTallaLiquidacionPesca.setPrdPrecio03(precioTallaLiquidacionPescaTO.getPrdPrecio03());
        precioTallaLiquidacionPesca.setPrdPrecio04(precioTallaLiquidacionPescaTO.getPrdPrecio04());
        precioTallaLiquidacionPesca.setPrdPrecio05(precioTallaLiquidacionPescaTO.getPrdPrecio05());
        precioTallaLiquidacionPesca.setPrdPrecio06(precioTallaLiquidacionPescaTO.getPrdPrecio06());

        precioTallaLiquidacionPesca.setUsrEmpresa(precioTallaLiquidacionPescaTO.getUsrEmpresa());
        precioTallaLiquidacionPesca.setUsrCodigo(precioTallaLiquidacionPescaTO.getUsrCodigo());
        precioTallaLiquidacionPesca.setUsrFechaInserta(precioTallaLiquidacionPescaTO.getUsrFechaInserta());

        return precioTallaLiquidacionPesca;
    }

    public static PrdPiscina convertirPrdPiscinaTO_PrdPiscina(PrdPiscinaTO prdPiscinaTO) {
        PrdPiscina prdPiscina = new PrdPiscina();
        prdPiscina.setPrdPiscinaPK(new PrdPiscinaPK(prdPiscinaTO.getPisEmpresa().trim(), prdPiscinaTO.getSecCodigo().trim(), prdPiscinaTO.getPisNumero().trim()));
        prdPiscina.setPisNombre(prdPiscinaTO.getPisNombre());
        prdPiscina.setPisHectareaje(prdPiscinaTO.getPisHectareaje());
        prdPiscina.setPisActiva(prdPiscinaTO.getPisActiva());
        prdPiscina.setCtaCostoDirecto(prdPiscinaTO.getCtaCostoDirecto() == null || prdPiscinaTO.getCtaCostoDirecto().equals("") ? null : prdPiscinaTO.getCtaCostoDirecto());
        prdPiscina.setCtaCostoIndirecto(prdPiscinaTO.getCtaCostoIndirecto() == null || prdPiscinaTO.getCtaCostoIndirecto().equals("") ? null : prdPiscinaTO.getCtaCostoIndirecto());
        prdPiscina.setCtaCostoTransferencia(prdPiscinaTO.getCtaCostoTransferencia() == null || prdPiscinaTO.getCtaCostoTransferencia().equals("") ? null : prdPiscinaTO.getCtaCostoTransferencia());
        prdPiscina.setCtaCostoProductoTerminado(prdPiscinaTO.getCtaCostoProductoTerminado() == null || prdPiscinaTO.getCtaCostoProductoTerminado().equals("") ? null : prdPiscinaTO.getCtaCostoProductoTerminado());
        prdPiscina.setUsrEmpresa(prdPiscinaTO.getUsrEmpresa());
        prdPiscina.setUsrCodigo(prdPiscinaTO.getUsrCodigo());
        prdPiscina.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdPiscinaTO.getUsrFechaInsertaPiscina())));
        prdPiscina.setCtaCostoVenta(prdPiscinaTO.getCtaCostoVenta() == null || prdPiscinaTO.getCtaCostoVenta().equals("") ? null : prdPiscinaTO.getCtaCostoVenta());
        return prdPiscina;
    }

    public static PrdPiscinaGrupoRelacion convertirPrdPiscinaGrupoRelacionTO_PrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacionTO prdPiscinaGrupoRelacionTO) {
        PrdPiscinaGrupoRelacion prdPiscinaGrupoRelacion = new PrdPiscinaGrupoRelacion();
        prdPiscinaGrupoRelacion.setGrupoSecuencial(prdPiscinaGrupoRelacionTO.getGrupoSecuencial());
        PrdPiscina piscina = new PrdPiscina();
        piscina.setPrdPiscinaPK(new PrdPiscinaPK(
                prdPiscinaGrupoRelacionTO.getPisEmpresa(),
                prdPiscinaGrupoRelacionTO.getPisSector(),
                prdPiscinaGrupoRelacionTO.getPisNumero())
        );
        prdPiscinaGrupoRelacion.setPrdPiscina(piscina);

        PrdPiscinaGrupo grupo = new PrdPiscinaGrupo();
        grupo.setPrdPiscinaGrupoPK(new PrdPiscinaGrupoPK(
                prdPiscinaGrupoRelacionTO.getGrupoEmpresa(),
                prdPiscinaGrupoRelacionTO.getGrupoCodigo()));

        prdPiscinaGrupoRelacion.setPrdPiscinaGrupo(grupo);

        prdPiscinaGrupoRelacion.setUsrEmpresa(prdPiscinaGrupoRelacionTO.getUsrEmpresa());
        prdPiscinaGrupoRelacion.setUsrCodigo(prdPiscinaGrupoRelacionTO.getUsrCodigo());
        prdPiscinaGrupoRelacion.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdPiscinaGrupoRelacionTO.getUsrFechaInserta())));
        return prdPiscinaGrupoRelacion;
    }

    public static PrdCorrida convertirPrdCorridaTO_PrdCorrida(PrdCorridaTO prdCorridaTO) {
        PrdCorrida prdCorrida = new PrdCorrida();
        prdCorrida.setPrdCorridaPK(new PrdCorridaPK(prdCorridaTO.getCorEmpresa(), prdCorridaTO.getCorSector(),
                prdCorridaTO.getCorPiscina(), prdCorridaTO.getCorNumero()));
        prdCorrida.setCorHectareas(prdCorridaTO.getCorHectareas());
        prdCorrida.setCorFechaDesde(UtilsValidacion.fecha(prdCorridaTO.getCorFechaDesde(), "yyyy-MM-dd"));
        prdCorrida.setCorFechaSiembra(UtilsValidacion.fecha(prdCorridaTO.getCorFechaSiembra(), "yyyy-MM-dd"));
        prdCorrida.setCorTipoSiembra(prdCorridaTO.getCorTipoSiembra());
        prdCorrida.setCorFechaPesca(UtilsValidacion.fecha(prdCorridaTO.getCorFechaPesca(), "yyyy-MM-dd"));
        prdCorrida.setCorFechaHasta(UtilsValidacion.fecha(prdCorridaTO.getCorFechaHasta(), "yyyy-MM-dd"));
        prdCorrida.setCorLaboratorio(prdCorridaTO.getCorLaboratorio());
        prdCorrida.setCorNauplio(prdCorridaTO.getCorNauplio());
        prdCorrida.setCorPellet(prdCorridaTO.getCorPellet());
        prdCorrida.setCorObservaciones(prdCorridaTO.getCorObservaciones());
        prdCorrida.setCorActiva(prdCorridaTO.getCorActiva());
        prdCorrida.setUsrCodigo(prdCorridaTO.getUsrCodigo());
        prdCorrida.setUsrEmpresa(prdCorridaTO.getUsrEmpresa());
        prdCorrida.setUsrFechaInserta(
                UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdCorridaTO.getUsrFechaInsertaCorrida())));

        prdCorrida.setCorNumeroLarvas(
                (prdCorridaTO.getCorNumeroLarvas() == 0) ? null : prdCorridaTO.getCorNumeroLarvas());

        prdCorrida.setCorBiomasa(
                (prdCorridaTO.getCorBiomasa().compareTo(BigDecimal.ZERO) == 0) ? null : prdCorridaTO.getCorBiomasa());

        prdCorrida.setCorValorVenta((prdCorridaTO.getCorValorVenta().compareTo(BigDecimal.ZERO) == 0) ? null
                : prdCorridaTO.getCorValorVenta());

        return prdCorrida;
    }

    public static PrdVehiculos convertirPrdVehiculosTO_PrdVehiculos(PrdVehiculosTO prdVehiculosTO) {
        PrdVehiculos prdVehiculos = new PrdVehiculos();
        prdVehiculos.setPrdVehiculosPK(new PrdVehiculosPK(prdVehiculosTO.getVehEmpresa(),
                prdVehiculosTO.getVehEstablecimiento(), prdVehiculosTO.getVehPlaca()));
        prdVehiculos.setVehMarca(prdVehiculosTO.getVehMarca());
        prdVehiculos.setVehModelo(prdVehiculosTO.getVehModelo());
        prdVehiculos.setVehColor(prdVehiculosTO.getVehColor());
        prdVehiculos.setVehAnio(prdVehiculosTO.getVehAnio());
        prdVehiculos.setVehMotor(prdVehiculosTO.getVehMotor());
        prdVehiculos.setVehChasis(prdVehiculosTO.getVehChasis());
        prdVehiculos.setVehOrigen(prdVehiculosTO.getVehOrigen());
        prdVehiculos.setVehTipo(prdVehiculosTO.getVehTipo());
        prdVehiculos.setVehTonelaje(prdVehiculosTO.getVehTonelaje());
        prdVehiculos.setVehCilindraje(prdVehiculosTO.getVehCilindraje());
        prdVehiculos.setVehCombustible(prdVehiculosTO.getVehCombustible());
        prdVehiculos.setVehTipoServicio(prdVehiculosTO.getVehTipoServicio());
        prdVehiculos.setVehObservaciones(prdVehiculosTO.getVehObservaciones());
        prdVehiculos.setVehCompraValor(prdVehiculosTO.getVehCompraValor());
        prdVehiculos.setVehCompraComision(prdVehiculosTO.getVehVentaComision());
        prdVehiculos.setVehCompraMultasCtg(prdVehiculosTO.getVehCompraMultasCtg());
        prdVehiculos.setVehCompraMultasAnt(prdVehiculosTO.getVehCompraMultasAnt());
        prdVehiculos.setVehVentaValor(prdVehiculosTO.getVehVentaValor());
        prdVehiculos.setVehVentaComision(prdVehiculosTO.getVehVentaComision());
        prdVehiculos.setVehMatriculadoANombreDe(prdVehiculosTO.getVehMatriculadoANombreDe());
        prdVehiculos.setVehProveedorId(prdVehiculosTO.getVehProveedorId());
        prdVehiculos.setVehProveedorNombre(prdVehiculosTO.getVehProveedorNombre());
        prdVehiculos.setVehProveedorDireccion(prdVehiculosTO.getVehProveedorDireccion());
        prdVehiculos.setVehProveedorTelefono(prdVehiculosTO.getVehProveedorTelefono());
        prdVehiculos.setVehClienteId(prdVehiculosTO.getVehClienteId());
        prdVehiculos.setVehClienteNombre(prdVehiculosTO.getVehClienteNombre());
        prdVehiculos.setVehClienteDireccion(prdVehiculosTO.getVehClienteDireccion());
        prdVehiculos.setVehClienteTelefono(prdVehiculosTO.getVehClienteTelefono());
        prdVehiculos.setVehPrimerTraspasoId(prdVehiculosTO.getVehPrimerTraspasoId());
        prdVehiculos.setVehPrimerTraspasoNombre(prdVehiculosTO.getVehPrimerTraspasoNombre());
        prdVehiculos.setVehPrimerTraspasoDireccion(prdVehiculosTO.getVehPrimerTraspasoDireccion());
        prdVehiculos.setVehPrimerTraspasoTelefono(prdVehiculosTO.getVehPrimerTraspasoTelefono());
        prdVehiculos.setVehSegundoTraspasoId(prdVehiculosTO.getVehSegundoTraspasoId());
        prdVehiculos.setVehSegundoTraspasoNombre(prdVehiculosTO.getVehSegundoTraspasoNombre());
        prdVehiculos.setVehSegundoTraspasoDireccion(prdVehiculosTO.getVehSegundoTraspasoDireccion());
        prdVehiculos.setVehSegundoTraspasoTelefono(prdVehiculosTO.getVehSegundoTraspasoTelefono());
        return prdVehiculos;
    }

    public static PrdSobrevivencia convertirPrdSobrevivenciaTO_PrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO) {
        PrdSobrevivencia prdSobrevivencia = new PrdSobrevivencia();
        prdSobrevivencia.setSobCodigo(prdSobrevivenciaTO.getSobCodigo());
        // prdSobrevivencia.setPrdSector(new PrdSector(
        // prdSobrevivenciaTO.getSecEmpresa(),
        // prdSobrevivenciaTO.getSecCodigo()));
        prdSobrevivencia.setSobDiasDesde(prdSobrevivenciaTO.getSobDiasDesde());
        prdSobrevivencia.setSobDiasHasta(prdSobrevivenciaTO.getSobDiasHasta());
        prdSobrevivencia.setSobSobrevivencia(prdSobrevivenciaTO.getSobSobrevivencia());
        prdSobrevivencia.setSobAlimentacion(prdSobrevivenciaTO.getSobAlimentacion());
        prdSobrevivencia.setUsrEmpresa(prdSobrevivenciaTO.getUsrEmpresa());
        prdSobrevivencia.setUsrCodigo(prdSobrevivenciaTO.getUsrCodigo());
        prdSobrevivencia.setUsrFechaInserta(
                UtilsValidacion.fechaString_Date(prdSobrevivenciaTO.getUsrFechaInsertaSobrevivencia()));
        return prdSobrevivencia;
    }

    public static PrdGrameaje convertirPrdGrameajeTO_PrdGrameaje(PrdGrameajeTO prdGrameajeTO) {
        PrdGrameaje prdGrameaje = new PrdGrameaje();
        prdGrameaje.setPrdGrameajePK(new PrdGrameajePK(prdGrameajeTO.getGraEmpresa(), prdGrameajeTO.getGraSector(),
                prdGrameajeTO.getGraPiscina(), UtilsValidacion.fecha(prdGrameajeTO.getGraFecha(), "yyyy-MM-dd")));
        prdGrameaje.setGraTgrande(prdGrameajeTO.getGraTGrande());
        prdGrameaje.setGraTmediano(prdGrameajeTO.getGraTMediano());
        prdGrameaje.setGraTpequeno(prdGrameajeTO.getGraTPequeno());
        prdGrameaje.setGraTpromedio(prdGrameajeTO.getGraTPromedio());
        prdGrameaje.setGraItgrande(prdGrameajeTO.getGraItGrande());
        prdGrameaje.setGraItmediano(prdGrameajeTO.getGraItMediano());
        prdGrameaje.setGraItpequeno(prdGrameajeTO.getGraItPequeno());
        prdGrameaje.setGraIpromedio(prdGrameajeTO.getGraItPromedio());
        prdGrameaje.setGraBiomasa(prdGrameajeTO.getGraBiomasa());
        prdGrameaje.setGraSobrevivencia(prdGrameajeTO.getGraSobrevivencia());
        prdGrameaje.setGraAlimentacion(prdGrameajeTO.getGraAlimentacion());
        prdGrameaje.setGraLibrasBalanceado(prdGrameajeTO.getGraLibrasBalanceado());
        prdGrameaje.setGraCostoDirecto(prdGrameajeTO.getGraCostoDirecto());
        prdGrameaje.setGraCostoIndirecto(prdGrameajeTO.getGraCostoIndirecto());
        prdGrameaje.setGraComentario(prdGrameajeTO.getGraComentario());
        prdGrameaje.setGraAnulado(prdGrameajeTO.getGraAnulado());
        prdGrameaje.setUsrEmpresa(prdGrameajeTO.getUsrEmpresa());
        prdGrameaje.setUsrCodigo(prdGrameajeTO.getUsrCodigo());
        prdGrameaje.setUsrFechaInserta(UtilsValidacion.fechaString_Date(prdGrameajeTO.getUsrFechaInsertaGrameaje()));
        return prdGrameaje;
    }

    public static PrdFitoplancton convertirPrdFitoplanctonTO_PrdFitoplancton(PrdFitoplanctonTO prdFitoplanctonTO) {
        PrdFitoplancton prdFitoplancton = new PrdFitoplancton();
        prdFitoplancton.setFitClorophytas(prdFitoplanctonTO.getFitClorophytasActual());
        prdFitoplancton.setFitCyanofitas(prdFitoplanctonTO.getFitCyanofitasActual());
        prdFitoplancton.setFitDiatomeas(prdFitoplanctonTO.getFitDiatomeasActual());
        prdFitoplancton.setFitDinoflagelados(prdFitoplanctonTO.getFitDinoflageladosActual());
        prdFitoplancton.setFitEuglenales(prdFitoplanctonTO.getFitEuglenalesActual());
        prdFitoplancton.setFitPorcentajeSalinidad(prdFitoplanctonTO.getFitPorcentajeSalinidadActual());

        return prdFitoplancton;
    }

    public static PrdGrameaje convertirPrdGrameaje_PrdGrameaje(PrdGrameaje prdGrameajeAux) {
        if (prdGrameajeAux != null) {
            PrdGrameaje prdGrameaje = new PrdGrameaje();
            prdGrameaje.setPrdGrameajePK(prdGrameajeAux.getPrdGrameajePK());
            prdGrameaje.setGraTgrande(prdGrameajeAux.getGraTgrande());
            prdGrameaje.setGraTmediano(prdGrameajeAux.getGraTmediano());
            prdGrameaje.setGraTpequeno(prdGrameajeAux.getGraTpequeno());
            prdGrameaje.setGraTpromedio(prdGrameajeAux.getGraTpromedio());
            prdGrameaje.setGraItgrande(prdGrameajeAux.getGraItgrande());
            prdGrameaje.setGraItmediano(prdGrameajeAux.getGraItmediano());
            prdGrameaje.setGraItpequeno(prdGrameajeAux.getGraItpequeno());
            prdGrameaje.setGraIpromedio(prdGrameajeAux.getGraIpromedio());
            prdGrameaje.setGraBiomasa(prdGrameajeAux.getGraBiomasa());
            prdGrameaje.setGraSobrevivencia(prdGrameajeAux.getGraSobrevivencia());
            prdGrameaje.setGraAlimentacion(prdGrameajeAux.getGraAlimentacion());
            prdGrameaje.setGraLibrasBalanceado(prdGrameajeAux.getGraLibrasBalanceado());
            prdGrameaje.setGraCostoDirecto(prdGrameajeAux.getGraCostoDirecto());
            prdGrameaje.setGraCostoIndirecto(prdGrameajeAux.getGraCostoIndirecto());
            prdGrameaje.setGraComentario(prdGrameajeAux.getGraComentario());
            prdGrameaje.setGraAnulado(prdGrameajeAux.getGraAnulado());
            prdGrameaje.setUsrEmpresa(prdGrameajeAux.getUsrEmpresa());
            prdGrameaje.setUsrCodigo(prdGrameajeAux.getUsrCodigo());
            prdGrameaje.setUsrFechaInserta(prdGrameajeAux.getUsrFechaInserta());
            return prdGrameaje;
        } else {
            return null;
        }

    }

    public static PrdLiquidacionMotivoTO convertirPrdLiquidacionMotivo_PrdLiquidacionMotivoTO(
            PrdLiquidacionMotivo prdLiquidacionMotivo) {
        PrdLiquidacionMotivoTO prdLiquidacionMotivoTO = new PrdLiquidacionMotivoTO();
        prdLiquidacionMotivoTO.setLmEmpresa(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmEmpresa());
        prdLiquidacionMotivoTO.setLmCodigo(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo());
        prdLiquidacionMotivoTO.setLmDetalle(prdLiquidacionMotivo.getLmDetalle());
        prdLiquidacionMotivoTO.setLmInactivo(prdLiquidacionMotivo.getLmInactivo());
        prdLiquidacionMotivoTO.setUsrCodigo(prdLiquidacionMotivo.getUsrCodigo());
        prdLiquidacionMotivoTO
                .setUsrFechaInserta(UtilsValidacion.fecha(prdLiquidacionMotivo.getUsrFechaInserta(), "yyyy-MM-dd"));
        return prdLiquidacionMotivoTO;
    }

    public static PrdLiquidacionMotivo convertirPrdLiquidacionMotivoTO_PrdLiquidacionMotivo(
            PrdLiquidacionMotivoTO prdLiquidacionMotivoTO) {
        PrdLiquidacionMotivo prdLiquidacionMotivo = new PrdLiquidacionMotivo();
        prdLiquidacionMotivo.setPrdLiquidacionMotivoPK(new PrdLiquidacionMotivoPK(prdLiquidacionMotivoTO.getLmEmpresa(),
                prdLiquidacionMotivoTO.getLmCodigo()));
        prdLiquidacionMotivo.setLmDetalle(prdLiquidacionMotivoTO.getLmDetalle());
        prdLiquidacionMotivo.setLmInactivo(prdLiquidacionMotivoTO.getLmInactivo());
        prdLiquidacionMotivo.setUsrEmpresa(prdLiquidacionMotivoTO.getLmEmpresa());
        prdLiquidacionMotivo.setUsrCodigo(prdLiquidacionMotivoTO.getUsrCodigo());
        prdLiquidacionMotivo.setUsrFechaInserta(
                UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdLiquidacionMotivoTO.getUsrFechaInserta())));
        return prdLiquidacionMotivo;
    }

    public static PrdTalla convertirPrdLiquidacionTallaTO_PrdLiquidacionTalla(
            PrdLiquidacionTallaTO prdLiquidacionTallaTO) {
        PrdTalla prdLiquidacionTalla = new PrdTalla();
        prdLiquidacionTalla.setPrdLiquidacionTallaPK(
                new PrdTallaPK(prdLiquidacionTallaTO.getTallaEmpresa(), prdLiquidacionTallaTO.getTallaCodigo()));
        prdLiquidacionTalla.setTallaDetalle(prdLiquidacionTallaTO.getTallaDetalle());
        prdLiquidacionTalla.setTallaDescripcion(prdLiquidacionTallaTO.getTallaDescripcion());
        prdLiquidacionTalla.setTallaInactivo(prdLiquidacionTallaTO.getTallaInactivo());
        prdLiquidacionTalla.setUsrEmpresa(prdLiquidacionTallaTO.getTallaEmpresa());
        prdLiquidacionTalla.setUsrCodigo(prdLiquidacionTallaTO.getUsrCodigo());
        prdLiquidacionTalla.setUsrFechaInserta(
                UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdLiquidacionTallaTO.getUsrFechaInserta())));
        return prdLiquidacionTalla;
    }

    public static PrdLiquidacionTallaTO convertirPrdLiquidacionTalla_PrdLiquidacionTallaTO(
            PrdTalla prdLiquidacionTalla) {
        PrdLiquidacionTallaTO prdLiquidacionMotivoTO = new PrdLiquidacionTallaTO();
        prdLiquidacionMotivoTO.setTallaEmpresa(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaEmpresa());
        prdLiquidacionMotivoTO.setTallaCodigo(prdLiquidacionTalla.getPrdLiquidacionTallaPK().getTallaCodigo());
        prdLiquidacionMotivoTO.setTallaDetalle(prdLiquidacionTalla.getTallaDetalle());
        prdLiquidacionMotivoTO.setTallaDescripcion(prdLiquidacionTalla.getTallaDescripcion());
        prdLiquidacionMotivoTO.setTallaInactivo(prdLiquidacionTalla.getTallaInactivo());
        prdLiquidacionMotivoTO.setUsrCodigo(prdLiquidacionTalla.getUsrCodigo());
        prdLiquidacionMotivoTO
                .setUsrFechaInserta(UtilsValidacion.fecha(prdLiquidacionTalla.getUsrFechaInserta(), "yyyy-MM-dd"));
        return prdLiquidacionMotivoTO;
    }

    public static PrdLiquidacionProductoTO convertirPrdLiquidacionProducto_PrdLiquidacionProductoTO(
            PrdProducto prdLiquidacionProducto) {
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = new PrdLiquidacionProductoTO();
        prdLiquidacionProductoTO.setProdEmpresa(prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdEmpresa());
        prdLiquidacionProductoTO.setProdCodigo(prdLiquidacionProducto.getPrdLiquidacionProductoPK().getProdCodigo());
        prdLiquidacionProductoTO.setProdDetalle(prdLiquidacionProducto.getProdDetalle());
        prdLiquidacionProductoTO.setProdTipo(prdLiquidacionProducto.getProdTipo());
        prdLiquidacionProductoTO.setProdClase(prdLiquidacionProducto.getProdClase());
        prdLiquidacionProductoTO.setProdInactivo(prdLiquidacionProducto.getProdInactivo());
        prdLiquidacionProductoTO.setUsrCodigo(prdLiquidacionProducto.getUsrCodigo());
        prdLiquidacionProductoTO
                .setUsrFechaInserta(UtilsValidacion.fecha(prdLiquidacionProducto.getUsrFechaInserta(), "yyyy-MM-dd"));

        prdLiquidacionProductoTO.setProdDescuento(prdLiquidacionProducto.getProdDescuento());
        return prdLiquidacionProductoTO;
    }

    public static PrdProducto convertirPrdLiquidacionProductoTO_PrdLiquidacionProducto(
            PrdLiquidacionProductoTO prdLiquidacionProductoTO) {
        PrdProducto prdLiquidacionProducto = new PrdProducto();
        prdLiquidacionProducto.setPrdLiquidacionProductoPK(
                new PrdProductoPK(prdLiquidacionProductoTO.getProdEmpresa(), prdLiquidacionProductoTO.getProdCodigo()));
        prdLiquidacionProducto.setProdDetalle(prdLiquidacionProductoTO.getProdDetalle());
        prdLiquidacionProducto.setProdTipo(prdLiquidacionProductoTO.getProdTipo());
        prdLiquidacionProducto.setProdClase(prdLiquidacionProductoTO.getProdClase());
        prdLiquidacionProducto.setProdInactivo(prdLiquidacionProductoTO.getProdInactivo());
        prdLiquidacionProducto.setUsrEmpresa(prdLiquidacionProductoTO.getProdEmpresa());
        prdLiquidacionProducto.setUsrCodigo(prdLiquidacionProductoTO.getUsrCodigo());
        prdLiquidacionProducto
                .setUsrFechaInserta(UtilsValidacion.fechaString_Date(prdLiquidacionProductoTO.getUsrFechaInserta()));
        prdLiquidacionProducto.setProdDescuento(prdLiquidacionProductoTO.getProdDescuento());
        return prdLiquidacionProducto;
    }

    public static PrdLiquidacionDetalle convertirPrdLiquidacionDetalleTO_PrdLiquidacionDetalle(
            PrdLiquidacionDetalleTO prdLiquidacionDetalleTO) {
        PrdLiquidacionDetalle prdLiquidacionDetalle = new PrdLiquidacionDetalle();
        prdLiquidacionDetalle.setDetSecuencial(prdLiquidacionDetalleTO.getDetSecuencial());
        prdLiquidacionDetalle.setDetLibras(prdLiquidacionDetalleTO.getDetLibras());
        prdLiquidacionDetalle.setDetPrecio(prdLiquidacionDetalleTO.getDetPrecio());
        prdLiquidacionDetalle.setDetOrden(prdLiquidacionDetalleTO.getDetOrden());
        return prdLiquidacionDetalle;
    }

    public static PrdLiquidacion convertirPrdLiquidacionTO_PrdLiquidacion(PrdLiquidacionTO prdLiquidacionTO) {
        PrdLiquidacion prdLiquidacion = new PrdLiquidacion();
        prdLiquidacion.setPrdLiquidacionPK(new PrdLiquidacionPK(prdLiquidacionTO.getLiqEmpresa(),
                prdLiquidacionTO.getLiqMotivo(), prdLiquidacionTO.getLiqNumero()));
        prdLiquidacion.setPrdPiscina(new PrdPiscina(prdLiquidacionTO.getPisEmpresa(), prdLiquidacionTO.getPisSector(),
                prdLiquidacionTO.getPisNumero()));

        prdLiquidacion.setLiqAnimalesCosechados(prdLiquidacionTO.getLiqAnimalesCosechados());
        prdLiquidacion.setLiqAnulado(prdLiquidacionTO.isLiqAnulado());
        prdLiquidacion.setLiqFecha(UtilsValidacion.fecha(prdLiquidacionTO.getLiqFecha(), "yyyy-MM-dd"));
        // invCompras.setProvCodigo(invComprasTO.getProvCodigo());
        prdLiquidacion.setLiqLibrasBasura(prdLiquidacionTO.getLiqLibrasBasura());
        prdLiquidacion.setLiqLibrasCola(prdLiquidacionTO.getLiqLibrasCola());
        prdLiquidacion.setLiqLibrasColaProcesadas(prdLiquidacionTO.getLiqLibrasColaProcesadas());
        prdLiquidacion.setLiqLibrasEntero(prdLiquidacionTO.getLiqLibrasEntero());
        prdLiquidacion.setLiqLibrasEnviadas(prdLiquidacionTO.getLiqLibrasEnviadas());
        prdLiquidacion.setLiqLibrasNetas(prdLiquidacionTO.getLiqLibrasNetas());
        prdLiquidacion.setLiqLibrasRecibidas(prdLiquidacionTO.getLiqLibrasRecibidas());
        prdLiquidacion.setLiqLibrasRetiradas(prdLiquidacionTO.getLiqLibrasRetiradas());
        prdLiquidacion.setLiqLote(prdLiquidacionTO.getLiqLote());
        prdLiquidacion.setLiqPendiente(prdLiquidacionTO.isLiqPendiente());
        prdLiquidacion.setLiqTotalMonto(prdLiquidacionTO.getLiqTotalMonto());

        /*
		 * prdLiquidacion.setCliCodigo(prdLiquidacionTO.getCliCodigo());
		 * prdLiquidacion.setCliEmpresa(prdLiquidacionTO.getCliEmpresa());
         */
        prdLiquidacion.setUsrEmpresa(prdLiquidacionTO.getUsrEmpresa());
        prdLiquidacion.setUsrCodigo(prdLiquidacionTO.getUsrCodigo());
        prdLiquidacion.setUsrFechaInserta(
                UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdLiquidacionTO.getUsrFechaInserta())));
        return prdLiquidacion;
    }

    public static PrdCorrida convertirPrdCorrida_PrdCorrida(PrdCorrida prdCorrida2) {
        PrdCorrida prdCorrida = new PrdCorrida();
        prdCorrida.setPrdCorridaPK(prdCorrida2.getPrdCorridaPK());
        prdCorrida.setCorHectareas(prdCorrida2.getCorHectareas());
        prdCorrida.setCorFechaDesde(prdCorrida2.getCorFechaDesde());
        prdCorrida.setCorFechaSiembra(prdCorrida2.getCorFechaSiembra());
        prdCorrida.setCorFechaPesca(prdCorrida2.getCorFechaPesca());
        prdCorrida.setCorFechaHasta(prdCorrida2.getCorFechaHasta());
        prdCorrida.setCorTipoSiembra(prdCorrida2.getCorTipoSiembra());
        prdCorrida.setCorTipoSiembra(prdCorrida2.getCorTipoSiembra());
        prdCorrida.setCorNumeroLarvas(prdCorrida2.getCorNumeroLarvas());
        prdCorrida.setCorLaboratorio(prdCorrida2.getCorLaboratorio());
        prdCorrida.setCorNauplio(prdCorrida2.getCorNauplio());
        prdCorrida.setCorPellet(prdCorrida2.getCorPellet());
        prdCorrida.setCorBiomasa(prdCorrida2.getCorBiomasa());
        prdCorrida.setCorCostoDirecto(prdCorrida2.getCorCostoDirecto());
        prdCorrida.setCorCostoIndirecto(prdCorrida2.getCorCostoIndirecto());
        prdCorrida.setCorCostoTransferencia(prdCorrida2.getCorCostoTransferencia());
        prdCorrida.setCorValorVenta(prdCorrida2.getCorValorVenta());
        prdCorrida.setCorObservaciones(prdCorrida2.getCorObservaciones());
        prdCorrida.setCorActiva(prdCorrida2.getCorActiva());
        prdCorrida.setUsrCodigo(prdCorrida2.getUsrCodigo());
        prdCorrida.setUsrEmpresa(prdCorrida2.getUsrEmpresa());
        prdCorrida.setUsrFechaInserta(prdCorrida2.getUsrFechaInserta());
        prdCorrida.setPrdPiscina(prdCorrida2.getPrdPiscina());
        prdCorrida.setPrdCorridaDetalleList(prdCorrida2.getPrdCorridaDetalleList());
        prdCorrida.setConContable(prdCorrida2.getConContable());
        prdCorrida.setCorNumeroLarvasPlus(prdCorrida2.getCorNumeroLarvasPlus());
        prdCorrida.setConContableCostoVenta(prdCorrida2.getConContableCostoVenta());
        return prdCorrida;
    }

    public static PrdLiquidacion convertirPrdLiquidacion_PrdLiquidacion(PrdLiquidacion prdLiquidacionAux) {
        PrdLiquidacion detalle = new PrdLiquidacion();
        detalle.setPrdLiquidacionPK(prdLiquidacionAux.getPrdLiquidacionPK());
        detalle.setLiqLote(prdLiquidacionAux.getLiqLote());
        detalle.setLiqFecha(prdLiquidacionAux.getLiqFecha());
        detalle.setLiqLibrasEnviadas(prdLiquidacionAux.getLiqLibrasEnviadas());
        detalle.setLiqLibrasRecibidas(prdLiquidacionAux.getLiqLibrasRecibidas());
        detalle.setLiqLibrasBasura(prdLiquidacionAux.getLiqLibrasBasura());
        detalle.setLiqLibrasRetiradas(prdLiquidacionAux.getLiqLibrasRetiradas());
        detalle.setLiqLibrasNetas(prdLiquidacionAux.getLiqLibrasNetas());
        detalle.setLiqLibrasEntero(prdLiquidacionAux.getLiqLibrasEntero());
        detalle.setLiqLibrasCola(prdLiquidacionAux.getLiqLibrasCola());
        detalle.setLiqLibrasColaProcesadas(prdLiquidacionAux.getLiqLibrasColaProcesadas());
        detalle.setLiqAnimalesCosechados(prdLiquidacionAux.getLiqAnimalesCosechados());
        detalle.setLiqTotalMonto(prdLiquidacionAux.getLiqTotalMonto());
        detalle.setLiqPendiente(prdLiquidacionAux.getLiqPendiente());
        detalle.setLiqAnulado(prdLiquidacionAux.getLiqAnulado());
        detalle.setUsrEmpresa(prdLiquidacionAux.getUsrEmpresa());
        detalle.setUsrCodigo(prdLiquidacionAux.getUsrCodigo());
        detalle.setUsrFechaInserta(prdLiquidacionAux.getUsrFechaInserta());
        detalle.setPrdPiscina(prdLiquidacionAux.getPrdPiscina());
        detalle.setInvCliente(prdLiquidacionAux.getInvCliente());
        detalle.setPrdCorrida(prdLiquidacionAux.getPrdCorrida());
        detalle.setPrdPreLiquidacion(prdLiquidacionAux.getPrdPreLiquidacion());
        detalle.setInvProveedor(prdLiquidacionAux.getInvProveedor());
        detalle.setLiqFacturado(prdLiquidacionAux.isLiqFacturado());
        detalle.setLiqGuia(prdLiquidacionAux.getLiqGuia());
        detalle.setLiqTipoReliquidacion(prdLiquidacionAux.getLiqTipoReliquidacion());
        detalle.setLiqGramaje(prdLiquidacionAux.getLiqGramaje());
        detalle.setLiqGavetas(prdLiquidacionAux.getLiqGavetas());
        detalle.setLiqPesoPromedio(prdLiquidacionAux.getLiqPesoPromedio());
        detalle.setLiqLarvilla(prdLiquidacionAux.getLiqLarvilla());
        detalle.setLiqQuebrado(prdLiquidacionAux.getLiqQuebrado());
        detalle.setLiqObservaciones(prdLiquidacionAux.getLiqObservaciones());
        detalle.setLiqComisionista(prdLiquidacionAux.getLiqComisionista());
        detalle.setLiqListaDePrecios(prdLiquidacionAux.getLiqListaDePrecios());
        detalle.setLiqBines(prdLiquidacionAux.getLiqBines());
        detalle.setLiqAguaje(prdLiquidacionAux.getLiqAguaje());
        detalle.setLiqPiscina(prdLiquidacionAux.getLiqPiscina());
        detalle.setPrdComisionista(prdLiquidacionAux.getPrdComisionista());

        return detalle;
    }

    public static PrdLiquidacionDetalle convertirPrdLiquidacionDetalle_PrdLiquidacionDetalle(PrdLiquidacionDetalle prdLiquidacionDetalleAux) {
        PrdLiquidacionDetalle prdLiquidacionDetalle = new PrdLiquidacionDetalle();
        prdLiquidacionDetalle.setDetSecuencial(prdLiquidacionDetalleAux.getDetSecuencial());
        prdLiquidacionDetalle.setDetOrden(prdLiquidacionDetalleAux.getDetOrden());
        prdLiquidacionDetalle.setDetLibras(prdLiquidacionDetalleAux.getDetLibras());
        prdLiquidacionDetalle.setDetPrecio(prdLiquidacionDetalleAux.getDetPrecio());
        prdLiquidacionDetalle.setPrdTalla(prdLiquidacionDetalleAux.getPrdTalla());
        prdLiquidacionDetalle.setPrdProducto(prdLiquidacionDetalleAux.getPrdProducto());
        prdLiquidacionDetalle.setPrdLiquidacion(prdLiquidacionDetalleAux.getPrdLiquidacion());

        return prdLiquidacionDetalle;
    }

    public static PrdReproceso convertirPrdReprocesoTO_PrdReproceso(PrdReprocesoTO prdReprocesoTO) {
        PrdReproceso prdReproceso = new PrdReproceso();
        //bodega
        InvBodega bodega = new InvBodega(new InvBodegaPK(prdReprocesoTO.getBodEmpresa(), prdReprocesoTO.getBodCodigo()));
        prdReproceso.setInvBodega(bodega);
        //pk
        prdReproceso.setPrdReprocesoPK(new PrdReprocesoPK(prdReprocesoTO.getRepEmpresa(), prdReprocesoTO.getRepPeriodo(), prdReprocesoTO.getRepMotivo(), prdReprocesoTO.getRepNumero()));
        prdReproceso.setRepAnulado(prdReprocesoTO.isRepAnulado());
        prdReproceso.setRepFecha(UtilsValidacion.fecha(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd"));
        prdReproceso.setRepObservaciones(prdReprocesoTO.getRepObservaciones());
        prdReproceso.setRepPendiente(prdReprocesoTO.isRepPendiente());
        prdReproceso.setUsrCodigo(prdReprocesoTO.getUsrCodigo());
        prdReproceso.setUsrEmpresa(prdReprocesoTO.getUsrEmpresa());
        prdReproceso.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaString_Date(prdReprocesoTO.getUsrFechaInserta())));

        return prdReproceso;
    }

    public static PrdReprocesoTO convertirPrdReproceso_PrdReprocesoTO(PrdReproceso prdReproceso) {
        PrdReprocesoTO prdReprocesoTO = new PrdReprocesoTO();
        //bodega
        if (prdReproceso.getInvBodega() != null && prdReproceso.getInvBodega().getInvBodegaPK() != null) {
            prdReprocesoTO.setBodCodigo(prdReproceso.getInvBodega().getInvBodegaPK().getBodCodigo());
            prdReprocesoTO.setBodEmpresa(prdReproceso.getInvBodega().getInvBodegaPK().getBodEmpresa());
            prdReprocesoTO.setBodNombre(prdReproceso.getInvBodega().getBodNombre());
        }

        //pk
        if (prdReproceso.getPrdReprocesoPK() != null) {
            prdReprocesoTO.setRepEmpresa(prdReproceso.getPrdReprocesoPK().getRepEmpresa());
            prdReprocesoTO.setRepPeriodo(prdReproceso.getPrdReprocesoPK().getRepPeriodo());
            prdReprocesoTO.setRepMotivo(prdReproceso.getPrdReprocesoPK().getRepMotivo());
            prdReprocesoTO.setRepNumero(prdReproceso.getPrdReprocesoPK().getRepNumero());
        }
        prdReprocesoTO.setRepAnulado(prdReproceso.isRepAnulado());
        prdReprocesoTO.setRepObservaciones(prdReproceso.getRepObservaciones());
        prdReprocesoTO.setRepPendiente(prdReproceso.isRepPendiente());
        prdReprocesoTO.setUsrCodigo(prdReproceso.getUsrCodigo());
        prdReprocesoTO.setUsrEmpresa(prdReproceso.getUsrEmpresa());
        prdReprocesoTO.setRepFecha(UtilsValidacion.fecha(prdReproceso.getRepFecha(), "yyyy-MM-dd"));
        prdReprocesoTO.setUsrFechaInserta(UtilsValidacion.fecha(prdReproceso.getUsrFechaInserta(), "yyyy-MM-dd"));

        return prdReprocesoTO;
    }

    public static PrdReprocesoIngreso convertirPrdReprocesoIngresoTO_PrdReprocesoIngreso(PrdReprocesoIngresoTO prdReprocesoIngresoTO) {
        PrdReprocesoIngreso prdReprocesoIngreso = new PrdReprocesoIngreso();
        //bodega
        InvBodega bodega = new InvBodega(new InvBodegaPK(prdReprocesoIngresoTO.getBodEmpresa(), prdReprocesoIngresoTO.getBodCodigo()));
        prdReprocesoIngreso.setInvBodega(bodega);

        prdReprocesoIngreso.setPisEmpresa(prdReprocesoIngresoTO.getPisEmpresa());
        prdReprocesoIngreso.setPisNumero(prdReprocesoIngresoTO.getPisNumero());
        prdReprocesoIngreso.setPisSector(prdReprocesoIngresoTO.getPisSector());
        //reproceso
        if (prdReprocesoIngresoTO.getRepEmpresa() != null && prdReprocesoIngresoTO.getRepNumero() != null) {
            PrdReprocesoPK pk = new PrdReprocesoPK(
                    prdReprocesoIngresoTO.getRepEmpresa(),
                    prdReprocesoIngresoTO.getRepPeriodo(),
                    prdReprocesoIngresoTO.getRepMotivo(),
                    prdReprocesoIngresoTO.getRepNumero());
            PrdReproceso prdReproceso = new PrdReproceso();
            prdReproceso.setPrdReprocesoPK(pk);
            prdReprocesoIngreso.setPrdReproceso(prdReproceso);
        }

        //pk
        prdReprocesoIngreso.setRepCantidad(prdReprocesoIngresoTO.getRepCantidad());
        prdReprocesoIngreso.setRepOrden(prdReprocesoIngresoTO.getRepOrden());
        prdReprocesoIngreso.setRepPorcentaje(prdReprocesoIngresoTO.getRepPorcentaje());
        prdReprocesoIngreso.setRepPrecio(prdReprocesoIngresoTO.getRepPrecio());
        prdReprocesoIngreso.setRepSecuencial(prdReprocesoIngresoTO.getRepSecuencial());
        prdReprocesoIngreso.setSecCodigo(prdReprocesoIngresoTO.getSecCodigo());
        prdReprocesoIngreso.setSecEmpresa(prdReprocesoIngresoTO.getSecEmpresa());
        //producto
        if (prdReprocesoIngresoTO.getProCodigoPrincipal() != null) {
            InvProducto producto = new InvProducto();
            producto.setInvProductoPK(new InvProductoPK(prdReprocesoIngresoTO.getProEmpresa(), prdReprocesoIngresoTO.getProCodigoPrincipal()));
            prdReprocesoIngreso.setInvProducto(producto);
        }
        return prdReprocesoIngreso;
    }

    public static PrdReprocesoEgreso convertirPrdReprocesoEgresoTO_PrdReprocesoEgreso(PrdReprocesoEgresoTO prdReprocesoEgresoTO) {
        PrdReprocesoEgreso prdReprocesoEgreso = new PrdReprocesoEgreso();
        //bodega
        InvBodega bodega = new InvBodega(new InvBodegaPK(prdReprocesoEgresoTO.getBodEmpresa(), prdReprocesoEgresoTO.getBodCodigo()));
        prdReprocesoEgreso.setInvBodega(bodega);

        prdReprocesoEgreso.setPisEmpresa(prdReprocesoEgresoTO.getPisEmpresa());
        prdReprocesoEgreso.setPisNumero(prdReprocesoEgresoTO.getPisNumero());
        prdReprocesoEgreso.setPisSector(prdReprocesoEgresoTO.getPisSector());
        //reproceso
        if (prdReprocesoEgresoTO.getRepEmpresa() != null && prdReprocesoEgresoTO.getRepNumero() != null) {
            PrdReprocesoPK pk = new PrdReprocesoPK(
                    prdReprocesoEgresoTO.getRepEmpresa(),
                    prdReprocesoEgresoTO.getRepPeriodo(),
                    prdReprocesoEgresoTO.getRepMotivo(),
                    prdReprocesoEgresoTO.getRepNumero());
            PrdReproceso prdReproceso = new PrdReproceso();
            prdReproceso.setPrdReprocesoPK(pk);
            prdReprocesoEgreso.setPrdReproceso(prdReproceso);
        }

        //pk
        prdReprocesoEgreso.setRepCantidad(prdReprocesoEgresoTO.getRepCantidad());
        prdReprocesoEgreso.setRepOrden(prdReprocesoEgresoTO.getRepOrden());
        prdReprocesoEgreso.setRepSecuencial(prdReprocesoEgresoTO.getRepSecuencial());
        prdReprocesoEgreso.setSecCodigo(prdReprocesoEgresoTO.getSecCodigo());
        prdReprocesoEgreso.setSecEmpresa(prdReprocesoEgresoTO.getSecEmpresa());
        //producto
        if (prdReprocesoEgresoTO.getProCodigoPrincipal() != null) {
            InvProducto producto = new InvProducto();
            producto.setInvProductoPK(new InvProductoPK(prdReprocesoEgresoTO.getProEmpresa(), prdReprocesoEgresoTO.getProCodigoPrincipal()));
            prdReprocesoEgreso.setInvProducto(producto);
        }

        return prdReprocesoEgreso;
    }

    public static PrdReprocesoIngresoTO convertirPrdReprocesoIngreso_PrdReprocesoIngresoTO(PrdReprocesoIngreso prdReprocesoIng) {
        PrdReprocesoIngresoTO prdReprocesoIngresoTO = new PrdReprocesoIngresoTO();
        //bodega
        prdReprocesoIngresoTO.setBodCodigo(prdReprocesoIng.getInvBodega().getInvBodegaPK().getBodCodigo());
        prdReprocesoIngresoTO.setBodEmpresa(prdReprocesoIng.getInvBodega().getInvBodegaPK().getBodEmpresa());
        prdReprocesoIngresoTO.setBodNombre(prdReprocesoIng.getInvBodega().getBodNombre());

        prdReprocesoIngresoTO.setPisEmpresa(prdReprocesoIng.getPisEmpresa());
        prdReprocesoIngresoTO.setPisNumero(prdReprocesoIng.getPisNumero());
        prdReprocesoIngresoTO.setPisSector(prdReprocesoIng.getPisSector());
        //reproceso
        prdReprocesoIngresoTO.setRepEmpresa(prdReprocesoIng.getPrdReproceso().getPrdReprocesoPK().getRepEmpresa());
        prdReprocesoIngresoTO.setRepPeriodo(prdReprocesoIng.getPrdReproceso().getPrdReprocesoPK().getRepPeriodo());
        prdReprocesoIngresoTO.setRepMotivo(prdReprocesoIng.getPrdReproceso().getPrdReprocesoPK().getRepMotivo());
        prdReprocesoIngresoTO.setRepNumero(prdReprocesoIng.getPrdReproceso().getPrdReprocesoPK().getRepNumero());
        //pk
        prdReprocesoIngresoTO.setRepCantidad(prdReprocesoIng.getRepCantidad());
        prdReprocesoIngresoTO.setRepOrden(prdReprocesoIng.getRepOrden());
        prdReprocesoIngresoTO.setRepPorcentaje(prdReprocesoIng.getRepPorcentaje());
        prdReprocesoIngresoTO.setRepPrecio(prdReprocesoIng.getRepPrecio());
        prdReprocesoIngresoTO.setRepSecuencial(prdReprocesoIng.getRepSecuencial());
        prdReprocesoIngresoTO.setSecCodigo(prdReprocesoIng.getSecCodigo());
        prdReprocesoIngresoTO.setSecEmpresa(prdReprocesoIng.getSecEmpresa());
        //producto
        prdReprocesoIngresoTO.setProEmpresa(prdReprocesoIng.getInvProducto() != null && prdReprocesoIng.getInvProducto().getInvProductoPK() != null ? prdReprocesoIng.getInvProducto().getInvProductoPK().getProEmpresa() : null);
        prdReprocesoIngresoTO.setProCodigoPrincipal(prdReprocesoIng.getInvProducto() != null && prdReprocesoIng.getInvProducto().getInvProductoPK() != null ? prdReprocesoIng.getInvProducto().getInvProductoPK().getProCodigoPrincipal() : null);
        prdReprocesoIngresoTO.setProCodigoPrincipalCopia(prdReprocesoIng.getInvProducto() != null && prdReprocesoIng.getInvProducto().getInvProductoPK() != null ? prdReprocesoIng.getInvProducto().getInvProductoPK().getProCodigoPrincipal() : null);
        prdReprocesoIngresoTO.setProNombre(prdReprocesoIng.getInvProducto() != null ? prdReprocesoIng.getInvProducto().getProNombre() : null);

        return prdReprocesoIngresoTO;
    }

    public static PrdReprocesoEgresoTO convertirPrdReprocesoEgreso_PrdReprocesoEgresoTO(PrdReprocesoEgreso prdReprocesoEgr) {
        PrdReprocesoEgresoTO prdReprocesoEgresoTO = new PrdReprocesoEgresoTO();
        //bodega
        prdReprocesoEgresoTO.setBodCodigo(prdReprocesoEgr.getInvBodega() != null ? prdReprocesoEgr.getInvBodega().getInvBodegaPK().getBodCodigo() : null);
        prdReprocesoEgresoTO.setBodEmpresa(prdReprocesoEgr.getInvBodega() != null ? prdReprocesoEgr.getInvBodega().getInvBodegaPK().getBodEmpresa() : null);
        prdReprocesoEgresoTO.setBodNombre(prdReprocesoEgr.getInvBodega() != null ? prdReprocesoEgr.getInvBodega().getBodNombre() : null);

        prdReprocesoEgresoTO.setPisEmpresa(prdReprocesoEgr.getPisEmpresa());
        prdReprocesoEgresoTO.setPisNumero(prdReprocesoEgr.getPisNumero());
        prdReprocesoEgresoTO.setPisSector(prdReprocesoEgr.getPisSector());
        //reproceso
        prdReprocesoEgresoTO.setRepEmpresa(prdReprocesoEgr.getPrdReproceso().getPrdReprocesoPK().getRepEmpresa());
        prdReprocesoEgresoTO.setRepPeriodo(prdReprocesoEgr.getPrdReproceso().getPrdReprocesoPK().getRepPeriodo());
        prdReprocesoEgresoTO.setRepMotivo(prdReprocesoEgr.getPrdReproceso().getPrdReprocesoPK().getRepMotivo());
        prdReprocesoEgresoTO.setRepNumero(prdReprocesoEgr.getPrdReproceso().getPrdReprocesoPK().getRepNumero());
        //pk
        prdReprocesoEgresoTO.setRepCantidad(prdReprocesoEgr.getRepCantidad());
        prdReprocesoEgresoTO.setRepOrden(prdReprocesoEgr.getRepOrden());
        prdReprocesoEgresoTO.setRepSecuencial(prdReprocesoEgr.getRepSecuencial());
        prdReprocesoEgresoTO.setSecCodigo(prdReprocesoEgr.getSecCodigo());
        prdReprocesoEgresoTO.setSecEmpresa(prdReprocesoEgr.getSecEmpresa());
        //producto
        prdReprocesoEgresoTO.setProEmpresa(prdReprocesoEgr.getInvProducto() != null && prdReprocesoEgr.getInvProducto().getInvProductoPK() != null ? prdReprocesoEgr.getInvProducto().getInvProductoPK().getProEmpresa() : null);
        prdReprocesoEgresoTO.setProCodigoPrincipal(prdReprocesoEgr.getInvProducto() != null && prdReprocesoEgr.getInvProducto().getInvProductoPK() != null ? prdReprocesoEgr.getInvProducto().getInvProductoPK().getProCodigoPrincipal() : null);
        prdReprocesoEgresoTO.setProCodigoPrincipalCopia(prdReprocesoEgr.getInvProducto() != null && prdReprocesoEgr.getInvProducto().getInvProductoPK() != null ? prdReprocesoEgr.getInvProducto().getInvProductoPK().getProCodigoPrincipal() : null);
        prdReprocesoEgresoTO.setProNombre(prdReprocesoEgr.getInvProducto() != null ? prdReprocesoEgr.getInvProducto().getProNombre() : null);
        return prdReprocesoEgresoTO;
    }
}
