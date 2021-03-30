breedte=73;
lengte=73;
hoogte=25;

hoogte_verticale_asbuis=14;
hoogte_horizontale_asbuis=3;

afstand_tussen_assen=19;
hoogte_verticale_assen=17.5;
hoogte_horizontale_as=6.5;
diameter_buitenste_asgaten=5.25;

hoogte_onderste_magneet_schroefdraad=5;
hoogte_bovenste_magneet_schroefdraad=14;
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

afstand_tussen_horizontale_deksel_schroefgaten=15;
afstand_tussen_verticale_deksel_schroefgaten=35;

difference(){
	union(){


        // gat voor sleepcontact onderste as
        translate([lengte/2-afstand_tussen_magneten/2,0,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte_horizontale_asbuis, r=buitensleepcontact/2, $fn=100, center=false);
            }
        }

        // gat voor sleepcontact bovenste assen
        translate([lengte/2-afstand_tussen_assen/2,-buitensleepcontact/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte_verticale_asbuis, r=buitensleepcontact/2, $fn=100, center=false);
            }
        }
        translate([lengte/2+afstand_tussen_assen/2,-buitensleepcontact/2,0]){
            rotate([0,0,90]){
                cylinder(h=hoogte_verticale_asbuis, r=buitensleepcontact/2, $fn=100, center=false);
            }
        }



        // gaten voor draden
        translate([buitendiameter_draden/2,breedte/2-buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }
        translate([buitendiameter_draden/2,-breedte/2+buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }

        translate([lengte-buitendiameter_draden/2,breedte/2-buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }
        translate([lengte-buitendiameter_draden/2,-breedte/2+buitendiameter_draden/2,0]){
            rotate([0,0,90]){
                cylinder(h=3, r=buitendiameter_draden/2, $fn=100, center=false);
            }
        }


        // rand
        translate([0,-breedte/2+buitendiameter_draden/2,0]){
           cube([lengte,breedte-buitendiameter_draden,3], center=false);
        }
        translate([2,-breedte/2+binnendiameter_draden/2,0]){
           cube([lengte-4,breedte-binnendiameter_draden,3], center=false);
        }

        translate([0,-breedte/2+buitendiameter_draden/2,0]){
           cube([3,breedte-buitendiameter_draden,3], center=false);
        }
        translate([lengte-3,-breedte/2+buitendiameter_draden/2,0]){
           cube([3,breedte-buitendiameter_draden,3], center=false);
        }
        translate([buitendiameter_draden/2,-breedte/2,0]){
            cube([lengte-buitendiameter_draden,3,3], center=false);
        }
        translate([buitendiameter_draden/2,breedte/2-3,0]){
           cube([lengte-buitendiameter_draden,3,3], center=false);
        }       
      
        
	}
	union() {

        // voorbeeld magneet
        translate([lengte/2-afstand_tussen_magneten/2-18/2,breedte/2-18-8,-1]){
           cube([18,18,25], center=false);
        }
        translate([lengte/2+afstand_tussen_magneten/2-18/2,breedte/2-18-8,-1]){
           cube([18,18,25], center=false);
        }
        translate([lengte/2-afstand_tussen_magneten/2-18/2,-breedte/2+8,-1]){
           cube([18,18,25], center=false);
        }
        translate([lengte/2+afstand_tussen_magneten/2-18/2,-breedte/2+8,-1]){
           cube([18,18,25], center=false);
        }

        // gat voor sleepcontact onderste as
        translate([lengte/2-afstand_tussen_magneten/2,0,-1]){
            rotate([0,0,90]){
                cylinder(h=99, r=binnensleepcontact/2, $fn=100, center=false);
            }
        }
        
        // gat voor sleepcontact bovenste assen
        translate([lengte/2-afstand_tussen_assen/2,-buitensleepcontact/2,-1]){
            rotate([0,0,90]){
                cylinder(h=992, r=binnensleepcontact/2, $fn=100, center=false);
            }
        }
        translate([lengte/2+afstand_tussen_assen/2,-buitensleepcontact/2,-1]){
            rotate([0,0,90]){
                cylinder(h=99, r=binnensleepcontact/2, $fn=100, center=false);
            }
        }

        // schroefgat voor sleepcontact onderste as
        translate([lengte/2-afstand_tussen_magneten/2+3,afstand_tussen_horizontale_deksel_schroefgaten/2,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.2, $fn=100, center=false);
            }
        }
        translate([lengte/2-afstand_tussen_magneten/2-3,-afstand_tussen_horizontale_deksel_schroefgaten/2,-1]){
            rotate([0,0,90]){
                cylinder(h=5, r=1.2, $fn=100, center=false);
            }
        }

        // schroefgat voor sleepcontact bovenste assen
        translate([lengte/2-afstand_tussen_verticale_deksel_schroefgaten/2,-buitensleepcontact/2,-1]){
            rotate([0,0,90]){
                cylinder(h=992, r=1.2, $fn=100, center=false);
            }
        }        
        translate([lengte/2+afstand_tussen_verticale_deksel_schroefgaten/2,-buitensleepcontact/2,-1]){
            rotate([0,0,90]){
                cylinder(h=992, r=1.2, $fn=100, center=false);
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


        


	}
}
