package ejercicio2;

public class Hilo implements Runnable {
	
		private final Contador c;
	
		
		
		public String nombreHilo;
		public int suma;

		public Hilo(String nombreHilo, Contador c,int suma) {
			super();
			this.nombreHilo = nombreHilo;
			this.c=c;
			this.suma=suma;
			
		}

		public String getNombreHilo() {
			return nombreHilo;
		}

		public void setNombreHilo(String nombreHilo) {
			this.nombreHilo = nombreHilo;
		}
		
		public void sumar(int sumar) throws InterruptedException {
				for(int i=0; i<sumar ; i++) {
					c.imcrementarContador();
					System.out.println(getNombreHilo()+" Ha incrementado en "+i);
					
					try{
						Thread.sleep(500);
					}catch(InterruptedException x) {
						System.out.println(x.getMessage());
					}
				}
				System.out.println(getNombreHilo()+"he terminado de incrementar");
				} 
		
		@Override
		public void run() {
			
			try {
			sumar(suma);
				
			}catch(InterruptedException x) {
				System.out.println(x.getMessage());
			}
			
			
		}
		
		
		

	}


