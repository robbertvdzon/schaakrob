
difference(){
	union(){



        translate([-2.5,-4,0]){
           cube([5,8,13], center=false);
        }        

        translate([0,4,13]){
            rotate([90,0,0]){
                cylinder(h=8, r=2.5, $fn=100, center=false);
            }
        }


        translate([-2.5,-1,13-30]){
           cube([5,2,43], center=false);
        }        

        translate([0,1,13-30]){
            rotate([90,0,0]){
                cylinder(h=2, r=2.5, $fn=100, center=false);
            }
        }

        
	}
	union() {

        translate([-5,-1.5,5]){
           cube([10,3,40], center=false);
        }        



        translate([0,30,13]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }


        translate([0,30,13-30]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.6, $fn=100, center=false);
            }
        }


	}
}
