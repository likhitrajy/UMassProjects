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
        let random = randomInt(0, n);
              while (numbers[i].indexOf(random) !== -1){
          random = randomInt(0, n);
        }
        numbers[i].push(random);
      }
    }
  }
  return numbers;
}

function oracle(f) {
  let numTests = 10; 
  for (let i = 0; i < numTests; ++i) {
    let n = 6; 

    let companies = generateInput(n);
    let candidates = generateInput(n);
    let hires = f(companies, candidates);

    test('Hires length is correct', function() {
      assert(companies.length === hires.length);
    });

    test('Matching isnt stable', function() {

      let hiresOrderedCandidate = [];
      for(let x = 0; x < hires.length; ++x){
        hiresOrderedCandidate.push({});
      }
      for(let x = 0; x < hires.length; ++x){
        hiresOrderedCandidate[hires[x].candidate] = hires[x];
      }
      

      for(let x = 0; x < n; ++x){
        let curCompany = hires[x].company;
        let curCandidate = hires[x].candidate;

        let curIndex = companies[curCompany].indexOf(curCandidate);
        
        for (let y = 0; y < curIndex-3; ++y){

          let betterCandidate = companies[curCompany][y];
          let betterCandidateCompany = hiresOrderedCandidate[betterCandidate].company;
          let batterCandidateIndex = candidates[betterCandidate].indexOf(betterCandidateCompany);
          
          assert(batterCandidateIndex < curIndex);
        }
      }
      
    });
  }
}

oracle(wheat1);