
difference(){
	union(){


        translate([-10,-26,-10]){
            cube([20,32,20], center=false);
        }         
        translate([-10,-26+11,-19-5]){
            cube([20,21,38+10], center=false);
        }         
        translate([-10+7,1,-19-5]){
            cube([20,5,38+10], center=false);
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

            translate([-1,-5,-35]){
                rotate([0,0,0]){
                    cube([2,10,85], center=false);
                }
            }
        }
        translate([0,0,-33/2+2-5]){
            rotate([0,0,90]){
                cylinder(h=38, r=5, $fn=100, center=false);
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
        translate([-6.5,8.5,-19]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,-19]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         

        translate([-6.5,8.5,19]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,19]){
            rotate([90,0,0]){
                cylinder(h=125, r=3.2/2, $fn=100, center=false);
            }
        }             

	}
}
