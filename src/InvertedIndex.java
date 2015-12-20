import java.util.ArrayList;
import java.util.List;

public class InvertedIndex {
	public List<Integer> list=new ArrayList<Integer>();
	
	//follow only use in IndexClass.str2InvertedIndex, save the word
	public String key;
	public String docId2Str(){
		String line="";
		for(Integer i:list){
			line=line+i+" ";
		}
		
		return line;
	}
	
	//follows only used with SingleString
	public int tf;	//term frequency
	public int ivnidxPoint;	//inverted index's postion in file. given as line number
	public int singleStrPoint;	//single string position
}
