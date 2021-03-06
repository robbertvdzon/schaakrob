dikte_dunne_as = 1.65;
afstand_tussen_assen = 1.7;
dikte_dikke_as = 5;
totale_dikte = dikte_dunne_as*2+afstand_tussen_assen;

difference(){
	union(){


        translate([-20,-7,0]){
           cube([40,20,1], center=false);
        }        


        translate([0,5,15]){
            rotate([90,0,0]){
               cylinder(h=5, r=15, $fn=100, center=false);
            }
        }

        
	}
	union() {

        translate([0,6,15]){
            rotate([90,0,0]){
               cylinder(h=7, r=10, $fn=100, center=false);
            }
        }

        
        translate([-20,dikte_dunne_as,23]){
           cube([40,afstand_tussen_assen,8], center=false);
        }        
        
        translate([0,30,3]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }

        translate([0,30,27]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }



	}
}
