package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PagosFormaService {

    public String accionCarPagosForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public List<CarComboPagosCobrosFormaTO> getCarComboPagosCobrosFormaTO(String empresa, char accion) throws Exception;

    public List<CarListaPagosCobrosFormaTO> getCarListaPagosCobrosFormaTO(String empresa, char accion) throws Exception;

    public List<CarPagosCobrosFormaTO> getListaPagosCobrosFormaTO(String empresa, char accion, boolean inactivos) throws Exception;

    public String modificarEstadoCarPagosForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception;

}
