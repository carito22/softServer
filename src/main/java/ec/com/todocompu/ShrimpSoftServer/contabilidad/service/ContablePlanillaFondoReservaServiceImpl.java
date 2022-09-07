/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContablePlanillaFondoReservaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaFondoReserva;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class ContablePlanillaFondoReservaServiceImpl implements ContablePlanillaFondoReservaService {

    @Autowired
    private ContablePlanillaFondoReservaDao contablePlanillaFondoReservaDao;
    @Autowired
    private PeriodoService periodoService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private CuentasDao cuentasDao;

    @Override
    public List<ConContablePlanillaFondoReserva> getListaConContablePlanillaFondoReserva(String empresa, String sector, String periodo) throws Exception {
        return contablePlanillaFondoReservaDao.getListaConContablePlanillaFondoReserva(empresa, sector, periodo);
    }

    @Override
    public MensajeTO insertarConContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();

        ConCuentas conCuentasAux = cuentasDao.obtenerPorId(ConCuentas.class, conContablePlanillaFondoReserva.getConCuentas().getConCuentasPK());
        if (conCuentasAux != null) {
            PrdSector sectorAux = sectorService.obtenerPorEmpresaSector(sisInfoTO.getEmpresa(), conContablePlanillaFondoReserva.getPrdSector().getPrdSectorPK().getSecCodigo());
            if (sectorAux != null) {
                conContablePlanillaFondoReserva.setUsrCodigo(sisInfoTO.getUsuario());
                conContablePlanillaFondoReserva.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContablePlanillaFondoReserva.setUsrFechaInserta(UtilsDate.timestamp());
                conContablePlanillaFondoReserva.setConCuentas(conCuentasAux);
                conContablePlanillaFondoReserva.setPrdSector(sectorAux);
                susClave = sisInfoTO.getEmpresa();
                susDetalle = "Se insertó plantilla de fondo de reserva con sector:" + conContablePlanillaFondoReserva.getPrdSector().getPrdSectorPK().getSecCodigo();
                susSuceso = "INSERT";
                susTabla = "contabilidad.con_contable_planilla_fondo_reserva";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                if (contablePlanillaFondoReservaDao.insertarContablePlanillaFondoReserva(conContablePlanillaFondoReserva, sisSuceso)) {
                    mensajeTO.setMensaje("T");
                    Map<String, Object> campos = new HashMap<>();
                    campos.put("conContablePlanillaFondoReserva", conContablePlanillaFondoReserva);
                    mensajeTO.setMap(campos);
                } else {
                    mensajeTO.setMensaje("FOcurrió un erorr al insertar el pago de planilla de fondo de reserva");
                }
            } else {
                mensajeTO.setMensaje("FLa cuenta seleccionada no existe");
            }
        } else {
            mensajeTO.setMensaje("FEl sector seleccionado no existe");
        }

        return mensajeTO;
    }

    @Override
    public MensajeTO modificarConContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConContablePlanillaFondoReserva planillaAux = contablePlanillaFondoReservaDao.obtenerPorId(ConContablePlanillaFondoReserva.class, conContablePlanillaFondoReserva.getPlanillaSecuencial());
        if (planillaAux != null) {

            ConCuentas conCuentasAux = cuentasDao.obtenerPorId(ConCuentas.class, conContablePlanillaFondoReserva.getConCuentas().getConCuentasPK());
            if (conCuentasAux != null) {
                PrdSector sectorAux = sectorService.obtenerPorEmpresaSector(sisInfoTO.getEmpresa(), conContablePlanillaFondoReserva.getPrdSector().getPrdSectorPK().getSecCodigo());
                if (sectorAux != null) {
                    conContablePlanillaFondoReserva.setConCuentas(conCuentasAux);
                    conContablePlanillaFondoReserva.setPrdSector(sectorAux);
                    susClave = conContablePlanillaFondoReserva.getPlanillaSecuencial() + "";
                    susDetalle = "Se insertó plantilla de fondo de reserva con sector:" + conContablePlanillaFondoReserva.getPrdSector().getPrdSectorPK().getSecCodigo();
                    susSuceso = "UPDATE";
                    susTabla = "contabilidad.con_contable_planilla_fondo_reserva";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    if (contablePlanillaFondoReservaDao.modificarContablePlanillaFondoReserva(conContablePlanillaFondoReserva, sisSuceso)) {
                        mensajeTO.setMensaje("T");
                        Map<String, Object> campos = new HashMap<>();
                        campos.put("conContablePlanillaFondoReserva", conContablePlanillaFondoReserva);
                        mensajeTO.setMap(campos);
                    } else {
                        mensajeTO.setMensaje("FOcurrió un erorr al modificar el pago de planilla de fondo de reserva");
                    }
                } else {
                    mensajeTO.setMensaje("FLa cuenta seleccionada no existe");
                }
            } else {
                mensajeTO.setMensaje("FEl sector seleccionado no existe");
            }
        } else {
            mensajeTO.setMensaje("FEl pago de planilla de fondo de reserva no existe");
        }

        return mensajeTO;
    }

    @Override
    public MensajeTO eliminarConContablePlanillaFondoReserva(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        boolean comprobar = false;
        ConContablePlanillaFondoReserva planillaAux = contablePlanillaFondoReservaDao.obtenerPorId(ConContablePlanillaFondoReserva.class, secuencial);
        if (planillaAux != null) {
            susClave = secuencial + "";
            susDetalle = "Se eliminó pago de planilla de fondo de reserva con sector: " + planillaAux.getPrdSector().getPrdSectorPK().getSecCodigo();
            susSuceso = "DELETE";
            susTabla = "contabilidad.con_contable_planilla_fondo_reserva";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = contablePlanillaFondoReservaDao.eliminarContablePlanillaFondoReserva(planillaAux, sisSuceso);
            if (comprobar) {
                mensajeTO.setMensaje("TSe eliminó correctamente el pago de planilla de fondo de reserva: " + secuencial);
            } else {
                mensajeTO.setMensaje("FEl pago de planilla tiene problemas al eliminarse ");
            }
        } else {
            mensajeTO.setMensaje("FEl pago de planilla de fondo de reserva no existe");
        }
        return mensajeTO;
    }

    @Override
    public Map<String, Object> obtenerDatosParaPlanillaFondoReserva(String empresa, boolean mostrarTodos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa.trim());
        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(empresa.trim(), mostrarTodos);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaSectores", listBuscarSectorTO);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaPlanillaFondoReservaFormulario(String empresa, boolean mostrarTodos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa.trim());
        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(empresa.trim(), mostrarTodos);
        List<RhComboFormaPagoTO> listaFormaPago = formaPagoService.getComboFormaPagoTO(empresa);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaSectores", listBuscarSectorTO);
        campos.put("listaFormaPago", listaFormaPago);
        return campos;
    }

}
