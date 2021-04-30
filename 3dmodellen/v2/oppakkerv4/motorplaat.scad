breedte = 22.7;
hele_breedte = 32.5;
diepte = 27;
dikte_rand = 2.5;
marge = 0.2;


motor_breedte = hele_breedte+marge*2;
motor_lengte = diepte+marge*2;

breedte_ruimte_tussen_2_motoren = 2;
lengte_ruimte_tussen_2_motoren = 2;


difference(){
	union(){
        
        translate([-motor_breedte/2,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2
            ,motor_lengte*2+lengte_ruimte_tussen_2_motoren*2
            ,4], center=false);
        }         

	}
	union() {

        // ruimte voor assen
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,diepte/2+4,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=6.5, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-4,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=6.5, $fn=100, center=false);
            }
        }            


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

	}
}
