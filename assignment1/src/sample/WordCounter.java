package sample;
import java.io.*;
import java.util.*;


public class WordCounter{
	
	private Map<String, Integer> wordCounts;
	List<String> arr = new ArrayList<>();
	int totalFiles = 0; 
	
	public WordCounter(){
		wordCounts = new TreeMap<>();
	}
	
	public void parseFile(File file) throws IOException{
		//System.out.println("Starting parsing the file:" + file.getAbsolutePath());
		arr.clear();
		totalFiles++;
		
		if(file.isDirectory()){
			//parse each file inside the directory
			File[] content = file.listFiles();
			for(File current: content){
				parseFile(current);
			}
		}else{
			Scanner scanner = new Scanner(file);
			// scanning token by token
			while (scanner.hasNext()){
				String token = scanner.next();
				if (isValidWord(token)){
					countWord(token);
				}
			}	
		}		
		
	}
	
	private boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return word.matches(allLetters);
			
	}
	
	private void countWord(String word){
		if (arr.contains(word))
		{
			return;
		}
		if(wordCounts.containsKey(word)){
			int previous = wordCounts.get(word);
			wordCounts.put(word, previous+1);
			arr.add(word);
		}else{
			wordCounts.put(word, 1);
			arr.add(word);
		}
	}
	
	public void outputWordCount(int minCount, File output) throws IOException{
		System.out.println("Saving file to word occurrance counts to file:" + output.getAbsolutePath());
		System.out.println("Total files:" + totalFiles);
		
		if (!output.exists()){
			output.createNewFile();
		}
		if (output.canWrite()){
				PrintWriter fileOutput = new PrintWriter(output);
				
				Set<String> keys = wordCounts.keySet();
				Iterator<String> keyIterator = keys.iterator();
				
				while(keyIterator.hasNext()){
					String key = keyIterator.next();
					int count = wordCounts.get(key);
					// testing minimum number of occurances
					if(count>=minCount){					
						fileOutput.println(key + ": " + count);
					}
				}
				
				fileOutput.close();
			}
		
	}
	
	//main method
	public static void main(String[] args) {
		
		if(args.length < 2){
			System.err.println("Usage: java WordCounter <inputDir> <outfile>");
			System.exit(0);
		}
		
		File dataDirHam1 = new File(args[0]);
		File dataDirHam2 = new File(args[1]);
		File outFileHam = new File(args[2]);
		File dataDirSpam = new File(args[3]);
		File outFileSpam = new File(args[4]);		
		
		WordCounter wordCounter = new WordCounter();
		System.out.println("Hello");
		try{
			wordCounter.parseFile(dataDirHam1);
			wordCounter.outputWordCount(2, outFileHam);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + dataDirHam1.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		WordCounter wordCounter2 = new WordCounter();
		try{
			wordCounter2.parseFile(dataDirHam2);
			wordCounter2.outputWordCount(2, outFileHam);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + dataDirHam2.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		WordCounter wordCounter3 = new WordCounter();
		try{
			wordCounter3.parseFile(dataDirSpam);
			wordCounter3.outputWordCount(2, outFileSpam);
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir: " + dataDirSpam.getAbsolutePath());
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
}