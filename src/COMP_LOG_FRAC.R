
#Generation of fractals from complex logistic map
#Mamta Rani a, * , Rashi Agarwal b
cols <- colorRampPalette(c("white","yellow","red","black"))(5)

# variables
x <- seq(xmin, xmax,by=gap)
y <- seq(ymin, ymax,by=gap)
c <- outer(x,y*1i,FUN="+")
p <- matrix(c, nrow=length(x), ncol=length(y))
k <- matrix(0.0, nrow=length(x), ncol=length(y))
for (rep in 1:n) {
  index <- which(Mod(p) <= (2/B))
  px = Re(p[index])
  py = Im((p[index]))
  pxt = B*(rx*(px - px*px + py*py)- ry*(py - 2*px*py)) + (1-B)*px
  pyt = B*(rx*(py-2*px*py) + ry*(px - px*px+py*py)) + (1-B)*py
  px = pxt
  py = pyt
  p[index] = px + py*1i;
  k[index] <- k[index] + 1
}
image(x, y, k, col = cols,ylab=sprintf("xmin=%.2f,xmax=%.3f,ymin=%.3f,ymax=%.3f",xmin,xmax,ymin,ymax),xlab=sprintf("pixelgap=%.3f,rx=%.3f,ry=%.3f,n=%d",gap,rx,ry,n))
##image(x,y,k, col=cols)
