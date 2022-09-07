package ec.com.todocompu.ShrimpSoftServer.caja.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

public interface ValesService {

    @Transactional
    public String accionCajaValesTO(CajCajaValesTO cajCajaValesTO, String accion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO accionCajaVales(CajCajaValesTO cajCajaValesTO, String accion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<CajaValesTO> getListadoCajValesTO(String empresa, String motCodigo, String fechaDesde,
            String fechaHasta) throws Exception;

    @Transactional
    public CajCajaValesTO getCajCajaValesTO(String empresa, String perCodigo, String motCodigo, String valeNumero)
            throws Exception;

    @Transactional
    public List<CajCuadreCajaTO> getCajCuadreCajaTOs(String empresa, String codigoMotivo, String fechaDesde,
            String fechaHasta) throws Exception;

    public Map<String, Object> obtenerDatosParaValeCaja(Map<String, Object> map) throws Exception;
    
    public Map<String, Object> obtenerDatosBasicosParaValeCaja(Map<String, Object> map) throws Exception;

}
