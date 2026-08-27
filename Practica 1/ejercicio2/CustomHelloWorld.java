package ejercicio2;

import java.util.Scanner;

public class CustomHelloWorld {

	public static void main(String[] args) {

		String scanner;
		
		Scanner scanner1= new Scanner(System.in);
		System.out.println("Escribe tu nombre: ");
		String nombre = scanner1.nextLine();
		scanner1.close();
		
		System.out.println("Hola " +nombre);
		
		
		
	}

}
