breedte = 22.7;
hele_breedte = 32.5;
diepte = 27;
dikte_rand = 2.5;
marge = 0.2;

extra_lengte_voor_en_achter = 18;

motor_breedte = hele_breedte+marge*2;
motor_lengte = diepte+marge*2;

breedte_ruimte_tussen_2_motoren = 2;
lengte_ruimte_tussen_2_motoren =2;
rand_marge_kleiner = 0.01;

afstand_rand_verdikking = 5;
hoogte_as = 20;
hoogte_verdikking = 7;

extra_lengte=7;
extra_breedte = 10;

afstand_tussen_print_gaten = 89;

difference(){
	union(){


        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30,diepte/2+9.5,0]){
            rotate([0,0,0]){
               cylinder(h=hoogte_as , r=3.5, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30,diepte/2+9.5,0]){
            rotate([0,0,0]){
                cylinder(h=hoogte_as , r=3.5, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-9.5,0]){
            rotate([0,0,0]){
                cylinder(h=hoogte_as , r=3.5, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-9.5,0]){
            rotate([0,0,0]){
                cylinder(h=hoogte_as , r=3.5, $fn=100, center=false);
            }
        }        

        
// basis plaat        
        translate([
            -motor_breedte/2+rand_marge_kleiner-extra_breedte,
            -motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+rand_marge_kleiner-extra_lengte_voor_en_achter-extra_lengte/2,
            0
        ]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren-rand_marge_kleiner*2+extra_breedte*2,
            motor_lengte*2+lengte_ruimte_tussen_2_motoren-rand_marge_kleiner*2+extra_lengte_voor_en_achter*2+extra_lengte,
            hoogte_verdikking], center=false);
        }         
        


//versteviging voor ashouders
        translate([-motor_breedte/2+rand_marge_kleiner,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+rand_marge_kleiner-extra_lengte_voor_en_achter,0]){
            cube([7.5,18,15], center=false);
        }         


        translate([43.8,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+rand_marge_kleiner-extra_lengte_voor_en_achter,0]){
            cube([7.5,18,15], center=false);
        }         


        translate([-motor_breedte/2+rand_marge_kleiner,13.5,0]){
            cube([7.5,18,15], center=false);
        }         


        translate([43.8,13.5,0]){
            cube([7.5,18,15], center=false);
        }         

// deksel
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-25,-64.6,0]){
            cube([8,3,15], center=false);
        }         

        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2+25-8,-64.6,0]){
            cube([8,3,15], center=false);
        }         

        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-25,32.2,0]){
            cube([8,3,15], center=false);
        }         

        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2+25-8,32.2,0]){
            cube([8,3,15], center=false);
        }              

        

	}
	union() {

        // schroefgaten
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-2,diepte/2-3-10,-1]){
            rotate([0,0,0]){
                cylinder(h=17, r=1.2, $fn=100, center=false);
            }
        }           
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren,-diepte-2-diepte/2+3+10,-1]){
            rotate([0,0,0]){
                cylinder(h=17, r=1.2, $fn=100, center=false);
            }
        }           

        translate([-hele_breedte/2+2,diepte/2-3-10,-1]){
            rotate([0,0,0]){
                cylinder(h=17, r=1.2, $fn=100, center=false);
            }
        }           
        translate([-hele_breedte/2+2,-diepte-2-diepte/2+3+10,-1]){
            rotate([0,0,0]){
                cylinder(h=17, r=1.2, $fn=100, center=false);
            }
        }           
        

        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30,diepte/2+9.5,-1]){
            rotate([0,0,0]){
                cylinder(h=30, r=2, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30,diepte/2+9.5,-1]){
            rotate([0,0,0]){
                cylinder(h=30, r=2, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-9.5,-1]){
            rotate([0,0,0]){
                cylinder(h=30, r=2, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-9.5,-1]){
            rotate([0,0,0]){
                cylinder(h=30, r=2, $fn=100, center=false);
            }
        }        

        translate([0,5.5,4]){
            rotate([0,0,0]){
                include <servo-mal.scad>;                
            }
        }  
        translate([breedte_ruimte_tussen_2_motoren+motor_breedte,5.5,4]){
            rotate([0,0,0]){
                include <servo-mal.scad>;                
            }
        }  
        
        translate([breedte_ruimte_tussen_2_motoren+motor_breedte,-(lengte_ruimte_tussen_2_motoren+motor_lengte)-5,4]){
            rotate([0,0,180]){
                include <servo-mal.scad>;                
            }
        }  
        translate([0,-(lengte_ruimte_tussen_2_motoren+motor_lengte)-5,4]){
            rotate([0,0,180]){
                include <servo-mal.scad>;                
            }
        }  
        
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2+25-4,40,12]){
            rotate([90,0,0]){
                cylinder(h=117, r=1.2, $fn=100, center=false);
            }
        }         
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-25+4,40,12]){
            rotate([90,0,0]){
                cylinder(h=117, r=1.2, $fn=100, center=false);
            }
        }         
        
// gaten voor magneet snoeren
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-4+10,-64.6+2,-1]){
            cube([8,3,15], center=false);
        }         
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-4+10,-64.6-0.5,-1]){
            cube([3,3,15], center=false);
        }         

        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-4+10,32.2-2,-1]){
            cube([8,3,15], center=false);
        }         
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2-4+10,32.2+0.5,-1]){
            cube([3,3,15], center=false);
        }         
//gaten voor printplaat
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2,-64.6+6,-1]){
                cylinder(h=117, r=1.2, $fn=100, center=false);
        }         
        translate([motor_breedte/2+breedte_ruimte_tussen_2_motoren/2,-64.6+6+afstand_tussen_print_gaten,-1]){
                cylinder(h=117, r=1.2, $fn=100, center=false);
        }         
        
// gaten voor motorsnoeren
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30-12,-diepte/2-lengte_ruimte_tussen_2_motoren/2,-1]){
            cube([3,15,15], center=false);
        }        

        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30-15,-diepte/2-lengte_ruimte_tussen_2_motoren/2,-1]){
            cube([4,5,15], center=false);
        }        

        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30+9,-diepte/2-lengte_ruimte_tussen_2_motoren/2,-1]){
            cube([3,15,15], center=false);
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30+11,-diepte/2-lengte_ruimte_tussen_2_motoren/2,-1]){
            cube([4,5,15], center=false);
        }        

// schroef voor draden moter achter te vouwen
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2-30-10,-diepte/2-lengte_ruimte_tussen_2_motoren/2+19,-1]){
            rotate([0,0,0]){
                cylinder(h=17, r=1.2, $fn=100, center=false);
            }
        }        


        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2+30+9,-diepte/2-lengte_ruimte_tussen_2_motoren/2+19,-1]){
            rotate([0,0,0]){
                cylinder(h=17, r=1.2, $fn=100, center=false);
            }
        }  
        
	}
}
