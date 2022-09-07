package ec.com.todocompu.ShrimpSoftServer.rrhh.service;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhImpuestoRenta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImpuestoRentaServiceImpl implements ImpuestoRentaService {
    
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<RhImpuestoRenta> getImpuestoRenta(String empresa, String año) {
        String sql = "SELECT * FROM recursoshumanos.rh_impuesto_renta WHERE ( rta_desde = '" + año +"-01-01"+ "')";
        return genericSQLDao.obtenerPorSql(sql, RhImpuestoRenta.class);
    }
}
