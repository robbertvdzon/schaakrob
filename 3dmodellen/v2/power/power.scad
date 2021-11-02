
difference(){
	union(){

        translate([0,0,2]){
            rotate([90,0,0]){
                include <powerconnector.scad>;
            }
        }

        translate([-3,-2,2]){
           cube([3,43,38], center=false);
        }        

        translate([98,-2,2]){
           cube([3,43,38], center=false);
        }        

        translate([0,-2,38]){
           cube([98,43,2], center=false);
        }        



        
	}
	union() {
        translate([27,3.5,37]){
           cube([11,30,4], center=false);
        }        
        translate([27+18,3.5,37]){
           cube([11,30,4], center=false);
        }        
        translate([27+18*2,3.5,37]){
           cube([11,30,4], center=false);
        }        
        translate([27+18*3,3.5,37]){
           cube([11,30,4], center=false);
        }        

        translate([10,2,4]){
            rotate([0,90,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([10,2+37,4+28]){
            rotate([0,90,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }

	}
}
