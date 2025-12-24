
difference(){
	union(){
        translate([-8,1,-34]){
            cube([19,3,68], center=false);
        }           

        translate([-8,1,-34]){
            cube([19,4,5], center=false);
        }           
        translate([-8,1,-34+68-5]){
            cube([19,4,5], center=false);
        }           

        translate([-8,1,-34-5]){
            cube([19,5,5], center=false);
        }           
        translate([-8,1,-34+68]){
            cube([19,5,5], center=false);
        }           




	}
	union() {
        translate([4,0,-12]){
            cube([8,20,24], center=false);
        }           
       
        translate([-6.5,8.5,-19]){
            rotate([90,0,0]){
                cylinder(h=125, r=5, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,-19]){
            rotate([90,0,0]){
                cylinder(h=125, r=2, $fn=100, center=false);
            }
        }         

        translate([-6.5,8.5,19]){
            rotate([90,0,0]){
                cylinder(h=125, r=5, $fn=100, center=false);
            }
        }         
        translate([6.5,8.5,19]){
            rotate([90,0,0]){
                cylinder(h=125, r=2, $fn=100, center=false);
            }
        }              



	}
}
