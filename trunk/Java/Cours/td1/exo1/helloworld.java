public class helloworld {

	public static void main (String [] args) {
	
	int a,i,somme=0;

	for(a=1;a<40000000;a++){
			somme=0;
		
			for(i=1;i<a;i++){
				if(a%i==0){
					somme=somme+i;
				}
		
			}
		
			if(somme==a)
					System.out.println(a +" est parfait!!!!!");
				
		
		}
	}
}