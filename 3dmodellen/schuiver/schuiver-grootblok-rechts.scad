mirror([1,0,0]){
    
difference(){
        union(){
            include <schuiver-grootblok-links.scad>;                
        
            translate([0,0.5,0]){
                rotate([180,90,0]){
                    translate([-13,-7,-7+7.5]){
                        cube([26,14,35-7.5-7.5], center=false);
                    }         
                }
            }  
        }
        union() {
           translate([0,0,0]){
                rotate([180,90,0]){
                    include <grafiethouder-reverse.scad>;                
                }
            }  
        }
    }
}
