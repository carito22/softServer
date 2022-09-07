package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasProgramadas;
import ec.com.todocompu.ShrimpSoftUtils.quartz.TO.InvComprasProgramadasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ComprasProgramadasDaoImpl extends GenericDaoImpl<InvComprasProgramadas, Integer> implements ComprasProgramadasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<InvComprasProgramadasTO> invListadoComprasProgramadasTO(String fecha) throws Exception {
        String sql = "select inv_compras_programadas.*,inv_compras.comp_total, inv_compras.comp_observaciones  from inventario.inv_compras_programadas "
                + "join inventario.inv_compras "
                + "on inv_compras_programadas.comp_empresa = inv_compras.comp_empresa "
                + "and inv_compras_programadas.comp_periodo = inv_compras.comp_periodo "
                + "and inv_compras_programadas.comp_motivo = inv_compras.comp_motivo "
                + "and inv_compras_programadas.comp_numero = inv_compras.comp_numero "
                + " where '" + fecha + "' BETWEEN cp_desde AND cp_hasta";
        return genericSQLDao.obtenerPorSql(sql, InvComprasProgramadasTO.class);
    }

    @Override
    public List<InvComprasProgramadasTO> listarInvComprasProgramadasTO(String empresa) throws Exception {
        String sql = "select inv_compras_programadas.*,inv_compras.comp_total, inv_compras.comp_observaciones "
                + "from inventario.inv_compras_programadas "
                + "join inventario.inv_compras "
                + "on inv_compras_programadas.comp_empresa = inv_compras.comp_empresa "
                + "and inv_compras_programadas.comp_periodo = inv_compras.comp_periodo "
                + "and inv_compras_programadas.comp_motivo = inv_compras.comp_motivo "
                + "and inv_compras_programadas.comp_numero = inv_compras.comp_numero "
                + "where inv_compras_programadas.comp_empresa =  '" + empresa + "';";
        return genericSQLDao.obtenerPorSql(sql, InvComprasProgramadasTO.class);
    }

    public InvComprasProgramadas obtenerCompraProgramadaPorCompra(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "select * from inventario.inv_compras_programadas where comp_empresa = '" + empresa + "' and comp_periodo = '"
                + periodo + "' and comp_motivo = '" + motivo + "' and comp_numero = '" + numero + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvComprasProgramadas.class);
    }

    @Override
    public String generarInvComprasProgramadas(InvComprasProgramadasTO invComprasProgramadas, String fecha, String usuario) throws Exception {
        String sql = "SELECT inventario.fun_compras_duplicar('" + invComprasProgramadas.getCompEmpresa() + "','" + invComprasProgramadas.getCompPeriodo()
                + "','" + invComprasProgramadas.getCompMotivo() + "','" + invComprasProgramadas.getCompNumero() + "','" + fecha + "','" + usuario + "');";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public InvComprasProgramadas configurarCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception {
        InvComprasPK compraPk = comprasProgramadas.getInvCompras().getInvComprasPK();
        if (obtenerCompraProgramadaPorCompra(compraPk.getCompEmpresa(), compraPk.getCompPeriodo(), compraPk.getCompMotivo(), compraPk.getCompNumero()) != null) {
            throw new GeneralException("Ya existe una configuración para la compra seleccionada.");
        }
        establecerCompraComoProgramada(compraPk);
        insertar(comprasProgramadas);
        String susClave = comprasProgramadas.getCpSecuencial() + "";
        String susDetalle = "Se insertó la configuracion de compra programada: " + susClave;
        String susSuceso = "INSERT";
        String susTabla = "inventario.inv_compras_programadas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sucesoDao.insertar(sisSuceso);
        return comprasProgramadas;
    }

    private boolean establecerCompraComoProgramada(InvComprasPK pk) throws Exception {
        String sql = "UPDATE inventario.inv_compras SET comp_programada=" + true + " WHERE comp_empresa='"
                + pk.getCompEmpresa() + "' and  comp_periodo='" + pk.getCompPeriodo()
                + "' and comp_motivo='" + pk.getCompMotivo() + "' and comp_numero='" + pk.getCompNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        return true;
    }

    @Override
    public InvComprasProgramadas editarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception {
        actualizar(comprasProgramadas);
        String susClave = comprasProgramadas.getCpSecuencial() + "";
        String susDetalle = "Se actualizó la configuracion de compra programada: " + susClave;
        String susSuceso = "UPDATE";
        String susTabla = "inventario.inv_compras_programadas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sucesoDao.insertar(sisSuceso);
        return comprasProgramadas;
    }

    @Override
    public InvComprasProgramadas eliminarConfiguracionCompraProgramada(InvComprasProgramadas comprasProgramadas, SisInfoTO sisInfoTO) throws Exception {
        eliminar(comprasProgramadas);
        String susClave = comprasProgramadas.getCpSecuencial() + "";
        String susDetalle = "Se eliminó la configuracion de compra programada: " + susClave;
        String susSuceso = "DELETE";
        String susTabla = "inventario.inv_compras_programadas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sucesoDao.insertar(sisSuceso);
        return comprasProgramadas;
    }
}
