#Simple Logistic map using x* = x*r*(1-x)
#simplelogisticsmap

logistic = function(arg,r){
  return (r*arg*(1-arg));
}
X=0
x0=x
for(i in 1:n){
  x = logistic(x,r)
  X = c(X,x)
}
plot(X,type="l",ylab="x",xlab=sprintf("Iterations. x0= %f r=%f",x0,r))
tail(X,100)