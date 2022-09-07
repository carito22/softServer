package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import java.util.Date;
import java.util.List;

@Service
public class EmpresaParametrosServiceImpl implements EmpresaParametrosService {

    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Override
    public Integer getColumnasEstadosFinancieros(String empCodigo) throws Exception {
        return empresaParametrosDao.getColumnasEstadosFinancieros(empCodigo);
    }

    @Override
    public String actualizarTemporalDeEmpresas(List<String> empresas) throws Exception {
        String temp = "temp" + UtilsDate.timestampCompleto(new Date()).toString();
        if (empresas != null) {
            for (String empresa : empresas) {
                SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
                if (empresaParametros != null) {
                    empresaParametros.setParTextoAgrupacion(temp);
                    empresaParametrosDao.actualizar(empresaParametros);
                }
            }
        }
        return temp;
    }

    @Override
    public SisEmpresaParametros obtenerEmpresaParametros(String empCodigo) throws Exception {
        return empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empCodigo);
    }

}
