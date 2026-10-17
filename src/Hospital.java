public class Hospital {
    private Paciente[] pacientes;
    private int contadorPacientes;
    private Jaula[] jaulas;

    public Hospital() {
        this.pacientes = new Paciente[10];
        this.contadorPacientes = 0;
        this.jaulas = new Jaula[6];

        inicializarJaulas();
        cargarDatosIniciales();
    }

    private void inicializarJaulas() {
        jaulas[0] = new Jaula(1, 'S', 'A');
        jaulas[1] = new Jaula(2, 'M', 'A');
        jaulas[2] = new Jaula(3, 'M', 'A');
        jaulas[3] = new Jaula(4, 'L', 'A');
        jaulas[4] = new Jaula(5, 'S', 'B');
        jaulas[5] = new Jaula(6, 'M', 'B');
    }

    private void cargarDatosIniciales() {
        Perro rocky = new Perro("Rocky", 30.0, 4, "Labrador", true);
        pacientes[contadorPacientes++] = rocky;

        Gato misifu = new Gato("Misifu", 4.0, 3, 0);
        pacientes[contadorPacientes++] = misifu;

        AveExotica kiwi = new AveExotica("Kiwi", 0.8, 2, 70.0);
        pacientes[contadorPacientes++] = kiwi;
    }

    public Paciente[] getPaciente( ){
        return pacientes;
    }

    public int getContadorPacientes(){
        return contadorPacientes;
    }

    public Jaula[] getJaulas() {
        return jaulas;
    }

    public String ingresarPerro(String nombre, double peso, int edad, String raza, String vacunadoStr) {
        boolean vacunado = vacunadoStr.equalsIgnoreCase("si");

        // Validación de datos R1 (nombre no vacío, peso > 0 y <= 80, edad 0 a 30)
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 80 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }

        // Validación R2: Duplicado (sin distinguir mayúsculas) y Hospital lleno
        String errorDuplicadoOLleno = validarDuplicadoYLleno(nombre);
        if (errorDuplicadoOLleno != null) {
            return errorDuplicadoOLleno;
        }

        Perro nuevoPerro = new Perro(nombre, peso, edad, raza, vacunado);
        pacientes[contadorPacientes++] = nuevoPerro;

        // Regla R3: si no está vacunado ingresa en CUARENTENA
        if (!vacunado) {
            return "OK: " + nombre + " ingresado en CUARENTENA";
        }
        return "OK: " + nombre + " ingresado";
    }

    public String ingresarGato(String nombre, double peso, int edad) {
        // Regla R1: peso máximo 15 para gato
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }

        String errorDuplicadoOLleno = validarDuplicadoYLleno(nombre);
        if (errorDuplicadoOLleno != null) {
            return errorDuplicadoOLleno;
        }

        // Gato ingresa con estrés inicial 0 por defecto al crear
        Gato nuevoGato = new Gato(nombre, peso, edad, 0);
        pacientes[contadorPacientes++] = nuevoGato;
        return "OK: " + nombre + " ingresado";
    }

    public String ingresarAve(String nombre, double peso, int edad, double envergadura) {
        // Regla R1: peso máximo 15 para ave, envergadura > 0
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30 || envergadura <= 0) {
            return "ERROR: datos invalidos";
        }

        String errorDuplicadoOLleno = validarDuplicadoYLleno(nombre);
        if (errorDuplicadoOLleno != null) {
            return errorDuplicadoOLleno;
        }

        AveExotica nuevaAve = new AveExotica(nombre, peso, edad, envergadura);
        pacientes[contadorPacientes++] = nuevaAve;
        return "OK: " + nombre + " ingresado";
    }

    private String validarDuplicadoYLleno(String nombre) {
        // Orden R2: duplicado antes que lleno
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return "ERROR: nombre duplicado";
            }
        }
        if (contadorPacientes >= 10) {
            return "ERROR: hospital lleno";
        }
        return null;
    }

    // Método auxiliar para buscar paciente por nombre
    private Paciente buscarPaciente(String nombre) {
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return pacientes[i];
            }
        }
        return null;
    }

    // --- 2. COMANDO VACUNAR ---
    public String vacunar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (!(p instanceof Perro)) {
            return "ERROR: solo aplica a perros";
        }
        Perro perro = (Perro) p;
        if (perro.isVacunado()) {
            return "ERROR: ya vacunado";
        }

        perro.setVacunado(true);
        perro.setEstado("INGRESADO");
        return "OK: " + nombre + " vacunado (pasa a INGRESADO)";
    }

    // --- 3. COMANDO ASIGNAR ---
    public String asignar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        // Orden de errores R6: no encontrado, cuarentena, ya tiene jaula, sin jaula compatible
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (p.getEstado().equals("CUARENTENA")) {
            return "ERROR: paciente en cuarentena";
        }
        if (p.getJaulaAsignada() != null) {
            return "ERROR: paciente ya tiene jaula";
        }

        // Determinar tamaño requerido (Regla R4) y zona (Regla R5)
        char tamanoReq = determinarTamanoJaula(p);
        char zonaReq = (p instanceof AveExotica) ? 'B' : 'A';

        Jaula jaulaLibre = null;
        for (Jaula j : jaulas) {
            if (j.estaLibre() && j.getZona() == zonaReq && j.getTamano() >= tamanoReq) {
                // Nota: Asumiendo 'S' < 'M' < 'L' en jerarquía de tamaños o comprobación directa
                if (cumpleTamano(j.getTamano(), tamanoReq)) {
                    jaulaLibre = j;
                    break;
                }
            }
        }

        if (jaulaLibre == null) {
            return "ERROR: no hay jaula compatible";
        }

        // Enlazar paciente y jaula
        p.setJaulaAsignada(jaulaLibre);
        jaulaLibre.setOcupante(p);
        return "OK: " + nombre + " asignado a jaula " + jaulaLibre.getNumero();
    }

    private char determinarTamanoJaula(Paciente p) {
        double peso = p.getPeso();
        char tamano = 'S';
        if (peso >= 5 && peso <= 25) {
            tamano = 'M';
        } else if (peso > 25) {
            tamano = 'L';
        }

        // Regla R4: Un ave con envergadura mayor a 60 cm necesita como mínimo M
        if (p instanceof AveExotica) {
            AveExotica ave = (AveExotica) p;
            if (ave.getEnvergadura() > 60 && tamano == 'S') {
                tamano = 'M';
            }
        }
        return tamano;
    }

    private boolean cumpleTamano(char jaulaTam, char reqTam) {
        if (reqTam == 'S') return jaulaTam == 'S' || jaulaTam == 'M' || jaulaTam == 'L';
        if (reqTam == 'M') return jaulaTam == 'M' || jaulaTam == 'L';
        if (reqTam == 'L') return jaulaTam == 'L';
        return false;
    }

    // --- 4. COMANDO MEDICAR ---
    public String medicar(String nombre, double mg) {
        Paciente p = buscarPaciente(nombre);

        // Orden de errores R8: no encontrado, mg <= 0, cuarentena, sin jaula, gato con estrés alto, dosis > máxima, acumulado superado
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (mg <= 0) {
            return "ERROR: datos invalidos";
        }
        if (p.getEstado().equals("CUARENTENA")) {
            return "ERROR: paciente en cuarentena";
        }
        if (p.getJaulaAsignada() == null) {
            return "ERROR: paciente sin jaula";
        }
        if (p instanceof Gato) {
            Gato gato = (Gato) p;
            if (gato.getNivelEstres() >= 8) {
                return "ERROR: gato con estres alto";
            }
        }

        double dosisMaxAplicacion = p.getPeso() * p.getFactorDosis();
        if (mg > dosisMaxAplicacion) {
            return "ERROR: dosis excede el maximo";
        }

        double limiteAcumulado = 3 * dosisMaxAplicacion;
        if ((p.getDosisAcumulada() + mg) > limiteAcumulado) {
            return "ERROR: tope acumulado superado";
        }

        // Aplicar medicación (Regla R9)
        p.setDosisAcumulada(p.getDosisAcumulada() + mg);
        p.setEstado("EN_TRATAMIENTO");

        if (p instanceof Gato) {
            Gato gato = (Gato) p;
            int nuevoEstres = Math.min(10, gato.getNivelEstres() + 2); // Sube 2, tope 10
            gato.setNivelEstres(nuevoEstres);
        }

        return "OK: " + nombre + " medicado con " + (int)mg + " mg"; // Ajustar formato si mg es entero/decimal según consola
    }

    // --- 5. COMANDO ALTA ---
    public String alta(String nombre) {
        int index = -1;
        Paciente p = null;
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                p = pacientes[i];
                index = i;
                break;
            }
        }

        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        // Regla R10: solo procede si el estado es EN_TRATAMIENTO
        if (!p.getEstado().equals("EN_TRATAMIENTO")) {
            return "ERROR: paciente no apto para alta";
        }

        // Libera la jaula si tiene
        if (p.getJaulaAsignada() != null) {
            p.getJaulaAsignada().setOcupante(null);
        }

        // Elimina al paciente del vector compactándolo (desplazando una posición)
        for (int i = index; i < contadorPacientes - 1; i++) {
            pacientes[i] = pacientes[i + 1];
        }
        pacientes[contadorPacientes - 1] = null;
        contadorPacientes--;

        return "OK: " + nombre + " dado de alta";
    }

    // --- 6. COMANDO REPORTE ---
    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE\n");
        sb.append("Pacientes: ").append(contadorPacientes).append("/10\n");

        for (int i = 0; i < contadorPacientes; i++) {
            Paciente p = pacientes[i];
            String tipo = "Desconocido";
            if (p instanceof Perro) tipo = "Perro";
            else if (p instanceof Gato) tipo = "Gato";
            else if (p instanceof AveExotica) tipo = "Ave";

            String jaulaStr = (p.getJaulaAsignada() == null) ? "-" : String.valueOf(p.getJaulaAsignada().getNumero());

            // Formato: <nombre> | <tipo> | <peso> kg | <estado> | jaula: <n o ->[cite: 2]
            sb.append(p.getNombre()).append(" | ")
                    .append(tipo).append(" | ")
                    .append(String.format("%.1f", p.getPeso())).append(" kg | ")
                    .append(p.getEstado()).append(" | ")
                    .append("jaula: ").append(jaulaStr).append("\n");
        }

        int jaulasLibres = 0;
        for (Jaula j : jaulas) {
            if (j.estaLibre()) jaulasLibres++;
        }
        sb.append("Jaulas libres: ").append(jaulasLibres);

        return sb.toString();
    }
}
