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
        jaulas[1] = new Jaula(1, 'S', 'A');
        jaulas[2] = new Jaula(2, 'M', 'A');
        jaulas[3] = new Jaula(3, 'M', 'A');
        jaulas[4] = new Jaula(4, 'L', 'A');
        jaulas[5] = new Jaula(5, 'S', 'B');
        jaulas[6] = new Jaula(6, 'M', 'B');
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
}
