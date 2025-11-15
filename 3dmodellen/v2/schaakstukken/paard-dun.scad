difference(){
	union(){ 

        difference(){
            union(){ 
                mirror([1,0,0])
                translate([-27.5, -2,0]){
                    linear_extrude(height = 0.5, center = false, convexity = 10)
                    scale([1,1]) import(file = "svg/paard_dicht.svg", layer = "plate");
                }                              
            }
            union() {
            }
         }

	}
	union() {
    }
}


 