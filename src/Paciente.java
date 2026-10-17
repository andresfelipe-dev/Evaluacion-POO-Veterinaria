public class Paciente {
    private String nombre;
    private double peso;
    private int edad;
    private String estado;
    private double factorDosis;
    private double dosisAcumulada;
    private Jaula jaulaAsignada;

    public Paciente(String nombre, double peso, int edad, String estado, double factorDosis){
        this.nombre = nombre;
        this.peso = peso;
        this.edad = edad;
        this.estado = estado;
        this.factorDosis = factorDosis;
        this.dosisAcumulada = 0.0;
        this.jaulaAsignada = null;
    }

    //Getters
    public double getFactorDosis() {
        return factorDosis;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public int getEdad() {
        return edad;
    }

    public String getEstado() {
        return estado;
    }

    public double getDosisAcumulada() {
        return dosisAcumulada;
    }

    public Jaula getJaulaAsignada() {
        return jaulaAsignada;
    }

    //Setters
    public void setNombre(String nombre) {
        // Regla R1: nombre no vacío
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public void setPeso(double peso) {
        if (peso > 0) {
            this.peso = peso;
        }
    }

    public void setEdad(int edad) {
        // Regla R1: edad de 0 a 30
        if (edad >= 0 && edad <= 30) {
            this.edad = edad;
        }
    }

    public void setEstado(String estado) {
        if (estado != null && (estado.equals("INGRESADO") ||
                estado.equals("CUARENTENA") ||
                estado.equals("EN_TRATAMIENTO"))) {
            this.estado = estado;
        }
    }

    public void setDosisAcumulada(double dosisAcumulada) {
        this.dosisAcumulada = dosisAcumulada;
    }

    public void setJaulaAsignada(Jaula jaulaAsignada) {
        this.jaulaAsignada = jaulaAsignada;
    }
}