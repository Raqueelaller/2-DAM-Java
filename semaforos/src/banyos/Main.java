package banyos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Persona[] personita = new Persona[10];
		
		for(int i= 0; i<10; i++) {
			personita[i]=new Persona("personita "+(i +1));
			personita[i].start();
			
		}
		
	}

}
