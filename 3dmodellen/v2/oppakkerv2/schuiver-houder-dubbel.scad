
afstand_tussen_verticale_deksel_schroefgaten=35;
afstand_tussen_assen=19;

difference(){
	union(){


        translate([0,afstand_tussen_assen/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }
        translate([0,-afstand_tussen_assen/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }
        translate([0,afstand_tussen_verticale_deksel_schroefgaten/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }
        translate([0,-afstand_tussen_verticale_deksel_schroefgaten/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }


        translate([-2,-afstand_tussen_verticale_deksel_schroefgaten/2,0]){
           cube([4,afstand_tussen_verticale_deksel_schroefgaten,3], center=false);
        }
        
	}
	union() {

        translate([0,afstand_tussen_assen/2,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }
        translate([0,-afstand_tussen_assen/2,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }
        translate([0,afstand_tussen_verticale_deksel_schroefgaten/2,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }
        translate([0,-afstand_tussen_verticale_deksel_schroefgaten/2,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.5, $fn=100, center=false);
            }
        }

        


	}
}
