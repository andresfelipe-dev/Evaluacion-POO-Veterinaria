public class Jaula {
    private int numero;
    private char tamano;
    private char zona;
    private Paciente ocupante;

    public Jaula(int numero, char tamano, char zona){
        this.numero = numero;
        this.tamano = tamano;
        this.zona = zona;
        this.ocupante = null;
    }

    public boolean estaLibre() {
        return this.ocupante == null;
    }

    // --- GETTERS Y SETTERS ---
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero > 0) {
            this.numero = numero;
        }
    }

    public char getTamano() {
        return tamano;
    }

    public void setTamano(char tamano) {
        if (tamano == 'S' || tamano == 'M' || tamano == 'L') {
            this.tamano = tamano;
        }
    }

    public char getZona() {
        return zona;
    }

    public void setZona(char zona) {
        if (zona == 'A' || zona == 'B') {
            this.zona = zona;
        }
    }

    public Paciente getOcupante() {
        return ocupante;
    }

    public void setOcupante(Paciente ocupante) {
        this.ocupante = ocupante;
    }
}
