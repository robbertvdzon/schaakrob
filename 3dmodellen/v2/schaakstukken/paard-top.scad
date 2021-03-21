    mirror(v= [0,0,180] ) {
        mirror([1,0,0]) linear_extrude(height = 0.45, center = false, convexity = 10)
        scale([1,1]) import(file = "svg/paard.svg", layer = "plate");
    }
