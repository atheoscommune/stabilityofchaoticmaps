logisticmap= function(r,x=0){
	return (r*x*(1-x))
}
cols <- colorRampPalette(c("white", "yellow", "red", "black"))(11)
n=100
xmin = -2
xmax = +2
ymin = -2
ymax = 2
gap = 0.008
xaxis = seq(xmin, xmax, by = gap)
yaxis = seq(ymin, ymax, by = gap)
comparray = outer(xaxis, yaxis * 1i, FUN = "+")
oper = matrix(comparray, nrow = length(xaxis), ncol = length(yaxis))
plotmatrix = matrix(0.0, nrow = length(xaxis), ncol = length(yaxis))
for (i in 1:n) {
	index <- which(Mod(oper) <=2)
	oper[index] = logisticmap(r,oper[index])
	plotmatrix[index] = plotmatrix[index] + 1
}
##image(xaxis, yaxis, plotmatrix, col = cols)
image(xaxis, yaxis, plotmatrix, col = cols,ylab=sprintf("xmin=%.3f,xmax=%.3f,ymin=%.3f,ymax=%.3f",xmin,xmax,ymin,ymax),xlab=sprintf("pixelgap=%.3f,r=%.3f,n=%d",gap,r,n))