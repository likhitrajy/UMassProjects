# import package ...
import sys
import math



def countRelDocs(qrels_data):
    relevant_docs_count = {}
    for query_name in qrels_data:
        for doc_id in qrels_data[query_name]:
         relevant_docs_count[query_name] = sum(1 for doc_id in qrels_data[query_name] if qrels_data[query_name][doc_id] > 0)
    return relevant_docs_count       

def countRelDocsTrec(trecrun_data, qrels_data):
    relFound = {}
    for query_name in trecrun_data: 
       relFound[query_name] = 0
       for doc_id in trecrun_data[query_name]:
          if doc_id in qrels_data[query_name]:
             if qrels_data[query_name][doc_id] > 0:
              relFound[query_name] = 1 if query_name not in relFound else relFound[query_name] + 1
             
          
          
    return relFound             

def calculate_ndcg(trecrun, qrels):
    iDCG = {}
    NDCGrt = {}
    NDCG = {}
    rel = {}
    with open(trecrun, 'r') as run:
       with open(qrels, 'r') as qrel:   
        for line in qrel:
         rel[line.split()[0] + '_' + line.split()[2]] = int(line.split()[3])
         iDCG[line.split()[0]] = [int(line.split()[3])] if line.split()[0] not in iDCG else iDCG[line.split()[0]] + [int(line.split()[3])]
     
        for line in run:
         if (20 >= int(line.split()[3])):
            if(line.split()[0] + '_' + line.split()[2] in rel):
                if(line.split()[0] not in NDCGrt):
                    NDCGrt[line.split()[0]] = rel[line.split()[0] + '_' + line.split()[2]]  
                else:
                    NDCGrt[line.split()[0]] = NDCGrt[line.split()[0]]+rel[line.split()[0] + '_' + line.split()[2]] / math.log(int(line.split()[3]), 2)
        for query_name in iDCG.keys():
         DCG = sorted(iDCG[query_name], reverse=True)[:20]
         dcg = DCG[0] + sum([DCG[i] / math.log(i + 1, 2) for i in range(1, len(DCG))])
         NDCG[query_name] = 0 if query_name not in NDCGrt or dcg == 0 else NDCGrt[query_name] / dcg

    
    return NDCG  


def calculate_rr(trecrun_data, qrels_data):
    rr = {}
    for query_name in trecrun_data:
     rr[query_name] = 0
     for doc_id in trecrun_data[query_name]:
          if doc_id in qrels_data[query_name]: 
             if qrels_data[query_name][doc_id] > 0:        
              rr[query_name] = 1 / int(trecrun_data[query_name][doc_id]['rank'])
              break 
    return rr

def calculate_p10(trecrun_data, qrels_data):
    p_at_10 = {}
    for query_name in trecrun_data:
        ranked_docs = [doc_id for doc_id in trecrun_data[query_name] if trecrun_data[query_name][doc_id]['rank'] <= 10]
        relevant_docs = [doc_id for doc_id in qrels_data[query_name] if qrels_data[query_name][doc_id] > 0]
        p_at_10[query_name] = len(set(ranked_docs).intersection(set(relevant_docs))) / 10 if len(ranked_docs) >= 10 else 0
    return p_at_10

def calculate_r10(trecrun_data, qrels_data):
    R_at_10 = {}
    for query_name in trecrun_data:
        ranked_docs = [doc_id for doc_id in trecrun_data[query_name] if trecrun_data[query_name][doc_id]['rank'] <= 10]
        relevant_docs = [doc_id for doc_id in qrels_data[query_name] if qrels_data[query_name][doc_id] > 0]
        R_at_10[query_name] =  len(set(ranked_docs).intersection(set(relevant_docs))) / len(relevant_docs) if len(relevant_docs) != 0 else 0
    return R_at_10

def calculate_ap(trecrun_data, qrels_data):
    ap = {}
    
    for query_name in trecrun_data:
        if query_name not in qrels_data:
            continue
        
        relevant_docs = [doc_id for doc_id in qrels_data[query_name] if qrels_data[query_name][doc_id] > 0]
        ranked_docs = [(doc_id, trecrun_data[query_name][doc_id]['rank']) for doc_id in trecrun_data[query_name]]
        ranked_docs = sorted(ranked_docs, key=lambda x: x[1])
        
        precision_vals = []
        recall_vals = []
        num_relevant_found = 0
        
        for i, (doc_id, rank) in enumerate(ranked_docs):
            if doc_id in relevant_docs:
                num_relevant_found += 1
            
            precision = num_relevant_found / (i + 1)
            recall = num_relevant_found / len(relevant_docs) if len(relevant_docs) != 0 else 0
            
            precision_vals.append(precision)
            recall_vals.append(recall)
        
        ap[query_name] = 0
        
        for i in range(len(ranked_docs)):
            if ranked_docs[i][0] in relevant_docs:
                ap[query_name] += precision_vals[i] / len(relevant_docs)
        
    return ap

