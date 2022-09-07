package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunMovimientosSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSerieTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface InvSeriesService {

    public List<InvSerieTO> listarSeriesVigentes(String empresa, String bodega, String producto) throws Exception;

    public boolean esSerieValidaAlGuardarVenta(String tipoDocumento, String serie, String producto, String bodega, String empresa) throws Exception;

    public boolean serieCompraOcupada(String empresa, String numero_serie, String codigo_producto) throws Exception;

    public boolean validarSiExisteSerieCompra(String empresa, String numero_serie, String codigo_producto, Integer secuencial) throws Exception;

    public boolean validarSerieRepetidaAlGuardarConsumo(Integer secuencial, String serie, String producto) throws Exception;

    public List<InvFunMovimientosSerieTO> listarMovimientosSeries(String empresa, String serie, String producto) throws Exception;

}
