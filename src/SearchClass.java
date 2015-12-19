public class SearchClass {
	
	public static void main (String args[]) {
		System.out.println("hello lucence");
	}
	
	public void run(String queryStr){
		String[] words = queryStr.split(" ");
		words=removeStopWords(words);
		
		int[] results;
		for(String word : words){
			int[] docids=getMatchedDocId(word);
			//add docids to results!
			
		}
		
		outputResult(results);
	}
	
	public String[] removeStopWords(String[] words){
		
	}
	
	public int[] getMatchedDocId(String word){
		
	}
	
	public void outputResult(int[] results){
		
	}
}