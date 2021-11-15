
difference(){
	union(){
        

        translate([10.5,-2,0]){
            cube([7,5,8], center=false);
        }         
        translate([32.5,-2,0]){
            cube([7,5,8], center=false);
        }         


        translate([-2.5,36,0]){
            cube([55,3,5], center=false);
        }         

        translate([0,0,0]){
            cube([50,3,8], center=false);
        }         
        translate([00,36,0]){
            cube([50,3,8], center=false);
        }         
        translate([35/2,0,0]){
            cube([15,3,14], center=false);
        }         
        translate([35/2,36,0]){
            cube([15,3,14], center=false);
        }         

        translate([-4.5,0,0]){
            cube([5,39,2], center=false);
        }         
        translate([49.5,0,0]){
            cube([5,39,2], center=false);
        }         


        translate([25,3,40-1-20-3-2]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }       
        translate([5,3,40-3-20-7-2]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }       
        translate([45,3,40-3-20-7-2]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }   




        translate([25,39,40-1-20-3-2]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }       
        translate([5,39,40-3-20-7-2]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }       
        translate([45,39,40-3-20-7-2]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }   


        // schroefgaten
        translate([-3,35/2+10,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }
    
        translate([53,35/2+10,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }     
   

	}
	union() {

        // assen
        translate([14,30,5]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.2, $fn=100, center=false);
            }
        }       
        translate([36,30,5]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.2, $fn=100, center=false);
            }
        }       

  
        // assen
        translate([25,40,40-1-20-3-2]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.2, $fn=100, center=false);
            }
        }       
        translate([5,40,40-3-20-7-2]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.2, $fn=100, center=false);
            }
        }       
        translate([45,40,40-3-20-7-2]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.2, $fn=100, center=false);
            }
        }   
        // schroefgaten
        translate([-3,35/2+10,-1]){
            rotate([0,0,90]){
                cylinder(h=99, r=2, $fn=100, center=false);
            }
        }
    
        translate([53,35/2+10,-1]){
            rotate([0,0,90]){
                cylinder(h=99, r=2, $fn=100, center=false);
            }
        }     
        

  

	}
}
