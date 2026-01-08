

difference(){
	union(){          

        translate([-16.5,-30,10]){
              cube([33,20,3], center=false);
        }

        translate([9,-30+2.5+7,13]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
        translate([-9,-30+2.5+7,13]){
            rotate([0,0,90]){
                cylinder(h=2, r1=3.5, r2=1.5, $fn=100, center=false);
            }
        }           
   

	}
	union() {


        translate([9.2,-30+2.5+7,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           
        translate([-9.2,-30+2.5+7,-2]){
            rotate([0,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }           


        
        
	}
}
