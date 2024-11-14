class FSA{

  constructor(){

    let currentState = undefined;
    let allStates = [];

    this.getState = () => {return currentState};

    //nextState(e: string): this
    this.nextState = (e) => {

      if(currentState !== undefined){
        let nextState = currentState.nextState(e);
        if (nextState !== undefined){
          currentState = nextState;
        }
      }

      return this;
    }
    
    //createState(s: string, transitions: Transition[]): this
    this.createState = (s, transitions) => {

      let newState = newState = new State(s);
      let eventName = "";
      let stateName = "";


      if(currentState === undefined){
        
        currentState = newState;
      }else{
        for(let i = 0; i < allStates.length; ++i){
          
          //If state already exists, remove all transitions
          if(s === allStates[i].getName()){
            //allStates[i].resetTransitions();
            for(let x = 0; x < transitions.length; ++x){
              eventName = Object.keys(transitions[x])[0]
              stateName = lib220.getProperty(transitions[x], eventName).value
              allStates[i].addTransition(eventName, stateName);
            }
            return this;
          }
        
        }
      }

      for(let i = 0; i < transitions.length; ++i){
        eventName = Object.keys(transitions[i])[0]
        stateName = lib220.getProperty(transitions[i], eventName).value
        newState.addTransition(eventName, stateName);
      }

      allStates.push(newState);
      return this;
    }

    //(s: string, t: Transition): returns this
    this.addTransition = (s, t) => {

      let eventName = Object.keys(t)[0]
      let stateName = lib220.getProperty(t, eventName).value
      
      let updateState = undefined;
      let stateTrans = undefined;

      for(let i = 0; i < allStates.length; ++i){

        if(s === allStates[i].getName()){
          updateState = allStates[i]
        }

        if(stateName === allStates[i].getName()){
          stateTrans = allStates[i]
        }
      }

      if (updateState === undefined){
        this.createState(s, [t]);
      }else{
        updateState.addTransition(eventName, stateTrans);
      }
      if (stateTrans === undefined){
        stateTrans = new State(stateName);
        allStates.push(stateTrans);
      }

    
      return this;
    }

    //showState(): string: returns the name of the current state, or undefined
    this.showState = () => {
      if(currentState === undefined){
        return undefined;
      }else{
        return currentState.getName();
      }
    }

    //createMemento(): Memento: creates a memento object with the current state
    this.createMemento = () => {

      let memento = new Memento();
      memento.storeState(currentState);

      return memento;
    }

                //m: Memento; returns this
    this.restoreMemento = (m) => {

      let mementoState = m.getState();

      for(let i = 0; i < allStates.length; ++i){
        if(mementoState === allStates[i]){
          currentState = mementoState;
          break;
        }
      }

      return this;
    }


    class State{

      //constructor(name: string)
      constructor(name){

        this.name = name;

        //{ key: state }[]
        let allTransitions = [];
        this.resetTransitions = () => {
          allTransitions = [];
        }

          //getName(): string: returns the name of the state
        this.getName = () => {
          return this.name;
        }

        //setName(s: string): returns this: changes the name of the state
        this.setName = (s) => {
          this.name = s;
          return this;
        }

        //addTransition(e: string, s: State) adds a transition that on event e moves to state s. It returns this.
        this.addTransition = (e, s) => {
          let newTransition = {};
          lib220.setProperty(newTransition, e, s);
          allTransitions.push(newTransition);
          return this;
        }
                //e: string; returns next state or undefined
        this.nextState = (e) => {
          
          let outputStates = this.nextStates(e);

          if(outputStates.length === 0){
            return undefined;
          }

          return outputStates[Math.floor(Math.random() * outputStates.length)];
        }

        //nextStates(e: string): State[] returns an array of all successor states as a result of event e.
        this.nextStates = (e) => {

          let outputStates = []

          for(let i = 0; i < allTransitions.length; ++i){

            let transition = lib220.getProperty(allTransitions[i], e);
            if(transition.found === true){
              
              let state = transition.value;

              for(let i = 0; i < allStates.length; ++i){
                if(state === allStates[i].getName()){
                  outputStates.push(allStates[i]);
                  break;
                }
              }
              
            }

          }
          return outputStates;
        }
      }
    }


    class Memento{
      
      constructor(){

        let state = undefined;

        //storeState(s: State): void which takes a state as input, and saves it so it can be restored
        this.storeState = (s) => {
          state = s;
        }

        //getState(): State, which returns the state being stored in its attribute
        this.getState = () => {
          return state;
        }
      }
    }
  }
}
