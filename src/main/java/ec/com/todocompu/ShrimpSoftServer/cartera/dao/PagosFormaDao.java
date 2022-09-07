package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosForma;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PagosFormaDao extends GenericDao<CarPagosForma, Integer> {

    public Boolean accionCarFormaPago(CarPagosForma carPagosForma, SisSuceso sisSuceso, char accion) throws Exception;

    public String buscarCtaFormaPago(String empresa, Integer secuencial) throws Exception;

    public Boolean buscarCarPagosForma(String ctaContable, String empresa, String sector) throws Exception;

    public List<CarComboPagosCobrosFormaTO> getCarComboPagosCobrosForma(String empresa, char accion) throws Exception;

    public List<CarListaPagosCobrosFormaTO> getCarListaPagosCobrosForma(String empresa, char accion) throws Exception;

    public List<CarPagosCobrosFormaTO> getListaPagosCobrosFormaTO(String empresa, char accion, boolean inactivos) throws Exception;

    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception;

}
