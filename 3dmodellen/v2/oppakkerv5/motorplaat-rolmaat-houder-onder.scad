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
         

        translate([-motor_breedte/2+9.5+4   ,  -motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6+4,2]){
            cube([
            13
            ,10
            ,15], center=false);
        }        


	}
	union() {
        translate([1,15,10.5]){
            rotate([90,0,0]){
                cylinder(h=1000, r=1.5, $fn=100, center=false);
            }
        }   

        translate([-motor_breedte/2+9.5+2+8   ,  -motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6+6,4]){
            rotate([0,0,-20]){
                cube([
                1
                ,15
                ,30], center=false);
            }
        }       
        

	}
}
