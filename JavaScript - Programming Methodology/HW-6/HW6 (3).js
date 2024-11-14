// sempty: Stream<T>
let sempty = { isEmpty: () => true, toString: () => 'sempty' };

// snode<T>(head: T, tail: Memo<Stream<T>>): Stream<T>
function snode(head, tail) {
  return { isEmpty: () => false, head: () => head, tail: tail.get,
           toString: () => "snode(" + head.toString() + ", " + tail.toString() + ")" }
}


// type Memo<T> = { get: () => T, toString() => string }
// memo0<T>(f: () => T): Memo<T>
function memo0(f) {

  let r = { evaluated: false };

  return { get: function() {
                  if (! r.evaluated) {
                    r = { evaluated: true, v: f() }
                  }
                  return r.v;
                },
                toString: function() {
                  return r.evaluated ? r.v.toString()
                    : '<unevaluated>';
                } 
          };
}



// sfilter<A>(stream: Stream<A>, f: A=>boolean): Stream<A>
function sfilter(stream, pred) {
  if (stream.isEmpty()) { return sempty; }
  return pred(stream.head()) ? snode(stream.head(), memo0(() => sfilter(stream.tail(), pred))) : sfilter(stream.tail(), pred);
}


// smap<A,B>(f: (x: A) => B, stream: Stream<A>): Stream<B>
function smap(stream, f) {
  if (stream.isEmpty()) { return sempty; }
  return snode(f(stream.head()), memo0(() => smap(stream.tail(), f)));
}


function addSeries(s1, s2){
  if(s1.isEmpty() || s2.isEmpty()){
    return sempty;
  }

  return snode(s1.head() + s2.head(), memo0(() => addSeries(s1.tail(), s2.tail())));
}



function prodSeries(s1, s2){

  if(s1.isEmpty() || s2.isEmpty()){
    return sempty;
  }

  return addSeries(smap(s1, x => x * s2.head()), snode(0, memo0(() => prodSeries(s1, s2.tail()))));
}



function derivSeries(series){

  let exp = 0;

  if(series.isEmpty()){
      return sempty;
  }

  function traverseSeries(serNode, exp){

    if(serNode.isEmpty()){
      return sempty;
    }
    return snode(exp * serNode.head(), memo0(() => traverseSeries(serNode.tail(), exp + 1)));
  }

  return traverseSeries(series, exp);
}



function coeff(serNode, n){
  if(serNode.isEmpty() || n < 0){
    return [];
  }

  let arr = [];

  while(n >= 0){
    if (serNode.isEmpty()) { break }
    
    arr.push(serNode.head());

    snode(serNode.head(), memo0(() => traverseSeries(serNode.tail(), n, arr)));

    n -= 1;
    serNode = serNode.tail();
  }

  return arr;
}


function evalSeries(serNode, n){

  function closure(x){
    
    let sum = 0;

    let exponent = 0;

    while(n >= 0){
      if (serNode.isEmpty()) { break }

      sum += serNode.head() * (Math.pow(x, exponent));

      snode(serNode.head(), memo0(() => traverseSeries(serNode.tail())));

      exponent += 1;
      n -= 1;
      serNode = serNode.tail();
    }

    return sum;
  }

  return closure
}


function rec1Series(f, v){

  function createSeries(a, f){
    return snode(f(a), memo0(() => createSeries(f(a), f)));
  }

  return snode(v, memo0(() => createSeries(v, f)));
}


function expSeries(){

  let kCounter = 1

  let factorialTotal = 1;

  function taylor(){

    kCounter = kCounter + 1;

    factorialTotal = factorialTotal * kCounter;
    
    console.log(factorialTotal);

    let retVal = 1 / factorialTotal;

    return snode(retVal, memo0(() => taylor()));
  }

  return snode(1, memo0(() => snode(1, memo0(() => taylor()))));

}


function recurSeries(coef, init){

  let k = coef.length;

  let n = 0;

  let counter = 0;

  let storedRets = [init[0]]

  function createSeries(){
    if(counter < k - 1){
      counter += 1;
      storedRets.push(init[counter]);
      return snode(init[counter], memo0(() => createSeries()));
    }else{

      let retVal = 0;

      for(let i = 0; i < k; ++i){
        retVal += storedRets[n+i] * coef[i];
      }

      n += 1;

      storedRets.push(retVal);

      return snode(retVal, memo0(() => createSeries()));
    }
  }

  return snode(init[0], memo0(() => createSeries()));
}
