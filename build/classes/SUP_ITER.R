#Two Step Superior Iteration Logistic maps using x* = B*f(x) + (1-B)*x
#A new experimental approach to study the stability of logistic map
#Mamta Rani a, * , Rashi Agarwal b

logistic = function(x,r){
	return (x*r*(1-x))
}

calcx = function(B,x,r){
	return ((1-B)*x + B*logistic(x,r))
}

X = x

for(i in 1:n){
  x = calcx(B,x,r)
  X = c(X,x)
}
plot(X,type="l",ylab="x",xlab="Iterations")
tail(X,100)