mirror(v= [0,180,0] ) {
    
difference(){
	union(){
        
        translate([-4,3,-23]){
           cube([20,45,5], center=false);
        }
        translate([-4,3,-23]){
           cube([34+8,25,5], center=false);
        }
        translate([0-4,-11,-23]){
           cube([5,81,68], center=false);
        }

        translate([0,45,-16]){
           cube([25,5,50.5], center=false);
        }
        translate([0,45,-16]){
           cube([45,5,50.5-30], center=false);
        }
        translate([-4,45,-23]){
           cube([55,25,8], center=false);
        }

        translate([47.5,47.5,22]){
            rotate([90,0,0]){
           //     cylinder(h=5, r=12.5, $fn=100, center=true);
            }
        }



        translate([0,45,-16+25]){
           cube([20,25,5], center=false);
        }
        translate([0,45,-16+25+20.5]){
           cube([20,25,5], center=false);
        }

        translate([0,45+20,-16+25]){
           cube([62,5,50.5-25], center=false);
        }
        translate([47.5+12,47.5+20,22]){
            rotate([90,0,0]){
                cylinder(h=5, r=12.5, $fn=100, center=true);
            }
        }

        translate([40+8+3,58-10,-18-1]){
            rotate([0,0,90]){
                cylinder(h=8, r=7, $fn=100, center=true);
            }
        }

        translate([57/2+8,32/2,-47+26.5]){
            rotate([0,0,90]){
                cylinder(h=5, r=11, $fn=100, center=true);
            }
        }


       translate([40,47.5,-13-2.5]){
            rotate([0,0,90]){
                cylinder(h=15, r=4, $fn=100, center=true);
            }
        }

 
	}
	union() {
        
        translate([0-5,12,-18-5+10]){
           cube([7,30,50], center=false);
        }        
        
       translate([40,47.5,-14]){
            rotate([0,0,90]){
                cylinder(h=22, r=1.2, $fn=100, center=true);
            }
        }
       translate([40,47.5,-35]){
            rotate([0,0,90]){
                cylinder(h=15, r=2, $fn=100, center=false);
            }
        }

        translate([28,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=6, $fn=100, center=true);
            }
        }
        translate([18+8+3+8,58,-16]){
            rotate([0,0,90]){
                cylinder(h=15, r=7, $fn=100, center=true);
            }
        }

        translate([5,44,-12]){
           cube([23,10,12], center=false);
        }

        translate([10,51,-24]){
           cube([28,14,16], center=false);
        }

        translate([40,48,-6]){
            rotate([90,0,0]){
                cylinder(h=10, r=4, $fn=100, center=true);
            }
        }
        translate([51,-5,-19]){
            rotate([90,0,0]){
                cylinder(h=100, r=1.2, $fn=100, center=true);
            }
        }
        translate([40+8+3,58-10,-20]){
            rotate([0,0,90]){
                cylinder(h=20, r=4, $fn=100, center=true);
            }
        }
        translate([47/2+5+8+3+8,57-9,20+8-6]){
            rotate([90,0,0]){
                cylinder(h=8, r=7.5, $fn=100, center=true);
            }
        }
        translate([47/2+24,57-9+20,20+8-6]){
            rotate([90,0,0]){
                cylinder(h=8, r=7.5, $fn=100, center=true);
            }
        }
        translate([47/2+24-36.6/2,57-9+20,20+8-6]){
            rotate([90,0,0]){
                cylinder(h=8, r=2.5, $fn=100, center=true);
            }
        }
        translate([47/2+24+36.6/2,57-9+20,20+8-6]){
            rotate([90,0,0]){
                cylinder(h=8, r=2.5, $fn=100, center=true);
            }
        }
        

        translate([-4,5+2,-33+5-2+20]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,62,40]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }
        translate([0,7,40]){
            rotate([0,90,0]){
                cylinder(h=200, r=3, $fn=100, center=true);
            }
        }

        translate([57/2+8,32/2,-47+25]){
            rotate([0,0,90]){
                cylinder(h=100, r=7.5, $fn=100, center=true);
            }
        }

	}
}
}