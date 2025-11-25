
difference(){
	union(){
        
        translate([-13,0,-4.5]){
            cube([28,3,10], center=false);
        }         
        translate([-5,0,-2.5]){
            cube([10,1,30], center=false);
        }         
        translate([-5,0,25]){
            cube([10,10,5], center=false);
        }         



          
      

	}
	union() {
        translate([-7.5,-1,0]){
            rotate([270,0,0]){
                cylinder(h=25, r=3, $fn=100, center=false);
            }
        }         
        translate([7.5,-1,0]){
            rotate([270,0,0]){
                cylinder(h=25, r=3, $fn=100, center=false);
            }
        }       
//

 

	}
}
