
difference(){
	union(){

        translate([51/2-4.5-17-7,(27-4)/2-4.5,0]){
           cube([9,4,12], center=false);
        }        
        translate([51/2-4.5-17-7,(27-4)/2+4.5,0]){
           cube([9,4,12], center=false);
        }        
        translate([51/2-4.5+17+7,(27-4)/2-4.5,0]){
           cube([9,4,12], center=false);
        }        
        translate([51/2-4.5+17+7,(27-4)/2+4.5,0]){
           cube([9,4,12], center=false);
        }        

        translate([-5,7,0]){
           cube([61,13,2], center=false);
        }        
        
        translate([51/2,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=2, r=14.0, $fn=100, center=false);
            }
        }            

//--
        translate([51/2-2.5+25,27/2-8,0]){
           //cube([5,5,10], center=false);
        }        

        translate([51/2+30,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=2, r=6.5, $fn=100, center=false);
            }
        }            

        translate([51/2-2.5-25,27/2-8,0]){
           //cube([5,5,10], center=false);
        }        

        translate([51/2-30,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=2, r=6.5, $fn=100, center=false);
            }
        }            



        translate([51/2,27/2,0]){
            rotate([0,0,0-30]){
                translate([9.5,0,0]){
                    cylinder(h=5, r=3.5, $fn=100, center=false);
                }
            }
            rotate([0,0,120-30]){
                translate([9.5,0,0]){
                    cylinder(h=5, r=3.5, $fn=100, center=false);
                }
            }
            rotate([0,0,240-30]){
                translate([9.5,0,0]){
                    cylinder(h=5, r=3.5, $fn=100, center=false);
                }
            }
        }


        
	}
	union() {
        translate([51/2+30,27/2,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=2, $fn=100, center=false);
            }
        }            
        translate([51/2-30,27/2,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=2, $fn=100, center=false);
            }
        }            
        
        translate([51/2-17-7,30,9]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }
        translate([51/2+17+7,30,9]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }



        translate([51/2,27/2,-1]){
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
