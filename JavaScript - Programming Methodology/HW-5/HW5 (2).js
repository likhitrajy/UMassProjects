class FluentRestaurants{

  constructor(jsonData) {
    this.data = jsonData;
  }

  fromState(stateStr){
    let restuarants = [];
    this.data.forEach(function(restuarant){
      
      if(restuarant.state === stateStr){
             restuarants.push(restuarant);
             }
    });
      return new FluentRestaurants(restuarants);
    }

    ratingLeq(rating){
    let restuarants = [];
    this.data.forEach(function(restuarant){
        if(restuarant.stars <= rating){
            restuarants.push(restuarant);
           }
    });
      return new FluentRestaurants(restuarants);
  }

  ratingGeq(rating){
    let restuarants = [];
    this.data.forEach(function(restuarant){
      if(restuarant.stars >= rating){
        restuarants.push(restuarant);
        }
      });
      return new FluentRestaurants(restuarants);
  }

  category(categoryStr){
    let restuarants = [];
    this.data.forEach(function(restuarant){
        if(restuarant.categories.indexOf(categoryStr) !== -1){
            restuarants.push(restuarant);
          }
      });
        return new FluentRestaurants(restuarants);
  }

  hasAmbience(ambienceStr){
    let restuarants = [];
    this.data.forEach(function(restuarant){
      let ambience = lib220.getProperty(restuarant.attributes, "Ambience");
      if(ambience.found === true){
        let ambienceValues = lib220.getProperty(ambience.value, ambienceStr);
       
        if(ambienceValues.found === true && ambienceValues.value === true){
          restuarants.push(restuarant);
        }
      }
    });
    return new FluentRestaurants(restuarants);
  }

  bestPlace(){
    let bestRestaurant = this.data[0];
    this.data.forEach(function(restuarant){
      if(bestRestaurant.stars > restuarant.stars){

      }else if(bestRestaurant.stars < restuarant.stars){
        bestRestaurant = restuarant;
      }else{
        if(restuarant.review_count > bestRestaurant.review_count){
          bestRestaurant = restuarant;
        }
      }
    });
    return bestRestaurant;
  }

  mostReviews(){
    let mostReviewedRestaurant = this.data[0];
    this.data.forEach(function(restuarant){
      if(mostReviewedRestaurant.review_count > restuarant.review_count){

      }else if(mostReviewedRestaurant.review_count < restuarant.review_count){
        mostReviewedRestaurant = restuarant;
      }else{
        if(restuarant.stars > mostReviewedRestaurant.stars){
          mostReviewedRestaurant = restuarant;
        }
      }
    });
    return mostReviewedRestaurant;
  }
}