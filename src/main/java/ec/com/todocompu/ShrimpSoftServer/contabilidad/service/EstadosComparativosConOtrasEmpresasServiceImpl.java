package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstadosComparativosConOtrasEmpresasDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstadoResultadosComparativoOtrasEmpresasTO;

@Service
public class EstadosComparativosConOtrasEmpresasServiceImpl implements EstadosComparativosConOtrasEmpresasService {

    @Autowired
    private EstadosComparativosConOtrasEmpresasDao comparativoOtrasEmpresasDao;

    @Override
    public Map<String, Object> obtenerCuadroComparativoEntreEmpresas(String empresa, String fechaHasta) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<ConEstadoSituacionFinancieraComparativoOtrasEmpresasTO> datosDeBase = comparativoOtrasEmpresasDao.obtenerCuadroComparativoEntreEmpresas(empresa, fechaHasta);
        if (datosDeBase != null) {
            List<String> primerNivel = datosDeBase.stream()
                    .map(item -> item.getEsfGrupo())
                    .distinct()
                    .collect(Collectors.toList());
            List<String> segundoNivel = datosDeBase.stream()
                    .map(item -> item.getEsfGrupoComparativo())
                    .distinct()
                    .collect(Collectors.toList());
            campos.put("primerNivel", primerNivel);
            campos.put("segundoNivel", segundoNivel);
        }
        campos.put("datosDeBase", datosDeBase);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerResultadoComparativoEntreEmpresas(String temporal, String desde, String hasta) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<ConEstadoResultadosComparativoOtrasEmpresasTO> datosDeBase = comparativoOtrasEmpresasDao.obtenerResultadoComparativoEntreEmpresas(temporal, desde, hasta);
        if (datosDeBase != null) {
            List<String> primerNivel = datosDeBase.stream()
                    .map(item -> item.getErGrupo())
                    .distinct()
                    .collect(Collectors.toList());
            List<String> segundoNivel = datosDeBase.stream()
                    .map(item -> item.getErGrupoComparativo())
                    .distinct()
                    .collect(Collectors.toList());
            campos.put("primerNivel", primerNivel);
            campos.put("segundoNivel", segundoNivel);
        }
        campos.put("datosDeBase", datosDeBase);
        return campos;
    }

}
