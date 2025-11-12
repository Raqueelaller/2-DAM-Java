package saludos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Saludo s = new Saludo();
		
		Personal empleado1 = new Personal("jorge",s,false);
		Personal empleado2 = new Personal("Antonio",s,false);
		Personal empleado3 = new Personal("hector",s,false);
		Personal jefe = new Personal("jefe",s,true);
		
		
		empleado1.start();
		empleado2.start();
		empleado3.start();
		jefe.start();
		
	}

}
