import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void conectar(String user, String password) throws SQLException {
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
    public ArrayList<String> obtenerColumnasDeUnaTabla(String nombreTabla) {
        String consulta = "SHOW COLUMNS FROM " + nombreTabla;
        ArrayList<String> nombreCampos = new ArrayList<>();
        try {
            ResultSet data;
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                nombreCampos.add(data.getString("Field"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nombreCampos;
    }

    public int obtenerId(String nombreTabla, String atributo, Object valor) {
        int id = 0;
        ResultSet data ;
        String atributoPK= obtenerColumnasDeUnaTabla(nombreTabla).get(0);
        String consulta = "SELECT "+ atributoPK+ " FROM " + nombreTabla + " where " + atributo + " = " + "\"" + valor + "\"";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                id=data.getInt(atributoPK);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    public Animal obtenerAnimalPorId(int idAnimal) {
        ResultSet data;
        Animal animal=new Animal();
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero, Descripcion from Animal WHERE ID = "+idAnimal+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                animal=(new Animal(obtenerValoresVacuna(data.getString("ID")), LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return animal;
    }
    public Persona obtenerPersonaPorId(int idPersona) {
        ResultSet data;
        Persona persona=new Persona();
        String consulta= "Select * from Persona WHERE ID ="+idPersona+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                persona=(new Persona(data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono")));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return persona;
    }
    public Estado obtenerEstadoPorId(int idEstado) {
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

    public ArrayList<String> obtenerSelectConMasDeUnValor(String nombreTabla, String nombreCampo,String columnaTabla,Object condicion){
        ResultSet data;
        ArrayList<String> valorCampo=new ArrayList<>();
        String consulta= "Select "+ nombreCampo+ " from " + nombreTabla+" where "+columnaTabla+"="+"\""+condicion+"\""+";";
        System.out.println(consulta);
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                valorCampo.add(data.getString(nombreCampo));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valorCampo;
    }


    public HashSet<Vacuna> obtenerValoresVacuna(String ID){
        ResultSet data;
        HashSet<Vacuna> vacunas = new HashSet<>();
        String consulta= "select Fecha, Dosis, Nombre from DetallesVacuna join Vacuna on Vacuna_ID = ID where Animal_ID = " + ID + ";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                vacunas.add(new Vacuna(LocalDate.of(Integer.parseInt(data.getString("Fecha").substring(0, 4)),Integer.parseInt(data.getString("Fecha").substring(5, 7)), Integer.parseInt(data.getString("Fecha").substring(8, 10))), data.getString("Dosis"), data.getString("Nombre")));
                //animales.add(new Animal(new HashSet<>(), LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza")));
                //valorCampo.add(data.getString("ID")+"-"+data.getString("Nombre")+"-"+data.getString("Especie")+"-"+data.getString("Raza")+"-"+data.getString("Fecha_nacimiento")+"-"+data.getString("Genero"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vacunas;
    }
    public HashSet<Animal> obtenerValoresAnimal(){
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
    public HashSet<Persona> obtenerValoresPersona(){
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
    public Animal buscarAnimal(HashSet<Animal> animales, HashSet<Vacuna> vacunas,LocalDate fechaNacimiento,String descripcion,String especie,String genero,String nombre,String raza){ //Comparar vacunas hace que no ande asi que lo saque
        for (Animal animal : animales){
            if (animal.esIgual(vacunas, fechaNacimiento, descripcion, especie, genero, nombre, raza)){
                return animal;
            }
        }
        return new Animal();
    }
    public Persona buscarPersona(HashSet<Persona> personas, boolean experiencia,LocalDate nacimiento,String correo,String direccion,String nombre,String ocupacion,String telefono){
        for (Persona persona : personas){
            if (persona.esIgual(experiencia, nacimiento, correo, direccion, nombre, ocupacion, telefono)){
                return persona;
            }
        }
        return new Persona();
    }
    public Animal obtenerAnimalPorIdEnJava(int idAnimal, HashSet<Animal> animales) {
        ResultSet data;
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero, Descripcion from Animal WHERE ID = "+idAnimal+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                return buscarAnimal(animales, obtenerValoresVacuna(data.getString("ID")), LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza"));
                //animal=(new Animal(obtenerValoresVacuna(data.getString("ID")), LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))), data.getString("Descripcion"), data.getString("Especie"), data.getString("Genero"), data.getString("Nombre"), data.getString("Raza")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Animal();
    }
    public Persona obtenerPersonaPorIdEnJava(int idPersona, HashSet<Persona> personas) {
        ResultSet data;
        String consulta= "Select * from Persona WHERE ID ="+idPersona+";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                return buscarPersona(personas, data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono"));
                //persona=(new Persona(data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono")));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return new Persona();
    }

    public HashSet<Solicitud> obtenerValoresSolicitud(HashSet <Animal> animales, HashSet<Persona> personas) {
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
                        aux));      //Hago esto porque si Fecha_adopcion es null se rompe
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return solicitudes;
    }
    public HashSet<Solicitud> obtenerValores() {
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
                        obtenerPersonaPorId(data.getInt("Persona_ID")),
                        obtenerAnimalPorId(data.getInt("Animal_ID")),
                        LocalDate.of(
                                Integer.parseInt(data.getString("Fecha_envio").substring(0, 4)),
                                Integer.parseInt(data.getString("Fecha_envio").substring(5, 7)),
                                Integer.parseInt(data.getString("Fecha_envio").substring(8, 10))),
                        obtenerEstadoPorId(data.getInt("Estado_ID")),
                        aux));      //Hago esto porque si Fecha_adopcion es null se rompe
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return solicitudes;
    }
    //Las funciones de abajo no las probé
    public HashSet<Animal> obtenerValoresAnimalSinSolicitudes(){
        ResultSet data;
        HashSet<Animal> animales=new HashSet<>();
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero, Descripcion from Animal where ID not in (select Animal_ID from Solicitud);";
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
    public HashSet<Persona> obtenerValoresPersonaSinSolicitudes(){
        ResultSet data;
        HashSet<Persona> personas=new HashSet<>();
        String consulta= "Select * from Persona where ID not in (select Persona_ID from Solicitud);";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next()) {
                personas.add(new Persona(data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono")));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return personas;
    }
}
//File
//Project Structure
//Modules
//Dependencies
// "+"
