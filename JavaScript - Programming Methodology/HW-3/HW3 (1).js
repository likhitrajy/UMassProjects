function imageMap(img, func){
  let imageCopy = img.copy() ;
  
  for (let i = 0; i < imageCopy.width; ++i) {
    for (let j = 0; j < imageCopy.height; ++j) {
      imageCopy.setPixel(i, j, func(imageCopy.getPixel(i,j)));
    }
  }
  return imageCopy;
}

function imageMapXY(image, func){
  let imageCopy = image.copy();

  for (let x = 0; x < imageCopy.width; ++x) {
    for (let y = 0; y < imageCopy.height; ++y) {
      imageCopy.setPixel(x, y, func(image, x, y));
    }
  }
   return imageCopy;
}
function isGrayish(p){
  let max = p[0];
  let min = p[0];
  for(let i = 1; i < 3; ++i){
    if(p[i] > max){ max = p[i];}
    if(p[i] < min){ min = p[i];}
  }
  if( max - min <= 1/3){ return true;}
  else{ return false;} 
}
 
function blurPixel(image, x, y){
  function colorMean(colorNum){
    let pixelCount = 0;
    let pixelTotal = 0;
      for (let i = -1; i <= 1; ++i){
        for (let j = -1; j <= 1; ++j){
          let pixelX = x + i;
          let pixelY = y + j;
           if (pixelX >= 0 && pixelX < image.width && pixelY >= 0 && pixelY < image.height){
              pixelCount = pixelCount + 1;
              pixelTotal = pixelTotal + image.getPixel(pixelX, pixelY)[colorNum];
            }
          }
        }
        return pixelTotal / pixelCount;
      }
      return [colorMean(0),colorMean(1),colorMean(2)];
}

function blurImage(image){

return imageMapXY(image,blurPixel);  
}

function diffLeft(image, x, y){
      let m1 = (image.getPixel(x, y)[0] + image.getPixel(x, y)[1] + image.getPixel(x, y)[2]) / 3;
      let m2 = m1;
      if(x + 1 < image.width && y + 1 < image.height){
        m2 = (image.getPixel(x, y+1)[0] + image.getPixel(x, y+1)[1] + image.getPixel(x, y+1)[2]) / 3;
      }
      
      let m = m1-m2;
      if (m < 0){
        m = -m;
      } 
      return [m,m,m];
}

function highlightEdges(image){
  
  return imageMapXY(image, diffLeft);
}

function reduceFunctions(fa){
  return fa.reduce((f, g) => (x) => g(f(x)), fa[0]); 
}

function combineThree(image){
  let fa = [
    function(p) {
    if (isGrayish(p)){
      return p; }
    else{
      let m = (p[0] + p[1] + p[2]) / 3;
      return [m, m, m];}
      },

    function(p) {
    if (p[0] < 1/3){ p[0] = 0.0};
    if (p[1] < 1/3){ p[1] = 0.0};
    if (p[2] < 1/3){ p[2] = 0.0};
    return p;
    },
    function(p){
    let temp = p[0];
    p[0] = p[1];
    p[1] = p[2];
    p[2] = temp;
    return p;
    }
  ];
  return imageMap(image, reduceFunctions(fa));
}

// Test Cases

function pixelEq (p1, p2) { 
  const epsilon = 0.002;
  for (let i = 0; i < 3; ++i) {
    if (Math.abs(p1[i] - p2[i]) > epsilon) {
      return false;
} }
  return true;
};

test('blurPixel1', function() {
  const i = lib220.createImage(30, 30, [0.5, 0.5, 0.5]);
  i.setPixel(15, 15, [1, 1, 1]);
  let p = blurImage(i).getPixel(15, 15);
  assert(pixelEq(p, [0.556, 0.556, 0.556]));
});

test('blurPixel2', function() {
  const i = lib220.createImage(30, 30, [0.6, 0.6, 0.6]);
  i.setPixel(0, 0, [1, 1, 1]);
  let p = blurImage(i).getPixel(0, 0);
  assert(pixelEq(p, [0.7, 0.7, 0.7]));
});

test('diffLeft1', function() {
  const i = lib220.createImage(30, 30, [0.5, 0.5, 0.5]);
  i.setPixel(15, 15, [1, 1, 1]);
  let p = diffLeft(i, 15, 15);
  assert(pixelEq(p, [0.5, 0.5, 0.5]));
});

test('diffLeft2', function() {
  const i = lib220.createImage(30, 30, [0.5, 0.5, 0.5]);
  i.setPixel(0, 10, [1, 1, 1]);
  let p = diffLeft(i, 0, 10);
  assert(pixelEq(p, [0.5, 0.5, 0.5]));
});

test('combineThree1', function() {
  const i = lib220.createImage(30, 30, [0, 0, 0]);
  i.setPixel(10, 10, [0.2, 0.3, 0.4]);
  let p = combineThree(i);
  assert(pixelEq(p.getPixel(10,10), [0, 0.4, 0]));
});

test('combineThree2', function() {
  const i = lib220.createImage(30, 30, [0.5, 0.5, 0.5]);
  i.setPixel(0, 10, [0.1, 0.1, 0.5]);
  let p = combineThree(i);
  console.log(p.getPixel(0,10));
  assert(pixelEq(p.getPixel(0,10), [0, 0, 0]));
});