difference(){
	union(){ 

        difference(){
            union(){ 
                translate([-27.5, -2,0]){
                    linear_extrude(height = 0.8, center = false, convexity = 10)
                    scale([1,1]) import(file = "svg/pion_dicht.svg", layer = "plate");
                }                              
            }
            union() {
               
            }
         }

	}
	union() {
    }
}


 