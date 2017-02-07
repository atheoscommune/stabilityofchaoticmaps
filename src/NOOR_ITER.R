#4 step feed back machine Noor Iterations
#On the Convergence of Logistic Map in NOOR Orbit

#Here A=alpha,B=Beta,G=gamma,T = logistic() map
#noorIterations.R
logistic = function(x,r=3.11){
  return (x*r*(1-x))
}

calcz = function(G,x,r=3.11){
  return ((1-G)*x + G*logistic(x,r))
}

calcy = function(A,G,x,r=3.11){
  return = ((1-A)*x + A*logistic(calcz(G,x,r)))
}

calcx = function(A,B,G,x,r=3.11){
  return ((1-B)*x + B*logistic(calcy(A,G,x,r)))
}


#To plot the noor orbit all changes should be done below and no where else
X=0
X = x

for(i in 1:n){
  x = calcx(A,B,G,x,r)
  X = c(X,x)
}
plot(X,type="l",ylab="x",xlab="Iterations")
tail(X,100)