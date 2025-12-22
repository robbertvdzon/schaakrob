
difference(){
	union(){

        translate([10,-26,-19]){
            rotate([0,-90,0]){
                linear_extrude(height = 20)
                    polygon(points=[
                        [0,0],
                        [38,0],
                        [19,15]
                    ]);
            }            
        }

        translate([-10,-26,-19]){
        //    cube([20,2,38], center=false);
            
        }         

        


	}
	union() {
        
        // gaten voor riemhouders
        translate([-6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,-14.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         

        translate([-6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,13.5]){
            rotate([90,0,0]){
                cylinder(h=125, r=4.7/2, $fn=100, center=false);
            }
        }             



	}
}
