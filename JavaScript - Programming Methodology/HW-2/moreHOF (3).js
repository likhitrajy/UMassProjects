// Image mapping function that is similar to imageMap but this takes in x,y as function parameters
function imageMapXY(image, func){
  let imageCopy = image.copy();

  for (let x = 0; x < imageCopy.width; ++x) {
    for (let y = 0; y < imageCopy.height; ++y) {
      imageCopy.setPixel(x, y, func(image, x, y));
    }
  }
   return imageCopy;
}

// Image masking function that masks an image accordingly based on the condition with a maskvalue which is a pixel color.
function imageMask(img, cond, maskValue){
  return imageMapXY(img, function(img, x, y) {
    if (cond(img, x, y) === true){
      return maskValue;
    }else{
      return img.getPixel(x, y);
    }
  })
}

// This function returns the function(func) applied pixels when the condition is satisfied 
function imageMapCond(img, cond, func){
    return imageMapXY(img, function(img, x, y){
     if(cond(img, x, y) === true){ 
     return func(img.getPixel(x, y));
    }
     else{
      return img.getPixel(x, y);
    }
  })
}

// A function which checks if the pixel max and min color channels have a difference of 1/3 or less  
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

// Makes the image gray using the imageMapXY and isGrayish functions
function makeGrayish(image){
  return imageMapXY(image, function(img, x, y){
   if(isGrayish(img.getPixel(x,y)) === false){ 
     let p = (img.getPixel(x,y)[0] + img.getPixel(x,y)[1] + img.getPixel(x,y)[2])/3
    return [p,p,p];
    }
    else{ return [img.getPixel(x,y)[0],img.getPixel(x,y)[1],img.getPixel(x,y)[2]];
  }
  });
}

// Makes the top half of the image gray based on the conditions
function grayHalfImage(image){
  return imageMapXY(image, function(image, x, y){
    if(y < (image.height/2)){
     if(isGrayish(image.getPixel(x,y)) === false){ 
       let p = (image.getPixel(x,y)[0] + image.getPixel(x,y)[1] + image.getPixel(x,y)[2])/3
       return [p,p,p];
      }
     else{ return [image.getPixel(x,y)[0],image.getPixel(x,y)[1],image.getPixel(x,y)[2]];
      }
    }
    else{ return image.getPixel(x,y);}
  });
}

// Blackens the image based on the condition that pixel color channels are less than 1/3
function blackenLow(image){
  return imageMapXY(image, function(image, x, y){
    let p = [image.getPixel(x,y)[0],image.getPixel(x,y)[1],image.getPixel(x,y)[2]];
    if(image.getPixel(x,y)[0] < (1/3) ){ p[0] = 0;}
    if(image.getPixel(x,y)[1] < (1/3) ){ p[1] = 0;}
    if(image.getPixel(x,y)[2] < (1/3) ){ p[2] = 0;}
    return p;
  });
}
// Tests
let url = 'https://people.cs.umass.edu/~joydeepb/robot.jpg';
let robot = lib220.loadImageFromURL(url);
robot.show();
makeGrayish(robot).show();
grayHalfImage(robot).show();
blackenLow(robot).show();

test('imageMapXY function definition is correct', function() {
function identity(image, x, y) { return image.getPixel(x, y); }
let inputImage = lib220.createImage(10, 10, [0, 0, 0]);
let outputImage = imageMapXY(inputImage, identity);
let p = outputImage.getPixel(0, 0); // output should be an image, getPixel works
assert(p.every(c => c === 0)); // every pixel channel is 0
assert(inputImage !== outputImage); // output should be a different image object
});

function pixelEq (p1, p2) {
const epsilon = 0.002; // increase for repeated storing & rounding
return [0,1,2].every(i => Math.abs(p1[i] - p2[i]) <= epsilon);
};
test('identity function with imageMapXY', function() {
let identityFunction = function(image, x, y ) {
return image.getPixel(x, y);
};
let inputImage = lib220.createImage(10, 10, [0.2, 0.2, 0.2]);
inputImage.setPixel(0, 0, [0.5, 0.5, 0.5]);
inputImage.setPixel(5, 5, [0.1, 0.2, 0.3]);
inputImage.setPixel(2, 8, [0.9, 0.7, 0.8]);
let outputImage = imageMapXY(inputImage, identityFunction);
assert(pixelEq(outputImage.getPixel(0, 0), [0.5, 0.5, 0.5]));
assert(pixelEq(outputImage.getPixel(5, 5), [0.1, 0.2, 0.3]));
assert(pixelEq(outputImage.getPixel(2, 8), [0.9, 0.7, 0.8]));
assert(pixelEq(outputImage.getPixel(9, 9), [0.2, 0.2, 0.2]));
});

