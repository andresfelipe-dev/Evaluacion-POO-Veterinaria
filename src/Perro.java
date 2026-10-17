public class Perro extends Paciente{
    private String raza;
    private boolean vacunado;

    public Perro(String nombre, double peso, int edad, String raza, boolean vacunado){
        super(nombre, peso, edad, vacunado ? "INGRESADO" : "CUARENTENA", 10);
        this.raza = raza;
        this.vacunado = vacunado;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        if (raza != null && !raza.trim().isEmpty()) {
            this.raza = raza;
        }
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    @Override
    public void setPeso(double peso) {
        if (peso > 0 && peso <= 80) {
            super.setPeso(peso);
        }
    }
}
