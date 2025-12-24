difference(){
	union(){
        
        translate([-25,4.5,10]){
            cube([15+17,3,5], center=false);
        }         
        translate([-25,4.5,10]){
            cube([13,5,5], center=false);
        }         
        


        translate([-6.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }         
        translate([6.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=3, r=4, $fn=100, center=false);
            }
        }         


	}
	union() {
        translate([-6.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }             


   

	}
}
