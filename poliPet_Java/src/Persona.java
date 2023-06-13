import java.util.Date;
public class Persona {
    private boolean experiencia;
    private int edad;
    private Date nacimiento;
    private String correo;
    private String direccion;
    private String nombre;
    private String ocupacion;
    private String telefono;

    public Persona(boolean experiencia, int edad, Date nacimiento, String correo, String direccion, String nombre, String ocupacion, String telefono) {
        this.experiencia = experiencia;
        this.edad = edad;
        this.nacimiento = nacimiento;
        this.correo = correo;
        this.direccion = direccion;
        this.nombre = nombre;
        this.ocupacion = ocupacion;
        this.telefono = telefono;
    }
    public Persona() {
        this.experiencia = false;
        this.edad = 0;
        this.nacimiento = new Date();
        this.correo = "";
        this.direccion = "";
        this.nombre = "";
        this.ocupacion = "";
        this.telefono = "";
    }

    public boolean isExperiencia() {
        return experiencia;
    }

    public void setExperiencia(boolean experiencia) {
        this.experiencia = experiencia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public boolean mayorEdad(){
        if(edad>=18){
            return true;
        }else{
            return false;
        }
    }
}