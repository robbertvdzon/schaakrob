difference(){
	union(){ 

        difference(){
            union(){ 
                translate([-27.2, -2,0]){
                    linear_extrude(height = 2, center = false, convexity = 10)
                    scale([1,1]) import(file = "svg/pion_dicht.svg", layer = "plate");
                }                              
            }
            union() {
                translate([0, 0,-0.1]){
                  cylinder(h=2.2, r=15 , $fn=100, center=false);
                }  
                translate([0, 5,-0.1]){
                  cylinder(h=2.2, r=12 , $fn=100, center=false);
                }  
                translate([0, 12,-0.1]){
                  cylinder(h=2.2, r=7.5 , $fn=100, center=false);
                }  
                translate([0, 15,-0.1]){
                  cylinder(h=2.2, r=6 , $fn=100, center=false);
                }    
            }
         }

	}
	union() {
    }
}


 