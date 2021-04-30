breedte = 22.7;
hele_breedte = 32.5;
diepte = 27;
dikte_rand = 2.5;
marge = 0.2;

extra_lengte_voor_en_achter = 18;

motor_breedte = hele_breedte+marge*2;
motor_lengte = diepte+marge*2;

breedte_ruimte_tussen_2_motoren = 2;
lengte_ruimte_tussen_2_motoren = 2;
rand_marge_kleiner = 0.01;

difference(){
	union(){

        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,diepte/2+4,0]){
            rotate([0,0,0]){
                cylinder(h=20, r=6, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-4,0]){
            rotate([0,0,0]){
                cylinder(h=20, r=6, $fn=100, center=false);
            }
        }        

        
        translate([
            -motor_breedte/2+rand_marge_kleiner,
            -motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+rand_marge_kleiner-extra_lengte_voor_en_achter,
            0
        ]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren-rand_marge_kleiner*2,
            motor_lengte*2+lengte_ruimte_tussen_2_motoren-rand_marge_kleiner*2+extra_lengte_voor_en_achter*2,
            4], center=false);
        }         
        

	}
	union() {
        
        // middengat
        translate([hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren/2,-diepte/2-lengte_ruimte_tussen_2_motoren/2,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=2, $fn=100, center=false);
            }
        }           

        // schroefgaten
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-2,diepte/2-3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren,-diepte-2-diepte/2+3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           

        translate([-hele_breedte/2+2,diepte/2-3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           
        translate([-hele_breedte/2+2,-diepte-2-diepte/2+3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           
        

        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,diepte/2+4,-1]){
            rotate([0,0,0]){
                cylinder(h=30, r=4, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-4,-1]){
            rotate([0,0,0]){
                cylinder(h=30, r=4, $fn=100, center=false);
            }
        }        

        translate([0,0,2]){
            rotate([0,0,0]){
                include <servo-mal.scad>;                
            }
        }  
        translate([breedte_ruimte_tussen_2_motoren+motor_breedte,0,2]){
            rotate([0,0,0]){
                include <servo-mal.scad>;                
            }
        }  
        
        translate([breedte_ruimte_tussen_2_motoren+motor_breedte,-(lengte_ruimte_tussen_2_motoren+motor_lengte),2]){
            rotate([0,0,180]){
                include <servo-mal.scad>;                
            }
        }  
        translate([0,-(lengte_ruimte_tussen_2_motoren+motor_lengte),2]){
            rotate([0,0,180]){
                include <servo-mal.scad>;                
            }
        }  
	}
}
