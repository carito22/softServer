package ec.com.todocompu.ShrimpSoftServer.cartera.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoFormaCobroCarteraDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosForma;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoFormaCobroCartera;

@Repository
public class CobrosFormaDaoImpl extends GenericDaoImpl<CarCobrosForma, Integer> implements CobrosFormaDao {

    @Autowired
    private SucesoDao sisSucesoDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoFormaCobroCarteraDao sucesoFormaCobroCarteraDao;

    @Override
    public Boolean accionCarFormaCobro(CarCobrosForma carCobrosFormaAux, CarCobrosForma carCobrosForma, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(carCobrosForma);
        }
        if (accion == 'M') {
            actualizar(carCobrosForma);
        }
        if (accion == 'E') {
            eliminar(carCobrosForma);
        }
        sisSucesoDao.insertar(sisSuceso);
        if (accion == 'I' || accion == 'M') {
            SisSucesoFormaCobroCartera sucesoFc = new SisSucesoFormaCobroCartera();
            String json = UtilsJSON.objetoToJson(carCobrosForma);
            sucesoFc.setSusJson(json);
            sucesoFc.setSisSuceso(sisSuceso);
            sucesoFc.setCarCobrosForma(carCobrosForma);
            sucesoFormaCobroCarteraDao.insertar(sucesoFc);
        }
        return true;
    }

    @Override
    public String buscarCtaFormaCobro(String empresa, Integer secuencial) throws Exception {
        try {
            String sql = "SELECT cta_codigo " + "FROM cartera.car_cobros_forma " + "WHERE (usr_empresa = '" + empresa
                    + "') AND " + "(fp_secuencial = " + secuencial + ") AND "
                    + "(NOT fp_inactivo OR fp_inactivo IS NULL);";
            return (String) genericSQLDao.obtenerObjetoPorSql(sql);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception {
        String sql = "SELECT * FROM cartera.car_cobros_forma WHERE "
                + "(usr_empresa = '" + empresa + "') AND "
                + "(cta_codigo = '" + ctaCodigo + "') AND "
                + "(sec_empresa = '" + empresa + "') AND "
                + "(sec_codigo = '" + sector + "')";
        return genericSQLDao.obtenerPorSql(sql, CarPagosCobrosFormaTO.class).get(0);
    }
}
