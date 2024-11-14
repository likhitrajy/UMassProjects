#!/usr/bin/python

import sys
import gzip
import shutil
import numpy


def InLinks(line):
    line = line.split()
    sourcePage = line[0]
    targetPage = line[1]

    if targetPage in inLinksIncoming.keys():
        inLinksIncoming[targetPage] += [sourcePage]
    else:
        inLinksIncoming[targetPage] = [sourcePage]

    if sourcePage in outgoingLinks.keys():
        outgoingLinks[sourcePage] += [targetPage]
    else:
        outgoingLinks[sourcePage] = [targetPage]


def PRtoC():

    P = list(outgoingLinks.keys()) + list(set(list(inLinksIncoming.keys())) - set(list(outgoingLinks.keys()))) #all web pages

    LP = len(P) #|P|

    I = dict.fromkeys(P, 1/LP) # oldPR[]
    
    converged = False
    while converged != True:

        R = dict.fromkeys(P, lambda_val/LP) # newPR[]

        pVals = []

        for p in P:

            if p in outgoingLinks:
                Q = outgoingLinks[p] #pOutlinks
            else:
                Q = []

            LQ = len(Q) #|Q|
            if LQ > 0:
                for u in Q:
                    R[u] = R[u] + (1-lambda_val) * (I[p]/LQ)
            else:
                pVals.append(p)

        if len(pVals) > 0:
            total = 0
            for p in pVals:
                total = total + (1-lambda_val) * (I[p]/LP)
            for p in P:
                R[p] = R[p] + total

        #Check for convergence tau > ||new - old||
        if tau > numpy.linalg.norm(numpy.fromiter(I.values(), dtype=float) - numpy.fromiter(R.values(), dtype=float)):
            converged = True

        I = R.copy()

    return I

def PratN():
    P = list(outgoingLinks.keys()) + list(set(list(inLinksIncoming.keys())) - set(list(outgoingLinks.keys()))) #all web pages

    LP = len(P) #|P|

    I = dict.fromkeys(P, 1/LP) # oldPR[]
    i = 0
    while i < N and i != 100:
        R = dict.fromkeys(P, lambda_val/LP) # newPR[]

        pVals = []

        for p in P:

            if p in outgoingLinks:
                Q = outgoingLinks[p] #pOutlinks
            else:
                Q = []

            LQ = len(Q) #|Q|
            if LQ > 0:
                for u in Q:
                    R[u] = R[u] + (1-lambda_val) * (I[p]/LQ)
            else:
                pVals.append(p)

        if len(pVals) > 0:
            total = 0
            for p in pVals:
                total = total + (1-lambda_val) * (I[p]/LP)
            for p in P:
                R[p] = R[p] + total
            i += 1
            I = R.copy()
            if i >= 101:
                break
    return I



if __name__ == '__main__':
    # Read arguments from command line; or use sane defaults for IDE.
    argc = len(sys.argv)
    input_file = sys.argv[1] if argc > 1 else "links.srt.gz"
    lambda_val = float(sys.argv[2]) if argc > 2 else 0.2
    tau = 0.005
    N = -1  # signals to run until convergence
    if argc > 3:
        arg = sys.argv[3]
        if arg.lower().startswith('exactly'):
            N = int(arg.split(' ')[1])
        else:
            tau = float(arg)
    inlinks_file = sys.argv[4] if argc > 4 else "inlinks.txt"
    pagerank_file = sys.argv[5] if argc > 5 else "pagerank.txt"
    k = int(sys.argv[6]) if argc > 6 else 100

    inlink_counts = {}    
    outgoingLinks = {} #v[] {link1: [link3, link4], link2: [link1, link3]}

    inLinksIncoming = {} #Bu {link1: [link3, link4], link2: [link1, link3]}

    print('Running InLinks')

    with gzip.open(input_file, 'r') as f:
        for line in f:
            parts = line.strip().split()
            if len(parts) != 2:
                continue
            page_to = parts[1]
            if page_to not in inlink_counts:
                inlink_counts[page_to] = 0
            inlink_counts[page_to] += 1
    inlink_cnt = sorted(inlink_counts.items(), key=lambda x: (-x[1], x[0]))[:k]
    i = 1
    with open(inlinks_file, 'w') as il:
     for cnt in inlink_cnt:
        il.write(cnt[0].decode('utf-8')+"\t"+ str(i)+"\t"+str(cnt[1])+"\n")
        i += 1


    print('Finished InLinks, running RageRanks')

    if input_file[-3:] == '.gz':
        with gzip.open(input_file, 'rb') as f_in:
            with open(input_file[:-3], 'wb') as f_out:
                shutil.copyfileobj(f_in, f_out)
    
    with open(input_file[:-3], "r", encoding="utf8") as f:
        for line in f.read().split('\n'):
            if line != '':
                InLinks(line)
                

   


    with open(pagerank_file, 'w') as f:
       
        if N == -1:
            topPageRanks = PRtoC()
        else:
            topPageRanks = PratN()    
        pr_cnt = sorted(topPageRanks.items(), key = lambda x: (-x[1], x[0]))[:k]    
        i = 0
        for cnt in pr_cnt:
            i += 1
            f.write(cnt[0] + '\t' + str(i) + '\t' + str('{:.12f}'.format(cnt[1])) + '\n')


    print('Finished RageRanks')
