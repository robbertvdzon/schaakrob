dikte_dunne_as = 1.65;
afstand_tussen_assen = 1.7;
dikte_dikke_as = 5;
totale_dikte = dikte_dunne_as*2+afstand_tussen_assen;

difference(){
	union(){
        translate([0,0,0]){
           cube([5,dikte_dunne_as,14], center=false);
        }        
        translate([0,afstand_tussen_assen+dikte_dunne_as,0]){
           cube([5,dikte_dunne_as,14], center=false);
        }        

        translate([0,totale_dikte/2-dikte_dikke_as/2,10]){
           cube([5,5,20], center=false);
        }        



        
	}
	union() {
        
        
        translate([2.6,30,3]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }

        translate([2.6,30,27]){
            rotate([90,0,0]){
                cylinder(h=50, r=1.2, $fn=100, center=false);
            }
        }



	}
}
