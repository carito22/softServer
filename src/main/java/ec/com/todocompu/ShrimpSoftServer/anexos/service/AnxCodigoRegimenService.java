/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCodigoRegimen;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface AnxCodigoRegimenService {

    public List<AnxCodigoRegimen> getListaAnxCodigoRegimen() throws Exception;
}
