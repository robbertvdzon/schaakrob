breedte = 22.7;
hele_breedte = 32.5;
diepte = 27;
dikte_rand = 2.5;
marge = 0.2;


motor_breedte = hele_breedte+marge*2;
motor_lengte = diepte+marge*2;

breedte_ruimte_tussen_2_motoren = 2;
lengte_ruimte_tussen_2_motoren = 2;
//38
//28

difference(){
	union(){
        
        translate([-12.45,-31.7,30]){
            cube([
            42.8,
            35,
            ,4], center=false);
        }       

// montage hoeken
        translate([-12.45,-31.7,26]){
            cube([
            3,
            35,
            7], center=false);
        }       

        translate([-12.45+58.8-19,-31.7,26]){
            cube([
            3,
            35,
            7], center=false);
        }       


	}
	union() {
        translate([-10.05,-28.2,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
        }       
        translate([-10.05,-28.2+28,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
        }       
        translate([-10.05+38,-28.2,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
        }       
        translate([-10.05+38,-28.2+28,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
        }       


        
        // schroefgaten voor geleider
        translate([-20,5-1.2-8,28]){
            rotate([0,90,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
            }
        }           
        translate([-20,-31-1.2+8,28]){
            rotate([0,90,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
            }
        }       


	}
}
