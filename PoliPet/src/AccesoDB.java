import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
                } catch (IllegalArgumentException e) {  }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return estado;
    }
    public HashSet<Object> obtenerValoresVacuna(String ID){ // Se le ingresa el ID del animal y devuelve las vacunas que se le han aplicado
        ResultSet data;
        HashSet<Object> vacunas = new HashSet<Object>();
        String consulta= "select Fecha, Dosis, Nombre from DetallesVacuna join Vacuna on Vacuna_ID = ID where Animal_ID = " + ID + ";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                HashMap<String, Object> vacuna = new HashMap<String, Object>();
                vacuna.put("Nombre", data.getString("Nombre"));
                vacuna.put("Dosis", data.getString("Dosis"));
                vacuna.put("Fecha", LocalDate.of(Integer.parseInt(data.getString("Fecha").substring(0, 4)),Integer.parseInt(data.getString("Fecha").substring(5, 7)), Integer.parseInt(data.getString("Fecha").substring(8, 10))));
                vacunas.add(vacuna);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacunas;
    }
    public HashMap<Integer, HashMap<String, Object>> obtenerValoresAnimal(){ // Llena los HashSet del Main con los datos del SQL
        ResultSet data;
        HashMap<Integer, HashMap<String, Object>> animales=new HashMap<Integer, HashMap<String, Object>>();
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero, Descripcion from Animal;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                HashMap<String, Object> animal = new HashMap<String, Object>();
                animal.put("Vacunas", obtenerValoresVacuna(data.getString("ID")));
                animal.put("Genero", data.getString("Genero"));
                animal.put("Nombre", data.getString("Nombre"));
                animal.put("Raza", data.getString("Raza"));
                animal.put("Especie", data.getString("Especie"));
                animal.put("Descripcion", data.getString("Descripcion"));
                animal.put("Fecha_nacimiento", LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))));
                animales.put(data.getInt("ID"), animal);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animales;
    }
    public HashMap<Integer,HashMap<String,Object>> obtenerValoresPersona(){ // Llena los HashSet del Main con los datos del SQL
        ResultSet data;
        HashMap<Integer,HashMap<String,Object>> personas=new HashMap<>();
        String consulta= "Select * from Persona;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                HashMap<String,Object> persona=new HashMap<>();
                persona.put("Nombre_completo",data.getString("Nombre_completo"));
                persona.put("Email",data.getString("Email"));
                persona.put("Telefono",data.getString("Telefono"));
                persona.put("Direccion",data.getString("Direccion"));
                persona.put("Fecha_nacimiento", LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))));
                persona.put("Ocupacion",data.getString("Ocupacion"));
                persona.put("Experiencia_previa",data.getBoolean("Experiencia_previa"));
                personas.put(data.getInt("ID"),persona);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return personas;
    }
    public HashMap<Integer,HashMap<String,Object>> obtenerAnimalPorIdEnJava(int idAnimal) { // Busca un animal ya existente en Java por su ID
        ResultSet data;
        HashMap<Integer,HashMap<String,Object>> animal_=new HashMap<>();
        String consulta= "Select * from Animal WHERE ID = "+idAnimal+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                HashMap<String, Object> animal = new HashMap<String, Object>();
                animal.put("Vacunas", obtenerValoresVacuna(data.getString("ID")));
                animal.put("Genero", data.getString("Genero"));
                animal.put("Nombre", data.getString("Nombre"));
                animal.put("Raza", data.getString("Raza"));
                animal.put("Especie", data.getString("Especie"));
                animal.put("Descripcion", data.getString("Descripcion"));
                animal.put("Fecha_nacimiento", LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))));
                animal_.put(idAnimal,animal);
                // return buscarAnimal(animales, LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animal_;
    }
    public HashMap<Integer, HashMap<String, Object>> obtenerPersonaPorIdEnJava(int idPersona) { // Busca una persona ya existente en Jaba por su ID
        ResultSet data;
        HashMap<Integer,HashMap<String,Object>> personas=new HashMap<>();
        String consulta= "Select * from Persona WHERE ID = "+idPersona+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                HashMap<String,Object> persona=new HashMap<>();
                persona.put("Nombre_completo",data.getString("Nombre_completo"));
                persona.put("Email",data.getString("Email"));
                persona.put("Telefono",data.getString("Telefono"));
                persona.put("Direccion",data.getString("Direccion"));
                persona.put("Fecha_nacimiento", LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))));
                persona.put("Ocupacion",data.getString("Ocupacion"));
                persona.put("Experiencia_previa",data.getBoolean("Experiencia_previa"));
                personas.put(data.getInt("ID"),persona);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return personas;
    }

    public HashMap<ArrayList<Integer>,HashMap<String,Object>> obtenerValoresSolicitud() { // Llena el HashSet del Main con los datos de la tabla Solicitud de MySQL
        ResultSet data;
        HashMap<ArrayList<Integer>,HashMap<String,Object>> solicitudes=new HashMap<>();
        String consulta= "Select * from Solicitud;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                ArrayList<Integer> ids=new ArrayList<>();
                HashMap<String,Object> solicitud=new HashMap<>();
                LocalDate aux=null;
                if(data.getString("Fecha_adopcion")!=null){
                    aux=LocalDate.of(
                            Integer.parseInt(data.getString("Fecha_adopcion").substring(0, 4)),
                            Integer.parseInt(data.getString("Fecha_adopcion").substring(5, 7)),
                            Integer.parseInt(data.getString("Fecha_adopcion").substring(8, 10)));
                }
                ids.add(data.getInt("Persona_ID"));
                ids.add(data.getInt("Animal_ID"));
                // solicitud.put("Persona_ID",obtenerPersonaPorIdEnJava(data.getInt("Persona_ID"),personas));
                // solicitud.put("Animal_ID",obtenerAnimalPorIdEnJava(data.getInt("Animal_ID"),animales));
                solicitud.put("Fecha_envio",LocalDate.of(Integer.parseInt(data.getString("Fecha_envio").substring(0, 4)),Integer.parseInt(data.getString("Fecha_envio").substring(5, 7)), Integer.parseInt(data.getString("Fecha_envio").substring(8, 10))));
                solicitud.put("Fecha_adopcion",aux);
                solicitud.put("Estado",obtenerEstadoPorId(data.getInt("Estado_ID")));
                solicitudes.put(ids,solicitud);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return solicitudes;
    }
     public HashMap<Integer, HashMap<String, Object>> procedureA(){  // Llama a la procedure A del MySQL
        ResultSet data;
        String consulta= "CALL animalesSolicitados()";
        HashMap<Integer, HashMap<String, Object>> animals=new HashMap<>();
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                animals.put(data.getInt("Animal_ID"), obtenerAnimalPorIdEnJava(data.getInt("Animal_ID")).get(data.getInt("Animal_ID")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animals;
    }
}