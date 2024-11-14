# import package ...
import sys
import gzip
import json
from typing import Dict, List
import math
import re

def totDoc(corpus_list):
    docs = []
    for items in corpus_list:
        docs.append(items.get("storyID"))
    return docs    

def getDocLen(corpus_list, doc_id):

    for item in corpus_list:

        if item.get("storyID") == doc_id:

            return len(item.get("text", ""))
    
    return 0

def get_average_document_length(corpus_list):
    total_words = 0
    num_ids = 0
    for item in corpus_list:
        text = item.get("text", "")
        words = text.split()
        word_count = len(words)
       
        
        total_words += word_count
        num_ids += 1
    
    average_length = total_words / num_ids if num_ids > 0 else 0
    
    return average_length

def getCtf(inverted_index, word):
        
        ctf_raw = 0
        for doc_id in inverted_index[word]:               
            ctf_raw += inverted_index[word][doc_id]['tf']
        ctf_raw
        return ctf_raw  

def calculate_idf(term, inverted_index, num_documents):
    doc_freq = len(inverted_index.get(term, {}))
    idf = math.log((num_documents - doc_freq + 0.5) / (doc_freq + 0.5))
    return idf  

def buildIndex(inputFile):
    # Your function start here ...
    
    with gzip.open(inputFile, 'r') as f:

        data_dict = json.load(f)
        

    corpus_list = data_dict['corpus']
    inverted_index = {}
    numTerm = 0
    
    for d in corpus_list:
       
        text = d['text']
    
        words = [word for word in re.split(r'\s+', text) if word]
        numTerm += len(words)

        for i, word in enumerate(words):
        
            if word not in inverted_index:
                inverted_index[word] = {d['storyID']: {'pos': [i+1], 'tf': 1}}
        
            else:
        
                if d['storyID'] not in inverted_index[word]:
                    inverted_index[word][d['storyID']] ={'pos':[i+1], 'tf': 1}                     
        
                else:
                    inverted_index[word][d['storyID']]['pos'].append(i+1)
                    inverted_index[word][d['storyID']]['tf'] += 1
    
    return inverted_index, corpus_list, numTerm


def runQueries(index, corpus_list, totNum, queriesFile, outputFile):
    spec = set()
    tsv_data = {}
    with open(queriesFile, 'r') as f_in:
        for line in f_in:
                parts = line.split('\t')
                query_type = parts[0]
                query_name = parts[1]
                query_texts = "\t".join(parts[2:])
                
                
                tsv_data[query_name] = {query_type: query_texts}

    
    with open(outputFile,'w') as f_out:
        for query_name in tsv_data:
           for query_type in tsv_data[query_name]:
               if query_type == "or":
                i = 1
                out_results = or_query(index, tsv_data[query_name][query_type])
                for doc_id in out_results:
                        f_out.write(query_name +'\t' + 'skip '+ doc_id.ljust(22) + str(i) + " 1.0000 "+ "lyerramsetty" +"\n")
                        i+=1
                
               elif query_type == 'ql':
                   
                   i = 1
                   out_results = ql_query(index, tsv_data[query_name][query_type], corpus_list, totNum)
                   out = dict(sorted(out_results.items(), key = lambda item: item[1], reverse = True))
                   for doc_id in out:
                        f_out.write(query_name +'\t' +'skip'+'\t' + doc_id +'\t'+ str(i)+'\t' + "{:.4f}".format(out[doc_id])+'\t'+"lyerramsetty"+"\n")
                        i+=1      
               elif query_type == 'bm25':
                   i = 1
                   out_results = bm25_query(index, tsv_data[query_name][query_type], corpus_list)
                   out = dict(sorted(out_results.items(), key = lambda x: x[1], reverse = True))
                   for doc_id in out:
                        f_out.write(query_name +'\t' + 'skip'+ '\t' +doc_id + '\t' +str(i)+'\t'  + "{:.4f}".format(out[doc_id])+ '\t' +"lyerramsetty"+"\n")
                        i+=1
               elif query_type == 'and':
                   i = 1
                   out_results = and_query(index, tsv_data[query_name][query_type])
                   for doc_id in out_results:
                        f_out.write(query_name +'\t' + 'skip'+ '\t' +doc_id +'\t' + str(i) + '\t' +"1.0000"+ '\t' +"lyerramsetty"+"\n")
                        i+=1       

    print(len(spec))                     
    return 1

