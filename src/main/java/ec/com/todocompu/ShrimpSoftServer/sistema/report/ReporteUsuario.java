/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.report;

/**
 *
 * @author Desarrollador1
 */
public class ReporteUsuario {
    private String codigo;
    private String nickName;
    private String apellidos;
    private String nombres;
    private String grupo;
    private String estado;

    public ReporteUsuario() {
    }

    public ReporteUsuario(String codigo, String nickName, String apellidos, String nombres, String grupo, String estado) {
        this.codigo = codigo;
        this.nickName = nickName;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.grupo = grupo;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
