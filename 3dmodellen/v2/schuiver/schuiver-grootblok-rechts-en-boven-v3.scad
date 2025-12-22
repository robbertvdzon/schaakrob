
difference(){
	union(){


        translate([-10,-26,-19]){
            cube([20,32,38], center=false);
        }         


        


	}
	union() {
        // plek voor bout midden
        translate([0,20,-0.5]){
            rotate([90,0,0]){
                cylinder(h=100, r=5.7/2, $fn=100, center=false);
            }
        }         
        

        for (hoek =[0:45:360])
        rotate([0,0,hoek]){

            translate([-1,-5,-25]){
                rotate([0,0,0]){
                    cube([2,10,65], center=false);
                }
            }
        }
        translate([0,0,-33/2+2]){
            rotate([0,0,90]){
                cylinder(h=28, r=5, $fn=100, center=false);
            }
        }
        translate([0,0,-35]){
            rotate([0,0,90]){
                cylinder(h=100, r=4, $fn=100, center=false);
            }
        }

        translate([-60,-10-3,-0.5]){
            rotate([0,90,0]){
                cylinder(h=200, r=4, $fn=100, center=false);
            }
        }
        
        // gaten voor riemhouders
        translate([-6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         

        translate([-6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }             

	}
}
