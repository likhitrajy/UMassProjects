/* CMPSCI 373 Homework 5: Hierarchical Scene */

const width = 800, height = 600;
const fov = 60;
const cameraz = 5;
const aspect = width/height;
const smoothShading = true;
let   animation_speed = 1.0;

let scene = new THREE.Scene();
let camera = new THREE.PerspectiveCamera(fov, aspect, 1, 1000);
camera.position.set(0, 1, cameraz);

let renderer = new THREE.WebGLRenderer({antialias: true});
renderer.setSize(width, height);
renderer.setClearColor(0x202020);
window.onload = function(e) {
	document.getElementById('window').appendChild(renderer.domElement);
}
let orbit = new THREE.OrbitControls(camera, renderer.domElement);	// create mouse control

let light0 = new THREE.DirectionalLight(0xFFFFFF, 1.0);
light0.position.set(camera.position.x, camera.position.y, camera.position.z);	// this light is at the camera
scene.add(light0);

let light1 = new THREE.DirectionalLight(0x800D0D, 1.0); // red light
light1.position.set(-1, 1, 0);
scene.add(light1);

let light2 = new THREE.DirectionalLight(0x0D0D80, 1.0); // blue light
light2.position.set(1, 1, 0);
scene.add(light2);

let amblight = new THREE.AmbientLight(0x202020);	// ambient light
scene.add(amblight);

let material = new THREE.MeshPhongMaterial({color:0x808080, specular:0x101010, shininess: 50, side:THREE.FrontSide});
let models = []; // array that stores all models
let numModelsLoaded = 0;
let numModelsExpected = 0;

// load models
// ===YOUR CODE STARTS HERE===
loadModel(sun_model, new THREE.MeshBasicMaterial({color:0x00ffa500}), 'sun');
loadModel(planet_model, new THREE.MeshPhongMaterial({color:0x00228B22, side:THREE.FrontSide}), 'planet1');
loadModel(moon_model, new THREE.MeshPhongMaterial({color:0x585858, side:THREE.FrontSide}), 'moon1');
loadModel(satellite_model, new THREE.MeshPhongMaterial({color:0x0000FF, side:THREE.FrontSide}), 'satellite1');
loadModel(satellite_model, new THREE.MeshPhongMaterial({color:0x800080, shininess: 50, side:THREE.FrontSide}), 'satellite2');
loadModel(planet_model, new THREE.MeshPhongMaterial({color:0xD3D3D3, specular:0x101010, shininess: 5, side:THREE.FrontSide}), 'planet2');
loadModel(moon_model, new THREE.MeshPhongMaterial({color:0x585858, specular:0x111111, shininess: 5, side:THREE.FrontSide}), 'moon2');
loadModel(satellite_model, new THREE.MeshPhongMaterial({color:0x964B00, specular:0x101010, shininess: 50, side:THREE.FrontSide}), 'satellite3');


// ---YOUR CODE ENDS HERE---

// 'label' is a unique name for the model for accessing it later
function loadModel(objstring, material, label) {
	numModelsExpected++;
	loadOBJFromString(objstring, function(mesh) { // callback function for non-blocking load
		mesh.computeFaceNormals();
		if(smoothShading) mesh.computeVertexNormals();
		models[label] = new THREE.Mesh(mesh, material);
		numModelsLoaded++;
	}, function() {}, function() {});
}

let initialized = false;
function animate() {
	requestAnimationFrame( animate );
	if(numModelsLoaded == numModelsExpected) {	// all models have been loaded
		if(!initialized) {
			initialized = true;
			// construct the scene by adding models
// ===YOUR CODE STARTS HERE===
			
scene.add(models['sun']);
			scene.add(models['planet1']);
			scene.add(models['moon1']);
			scene.add(models['satellite']);
			scene.add(models['satellite2']);
			scene.add(models['planet2']);
			scene.add(models['moon2']);
			scene.add(models['satellite3']);
			
			models['planet1'].position.x= 4.5;
			models['planet2'].position.x= 2;
			models['moon1'].position.x= 2;
			models['moon2'].position.y= 2;
			models['satellite1'].position.y= 2;
			models['satellite2'].position.y= 3;
			models['satellite3'].position.x= 2;
			
			models['sun'].scale.set(3, 3, 3);
			models['planet1'].scale.set(0.45, 0.45, 0.45);
			models['planet2'].scale.set(0.25, 0.25, 0.25);
			models['moon1'].scale.set(0.25, 0.25, 0.25);
			models['moon2'].scale.set(0.25, 0.25, 0.25);
			models['satellite1'].scale.set(0.5, 0.5, 0.5);
			models['satellite2'].scale.set(0.5, 0.5, 0.5);
			models['satellite3'].scale.set(0.5, 0.5, 0.5);

			models['sun'].add(new THREE.Group().add(models['planet1']));
			models['planet1'].add(new THREE.Group().add(models['moon1']));
			models['moon1'].add(new THREE.Group().add(models['satellite1']));
			models['moon1'].add(new THREE.Group().add(models['satellite2']));
			models['sun'].add(new THREE.Group().add(models['planet2']));
			models['planet2'].add(new THREE.Group().add(models['moon2']));
			models['moon2'].add(new THREE.Group().add(models['satellite3']));


// ---YOUR CODE ENDS HERE---
			
	
		}
		// animate the scene
// ===YOUR CODE STARTS HERE===
 models['sun'].rotation.y+=0.001*animation_speed;
 models['planet1'].rotation.y+=0.008*animation_speed;
 models['moon1'].rotation.x+=0.016*-animation_speed;
 models['satellite1'].rotation.y+=0.012*animation_speed;
 models['satellite2'].rotation.y+=0.012*-animation_speed;
 models['planet2'].rotation.x+=0.008*animation_speed;
 models['moon2'].rotation.y+=0.016*animation_speed;
 models['satellite3'].rotation.y+=0.012*-animation_speed;
		
// ---YOUR CODE ENDS HERE---
	
	}
	light0.position.set(camera.position.x, camera.position.y, camera.position.z); // light0 always follows camera position
	renderer.render(scene, camera);
}

animate();

function onKeyDown(event) {
	switch(event.key) {
		case 'w':
		case 'W':
			material.wireframe = !material.wireframe;
			break;
		case '=':
		case '+':
			animation_speed += 0.05;
			document.getElementById('msg').innerHTML = 'animation_speed = '+animation_speed.toFixed(2);
			break;
		case '-':
		case '_':
			if(animation_speed>0) animation_speed-=0.05;
			document.getElementById('msg').innerHTML = 'animation_speed = '+animation_speed.toFixed(2);
			break;
		case 'r':
		case 'R':
			orbit.reset();
			break;
	}
}

window.addEventListener('keydown', onKeyDown, false); // as key control if you need
