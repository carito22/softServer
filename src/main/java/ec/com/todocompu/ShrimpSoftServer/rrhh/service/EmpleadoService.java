package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnulacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAvisoEntradaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCancelarBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaIessTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

public interface EmpleadoService {

    @Transactional
    public String insertarRhEmpleado(RhEmpleadoTO rhEmpleadoTO,
            List<RhEmpleadoDescuentosFijosTO> ListarhEmpleadoDescuentosFijosTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarRhEmpleado(RhEmpleadoTO rhEmpleadoTO, List<RhEmpleadoDescuentosFijosTO> listaModificar,
            List<RhEmpleadoDescuentosFijosTO> listaEliminar, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarRhEmpleado(RhEmpleadoTO rhEmpleadoTO, List<RhEmpleadoDescuentosFijosTO> listaEliminar,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<RhEmpleadoTO> getListaRhEmpleadoTO(String empresa, String buscar, boolean estado) throws Exception;

    @Transactional
    public List<RhListaEmpleadoTO> getListaConsultaEmpleadoTO(String empresa, String buscar, Boolean interno,
            boolean estado) throws Exception;

    @Transactional
    public List<RhFunListadoEmpleadosTO> getRhFunListadoEmpleadosTO(String empresa, String provincia, String canton,
            String sector, String categoria, boolean estado) throws Exception;

    @Transactional
    public String accionRhAvisosEntrada(RhAvisoEntradaTO rhAvisoEntradaTO, char accion, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public boolean getRhEmpleadoRetencion(String empCodigo, String empId) throws Exception;

    @Transactional
    public RhCtaIessTO buscarCtaRhIess(String empCodigo, String empId) throws Exception;

    @Transactional
    public RhCancelarBeneficioSocialTO getRhCancelarBeneficioSocialTO(String empCodigo, String empId) throws Exception;

    @Transactional
    public BigDecimal getRhValorImpuestoRenta(String empCodigo, String empId, String fechaHasta, Integer diasLaborados,
            BigDecimal rolIngresos, BigDecimal rolExtras, BigDecimal rolIngresosExento) throws Exception;

    @Transactional
    public List<RhListaProvisionesTO> getRhListaProvisionesTO(String empresa, String periodo, String sector,
            String status) throws Exception;

    @Transactional
    public List<RhListaProvisionesTO> getRhListaProvisionesComprobanteContableTO(String empresa, String periodo,
            String tipo, String numero) throws Exception;

    @Transactional
    public BigDecimal getValorRecalculadoIR(BigDecimal valor, String desde, String hasta) throws Exception;

    @Transactional
    public boolean getSwInactivaEmpleado(String empresa, String empleado) throws Exception;

    public String getInactivaEmpleado(String empresa, String empleado) throws Exception;

    @Transactional
    public List<RhEmpleadoDescuentosFijosTO> getRhEmpleadoDescuentosFijosTO(String empresa, String empresaID)
            throws Exception;

    @Transactional
    public String guardarImagenEmpleado(byte[] imagen, String nombre) throws Exception;

    @Transactional
    public String eliminarImagenEmpleado(String nombre) throws Exception;

    @Transactional
    public byte[] obtenerImagenEmpleado(String nombre) throws Exception;

    @Transactional
    public String obtenerRutaImagenEmpleado(String nombre) throws Exception;

    @Transactional
    public List<RhAnulacionesTO> getRhAnulacionesTO(String empresa, String periodo, String tipo, String numero)
            throws Exception;

    public RetornoTO getConsolidadoIngresosTabulado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String usuario) throws Exception;

    public RetornoTO getProvisionPorFechas(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            String agrupacion) throws Exception;

    @Transactional
    public List<RhEmpleado> getListaEmpleado(String empresa, String buscar, boolean estado) throws Exception;

    @Transactional
    public RhEmpleado getEmpleado(String empCodigo, String empId) throws Exception;

    @Transactional
    public MensajeTO insertarModificarRhEmpleado(RhEmpleado rhEmpleado,
            List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarRhEmpleado(RhEmpleadoPK rhEmpleadoPK, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO cambiarEstadoRhEmpleado(RhEmpleadoPK pk, boolean activar, SisInfoTO sisInfoTO) throws Exception;

    public List<RhListaEmpleadoLoteTO> getListaEmpleadoLote(String empresa, String categoria, String sector,
            String fechaHasta, String motivo, boolean rol);

    public Map<String, Object> obtenerDatosParaBusquedaEmpleados(Map<String, Object> map) throws Exception;

    //Nuevos
    @Transactional
    public List<RhListaBonosLoteTO> getListaRhBonos(String empresa, String categoria, String sector, String fechaHasta, String motivo, boolean rol, List<PrdListaPiscinaTO> listadoPiscinas) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudEmpleados(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerComplementosEmpleado(Map<String, Object> map) throws Exception;

    public void guardarImagenEmpleado(byte[] decodedString, RhEmpleado rhEmpleado) throws Exception;

    public String cambiarCedulaEmpleado(String empresa, String cedulaInconrrecta, String cedulaCorrecta) throws Exception;

    //reconstruccion de saldos empleados
    public boolean getReconstruccionSaldosEmpleados(String empresa) throws Exception;

    public List<RhProvisionDetalladoTO> listarProvisionesPorEmpleado(String empresa, String codigoSector, String fechaInicio, String fechaFin, boolean mostrarSaldos, String trabajador) throws Exception;

    @Transactional
    public Map<String, Object> verificarExistenciaEmpleados(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> insertarModificarRhEmpleadoLote(Map<String, Object> map) throws Exception;
}
