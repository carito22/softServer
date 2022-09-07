package ec.com.todocompu.ShrimpSoftServer.anexos.service;


import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprobantesTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface ComprobantesPublicosService {

    public List<InvComprobantesTO> listarComprobantesPublicos(String comprobante, String identificador, String fechaDesde, String fechaHasta, String tipoDocumento) throws Exception;

}
