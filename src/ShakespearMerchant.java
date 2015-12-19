public class ShakespearMerchant {
	
	public static void main (String args[]) {
		System.out.println("hello lucence");
	}
	
	public void index(){
		IndexClass indexClass=new IndexClass();
		indexClass.run();
	}
	
	public void search(String queryStr){
		SearchClass searchClass=new SearchClass();
		searchClass.run(String queryStr);
	}
	
	public void gamaEncode(){
		
	}
	
}