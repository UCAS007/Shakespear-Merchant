import java.io.IOException;

public class ShakespearMerchant {
	

	public static void main (String args[]) throws IOException {
		System.out.println("hello lucence");
		long time1,time2,usetime;
		
		int command=0;
		
		if(command==0){
			time1=System.currentTimeMillis();
			index();
			time2=System.currentTimeMillis();
			usetime=time2-time1;
			System.out.println("total run time is "+usetime);
		}
		
		if(command==1){
			time1=System.currentTimeMillis();
			gama();
			time2=System.currentTimeMillis();
			usetime=time2-time1;
			System.out.println("total run time is "+usetime);
		}
		
		if(command==2){
			time1=System.currentTimeMillis();
			singleString();
			time2=System.currentTimeMillis();
			usetime=time2-time1;
			System.out.println("total run time is "+usetime);
		}
		
		if(command==3){
			time1=System.currentTimeMillis();
			search("his friend, suitor likewise to Portia");
			time2=System.currentTimeMillis();
			usetime=time2-time1;
			System.out.println("total run time is "+usetime);
		}
	}
	
	public static void index() throws IOException{
		IndexClass indexClass=new IndexClass();
		indexClass.run();
	}
	
	public static void search(String queryStr) throws IOException{
		SearchClass searchClass=new SearchClass();
		searchClass.run(queryStr);
	}
	
	public static void gama() throws IOException{
		GamaEncodeClass gama=new GamaEncodeClass();
		gama.run();
	}
	
	public static void singleString() throws IOException{
		SingleString singlestr=new SingleString();
		singlestr.run();
	}
	
}