
mirror(v= [0,180,0] ) {

difference(){
	union(){
        translate([0,0,-30]){
           cube([50,45,4], center=false);
        }
        translate([0,0,-30]){
           cube([5,45,47], center=false);
        }
        translate([0,45,-28]){
           cube([45,5,32], center=false);
        }
        translate([0,45,-28]){
           cube([60,5,17], center=false);
        }
        translate([0,65,-28]){
           cube([60,5,13], center=false);
        }
        translate([0,45,-30]){
           cube([60,25,2], center=false);
        }
        translate([0,45,0]){
           cube([53,25,4], center=false);
        }
        translate([0,45,-15]){
           cube([5,25,32], center=false);
        }




        translate([40+8+3,58,-13+15]){
            rotate([0,0,90]){
                cylinder(h=4, r=6, $fn=100, center=true);
            }
        }
        translate([40,47.5,-6]){
            rotate([90,0,0]){
                cylinder(h=5, r=6, $fn=100, center=true);
            }
        }
                

/*        
        translate([47/2+5,42/2,-6]){
            rotate([0,0,90]){
                color("red")  
                cylinder(h=10, r=21, $fn=100, center=true);
            }
        }
        translate([47/2+5,42/2,40]){
            rotate([0,0,90]){
                color("red") 
                cylinder(h=100, r=4, $fn=100, center=true);
            }
        }
        
        translate([40,100,-6]){
            rotate([90,0,0]){
                color("red") 
                cylinder(h=100, r=4, $fn=100, center=true);
            }
        }

        translate([47/2+5+8+3-14,57,-20]){
            rotate([90,0,0]){
                color("red") 
                cylinder(h=10, r=7, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3+14,57,-20]){
            rotate([90,0,0]){
                color("red") 
                cylinder(h=10, r=7, $fn=100, center=true);
            }
        }

        translate([40+8+3,58,105]){
            rotate([0,0,90]){
                color("red") 
                cylinder(h=100, r=4, $fn=100, center=true);
            }
        }
        
*/       
	}
	union() {

        translate([47/2+5+8+3-14,57,-20]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.3, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3+14,57,-20]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.3, $fn=100, center=true);
            }
        }

        translate([47/2+5,42/2,-10]){
            rotate([0,0,90]){
                cylinder(h=50, r=7.5, $fn=100, center=true);
            }
        }


        translate([20,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=6, $fn=100, center=true);
            }
        }
        translate([5,44,-12]){
           cube([15,10,12], center=false);
        }
        
        
        //-
        translate([18+8+3,58,-15+15]){
            rotate([0,0,90]){
                cylinder(h=10, r=7, $fn=100, center=true);
            }
        }
        translate([10,51,-20+15]){
           cube([20,14,12], center=false);
        }
        translate([40+8+3,58,-15+15]){
            rotate([0,0,90]){
                cylinder(h=10, r=4, $fn=100, center=true);
            }
        }



        translate([40,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=4, $fn=100, center=true);
            }
        }

        translate([0,5,12]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }
        translate([0,65,12]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }
        translate([0,5,-20]){
            rotate([0,90,0]){
                cylinder(h=200, r=1.5, $fn=100, center=true);
            }
        }

        translate([47/2+5,42/2,4]){
            rotate([0,0,90]){
                cylinder(h=9, r=13.5, $fn=100, center=true);
            }
        }

	}
}
}