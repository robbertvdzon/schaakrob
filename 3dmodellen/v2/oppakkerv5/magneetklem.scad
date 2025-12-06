//
difference(){
	union(){


        translate([-5,7,0]){
           cube([61,13,5], center=false);
        }        
        
        translate([51/2,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=5, r=15.0, $fn=100, center=false);
            }
        }            


        translate([51/2+30,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=18, r=6.5, $fn=100, center=false);
            }
        }            

        translate([51/2-30,27/2,0]){
            rotate([0,0,0]){
                cylinder(h=18, r=6.5, $fn=100, center=false);
            }
        }            




        
	}
	union() {

        translate([51/2-30,27/2,15]){
            rotate([0,0,0]){
                cylinder(h=3, r1=3, r2=1.2,  $fn=100, center=false);
            }
        }            
        translate([51/2+30,27/2,15]){
            rotate([0,0,0]){
                cylinder(h=3, r1=3, r2=1.2,  $fn=100, center=false);
            }
        }            
        translate([51/2+30,27/2,1.5]){
            rotate([0,0,0]){
                cylinder(h=13.7, r=3, $fn=100, center=false);
            }
        }            


        translate([51/2-30,27/2,1.5]){
            rotate([0,0,0]){
                cylinder(h=13.7, r=3, $fn=100, center=false);
            }
        }            


        translate([51/2+21,32,3]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }
        translate([51/2+-21,32,3]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }

        
//        translate([51/2,27/2,4]){
//            rotate([0,0,0]){
//                cylinder(h=5, r=15.5, $fn=100, center=false);
//            }
//        }            
        
        // deze
        translate([51/2+30,27/2,-0.1]){
            rotate([0,0,0]){
                cylinder(h=20, r=2, $fn=100, center=false);
            }
        }            
        translate([51/2-30,27/2,-0.1]){
            rotate([0,0,0]){
                cylinder(h=20, r=2, $fn=100, center=false);
            }
        }            
        

        translate([51/2-4.5+17+7-4,(27-4)/2+4.5-4.5,-2]){
           cube([10,4,22], center=false);
        }        

        translate([51/2-4.5-17-7+3,(27-4)/2+4.5-4.5,-2]){
           cube([10,4,22], center=false);
        }        


        translate([51/2,27/2,-1]){
            rotate([0,0,0-30]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
            rotate([0,0,120-30]){
                translate([11.5,0,0]){
                   cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
            rotate([0,0,240-30]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=1.5, $fn=100, center=false);
                }
            }
        }

        translate([51/2,27/2,3]){
            rotate([0,0,0-30]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=3, $fn=100, center=false);
                }
            }
            rotate([0,0,120-30]){
                translate([11.5,0,0]){
                   cylinder(h=500, r=3, $fn=100, center=false);
                }
            }
            rotate([0,0,240-30]){
                translate([11.5,0,0]){
                    cylinder(h=500, r=3, $fn=100, center=false);
                }
            }
        }



	}
}
