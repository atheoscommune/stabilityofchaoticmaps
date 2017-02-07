#4 step feed back machine Noor Iterations
#On the Convergence of Logistic Map in NOOR Orbit
#Series equation to show the nature of increment and decrement
#NoorSeriesEquationRelationABYandr
X=0;
Seque = seq(from=0,to=15,by = gap)
for(k in Seque){
  x = ((0.967*k*k*k)-(5.16*k*k)+(9.477*k)-2.155)
  X = c(X,x)
}
plot(X,type="l",ylab="x",xlab="Iterations")
