
#4 step feed back machine Noor Iterations
#On the Convergence of Logistic Map in NOOR Orbit


#Here A=alpha,B=Beta,G=gamma,T = logistic() map

##The map converges for A=B=Y=0.9 and 0<x<3.2
logistic = function(x,r){
  return (x*r*(1-x))
}

calcz = function(G,x,r){
  return ((1-G)*x + G*logistic(x,r))
}

calcy = function(B,G,x,r){
  return  ((1-B)*x + B*logistic(calcz(G,x,r),r))
}

calcx = function(A,B,G,x,r){
  return ((1-A)*x + A*logistic(calcy(B,G,x,r),r))
}
