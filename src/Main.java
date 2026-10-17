import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner scanner = new Scanner(System.in);
        String comando;

        // El programa lee comandos hasta recibir "salir"
        while (true) {
            if (!scanner.hasNextLine()) break;
            comando = scanner.nextLine().trim();

            if (comando.equalsIgnoreCase("salir")) {
                break;
            }

            String respuesta = procesarComando(hospital, comando);
            if (respuesta != null && !respuesta.isEmpty()) {
                System.out.println(respuesta);
            }
        }
        scanner.close();
    }

    private static String procesarComando(Hospital hospital, String comando) {
        String[] partes = comando.split("\\s+");
        if (partes.length == 0 || partes[0].isEmpty()) {
            return "";
        }

        String accion = partes[0].toLowerCase();

        switch (accion) {
            case "ingresar":
                if (partes.length < 2) return "ERROR: comando desconocido.";
                String tipo = partes[1].toLowerCase();

                if (tipo.equals("perro") && partes.length >= 7) {
                    // ingresar perro <nombre> <peso> <edad> <raza> <si/no>
                    String nombre = partes[2];
                    try {
                        double peso = Double.parseDouble(partes[3]);
                        int edad = Integer.parseInt(partes[4]);
                        String raza = partes[5];
                        String vacunado = partes[6];
                        return hospital.ingresarPerro(nombre, peso, edad, raza, vacunado);
                    } catch (NumberFormatException e) {
                        return "ERROR: datos invalidos";
                    }
                } else if (tipo.equals("gato") && partes.length >= 5) {
                    // ingresar gato <nombre> <peso> <edad>
                    String nombre = partes[2];
                    try {
                        double peso = Double.parseDouble(partes[3]);
                        int edad = Integer.parseInt(partes[4]);
                        return hospital.ingresarGato(nombre, peso, edad);
                    } catch (NumberFormatException e) {
                        return "ERROR: datos invalidos";
                    }
                } else if (tipo.equals("ave") && partes.length >= 6) {
                    // ingresar ave <nombre> <peso> <edad> <envergadura>[cite: 2]
                    String nombre = partes[2];
                    try {
                        double peso = Double.parseDouble(partes[3]);
                        int edad = Integer.parseInt(partes[4]);
                        double envergadura = Double.parseDouble(partes[5]);
                        return hospital.ingresarAve(nombre, peso, edad, envergadura);
                    } catch (NumberFormatException e) {
                        return "ERROR: datos invalidos";
                    }
                }
                return "ERROR: datos invalidos";

            case "vacunar":
                // vacunar <nombre>[cite: 2]
                if (partes.length < 2) return "ERROR: comando desconocido.";
                return hospital.vacunar(partes[1]);

            case "asignar":
                // asignar <nombre>[cite: 2]
                if (partes.length < 2) return "ERROR: comando desconocido.";
                return hospital.asignar(partes[1]);

            case "medicar":
                // medicar <nombre> <mg>[cite: 2]
                if (partes.length < 3) return "ERROR: comando desconocido.";
                String nombreMed = partes[1];
                try {
                    double mg = Double.parseDouble(partes[2]);
                    return hospital.medicar(nombreMed, mg);
                } catch (NumberFormatException e) {
                    return "ERROR: datos invalidos";
                }

            case "alta":
                // alta <nombre>[cite: 2]
                if (partes.length < 2) return "ERROR: comando desconocido.";
                return hospital.alta(partes[1]);

            case "reporte":
                // reporte[cite: 2]
                return hospital.generarReporte();

            default:
                return "ERROR: comando desconocido.";
        }
    }
}
