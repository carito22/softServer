package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

public interface MigracionesService {

    public List<String> migrarImagenesDeCompra(String empresa) throws Exception;

    public String migrarImagenDeCompra(Integer secuencial, String bucket) throws Exception;

    public String verificarImagenesMigradas(Integer imagen) throws Exception;

    public String migrarImagenDeProducto(Integer imagen, String bucket) throws Exception;
    
    public String verificarImagenesProductos(Integer imagen) throws Exception;

}
