import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
public class AccesoDB {
    private Connection conexion;
    private List<String> nombreTabla;
    private String nombreBaseDeDatos;
    public AccesoDB(String nombreBaseDeDatos, List<String> nombreTabla) {
        this.nombreBaseDeDatos = nombreBaseDeDatos;
        this.nombreTabla = nombreTabla;
    }
    public void conectar(String user, String password) throws SQLException { // Establece la conexión con la base de datos en MySQL
        String url = "jdbc:mysql://localhost:3306/"+nombreBaseDeDatos;
        try {
            conexion = DriverManager.getConnection(url, user, password);
            if (conexion != null) {
                System.out.println("Se ha conectado exitósamente a la base de datos");
            } else {
                System.out.println("No se ha podido conectar a la base de datos");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public Estado obtenerEstadoPorId(int idEstado) { // Se le ingresa una ID de estado y devuelve los detalles
        ResultSet data;
        String estadoStr="";
        Estado estado = null;
        String consulta= "Select Detalles FROM Estado WHERE ID ="+idEstado+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                estadoStr = data.getString("Detalles").toUpperCase();
                try {
                    estado = Estado.valueOf(estadoStr);
                    String valorEstado = estado.getEstado();
                } catch (IllegalArgumentException e) {  }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return estado;
    }
    public HashSet<Vacuna> obtenerValoresVacuna(String ID){ // Se le ingresa el ID del animal y devuelve las vacunas que se le han aplicado
        ResultSet data;
        HashSet<Vacuna> vacunas = new HashSet<>();
        String consulta= "select Fecha, Dosis, Nombre from DetallesVacuna join Vacuna on Vacuna_ID = ID where Animal_ID = " + ID + ";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                vacunas.add(new Vacuna(LocalDate.of(Integer.parseInt(data.getString("Fecha").substring(0, 4)),Integer.parseInt(data.getString("Fecha").substring(5, 7)), Integer.parseInt(data.getString("Fecha").substring(8, 10))), data.getString("Dosis"), data.getString("Nombre")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacunas;
    }
    public HashSet<Animal> obtenerValoresAnimal(){ // Llena los HashSet del Main con los datos del SQL
        ResultSet data;
        HashSet<Animal> animales=new HashSet<>();
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero, Descripcion from Animal;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                animales.add(new Animal(obtenerValoresVacuna(data.getString("ID")), LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animales;
    }
    public HashSet<Persona> obtenerValoresPersona(){ // Llena los HashSet del Main con los datos del SQL
        ResultSet data;
        HashSet<Persona> personas=new HashSet<>();
        String consulta= "Select * from Persona;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                personas.add(new Persona(data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono")));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return personas;
    }
    public Animal buscarAnimal(HashSet<Animal> animales, HashSet<Vacuna> vacunas,LocalDate fechaNacimiento,String descripcion,String especie,String genero,String nombre,String raza){ // Se le ingresan todos los datos de un animal y busca un animal en Java que coincida
        for (Animal animal : animales){
            if (animal.esIgual(vacunas, fechaNacimiento, descripcion, especie, genero, nombre, raza)){
                return animal;
            }
        }
        return new Animal();
    }
    public Persona buscarPersona(HashSet<Persona> personas, boolean experiencia,LocalDate nacimiento,String correo,String direccion,String nombre,String ocupacion,String telefono){ // Se le ingresan todos los datos de una persona y busca una persona en Java que coincida
        for (Persona persona : personas){
            if (persona.esIgual(experiencia, nacimiento, correo, direccion, nombre, ocupacion, telefono)){
                return persona;
            }
        }
        return new Persona();
    }
    public Animal obtenerAnimalPorIdEnJava(int idAnimal, HashSet<Animal> animales) { // Busca un animal ya existente en Java por su ID
        ResultSet data;
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero, Descripcion from Animal WHERE ID = "+idAnimal+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                return buscarAnimal(animales, obtenerValoresVacuna(data.getString("ID")), LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Animal();
    }
    public Persona obtenerPersonaPorIdEnJava(int idPersona, HashSet<Persona> personas) { // Busca una persona ya existente en Jaba por su ID
        ResultSet data;
        String consulta= "Select * from Persona WHERE ID ="+idPersona+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                return buscarPersona(personas, data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono"));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return new Persona();
    }

    public HashSet<Solicitud> obtenerValoresSolicitud(HashSet <Animal> animales, HashSet<Persona> personas) { // Llena el HashSet del Main con los datos de la tabla Solicitud de MySQL
        ResultSet data;
        HashSet<Solicitud> solicitudes=new HashSet<Solicitud>();
        String consulta= "Select * from Solicitud;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                LocalDate aux=null;
                if(data.getString("Fecha_adopcion")!=null){
                    aux=LocalDate.of(
                            Integer.parseInt(data.getString("Fecha_adopcion").substring(0, 4)),
                            Integer.parseInt(data.getString("Fecha_adopcion").substring(5, 7)),
                            Integer.parseInt(data.getString("Fecha_adopcion").substring(8, 10)));
                }
                solicitudes.add(new Solicitud(
                        obtenerPersonaPorIdEnJava(data.getInt("Persona_ID"), personas),
                        obtenerAnimalPorIdEnJava(data.getInt("Animal_ID"), animales),
                        LocalDate.of(
                                Integer.parseInt(data.getString("Fecha_envio").substring(0, 4)),
                                Integer.parseInt(data.getString("Fecha_envio").substring(5, 7)),
                                Integer.parseInt(data.getString("Fecha_envio").substring(8, 10))),
                        obtenerEstadoPorId(data.getInt("Estado_ID")),
                        aux));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return solicitudes;
    }
     public HashSet<Animal> procedureA(HashSet<Animal> animales){  // Llama a la procedure A del MySQL
        ResultSet data;
        String consulta= "CALL animalesSolicitados()";
        HashSet<Animal> animals=new HashSet<Animal>();
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                animals.add(obtenerAnimalPorIdEnJava(data.getInt("Animal_ID"), animales));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animals;
    }
}