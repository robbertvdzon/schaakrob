
difference(){
	union(){



        translate([-5,0,0]){
           cube([53,10,2], center=false);
        }        
        translate([-5,10,0]){
           cube([53,2,10], center=false);
        }        
        translate([-5,10,10]){
           cube([53,10,2], center=false);
        }        




        
	}
	union() {
        translate([0,5,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([43,5,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([0,15,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([43,15,-1]){
            rotate([0,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }

	}
}
