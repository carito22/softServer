package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface CobrosFormaService {

    public CarListaCobrosClienteTO getCobrosConsultaClienteAnticipoTO(String empresa, String periodo, String tipo, String numero) throws Exception;

    public String accionCarCobrosForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoCarCobroForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception;

}
