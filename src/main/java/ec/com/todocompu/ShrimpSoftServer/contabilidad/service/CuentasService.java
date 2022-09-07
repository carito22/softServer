package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface CuentasService {

    public String insertarConCuenta(ConCuentasTO conCuentasTO, SisInfoTO sisInfoTO) throws Exception;

    public Boolean insertarListaConCuentaTO(List<ConCuentasTO> listaConCuentasTO, String empresa, SisInfoTO sisInfoTO) throws Exception;

    public String modificarConCuenta(ConCuentasTO conCuentasTO, String codigoCambiarLlave, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarEstadoConCuenta(ConCuentasTO conCuentasTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;
    
    public MensajeTO modificarEstadoBloqueoConCuenta(ConCuentasTO conCuentasTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarConCuenta(ConCuentasTO conCuentasTO, SisInfoTO sisInfoTO) throws Exception;

    public List<ConCuentasTO> getListaConCuentasTO(String empresa) throws Exception;

    public List<ConCuentasTO> getRangoCuentasTO(String empresa, String codigoCuentaDesde, String codigoCuentaHasta,
            int largoCuenta, String usuario, SisInfoTO sisInfoTO) throws Exception;

    public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(String empresa) throws Exception;

    public List<ConCuentasTO> getListaBuscarConCuentasTO(String empresa, String buscar, String ctaGrupo) throws Exception;

    public boolean validarCuentaGrupoDetalle(ConCuentasTO conCuentasTO, SisInfoTO usuario) throws Exception;

    public ConCuentas obtenerConCuenta(String empresa, String cuenta) throws Exception;

    public ConCuentasTO obtenerConCuentaTO(String empresa, String cuenta) throws Exception;

    public Map<String, Object> buscarNombresDesdeHastaPadre(String empresa, String codigoDesde, String codigoHasta, SisInfoTO usuario) throws Exception;

    public String buscarDetalleContablePadre(String empresa, String codigo) throws Exception;

    public int getConteoCuentasTO(String empresa) throws Exception;

    public String eliminarTodoConCuentas(String empresa) throws Exception;

    public List<ConCuentasTO> getListaBuscarConCuentasTO(String empresa, String buscar, String ctaGrupo, boolean cuentaActivo) throws Exception;

    public List<ConCuentasTO> obtenerConCuentasParaSinFormatear(String empresa) throws Exception;

    public Map<String, Object> obtenerDatosParaPlanContable(String empresa) throws Exception;

    public Map<String, Object> obtenerConCuentaProductoCategoria(String empresa, String codigoCategoria) throws Exception;
}
