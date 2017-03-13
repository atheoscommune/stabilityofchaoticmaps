#FILTER 2

#read from the datasheet 1
datasheet = read.table(file="datasheetfilter1",header=TRUE)

l = length(datasheet[,1])

#empty list with same header as that of datasheet
d = datasheet[-1:-l,]

#Declaration/Initialization of variables
A=0
B=0
G=0
r=0
i=1
# Read the file containing R script for noor function
source("noor.R")

# A vector containing values {0.1,0.2,....,0.9,1.0}
xvals = 1.0
#read every row and calculate iterations for all 0<x<=1
while(i<=l){
  A = datasheet[i,1]
  B = datasheet[i,2]
  G = datasheet[i,3]
  r = datasheet[i,4]
  cond = FALSE
  g = 0.1
  #for each combination of A,B,G,r check for all the values of x. 
  while(g <= xvals){
    count = 1
    x=g
    X = x
    #NO iteration 
    while(count<100){
      x = calcx(A,B,G,x,r)
      X = c(X,x)
      count = count+1
    }
    #Check if the vector contains -Inf or Inf or is zero or NaN. Here I am checkin the last value
    #because if any of the preceeding value is Inf then all the succeeding value is Inf
    if(is.na(X[99]) || X[99]==-Inf || X[99]==Inf || X[99]==0){
      cond = FALSE
      break
    }else{
      #I don't want values for which chaotic nature occurs at less significant decimal values
      #Therefore consider only 10 values after decimal point.
      c1 = 1
      while(c1<=100){
        X[c1] = ceiling(X[c1]*10e10)/10e10
        c1=c1+1
      }
      
      cond = FALSE
      #No repetition will occur if values are chaotic
      if(length(unique(X))<99){
        cond = FALSE
        break
      }else{
        cond=TRUE
      }
    }
    g = g+0.1
  }
  if(cond){
    #If conditions are satisfied then add the values to the list
    d = rbind(d,datasheet[i,])
  }
  i=i+1
}



datasheet = d

write.table(d,file="datasheetfilter2raw2",col.names=TRUE)
