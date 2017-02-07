#Reference paper : Julia sets and Mandelbrot sets in Noor orbit
#Ashish a , Mamta Rani b, ⇑ , Renu Chugh a

quadratic = function(z, c = 0) {
	return (z * z + c)
	
}

calcz = function(G, z, c = 0) {
	return ((1 - G) * z + G * quadratic(z, c))
}

calcy = function(B, G, z, c = 0) {
	return = ((1 - B) * z + B * quadratic(calcz(G, z, c),c))
}

calcx = function(A, B, G, z, c = 0) {
	return ((1 - A) * z + A * quadratic(calcy(B, G, z, c),c))
}
## n=10
## c=0.05+0.3i
## A=B=G=0.3
## gap=0.1
cols <- colorRampPalette(c("white", "yellow", "red", "black"))(11)
xaxis = seq(xmin, xmax, by = gap)
yaxis = seq(ymin, ymax, by = gap)
comparray = outer(xaxis, yaxis * 1i, FUN = "+")
oper = matrix(comparray, nrow = length(xaxis), ncol = length(yaxis))
plotmatrix = matrix(0.0, nrow = length(xaxis), ncol = length(yaxis))

mvc = Mod(c)
mvA = 2 / Mod(A)
mvB = 2 / Mod(B)
mvG = 2 / Mod(G)


if (A != 0 && B != 0 && G != 0) {
	for (i in 1:n) {
		
		index <- which(Mod(oper) <= max(mvc, mvG, mvB, mvA))
		oper[index] = calcx(A, B, G, oper[index], c)
		plotmatrix[index] = plotmatrix[index] + 1
	}
} else{
	for (i in 1:n) {
		index <- which(Mod(oper) != Inf)
		oper[index] = quadratic(oper[index], c)
		plotmatrix[index] = plotmatrix[index] + 1
	}
}

image(xaxis, yaxis, plotmatrix, col = cols)
#image(xaxis, yaxis, plotmatrix, col = cols,xlab=sprintf("pixelgap=%.3f,r=%.3f,n=%d",gap,Mod(r),n),ylab=sprintf("xmin=%.3f,xmax=%.3f,ymin=%.3f,ymax=%.3f,A=%.2f,B=%.2f,G=%.2f",xmin,xmax,ymin,ymax,A,B,G))


