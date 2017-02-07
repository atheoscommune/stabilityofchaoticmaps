#Generation of fractals from complex logistic map
#Mamta Rani a, * , Rashi Agarwal b, Complex logistics map and mann iterations

px = 0.01

  rx =3.57 #input from user
  ry =0#input from user
  B = 1#input from user
py = 0
  pxt = 0
  pyt = 0
  vec = 1:100
  vec[1] = sqrt(px**2 + py**2)
  for (i in 1:100) {
    pxt = B*(rx*(px - px*px + py*py)- ry*(py - 2*px*py)) + (1-B)*px
    pyt = B*(rx*(py-2*px*py) + ry*(px - px*px+py*py)) + (1-B)*py
    py = pyt
    px = pxt
    vec[i] = (sqrt(px**2 + py**2))
  }
  
  plot(vec,type="b")
  