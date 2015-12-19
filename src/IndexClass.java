public class IndexClass {
	public ArrayList<String,int> word_id_list;
	public ArrayList<String,int> doc_id_list;
	public Map<int,int> word_doc_map;
	
	public static void main (String args[]) {
		System.out.println("hello lucence");
	}
	
	public void run(){
		String[] filenames = new String[]{"./data/shakespeare-merchant.trec.1","./data/shakespeare-merchant.trec.2"};
		for(String filename:filenames){
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String strLine = br.readLine();
			int docId =-1;
			while (strLine != null)
			{
				//lowcase strline ...
				//System.out.println(strLine);
				if (strLine.contains("<DOCNO>"))
				{
					String strTemp = strLine;
					strTemp.replaceAll("<.*?>","");
					
					//if DOCNO exist in doc_id_list, output error information and return docID
					//else add DOCNO to doc_id_list and return doc_id_list.size(); 
					
					docId = AddToDocIdList(strTemp.trim());
					//System.out.println(strDocID);
				}
				else
				{
					// lowcase strTemp;
					String strTemp = strLine;
					strTemp.replaceAll("<.*?>","");
					
					AddToWordIdList(strTemp);
					AddToWordDocMap(strTemp,docId);
				}
				strLine = br.readLine();
			}
			br.close();
		}

		SortWordDocMap();
		Save();
	}
	
	public int AddToDocIdList(String docno)
	{
		if(docno exist in doc_id_list){
			System.out.println("*************** error in addToDocIdList *************");
			return -1;
		}
		else{
			// add docno into doc_id_list
			return doc_id_list.size();
		}
	}
	
	public void AddToDocIdList(String words)
	{
		for(String word : words){
			if(word no in word_id_list){
				// add word into word_id_list
			}
		}
	}
	
	public void AddToWordDocMap(String words,int docId){
		for(String word : words){
			// add to word-doc map
		}
	}
	
	public void SortWordDocMap(){
		Collections.sort(listTerms,new Comparator<InverseTerm>()	
		{
			public int compare(InverseTerm arg0,InverseTerm arg1)
			{
				return arg0.getTerm().compareTo(arg1.getTerm());
			}
		});
	}
	
	public void Save(){
		
	}
}