public enum Estado {
    PENDIENTE("Pendiente"),APROBADO("Aprobado"),RECHAZADO("Rechazado");
    private String estado;
    private Estado(String estado) { this.estado=estado; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}