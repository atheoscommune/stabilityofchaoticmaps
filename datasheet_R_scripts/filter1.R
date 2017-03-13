datasheet = read.csv(file = "datasheet",header=TRUE)
l = length(datasheet[,1])
d = datasheet[-1:-l,]
i=1
#Remove all the rows for which value does not seem chaotic
while(i<=l){
  if(is.nan(datasheet[i,20]) || is.na(datasheet[i,20]) || datasheet[i,20]==-Inf || datasheet[i,20]==Inf || length( unique( datasheet[i,5:20] ) )<16){
    # If any of the above condition is true then NOP
  }else{
    # Otherwise include it in the filtered list
    d = rbind(datasheet[i,],d)
  }
  i=i+1
}

# Reusing of variables is always a good practice, just kidding if I don't do it then my R session
# will overflow soon

datasheet = d

# Consider only upto 20 digits after decimal point to see chaotic nature.
# This process will be repeated with lesser precision digits to find maximum possible chaotic values
# I am not sure whether I should do this step or not. Maybe this could be done later
# after the checking chaotic nture for all 0<x<=1. But anyway ! 8GB RAM `kisliye hai ??`

i=1
while(i<=length(datasheet[,1]))
{
  j=5
  while(j<=20){
    datasheet[i,j] = ceiling(datasheet[i,j]*20)/10e20
    j=j+1
  }
  i=i+1
}

# Now remove all the rows where the values are not chaotic
d = datasheet[-1:-35933,]

i=1
while(i<=length(datasheet[,1]))
{
  #if values are chaotic that means nor reoccurence of values
  #Check the number of unique values in the iterations and add it to d
  if(length(unique(datasheet[i,5:20]))==16)
  {
    d = rbind(datasheet[i,],d)
    
  }
  i=i+1
}

datasheet = d

# Write the Observation into file

write.table(d,file="datasheetfilter1",col.names = TRUE)