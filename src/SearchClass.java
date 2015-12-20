import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchClass {
	public String indexfilename="./output/wordDoc.txt";
	public String docfilename="./output/doc.txt";
	public void run(String queryStr) throws IOException{
		SingleString singlestr=new SingleString();
		List<InvertedIndex> invList=singlestr.loadInvIdxList();
		
		HashMap<String,Integer> ht=new HashMap<String,Integer>();
		int idx=0;
		for(InvertedIndex inv:invList){
			//in fact, we need decode the result
			ht.put(inv.key, idx);
			idx=idx+1;
		}
		
		queryStr=queryStr.toLowerCase().replaceAll("[^a-z- ]"," ");
		System.out.println("queryStr="+queryStr);
		String[] words = queryStr.split(" ");
		List<String> strlist=removeStopWords(words,invList,ht);
		List<Integer> lineList=getInvertedIndexPosition(strlist,invList,ht);
		
		List<Integer> results=getMatchedDocId(lineList);
		
		outputResult(results);
	}
	
	public List<Integer> getInvertedIndexPosition(List<String> strlist, List<InvertedIndex> invList,
			HashMap<String, Integer> ht) {
		// TODO Auto-generated method stub
		List<Integer> list=new ArrayList<Integer>();
		for(String word:strlist){
			int idx=ht.get(word);
			int lineNum=invList.get(idx).ivnidxPoint;
			list.add(lineNum);
		}
		return list;
	}

	public List<String> removeStopWords(String[] words,
			List<InvertedIndex> invList,HashMap<String,Integer> ht){
		List<String> strlist=new ArrayList<String>();
		for(String word : words){
			word=word.trim();
			if(word.isEmpty()){
				continue;
			}
			System.out.println("word="+word);
			int idx=ht.get(word);
			InvertedIndex inv=invList.get(idx);
			System.out.println("idx="+idx+" word="+word+" inv.key="+inv.key);
			int tf=inv.tf;
			
			if(word.length()<=5&&tf>20){
				continue;
			}
			
			strlist.add(word);
		}
		return strlist;
	}
	
	public List<Integer> getMatchedDocId(List<Integer> lineList) throws IOException{
		List<Integer> intlist=new ArrayList<Integer>();
		int lineNum=0;
		lineList.sort(null);
		BufferedReader br=new BufferedReader(new FileReader(indexfilename));
		
		String line=br.readLine();
		int N=lineList.size();
		int max=lineList.get(N-1);
		int current=0;
		while(line!=null){
			if(lineList.contains(lineNum)){
				List<Integer> docIdList=new ArrayList<Integer>();
				String[] words=line.split(" ");
				int i=0;
				for(String word:words){
					if(i>0){
						int docid=Integer.parseInt(word);
						docIdList.add(docid);
					}
					i=i+1;
				}
				
				if(current==0){
					intlist.addAll(docIdList);
				}
				else{
					//intlist = intlist and docIdList
					intlist.retainAll(docIdList);
				}
				current=current+1;
			}
			
			line=br.readLine();
			lineNum=lineNum+1;
			if(lineNum>max){
				break;
			}
		}
		return intlist;
	}
	
	public void outputResult(List<Integer> results) throws IOException{
		System.out.println("find correspondent docid as follow: ");
		System.out.println(results);
		
		//find correspondent doc and output it!
		BufferedReader br=new BufferedReader(new FileReader(docfilename));
		
		String line=br.readLine();
		
		//lineNum = docid
		int lineNum=0;
		while(line!=null){
			if(results.contains(lineNum)){
				System.out.println(line);
			}
			
			line=br.readLine();
			lineNum=lineNum+1;
		}
		
		br.close();
	}
}