def and_query(index, query_texts):
    out_queries = []
    query_count = {}
    query_terms = query_texts.split('\t')
    for term in query_terms:
        if term in index.keys():
            for id in index[term]:
                out_queries.append(id)
    for term in out_queries:
        for id in out_queries:
            if id in query_count:
                query_count[id] += 1
            else:
                query_count[id] = 1

    out_id = []
    for id in query_count:
        if query_count[id] > 1:
            out_id.append(id)
            
    out_id.sort()
    return out_id

def or_query(index, query_texts):
    query_terms = query_texts.split()
    result_docs = set()
    
    for term in query_terms:
        if term in index:
            doc_positions = index[term]
            doc_ids = set(doc_positions.keys())
            
            result_docs.update(doc_ids)
    
    return result_docs
   
    

def ql_query(index, query_texts, corpus_list, totNum):
    mu = 300
    results = {}
    query_terms = query_texts.split('\t')
    docs = totDoc(corpus_list)
    
    for doc_id in docs:
            score = 0
            doc_len = getDocLen(corpus_list, doc_id)
            for term in query_terms:
                if term in index and doc_id in index[term]: 
                    tf = index[term][doc_id]['tf']
                    ctf =  getCtf(index, term)
                    score += ql_calc(tf,doc_len, ctf, totNum, mu)      
                    results[doc_id] = score
                    
    return results

def ql_calc(tf,doc_len, ctf, totNum, mu):
    a = mu/(doc_len + mu)
    score = math.log(((1-a)*(tf/doc_len)) + (a)*(ctf/totNum))
    return score

def bm25_query(index, query_texts, corpus_list):
    query_terms = query_texts.split('\t')
    results = {}
    idf_values = {}
    k1 = 1.8
    b = 0.75
    k2 = 5
    docs = totDoc(corpus_list)
    avg_doc_len = get_average_document_length(corpus_list)
    for doc_id in docs:
        score = 0
        doc_len = getDocLen(corpus_list, doc_id)
        for term in query_terms:
            if term in index and doc_id in index[term]:
                tf = index[term][doc_id]['tf']
                ctf = getCtf(index, term)
                idf_values[term] = math.log((len(index) - tf + 0.5) / (tf + 0.5))
                score += bm_calc(doc_len,tf, ctf, k1, b, k2 ,idf_values[term], avg_doc_len)
                results[doc_id] = score
    
    return results            

def bm_calc(doc_len, tf,ctf, k1, b, k2, idf, avg_doc):
    k = k1*((1-b)+ (b*(doc_len/avg_doc)))
    result = math.log(idf * (((k1 + 1)*tf)/(k + tf)) * (((k2+1)*ctf)/(k2+ctf)) )
    return result





if __name__ == '__main__':
    # Read arguments from command line, or use sane defaults for IDE.
    argv_len = len(sys.argv)
    inputFile = sys.argv[1] if argv_len >= 2 else "sciam.json.gz"
    queriesFile = sys.argv[2] if argv_len >= 3 else "P3train.tsv"
    outputFile = sys.argv[3] if argv_len >= 4 else "P3train.trecrun"

    index, corpus_list, totNum = buildIndex(inputFile)
    if queriesFile == 'showIndex':
        # Invoke your debug function here (Optional)
            print(1) 
    elif queriesFile == 'showTerms':
        # Invoke your debug function here (Optional)
            print(1)
  #  else:
    runQueries(index, corpus_list, totNum ,queriesFile, outputFile)

    # Feel free to change anything
