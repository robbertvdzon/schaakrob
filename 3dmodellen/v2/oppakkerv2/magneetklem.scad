afstand_tussen_magneten=45;
breedte = 51;
hoogte = 16;
//5 mm hoger

difference(){
	union(){
        translate([0,0,0]){
           cube([6,27,7], center=false);
        }        
        translate([45,0,0]){
           cube([6,27,7], center=false);
        }        

        translate([0,0,0]){
           cube([breedte,27,3], center=false);
        }        
        translate([breedte/2,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=hoogte, r=14, $fn=100, center=false);
            }
        }      
        

        
	}
	union() {

        translate([-1,21,3]){
           cube([10,20,30], center=false);
        }        
        translate([43,21,3]){
           cube([10,20,30], center=false);
        }        


        translate([-1,-14,3]){
           cube([10,20,30], center=false);
        }        
        translate([43,-14,3]){
           cube([10,20,30], center=false);
        }        


        translate([44,6,8]){
            rotate([0,90,0]){
                cylinder(h=10, r=5, $fn=100, center=false);
            }
        }        
        translate([44,21,8]){
            rotate([0,90,0]){
                cylinder(h=10, r=5, $fn=100, center=false);
            }
        }        
        translate([-3,6,8]){
            rotate([0,90,0]){
                cylinder(h=10, r=5, $fn=100, center=false);
            }
        }        
        translate([-3,21,8]){
            rotate([0,90,0]){
                cylinder(h=10, r=5, $fn=100, center=false);
            }
        }        





        translate([-1,-24,-1]){
           cube([60,27,30], center=false);
        }        
        translate([-1,25,-1]){
           cube([60,5,30], center=false);
        }        

        translate([breedte/2,27/2,-1]){
            rotate([0,0,0]){
                cylinder(h=hoogte-3, r=13.2, $fn=100, center=false);
            }
        }
        
        
        translate([breedte/2+afstand_tussen_magneten/2,27/2,-1]){
            rotate([0,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }
        translate([breedte/2-afstand_tussen_magneten/2,27/2,-1]){
            rotate([0,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }


        translate([breedte/2,27/2,-1]){
            rotate([0,0,0-30]){
                translate([9.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
            rotate([0,0,120-30]){
                translate([9.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
            rotate([0,0,240-30]){
                translate([9.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
        }


	}
}
