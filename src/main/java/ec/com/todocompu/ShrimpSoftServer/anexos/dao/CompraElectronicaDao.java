package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronica;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaComprasFacturasPorNumeroTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface CompraElectronicaDao extends GenericDao<AnxCompraElectronica, Integer> {

    public AnxCompraElectronica buscarAnxCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public Boolean accionAnxCompraElectronica(AnxCompraElectronica anxCompraElectronica, AnxCompra anxCompra, SisSuceso sisSuceso, char accion) throws Exception;

    public String getXmlComprobanteRetencion(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception;

    public boolean comprobarAnxCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public boolean comprobarRetencionAutorizadaProcesamiento(String empresa, String periodo, String motivo, String numero) throws Exception;

    public InvConsultaComprasFacturasPorNumeroTO getConsultaPKRetencion(String codEmpresa, String numRetencion, String numAutorizacion) throws Exception;

    public List<AnxListaLiquidacionComprasPendientesTO> getListaLiquidacionCompraPendientes(String empresa) throws Exception;

    public String getXmlLiquidacionCompras(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception;

}
