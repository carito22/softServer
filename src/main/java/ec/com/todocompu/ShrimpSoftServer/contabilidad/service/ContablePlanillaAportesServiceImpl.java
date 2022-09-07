/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContablePlanillaAportesDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.service.FormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaAportes;
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
public class ContablePlanillaAportesServiceImpl implements ContablePlanillaAportesService {

    @Autowired
    private PeriodoService periodoService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private ContablePlanillaAportesDao contablePlanillaAportesDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private FormaPagoService formaPagoService;

    @Override
    public Map<String, Object> obtenerDatosParaPlanillaAportes(String empresa, boolean mostrarTodos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa.trim());
        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(empresa.trim(), mostrarTodos);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaSectores", listBuscarSectorTO);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaPlanillaAportesFormulario(String empresa, boolean mostrarTodos) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa.trim());
        List<PrdListaSectorTO> listBuscarSectorTO = sectorService.getListaSectorTO(empresa.trim(), mostrarTodos);
        List<RhComboFormaPagoTO> listaFormaPago = formaPagoService.getComboFormaPagoTO(empresa);
        campos.put("listaPeriodos", listaPeriodos);
        campos.put("listaSectores", listBuscarSectorTO);
        campos.put("listaFormaPago", listaFormaPago);
        return campos;
    }

    @Override
    public List<ConContablePlanillaAportes> getListaConContablePlanillaAportes(String empresa, String sector, String periodo) throws Exception {
        return contablePlanillaAportesDao.getListaConContablePlanillaAportes(empresa, sector, periodo);
    }

    @Override
    public MensajeTO insertarConContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();

        ConCuentas conCuentasAux = cuentasDao.obtenerPorId(ConCuentas.class, conContablePlanillaAportes.getConCuentas().getConCuentasPK());
        if (conCuentasAux != null) {
            PrdSector sectorAux = sectorService.obtenerPorEmpresaSector(sisInfoTO.getEmpresa(), conContablePlanillaAportes.getPrdSector().getPrdSectorPK().getSecCodigo());
            if (sectorAux != null) {
                conContablePlanillaAportes.setUsrCodigo(sisInfoTO.getUsuario());
                conContablePlanillaAportes.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContablePlanillaAportes.setUsrFechaInserta(UtilsDate.timestamp());
                conContablePlanillaAportes.setConCuentas(conCuentasAux);
                conContablePlanillaAportes.setPrdSector(sectorAux);
                susClave = sisInfoTO.getEmpresa();
                susDetalle = "Se insertó plantilla de aportes con sector:" + conContablePlanillaAportes.getPrdSector().getPrdSectorPK().getSecCodigo();
                susSuceso = "INSERT";
                susTabla = "contabilidad.con_contable_planilla_aportes";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                if (contablePlanillaAportesDao.insertarContablePlanillaAportes(conContablePlanillaAportes, sisSuceso)) {
                    mensajeTO.setMensaje("T");
                    Map<String, Object> campos = new HashMap<>();
                    campos.put("conContablePlanillaAportes", conContablePlanillaAportes);
                    mensajeTO.setMap(campos);
                } else {
                    mensajeTO.setMensaje("FOcurrió un erorr al insertar el pago de planilla de aportes");
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
    public MensajeTO modificarConContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        ConContablePlanillaAportes planillaAux = contablePlanillaAportesDao.obtenerPorId(ConContablePlanillaAportes.class, conContablePlanillaAportes.getPlanillaSecuencial());
        if (planillaAux != null) {

            ConCuentas conCuentasAux = cuentasDao.obtenerPorId(ConCuentas.class, conContablePlanillaAportes.getConCuentas().getConCuentasPK());
            if (conCuentasAux != null) {
                PrdSector sectorAux = sectorService.obtenerPorEmpresaSector(sisInfoTO.getEmpresa(), conContablePlanillaAportes.getPrdSector().getPrdSectorPK().getSecCodigo());
                if (sectorAux != null) {
                    conContablePlanillaAportes.setConCuentas(conCuentasAux);
                    conContablePlanillaAportes.setPrdSector(sectorAux);
                    susClave = conContablePlanillaAportes.getPlanillaSecuencial() + "";
                    susDetalle = "Se insertó plantilla de aportes con sector:" + conContablePlanillaAportes.getPrdSector().getPrdSectorPK().getSecCodigo();
                    susSuceso = "UPDATE";
                    susTabla = "contabilidad.con_contable_planilla_aportes";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    if (contablePlanillaAportesDao.modificarContablePlanillaAportes(conContablePlanillaAportes, sisSuceso)) {
                        mensajeTO.setMensaje("T");
                        Map<String, Object> campos = new HashMap<>();
                        campos.put("conContablePlanillaAportes", conContablePlanillaAportes);
                        mensajeTO.setMap(campos);
                    } else {
                        mensajeTO.setMensaje("FOcurrió un erorr al modificar el pago de planilla de aportes");
                    }
                } else {
                    mensajeTO.setMensaje("FLa cuenta seleccionada no existe");
                }
            } else {
                mensajeTO.setMensaje("FEl sector seleccionado no existe");
            }
        } else {
            mensajeTO.setMensaje("FEl pago de planilla de aportes no existe");
        }

        return mensajeTO;
    }

    @Override
    public MensajeTO eliminarConContablePlanillaAportes(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        boolean comprobar = false;
        ConContablePlanillaAportes planillaAux = contablePlanillaAportesDao.obtenerPorId(ConContablePlanillaAportes.class, secuencial);
        if (planillaAux != null) {
            susClave = secuencial + "";
            susDetalle = "Se eliminó pago de planilla de aportes con sector: " + planillaAux.getPrdSector().getPrdSectorPK().getSecCodigo();
            susSuceso = "DELETE";
            susTabla = "contabilidad.con_contable_planilla_aportes";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = contablePlanillaAportesDao.eliminarContablePlanillaAportes(planillaAux, sisSuceso);
            if (comprobar) {
                mensajeTO.setMensaje("TSe eliminó correctamente el pago de planilla de aportes: " + secuencial);
            } else {
                mensajeTO.setMensaje("FEl pago de planilla tiene problemas al eliminarse ");
            }
        } else {
            mensajeTO.setMensaje("FEl pago de planilla de aportes no existe");
        }
        return mensajeTO;
    }

}
