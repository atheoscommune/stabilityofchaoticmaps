#ishikawaiterates
logistic = function(arg,r){
  return (r*arg*(1-arg));
}

X=NULL
Y=NULL
y = 0

for(i in 1:n){
  y = B*logistic(x,r) + (1-B)*x
  x = A*logistic(y,r) + (1-A)*x
  X = c(X,x)
}

plot(X,type="l",ylab="x",xlab="Iterations")
tail(X,50)