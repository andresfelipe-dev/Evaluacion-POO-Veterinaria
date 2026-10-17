public class AveExotica extends Paciente{
    private double envergadura;

    public AveExotica(String nombre, double peso, int edad,
                      double envergadura){
        super(nombre, peso, edad, "INGRESADO", 3);
        this.envergadura = envergadura;
    }

    public double getEnvergadura() {
        return envergadura;
    }

    public void setEnvergadura(double envergadura) {
        if (envergadura > 0) {
            this.envergadura = envergadura;
        }
    }

    @Override
    public void setPeso(double peso) {
        if (peso > 0 && peso <= 15) {
            super.setPeso(peso);
        }
    }
}
