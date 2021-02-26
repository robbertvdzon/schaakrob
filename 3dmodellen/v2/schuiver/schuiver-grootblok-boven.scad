mirror([1,0,0]){


difference(){
	union(){
   
        
        translate([0,0,0]){
            rotate([180,90,0]){
                translate([-13,-7.5,-7+7.5]){
                    cube([26,15,35-7.5-7.5], center=false);
                }         
            }
        }  



        translate([0,0,-33/2+1-8-6]){
            rotate([0,0,90]){
                cylinder(h=59, r=7.5, $fn=100, center=false);
            }
        }
        translate([0,-10,-13]){
            rotate([0,0,90]){
                cylinder(h=5, r=5, $fn=100, center=false);
            }
        }
        translate([0,0,25]){
            rotate([90,0,0]){
                cylinder(h=10, r=3, $fn=100, center=false);
            }
        }
        


        translate([0,0,0-5.5-4]){
            rotate([0,90,0]){
                cylinder(h=8+14-3, r=7.5, $fn=100, center=false);
            }
        }
        translate([0,0,0+5.5+4]){
            rotate([0,90,0]){
                cylinder(h=8+14-3, r=7.5, $fn=100, center=false);
            }
        }

        translate([19-3,0,5.5+4]){
            rotate([90,0,0]){
                cylinder(h=10, r=3, $fn=100, center=false);
            }
        }
        translate([19-3,0,-5.5-4]){
            rotate([90,0,0]){
                cylinder(h=10, r=3, $fn=100, center=false);
            }
        }
      


	}
	union() {
//
  
        
        translate([0,0,25]){
            rotate([90,0,0]){
                cylinder(h=12, r=1.2, $fn=100, center=false);
            }
        }


        translate([0,-10,-30]){
            rotate([0,0,90]){
                cylinder(h=50, r=2.5, $fn=100, center=false);
            }
        }


        translate([19-3,-5.5,-30]){
            rotate([0,0,90]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }



        translate([0,0,0]){
            rotate([180,90,0]){
                include <grafiethouder-reverse.scad>;                
            }
        }  

        for (hoek =[0:45:360])
        rotate([0,0,hoek]){

            translate([-1,-5,-30]){
                rotate([0,0,0]){
                    cube([2,10,60], center=false);
                }
            }
        }

        translate([0,0,-33/2+1-10]){
            rotate([0,0,90]){
                cylinder(h=50, r=5, $fn=100, center=false);
            }
        }
        translate([0,0,-35]){
            rotate([0,0,90]){
                cylinder(h=100, r=4, $fn=100, center=false);
            }
        }
        translate([6,0,0-5.5-4]){
            rotate([0,90,0]){
                cylinder(h=200, r=4.4, $fn=100, center=false);
            }
        }
        translate([6,0,0+5.5+4]){
            rotate([0,90,0]){
                cylinder(h=200, r=4.4, $fn=100, center=false);
            }
        }
        
      


        translate([19-3,20,5.5+4]){
            rotate([90,0,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
            }
        }
        translate([19-3,20,-5.5-4]){
            rotate([90,0,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
            }
        }


	}
}


}
