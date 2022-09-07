package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstadoResultadosComparativoOtrasEmpresasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO;

@Repository
public class EstadosComparativosConOtrasEmpresasDaoImpl implements EstadosComparativosConOtrasEmpresasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO> obtenerCuadroComparativoEntreEmpresas(String empresa, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_estado_situacion_financiera_comparativo_otras_empresas('" + empresa + "', '" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO.class);
    }

    @Override
    public List<ConEstadoResultadosComparativoOtrasEmpresasTO> obtenerResultadoComparativoEntreEmpresas(String temporal, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_estado_resultados_comparativo_otras_empresas('" + temporal + "', '" + desde + "', '" + hasta + "')";
        return genericSQLDao.obtenerPorSql(sql, ConEstadoResultadosComparativoOtrasEmpresasTO.class);
    }

}
