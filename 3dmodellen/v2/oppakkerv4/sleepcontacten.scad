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


    

        translate([2.75,-7,13]){
            cube([
            31.5,10,17], center=false);
        }       
        translate([2.75,-22,25]){
            cube([
            31.5,15,5], center=false);
        }       

/*


        translate([-motor_breedte/2+15,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30
            ,4
            ,18], center=false);
        }       
s
        translate([-motor_breedte/2+15,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+motor_lengte*2+lengte_ruimte_tussen_2_motoren*2-4-6,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30
            ,4
            ,18], center=false);
        }       


        translate([-motor_breedte/2+15,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            4
            ,motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-24
            ,30], center=false);
        }       

        translate([-motor_breedte/2+15+motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30-4,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            4
            ,motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-24
            ,30], center=false);
        }       

*/



        

	}
	union() {
        
        translate([-20.25,-19.5,28]){
            rotate([180,0,0]){      
                // as gaten voor schuif assen        
                translate([-9.5+75/2,50,17.5]){
                    rotate([90,0,0]){
                        cylinder(h=200, r=5, $fn=100, center=false);
                    }
                }
                translate([9.5+75/2,50,17.5]){
                    rotate([90,0,0]){
                        cylinder(h=200, r=5, $fn=100, center=false);
                    }
                }        
                translate([-10,-10+5,6.5]){
                    rotate([0,90,0]){
                       cylinder(h=200, r=5, $fn=100, center=false);
                    }
                }        
               
    

            }
       }        
        

	}
}
