afstand_tussen_gaten = 27.9;
hoogte_gaten = 6.5;
pos_motor2 = afstand_tussen_gaten+5+1;
dikte_blokken = 4;
breedte_blokken = 4.5;
pos_achterste_motoren = 24+dikte_blokken;

difference(){
	union(){
        

        translate([0,0,-2]){
            cube([pos_motor2+afstand_tussen_gaten+5,pos_achterste_motoren+dikte_blokken,2], center=false);
        }         



        translate([0,0,0]){
            cube([5,dikte_blokken,hoogte_gaten+2], center=false);
        }         
        translate([afstand_tussen_gaten,0,0]){
            cube([breedte_blokken,dikte_blokken,hoogte_gaten+2], center=false);
        }         

        translate([pos_motor2,0,0]){
            cube([breedte_blokken,dikte_blokken,hoogte_gaten+2], center=false);
        }         
        translate([pos_motor2+afstand_tussen_gaten,0,0]){
            cube([breedte_blokken,dikte_blokken,hoogte_gaten+2], center=false);
        }         
        


        translate([0,pos_achterste_motoren,0]){
            cube([5,dikte_blokken,hoogte_gaten+2], center=false);
        }         
        translate([afstand_tussen_gaten,pos_achterste_motoren,0]){
            cube([5,dikte_blokken,hoogte_gaten+2], center=false);
        }         

        translate([pos_motor2,pos_achterste_motoren,0]){
            cube([5,dikte_blokken,hoogte_gaten+2], center=false);
        }         
        translate([pos_motor2+afstand_tussen_gaten,pos_achterste_motoren,0]){
            cube([5,dikte_blokken,hoogte_gaten+2], center=false);
        }         
        

   

	}
	union() {
        translate([breedte_blokken/2,100,hoogte_gaten]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }       
        translate([breedte_blokken/2+afstand_tussen_gaten,100,hoogte_gaten]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }       

        translate([pos_motor2+breedte_blokken/2,100,hoogte_gaten]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }       
        translate([pos_motor2+breedte_blokken/2+afstand_tussen_gaten,100,hoogte_gaten]){
            rotate([90,0,0]){
                cylinder(h=200, r=1.2, $fn=100, center=false);
            }
        }       

  

	}
}
