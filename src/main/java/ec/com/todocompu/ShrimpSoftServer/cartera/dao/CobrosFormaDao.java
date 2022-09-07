package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosForma;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CobrosFormaDao extends GenericDao<CarCobrosForma, Integer> {

    public Boolean accionCarFormaCobro(CarCobrosForma carCobrosFormaAux, CarCobrosForma carCobrosForma, SisSuceso sisSuceso, char accion) throws Exception;

    public String buscarCtaFormaCobro(String empresa, Integer secuencial) throws Exception;

    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception;

}
