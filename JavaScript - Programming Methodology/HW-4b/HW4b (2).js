
function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min)) + min;
}

function generateInput(n){
  let numbers = [];
  for (let i=0; i < n; ++i){
    for (let j=0; j < n; ++j){
      if (j===0){
        numbers.push([randomInt(0, n)]);
      }else{
        let rndInt = randomInt(0, n);
     
        while (numbers[i].indexOf(rndInt) !== -1){
          rndInt = randomInt(0, n);
        }
        numbers[i].push(rndInt);
      }
    }
  }
  return numbers;
}


function runOracle(f){

  let n = 3;

   let companies = generateInput(n);
   let candidates = generateInput(n);
   let Run = f(companies, candidates);
   let Offer = Run.trace;
   let Hire = Run.out;


  test('hire length is valid', function() {
    assert(Hire.length === n);
  });

  test('out is valid', function() {
    
    for (let i=0; i < Hire.length; ++i){
      let company = Hire[i].company;
      let candidate = Hire[i].candidate;

      for (let i=0; i < Offer.length; ++i){
        let offer = Offer[i];
        let from = offer.from;
        let to = offer.to;
        let fromComp = offer.fromCo;

         if(to === candidate && fromComp === true){
          let newComp = candidates[candidate].indexOf(from);
          let curComp = candidates[candidate].indexOf(company)

          if(newComp < curComp){
            assert(false);
          }
        }
        if(to === company && fromComp === false){
          let newCand = companies[company].indexOf(from);
          let curCand = companies[company].indexOf(candidate)
          
          if(newCand < curCand){
            assert(false);
          }
        }
      }
    }
 
  });


  test('trace is valid', function() {
    let bestOffersIndexCompanies = [];
    for(let x = 0; x < n; ++x){
      bestOffersIndexCompanies.push(-1);
    }
    let bestOffersIndexCandidates = [];
    for(let x = 0; x < n; ++x){
      bestOffersIndexCandidates.push(-1);
    }


    for (let i=0; i < Offer.length; ++i){
      let offer = Offer[i];
      let from = offer.from;
      let to = offer.to;

      if(offer.fromCo === true){
        let candidateIndex = companies[from].indexOf(to);
        if (candidateIndex === bestOffersIndexCompanies[from] + 1){
          bestOffersIndexCompanies[from] = candidateIndex;
        }else{
          assert(false);
        }
      }

      if(offer.fromCo === false){
        let companyIndex = candidates[from].indexOf(to);
        if (companyIndex === bestOffersIndexCandidates[from] + 1){
          bestOffersIndexCandidates[from] = companyIndex;
        }else{
          assert(false);
        }
      }
    }

  });
}

const oracleLib = require('oracle');
runOracle(oracleLib.traceWheat1);
runOracle(oracleLib.traceChaff1);