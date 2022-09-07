package ec.com.todocompu.ShrimpSoftServer.banco.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaPostfechadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChequePostfechadoService {

    public List<CarCobrosDetalleFormaPostfechadoTO> getListaChequesPostfechados(String empresa, String desde, String hasta) throws Exception;

    public Map<String, Object> obtenerDatosParaChequesPostfechados(Map<String, Object> map) throws Exception;

    public MensajeTO insertarContableDeposito(String observacionesContable, List<CarCobrosDetalleFormaPostfechadoTO> listaPosfechados, String fecha, String cuenta, SisInfoTO sisInfoTO) throws Exception;

    public boolean actualizarFechaVencimiento(Integer secuencial, Date fechaVencimiento, String banco, SisInfoTO sisInfoTO) throws Exception;

    public CarFunCobrosTO obtenerCobroAPartirDeChequePostfechado(Integer secuencial) throws Exception;

    public Map<String, Object> obtenerDatosParaChequesReversar(Map<String, Object> map) throws Exception;

    public boolean actualizarContableAnticipos(List<CarCobrosDetalleFormaPostfechadoTO> listaPosfechadosAnt, ConContable contable, boolean desdeContableDeposito, String cuenta, SisInfoTO sisInfoTO) throws Exception;
}
