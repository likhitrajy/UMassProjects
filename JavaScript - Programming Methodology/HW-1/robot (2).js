// Function that only extracts the red pixels from the image (using loops)
function removeBlueAndGreen(image){
 for (let i = 0; i < image.width; ++i) {
   for (let j = 0; j < image.height;++j) {
     image.setPixel(i,j, [image.getPixel(i,j)[0], 0, 0]);
    }
  } 
  return image;
}
// Function that shifts the intesity of red, green, blue to gree, blue, red (using loops)
function shiftRGB(image){
 for(let i = 0; i < image.width; ++i) {
   for(let j = 0; j < image.height;++j) {
     image.setPixel(i, j, [image.getPixel(i,j)[1], image.getPixel(i,j)[2], image.getPixel(i,j)[0]]);
    }
  }
  return image;
}
// Making a map function for mapToRed and mapToGBR and increaseContrast
function imageMap(img, func){
  let imageCopy = img.copy() ;
  
  for (let i = 0; i < imageCopy.width; ++i) {
    for (let j = 0; j < imageCopy.height; ++j) {
      imageCopy.setPixel(i, j, func(imageCopy.getPixel(i,j)));
    }
  }
  return imageCopy;
}

// This function uses imageMap and map to get red color out of the pixels only
function mapToRed(image){
  function redPixel(p){
  let red = p[0];
  return [red, 0, 0];
  }
  return imageMap(image, redPixel);

}

// This function uses imageMap and map to shift colors from RGB to GBR
function mapToGBR(image){
  function colorSwitch(p){
  let temp = 0;
   temp = p[0];
   p[0] = p[1];
   p[1] = p[2];
   p[2] = temp;

  return p;
  }
  return imageMap(image, colorSwitch);
}

// This increases the contrast of the image using mapping.
function increaseContrast(image){
  function contrast(p){

  if(p[0] > 0.5){
   p[0] = (p[0] + ( (1 - p[0])/10) );
 }
  else if(p[0] < 0.5){
   p[0] = (p[0] - (p[0]/10));
 }
  else if(p[1] > 0.5){
   p[1] = (p[1] + ( (1 - p[1])/10) );
 }
  else if(p[1] < 0.5){
   p[1] = (p[1] - (p[1]/10));
 }
  else if(p[2] > 0.5){
   p[2] = (p[2] + ( (1 - p[2])/10) );
 }
  else if(p[2] < 0.5){
   p[2] = (p[2] - (p[2]/10));
 }

  return p; 
  }
  return imageMap(image, contrast);
}

// Test Cases

test('removeBlueAndGreen function definition is correct', function() {
const white = lib220.createImage(10, 10, [1,1,1]);
removeBlueAndGreen(white).getPixel(0,0);
// Checks that code runs. Need to use assert to check properties.
});

test('No blue or green in removeBlueAndGreen result', function() {
// Create a test image, of size 10 pixels x 10 pixels, and set it to all white.
const white = lib220.createImage(10, 10, [1,1,1]);
// Get the result of the function.
const shouldBeRed = removeBlueAndGreen(white);
// Read the center pixel.
const pixelValue = shouldBeRed.getPixel(5, 5);
// The red channel should be unchanged.
assert(pixelValue[0] === 1);
// The green channel should be 0.
assert(pixelValue[1] === 0);
// The blue channel should be 0.
assert(pixelValue[2] === 0);
});

test('Check pixel equality', function() {
const inputPixel = [0.5, 0.5, 0.5]
// Create a test image, of size 10 pixels x 10 pixels, and set it to the inputPixel
const image = lib220.createImage(10, 10, inputPixel);
// Process the image.
const outputImage = removeBlueAndGreen(image);
// Check the center pixel.
const centerPixel = outputImage.getPixel(5, 5);
assert(pixelEq(centerPixel, [0.5, 0, 0]));
// Check the top-left corner pixel.
const cornerPixel = outputImage.getPixel(0, 0);
assert(pixelEq(cornerPixel, [0.5, 0, 0]));
});