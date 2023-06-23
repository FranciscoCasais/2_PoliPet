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
            while (data.next() == true) {
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
            while (data.next() == true) {
                id=data.getInt(atributoPK);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<String> obtenerSelectConMasDeUnValor(String nombreTabla, String nombreCampo,String columnaTabla,Object condicion){
        ResultSet data;
        ArrayList<String> valorCampo=new ArrayList<>();
        String consulta= "Select "+ nombreCampo+ " from " + nombreTabla+" where "+columnaTabla+"="+"\""+condicion+"\""+";";
        System.out.println(consulta);
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next() == true) {
                valorCampo.add(data.getString(nombreCampo));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valorCampo;
    }
    /*public ArrayList<String> obtenerValoresAnimal(){ // Barbieri dijo que esta función debería devolver animales y no Strings. Abajo de este método está lo que avanzamos.
        ResultSet data;
        ArrayList<String> valorCampo=new ArrayList<>();
        String consulta= "Select ID, Nombre, Especie, Raza, Fecha_nacimiento, Genero from Animal;";
        System.out.println(consulta);
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next() == true) {
                valorCampo.add(data.getString("ID")+"-"+data.getString("Nombre")+"-"+data.getString("Especie")+"-"+data.getString("Raza")+"-"+data.getString("Fecha_nacimiento")+"-"+data.getString("Genero"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valorCampo;
    }*/

    public HashSet<Vacuna> obtenerValoresVacuna(String ID){
        ResultSet data;
        HashSet<Vacuna> vacunas = new HashSet<>();
        String consulta= "select Fecha, Dosis, Nombre from DetallesVacuna join Vacuna on Vacuna_ID = ID where Animal_ID = " + ID + ";";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next() == true) {
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
            while (data.next() == true) {
                personas.add(new Persona(data.getBoolean("Experiencia_previa"),LocalDate.of(Integer.parseInt(data.getString("Fecha_nacimiento").substring(0, 4)),Integer.parseInt(data.getString("Fecha_nacimiento").substring(5, 7)), Integer.parseInt(data.getString("Fecha_nacimiento").substring(8, 10))),data.getString("Email"),data.getString("Direccion"),data.getString("Nombre_completo"),data.getString("Ocupacion"),data.getString("Telefono")));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return personas;
    }
    public HashSet<Persona> obtenerValoresSolicitud() {
        ResultSet data;
        HashSet<Persona> personas=new HashSet<>();
        String consulta= "Select * from Persona;";
        try {
            PreparedStatement sentenciaSQL = conexion.prepareStatement(consulta);
            data = sentenciaSQL.executeQuery(consulta);
            while (data.next() == true) {
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