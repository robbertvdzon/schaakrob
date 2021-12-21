
difference(){
	union(){
        
        translate([-13.5,-10,30]){
            cube([
            12,
            21-8,
            ,4], center=false);
        }       

        

	}
	union() {
        // schroefgaten voor ashouder1
        translate([-10,-5,0]){
                cylinder(h=100, r=1.6, $fn=100, center=false);
        }  
        translate([-5,-5,0]){
                cylinder(h=100, r=1.6, $fn=100, center=false);
        }  
	}
}
