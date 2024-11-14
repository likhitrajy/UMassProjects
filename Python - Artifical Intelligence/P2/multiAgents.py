# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent
from pacman import GameState

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState: GameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState: GameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        score = successorGameState.getScore()
        posGhost = successorGameState.getGhostPositions()
        foodDist = []
        ghostDist = []        

        for pos in posGhost:
            dist = manhattanDistance(pos, newPos)
            ghostDist.append(dist)


        for food in newFood.asList():
            dist = manhattanDistance(food, newPos)
            foodDist.append(dist)


        for dist in ghostDist:
            if dist > 0:
                score -= 1/dist

        for fd in foodDist:
            score += 1/fd
        

        return score

def scoreEvaluationFunction(currentGameState: GameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """
    def getAction(self, gameState: GameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        return self.miniMax(gameState, 0, gameState.getNumAgents(), self.depth)[1]
    
    def minOrMax(self, valType, gameState, index, numAgents, depth):
        legalActions = gameState.getLegalActions(index)
        pacActions = [] 

        for act in legalActions:
            pacActions.append((self.miniMax(gameState.generateSuccessor(index, act), index + 1, numAgents, depth)[0], act))
        
        if valType == 'max':
            return max(pacActions)
        else:
            return min(pacActions)
    
    
    def miniMax(self, gameState, index, numAgents, depth):
        if depth == 0 or numAgents == 0:
            eval = (self.evaluationFunction(gameState), "Stop")
            return eval
        
        if gameState.isWin() or gameState.isLose():
                eval = (self.evaluationFunction(gameState), "Stop")
                return eval
        
        index = index % numAgents
        
        if index == (numAgents - 1):   
            depth -= 1 
        
        if index == 0:
            return self.minOrMax('max', gameState, index, numAgents, depth)
        
        else:
            return self.minOrMax('min', gameState, index, numAgents, depth)

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """
    def getAction(self, gameState: GameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        numOfAgents = gameState.getNumAgents()
        infinity = float('inf')
        return self.miniMaxAB(gameState, 0, numOfAgents, self.depth, -infinity, infinity)[1]
    
    def minOrMaxAB(self, valType, gameState, index, agents, depth, alpha, beta):
        legalActions = gameState.getLegalActions(index)
        pacActions = []

        for act in legalActions:
            val = self.miniMaxAB(gameState.generateSuccessor(index, act), index + 1, agents, depth, alpha, beta)[0]
            pacActions.append((val, act))

            values = self.valEval(valType, val, alpha, beta)
            
            if values["bool"]:
                return (values["value"], act)
            
            alpha = values["alpha"]
            beta = values["beta"]

        if valType == "max":
            return max(pacActions)
        
        else:
            return min(pacActions)

    
    
    def valEval(self, valueType, value , alpha, beta):
        boolFlag = False
        
        if valueType == "max":
            if value > beta:
                boolFlag = True 
                
                return {"bool": boolFlag, "value": value, "alpha": alpha, "beta": beta}
            
            else:
                alpha = max(alpha, value)
                
                return {"bool": boolFlag, "value": value, "alpha": alpha, "beta": beta}
            
        if valueType == "min":
            if value < alpha:
                boolFlag = True

                return {"bool": boolFlag, "value": value, "alpha": alpha, "beta": beta}
            
            else: 
                beta = min(beta, value)

                return {"bool": boolFlag, "value": value, "alpha": alpha, "beta": beta}
        


    def miniMaxAB(self, gameState, index, agents, depth, alpha, beta):
        if depth == 0 or agents == 0:
            return (self.evaluationFunction(gameState), "Stop")
        
        if gameState.isWin() or gameState.isLose():
            return (self.evaluationFunction(gameState), "Stop")
        
        
        index = index % agents

        if index == agents - 1:
            depth -= 1

        if index == 0:
            return self.minOrMaxAB("max", gameState, index, agents, depth, alpha, beta)
        else:
            return self.minOrMaxAB("min", gameState, index, agents, depth, alpha, beta)

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """        
    def getAction(self, gameState: GameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        return self.expectimax(gameState, 0, gameState.getNumAgents(), self.depth)[1]
    

    def maxOrExp(self, funcType, gameState, index, agents, depth):
        legalActions = gameState.getLegalActions(index)
        pacActions = []

        numSuccessors = 0 
        valTotal = 0

        for act in legalActions:
            if funcType == "max":
                val = self.expectimax(gameState.generateSuccessor(index, act), index + 1, agents, depth)[0]
                pacActions.append((val, act))

            else: 
                val = self.expectimax(gameState.generateSuccessor(index, act), index + 1, agents, depth)[0]
                pacActions.append((val, act))
                numSuccessors += 1 
                probability = numSuccessors / len(pacActions)

                valTotal += (probability * val)

        if funcType == "max":
            return max(pacActions)
        
        else:
            return (valTotal, None)
        

        
    def expectimax(self, gameState, index, agents, depth):
        if depth == 0 or agents == 0:
            return (self.evaluationFunction(gameState), "Stop")
        
        if gameState.isWin() or gameState.isLose():
            return (self.evaluationFunction(gameState), "Stop")
        
        index = index % agents 

        if index == agents - 1:
            depth -= 1 

        if index == 0:
            return self.maxOrExp("max", gameState, index, agents, depth)
        else:
            return self.maxOrExp("exp", gameState, index, agents, depth)
    
               

def betterEvaluationFunction(currentGameState: GameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    gameDt = {"PacPos": currentGameState.getPacmanPosition(), "GhostPos": currentGameState.getGhostPositions(),
                "food": currentGameState.getFood().asList(), "caps": currentGameState.getCapsules(), "DefaultDis": 100}
    
    foodScr = calculate(gameDt["DefaultDis"], gameDt["PacPos"], gameDt["food"], "food")
    capScr = calculate(gameDt["DefaultDis"], gameDt["PacPos"], gameDt["caps"], "capsule")
    ghostScr = calculate(gameDt["DefaultDis"], gameDt["PacPos"], gameDt["GhostPos"], "ghost")

    score = currentGameState.getScore()

    return score + foodScr + capScr + ghostScr
    
def calculate(distance, position, dataList, item):
    dist = distance
    
    for data in dataList:
        dataDist = util.manhattanDistance(position, data)
        dist = min(dataDist, dist)
    
    if item == "food":
        return 1000 - len(dataList) * 5
    elif item == "capsule":
        return 500 - len(dataList) * 10
    elif item == "ghost":
        return -(dist * 2)    
    else:
        return -5

# Abbreviation
better = betterEvaluationFunction
