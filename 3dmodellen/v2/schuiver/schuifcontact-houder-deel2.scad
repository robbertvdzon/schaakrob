
difference(){
	union(){
        

        translate([1,0,0]){
            cube([6,8,1], center=false);
        }         
        translate([4,4,0]){
            rotate([0,0,0]){
                cylinder(h=7, r=2, $fn=100, center=false);
            }
        }         

          
      

	}
	union() {

	}
}
