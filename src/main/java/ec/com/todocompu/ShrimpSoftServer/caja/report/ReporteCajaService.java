package ec.com.todocompu.ShrimpSoftServer.caja.report;

import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;

public interface ReporteCajaService {

    public byte[] generarReporteCajCuadreDeCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String codigoMotivo, String descripcionMotivo, List<CajCuadreCajaTO> listCajCuadreCajaTOs) throws Exception;

    public byte[] generarReporteCajVales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String descripcionCajaConcepto, String codigoCajaConcepto, CajCajaValesTO cajCajaValesTo) throws Exception;

    public byte[] generarReporteCajValesListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CajCajaValesTO> listaCajValesListaTOs) throws Exception;

    public Map<String, Object> exportarReporteCajCuadreCajaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CajCuadreCajaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteCajValesListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CajCajaValesTO> listaCajValesListaTOs, 
            String fechaInicio, String fechaHasta) throws Exception;

}
