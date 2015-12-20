import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndexClass {
	//docList: docno - docid
	List<String> docList=new ArrayList<String>();
	long tokenNum=0;
	long termNum=0;
	int docNum=0;
	
	public static void main (String args[]) {
		System.out.println("hello lucence");
	}
	
	public void run() throws IOException{
		String[] filenames = new String[]{"./data/shakespeare-merchant.trec.1","./data/shakespeare-merchant.trec.2"};
		long time1=System.currentTimeMillis();
		long time0=time1;
		
		for(String filename:filenames){
			System.out.println("processing "+filename);
			//termDocMap: term-docid1-docid2-...-docidn
			Map<String,InvertedIndex> termDocMap=new HashMap<String,InvertedIndex>();
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String strLine = br.readLine();
			int docId =-1;
			
			
			while (strLine != null)
			{
				strLine=strLine.toLowerCase().trim();
				//lowcase strline ...
					
				//System.out.println(strLine);
				if (strLine.contains("<docno>"))
				{
					String strTemp = strLine;
					strTemp=strTemp.replaceAll("<.*?>","");
					
					//if DOCNO exist in doc_id_list, output error information and return docID
					//else add DOCNO to doc_id_list and return doc_id_list.size(); 
					
					docId = AddToDocList(strTemp.trim());
					//System.out.println(strDocID);
					long time2=System.currentTimeMillis();
					long usetime=time2-time1;
					time1=time2;
					
					System.out.println("docNum = "+docNum+" this doc run time is "+usetime);
					usetime=time2-time0;
					System.out.println("total run time is "+usetime);
					
					docNum=docNum+1;
				}
				else
				{
					String strTemp = strLine;
					strTemp=strTemp.replaceAll("<.*?>","");
					strTemp=strTemp.replaceAll("[^a-z- ]","");
					strTemp=strTemp.trim();
					if(!strTemp.isEmpty()){
						// lowcase strTemp;
						if(strTemp.compareTo("a")<0){
							System.out.println("..... fuck strTemp is"+strTemp+"...");
						}
						AddToTermDocMap(strTemp,docId,termDocMap);
					}
				}
				strLine = br.readLine();
			}
			br.close();
			
			String outputFileName=filename+".record";
			BufferedWriter bw=new BufferedWriter(new FileWriter(outputFileName));
			
			SaveTermDocMap(bw,termDocMap);
			bw.close();
		}
		
		OutOfCoreSort();
		
		double doclen=tokenNum*1.0/docNum;
		System.out.println("tokenNum ="+tokenNum+" termNum="+termNum+" average doc len="+doclen);
	}
	
	public int AddToDocList(String docno)
	{
		if(docList.contains(docno)){
			System.out.println("*************** contains error in addToDocList *************");
			return -1;
		}
		else{
			// add docno into doc_id_list
			docList.add(docno);
			
//			int idx=docList.indexOf(docno);
//			if(idx!=docList.size()-1){
//				System.out.println("*************** idx error in addToDocList *************");
//				return -1;
//			}
			return docList.size()-1;
		}
	}
	
	
	public void AddToTermDocMap(String line,int docId,Map<String,InvertedIndex> termDocMap){
		String[] words=line.split(" ");
		for(String word : words){
			// add to word-doc map
			word=word.trim();
			if(word.isEmpty()) continue;
			
			tokenNum=tokenNum+1;
			if(termDocMap.containsKey(word)){
				InvertedIndex invidx=termDocMap.get(word);
				if(!invidx.list.contains(docId)){
					invidx.list.add(docId);
					termDocMap.replace(word, invidx);
				}
				
			}
			else{
				termNum=termNum+1;
				InvertedIndex invidx=new InvertedIndex();
				invidx.list.add(docId);
				termDocMap.put(word,invidx);
			}
		}
	}
	
	public void SaveTermDocMap(BufferedWriter bw,Map<String,InvertedIndex> termDocMap) throws IOException{
		Set<String> set=termDocMap.keySet();
		List<String> keys=new ArrayList<String>();
		for(String elm:set){
			keys.add(elm);
		}
		// how to sort the word-doc map ?
		keys.sort(null);
		
		for(String key:keys){
			InvertedIndex invidx=termDocMap.get(key);
			String line=key+" "+invidx.docId2Str();
			bw.write(line+"\n");
		}
	}
	
	public void OutOfCoreSort() throws IOException{
		String docfilename="./output/doc.txt";
		BufferedWriter bw=new BufferedWriter(new FileWriter(docfilename));
		
		int siz=docList.size();
		
		for(int i=0;i<siz;i++){
			bw.write(docList.get(i)+" "+i+"\n");
		}
		bw.close();
		
		String[] filenames = new String[]{"./data/shakespeare-merchant.trec.1",
				"./data/shakespeare-merchant.trec.2"};
		BufferedReader br0=new BufferedReader(new FileReader(filenames[0]+".record"));
		BufferedReader br1=new BufferedReader(new FileReader(filenames[1]+".record"));
		
		String outfilename="./output/wordDoc.txt";
		bw=new BufferedWriter(new FileWriter(outfilename));
		
		
		int sortEnd=0;
		String str0=br0.readLine();
		String str1=br1.readLine();
		
		while(sortEnd!=1){
			if(str0!=null&&str1!=null){
				String[] strs0=str0.split(" ");
				String[] strs1=str1.split(" ");
				int flag=strs0[0].compareTo(strs1[0]);
				
				if(flag==0){
					InvertedIndex invidx0=str2InvertedIndex(str0);
					InvertedIndex invidx1=str2InvertedIndex(str1);
					
					List<Integer> list0=invidx0.list;
					
					for(Integer i : list0){
						if(!invidx1.list.contains(i)){
							invidx1.list.add(i);
						}
					}
					
					invidx1.list.sort(null);
					
					String line=invidx1.key+" "+invidx1.docId2Str();
					bw.write(line+"\n");
					
					str0=br0.readLine();
					str1=br1.readLine();
				}
				else if(flag>0){
					InvertedIndex invidx1=str2InvertedIndex(str1);
					String line=invidx1.key+" "+invidx1.docId2Str();
					bw.write(line+"\n");
					
					str1=br1.readLine();
				}
				else{
					InvertedIndex invidx0=str2InvertedIndex(str0);
					String line=invidx0.key+" "+invidx0.docId2Str();
					bw.write(line+"\n");
					
					str0=br0.readLine();
				}
			}
			else if(str0==null){
				InvertedIndex invidx1=str2InvertedIndex(str1);
				String line=invidx1.key+" "+invidx1.docId2Str();
				bw.write(line+"\n");
				
				str1=br1.readLine();
			}
			else{
				InvertedIndex invidx0=str2InvertedIndex(str0);
				String line=invidx0.key+" "+invidx0.docId2Str();
				bw.write(line+"\n");
				str0=br0.readLine();
			}
			
			if(str0==null&&str1==null){
				sortEnd=1;
			}
		}
		
		bw.close();
		br1.close();
		br0.close();
	}
	
	public InvertedIndex str2InvertedIndex(String line){
		InvertedIndex invidx=new InvertedIndex();
		String[] words=line.split(" ");
		
		int i=0;
		for(String word : words){
			word=word.trim();
			if(word.isEmpty()) continue;
			
			if(i==0){
				invidx.key=word;
				i=i+1;
			}
			else{
				invidx.list.add(Integer.parseInt(word));
			}
		}
		
		return invidx;
	}
}