/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxFormulario104Dao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormulario104;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class AnxFormulario104ServiceImpl implements AnxFormulario104Service {

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    @Autowired
    private AnxFormulario104Dao anxFormulario104Dao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxFormulario104> getAnxListaAnxFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return anxFormulario104Dao.getAnxListaAnxFormulario104(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public String insertarAnxFormulario104(AnxFormulario104 anxFormulario104, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susClave = anxFormulario104.getFrm104Secuencial() + "";
        susDetalle = "Se insertó el formulario 104 con código " + anxFormulario104.getFrm104Secuencial();
        susSuceso = "INSERT";
        susTabla = "anexo.anx_formulario_104";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        anxFormulario104.setUsrEmpresa(sisInfoTO.getEmpresa());
        anxFormulario104.setUsrCodigo(sisInfoTO.getUsuario());
        anxFormulario104.setUsrFechaInserta(UtilsDate.timestamp());
        if (anxFormulario104Dao.insertarAnxFormulario104(anxFormulario104, sisSuceso)) {
            retorno = "TEl formulario 104, con código:" + anxFormulario104.getFrm104Secuencial() + " , se guardó correctamente.";
        } else {
            retorno = "FHubo un error al guardar la anulación...\nIntente de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

    @Override
    public String modificarAnxFormulario104(AnxFormulario104 anxFormulario104, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susClave = anxFormulario104.getFrm104Secuencial() + "";
        susDetalle = "Se modificó el formulario 104 con código " + anxFormulario104.getFrm104Secuencial();
        susSuceso = "UPDATE";
        susTabla = "anexo.anx_formulario_104";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        anxFormulario104.setUsrEmpresa(sisInfoTO.getEmpresa());
        anxFormulario104.setUsrCodigo(sisInfoTO.getUsuario());
        anxFormulario104.setUsrFechaInserta(UtilsDate.timestamp());
        if (anxFormulario104Dao.modificarAnxFormulario104(anxFormulario104, sisSuceso)) {
            retorno = "TEl formulario 104, con código:" + anxFormulario104.getFrm104Secuencial() + " , se modificó correctamente.";
        } else {
            retorno = "FHubo un error al guardar la anulación...\nIntente de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

    @Override
    public String eliminarAnxFormulario104(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susClave = secuencial + "";
        susDetalle = "Se eliminó el formulario 104 con código " + secuencial;
        susSuceso = "DELETE";
        susTabla = "anexo.anx_formulario_104";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        AnxFormulario104 anxFormulario104 = anxFormulario104Dao.getAnxFormulario104(secuencial);
        if (anxFormulario104Dao.eliminarAnxFormulario104(anxFormulario104, sisSuceso)) {
            retorno = "TEl formulario 104, con código:" + anxFormulario104.getFrm104Secuencial() + " , se eliminó correctamente.";
        } else {
            retorno = "FHubo un error al guardar la anulación...\nIntente de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

    @Override
    public AnxFormulario104 obtenerAnxFormulario104(String empresa, String inicio, String fin) throws Exception {
        String sql = "select * from anexo.anx_formulario_104 where frm104_empresa = '" + empresa
                + "' and DATE(frm104_fecha_desde) = '" + inicio + "' and DATE(frm104_fecha_hasta) = '" + fin + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxFormulario104.class);
    }

}
