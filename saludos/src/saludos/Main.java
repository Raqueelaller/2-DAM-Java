package saludos;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Personal empleado1 = new Personal("jorge",false);
		Personal empleado2 = new Personal("Antonio",false);
		Personal empleado3 = new Personal("hector",false);
		Personal jefe = new Personal("jefe",true);
		
		
		empleado1.start();
		empleado2.start();
		empleado3.start();
		jefe.start();
		
	}

}
