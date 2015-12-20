import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamaEncodeClass {
	static String docfilename="./output/wordDoc.txt";
	static String decodeFilename="./output/decode.txt";
	static String encodeFilename="./output/encode.txt";
	public static void main (String args[]) {
		System.out.println("hello lucence");
		
		String code="";
		for(int i=2;i<10;i++){
			String str=gamaEncode(i);
			code=code+str;
			
			System.out.println("i="+i+" \t gama encode = "+str);
		}
		List<Integer> list=gamaDecode(code);
		
		for(int i:list){
			System.out.println("i="+i);
		}
	}
	
	public void run() throws IOException{
		encode();
		decode();
	}
	
	public static void encode() throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(docfilename));
		BufferedWriter bw=new BufferedWriter(new FileWriter(encodeFilename));
		String line;
		line=br.readLine();
		while(line!=null){
			String[] list=line.split(" ");
			String newline="";
			
			int i=0;
			int idx=0;
			int idx0=0;
			for(String word : list){
				if(i==0){
					newline=word+" ";
					i=i+1;
				}
				else{
					idx=Integer.parseInt(word);
					
					newline=newline+gamaEncode(idx-idx0+1);
					
					idx0=idx;
				}	
			}
			
			bw.write(newline+"\n");
			line=br.readLine();
		}
		
		bw.close();
		br.close();
	}
	
	public static void decode() throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(encodeFilename));
		BufferedWriter bw=new BufferedWriter(new FileWriter(decodeFilename));
		String line;
		line=br.readLine();
		while(line!=null){
			String[] list=line.split(" ");
			String newline=list[0]+" ";
			
			List<Integer> ints=gamaDecode(list[1]);
			
			int idx=0;
			for(int i : ints){
				idx=idx+i-1;
				newline=newline+idx+" ";
			}
			
			bw.write(newline+"\n");
			line=br.readLine();
		}
		
		bw.close();
		br.close();
	}
	
	public static String oneEncode(int n){
		String str="";
		while(n>0){
			str=str+"1";
			n=n-1;
		}
		str=str+"0";
		
		return str;
	}
	
	public static String offsetEncode(int off){
		String str="";
		while(off>1){
			int a=off%2;
			off=off/2;
			str=a+str;
		}
		
		return str;
	}
	
	public static String gamaEncode(int n){
		String offset=offsetEncode(n);
		int L=offset.length();
		String one=oneEncode(L);
		
		return one+offset;
	}
	
	public static List<Integer> gamaDecode(String str){
		List<Integer> list=new ArrayList<Integer>();
		
		int status=0;
		int n=0,offset=0;
		int L=str.length();
		for(int i=0;i<L;i++){
			if(status==0){
				if(str.charAt(i)=='1'){
					n=n+1;
				}
				else{
					status=1;
					offset=1;
				}
			}
			else{
				if(n>0){
					if(str.charAt(i)=='1'){
						offset=offset*2+1;
					}
					else{
						offset=offset*2;
					}
					n=n-1;
					if(n==0){
						list.add(offset);
						status=0;
					}
				}
			}
		}
		
		return list;
	}
}