package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{


	private int contadorPersonasTotales;
    	private Hashtable<String, Integer> contadoresPersonasPuerta;
    	private static final int AFORO_MAXIMO = 50;

    	public Parque() {
        	contadorPersonasTotales = 0;
        	contadoresPersonasPuerta = new Hashtable<String, Integer>();
    	}


	@Override
    	public synchronized void entrarAlParque(String puerta) {
        	comprobarAntesDeEntrar();
        	if (contadoresPersonasPuerta.get(puerta) == null) {
            	contadoresPersonasPuerta.put(puerta, 0);
        	}

        	contadorPersonasTotales++;
        	contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);

        	imprimirInfo(puerta, "Entrada");
        	checkInvariante();
    	}
	
	@Override
    	public synchronized void salirDelParque(String puerta) {
        	comprobarAntesDeSalir();
        	contadorPersonasTotales--;
        	contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);

        	imprimirInfo(puerta, "Salida");
        	checkInvariante();
    	}
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
        	assert contadorPersonasTotales <= AFORO_MAXIMO : "INV: Se ha superado el aforo máximo del parque";
        	assert contadorPersonasTotales >= 0 : "INV: El contador total de personas no puede ser negativo";
        	assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador total";
    	}

	protected void comprobarAntesDeEntrar() {
        	assert contadorPersonasTotales < AFORO_MAXIMO : "El parque está lleno, no se pueden admitir más personas.";
    	}

	protected void comprobarAntesDeSalir() {
        	assert contadorPersonasTotales > 0 : "El parque está vacío, no se puede salir.";
    	}


}
