breedte=56;
lengte=70;
hoogte=25;

afstand_tussen_assen=19;
hoogte_verticale_assen=17.5;
hoogte_horizontale_as=6.5;
diameter_buitenste_asgaten=4.75;

hoogte_onderste_magneet_schroefdraad=5;
hoogte_bovenste_magneet_schroefdraad=20;
afstand_tussen_magneten=45;

hoogte_bovenste_middenas=14;
breedte_onderste_middenas=14;


difference(){
	union(){


        // voorbeeld magneet
        translate([4,8.5,0]){
         //  cube([16,16,25], center=false);
        }
        
        // midden-assen
        translate([3,-1.5,hoogte-hoogte_bovenste_middenas]){
           cube([lengte-3,3,hoogte_bovenste_middenas], center=false);
        }
        translate([lengte/2-1.5,-breedte/2+3,hoogte-hoogte_bovenste_middenas]){
           cube([3,breedte-6,hoogte_bovenste_middenas], center=false);
        }
        translate([lengte/2-1.5,-breedte_onderste_middenas/2,0]){
           cube([3,breedte_onderste_middenas,hoogte], center=false);
        }


        // rand
        translate([0,-breedte/2,0]){
           cube([3,breedte,hoogte], center=false);
        }
        translate([lengte,-breedte/2,0]){
           cube([3,breedte,hoogte], center=false);
        }
        translate([0,-breedte/2,0]){
             cube([lengte,3,hoogte], center=false);
        }
        translate([0,breedte/2-3,0]){
             cube([lengte,3,hoogte], center=false);
        }
        

      
        
	}
	union() {

        // as gaten voor schuif assen        
        translate([lengte/2-2.5,0,hoogte_horizontale_as]){
            rotate([0,0,90]){
                include <lager-gat.scad>;                
            }
        }         
        translate([lengte/2-afstand_tussen_assen/2,2.5,hoogte_verticale_assen]){
            rotate([0,90,0]){
                include <lager-gat.scad>;                
            }
        }         
        translate([lengte/2+afstand_tussen_assen/2,2.5,hoogte_verticale_assen]){
            rotate([0,90,0]){
                include <lager-gat.scad>;                
            }
        }         


        // as gaten voor buitenrand      
        translate([-afstand_tussen_assen/2+lengte/2,breedte/2+1,hoogte_verticale_assen]){
            rotate([90,0,0]){
                cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }
        translate([afstand_tussen_assen/2+lengte/2,breedte/2+1,hoogte_verticale_assen]){
            rotate([90,0,0]){
                cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }        

        translate([-afstand_tussen_assen/2+lengte/2,-breedte/2+4,hoogte_verticale_assen]){
            rotate([90,0,0]){
                cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }
        translate([afstand_tussen_assen/2+lengte/2,-breedte/2+4,hoogte_verticale_assen]){
            rotate([90,0,0]){
                cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }        

        translate([-1,0,hoogte_horizontale_as]){
            rotate([0,90,0]){
               cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }        
        translate([lengte-1,0,hoogte_horizontale_as]){
            rotate([0,90,0]){
               cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }    

        // schroefgaten voor pull magneten
        translate([lengte/2-afstand_tussen_magneten/2,50.5,hoogte_onderste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }
        translate([lengte/2-afstand_tussen_magneten/2,50,hoogte_bovenste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }

        translate([lengte/2+afstand_tussen_magneten/2,50.5,hoogte_onderste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }
        translate([lengte/2+afstand_tussen_magneten/2,50,hoogte_bovenste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.5, $fn=100, center=false);
            }
        }



        


	}
}
