
//import things
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;


public class tokens {

	// The stopword list hardcoded for you
	static String allStopWords[] = { "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
			"has", "he", "in", "is", "it", "its", "of", "on", "that", "the", "to",
			"was", "were", "with" };
	static String[] vowels = { "a", "e", "i", "o", "u", "y" };

	public static ArrayList<String> porterStem(ArrayList<String> tokens){
		for (int i = 0; i < tokens.size(); i++) {
			String str = tokens.get(i);
			if (str.endsWith("sses")) {
				tokens.set(i, str.substring(0, str.length() - 2));
			} else if (str.endsWith("ies") || str.endsWith("ied")) {
				if (str.length() > 4) {
					tokens.set(i, str.substring(0, str.length() - 3) + "i");
				} else {
					tokens.set(i, str.substring(0, str.length() - 1));
				}
			} else if (str.endsWith("us") || str.endsWith("ss")) {
			} else if (str.endsWith("s")) {
				for (String vowel : vowels) {
					if (str.substring(0, str.length() - 2).contains(vowel)) {
						tokens.set(i, str.substring(0, str.length() - 1));
					}
				}
			} else if (str.endsWith("eed")) {
				for (String vowel : vowels) {
					if (str.startsWith(vowel)) {
						tokens.set(i, str.substring(0, str.length() - 1));
					}
				}
			} else if (str.endsWith("eedly")) {
				for (String vowel : vowels) {
					if (str.startsWith(vowel)) {
						tokens.set(i, str.substring(0, str.length() - 3));
					}
				}
			} else if (str.endsWith("ed") && !str.endsWith("eed")) {
				for (String vowel : vowels) {
					if (str.substring(0, str.length() - 2).contains(vowel)) {
						if (str.substring(str.length() - 4, str.length() - 2).equals("ed")) {
							tokens.set(i, str.substring(0, str.length() - 1));
						}
						else {
							tokens.set(i, str.substring(0, str.length() - 2));
						}
					}
				}
				String stem = tokens.get(i);
				if (stem.endsWith("at") || stem.endsWith("bl") || stem.endsWith("iz")) {
					tokens.set(i, stem + "e");
				} else if (stem.endsWith("bb") || stem.endsWith("dd") || stem.endsWith("ff")
						|| stem.endsWith("gg") || stem.endsWith("mm") || stem.endsWith("nn")
						|| stem.endsWith("pp") || stem.endsWith("rr") || stem.endsWith("tt")) {
					tokens.set(i, stem.substring(0, stem.length() - 1));
				}
				for (String vowel : vowels) {
					if (stem.startsWith(vowel) && !stem.endsWith(vowel) && (stem.length() == 2)) {
						tokens.set(i, stem + "e");
					} else if ((!stem.startsWith(vowel)) && String.valueOf(stem.charAt(1)).equals(vowel)
							&& (!stem.endsWith(vowel)) && (!stem.endsWith("x")) && (!stem.endsWith("w")) && stem.length()==3) {
						tokens.set(i, stem + "e");
					}
				}
			} else if (str.endsWith("edly")) {
				for (String vowel : vowels) {
					if (str.substring(0, str.length() - 4).contains(vowel)) {
						tokens.set(i, str.substring(0, str.length() - 4));
					}
				}
				String stem = tokens.get(i);
				if (stem.endsWith("at") || stem.endsWith("bl") || stem.endsWith("iz")) {
					tokens.set(i, stem + "e");
				} else if (stem.endsWith("bb") || stem.endsWith("dd") || stem.endsWith("ff")
						|| stem.endsWith("gg") || stem.endsWith("mm") || stem.endsWith("nn")
						|| stem.endsWith("pp") || stem.endsWith("rr") || stem.endsWith("tt")) {
					
					tokens.set(i,stem.substring(0,stem.length()-1));
				}
				for (String vowel : vowels) {
					if (stem.startsWith(vowel) && !stem.endsWith(vowel) && (stem.length() == 2)) {
						tokens.set(i, stem + "e");
					} else if ((!stem.startsWith(vowel)) && String.valueOf(stem.charAt(1)).equals(vowel)
							&& (!stem.endsWith(vowel)) && (!stem.endsWith("x")) && (!stem.endsWith("w"))
							&& (stem.length() == 3)) {
						tokens.set(i, stem + "e");
					}
				}
			} else if (str.endsWith("ing")) {
				for (String vowel : vowels) {
					if (str.substring(0, str.length() - 3).contains(vowel)) {
						tokens.set(i, str.substring(0, str.length() - 3));
					}
				}
				String stem = tokens.get(i);
				if (stem.endsWith("at") || stem.endsWith("bl") || stem.endsWith("iz")) {
					tokens.set(i, stem + "e");
				} else if (stem.endsWith("bb") || stem.endsWith("dd") || stem.endsWith("ff")
						|| stem.endsWith("gg") || stem.endsWith("mm") || stem.endsWith("nn")
						|| stem.endsWith("pp") || stem.endsWith("rr") || stem.endsWith("tt")) {
					tokens.set(i, stem.substring(0, stem.length() - 1));
				}
				for (String vowel : vowels) {
					if (String.valueOf(stem.charAt(0)).equals(vowel) && !stem.endsWith(vowel)
							&& (stem.length() == 2)) {
						tokens.set(i, stem + "e");
					} else if ((!String.valueOf(stem.charAt(0)).equals(vowel))
							&& String.valueOf(stem.charAt(1)).equals(vowel) && (!stem.endsWith(vowel))
							&& (!stem.endsWith("x")) && (!stem.endsWith("w")) && (stem.length()==3) ) {
						tokens.set(i, stem + "e");
					}
				}
			} else if (str.endsWith("ingly")) {
				for (String vowel : vowels) {
					if ((str.substring(0, str.length() - 5).contains(vowel))) {
						
						tokens.set(i, str.substring(0, str.length() - 5));
						String stem = tokens.get(i);
						if (stem.endsWith("at") || stem.endsWith("bl") || stem.endsWith("iz") ) {
							tokens.set(i, stem + "e");
						} else if (stem.endsWith("bb") || stem.endsWith("dd") || stem.endsWith("ff")
								|| stem.endsWith("gg") || stem.endsWith("mm") || stem.endsWith("nn")
								|| stem.endsWith("pp") || stem.endsWith("rr") || stem.endsWith("tt")) {
							tokens.set(i, stem.substring(0, stem.length() - 1));
						}
						//tokens.set(i, str.substring(0, str.length() - 5));
					} else if (str.endsWith("y")) {
						if (!(str.substring(0, str.length() - 1).contains(vowel)) && (str.length()-5 > 2)) {
							tokens.set(i, str.substring(0, str.length() - 5) + "i");
						}
						else {
							tokens.set(i, str.substring(0, str.length() - 5) + "e");
						}
					}
				}
				String stem = tokens.get(i);
				if (stem.endsWith("at") || stem.endsWith("bl") || stem.endsWith("iz")) {
					tokens.set(i, stem + "e");
				} else if (stem.endsWith("bb") || stem.endsWith("dd") || stem.endsWith("ff")
						|| stem.endsWith("gg") || stem.endsWith("mm") || stem.endsWith("nn")
						|| stem.endsWith("pp") || stem.endsWith("rr") || stem.endsWith("tt")) {
					tokens.set(i, stem.substring(0, stem.length() - 1));
				}
				for (String vowel : vowels) {
					if (stem.startsWith(vowel) && !stem.endsWith(vowel) && (stem.length() == 2)) {
						tokens.set(i, stem + "e");
					} else if ((!stem.startsWith(vowel)) && String.valueOf(stem.charAt(1)).equals(vowel)
							&& (!stem.endsWith(vowel)) && (!stem.endsWith("x")) && (!stem.endsWith("w")) && stem.length()>=3) {
						tokens.set(i, stem + "e");
					}
				}
			}
			str = tokens.get(i);			
			if(str.endsWith("y") && (str.length() >= 3)) { 
				int match = 0;
				for(String vowel : vowels){
					if (String.valueOf(str.charAt(str.length()-2)).equals(vowel)) {	
						match++;
					}
				}
				if (match == 0) {
					tokens.set(i,str.substring(0,str.length()-1)+"i");
				}											
			}
		}
		return tokens;
	}

