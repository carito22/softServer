package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhHorasExtrasErrorTO;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.HorasExtrasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtras;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HorasExtrasServiceImpl implements HorasExtrasService {
    
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private HorasExtrasDao horasExtrasDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private HorasExtrasConceptoService horasExtrasConceptoService;
    @Autowired
    private HorasExtrasMotivoService horasExtrasMotivoService;
    @Autowired
    private EmpleadoService empleadoService;
    private Boolean comprobar = false;
    
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    
    @Override
    public List<RhListaDetalleHorasExtrasLoteTO> getRhDetalleHorasExtrasLoteTO(String empresa, String periodo, String tipo, String numero) throws Exception {
        return horasExtrasDao.getRhDetalleHorasExtrasLoteTO(empresa, periodo, tipo, numero);
    }
    
    @Override
    public List<RhListaSaldoConsolidadoHorasExtrasTO> getRhSaldoConsolidadoHorasExtrasTO(String empCodigo, String fechaHasta) throws Exception {
        return horasExtrasDao.getRhSaldoConsolidadoHorasExtrasTO(empCodigo, fechaHasta);
    }
    
    @Override
    public List<RhListaDetalleHorasExtrasTO> getRhListaDetalleHorasExtrasFiltradoTO(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId, String parametro) throws Exception {
        return horasExtrasDao.getRhListaDetalleHorasExtrasFiltradoTO(empCodigo, fechaDesde, fechaHasta, empCategoria, empId, parametro);
    }
    
    @Override
    public List<RhListaConsolidadoHorasExtrasTO> getRhListadoConsolidadoHorasExtrasTO(String empCodigo, String fechaDesde, String fechaHasta) throws Exception {
        return horasExtrasDao.getRhListadoConsolidadoHorasExtrasTO(empCodigo, fechaDesde, fechaHasta);
    }
    
    private String validarDetalle(List<RhHorasExtras> listaHorasExtras) {
        BigDecimal cero = new BigDecimal("0.00");
        for (RhHorasExtras horasExtras : listaHorasExtras) {
            if (horasExtras.getHeValor50().compareTo(cero) != 0 || horasExtras.getHeValor100().compareTo(cero) != 0 || horasExtras.getHeValorExtraordinaria100().compareTo(cero) != 0) {
                RhEmpleado empleado = empleadoDao.obtenerPorId(RhEmpleado.class, horasExtras.getRhEmpleado().getRhEmpleadoPK());
                if (horasExtras.getHeValor50().compareTo(cero) != 0 && (empleado.getRhCategoria().getCtaGastoHorasExtras() == null || empleado.getRhCategoria().getCtaGastoHorasExtras().isEmpty())) {
                    return "FNo se puede ingresar el Horas Extras para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'GASTO HORAS EXTRAS' ";
                }
                if (horasExtras.getHeValor100().compareTo(cero) != 0 && (empleado.getRhCategoria().getCtaGastoHorasExtras100() == null || empleado.getRhCategoria().getCtaGastoHorasExtras100().isEmpty())) {
                    return "FNo se puede ingresar el Horas Extras para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'GASTO HORAS EXTRAS 100%' ";
                }
                if (horasExtras.getHeValorExtraordinaria100().compareTo(cero) != 0 && (empleado.getRhCategoria().getCtaGastoHorasExtrasExtraordinarias100() == null || empleado.getRhCategoria().getCtaGastoHorasExtrasExtraordinarias100().isEmpty())) {
                    return "FNo se puede ingresar el Horas Extras para el empleado " + empleado.getEmpApellidos() + " "
                            + empleado.getEmpNombres() + "; falta la cuenta 'GASTO HORAS EXTRAS EXTRAORDINARIAS 100%' ";
                }
            }
        }
        return null;
    }
    
    public boolean horasSuplementariasValidos(BigDecimal horas50, BigDecimal horas100, BigDecimal horasSaldo50, BigDecimal horasSaldo100, BigDecimal sueldo) {
        horas50 = horas50 != null && horas50.compareTo(BigDecimal.ZERO) > 0 ? horas50 : BigDecimal.ZERO;
        horas100 = horas100 != null && horas100.compareTo(BigDecimal.ZERO) > 0 ? horas100 : BigDecimal.ZERO;
        horasSaldo50 = horasSaldo50 != null && horasSaldo50.compareTo(BigDecimal.ZERO) > 0 ? horasSaldo50 : BigDecimal.ZERO;
        horasSaldo100 = horasSaldo100 != null && horasSaldo100.compareTo(BigDecimal.ZERO) > 0 ? horasSaldo100 : BigDecimal.ZERO;
        sueldo = sueldo != null && sueldo.compareTo(BigDecimal.ZERO) > 0 ? sueldo : BigDecimal.ZERO;
        
        BigDecimal horas = pasarValorMonetarioAHoraExtra50(sueldo, horas50).add(pasarValorMonetarioAHoraExtra100(sueldo, horas100)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal horasSaldos = pasarValorMonetarioAHoraExtra50(sueldo, horasSaldo50).add(pasarValorMonetarioAHoraExtra100(sueldo, horasSaldo100)).setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal sumaHoras = horas.add(horasSaldos);
        System.out.println("  horas50=====================>" + horas50);
        System.out.println("  horas100=====================>" + horas100);
        System.out.println("  horasSaldo50=====================>" + horasSaldo50);
        System.out.println("  horasSaldo100=====================>" + horasSaldo100);
        System.out.println("HORAS  SUMATORIA=====================>" + sumaHoras);
        boolean resp = sumaHoras.compareTo(new BigDecimal("48.00")) == 0 || sumaHoras.compareTo(new BigDecimal("48.00")) < 0;
        return resp;
    }
    
    public boolean horasExtraordinariasValidos(BigDecimal horasExtraordinarias100, BigDecimal horasExtraordinariasSaldo100, BigDecimal sueldo) {
        BigDecimal horas = pasarValorMonetarioAHoraExtra100(sueldo, horasExtraordinarias100).setScale(2, RoundingMode.HALF_UP);
        BigDecimal saldoHoras = pasarValorMonetarioAHoraExtra100(sueldo, horasExtraordinariasSaldo100).setScale(2, RoundingMode.HALF_UP);
        
        System.out.println("  horasExtraordinarias100=====================>" + horasExtraordinarias100);
        System.out.println("  horasExtraordinariasSaldo100=====================>" + horasExtraordinariasSaldo100);
        System.out.println("  sueldo=====================>" + sueldo);
        BigDecimal sumaHoras = horas.add(saldoHoras);
        System.out.println("HORAS  SUMATORIA EXTRAORDINARIA=====================>" + sumaHoras);
        boolean resp = sumaHoras.compareTo(new BigDecimal("432.00")) == 0 || sumaHoras.compareTo(new BigDecimal("432.00")) < 0;
        return resp;
    }
    
    public BigDecimal pasarValorMonetarioAHoraExtra50(BigDecimal sueldo, BigDecimal valorMonetario) {
        if (sueldo != null && sueldo.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal horas = (valorMonetario.multiply(new BigDecimal("240.00"))).divide(sueldo.multiply(new BigDecimal("1.5")), 2, RoundingMode.HALF_UP);
            if (horas.compareTo(BigDecimal.ZERO) > 0) {
                return horas;
            }
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }
    
    public BigDecimal pasarValorMonetarioAHoraExtra100(BigDecimal sueldo, BigDecimal valorMonetario) {
        if (sueldo != null && sueldo.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal horas = (valorMonetario.multiply(new BigDecimal("120.00"))).divide(sueldo, 2, RoundingMode.HALF_UP);
            if (horas.compareTo(BigDecimal.ZERO) > 0) {
                return horas;
            }
            return BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public MensajeTO insertarRhHorasExtras(Date fechaHasta, String empresa, List<RhHorasExtras> listaRhHorasExtras, String observacionesContable, String codigoUnico, SisInfoTO sisInfoTO) throws Exception {
        ConContable contable;
        MensajeTO mensaje = new MensajeTO();
        contable = new ConContable(empresa, "", "C-HE", "");
        contable.setConObservaciones(observacionesContable);
        contable.setConFecha(fechaHasta);
        contable.setConPendiente(false);
        contable.setConCodigo(codigoUnico);
        //validar
        List<RhListaEmpleadoLoteTO> listRhListaEmpleadoLoteTO = empleadoService.getListaEmpleadoLote(empresa, null, null, UtilsDate.fechaFormatoString(fechaHasta, "yyyy-MM-dd"), null, false);
        List<RhHorasExtrasErrorTO> listaMensajeErrores = new ArrayList<>();
        for (RhHorasExtras item : listaRhHorasExtras) {
            List<RhListaEmpleadoLoteTO> filtrados = null;
            filtrados = listRhListaEmpleadoLoteTO.stream()
                    .filter(item2 -> item2.getPrId().equals(item.getRhEmpleado().getRhEmpleadoPK().getEmpId()))
                    .collect(Collectors.toList());
            
            if (filtrados != null && filtrados.size() > 0) {
                RhListaEmpleadoLoteTO filtrado = filtrados.get(0);
                boolean validoHoras = horasSuplementariasValidos(
                        item.getHeValor50(),
                        item.getHeValor100(),
                        filtrado.getPrSaldoHorasExtras50(),
                        filtrado.getPrSaldoHorasExtras100(),
                        filtrado.getPrSueldo()
                );
                
                boolean validoHorasExtraordinarias = horasExtraordinariasValidos(
                        item.getHeValorExtraordinaria100(),
                        filtrado.getPrSaldoHorasExtrasExtraordinarias100(),
                        filtrado.getPrSueldo());
                
                if (!validoHoras || !validoHorasExtraordinarias) {
                    //Existe error
                    RhHorasExtrasErrorTO det = new RhHorasExtrasErrorTO();
                    det.setDatosEmpleado(filtrado);
                    det.setEmpId(item.getRhEmpleado().getRhEmpleadoPK().getEmpId());
                    det.setMensaje("FEl empleado con identificación: " + item.getRhEmpleado().getRhEmpleadoPK().getEmpId()
                            + (!validoHoras ? " tiene horas extras superiores a 48 horas" : "")
                            + (!validoHorasExtraordinarias ? (!validoHoras ? " y" : "") + " tiene horas extras extraordinarias superiores a 432" : ""));
                    listaMensajeErrores.add(det);
                }
            }
            
        }
        
        if (listaMensajeErrores.isEmpty()) {
            //insertar
            mensaje = insertarModificarRhHorasExtras(contable, listaRhHorasExtras, sisInfoTO);
            if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
                contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            }
        } else {
            mensaje.setMensaje("F");
            mensaje.getMap().put("listaErrores", listaMensajeErrores);
        }
        
        return mensaje;
    }
    
    @Override
    public MensajeTO insertarModificarRhHorasExtras(ConContable conContable, List<RhHorasExtras> listaRhHorasExtras, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        
        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else if ((retorno = validarDetalle(listaRhHorasExtras)) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                if (listaRhHorasExtras.size() == 1) {
                    conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                            listaRhHorasExtras.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                            listaRhHorasExtras.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                } else {
                    conContable.setConConcepto("HORAS EXTRAS POR LOTES");
                }
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_horas_extras");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }
            
            susSuceso = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "recursoshumanos.rh_horas_extras";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó" : "modificó";
            comprobar = horasExtrasDao.insertarModificarRhHorasExtras(conContable, listaRhHorasExtras, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar horas extras.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " horas extras con la siguiente información:<br><br>"
                        + "Período: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Motivo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Número: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("conContable", conContable);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }
    
    @Override
    public List<RhHorasExtras> getListRhHorasExtras(ConContablePK conContablePK) throws Exception {
        return horasExtrasDao.getListRhHorasExtras(conContablePK);
    }
    
    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return horasExtrasDao.getPermisoAcciones(conContablePK, fechaContable);
    }
    
    @Override
    public Map<String, Object> obtenerDatosParaCrudHorasExtras(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<RhComboCategoriaTO> listadoCategorias = categoriaService.getComboRhCategoriaTO(empresa);
        List<RhHorasExtrasConcepto> listaConceptos = horasExtrasConceptoService.getListaHorasExtrasConceptos(empresa, false);
        List<RhHorasExtrasMotivo> listaMotivos = horasExtrasMotivoService.getListaRhHorasExtrasMotivos(empresa, false);
        campos.put("listaSectores", listaSectores);
        campos.put("listaCategorias", listadoCategorias);
        campos.put("listaConceptos", listaConceptos);
        campos.put("listaMotivos", listaMotivos);
        return campos;
    }
    
}
