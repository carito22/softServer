/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.dao;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

/**
 *
 * @author VALDIVIEZO
 */
public interface SisEmpresaParametrosMikrotikDao {

    public SisEmpresaParametrosMikrotik obtenerConfiguracionMikrotik(String empresa) throws Exception;

    public SisEmpresaParametrosMikrotik insertarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisSuceso sisSuceso) throws Exception;

    public SisEmpresaParametrosMikrotik modificarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisSuceso sisSuceso) throws Exception;

}
