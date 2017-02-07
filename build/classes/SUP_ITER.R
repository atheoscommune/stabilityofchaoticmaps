#Two Step Superior Iteration Logistic maps using x* = B*f(x) + (1-B)*x
#A new experimental approach to study the stability of logistic map
#Mamta Rani a, * , Rashi Agarwal b

logistic = function(arg,r){
  return (r*arg*(1-arg));
}
X=0

x = B*logistic(x,r) +(1-B)*x
X = c(X,x)

for(i in 1:n){
  x = B*logistic(x,r) +(1-B)*x
  X = c(X,x)
}
plot(X,type="l",ylab="x",xlab="Iterations")
tail(X,100)