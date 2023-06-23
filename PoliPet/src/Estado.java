public enum Estado {
    PENDIENTE("Pendiente"),APROBADA("Aprobada"),RECHAZADA("Rechazada");
    private String estado;
    private Estado(String estado) { this.estado=estado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