def calculate_avg(values, unique_queries):
    total = 0.0
    for query_name in values:
       total += values[query_name]
    return total/(len(unique_queries))
###################################
def eval(trecrun, qrels, out):
    trecrun_data = {}

    with open(trecrun, 'r') as f:
     for line in f:
        trecrun_stats = line.strip().split()
        query_name = trecrun_stats[0]
        doc_id = trecrun_stats[2]
        rank = int(trecrun_stats[3])
        score = float(trecrun_stats[4])
        if query_name not in trecrun_data:
            trecrun_data[query_name] = {}
     
        trecrun_data[query_name][doc_id] = {
            'rank': rank,
            'score': score
        }   
    
    qrels_data = {}

    with open(qrels, 'r') as f:
     for line in f:
        qrels_stats = line.strip().split()
        query_name = qrels_stats[0]
        doc_id = qrels_stats[2]
        relevance = int(qrels_stats[3])
        
        if query_name not in qrels_data:
            qrels_data[query_name] = {}
        
        qrels_data[query_name][doc_id] = relevance
    
    numRel = countRelDocs(qrels_data)
    relFound = countRelDocsTrec(trecrun_data, qrels_data)
    NDCG = calculate_ndcg(trecrun, qrels) 
    P10 = calculate_p10(trecrun_data, qrels_data)
    R10 = calculate_r10(trecrun_data, qrels_data)
    RR = calculate_rr(trecrun_data, qrels_data)
    F10 = {}
    for query_name in P10:
       if P10[query_name] != 0 or R10[query_name] != 0:
         F10[query_name] = (2 * P10[query_name] * R10[query_name])/(P10[query_name] + R10[query_name])
       else:
          F10[query_name] = 0
    AP = calculate_ap(trecrun_data, qrels_data)
    

     
    unique_queries = []
    for query_name in trecrun_data:
     if query_name not in unique_queries:
        unique_queries.append(query_name)

    total_numRel = 0
    total_relFound = 0
    for query_name in numRel:
     total_numRel += (numRel[query_name])

    for query_name in relFound:
     total_relFound += (relFound[query_name])
    
    total_NDCG = calculate_avg(NDCG,unique_queries)
    total_P10 = calculate_avg(P10,unique_queries)
    total_R10 = calculate_avg(R10,unique_queries)
    MRR = calculate_avg(RR,unique_queries)
    total_F10 = calculate_avg(F10,unique_queries)
    MAP = calculate_avg(AP,unique_queries) 
    with open(out, 'w') as o:
       for query_name in unique_queries:
          o.write(   'NDCG@20  '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(NDCG[query_name])) + '\n'
                     'numRel   '.ljust(9) + str(query_name).ljust(9) + str(numRel[query_name]) + '\n'
                     'relFound '.ljust(9) + str(query_name).ljust(9) + str(relFound[query_name]) + '\n'
                     'RR       '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(RR[query_name])) + '\n'
                     'P@10     '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(P10[query_name])) + '\n'
                     'R@10     '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(R10[query_name])) + '\n'
                     'F1@10    '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(F10[query_name])) + '\n'
                     'P@20%    '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(P10[query_name])) + '\n'
                     'AP       '.ljust(9) + str(query_name).ljust(9) + str('{:.4f}'.format(AP[query_name])) + '\n')

       o.write(      'NDCG@20  '.ljust(9) + 'all      ' + str('{:.4f}'.format(total_NDCG)) + '\n'
                     'numRel   '.ljust(9) + 'all      ' + str(total_numRel) + '\n'
                     'relFound '.ljust(9) + 'all      ' + str(total_relFound) + '\n'
                     'MRR      '.ljust(9) + 'all      ' + str('{:.4f}'.format(MRR)) + '\n'
                     'P@10     '.ljust(9) + 'all      ' + str('{:.4f}'.format(total_P10)) + '\n'
                     'R@10     '.ljust(9) + 'all      ' + str('{:.4f}'.format(total_R10)) + '\n'
                     'F1@10    '.ljust(9) + 'all      ' + str('{:.4f}'.format(total_F10)) + '\n'
                     'P@20%    '.ljust(9) + 'all      ' + str('{:.4f}'.format(total_P10)) + '\n'
                     'MAP      '.ljust(9) + 'all      ' + str('{:.4f}'.format(MAP)) + '\n' )      
     
    
if __name__ == '__main__':
    argv_len = len(sys.argv)
    runFile = sys.argv[1] if argv_len >= 2 else "msmarcofull-ql.trecrun"
    qrelsFile = sys.argv[2] if argv_len >= 3 else "msmarco.qrels"
    outputFile = sys.argv[3] if argv_len >= 4 else "ql.eval"
   
    eval(runFile, qrelsFile, outputFile)
    
   # calculate_ndcg(runFile, qrelsFile, outputFile)
    
