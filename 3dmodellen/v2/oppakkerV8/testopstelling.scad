

difference(){
	union(){    
        
        
        translate([-71/2,-102/2+10,10]){
//              cube([71,82,30], center=false);
        }
        translate([-62,-62,10]){
              cube([124,124,35], center=false);
        }
        translate([-62,-80,10]){
              cube([5,160,35], center=false);
        }

        

	}
	union() {


        translate([-55,-55,0]){
              cube([110,110,50], center=false);
        }

       translate([0,250,19]){
            rotate([90,0,0]){
                cylinder(h=500, r=4.3, $fn=100, center=false);
            }            
        }
       translate([-250,0,29]){
            rotate([0,90,0]){
                cylinder(h=500, r=4.3, $fn=100, center=false);
            }            

        }

       translate([-250,70,29]){
            rotate([0,90,0]){
                cylinder(h=500, r=2, $fn=100, center=false);
            }            

        }
       translate([-250,-70,29]){
            rotate([0,90,0]){
                cylinder(h=500, r=2, $fn=100, center=false);
            }            

        }
        
        
	}
}
