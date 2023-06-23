
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> tablas = Arrays.asList("Estado", "Persona", "Solicitud", "Animal", "DetallesVacuna", "Vacuna");

        AccesoDB bdd = new AccesoDB("PoliPet", tablas);
        try {
            bdd.conectar("alumno","alumnoipm");

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        ArrayList<String> pedrosConsulta = new ArrayList<>();

        //pedrosConsulta = bdd.obtenerValoresAnimal();

        for(String s:pedrosConsulta){
            System.out.println(s);
        }

    }
}
