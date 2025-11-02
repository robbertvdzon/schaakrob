    mirror(v= [0,0,180] ) {
        linear_extrude(height = 0.45, center = false, convexity = 10)
        scale([1,1]) import(file = "svg/pion.svg", layer = "plate");
    }
