# Script to find chaotic ranges of Noor map Logistic map


#functions to calculate the Noor iteration of Logistic map
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

#datasheet is a matrix whose row collects information 
#of last 16 consecutive values x when iterated 100 times
#The values are corresponding to the combination of A,B,G,r. Where max(r)<100
datasheet = matrix(ncol=20,byrow = TRUE)
colnames(datasheet, do.NULL = TRUE, prefix = "col")
colnames(datasheet)<-c("A","B","G","r",1:16)

#initialize cariables
A=0
B=0
G=0

n=0

r=0

x=0

X=0

while(A<=1.0){
  
  while(B<=1){
    G=0.0
    while(G<=1){
      r = 0.1
      
      while(r<100){
        # First step is to calculate the values with initial value of x as 0.5
        x = 0.5
        X = x
        n=0
        while(n<100){
          x = calcx(A,B,G,x,r)
          X = c(X,x)
          n = n+1
        }
        X = tail(X,16)
        # Add the new row
        datasheet = rbind(datasheet,c(A,B,G,r,X))
        
        r = r+0.5
      }
      G = G+0.1      
    } 
    
    B = B+0.1
  }
  A=A+0.1
}
#Write the data into file
write.table(datasheet,file="datasheet1",col.names = TRUE,sep=",")
