import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleString {
	
	public String singlestr="";
	public String outstr="";
	
	public String docfilename="./output/wordDoc.txt";
	public String outFilename="./output/singlestr.txt";
	
	public void run() throws IOException{
		//create single string and inverted index term-singlestring list
		List<InvertedIndex> invidxList=loadInvIdxList();
		
		BufferedWriter bw=new BufferedWriter(new FileWriter(outFilename));
		bw.write("singlestr's readable format as follow: "+"\n");
		bw.write(outstr+"\n\n\n");
		
		bw.write("singlestr decode's result as follow: "+"\n");
		
		for(InvertedIndex inv:invidxList){
			int pos=inv.singleStrPoint;
			char ch=singlestr.charAt(pos);
			int len=ch;
			
			String term=singlestr.substring(pos+1, pos+len+1);
			bw.write(term+" tf="+inv.tf+" lineNum="+inv.ivnidxPoint+"\n");
		}
		bw.close();
		
	}
	
	public List<InvertedIndex> loadInvIdxList() throws IOException{
		List<InvertedIndex> list=new ArrayList<InvertedIndex>();
		BufferedReader br=new BufferedReader(new FileReader(docfilename));
		String line=br.readLine();
		
		int wordPos=0;
		int lineNum=0;
		singlestr="";
		outstr="";
		
		while(line!=null){
			String[] words=line.split(" ");
			int strlen=words[0].length();
			char ch=(char) strlen;
			singlestr=singlestr+ch+words[0];
			outstr=outstr+strlen+words[0];
			
			
			int tf=words.length-1;
			
			InvertedIndex inv=new InvertedIndex();
			
			inv.key=words[0];	//save term to check or debug
			
			inv.tf=tf;
			inv.singleStrPoint=wordPos;
			inv.ivnidxPoint=lineNum;
			
			list.add(inv);
			
			wordPos=wordPos+strlen+1;
			lineNum=lineNum+1;
			line=br.readLine();
		}
		
		br.close();
		return list;
	}
	
}