	static ArrayList<String> textFileProcess(String inputZipFile) throws FileNotFoundException, IOException {
		String gzipFilePath = inputZipFile;
		ArrayList<String> tokens = new ArrayList<>();
		try (GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(gzipFilePath));
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
				) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] words = line.split("\\s+");
				for (int i = 0; i < words.length; i++) {
					tokens.add(words[i]);
				}
	    
        	}
		}
		tokens.removeIf(str -> str.trim().isEmpty());
	    return tokens;
	}
	static ArrayList<String> fancyTokenizer(ArrayList<String> tokens){
	   
		ArrayList<String> fancyTokens = new ArrayList<>();
		 for (int i = 0; i < tokens.size(); i++) {
				 if (tokens.get(i).startsWith("https://") || tokens.get(i).startsWith("http://")) {
					 if(tokens.get(i).endsWith(")")){
						 String url = tokens.get(i).substring(0,tokens.get(i).length()-1);
						 tokens.set(i, url);
					 }
				 }
 
				 else {
					 String lcElem = tokens.get(i);
					 String lowercaseElement = lcElem.toLowerCase();
					 tokens.set(i, lowercaseElement);
					 String waElem = tokens.get(i);
					 String withoutApostrophes = waElem.replaceAll("'", "");
					 tokens.set(i, withoutApostrophes);
					 String wpsElem = tokens.get(i);
					 String withoutPeriodsSpace = wpsElem.replaceAll("\\.(?!\\d)", "");
					 tokens.set(i, withoutPeriodsSpace);
					 String wsElem = tokens.get(i);
					 String withoutSpecial = wsElem.replaceAll("[^\\-\\+\\w.]", " ");
					 tokens.set(i, withoutSpecial);
				 }
			 }
 
			 for (int i = 0; i < tokens.size(); ++i) {
 
				 if (tokens.get(i).contains("-") && !tokens.get(i).matches(".*[-+]?\\d*\\.?\\d+")) {
					 String[] hyphenWords = tokens.get(i).split("-");
					 String concatWord = "";
					 for (int j = 0; j < hyphenWords.length; ++j) {
						 fancyTokens.add(hyphenWords[j]);
						 concatWord += hyphenWords[j];
					 }
					 fancyTokens.add(concatWord);
				 } else if (tokens.get(i).matches("\\s+")) {
					 String[] spaceWords = tokens.get(i).split("\\s+");
					 for (int j = 0; j < spaceWords.length; ++j) {
						 fancyTokens.add(spaceWords[j].trim());
 
					 }
				 } else {
					 fancyTokens.add(tokens.get(i));
				 }
			 }
 
			 for (int i = 0; i < fancyTokens.size(); ++i) {
				 String str = fancyTokens.get(i).trim();
				 fancyTokens.set(i, str);
			 }
		 return fancyTokens;
		 }
 
		 static ArrayList<String> stopWord(ArrayList<String> tokens){
			for (int i = 0; i < tokens.size(); i++) {
							String s = tokens.get(i);
							for (String word : allStopWords) {
								if (s.equals(word)) {
									s = s.replace(word, ""); 
								}
							}
							tokens.set(i, s);
						}
				return tokens;
			}


	static void statWrite(ArrayList<String> tokens, String outPrefix){
		tokens.removeIf(str -> str.trim().isEmpty());
		String statFilePath = outPrefix + "-stats.txt";
		try (FileWriter statWriter = new FileWriter(statFilePath)) {
			statWriter.write(tokens.size()+"\n");
			Set<String> noDups = new LinkedHashSet<>();
			noDups.addAll(tokens);
			System.out.println(noDups.size());
			statWriter.write(noDups.size()+"\n");
			Map<String, Long> counts = tokens.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

       			 counts.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.limit(100)
                .forEach(entry -> {
					try {
						statWriter.write(entry.getKey() + " " + entry.getValue()+"\n");
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				});
				statWriter.close();
		
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	static void heapWrite(ArrayList<String> tokens, String outPrefix){
		
		tokens.removeIf(str -> str.trim().isEmpty());
		String heapFilePath = outPrefix + "-heaps.txt";
		try (FileWriter heapWriter = new FileWriter(heapFilePath)){
			Set<String> noDup = new LinkedHashSet<>();
			
			for(int i = 0; i < tokens.size(); ++i){
				if((i % 10 == 0 && i != 0)){
					heapWriter.write(i + " " + noDup.size() + "\n");
					}
					noDup.add(tokens.get(i));
				if( i ==(tokens.size()-1)){
					heapWriter.write((i+1) + " " + noDup.size() + "\n");
				}	
			}
			heapWriter.close();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}	
	}

	static void tokenWrite(String inputZipFile,ArrayList<String> tokens, String outPrefix) throws FileNotFoundException, IOException{
		String gzipFilePath = inputZipFile;
		ArrayList<String> origTokens = new ArrayList<>();
		String tokenFilePath = outPrefix +"-tokens.txt";
		try (GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(gzipFilePath));
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				) {
			String line;

			while ((line = reader.readLine()) != null) {
				String[] words = line.split("\\s+");
				for (int i = 0; i < words.length; i++) {
					origTokens.add(words[i]);
					}
				}
				origTokens.removeIf(str->str.trim().isEmpty());

			}
			
		try (FileWriter tokenWriter = new FileWriter(tokenFilePath)){
			for(int i = 0; i < origTokens.size(); i++){
				tokenWriter.write(origTokens.get(i)+ " "+ tokens.get(i) +"\n");
					}
					tokenWriter.close();
				}
		catch(IOException e){
			e.printStackTrace();
		}

		}
		
		
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Read arguments from command line; or use sane defaults for IDE.
		String inputZipFile = args.length >= 1 ? args[0] : "P1-train.gz";
		String outPrefix = args.length >= 2 ? args[1] : "outPrefix";
		String tokenize_type = args.length >= 3 ? args[2] : "spaces";
		String stoplist_type = args.length >= 4 ? args[3] : "yesStop";
		String stemming_type = args.length >= 5 ? args[4] : "";
		ArrayList<String> tokens = textFileProcess(inputZipFile);
		ArrayList<String> fakeTokens = new ArrayList<>();
		if(tokenize_type.equals("fancy")){
			fakeTokens = fancyTokenizer(tokens);
			tokens = fakeTokens;
		}

		if(stoplist_type.equals("yesStop")){
			fakeTokens = stopWord(tokens);
			tokens = fakeTokens;
		}
	
		if(stemming_type.equals("porterStem")){
			fakeTokens = porterStem(tokens);
			tokens = fakeTokens;
		}
	/*	for(String token: tokens){
			System.out.println(token);
		}
		*/
		tokenWrite(inputZipFile, tokens, outPrefix);
		statWrite(tokens, outPrefix);
		heapWrite(tokens, outPrefix);
		
	}
}