
difference(){
	union(){

        translate([26.5-3,0,0]){
            rotate([0,0,0]){
                mirror([1,0,0]){
                    include <powerconnector.scad>;
                }
            }
        }

        translate([-18,-4,0]){
           cube([150,3,10], center=false);
        }        


        translate([-18,-4,0]){
           cube([3,44,38], center=false);
        }        
        translate([-15,37,0]){
           cube([13,3,38], center=false);
        }        
        translate([122,37,0]){
           cube([13,3,38], center=false);
        }        
        translate([53,36,0]){
           cube([13,4,38], center=false);
        }        

        translate([132,-4,0]){
           cube([3,44,38], center=false);
        }        

        translate([22,-4,0]){
           cube([50+60,44,2], center=false);
        }        
        translate([-15,-4,0]){
           cube([12,44,2], center=false);
        }        


        
	}
	union() {

        translate([27,3.5,-1]){
           cube([11,30,4], center=false);
        }        
        translate([27+18,3.5,-1]){
           cube([11,30,4], center=false);
        }        
        translate([27+18*2,3.5,-1]){
           cube([11,30,4], center=false);
        }        

        translate([-6,50,32]){
            rotate([90,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([125,50,32]){
            rotate([90,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }
        translate([60,50,32]){
            rotate([90,0,0]){
               cylinder(h=999, r=1.5, $fn=100, center=false);
            }
        }

        translate([70,20,5]){
            rotate([0,90,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
        translate([70,25,5]){
            rotate([0,90,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
        translate([70,30,5]){
            rotate([0,90,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }
        translate([70,35,5]){
            rotate([0,90,0]){
               cylinder(h=999, r=2, $fn=100, center=false);
            }
        }

	}
}
