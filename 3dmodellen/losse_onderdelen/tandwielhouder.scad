
difference(){
	union(){
        
        translate([-10,-4,0]){
           cube([20,8,10], center=false);
        }  


        translate([-5,0,0]){
           cube([10,9,10], center=false);
        }  
        translate([-5,-9,0]){
           cube([10,9,10], center=false);
        }  



        translate([0,0,0]){
            rotate([0,0,90]){
                cylinder(h=10, r=7.5, $fn=100, center=false);
            }
        }

        translate([10,0,0]){
            rotate([0,0,90]){
                cylinder(h=10, r=4, $fn=100, center=false);
            }
        }
        translate([-10,0,0]){
            rotate([0,0,90]){
                cylinder(h=10, r=4, $fn=100, center=false);
            }
        }

   
	}
	union() {
        translate([0,0,-1]){
            rotate([0,0,90]){
                cylinder(h=100, r=6.35/2, $fn=100, center=false);
            }
        }
        translate([19.558/2,0,-1]){
            rotate([0,0,90]){
                cylinder(h=15, r=1.7, $fn=100, center=false);
            }
        }
        translate([-19.558/2,0,-1]){
            rotate([0,0,90]){
                cylinder(h=15, r=1.7, $fn=100, center=false);
            }
        }

        translate([0,10,5]){
            rotate([90,0,0]){
                cylinder(h=40, r=1.7, $fn=100, center=false);
            }
        }
	}
}
