difference(){
	union(){ 

        difference(){
            union(){ 

                translate([-27.5, -2,0]){
                    linear_extrude(height = 2.8-0.45+0.3, center = false, convexity = 10)
                    scale([1,1]) import(file = "svg/toren_dicht.svg", layer = "plate");
                }                              
            }
            union() {
                translate([0+10,5,1.95-0.45]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0-10,5,1.95-0.45]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0,15,1.95-0.45]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=2.7, $fn=100, center=false);
                    }   
                }
                translate([0,3,0.3]){
                    rotate([0,0,0]){
                        cylinder(h=500, r=11.5, $fn=100, center=false);
                    }   
                }
            }
         }

	}
	union() {
    }
}


 