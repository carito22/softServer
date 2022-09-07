package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnulacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCancelarBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhConsolidadoIngresosTabuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaIessTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionFechasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface EmpleadoDao extends GenericDao<RhEmpleado, RhEmpleadoPK> {

    Boolean accionRhAvisoEntrada(RhEmpleado rhEmpleado, SisSuceso sisSuceso, char accion) throws Exception;

    RhCtaIessTO buscarCtaRhIess(String empCodigo, String empId) throws Exception;

    List<RhEmpleadoTO> getListaEmpleadoTO(String empresa, String buscar, boolean estado) throws Exception;

    List<RhListaEmpleadoTO> getListaConsultaEmpleado(String empresa, String buscar, Boolean interno, boolean estado)
            throws Exception;

    RhEmpleadoRolTO getEmpleadoRolTO(String empresa, String empId) throws Exception;

    boolean getRhEmpleadoRetencion(String empresa, String empId) throws Exception;

    String buscarCtaRhAnticipo(String empCodigo, String empId) throws Exception;

    String buscarCtaRhPrestamo(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoViaticos(String empCodigo, String empId) throws Exception;

    String buscarCtaRhPorPagarViaticos(String empCodigo, String empId) throws Exception;

    String buscarCtaRhPorPagarImpuestoRenta(String empCodigo, String empId) throws Exception;

    String buscarCtaRhPorPagarUtilidades(String empCodigo, String empId) throws Exception;

    String buscarCtaRhPorPagarBonos(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoBonoFijo(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoBonoFijoND(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoOtrosIngresosND(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoOtrosIngresos(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoBonosND(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoBonos(String empCodigo, String empId) throws Exception;

    String buscarCategoriaTipo(String empCodigo, String empId, String catNombre) throws Exception;

    String buscarCtaRhPorPagarSueldos(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoSueldos(String empCodigo, String empId) throws Exception;

    public String buscarCtaRhSalarioNeto(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoXiii(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoXiv(String empCodigo, String empId) throws Exception;

    String buscarCtaRhFondoReserva(String empCodigo, String empId) throws Exception;

    String buscarCtaRhLiquidacionBonificacion(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoFondoReserva(String empCodigo, String empId) throws Exception;

    String buscarCtaRhXiii(String empCodigo, String empId) throws Exception;

    String buscarCtaRhXiv(String empCodigo, String empId) throws Exception;

    String buscarCtaRhVacaciones(String empCodigo, String empId) throws Exception;

    String buscarCtaRhSalarioDigno(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoSalarioDigno(String empCodigo, String empId) throws Exception;

    String buscarCtaRhDesahucio(String empCodigo, String empId) throws Exception;

    String buscarCtaRhGastoDesahucioIntempestivo(String empCodigo, String empId) throws Exception;

    RhCancelarBeneficioSocialTO getRhCancelarBeneficioSocialTO(String empresa, String empleadoId) throws Exception;

    List<RhEmpleadoDescuentosFijosTO> getRhEmpleadoDescuentosFijosTO(String empresa, String empresaID) throws Exception;

    public boolean repetidoRhEmpleado(String empresa, String id, String apellidos, String nombres) throws Exception;

    public List<RhFunListadoEmpleadosTO> getRhFunListadoEmpleadosTO(String empresa, String provincia, String canton,
            String sector, String categoria, boolean estado) throws Exception;

    public boolean getSwInactivaEmpleado(String empresa, String empleado) throws Exception;

    public String getInactivaEmpleado(String empresa, String empleado) throws Exception;

    public BigDecimal getRhValorImpuestoRenta(String empCodigo, String empId, String fechaHasta, Integer diasLaborados,
            java.math.BigDecimal rolIngresos, java.math.BigDecimal rolExtras, java.math.BigDecimal rolIngresosExento) throws Exception;

    public List<RhAnulacionesTO> getRhAnulacionesTO(String empresa, String periodo, String tipo, String numero)
            throws Exception;

    public List<RhListaProvisionesTO> getRhListaProvisionesTO(String empresa, String periodo, String sector,
            String status) throws Exception;

    public List<RhListaProvisionesTO> getRhListaProvisionesComprobanteContableTO(String empresa, String periodo,
            String tipo, String numero) throws Exception;

    public BigDecimal getValorRecalculadoIR(java.math.BigDecimal valor, String desde, String hasta) throws Exception;

    public List<RhConsolidadoIngresosTabuladoTO> getConsolidadoIngresosTabulado(String empresa, String codigoSector,
            String fechaInicio, String fechaFin) throws Exception;

    @Transactional
    public List<RhProvisionFechasTO> getProvisionPorFechas(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String agrupacion) throws Exception;

    public List<RhEmpleado> getListaEmpleado(String empresa, String buscar, boolean estado) throws Exception;

    public RhEmpleado buscarEmpleado(String empCodigo, String empId) throws Exception;

    public boolean insertarModificarRhEmpleado(RhEmpleado rhEmpleado,
            List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos, SisSuceso sisSuceso) throws Exception;

    public boolean insertarRhEmpleado(RhEmpleado rhEmpleado,
            List<RhEmpleadoDescuentosFijos> ListRhEmpleadoDescuentosFijos, SisSuceso sisSuceso) throws Exception;

    public boolean modificarRhEmpleado(RhEmpleado rhEmpleado, List<RhEmpleadoDescuentosFijos> listaModificar,
            List<RhEmpleadoDescuentosFijos> listaEliminar, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarRhEmpleado(RhEmpleado rhEmpleado, List<RhEmpleadoDescuentosFijos> lista, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarRhEmpleado(String empCodigo, String empId) throws Exception;

    public String getRhEmpleadoApellidosNombres(String empresa, String id) throws Exception;

    public List<RhListaEmpleadoLoteTO> getListaEmpleadoLote(String empresa, String categoria, String sector,
            String fechaHasta, String motivo, boolean rol);

    public RhListaEmpleadoLoteTO existeEmpleadoEnPlantilla(RhRol rol) throws Exception;

    public boolean cambiarCedulaEmpleado(String empresa, String cedulaInconrrecta, String cedulaCorrecta) throws Exception;

    public RhEmpleadoTO getEmpleadoTO(String empresa, String empleadoId) throws Exception;

    //getEstado saldos empleados(Reconstruccion)
    public boolean getReconstruccionSaldosEmpleados(String empresa) throws Exception;

    public List<RhProvisionDetalladoTO> listarProvisionesPorEmpleado(String empresa, String codigoSector, String fechaInicio, String fechaFin, boolean mostrarSaldos, String trabajador) throws Exception;
}
