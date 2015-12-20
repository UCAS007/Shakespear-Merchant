import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a="Helloworld";
		String b=a.toLowerCase();
		
		List<String> strlist=new ArrayList<String>();
		strlist.add(a);
		strlist.add(b);
		int idxa=strlist.indexOf(a);
		int idxb=strlist.indexOf(b);
		int siz=strlist.size();
		
		System.out.println("a b size is "+idxa+","+idxb+","+siz);
//		a.toLowerCase();
		System.out.println(a);
		System.out.println(b);
		
		String nu="\r";
		if(nu.isEmpty()){
			System.out.println("null is empty");
		}
		else{
			System.out.println("null is no empty");
		}
	}

}
