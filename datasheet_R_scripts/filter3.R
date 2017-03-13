#Filter3(Final Filter)

# Read from previous datasheet

datasheet = read.table(file="datasheetfilter2raw2",header=TRUE)

l = length(datasheet[,1])

# New list
d = datasheet[-1:-l,]

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
  # The value of x was changed manually below  however it can be done through loop
  x = 0.9
    count = 1
    X = x
    # Iterate NO
    while(count<100){
      x = calcx(A,B,G,x,r)
      X = c(X,x)
      count = count+1
    }
    #Ignore the iterations where value goes out of LM range i.e >1
    #The iteration to be chaotic must have values distributed all over the interval
    #As all the iterations can't be calculated therefore checking for existence of values >0.8 and <0.3
    if(sum(X>1)==0 && sum(X>0.8)>10 && sum(X<0.3)>10){
      c1 = 1
      while(c1<=100){
        X[c1] = ceiling(X[c1]*10e5)/10e5
        c1=c1+1
      }
      #If it is truly chaotic then no repetition of values
      if(length(unique(X))==100){
        
        d = rbind(d,datasheet[i,])
      }
    }
  i=i+1
}

#write to file
write.table(d,file="datasheetfiltercomplete",col.names=TRUE)

datasheet = d
