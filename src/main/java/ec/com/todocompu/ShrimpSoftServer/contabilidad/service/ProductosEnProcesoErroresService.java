package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosCuentasInconsistentes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresCompra;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresConsumo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridasInconsistentesTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductosEnProcesoErroresService {

    public List<InventarioProductosEnProcesoErroresCompra> listarPppErroresCompra(String empresa, String desde, String hasta) throws Exception;

    public List<InventarioProductosEnProcesoErroresConsumo> listarPppErroresConsumo(String empresa, String desde, String hasta) throws Exception;

    public List<InventarioProductosCuentasInconsistentes> productosCuentasInconsitentes(String empresa) throws Exception;

    public List<PrdCorridasInconsistentesTO> listarCorridasInconsistentes(String empresa, String fechaInicio, String fechaFin) throws Exception;

}
