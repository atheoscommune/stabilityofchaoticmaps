#Reference paper : Julia sets and Mandelbrot sets in Noor orbit
#Ashish a , Mamta Rani b, ⇑ , Renu Chugh a

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
cols <- colorRampPalette(c("white", "yellow", "red", "black"))(11)
xaxis = seq(xmin, xmax, by = gap)
yaxis = seq(ymin, ymax, by = gap)
comparray = outer(xaxis, yaxis * 1i, FUN = "+")
oper = matrix(comparray, nrow = length(xaxis), ncol = length(yaxis))
plotmatrix = matrix(0.0, nrow = length(xaxis), ncol = length(yaxis))




for (i in 1:n) {
	index <- which(Mod(oper) != Inf )
	oper[index] = calcx(A, B, G, oper[index], r)
	plotmatrix[index] = plotmatrix[index] + 1
}


image(xaxis, yaxis, plotmatrix, col = cols,xlab=sprintf("pixelgap=%.3f,r=%.3f,n=%d,xmin=%.3f,xmax=%.3f",gap,Mod(r),n,xmin,xmax),ylab=sprintf("ymin=%.3f,ymax=%.3f,A=%.2f,B=%.2f,G=%.2f",ymin,ymax,A,B,G))

