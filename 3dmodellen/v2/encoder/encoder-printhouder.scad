
difference(){
	union(){
        

        translate([10.5,0,0]){
            cube([7,3,31], center=false);
        }         
        translate([32.5,0,0]){
            cube([7,3,31], center=false);
        }         
        translate([-2,0,31]){
            cube([41.5,2,7], center=false);
        }         
        translate([-2,0,35]){
            cube([10,16.5,3], center=false);
        }         
   
        
   

	}
	union() {
        translate([14,30,5]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.5, $fn=100, center=false);
            }
        }       
        translate([36,30,5]){
            rotate([90,0,0]){
                cylinder(h=99, r=1.5, $fn=100, center=false);
            }
        }       
        translate([2,9.5,0]){
            rotate([0,0,90]){
                cylinder(h=99, r=1.5, $fn=100, center=false);
            }
        }       

  

	}
}
