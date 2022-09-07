package ec.com.todocompu.ShrimpSoftServer.caja.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.caja.report.ReporteCajaService;
import ec.com.todocompu.ShrimpSoftServer.caja.service.CajaService;
import ec.com.todocompu.ShrimpSoftServer.caja.service.ValesConceptosService;
import ec.com.todocompu.ShrimpSoftServer.caja.service.ValesService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptosComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;

@RestController
@RequestMapping("/todocompuWS/cajaController")
public class CajaController {

	@Autowired
	private CajaService cajaService;
	@Autowired
	private ValesConceptosService valesConceptosService;
	@Autowired
	private ValesService valesService;
	@Autowired
	private ReporteCajaService reporteCajaService;
	@Autowired
	private EnviarCorreoService envioCorreoService;

	@RequestMapping("/accionCajCajaTO")
	public MensajeTO accionCajCajaTO(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		CajCajaTO cajCajaTO = UtilsJSON.jsonToObjeto(CajCajaTO.class, map.get("cajCajaTO"));
		String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

		try {
			return cajaService.accionCajCajaTO(cajCajaTO, accion, sisInfoTO);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/getListadoCajCajaTO")
	public List<CajCajaTO> getListadoCajCajaTO(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return cajaService.getListadoCajCajaTO(empresa);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/getCajValesConceptosComboTOs")
	public List<CajValesConceptosComboTO> getCajValesConceptosComboTOs(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return valesConceptosService.getCajValesConceptosComboTOs(empresa);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/accionCajaValesTO")
	public String accionCajaValesTO(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		CajCajaValesTO cajCajaValesTO = UtilsJSON.jsonToObjeto(CajCajaValesTO.class, map.get("cajCajaValesTO"));
		String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return valesService.accionCajaValesTO(cajCajaValesTO, accion, sisInfoTO);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/accionCajValesConceptosTO")
	public String accionCajValesConceptosTO(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		CajValesConceptoTO cajValesConceptosTO = UtilsJSON.jsonToObjeto(CajValesConceptoTO.class,
				map.get("cajValesConceptosTO"));
		char accion = UtilsJSON.jsonToObjeto(Character.class, map.get("accion"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return valesConceptosService.accionCajValesConceptosTO(cajValesConceptosTO, accion, sisInfoTO);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/getListadoCajValesTO")
	public List<CajaValesTO> getListadoCajValesTO(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
		String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
		String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
		String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return valesService.getListadoCajValesTO(empresa, motCodigo, fechaDesde, fechaHasta);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/getCajCajaValesTO")
	public CajCajaValesTO getCajCajaValesTO(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
		String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
		String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
		String valeNumero = UtilsJSON.jsonToObjeto(String.class, map.get("valeNumero"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return valesService.getCajCajaValesTO(empresa, perCodigo, motCodigo, valeNumero);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/getCajCuadreCajaTOs")
	public List<CajCuadreCajaTO> getCajCuadreCajaTOs(@RequestBody String json) {

		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
		String codigoMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoMotivo"));
		String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
		String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return valesService.getCajCuadreCajaTOs(empresa, codigoMotivo, fechaDesde, fechaHasta);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/getCajCajaTO")
	public CajCajaTO getCajCajaTO(@RequestBody String json) {

		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
		String usuarioCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("usuarioCodigo"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return cajaService.getCajCajaTO(empresa, usuarioCodigo);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/generarReporteCajCuadreDeCaja")
	public byte[] generarReporteCajCuadreDeCaja(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
				map.get("usuarioEmpresaReporteTO"));
		String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
		String codigoMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoMotivo"));
		String descripcionMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("descripcionMotivo"));
		List<CajCuadreCajaTO> listCajCuadreCajaTOs = UtilsJSON.jsonToList(CajCuadreCajaTO.class,
				map.get("listCajCuadreCajaTOs"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return reporteCajaService.generarReporteCajCuadreDeCaja(usuarioEmpresaReporteTO, fechaHasta, codigoMotivo,
					descripcionMotivo, listCajCuadreCajaTOs);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/generarReporteCajVales")
	public byte[] generarReporteCajVales(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
				map.get("usuarioEmpresaReporteTO"));
		String descripcionCajaConcepto = UtilsJSON.jsonToObjeto(String.class, map.get("descripcionCajaConcepto"));
		String codigoCajaConcepto = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCajaConcepto"));
		CajCajaValesTO cajCajaValesTo = UtilsJSON.jsonToObjeto(CajCajaValesTO.class, map.get("cajCajaValesTo"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return reporteCajaService.generarReporteCajVales(usuarioEmpresaReporteTO, descripcionCajaConcepto,
					codigoCajaConcepto, cajCajaValesTo);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}

	@RequestMapping("/generarReporteCajValesListado")
	public byte[] generarReporteCajValesListado(@RequestBody String json) {
		Map<String, Object> map = UtilsJSON.jsonToMap(json);
		UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
				map.get("usuarioEmpresaReporteTO"));
		List<CajCajaValesTO> listaCajValesListaTOs = UtilsJSON.jsonToList(CajCajaValesTO.class,
				map.get("listaCajValesListaTOs"));
		SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
		try {
			return reporteCajaService.generarReporteCajValesListado(usuarioEmpresaReporteTO, listaCajValesListaTOs);
		} catch (Exception e) {
			envioCorreoService.enviarError("SERVER",
					UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
		}
		return null;
	}
}