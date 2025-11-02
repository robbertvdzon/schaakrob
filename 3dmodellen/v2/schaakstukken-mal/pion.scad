
difference(){
    union(){ 
        translate([-27.5, -2,3]){
            linear_extrude(height = 5, center = false, convexity = 10)
            scale([1,1]) import(file = "svg/pion_dicht.svg", layer = "plate");
        }       
        translate([0+10,5,4.5]){
            rotate([0,0,0]){
                cylinder(h=4, r=2.7, $fn=100, center=false);
            }   
        }
        translate([0-10,5,4.5]){
            rotate([0,0,0]){
                cylinder(h=4, r=2.7, $fn=100, center=false);
            }   
        }
        translate([0,15,4.5]){
            rotate([0,0,0]){
                cylinder(h=4, r=2.7, $fn=100, center=false);
            }   
        }        

    }
    union() {
        translate([0+5,5,7]){
            rotate([0,0,0]){
                cylinder(h=20, r=1, $fn=100, center=false);
            }   
        }
        translate([0-5,5,7]){
            rotate([0,0,0]){
                cylinder(h=20, r=1, $fn=100, center=false);
            }   
        }
        translate([0,10,7]){
            rotate([0,0,0]){
                cylinder(h=20, r=1, $fn=100, center=false);
            }   
        }        

    }
 }


    
    
     