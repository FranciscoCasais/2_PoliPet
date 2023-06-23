import java.time.LocalDate;
public class Persona {
    private boolean experiencia;
    private LocalDate fechaNacimiento;
    private String correo,direccion,nombre,ocupacion,telefono;
    public Persona() {
        this.experiencia=false;
        this.fechaNacimiento =null;
        this.correo="";
        this.direccion="";
        this.nombre="";
        this.ocupacion="";
        this.telefono="";
    }
    public Persona(boolean experiencia,LocalDate nacimiento,String correo,String direccion,String nombre,String ocupacion,String telefono) {
        this.experiencia=experiencia;
        this.fechaNacimiento =nacimiento;
        this.correo=correo;
        this.direccion=direccion;
        this.nombre=nombre;
        this.ocupacion=ocupacion;
        this.telefono=telefono;
    }
    public boolean getExperiencia() {
        return experiencia;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public String getCorreo() {
        return correo;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getNombre() {
        return nombre;
    }
    public String getOcupacion() {
        return ocupacion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setExperiencia(boolean experiencia) {
        this.experiencia=experiencia;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento=fechaNacimiento;
    }
    public void setCorreo(String correo) {
        this.correo=correo;
    }
    public void setDireccion(String direccion) {
        this.direccion=direccion;
    }
    public void setNombre(String nombre) {
        this.nombre=nombre;
    }
    public void setOcupacion(String ocupacion) {
        this.ocupacion=ocupacion;
    }
    public void setTelefono(String telefono) {
        this.telefono=telefono;
    }

    public int calcularEdad() {
        int edad;
        if(LocalDate.now().getDayOfYear()>=fechaNacimiento.getDayOfYear()){
            edad=LocalDate.now().getYear()-fechaNacimiento.getYear();
        }
        else{
            edad=LocalDate.now().getYear()-fechaNacimiento.getYear()-1;
        }
        return edad;
    }
    public boolean mayorDeEdad() {
        if(LocalDate.now().getYear()-fechaNacimiento.getYear()>=18) { return true; }
        else if(LocalDate.now().getYear()-fechaNacimiento.getYear()==18 && LocalDate.now().getDayOfYear()>=fechaNacimiento.getDayOfYear()) { return true; }
        else { return false; }
    }
}