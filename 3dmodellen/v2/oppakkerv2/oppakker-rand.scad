breedte=73;
lengte=73;
hoogte=25;

afstand_tussen_assen=19;
hoogte_verticale_assen=17.5;
hoogte_horizontale_as=6.5;
diameter_buitenste_asgaten=5.25;

hoogte_onderste_magneet_schroefdraad=5;
hoogte_bovenste_magneet_schroefdraad=20;
afstand_tussen_magneten=45;

hoogte_bovenste_middenas=14;
breedte_onderste_middenas=14;

afstand_tussen_montage_gaten_deksel=10;
buitendiameter_montage_gaten_deksel=6;

buitendiameter_draden=9;
binnendiameter_draden=5;

buitensleepcontact=12;
binnensleepcontact=8;

breedte_verdikking=8;

difference(){
	union(){


        // voorbeeld magneet
        translate([lengte/2-afstand_tussen_magneten/2-18/2,breedte/2-18-8,0]){
//           cube([18,18,25], center=false);
        }
        translate([lengte/2+afstand_tussen_magneten/2-18/2,breedte/2-18-8,0]){
//           cube([18,18,25], center=false);
        }
        translate([lengte/2-afstand_tussen_magneten/2-18/2,-breedte/2+8,0]){
//           cube([18,18,25], center=false);
        }
        translate([lengte/2+afstand_tussen_magneten/2-18/2,-breedte/2+8,0]){
//           cube([18,18,25], center=false);
        }


        // gat voor sleepcontact onderste as
        translate([lengte/2-afstand_tussen_magneten/2,0,hoogte-hoogte_bovenste_middenas]){
            rotate([0,0,90]){
                cylinder(h=hoogte_bovenste_middenas, r=buitensleepcontact/2, $fn=100, center=false);
            }
        }

        // gaten voor montage deksel boven en onder
        translate([3,+buitendiameter_montage_gaten_deksel/2+afstand_tussen_montage_gaten_deksel/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_montage_gaten_deksel/2, $fn=100, center=false);
            }
        }
        translate([3,-buitendiameter_montage_gaten_deksel/2-afstand_tussen_montage_gaten_deksel/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_montage_gaten_deksel/2, $fn=100, center=false);
            }
        }

        translate([lengte-3,+buitendiameter_montage_gaten_deksel/2+afstand_tussen_montage_gaten_deksel/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_montage_gaten_deksel/2, $fn=100, center=false);
            }
        }
        translate([lengte-3,-buitendiameter_montage_gaten_deksel/2-afstand_tussen_montage_gaten_deksel/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_montage_gaten_deksel/2, $fn=100, center=false);
            }
        }

        // gaten voor draden
        translate([buitendiameter_draden/2,breedte/2-buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }
        translate([buitendiameter_draden/2,-breedte/2+buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }

        translate([lengte-buitendiameter_draden/2,breedte/2-buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }
        translate([lengte-buitendiameter_draden/2,-breedte/2+buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }


        
        // midden-assen
        translate([0,-1.5,hoogte-hoogte_bovenste_middenas]){
           cube([lengte,3,hoogte_bovenste_middenas], center=false);
        }
        translate([lengte/2-1.5,-breedte/2+3,hoogte-hoogte_bovenste_middenas]){
           cube([3,breedte-6,hoogte_bovenste_middenas], center=false);
        }
        translate([lengte/2-1.5,-breedte_onderste_middenas/2,0]){
           cube([3,breedte_onderste_middenas,hoogte], center=false);
        }


        // rand
        translate([0,-breedte/2+buitendiameter_draden/2,0]){
           cube([3,breedte-buitendiameter_draden,hoogte], center=false);
        }
        translate([lengte-3,-breedte/2+buitendiameter_draden/2,0]){
           cube([3,breedte-buitendiameter_draden,hoogte], center=false);
        }
        translate([buitendiameter_draden/2,-breedte/2,0]){
            cube([lengte-buitendiameter_draden,3,hoogte], center=false);
        }
        translate([buitendiameter_draden/2,breedte/2-3,0]){
           cube([lengte-buitendiameter_draden,3,hoogte], center=false);
        }
        
        // verdikking
        translate([lengte/2-afstand_tussen_magneten/2-breedte_verdikking/2,-breedte/2,0]){
             cube([breedte_verdikking,8,hoogte], center=false);
        }
        translate([lengte/2+afstand_tussen_magneten/2-breedte_verdikking/2,-breedte/2,0]){
             cube([breedte_verdikking,8,hoogte], center=false);
        }
        

        translate([lengte/2-afstand_tussen_magneten/2-breedte_verdikking/2,+breedte/2-8,0]){
             cube([breedte_verdikking,8,hoogte], center=false);
        }
        translate([lengte/2+afstand_tussen_magneten/2-breedte_verdikking/2,+breedte/2-8,0]){
             cube([breedte_verdikking,8,hoogte], center=false);
        }

      
        
	}
	union() {

        // gat voor sleepcontact onderste as
        translate([lengte/2-afstand_tussen_magneten/2,0,hoogte-hoogte_bovenste_middenas-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte_bovenste_middenas+2, r=binnensleepcontact/2, $fn=100, center=false);
            }
        }

        // gaten voor montage deksel boven en onder
        translate([0+3,+buitendiameter_montage_gaten_deksel/2+afstand_tussen_montage_gaten_deksel/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=1.5, $fn=100, center=false);
            }
        }
        translate([0+3,-buitendiameter_montage_gaten_deksel/2-afstand_tussen_montage_gaten_deksel/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=1.5, $fn=100, center=false);
            }
        }

        translate([lengte-3,+buitendiameter_montage_gaten_deksel/2+afstand_tussen_montage_gaten_deksel/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=1.5, $fn=100, center=false);
            }
        }
        translate([lengte-3,-buitendiameter_montage_gaten_deksel/2-afstand_tussen_montage_gaten_deksel/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=1.5, $fn=100, center=false);
            }
        }


        // gaten voor draden
        translate([buitendiameter_draden/2,breedte/2-buitendiameter_draden/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=binnendiameter_draden/2, $fn=100, center=false);
            }
        }
        translate([buitendiameter_draden/2,-breedte/2+buitendiameter_draden/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=binnendiameter_draden/2, $fn=100, center=false);
            }
        }

        translate([lengte-buitendiameter_draden/2,breedte/2-buitendiameter_draden/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=binnendiameter_draden/2, $fn=100, center=false);
            }
        }
        translate([lengte-buitendiameter_draden/2,-breedte/2+buitendiameter_draden/2,-1]){
            rotate([0,0,90]){
                cylinder(h=hoogte+2, r=binnendiameter_draden/2, $fn=100, center=false);
            }
        }

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
        translate([lengte-4,0,hoogte_horizontale_as]){
            rotate([0,90,0]){
               cylinder(h=5, r=diameter_buitenste_asgaten, $fn=100, center=false);
            }
        }    

        // schroefgaten voor pull magneten
        translate([lengte/2-afstand_tussen_magneten/2,breedte/2+1,hoogte_onderste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=15, r=1.5, $fn=100, center=false);
            }
        }
        translate([lengte/2-afstand_tussen_magneten/2,breedte/2+1,hoogte_bovenste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=15, r=1.5, $fn=100, center=false);
            }
        }

        translate([lengte/2-afstand_tussen_magneten/2,-breedte/2+10,hoogte_onderste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=15, r=1.5, $fn=100, center=false);
            }
        }
        translate([lengte/2-afstand_tussen_magneten/2,-breedte/2+10,hoogte_bovenste_magneet_schroefdraad]){
            rotate([90,0,0]){
                cylinder(h=15, r=1.5, $fn=100, center=false);
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
