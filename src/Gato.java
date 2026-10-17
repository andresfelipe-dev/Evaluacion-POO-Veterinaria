public class Gato extends Paciente{
    private int nivelEstres;

    public Gato(String nombre, double peso, int edad, int nivelEstres){
        super(nombre, peso, edad, "INGRESADO", 6);
        this.nivelEstres = nivelEstres;
    }

    public int getNivelEstres() {
        return nivelEstres;
    }

    public void setNivelEstres(int nivelEstres) {
        if (nivelEstres >= 0 && nivelEstres <= 10) {
            this.nivelEstres = nivelEstres;
        }
    }

    @Override
    public void setPeso(double peso) {
        if (peso > 0 && peso <= 15) {
            super.setPeso(peso);
        }
    }
}
