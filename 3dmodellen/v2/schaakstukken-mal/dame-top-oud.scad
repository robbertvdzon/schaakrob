    mirror(v= [0,0,180] ) {
        linear_extrude(height = 0.45, center = false, convexity = 10)
        scale([0.095,0.095]) import(file = "svg/dame.svg", layer = "plate");
    }
